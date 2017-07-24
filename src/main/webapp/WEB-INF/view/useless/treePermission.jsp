<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script src="${APP_PATH}/assets/js/jquery-1.11.1.min.js"></script>
    <link href="${APP_PATH}/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="${APP_PATH}/assets/bootstrap/js/bootstrap.min.js"></script>
    <style>
        /*a {*/
        /*text-decoration: none;*/
        /*out-line: none;*/
        /*color: #ffffff*/
        /*}*/
        /*.userdata ul li a{*/
        /*color: #000000!important;*/
        /*}*/
        /*a:hover, a:visited, a:link, a:active {*/
        /*text-decoration: none;*/
        /*out-line: none;*/
        /*color: #ffffff*/
        /*}*/
        .vernav {
            width: 240px !important;
            left: -10px !important;
            top: 169px !important;
        }


    </style>
    <script type="text/javascript">
        //#region 页面执行入口
        $(document).ready(function () {
            //#region 浏览器检测相关方法
            window["MzBrowser"] = {};
            (function () {
                if (MzBrowser.platform) return;
                var ua = window.navigator.userAgent;
                MzBrowser.platform = window.navigator.platform;
                MzBrowser.firefox = ua.indexOf("Firefox") > 0;
                MzBrowser.opera = typeof (window.opera) == "object";
                MzBrowser.ie = !MzBrowser.opera && ua.indexOf("MSIE") > 0;
                MzBrowser.mozilla = window.navigator.product == "Gecko";
                MzBrowser.netscape = window.navigator.vendor == "Netscape";
                MzBrowser.safari = ua.indexOf("Safari") > -1;
                if (MzBrowser.firefox) var re = /Firefox(\s|\/)(\d+(\.\d+)?)/;
                else if (MzBrowser.ie) var re = /MSIE( )(\d+(\.\d+)?)/;
                else if (MzBrowser.opera) var re = /Opera(\s|\/)(\d+(\.\d+)?)/;
                else if (MzBrowser.netscape) var re = /Netscape(\s|\/)(\d+(\.\d+)?)/;
                else if (MzBrowser.safari) var re = /Version(\/)(\d+(\.\d+)?)/;
                else if (MzBrowser.mozilla) var re = /rv(\:)(\d+(\.\d+)?)/;
                if ("undefined" != typeof (re) && re.test(ua))
                    MzBrowser.version = parseFloat(RegExp.$2);
            })();
        });

        //显示删除
        function showRemove(obj) {
            $(obj).addClass("remove");
        }

        //隐藏删除
        function hideRemove(obj) {
            $(obj).removeClass("remove");
        }

        //鼠标移动到删除图标，显示手（pointer）
        function setRemove(obj, event) {
            var width = $(obj).width();
            var left = $(obj).position().left;
            var e = event || window.event;
            var x = IsIE(GetVersion()) ? e.x : e.pageX;
            if (x > left + width - 9) {
                $(obj).css("cursor", "pointer")
            } else {
                $(obj).css("cursor", "default")
            }
        }

        function GetVersion() {
            return MzBrowser.version;
        }

        function GetName() {
            var name = "undefined";
            if (MzBrowser.ie) {
                name = "ie";
            }
            else if (MzBrowser.firefox) {
                name = "firefox";
            }
            else if (MzBrowser.safari) {
                name = "safari";
            }
            return name;
        }

        function IsIE(versionValue) {
            if (versionValue == 11) {
                return IsIE11();
            }
            var name = GetName();
            var version = GetVersion();
            return name == 'ie' && parseInt(version) == versionValue;
        }
    </script>
    <script type="text/javascript">
        function test() {
            var count = 0;
            var obj = document.all.authority;

            for (i = 0; i < obj.length; i++) {
                if (obj[i].checked) {
                    alert(obj[i].value);
                    count++;
                }
            }
        }

        //搜索节点并展开节点
        function nodeSearching() {
            var dosearch = $.trim($("#dosearch_text").val());//获取要查询的文字
            var dtree_div = $("#dtree_div").find(".dtree_node").show().filter(":contains('" + dosearch + "')");//获取所有包含文本的节点
            $.each(dtree_div, function (index, element) {
                var s = $(element).attr("node_id");
                d.openTo(s);//根据id打开节点
            });
        }
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
                    <img src="assets/img/avatar1.png" alt=""/>
                    <div class="changetheme">
                        切换主题: <br/>
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
                        <li><a href="javascript:;">开发中</a></li>
                        <li><a href="javascript:;">开发中</a></li>
                        <li><a href="user/index" style="font-color: black;">个人主页</a></li>
                        <li><a href="homePage/exit">退出</a></li>
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
            <li class="current"><a href="homePage/permissionManagement"><span class="icon icon-message"></span>权限管理</a>
            </li>
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
            <li><a href="homePage/userManagement" class="editor">用户管理</a></li>
            <li><a href="homePage/roleManagement">角色管理</a></li>
            <li class="current"><a href="homePage/permissionManagement">权限管理</a></li>
            <li><a href="permission/permissionTest">功能模拟</a></li>
        </ul>
    </div><!--leftmenu-->

    <div class="centercontent">
        <div class="container-fiuled">
            <div class="row">
                <div class="col-md-12">
                    <div class="dtree" id="dtree_div">

                        <p><a href="javascript:  d.closeAll();">打开</a> | <a href="javascript: d.openAll();">关闭</a></p>
                        <input id="dosearch_text" type="text"/>
                        <input id="dosearch" type="button" value="查询" onclick="nodeSearching() "/>
                        <script type="text/javascript">
                            d = new dTree('d', true);   //参数一: 树名称。参数二：单选多选 true多选  false单选  默认单选
                            d.add(0, -1, '权限管理');
                            d.add(1, 0, 'authority', '0001', '日常工作', true, false);
                            d.add(2, 1, 'authority', '0002', '新建工作', true, false);
                            d.add(3, 2, 'authority', '0003', '人事 ', true, false);
                            d.add(4, 2, 'authority', '0004', '财务', false, false);
                            d.add(5, 2, 'authority', '0005', '客服', false, false);
                            d.add(15, 3, 'authority', '0006', '请假申请', false, false);
                            d.add(16, 3, 'authority', '0007', '出差申请', false, false);
                            d.add(17, 3, 'authority', '0008', '招聘申请', false, false);

                            // dTree实例属性以此为：  节点ID，父类ID，chechbox的名称，chechbox的值，chechbox的显示名称，
                            //chechbox是否被选中--默认是不选，chechbox是否可用：默认是可用，节点链接：默认是虚链接
                            d.add(6, 0, 'authority', '0012', '一级菜单2 ', true, false);
                            d.add(7, 6, 'authority', '0013', '二级菜单2 ', true, false);
                            d.add(8, 7, 'authority', '0014', '用户管理 ', false, false);
                            d.add(9, 7, 'authority', '0015', '用户组管理 ', false, false);


                            d.add(11, 0, 'authority', '0016', '一级菜单3 ', true, false);
                            d.add(12, 11, 'authority', '0017', '二级菜单3 ', true, false);
                            d.add(13, 12, 'authority', '0018', '用户管理 ', false, false);
                            d.add(14, 12, 'authority', '0019', '用户组管理 ', false, false);
                            d.add(20, 0, 'authority', '0020', '测试', true, false);
                            document.write(d);
                            d.openAll();
                        </script>
                    </div>

                    <div class="selectorResult">
                        选择结果：
                        <ul id="ulSelected">
                            <!--<li id="user_2_1" uid="1" name="姓名" mytype="2">
                                <div class="selectedUser" onmouseover="showRemove(this)" onmouseout="hideRemove(this)" onmousemove="setRemove(this,event)" onclick="doRemove(this,event);" style="cursor: pointer;">
                                    姓名
                                </div>
                            </li>-->
                        </ul>
                    </div>
                    <!--<div>
                        <input type='button' name='bTest' value='test' onclick='test();'>
                    </div>-->

                    <script type="text/javascript">
                        function test() {
                            var count = 0;
                            var obj = document.all.authority;

                            for (i = 0; i < obj.length; i++) {
                                if (obj[i].checked) {
                                    alert(obj[i].value);
                                    count++;
                                }
                            }
                        }

                        //搜索节点并展开节点
                        function nodeSearching() {
                            var dosearch = $.trim($("#dosearch_text").val());//获取要查询的文字
                            var dtree_div = $("#dtree_div").find(".dtree_node").show().filter(":contains('" + dosearch + "')");//获取所有包含文本的节点
                            $.each(dtree_div, function (index, element) {
                                var s = $(element).attr("node_id");
                                d.openTo(s);//根据id打开节点
                            });
                        }
                    </script>
                    <script type="text/javascript">
                        //#region 页面执行入口
                        $(document).ready(function () {
                            //#region 浏览器检测相关方法
                            window["MzBrowser"] = {};
                            (function () {
                                if (MzBrowser.platform) return;
                                var ua = window.navigator.userAgent;
                                MzBrowser.platform = window.navigator.platform;
                                MzBrowser.firefox = ua.indexOf("Firefox") > 0;
                                MzBrowser.opera = typeof (window.opera) == "object";
                                MzBrowser.ie = !MzBrowser.opera && ua.indexOf("MSIE") > 0;
                                MzBrowser.mozilla = window.navigator.product == "Gecko";
                                MzBrowser.netscape = window.navigator.vendor == "Netscape";
                                MzBrowser.safari = ua.indexOf("Safari") > -1;
                                if (MzBrowser.firefox) var re = /Firefox(\s|\/)(\d+(\.\d+)?)/;
                                else if (MzBrowser.ie) var re = /MSIE( )(\d+(\.\d+)?)/;
                                else if (MzBrowser.opera) var re = /Opera(\s|\/)(\d+(\.\d+)?)/;
                                else if (MzBrowser.netscape) var re = /Netscape(\s|\/)(\d+(\.\d+)?)/;
                                else if (MzBrowser.safari) var re = /Version(\/)(\d+(\.\d+)?)/;
                                else if (MzBrowser.mozilla) var re = /rv(\:)(\d+(\.\d+)?)/;
                                if ("undefined" != typeof (re) && re.test(ua))
                                    MzBrowser.version = parseFloat(RegExp.$2);
                            })();
                        });

                        //显示删除
                        function showRemove(obj) {
                            $(obj).addClass("remove");
                        }

                        //隐藏删除
                        function hideRemove(obj) {
                            $(obj).removeClass("remove");
                        }

                        //鼠标移动到删除图标，显示手（pointer）
                        function setRemove(obj, event) {
                            var width = $(obj).width();
                            var left = $(obj).position().left;
                            var e = event || window.event;
                            var x = IsIE(GetVersion()) ? e.x : e.pageX;
                            if (x > left + width - 9) {
                                $(obj).css("cursor", "pointer")
                            } else {
                                $(obj).css("cursor", "default")
                            }
                        }

                        function GetVersion() {
                            return MzBrowser.version;
                        }

                        function GetName() {
                            var name = "undefined";
                            if (MzBrowser.ie) {
                                name = "ie";
                            }
                            else if (MzBrowser.firefox) {
                                name = "firefox";
                            }
                            else if (MzBrowser.safari) {
                                name = "safari";
                            }
                            return name;
                        }

                        function IsIE(versionValue) {
                            if (versionValue == 11) {
                                return IsIE11();
                            }
                            var name = GetName();
                            var version = GetVersion();
                            return name == 'ie' && parseInt(version) == versionValue;
                        }
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
