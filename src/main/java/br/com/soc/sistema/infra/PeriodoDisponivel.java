package br.com.soc.sistema.infra;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import br.com.soc.sistema.exception.BusinessException;

public enum PeriodoDisponivel {
	
	MANHA("1","Manhã"),
	TARDE("2","Tarde"),
	AMBOS("3","Ambos");
	
	private String codigo;
	private String descricao;
	private static final Map<String, PeriodoDisponivel> opcoes = new HashMap<>();
	
	static {
		Arrays.asList(PeriodoDisponivel.values())
			.forEach(opcao -> opcoes.put(opcao.getCodigo(), opcao));
	}
	
	PeriodoDisponivel(String codigo, String descricao){
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static PeriodoDisponivel buscarPor(String codigo) {
		if (codigo == null) {
			throw new IllegalArgumentException("Informe um codigo válido");
		}
		
		return Optional.ofNullable(opcoes.get(codigo))
				.orElseThrow(() -> new BusinessException("Código informado não existe"));
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	

}
