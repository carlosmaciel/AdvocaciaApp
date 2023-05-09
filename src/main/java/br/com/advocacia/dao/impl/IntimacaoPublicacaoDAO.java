package br.com.advocacia.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.advocacia.dao.DAO;
import br.com.advocacia.exception.BusinessException;
import br.com.advocacia.model.IntimacaoPublicacao;
import br.com.advocacia.model.Processo;
import br.com.advocacia.util.HibernateUtil;

public class IntimacaoPublicacaoDAO implements DAO<IntimacaoPublicacao>{
	
	public IntimacaoPublicacaoDAO() {}
	
	@Override
	public void salvar(IntimacaoPublicacao intimacaoPublicacao) throws BusinessException {		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		if(intimacaoPublicacao.getId() == null)		
			session.save(intimacaoPublicacao);
		else
			session.merge(intimacaoPublicacao);
		
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<IntimacaoPublicacao> listar() throws BusinessException {
	   List<IntimacaoPublicacao> lista = new ArrayList<IntimacaoPublicacao>();
	   Session session = null;
			  
	   session = HibernateUtil.getSessionFactory().openSession();
	   lista = (List<IntimacaoPublicacao>) session.createCriteria(IntimacaoPublicacao.class).list();
	   session.close();
	   
	   return lista;
    }
	
	@Override
	public IntimacaoPublicacao buscarPorId(Long id) throws BusinessException {
		IntimacaoPublicacao intimacaoPublicacao = null;
	   Session session = null;
	   
	   session = HibernateUtil.getSessionFactory().openSession();
	   intimacaoPublicacao = (IntimacaoPublicacao) session.get(IntimacaoPublicacao.class, id);
	   session.close();
	   
	   return intimacaoPublicacao;
	}
	
	@SuppressWarnings("unchecked")
	public boolean hasIntimacaoPublicacao(Processo processo) throws BusinessException, HibernateException {
	   Session session = HibernateUtil.getSessionFactory().openSession();	   
	   Query query = session.createQuery("from IntimacaoPublicacao WHERE id_processo = :id AND status = 1");
	   query.setParameter("id", processo.getId());
	   List<IntimacaoPublicacao> list = (List<IntimacaoPublicacao>) query.list();
	   session.close();
	   
	   return list != null && !list.isEmpty();
    }
}
