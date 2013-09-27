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
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-core.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-more.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/catalogo-estructura-nuevo.js" ></script> 
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/catalogo-estructura-edita.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>
	<h1>Editar Estructura</h1>
	<s:url id="urlCancelar" value="/catalogo-estructura"
		includeContext="true" />
	<s:actionerror id="saeArea" theme="jquery" />
	<s:fielderror id="sfeArea" theme="jquery" />
	<center>
		<!-- <h1>Estructura</h1>  -->


		<s:form method="post"
			action="%{#request.contextPath}/catalogo-estructura/%{idEstructura}"
			theme="simple" id="frmEstructura">


			<s:hidden id="hdnMethod" name="_method" value="put" />

			<s:property value="%{ruta}" />


			<div>
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
									días </label>

							</s:if></td>
					</tr>
					<tr>
						<td>Nombre de <s:property
								value="#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@NIVEL_ESTRUCTURA].nombre" />
						</td>
						<td><s:textfield name="model.nombre" id="txtNombre"
								cssClass="campoEditableEstructura" maxlength="200" />
						</td>
					</tr>
					<tr>
						<td>Descripción de <s:property
								value="#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@NIVEL_ESTRUCTURA].nombre" />
						</td>
						<td><s:textfield name="model.descripcion" id="txtDescripcion"
								cssClass="campoEditableEstructura" maxlength="400" />
						</td>
					</tr>

					<tr>
						<td>Periodo</td>
						<td>
						
							<!-- <input type="radio" id="rdoDuracion"	name="banderaTipoPeriodo" value="0" /> Por duracion -->
							
							<s:if test="%{banderaTipoPeriodoPadre eq 2}">	
								<input type="radio" id="rdoFecha" name="banderaTipoPeriodo" value="1" />Por fechas 
							</s:if>
							<input type="radio" id="rdoIndefinido" name="banderaTipoPeriodo" value="2" />Indefinido 
						</td>
					</tr>
				</table>

				<div id="periodoConDuracion" style="display: none">
					<table>
						<tr>
							<td>Duración</td>
							<td><s:textfield name="model.periodo.duracion"
									id="txtDuracion" size="5" cssClass="campoDuracion"
									maxlength="5" /> <select id="comboTipoPeriodo">
									<option value="day">dias</option>
									<option value="week">semanas</option>
									<option value="month">meses</option>
									<option value="year">años</option>
							</select>
							</td>
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
									cssClass="campoFechas" readonly="true"/></td>


						</tr>
						<tr>
							<td>Fin</td>
							<td><sj:datepicker name="model.periodo.fechaFin"
									displayFormat="yy-mm-dd" id="dtpFechaFin"
									cssClass="campoFechas" readonly="true"/></td>
						</tr>

					</table>
				</div>
			</div>
			<s:hidden value="%{banderaTipoPeriodo}" id="hdnBanderaTipoPeriodo" />

			<br />

			<sj:a id="btnAceptar" button="true" 
				href="#" onclick="capturaSubmit();">Aceptar</sj:a>

			<sj:a button="true"  href="#"
				onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>


		</s:form>
	</center>
</body>
	</html>
</jsp:root>

