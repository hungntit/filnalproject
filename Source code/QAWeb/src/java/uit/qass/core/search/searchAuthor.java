/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qass.core.search;

import java.util.ArrayList;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uit.qass.model.Author;
import uit.qass.model.Publication;

import uit.qass.util.hibernate.HibernateUtil;

/**
 *
 * @author Hoang-PC
 */
public class searchAuthor {

    private static int MAX_RESULT = 30;

    public static List<String> searchAuthorByKey(String key) {
        List result = new ArrayList();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query q = session.createSQLQuery("select distinct author from dblp_author_ref_new where dblp_author_ref_new.author like :var");
        q.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        q.setString("var", key + '%');
        q.setMaxResults(MAX_RESULT);
        result = q.list();
        session.close();
        return result;
    }

    public static List<Publication> searchPubsByAuthorName(String name) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        session = sessionFactory.openSession();
        Query q = session.createQuery("from Author au where au.author = :var");
        q.setString("var", name);
        List authors = q.list();
        List<Publication> pubs = new ArrayList<Publication>();
        for (int i = 0; i < authors.size(); i++) {
            Author au = (Author) authors.get(i);
            Publication mypub = au.getPublications();
            pubs.add(mypub);
        }
        return pubs;
    }
}
