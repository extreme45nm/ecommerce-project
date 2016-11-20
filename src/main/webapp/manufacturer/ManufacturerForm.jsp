<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Manufacturer Form</title>
</head>
<body>
	<h1>Add Manufacturer information</h1>

	<c:choose>
		<c:when test="${manufacturerId == null}">
			<c:set var="action" value="add" />
		</c:when>
		<c:otherwise>
			<c:set var="action" value="../add" />
		</c:otherwise>
	</c:choose>

	<form:form method="POST" action="${action}">
		<table>
			<tr>
				<td><form:label path="name">Manufacturer Name:</form:label></td>
				<td><form:input path="name" /></td>
				<td><form:errors path="name" /></td>
			</tr>
			
			<tr>
				<td><form:label path="location">Location: </form:label></td>
				<td><form:input path="location"></form:input></td>
				<td><form:errors path="location"></form:errors></td>
			</tr>

			<tr>
				<td colspan="3"><input type="submit" value="Submit"></td>
			</tr>

		</table>
	</form:form>

	<c:choose>
		<c:when test="${manufacturerId != null}">
			<h1>Upload a image of the manufacturer</h1>
			<form method="post" action="../images/save"
				enctype="multipart/form-data">
				<input type="hidden" name="switchId" value="${manufacturerId}" /> <input
					type="file" name="file" /> <input type="submit" value="Upload" />
			</form>
		</c:when>
	</c:choose>

</body>
</html>