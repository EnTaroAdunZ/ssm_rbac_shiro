<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <% pageContext.setAttribute("APP_PATH", request.getContextPath()); %>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
    %>

    <base href="<%=basePath%>"/>
    <title>权限管理</title>
    <link rel="stylesheet" href="assets/css/style.default.css" type="text/css"/>
    <script src="assets/js/jquery-1.11.1.min.js"></script>
    <link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <style>
        a {
            text-decoration: none;
            out-line: none;
            color: #ffffff
        }

        a:hover, a:visited, a:link, a:active {
            text-decoration: none;
            out-line: none;
            color: #ffffff
        }
        .vernav {
            width: 240px!important;
            left: -10px!important;
            top: 169px!important;
        }
        .leftBg {
            height: 100%;
            padding: 0px;
            background: #485b79;

        }
        .userdata ul li a{
            color: #000000!important;
        }

        #leftBg {
            top: 179px;
        }
    </style>
    <script>
        var ID;
        $(document).ready(function () {

            $(".userinfo").click(function () {
                $(".userinfo").toggleClass("active");
                var style=$(".userinfodrop").attr("style");
                if(style=="display:block;"){
                    $("#userinfodrop").removeAttr("style");
                    $("#userinfodrop").attr("style","display:none;");
                }else{
                    $("#userinfodrop").removeAttr("style");
                    $("#userinfodrop").attr("style","display:block;");
                }
            });
            $(".deleteBtn").each(function () {
                $(this).click(function () {
                    ID = $(this).attr("name");
                    document.getElementById("ID_SHOW").innerHTML = "ID:" + ID;
                    console.log($("#deleteID").attr("name"));
                    $("#deleteID").attr("value", ID);
                })
            });
        });
        $(document).ready(function () {
            $(".editBtn").each(function () {
                $(this).click(function () {
                    ID = $(this).attr("name");
                    $("#editID").attr("value", ID);
                    //发送请求
                    $.ajax({
                        url: "permission/permissionEditShow",
                        data: "ID=" + ID,
                        type: "POST",
                        success: function (result) {
                            if (result.code == 100) {
                                //还没写
                                var permission = result.extend.permission;
                                $("#editName").attr("value", permission.name);
                                $("#editExpression").attr("value", permission.expression);
                            } else {
                                //找不到数据处理
                            }
                        }
                    });
                })
            });
            var isNameCurrent=[false,false];
            var isExpressionCurrent=[false,false];
            $("#editExpression").bind('input propertychange',function () {checkExpression("#editExpression",1);});
            $("#editName").bind('input propertychange',function () {checkName("#editName",1);});
            $("#add_name").bind('input propertychange',function () {checkName("#add_name",0);});
            $("#add_expression").bind('input propertychange',function () {checkExpression("#add_expression",0);});
            function checkName(ele,num) {
                checkAdd(ele,
                    /^[\u2E80-\u9FFF]{1,10}$/,
                    isNameCurrent,num,
                    "权限名必须是3-10中文组合"
                );
            }
            function checkExpression(ele,num) {
                checkAdd(ele,
                    /^[a-zA-Z0-9.-:]{3,30}$/,
                    isExpressionCurrent,num,
                    "权限标识符必须是3-30位英文和数字组合"
                );
            }
            function checkAdd(ele,reg,judge,num,tip){
                judge[num]=false;
                var regWord=$(ele).val();
                if(regVerify(ele,regWord,reg,tip)==true){
                    judge[num]=true;
                }
            }
            function regVerify(ele,regWord,reg,tip) {
                if(reg.test(regWord)){
                    show_help_block(ele,"success","格式输入正确！");
                    return true;
                }
                else{
                    show_help_block(ele,"error",tip);
                    return false;
                }
            }
            function show_help_block(ele,status,msg) {
                $(ele).parent().removeClass("has-success has-warning");
                if(status=="success"){
                    $(ele).parent().addClass("has-success");
                    $(ele).next("span").text(msg);
                }else if(status=="error"){
                    $(ele).parent().addClass("has-warning");
                    $(ele).next("span").text(msg);
                }
            }
            function clearFrom(name,sn) {
                $(name).parent().removeClass("has-success has-warning");
                $(sn).parent().removeClass("has-success has-warning");
                $(name).next("span").text("");
                $(sn).next("span").text("");
            }
            $("#btn_close_add").click(function () {
                clearFrom("#add_name","#add_sn");
            });

            function checkFormat(nameInput,expressionInput,form,closeBtn,num) {
                checkName(nameInput,num);
                checkExpression(expressionInput,num);
                var name=$(nameInput).val();
                var expression=$(expressionInput).val();
                if(isNameCurrent[num]==true&&isExpressionCurrent[num]==true){
                    $.ajax({
                        url: "permission/checkExpressionAndName",
                        data: {"name":name,"expression":expression},
                        type: "POST",
                        success: function (result) {
                            if(result.code==200){
                                show_help_block(nameInput,"error","权限代码、权限标识符已存在，请重新输入！");
                                show_help_block(expressionInput,"error","权限代码、权限标识符已存在，请重新输入！");
                            }
                            else if(result.code ==100){
                                $(form).submit();
                                $(closeBtn).click();
                            }
                        }
                    });
                }
            }

            $("#btn_submit_add").click(function () {
                checkFormat("#add_name","#add_expression","#form_add","#btn_close_add");
            });
            $("#btn_submit_edit").click(function () {
                checkFormat("#editName","#editExpression","#form_edit","#btn_close_edit");
            });
        });
    </script>
