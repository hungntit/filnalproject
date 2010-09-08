/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qass.core.search;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uit.qass.model.Publication;
import uit.qass.util.hibernate.HibernateUtil;

/**
 *
 * @author Hoang-PC
 */
public class searchPublication {

    public static Publication searchPubByID(String id) {
        Publication result = new Publication();
        int pubID = Integer.parseInt(id);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        result = (Publication) session.get(Publication.class, pubID);
        return result;
    }
}
