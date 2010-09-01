package uit.qass.model;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uit.qass.util.HibernateUtil;
import uit.qass.model.Publication;
 
public class Test {

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        //get Publication with ID = 1
        Publication a = (Publication) session.load(Publication.class, 1);
        System.out.println(a.getTitle());
//        System.out.println(a.getAuthors().size());

        // get author with name = Philip K. Chan
        Query q = session.createQuery("from Author au where au.author = :var");
        q.setString("var", "Philip K. Chan");
        List result = q.list();
        System.out.println("Number of Objects: " + result.size());
        for (int i = 0; i < result.size(); i++) {
            Author temp = (Author) result.get(i);
            Publication pubs = temp.getPublications();
            System.out.println("Publication -------------");
            System.out.println("ID: " + pubs.getId());
            System.out.println("Name: " + pubs.getTitle());
            System.out.println("--------------------------");
        }
    }
}
