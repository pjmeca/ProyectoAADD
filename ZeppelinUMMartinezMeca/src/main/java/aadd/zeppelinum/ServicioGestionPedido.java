package aadd.zeppelinum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import aadd.persistencia.dto.PedidoDTO;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import aadd.persistencia.mongo.bean.ItemPedido;
import org.bson.types.ObjectId;
import aadd.persistencia.mongo.bean.Pedido;
import aadd.persistencia.mongo.bean.TipoEstado;
import aadd.persistencia.dto.OpinionDTO;
import aadd.persistencia.jpa.EntityManagerHelper;
import aadd.persistencia.jpa.bean.Restaurante;
import aadd.persistencia.jpa.bean.Usuario;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.persistencia.jpa.dao.UsuarioDAO;
import aadd.persistencia.mongo.bean.Opinion;
import aadd.persistencia.mongo.dao.OpinionDAO;
import aadd.persistencia.mongo.dao.PedidoDAO;

public class ServicioGestionPedido {
    
    private static ServicioGestionPedido servicio;

    private static ZeppelinUMRemoto zeppelinumRemoto;
    
    private static int nextCodigoPedido = PedidoDAO.getPedidoDAO().getTotalDocumentos();


    public static ServicioGestionPedido getServicioGestionPedido() {
        if (servicio == null) {
            try {
                zeppelinumRemoto = (ZeppelinUMRemoto) InitialContextUtil.getInstance().lookup("ejb:AADD2022/ZeppelinUMMartinezMecaEJB/ZeppelinUMRemoto!aadd.zeppelinum.ZeppelinUMRemoto");
            } catch (NamingException e) {
                e.printStackTrace();
            }
            servicio = new ServicioGestionPedido();
        }
        return servicio;
    }  
    
    public ObjectId registrarPedido(Integer usu, Integer res, LocalDateTime fH, LocalDateTime fE, String coment, String datosDir
    		, Double importe, Integer rep, LocalDateTime fEstado, List<ItemPedido> items) {
    	PedidoDAO ped = PedidoDAO.getPedidoDAO();
    	Pedido p = new Pedido(getNextCodigoPedido(),usu,res,fH,fE,coment,datosDir,importe,rep,fEstado,items);
    	return ped.save(p); 
    	
    }
    
    public static String getNextCodigoPedido() {
    	return String.valueOf(nextCodigoPedido++);
    }
    
    public boolean editarEstado(String cod, TipoEstado estado, LocalDateTime fecha) {
    	return PedidoDAO.getPedidoDAO().editarEstadoById(cod, estado, fecha);
    }
    
    public boolean asignarRepartidor(String cod, Integer rep) {
    	return PedidoDAO.getPedidoDAO().asignarRepartidorById(cod, rep);
    }
    
    public List<PedidoDTO> findByUsuarioRestaurante(Integer codUsu, Integer codRes){
    	List<PedidoDTO> dto = new ArrayList<PedidoDTO>();
    	List<Pedido> peds = PedidoDAO.getPedidoDAO().getByUsuarioRestaurante(codUsu,codRes);
    	
    	for(Pedido p:peds) {
    		PedidoDTO pdto = new PedidoDTO();
    		pdto.setCliente(p.getCliente());
    		pdto.setRestaurante(p.getRestaurante());
    		pdto.setComentarios(p.getComentarios());
    		pdto.setDatosDireccion(p.getDatosDireccion());
    		pdto.setImporte(p.getImporte());
    		dto.add(pdto);
    	}
    	return dto;
    }
    
    public boolean opinar(Integer usuario, Integer restaurante, String comentario, Double valoracion) {
        OpinionDAO opinionDAO = OpinionDAO.getOpinionDAO();

        Opinion o = new Opinion();
        o.setOpinion(comentario);
        o.setRestaurante(restaurante);
        o.setUsuario(usuario);
        o.setValor(valoracion);

        ObjectId id = opinionDAO.save(o);
        if (id != null) {
            // si se ha creado tengo que modificar la nota del restaurante
            EntityManager em = EntityManagerHelper.getEntityManager();
            try {
                em.getTransaction().begin();
                Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
                Integer numeroValoraciones = r.getNumValoraciones();                
                Integer nuevoNumValoraciones = numeroValoraciones + 1;
                r.setNumValoraciones(nuevoNumValoraciones);
                if(r.getNumValoraciones() == 0) {                   
                    r.setValoracionGlobal(valoracion);
                }
                else {
                    Double nota = r.getValoracionGlobal();
                    double nuevaGlobal = ((nota * numeroValoraciones) + valoracion) / nuevoNumValoraciones;
                    r.setValoracionGlobal(nuevaGlobal);
                }               
                em.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
            return true;
        } else
            return false;
    }

    public List<OpinionDTO> findByUsuario(Integer usuario) {
        OpinionDAO opinionDAO = OpinionDAO.getOpinionDAO();
        List<Opinion> opiniones = opinionDAO.findByUsuario(usuario);
        List<OpinionDTO> opinionesDTO = new ArrayList<>();

        for (Opinion o : opiniones) {
            Restaurante r = RestauranteDAO.getRestauranteDAO().findById(o.getRestaurante());
            OpinionDTO opinionDTO = new OpinionDTO();
            opinionDTO.setNombreRestaurante(r.getNombre());
            opinionDTO.setValoracion(o.getValor());
            opinionDTO.setComentario(o.getOpinion());
            opinionesDTO.add(opinionDTO);
        }
        return opinionesDTO;
    }

    public List<OpinionDTO> findByRestaurante(Integer restaurante) {
        OpinionDAO opinionDAO = OpinionDAO.getOpinionDAO();
        List<Opinion> opiniones = opinionDAO.findByRestaurante(restaurante);
        List<OpinionDTO> opinionesDTO = new ArrayList<>();

        for (Opinion o : opiniones) {
            Usuario u = UsuarioDAO.getUsuarioDAO().findById(o.getUsuario());
            OpinionDTO opinionDTO = new OpinionDTO();
            opinionDTO.setNombreUsuario(u.getNombre());
            opinionDTO.setValoracion(o.getValor());
            opinionDTO.setComentario(o.getOpinion());
            opinionesDTO.add(opinionDTO);
        }
        return opinionesDTO;
    }
    
    public boolean crearPedido(Integer usu, Integer res, LocalDateTime fH, LocalDateTime fE, String coment, String datosDir
    		, Double importe, List<ItemPedido> items) {
        //se crea un pedido, este método deberá tener los atributos necesarios
    	
    	ObjectId id = registrarPedido(usu, res, fH, fE, coment, datosDir, importe, 0, fH, items);
    	
        //una vez creado, nos quedamos con el id que le ha generado mongodb y con eso activamos el timer
        //zeppelinumRemoto.pedidoIniciado(id.toString());
        
        return id != null;
    }
}
