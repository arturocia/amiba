<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>Vizualizar Coordinador</title>
	<jsp:text>
		<![CDATA[ 
		<script src="${pageContext.request.contextPath}/scripts/catalogo-contacto.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
		 ]]>
	</jsp:text>
	</head>
	<body>
	<h1>Visualizar Coordinador</h1>
	<s:url id="urlRegresar" value="/administrar-coordinador" includeContext="true" />
	<s:actionerror id="saeUsuario" theme="jquery" />
	<s:fielderror id="sfeUsuario" theme="jquery" />
	<s:form action="%{#request.contextPath}/administrar-coordinador/%{idUsuario}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<label>Login:</label>
		<b><s:label id="txtLogin" name="model.login" disabled="true" /></b>
		<br />

		<br />
		<label>Activado: </label>
		<s:checkbox id="chkbxActivado" name="model.activado" fieldValue="true"
			disabled="true" />
		<br />
		<br />
		<label>Nombre:</label>
		<b><s:label id="txtNombre" name="model.nombre" disabled="true" /></b>
		<br />
		<label>Apellido Paterno:</label>
		<b><s:label id="txtApPat" name="model.apPat" disabled="true" /></b>
		<br />
		<label>Apellido Materno:</label>
		<b><s:label id="txtApMat" name="model.apMat" disabled="true" /></b>
		<br />
		<label>RFC:</label>
		<b><s:label id="txtRFC" name="model.rfc" disabled="true" /></b>
		<br />
		<label>Perfil de Usuario:</label>
		<b><s:label id="txtPerfilUsuario"
			name="model.perfilUsuario.nombre" disabled="true" /></b>
		<br />
		<label>Dirección:</label>
		<b><s:label id="txtArea" name="model.area.nombre" disabled="true" /></b>
		<br />
		<label>Cargo:</label>
		<b><s:label id="txtCargo" name="model.cargo" disabled="true" /></b>
		<br />
		<label>Calle:</label>
		<b><s:label id="txtCalle" name="model.direccion.calle"
			disabled="true" /></b>
		<br />
		<label>Número:</label>
		<b><s:label id="txtNumero" name="model.direccion.direccion"
			disabled="true" /></b>
		<br />
		<label>Colonia:</label>
		<b><s:label id="txtColonia" name="model.direccion.colonia"
			disabled="true" /></b>
		<br />
		<label>Código Postal:</label>
		<b><s:label id="txtCp" name="model.direccion.cp" disabled="true" /></b>
		<br />
		<label>Delegación:</label>
		<b><s:label id="txtDeleg" name="model.direccion.deleg"
			disabled="true" /></b>
		<br />
		<label>Estado:</label>
		<b><s:label id="txtEdo" name="model.direccion.edo" disabled="true" /></b>
		<br />
		<label>País:</label>
		<b><s:label id="txtPais" name="model.direccion.pais"
			disabled="true" /></b>
		<br />
		
		<br />
	<table id="tblContacto">
		<thead>
			<tr>
				<th>Tipo</th>
				<th>Contacto</th>
				<th>Descripcion</th>				
			</tr>
		</thead>
		<tbody>
			<s:iterator value="listContactos">
				<tr>
					<td>${tipoContacto.nombre}</td>
					<td>${Contacto}</td>
					<td>${descripcion}</td>					
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
		
		
		<sj:a id="btnRegresar" button="true"  href="#" onclick="location.href='%{urlRegresar}'">Cancelar</sj:a>
	</s:form>
	</body>
	</html>
</jsp:root>