package br.com.soc.sistema.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.filter.RelatorioFilter;
import br.com.soc.sistema.infra.PeriodoDisponivel;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;

public class CompromissoBusinessTest {

	@Test
	public void deveSalvarCompromissoNoHorarioDisponivel() {
		String nomeFuncionario = "Funcionario compromisso valido";
		String nomeAgenda = "Agenda ambos valida";
		String codigoFuncionario = criarFuncionario(nomeFuncionario);
		String codigoAgenda = criarAgenda(nomeAgenda, PeriodoDisponivel.AMBOS);
		CompromissoBusiness business = new CompromissoBusiness();

		business.salvarCompromisso(criarCompromisso(codigoFuncionario, codigoAgenda, "2026-09-12", "17:59"));

		CompromissoVo compromisso = business.trazerTodosOsCompromissos()
				.stream()
				.filter(item -> item.getNomeFuncionario().equals(nomeFuncionario))
				.findFirst()
				.orElse(null);

		assertNotNull(compromisso);
		assertEquals(nomeAgenda, compromisso.getNomeAgenda());
		assertEquals("17:59", compromisso.getHorario());
	}

	@Test(expected = BusinessException.class)
	public void naoDeveSalvarCompromissoDaManhaAoMeioDia() {
		String codigoFuncionario = criarFuncionario("Funcionario manha invalido");
		String codigoAgenda = criarAgenda("Agenda manha", PeriodoDisponivel.MANHA);

		new CompromissoBusiness().salvarCompromisso(
				criarCompromisso(codigoFuncionario, codigoAgenda, "2026-09-12", "12:00"));
	}

	@Test(expected = BusinessException.class)
	public void naoDeveSalvarCompromissoDaTardeAposFimDoExpediente() {
		String codigoFuncionario = criarFuncionario("Funcionario tarde invalido");
		String codigoAgenda = criarAgenda("Agenda tarde", PeriodoDisponivel.TARDE);

		new CompromissoBusiness().salvarCompromisso(
				criarCompromisso(codigoFuncionario, codigoAgenda, "2026-09-12", "18:00"));
	}

	@Test
	public void deveAlterarCompromisso() {
		String nomeFuncionario = "Funcionario compromisso alterado";
		String codigoFuncionario = criarFuncionario(nomeFuncionario);
		String codigoAgendaManha = criarAgenda("Agenda original", PeriodoDisponivel.MANHA);
		String codigoAgendaTarde = criarAgenda("Agenda alterada", PeriodoDisponivel.TARDE);
		CompromissoBusiness business = new CompromissoBusiness();

		business.salvarCompromisso(criarCompromisso(codigoFuncionario, codigoAgendaManha, "2026-09-12", "08:00"));

		CompromissoVo compromisso = business.trazerTodosOsCompromissos()
				.stream()
				.filter(item -> item.getNomeFuncionario().equals(nomeFuncionario))
				.findFirst()
				.orElse(null);

		assertNotNull(compromisso);
		compromisso.setCodigoAgenda(codigoAgendaTarde);
		compromisso.setData("2026-09-13");
		compromisso.setHorario("13:00");

		business.alterarCompromisso(compromisso);

		CompromissoVo compromissoAlterado = business.buscarCompromissoPor(compromisso.getRowid());

		assertEquals(codigoAgendaTarde, compromissoAlterado.getCodigoAgenda());
		assertEquals("2026-09-13", compromissoAlterado.getData());
		assertEquals("13:00", compromissoAlterado.getHorario());
	}

	@Test
	public void deveExcluirCompromissoPeloCodigo() {
		String nomeFuncionario = "Funcionario compromisso excluido";
		String codigoFuncionario = criarFuncionario(nomeFuncionario);
		String codigoAgenda = criarAgenda("Agenda para exclusao de compromisso", PeriodoDisponivel.AMBOS);
		CompromissoBusiness business = new CompromissoBusiness();

		business.salvarCompromisso(criarCompromisso(codigoFuncionario, codigoAgenda, "2026-09-12", "10:00"));

		CompromissoVo compromisso = business.trazerTodosOsCompromissos()
				.stream()
				.filter(item -> item.getNomeFuncionario().equals(nomeFuncionario))
				.findFirst()
				.orElse(null);

		assertNotNull(compromisso);

		business.excluirCompromisso(compromisso.getRowid());

		assertNull(business.buscarCompromissoPor(compromisso.getRowid()));
	}

	@Test
	public void deveBuscarCompromissosDentroDoPeriodoInformado() {
		String codigoFuncionario = criarFuncionario("Funcionario do relatorio");
		String codigoAgenda = criarAgenda("Agenda do relatorio", PeriodoDisponivel.AMBOS);
		CompromissoBusiness business = new CompromissoBusiness();

		business.salvarCompromisso(criarCompromisso(codigoFuncionario, codigoAgenda, "2026-09-10", "08:00"));
		business.salvarCompromisso(criarCompromisso(codigoFuncionario, codigoAgenda, "2026-09-12", "09:00"));

		RelatorioFilter filtro = new RelatorioFilter();
		filtro.setDataInicial("2026-09-11");
		filtro.setDataFinal("2026-09-12");

		assertEquals(1, business.buscarCompromissosPorPeriodo(filtro).stream()
				.filter(item -> item.getNomeFuncionario().equals("Funcionario do relatorio"))
				.count());
	}

	@Test(expected = BusinessException.class)
	public void naoDeveBuscarRelatorioComPeriodoInvalido() {
		RelatorioFilter filtro = new RelatorioFilter();
		filtro.setDataInicial("2026-09-13");
		filtro.setDataFinal("2026-09-12");

		new CompromissoBusiness().buscarCompromissosPorPeriodo(filtro);
	}

	private String criarFuncionario(String nome) {
		FuncionarioBusiness business = new FuncionarioBusiness();
		business.salvarFuncionario(new FuncionarioVo(null, nome));

		FuncionarioVo funcionario = business.trazerTodosOsFuncionarios()
				.stream()
				.filter(item -> item.getNome().equals(nome))
				.findFirst()
				.orElse(null);

		assertNotNull(funcionario);
		return funcionario.getRowid();
	}

	private String criarAgenda(String nome, PeriodoDisponivel periodo) {
		AgendaBusiness business = new AgendaBusiness();
		business.salvarAgenda(new AgendaVo(null, nome, periodo));

		AgendaVo agenda = business.trazerTodasAsAgendas()
				.stream()
				.filter(item -> item.getNome().equals(nome))
				.findFirst()
				.orElse(null);

		assertNotNull(agenda);
		return agenda.getRowid();
	}

	private CompromissoVo criarCompromisso(String codigoFuncionario, String codigoAgenda, String data, String horario) {
		CompromissoVo compromisso = new CompromissoVo();
		compromisso.setCodigoFuncionario(codigoFuncionario);
		compromisso.setCodigoAgenda(codigoAgenda);
		compromisso.setData(data);
		compromisso.setHorario(horario);
		return compromisso;
	}
}
