package br.com.advocacia.mb;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.advocacia.util.Mail;

@ManagedBean(name="homeBean")
@RequestScoped
public class HomeBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean showLabelEmail;
	
	@PostConstruct
    public void init() {
		showLabelEmail = false;
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
			showLabelEmail = true;
		else
			showLabelEmail = false;
	}

	public boolean isShowLabelEmail() {
		return showLabelEmail;
	}

	public void setShowLabelEmail(boolean showLabelEmail) {
		this.showLabelEmail = showLabelEmail;
	}
	
}
