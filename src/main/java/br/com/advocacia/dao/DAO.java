package br.com.advocacia.dao;

import java.util.List;

public interface DAO<T> {
	public void salvar(T t);
	
	public void remover(T t);
	
	public T buscarPorId(Long id);
	
	public List<T> listar();
}
