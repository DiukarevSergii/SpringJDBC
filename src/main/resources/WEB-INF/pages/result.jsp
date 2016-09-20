<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Result</title>
</head>
<body>
<div align="center">
    <h1>Test database: </h1>
    <c:forEach items="${list}" var="line">
        <br/>${line}
    </c:forEach>
    <br/><input type="submit" value="To main page" onclick="window.location='/'"/>


</div>
</body>
</html>