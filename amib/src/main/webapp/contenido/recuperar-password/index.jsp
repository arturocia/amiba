<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Recuperar password</title>
</head>
<body>
	<s:url id="urlCancelar" value="/login.jsp" includeContext="true" />
	<s:actionerror theme="jquery" />
	<s:fielderror theme="jquery" />
	<h1>Recuperar contraseña</h1>
	<s:form id="frmRecuperar" action="%{#request.contextPath}/recuperar-password!execute"
		method="post" theme="simple" acceptcharset="UTF-8">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<table>
			<tr>
				<td><label>Usuario:</label>
				</td>
				<td><s:textfield name="userId" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><sj:submit id="btnAceptar" value="Aceptar"
						button="true" formIds="frmRecuperar" /> <sj:a id="btnCancelar"
						button="true" href="#"
						onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>
				</td>
			</tr>
		</table>
	</s:form>
</body>
	</html>
</jsp:root>
