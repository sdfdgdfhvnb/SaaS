/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.37
 * Generated at: 2019-09-15 11:28:14 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.item;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class list_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\r\n");
      out.write("\"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("    <title>查询商品列表</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("<p>我是重定向过来的，【不】可以获取到request中的数据：");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${param.itemId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("</p>\r\n");
      out.write("<hr/>\r\n");
      out.write("<form action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/queryItem.do\"\r\n");
      out.write("      method=\"post\">\r\n");
      out.write("    查询条件：\r\n");
      out.write("    <table width=\"100%\" border=1>\r\n");
      out.write("        <tr>\r\n");
      out.write("            <td>\r\n");
      out.write("                商品名称：<input type=\"text\" name=\"item.name\" value=\"\"/>\r\n");
      out.write("               ");
      out.write("\r\n");
      out.write("                <input type=\"submit\" value=\"查询\" />\r\n");
      out.write("            </td>\r\n");
      out.write("        </tr>\r\n");
      out.write("    </table>\r\n");
      out.write("    商品列表：<input type=\"submit\" value=\"批量删除商品\"/>\r\n");
      out.write("    <table width=\"100%\" border=1>\r\n");
      out.write("        <tr>\r\n");
      out.write("            <td>商品Id</td>\r\n");
      out.write("            <td>商品名称</td>\r\n");
      out.write("            <td>商品价格</td>\r\n");
      out.write("            <td>生产日期</td>\r\n");
      out.write("            <td>商品描述</td>\r\n");
      out.write("            <td>操作</td>\r\n");
      out.write("        </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("                <td><input type=\"checkbox\" name=\"ids\" value=\"1\"/></td>\r\n");
      out.write("                <td>笔记本</td>\r\n");
      out.write("                <td>8000</td>\r\n");
      out.write("                <td>2018-07-15 17:22:30</td>\r\n");
      out.write("                <td>国产的质量越来越好了，放心使用</td>\r\n");
      out.write("                <td>\r\n");
      out.write("                    <a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/queryItemById.do?id=1\">修改</a>\r\n");
      out.write("                </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("                <td><input type=\"checkbox\" name=\"ids\" value=\"2\"/></td>\r\n");
      out.write("                <td>台式机</td>\r\n");
      out.write("                <td>5000</td>\r\n");
      out.write("                <td>2018-07-15 17:22:30</td>\r\n");
      out.write("                <td>国产的质量越来越好了，放心使用</td>\r\n");
      out.write("                <td>\r\n");
      out.write("                    <a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/queryItemById.do?id=2\">修改</a>\r\n");
      out.write("                </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("\r\n");
      out.write("    </table>\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("<hr/>\r\n");
      out.write("<p>批量修改商品：</p>\r\n");
      out.write("<form action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/queryItem.do\"\r\n");
      out.write("      method=\"post\">\r\n");
      out.write("    商品列表：<input type=\"submit\" value=\"批量修改商品\"/>\r\n");
      out.write("    <table width=\"100%\" border=1>\r\n");
      out.write("        <tr>\r\n");
      out.write("            <td>商品名称</td>\r\n");
      out.write("            <td>商品价格</td>\r\n");
      out.write("            <td>生产日期</td>\r\n");
      out.write("            <td>商品描述</td>\r\n");
      out.write("            <td>操作</td>\r\n");
      out.write("        </tr>\r\n");
      out.write("        <tr>\r\n");
      out.write("            ");
      out.write("\r\n");
      out.write("            <input type=\"hidden\"  name=\"itemList[0].id\" value=\"1\"/>\r\n");
      out.write("            <td><input type=\"text\" name=\"itemList[0].name\" value=\"笔记本\"/></td>\r\n");
      out.write("            <td><input type=\"text\" name=\"itemList[0].price\" value=\"8000\"/></td>\r\n");
      out.write("            <td><input type=\"text\" name=\"itemList[0].createtime\" value=\"2018-07-15 17:22:30\"/></td>\r\n");
      out.write("            <td><input type=\"text\" name=\"itemList[0].detail\" value=\"国产的质量越来越好了，放心使用\"/></td>\r\n");
      out.write("            <td>\r\n");
      out.write("                <a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/queryItemById.do?id=1\">修改</a>\r\n");
      out.write("            </td>\r\n");
      out.write("        </tr>\r\n");
      out.write("        <tr>\r\n");
      out.write("            <input type=\"hidden\"  name=\"itemList[1].id\" value=\"2\"/>\r\n");
      out.write("            <td><input type=\"text\" name=\"itemList[1].name\" value=\"台式机\"/></td>\r\n");
      out.write("            <td><input type=\"text\" name=\"itemList[1].price\" value=\"5000\"/></td>\r\n");
      out.write("            <td><input type=\"text\" name=\"itemList[1].createtime\" value=\"2018-07-15 17:22:30\"/></td>\r\n");
      out.write("            <td><input type=\"text\" name=\"itemList[1].detail\" value=\"国产的质量越来越好了，放心使用\"/></td>\r\n");
      out.write("            <td>\r\n");
      out.write("                <a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/queryItemById.do?id=2\">修改</a>\r\n");
      out.write("            </td>\r\n");
      out.write("        </tr>\r\n");
      out.write("\r\n");
      out.write("    </table>\r\n");
      out.write("</form>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}