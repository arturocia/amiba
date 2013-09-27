<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags"
	xmlns:sjt="/struts-jquery-tree-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Datos del proyecto</title>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/datosProyecto.css" />
<jsp:text>
	<![CDATA[ 
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-core.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/mootools-more.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/struts/utils.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/struts/js/jstree/jquery.jstree.js" ></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/editar-datos-proyecto.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>
	<s:url id="urlRegresar" value="/" includeContext="true" />
	<s:url id="urlCancelar" action="proyectos-disponibles"
		includeContext="true" />
	<s:url id="urlEditar"
		value="/operacion-editar-datos-proyecto/%{model.id}/edit"
		includeContext="true" />
	<s:actionmessage id="algo" theme="jquery" />
	<s:actionerror theme="jquery" />
	<s:fielderror theme="jquery" />
	<s:form theme="simple" acceptcharset="UTF-8"
		action="%{#request.contextPath}/operacion-editar-datos-proyecto/%{model.id}"
		id="frmProyecto" method="post">
		<s:hidden id="hdnMethod" name="_method" value="put" />
		<h1>Datos del proyecto</h1>
		<center>

			<table border="0" id="tblDatosProyecto">
				<tr class="dtsProy">
					<td>Nombre:</td>
					<td><label>${usuarioActual.nombre}</label></td>
				</tr>
				<tr class="dtsProy">
					<td>Apellido Paterno:</td>
					<td><label>${usuarioActual.apPat}</label></td>
				</tr>

				<tr class="dtsProy">
					<td>Apellido Materno:</td>
					<td><label>${usuarioActual.apMat}</label></td>
				</tr>
				<tr class="dtsProy">
					<td>Dirección:</td>
					<td><label>${usuarioActual.area.nombre}</label></td>
				</tr>
				<tr class="dtsProy">
					<td>Cargo:</td>
					<td><label>${usuarioActual.cargo}</label></td>
				</tr>
				<tr class="dtsProy">
					<td>Teléfono:</td>
					<td><label>${tel}</label></td>
				</tr>
				<tr class="dtsProy">
					<td>Correo: <br /></td>
					<td><label>${mail}</label></td>
				</tr>
				<tr>
					<td><br /> <br /> <br /></td>
					<td><br /> <br /> <br /></td>
				</tr>
				<tr class="dtsProy">
					<td>Nombre del proyecto:</td>
					<td><label>${model.nombre}</label></td>
				</tr>
				<tr class="dtsProy">
					<td>Costo total:</td>
					<td><label><s:property
								value="getText('$ {0,number,#,##0.00}',{model.costoTotal})" />
					</label></td>
				</tr>
				<tr class="dtsProy">
					<td>Siglas:</td>
					<td><label>${model.siglas}</label></td>
				</tr>
				<tr class="dtsProy">
					<td>Resumen</td>
					<td><label>${model.resumen}</label></td>
				</tr>
				<tr class="dtsProy">
					<td>Objetivo general:</td>
					<td><label>${model.objetivoGeneral}</label></td>
				</tr>
			</table>

		</center>


		<center>
			<!-- <div>  -->
			<s:if
				test="%{banderaTipoPeriodo eq @mx.ipn.escom.cdt.besp.controller.CatalogoEstructuraController@BANDERA_DURACION}">
				<table>
					<tr>
						<td colspan="2"><div style="text-align: center">Periodo</div>
						</td>
					</tr>
					<tr>
						<td>Por duración:</td>
						<td><s:property value="model.periodo.duracion" /> días<br />
							<s:if test="model.periodo.fechaInicio">
							</s:if>
						</td>
					</tr>
				</table>

			</s:if>

			<s:if
				test="%{banderaTipoPeriodo eq @mx.ipn.escom.cdt.besp.controller.CatalogoEstructuraController@BANDERA_FECHA}">
				<table>
					<tr>
						<td colspan="2"><div style="text-align: center">Periodo
								por Fechas</div></td>
					</tr>
					<tr>
						<td>Inicio: <s:date name="model.periodo.fechaInicio"
								format="yyyy-MM-dd" />
						</td>

						<td>Fin: <s:date name="model.periodo.fechaFin"
								format="yyyy-MM-dd" />
						</td>
					</tr>
				</table>
			</s:if>

			<s:if
				test="%{banderaTipoPeriodo eq @mx.ipn.escom.cdt.besp.controller.CatalogoEstructuraController@BANDERA_INDEFINIDO}">
				<table>
					<tr>
						<td>Periodo:</td>
						<td>Indefinido</td>
					</tr>
				</table>
			</s:if>
			<!-- 	</div>  -->
			<s:hidden value="%{banderaTipoPeriodo}" id="hdnBanderaTipoPeriodo" />
		</center>

		<div>
			<BR /> <br />
			<s:if test="%{alineacionSectorial}">
			Alineado con el programa sectorial:<br />
				<s:property value="caminosMigajas[0]" />
				<br />
			</s:if>
			<s:if test="%{alineacion}">
			Alineado con otro programa:
			<s:property value="caminosMigajas[1]" />
			</s:if>
		</div>

		<center>
			<table border="0">
				<tr>
					<td>Ejes temáticos</td>
					<td><ul>
							<s:iterator value="ejeTematicosSelecccionados">
								<li>${nombre}</li>
							</s:iterator>
						</ul></td>
				</tr>
				<tr>
					<td>Temas transversales</td>
					<td><ul>
							<s:iterator value="temaTransversalSeleccionados">
								<li>${nombre}</li>
							</s:iterator>
						</ul>
					</td>
				</tr>
			</table>
		</center>


		<s:hidden id="hdnIdEstructuraSectorial"
			name="idEstructuraSectorialSel" />
		<s:hidden id="hdnIdEstructura" name="idEstructuraSel" />

		<s:if
			test="%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIO].idPerfilUsuario eq
		@mx.ipn.escom.cdt.besp.modelo.PerfilUsuario@COORDINADOR and ( #session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idEstado == @mx.ipn.escom.cdt.besp.modelo.Estado@EDICION or
		 #session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@PROYECTO].idEstado == @mx.ipn.escom.cdt.besp.modelo.Estado@REGISTRADO)}">

			<sj:a id="btnEditar" button="true"
				onclick="window.location='%{urlEditar}'">Asociar proyecto</sj:a>
			<sj:a id="btnCancelar" button="true"
				onclick="window.location='%{urlCancelar}'">Cancelar</sj:a>
		</s:if>


	</s:form>
</body>
	</html>
</jsp:root>