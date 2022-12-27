package aadd.zeppelinum;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import aadd.persistencia.dto.EstadisticaOpinionDTO;
import aadd.persistencia.dto.EstadisticaPedidosRestauranteDTO;
import aadd.persistencia.dto.PlatoDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.EntityManagerHelper;
import aadd.persistencia.jpa.bean.CategoriaRestaurante;
import aadd.persistencia.jpa.bean.Incidencia;
import aadd.persistencia.jpa.bean.Plato;
import aadd.persistencia.jpa.bean.Restaurante;
import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.persistencia.jpa.bean.Usuario;
import aadd.persistencia.jpa.dao.CategoriaRestauranteDAO;
import aadd.persistencia.jpa.dao.IncidenciaDAO;
import aadd.persistencia.jpa.dao.PlatoDAO;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.persistencia.jpa.dao.UsuarioDAO;
import aadd.persistencia.mongo.bean.Direccion;
import aadd.persistencia.mongo.dao.DireccionDAO;

public class ServicioGestionPlataforma {

	private static ServicioGestionPlataforma servicio;
	private static ZeppelinUMRemoto zeppelinumRemoto;

	public static ServicioGestionPlataforma getServicioGestionPlataforma() {
		
		if (servicio == null) {
            try {
                zeppelinumRemoto = (ZeppelinUMRemoto) InitialContextUtil.getInstance().lookup("ejb:AADD2022/ZeppelinUMMartinezMecaEJB/ZeppelinUMRemoto!aadd.zeppelinum.ZeppelinUMRemoto");
            } catch (NamingException e) {
                e.printStackTrace();
            }
            servicio = new ServicioGestionPlataforma();
        }
        return servicio;
	}

