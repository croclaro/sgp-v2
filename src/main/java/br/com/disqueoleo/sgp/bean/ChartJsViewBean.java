package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

import br.com.disqueoleo.sgp.dao.RetiradaDao;
import br.com.disqueoleo.sgp.dto.RetiradaDTO;

@SuppressWarnings("serial")
@ManagedBean
@Named
@ViewScoped
public class ChartJsViewBean implements Serializable {

	private LineChartModel lineModel;
	private LineChartModel grfRetiradaBairro;
	private BarChartModel barModel;
	private BarChartModel barModel2;
	private LineChartModel grfRetiradaOleo;
	private LineChartModel cartesianLinerModel;

	private Integer ano;
	private Integer mes;
	private Integer oleo;
	private Integer residuo;
	
	private List<Integer> anos;

	private RetiradaDao retiradaDao = new RetiradaDao();
	
	@ManagedProperty("#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;

	public LineChartModel getGrfRetiradaOleo() {
		return grfRetiradaOleo;
	}

	public Integer getOleo() {
		return oleo;
	}

	public void setOleo(Integer oleo) {
		this.oleo = oleo;
	}

	public Integer getResiduo() {
		return residuo;
	}

	public void setResiduo(Integer residuo) {
		this.residuo = residuo;
	}

	public void setGrfRetiradaOleo(LineChartModel grfRetiradaOleo) {
		this.grfRetiradaOleo = grfRetiradaOleo;
	}

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}

	public List<Integer> getAnos() {
		return anos;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public LineChartModel getGrfRetiradaBairro() {
		return grfRetiradaBairro;
	}

	public void setGrfRetiradaBairro(LineChartModel grtRetiradaBairro) {
		this.grfRetiradaBairro = grtRetiradaBairro;
	}

	public LineChartModel getCartesianLinerModel() {
		return cartesianLinerModel;
	}

	public void setCartesianLinerModel(LineChartModel cartesianLinerModel) {
		this.cartesianLinerModel = cartesianLinerModel;
	}

	public RetiradaDao getRetiradaDao() {
		return retiradaDao;
	}

	public void setRetiradaDao(RetiradaDao retiradaDao) {
		this.retiradaDao = retiradaDao;
	}

	public void setAnos(List<Integer> anos) {
		this.anos = anos;
	}

	public BarChartModel getBarModel2() {
		return barModel2;
	}

	public void setBarModel2(BarChartModel barModel2) {
		this.barModel2 = barModel2;
	}
	
	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}
	
	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	@PostConstruct
	public void init() {
		criarAnos();
		criarMeses();
		createGrfRetiradaBairro();
		createBarModel();
		createGrfRetiradaOleo();
		createCartesianLinerModel();
		createBarModel2();
		createRetiradaOleoBox();
	}

	private void criarAnos() {
		int anoCorrente = LocalDate.now().getYear();
		ano = anoCorrente;

		anos = new ArrayList<>();

		for (int ano = 2018; ano <= anoCorrente; ano++) {
			anos.add(ano);
		}
	}

	private void criarMeses() {
		mes = null;
	}

