package aadd.spring.zeppelinum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.mongodb.client.model.geojson.Position;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.jpa.bean.Restaurante;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.persistencia.mongo.bean.Direccion;
import aadd.persistencia.mongo.dao.DireccionDAO;

@Service
public class ServicioGestionSpring {

    public List<RestauranteDTO> buscarRestaurantesLazy(String keyword, boolean verNovedades, boolean ordernarByValoracion,boolean ceroIncidencias, Double latitud, Double longitud, int start, int max) {
        RestauranteDAO restauranteDAO = RestauranteDAO.getRestauranteDAO();
        if(keyword != null && keyword.isBlank()) {
            keyword = null;
        }
        LocalDate fecha = null;
        if(verNovedades) { // filtramos por aquellos dados de alta la última semana
            fecha = LocalDate.now();
            fecha = fecha.minusWeeks(1);
        }
        // si hemos filtrado por algo, buscamos según filtros
        if ((keyword != null && !keyword.isBlank()) || fecha != null || ordernarByValoracion || ceroIncidencias) {
            return restauranteDAO.findRestauranteByFiltrosLazy(keyword, fecha, ordernarByValoracion, ceroIncidencias, start, max);
        }

        // si no hemos filtrado, búscamos ordenados por localización
        else {
            List<Direccion> direcciones = DireccionDAO.getDireccionDAO().findOrdenadoPorCercania(latitud, longitud, max,start);
            List<RestauranteDTO> restaurantes = new ArrayList<>();
            for (Direccion d : direcciones) {
                Restaurante r = restauranteDAO.findById(d.getRestaurante());
                Position coordenadas = d.getCoordenadas().getCoordinates();
                RestauranteDTO restauranteDTO = new RestauranteDTO(r.getId(), r.getNombre(), r.getValoracionGlobal(), coordenadas.getValues().get(0), coordenadas.getValues().get(1), d.getCalle(),d.getCodigoPostal(), d.getCiudad(), d.getNumero(), r.getNumPlatos());
                restaurantes.add(restauranteDTO);
            }
            return restaurantes;
        }

    }

    public int countRestaurantes(String keyword, boolean verNovedades,boolean ceroIncidencias) {
        if(keyword != null && keyword.isBlank()) {
            keyword = null;
        }
        LocalDate fecha = null;
        if(verNovedades) { // filtramos por aquellos dados de alta la última semana
            fecha = LocalDate.now();
            fecha = fecha.minusWeeks(1);
        }
        return RestauranteDAO.getRestauranteDAO().countRestaurantesByFiltros(keyword, fecha, ceroIncidencias).intValue();
    }
}