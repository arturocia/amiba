<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags"
	xmlns:sjt="/struts-jquery-tree-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Editar datos de proyecto</title>
<jsp:text>
	<![CDATA[
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/interface/OperacionEditarDatosProyectoController.js'></script>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/engine.js'></script>
			<script type='text/javascript' src='${pageContext.request.contextPath}/dwr/util.js'></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-core.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-more.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/struts/utils.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/struts/js/jstree/jquery.jstree.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/editar-datos-proyecto.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>

	<s:url id="urlTraerEstructuras"
		value="/operacion-editar-datos-proyecto!traerEstructura"
		includeContext="true" />



	<sjt:tree id="treEstructurasProgramaOtro" href="traerEstructura"
		onClickTopics="elementoClickeado" onCompleteTopics="caca1" />

</body>
	</html>
</jsp:root>