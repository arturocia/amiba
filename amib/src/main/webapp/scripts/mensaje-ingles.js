jQuery(document).ready(function() {
      eliminarErroresEnInglesStruts();
});

function eliminarErroresEnInglesStruts() {
	var contenido = "";
	$("span").each(
			function(index, item) {
				contenido = $(item).text();
				// console.log("El item es " + contenido);
				if (contenido.match(/field/)) {
					// $(item).attr("style","display:none");
					// $(item).attr("value",));
					$(item).text(
							"El valor del campo \""
									+ $(item).text().split("\"")[1]+ "\"" + "es incorrecto, favor de introducir un dato válido. ");
				}
			});
};