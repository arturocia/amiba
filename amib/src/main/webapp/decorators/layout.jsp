<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
	xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:s="/struts-tags"
	xmlns:sj="/struts-jquery-tags" xmlns:sjc="/struts-jquery-chart-tags"
	xmlns:log="http://jakarta.apache.org/taglibs/log-1.0"
	xmlns:sjg="/struts-jquery-grid-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<jsp:text>
		<![CDATA[ <?xml version="1.0" encoding="UTF-8" ?> ]]>
	</jsp:text>
	<jsp:text>
		<![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
	</jsp:text>
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<s:url id="urlRutaContexto"
	value="%{pageContext.request.contextPath}/template/themes"
	includeContext="true" />
<sj:head debug="true" jqueryui="true" jquerytheme="cdt-sidam"
	customBasepath="%{urlRutaContexto}" locale="es" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/scripts/dataTables/media/css/demo_page.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/scripts/dataTables/media/css/demo_table.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/scripts/dataTables/media/css/demo_table_jui.css" />

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/print.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/main.css" />

<jsp:text>
	<![CDATA[ 
			<script src="${pageContext.request.contextPath}/scripts/log4javascript.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/log4javascript.conf.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/struts/js/plugins/jquery.jqGrid.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/struts/js/plugins/jquery.form.min.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/dataTables/media/js/jquery.dataTables.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/jquery.blockUI.js" type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/scripts/jquery.feedback.js" type="text/javascript"></script>
			<script src='${pageContext.request.contextPath}/dwr/engine.js' type='text/javascript'></script>
			<script src='${pageContext.request.contextPath}/dwr/util.js' type='text/javascript'></script>
			<script src="${pageContext.request.contextPath}/scripts/cdt-util.js" type="text/javascript"></script>
		]]>
</jsp:text>

<decorator:head />

</head>
<body>
	<div id="pageHeader">
		<div class="banner">
			<img
				src="${pageContext.request.contextPath}/styles/images/banner/logo.png" />
		</div>
	</div>
	<input type="text" style="display: none;" id="hdnRutaContexto"
		value="${pageContext.request.contextPath}" />

	<div id="pageMain">
		<div id="pageMenu">
			<div class="menu">
				<s:if test="%{#session.usuario == null}">
					<h3>SIDAM</h3>
					<a href="http://148.204.56.138:8085/SIDAM">Inico</a>
					
					<h3>Banco de estudios</h3>
					<a href="${pageContext.request.contextPath}/consulta-general"
						class="selected">Acceso al banco de estudios</a>
				</s:if>

				<s:if
					test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@ADMINISTRADOR}">
					<h3>Administrador</h3>
					<h3>Usuario</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-usuario">Gestión</a>
					<br />
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-tipo-contacto">Tipo
						de contacto</a>
					<br />

					<br />
					<h3>Administración de catálogos</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-area">Direcciones</a>
					<br />

					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-programa">Programas</a>
					<br />

					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-eje-tematico">Ejes
						temáticos</a>
					<br />
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-tema-transversal">Temas
						transversales</a>
					<br />
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-unidad">Unidades</a>


					<br />

					<br />
				</s:if>
				<s:if
					test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE}">
					<h3>Coordinador</h3>
					<h3>Programa</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/revisar-programa">Revisar
						programa</a>
					<br />
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-programa">Programas</a>
					<br />

					<h3>Proyectos</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/consultar-proyectos">Consultar
						proyectos</a>
					<br />

					<br />
					<a class="selected"
						href="${pageContext.request.contextPath}/seguimiento-proyecto">Seguimiento
						de Proyecto </a>
					<a class="selected"
						href="${pageContext.request.contextPath}/notificaciones">
						Notificaciones </a>



					<br />
					<h3>Usuario</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-usuario">Gestión</a>
					<br />



				</s:if>
				<s:if
					test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@COORDINADOR}">
					<h3>Lider de proyecto</h3>
					<h3>Proyectos</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/proyectos-disponibles/">Ver
						proyectos disponibles</a>
					<br />
					<a class="selected" id="lnkRegistrarProyecto"
						href="${pageContext.request.contextPath}/registrar-proyecto/">Registrar
						proyecto</a>
					<br />
					<a class="selected"
						href="${pageContext.request.contextPath}/notificaciones">
						Notificaciones</a>


				</s:if>
				<s:if
					test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@SECRETARIO}">
					<h3>Titular de la Secretaría</h3>

					<h3>Programa</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/revisar-programa">
						Revisar programa </a>
					<br />

					<a class="selected"
						href="${pageContext.request.contextPath}/consultar-proyectos">Consultar
						proyectos</a>
					<br />
					<a class="selected"
						href="${pageContext.request.contextPath}/notificaciones">Notificaciones</a>
					<br />
				</s:if>
				<s:if
					test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@DIRECTORGENERAL}">
					<h3>Consulta</h3>
					<h3>Consultar Proyectos</h3>
					<a href="${pageContext.request.contextPath}/consultar-proyectos">Consultar
						proyectos</a>
					<h3>Usuario</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-usuario">Gestión</a>
				</s:if>
				<s:if test="%{#session.usuario.idPerfilUsuario != null}">
					<a href="${pageContext.request.contextPath}/logout">
						<h3>Cerrar sesión</h3>
					</a>
				</s:if>
				<!-- -->
			</div>
		</div>

		<div id="pageContent">
			<decorator:body />
		</div>

	</div>
	<div id="pageFooter">
		<h2>Secretaría del Medio Ambiente del Gobierno del Distrito
			Federal.</h2>
		<p>Plaza de la Constitución No. 1, 3er piso, Centro Histórico,
			Deleg. Cuauhtémoc C.P. 06068, México, D.F.</p>
		e-mail: <a href="mailto:dgplaneacion@.com">dgplaneacion@.com</a>
	</div>

	<br />
	<br />
</body>
	</html>
</jsp:root>