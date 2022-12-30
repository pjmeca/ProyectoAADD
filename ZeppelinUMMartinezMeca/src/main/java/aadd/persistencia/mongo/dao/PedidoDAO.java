package aadd.persistencia.mongo.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;

import aadd.persistencia.dto.PedidoDTO;
import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.bean.Usuario;
import aadd.persistencia.mongo.bean.EstadoPedido;
import aadd.persistencia.mongo.bean.ItemPedido;
import aadd.persistencia.mongo.bean.Pedido;
import aadd.persistencia.mongo.bean.TipoEstado;

public class PedidoDAO extends ExtensionMongoDAO<Pedido>{
		
	private static PedidoDAO pedidoDAO;

    public static PedidoDAO getPedidoDAO() {
        if (pedidoDAO == null) {
            pedidoDAO = new PedidoDAO();
        }
        return pedidoDAO;
    }
    
    @Override
    public void createCollection() {
        collection = db.getCollection("pedido", Pedido.class).withCodecRegistry(defaultCodecRegistry);
    }
    
    public List<PedidoDTO> transformarToDTO(List<Pedido> pedidos) {
        List<PedidoDTO> pedidosDTO = new ArrayList<>();
        for (Pedido p : pedidos) {
            pedidosDTO.add(new PedidoDTO(p.getCodigo(), p.getCliente(), p.getRestaurante(), p.getComentarios(), p.getDatosDireccion(), p.getImporte(), p.getEstados(), p.getFechaHora()));
        }
        return pedidosDTO;
    }
    
    public String registrarPedido(String cod, Integer usu, Integer res, LocalDateTime fH, LocalDateTime fE, String coment, String datosDir
    		, Double importe, Integer rep, LocalDateTime fEstado, List<ItemPedido> items) {
    	Pedido p = new Pedido(cod,usu,res,fH,fE,coment,datosDir,importe,rep,fEstado,items);
    	if (save(p) != null)
    		return p.getCodigo();
    	else
    		return null;
    }
    
    public PedidoDTO getByCodigo(String codigo) {
    	Bson query = Filters.eq("codigo", codigo);
    	FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> ped = new ArrayList<Pedido>();
		while (it.hasNext()) {
			ped.add(it.next());
		}
		return transformarToDTO(ped).get(0);
    }
    
    public EstadoPedido editarEstadoById(String cod, TipoEstado estado, LocalDateTime fecha) {
		
    	EstadoPedido nuevoEstado = new EstadoPedido(estado, fecha);
    	
    	Bson query = Filters.eq("codigo", cod);
		Bson update = Updates.addToSet("estados", nuevoEstado);
		if (collection.updateOne(query, update).wasAcknowledged())
			return nuevoEstado;
		else
			return null;
	}
    
    public boolean asignarRepartidorById(String cod, Integer rep) {
		Bson query = Filters.eq("codigo", cod);
		Bson update = Updates.set("repartidor", rep);
		return collection.updateOne(query, update).wasAcknowledged();
	}
    
    public List<PedidoDTO> getByUsuarioYRestaurante(Integer codUsu, Integer codRes) {
    	Bson query = Filters.and(Filters.eq("cliente", codUsu),Filters.eq("restaurante",codRes));
    	FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> ped = new ArrayList<Pedido>();
		while (it.hasNext()) {
			ped.add(it.next());
		}
		return transformarToDTO(ped);
    }
    
    public List<PedidoDTO> getByUsuario(Integer codUsu) {
    	Bson query = Filters.eq("cliente", codUsu);
    	FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> ped = new ArrayList<Pedido>();
		while (it.hasNext()) {
			ped.add(it.next());
		}
		return transformarToDTO(ped);
    }
    
    public List<PedidoDTO> getByRestaurante(Integer codRes) {
    	Bson query = Filters.eq("restaurante", codRes);
    	FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> ped = new ArrayList<Pedido>();
		while (it.hasNext()) {
			ped.add(it.next());
		}
		return transformarToDTO(ped);
    }
}
