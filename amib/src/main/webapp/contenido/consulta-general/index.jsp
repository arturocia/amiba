<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Consulta general</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/consulta-general.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlCancelar" value="/login.jsp" includeContext="true" />
	<h1>Consulta general</h1>
<s:actionerror id="saeConsulta" theme="jquery" />
	<s:fielderror id="sfeConsulta" theme="jquery" />
	<s:form action="%{#request.contextPath}/consulta-general!buscar"
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
				<th rowspan="2"><sj:submit id="btnBuscar" value="Buscar"
						button="true" formIds="frmproyecto" /></th>
			</tr>
			<tr>
				<s:if test="%{#session.usuario.idPerfilUsuario != 0}">
					<td><s:select id="idProgramass" list="listProgramas" listValue="nombre"
							listKey="idPrograma" name="idProgramas"
							headerValue="- - Seleccione una opción - -" headerKey="-1"
							requiered="true" multiple="true" size="3" cssStyle="width:270px;" onchange="des4();"/>
					</td>
				</s:if>
				<td><s:select id="idEjes" list="listEjes" listValue="nombre"
						listKey="idEje"  name="idEjes"
						headerValue="- - Seleccione una opción - -" headerKey="-1"
						requiered="true" multiple="true" size="3" onchange="des1();" />
				</td>
				<td>
				<s:select id="idTemas" list="listTemas" listValue="nombre"
						listKey="idTema" name="idTemas"
						headerValue="- - Seleccione una opción - -" headerKey="-1"
						requiered="true" multiple="true" size="3" onchange="des2();"/>
				</td>
				<s:if test="%{#session.usuario.idPerfilUsuario != 0}">
					<td><s:select id="idAreas" list="listAreas" listValue="nombre"
							listKey="idArea" name="idAreas"
							headerValue="- - Seleccione una opción - -" headerKey="-1"
							requiered="true" multiple="true" size="3" onchange="des3();"/>
					</td>
				</s:if>
			</tr>
		</table>
		<br />
		<br />
		<br />
		<table id="tblProyecto">
			<thead>
				<tr>
				    <th>Titulo del estudio</th>
					<th>Tema relacionado</th>
					<th>Archivo</th>
					<th>Tipo de archivo</th>
					<s:if test="%{#session.usuario.idPerfilUsuario != 0}">
						<th>% de avance</th>
						<th>Monto erogado</th>
					</s:if>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="listEvidencias" var="evidencia" status="contador">
					<tr>
					    <td>${nombre}</td>
						<td>${proyecto.nombre}</td>
						
						<td><s:url id="archivoDescarga"
								action="consulta-general!obtenerEvidencia">
								<s:param name="idEvidenciaSel" value="#evidencia.idEvidencia" />
							</s:url> <s:a href="%{archivoDescarga}">
								<img height="30" width="30" title="Descargar archivo"
									src="images/buttons/botBajarVerde.png" />
							</s:a></td>
						<td><s:if test="%{#evidencia.estudio == true}">
						Archivo de estudio
						<s:else>
							Archivo normal
						</s:else>
							</s:if></td>
						<s:if test="%{#session.usuario.idPerfilUsuario != 0}">
							<td><s:property value="%{#evidencia.proyecto.avance}" />
							</td>
							<td><s:property value="%{#evidencia.presupuestoErogado}" />
							</td>
						</s:if>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<br />

	</s:form>
	<center>
		<sj:a id="btnCancelar" button="true" href="#"
			onclick="location.href='%{urlCancelar}'">Salir</sj:a>
	</center>

</body>
	</html>
</jsp:root>