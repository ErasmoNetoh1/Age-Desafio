package br.com.soc.sistema.business;

import java.util.List;

import br.com.soc.sistema.dao.AgendaDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.vo.AgendaVo;

public class AgendaBusiness {

	private AgendaDao dao;

	public AgendaBusiness() {
		dao = new AgendaDao();
	}

	public List<AgendaVo> trazerTodasAsAgendas() {
		return dao.findAllAgendas();
	}

	public void salvarAgenda(AgendaVo agendaVo) {
		try {
			validarAgenda(agendaVo);
			dao.insertAgenda(agendaVo);
		} catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a inclusao da agenda");
		}
	}

	private void validarAgenda(AgendaVo agendaVo) {
		if (agendaVo == null || agendaVo.getNome() == null || agendaVo.getNome().trim().isEmpty())
			throw new IllegalArgumentException("Nome da agenda nao pode ser em branco");

		if (agendaVo.getPeriodoDisponivel() == null)
			throw new IllegalArgumentException("Periodo disponivel deve ser informado");
	}
}
