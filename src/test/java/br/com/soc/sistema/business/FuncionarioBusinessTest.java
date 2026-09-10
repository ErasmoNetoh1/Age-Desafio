package br.com.soc.sistema.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
	
	@Test
	public void deveAlterarNomeDeFuncionario() {
		FuncionarioBusiness business = new FuncionarioBusiness();
		FuncionarioVo funcionario = new FuncionarioVo("1", "João Atualizado");
		
		business.alterarFuncionario(funcionario);
		
		FuncionarioVo funcionarioAlterado = business.buscarFuncionarioPor("1");
		
		assertEquals("João Atualizado", funcionarioAlterado.getNome());
	}

	@Test
	public void deveExcluirFuncionarioPeloCodigo() {
		FuncionarioBusiness business = new FuncionarioBusiness();
		String nome = "Funcionario para exclusao";

		business.salvarFuncionario(new FuncionarioVo(null, nome));

		FuncionarioVo funcionario = business.trazerTodosOsFuncionarios()
				.stream()
				.filter(item -> item.getNome().equals(nome))
				.findFirst()
				.orElse(null);

		assertNotNull(funcionario);

		business.excluirFuncionario(funcionario.getRowid());

		assertNull(business.buscarFuncionarioPor(funcionario.getRowid()));
	}
	
}

//Se tentar salvar com nome vazio cai em business exception
