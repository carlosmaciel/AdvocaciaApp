package br.com.advocacia.dao.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.advocacia.dao.DAO;
import br.com.advocacia.exception.BusinessException;
import br.com.advocacia.model.Cliente;
import br.com.advocacia.util.HibernateUtil;

public class ClienteDAO implements DAO<Cliente>{
	
	public ClienteDAO() {}
	
	@Override
	public void salvar(Cliente cliente) throws SQLIntegrityConstraintViolationException, HibernateException {	
		Session session = HibernateUtil.getSessionFactory().openSession();; 
		
		try {			
			session.beginTransaction();
			
			if(cliente.getId() == null)		
				session.save(cliente);
			else
				session.merge(cliente);
			
			session.getTransaction().commit();			
		} finally {
			session.close();
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> listar() throws BusinessException {
	   List<Cliente> lista = new ArrayList<Cliente>();
	   Session session = null;
			  
	   session = HibernateUtil.getSessionFactory().openSession();
	   lista = (List<Cliente>) session.createCriteria(Cliente.class).list();
	   session.close();
	   
	   return lista;
    }
	
	@SuppressWarnings("unchecked")
	public List<Cliente> listarAtivos() throws BusinessException {
	   List<Cliente> lista = new ArrayList<Cliente>();
	   Session session = null;
			  
	   session = HibernateUtil.getSessionFactory().openSession();
	   Criteria crit = session.createCriteria(Cliente.class);
	   crit.add(Restrictions.eq("status",1));
	   lista = (List<Cliente>) crit.list();
	   session.close();
	   
	   return lista;
    }
	
	@Override
	public Cliente buscarPorId(Long id) throws BusinessException {
	   Cliente cliente = null;
	   Session session = null;
	   
	   session = HibernateUtil.getSessionFactory().openSession();
	   cliente = (Cliente) session.get(Cliente.class, id);
	   session.close();
	   
	   return cliente;
	}
	
	public boolean hasProcesso(Cliente cliente) throws BusinessException, HibernateException {
		return new ProcessoDAO().hasProcesso(cliente);
	}

}
