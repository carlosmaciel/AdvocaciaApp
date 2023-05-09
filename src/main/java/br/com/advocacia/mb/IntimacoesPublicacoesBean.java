package br.com.advocacia.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.com.advocacia.dao.impl.IntimacaoPublicacaoDAO;
import br.com.advocacia.dao.impl.ProcessoDAO;
import br.com.advocacia.model.IntimacaoPublicacao;
import br.com.advocacia.model.Processo;

@ManagedBean(name="intimacoesPublicacoesBean")
@RequestScoped
public class IntimacoesPublicacoesBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private IntimacaoPublicacao intimacaoPublicacao;
	private List<IntimacaoPublicacao> intimacoesPublicacoes = null;
	
	private List<SelectItem> processos = null;
	
	private IntimacaoPublicacaoDAO dao = new IntimacaoPublicacaoDAO();
	private ProcessoDAO daoProcesso = new ProcessoDAO();
		
	@PostConstruct
    public void init() {
		novo();
    }
    
    public void salvar() {
		dao.salvar(intimacaoPublicacao);
		novo();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Intimação ou Publicação salva com sucesso."));
	}
    
    public void alterar() {
		dao.salvar(intimacaoPublicacao);
		novo();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Intimação ou Publicação alterada com sucesso."));
	}
    
    public void listar() {
    	List<IntimacaoPublicacao> intimacoesPublicacoes = dao.listar();
    	this.intimacoesPublicacoes = intimacoesPublicacoes;
	}
    
    public void buscarPorId() {
    	intimacaoPublicacao = dao.buscarPorId(intimacaoPublicacao.getId());
	}
    
    public void novo() {
    	intimacaoPublicacao = new IntimacaoPublicacao();
    	intimacaoPublicacao.setStatus(1);
    	processos = getProcessos();
    	listar();
	}

	public IntimacaoPublicacao getIntimacaoPublicacao() {
		return intimacaoPublicacao;
	}

	public void setIntimacaoPublicacao(IntimacaoPublicacao intimacaoPublicacao) {
		this.intimacaoPublicacao = intimacaoPublicacao;
	}

	public List<IntimacaoPublicacao> getIntimacoesPublicacoes() {
		return intimacoesPublicacoes;
	}

	public void setIntimacoesPublicacoes(List<IntimacaoPublicacao> intimacoesPublicacoes) {
		this.intimacoesPublicacoes = intimacoesPublicacoes;
	}
	
	public List<SelectItem> getProcessos() {		
		processos = new ArrayList<SelectItem>(); 
        List<Processo> lista = daoProcesso.listarAtivos();
 
        for (Processo processo : lista) {
            SelectItem selecao = new SelectItem(processo, processo.getNumero() != null ? processo.getNumero().toString() : processo.getId().toString());
            processos.add(selecao); 
        }
 
        return processos;
    }
    
	/*public void download() throws IOException
	{

	    File file = new File("file.xl");

	    FacesContext facesContext = FacesContext.getCurrentInstance();

	    HttpServletResponse response = 
	            (HttpServletResponse) facesContext.getExternalContext().getResponse();

	    response.reset();
	    response.setHeader("Content-Type", "application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment;filename=file.txt");

	    OutputStream responseOutputStream = response.getOutputStream();

	    InputStream fileInputStream = new FileInputStream(file);

	    byte[] bytesBuffer = new byte[2048];
	    int bytesRead;
	    while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) 
	    {
	        responseOutputStream.write(bytesBuffer, 0, bytesRead);
	    }

	    responseOutputStream.flush();

	    fileInputStream.close();
	    responseOutputStream.close();

	    facesContext.responseComplete();

	}*/
	
	
}
