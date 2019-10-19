<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>修改商品信息</title>

</head>
<body>
<p>我是Model封装的模型数据：${model}</p>
<!-- 上传图片是需要指定属性 enctype="multipart/form-data" -->
<form id="itemForm"
      action="${pageContext.request.contextPath }/updateItem.do"
      method="post">

    修改商品信息：
    <input type="hidden" name="id" value="1" />
    <table width="100%" border=1>
        <tr>
            <td>商品名称</td>
            <td><input type="text" name="name" value="笔记本" /></td>
        </tr>
        <tr>
            <td>商品价格</td>
            <td><input type="text" name="price" value="8000" /></td>
        </tr>

         <tr>
            <td>商品生产日期</td>
            <td><input type="text" name="createtime" value="2018-07-15 17:22:30" /></td>
        </tr>
        <%--
            <tr>
                <td>商品图片</td>
                <td>
                    <img src="http://127.0.0.1:8081/pic/1.jpg" width=100 height=100/>
                    <input type="file"  name="pictureFile"/>
                </td>
            </tr>
             --%>
        <tr>
            <td>商品简介</td>
            <td><textarea rows="3" cols="30" name="detail">国产的质量越来越好了，放心使用</textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="提交" />
            </td>
        </tr>
    </table>

</form>
</body>

</html>