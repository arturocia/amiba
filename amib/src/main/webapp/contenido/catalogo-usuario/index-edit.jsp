<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Editar Usuario</title>
<jsp:text>
	<![CDATA[
		<script src="${pageContext.request.contextPath}/scripts/catalogo-contacto.js" type="text/javascript"></script> 
			<script src="${pageContext.request.contextPath}/scripts/desactivarUsuario.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlAgregar" value="/catalogo-contacto/new"
		includeContext="true" />
	<h1>Editar Usuario</h1>
	<s:url id="urlCancelar" value="/catalogo-usuario" includeContext="true" />
	<s:actionerror id="saeUsuario" theme="jquery" />
	<s:fielderror id="sfeUsuario" theme="jquery" />
	<s:form action="%{#request.contextPath}/catalogo-usuario/%{idUsuario}"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="put" />

		<table border="0">
			<tr>
				<td><label>Login:</label></td>
				<td><s:textfield id="txtLogin" name="model.login"
						disabled="true" maxlength="20"/></td>
			</tr>
			<tr>
				<td><label id="lblTxtPsw">Contraseña:</label></td>
				<td><s:password id="txtPsw" name="model.psw" maxlength="20" /></td>
			</tr>
			<tr>
				<td><label id="lblTxtPsw">Repita Contraseña:</label></td>
				<td><s:password id="txtPsw2" name="psw2" maxlength="20" /></td>
			</tr>
			<tr>
				<td colspan="2"><s:checkbox id="chkbxActivado"
						name="model.activado" fieldValue="true" /><label>Activo/Inactivo.</label>
					<s:select list="usuariosExistentes" listValue="nombre"
						id="lstUsuariosExistentes" listKey="idUsuario"
						name="idNuevoUsuarioAsignado"
						headerValue="- - Seleccione una Coordinador - -" headerKey="-1"
						requiered="true" /></td>
			</tr>
			<tr>
				<td><label>Nombre:</label></td>
				<td><s:textfield id="txtNombre" name="model.nombre" maxlength="50"/></td>
			</tr>
			<tr>
				<td><label>Apellido Paterno:</label></td>
				<td><s:textfield id="txtApPat" name="model.apPat" maxlength="50"/></td>
			</tr>
			<tr>
				<td><label>Apellido Materno:</label></td>
				<td><s:textfield id="txtApMat" name="model.apMat" maxlength="50"/></td>
			</tr>
			<tr>
				<td><label>RFC:</label></td>
				<td><s:textfield id="txtRFC" name="model.rfc" maxlength="18" /></td>
			</tr>
			<tr>
				<td><label>Perfil de Usuario:</label></td>
				<td><s:property value="model.perfilUsuario.nombre" /></td>
			</tr>
			<tr>
				<td><label>Dirección:</label></td>
				<td><s:if
						test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario==
			@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@DIRECTORGENERAL}">
						<s:label
							value="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].area.nombre}" />
						<s:hidden name="model.idArea"
							value="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].area.idArea}" />
					</s:if> <s:else>
						<s:select list="listareas" listValue="nombre" listKey="idArea"
							name="model.idArea" headerValue="- - Seleccione una opción - -"
							headerKey="-1" requiered="true" />
					</s:else></td>
			</tr>
			<tr>
				<td><label>Cargo:</label></td>
				<td><s:textarea id="txtCargo" rows="5" cols="50"
						name="model.cargo" /></td>
			</tr>
			<tr>
				<td><label>Calle:</label></td>
				<td><s:textfield id="txtCalle" name="model.direccion.calle" maxlength="200"/></td>
			</tr>
			<tr>
				<td><label>Número:</label></td>
				<td><s:textfield id="txtNumero"
						name="model.direccion.direccion" maxlength="20" /></td>
			</tr>
			<tr>
				<td><label>Colonia:</label></td>
				<td><s:textfield id="txtColonia" name="model.direccion.colonia"
						maxlength="50" /></td>
			</tr>
			<tr>
				<td><label>Código Postal:</label></td>
				<td><s:textfield id="txtCp" name="model.direccion.cp"
						maxlength="5" /></td>
			</tr>
			<tr>
				<td><label>Delegación:</label></td>
				<td><s:textfield id="txtDeleg" name="model.direccion.deleg" maxlength="30"/></td>
			</tr>
			<tr>
				<td><label>Estado:</label></td>
				<td><s:textfield id="txtEdo" name="model.direccion.edo" maxlength="30"/></td>
			</tr>
			<tr>
				<td><label>País:</label></td>
				<td><s:textfield id="txtPais" name="model.direccion.pais"
						maxlength="30" /></td>
			</tr>
		</table>
		<br />
		<br />
		<br />
		<table id="tblContacto">
			<thead>
				<tr>
					<th>Tipo</th>
					<th>Contacto</th>
					<th>Prioridad</th>
					<th>Descripción</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<s:actionerror theme="jquery" />
				<s:actionmessage id="message" theme="jquery" />
				<s:iterator value="listContactos">
					<tr>
						<td>${tipoContacto.nombre}</td>
						<td>${Contacto}</td>
						<td><s:if test="principal!=true">Normal</s:if>
							<s:else>Principal</s:else></td>
						<td>${descripcion}</td>
						<td><a
							href="${pageContext.request.contextPath}/catalogo-contacto/${idContacto}!edit?idUsuarioSel=${idUsuario}">
								<img height="20" width="20"
								src="${pageContext.request.contextPath}/images/buttons/editar.png" />
						</a> <a
							href="${pageContext.request.contextPath}/catalogo-contacto/${idContacto}!deleteConfirm?idUsuarioSel=${idUsuario}">
								<img height="20" width="20"
								src="${pageContext.request.contextPath}/images/buttons/eliminar.png" />
						</a></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<p>
			<sj:a id="btnAgregar" button="true" href="#"
				onclick="location.href='%{urlAgregar}'">Agregar Contacto</sj:a>
		</p>
		<br />

		<sj:submit id="btnAceptar" value="Aceptar" button="true" />
		<sj:a id="btnCancelar" button="true" href="#"
			onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
</body>
	</html>
</jsp:root>