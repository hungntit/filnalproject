package com.myapp.struts.init;

import java.io.InputStream;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.struts.util.LabelValueBean;

import uit.qabpss.dbconfig.DBInfoUtil;
import uit.qabpss.util.hibernate.HibernateUtil;

/**
 * ApplicationScopeInit initializes relevant objects and puts them in application scope.
 * In this case, it reads the states.properties and puts it in applicationscope with the
 * name "struts.example.states"
 *
 * @author Srikanth Shenoy
 * @version $Revision:   $ $Date:   $
 */
public class ApplicationScopeInit implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            HibernateUtil.getSessionFactory();
            DBInfoUtil.initDb();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            //Use context classloader to read states.properties
            InputStream iStream = loader.getResourceAsStream("uit/qabpss/extracttriple/SampleQuestion.properties");
            Properties props = new Properties();
            //Load the stream into the properties object directly
            props.load(iStream);
            //Look up by key and load them into a ArrayList as NameValuePair collection
            Enumeration keyEnum = props.propertyNames();
            // Use a Sorted Set to hold the state names and values
            // Define an anonymous inner class to provide
            // the comparison algorithm to the TreeSet
            Set stateSet = new TreeSet(
                    new Comparator() {
                        public int compare(Object a, Object b) {
                            LabelValueBean nvpA = (LabelValueBean) a;
                            LabelValueBean nvpB = (LabelValueBean) b;

                            String valA = nvpA.getLabel();
                            String valB = nvpB.getLabel();
                            return valA.compareTo(valB);
                        }
                    });
            LabelValueBean nvp = null;
            String keyName = null;
            String label = null;
            while (keyEnum.hasMoreElements()) {
                keyName = (String) keyEnum.nextElement();
                label = props.getProperty(keyName);
                nvp = new LabelValueBean(label, keyName);
                stateSet.add(nvp);
            }
            //Get ServletContext and set the properties as a application scope object
            ServletContext context = event.getServletContext();
            context.setAttribute("EXAMPLE_QUESTIONS", stateSet);
        } catch (Exception e) {
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        HibernateUtil.closeSessionFactory();
    }
}
