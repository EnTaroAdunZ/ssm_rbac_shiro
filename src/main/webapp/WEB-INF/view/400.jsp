<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<% pageContext.setAttribute("APP_PATH", request.getContextPath()); %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html>
<html>
<head>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>"></base>
<meta charset="utf-8">
<title>操作失败！</title>
<link href="assets/css/pintuer.css" rel="stylesheet"/>
<style>
*{ margin:0; padding:0; list-style:none;}
table{border-collapse:collapse;border-spacing:0;}
body,html{ height:100%; font-family:'微软雅黑'; overflow-y:hidden;}
.main{ width:60%; margin-left:20%; margin-right:20%; margin-top:10%;}
.main_left{ width:38%; margin-left:12%; margin-top:10%; float:left;}
.main_right{width:50%; float:left;}
.main_radius{ padding-top:4%; width:75%; height:130px; border-radius:50%; background:#fef2ec; font-size:18px;text-align:center;}
.main_p{ font-family:'华文行楷';}
</style>
</head>

<body>
<div class="main">
   <div class="main_left"><img src="assets/img/img2.png" width="229" height="128"/></div>
   <div class="main_right">
      <div class="main_radius">
         <p class="main_p">后端校验失败</p>
         <p class="main_p">相关信息:${msg}</p>
      </div>
      <div class="text-left" style="margin-top:10%; margin-left:8%;">
          <a href="user/index" class="button bg-yellow">返回首页</a>
      </div>
   </div>
</div>
</body>
</html>