</head>
<body class="withvernav">
<div class="bodywrapper">
    <div class="topheader">
        <div class="left">
            <h1 class="logo">新日暮里.<span>研究所</span></h1>
            <span class="slogan">权限管理系统</span>

            <div class="search">
                <form action="${ pageContext.request.contextPath }/homePage/permissionManagement" method="post">
                    <input type="text" name="keyWord" id="keyWord" placeholder="请输入"/>
                    <button class="submitbutton" type="submit"></button>
                </form>
            </div><!--search-->

            <br clear="all"/>

        </div><!--left-->

        <div class="right">
            <!--<div class="notification">
                <a class="count" href="ajax/notifications.html"><span>9</span></a>
            </div>-->
            <div class="userinfo">
                <img src="assets/img/avatar1.png" alt=""/>
                <span>尊敬的用户，欢迎您</span>
            </div><!--userinfo-->
            <div id="userinfodrop" class="userinfodrop" style="display:none;">
                <div class="avatar">
                    <img src="assets/img/avatar1.png" alt="" />
                    <div class="changetheme">
                        切换主题: <br />
                        <a class="default"></a>
                        <a class="blueline"></a>
                        <a class="greenline"></a>
                        <a class="contrast"></a>
                        <a class="custombg"></a>
                    </div>
                </div><!--avatar-->
                <div class="userdata">
                    <h4>Van</h4>
                    <span class="email">do you want to 玩游戏？</span>
                    <ul>
                        <li ><a href="javascript:;">开发中</a></li>
                        <li ><a href="javascript:;">开发中</a></li>
                        <li ><a href="user/index" style="font-color: black;">个人主页</a></li>
                        <li ><a href="homePage/exit">退出</a></li>
                    </ul>
                </div><!--userdata-->
            </div><!--userinfodrop-->

        </div><!--right-->
    </div><!--topheader-->


    <div class="header">
        <ul class="headermenu">
            <li href=""><a href="homePage/userManagement"><span class="icon icon-flatscreen"></span>用户管理</a>
            </li>
            <li><a href="homePage/roleManagement"><span class="icon icon-pencil"></span>角色管理</a></li>
            <li  class="current"><a href="homePage/permissionManagement"><span class="icon icon-message"></span>权限管理</a></li>
            <li><a href="permission/permissionTest"><span class="icon icon-chart"></span>功能模拟</a></li>
        </ul>

        <div class="headerwidget">
            <div class="earnings">
                <div class="one_half">
                    <h4>论坛访问人数</h4>
                    <h2>6300</h2>
                </div><!--one_half-->
                <div class="one_half last alignright">
                    <h4>活跃比率</h4>
                    <h2>93%</h2>
                </div><!--one_half last-->
            </div><!--earnings-->
        </div><!--headerwidget-->

    </div><!--header-->

    <div class="vernav">
        <ul>
            <li ><a href="homePage/userManagement" class="editor">用户管理</a></li>
            <li><a href="homePage/roleManagement">角色管理</a></li>
            <li class="current"><a href="homePage/permissionManagement">权限管理</a></li>
            <li ><a href="permission/permissionTest">功能模拟</a></li>
        </ul>
    </div><!--leftmenu-->

    <div class="centercontent">
        <div class="container-fiuled">
            <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form id="form_add" role="form" action="permission/permissionAdd" method="post">
                            <div class="modal-header">
                                <button data-dismiss="modal" class="close" type="button"><span
                                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                                <h4 class="modal-title">权限添加</h4>
                            </div>
                            <div class="modal-body">
                                <p>权限标识符</p>
                                <input id="add_expression" class="form-control" type="text" name="expression" />
                                <span></span>
                            </div>
                            <div class="modal-body">
                                <p>权限名</p>
                                <input id="add_name" class="form-control" type="text" name="name" />
                                <span></span>
                            </div>
                            <div class="modal-footer">
                                <button id="btn_close_add" data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                                <button id="btn_submit_add" class="btn btn-primary" type="button">提交</button>
                            </div>
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>


            <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form id="form_edit" role="form" action="permission/permissionEdit?pn=${pageInfo.pageNum}&&keyWord=${keyWord}" method="post">
                            <div class="modal-header">
                                <button data-dismiss="modal" class="close" type="button"><span
                                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                                <h4 class="modal-title">权限编辑</h4>
                            </div>
                            <div class="modal-body">
                                <p>ID</p>
                                <input id="editID" name="id" readonly="readonly" class="form-control" type="text"
                                       name="expression"></input>
                            </div>
                            <div class="modal-body">
                                <p>权限标识符</p>
                                <input id="editExpression" class="form-control" type="text" name="expression" />
                                <span></span>
                            </div>
                            <div class="modal-body">
                                <p>权限名</p>
                                <input id="editName" class="form-control" type="text" name="name" />
                                <span></span>
                            </div>
                            <div class="modal-footer">
                                <button id="btn_close_edit" data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                                <button id="btn_submit_edit" class="btn btn-primary" type="button">提交</button>
                            </div>
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>

            <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form role="form" action="permission/permissionDelete?pn=${pageInfo.pageNum}&&keyWord=${keyWord}" method="post">
                            <div class="modal-header">
                                <button data-dismiss="modal" class="close" type="button"><span
                                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                                <h4 class="modal-title">确定删除权限吗？</h4>
                            </div>
                            <input id="deleteID" name="ID" type="hidden" value="">
                            <div class="modal-body">
                                <p id="ID_SHOW"></p>
                            </div>
                            <div class="modal-footer">
                                <button data-dismiss="modal" class="btn btn-info" type="button">取消</button>
                                <button class="btn btn-danger" type="submit">确定</button>
                            </div>
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>



            <div class="row">
                <div class="col-md-12 ">
                    <table class="table table-hover table table-striped">
                        <tr>
                            <th>ID</th>
                            <th>权限标识符</th>
                            <th>权限名</th>
                            <th>操作</th>
                        </tr>

                        <c:forEach items="${pageInfo.list}" var="permission">
                            <tr>
                                <th>${permission.id}</th>
                                <th>${permission.expression}</th>
                                <th>${permission.name}</th>
                                <th>
                                    <c:choose>
                                        <c:when test="${fn:contains(permission.expression,'cn.etop')}">
                                            <shiro:hasPermission name="permission:permissionEdit">
                                                <button class="btn btn-warning glyphicon glyphicon-cog btn-sm disabled">
                                                    禁止编辑
                                                </button>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="permission:permissionDelete">
                                            <button class="btn btn-danger glyphicon glyphicon-trash btn-sm disabled">
                                                禁止删除
                                            </button>
                                            </shiro:hasPermission>
                                        </c:when>
                                        <c:otherwise>
                                            <shiro:hasPermission name="permission:permissionEdit">
                                            <button name="${permission.id}"
                                                    class="editBtn   btn btn-warning glyphicon glyphicon-cog btn-sm"
                                                    data-toggle="modal" data-target="#editModal">编辑
                                            </button>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="permission:permissionDelete">
                                            <button name="${permission.id}"
                                                    class="deleteBtn btn btn-danger glyphicon glyphicon-cog btn-sm"
                                                    data-toggle="modal" data-target="#deleteModal">删除
                                            </button>
                                            </shiro:hasPermission>
                                        </c:otherwise>
                                    </c:choose>
                                </th>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <shiro:hasPermission name="permission:permissionAdd">
                    <button class="btn btn-primary glyphicon glyphicon-plus" data-toggle="modal"
                            data-target="#addModal">增加
                    </button>
                    </shiro:hasPermission>
                </div>
                <div class="col-md-4">
                    <button type="button" class="btn btn-info">当前页码:${pageInfo.pageNum}</button>
                    <button type="button" class="btn btn-info">总页码:${pageInfo.pages}</button>
                    <button type="button" class="btn btn-info">总记录数:${pageInfo.total}</button>
                </div>
                <div class="col-md-6">
                    <nav aria-label="Page navigation">
                        <ul class="pagination">
                            <li><a href="${ pageContext.request.contextPath }/homePage/permissionManagement?pn=1&&keyWord=${keyWord}">首页</a>
                            </li>
                            <li>
                                <a href="${ pageContext.request.contextPath }/homePage/permissionManagement?pn=${pageInfo.pageNum-1}&&keyWord=${keyWord}"
                                   aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach items="${pageInfo.navigatepageNums}" var="index">
                                <c:if test="${index==pageInfo.pageNum}">
                                    <li class="active"><a
                                            href="${ pageContext.request.contextPath }/homePage/permissionManagement?pn=${index}&&keyWord=${keyWord}">${index}</a>
                                    </li>
                                </c:if>
                                <c:if test="${index!=pageInfo.pageNum}">
                                    <li>
                                        <a href="${ pageContext.request.contextPath }/homePage/permissionManagement?pn=${index}&&keyWord=${keyWord}">${index}</a>
                                    </li>
                                </c:if>
                            </c:forEach>

                            <li>
                                <a href="${ pageContext.request.contextPath }/homePage/permissionManagement?pn=${pageInfo.pageNum+1}&&keyWord=${keyWord}"
                                   aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                            <li>
                                <a href="${ pageContext.request.contextPath }/homePage/permissionManagement?pn=${pageInfo.pages}&&keyWord=${keyWord}">末页</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
