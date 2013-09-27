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
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cambiaBoton.js" ></script>
		]]>
</jsp:text>

<decorator:head />

</head>
<body onload="funcion()">
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
				
				<s:if
					test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@COORDINADOR}">
					<h3>Líder de proyecto</h3>
					<s:hidden id="OperacionEstado"
						value="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@OPERACION_ESTADO]}"></s:hidden>
					<h3>Proyectos</h3>

					<a class="selected"
						href="${pageContext.request.contextPath}/proyectos-disponibles/">Ver
						proyectos disponibles</a>

					<br />
					<a class="selected" id="lnkRegistrarProyecto"
						href="${pageContext.request.contextPath}/registrar-proyecto/">Registrar
						proyectos</a>
					<br />
					<a class="selected"
						href="${pageContext.request.contextPath}/notificaciones">
						Notificaciones </a>
					<br />
					<!-- <a class="selected"
					href="${pageContext.request.contextPath}/catalogo-proyecto">Gestión
					de proyecto</a>!-->
					<br />

					<br />
				</s:if>
				<s:if
					test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@ADMINISTRADOR}">
					<h3>Administrador</h3>
					<h3>Usuario</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-usuario">Gestión
						usuarios</a>
					<br />
					<h3>Tipo de Contacto</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-tipo-contacto">Gestión
						tipos de contacto</a>
					<br />

					<br />
					<h3>Dirección</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-area">Gestión
						de Direcciones</a>
					<br />
					<h3>Programa</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-programa">Gestión
						de programas</a>
					<br />
					<h3>Gestión Unidades</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-unidad">Gestiónar
						unidades</a>
					<br />
					<h3>Tema Transversal</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-tema-transversal">Gestión
						de temas transversales</a>
					<br />
					<h3>Eje Temático</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-eje-tematico">Gestión
						de ejes temáticos</a>
					<br />
				</s:if>
				<s:if
					test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE}">
					<h3>Coordinador</h3>
					<h3>Programa</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/revisar-programa">Revisar
						programa</a>
						<br/>
					<a class="selected"
						href="${pageContext.request.contextPath}/revisar-programa">Programas</a>
					<br />
					<h3>Proyectos</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/consultar-proyectos">Consultar
						proyectos</a>
					<br />
					<br/>
					<a class="selected"
						href="${pageContext.request.contextPath}/seguimiento-proyecto">Seguimiento
						de Proyecto </a>
					<a class="selected"
						href="${pageContext.request.contextPath}/notificaciones">
						Notificaciones </a>
					<br />
		
					<h3>Usuario</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/catalogo-usuario">
						Gestión </a>
						<br/>
				</s:if>
				<s:if
					test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@SECRETARIO}">
					<h3>Titular de la Secretaría</h3>

					<h3>Notificaciones</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/notificaciones">
						Notificaciones</a>
					<h3>Revisar programa</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/revisar-programa">
						Revisar programa </a>
					<br />
					<h3>Proyectos</h3>
					<a class="selected"
						href="${pageContext.request.contextPath}/consultar-proyectos">Consultar
						proyectos</a>
					<br />
				</s:if>
				<s:if
					test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@DIRECTORGENERAL}">
					<h3>Consulta</h3>
					<h3>Consultar Proyectos</h3>
					<a href="${pageContext.request.contextPath}/consultar-proyectos">Consultar
						proyectos</a>
					<!-- 	<h3>Coordinadores</h3>
				<a class="selected"
					href="${pageContext.request.contextPath}/catalogo-usuario">Gestión
					de coordinadores</a>-->
					<br />
				</s:if>

				<s:if test="%{#session.usuario.idPerfilUsuario != null}">
					<a href="${pageContext.request.contextPath}/logout">
						<h3>Cerrar sesión</h3>
					</a>
				</s:if>
			</div>
		</div>

		<div id="pageContent">
			<div id="container"
				style="border-radius: 5px; border-color: #45930B; border-width: 1px; border-style: solid;">
				<div id="pestaña"
					style="border-radius: 5px; background-color: #45930B; padding-left: 5px; padding-top: 5px; padding-bottom: 2px; text-align: left;">
					<s:url id="urlDatos"
						value="/operacion-editar-datos-proyecto/%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idProyecto}"
						includeContext="true" />

					<s:url id="urlSeguimiento"
						value="/operacion-bitacora/%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idProyecto}"
						includeContext="true" />
					<s:url id="urlAvances"
						value="/operacion-revisar-avances/%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idProyecto}"
						includeContext="true" />
					<s:url id="urlRevisar"
						value="/aprobar-proyecto/%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idProyecto}/edit"
						includeContext="true" />


					<sj:a button="true" id="operacionDatos"
						onclick="window.location='%{urlDatos}'">Datos Generales</sj:a>

					<s:if
						test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@COORDINADOR}">

						<s:if
							test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idEstado eq @mx.ipn.escom.cdt.besp.modelo.Estado@EDICION}">
							<s:url id="urlPlaneacion"
								value="/operacion-planeacion/%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idProyecto}"
								includeContext="true" />
							<sj:a button="true" id="operacionPlaneacion"
								onclick="window.location='%{urlPlaneacion}'">Planeación</sj:a>
						</s:if>
					</s:if>

					<s:if
						test="%{ @mx.ipn.escom.cdt.besp.util.CDTUtil@validaProyectoAlineado(#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO]) and  #session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idEstado  != @mx.ipn.escom.cdt.besp.modelo.Estado@CERRADO}">
						<sj:a button="true" id="operacionSeguimiento"
							onclick="window.location='%{urlSeguimiento}'">Seguimiento</sj:a>
					</s:if>
					<s:elseif
						test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE or #session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@SECRETARIO}">
						<sj:a button="true" id="operacionSeguimiento"
							onclick="window.location='%{urlSeguimiento}'">Seguimiento</sj:a>
					</s:elseif>

					<s:if
						test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idEstado == @mx.ipn.escom.cdt.besp.modelo.Estado@EJECUCION or #session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idEstado == @mx.ipn.escom.cdt.besp.modelo.Estado@CERRADO and #session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@COORDINADOR or #session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE or #session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@SECRETARIO  or #session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@DIRECTORGENERAL }">
						<sj:a button="true" id="operacionAvances"
							onclick="window.location='%{urlAvances}'">Avances</sj:a>
					</s:if>

					<s:if
						test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE and #session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idEstado == @mx.ipn.escom.cdt.besp.modelo.Estado@REVISION}">
						<sj:a button="true" onclick="window.location='%{urlRevisar}'">Revisar proyecto</sj:a>
					</s:if>

				</div>
				<decorator:body />
			</div>
		</div>
	</div>
	<div id="pageFooter">
		<h2>Secretaría del Medio Ambiente del Gobierno del Distrito
			Federal.</h2>
		<p>Plaza de la Constitución No. 1, 3er piso, Centro Histórico,
			Deleg. Cuauhtémoc C.P. 06068, México, D.F.</p>
		e-mail: <a href="mailto:dgplaneacion@sma.df.gob.mx">dgplaneacion@sma.df.gob.mx</a>
	</div>

	<br />
	<br />
</body>
	</html>
</jsp:root>