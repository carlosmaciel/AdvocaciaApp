package br.com.advocacia.dao.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.advocacia.dao.DAO;
import br.com.advocacia.exception.BusinessException;
import br.com.advocacia.model.Cliente;
import br.com.advocacia.model.Processo;
import br.com.advocacia.util.HibernateUtil;

public class ProcessoDAO implements DAO<Processo>{
	
	public ProcessoDAO() {}
	
	@Override
	public void salvar(Processo processo) throws SQLIntegrityConstraintViolationException, HibernateException {		
		Session session = HibernateUtil.getSessionFactory().openSession();; 
		
		try {			
			session.beginTransaction();
			
			if(processo.getId() == null)		
				session.save(processo);
			else
				session.merge(processo);
			
			session.getTransaction().commit();			
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Processo> listar() throws BusinessException {
	   List<Processo> lista = new ArrayList<Processo>();
	   Session session = null;
			  
	   session = HibernateUtil.getSessionFactory().openSession();
	   lista = (List<Processo>) session.createCriteria(Processo.class).list();
	   session.close();
	   
	   return lista;
    }
	
	@SuppressWarnings("unchecked")
	public List<Processo> listarAtivos() throws BusinessException {
	   List<Processo> lista = new ArrayList<Processo>();
	   Session session = null;
			  
	   session = HibernateUtil.getSessionFactory().openSession();
	   Criteria crit = session.createCriteria(Processo.class);
	   crit.add(Restrictions.eq("status",1));
	   lista = (List<Processo>) crit.list();
	   session.close();
	   
	   return lista;
    }
	
	@Override
	public Processo buscarPorId(Long id) throws BusinessException {
	   Processo processo = null;
	   Session session = null;
	   
	   session = HibernateUtil.getSessionFactory().openSession();
	   processo = (Processo) session.get(Processo.class, id);
	   session.close();
	   
	   return processo;
	}
	
	public boolean hasIntimacaoPublicacao(Processo processo) throws BusinessException, HibernateException {
		return new IntimacaoPublicacaoDAO().hasIntimacaoPublicacao(processo);
	}
	
	@SuppressWarnings("unchecked")
	public boolean hasProcesso(Cliente cliente) throws BusinessException, HibernateException {
		   Session session = HibernateUtil.getSessionFactory().openSession();	   
		   Query query = session.createQuery("from Processo WHERE id_cliente = :id AND status = 1");
		   query.setParameter("id", cliente.getId());
		   List<Processo> list = (List<Processo>) query.list();
		   session.close();
		   
		   return list != null && !list.isEmpty();
	    }
}
