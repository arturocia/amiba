<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Agregar meta intermedia</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
			
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-core.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-more.js" ></script>			
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/catalogo-accion-nuevo.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlCancelar" value="/operacion-planeacion/%{idSel}"
		includeContext="true" />
	<s:actionerror id="saeAccion" theme="jquery" />
	<s:fielderror id="sfeAccion" theme="jquery" />
	<h1>Agregar meta intermedia</h1>
	<s:form action="%{#request.contextPath}/catalogo-accion" method="post"
		theme="simple" acceptcharset="UTF-8">
		<!--<s:hidden id="hdnMethod" name="_method" value="put" />!-->
		<s:hidden name="model.idProyecto" value="%{idSel}" />
		<div class="title">Agregar meta intermedia</div>
		<div class="section">Datos Generales</div>
		<table>
			<tr>
				<td><label>Nombre de la meta intermedia:</label></td>
				<td><s:textfield name="model.nombre" /></td>
			</tr>
			<tr>
				<td><label>Objetivo de la acción:</label></td>
				<td><s:textarea name="model.objetivo" cols="30" rows="10" /></td>
			</tr>
			<tr>
				<td><label>Descripción:</label></td>
				<td><s:textarea name="model.descripcion" cols="30" rows="10" />
				</td>
			</tr>
		</table>
		<div class="section">Definición de Tiempo</div>
		<table border="0">
			<tr>
				<td colspan="2"><s:if
						test="#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@NODO_CON_PERIODO_RESTRICTIVO].periodo.tipoPeriodo eq @mx.ipn.escom.cdt.besp.modelo.Periodo@PERIODO_DEFINIDO">
						<label for="txtPeriodoRestrictivo">El periodo debe estar
							entre <s:property
								value="#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@NODO_CON_PERIODO_RESTRICTIVO].periodo.fechaInicio" />
							y <s:property
								value="#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@NODO_CON_PERIODO_RESTRICTIVO].periodo.fechaFinCalculada" />
						</label>

					</s:if> <s:if
						test="#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@NODO_CON_PERIODO_RESTRICTIVO].periodo.tipoPeriodo eq @mx.ipn.escom.cdt.besp.modelo.Periodo@PERIODO_RELATIVO">
						<label for="txtPeriodoRestrictivo">El periodo relativo
							debe ser menor a <s:property
								value="#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@NODO_CON_PERIODO_RESTRICTIVO].periodo.duracion" />
							días
						</label>

					</s:if></td>
			</tr>
			<tr>
				<td>Periodo</td>
				<td>
					<!-- por duracion <input type="radio" id="duracion"
					name="tipoPeriodo" value="duracion" />  --> <s:if
						test="#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@NODO_CON_PERIODO_RESTRICTIVO].periodo.tipoPeriodo eq @mx.ipn.escom.cdt.besp.modelo.Periodo@PERIODO_DEFINIDO">

						<input type="radio" id="fechas" name="tipoPeriodo" value="fechas" />Por fechas
					</s:if> <input type="radio" id="indefinido" name="tipoPeriodo"
					value="indefinido" />Indeterminado
				</td>
			</tr>
		</table>
		<div id="periodoConDuracion" style="display: none">
			<table>
				<tr>
					<td>Duración</td>
					<td><s:textfield name="model.periodo.duracion"
							id="txtDuracion" size="2" /> <select id="selectMetDuracion"
						cssClass="campoDuracion">
							<option value="day">dias</option>
							<option value="week">semanas</option>
							<option value="month">meses</option>
							<option value="year">años</option>
					</select></td>
				</tr>
				<tr>
					<td>Inicio</td>
					<td><sj:datepicker name="model.periodo.fechaInicio"
							displayFormat="yy-mm-dd" id="dtpFechaInicioDuracion"
							cssClass="campoDuracion" /></td>
				</tr>
			</table>
		</div>
		<br />
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
	</s:form>
</body>
	</html>
</jsp:root>