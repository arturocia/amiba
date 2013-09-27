<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Indice de Programa1n</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-programa.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/Gerente.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlAgregarPrograma" value="/catalogo-programa/new"
		includeContext="true" />
	<s:actionmessage id="algo" theme="jquery" />

	<h1>Gestión de Programa</h1>
	<table id="tblPrograma">
		<thead>
			<tr>
				<th>Nombre del Programa</th>
				<th>Siglas</th>
				<th>Descripción</th>
				<th>Duración</th>
				<th>Fecha Inicio</th>
				<th>Fecha Fin</th>
				<th>Sectorial</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="list">
				<tr>
					<td>${nombre}</td>
					<td>${siglas}</td>
					<td>${resumen}</td>
					<td>${unidadMedidaDuracion}</td>
					<td><s:date name="periodo.fechaInicio" format="yyyy-MM-dd" />
					</td>
					<td><s:date name="periodo.fechaFin" format="yyyy-MM-dd" /></td>
					<td><s:if test="%{sectorial}">Si</s:if></td>
					<td><a
						href="${pageContext.request.contextPath}/catalogo-programa/${idPrograma}/edit"><img
							height="20" width="20" src="images/buttons/editar.png" /> </a> <a
						href="${pageContext.request.contextPath}/catalogo-programa/${idPrograma}/deleteConfirm"><img
							height="20" width="20" src="images/buttons/eliminar.png" /> </a> <a
						href="${pageContext.request.contextPath}/catalogo-nivel?idProgramaSel=${idPrograma}">Niveles
					</a> <a
						href="${pageContext.request.contextPath}/catalogo-estructura?idProgramaSel=${idPrograma}">Estructuras
					</a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>

	<s:if
		test="%{(#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE and #session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].programas.isEmpty) 
			or #session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@ADMINISTRADOR}">
		<sj:a id="btnAgregar" button="true" href="javascript:void(0)"
			onclick="location.href='%{urlAgregarPrograma}'">Nuevo Programa</sj:a>
	</s:if>
	<br />
	<br />



</body>
	</html>
</jsp:root>