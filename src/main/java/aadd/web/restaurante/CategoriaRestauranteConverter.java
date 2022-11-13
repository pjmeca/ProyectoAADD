package aadd.web.restaurante;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import aadd.persistencia.jpa.bean.CategoriaRestaurante;
import aadd.zeppelinum.ServicioGestionPlataforma;

@FacesConverter(value="categoriaConverter")
public class CategoriaRestauranteConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		CategoriaRestaurante c = ServicioGestionPlataforma.getServicioGestionPlataforma().getCategoriaById(Integer.parseInt(value));
		return c;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return ((CategoriaRestaurante) value).getId().toString();
	}
	

}
