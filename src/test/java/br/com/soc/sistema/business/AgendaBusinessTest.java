package br.com.soc.sistema.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.infra.PeriodoDisponivel;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;

public class AgendaBusinessTest {

	@Test(expected = BusinessException.class)
	public void naoDeveSalvarAgendaComNomeVazio() {
		AgendaVo agenda = new AgendaVo(null, "", PeriodoDisponivel.MANHA);

		new AgendaBusiness().salvarAgenda(agenda);
	}

	@Test(expected = BusinessException.class)
	public void naoDeveSalvarAgendaSemPeriodoDisponivel() {
		AgendaVo agenda = new AgendaVo(null, "Agenda de teste", null);

		new AgendaBusiness().salvarAgenda(agenda);
	}

	@Test
	public void deveSalvarEListarAgenda() {
		AgendaBusiness business = new AgendaBusiness();
		String nome = "Agenda de teste";

		business.salvarAgenda(new AgendaVo(null, nome, PeriodoDisponivel.MANHA));

		AgendaVo agenda = business.trazerTodasAsAgendas()
				.stream()
				.filter(item -> item.getNome().equals(nome))
				.findFirst()
				.orElse(null);

		assertNotNull(agenda);
		assertEquals(PeriodoDisponivel.MANHA, agenda.getPeriodoDisponivel());
	}

	@Test
	public void deveAlterarAgenda() {
		AgendaBusiness business = new AgendaBusiness();
		String nome = "Agenda para alterar";

		business.salvarAgenda(new AgendaVo(null, nome, PeriodoDisponivel.MANHA));

		AgendaVo agenda = business.trazerTodasAsAgendas()
				.stream()
				.filter(item -> item.getNome().equals(nome))
				.findFirst()
				.orElse(null);

		assertNotNull(agenda);
		agenda.setNome("Agenda alterada");
		agenda.setPeriodoDisponivel(PeriodoDisponivel.TARDE);

		business.alterarAgenda(agenda);

		AgendaVo agendaAlterada = business.buscarAgendaPor(agenda.getRowid());

		assertEquals("Agenda alterada", agendaAlterada.getNome());
		assertEquals(PeriodoDisponivel.TARDE, agendaAlterada.getPeriodoDisponivel());
	}

	@Test
	public void deveExcluirAgendaPeloCodigo() {
		AgendaBusiness business = new AgendaBusiness();
		String nome = "Agenda para exclusao";

		business.salvarAgenda(new AgendaVo(null, nome, PeriodoDisponivel.AMBOS));

		AgendaVo agenda = business.trazerTodasAsAgendas()
				.stream()
				.filter(item -> item.getNome().equals(nome))
				.findFirst()
				.orElse(null);

		assertNotNull(agenda);

		business.excluirAgenda(agenda.getRowid());

		assertNull(business.buscarAgendaPor(agenda.getRowid()));
	}

	@Test(expected = BusinessException.class)
	public void naoDeveExcluirAgendaComCompromissos() {
		String nomeFuncionario = "Funcionario da agenda bloqueada";
		String nomeAgenda = "Agenda com compromisso";
		FuncionarioBusiness funcionarioBusiness = new FuncionarioBusiness();
		AgendaBusiness agendaBusiness = new AgendaBusiness();

		funcionarioBusiness.salvarFuncionario(new FuncionarioVo(null, nomeFuncionario));
		agendaBusiness.salvarAgenda(new AgendaVo(null, nomeAgenda, PeriodoDisponivel.AMBOS));

		FuncionarioVo funcionario = funcionarioBusiness.trazerTodosOsFuncionarios()
				.stream()
				.filter(item -> item.getNome().equals(nomeFuncionario))
				.findFirst()
				.orElse(null);
		AgendaVo agenda = agendaBusiness.trazerTodasAsAgendas()
				.stream()
				.filter(item -> item.getNome().equals(nomeAgenda))
				.findFirst()
				.orElse(null);

		assertNotNull(funcionario);
		assertNotNull(agenda);

		CompromissoVo compromisso = new CompromissoVo();
		compromisso.setCodigoFuncionario(funcionario.getRowid());
		compromisso.setCodigoAgenda(agenda.getRowid());
		compromisso.setData("2026-09-12");
		compromisso.setHorario("12:00");
		new CompromissoBusiness().salvarCompromisso(compromisso);

		agendaBusiness.excluirAgenda(agenda.getRowid());
	}

	@Test
	public void naoDeveAlterarPeriodoQueInvalidaCompromissoExistente() {
		String nomeFuncionario = "Funcionario da agenda alterada";
		String nomeAgenda = "Agenda com horario da tarde";
		FuncionarioBusiness funcionarioBusiness = new FuncionarioBusiness();
		AgendaBusiness agendaBusiness = new AgendaBusiness();

		funcionarioBusiness.salvarFuncionario(new FuncionarioVo(null, nomeFuncionario));
		agendaBusiness.salvarAgenda(new AgendaVo(null, nomeAgenda, PeriodoDisponivel.AMBOS));

		FuncionarioVo funcionario = funcionarioBusiness.trazerTodosOsFuncionarios()
				.stream()
				.filter(item -> item.getNome().equals(nomeFuncionario))
				.findFirst()
				.orElse(null);
		AgendaVo agenda = agendaBusiness.trazerTodasAsAgendas()
				.stream()
				.filter(item -> item.getNome().equals(nomeAgenda))
				.findFirst()
				.orElse(null);

		assertNotNull(funcionario);
		assertNotNull(agenda);

		CompromissoVo compromisso = new CompromissoVo();
		compromisso.setCodigoFuncionario(funcionario.getRowid());
		compromisso.setCodigoAgenda(agenda.getRowid());
		compromisso.setData("2026-09-12");
		compromisso.setHorario("16:00");
		new CompromissoBusiness().salvarCompromisso(compromisso);

		agenda.setPeriodoDisponivel(PeriodoDisponivel.MANHA);

		try {
			agendaBusiness.alterarAgenda(agenda);
			fail("A alteracao deveria ser bloqueada");
		} catch (BusinessException e) {
			assertEquals(PeriodoDisponivel.AMBOS, agendaBusiness.buscarAgendaPor(agenda.getRowid()).getPeriodoDisponivel());
		}
	}
}
