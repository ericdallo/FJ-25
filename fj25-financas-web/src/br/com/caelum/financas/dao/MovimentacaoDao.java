package br.com.caelum.financas.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.caelum.financas.exception.ValorNegativoException;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.modelo.ValorPorMesEAno;

@Stateless
public class MovimentacaoDao {

	@Inject
	private EntityManager manager;

	public void adiciona(Movimentacao movimentacao) {
		this.manager.joinTransaction();
		this.manager.persist(movimentacao);
		
		if(movimentacao.getValor().compareTo(BigDecimal.ZERO) < 0){
			throw new ValorNegativoException("Movimentacao negativa");
		}
	}

	public Movimentacao busca(Integer id) {
		return this.manager.find(Movimentacao.class, id);
	}

	public List<Movimentacao> lista() {
		return this.manager.createQuery("select m from Movimentacao m", Movimentacao.class).getResultList();
	}
	
	public List<Movimentacao> listaPorConta(Conta conta) {
		String jpql = "SELECT m from " + Movimentacao.class.getSimpleName() + " m "
				+ "where m.conta = :conta order by m.valor desc";
		Query query = manager.createQuery(jpql);
		query.setParameter("conta", conta);
		
		return query.getResultList();
	}
	
	public List<Movimentacao> listaPorValorETipo(BigDecimal valor, TipoMovimentacao tipo) {
		String jpql = "SELECT m from " + Movimentacao.class.getSimpleName() + " m "
				+ "where m.valor <= :valor AND m.tipoMovimentacao = :tipo";
		Query query = manager.createQuery(jpql);
		query.setParameter("valor", valor);
		query.setParameter("tipo", tipo);
		//query.setHint("org.hibernate.cacheable", "true");
		
		return query.getResultList();
	}
	
	public BigDecimal calculaTotalMovimentacao(Conta conta, TipoMovimentacao tipo){
		String jpql = "SELECT sum(m.valor) from " + Movimentacao.class.getSimpleName() + " m"
				+ "where m.conta = :conta AND m.tipoMovimentacao = :tipo";
		TypedQuery<BigDecimal> query = manager.createQuery(jpql,BigDecimal.class);
		
		return query.getSingleResult();
		
	}
	
	public List<Movimentacao> buscaTodasPorConta(String titular){
		String jpql = "SELECT m from " + Movimentacao.class.getSimpleName() + " m "
				+ "WHERE m.conta.titular like :titular";
		TypedQuery<Movimentacao> query = manager.createQuery(jpql,Movimentacao.class);
		query.setParameter("titular", "%"+titular+"%");
		
		return query.getResultList();
	}
	
	public List<ValorPorMesEAno> listaMuitaCoisa(Conta conta, TipoMovimentacao tipo){
		String jpql = "SELECT new " + ValorPorMesEAno.class.getName() + "(month(m.data), year(m.data), sum(m.valor)) "+
					"from Movimentacao m "+
					"WHERE m.conta = :conta AND m.tipoMovimentacao = :tipo " + 
					"GROUPED BY year(m.data) ||month(m.data) "+
					"ORDER BY sum(m.valor) desc";
		TypedQuery<ValorPorMesEAno> query = manager.createQuery(jpql,ValorPorMesEAno.class);
		
		return query.getResultList(); 
	}

	public void remove(Movimentacao movimentacao) {
		this.manager.joinTransaction();
		Movimentacao movimentacaoParaRemover = this.manager.find(Movimentacao.class, movimentacao.getId());
		this.manager.remove(movimentacaoParaRemover);
	}
	
	public List<Movimentacao> listaTodasComCriteria(){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Movimentacao> criteria = builder.createQuery(Movimentacao.class);
		criteria.from(Movimentacao.class);
		
		return manager.createQuery(criteria).getResultList();
	}

	public BigDecimal somaMovimentacoesPorTitular(String titular){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteria = builder.createQuery(BigDecimal.class);
		
		Root<Movimentacao> root = criteria.from(Movimentacao.class);
		criteria.select(
			builder.sum(root.<BigDecimal>get("valor"))
		).where(
			builder.like(root.<Conta>get("conta").<String>get("titular"), "%"+titular+"%")
		);
		
		return manager.createQuery(criteria).getSingleResult();
	}
	
	public List<Movimentacao> pesquisa(Conta conta,TipoMovimentacao tipo, Integer mes){
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Movimentacao> criteria = builder.createQuery(Movimentacao.class);
		
		Root<Movimentacao> root = criteria.from(Movimentacao.class);
		
		Predicate conjunction = builder.conjunction();
		if(conta.getId() != null){
			conjunction = builder.and(conjunction , builder.equal(root.<Conta>get("conta"), conta));
		}
		
		if(mes != null && mes != 0){
			Expression<Integer> expression = builder.function("month", Integer.class, root.<Calendar>get("data"));
			conjunction = builder.and(conjunction , builder.equal(expression, mes));
		}
		
		if(tipo != null){
			conjunction = builder.and(conjunction,builder.equal(root.<TipoMovimentacao>get("tipoMovimentacao"), tipo));
		}
		
		criteria.where(conjunction);
		return manager.createQuery(criteria).getResultList();
	}
}
