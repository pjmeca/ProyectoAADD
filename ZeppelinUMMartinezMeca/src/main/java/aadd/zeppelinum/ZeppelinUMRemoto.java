package aadd.zeppelinum;

import java.util.List;
import javax.ejb.Remote;
import aadd.persistencia.dto.EstadisticaOpinionDTO;
import aadd.persistencia.dto.EstadisticaPedidosRestauranteDTO;

@Remote
public interface ZeppelinUMRemoto { 
    public Integer getNumVisitas(Integer idUsuario);
    public void pedidoIniciado(String pedido);
    public void nuevaVisita(Integer idUsuario);
    public List<EstadisticaOpinionDTO> getEstadisticasOpinion(Integer idUsuario);
    public Integer getNumRestaurantesCreados(Integer idUsuario);
    public List<EstadisticaPedidosRestauranteDTO> getNumPedidosRestaurantes(Integer idUsuario);
}