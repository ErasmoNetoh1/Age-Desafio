<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title><s:text name="label.titulo.pagina.cadastro"/></title>
		<link rel='stylesheet' href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
	</head>
	<body class="bg-secondary">
		<div class="container">
			<s:form action="/salvarCompromissos.action">
				<div class="card mt-5">
					<div class="card-header">
						<div class="row">
							<div class="col-sm-5">
								<s:url action="todosCompromissos" var="todos"/>
								<a href="${todos}" class="btn btn-success">Compromissos</a>
							</div>
							<div class="col-sm"><h5 class="card-title"><s:text name="label.titulo.pagina.cadastro"/></h5></div>
						</div>
					</div>

					<div class="card-body">
						<div class="row align-items-center">
							<label for="id" class="col-sm-2 col-form-label text-center">Codigo:</label>
							<div class="col-sm-2"><s:textfield cssClass="form-control" id="id" name="compromissoVo.rowid" readonly="true"/></div>
						</div>

						<div class="row align-items-center mt-3">
							<label for="funcionario" class="col-sm-2 col-form-label text-center"><s:text name="label.funcionario"/>:</label>
							<div class="col-sm-5">
								<s:select cssClass="form-select" id="funcionario" name="compromissoVo.codigoFuncionario" list="funcionarios" listKey="rowid" listValue="nome" headerKey="" headerValue="Escolha..."/>
							</div>
						</div>

						<div class="row align-items-center mt-3">
							<label for="agenda" class="col-sm-2 col-form-label text-center"><s:text name="label.agenda"/>:</label>
							<div class="col-sm-5">
								<s:select cssClass="form-select" id="agenda" name="compromissoVo.codigoAgenda" list="agendas" listKey="rowid" listValue="nome" headerKey="" headerValue="Escolha..."/>
							</div>
						</div>

						<div class="row align-items-center mt-3">
							<label for="data" class="col-sm-2 col-form-label text-center"><s:text name="label.data"/>:</label>
							<div class="col-sm-3"><input type="date" class="form-control" id="data" name="compromissoVo.data" value="<s:property value='compromissoVo.data'/>"></div>
						</div>

						<div class="row align-items-center mt-3">
							<label for="horario" class="col-sm-2 col-form-label text-center"><s:text name="label.horario"/>:</label>
							<div class="col-sm-3"><input type="time" class="form-control" id="horario" name="compromissoVo.horario" value="<s:property value='compromissoVo.horario'/>"></div>
						</div>
					</div>

					<div class="card-footer">
						<div class="form-row">
							<button class="btn btn-primary col-sm-4 offset-sm-1">Salvar</button>
							<button type="reset" class="btn btn-secondary col-sm-4 offset-sm-2">Limpar Formulario</button>
						</div>
					</div>
				</div>
			</s:form>
		</div>
		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
	</body>
</html>
