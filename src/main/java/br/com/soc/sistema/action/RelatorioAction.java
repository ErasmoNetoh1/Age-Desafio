package br.com.soc.sistema.action;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import br.com.soc.sistema.business.CompromissoBusiness;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.filter.RelatorioFilter;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.relatorio.RelatorioXlsx;
import br.com.soc.sistema.vo.CompromissoVo;

public class RelatorioAction extends Action {

	private List<CompromissoVo> compromissos = new ArrayList<>();
	private CompromissoBusiness business = new CompromissoBusiness();
	private RelatorioFilter filtro = new RelatorioFilter();
	private RelatorioXlsx relatorioXlsx = new RelatorioXlsx();

	public String todos() {
		return SUCCESS;
	}

	public String gerar() {
		compromissos.addAll(business.buscarCompromissosPorPeriodo(filtro));
		return SUCCESS;
	}

	public String exportar() {
		try {
			byte[] arquivo = relatorioXlsx.gerar(business.buscarCompromissosPorPeriodo(filtro));
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=relatorio-compromissos.xlsx");
			response.setContentLength(arquivo.length);
			response.getOutputStream().write(arquivo);
			response.flushBuffer();
			return NONE;
		} catch (IOException e) {
			throw new BusinessException("Nao foi possivel gerar o arquivo XLSX");
		}
	}

	public List<CompromissoVo> getCompromissos() {
		return compromissos;
	}

	public RelatorioFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(RelatorioFilter filtro) {
		this.filtro = filtro;
	}
}
