package aadd.web.estadistica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import aadd.persistencia.dto.EstadisticaOpinionDTO;
import aadd.persistencia.dto.EstadisticaPedidosRestauranteDTO;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class EstadisticasWeb implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	@Inject
    protected UserSessionWeb usuarioSesion;
    protected ServicioGestionPlataforma servicioPlataforma;
    private LineChartModel lineModel;
    private BarChartModel barModel;

    public EstadisticasWeb() {      
        servicioPlataforma = ServicioGestionPlataforma.getServicioGestionPlataforma();  }

    @PostConstruct
    public void init() {    	
        createLineModel();
        createBarModel();
        
        servicioPlataforma.nuevaVisitaEstadisticas(usuarioSesion.getUsuario().getId());
    }

    private void createLineModel() {
    	if(!usuarioSesion.isCliente())
    		return;
    	
        List<EstadisticaOpinionDTO> estadisticas = servicioPlataforma.getEstadisticasOpinion(usuarioSesion.getUsuario().getId());
        lineModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            values.add(0);
        }
        for (EstadisticaOpinionDTO e : estadisticas) {
            values.set(e.getNota().intValue(), e.getTotal());
        }

        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Valoraciones a restaurantes");
        dataSet.setBorderColor("rgb(75, 192, 192)");

        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();
        labels.add("0");
        labels.add("1");
        labels.add("2");
        labels.add("3");
        labels.add("4");
        labels.add("5");
        labels.add("6");
        labels.add("7");
        labels.add("8");
        labels.add("9");
        labels.add("10");
        data.setLabels(labels);

        // Options
        LineChartOptions options = new LineChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Line Chart");
        options.setTitle(title);

        lineModel.setOptions(options);
        lineModel.setData(data);
    }
    public Integer getNumVisitas() {
        return servicioPlataforma.getNumVisitas(usuarioSesion.getUsuario().getId());
    }
    public LineChartModel getLineModel() {
        return lineModel;
    }
    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }
    public Integer getNumRestaurantesCreados() {
        return servicioPlataforma.getNumRestaurantesCreados(usuarioSesion.getUsuario().getId());
    }
    
    public void createBarModel() {
    	if(!usuarioSesion.isRestaurante())
    		return;
    	
    	List<EstadisticaPedidosRestauranteDTO> estadisticas = servicioPlataforma.getNumPedidosRestaurantes(usuarioSesion.getUsuario().getId());
    	
        barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("");

        List<Number> values = new ArrayList<>();
        for(EstadisticaPedidosRestauranteDTO e : estadisticas) {
        	values.add(e.getNumPedidos());
        }
        barDataSet.setData(values);
        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        for(EstadisticaPedidosRestauranteDTO e : estadisticas) {
        	labels.add(e.getNombre());
        }
        data.setLabels(labels);
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Número de pedidos en tus restaurantes");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(false);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("italic");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        barModel.setOptions(options);
    }
	public BarChartModel getBarModel() {
		return barModel;
	}
	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}
}