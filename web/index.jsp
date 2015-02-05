<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="org.lift.massreg.util.Constants"%>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="am">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>MASSREG</title>
        <link href="<%=request.getContextPath()%>/assets/images/imassreg.png" rel="icon" type="image/png"  >
        <link href="<%=request.getContextPath()%>/assets/css/bootstrap.min.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/assets/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/assets/css/plugins/dataTables.bootstrap.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/assets/css/style.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/assets/css/sb-admin-2.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/assets/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        
        <link href="<%=request.getContextPath()%>/assets/css/bootstrap-select.css" rel="stylesheet" type="text/css">
        <link href="<%=request.getContextPath()%>/assets/css/jquery-ui.css" rel="stylesheet" type="text/css">
        <link href="<%=request.getContextPath()%>/assets/css/ethdate/jquery.calendars.picker.css" rel="stylesheet" type="text/css">


        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <script src="<%=request.getContextPath()%>/assets/js/jquery.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/jquery-ui.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/miniNotification.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/plugins/metisMenu/metisMenu.min.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/plugins/dataTables/jquery.dataTables.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/plugins/dataTables/dataTables.bootstrap.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/sb-admin-2.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/bootbox.js"></script>
        
        <script src="<%=request.getContextPath()%>/assets/js/ethdate/jquery.plugin.min.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/ethdate/jquery.calendars.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/ethdate/jquery.calendars.plus.min.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/ethdate/jquery.calendars.picker.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/ethdate/jquery.calendars.ethiopian.min.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/ethdate/jquery.calendars.ethiopian-am.js"></script>
        <script src="<%=request.getContextPath()%>/assets/js/ethdate/jquery.calendars.validation.min.js"></script>
        
        
        <script type="text/javascript">
            function showMessage(msg) {
                bootbox.alert(msg);
            }
            function showError(msg) {
                bootbox.alert(msg);
            }
            function showWarning(msg) {
                bootbox.alert(msg);
            }
            function showajaxerror() {
                bootbox.alert("System Error");
            }

            function loadForward(result) {
                $("#page-wrapper").hide('slide', {direction: 'left', complete: function() {
                        $("#page-wrapper").html(result);
                    }}, 800);
                $("#page-wrapper").show('slide', {direction: 'right'}, 800);

            }
            function loadBackward(result) {
                $("#page-wrapper").hide('slide', {direction: 'right', complete: function() {
                        $("#page-wrapper").html(result);
                    }}, 800);
                $("#page-wrapper").show('slide', {direction: 'left'}, 800);
            }
            function loadInPlace(result) {
                $("#page-wrapper").html(result);
            }
        </script>
    </head>
    <body>
        <div id="notification-error" class="notification">
            <p></p>
        </div>
        <div id="notification-warning" class="notification">
            <p></p>
        </div>
        <div id="notification-message" class="notification">
            <p></p>
        </div>
        <div id="wrapper">
            <!-- Navigation -->
            <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/MASSREG/Index">
                        <img src="<%=request.getContextPath()%>/assets/images/beta_icon.png" style="display:inline; width:40px; position:absolute;left:5px;z-index:4;top:0px"> 
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; MASSREG v2.0 
                        - <%= CommonStorage.getCurrentWoredaName()%> Woreda </a>
                </div> <!-- /.navbar-header -->
                <ul class="nav navbar-top-links navbar-right">
                    <%-- <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <i class="fa fa-bell fa-fw"></i>  <i class="fa fa-caret-down"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <li>
                                <a href="#">
                                    <div>
                                        <i class="fa fa-comment fa-fw"></i> New Comment
                                        <span class="pull-right text-muted small">4 minutes ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div>
                                        <i class="fa fa-twitter fa-fw"></i> 3 New Followers
                                        <span class="pull-right text-muted small">12 minutes ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div>
                                        <i class="fa fa-envelope fa-fw"></i> Message Sent
                                        <span class="pull-right text-muted small">4 minutes ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div>
                                        <i class="fa fa-tasks fa-fw"></i> New Task
                                        <span class="pull-right text-muted small">4 minutes ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div>
                                        <i class="fa fa-upload fa-fw"></i> Server Rebooted
                                        <span class="pull-right text-muted small">4 minutes ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a class="text-center" href="#">
                                    <strong>See All Alerts</strong>
                                    <i class="fa fa-angle-right"></i>
                                </a>
                            </li>
                        </ul> <!-- /.dropdown-alerts -->
                    </li> <!-- /.dropdown --> --%>

                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <%=CommonStorage.getCurrentUser(request).getNameWithRole()%>
                            <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="#" id="viewProfileButton"><i class="fa fa-user fa-fw" title="see <%= CommonStorage.getCurrentUser(request).getFullName()%>'s profile"></i> <span class="name">View Profile</span></a> </li>
                            <li class="divider"></li>
                            <li> <a href="<%=request.getContextPath()%>/Index?action=<%= Constants.ACTION_LOGOUT%>"><i class="fa fa-sign-out fa-fw"></i> Logout</a> </li>
                        </ul> <!-- /.dropdown-user -->
                    </li> <!-- /.dropdown -->
                </ul> <!-- /.navbar-top-links -->
            </nav>
            <div id="page-wrapper" >
                <jsp:include page="${requestScope.page}"  flush="true"/>
            </div> <!-- /#page-wrapper -->
        </div> <!-- /#wrapper -->
    </body>
    <script type="text/javascript">
        $("#viewProfileButton").click(function() {
            $.ajax({
                url: "<%=request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PROFILE%>",
                error: showajaxerror,
                success: loadInPlace
            });
        });
    </script>
</html>
