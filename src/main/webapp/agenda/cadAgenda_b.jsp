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
					<s:url action="todosFuncionarios" var="funcionarios"/>
					<a href="${funcionarios}" class="btn btn-success">Funcionarios</a>
					<s:url action="todosCompromissos" var="compromissos"/>
					<a href="${compromissos}" class="btn btn-info text-white">Compromissos</a>
				</div>
			</div>

			<div class="row">
				<table class="table table-light table-striped align-middle">
					<thead>
						<tr>
							<th><s:text name="label.id"/></th>
							<th><s:text name="label.nome"/></th>
							<th><s:text name="label.periodo.disponivel"/></th>
							<th class="text-end"><s:text name="label.acao"/></th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="agendas">
							<tr>
								<td>${rowid}</td>
								<td><s:property value="nome" escapeHtml="true"/></td>
								<td>${periodoDisponivel.descricao}</td>
								<td class="text-end">
									<s:url action="editarAgendas" var="editar">
										<s:param name="agendaVo.rowid" value="rowid"/>
									</s:url>
									<s:url action="excluirAgendas" var="excluirAgenda">
										<s:param name="agendaVo.rowid" value="rowid"/>
									</s:url>

									<a href="${editar}" class="btn btn-warning text-white">Editar</a>
									<a href="#" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#confirmarExclusao" data-url="${excluirAgenda}">Excluir</a>
								</td>
							</tr>
						</s:iterator>
					</tbody>
					<tfoot class="table-secondary">
						<tr>
							<td colspan="4">
								<s:url action="novoAgendas" var="novo"/>
								<a href="${novo}" class="btn btn-success">Novo</a>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>

		<div class="modal fade" id="confirmarExclusao" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title"><s:text name="label.modal.titulo"/></h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<span><s:text name="label.modal.corpo"/></span>
					</div>
					<div class="modal-footer">
						<a class="btn btn-secondary" data-bs-dismiss="modal">Nao</a>
						<a id="excluir" class="btn btn-primary" style="width: 75px;" href="#">Sim</a>
					</div>
				</div>
			</div>
		</div>

		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
		<script>
			const modalExclusao = document.getElementById("confirmarExclusao");

			modalExclusao.addEventListener("show.bs.modal", function(event) {
				const botaoExcluir = event.relatedTarget;
				document.getElementById("excluir").href = botaoExcluir.getAttribute("data-url");
			});
		</script>
	</body>
</html>
