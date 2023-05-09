package br.com.advocacia.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil<T> {
	
   private static SessionFactory sessionFactory;

   public static SessionFactory getSessionFactory() throws HibernateException {
	   if (sessionFactory == null) {
           // loads configuration and mappings
           //Configuration configuration = new Configuration().configure();
           //configuration.addAnnotatedClass(Cliente.class);
           /*ServiceRegistry serviceRegistry
               = new StandardServiceRegistryBuilder()
                   .applySettings(configuration.getProperties()).build();*/
		   ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
	                .configure()
	                .build();
            
           // builds a session factory from the service registry
           sessionFactory = new Configuration().buildSessionFactory(serviceRegistry);           
       }
	   
	   return sessionFactory;
   }
   
}
