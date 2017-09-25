<%@page import="org.lift.massreg.util.Constants"%>
<div class="col-lg-8 col-lg-offset-2">
    <div class="row">
        <div class="col-lg-3 col-lg-offset-5 ">
            <h2 class="page-header">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <%=request.getSession().getAttribute("title")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading ">
                    <div class="col-lg-offset-4">
                    </div>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12 ">
                            <div class="row">
                                <div class="col-lg-offset-1">
                                    <h3><%=request.getSession().getAttribute("message")%></h3>
                                </div>
                            </div>
                            <div class="col-lg-offset-5">
                                <button id="goToHomeBtn" class="btn btn-default"><%=request.getSession().getAttribute("returnTitle")%></button>
                            </div>
                        </div> <!-- /.col-lg-6 (nested) -->
                    </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div>
    <script type="text/javascript">
        $("#goToHomeBtn").click(function() {
            // Call to the service to get the form
            $.ajax({
                success: loadBackward,
                type: 'POST',
                url: '<%=request.getContextPath()%>/Index?action=<%=request.getSession().getAttribute("returnAction")%>'
                        });
                        return false;
                    });
    </script>
</div>