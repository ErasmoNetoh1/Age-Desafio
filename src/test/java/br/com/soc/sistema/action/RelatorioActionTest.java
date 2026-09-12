package br.com.soc.sistema.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class RelatorioActionTest {

	@Test
	public void deveExibirMensagemQuandoPeriodoDoRelatorioNaoForInformado() {
		RelatorioAction action = new RelatorioAction();

		assertEquals("success", action.gerar());
		assertFalse(action.getActionErrors().isEmpty());
	}

	@Test
	public void deveExibirMensagemQuandoExportacaoNaoTiverPeriodoValido() {
		RelatorioAction action = new RelatorioAction();

		assertEquals("success", action.exportar());
		assertFalse(action.getActionErrors().isEmpty());
	}
}
