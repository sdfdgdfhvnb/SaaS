<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>restful学习</title>
</head>
<body>

<h3>rest请求 - post</h3>
<form action="${pageContext.request.contextPath}/item/add" method="post">
    商品名称： <input type="text" name="name"><br/>
    <input type="hidden" name="_method" value="POST">
    <input type="submit" value="新增">
</form>

<h3>rest请求 - put</h3>
<form action="${pageContext.request.contextPath}/item/1" method="post">
    商品名称： <input type="text" name="name"><br/>
    <input type="hidden" name="_method" value="PUT">
    <input type="submit" value="修改">
</form>

<h3>rest请求 - get</h3>
<form action="${pageContext.request.contextPath}/item/1" method="post">
    <input type="hidden" name="_method" value="GET">
    <input type="submit" value="查询">
</form>

<h3>rest请求 - delete</h3>
<form action="${pageContext.request.contextPath}/item/1" method="post">
    <input type="hidden" name="_method" value="DELETE">
    <input type="submit" value="删除">
</form>

</body>

</html>
