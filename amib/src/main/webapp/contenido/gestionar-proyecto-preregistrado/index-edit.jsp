<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Aprobar proyectos</title>
<jsp:text>
	<![CDATA[ 
					<script src="${pageContext.request.contextPath}/scripts/catalogo-proyecto.js" type="text/javascript"></script>
					<script src="${pageContext.request.contextPath}/scripts/gestionar-proyecto-preregistrado.js" type="text/javascript"></script>
				 ]]>
</jsp:text>
</head>

<body>
	<s:actionmessage />
	<h1>Editar Proyecto Preregistrado</h1>
	<s:actionerror id="saeGesProyecto" theme="jquery" />
	<s:fielderror id="sfeGesProyecto" theme="jquery" />
	<s:url id="urlCancelar" value="/gestionar-proyecto-preregistrado"
		includeContext="true" />
	<s:hidden id="hdnRedireccionado" value="%{redireccionado}" />
	<s:form
		action="%{#request.contextPath}/gestionar-proyecto-preregistrado/%{idProyecto}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<s:actionmessage />
		<div>

			Formulario responsable<br /> <input type="radio" name="tipoRegistro"
				id="buttonCrearCuentaDeUsuario" value="1" checked="checked" /> <label
				for="tipoRegistro">Crear cuenta de usuario</label> <br />
			<div id="opcionCrearusuario">
				<label>Responsable:</label> <label>Nombre</label>
				<s:textfield id="nombResponsable" name="model.responsable.nombre"
					disabled="true" />
				<br /> <label>Apellido Paterno</label>
				<s:textfield id="apPatResponsable" name="model.responsable.apPat"
					disabled="true" />
				<br /> <label>Apellido Materno</label>
				<s:textfield id="apMatResponsable" name="model.responsable.apMat"
					disabled="true" />
				<br /> <label>Dirección</label>
				<s:textfield id="areaResponsable"
					name="model.responsable.area.nombre" disabled="true" />
				<!-- areaResp -->
				<br /> <label>RFC</label>
				<s:textfield id="rfcResponsable" name="model.responsable.rfc"
					disabled="true" />
				<br /> <label>Telefono</label>
				<s:textfield id="telResponsable"
					name="model.responsable.contactos[1].contacto" disabled="true" />
				<br /> <label>Correo</label>
				<s:textfield id="correo	Responsable"
					name="model.responsable.contactos[0].contacto" disabled="true" />
				<br />
				<s:if test="%{!redireccionado}">
					<a id="linkAgregar"
						href="${pageContext.request.contextPath}/catalogo-usuario!editNewPreregistro">Agregar</a>
				</s:if>

			</div>
			<br />
			<s:if test="%{!redireccionado}">
				<input type="radio" name="tipoRegistro" id="buttonUsuarioRegistrado"
					value="2" />
				<label for="tipoRegistro" id="etiquetaButtonUsuarioReg">Utilizar
					cuenta de usuario existente</label>

				<br />
			</s:if>

			<div id="opcionUsuarioExistente">
				<select id="listaUsuariosRegistrados" onchange="habilita()"
					name="idUsuarioSel">
					<s:iterator value="listUsuarios">
						<option value="${idUsuario}">${nombre} ${apPat} ${apMat}</option>
					</s:iterator>
				</select>
			</div>

			<!-- <div id="opcionUsuarioExistente">
				<table id="listaUsuarioRegistrados">
					<thead>
						<tr>
							<th>Seleccion</th>
							<th>Nombre</th>
							<th>Apellido Paterno</th>
							<th>Apellido Materno</th>
							<th>RFC</th>
							<th>Dirección</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="listUsuarios">
							<tr>
								<td><input type="radio" name="idUsuarioSel"
									id="buttonCrearCuentaDeUsuario" value="${idUsuario}" /></td>
								<td>${nombre}</td>
								<td>${apPat}</td>
								<td>${apMat}</td>
								<td>${rfc}</td>
								<td>${area.nombre}</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>-->
		</div>
		<div>
			<label>Siglas:</label>
			<s:textfield id="txtSiglas" name="model.siglas" disabled="true" />
			<br /> <label>Nombre:</label>
			<s:textfield id="txtNombre" name="model.nombre" disabled="true" />
			<br /> <label>Resumen:</label>
			<s:textarea id="txResumen" name="model.resumen" cols="40" rows="10" />
			<br /> <label>Objetivo General:</label>
			<s:textarea id="txResumen" name="model.objetivoGeneral" cols="40"
				rows="10" />
			<br />
		</div>
		<div>
			Datos periodo<br /> <label>Duración:</label>
			<s:textfield id="txtDuracion" name="model.periodo.duracion"
				disabled="true" />
			<br /> <label>Inicia:</label>
			<s:textfield id="txtFechaInicio" name="model.periodo.fechaInicio"
				disabled="true" />
			<br /> <label>Termina:</label>
			<s:textfield id="txtFechaFin" name="model.periodo.fechaFin"
				disabled="true" />
			<br />
		</div>
		<div>
			Resultados<BR /> El proyecto ha sido:<br /> <input
				id="opcionAceptado" type="radio" name="model.idEstado" value="2"
				disabled="true" /> <label for="eleccion">Aceptado</label><br /> <input
				id="opcionRechazado" type="radio" name="model.idEstado" value="3" />
			<label for="eleccion">Rechazado</label><br />
		</div>
		<!-- <div style="display:none;">
			Observaciones<BR />
			<s:textarea cols="40" rows="10" />
		</div>-->
		<div>
			Observaciones<BR />
			<s:textarea cols="40" rows="10" name="cadena" maxlenght="250"/>
		</div>
		<sj:submit id="btnAceptar" value="Enviar" button="true"
			disabled="true" />
		<sj:a id="btnCancelar" button="true" 
			href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
</body>
	</html>
</jsp:root>