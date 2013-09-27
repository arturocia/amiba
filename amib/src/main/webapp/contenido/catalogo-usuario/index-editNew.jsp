<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Nuevo usuario</title>
<jsp:text>
	<![CDATA[ 
		<script src="${pageContext.request.contextPath}/scripts/catalogo-contacto-temporal.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlAgregar" value="/catalogo-contacto-temporal/new"
		includeContext="true" />

	<s:url id="urlCancelar" value="/catalogo-usuario" includeContext="true" />
	<s:actionerror id="saeUsuario" theme="jquery" />
	<s:fielderror id="sfeUsuario" theme="jquery" />
	<s:form action="%{#request.contextPath}/catalogo-usuario" method="post"
		theme="simple" acceptcharset="UTF-8">
		<table>
			<tr>
				<td colspan="2">
					<div class="title">Agregar Usuario</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="section">Datos Generales</div>
				</td>
			</tr>
			<tr>
				<td><label>Login:</label></td>
				<td><s:textfield id="txtLogin" name="model.login"
						maxlength="20" /></td>
			</tr>
			<tr>
				<td><label>Contraseña:</label></td>
				<td><s:password id="txtPsw" name="model.psw" maxlength="20" />
				</td>
			</tr>
			<tr>
				<td><label>Repita Contraseña:</label></td>
				<td><s:password id="txtPsw2" name="psw2" maxlength="20" /></td>
			</tr>
			<tr>
				<td colspan="2"><s:checkbox id="chkbxActivado"
						name="model.activado" fieldValue="true" /> <label>Activo/Inactivo.</label>
				</td>
			</tr>
			<tr>
				<td><label>Nombre:</label></td>
				<td><s:textfield id="txtNombre" name="model.nombre"
						maxlength="50" /></td>
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
				<td><label>RFC (*):</label></td>
				<td><s:textfield id="txtRFC" name="model.rfc" maxlength="18" />
				</td>
			</tr>
			<tr>
				<td><label>Perfil de Usuario:</label></td>
				<td><s:if
						test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario==
						@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@DIRECTORGENERAL}">
						<s:label value="Coordinador" />
						<s:hidden name="model.idPerfilUsuario"
							value="%{@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE}" />
					</s:if> <s:if
						test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario==
						@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE}">
						<s:label value="Lider de proyecto" />
						<s:hidden name="model.idPerfilUsuario"
							value="%{@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@COORDINADOR}" />
					</s:if> <s:if
						test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario==
						@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@ADMINISTRADOR}">

						<s:select
							list="#{@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@DIRECTORGENERAL:'Director',
											@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@SECRETARIO :'Secretaria'}"
							name="model.idPerfilUsuario" required="true" />

					</s:if></td>

			</tr>

			<s:if
				test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario !=
						@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@ADMINISTRADOR}">
				<tr>
					<td><label>Jefe inmediato:</label></td>
					<td><s:property
							value="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].nombre
							+' '+#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].apPat
							+' '+#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].apMat}" />
						<s:hidden name="model.empleado"
							value="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idUsuario}" />
					</td>
				</tr>

			</s:if>
			<tr>
				<td colspan="2"><label>Dirección:</label></td>
			</tr>
			<tr>
				<td colspan="2"><s:if
						test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario==
						@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@DIRECTORGENERAL 
						or 
						#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario==
						@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE }">
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
				<td colspan="2"><label>Cargo:</label></td>
			</tr>
			<tr>
				<td colspan="2"><s:textarea id="txtCargo" rows="3" cols="70"
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
			<tr>
				<td><label>Teléfono:</label></td>
				<td><s:textfield id="txtTel" name="tel.contacto" maxlength="20" />
				</td>
			</tr>
			<tr>
				<td><label>Descripción Teléfono:</label></td>
				<td><s:textfield id="txtDescTel" name="tel.descripcion"
						maxlength="70" /></td>
			</tr>
			<tr>
				<td><label>Correo electrónico:</label></td>
				<td><s:textfield id="txtCorreo" name="correo.contacto" maxlength="50"/></td>
			</tr>
			<tr>
				<td><label>Descripción correo electrónico:</label></td>
				<td><s:textfield id="txtDescCorreo" name="correo.descripcion"
						maxlength="70" /></td>
			</tr>
		</table>

		<br />
		<sj:submit id="btnAceptar" value="Aceptar" button="true" />
		<sj:a id="btnRegresar" button="true" href="#"
			onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
	</s:form>
	<p>NOTA: Los puntos marcados con (*) son opcionales</p>
</body>
	</html>
</jsp:root>