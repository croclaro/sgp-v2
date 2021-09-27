package br.com.disqueoleo.sgp.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.disqueoleo.sgp.domain.Afiliado;
import br.com.disqueoleo.sgp.util.HibernateUtil;

public class AfiliadoDAO extends GenericoDAO<Afiliado> {
	public Afiliado buscarPorCPFOuEmail(String cpfOuEmail) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Afiliado.class);
			consulta.add(Restrictions.or(Restrictions.eq("cpf", cpfOuEmail), Restrictions.eq("email", cpfOuEmail)));
			Afiliado resultado = (Afiliado) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
