<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:s="/struts-tags" xmlns:sj="/struts-jquery-tags">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />

	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Indice de estructura</title>
<jsp:text>
	<![CDATA[ 
			<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/reportar-avance-proyecto.js" ></script>
		 ]]>
</jsp:text>
</head>
<body>

	<center>
		<h1>Reportar avance</h1> <br/>
		<h2>
			<s:property value="%{accionSel.nombre}" />
		</h2>
	</center>
	<s:actionerror theme="jquery" />
	<s:fielderror theme="jquery" />
	<s:url id="urlCancelar" value="/operacion-revisar-avances/%{idSel}" includeContext="true" />
	<!-- 	<s:property value="%{idProyecto}"/> -->

	<s:form method="post"
		action="%{#request.contextPath}/reportar-avance-proyecto!update?%{idSel}"
		theme="simple" id="frmIndicador">

		<table>
			<thead>
				<tr>
					<th colspan="5"><div style="text-align: center;">
							Indicadores <br />
						</div></th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="list" status="estatus" var="indicador">
					<tr>
						<td colspan="5"><div style="text-align: center;">
								<br />
								<hr />
							</div>
						</td>
					</tr>
					<tr>
						<td rowspan="2"><s:property value="#estatus.index+1" />
						</td>
						<td><div style="text-align: left;">
								<b>Descripción: </b> ${descripcion}
							</div>
						</td>
						<td><div style="text-align: left;">
								<b>Meta: </b> ${meta}
							</div>
						</td>
						<td><div style="text-align: left;">
								<b>Peso: </b> ${peso}
							</div>
						</td>
						<!-- <td rowspan="2"><input type="checkbox" name="idIndicadoresModificados"
							id="chkbxModificado_${idIndicador}" value="${idIndicador}"
							class="indicaModificacion" /> </td>-->
					</tr>
					<tr>


						<td><div style="text-align: left;">
								<b>Último Reporte: </b>
								<s:date name="fechaUltimoReporte" format="yyyy-MM-dd" />
								<b>Avance actual: </b>
								<s:property value="listaAvancesMap[#indicador.idIndicador]" />
							</div></td>
						<td><div style="text-align: left;">
								<b>Reportar avance </b> <input type="checkbox"
									name="idIndicadoresModificados"
									id="chkbxModificado_${idIndicador}" value="${idIndicador}"
									class="indicaModificacion" />
								<s:textfield name="list[%{#estatus.index}].avance"
									disabled="false" id="txtAvance_%{idIndicador}"
									cssStyle="display:none;" size="10" />
							</div>
						</td>
						
						<td><div style="text-align: left;">
								<b>barra avance</b>
							</div>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<!-- 	<table>
			<thead>
				<tr>
					<th>Descripción</th>
					<th>Unidad</th>
					<th>Meta</th>
					<th>Avance actual</th>
					<th>Modificar</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="list" status="estado">
					<tr>
						<td>${descripcion}</td>
						<td>${unidad.nombre}</td>
						<td>${meta}</td>
						<td><s:textfield name="list[%{#estado.index}].avance"
								disabled="true" id="txtAvance_%{idIndicador}" /></td>
						<td><input type="checkbox" name="idIndicadoresModificados"
							id="chkbxModificado_${idIndicador}" value="${idIndicador}"
							class="indicaModificacion" /></td>
					</tr>
				</s:iterator>
			</tbody>
		</table> -->
		<sj:a id="btnAceptar" button="true" onclick=""
			href="#" formIds="frmIndicador">Aceptar</sj:a>

		<sj:a button="true" href="#"
			onclick="location.href='%{urlCancelar}'">Cancelar</sj:a>

	</s:form>
</body>
	</html>
</jsp:root>

