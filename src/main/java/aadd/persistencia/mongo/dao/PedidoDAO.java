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



import aadd.persistencia.mongo.bean.EstadoPedido;
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
    
    public boolean editarEstadoById(String cod, TipoEstado estado, LocalDateTime fecha) {
		Bson query = Filters.eq("codigo", cod);
		Bson update = Updates.addToSet("estados", new EstadoPedido(estado, fecha));
		return collection.updateOne(query, update).wasAcknowledged();
	}
    
    public boolean asignarRepartidorById(String cod, Integer rep) {
		Bson query = Filters.eq("codigo", cod);
		Bson update = Updates.set("repartidor", rep);
		return collection.updateOne(query, update).wasAcknowledged();
	}
    
    
    
    public List<Pedido> getByUsuarioRestaurante(Integer codUsu, Integer codRes) {
    	Bson query = Filters.and(Filters.eq("cliente", codUsu),Filters.eq("restaurante",codRes));
    	FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> ped = new ArrayList<Pedido>();
		while (it.hasNext()) {
			ped.add(it.next());
		}
		return ped;
    }
    
    
}
