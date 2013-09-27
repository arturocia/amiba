<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Proyectos Disponibles</title>
<jsp:text>
	<![CDATA[ 
				<script src="${pageContext.request.contextPath}/scripts/proyectos-disponibles.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
<s:url id="urlAgregar" value="/catalogo-usuario/new" includeContext="true" />
<h1>Proyectos Disponibles</h1>
	<s:actionmessage id="algo" theme="jquery" />
	<center>
	<table id="tblProyectos">
		<thead>
			<tr>				
				<th>Nombre</th>
				<th>Siglas</th>				
				
			</tr>
		</thead>
		<tbody>
			<s:iterator
					value="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTOS_ASOCIADOS]}"
					var="proyecto">
					<tr><td style="width: 400px;">
						<s:url
							value="%{'/operacion-editar-datos-proyecto/'+#proyecto.id+''}"
							includeContext="true" id="%{'urlEditarProyecto'}" />
						<s:a href="%{urlEditarProyecto}">
							<s:property value="#proyecto.nombre" />
						</s:a>
					</td>
					<td>
					<s:property value="#proyecto.siglas" />
					</td>					
					</tr>
				</s:iterator>
		</tbody>
		<label id="selium"></label>
	</table>
	</center>
	
</body>
	</html>
</jsp:root>