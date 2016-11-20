<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>List Manufacturers</title>
	</head>
	
	<body>
		<h1>Manufacturers</h1>
		<table border="2">
			<tr>
				<form method="GET" action ="list">
					<td colspan="4"><input type="text" name="querry" size="30"/></td>
					<td><input type="submit" value="Submit"/></td>
				</form>
			</tr>
			<tr>
				<td>#</td> <td>#</td> <td>ManufacturerID</td>	<td>Name</td>	<td>Location</td> 
			</tr>
			
			<c:forEach items="${manufacturer}" var = "manufacturer">
			<tr>
			<td><a href="delete/${manufacturer.manufacturerId}">Delete</a></td>	
			<td><a href="edit/${manufacturer.manufacturerId}">Edit</a></td> 
			<td>${manufacturer.manufacturerId}</td> 
			<td><a href="view/${manufacturer.manufacturerId}">${manufacturer.name}</a></td>
			<td>${manufacturer.location}</td>
			</c:forEach>
		</table>
	</body>
