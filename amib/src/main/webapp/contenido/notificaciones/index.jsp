<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${titulo}</title>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/notificaciones.css" />
<jsp:text>
	<![CDATA[ 
				<script src="${pageContext.request.contextPath}/scripts/catalogo-bitacora.js" type="text/javascript"></script>
				
			]]>
</jsp:text>
</head>
<body>
	<s:actionerror theme="jquery" />
	<s:actionmessage id="message" theme="jquery" />
	<s:fielderror id="sfeBitacora" theme="jquery" />
	<div id="contenedorNotificaciones">
		<div id="tituloNotificaciones">Notificaciones del sistema</div>
		<div class="seccionNotificaciones">
			<s:if
				test="%{#session.usuario.idPerfilUsuario != @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@SECRETARIO}">
				<div class="tituloSeccion">Por proyecto</div>
				<div class="notificaciones">
					<s:iterator value="proyectos">
						<s:url
							value="%{'/operacion-editar-datos-proyecto/'+idProyecto+''}"
							includeContext="true" id="urlEditarProyecto" />
						<!--<s:url value="%{'/revisar-proyecto/'+idProyecto+'/show'}"
								includeContext="true" id="urlAprobarProyecto" />-->
						<s:url value="%{'/aprobar-proyecto/' + idProyecto + '/edit'}"
							includeContext="true" id="urlAprobarProyecto" />
						<s:if test="idEstado eq 1">
							<div class="notificacion nuevoProyecto">
								<s:a href="%{urlEditarProyecto}">${nombre}</s:a>
								se ha creado (falta su alineación con algun programa).
								<div class="detallesNotificacion">
									<s:date name="fechaRegistro" format="yyyy-MM-dd" />
								</div>
							</div>
						</s:if>
						<s:if test="idEstado eq 3">
							<div class="notificacion nuevoProyecto">
								<s:a href="%{urlEditarProyecto}">${nombre}</s:a>
								se ha enviado a revision para su
								<s:a href="%{urlAprobarProyecto}">aprobacion/rechazo</s:a>
								.
								<div class="detallesNotificacion">
									<s:date name="fechaModificacion" format="yyyy-MM-dd" />
								</div>
							</div>
						</s:if>
					</s:iterator>
					<s:if test="proyectos.size() eq 0">
						<div class="notificacion">
							<label>No hay notificaciones que mostrar por proyectos</label>
						</div>
					</s:if>
				</div>
			</s:if>
			<div class="tituloSeccion">Por restricción</div>
			<div class="notificaciones">
				<s:iterator value="proyectosConPendientes">
					<s:url value="%{'/operacion-editar-datos-proyecto/'+idProyecto+''}"
						includeContext="true" id="urlEditarProyecto" />
					<s:url value="%{'/operacion-bitacora/'+idProyecto+''}"
						includeContext="true" id="urlSeguimientoProyecto" />
					<div class="notificacion atenderBitacoraProyecto">
						<s:a href="%{urlEditarProyecto}">${nombre}</s:a>
						tiene
						<s:if test="restriccionesNoAtendidas > 0">
								${restriccionesNoAtendidas} restricción(es) no atendida(s)
							</s:if>
						<s:if test="restriccionesNoAtendidas > 0 and observaciones > 0">
							y 
							</s:if>
						<s:if test="observaciones > 0">
								${observaciones} observación(es)
							</s:if>
						por
						<s:a href="%{urlSeguimientoProyecto}">atender</s:a>
						<div class="detallesNotificacion">
							<s:date name="fechaModificacion" format="yyyy-MM-dd" />
						</div>
					</div>
				</s:iterator>
				<s:if test="proyectosConPendientes.size() eq 0">
					<div class="notificacion">
						<label>No hay notificaciones que mostrar por restricciones</label>
					</div>
				</s:if>
			</div>
		</div>
	</div>
</body>
	</html>
</jsp:root>
