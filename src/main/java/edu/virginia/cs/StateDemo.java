package edu.virginia.cs;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class StateDemo {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Persist Example
        State virginia = new State("Virginia", 8700000, "Richmond");
        session.persist(virginia);
        session.getTransaction().commit();

        // Get Example
        State maryland = session.get(State.class, 1);
        System.out.println(maryland);

        // Get All States:
        String hql = "from State"; //class name, not Table name!
        Query<State> stateQuery = session.createQuery(hql);
        List<State> stateList = stateQuery.list();
        System.out.printf("Number of States = %d\n", stateList.size());

        // Get the State with the capitol Annapolis
        hql = "from State S where S.name = 'Annapolis'";
        Query<State> annapolisQuery = session.createQuery(hql);
        List<State> annapolisList = stateQuery.list();
        System.out.printf("State with capitol Annapolis = %s\n", stateList.get(0));

        session.close();
    }
}
