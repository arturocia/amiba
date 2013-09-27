<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Revisar reportes de avances de una Acción</title>
<jsp:text>
	<![CDATA[
		 ]]>
</jsp:text>
</head>
<body>
	<h1>Reportes de avances de una meta intermedia</h1>
	<s:form>
		<table>
			<thead>
				<tr>
					<th colspan="4"><div style="text-align: center;">
							Indicadores <br />
						</div>
					</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="model.indicadores" status="estatus">
					<tr>
						<td colspan="4"><div style="text-align: center;">
								<br />
								<hr />
							</div></td>
					</tr>
					<tr>
						<td rowspan="2"><s:property value="#estatus.index+1" /></td>
						<td><div style="text-align: left;">
								<b>Descripción:</b> ${descripcion}
							</div>
						</td>
						<td><div style="text-align: left;">
								<b>Meta:</b> ${meta}
							</div>
						</td>
						<td><div style="text-align: left;">
								<b>Peso:</b> ${peso}
							</div>
						</td>
					</tr>
					<tr>
						<td><div style="text-align: left;">
								<b>Último Reporte:</b>
								<s:date name="fechaUltimoReporte" format="yyyy-MM-dd" />
							</div></td>

						<td><div style="text-align: left;">
								<b>Avance: </b> ${avance}
							</div></td>

						<td><div style="text-align: left;">
								<b>barra avance</b>
							</div></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</s:form>
	<br />

	<s:url id="urlSubirEvidencia"
		value="%{'/subir-evidencia/new?idAccionSel='+idSel+''}"
		includeContext="true" />
	<s:url id="urlCancelar"
		value="/operacion-revisar-avances/%{model.idProyecto}"
		includeContext="true" />

	<s:if test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@COORDINADOR}">
		<sj:a id="btnEditar" button="true" 
			onclick="window.location='%{urlSubirEvidencia}'">Subir evidencia</sj:a>
	</s:if>


	<sj:a button="true" href="#"
		onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>

	<!--<s:url value="%{'/subir-evidencia/new?idAccionSel='+idSel+''}"
			includeContext="true" id="urlSubirEvidencia" />
		<s:a href="%{urlSubirEvidencia}">Subir evidencia</s:a>-->
</body>
	</html>
</jsp:root>