	public void createGrfRetiradaOleo() {
		ChartData data = new ChartData();
		List<String> labels = new ArrayList<>();

		//List<RetiradaDTO> retiradas = retiradaDao.buscarPorAnoEMes(ano, mes);
		List<RetiradaDTO> retiradas = retiradaDao.buscarPorAnoEMesEUsuario(ano, mes, autenticacaoBean.getUsuarioLogado());
		
		List<Number> valuesOleo = new ArrayList<>();
		List<Number> valuesResiduos = new ArrayList<>();

		retiradas.forEach(retirada -> {
			valuesOleo.add(retirada.getOleo());
			valuesResiduos.add(retirada.getResiduo());
			labels.add(retirada.getAno() + "/" + retirada.getMes());
		});

		LineChartDataSet dataSetOleo = new LineChartDataSet();
		dataSetOleo.setData(valuesOleo);
		dataSetOleo.setFill(false);
		dataSetOleo.setLabel("Oléo");
		dataSetOleo.setBackgroundColor("rgb(34, 139, 34");
		dataSetOleo.setBorderColor("rgb(34, 139, 34");
		dataSetOleo.setLineTension(0.1);

		LineChartDataSet dataSetResiduo = new LineChartDataSet();
		dataSetResiduo.setData(valuesResiduos);
		dataSetResiduo.setFill(false);
		dataSetResiduo.setLabel("Resíduo");
		dataSetResiduo.setBackgroundColor("rgb(144, 238,144)");
		dataSetResiduo.setBorderColor("rgb(144, 238,144)");
		dataSetResiduo.setLineTension(0.1);

		data.addChartDataSet(dataSetOleo);
		data.addChartDataSet(dataSetResiduo);
		data.setLabels(labels);

		// Options
		LineChartOptions options = new LineChartOptions();
		Title title = new Title();
		title.setDisplay(true);
		title.setText("Retirada de Óleo Mensal");
		options.setTitle(title);

		grfRetiradaOleo = new LineChartModel();
		grfRetiradaOleo.setOptions(options);
		grfRetiradaOleo.setData(data);
	}	

	public void createGrfRetiradaBairro() {
		ChartData data = new ChartData();
		List<String> labels = new ArrayList<>();

		List<RetiradaDTO> retiradas = retiradaDao.buscarPorAnoEMesEUsuario(ano, mes, autenticacaoBean.getUsuarioLogado());
		List<Number> valuesOleo = new ArrayList<>();
		List<Number> valuesResiduos = new ArrayList<>();

		retiradas.forEach(retirada -> {
			valuesOleo.add(retirada.getOleo());
			valuesResiduos.add(retirada.getResiduo());
			labels.add(retirada.getAno() + "/" + retirada.getMes());
		});

		LineChartDataSet dataSetOleo = new LineChartDataSet();
		dataSetOleo.setData(valuesOleo);
		dataSetOleo.setFill(false);
		dataSetOleo.setLabel("Oléo");
		dataSetOleo.setBorderColor("rgb(255, 140, 0)");
		dataSetOleo.setLineTension(0.1);

		LineChartDataSet dataSetResiduo = new LineChartDataSet();
		dataSetResiduo.setData(valuesResiduos);
		dataSetResiduo.setFill(false);
		dataSetResiduo.setLabel("Resíduo");
		dataSetResiduo.setBorderColor("rgb(255, 191, 0)");
		dataSetResiduo.setLineTension(0.1);

		data.addChartDataSet(dataSetOleo);
		data.addChartDataSet(dataSetResiduo);
		data.setLabels(labels);

		// Options
		LineChartOptions options = new LineChartOptions();
		Title title = new Title();
		title.setDisplay(true);
		title.setText("Retirada de Óleo Mensal");
		options.setTitle(title);

		grfRetiradaBairro = new LineChartModel();
		grfRetiradaBairro.setOptions(options);
		grfRetiradaBairro.setData(data);
	}

