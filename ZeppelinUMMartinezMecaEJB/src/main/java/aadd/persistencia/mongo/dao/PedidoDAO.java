package aadd.persistencia.mongo.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;

import aadd.persistencia.dto.PedidoDTO;
import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.bean.Usuario;
import aadd.persistencia.mongo.bean.EstadoPedido;
import aadd.persistencia.mongo.bean.Pedido;
import aadd.persistencia.mongo.bean.TipoEstado;

@Singleton(name = "PedidoDAO")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class PedidoDAO{
	
	private MongoClient mongoClient;
	private MongoDatabase db;
	protected MongoCollection<Pedido> collection;
	protected CodecRegistry defaultCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
	
	@PostConstruct
	public void init() {
		mongoClient = MongoClients.create("mongodb://localhost:27017");
		db = mongoClient.getDatabase("zeppelinummartinezmeca");
		collection = db.getCollection("pedido", Pedido.class).withCodecRegistry(defaultCodecRegistry);
	}

	@PreDestroy
	public void destroy() {
		mongoClient.close();
	}
		
	private static PedidoDAO pedidoDAO;

    public static PedidoDAO getPedidoDAO() {
        if (pedidoDAO == null) {
            pedidoDAO = new PedidoDAO();
        }
        return pedidoDAO;
    }
    
    public List<PedidoDTO> transformarToDTO(List<Pedido> pedidos) {
        List<PedidoDTO> pedidosDTO = new ArrayList<>();
        for (Pedido p : pedidos) {
            pedidosDTO.add(new PedidoDTO(p.getCodigo(), p.getCliente(), p.getRestaurante(), p.getComentarios(), p.getDatosDireccion(), p.getImporte(), p.getEstados()));
        }
        return pedidosDTO;
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
    
    public List<PedidoDTO> getByUsuarioRestaurante(Integer codUsu, Integer codRes) {
    	Bson query = Filters.and(Filters.eq("cliente", codUsu),Filters.eq("restaurante",codRes));
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
    
    public void canceladoAutomatico(String codigo) {
    	Bson query = Filters.eq("codigo", codigo);
    	FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> ped = new ArrayList<Pedido>();
		while (it.hasNext()) {
			ped.add(it.next());
		}
		
		if(!ped.isEmpty()) {			
			if (ped.size() > 1)
				System.err.println("Error de integridad: hay más de un pedido con el mismo código.");
			
			for(Pedido p : ped) {
				if (!(p.getEstados().size() > 1)) {
					System.out.println("Pedido "+codigo+" cancelado.");
					editarEstadoById(codigo, TipoEstado.CANCELADO, LocalDateTime.now());
				}
			}			
		}
    }
}
