package br.com.disqueoleo.sgp.dao;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.disqueoleo.sgp.domain.Usuario;
import br.com.disqueoleo.sgp.util.HibernateUtil;

public class UsuarioDAO extends GenericoDAO<Usuario> {
	public Usuario autenticar(String cpf, String senha, Boolean status) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.createAlias("funcionario", "f");			
			
			consulta.add(Restrictions.eq("f.cpf", cpf));				
			
			SimpleHash hash = new SimpleHash("md5", senha);
			consulta.add(Restrictions.eq("senha", hash.toHex()));

			Usuario resultado = (Usuario) consulta.uniqueResult();

			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	public Usuario autenticarFuncionario (String cpfOuEmail, String senha, Boolean status) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.createAlias("funcionario", "f");			
			
			consulta.add(
				Restrictions.or(
					Restrictions.eq("f.cpf", cpfOuEmail),
					Restrictions.eq("f.email", cpfOuEmail)
				)
			);				
			
			SimpleHash hash = new SimpleHash("md5", senha);
			consulta.add(Restrictions.eq("senha", hash.toHex()));

			Usuario resultado = (Usuario) consulta.uniqueResult();

			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	public Usuario autenticarAFiliado (String cpfOuEmail, String senha, Boolean status) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.createAlias("afiliado", "a");			
			
			consulta.add(
				Restrictions.or(
					Restrictions.eq("a.cpf", cpfOuEmail),
					Restrictions.eq("a.email", cpfOuEmail)
				)
			);				
			
			SimpleHash hash = new SimpleHash("md5", senha);
			consulta.add(Restrictions.eq("senha", hash.toHex()));

			Usuario resultado = (Usuario) consulta.uniqueResult();

			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	public Usuario autenticarFornecedor (String cpfOuCNPJOuEmail, String senha, Boolean status) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.createAlias("fornecedor", "f");			
			
			consulta.add(
				Restrictions.or(
					Restrictions.eq("f.cpf", cpfOuCNPJOuEmail),
					Restrictions.eq("f.cnpj", cpfOuCNPJOuEmail),
					Restrictions.eq("f.email", cpfOuCNPJOuEmail)
				)
			);				
			
			SimpleHash hash = new SimpleHash("md5", senha);
			consulta.add(Restrictions.eq("senha", hash.toHex()));

			Usuario resultado = (Usuario) consulta.uniqueResult();

			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}