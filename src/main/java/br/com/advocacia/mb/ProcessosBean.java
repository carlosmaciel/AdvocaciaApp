package br.com.advocacia.mb;

import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.com.advocacia.dao.impl.ClienteDAO;
import br.com.advocacia.dao.impl.ProcessoDAO;
import br.com.advocacia.model.Cliente;
import br.com.advocacia.model.Processo;

@ManagedBean(name="processosBean")
@RequestScoped
public class ProcessosBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Processo processo;
	private List<Processo> processos = null;
	
	private List<SelectItem> clientes = null;
	
	private ProcessoDAO dao = new ProcessoDAO();
	private ClienteDAO daoCliente = new ClienteDAO();
		
	@PostConstruct
    public void init() {
		novo();
    }
    
    public void salvar() {		
		try {
			String mensagem = null;
			
			if(processo.getId() == null) {
				dao.salvar(processo);
				mensagem = "Processo salvo com sucesso.";
				novo();
			} else {
				mensagem = "Processo alterado com sucesso.";
				
				if(isStatusUpdatedToInativo()) {
					//Usuário está tentando inativar um processo
					if(hasIntimacaoPublicacao()) {
						//Processo está associado a alguma intimação ou publicação
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Não é permitido inativar um processo que esteja associado a alguma Intimação ou Publicação ativa."));
						novo();
						return;
					} else {
						dao.salvar(processo);
						novo();
					}
				} else {
					dao.salvar(processo);
					novo();
				}
			}
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensagem));
		} catch (Exception e) {
			Throwable result = e;
			
			if(result.getCause() instanceof SQLIntegrityConstraintViolationException || (result.getCause() != null && result.getCause().getCause() instanceof SQLIntegrityConstraintViolationException)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Já existe um Processo com este número cadastrado."));
				novo();
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			}
		}
	}
    
    private boolean hasIntimacaoPublicacao() {
    	return dao.hasIntimacaoPublicacao(processo);
    }
        
    private boolean isStatusUpdatedToInativo() {
    	Integer INATIVO = 0;    	    	
    	Processo processoBanco = dao.buscarPorId(processo.getId());
    	
    	if(!processoBanco.getStatus().equals(processo.getStatus()) && processo.getStatus().equals(INATIVO))
    		return true;
    	
    	return false;
    }
    
    public void listar() {
    	List<Processo> processos = dao.listar();
    	this.processos = processos;
	}
    
    public void buscarPorId() {
    	processo = dao.buscarPorId(processo.getId());
	}
    
    public void novo() {
    	processo = new Processo();
    	processo.setStatus(1);
    	clientes = getClientes();
    	listar();
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public List<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}
	
	public List<SelectItem> getClientes() {		
		clientes = new ArrayList<SelectItem>(); 
        List<Cliente> lista = daoCliente.listarAtivos();
 
        for (Cliente cliente : lista) {
            SelectItem selecao = new SelectItem(cliente, cliente.getNome());
            clientes.add(selecao); 
        }
 
        return clientes;
    }
}
