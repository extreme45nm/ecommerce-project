<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Switch Form</title>
</head>
<body>
	<h1>Add switch information</h1>

	<c:choose>
		<c:when test="${switchId == null}">
			<c:set var="action" value="add" />
		</c:when>
		<c:otherwise>
			<c:set var="action" value="../add" />
		</c:otherwise>
	</c:choose>

	<form:form method="POST" action="${action}">
		<table>
			<tr>
				<td><form:label path="switchName">Switch Name:</form:label></td>
				<td><form:input path="switchName" /></td>
				<td><form:errors path="switchName" /></td>
			</tr>
			<tr>
				<td><form:label path="manufacturerId">Manufacturer Id:</form:label></td>
				<td><form:input path="manufacturerId" /></td>
				<td><form:errors path="manufacturerId" /></td>
			</tr>
			<tr>
				<td><form:label path="switchForce">Switch Force: </form:label></td>
				<td><form:input path="switchForce"></form:input></td>
				<td><form:errors path="switchForce"></form:errors></td>
			</tr>

			<tr>
				<td colspan="3"><input type="submit" value="Submit"></td>
			</tr>

		</table>
	</form:form>

	<c:choose>
		<c:when test="${switchId != null}">
			<h1>Upload a image of the switch</h1>
			<form method="post" action="../images/save"
				enctype="multipart/form-data">
				<input type="hidden" name="switchId" value="${switchId}" /> <input
					type="file" name="file" /> <input type="submit" value="Upload" />
			</form>
		</c:when>
	</c:choose>

</body>
</html>