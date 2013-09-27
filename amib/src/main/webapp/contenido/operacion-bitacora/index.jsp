<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0" xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${titulo}</title>
		<link type="text/css" rel="stylesheet"
			href="${pageContext.request.contextPath}/styles/bitacora.css" />
		<jsp:text>
			<![CDATA[ 
				<script src="${pageContext.request.contextPath}/scripts/catalogo-bitacora.js" type="text/javascript"></script>
				
			]]>
		</jsp:text>
	</head>
	<body>
		<s:actionerror theme="jquery" />
		<s:actionmessage id="message" theme="jquery"/>
		<s:fielderror id="sfeBitacora" theme="jquery" />
		<div id="containerBitacora">
			<s:form action="%{#request.contextPath}/operacion-bitacora"
				method="post" theme="simple" acceptcharset="UTF-8" id="bitacoraSeguimiento">
			<s:hidden name="idSel"/>
			<div id="guiaVisual">
				<label>Guía visual: </label>
				<label class="icono iconoObservacion">----</label><label>Observación</label>
				<label class="icono iconoAtendida">----</label><label>Restricción atendida</label>
				<label class="icono iconoTurnada">----</label><label>Restricción turnada</label>
				<label class="icono iconoNoAtendida">----</label><label>Restricción no atendida</label>
			</div>
			<h2>${titulo}</h2>
				<s:if test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@COORDINADOR}">
					
					<sj:a id="btnnueva" button="true" href="#" onclick="nuevaRestriccion();">Registrar restricción</sj:a>
					<div id="nuevaRestriccion" class="restriccion">
						<div class="icon nuevaRestriccion" ><label></label></div>
						<div class="titleRes tituloRestriccion"><label class="tituloProyecto">Nueva Restricción</label></div>
						<div id="cuerpoRestriccion">
							<div class="usuarios">
								<s:hidden name="idRemitenteSel" value="%{#session.usuario.idUsuario}" />
								<s:hidden name="idDestinatarioSel"/>
								<label class="usuarioFecha"><s:date name="fecha" format="yyyy-MM-dd" /></label>
								<label id="usuarioDe"> ${session.usuario.nombre} ${session.usuario.apPat} ${session.usuario.apMat}</label>
								<s:set var="proyectoSele" value="proyectoSel">
								<label>Usuario: <s:property value="idProyecto"/></label>
								</s:set>
								<label id="usuarioPara"> > ${usuarioPara.nombre} ${usuarioPara.apMat} ${usuarioPara.apMat}</label>
								
							</div>
							<div id="inputs" class="mensajes">
								<div class="titleResS tituloDescripcion">Descripción</div>
								<s:textarea name="descripcionRestriccion" cssClass="areaText" id="descripcion" maxlenght="100"></s:textarea>
								<div class="titleResS tituloDescripcion">Sugerencia</div>
								<s:textarea name="sugerenciaRestriccion"  cssClass="areaText" id="sugerencia" maxlenght="100"></s:textarea>
								<div id="controles">
									<sj:submit id="btnAceptar" value="Registrar" button="true" />
									<sj:a id="btnCancelar" button="true" href="#" onclick="cancelaRestriccion();">Cancelar</sj:a>
								</div>
							</div>
						</div>
					</div>
				</s:if>
				
				<s:if test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@COORDINADOR}">
					<s:hidden name="accionForma" value="nuevaRestriccion"/>
				</s:if>
				<s:else>
					<s:hidden name="accionForma" id="accionForma" value=""/>
					<s:hidden name="idRestriccionSel" id="idRestriccionSel" value=""/>
					<s:hidden name="detallesAccionRestriccion" id="detallesAccionRestriccion" value=""/>
					<s:hidden name="idRemitenteSel" value="%{#session.usuario.idUsuario}" />
					<s:hidden name="estadoEdicion" id="estadoEdicion" value="" />
				</s:else>
				
				<div id="contenedorRestricciones" >
					<s:set name="tamRegistros" value="registrosBitacora.size()"/>
					<!-- <p>Tamaño registros<s:property value="%{#tamRegistros}"/></p>!-->
					<s:iterator value="registrosBitacora" status="stat" >
						<!-- <p>Index:<s:property value="#stat.index"/></p>
						<s:property value="#stat.index"/>
						<br/>
						<s:property value="registrosBitacora[#stat.index].size()"/>!-->
						<div class="restriccion">	
							<s:iterator value="registrosBitacora[#stat.index]" var="bitacora" status="statMensajes">
								<s:set name="tamMensajes" value="registrosBitacora[#stat.index].size()"/>
								<!-- <p>Mensajes:<s:property value="#tamMensajes"/></p>
								<p>Mensaje:<s:property value="#statMensajes.index+1"/></p>!-->
								
								<s:if test="idTipoRegistro == 2">
									<s:if test="registrosBitacora[#stat.index][#tamMensajes-1].idTipoRegistro eq 2 or registrosBitacora[#stat.index][#tamMensajes-1].idTipoRegistro eq 5">
										<div class="icon noAtendida" ><label></label></div>
									</s:if>
									<s:if test="registrosBitacora[#stat.index][#tamMensajes-1].idTipoRegistro eq 4">
										<div class="icon turnada" ><label></label></div>
									</s:if>
									<s:if test="registrosBitacora[#stat.index][#tamMensajes-1].idTipoRegistro eq 3 or registrosBitacora[#stat.index][#tamMensajes-1].idTipoRegistro eq 6">
										<div class="icon atendida" ><label></label></div>
									</s:if>
									<div class="titleRes tituloRestriccion">
										<div class="content">
											<label class="tituloProyecto">Proyecto: <s:property value="%{#bitacora.proyecto.nombre}"/></label>
											<br/>
											<label class="idRes">Restricción <b>R${idRegistro}</b></label>
										</div>
									</div>
								</s:if>
								<s:if test="idTipoRegistro == 1">
									<div class="icon observacion" ><label></label></div>
									<div class="titleRes tituloRestriccion">
										<label class="tituloProyecto">Proyecto: <s:property value="%{#bitacora.proyecto.nombre}"/></label>
										<br/>
										<label class="idRes">Resultado aprobacion proyecto <b>R${idRegistro}</b></label>
									</div>
								</s:if>
								<div id="cuerpoRestriccion">
									<div class="usuarios">
										<label class="usuarioFecha">Fecha: <s:date name="fechaRegistro" format="yyyy-MM-dd" /></label>
										<label id="usuarioDe"><s:property value="%{#bitacora.usuarioRemitente.nombre+' '+#bitacora.usuarioRemitente.apPat+' '+#bitacora.usuarioRemitente.apMat}"/></label>
										<label id="usuarioPara"> > <s:property value="%{#bitacora.usuarioDestinatario.nombre+' '+#bitacora.usuarioDestinatario.apPat+' '+#bitacora.usuarioDestinatario.apMat}"/></label>
									</div>
									<div class="mensajes">
									<s:if test="idTipoRegistro == 2">
										<s:set value="%{descripcion.split(':desc:')}" name="detallesRestriccion"/>
										<div class="titleResS tituloDescripcion">Descripción</div>
		  								<p><s:property value="%{#detallesRestriccion[0]}"/></p>
		  								<div class="titleResS tituloDescripcion">Sugerencia</div>
		  								<p><s:property value="%{#detallesRestriccion[1]}"/></p>
									</s:if>
									<s:else>
										<div class="titleResS tituloDescripcion">Mensaje</div>
										<p align="justify">${descripcion}</p>
									</s:else>
									</div>
									<s:if test="#tamMensajes == #statMensajes.index+1">
										<s:if test="idTipoRegistro eq 2" >
											<s:if test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE}">
												<div id="inputs_r${idRegistro}" class="inputs">
													<div class="titleResS tituloDescripcion">Detalles de acción</div>
													<s:textarea id="detallesRestriccion_%{idRegistro}" cssClass="areaText" maxlenght="100"></s:textarea>
													<div class="controles">
														<s:checkbox name="estado_%{idRegistro}" id="estado_%{idRegistro}" label="Habilitar edición"/><label>Habilitar edición </label>
														<sj:a id="btnAtender%{idRegistro}" onclick="atencionInmediata('%{idRegistro}');" button="true" cssStyle="display:none;" href="#">Atender</sj:a>
														<sj:a id="btnTurnar%{idRegistro}" onclick="turnar('%{idRegistro}');" button="true" cssStyle="display:none;" href="#">Turnar</sj:a>
														<sj:a id="btnCancelar%{idRegistro}" button="true" href="#" onclick="cancelar('%{idRegistro}');">Cancelar</sj:a>
													</div>
												</div>
												<div id="controles_r${idRegistro}" class="controles">
													<sj:a id="btnAtenderRestriccion%{idRegistro}" button="true" href="#" onclick="atenderRestriccion('%{idRegistro}');">Atender restricción</sj:a>
													<sj:a id="btnTurnarRestriccion%{idRegistro}" button="true" href="#" onclick="turnarRestriccion('%{idRegistro}');">Turnar restricción</sj:a>
												</div>
											</s:if>
										</s:if>
										<s:if test="idTipoRegistro eq 4" >
											<s:if test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@SECRETARIO}">
												<div id="inputs_r${idRegistro}" class="inputs">
													<div class="titleResS tituloDescripcion">Detalles de accion</div>
													<s:textarea id="detallesRestriccion_%{idRegistro}" cssClass="areaText" maxlenght="100"></s:textarea>
													<div class="controles">
														<sj:a id="btnAtender%{idRegistro}" button="true" href="#" onclick="atencionSecretaria('%{idRegistro}');" >Atender</sj:a>
														<sj:a id="btnCancelar%{idRegistro}" button="true" href="#" onclick="cancelar('%{idRegistro}');">Cancelar</sj:a>
													</div>
												</div>
												<div id="controles_r${idRegistro}" class="controles">
													<sj:a id="btnAtenderRestriccion%{idRegistro}" button="true" href="#" onclick="atenderRestriccion('%{idRegistro}');">Atender restricción</sj:a>
												</div>
											</s:if>
										</s:if>
										<s:if test="idTipoRegistro eq 5" >
											<s:if test="%{#session.usuario.idPerfilUsuario eq @mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@GERENTE}">
												<div id="inputs_r${idRegistro}" class="inputs">
													<div class="titleResS tituloDescripcion">Detalles de accion</div>
													<s:textarea id="detallesRestriccion_%{idRegistro}" cssClass="areaText" maxlenght="100"></s:textarea>
													<div class="controles">
														<s:checkbox name="estado_%{idRegistro}" id="estado_%{idRegistro}" label="Habilitar edición"/><label>Habilitar edición </label>
														<sj:a id="btnAtender%{idRegistro}" button="true" href="#" onclick="atencionRestriccionSecretaria('%{idRegistro}');" >Atender</sj:a>
														<sj:a id="btnCancelar%{idRegistro}" button="true" href="#" onclick="cancelar('%{idRegistro}');">Cancelar</sj:a>
													</div>
												</div>
												<div id="controles_r${idRegistro}" class="controles">
													<sj:a id="btnAtenderRestriccion%{idRegistro}" button="true" href="#" onclick="atenderRestriccion('%{idRegistro}');">Atender restricción</sj:a>
												</div>
											</s:if>
										</s:if>
									</s:if>
								</div>
							</s:iterator>
						</div>
					</s:iterator>
				</div>
			</s:form>
		</div>
	</body>
</html>
</jsp:root>
