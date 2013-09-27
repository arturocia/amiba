<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags"
	xmlns:sjt="/struts-jquery-tree-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Editar datos de proyecto</title>
<jsp:text>
	<![CDATA[
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/OperacionEditarDatosProyectoController.js'></script>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'></script>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-core.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-more.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/struts/utils.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/struts/js/jstree/jquery.jstree.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/editar-datos-proyecto.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlCancelar"
		value="/operacion-editar-datos-proyecto/%{model.id}"
		includeContext="true" />
	<s:actionmessage id="algo" theme="jquery" />
	<s:actionerror theme="jquery" />
	<s:fielderror theme="jquery" />
	<s:form theme="simple" acceptcharset="UTF-8"
		action="%{#request.contextPath}/operacion-editar-datos-proyecto/%{model.id}"
		id="frmProyecto" method="post" onsubmit="onsumbit()">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<s:hidden id="idEstructuraUnoAux" name="idEstructuraUnoAux"/>
		<s:hidden id="idEstructuraDosAux" name="idEstructuraDosAux"/>
		<h1>Edición de datos del proyecto</h1>
		<center>
			<table border="0">
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
							name="usuarioActual.area.nombre" disabled="true" /></td>
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

				<tr>
					<td><label>Nombre del proyecto:</label></td>
					<td><s:textfield id="txtNomProy" name="model.nombre" /></td>
				</tr>

				<tr>
					<td><label>Siglas:</label></td>
					<td><s:textfield id="txtSiglasProy" name="model.siglas" /></td>
				</tr>

				<tr>
					<td colspan="2"><br /> <label>Resumen:</label></td>
				</tr>

				<br>
				<td colspan="2"><s:textarea rows="5" cols="50" id="txtResProy"
						name="model.resumen" /></td>
				</br>

				<tr>
					<td colspan="2"><label>Objetivo General:</label></td>
				</tr>

				<tr>
					<td colspan="2"><s:textarea rows="5" cols="40"
							id="txtObGenProy" name="model.objetivoGeneral" /></td>
				</tr>


			</table>

		</center>
		<center>
			<div>
				<table border="0">
					<tr>
						<td>Periodo</td>
					</tr>
					<tr>

						<!-- <td>Por duración <input type="radio" id="rdoDuracion"
							name="banderaTipoPeriodo" value="0" /> --> <td>Por fechas <input
							type="radio" id="rdoFecha" name="banderaTipoPeriodo" value="1" />
							Indefinido <input type="radio" id="rdoIndefinido"
							name="banderaTipoPeriodo" value="2" /></td>

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
							<td>Inició</td>
							<td><sj:datepicker name="model.periodo.fechaInicio"
									displayFormat="yy-mm-dd" id="dtpFechaInicio"
									cssClass="campoFechas" /></td>


						</tr>
						<tr>
							<td>Fin</td>
							<td><sj:datepicker name="model.periodo.fechaFin"
									displayFormat="yy-mm-dd" id="dtpFechaFin"
									cssClass="campoFechas" /></td>
						</tr>

					</table>
				</div>
			</div>
			<s:hidden value="%{banderaTipoPeriodo}" id="hdnBanderaTipoPeriodo" />
		</center>

		<div>
			<!-- TODO Alinear verticalmente los arboles -->
			<div id="periodoRestrictivo">
				<p id="restrictivo"></p>
			</div>
			<table border="1">
				<THEAD>
					<TR>
						<TH>Alinear con el programa sectorial <s:property
								value="#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROGRAMA_SECTORIAL].nombre" />
							<s:checkbox name="alineacionSectorial"
								id="chkbxAlineacionSectorial" cssClass="checkAlineacion" /></TH>
						<TH>Alinear con otro programa <s:checkbox name="alineacion"
								id="chkbxAlineacion" cssClass="checkAlineacion" /></TH>
					</TR>
				</THEAD>

				<TBODY>
					<tr>
						<TD><s:url id="urlTraerEstructurasSectorial"
								value="/operacion-editar-datos-proyecto!traerEstructurasSectorial"
								includeContext="true" /> <sjt:tree
								id="treEstructurasProgramaSectorial"
								href="%{urlTraerEstructurasSectorial}"
								onClickTopics="elementoClickeadoSectorial"
								onCompleteTopics="caca" /></TD>


						<TD>
						<s:hidden id="idBanderaAux" name="banderaAux"></s:hidden>
						<s:hidden id="idPeriNull" name="periNull"></s:hidden>
						<s:if test="%{banderaAux eq 1}">
							<sj:div href="otrasEstructuras" deferredLoading="true"
									listenTopics="caca"></sj:div>
						</s:if>
						<s:else>
								
								<s:url id="urlTraerEstructuras"
								value="/operacion-editar-datos-proyecto!traerEstructura"
								includeContext="true" /> <sjt:tree
								id="treEstructurasProgramaOtro" href="%{urlTraerEstructuras}"
								onClickTopics="elementoClickeado" onCompleteTopics="caca1" />
								
						</s:else>
						
						
						
								</TD>


					</tr>
				</TBODY>
			</table>
		</div>

		<center>
			<div id="divEjes">
				<s:optiontransferselect id="slcIdEjesTematicosDisponibles"
					doubleId="slcIdEjesTematicosSeleccionados" doubleMultiple="true"
					label="Ejes Tematicos" name="idEjesTematicosDisponibles"
					leftTitle="Ejes Temáticos disponibles"
					rightTitle="Ejes Temáticos Asociados"
					list="ejeTematicosDisponibles" listKey="idEje" listValue="nombre"
					multiple="true" doubleList="ejeTematicosSelecccionados"
					doubleName="idEjesTematicosSeleccionados" doubleListKey="idEje"
					doubleListValue="nombre" />
			</div>
			<div id="divTemas">
				<s:optiontransferselect id="slcIdTemasTransversalesDisponibles"
					doubleId="slcIdTemasTransversalesSeleccionados"
					label="Temas Transversales" name="idTemasTransversalesDisponibles"
					leftTitle="Temas Transversales Disponibles"
					rightTitle="Temas Transversales Asociados"
					list="temaTransversalDisponibles" listKey="idTema"
					listValue="nombre" multiple="true"
					doubleList="temaTransversalSeleccionados	"
					doubleName="idTemasTransversalesSeleccionados"
					doubleListKey="idTema" doubleListValue="nombre" />
			</div>
		</center>

		<s:hidden id="hdnIdEstructuraSectorial"
			name="idEstructuraSectorialSel" />
		<s:hidden id="hdnIdEstructura" name="idEstructuraSel" />
		

		<s:hidden id="perf1" name="periodoAux.fechaInicio"></s:hidden>
		<s:hidden id="perf2" name="periodoAux.fechaFin"></s:hidden>

		<sj:submit id="btnAceptar" value="Aceptar" button="true" />
		<sj:a id="btnCancelar" button="true"
			onclick="window.location='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
</body>
	</html>
</jsp:root>