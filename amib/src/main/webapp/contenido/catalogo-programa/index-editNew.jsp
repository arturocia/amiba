<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Nuevo Programa</title>
<jsp:text>
	<![CDATA[ 
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-core.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-more.js" ></script>
			<script src="${pageContext.request.contextPath}/scripts/mensaje-ingles.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/Gerente.js" type="text/javascript"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/catalogo-programa-nuevo.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>
	<h1>Agregar Programa</h1>
	<s:url id="urlCancelar" value="/catalogo-programa"
		includeContext="true" />
	<s:actionerror id="saePrograma" theme="jquery" />
	<s:fielderror id="sfePrograma" theme="jquery" />

	<s:form action="%{#request.contextPath}/catalogo-programa"
		method="post" theme="simple" acceptcharset="UTF-8" id="frmPrograma">

		<form>
			<div class="title">Nuevo Programa</div>
			<div class="section">Datos del Programa</div>


			<table>
				<tr>
					<td><label>Siglas:</label></td>
					<td><s:textfield id="txtsiglas" name="model.siglas"
							maxlength="10" /></td>
				</tr>
				<tr>
					<td><label>Nombre del Programa:</label></td>
					<td><s:textfield id="txtNombre" name="model.nombre"
							maxlength="70" /></td>
				</tr>
				<tr>

					<td colspan="2"><label>Descripción:</label> <br /> <s:textarea
							id="txtResumen" name="model.resumen" cols="70" rows="5"
							maxlength="255" /></td>
				</tr>
				<tr>

					<td colspan="2"><label>Responsable:</label> <s:property
							value="#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].nombre" />
						<s:hidden
							value="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idUsuario}"
							name="model.idUsuario" /></td>
				</tr>
			</table>


			<table border="0">
				<tr>
					<td>Período</td>
				</tr>
				<tr>
					<td><!-- <input type="radio" id="duracion" name="tipoPeriodo" value="duracion" />Por duración-->
						<input	type="radio" id="fechas" name="tipoPeriodo" value="fechas" /> Por fechas
						<input type="radio" id="indefinido" name="tipoPeriodo"	value="indefinido" />Indefinido </td>
				</tr>
			</table>
			<div id="periodoConDuracion" style="display: none">
				<table>
					<tr>
						<td>Duración</td>
						<td><s:textfield name="model.periodo.duracion"
								id="txtDuracion" size="5" cssClass="campoDuracion" /> <select
							id="comboTipoPeriodo">
								<option value="day">dias</option>
								<option value="week">semanas</option>
								<option value="month">meses</option>
								<option value="year">años</option>
						</select></td>
					</tr>

				</table>
			</div>
			<div id="periodoSinDuracion" style="display: none">
				<table>
					<tr>
						<td>Inicio</td>
						<td><sj:datepicker name="model.periodo.fechaInicio"
								displayFormat="yy-mm-dd" id="dtpFechaInicio"
								cssClass="campoFechas" readonly="true" /></td>
					</tr>
					<tr>
						<td>Fin</td>
						<td><sj:datepicker name="model.periodo.fechaFin"
								displayFormat="yy-mm-dd" id="dtpFechaFin" cssClass="campoFechas"
								readonly="true" /></td>
					</tr>
				</table>
			</div>
			<s:hidden id="banderaTipoPeriodo" name="banderaTipoPeriodo" />
			<sj:submit id="btnAceptar" value="Aceptar" button="true" />
			<sj:a id="btnCancelar" button="true" href="#"
				onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
		</form>
	</s:form>

</body>
	</html>
</jsp:root>