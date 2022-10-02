package aadd.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.zeppelinum.ServicioGestionPlataforma;

class Test {

	@org.junit.jupiter.api.Test
	void crearUsuario() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario = servicio.registrarUsuario("Periquita", "Palotes", fechaNacimiento, "periquita@palotes.es",
				"12345", TipoUsuario.RESTAURANTE);
		assertTrue(usuario != null);
	}

	@org.junit.jupiter.api.Test
	void validarUsuario() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		boolean exito = servicio.validarUsuario(1);
		assertTrue(exito);
	}

	@org.junit.jupiter.api.Test
	void crearRestaurantePlato() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();

		Integer rest = servicio.registrarRestaurante("La periquita", 1);
		assertTrue(rest != null);
		boolean exito = servicio.nuevoPlato("Marmitako de bonito", "plato de bonito, patatas y cebolla con verduras",
				20d, rest);
		assertTrue(exito);

	}
	
	@org.junit.jupiter.api.Test
	public void loginTest() {
	    ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	    assertTrue(servicio.login("periquita@palotes.es", "12345")!=null);
	    assertFalse(servicio.login("mdclg3@um.es", "loquesea")!=null);
	    assertFalse(servicio.login("periquita@palotes.es", "123456")!=null);
	}

	@org.junit.jupiter.api.Test
	public void checkUsuarioTest() {
	    ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	    assertTrue(servicio.isUsuarioRegistrado("periquita@palotes.es"));
	    assertFalse(servicio.isUsuarioRegistrado("mdclg3@um.es"));
	}
	
	@org.junit.jupiter.api.Test
	void crearPlato() {
	    ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	    boolean exito = servicio.nuevoPlato("Plato no disponible", "plato que voy a cambiar a no disponible", 20d, 1);
	    assertTrue(exito);  
	}

	@org.junit.jupiter.api.Test
	public void platosByRestaurante() {
	    ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	    assertTrue(servicio.getMenuByRestaurante(1).size()==1);
	}
	
	@org.junit.jupiter.api.Test
	public void buscarRestaurantes() {
	        ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
	        assertTrue(servicio.getRestaurantesByFiltros("periqui",true,true,false).size()==1);
	        assertTrue(servicio.getRestaurantesByFiltros("venta",true,true,true).size()==0);
	}

}
