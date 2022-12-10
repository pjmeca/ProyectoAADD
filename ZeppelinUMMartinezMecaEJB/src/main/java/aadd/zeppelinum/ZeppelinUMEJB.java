package aadd.zeppelinum;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.bson.Document;
import aadd.persistencia.dto.EstadisticaOpinionDTO;
import aadd.persistencia.dto.EstadisticaPedidosRestauranteDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.persistencia.mongo.dao.OpinionDAO;
import aadd.persistencia.mongo.dao.PedidoDAO;

@Stateless(name="ZeppelinUMRemoto")
public class ZeppelinUMEJB implements ZeppelinUMRemoto{

    @EJB(beanName="OpinionDAO")
    private OpinionDAO opinionDAO;
    
    @EJB(beanName="RestauranteDAO")
    private RestauranteDAO restauranteDAO;
    
    @EJB(beanName="Contador")
    private ContadorVisitasEJB contadorVisitas;
    
    @EJB(beanName="EstadoAutomatico")
    private IEstadoAutomatico estadoAutomatico;

    @Override
    public Integer getNumVisitas(Integer idUsuario) {
        return contadorVisitas.getVisitas(idUsuario);
    }

    @Override
    public void pedidoIniciado(String pedido) {
    	estadoAutomatico.startUpTimer(pedido, 30); //el timer saltará a los 30 minutos, 
                                                  //para hacer pruebas, puedes cambiar
                                                  //esto a un número de minutos más bajo     
    }

    @Override
    public void nuevaVisita(Integer idUsuario) {
    	contadorVisitas.nuevaVisita(idUsuario);
    }
    
    @Override
    public List<EstadisticaOpinionDTO> getEstadisticasOpinion(Integer idUsuario) {  
        List<EstadisticaOpinionDTO> estadisticas = new ArrayList<>();
        
        List<Document> resultados = opinionDAO.calcularEstadisticas(idUsuario);
        for(Document r:resultados) {
            estadisticas.add(new EstadisticaOpinionDTO(r.getDouble("_id"), r.getInteger("total")));
        }       
        return estadisticas;
    }

	@Override
	public Integer getNumRestaurantesCreados(Integer idUsuario) {
		return restauranteDAO.count(idUsuario);
	}

	@Override
	public List<EstadisticaPedidosRestauranteDTO> getNumPedidosRestaurantes(Integer idUsuario) {
		List<EstadisticaPedidosRestauranteDTO> lista = new ArrayList<>();
		
		List<RestauranteDTO> restaurantes = restauranteDAO.findRestaurantesByUsuarioResponsable(idUsuario);
		
		for(RestauranteDTO r : restaurantes) {
			int numPedidos = PedidoDAO.getPedidoDAO().getByRestaurante(r.getId()).size();
			
			EstadisticaPedidosRestauranteDTO e = new EstadisticaPedidosRestauranteDTO(r.getNombre(), numPedidos);
			lista.add(e);
		}
		return lista;
	}
}