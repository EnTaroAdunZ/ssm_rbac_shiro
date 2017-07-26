<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<% pageContext.setAttribute("APP_PATH", request.getContextPath()); %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
    %>
    <base href="<%=basePath%>"></base>
    <title>角色管理</title>
    <link rel="stylesheet" href="${APP_PATH}/assets/css/style.default.css" type="text/css"/>
    <link rel="stylesheet" href="${APP_PATH}/assets/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script src="${APP_PATH}/assets/js/jquery-1.11.1.min.js"></script>
    <script src="${APP_PATH}/assets/ztree/js/jquery.ztree.core.js" type="text/javascript"></script>
    <script src="${APP_PATH}/assets/ztree/js/jquery.ztree.excheck.js"></script>
    <link href="${APP_PATH}/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="${APP_PATH}/assets/bootstrap/js/bootstrap.min.js"></script>
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

        .preBtnShow {
            margin-right: 5px;
            margin-bottom: 5px;
        }

        .leftBg {
            height: 100%;
            padding: 0px;
            background: #485b79;

        }
        .userdata ul li a{
            color: #000000!important;
        }
        .vernav {
            width: 240px!important;
            left: -10px!important;
            top: 169px!important;
        }
        #leftBg {
            top: 179px;
        }
    </style>


    <script type="text/javascript">
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        var zNodes ;

        var code;

        function setCheck() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                type = { "Y" : "ps", "N" : "ps" };
            zTree.setting.check.chkboxType =  type;
        }
