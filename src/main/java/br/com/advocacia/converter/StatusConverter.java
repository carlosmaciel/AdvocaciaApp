package br.com.advocacia.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("statusConverter")
public class StatusConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (value == null) {
			return null;
	    }
		
		Integer status = (Integer) value;
		
		return status == 1 ? "ATIVO" : "INATIVO";
	}

}
