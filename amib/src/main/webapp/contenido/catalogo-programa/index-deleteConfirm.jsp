<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Eliminar Programa ${nombre}</title>
	<jsp:text>
		<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/Gerente.js" type="text/javascript"></script>
		 ]]>
	</jsp:text>
</head>
<body>
<h1>Eliminar Programa</h1>
	<s:url id="urlCancelar" value="/catalogo-programa"
		includeContext="true" />
	<s:actionerror id="saePrograma" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/catalogo-programa/%{idPrograma}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<form>
			<div class="title">Eliminar Programa</div>
			<div class="section">Datos del Programa</div>
			<table>


				<s:hidden id="hdnMethod" name="_method" value="delete" />
				<tr>
					<td><label>Siglas:</label>
					</td>
					<td><s:textfield id="txtsiglas" name="model.siglas"
							disabled="true" /></td>
				</tr>
				<tr>
					<td><label>Nombre del Programa:</label>
					</td>
					<td><s:textfield id="txtNombre" name="model.nombre"
							disabled="true" /></td>
				</tr>
				<tr>
					<td><label>Descripción:</label>
					</td>
					<td><s:textarea id="txtResumen" name="model.resumen" cols="30"
							rows="10" disabled="true" />
					</td>
				</tr>
				<tr>
					<td><label>Duración del Periodo:</label>
					</td>
					<td><s:textfield id="txtDuracion"
							name="model.periodo.duracion" disabled="true" /> días
					</td>
				</tr>
				<tr>
					<td><label>Fecha de Inicio:</label>
					</td>
					<td><s:textfield id="txtDuracion"
							name="model.periodo.fechaInicio" disabled="true" />
					</td>
				</tr>
				<tr>
					<td><label>Fecha de Fin:</label>
					</td>
					<td><s:textfield id="txtDuracion"
							name="model.periodo.fechaFin" disabled="true" />					
					</td>
				</tr>
			</table>
			<sj:submit id="btnAceptar" value="Aceptar" button="true"
			/>
			<sj:a id="btnCancelar" button="true"  href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
		</form>
	</s:form>
</body>
	</html>
</jsp:root>