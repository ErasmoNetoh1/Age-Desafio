package br.com.soc.sistema.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.com.soc.sistema.exception.BusinessException;
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
