package br.com.soc.sistema.business;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.com.soc.sistema.dao.CompromissoDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.filter.RelatorioFilter;
import br.com.soc.sistema.infra.PeriodoDisponivel;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;

public class CompromissoBusiness {

	private static final LocalTime INICIO_MANHA = LocalTime.of(7, 0);
	private static final LocalTime INICIO_TARDE = LocalTime.of(12, 0);
	private static final LocalTime FIM_EXPEDIENTE = LocalTime.of(18, 0);

	private CompromissoDao dao;
	private FuncionarioBusiness funcionarioBusiness;
	private AgendaBusiness agendaBusiness;

	public CompromissoBusiness() {
		dao = new CompromissoDao();
		funcionarioBusiness = new FuncionarioBusiness();
		agendaBusiness = new AgendaBusiness();
	}

	public List<CompromissoVo> trazerTodosOsCompromissos() {
		return dao.findAllCompromissos();
	}

	public List<CompromissoVo> buscarCompromissosPorPeriodo(RelatorioFilter filtro) {
		try {
			if (filtro == null || estaVazio(filtro.getDataInicial()) || estaVazio(filtro.getDataFinal()))
				throw new BusinessException("As datas inicial e final devem ser informadas");

			LocalDate dataInicial = LocalDate.parse(filtro.getDataInicial());
			LocalDate dataFinal = LocalDate.parse(filtro.getDataFinal());

			if (dataInicial.isAfter(dataFinal))
				throw new BusinessException("A data inicial nao pode ser maior que a data final");

			return dao.findCompromissosPorPeriodo(dataInicial, dataFinal);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("As datas informadas sao invalidas");
		}
	}

	public void salvarCompromisso(CompromissoVo compromissoVo) {
		try {
			validarCompromisso(compromissoVo);
			dao.insertCompromisso(compromissoVo);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a inclusao do compromisso");
		}
	}

	public void alterarCompromisso(CompromissoVo compromissoVo) {
		try {
			validarCompromisso(compromissoVo);
			dao.updateCompromisso(compromissoVo);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a alteracao do compromisso");
		}
	}

	public void excluirCompromisso(String codigo) {
		try {
			Integer.parseInt(codigo);
			dao.deleteCompromisso(codigo);
		} catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a exclusao do compromisso");
		}
	}

	public CompromissoVo buscarCompromissoPor(String codigo) {
		try {
			return dao.findByCodigo(Integer.parseInt(codigo));
		} catch (NumberFormatException e) {
			throw new BusinessException("Foi informado um caracter no lugar de um numero");
		}
	}

	private void validarCompromisso(CompromissoVo compromissoVo) {
		validarCamposObrigatorios(compromissoVo);
		FuncionarioVo funcionario = funcionarioBusiness.buscarFuncionarioPor(compromissoVo.getCodigoFuncionario());
		AgendaVo agenda = agendaBusiness.buscarAgendaPor(compromissoVo.getCodigoAgenda());

		if (funcionario == null)
			throw new BusinessException("Funcionario informado nao existe");

		if (agenda == null)
			throw new BusinessException("Agenda informada nao existe");

		LocalDate.parse(compromissoVo.getData());
		LocalTime horario = LocalTime.parse(compromissoVo.getHorario());
		validarDisponibilidade(agenda.getPeriodoDisponivel(), horario);
	}

	private void validarCamposObrigatorios(CompromissoVo compromissoVo) {
		if (compromissoVo == null
				|| estaVazio(compromissoVo.getCodigoFuncionario())
				|| estaVazio(compromissoVo.getCodigoAgenda())
				|| estaVazio(compromissoVo.getData())
				|| estaVazio(compromissoVo.getHorario()))
			throw new BusinessException("Todos os campos do compromisso devem ser informados");
	}

	private void validarDisponibilidade(PeriodoDisponivel periodo, LocalTime horario) {
		boolean horarioValido;

		switch (periodo) {
		case MANHA:
			horarioValido = !horario.isBefore(INICIO_MANHA) && horario.isBefore(INICIO_TARDE);
			break;
		case TARDE:
			horarioValido = !horario.isBefore(INICIO_TARDE) && horario.isBefore(FIM_EXPEDIENTE);
			break;
		case AMBOS:
			horarioValido = !horario.isBefore(INICIO_MANHA) && horario.isBefore(FIM_EXPEDIENTE);
			break;
		default:
			horarioValido = false;
		}

		if (!horarioValido)
			throw new BusinessException("Horario nao esta disponivel para a agenda informada");
	}

	private boolean estaVazio(String valor) {
		return valor == null || valor.trim().isEmpty();
	}
}
