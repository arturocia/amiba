<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Editar nivel ${nombre}</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/Director.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
<h1>Editar Nivel</h1>
	<s:url id="urlCancelar" value="/catalogo-nivel" includeContext="true" />
	<s:actionerror id="saeNivel" theme="jquery" />
	<s:fielderror id="sfeNivel" theme="jquery" />


	<s:form action="%{#request.contextPath}/catalogo-nivel/%{idNivel}"
		method="post" theme="simple" acceptcharset="UTF-8">

		<div class="title">Modificar Nivel</div>
		<s:hidden id="hdnMethod" name="_method" value="put" />

		<label>Programa:</label>
		<s:textfield id="txtNombre" name="programaSel.nombre" disabled="true" />
		<br />
		<label>Posición:</label>
		<s:textfield id="txtPosicion" name="model.posicion" disabled="true" />
		<br />
		<label>Nombre:</label>
		<s:textfield id="txtNombre" name="model.nombre" maxlength="249" />
		<br />
		<label>Descripción:</label>
		<s:textfield id="txtDescripcion" name="model.descripcion"
			maxlength="499" />
		<br />
		<!-- <label>Programa</label>
			<s:select list="programas" listValue="nombre"
				listKey="idPrograma" name="model.idPrograma"/>-->
		<sj:submit id="btnAceptar" value="Aceptar" button="true"
		/>
		<sj:a id="btnCancelar" button="true" 
			href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>

</body>
	</html>
</jsp:root>