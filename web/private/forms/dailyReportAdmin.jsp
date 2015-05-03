<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.dto.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    ArrayList<DailyPerformance> report = (ArrayList<DailyPerformance>) request.getAttribute("report");

%>
<style>

    tbody{
        page-break-inside: avoid;
    }
    table{
        border-right-style:solid;
        border-right-color: #555 ; 
        border-right-width: 1px;
        border-top-style:solid;
        border-top-color: #555 ; 
        border-top-width: 1px;
        page-break-inside: avoid;
    }
    td,th{
        border-left-style:solid;
        border-left-color: #555 ; 
        border-left-width: 1px;  
        border-bottom-style:solid;
        border-bottom-color: #555 ; 
        border-bottom-width: 1px;  
        page-break-inside: avoid;
    }
    .warning{
        float: right;
        text-align: right;
        margin-right: 2em;
        font-weight: bolder;
        color: #C40000;
    }
</style>
<div class="table-responsive">
    <table class="table table-striped table-bordered table-hover" >
        <caption><center>Daily Performance Report</center></caption>
        <thead>
            <tr>
                <th><%=CommonStorage.getText("name")%></th>
                <th><%=CommonStorage.getText("first_entry_operator")%></th>
                <th><%=CommonStorage.getText("second_entry_operator")%></th>
                <th><%=CommonStorage.getText("supervisor")%></th>
            </tr>
        </thead>
        <tbody>
            <%
                for (int i = 0; i < report.size(); i++) {
                    out.println("<tr>");
                        out.println("<td>" + report.get(i).getUser()+"</td>");
                        out.println("<td>" + report.get(i).getFirstEntry() + "</td>");
                        out.println("<td>" + report.get(i).getSecondEntry()+ "</td>");
                        out.println("<td>" + report.get(i).getSupervisor()+ "</td>");
                    out.println("</tr>");
                }
            %>
        </tbody>
    </table>

</div>