<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Nuevo Coordinador</title>
<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/Administrador.js" type="text/javascript"></script>
		 ]]>
</jsp:text>
</head>
<body>
	<h1>Agregar Coordinador</h1>
	<s:url id="urlRegresar" value="/administrar-coordinador"
		includeContext="true" />
	<s:actionerror id="saeUsuario" theme="jquery" />
	<s:fielderror id="sfeUsuario" theme="jquery" />
	<s:form action="%{#request.contextPath}/administrar-coordinador"
		method="post" theme="simple" acceptcharset="UTF-8">


		<label>Login:</label>
		<s:textfield id="txtLogin" name="model.login" maxlength="20" />
		<br />
		<label>Contraseña:</label>
		<s:password id="txtPsw" name="model.psw" maxlength="20"/>
		<br />
		<label>Repita Contraseña:</label>
		<s:password id="txtPsw2" name="psw2" maxlength="20"/>
		<br />
		<s:checkbox id="chkbxActivado" name="model.activado" fieldValue="true" />
		<label>Permitir el uso del sistema.</label>
		<br />
		<label>Nombre:</label>
		<s:textfield id="txtNombre" name="model.nombre" maxlength="20"/>
		<br />
		<label>Apellido Paterno:</label>
		<s:textfield id="txtApPat" name="model.apPat" maxlength="20"/>
		<br />
		<label>Apellido Materno:</label>
		<s:textfield id="txtApMat" name="model.apMat" maxlength="20"/>
		<br />
		<label>RFC:</label>
		<s:textfield id="txtRFC" name="model.rfc" maxlength="18"/>
		<br />
		<label>Perfiles de Usuario:</label>		
		<s:select list="perfilUsuarios" listValue="nombre" listKey="idPerfilUsuario" 
		name="model.idPerfilUsuario" headerValue="- - Seleccione una opción - -" headerKey="-1" requiered="true" disabled="true"/>
		<br />
		<label>Dirección:</label>
		<s:select list="listareas" listValue="nombre" listKey="idArea" name="model.idArea"
		headerValue="- - Seleccione una opción - -" headerKey="-1" requiered="true" />
		<br />
		<label>Cargo:</label>
		<br/>
		<s:textarea id="txtCargo" rows="3" cols="70" name="model.cargo" maxlength="70"/>
		<br />
		<label>Calle:</label>
		<s:textfield id="txtCalle" name="model.direccion.calle" maxlength="20"/>
		<br />
		<label>Número:</label>
		<s:textfield id="txtNumero" name="model.direccion.direccion" maxlength="20"/>
		<br />
		<label>Colonia:</label>
		<s:textfield id="txtColonia" name="model.direccion.colonia" maxlength="20"/>
		<br />
		<label>Código Postal:</label>
		<s:textfield id="txtCp" name="model.direccion.cp" maxlength="5"/>
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
		
		<label>Teléfono:</label>
		<s:textfield id="txtTel" name="tel.contacto" maxlength="20"/>
		<br />
		<label>Descripción Teléfono:</label>
		<s:textfield id="txtDescTel" name="tel.descripcion" maxlength="70"/>
		<br />
		<label>Correo electrónico:</label>
		<s:textfield id="txtCorreo" name="correo.contacto" maxlength="25"/>
		<br />
		<label>Descripción correo electrónico:</label>
		<s:textfield id="txtDescCorreo" name="correo.descripcion" maxlength="70"/>
		<br />
		
		

		<sj:submit id="btnAceptar" value="Aceptar" button="true"
			/>
		<sj:a id="btnRegresar" button="true" 
			href="#" onclick="history.go(-1)">Cancelar	</sj:a>
	</s:form>
</body>
	</html>
</jsp:root>