//        $(function(){
//            $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
//            $('.tree li.parent_li > span').on('click', function (e) {
//                var children = $(this).parent('li.parent_li').find(' > ul > li');
//                if (children.is(":visible")) {
//                    children.hide('fast');
//                    $(this).attr('title', 'Expand this branch').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
//                } else {
//                    children.show('fast');
//                    $(this).attr('title', 'Collapse this branch').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
//                }
//                e.stopPropagation();
//            });
//        });
    </script>
    <script>
        var ID;
        $(document).ready(function () {
            $(".deleteBtn").each(function () {
                $(this).click(function () {
                    ID = $(this).attr("name");
                    document.getElementById("ID_SHOW").innerHTML = "ID:" + ID;
                    $("#deleteID").attr("value", ID);

                })
            });
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
            $(".treeBtn").each(function () {
                $(this).click(function () {
                    ID = $(this).attr("name");
                    $.ajax({
                        url: "role/treePermission_echo",
                        data: "ID=" + ID,
                        type: "POST",
                        success: function (result) {
                            if (result.code == 100) {
                                zNodes=result.extend.allZtreeMsg;
                                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                                setCheck();
                            } else if(result.code==250){
                                $("#btn_closeTree").click();
                                show_errorWindows(result.extend.returnMsg);
                            }
                        }
                    });
                });
            });
            $("#btn_closeTree").click(function () {
                $("#treeDemo").empty();
            });
            function show_errorWindows(errorMsg) {
                $("#errorWindow_msg").text(errorMsg);
                $("#errorWindom").modal();
            }
            var permissionListTree = new Array();
            $("#btn_inputTree").click(function () {
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");

                var nodes=zTree.getCheckedNodes(true);
                permissionListTree = new Array();
                permissionListTree.push(ID);
                for ( var j = 0; j < nodes.length; j++) {
                    permissionListTree.push(nodes[j].id);
                }
                $.ajax({

                    url: "role/treePermission_alter",
                    data: "permissionListTree="+permissionListTree,
                    type: "POST",
                    success: function (result) {
                        if(result.code ==100){
                            $("#btn_closeTree").click();
                        }else if(result.code==250){
                            $("#btn_closeTree").click();
                            show_errorWindows(result.extend.returnMsg);
                        }

                    }
                });


            });
            $(".editBtn").each(function () {
                $(this).click(function () {
                    ID = $(this).attr("name");
                    $("#editID").attr("value", ID);
                    $.ajax({
                        url: "role/getRoleById",
                        data: "ID=" + ID,
                        type: "POST",
                        success: function (result) {
                            if (result.code == 100) {
                                var role = result.extend.role;
                                $("#editName").attr("value", role.name);
                                $("#editSn").attr("value", role.sn);
                            } else {
                                //找不到数据处理
                            }
                        }
                    });
                })
            });
            $(".preBtn").each(function () {
                $(this).click(function () {
                    ID = $(this).attr("name");
                })


            });

            $(document).on("click", ".preBtnShow", function () {
                $(this).click(function () {
                    $(this).toggleClass("btn-info");
                })
            });
            $("#inputPreChance").click(function () {
                var permissionList = new Array();
                permissionList.push(ID);
                $(".preBtnShow").each(function () {

                    if ($(this).hasClass("btn-info")) {
                        permissionList.push($(this).attr("id"));
                    }
                });
                $.ajax({

                    url: "permission/updatePermissionJson",
                    data: "permissionList=" + permissionList,
                    type: "POST",
                    success: function () {
                        $("#closePreChance").click();
                    }
                });
            });


            $(".preBtn").each(function () {
                $(this).click(function () {
                    $("#perHandler").empty();
                    $("#perHandler").append("<h5></h5>").append("你没有获得该权限！");
                    ID = $(this).attr("name");
                    $("#editID").attr("value", ID);
                    $.ajax({
                        url: "permission/getPermissionJson",
                        data: "ID=" + ID,
                        type: "GET",
                        success: function (result) {
                            var perList = result.extend.permissionJsonList;
                            $("#perHandler").empty();
                            $.each(perList, function (index, item) {
                                var isExit = item.exit;
                                if (isExit == false) {
                                    var preBtn = $("<button></button>")
                                        .addClass("btn preBtnShow")
                                        .attr("type", "button")
                                        .attr("id", item.permission.id)
                                        .append(item.permission.name);
                                    $("#perHandler").append(preBtn);
                                }
                                else {
                                    var preBtn = $("<button></button>")
                                        .addClass("btn preBtnShow btn-info")
                                        .attr("type", "button")
                                        .attr("id", item.permission.id)
                                        .append(item.permission.name);
                                    $("#perHandler").append(preBtn);
                                }
                            });
                        }
                    });
                })
            });
            var isNameCurrent=[false,false];
            var isSNCurrent=[false,false];
            $("#editName").bind('input propertychange',function () {checkName("#editName",1);});
            $("#editSn").bind('input propertychange',function () {checkSN("#editSn",1);});
            $("#add_name").bind('input propertychange',function () {checkName("#add_name",0);});
            $("#add_sn").bind('input propertychange',function () {checkSN("#add_sn",0);});
            function checkName(ele,num) {
                checkAdd(ele,
                    /^[\u2E80-\u9FFF]{1,10}$/,
                    isNameCurrent,num,
                    "角色名必须是1-10位中文组合"
                );
            }
            function checkSN(ele,num) {
                checkAdd(ele,
                    /^[a-zA-Z0-9_-]{3,30}$/,
                    isSNCurrent,num,
                    "角色代码必须是3-30位英文和数字组合"
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
            $("#btn_close_add").click(function () {
                clearFrom("#add_name","#add_sn");
            });
            $("#btn_submit_add").click(function () {
                checkName("#add_name",0);
                checkSN("#add_sn",0);
                var name=$("#add_name").val();
                var sn=$("#add_sn").val();
                if(isNameCurrent[0]==true&&isSNCurrent[0]==true){
                    $.ajax({
                        url: "role/ifCountAdd",
                        data: {"name":name,"sn":sn},
                        type: "POST",
                        success: function (result) {
                            if(result.code==200){
                                show_help_block("#add_name","error","角色代码、角色名已存在，请重新输入！");
                                show_help_block("#add_sn","error","角色代码、角色名已存在，请重新输入！");
                            }
                            else if(result.code ==100){
                                $("#form_add").submit();
                                $("#btn_close_add").click();
                                console.log("提交成功");
                            }
                        }
                    });
                }
            });
            $("#btn_submit_edit").click(function () {
                checkName("#editName",1);
                checkSN("#editSn",1);
                var name=$("#editName").val();
                var sn=$("#editSn").val();
                if(isNameCurrent[1]==true&&isSNCurrent[1]==true){
                    $.ajax({
                        url: "role/ifCountAdd",
                        data: {"name":name,"sn":sn},
                        type: "POST",
                        success: function (result) {
                            if(result.code==200){
                                show_help_block("#editName","error","角色代码、角色名已存在，请重新输入！");
                                show_help_block("#editSn","error","角色代码、角色名已存在，请重新输入！");
                            }
                            else if(result.code ==100){
                                $("#form_edit").submit();
                                $("#btn_close_edit").click();
                                console.log("提交成功");
                            }
                        }
                    });
                }
            });
            $("#btn_close_edit").click();
            function clearFrom(name,sn) {
                $(name).parent().removeClass("has-success has-warning");
                $(sn).parent().removeClass("has-success has-warning");
                $(name).next("span").text("");
                $(sn).next("span").text("");
            }
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
                <form action="${ pageContext.request.contextPath }/homePage/roleManagement" method="post">
                    <input type="text" name="keyWord" id="keyWord" placeholder="请输入"/>
                    <button class="submitbutton"></button>
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
            <li  href=""><a href="homePage/userManagement"><span class="icon icon-flatscreen"></span>用户管理</a>
            </li>
            <li  class="current"><a href="homePage/roleManagement"><span class="icon icon-pencil"></span>角色管理</a></li>
            <li ><a href="homePage/permissionManagement"><span class="icon icon-message"></span>权限管理</a></li>
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
            <li class="current"><a href="homePage/roleManagement">角色管理</a></li>
            <li><a href="homePage/permissionManagement">权限管理</a></li>
            <li><a href="permission/permissionTest">功能模拟</a></li>
        </ul>
    </div><!--leftmenu-->

    <div class="centercontent">
        <div class="container-fiuled">
            <div class="modal fade" id="preModal" tabindex="-1" role="dialog" aria-labelledby="preModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form role="form" action="role/roleAdd" method="post">
                            <div class="modal-header">
                                <button data-dismiss="modal" class="close" type="button"><span
                                        aria-hidden="true">×</span><span
                                        class="sr-only">Close</span></button>
                                <h4 class="modal-title">权限分配</h4>
                            </div>
                            <div class="modal-body" id="perHandler">

                            </div>
                            <div class="modal-footer">
                                <button id="closePreChance" data-dismiss="modal" class="btn btn-default" type="button">
                                    关闭
                                </button>
                                <button id="inputPreChance" class="btn btn-primary" type="button">提交</button>
                            </div>
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>

            <div class="modal fade" id="errorWindom" tabindex="-1" role="dialog" aria-labelledby="roleModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form role="form" action="" method="post">
                            <div class="modal-header">
                                <button data-dismiss="modal" class="close" type="button"><span
                                        aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                                <h4 class="modal-title" style="font-size: 30px" id="errorWindow_msg">权限不足</h4>
                            </div>
                            <div class="modal-body">
                                <img src="assets/img/sign-error-icon.png" style="width: 200px;height: 200px">
                            </div>
                            <div class="modal-footer">
                                <button id="closeErrorWindom" data-dismiss="modal" class="btn btn-default" type="button">
                                    关闭
                                </button>
                            </div>
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>

            <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form id="form_add" role="form" action="role/roleAdd" method="post">
                            <div class="modal-header">
                                <button data-dismiss="modal" class="close" type="button"><span
                                        aria-hidden="true">×</span><span
                                        class="sr-only">Close</span></button>
                                <h4 class="modal-title">角色添加</h4>

                            </div>
                            <div class="modal-body">
                                <p>角色代码</p>
                                <input id="add_sn" class="form-control" type="text" name="sn"></input>
                                <span class="help-block"></span>
                            </div>
                            <div class="modal-body">
                                <p>角色名</p>
                                <input id="add_name" class="form-control" type="text" name="name"></input>
                                <span class="help-block"></span>
                            </div>
                            <div class="modal-footer">
                                <button id="btn_close_add" data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                                <button id="btn_submit_add" class="btn btn-primary" type="button">提交</button>
                            </div>
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>

            <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form id="form_edit" role="form" action="role/roleEdit?pn=${pageInfo.pageNum+1}&&keyWord=${keyWord}" method="post">
                            <div class="modal-header">
                                <button data-dismiss="modal" class="close" type="button"><span
                                        aria-hidden="true">×</span><span
                                        class="sr-only">Close</span></button>
                                <h4 class="modal-title">角色编辑</h4>
                            </div>
                            <div class="modal-body">
                                <p>ID</p>
                                <input id="editID" name="id" readonly="readonly" class="form-control" type="text"
                                       name="expression"/>
                            </div>
                            <div class="modal-body">
                                <p>角色代号</p>
                                <input id="editSn" class="form-control" type="text" name="sn"/>
                                <span></span>
                            </div>
                            <div class="modal-body">
                                <p>角色名</p>
                                <input id="editName" class="form-control" type="text" name="name"></input>
                                <span></span>
                            </div>
                            <div class="modal-footer">
                                <button id="btn_close_edit" data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                                <button id="btn_submit_edit" class="btn btn-primary" type="button">提交</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form role="form" action="role/roleDelete?pn=${pageInfo.pageNum+1}&&keyWord=${keyWord}" method="post">
                            <div class="modal-header">
                                <button data-dismiss="modal" class="close" type="button"><span
                                        aria-hidden="true">×</span><span
                                        class="sr-only">Close</span></button>
                                <h4 class="modal-title">确定删除角色吗？</h4>
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

            <div class="modal fade" id="dtreeModal" tabindex="-1" role="dialog" aria-labelledby="preModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form role="form" action="" method="post">
                            <div class="modal-header">
                                <button data-dismiss="modal" class="close" type="button"><span
                                        aria-hidden="true">×</span><span
                                        class="sr-only">Close</span></button>
                                <h4 class="modal-title">树形分配</h4>
                            </div>
                            <div class="modal-body" >
                                <div>
                                    <ul id="treeDemo" class="ztree"></ul>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button  id="btn_closeTree" data-dismiss="modal" class="btn btn-default" type="button">
                                    关闭
                                </button>
                                <button  id="btn_inputTree" class="btn btn-primary" type="button">提交</button>
                            </div>
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>

            <div class="row">
                <div class="col-md-12">
                    <table class="table table-hover table table-striped">
                        <tr>
                            <th>ID</th>
                            <th>角色序列号</th>
                            <th>角色名</th>
                            <th>操作</th>
                        </tr>

                        <c:forEach items="${pageInfo.list}" var="role">
                            <tr>
                                <th>${role.id}</th>
                                <th>${role.sn}</th>
                                <th>${role.name}</th>
                                <th>
                                    <shiro:hasPermission name="permission:updatePermissionJson:ajax">
                                    <button name="${role.id}"
                                            class="preBtn btn btn-info glyphicon glyphicon-eye-open btn-sm"
                                            data-toggle="modal" data-target="#preModal"> 权限分配
                                    </button>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="role:treePermission_echo:ajax">
                                    <button name="${role.id}"
                                            class="treeBtn btn btn-success glyphicon glyphicon-tree-conifer btn-sm"
                                            data-toggle="modal" data-target="#dtreeModal"> 树形分配
                                    </button>
                                    </shiro:hasPermission>
                                    <c:choose>
                                        <c:when test="${fn:contains(role.name,'系统管理员')||fn:contains(role.name,'管理员')||fn:contains(role.name,'普通用户')}">
                                            <shiro:hasPermission name="role:roleEdit">
                                            <button class="editBtn btn btn-warning glyphicon glyphicon-cog btn-sm disabled">
                                                禁止编辑
                                            </button>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="role:roleDelete">
                                            <button class="deleteBtn btn btn-danger glyphicon glyphicon-trash btn-sm disabled">
                                                禁止删除
                                            </button>
                                            </shiro:hasPermission>
                                        </c:when>
                                        <c:otherwise>
                                            <shiro:hasPermission name="role:roleEdit">
                                            <button name="${role.id}"
                                                    class="editBtn btn btn-warning glyphicon glyphicon-cog btn-sm"
                                                    data-toggle="modal" data-target="#editModal">编辑
                                            </button>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="role:roleDelete">
                                            <button name="${role.id}"
                                                    class="deleteBtn btn btn-danger glyphicon glyphicon-trash btn-sm"
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
                    <shiro:hasPermission name="role:roleAdd">
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
                            <li><a href="${ pageContext.request.contextPath }/homePage/roleManagement?pn=1&&keyWord=${keyWord}">首页</a></li>
                            <li>
                                <a href="${ pageContext.request.contextPath }/homePage/roleManagement?pn=${pageInfo.pageNum-1}&&keyWord=${keyWord}"
                                   aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach items="${pageInfo.navigatepageNums}" var="index">
                                <c:if test="${index==pageInfo.pageNum}">
                                    <li class="active"><a
                                            href="${ pageContext.request.contextPath }/homePage/roleManagement?pn=${index}&&keyWord=${keyWord}">${index}</a>
                                    </li>
                                </c:if>
                                <c:if test="${index!=pageInfo.pageNum}">
                                    <li>
                                        <a href="${ pageContext.request.contextPath }/homePage/roleManagement?pn=${index}&&keyWord=${keyWord}">${index}</a>
                                    </li>
                                </c:if>
                            </c:forEach>

                            <li>
                                <a href="${ pageContext.request.contextPath }/homePage/roleManagement?pn=${pageInfo.pageNum+1}&&keyWord=${keyWord}"
                                   aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                            <li>
                                <a href="${ pageContext.request.contextPath }/homePage/roleManagement?pn=${pageInfo.pages}&&keyWord=${keyWord}">末页</a>
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
