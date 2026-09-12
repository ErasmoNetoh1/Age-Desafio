package br.com.soc.sistema.relatorio;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.Collections;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import br.com.soc.sistema.vo.CompromissoVo;

public class RelatorioXlsxTest {

	@Test
	public void deveGerarPlanilhaComCabecalhoELinhaDeCompromisso() throws Exception {
		CompromissoVo compromisso = new CompromissoVo();
		compromisso.setRowid("1");
		compromisso.setNomeFuncionario("Maria");
		compromisso.setNomeAgenda("Reuniao");
		compromisso.setData("2026-09-12");
		compromisso.setHorario("08:00");

		byte[] arquivo = new RelatorioXlsx().gerar(Collections.singletonList(compromisso));

		try (XSSFWorkbook planilha = new XSSFWorkbook(new ByteArrayInputStream(arquivo))) {
			assertEquals("Funcionario", planilha.getSheetAt(0).getRow(0).getCell(1).getStringCellValue());
			assertEquals("Maria", planilha.getSheetAt(0).getRow(1).getCell(1).getStringCellValue());
			assertEquals("12/09/2026", planilha.getSheetAt(0).getRow(1).getCell(3).getStringCellValue());
		}
	}
}
