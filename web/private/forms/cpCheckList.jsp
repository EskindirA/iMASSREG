<%@page import="org.lift.massreg.dto.CertificatePrintingCheckList"%>
<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.dto.HolderPublicDisplayDTO"%>
<%
    ArrayList<CertificatePrintingCheckList> list = (ArrayList<CertificatePrintingCheckList>) request.getAttribute("list");
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
</style>
<div class="table-responsive">
    <table class="table table-striped table-bordered table-hover" >
        <!--caption>
            Woreda - < %=CommonStorage.getCurrentWoredaName()%>
            Kebele - < %=request.getAttribute("kebeleName")%>
        </caption-->
        <thead>
            <tr>
                <th><%=CommonStorage.getText("administrative_upi")%></th>
                <th><%=CommonStorage.getText("status")%></th>
                <th><%=CommonStorage.getText("remark")%></th>
            </tr>
        </thead>
        <tbody>
            <%
                for (int i = 0; i < list.size(); i++) {
                    out.println("<tr>");
                        out.println("<td >" + list.get(i).getUPI() + "</td>");
                        out.println("<td >" + list.get(i).getStage() + "</td>");
                        out.println("<td></td>");
                    out.println("</tr>");
                }
            %>
        </tbody>
    </table>
</div>