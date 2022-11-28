package edu.virginia.cs;

import org.hibernate.Session;

public class StateDemo {
    public static void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            State virginia = new State("Virginia", 8700000, "Richmond");
            session.save(virginia);
            session.getTransaction().commit();
        }
    }
}
