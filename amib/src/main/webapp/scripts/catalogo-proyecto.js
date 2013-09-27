/**
 * 
 */
$(function() {
	$("#tblProyecto").dataTable();
	});

$(function() {
	$("#tblProyectoPreRegistrado").dataTable
	(
		{
	        "aaSorting": [[ 6, "desc" ],[7,"asc"]]
		}
	);
});