package edu.virginia.cs;


import org.hibernate.Session;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class CreateClient {
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Client bob = new Client();
        bob.setName("Bob Smith");
        bob.setAddress("123 Main Street");
        bob.setEmail("bob@bob.bob");
        List<Account> accountList = new ArrayList<>();

        Account bobChecking = new Account();
        bobChecking.setAccountHolder(bob);
        bobChecking.setAccountType(AccountType.CHECKING);
        bobChecking.setBalance(123.45);
        accountList.add(bobChecking);


        Account bobSavings = new Account();
        bobSavings.setAccountHolder(bob);
        bobSavings.setAccountType(AccountType.SAVINGS);
        bobSavings.setBalance(987.65);
        accountList.add(bobSavings);

        Transaction bobBuysGum = new Transaction();
        bobBuysGum.setAccount(bobChecking);
        bobBuysGum.setAmount(0.50);
        bobBuysGum.setTimestamp(new Timestamp(System.currentTimeMillis()));

        bob.setAccounts(accountList);
        bobChecking.getTransactionList().add(bobBuysGum);

        session.persist(bob);
        session.persist(bobChecking);
        session.persist(bobSavings);
        session.persist(bobBuysGum);


        session.getTransaction().commit();
        session.close();
        HibernateUtil.shutdown();
    }
}
