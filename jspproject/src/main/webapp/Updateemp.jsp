<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Employee</title>
</head>
<body>
	<form action="Employees" method="post">
		<!--need to mention method = post  -->

			<div>
				ID : <input type='text' name='id'>
			</div>
			<div>
				First Name : <input type='text' name='fname'>
			</div>
<div>
				Last Name : <input type='text' name='lname'>
			</div>
			<div>
				E-mail : <input type='text' name='email'>
			</div>
			<div>
				Hire Date : <input type='text' name='date'>
			</div>
			<div>
				Job ID : <input type='text' name='jobid'>
			</div>
			<div>
				Salary : <input type='text' name='salary'>
			</div>
			<div>
			 <input
				type='submit'name='click' value='UPDATE_EMP'>
				</div>
	</form>
</body>
</html>