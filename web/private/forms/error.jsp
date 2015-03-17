<%@page import="org.lift.massreg.util.*"%>
<h1><%=CommonStorage.getText("guardians_list")%></h1>
<button id="goToHomeBtn" class="btn btn-default"><%=CommonStorage.getText("go_back_to_home_page")%></button>
<script type="text/javascript">
    $("#goToHomeBtn").click(function() {
        window.location = '<%=request.getContextPath()%>/Index?action=<%=Constants.ACTION_WELCOME%>';
                return false;
            });

</script>