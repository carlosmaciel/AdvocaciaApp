package br.com.advocacia.dao;

import java.util.List;

import br.com.advocacia.exception.BusinessException;

public interface DAO<T> {
	public void salvar(T t) throws Exception;
	
	public T buscarPorId(Long id) throws BusinessException;
	
	public List<T> listar() throws BusinessException;
}
