package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.TipoSanguineoDAO;
import br.com.disqueoleo.sgp.domain.TipoSanguineo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ConsultaTipoSanguineoBean implements Serializable {
	private TipoSanguineo tipoSanguineo;
	private List<TipoSanguineo> tiposSanguineos;

	public TipoSanguineo getTipoSanguineo() {
		return tipoSanguineo;
	}

	public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}

	public List<TipoSanguineo> getTiposSanguineos() {
		return tiposSanguineos;
	}

	public void setTiposSanguineos(List<TipoSanguineo> tiposSanguineos) {
		this.tiposSanguineos = tiposSanguineos;
	}

	@PostConstruct
	public void listar() {
		try {

			TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
			tiposSanguineos = tipoSanguineoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as funções");
			erro.printStackTrace();
		}
	}

	public void cadastrar() {
		try {
			tipoSanguineo = new TipoSanguineo();

			TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
			tiposSanguineos = tipoSanguineoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao cadastrar a função!!!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
			tipoSanguineoDAO.merge(tipoSanguineo);

			cadastrar();

			Messages.addGlobalInfo("A Função foi editada com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao editar a função!!!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			tipoSanguineo = (TipoSanguineo) evento.getComponent().getAttributes().get("funcaoSelecionada");

			TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
			tiposSanguineos = tipoSanguineoDAO.listar();

			tiposSanguineos = tipoSanguineoDAO.listar();

			Messages.addGlobalInfo("Função excluida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao excluir a função.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			tipoSanguineo = (TipoSanguineo) evento.getComponent().getAttributes().get("tipoSanguineoSelecionado");

			TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
			tiposSanguineos = tipoSanguineoDAO.listar();

			Messages.addGlobalInfo("Tipo editado com sucesso");
		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}
	}
}
