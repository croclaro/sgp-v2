package br.com.disqueoleo.sgp.util;

import org.hibernate.Session;
import org.junit.Test;

public class HibernateUtilTeste {
	@Test
	public void conectar() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
	}
}
