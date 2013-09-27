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

#msjCerrado {
	background-color: #F0E547;
	margin-right: 20px;
	margin-left: 20px;
	border-radius: 5px;
}
</style>

	<s:url id="urlCerrar" value="/cerrar-proyecto" includeContext="true" />

	<s:url id="urlAgregarAccion" value="/catalogo-accion/new"
		includeContext="true" />
	<!--<s:url id="urlRegresar" value="/" includeContext="true" />-->
	<!--<s:url id="urlCancelar" value="/login" includeContext="true" />-->
	<s:url id="urlCancelar" value="/aprobar-proyecto/%{idSel}/edit"
		includeContext="true" />

	<s:url id="urlPresupuesto"
		value="/catalogo-indicador-financiero/%{idSel}?optAccion=avances"
		includeContext="true" />

	<s:hidden id="urlIndicador" value="/revisar-reportes-avance-proyecto" />
	<s:hidden id="urlReportarAvance" value="/reportar-avance-proyecto" />

	<s:actionmessage id="algo" theme="jquery" />
	<s:actionerror theme="jquery" />
	<s:fielderror theme="jquery" />
	<h1>Avances de Proyecto</h1>

	<s:form method="get"
		action="%{#request.contextPath}/revisar-reportes-avances-proyecto"
		theme="simple" id="frmEstructura" acceptcharset="UTF-8">

		<s:hidden id="hdnIdAccionSel" name="idAccionSel" />
		<s:hidden id="hdnProyecto" name="idSel" />
		<s:hidden id="hdnIdEstructuraPadreSel" name="idEstructuraPadreSel" />
		<s:hidden id="hdnAccionReportarAvance"
			name="%{#request.contextPath}/revisar-reportes-avances-proyecto/" />

		<s:if
			test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idEstado  eq @mx.ipn.escom.cdt.besp.modelo.Estado@CERRADO}">
			<div id="msjCerrado">
				<H3>Proyecto cerrado</H3>
			</div>
		</s:if>
		<s:elseif
			test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idEstado  eq @mx.ipn.escom.cdt.besp.modelo.Estado@EJECUCION and #session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@COORDINADOR or #session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE}">
			<sj:submit button="true" type="submit" id="btnAcciones"
				value="Revisar reportes de avances" cssClass="botonEstructura" />

			<sj:submit button="true" type="submit" id="btnReportarAvance"
				value="Reportar avance" cssClass="botonEstructura" />
		</s:elseif>

		<s:if
			test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario eq
		@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@COORDINADOR and #session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idEstado == @mx.ipn.escom.cdt.besp.modelo.Estado@EJECUCION}">
			<sj:a button="true"
				onclick="window.location='%{urlCerrar}/%{idSel}/edit'">
								Cerrar proyecto
							</sj:a>

		</s:if>

		<div id="contenedorGanttPapiChulo">
			<div style="width: 950px;" id="prjDiv"></div>
		</div>
	</s:form>
	<!-- <iframe border="0" frameborder="0" framespacing="0"
				width="100%" height="550px;" src="${urlPresupuesto}"> Su
				navegador no soporta esta funcion </iframe>!-->
	<center>
		<s:url id="urlBase" value="/aprobar-presupuesto" includeContext="true" />
		<s:url id="urlBase2" value="/catalogo-avance-financiero"
			includeContext="true" />
		<s:form method="get"
			action="%{#request.contextPath}/catalogo-indicador-financiero"
			theme="simple" id="frmEstructura" acceptcharset="UTF-8">
			<div class="section">
				Financiamiento
				<blockquote></blockquote>
				<h4>Costo total: <s:property
					value="getText('$ {0,number,#,##0.00}',{proyecto.costoTotal})" /></h4>

			</div>
			<table class="list">
				<thead>
					<tr>
						<th colspan="3">
							<div style="text-align: center;">Financimiento</div>
						</th>
						<th colspan="3">
							<div style="text-align: center;">Dictamen</div>
						</th>
						<th colspan="2">
							<div style="text-align: center;">Ejercido</div>
						</th>
					<tr>
						<th>Fuente</th>
						<th>Fecha de Solicitud</th>
						<th>Monto Solicitado</th>
						<th>Fecha</th>
						<th>Resultado</th>
						<th>Monto</th>
						<th>Fecha</th>Secretaría
						<th>Monto ($)</th>
					</tr>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="list" var="presupuesto">
						<tr>
							<td><b>${nombreFuente}</b></td>
							<td><s:date name="fechaSolicitado" format="yyyy-MM-dd" /></td>
							<td>$ ${montoSolicitado}</td>
							<td><s:date name="fechaAprobado" format="yyyy-MM-dd" /></td>
							<td></td>
							<td>$ ${montoAprobado}</td>
							<td><s:date name="fecha" format="yyyy-MM-dd" /></td>
							<td>${monto}</td>
						</tr>
						<tr>

							<td rowspan="2" colspan="6"><s:if
									test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idEstado != @mx.ipn.escom.cdt.besp.modelo.Estado@CERRADO and #session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@COORDINADOR or #session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE}">
									<s:if test="%{!#presupuesto.montoAprobado}">
										<sj:a button="true"
											onclick="window.location='%{urlBase}/%{idIndicadorFinanciero}/edit'">
								Dictaminar presupuesto
							</sj:a>
									</s:if>
									<s:if test="%{#presupuesto.montoAprobado}">
										<sj:a button="true"
											onclick="window.location='%{urlBase2}/new?idIndicadorFinancieroSel=%{idIndicadorFinanciero}'">						
								Registrar ejercicio
							</sj:a>
									</s:if>

								</s:if></td>

							<td>
								<table>
									<thead>
										<tr>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<s:iterator value="avancesFinancieros" var="avanceFinanciero">
											<tr>
												<td><s:date name="%{#avanceFinanciero.fechaAvance}"
														format="yyyy-MM-dd" /></td>
											</tr>
										</s:iterator>
									</tbody>
								</table> Total:</td>

							<td>
								<table>
									<thead>
										<tr>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<s:iterator value="avancesFinancieros" var="avanceFinanciero">
											<tr>
												<td><s:property value="%{#avanceFinanciero.monto}" />
												</td>
											</tr>
										</s:iterator>
									</tbody>
								</table> <s:property value="%{montoErogado}" /></td>
						</tr>
						<tr>
							<td>Por erogar</td>
							<td><s:property value="montoPorErogar" /></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>

			<div>
				<center>
					<table>
						<tr>
							<td>Total Ejercido:</td>
						</tr>
						<tr>
							<td><s:property value="%{totalEjercido}" />%</td>
						</tr>
					</table>
				</center>
				<div>
					Avance Total Ponderado:

					<s:property value="%{porcentajePonderado}" />
					%

				</div>
				<center>
					<table class="list">
						<thead>
							<tr>
								<th colspan="3">Restricciones</th>
							</tr>
							<tr>
								<th colspan="1">
									<div style="text-align: center;">Turnadas</div>
								</th>
								<th colspan="1">
									<div style="text-align: center;">Pendientes</div>
								</th>
								<th colspan="1">
									<div style="text-align: center;">Atendidas</div>
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th colspan="1"><s:property
										value="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].restriccionesTurnadas}" />
								</th>
								<th colspan="1"><s:property
										value="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].restriccionesNoAtendidas}" />
								</th>
								<th colspan="1"><s:property
										value="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].restriccionesAtendidas}" />
								</th>
							</tr>
						</tbody>
					</table>
				</center>
			</div>

		</s:form>
	</center>

</body>
	</html>
</jsp:root>