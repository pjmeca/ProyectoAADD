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

}
