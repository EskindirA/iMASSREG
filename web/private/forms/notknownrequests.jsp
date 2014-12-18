<%--@include file="../includes/checkDirectAccess.jsp" %>
<%--Welcom Page: For the first entry operator--%>
<%@page import="org.lift.massreg.util.Constants"%>
<div class="col-lg-8 col-lg-offset-2">
    <div class="row">
        <div class="col-lg-10 col-lg-offset-1 ">
            <h2 class="page-header"></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading ">
                    <div class="col-lg-offset-4">
                        <h4>Sorry, You'r request could not be recognized</h4>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12 ">
                            <br/><br/>
                            <div class="col-lg-offset-5">
                                <button id="goToHomeBtn" class="btn btn-default">Go back to home page</button>
                            </div>
                        </div> <!-- /.col-lg-6 (nested) -->
                    </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div>
    <script type="text/javascript">
        $("#goToHomeBtn").click(function() {
                    window.location = '<%=request.getContextPath()%>/Index?action=<%=Constants.ACTION_WELCOME%>';
                            return false;
                        });

    </script>
</div>