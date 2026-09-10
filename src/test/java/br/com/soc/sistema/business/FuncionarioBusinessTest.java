package br.com.soc.sistema.business;

import org.junit.Test;

import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionarioBusinessTest {
	
	@Test(expected = BusinessException.class)
	public void naoDeveSalvarFuncionarioComNomeVazio() {
		FuncionarioVo funcionario = new FuncionarioVo();
		funcionario.setNome("");
		
		new FuncionarioBusiness().salvarFuncionario(funcionario);
	}
	
}

//Se tentar salvar com nome vazio cai em business exception