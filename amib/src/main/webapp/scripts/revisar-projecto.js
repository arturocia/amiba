$(document).ready(function() {

});

$.subscribe('tabchange', function(event,data)
	{
		var tab = event.originalEvent.ui.index;
	    //logger.trace(tab);
	    switch (tab) {
			case 0:
				document.getElementById('ifDatos').contentWindow.location
						.reload(true);
			break;
			case 1:
				document.getElementById('ifPlaneacion').contentWindow.location
						.reload(true);
			break;
			case 2:
				document.getElementById('ifSeguimiento').contentWindow.location
						.reload(true);
			break;
			case 3:
				document.getElementById('ifAvances').contentWindow.location
						.reload(true);
			break;
		}
	}
);