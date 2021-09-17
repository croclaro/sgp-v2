package br.com.disqueoleo.sgp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.disqueoleo.sgp.domain.Fornecedor;
import br.com.disqueoleo.sgp.domain.Usuario;
import br.com.disqueoleo.sgp.enums.TipoUsuario;
import br.com.disqueoleo.sgp.util.HibernateUtil;

public class FornecedorDAO extends GenericoDAO<Fornecedor> {
	public Fornecedor buscarPorCPFOuCNPJOuEmail(String cpfOuCNPJOuEmail) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Fornecedor.class);
			consulta.add(
				Restrictions.or(
					Restrictions.eq("cpf", cpfOuCNPJOuEmail),
					Restrictions.eq("cnpj", cpfOuCNPJOuEmail),
					Restrictions.eq("email", cpfOuCNPJOuEmail)
				)
			);
			Fornecedor resultado = (Fornecedor) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Fornecedor> buscarPorAfiliado(Long afiliadoCodigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Fornecedor.class);
			consulta.createAlias("afiliado", "a");
			consulta.add(Restrictions.eq("a.codigo", afiliadoCodigo));
			consulta.addOrder(Order.asc("razaoSocial"));
			List<Fornecedor> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	public Long buscarFornecedores(Usuario usuario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Fornecedor.class);

			if (usuario.getTipoUsuario() == TipoUsuario.FORNECEDOR) {
				consulta.createAlias("fornecedor", "f");
				consulta.add(Restrictions.eq("f.codigo", usuario.getFornecedor().getCodigo()));
			} else if (usuario.getTipoUsuario() == TipoUsuario.FUNCIONARIO) {
				consulta.createAlias("funcionario", "f");
				consulta.add(Restrictions.eq("f.codigo", usuario.getFuncionario().getCodigo()));
			}

			consulta.setProjection(Projections.count("codBarras"));

			Long resultado = (Long) consulta.uniqueResult();

			return resultado == null ? 0 : resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