	public Integer registrarUsuario(String nombre, String apellidos, LocalDate fechaNacimiento, String email,
			String clave, TipoUsuario tipo) {

		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Usuario usu = new Usuario();
			usu.setNombre(nombre);
			usu.setApellidos(apellidos);
			usu.setFechaNacimiento(fechaNacimiento);
			usu.setEmail(email);
			usu.setClave(clave);
			usu.setTipo(tipo);

			if (tipo.name().equals("RESTAURANTE"))
				usu.setValidado(false);
			else
				usu.setValidado(true);

			UsuarioDAO.getUsuarioDAO().save(usu, em);

			em.getTransaction().commit();
			return usu.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public boolean validarUsuario(Integer usuario) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Usuario usu = UsuarioDAO.getUsuarioDAO().findById(usuario);
			usu.setValidado(true);

			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public Integer registrarRestaurante(String nombre, Integer responsable, String calle, String codigoPostal,
			Integer numero, String ciudad, Double latitud, Double longitud, List<Integer> categorias) {

		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Restaurante r = new Restaurante();
			r.setResponsable(UsuarioDAO.getUsuarioDAO().findById(responsable));
			r.setNombre(nombre);
			r.setFechaAlta(LocalDate.now());
			r.setValoracionGlobal(0d);
			r.setNumPenalizaciones(0);
			r.setNumValoraciones(0);

			List<CategoriaRestaurante> lista = new LinkedList<CategoriaRestaurante>();
			if (categorias != null) {
				for (Integer i : categorias) {
					CategoriaRestaurante cr = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(i);
					if (cr != null) {
						lista.add(cr);
					}
				}
			}
			r.setCategorias(lista);

			RestauranteDAO.getRestauranteDAO().save(r, em);

			em.flush(); // forzamos el insert para obtener el id de MySQL
			Direccion d = new Direccion();
			d.setCalle(calle);
			d.setCiudad(ciudad);
			d.setCodigoPostal(codigoPostal);
			d.setCoordenadas(new Point(new Position(longitud, latitud)));
			d.setNumero(numero);
			d.setRestaurante(r.getId());

			DireccionDAO.getDireccionDAO().save(d);

			em.getTransaction().commit();
			return r.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public boolean addCategoriaARestaurante(Integer categoria, Integer restaurante) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
			CategoriaRestaurante cr = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(categoria);

			r.getCategorias().add(cr);
			cr.getRestaurantes().add(r);

			RestauranteDAO.getRestauranteDAO().save(r, em);
			CategoriaRestauranteDAO.getCategoriaRestauranteDAO().save(cr, em);

			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public boolean nuevoPlato(String titulo, String descripcion, double precio, Integer restaurante) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
			Plato p = new Plato();
			p.setDescripcion(descripcion);
			p.setTitulo(titulo);
			p.setPrecio(precio);
			p.setRestaurante(r);
			p.setDisponibilidad(true);

			PlatoDAO.getPlatoDAO().save(p, em);

			em.getTransaction().commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public boolean nuevaCategoria(String categoria) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			CategoriaRestaurante cr = new CategoriaRestaurante();
			cr.setCategoria(categoria);
			CategoriaRestauranteDAO.getCategoriaRestauranteDAO().save(cr, em);

			em.getTransaction().commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}
	
	public CategoriaRestaurante getCategoriaById(Integer id) {
		return (CategoriaRestaurante) CategoriaRestauranteDAO.getCategoriaRestauranteDAO().getById(id);
	}
	
	public List<CategoriaRestaurante> getAllCategorias() {
		return CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findAll();
	}

	public boolean isUsuarioRegistrado(String email) {
		List<UsuarioDTO> u = UsuarioDAO.getUsuarioDAO().findByEmail(email);
		if (u != null && !u.isEmpty()) {
			return true;
		}
		return false;
	}

	public UsuarioDTO login(String email, String clave) {
		List<UsuarioDTO> usuarios = UsuarioDAO.getUsuarioDAO().findByEmailClave(email, clave);
		if (usuarios.isEmpty()) {
			System.out.println("Usuario no encontrado, email o clave incorrectos");
			return null;
		} else {
			System.out.println("Usuario logueado " + usuarios.get(0).getNombre());
			return usuarios.get(0);
		}
	}

	public List<PlatoDTO> getMenuByRestaurante(Integer restaurante, boolean findByDisponibles) {
		if(findByDisponibles)
			return PlatoDAO.getPlatoDAO().findPlatosDisponiblesByRestaurante(restaurante);
		return PlatoDAO.getPlatoDAO().findPlatosByRestaurante(restaurante);
	}

	public List<RestauranteDTO> getRestaurantesByFiltros(String keyword, boolean verNovedades,
			boolean ordernarByValoracion, boolean ceroIncidencias) {
		if (keyword != null && keyword.isBlank()) {
			keyword = null;
		}
		LocalDate fecha = null;
		if (verNovedades) { // filtramos por aquellos dados de alta la última semana
			fecha = LocalDate.now();
			fecha = fecha.minusWeeks(1);
		}
		return RestauranteDAO.getRestauranteDAO().findRestauranteByFiltros(keyword, fecha, ordernarByValoracion,
				ceroIncidencias);
	}

	public boolean setDisponbilidadPlato(Integer plato, boolean disponibilidad) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Plato p = PlatoDAO.getPlatoDAO().findById(plato);
			p.setDisponibilidad(disponibilidad);

			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public List<RestauranteDTO> getRestaurantesByCercanía(Double latitud, Double longitud, int limite, int skip) {
		List<Direccion> direcciones = DireccionDAO.getDireccionDAO().findOrdenadoPorCercania(latitud, longitud, limite,
				skip);

		RestauranteDAO restauranteDAO = RestauranteDAO.getRestauranteDAO();
		List<RestauranteDTO> restaurantes = new ArrayList<>();
		for (Direccion d : direcciones) {
			Restaurante r = restauranteDAO.findById(d.getRestaurante());
			Position coordenadas = d.getCoordenadas().getCoordinates();

			RestauranteDTO restauranteDTO = new RestauranteDTO(r.getId(), r.getNombre(), r.getValoracionGlobal(),
					coordenadas.getValues().get(0), coordenadas.getValues().get(1), d.getCalle(), d.getCodigoPostal(),
					d.getCiudad(), d.getNumero(), r.getNumPlatos());
			restaurantes.add(restauranteDTO);
		}
		return restaurantes;
	}

	public RestauranteDTO getDatosRestaurante(RestauranteDTO restaurante) {
		Direccion d = DireccionDAO.getDireccionDAO().findByRestaurante(restaurante.getId());
		Position coordenadas = d.getCoordenadas().getCoordinates();
		restaurante.setLongitud(coordenadas.getValues().get(0));
		restaurante.setLatitud(coordenadas.getValues().get(1));
		restaurante.setCalle(d.getCalle());
		restaurante.setCiudad(d.getCiudad());
		restaurante.setCodigoPostal(d.getCodigoPostal());
		restaurante.setNumero(d.getNumero());
		return restaurante;
	}

	public RestauranteDTO getRestaurante(Integer idRestaurante) {
		Restaurante restaurante = RestauranteDAO.getRestauranteDAO().findById(idRestaurante);
		return new RestauranteDTO(idRestaurante, restaurante.getNombre(), restaurante.getValoracionGlobal(), restaurante.getNumPlatos());
	}
	
	public List<RestauranteDTO> getAllRestaurantes() {
		return RestauranteDAO.getRestauranteDAO().findAllRestaurantes();
	}
	
	public List<RestauranteDTO> getRestaurantesDisponibles() {
		return RestauranteDAO.getRestauranteDAO().findRestaurantesDisponibles();
	}
	
	public List<Integer> getIdUsuariosByTipo(List<TipoUsuario> tipos){
	    return UsuarioDAO.getUsuarioDAO().findIdsByTipo(tipos);
	}
	
	public List<EstadisticaOpinionDTO> getEstadisticasOpinion(Integer idUsuario) {
	    return zeppelinumRemoto.getEstadisticasOpinion(idUsuario);
	}

	public Integer getNumVisitas(Integer idUsuario) {
	    return zeppelinumRemoto.getNumVisitas(idUsuario);
	}
	
	public Integer getNumRestaurantesCreados(Integer idUsuario) {
		return zeppelinumRemoto.getNumRestaurantesCreados(idUsuario);
	}
	
	public List<EstadisticaPedidosRestauranteDTO> getNumPedidosRestaurantes(Integer idUsuario) {
		return zeppelinumRemoto.getNumPedidosRestaurantes(idUsuario);
	}
	
	public void nuevaVisitaEstadisticas(Integer idUsuario) {
		zeppelinumRemoto.nuevaVisita(idUsuario);
	}
	
	public List<CategoriaRestaurante> getNoCategorias(Integer idRestaurante) {
		List<CategoriaRestaurante> tiene = RestauranteDAO.getRestauranteDAO().getCategorias(idRestaurante);
		List<CategoriaRestaurante> todas = getAllCategorias();
		
		todas.removeAll(tiene);
		return todas;		
	}
	
	public void addCategorias(Integer idRestaurante, List<CategoriaRestaurante> categorias) {
		RestauranteDAO.getRestauranteDAO().addCategorias(
				RestauranteDAO.getRestauranteDAO().findById(idRestaurante), 
				categorias);
	}
	
	public Integer registrarIncidencia(Usuario usuario, Restaurante restaurante, String descripcion) {

		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Incidencia i = new Incidencia();
			i.setFechaCreacion(Date.valueOf(LocalDate.now()));
			i.setUsuario(usuario);
			i.setRestaurante(restaurante);
			i.setDescripcion(descripcion);
			
			IncidenciaDAO.getIncidenciaDAO().save(i, em);
			
			em.getTransaction().commit();
			return i.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}
	
	public boolean cerrarIncidencia(Integer idIncidencia, String comentario) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Incidencia i = IncidenciaDAO.getIncidenciaDAO().findById(idIncidencia);
			i.setFechaCierre(Date.valueOf(LocalDate.now()));
			i.setComentarioCierre(comentario);

			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}
}