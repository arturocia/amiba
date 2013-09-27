<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Consultar Proyectos</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/consultar-proyectos.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
	<h1>Consultar proyecto</h1>

	<s:form action="%{#request.contextPath}/consultar-proyectos!buscar"
		id="frmproyecto" method="post" theme="simple" acceptcharset="UTF-8">
		<table>
			<tr>
				<s:if test="%{#session.usuario.idPerfilUsuario != 0}">
					<th>Programa</th>
				</s:if>
				<th>Eje temático</th>
				<th>Tema transversal</th>
				<s:if test="%{#session.usuario.idPerfilUsuario != 0}">
					<th>Dirección</th>
				</s:if>
				<!-- <th rowspan="2"><sj:submit id="btnBuscar" value="Buscar"
						button="true" formIds="frmproyecto" /></th> -->
			</tr>
			<tr>
				<s:if test="%{#session.usuario.idPerfilUsuario != 0}">
					<td><s:select list="listProgramas" listValue="nombre"
							listKey="idPrograma" name="idProgramas"
							headerValue="- - Seleccione una opción - -" headerKey="-1"
							requiered="true" multiple="true" size="3" cssStyle="width:270px;" />
					</td>
				</s:if>
				<td><s:select list="listEjes" listValue="nombre"
						listKey="idEje" name="idEjes"
						headerValue="- - Seleccione una opción - -" headerKey="-1"
						requiered="true" multiple="true" size="3" />
				</td>
				<td><s:select list="listTemas" listValue="nombre"
						listKey="idTema" name="idTemas"
						headerValue="- - Seleccione una opción - -" headerKey="-1"
						requiered="true" multiple="true" size="3" />
				</td>
				<s:if test="%{#session.usuario.idPerfilUsuario != 0}">
					<td><s:select list="listAreas" listValue="nombre"
							listKey="idArea" name="idAreas"
							headerValue="- - Seleccione una opción - -" headerKey="-1"
							requiered="true" multiple="true" size="3" />
					</td>
				</s:if>
			</tr>
		</table>
	</s:form>
<br/>
	<sj:submit id="btnBuscar" value="Buscar" button="true"
		formIds="frmproyecto" />

	<table id="tblProyectos">
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Resumen</th>
				<th>Reportes</th>
				<s:if test="%{#session.usuario.idPerfilUsuario != 0}">
					<th>% de avance</th>
					<th>Monto erogado</th>
				</s:if>
				<th>Revisar proyecto</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="listProyectos" var="evidencia">
				<tr>
					<td>${nombre}</td>
					<td>${resumen}</td>
					<td></td>
					<s:if test="%{#session.usuario.idPerfilUsuario != 0}">
						<td><s:property value="%{#evidencia.proyecto.avance}" /></td>
						<td><s:property value="%{#evidencia.presupuestoErogado}" />
						</td>
					</s:if>
					<td>
						<!-- <a
						href="${pageContext.request.contextPath}/operacion-editar-datos-proyecto/${idProyecto}">Revisar</a>  -->
						<a
						href="${pageContext.request.contextPath}/operacion-editar-datos-proyecto/${idProyecto}"><img
							height="20" width="20" src="images/buttons/ver.png" /> </a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</body>
	</html>
</jsp:root>