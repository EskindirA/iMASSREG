<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="org.apache.poi.ss.usermodel.*"%>
<%@page import="java.util.Set"%>
<%
    String reportUrl = (String) request.getAttribute("reportURL");
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-3 col-lg-offset-5 ">
            <h2 class="page-header"><%=CommonStorage.getText("your_report_is_ready")%></h2>
            <a class="btn-primary col-lg-offset-2 col-lg-8 btn" title="<%=CommonStorage.getText("download_report")%>" target="_blank" href="<%=reportUrl%>"><%=CommonStorage.getText("download")%></a>
        </div>
    </div>
</div> 