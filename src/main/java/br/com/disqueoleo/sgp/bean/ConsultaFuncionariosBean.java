package br.com.disqueoleo.sgp.bean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;



import br.com.disqueoleo.sgp.dao.FuncaoDAO;
import br.com.disqueoleo.sgp.dao.FuncionarioDAO;
import br.com.disqueoleo.sgp.dao.TipoSanguineoDAO;
import br.com.disqueoleo.sgp.domain.Funcao;
import br.com.disqueoleo.sgp.domain.Funcionario;
import br.com.disqueoleo.sgp.domain.TipoSanguineo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ConsultaFuncionariosBean implements Serializable {
	private Funcionario funcionario;
	private List<Funcionario> funcionarios;
	private List<Funcao> funcoes;
	private List<TipoSanguineo> tiposSanguineos;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<Funcao> funcoes) {
		this.funcoes = funcoes;
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
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os funcionários");
			erro.printStackTrace();
		}
	}

	public void cadastrar() {
		funcionario = new Funcionario();
	}

	public void salvar() {
		try {
			if (funcionario.getTelFixo() == "" && (funcionario.getCelular1() == "")) {
				Messages.addGlobalError("Você precisa digitar pelo menos um telefone para prosseguir.");
			} else if (funcionario.getTelFixo() != "" && (funcionario.getCelular1() == "")) {

			} else if (funcionario.getCpf() == "") {
				Messages.addGlobalError("O campo CPF não pode ficar em branco");
			} else if (funcionario.getNome() == "") {
				Messages.addGlobalError("O campo NOME não pode ficar em branco");
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				Funcionario funcionarioRetorno = funcionarioDAO.merge(funcionario);

				Path origem = Paths.get(funcionario.getCaminho());
				Path destino = Paths.get("C:/PROJETO CELSO JR/WORKSPACE/SGP/src/main/webapp/resources/images/"
						+ funcionarioRetorno.getCodigo() + ".png");
				Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

				cadastrar();

				FuncaoDAO funcaoDAO = new FuncaoDAO();
				funcoes = funcaoDAO.listar();

				TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
				tiposSanguineos = tipoSanguineoDAO.listar();

				Messages.addGlobalInfo("Funcionario editado com sucesso!!!");
			
			} else if (funcionario.getTelFixo() == "" && (funcionario.getCelular1() != "")) {
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				Funcionario funcionarioRetorno = funcionarioDAO.merge(funcionario);

				Path origem = Paths.get(funcionario.getCaminho());
				Path destino = Paths.get("C:/PROJETO CELSO JR/WORKSPACE/SGP/src/main/webapp/resources/images/"
						+ funcionarioRetorno.getCodigo() + ".png");
				Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

				cadastrar();

				FuncaoDAO funcaoDAO = new FuncaoDAO();
				funcoes = funcaoDAO.listar();

				TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
				tiposSanguineos = tipoSanguineoDAO.listar();

				Messages.addGlobalInfo("Funcionario editado com sucesso!!!");
				
			} else if (funcionario.getTelFixo() != "" && (funcionario.getCelular1() != "")) {
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				Funcionario funcionarioRetorno = funcionarioDAO.merge(funcionario);

				Path origem = Paths.get(funcionario.getCaminho());
				Path destino = Paths.get("C:/PROJETO CELSO JR/WORKSPACE/SGP/src/main/webapp/resources/images/"
						+ funcionarioRetorno.getCodigo() + ".png");
				Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

				cadastrar();

				FuncaoDAO funcaoDAO = new FuncaoDAO();
				funcoes = funcaoDAO.listar();

				TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
				tiposSanguineos = tipoSanguineoDAO.listar();

				Messages.addGlobalInfo("Funcionario editado com sucesso!!!");
				
			} else if (funcionario.getCaminho() == null) {
				Messages.addGlobalError("Você precisa buscar uma foto antes de continuar.");
			} else if (funcionario.getCaminho() != null) {
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				Funcionario funcionarioRetorno = funcionarioDAO.merge(funcionario);

				Path origem = Paths.get(funcionario.getCaminho());
				Path destino = Paths.get("C:/PROJETO CELSO JR/WORKSPACE/SGP/src/main/webapp/resources/images/"
						+ funcionarioRetorno.getCodigo() + ".png");
				Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

				cadastrar();

				FuncaoDAO funcaoDAO = new FuncaoDAO();
				funcoes = funcaoDAO.listar();

				TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
				tiposSanguineos = tipoSanguineoDAO.listar();

				Messages.addGlobalInfo("Funcionario editado com sucesso!!!");
			}
		} catch (RuntimeException | IOException erro) {
			if (funcionario.getCpf() == funcionario.getCpf()) {
				Messages.addGlobalError("O CPF " + funcionario.getCpf() + " já existe");
			}
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");

			FuncionarioDAO FuncionarioDAO = new FuncionarioDAO();
			FuncionarioDAO.excluir(funcionario);

			Path arquivo = Paths.get("C:/PROJETO CELSO JR/FOTOS/" + funcionario.getCodigo() + ".'png");
			Files.deleteIfExists(arquivo);

			Messages.addGlobalInfo("funcionario excluido com sucesso.");
		} catch (RuntimeException | IOException erro) {
			Messages.addGlobalError("Erro ao excluir o funcionario.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");
			funcionario.setCaminho("C:/PROJETO CELSO JR/FOTOS/" + funcionario.getCodigo() + ".png");

			TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
			tiposSanguineos = tipoSanguineoDAO.listar();

			FuncaoDAO funcaoDAO = new FuncaoDAO();
			funcoes = funcaoDAO.listar();

			Messages.addGlobalInfo("funcionario editado com sucesso");
		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}
	}

	/*/-public void upload(FileUploadEvent evento) {
		try {
			UploadedFile arquivoUpload = evento.getFile();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			funcionario.setCaminho(arquivoTemp.toString());
		} catch (IOException erro) {
			Messages.addGlobalInfo("Ocorreu um erro ao tentar realizar o upload do arquivo");
			erro.printStackTrace();
		}
	}*/
}
