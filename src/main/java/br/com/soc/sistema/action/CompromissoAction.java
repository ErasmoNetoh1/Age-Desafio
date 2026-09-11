package br.com.soc.sistema.action;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.business.AgendaBusiness;
import br.com.soc.sistema.business.CompromissoBusiness;
import br.com.soc.sistema.business.FuncionarioBusiness;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;

public class CompromissoAction extends Action {

	private List<CompromissoVo> compromissos = new ArrayList<>();
	private List<FuncionarioVo> funcionarios = new ArrayList<>();
	private List<AgendaVo> agendas = new ArrayList<>();
	private CompromissoBusiness business = new CompromissoBusiness();
	private FuncionarioBusiness funcionarioBusiness = new FuncionarioBusiness();
	private AgendaBusiness agendaBusiness = new AgendaBusiness();
	private CompromissoVo compromissoVo = new CompromissoVo();

	public String todos() {
		compromissos.addAll(business.trazerTodosOsCompromissos());
		return SUCCESS;
	}

	public String novo() {
		carregarDadosFormulario();
		return INPUT;
	}

	public String salvar() {
		if (compromissoVo.getRowid() == null || compromissoVo.getRowid().trim().isEmpty()) {
			business.salvarCompromisso(compromissoVo);
		} else {
			business.alterarCompromisso(compromissoVo);
		}

		return REDIRECT;
	}

	public String editar() {
		if (compromissoVo.getRowid() == null || compromissoVo.getRowid().trim().isEmpty())
			return REDIRECT;

		compromissoVo = business.buscarCompromissoPor(compromissoVo.getRowid());
		carregarDadosFormulario();
		return INPUT;
	}

	public String excluir() {
		if (compromissoVo.getRowid() == null || compromissoVo.getRowid().trim().isEmpty())
			return REDIRECT;

		business.excluirCompromisso(compromissoVo.getRowid());
		return REDIRECT;
	}

	private void carregarDadosFormulario() {
		funcionarios.clear();
		funcionarios.addAll(funcionarioBusiness.trazerTodosOsFuncionarios());
		agendas.clear();
		agendas.addAll(agendaBusiness.trazerTodasAsAgendas());
	}

	public List<CompromissoVo> getCompromissos() {
		return compromissos;
	}

	public List<FuncionarioVo> getFuncionarios() {
		return funcionarios;
	}

	public List<AgendaVo> getAgendas() {
		return agendas;
	}

	public CompromissoVo getCompromissoVo() {
		return compromissoVo;
	}

	public void setCompromissoVo(CompromissoVo compromissoVo) {
		this.compromissoVo = compromissoVo;
	}
}
