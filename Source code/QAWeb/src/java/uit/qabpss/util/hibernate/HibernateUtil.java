package uit.qabpss.util.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import uit.qabpss.dbconfig.ColumnInfo;
import org.hibernate.Query;
import uit.qabpss.dbconfig.DBInfo;
import uit.qabpss.dbconfig.DBInfoUtil;
import uit.qabpss.dbconfig.TableInfo;
import uit.qabpss.dbconfig.Type;
import uit.qabpss.preprocess.TripleWord;

public class HibernateUtil {
    
	private static SessionFactory sessionFactory;
	
	static {
		sessionFactory = new  Configuration().configure().buildSessionFactory();
                DBInfoUtil.initDb();

	}
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
        public static void closeSessionFactory() {
		sessionFactory.close();
	}


        public static void main(String[] args){
//            TripleWord t = HibernateUtil.getTripleFromValue("1999");
//             TripleWord t = HibernateUtil.getTripleFromValue("ACM");
//             TripleWord t = HibernateUtil.getTripleFromValue("Elsevier");
//             TripleWord t = HibernateUtil.getTripleFromValue("On Resolving Schematic Heterogeneity in Multidatabase Systems");
             //TripleWord t = HibernateUtil.getTripleFromValue("Spatial Data Structures.");
            //test with DOI values
//            TripleWord t = HibernateUtil.getTripleFromValue("10.1007/BFb0015242");
//            TripleWord t = HibernateUtil.getTripleFromValue("10.2200/S00193ED1V01Y200905CAC006");
//              TripleWord t = HibernateUtil.getTripleFromValue("10.1109/IM.2003.1240282");
            //Test with ISBN values
//            TripleWord t = HibernateUtil.getTripleFromValue("0-201-59098-0");
//            TripleWord t = HibernateUtil.getTripleFromValue("0-201-39829-X");
//            TripleWord t = HibernateUtil.getTripleFromValue("978-3-540-73108-5");
            //System.out.println("<" + t.getFirstObject() + "," + t.getRelationWord() + "," + t.getSecondObject() + ">");
        }
}
