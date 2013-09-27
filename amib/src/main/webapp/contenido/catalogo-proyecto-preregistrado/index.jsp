<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Gestionar Proyectos Pre-registrados</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-proyecto.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
<table id="tblProyectoPreRegistrado">
		<thead>
			<tr>
				<th colspan="11">Proyectos Pre-Registrados</th>
			</tr>
			<tr>
				<th>Siglas</th>
				<th>Nombre</th>
				<th>Responsable</th>
				<th>Teléfono</th>
				<th>Correo</th>
				<th>Dirección</th>
				<th>Fecha Pre-Registro</th>
				<th>Fecha Registro</th>
				<th>Estado</th>
				<th>Opción</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="list">
				<tr>
					<td>${siglas}</td>
					<td>${nombre}</td>
					<td>${responsable.nombre} ${responsable.apPat} ${responsable.apMat}</td>
					<td>${responsable.contactos[0].contacto}</td>
					<td>${responsable.contactos[1].contacto}</td>
					<td>${responsable.area.nombre}</td>
					<td>${fechaPreRegistro}</td>
					<td>${fechaRegistro}</td>
					<td>${estado.nombre}</td>
					<td><a
						href="${pageContext.request.contextPath}/catalogo-proyecto-preregistrado/${idProyecto}/edit">Aprobar</a>
						<a
						href="${pageContext.request.contextPath}/catalogo-proyecto-preregistrado/${idProyecto}/deleteConfirm">Eliminar</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>

	<s:if test="!listProyectosPreregistradosAnonimos.isEmpty">

		<table id="tblProyecto">
			<thead>
				<tr>
					<th colspan="11">Proyectos Pre-registrados por Ciudadanos</th>
				</tr>
				<tr>
					<th>Siglas</th>
					<th>Nombre</th>
					<th>Resumen</th>
					<th>Responsable</th>
					<th>Objetivo General</th>
					<th>Datos Pre-registro</th>
					<th>Duración</th>
					<th>Inicio</th>
					<th>Fin</th>
					<th>Estado</th>
					<th>Opción</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="listProyectosPreregistradosAnonimos">
					<tr>
						<td>${siglas}</td>
						<td>${nombre}</td>
						<td>${resumen}</td>
						<td>${usuario.nombre}</td>
						<td>${objetivoGeneral}</td>
						<td>${datosPreregistro}</td>
						<td>${periodo.duracion} días</td>
						<td>${periodo.fechaInicio}</td>
						<td>${periodo.fechaFin}</td>
						<td>${estado.nombre}</td>
						<td><a 
 							href="${pageContext.request.contextPath}/catalogo-proyecto-preregistrado/edit">Aprobar</a>
							<a
							href="${pageContext.request.contextPath}/catalogo-proyecto-preregistrado/deleteConfirm">Eliminar</a>
						</td> 
					</tr>


<!-- 							href="${pageContext.request.contextPath}/catalogo-proyecto-preregistrado/${idProyecto}/edit">Aprobar</a>
							<a
							href="${pageContext.request.contextPath}/catalogo-proyecto-preregistrado/${idProyecto}/deleteConfirm">Eliminar</a>
						</td> 
					</tr>  -->
				</s:iterator>
			</tbody>
		</table>
	</s:if>

</body>
</html>
</jsp:root>
