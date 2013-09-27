<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Planeación de proyecto</title>
<jsp:text>
	<![CDATA[ 
			<link href="${pageContext.request.contextPath}/styles/project.css" media="screen" type="text/css" rel="stylesheet"/>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/OperacionPlaneacionController.js'></script>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'></script>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-core.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-more.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/project.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/operacion-planeacion.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>
	<style type="text/css">
#pageMain,pageHMenu {
	display: block;
}
</style>
	<s:url id="urlAgregarAccion" value="/catalogo-accion/new"
		includeContext="true" />
	<s:url id="urlRegresar" value="/" includeContext="true" />
	<s:url id="urlPresupuesto"
		value="/catalogo-indicador-financiero/%{idSel}?optAccion=planeacion"
		includeContext="true" />
	<s:url id="urlAprobar" value="/enviar-proyecto-aprobacion"
		includeContext="true" />
	<s:hidden id="urlIndicador" value="/catalogo-indicador" />
	<s:actionmessage id="algo" theme="jquery" />
	<s:actionerror theme="jquery" />
	<s:fielderror theme="jquery" />
	<h1>Planeación de proyecto</h1>
	<s:form method="get" action="%{#request.contextPath}/catalogo-accion"
		theme="simple" id="frmEstructura" acceptcharset="UTF-8">
		<s:hidden id="idAccionSel" name="idAccionSel" value="" />
		<s:hidden id="hdnProyecto" name="idSel2" />
		<s:hidden id="hdnProyectoaa" name="idSel" />
		<s:hidden id="hdnIdEstructuraPadreSel" name="idEstructuraPadreSel" />

		<s:submit type="image" src="../images/icons/agregar.png"
			id="btnAgregar" cssClass="botonEstructura"
			title="Agregar meta intermedia" />
		<s:submit type="image" src="../images/buttons/editar.png"
			id="btnEditar" cssClass="botonEstructura" cssStyle="width:20px"
			title="Editar meta intermedia" />
		<s:submit type="image" src="../images/icons/elim.png" id="btnEliminar"
			cssClass="botonEstructura" title="Eliminar  meta intermedia" />
		<s:submit type="image" src="../images/icons/indicadores.jpg"
			id="btnIndicadores" cssClass="botonEstructura" width="20px"
			title="Gestionar indicadores" />
		<!--  	<s:submit type="submit" id="btnIndicadores"
			value="Gestionar Indicadores" cssClass="botonEstructura" />-->


		<div id="contenedorGanttPapiChulo">
			<div style="width: 950px;" id="prjDiv"></div>
		</div>
		<br />
		<br />

		<p align="right">
			<sj:a button="true"
				onclick="window.location='%{urlAprobar}/%{idSel}/edit'">
								Enviar proyecto para aprobación
							</sj:a>
		</p>
	</s:form>

	<s:url id="urlBase" value="/catalogo-indicador-financiero"
		includeContext="true" />
	<s:form method="get"
		action="%{#request.contextPath}/catalogo-indicador-financiero"
		theme="simple" id="frmEstructura" acceptcharset="UTF-8">
		<div class="section">
			Financiamiento
			<blockquote></blockquote>

			<h4>
				Costo total:
				<s:property
					value="getText('$ {0,number,#,##0.00}',{#session.proyecto.costoTotal})" />
			</h4>

		</div>
		<div style="text-align: right;">
			<sj:a button="true" id="btnAgregarFuente"
				onclick="window.location='%{urlBase}/new?idProyecto=%{idSel}'">
			Agregar Fuente
		</sj:a>
		</div>
		<table class="list">
			<thead>
				<tr>
					<th colspan="3"><div style="text-align: center;">Financimiento
						</div></th>
					<th colspan="3"><div style="text-align: center;">Dictamen</div>
					</th>
					<th colspan="2"><div style="text-align: center;">Ejercido</div>
					</th>
				<tr>
					<th>Fuente</th>
					<th>Fecha de Solicitud</th>
					<th>Monto Solicitado</th>
					<th>Fecha</th>
					<th>Resultado</th>
					<th>Monto</th>
					<th>Fecha</th>
					<th>Monto</th>
				</tr>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="list">
					<tr>
						<td><b>${nombreFuente}</b></td>
						<td><s:date name="fechaSolicitado" format="yyyy-MM-dd" /></td>
						<td>${montoSolicitado}</td>
						<td><s:date name="fechaAprobado" format="yyyy-MM-dd" /></td>
						<td></td>
						<td>${montoAprobado}</td>
						<td><s:date name="fecha" format="yyyy-MM-dd" /></td>
						<td>${monto}</td>

					</tr>
					<tr>
						<td rowspan="2" colspan="6"><sj:a button="true"
								onclick="window.location='%{urlBase}/%{idIndicadorFinanciero}/deleteConfirm'">
								Eliminar
							</sj:a></td>
						<td>Ejercido</td>
						<td></td>
					</tr>
					<tr>
						<td>Por erogar</td>
						<td></td>
					</tr>
				</s:iterator>
			</tbody>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</table>

		



	</s:form>
</body>
	</html>
</jsp:root>