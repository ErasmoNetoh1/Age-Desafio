package br.com.soc.sistema.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import br.com.soc.sistema.vo.CompromissoVo;

public class CompromissoActionTest {

	@Test
	public void deveExibirMensagemNoFormularioQuandoCompromissoForInvalido() {
		CompromissoAction action = new CompromissoAction();
		action.setCompromissoVo(new CompromissoVo());

		assertEquals("input", action.salvar());
		assertFalse(action.getActionErrors().isEmpty());
	}
}
