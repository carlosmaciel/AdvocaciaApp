package br.com.advocacia.mb;

import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.advocacia.dao.impl.ClienteDAO;
import br.com.advocacia.model.Cliente;

@ManagedBean(name="clientesBean")
@RequestScoped
public class ClientesBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Cliente cliente;
	private List<Cliente> clientes = null;
	
	private ClienteDAO dao = new ClienteDAO();
		
	@PostConstruct
    public void init() {
		novo();
    }
    
    public void salvar() {
    	
		try {
			String mensagem = null;
			
			if(cliente.getId() == null) {
				dao.salvar(cliente);
				mensagem = "Cliente salvo com sucesso.";
				novo();
			} else {
				mensagem = "Cliente alterado com sucesso.";
				
				if(isStatusUpdatedToInativo()) {
					//Usuário está tentando inativar um processo
					if(hasProcesso()) {
						//Processo está associado a alguma intimação ou publicação
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Não é permitido inativar um cliente que esteja associado a algum processo ativa."));
						novo();
						return;
					} else {
						dao.salvar(cliente);
						novo();
					}
				} else {
					dao.salvar(cliente);
					novo();
				}
			}
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensagem));			
		} catch (Exception e) {
			Throwable result = e;
			
			//verifica se a exceção é devido a constraint de unicidade unique
			if(result.getCause() instanceof SQLIntegrityConstraintViolationException || (result.getCause() != null && result.getCause().getCause() instanceof SQLIntegrityConstraintViolationException)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Já existe um Cliente com este nome cadastrado."));
				novo();
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			}
		}		
	}
    
    private boolean hasProcesso() {
    	return dao.hasProcesso(cliente);
    }
        
    private boolean isStatusUpdatedToInativo() {
    	Integer INATIVO = 0;    	    	
    	Cliente clienteBanco = dao.buscarPorId(cliente.getId());
    	
    	if(!clienteBanco.getStatus().equals(cliente.getStatus()) && cliente.getStatus().equals(INATIVO))
    		return true;
    	
    	return false;
    }
    
    public void listar() {
    	List<Cliente> clientes = dao.listar();
    	this.clientes = clientes;
	}
    
    public void buscarPorId() {
    	cliente = dao.buscarPorId(cliente.getId());
	}
    
    public void novo() {
    	cliente = new Cliente();
    	cliente.setStatus(1);
    	listar();
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
