package edu.virginia.cs;


import org.hibernate.Session;

import java.sql.Timestamp;

public class CreateBob {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Client bob = new Client();
        bob.setName("Bob Smith");
        bob.setAddress("123 Main Street");
        bob.setEmail("bob@bob.bob");

        Account bobChecking = new Account();
        bobChecking.setAccountHolder(bob);
        bobChecking.setAccountType(AccountType.CHECKING);
        bobChecking.setBalance(123.45);

        Account bobSavings = new Account();
        bobSavings.setAccountHolder(bob);
        bobSavings.setAccountType(AccountType.SAVINGS);
        bobSavings.setBalance(987.65);

        Transaction bobBuysGum = new Transaction();
        bobBuysGum.setAccount(bobChecking);
        bobBuysGum.setAmount(0.50);
        bobBuysGum.setTimestamp(new Timestamp(System.currentTimeMillis()));

        session.persist(bob);
        session.persist(bobChecking);
        session.persist(bobSavings);
        session.persist(bobBuysGum);


        session.getTransaction().commit();
        session.close();
        HibernateUtil.shutdown();
    }
}
