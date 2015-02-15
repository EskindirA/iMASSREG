<%@page import="java.util.Properties"%>
<%@page import="java.util.Set"%>
<%
    Properties report = (Properties) request.getAttribute("report");
    String reportUrl = (String) request.getAttribute("reportURL");
%>
<div class="row">
    <div class="col-lg-12 col-lg-offset-0">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Periodical Report</h3>
            </div>
            <div class="panel-body">
                <table class="table table-striped table-bordered table-hover col-lg-12" id="reportTable">
                    <thead>
                    <tr>
                        <th style='text-align: center '>Parameter</th>
                        <th style='text-align: center'>Kebele</th>
                        <th style='text-align: center'>Value</th>
                    </tr>
                    </thead>
                    <tbody>
                        <%
                        Set<String> titles = report.stringPropertyNames();

                        for (String title : titles) {
                            if(title.equalsIgnoreCase("Woreda name") || title.equalsIgnoreCase("Woreda Id") || title.equalsIgnoreCase("Report generated on") ){
                                continue;
                            }
                            out.println("<tr>");
                            out.println("<td style='text-align: left'>" + title.substring(0, title.indexOf(':')) + "</td>");
                            out.println("<td style='text-align: left'>" + title.substring(title.indexOf(':')+1) + "</td>");
                            out.println("<td style='text-align: center'>" + report.getProperty(title) + "</td>");
                            out.println("</tr>");
                        }
                        %>
                    </tbody>
                </table>
                <button style ="float:right; margin-right: 1em;margin-top: 1em" class="btn-primary col-lg-2 btn" title="Download Report" id="exportBtn">Download</button>                
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $("#reportTable").dataTable({'iDisplayLength':4});
    $("#exportBtn").click(function(){
        window.location="<%=reportUrl%>";
    });
</script> 