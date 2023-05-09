package br.com.advocacia.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.advocacia.dao.impl.ProcessoDAO;
import br.com.advocacia.model.Processo;

@FacesConverter(forClass = Processo.class)
public class ProcessoConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.isEmpty()) {
			return (Processo) new ProcessoDAO().buscarPorId(Long.parseLong(value));
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object objeto) {
		if(objeto != null) {
			Processo processo = (Processo) objeto;
			return processo.getId() != null ? processo.getId().toString() : null;
		}
		
		return null;
	}

}
