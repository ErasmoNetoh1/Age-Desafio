package br.com.soc.sistema.business;

import java.util.List;

import br.com.soc.sistema.dao.AgendaDao;
import br.com.soc.sistema.dao.CompromissoDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.vo.AgendaVo;

public class AgendaBusiness {

	private AgendaDao dao;
	private CompromissoDao compromissoDao;

	public AgendaBusiness() {
		dao = new AgendaDao();
		compromissoDao = new CompromissoDao();
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

	public void alterarAgenda(AgendaVo agendaVo) {
		try {
			validarAgenda(agendaVo);
			dao.updateAgenda(agendaVo);
		} catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a alteracao da agenda");
		}
	}

	public void excluirAgenda(String codigo) {
		try {
			Integer.parseInt(codigo);

			if (compromissoDao.existeCompromissoParaAgenda(codigo))
				throw new BusinessException("Nao e possivel excluir uma agenda com compromissos cadastrados");

			dao.deleteAgenda(codigo);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a exclusao da agenda");
		}
	}

	public AgendaVo buscarAgendaPor(String codigo) {
		try {
			return dao.findByCodigo(Integer.parseInt(codigo));
		} catch (NumberFormatException e) {
			throw new BusinessException("Foi informado um caracter no lugar de um numero");
		}
	}

	private void validarAgenda(AgendaVo agendaVo) {
		if (agendaVo == null || agendaVo.getNome() == null || agendaVo.getNome().trim().isEmpty())
			throw new IllegalArgumentException("Nome da agenda nao pode ser em branco");

		if (agendaVo.getPeriodoDisponivel() == null)
			throw new IllegalArgumentException("Periodo disponivel deve ser informado");
	}
}
