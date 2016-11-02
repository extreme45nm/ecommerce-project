<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>Mechky- All products</title>
	</head>
	
	<body>
		<h1>All products</h1>
		<table border="2">
			<tr>
				<form method="GET" action ="list">
					<td colspan="4"><input type="text" name="querry" size="30"/></td>
					<td><input type="submit" value="Submit"/></td>
				</form>
			</tr>
			<tr>
				<td>#</td> <td>#</td> <td>productID</td>	<td>Name</td>	<td>ManufacturerID</td>	<td>SwitchId</td> <td>ProfileId</td>	<td>Price</td>
			</tr>
			
			<c:forEach items="${keyboard}" var = "keyboard">
			<tr>
			<td><a href="delete/${keyboard.productId}">Delete</a></td>	<td><a href="edit/${keyboard.productId}">Edit</a></td> <td>${keyboard.productId}</td> <td><a href="view/${keyboard.productId}">${keyboard.name}</a></td> <td>${keyboard.manufacturerId}</td> <td>${keyboard.switchId}</td> <td>${keyboard.profileId}</td> <td>${keyboard.price}</td>
			</tr>
			</c:forEach>
		</table>
	</body>
</html>