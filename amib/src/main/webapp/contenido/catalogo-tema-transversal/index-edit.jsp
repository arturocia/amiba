<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">	
	<head>		
	
	<title>Editar tema transversal ${nombre}</title>	
	<jsp:text>
		<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/Gerente.js" type="text/javascript"></script>
		 ]]>
	</jsp:text>
	</head>
	<body>
	<h1>Editar Tema Transversal</h1>
	<s:url id="urlCancelar" value="/catalogo-tema-transversal" includeContext="true" />
	<s:actionerror id="saeTemaTransversal" theme="jquery" />
	<s:fielderror id="sfeTemaTransversal" theme="jquery" />
		
	
	<s:form
		action="%{#request.contextPath}/catalogo-tema-transversal/%{idTema}"
		method="post" theme="simple" acceptcharset="UTF-8">		
		
		<div class="title">Modificar Tema Transversal</div>				
		<s:hidden id="hdnMethod" name="_method" value="put" />
				<table>
				<tr>
			<td><label>Nombre:</label></td>			
			<td><s:textfield id="txtNombre" name="model.nombre" disabled="true" maxlength="254"/></td>
			</tr>
			<tr>			
			<td><label>Descripción:</label></td>			
			<td><s:textarea rows="5" cols="50" id="txtDescripcion" name="model.descripcion" maxlength="499" /></td>
			</tr>
			<tr>							
			<td colspan="2"><sj:submit id="btnAceptar" value="Aceptar" button="true"
			/>
			<sj:a id="btnCancelar" button="true"  href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
			</td>
			</tr>
			</table>
	</s:form>		
	
	</body>
	</html>
</jsp:root>