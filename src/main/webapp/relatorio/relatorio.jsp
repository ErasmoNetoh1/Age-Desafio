<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title><s:text name="label.titulo.pagina.consulta"/></title>
		<link rel='stylesheet' href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
	</head>
	<body class="bg-secondary">
		<div class="container">
			<div class="row mt-5 mb-2">
				<div class="col-sm p-0">
					<s:url action="todosCompromissos" var="compromissos"/>
					<a href="${compromissos}" class="btn btn-success">Compromissos</a>
				</div>
			</div>

			<div class="row">
				<s:form action="/gerarRelatorios.action" cssClass="card card-body mb-3">
					<div class="row align-items-end">
						<div class="col-sm-3">
							<label for="dataInicial" class="form-label"><s:text name="label.data.inicial"/>:</label>
							<input type="date" class="form-control" id="dataInicial" name="filtro.dataInicial" value="<s:property value='filtro.dataInicial'/>">
						</div>
						<div class="col-sm-3">
							<label for="dataFinal" class="form-label"><s:text name="label.data.final"/>:</label>
							<input type="date" class="form-control" id="dataFinal" name="filtro.dataFinal" value="<s:property value='filtro.dataFinal'/>">
						</div>
						<div class="col-sm-3">
							<button class="btn btn-primary"><s:text name="label.gerar"/></button>
							<s:url action="todosRelatorios" var="limpar"/>
							<a href="${limpar}" class="btn btn-secondary">Limpar</a>
							<s:url action="exportarRelatorios" var="exportar">
								<s:param name="filtro.dataInicial" value="filtro.dataInicial"/>
								<s:param name="filtro.dataFinal" value="filtro.dataFinal"/>
							</s:url>
							<a href="${exportar}" class="btn btn-success">Exportar XLSX</a>
						</div>
					</div>
				</s:form>
			</div>

			<div class="row">
				<table class="table table-light table-striped align-middle">
					<thead>
						<tr>
							<th><s:text name="label.id"/></th>
							<th><s:text name="label.funcionario"/></th>
							<th><s:text name="label.agenda"/></th>
							<th><s:text name="label.data"/></th>
							<th><s:text name="label.horario"/></th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="compromissos">
							<tr>
								<td>${rowid}</td>
								<td><s:property value="nomeFuncionario" escapeHtml="true"/></td>
								<td><s:property value="nomeAgenda" escapeHtml="true"/></td>
								<td>${dataFormatada}</td>
								<td>${horario}</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
		</div>
		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
	</body>
</html>
