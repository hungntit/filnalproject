package uit.qabpss.util.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import uit.qabpss.dbconfig.DBInfoUtil;

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
}
