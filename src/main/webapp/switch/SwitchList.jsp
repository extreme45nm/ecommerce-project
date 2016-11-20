<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>List Switch</title>
	</head>
	
	<body>
		<h1>Switch</h1>
		<table border="2">
			<tr>
				<form method="GET" action ="list">
					<td colspan="4"><input type="text" name="querry" size="30"/></td>
					<td><input type="submit" value="Submit"/></td>
				</form>
			</tr>
			<tr>
				<td>#</td> <td>#</td> <td>ManufacturerID</td>	<td>Name</td>	<td>Force</td> 
			</tr>
			
			<c:forEach items="${switchh}" var="switchh">
			<tr>
			<td><a href="delete/${switchh.switchId}">Delete</a></td>	
			<td><a href="edit/${switchh.switchId}">Edit</a></td> 
			<td>${switchh.switchId}</td> 
			<td><a href="view/${switchh.switchId}">${switchh.switchName}</a></td>
			<td>${switchh.switchForce}</td>
			</c:forEach>
		</table>
	</body>
