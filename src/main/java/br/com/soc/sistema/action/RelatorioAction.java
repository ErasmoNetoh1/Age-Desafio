package br.com.soc.sistema.action;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.business.CompromissoBusiness;
import br.com.soc.sistema.filter.RelatorioFilter;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.vo.CompromissoVo;

public class RelatorioAction extends Action {

	private List<CompromissoVo> compromissos = new ArrayList<>();
	private CompromissoBusiness business = new CompromissoBusiness();
	private RelatorioFilter filtro = new RelatorioFilter();

	public String todos() {
		return SUCCESS;
	}

	public String gerar() {
		compromissos.addAll(business.buscarCompromissosPorPeriodo(filtro));
		return SUCCESS;
	}

	public List<CompromissoVo> getCompromissos() {
		return compromissos;
	}

	public RelatorioFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(RelatorioFilter filtro) {
		this.filtro = filtro;
	}
}
