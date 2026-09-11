<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF8">
		<title><s:text name="label.titulo.pagina.cadastro"/></title>
		<link rel='stylesheet' href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
	</head>
	<body class="bg-secondary">
		<div class="container">
			<s:form action="/salvarAgendas.action">
				<div class="card mt-5">
					<div class="card-header">
						<div class="row">
							<div class="col-sm-5">
								<s:url action="todosAgendas" var="todas"/>
								<a href="${todas}" class="btn btn-success">Agendas</a>
							</div>
							<div class="col-sm">
								<h5 class="card-title"><s:text name="label.titulo.pagina.cadastro"/></h5>
							</div>
						</div>
					</div>

					<div class="card-body">
						<div class="row align-items-center">
							<label for="id" class="col-sm-2 col-form-label text-center">Codigo:</label>
							<div class="col-sm-2">
								<s:textfield cssClass="form-control" id="id" name="agendaVo.rowid" readonly="true"/>
							</div>
						</div>

						<div class="row align-items-center mt-3">
							<label for="nome" class="col-sm-2 col-form-label text-center">Nome:</label>
							<div class="col-sm-5">
								<s:textfield cssClass="form-control" id="nome" name="agendaVo.nome"/>
							</div>
						</div>

						<div class="row align-items-center mt-3">
							<label for="periodo" class="col-sm-2 col-form-label text-center">
								<s:text name="label.periodo.disponivel"/>:
							</label>
							<div class="col-sm-5">
								<s:select cssClass="form-select" id="periodo" name="agendaVo.periodoDisponivel"
									list="listaPeriodosDisponiveis" listKey="name()" listValue="descricao"
									headerKey="" headerValue="Escolha..."/>
							</div>
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
