package br.com.disqueoleo.sgp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;

import br.com.disqueoleo.sgp.domain.Fornecedor;
import br.com.disqueoleo.sgp.domain.Retirada;
import br.com.disqueoleo.sgp.domain.Usuario;
import br.com.disqueoleo.sgp.dto.RetiradaDTO;
import br.com.disqueoleo.sgp.enums.TipoUsuario;
import br.com.disqueoleo.sgp.util.HibernateUtil;

public class RetiradaDao extends GenericoDAO<Retirada> {
	public Long buscarResiduos(Usuario usuario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Retirada.class);

			if (usuario.getTipoUsuario() == TipoUsuario.FORNECEDOR) {
				consulta.createAlias("fornecedor", "f");
				consulta.add(Restrictions.eq("f.codigo", usuario.getFornecedor().getCodigo()));
			} else if (usuario.getTipoUsuario() == TipoUsuario.AFILIADO) {
				consulta.createAlias("afiliado", "a");
				consulta.add(Restrictions.eq("a.codigo", usuario.getAfiliado().getCodigo()));
			}

			consulta.setProjection(Projections.sum("residuo"));

			Long resultado = (Long) consulta.uniqueResult();

			return resultado == null ? 0 : resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public Long buscarOleos(Usuario usuario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Retirada.class);

			if (usuario.getTipoUsuario() == TipoUsuario.FORNECEDOR) {
				consulta.createAlias("fornecedor", "f");
				consulta.add(Restrictions.eq("f.codigo", usuario.getFornecedor().getCodigo()));
			} else if (usuario.getTipoUsuario() == TipoUsuario.AFILIADO) {
				consulta.createAlias("afiliado", "a");
				consulta.add(Restrictions.eq("a.codigo", usuario.getAfiliado().getCodigo()));
			}

			consulta.setProjection(Projections.sum("oleo"));

			Long resultado = (Long) consulta.uniqueResult();

