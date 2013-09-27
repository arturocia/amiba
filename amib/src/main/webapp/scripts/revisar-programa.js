$(document).ready(function() {
	logger.trace("Dom inicializado");
	logger.trace("Fin document ready");
	

		$("#tblProyecto").dataTable();

	
});

function tipo() {
	var seleccionado = $(this).val();
	var begin;
	var end;
	var id;
	logger.trace("el tipo seleccionado es " + seleccionado);
	if (seleccionado.search("Proyecto") != -1) {

		begin = seleccionado.search("_") + 1;
		end = seleccionado.length;

		id = seleccionado.substring(begin, end);

		$("#revision").val(
				$("#hdnRutaContexto").val() + "/aprobar-proyecto/" + id
						+ "/edit");
	} else {
		$("#revision").val("");
	}
}

function valida() {
	if ($("#revision").val() == "") {
		alert("Favor de seleccionar un proyecto");
	} else {
		logger.trace($("#revision").val());

		window.location = $("#revision").val();
	}
}

function generarGantt(root) {
	logger.trace("Se recibe como raiz " + root);
	raiz = root;
	logger.trace("RAiz nombre: " + raiz.nombre);

	proyecto = new cdt.Project(raiz.nombre, cdt.Escala.MESES,
			raiz.periodo.fechaInicio, raiz.periodo.fechaFinCalculada);
	mapeaNodoAProject(raiz, proyecto);
	cdt.render.renderProject(proyecto, "prjDiv", true);
	logger.trace("Generar gant listo");
};

function mapeaNodoAProject(nodo, nodoProject) {
	var nodoHijo;
	var nodoProjectHijo;
	var estadoNodo;

	logger.trace("Mapear ..... ");
	if (nodo.nodosHijo) {
		for ( var int = 0; int < nodo.nodosHijo.length; int++) {
			var dif = new Date();
			nodoHijo = nodo.nodosHijo[int];
			// logger.trace("El nodooooooooooooooo "
			// + introspect("proy", nodoHijo, "-", 2));
			if (nodoHijo != null
					&& ((nodoHijo.tipoNodo.match(/.*Proyecto.*/) && (nodoHijo.estadoEjecucion
							|| nodoHijo.estadoRevision || nodoHijo.estadoCerrado)) || (!nodoHijo.tipoNodo
							.match(/.*Proyecto.*/) && !nodoHijo.tipoNodo
							.match(/.*Accion.*/)))) {
				logger.debug("Su tipo es " + nodoHijo.$dwrClassName);
				logger
						.debug("El nodo " + nodoHijo.nombre
								+ " tiene fecha fin antes "
								+ nodoHijo.periodo.fechaFin);
				estadoNodo = calcularEstadoNodo(nodoHijo);
				logger.debug("El nodo " + nodoHijo.nombre
						+ " tiene fecha fin despues "
						+ nodoHijo.periodo.fechaFin);
				nodoProjectHijo = new cdt.Task(
						nodoHijo.tipoNodo + dif.getTime() + "_" + nodoHijo.id,
						nodoHijo.nombre,
						nodoHijo.periodo.fechaInicio,
						nodoHijo.periodo.fechaFin,
						nodoHijo.avance,
						estadoNodo,
						nodoHijo.restriccionesTurnadas ? nodoHijo.restriccionesTurnadas
								: 0,
						nodoHijo.restriccionesNoAtendidas ? nodoHijo.restriccionesNoAtendidas
								: 0,
						nodoHijo.restriccionesAtendidas ? nodoHijo.restriccionesAtendidas
								: 0);
				if (nodoHijo.avance && nodoHijo.avance > 0) {
					logger.warn("Nombre: " + nodoHijo.nombre + "Avance: "
							+ nodoHijo.avance)
				}
				mapeaNodoAProject(nodoHijo, nodoProjectHijo);
				nodoProject.addTask(nodoProjectHijo);
			}
		}
	}
};

function calcularEstadoNodo(nodo) {
	var estado;
	if (nodo.periodo.fechaInicio && nodo.periodo.duracion
			&& nodo.periodo.duracion > 0) {
		logger.trace("Al nodo se le calculara la fecha fin "
				+ nodo.periodo.duracion);
		nodo.periodo.fechaFin = nodo.periodo.fechaInicio.clone();
		nodo.periodo.fechaFin.increment("day", nodo.periodo.duracion);
	}
	if (nodo.periodo.fechaInicio && nodo.periodo.fechaFin) {
		estado = cdt.Task.Estado.EN_TIEMPO;
	} else {
		estado = cdt.Task.Estado.INDEFINIDA;
	}
	return estado;
};
