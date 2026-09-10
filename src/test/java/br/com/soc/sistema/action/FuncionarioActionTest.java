package br.com.soc.sistema.action;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.soc.sistema.business.FuncionarioBusiness;
import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionarioActionTest {

    @Test
    public void deveSalvarNovoFuncionarioQuandoCodigoForVazio() {
        FuncionarioBusiness business = new FuncionarioBusiness();
        int totalAntes = business.trazerTodosOsFuncionarios().size();

        FuncionarioAction action = new FuncionarioAction();
        action.setFuncionarioVo(new FuncionarioVo("", "Novo Funcionario"));

        assertEquals("redirect", action.salvar());
        assertEquals(totalAntes + 1, business.trazerTodosOsFuncionarios().size());
    }
}