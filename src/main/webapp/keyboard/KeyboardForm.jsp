<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>Add new keyboard</title>
	</head>
	
	<body>
		<h2>Please input keyboard for sale</h2>
		
		<c:choose>
			<c:when test="${productId == null}">
				<c:set var="action" value = "add"/>
			</c:when>
			<c:otherwise>
				<c:set var="action" value="../add"/>
			</c:otherwise>
		</c:choose>
		
		<form:form method="POST" action="${action}">
			<table>
				<tr>
					<td><form:label path="productId">Product Id:</form:label></td>
					<td><form:input path="productId"/></td>
					<td><form:errors path="productId"/></td>
				</tr>
				<tr>
					<td><form:label path="name">Name:</form:label></td>
					<td><form:input path="name"/></td>
					<td><form:errors path="name"/></td>
				</tr>
				<tr>
					<td><form:label path="manufacturerId">Manufacturer Id:</form:label></td>
					<td><form:input path="manufacturerId"/></td>
					<td><form:errors path="manufacturerId"/></td>
				</tr>
				<tr>
					<td><form:label path="switchId">SwitchId:</form:label></td>
					<td><form:input path="switchId"/></td>
					<td><form:errors path="switchId"/></td>
				</tr>
				<tr>
					<td><form:label path="profileId">ProfileId:</form:label></td>
					<td><form:input path="profileId"/></td>
					<td><form:errors path="profileId"/></td>
				</tr>
				<tr>
					<td><form:label path="price">Price:</form:label></td>
					<td><form:input path="price"/></td>
					<td><form:errors path="price"/></td>
				</tr>
				<tr><td colspan="3"><input type="submit" value="Submit"></td> </tr>
			
			</table>		
		</form:form>
		
		<c:choose>
			<c:when test="${productId != null}">
				<h1>Upload a image of your keyboard</h1>
				<form method="post" action="../images/save" enctype="multipart/form-data">
					<input type="hidden" name="productId" value="${productId}"/>
					<input type="file" name="file"/>
					<input type="submit" value="Upload"/>
				</form>
			</c:when>
		</c:choose>	
		
	</body>

</html>