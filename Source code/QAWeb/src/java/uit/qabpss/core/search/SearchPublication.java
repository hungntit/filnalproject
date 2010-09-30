/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.core.search;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uit.qabpss.model.Publication;
import uit.qabpss.util.dao.orm.hibernate.QueryPos;
import uit.qabpss.util.hibernate.HibernateUtil;

/**
 *
 * @author Hoang-PC
 */
public class SearchPublication {

    private static int MAX_RESULT = 100;

    public static Publication searchPubByID(String id) {
        Publication result = new Publication();
        int pubID = Integer.parseInt(id);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        result = (Publication) session.get(Publication.class, pubID);
        return result;
    }

    public static List searchTop100(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        SQLQuery   q   = session.createSQLQuery("select * from dblp_pub_new order by year desc");
        q.addEntity(Publication.class);
        q.setMaxResults(MAX_RESULT);
        return q.list();
    }

    public static List searchByType(String type){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        SQLQuery   q   = session.createSQLQuery("SELECT * FROM dblp_pub_new where type= :var order by year desc limit 100;");
        q.addEntity(Publication.class);
        q.setString("var", type);
        return q.list();
    }
}