	public void createBarModel() {
		ChartData data = new ChartData();
		List<String> labels = new ArrayList<>();
		List<RetiradaDTO> retiradas = retiradaDao.buscarPorAnoEMesEUsuario(ano, mes, autenticacaoBean.getUsuarioLogado());
		List<Number> valuesOleo = new ArrayList<>();
		List<Number> valuesResiduos = new ArrayList<>();

		retiradas.forEach(retirada -> {
			valuesOleo.add(retirada.getOleo());
			valuesResiduos.add(retirada.getResiduo());
			labels.add(retirada.getAno() + "/" + retirada.getMes());
		});

		BarChartDataSet dataSetOleo = new BarChartDataSet();
		dataSetOleo.setLabel("ÓLEO USADO");
		dataSetOleo.setData(valuesOleo);
		dataSetOleo.setLabel("Oléo");
		List<String> bgColorOleo = new ArrayList<>();
		bgColorOleo.add("rgba(255, 0, 0, 0.5)");
		bgColorOleo.add("rgba(255, 0, 0, 0.5)");
		bgColorOleo.add("rgba(255, 0, 0, 0.5)");
		bgColorOleo.add("rgba(255, 0, 0, 0.5)");
		bgColorOleo.add("rgba(255, 0, 0, 0.5)");
		bgColorOleo.add("rgba(255, 0, 0, 0.5)");
		bgColorOleo.add("rgba(255, 0, 0, 0.5)");
		bgColorOleo.add("rgba(255, 0, 0, 0.5)");
		bgColorOleo.add("rgba(255, 0, 0, 0.5)");
		bgColorOleo.add("rgba(255, 0, 0, 0.5)");
		bgColorOleo.add("rgba(255, 0, 0, 0.5)");
		bgColorOleo.add("rgba(255, 0, 0, 0.5)");

		dataSetOleo.setBackgroundColor(bgColorOleo);

		BarChartDataSet dataSetResiduo = new BarChartDataSet();
		dataSetResiduo.setLabel("RESÍDUO");
		dataSetResiduo.setData(valuesResiduos);
		dataSetResiduo.setLabel("Resíduo");
		List<String> bgColor = new ArrayList<>();
		bgColor.add("rgba(0, 127, 255, 0.5)");
		bgColor.add("rgba(0, 127, 255, 0.5)");
		bgColor.add("rgba(0, 127, 255, 0.5)");
		bgColor.add("rgba(0, 127, 255, 0.5)");
		bgColor.add("rgba(0, 127, 255, 0.5)");
		bgColor.add("rgba(0, 127, 255, 0.5)");
		bgColor.add("rgba(0, 127, 255, 0.5)");
		bgColor.add("rgba(0, 127, 255, 0.5)");
		bgColor.add("rgba(0, 127, 255, 0.5)");
		bgColor.add("rgba(0, 127, 255, 0.5)");
		bgColor.add("rgba(0, 127, 255, 0.5)");
		bgColor.add("rgba(0, 127, 255, 0.5)");

		dataSetResiduo.setBackgroundColor(bgColor);

		data.addChartDataSet(dataSetOleo);
		data.addChartDataSet(dataSetResiduo);
		data.setLabels(labels);

		// Options
		BarChartOptions options = new BarChartOptions();
		Title title = new Title();
		title.setDisplay(true);
		title.setText("Retirada de Óleo Mensal");
		options.setTitle(title);
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		linearAxes.setOffset(true);
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);

