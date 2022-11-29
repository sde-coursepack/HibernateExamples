package edu.virginia.cs;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class GetExample {
    private static final int BOB_ID_NUMBER = 1; //may need to update this number for your code to work
    private static Session session;

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Get a client with a known ID number
        Client bob = getClientFromIdNumber(BOB_ID_NUMBER);

        // Get all clients
        List<Client> clientList = getAllClientsFromDatabase();

        System.out.println("=======Printing Client List========");
        clientList.forEach(System.out::println);
        clientList.forEach(client -> System.out.println(client.getAccounts()));

        System.out.println("=======Printing All of Bob's Transactions========");
        for (Account account : bob.getAccounts()) {
            for (Transaction transaction : account.getTransactionList()) {
                System.out.printf("Transaction by %s on Account #%d - %f\n", bob.getName(), account.getId(), transaction.getAmount());
            }
        }

        System.out.println("=======Deposit 100 dollars to Bob's primary checking account========");
        //Get only Bob's checking accounts using a query
        List<Account> bobsCheckingAccounts = getCheckingAccountsForClient(bob);

        //add 100 dollars
        deposit100ToAccount(bobsCheckingAccounts.get(0));

        session.close();
    }

    private static Client getClientFromIdNumber(int idNumber) {
        return session.get(Client.class, idNumber);
    }

    private static List<Client> getAllClientsFromDatabase() {
        String allClients = "FROM Client"; //Note that this uses the CLASS name, not the table name CLIENTS
        Query<Client> query = session.createQuery(allClients, Client.class);
        return query.getResultList();
    }

    private static List<Account> getCheckingAccountsForClient(Client client) {
        CriteriaBuilder builder = session.getCriteriaBuilder(); // used to create our query that uses "where" criteria
        CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);

        //Predicates are individual criteria for a select statement
        Predicate checkingAccounts = builder.equal(root.get("accountType"), AccountType.CHECKING); //checking accounts only
        Predicate bobAccountId = builder.equal(root.get("accountHolder"), client.getId()); //accountHolder is Bob's ID

        //Add the predicates to the criteria with an AND
        criteria.select(root).where(builder.and(checkingAccounts, bobAccountId));

        //Generate a query from the criteria
        Query<Account> this_query = session.createQuery(criteria);

        //Get the list of accounts returned by the criteria
        return this_query.getResultList();
    }

    private static void deposit100ToAccount(Account account) {
        System.out.printf("Checking Total before deposit: %f\n", account.getBalance());
        account.deposit(100);
        System.out.printf("Checking Total after deposit: %f\n", account.getBalance());
        session.persist(account); //Update's the account balance
        session.getTransaction().commit(); //save the change
    }
}
