package br.com.disqueoleo.sgp.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.disqueoleo.sgp.domain.Funcionario;
import br.com.disqueoleo.sgp.util.HibernateUtil;

public class FuncionarioDAO extends GenericoDAO<Funcionario>{
	public Funcionario buscarPorCPFOuEmail(String cpfOuEmail) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Funcionario.class);
			consulta.add(
				Restrictions.or(
					Restrictions.eq("cpf", cpfOuEmail),
					Restrictions.eq("email", cpfOuEmail)
				)
			);
			Funcionario resultado = (Funcionario) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
