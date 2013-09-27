<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags"  xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Géstionar Presupuestos</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-indicador-financiero.js" type="text/javascript"></script>
		 ]]>
</jsp:text>

</head>
<body>
	<s:url id="urlBase" value="/catalogo-indicador-financiero"
		includeContext="true" />
	<s:form method="get"
		action="%{#request.contextPath}/catalogo-indicador-financiero" theme="simple"
		id="frmEstructura" acceptcharset="UTF-8">
	<div class="section">Financiamiento</div>
	<s:actionmessage id="algo" theme="jquery"/>
	<s:if test="optAccion eq 'planeacion'">
	<div style="text-align:right;">
		<sj:a button="true" id="btnAgregar" 
			onclick="window.location='%{urlBase}/new?idProyecto=%{idSel}'">
			Agregar Fuente
		</sj:a>
	</div>
	</s:if>
		<table class="list">
			<thead>
				<tr>
					<th colspan="3"><div style="text-align:center;">Financimiento</div></th>
					<th colspan="3"><div style="text-align:center;">Dictamen</div></th>
					<th colspan="2"><div style="text-align:center;">Ejercido</div></th>
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
					<td rowspan="2" colspan="6">
						<s:if test="optAccion eq 'planeacion'">
							<sj:a button="true"
								onclick="window.location='%{urlBase}/%{idIndicadorFinanciero}/deleteConfirm'">
								Eliminar
							</sj:a>
						</s:if>
						<s:if test="optAccion eq 'avances'">
							<sj:a button="true"
								onclick="window.location='%{urlBase}/%{idIndicadorFinanciero}/aprobar'">
								Aprobar presupuesto
							</sj:a>
							<sj:a button="true"
								onclick="window.location='%{urlBase}/%{idIndicadorFinanciero}/ejercicio'">
								Registrar ejercicio
							</sj:a>
						</s:if>
					</td>
					<td>Ejercido</td>
					<td></td>
				</tr>
				<tr>
					<td>Por erogar</td>
					<td></td>
				</tr>
				</s:iterator>
			</tbody>
		</table>
	</s:form>
</body>
	</html>
</jsp:root>
