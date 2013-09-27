<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<script type="text/javascript">
	function errorpage(){
		//alert("Hola");
		var contextPath = document.getElementById("contexPath").value;
		//alert("P: "+contextPath);
		window.location=contextPath+"/handler-error!notFound";
	}
</script>
</head>
<body onload="errorpage();">
	<input id="contexPath" type="hidden" value="${pageContext.request.contextPath}"/>
</body>
</html>