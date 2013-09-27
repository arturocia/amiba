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
		    
		    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/styles/jsgantt.css">
            <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jsgantt.js"></script>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/CatalogoEstructuraController.js'></script>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'></script>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'></script>
			<script type='text/javascript' src='${pageContext.request.contextPath}/scripts/json2.js'></script>
			<!--script type="text/javascript" src="${pageContext.request.contextPath}/scripts/catalogo-estructura.js" ></script-->
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/catalogo-estructura-forma.js" ></script>
			<script src="${pageContext.request.contextPath}/scripts/Director.js" type="text/javascript"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.feedback.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlCancelar" value="/catalogo-estructura"
		includeContext="true" />
	<DIV>
		<center>
			<ul id="ulErrores">
			</ul>
		</center>
	</DIV>
	<center>
		<h1>Estructura</h1>


		<s:form method="post"
			action="%{#request.contextPath}/catalogo-estructura/%{idPadre}" theme="simple"
			id="frmEstructura">

			
			<s:hidden name="struts.enableJSONValidation" value="true"
				id="hdnValidarJSON" />
				
			<input type="text" style="display:none;" id="idPadreEstructura" value="${pageContext.request.contextPath}" />
			
			<s:hidden id="hdnMethod" name="_method" />
			<s:hidden name="model.idPrograma" id="hdnIdPrograma" />
			<s:hidden name="model.idNivel" id="hdnIdNivel" />
			<s:hidden name="model.idPadre" id="hdnIdPadre" />

			<div>
				<table border="0">
					<tr>
						<td>Nivel</td>
						<td><s:textfield name="model.Nivel.nombre" id="txtNivel"
								disabled="true" />
						</td>
					</tr>
					<tr>
						<td>Nombre</td>
						<td><s:textfield name="model.nombre" id="txtNombre"
								 cssClass="campoEditableEstructura" disabled="true" /></td>
					</tr>
					<tr>
						<td>Descripción</td>
						<td><s:textfield name="model.descripcion" id="txtDescripcion"
								 cssClass="campoEditableEstructura" disabled="true" /></td>
					</tr>
					<tr>
						<td>Inicio</td>
						<td><sj:datepicker name="model.periodo.fechaInicio"
								displayFormat="yy-mm-dd" id="dtpFechaInicio" 
								cssClass="campoEditableEstructura" disabled="true" />
						</td>
					
					   <br></br>
					</tr>
					<tr>
						<td>Fin</td>
						<td><sj:datepicker name="model.periodo.fechaFin"
								displayFormat="yy-mm-dd" id="dtpFechaFin" 
								cssClass="campoEditableEstructura" disabled="true" />
						</td>
					</tr>
					
				</table>
			</div>

			<br />
            <sj:a id="btnCancelar" button="true"  href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
		</s:form>
	</center>
</body>
	</html>
</jsp:root>
