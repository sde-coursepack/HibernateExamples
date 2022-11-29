package edu.virginia.cs;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class StateDemo {
    private static Session session;

    public static void main(String[] args) {
        // This is used to adjust the logging - without this line you get a lot of annoying log information - useful
        // when testing production apps, but unneeded for this class. SEVERE will still show us any meaningful
        // Exceptions, even when we catch and handle them.
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Persist Example
        try {
            State virginia = new State("Virginia", 8700000, "Richmond");
            session.persist(virginia);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println("Virginia already added!");
        }

        // Get Example
        State maryland = session.get(State.class, 1);
        System.out.println(maryland);

        // Get All States:
        String hql = "SELECT s FROM State s"; //class name, not Table name!
        Query<State> stateQuery = session.createQuery(hql, State.class);
        List<State> stateList = stateQuery.getResultList();
        System.out.printf("Number of States = %d\n", stateList.size());

        // Get the State with the capitol Annapolis
        State state = getStateByCapitolCity("Annapolis");
        System.out.printf("State with capitol Annapolis = %s\n", state);

        session.close();
    }

    private static State getStateByCapitolCity(String capitolCity) {
        String hql = "SELECT e FROM State e WHERE e.capitolCity = :name";
        TypedQuery<State> capitolQuery = StateDemo.session.createQuery(hql, State.class);
        capitolQuery.setParameter("name", capitolCity);
        return capitolQuery.getSingleResult();
    }
}
