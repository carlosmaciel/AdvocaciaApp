package br.com.advocacia.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

import br.com.advocacia.dao.DAO;
import br.com.advocacia.exception.BusinessException;
import br.com.advocacia.model.Cliente;
import br.com.advocacia.util.HibernateUtil;

@Named("clienteDAO")
public class ClienteDAO implements DAO<Cliente>{

	@PersistenceContext
    private EntityManager entityManager;
	
	public ClienteDAO() {}
	
	@Override
	public void salvar(Cliente cliente) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		if(cliente.getId() == null){
			session.save(cliente);
		} else {
			session.merge(cliente);
		}
		
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public void remover(Cliente cliente) {
		cliente.setStatus(0);
		
		this.salvar(cliente);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> listar() {
	   List<Cliente> lista = new ArrayList<Cliente>();
	   Session session = null;
			   
	   try {
		   session = HibernateUtil.getSessionFactory().openSession();
		   lista = (List<Cliente>) session.createCriteria(Cliente.class).list();
	   } catch (Exception e) {
		   throw new BusinessException(e.getMessage());
	   } finally {
		   session.close();
	   }
	   
	   return lista;
    }
	
	@Override
	public Cliente buscarPorId(Long id) {
	   Cliente cliente = null;;
	   Session session = null;
			   
	   try {
		   session = HibernateUtil.getSessionFactory().openSession();
		   cliente = (Cliente) session.get(Cliente.class, id);
	   } catch (Exception e) {
		   throw new BusinessException(e.getMessage());
	   } finally {
		   session.close();
	   }
	   
	   return cliente;
	}

}
