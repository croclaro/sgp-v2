package br.com.disqueoleo.sgp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.disqueoleo.sgp.domain.Fornecedor;
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
}