			return resultado == null ? 0 : resultado;
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
			} else if (usuario.getTipoUsuario() == TipoUsuario.AFILIADO) {
				consulta.createAlias("afiliado", "a");
				consulta.add(Restrictions.eq("a.codigo", usuario.getAfiliado().getCodigo()));
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

	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	public List<RetiradaDTO> buscarPorAnoEMes(Integer ano, Integer mes) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("	ANO, ");
		sql.append("	MES, ");
		sql.append("	SUM(OLEO) OLEO,");
		sql.append("	SUM(RESIDUO) RESIDUO ");
		sql.append("FROM");
		sql.append("	(");
		sql.append("		SELECT ");
		sql.append("			SUBSTRING(dataRetirada, 7,4) ANO, ");
		sql.append("			SUBSTRING(dataRetirada, 4,2) MES, ");
		sql.append("			oleo, ");
		sql.append("			residuo ");
		sql.append("		FROM ");
		sql.append("			dbo.Retirada ");
		sql.append("	) TEMP ");
		sql.append("GROUP BY ");
		sql.append("	ANO, ");
		sql.append("	MES ");
		sql.append("HAVING ");
		sql.append("1 = 1 ");

		if (ano != null) {
			sql.append("	AND ANO = :ano ");
		}

		if (mes != null) {
			sql.append("	AND MES = :mes ");
		}

		sql.append("ORDER BY ");
		sql.append("	ANO, ");
		sql.append("	MES ");

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			SQLQuery consulta = sessao.createSQLQuery(sql.toString());

			if (ano != null) {
				consulta.setInteger("ano", ano);
			}

			if (mes != null) {
				consulta.setInteger("mes", mes);
			}

			consulta.setResultTransformer(new ResultTransformer() {
				@Override
				public Object transformTuple(Object[] tuple, String[] aliases) {
					return new RetiradaDTO(Integer.valueOf((String) tuple[0]), Integer.valueOf((String) tuple[1]),
							((Number) tuple[2]).intValue(), ((Number) tuple[3]).intValue());
				}

				@Override
				public List transformList(List collection) {
					return collection;
				}
			});

			List<RetiradaDTO> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	public List<RetiradaDTO> buscarPorAnoEMesEUsuario(Integer ano, Integer mes, Usuario usuario) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("	ANO, ");
		sql.append("	MES, ");
		sql.append("	SUM(OLEO) OLEO,");
		sql.append("	SUM(RESIDUO) RESIDUO ");
		sql.append("FROM");
		sql.append("	(");
		sql.append("		SELECT ");
		sql.append("			SUBSTRING(dataRetirada, 7,4) ANO, ");
		sql.append("			SUBSTRING(dataRetirada, 4,2) MES, ");
		sql.append("			oleo, ");
		sql.append("			residuo ");
		sql.append("		FROM ");
		sql.append("			dbo.Retirada ");

		if (usuario.getTipoUsuario() == TipoUsuario.FORNECEDOR) {
			sql.append("		WHERE fornecedor_codigo = :fornecedor ");
		} else if (usuario.getTipoUsuario() == TipoUsuario.AFILIADO) {
			sql.append("		WHERE afiliado_codigo = :afiliado ");
		}

		sql.append("	) TEMP ");
		sql.append("GROUP BY ");
		sql.append("	ANO, ");
		sql.append("	MES ");
		sql.append("HAVING ");
		sql.append("1 = 1 ");

		if (ano != null) {
			sql.append("	AND ANO = :ano ");
		}

		if (mes != null) {
			sql.append("	AND MES = :mes ");
		}

		sql.append("ORDER BY ");
		sql.append("	ANO, ");
		sql.append("	MES ");

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			SQLQuery consulta = sessao.createSQLQuery(sql.toString());

			if (usuario.getTipoUsuario() == TipoUsuario.FORNECEDOR) {
				consulta.setLong("fornecedor", usuario.getFornecedor().getCodigo());
			} else if (usuario.getTipoUsuario() == TipoUsuario.AFILIADO) {
				consulta.setLong("afiliado", usuario.getAfiliado().getCodigo());
			}

			if (ano != null) {
				consulta.setInteger("ano", ano);
			}

			if (mes != null) {
				consulta.setInteger("mes", mes);
			}

			consulta.setResultTransformer(new ResultTransformer() {
				@Override
				public Object transformTuple(Object[] tuple, String[] aliases) {
					return new RetiradaDTO(Integer.valueOf((String) tuple[0]), Integer.valueOf((String) tuple[1]),
							((Number) tuple[2]).intValue(), ((Number) tuple[3]).intValue());
				}

				@Override
				public List transformList(List collection) {
					return collection;
				}
			});

			List<RetiradaDTO> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public static void main(String[] args) {
		List<RetiradaDTO> lista = new RetiradaDao().buscarPorAnoEMes(2021, 1);

		lista.forEach(retirada -> System.out.println(retirada));
	}

	@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
	public List<RetiradaDTO> retiradaOleoResiduo(Integer ano, Integer mes) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("	ANO, ");
		sql.append("	MES, ");
		sql.append("	SUM(OLEO) OLEO,");
		sql.append("	SUM(RESIDUO) RESIDUO ");
		sql.append("FROM");
		sql.append("	(");
		sql.append("		SELECT ");
		sql.append("			SUBSTRING(dataRetirada, 7,4) ANO, ");
		sql.append("			SUBSTRING(dataRetirada, 4,2) MES, ");
		sql.append("			oleo, ");
		sql.append("			residuo ");
		sql.append("		FROM ");
		sql.append("			dbo.Retirada ");
		sql.append("	) TEMP ");
		sql.append("GROUP BY ");
		sql.append("	ANO, ");
		sql.append("	MES ");
		sql.append("HAVING ");
		sql.append("1 = 1 ");

		if (ano != null) {
			sql.append("	AND ANO = :ano ");
		}

		if (mes != null) {
			sql.append("	AND MES = :mes ");
		}

		sql.append("ORDER BY ");
		sql.append("	ANO, ");
		sql.append("	MES ");

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			SQLQuery consulta = sessao.createSQLQuery(sql.toString());

			if (ano != null) {
				consulta.setInteger("ano", ano);
			}

			if (mes != null) {
				consulta.setInteger("mes", mes);
			}

			consulta.setResultTransformer(new ResultTransformer() {
				@Override
				public Object transformTuple(Object[] tuple, String[] aliases) {
					return new RetiradaDTO(Integer.valueOf((String) tuple[0]), Integer.valueOf((String) tuple[1]),
							((Number) tuple[2]).intValue(), ((Number) tuple[3]).intValue());
				}

				@Override
				public List transformList(List collection) {
					return collection;
				}
			});

			List<RetiradaDTO> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
