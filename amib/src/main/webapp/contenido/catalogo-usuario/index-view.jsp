<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Vizualizar Usuario</title>
<jsp:text>
	<![CDATA[ 
		<script src="${pageContext.request.contextPath}/scripts/catalogo-contacto.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
	<h1>Visualizar Usuario</h1>
	<s:url id="urlRegresar" value="/catalogo-usuario" includeContext="true" />
	<s:actionerror id="saeUsuario" theme="jquery" />
	<s:fielderror id="sfeUsuario" theme="jquery" />
	<s:form action="%{#request.contextPath}/catalogo-usuario/%{idUsuario}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<table>
			<tr>
				<td><label>Login:</label>
				</td>
				<td><b><s:label id="txtLogin" name="model.login"
							disabled="true" /> </b>
				</td>
			</tr>
			<tr>
				<td><label>Activado: </label>
				</td>
				<td><s:checkbox id="chkbxActivado" name="model.activado"
						fieldValue="true" disabled="true" />
				</td>
			</tr>
			<tr>
				<td><label>Nombre:</label></td>
				<td><b><s:label id="txtNombre" name="model.nombre"
							disabled="true" /> </b></td>
			</tr>
			<tr>
				<td><label>Apellido Paterno:</label>
				</td>
				<td><b><s:label id="txtApPat" name="model.apPat"
							disabled="true" /> </b>
				</td>
			</tr>
			<tr>
				<td><label>Apellido Materno:</label></td>
				<td><b><s:label id="txtApMat" name="model.apMat"
							disabled="true" /> </b></td>
			</tr>
			<tr>
				<td><label>RFC:</label></td>
				<td><b><s:label id="txtRFC" name="model.rfc"
							disabled="true" /> </b></td>
			</tr>
			<tr>
				<td><label>Perfil de Usuario:</label></td>
				<td><b><s:label id="txtPerfilUsuario"
							name="model.perfilUsuario.nombre" disabled="true" /> </b></td>
			</tr>
			<tr>
				<td><label>Dirección:</label></td>
				<td><b><s:label id="txtArea" name="model.area.nombre"
							disabled="true" /> </b></td>
			</tr>
			<tr>
				<td><label>Cargo:</label>
				</td>
				<td><b><s:label id="txtCargo" name="model.cargo"
							disabled="true" /> </b>
				</td>
			</tr>
			<tr>
				<td><label>Calle:</label>
				</td>
				<td><b><s:label id="txtCalle" name="model.direccion.calle"
							disabled="true" /> </b>
				</td>
			</tr>
			<tr>
				<td><label>Número:</label>
				</td>
				<td><b><s:label id="txtNumero"
							name="model.direccion.direccion" disabled="true" /> </b>
				</td>
			</tr>
			<tr>
				<td><label>Colonia:</label>
				</td>
				<td><b><s:label id="txtColonia"
							name="model.direccion.colonia" disabled="true" /> </b>
				</td>
			</tr>
			<tr>
				<td><label>Código Postal:</label>
				</td>
				<td><b><s:label id="txtCp" name="model.direccion.cp"
							disabled="true" /> </b>
				</td>
			</tr>
			<tr>
				<td><label>Delegación:</label>
				</td>
				<td><b><s:label id="txtDeleg" name="model.direccion.deleg"
							disabled="true" /> </b>
				</td>
			</tr>
			<tr>
				<td><label>Estado:</label>
				</td>
				<td><b><s:label id="txtEdo" name="model.direccion.edo"
							disabled="true" /> </b>
				</td>
			</tr>
			<tr>
				<td><label>País:</label>
				</td>
				<td><b><s:label id="txtPais" name="model.direccion.pais"
							disabled="true" /> </b>
				</td>
			</tr>
		</table>
		
		<table id="tblContacto">
			<thead>
				<tr>
					<th>Tipo</th>
					<th>Contacto</th>
					<th>Descripción</th>
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



		<sj:a id="btnRegresar" button="true"  
			href="#" onclick="location.href='%{urlRegresar}'">Cancelar</sj:a>
	</s:form>
</body>
	</html>
</jsp:root>