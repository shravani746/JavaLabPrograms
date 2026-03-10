<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Simple Student Info System</title>
</head>
<body>

    <h2>Enter Student Details</h2>
    <form action="student.jsp" method="post">
        Name: <input type="text" name="name" required><br>
        Email: <input type="email" name="email" required><br>
        Course: <input type="text" name="course" required><br>
        Age: <input type="number" name="age" required><br>
        <input type="submit" value="Submit">
    </form>

</body>
</html>
