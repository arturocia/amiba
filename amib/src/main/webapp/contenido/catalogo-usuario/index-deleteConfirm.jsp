<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Eliminar Usuario</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/eliminar-desactivar_usuario.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlCancelar" value="/catalogo-usuario" includeContext="true" />
	<h1>Eliminar Usuario</h1>
	<input type="text" style="display: none;" id="hdnRutaContexto"
		value="${pageContext.request.contextPath}" />
	<s:fielderror theme="jquery" />
	<s:actionerror id="saeUsuario" theme="jquery" />
	<s:form action="%{#request.contextPath}/catalogo-usuario/%{idUsuario}"
		method="post" theme="simple" acceptcharset="UTF-8" id="frmUsuario">
		<s:hidden id="hdnMethod" name="_method" value="delete" />

		<table>
			<tr>
				<td><label>Nombre:</label>
				</td>
				<td><s:textfield id="txtNombre" name="model.nombre"
						disabled="true" />
				</td>
			</tr>
			<tr>
				<td><label>Login:</label>
				</td>
				<td><s:textfield id="txtLogin" name="model.login"
						disabled="true" />
				</td>
			</tr>
			<tr>
				<td><label>Apellido Paterno:</label>
				</td>
				<td><s:textfield id="txtApPat" name="model.apPat"
						disabled="true" />
				</td>
			</tr>
			<tr>
				<td><label>Apellido Materno:</label>
				</td>
				<td><s:textfield id="txtApMat" name="model.apMat"
						disabled="true" />
				</td>
			</tr>
			<tr>
				<td><label>RFC:</label>
				</td>
				<td><s:textfield id="txtRFC" name="model.rfc" disabled="true" />
				</td>
			</tr>
			<tr>
				<td><label>Activado: </label>
				</td>
				<td><s:checkbox id="chkbxActivado" name="model.activado"
						fieldValue="true" disabled="true" />
				</td>
			</tr>
		</table>

		<br />
		<s:hidden id="user" value="%{idUsuario}" />
		<s:hidden id="hdnConTrabajo" value="%{conTrabajo}" />

		<sj:dialog id="mydialog" title="Usuario asociado" modal="true"
			autoOpen="false"
			buttons="{ 
    		'Aceptar':function() { botonOk(); },
    		'Cancelar':function() { botonCancelar(); } 
    		}">
			<p style="color: white;">El usuario seleccionado tiene proyectos
				asociados, no es posible eliminar. ¿Desea desactivar el usuario?</p>
		</sj:dialog>
		<br />
		<sj:submit id="btnAceptar" value="Aceptar" button="true" />
		<sj:a id="btnCancelar" button="true"  
			href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
</body>
	</html>
</jsp:root>