		barModel = new BarChartModel();
		barModel.setOptions(options);
		barModel.setData(data);
	}

	public void createCartesianLinerModel() {
		cartesianLinerModel = new LineChartModel();
		ChartData data = new ChartData();

		LineChartDataSet dataSet = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		values.add(20);
		values.add(50);
		values.add(100);
		values.add(75);
		values.add(25);
		values.add(0);

		dataSet.setLabel("Left Dataset");
		dataSet.setYaxisID("left-y-axis");

		LineChartDataSet dataSet2 = new LineChartDataSet();
		List<Object> values2 = new ArrayList<>();
		values2.add(0.1);
		values2.add(0.5);
		values2.add(1.0);
		values2.add(2.0);
		values2.add(1.5);
		values2.add(0);

		dataSet2.setLabel("Right Dataset");
		dataSet2.setYaxisID("right-y-axis");

		data.addChartDataSet(dataSet);
		data.addChartDataSet(dataSet2);

		List<String> labels = new ArrayList<>();
		labels.add("Jan");
		labels.add("Feb");
		labels.add("Mar");
		labels.add("Apr");
		labels.add("May");
		labels.add("Jun");
		data.setLabels(labels);
		cartesianLinerModel.setData(data);

		// Options
		LineChartOptions options = new LineChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		linearAxes.setId("left-y-axis");
		linearAxes.setPosition("left");
		CartesianLinearAxes linearAxes2 = new CartesianLinearAxes();
		linearAxes2.setId("right-y-axis");
		linearAxes2.setPosition("right");

		cScales.addYAxesData(linearAxes);
		cScales.addYAxesData(linearAxes2);
		options.setScales(cScales);

		Title title = new Title();
		title.setDisplay(true);
		title.setText("Cartesian Linear Chart");
		options.setTitle(title);

		cartesianLinerModel.setOptions(options);
	}

	public void createBarModel2() {
		ChartData data = new ChartData();
		List<String> labels = new ArrayList<>();
		List<RetiradaDTO> retiradas = retiradaDao.buscarPorAnoEMesEUsuario(ano, mes, autenticacaoBean.getUsuarioLogado());
		List<Number> valuesOleo = new ArrayList<>();
		List<Number> valuesResiduos = new ArrayList<>();

		retiradas.forEach(retirada -> {
			valuesOleo.add(retirada.getOleo());
			valuesResiduos.add(retirada.getResiduo());
			labels.add(retirada.getAno() + "/" + retirada.getMes());
		});

		BarChartDataSet dataSetOleo = new BarChartDataSet();
		dataSetOleo.setLabel("ÓLEO USADO");
		dataSetOleo.setData(valuesOleo);
		dataSetOleo.setLabel("Oléo");
		List<String> bgColorOleo = new ArrayList<>();
		bgColorOleo.add("rgba(0, 0, 139, 0.4)");
		bgColorOleo.add("rgba(0, 0, 139, 0.4)");
		bgColorOleo.add("rgba(0, 0, 139, 0.4)");
		bgColorOleo.add("rgba(0, 0, 139, 0.4)");
		bgColorOleo.add("rgba(0, 0, 139, 0.4)");
		bgColorOleo.add("rgba(0, 0, 139, 0.4)");
		bgColorOleo.add("rgba(0, 0, 139, 0.4)");
		bgColorOleo.add("rgba(0, 0, 139, 0.4)");
		bgColorOleo.add("rgba(0, 0, 139, 0.4)");
		bgColorOleo.add("rgba(0, 0, 139, 0.4)");
		bgColorOleo.add("rgba(0, 0, 139, 0.4)");
		bgColorOleo.add("rgba(0, 0, 139, 0.4)");

		dataSetOleo.setBackgroundColor(bgColorOleo);

		BarChartDataSet dataSetResiduo = new BarChartDataSet();
		dataSetResiduo.setLabel("RESÍDUO");
		dataSetResiduo.setData(valuesResiduos);
		dataSetResiduo.setLabel("Resíduo");
		List<String> bgColor = new ArrayList<>();
		bgColor.add("rgba(0,  191, 255, 0.5)");
		bgColor.add("rgba(0,  191, 255, 0.5)");
		bgColor.add("rgba(0,  191, 255, 0.5)");
		bgColor.add("rgba(0,  191, 255, 0.5)");
		bgColor.add("rgba(0,  191, 255, 0.5)");
		bgColor.add("rgba(0,  191, 255, 0.5)");
		bgColor.add("rgba(0,  191, 255, 0.5)");
		bgColor.add("rgba(0,  191, 255, 0.5)");
		bgColor.add("rgba(0,  191, 255, 0.5)");
		bgColor.add("rgba(0,  191, 255, 0.5)");
		bgColor.add("rgba(0,  191, 255, 0.5)");
		bgColor.add("rgba(0,  191, 255, 0.5)");

		dataSetResiduo.setBackgroundColor(bgColor);

		data.addChartDataSet(dataSetOleo);
		data.addChartDataSet(dataSetResiduo);
		data.setLabels(labels);

		// Options
		BarChartOptions options = new BarChartOptions();
		Title title = new Title();
		title.setDisplay(true);
		title.setText("Retirada de Óleo Mensal");
		options.setTitle(title);
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		linearAxes.setOffset(true);
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);

		barModel2 = new BarChartModel();
		barModel2.setOptions(options);
		barModel2.setData(data);
	}
	
	private void createRetiradaOleoBox(){
		
	}

}
