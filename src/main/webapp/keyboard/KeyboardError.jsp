<%@ page isErrorPage="true" import="java.io.*"%>
<html>

	<head>
		<title>Student Error</title>	
	</head>
	<body>
		<%
			Exception exp = (Exception)request.getAttribute("javax.servlet.error.exception");
			out.println(exp.getMessage()+"<br/>");
			StackTraceElement[] elements = exp.getStackTrace();
			for(int i = 0 ; i < elements.length ; i++){
				out.println(elements[i].toString()+"<br/>");
			}
		%>
	</body>
</html>