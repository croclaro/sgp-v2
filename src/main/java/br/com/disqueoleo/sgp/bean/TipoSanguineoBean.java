package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.TipoSanguineoDAO;
import br.com.disqueoleo.sgp.domain.TipoSanguineo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TipoSanguineoBean implements Serializable {
	private TipoSanguineo tipoSanguineo;

	public TipoSanguineo getTipoSanguineo() {
		return tipoSanguineo;
	}

	public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}

	@PostConstruct
	public void cadastrar() {
		tipoSanguineo = new TipoSanguineo();
	}

	public void salvar() {
		try {

			TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
			tipoSanguineoDAO.merge(tipoSanguineo);

			cadastrar();

			Messages.addGlobalInfo("Tipo Sanguíneo salvo com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Tipo sanguíneo já existe.");
			erro.printStackTrace();
		}
	}
}