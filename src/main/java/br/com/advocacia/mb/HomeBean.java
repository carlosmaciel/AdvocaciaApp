package br.com.advocacia.mb;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.advocacia.util.Mail;

@ManagedBean(name="homeBean")
@RequestScoped
public class HomeBean implements Serializable{

	private static final long serialVersionUID = 1L;
		
	@PostConstruct
    public void init() {
		
    }
	
	public void sendEmail() {
		String txtHtml= "<html>"
				+ "<head><meta charset=\"UTF-8\"></head>"
                + "<body>Hello,<br><br>"
                + "Apenas um teste."
                + "</body></html>";
		
		//CHECAR O ARQUIVO MAIL.CFG EM C:/advocaciapp/mail.cfg PARA AS OUTRAS CONFIGURAÇÕES
		Boolean result = Mail.sendEmail(txtHtml);
		
		if(result)
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("E-mail enviado com sucesso!"));
		else
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro ao enviar e-mail."));
	}
	
}
