package br.com.advocacia.mb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.advocacia.dao.impl.ClienteDAO;
import br.com.advocacia.model.Cliente;

@Named
@SessionScoped
public class ClientesBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Cliente cliente;
	private List<Cliente> clientes = null;
	
	@Inject
	private ClienteDAO dao;
		
	@PostConstruct
    public void init() {
		cliente = new Cliente();
        listar();
    }
    
    public void salvar() {
		dao.salvar(cliente);
		listar();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente salvo com sucesso."));
	}
    
    public void alterar() {
		dao.salvar(cliente);
		listar();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente alterado com sucesso."));
	}
    
    public void remover() {
    	cliente.setStatus(0);
		dao.remover(cliente);
		listar();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente removido com sucesso."));
	}
    
    public void listar() {
    	setClientes(dao.listar());
	}
    
    public void buscarPorId() {
    	cliente = dao.buscarPorId(cliente.getId());
	}
    
    public void novo() {
    	cliente = new Cliente();
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
