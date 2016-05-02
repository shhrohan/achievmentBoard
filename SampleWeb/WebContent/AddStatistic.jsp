<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Statistics</title>
</head>
<body>
	<form  action="AddStatistic" method="post">
		<table align="center" style="width: 100%">
			<tr>
				<th align="center">Name</th>
				<th align="center">Weight</th>
				<th align="center">Value</th>
			</tr>

			<tr>
				<td align="center"><input type="text" name="name" /></td>
				<td align="center"><input type="text" name="weight" /></td>
				<td align="center"><input type="text" name="value" /></td>

			</tr>
			<tr>
				<td colspan="3" align="center"><input type="submit" value="Add" /> </td>
			</tr>

		</table>

	</form>

</body>
</html>