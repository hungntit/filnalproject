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
import uit.qass.util.Table;

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

        String[] words = getKeys(key);
        String sql = createSqlSearchAuthor(Table.AUTHOR, Table.AUTHOR_FIELD, words.length);
        Query q = session.createSQLQuery(sql);
        q.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        for (int i = 0; i < words.length; i++) {
            q.setString("var"+i, '%' + words[i] + '%');
        }
        q.setMaxResults(MAX_RESULT);
        result = q.list();
        session.close();
        return result;
    }

    public static List<Publication> searchPubsByAuthorName(String name) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
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

    private static String createSqlSearchAuthor(String tabelName, String field, int numKeys) {
        String result = "";
        result = " select distinct " + field + " from " + tabelName + " ";
        if (numKeys > 0) {
            String add = "where ";
            for (int i = 0; i < numKeys; i++) {
                if (i == numKeys - 1) {
                    add += field + " like :var" + i;
                } else {
                    add += field + " like :var" + i + " and ";
                }
            }
            result = result + add;
        }
        return result;
    }

    private static String[] getKeys(String key){
        String[] result = key.split(" ");
        return result;
    }
}
