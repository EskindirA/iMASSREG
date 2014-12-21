<%@page import="org.lift.massreg.util.Constants"%>
<h1>Some internal error has happened, the error has been saved please go back to the welcome page</h1>
<button id="goToHomeBtn" class="btn btn-default">Go back to home page</button>
<script type="text/javascript">
    $("#goToHomeBtn").click(function() {
        window.location = '<%=request.getContextPath()%>/Index?action=<%=Constants.ACTION_WELCOME%>';
                return false;
            });

</script>