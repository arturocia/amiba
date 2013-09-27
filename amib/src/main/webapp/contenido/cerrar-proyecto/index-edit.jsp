<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Cerrar Proyecto</title>
</head>
<body>
	<h1>Cerrar proyecto</h1>
	<s:url id="urlCancelar" value="/operacion-revisar-avances/%{idSel}"
		includeContext="true" />
	<s:actionerror id="saeProyecto" theme="jquery" />
	<s:fielderror id="sfeProyecto" theme="jquery" />
	<s:form
		action="%{#request.contextPath}/cerrar-proyecto/%{idSel}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<table>
			<tr>
				<td><label>Siglas:</label>
				</td>
				<td><s:property value="model.siglas" />
				</td>
			</tr>
			<tr>
				<td><label>Nombre:</label>
				</td>
				<td><s:property value="model.nombre" />
				</td>
			</tr>
			<tr>
				<td><label>Resumen:</label></td>
				<td><s:property value="model.resumen" /></td>
			</tr>
			<tr>
				<td><label>Responsable:</label></td>
				<td><s:property
						value="model.responsable.nombre + model.responsable.apPat + model.responsable.apMat" />
				</td>
			</tr>
			<tr>
				<td><label>Objetivo General:</label>
				</td>
				<td><s:property value="model.objetivoGeneral" />
				</td>
			</tr>
			<tr>
				<td><label>Datos Pre-registro:</label>
				</td>
				<td><s:property value="model.datosPreregistro" />
				</td>
			</tr>
			<tr>
				<td><label>Duración:</label>
				</td>
				<td><s:property value="model.periodo.duracion" />
				</td>
			</tr>
			<tr>
				<td><label>Inicio:</label>
				</td>
				<td><s:property value="model.periodo.fechaInicio" />
				</td>
			</tr>
			<tr>
				<td><label>Fin:</label>
				</td>
				<td><s:property value="model.periodo.fechaFin" />
				</td>
			</tr>
			<tr>
				<td><label>Estado:</label></td>
				<td><s:property value="model.estado.nombre" /></td>
			</tr>
		
		</table>
		
	
		<s:iterator value="acciones">
			<table>
				<tr>
					<td colspan="4" aling="left"><h4>${nombre}</h4></td>
					<table>
					<tr>Indicadores</tr>
						<th>Descripción-</th>
						<th>-Peso-</th>
						<th>-Meta-</th>
						<th>-Avance</th>
						<th>-Avance</th>
						<s:iterator value="indicadores">
								<tr>
									<td>${descripción}</td>
									<td>${peso}</td>
									<td>${meta}</td>
									<td>${avance}</td>
									<td>${(avance/meta)*100}%</td>
								</tr>
						</s:iterator>
					</table>
				</tr>
			</table>
		</s:iterator>
			<!--> validacion de economias     <-->
			<s:if test="%{#session.Sobrante}">
			<label>(Activado)Existen economias/no hubo economias.</label>
						<s:checkbox name="checkMe" fieldValue="true" label="Check Me for testing"/>
					</s:if>
			<table>
			<tr>
			<td colspan="2"><sj:submit id="btnAceptar" value="Aceptar"
						button="true"/> <sj:a id="btnCancelar"
						button="true"   href="#"
				onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
			</td>
			</tr>	
			</table>
		
	</s:form>
</body>
	</html>
</jsp:root>