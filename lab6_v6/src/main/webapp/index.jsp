<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Image Viewer</title>
</head>
<body>
<h1>Image Viewer</h1>
<form action="image" method="get">
    <label for="filename">Image filename:</label>
    <input type="text" name="filename" id="filename">
    <br>
    <input type="submit" value="View Image">
</form>
</body>
</html>