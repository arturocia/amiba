<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Aprobación de proyecto</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/aprobar-proyecto.js" type="text/javascript"></script>
		]]>
</jsp:text>
</head>
<body>
	<s:url id="urlCancelar" value="/enviar-proyecto-aprobacion/"
		includeContext="true" />
	<s:actionerror id="saeProyecto" theme="jquery" />
	<s:fielderror id="sfeProyecto" theme="jquery" />
	<s:form action="%{#request.contextPath}/aprobar-proyecto/%{idSel}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<div class="title">Aprobación de proyecto</div>
		<div class="section">Datos del proyecto</div>
		<table>
			<tr>
				<td><label>Siglas:</label></td>
				<td><s:property value="model.siglas" /></td>
			</tr>
			<tr>
				<td><label>Nombre:</label></td>
				<td><s:property value="model.nombre" /></td>
			</tr>
			<tr>
				<td><label>Resumen:</label></td>
				<td><s:property value="model.resumen" /></td>
			</tr>
			<tr>
				<td><label>Responsable:</label></td>
				<td><s:property
						value="model.responsable.nombre + ' '+ model.responsable.apPat + ' ' + model.responsable.apMat" />
				</td>
			</tr>
			<tr>
				<td><label>Objetivo General:</label></td>
				<td><s:property value="model.objetivoGeneral" /></td>
			</tr>
			<tr>
				<td><label>Duración:</label></td>
				<td><s:property value="model.periodo.duracion" /></td>
			</tr>
			<tr>
				<td><label>Inicio:</label></td>
				<td><s:property value="model.periodo.fechaInicio" /></td>
			</tr>
			<tr>
				<td><label>Fin:</label></td>
				<td><s:property value="model.periodo.fechaFin" /></td>
			</tr>
			<tr>
				<td><label>Estado:</label></td>
				<td><s:property value="model.estado.nombre" /></td>
			</tr>
			<s:if
				test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idEstado  == @mx.ipn.escom.cdt.besp.modelo.Estado@REVISION}">
				<tr>
					<td colspan="2">
						<div class="section">Resolución de aprobación</div>
					</td>
				</tr>
				<tr>
					<td colspan="2"><label>El proyecto se pasa a:</label>
						<div align="center" style="text-align: center;">
							<input type="radio" id="edoEjecucion" name="model.idEstado"
								value="4" onclick="opcion('aprobado');" /> <label>Ejecución</label>
							<input type="radio" id="edoRechazado" name="model.idEstado"
								value="2" onclick="opcion('rechazado');" /> <label>Rechazado</label>
						</div></td>
				</tr>
			</s:if>

			<tr>
				<td colspan="2">
					<div id="observacion" style="display: none;">
						<label>Ingrese las observaciones por las cuales el
							proyecto ha sido rechazado:</label>
						<s:textarea name="observacion"
							cssStyle="width:100%; margin-bottom:10px; height:100px;"></s:textarea>
					</div>
				</td>
			</tr>
			<s:if
				test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idEstado  == @mx.ipn.escom.cdt.besp.modelo.Estado@REVISION}">
				<tr>
					<td colspan="2"><sj:submit id="btnAceptar" value="Aceptar"
							button="true" cssStyle="float:right;" /></td>
				</tr>
			</s:if>

		</table>
	</s:form>
</body>
	</html>
</jsp:root>