<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<!--Es llamado por struts (GestionarAreas 14)-->
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>Indice de Eje Tematico</title>
	<jsp:text>
		<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-eje-tematico.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/Gerente.js" type="text/javascript"></script>

		 ]]>
	</jsp:text>
	</head>
	<body>
	<s:url id="urlNuevoEje" value="/catalogo-eje-tematico/new" includeContext="true" />
	<h1>Gestionar Eje Temático</h1>
	<s:actionmessage id="algo" theme="jquery"/>
	
	<table id="tblEjetematico"  class="list">
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Descripción</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="list">
				<tr>
					<td>${nombre}</td>
					<td>${descripcion}</td>
					<td width="60"><a
						href="${pageContext.request.contextPath}/catalogo-eje-tematico/${idEje}/edit"><img height="20" width="20" src="images/buttons/editar.png"/></a>
					<a
						href="${pageContext.request.contextPath}/catalogo-eje-tematico/${idEje}/deleteConfirm"><img height="20" width="20" src="images/buttons/eliminar.png"/></a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!--a href="${pageContext.request.contextPath}/catalogo-eje-tematico/new"
		id="lnkNuevoEjeTematico"><input type="submit" value="Nuevo Eje Temático"></input></a-->
	<sj:a id="btnNuevoEje" button="true"  href="#" onclick="location.href='%{urlNuevoEje}'">Nuevo Eje Temático	</sj:a>
	
	</body>
	</html>
</jsp:root>
