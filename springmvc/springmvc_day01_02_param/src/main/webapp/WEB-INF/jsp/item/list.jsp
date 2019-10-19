<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查询商品列表</title>
</head>
<body>

转发：${param.itemId}===>request.getParameter("itemId")
<p>我是转发过来的，可以获取到request中的数据：${param.itemId}</p>
<hr>
<form action="${pageContext.request.contextPath }/queryItem.do"
      method="post">
    查询条件：
    <table width="100%" border=1>
        <tr>
            <td>
                商品名称：<input type="text" name="item.name" value=""/>
                <input type="submit" value="查询"/>
            </td>
        </tr>
    </table>
    商品列表：<input type="submit" value="批量删除商品">
    <table width="100%" border=1>
        <tr>
            <td>商品id</td>
            <td>商品名称</td>
            <td>商品价格</td>
            <td>生产日期</td>
            <td>商品描述</td>
            <td>操作</td>
        </tr>
        <tr>
            <td><input type="checkbox" name="ids" value="1"></td>
            <td>笔记本</td>
            <td>8000</td>
            <td>2018-07-15 17:22:30</td>
            <td>国产的质量越来越好了，放心使用</td>
            <td>
                <a href="${pageContext.request.contextPath }/queryItemById.do?id=1">修改</a>
            </td>
        </tr>
        <tr>
            <td><input type="checkbox" name="ids" value="2"/></td>
            <td>台式机</td>
            <td>5000</td>
            <td>2018-07-15 17:22:30</td>
            <td>国产的质量越来越好了，放心使用</td>
            <td>
                <a href="${pageContext.request.contextPath }/queryItemById.do?id=2">修改</a>
            </td>
        </tr>

    </table>
</form>

<hr/>
<p>批量修改商品：</p>
<form action="${pageContext.request.contextPath }/queryItem.do"
      method="post">
    商品列表：<input type="submit" value="批量修改商品"/>
    <table width="100%" border=1>
        <tr>
            <td>商品名称</td>
            <td>商品价格</td>
            <td>生产日期</td>
            <td>商品描述</td>
            <td>操作</td>
        </tr>
        <tr>
            <%--分析集合List绑定过程
                itemList：要绑定的商品集合List
                itemList[0]：要绑定的第一个商品对象
                itemList[0].id：要绑定的第一个商品对象的id属性

            --%>
            <input type="hidden" name="itemList[0].id" value="1"/>
            <td><input type="text" name="itemList[0].name" value="笔记本"/></td>
            <td><input type="text" name="itemList[0].price" value="8000"/></td>
            <td><input type="text" name="itemList[0].createtime" value="2018-07-15 17:22:30"/></td>
            <td><input type="text" name="itemList[0].detail" value="国产的质量越来越好了，放心使用"/></td>
            <td>
                <a href="${pageContext.request.contextPath }/queryItemById.do?id=1">修改</a>
            </td>
        </tr>
        <tr>
            <input type="hidden" name="itemList[1].id" value="2"/>
            <td><input type="text" name="itemList[1].name" value="台式机"/></td>
            <td><input type="text" name="itemList[1].price" value="5000"/></td>
            <td><input type="text" name="itemList[1].createtime" value="2018-07-15 17:22:30"/></td>
            <td><input type="text" name="itemList[1].detail" value="国产的质量越来越好了，放心使用"/></td>
            <td>
                <a href="${pageContext.request.contextPath }/queryItemById.do?id=2">修改</a>
            </td>
        </tr>

    </table>
</form>

</body>

</html>
