package br.com.disqueoleo.sgp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.disqueoleo.sgp.domain.Fornecedor;
import br.com.disqueoleo.sgp.domain.Retirada;
import br.com.disqueoleo.sgp.util.HibernateUtil;

public class FornecedorDAO extends GenericoDAO<Fornecedor> {
	public Fornecedor buscarPorCPFOuCNPJ(String cpfOuCNPJ) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Fornecedor.class);
			consulta.add(Restrictions.or(Restrictions.eq("cpf", cpfOuCNPJ), Restrictions.eq("cnpj", cpfOuCNPJ)));
			Fornecedor resultado = (Fornecedor) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Fornecedor> buscarPorAfiliado(Long fornecedorCodigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Fornecedor.class);
			consulta.createAlias("fornecedor", "a");
			consulta.add(Restrictions.eq("a.codigo", fornecedorCodigo));
			consulta.addOrder(Order.asc("razaoSocial"));
			List<Fornecedor> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Fornecedor> buscarPorFornecedor(Long fornecedorCodigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Retirada.class);
			consulta.createAlias("fornecedor", "a");
			consulta.add(Restrictions.eq("a.codigo", fornecedorCodigo));
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
