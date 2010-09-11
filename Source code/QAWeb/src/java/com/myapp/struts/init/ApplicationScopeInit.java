package com.myapp.struts.init;

import java.io.IOException;
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
import uit.qass.util.hibernate.HibernateUtil;

/**
 * ApplicationScopeInit initializes relevant objects and puts them in application scope.
 * In this case, it reads the states.properties and puts it in applicationscope with the
 * name "struts.example.states"
 *
 * @author Srikanth Shenoy
 * @version $Revision:   $ $Date:   $
 */
public class ApplicationScopeInit implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        try {
            HibernateUtil.getSessionFactory();
        } catch (Exception e) {
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
        event.getServletContext().removeAttribute("STRUTS_EXAMPLE_STATES");
    }
}
