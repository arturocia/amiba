<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Gestión de Indicadores Fisicos</title>
<jsp:text>
	<![CDATA[ 
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/catalogo-indicador.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>
	<h1>Gestión de Indicadores Físicos</h1>
	<s:actionerror id="saeArea" theme="jquery" />
	<s:fielderror id="sfeArea" theme="jquery" />
	<s:actionmessage id="algo" theme="jquery" />
	<s:url id="urlNuevo" value="/catalogo-indicador/new?idAccion=%{idAccionSel}&amp;id=1" includeContext="true" />
	<s:url id="urlCancelar" value="/operacion-planeacion/%{idSel2}" includeContext="true" />
	<table id="tblIndicador">
		<thead>
			<tr>
				<th>Descripción</th>
				<th>Meta</th>
				<th>Peso</th>
				<th>Tipo de unidad</th>
				<th>Unidad</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<!--Se crea loop para llenar la tabla obtiene los datos de Area(GestionarAreas 14)-->
			<s:iterator value="list">
				<tr>
					<td>${descripcion}</td>
					<td>${meta}</td>
					<td>${peso}</td>
					<td>${unidad.tipoUnidad.nombre}</td>
					<td>${unidad.nombre}</td>
					<td><a
						href="${pageContext.request.contextPath}/catalogo-indicador/${idIndicador}/edit?idAccion=${idAccion}&amp;idPlan=${idSel2}"><img
							height="20" width="20" src="images/buttons/editar.png" /> </a> <a
						href="${pageContext.request.contextPath}/catalogo-indicador/${idIndicador}/deleteConfirm?idAccion=${idAccion}&amp;idPlan=${idSel2}"><img
							height="20" width="20" src="images/buttons/eliminar.png" /> </a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- <a href="${pageContext.request.contextPath}/catalogo-indicador/new"
		id="lnkNuevoIndicador"><input type="button"
		value="Nuevo Indicador"></input> </a>
	<s:a href="%{urlCancelar}">
		<input type="button" value="Cancelar"></input>
	</s:a>!-->
	<sj:a id="btnNuevo" button="true"  href="#" onclick="location.href='catalogo-indicador/new?idAccion=%{idAccionSel}&amp;idPlan=%{idSel2}'">Nuevo Indicador</sj:a>
	<sj:a id="btnCancelar" button="true"  href="#" onclick="location.href='operacion-planeacion/%{idSel2}'">Cancelar</sj:a>

</body>
	</html>
</jsp:root>