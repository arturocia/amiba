<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>		
	
	<title>Indice de tema transversal</title>		
	<jsp:text>
		<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/catalogo-tema-transversal.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/Gerente.js" type="text/javascript"></script>
		 ]]>
	</jsp:text>
	</head>
	<body>	
	<s:url id="urlNuevoTema" value="/catalogo-tema-transversal/new" includeContext="true" />
	<h1>Gestionar Tema Transversal</h1>
	<s:actionmessage id="algo" theme="jquery"/>
	<table id="tblTemaTransversal">	
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
					<td><a
						href="${pageContext.request.contextPath}/catalogo-tema-transversal/${idTema}/edit"><img height="20" width="20" src="images/buttons/editar.png"/></a>
					<a
						href="${pageContext.request.contextPath}/catalogo-tema-transversal/${idTema}/deleteConfirm"><img height="20" width="20" src="images/buttons/eliminar.png"/></a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
		
	<!--a href="${pageContext.request.contextPath}/catalogo-tema-transversal/new"
			id="lnkNuevoTemaTransversal"><input type="submit" value="Nuevo Tema Transversal"></input> </a-->
	<sj:a id="btnNuevoTema" button="true"  href="#" onclick="location.href='%{urlNuevoTema}'">Nuevo Tema Transversal	</sj:a>
		</body>
	</html>
</jsp:root>
