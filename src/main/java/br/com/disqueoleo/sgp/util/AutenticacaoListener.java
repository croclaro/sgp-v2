package br.com.disqueoleo.sgp.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import br.com.disqueoleo.sgp.bean.AutenticacaoBean;
import br.com.disqueoleo.sgp.domain.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		String paginaAtual = Faces.getViewId();

		boolean ehPaginaDeAutenticacao = paginaAtual.contains("bt-login.xhtml");
		
		boolean ehPaginaPublica = 
				paginaAtual.contains("bt-forgot-password.xhtml") || 
				paginaAtual.contains("bt-register.xhtml") ||
				paginaAtual.contains("cad-fornecedorIndicadoUpgrade.xhtml") ||
				paginaAtual.contains("bt-loginCodigo.xhtml");

		if (!ehPaginaPublica) {
			
			if (!ehPaginaDeAutenticacao) {
				AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
	
				if (autenticacaoBean == null) {
					Faces.navigate("bt-login.xhtml");
					return;
				}
	
				Usuario usuario = autenticacaoBean.getUsuarioLogado();
				if (usuario == null) {
					Faces.navigate("bt-login.xhtml");
					return;
				}
			}

		}//fim ehPaginaPublica	
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
