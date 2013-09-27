var raiz;
var proyecto;
var accion;

$(document).ready(function() {
	logger.trace("Dom inicializado");
	accion = $("#frmEstructura").attr("action");
	CatalogoEstructuraController.getNodos(generarGantt);
	$(".botonEstructura").click(peticionAccion);
});

function recorrerArbol(nodo) {
	var nodoHijo;
	logger.trace("El nodo actual " + nodo.idNivel);
	if (nodo.nodosHijo) {
		for ( var int = 0; int < nodo.nodosHijo.length; int++) {
			nodoHijo = nodo.nodosHijo[int];
			recorrerArbol(nodoHijo);
		}
	}
};

function generarGantt(root) {
	logger.trace("Se recibe como raiz " + root);
	raiz = root;
	recorrerArbol(raiz);
	proyecto = new cdt.Project(raiz.nombre, cdt.Escala.MESES,
					raiz.periodo.fechaInicio, raiz.periodo.fechaFin);
	mapeaNodoAProject(raiz, proyecto);
	cdt.render.renderProject(proyecto, "prjDiv", true);
};

function mapeaNodoAProject(nodo, nodoProject) {
	var nodoHijo;
	var nodoProjectHijo;
	var estadoNodo;
	if (nodo.nodosHijo) {
		for ( var int = 0; int < nodo.nodosHijo.length; int++) {
			nodoHijo = nodo.nodosHijo[int];
			
			logger.debug("El nodo " + nodoHijo.nombre
					+ " tiene fecha fin antes " + nodoHijo.periodo.fechaFin);
			estadoNodo = calcularEstadoNodo(nodoHijo);
			logger.debug("El nodo " + nodoHijo.nombre
					+ " tiene fecha fin despues " + nodoHijo.periodo.fechaFin);
			if(nodoHijo.tipoNodo=="Estructura"){
			nodoProjectHijo = new cdt.Task(nodoHijo.tipoNodo + "_"
					+ nodoHijo.id, nodoHijo.nombre,
					nodoHijo.periodo.fechaInicio, nodoHijo.periodo.fechaFin,
					nodoHijo.avance, estadoNodo);
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

function peticionAccion(event) {
	logger.trace("Se previene");
	var idLink;
	var taskSel;
	var accionModificada;
	var esEstructura;
	var tipoNodo;
	idLink = $(this).attr("id");
	taskSel = $("[name=task]:checked").val();
	if (taskSel) {
		tipoNodo = taskSel.split("_")[0];
		taskSel = taskSel.split("_")[1];
		esEstructura = tipoNodo.match(/Estructura/) ? true : false;
	}
	logger.trace("El id del link es " + idLink + " y la task seleccionada "
			+ taskSel + " y el tipo de nodo " + tipoNodo + " y es estructura "
			+ esEstructura);
	switch (idLink) {
	case "btnAgregar":
		accionModificada = accion + "/new";
		break;
	case "btnAgregarSubnivel":
		if (!taskSel || !esEstructura) {
			alert("Debe elegir una estructura");
			event.preventDefault();
		} else {
			accionModificada = accion + "/new";
			$("#hdnIdEstructuraPadreSel").val(taskSel);
		}
		break;
	case "btnEditar":
		if (!taskSel || !esEstructura) {
			alert("Debe elegir una estructura");
			event.preventDefault();
		} else {
			accionModificada = accion + "/" + taskSel + "/edit";
		}
		break;
	case "btnEliminar":
		if (!taskSel || !esEstructura) {
			alert("Debe elegir una estructura");
			event.preventDefault();
		} else {
			accionModificada = accion + "/" + taskSel + "/deleteConfirm";
		}
		break;
	default:
		break;
	}
	logger.trace("La accion es " + accionModificada);
	$("#frmEstructura").attr("action", accionModificada);
};