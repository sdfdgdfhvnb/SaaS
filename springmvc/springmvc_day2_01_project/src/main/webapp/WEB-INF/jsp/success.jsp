<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>springmvc入门案例程序页面</title>
</head>
<body>
        你好，世界！${hello}

<hr/>
<form action="${pageContext.request.contextPath}/requestMapping.do" method="post">
    <input type="submit" value="学习POST请求"/>
</form>
</body>
</html>
