package br.com.caelum.financas.mb;


import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.financas.dao.MovimentacaoDao;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.modelo.ValorPorMesEAno;

@Named
@RequestScoped
public class MesesComMovimentacaoBean {
	
	@Inject
	private MovimentacaoDao dao;
	
	private List<ValorPorMesEAno> valoresPorMesEAnos;
	private Conta conta = new Conta();

	private TipoMovimentacao tipoMovimentacao;
	
	public void lista() {
		System.out.println("Listando as contas pelos valores movimentados no mes");
		valoresPorMesEAnos = dao.listaMuitaCoisa(conta, tipoMovimentacao);
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public Conta getConta() {
		return conta;
	}

	public List<ValorPorMesEAno> getValoresPorMesEAnos() {
		return valoresPorMesEAnos;
	}

}
