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
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/CatalogoEstructuraController.js'></script>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'></script>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-core.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-more.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/project.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/catalogo-estructura.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>
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
		<h1>Gestión de la estructura del programa</h1>
	</center>

	<s:form method="get"
		action="%{#request.contextPath}/catalogo-estructura" theme="simple"
		id="frmEstructura" acceptcharset="UTF-8">

		<s:hidden id="hdnIdEstructuraPadreSel" name="idEstructuraPadreSel" />

		<s:url id="caca" value="/images/icons/agregar.png"
			includeContext="true" />
		<s:submit type="image" src="%{caca}" id="btnAgregar"
			cssClass="botonEstructura" title="Agregar Estructura" />

		<sj:submit button="true" value="Agregar subnivel"
			id="btnAgregarSubnivel" cssClass="botonEstructura" />
        <s:url id="editar" value="/images/buttons/editar.png" includeContext="true" />
		<s:submit type="image" src="%{editar}" id="btnEditar"
			cssClass="botonEstructura" cssStyle="width:20px"
			title="Modificar Estructura" />
       <s:url  id="eliminar" value="/images/icons/elim.png" includeContext="true" />
		<s:submit type="image" src="%{eliminar}" id="btnEliminar"
			cssClass="botonEstructura" title="Eliminar Estructura" />

		<div id="contenedorGanttPapiChulo">
			<div style="width: 950px;" id="prjDiv"></div>
		</div>
	</s:form>

</body>
	</html>
</jsp:root>

