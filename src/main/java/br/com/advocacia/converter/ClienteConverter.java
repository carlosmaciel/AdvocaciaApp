package br.com.advocacia.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.advocacia.dao.impl.ClienteDAO;
import br.com.advocacia.model.Cliente;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.isEmpty()) {
			return (Cliente) new ClienteDAO().buscarPorId(Long.parseLong(value));
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object objeto) {
		if(objeto != null) {
			Cliente cliente = (Cliente) objeto;
			return cliente.getId() != null ? cliente.getId().toString() : null;
		}
		
		return null;
	}

}
