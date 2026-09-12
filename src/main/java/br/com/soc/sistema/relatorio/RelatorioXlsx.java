package br.com.soc.sistema.relatorio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.soc.sistema.vo.CompromissoVo;

public class RelatorioXlsx {

	public byte[] gerar(List<CompromissoVo> compromissos) throws IOException {
		try (XSSFWorkbook planilha = new XSSFWorkbook(); ByteArrayOutputStream arquivo = new ByteArrayOutputStream()) {
			Sheet aba = planilha.createSheet("Compromissos");
			criarCabecalho(aba);

			for (int indice = 0; indice < compromissos.size(); indice++) {
				criarLinha(aba, indice + 1, compromissos.get(indice));
			}

			for (int coluna = 0; coluna < 5; coluna++) {
				aba.autoSizeColumn(coluna);
			}

			planilha.write(arquivo);
			return arquivo.toByteArray();
		}
	}

	private void criarCabecalho(Sheet aba) {
		Row cabecalho = aba.createRow(0);
		cabecalho.createCell(0).setCellValue("Codigo");
		cabecalho.createCell(1).setCellValue("Funcionario");
		cabecalho.createCell(2).setCellValue("Agenda");
		cabecalho.createCell(3).setCellValue("Data");
		cabecalho.createCell(4).setCellValue("Horario");
	}

	private void criarLinha(Sheet aba, int numeroLinha, CompromissoVo compromisso) {
		Row linha = aba.createRow(numeroLinha);
		linha.createCell(0).setCellValue(compromisso.getRowid());
		linha.createCell(1).setCellValue(compromisso.getNomeFuncionario());
		linha.createCell(2).setCellValue(compromisso.getNomeAgenda());
		linha.createCell(3).setCellValue(compromisso.getDataFormatada());
		linha.createCell(4).setCellValue(compromisso.getHorario());
	}
}
