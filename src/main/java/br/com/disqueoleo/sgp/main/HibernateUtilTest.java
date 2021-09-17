package br.com.disqueoleo.sgp.main;

import org.hibernate.Session;

import br.com.disqueoleo.sgp.util.HibernateUtil;

public class HibernateUtilTest {
	public static void main(String[] args) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
	}
}