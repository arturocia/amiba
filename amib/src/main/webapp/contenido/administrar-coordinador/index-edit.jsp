<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Editar Coordinador</title>
	<jsp:text>
		<![CDATA[
		<script src="${pageContext.request.contextPath}/scripts/catalogo-contacto.js" type="text/javascript"></script> 
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/desactivarUsuario.js" type="text/javascript"></script>
		 ]]>
	</jsp:text>
</head>
<body>
<h1>Editar Coordinador</h1>
	<s:url id="urlCancelar" value="/administrar-coordinador" includeContext="true" />
	<s:actionerror id="saeUsuario" theme="jquery" />
	<s:fielderror id="sfeUsuario" theme="jquery" />
	<s:form action="%{#request.contextPath}/administrar-coordinador/%{idUsuario}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<label>Login:</label>
		<s:textfield id="txtLogin" name="model.login" disabled="true" />
		<br />
		<label id="lblTxtPsw">Contraseña:</label>
		<s:password id="txtPsw" name="model.psw" maxlength="20"/>
		<br />
		<label id="lblTxtPsw">Repita Contraseña:</label>
		<s:password id="txtPsw2" name="psw2" maxlength="20"/>
		<br />
		<s:checkbox id="chkbxActivado" name="model.activado" fieldValue="true"/> <label>Permitir acceso al sistema.</label><!-- Yo lo hice -->
		<br />
		<label>Nombre:</label>
		<s:textfield id="txtNombre" name="model.nombre" maxlength="20" />
		<br />
		<label>Apellido Paterno:</label>
		<s:textfield id="txtApPat" name="model.apPat" maxlength="20" />
		<br />
		<label>Apellido Materno:</label>
		<s:textfield id="txtApMat" name="model.apMat" maxlength="20" />
		<br />
		<label>RFC:</label>
		<s:textfield id="txtRFC" name="model.rfc" maxlength="18"/>
		<br />
		<label>Perfil de Usuario:</label>
		<s:select list="perfilUsuarios" listValue="nombre"
			disabled="%{!model.proyectos.isEmpty}" listKey="idPerfilUsuario"
			name="model.idPerfilUsuario"
			headerValue="- - Seleccione una opción - -" headerKey="-1"
			requiered="true" />
		<br />
		<label>Dirección:</label>
		<s:select list="listareas" listValue="nombre" listKey="idArea"
			disabled="%{!model.proyectos.isEmpty}" name="model.idArea"
			headerValue="- - Seleccione una opción - -" headerKey="-1"
			requiered="true" />
		<br />
		<label>Cargo:</label>
		<s:textarea id="txtCargo" rows="5" cols="50" name="model.cargo" maxlength="70" />
		<br />
		<label>Calle:</label>
		<s:textfield id="txtCalle" name="model.direccion.calle" maxlength="20" />
		<br />
		<label>Numero:</label>
		<s:textfield id="txtNumero" name="model.direccion.direccion" maxlength="20"/>
		<br />
		<label>Colonia:</label>
		<s:textfield id="txtColonia" name="model.direccion.colonia" maxlength="20"/>
		<br />
		<label>Codigo Postal:</label>
		<s:textfield id="txtCp" name="model.direccion.cp" maxlength="10"/>
		<br />
		<label>Delegación:</label>
		<s:textfield id="txtDeleg" name="model.direccion.deleg" maxlength="20"/>
		<br />
		<label>Estado:</label>
		<s:textfield id="txtEdo" name="model.direccion.edo" maxlength="20"/>
		<br />
		<label>País:</label>
		<s:textfield id="txtPais" name="model.direccion.pais" maxlength="20"/>
		<br />

<br />
	<table id="tblContacto">
		<thead>
			<tr>
				<th>Tipo</th>
				<th>Contacto</th>
				<th>Descripcion</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="listContactos">
				<tr>
					<td>${tipoContacto.nombre}</td>
					<td>${Contacto}</td>
					<td>${descripcion}</td>
					<td><a
						href="${pageContext.request.contextPath}/catalogo-contacto/${idContacto}!edit?idUsuarioSel=${idUsuario}">Editar</a>
						<a
						href="${pageContext.request.contextPath}/catalogo-contacto/${idContacto}!deleteConfirm?idUsuarioSel=${idUsuario}">Eliminar</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<a
		href="${pageContext.request.contextPath}/catalogo-contacto/new"
		id="lnkNuevoGrupo"><input type="button" value="Agregar Contacto"></input></a>
		

<br />
<br />
<br />

		<sj:submit id="btnAceptar" value="Aceptar" button="true"/>
		<sj:a id="btnCancelar" button="true"  href="#" onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
</body>
	</html>
</jsp:root>