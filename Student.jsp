<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Details</title>
</head>
<body>

    <h2>Student Information</h2>
    <p><strong>Name:</strong> <%= request.getParameter("name") %></p>
    <p><strong>Email:</strong> <%= request.getParameter("email") %></p>
    <p><strong>Course:</strong> <%= request.getParameter("course") %></p>
    <p><strong>Age:</strong> <%= request.getParameter("age") %></p>

    <a href="index.jsp">Go Back</a>

</body>
</html>
