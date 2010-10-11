/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qabpss.analysisandprocess;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uit.qabpss.util.hibernate.HibernateUtil;

/**
 *
 * @author aa
 */
public class EntityReg {
    
    public String identifiedRecog(String namedEntity){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query q = null;
        //recognize Author
        q = session.createQuery("from Author where author = :var");
        q.setString("var", namedEntity);
        q.setMaxResults(1);
        if(q.list().size()>0){
            return "Author.author";
        }
        //recognize Title
        String tempNE = namedEntity.replace(" ","").replace(".", "").replace("-", "").replace("?", "");
        q = session.createQuery("from Publication where titleSignature = :var");
        q.setString("var", tempNE);
        q.setMaxResults(1);
        if(q.list().size()>0){
            return "Publication.title";
        }

        q = session.createQuery("from Publication where publisher = :var");
        q.setString("var", tempNE);
        q.setMaxResults(1);
        if(q.list().size()>0){
            return "Publication.publisher";
        }

        q = session.createQuery("from Publication where source = :var");
        q.setString("var", tempNE);
        q.setMaxResults(1);
        if(q.list().size()>0){
            return "Publication.source";
        }

         q = session.createQuery("from Publication where type = :var");
        q.setString("var", tempNE);
        q.setMaxResults(1);
        if(q.list().size()>0){
            return "Publication.type";
        }

        return null;
    }
}
