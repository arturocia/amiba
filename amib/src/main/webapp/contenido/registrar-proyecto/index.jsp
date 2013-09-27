<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Registrar Nuevo Proyecto</title>
<jsp:text>
	<![CDATA[ 
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-core.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-more.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/registrar-proyecto.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlRegresar" value="/" includeContext="true" />
	<s:actionmessage id="algo" theme="jquery" />
	<s:actionerror id="saeUsuario" theme="jquery" />
	<s:fielderror id="sfeUsuario" theme="jquery" />
	<h1>Registrar proyecto</h1>
	<s:form action="%{#request.contextPath}/registrar-proyecto"
		method="post" theme="simple" acceptcharset="UTF-8">
	<!-- 	<s:hidden id="hdnMethod" name="_method" value="put" />-->
		<table>
		<s:if
				test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario==
			@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE}">
				<!-- <tr>
					<td>Coordinadores:</td>
					<td><s:select list="listCoordinadores" listValue="nombre"
							listKey="idUsuario" name="idCoordinadorSel"
							headerValue="- - Seleccione una opción - -" headerKey="-1"
							requiered="true" /></td>
				</tr>-->
			</s:if>
			<s:else>
				<tr>
					<td><label>Nombre:</label></td>
					<td><s:textfield id="txtNomResp" name="usuarioActual.nombre"
							disabled="true" /></td>
				</tr>
				<tr>
					<td><label>Apellido Paterno:</label></td>
					<td><s:textfield id="txtApatResp" name="usuarioActual.apPat"
							disabled="true" /></td>
				</tr>
				<tr>
					<td><label>Apellido Materno:</label></td>
					<td><s:textfield id="txtRFCResp" name="usuarioActual.apMat"
							disabled="true" /></td>
				</tr>
				<tr>
					<td><label>Dirección:</label></td>
					<td><s:textfield id="txtAreaResp"
							name="usuarioActual.getArea().getNombre()" disabled="true" /></td>
				</tr>
				<tr>
					<td><label>Cargo:</label></td>
					<td><s:textfield id="txtCargoResp" name="usuarioActual.cargo"
							disabled="true" /></td>
				</tr>
				<tr>
					<td><label>Teléfono:</label></td>
					<td><s:textfield id="txtTelResp" name="tel" disabled="true" />
					</td>
				</tr>
				<tr>
					<td><label>Correo:</label></td>
					<td><s:textfield id="txtACorreoResp" name="mail"
							disabled="true" /></td>
				</tr>
			</s:else>
			<tr>
				<td><label>Nombre del Proyecto:</label></td>
				<td><s:textfield id="txtNomProy" name="model.nombre" /></td>
			</tr>
			<tr>
				<td><label>Siglas:</label></td>
				<td><s:textfield id="txtSiglasProy" name="model.siglas" /></td>
			</tr>
			<tr>
				<td><label>Resumen:</label></td>
				<td><s:textarea rows="5" cols="50" id="txtResProy"
						name="model.resumen" /></td>
			</tr>
			<tr>
				<td><label>Objetivo General:</label></td>
				<td><s:textarea rows="5" cols="50" id="txtObGenProy"
						name="model.objetivoGeneral" /></td>
			</tr>
			<tr>
				<td><label>Costo Total del Proyecto $:</label></td>
				<td><s:textfield id="txtCostoTotProy" name="model.costoTotal" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<div>
						<table border="0">
							<tr>
								<td>Período</td>
							</tr>
							<tr>
								<td><!-- Por duración <input type="radio" id="duracion"
									name="tipoPeriodo" value="duracion" /> -->
									
									
									Por fechas<input
									type="radio" id="fechas" name="tipoPeriodo" value="fechas" />


									Indefinido <input type="radio" id="indefinido"
									name="tipoPeriodo" value="indefinido" />
								</td>
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
											displayFormat="yy-mm-dd" id="dtpFechaFin"
											cssClass="campoFechas" readonly="true" /></td>
								</tr>

							</table>
						</div>
					</div> <s:hidden id="banderaTipoPeriodo" name="banderaTipoPeriodo" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><sj:submit id="btnAceptar" value="Aceptar"
						button="true" /> <!--<sj:a id="btnRegresar" button="true" href="#"
						onclick="history.go(-1)">Cancelar	</sj:a>--></td>
			</tr>
		</table>
	</s:form>
</body>
	</html>
</jsp:root>