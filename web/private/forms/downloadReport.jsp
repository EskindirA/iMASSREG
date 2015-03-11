<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="org.apache.poi.ss.usermodel.*"%>
<%@page import="java.util.Set"%>
<%
    String reportUrl = (String) request.getAttribute("reportURL");
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-3 col-lg-offset-5 ">
            <h2 class="page-header">Your report is ready</h2>
            <a class="btn-primary col-lg-offset-2 col-lg-8 btn" title="Download Report" target="_blank" href="<%=reportUrl%>">Download</a>
        </div>
    </div>
</div> 