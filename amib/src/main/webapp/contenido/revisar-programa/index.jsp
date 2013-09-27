<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />

	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Indice de estructura</title>
<jsp:text>
	<![CDATA[ 
			<link href="${pageContext.request.contextPath}/styles/project.css" media="screen" type="text/css" rel="stylesheet"/>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/RevisarProgramaController.js'></script>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'></script>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-core.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-more.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/project.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/revisar-programa.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlGestionProgramas" value="/catalogo-programa"
		includeContext="true" />
	<s:url id="urlGestion" value="/catalogo-area" includeContext="true" />

	<style type="text/css">
#pageMain,pageHMenu {
	display: block;
}
</style>
	<DIV>
		<center>
			<ul id="ulErrores">
				<s:actionerror id="saeArea" theme="jquery" />
			</ul>
		</center>
	</DIV>
	<center>
		<h1>Revisar Programa</h1>
	</center>


	<s:if
		test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE}">
		<sj:a id="btnGestionProgramas" button="true" href="#"
			onclick="location.href='%{urlGestionProgramas}'">Gestión de Programas	</sj:a>

	</s:if>
	<!--<sj:a id="btnRevisarProyecto" button="true" onclick="valida()">Revisar Proyecto</sj:a>-->
	<br />


	<s:hidden id="revision" name="rev" />

	<s:form method="get"
		action="%{#request.contextPath}/catalogo-estructura" theme="simple"
		id="frmEstructura" acceptcharset="UTF-8">
		<table id="tblProyecto">
			<thead>
				<tr>
					<th>Proyecto</th>
					<th></th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="listaProyectos ">
					<tr>
						<td><s:property value="nombre" /></td>
							<td></td>
						<s:url id="urlAprobar"
							value="/aprobar-proyecto/%{idProyecto}/edit"
							includeContext="true" />
						<td><s:a href="%{urlAprobar}">Aprobar/Rechazar</s:a></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</s:form>
</body>
	</html>
</jsp:root>