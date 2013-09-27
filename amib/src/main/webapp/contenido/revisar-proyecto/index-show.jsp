<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Revisar Proyecto</title>
<jsp:text>
	<![CDATA[ 
				<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/revisar-projecto.js" ></script>
			]]>
</jsp:text>
</head>
<body>
	<s:actionmessage id="algo" theme="jquery" />
	<s:actionerror id="saeUsuario" theme="jquery" />
	<s:fielderror id="sfeUsuario" theme="jquery" />
	<s:url id="urlDatos"
		value="/operacion-editar-datos-proyecto/%{idSel}"
		includeContext="true" />
	<s:url id="urlPlaneacion" value="/operacion-planeacion/%{idSel}"
		includeContext="true" />
	<s:url id="urlSeguimiento" value="/operacion-bitacora"
		includeContext="true" />
	<s:url id="urlAvances" value="/operacion-revisar-avances/%{idSel}" includeContext="true" />
		
	<sj:tabbedpanel id="remotetabs" spinner="Cargando ..."
		onCompleteTopics="tabcomplete" onChangeTopics="tabchange,ensureReload"
		cssStyle="padding:0px; marging:0px;">
		<sj:tab id="tab1" label="Datos" target="datos" />
		<sj:tab id="tab2" label="Planeación" target="planeacion" />
		<sj:tab id="tab3" label="Seguimiento" target="seguimiento" />
		<sj:tab id="tab4" label="Avances" target="avances" />
		<div id="datos" style="padding: 0px; marging: 0px;">
			<iframe id="ifDatos" border="0" frameborder="0" framespacing="0"
				width="100%" height="550px;" src="${urlDatos}"> Su
				navegador no soporta esta funcion </iframe>
		</div>
		<div id="planeacion" style="padding: 0px; marging: 0px;">
			<iframe id="ifPlaneacion" border="0" frameborder="0" framespacing="0"
				width="100%" height="550px;" src="${urlPlaneacion}"> Su
				navegador no soporta esta funcion </iframe>
		</div>
		<div id="seguimiento" style="padding: 0px; marging: 0px;">
			<iframe id="ifSeguimiento" border="0" frameborder="0"
				framespacing="0" width="100%" height="550px;"
				src="${urlSeguimiento}"> Su navegador no soporta esta
				funcion </iframe>
		</div>
		<div id="avances" style="padding: 0px; marging: 0px;">
			<iframe id="ifAvances" border="0" frameborder="0" framespacing="0"
				width="100%" height="550px;" src="${urlAvances}"> Su
				navegador no soporta esta funcion </iframe>
		</div>
	</sj:tabbedpanel>
</body>
	</html>
</jsp:root>