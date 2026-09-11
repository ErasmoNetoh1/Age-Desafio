package br.com.soc.sistema.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.infra.PeriodoDisponivel;
import br.com.soc.sistema.vo.AgendaVo;

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
}
