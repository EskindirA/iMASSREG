<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.dto.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    ArrayList<HolderPublicDisplayDTO> holdersList = (ArrayList<HolderPublicDisplayDTO>) request.getAttribute("holdersList");
    ArrayList<ParcelPublicDisplayDTO> parcels = (ArrayList<ParcelPublicDisplayDTO>) request.getAttribute("parcelList");

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
        <!--caption>
            Woreda - < %=CommonStorage.getCurrentWoredaName()%>
            Kebele - < %=request.getAttribute("kebeleName")%>
        </caption-->
        <thead>
            <tr>
                <th><%=CommonStorage.getText("holder_id")%></th>
                <th><%=CommonStorage.getText("name")%></th>
                <th><%=CommonStorage.getText("sex")%></th>
                <th><%=CommonStorage.getText("parcel_number")%></th>
                <th><%=CommonStorage.getText("has_dispute")%></th>
                <th><%=CommonStorage.getText("area")%></th>
                <th><%=CommonStorage.getText("guardian")%></th>
                <th><%=CommonStorage.getText("other_holders")%></th>
                <th><%=CommonStorage.getText("has_missing_value")%>?</th>
            </tr>
        </thead>
        <tbody>
            <%
                for (int i = 0; i < holdersList.size(); i++) {
                    out.println("<tr>");
                    int parcelCount = holdersList.get(i).getParcels().size();
                    out.println("<td rowspan='" + parcelCount + "' >" + holdersList.get(i).getId() + "</td>");
                    out.println("<td rowspan='" + parcelCount + "' >" + holdersList.get(i).getName() + "</td>");
                    out.println("<td rowspan='" + parcelCount + "' >" + holdersList.get(i).getSex() + "</td>");

                    for (int j = 0; j < holdersList.get(i).getParcels().size(); j++) {
                        out.println("<td>" + holdersList.get(i).getParcels().get(j).getParcelId() + "</td>");
                        out.println("<td>" + holdersList.get(i).getParcels().get(j).hasDisputeText() + "</td>");
                        out.println("<td>" + holdersList.get(i).getParcels().get(j).getArea() + "</td>");
                        String guardiansList = "";
                        for (String guardian : holdersList.get(i).getParcels().get(j).getGuardians()) {
                            guardiansList += guardian + ",";
                        }
                        out.println("<td>" + guardiansList.substring(0, guardiansList.length() - 1) + "</td>");
                        String coholdersList = "";
                        for (String holder : holdersList.get(i).getParcels().get(j).getOtherHolders()) {
                            coholdersList += holder + ",";
                        }
                        out.println("<td>" + coholdersList.substring(0, coholdersList.length() - 1) + "</td>");
                        boolean missing = holdersList.get(i).getParcels().get(j).hasMissingValue();
                        out.println("<td " + (missing ? "class='warning'" : "") + " >" + holdersList.get(i).getParcels().get(j).hasMissingValueText() + "</td>");
                        out.print("</tr><tr>");
                    }
                    out.println("</tr>");
                }
            %>
        </tbody>
    </table>
    <center>
        <table class="table table-striped table-bordered table-hover" >
            <caption>
                Parcels with no holder information: Kebele - <%=request.getAttribute("kebeleName")%>
            </caption>
            <thead>
                <tr>
                    <th><%=CommonStorage.getText("administrative_upi")%></th>
                    <th><%=CommonStorage.getText("parcel_number")%></th>
                    <th><%=CommonStorage.getText("has_dispute")%></th>
                    <th><%=CommonStorage.getText("area")%></th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (int i = 0; i < parcels.size(); i++) {
                        out.println("<tr>");
                        out.println("<td>" + parcels.get(i).getUpi() + "</td>");
                        out.println("<td>" + parcels.get(i).getParcelId() + "</td>");
                        out.println("<td>" + parcels.get(i).hasDisputeText() + "</td>");
                        out.println("<td>" + parcels.get(i).getArea() + "</td>");
                        out.print("</tr>");
                    }
                %>
            </tbody>
        </table>
    </center>
</div>