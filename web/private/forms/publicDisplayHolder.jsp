<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.dto.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    ArrayList<HolderPublicDisplayDTO> holdersListI = (ArrayList<HolderPublicDisplayDTO>) request.getAttribute("holdersListI");
    ArrayList<HolderPublicDisplayDTO> holdersListO = (ArrayList<HolderPublicDisplayDTO>) request.getAttribute("holdersListO");
    ArrayList<String> parcels = (ArrayList<String>) request.getAttribute("missingParcels");

%>
<style>
    @media print
    {
        table { page-break-after:auto }
        tr    { page-break-inside:avoid; page-break-after:auto;clear:both!important;}
        td    { page-break-inside:avoid; page-break-after:auto; }
        thead { display: table-header-group;}
        tbody{ page-break-inside: auto; display: table-header-group; }
        td,th{ border-left-style:solid; border-left-color: #555 ; 
               border-left-width: 1px; border-bottom-style:solid;
               border-bottom-color: #555 ; border-bottom-width: 1px;
               page-break-inside: avoid; page-break-after: auto;}
    }
    table{ border-right-style:solid; border-right-color: #555 ; border-right-width: 1px;
           border-top-style:solid; border-top-color: #555 ; border-top-width: 1px; page-break-after: auto;}
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
                <th><%=CommonStorage.getText("incomplete")%>?</th>
            </tr>
        </thead>
        <tbody>
            <%
                for (int i = 0; i < holdersListI.size(); i++) {
                    out.println("<tr>");
                    int parcelCount = holdersListI.get(i).getParcels().size();
                    out.println("<td rowspan='" + parcelCount + "' >" + holdersListI.get(i).getId() + "(" + holdersListI.get(i).getParcels().size() + ")</td>");
                    out.println("<td rowspan='" + parcelCount + "' >" + holdersListI.get(i).getName() + "</td>");
                    out.println("<td rowspan='" + parcelCount + "' >" + holdersListI.get(i).getSex() + "</td>");

                    for (int j = 0; j < holdersListI.get(i).getParcels().size(); j++) {
                        out.println(String.format("<td> %05d</td>", holdersListI.get(i).getParcels().get(j).getParcelId()));
                        out.println("<td>" + holdersListI.get(i).getParcels().get(j).hasDisputeText() + "</td>");
                        out.println("<td>" + String.format("%.5f", holdersListI.get(i).getParcels().get(j).getArea()) + "</td>");
                        String guardiansList = "";
                        for (String guardian : holdersListI.get(i).getParcels().get(j).getGuardians()) {
                            guardiansList += guardian + ",";
                        }
                        out.println("<td>" + guardiansList.substring(0, guardiansList.length() - 1) + "</td>");
                        String coholdersList = "";
                        for (String holder : holdersListI.get(i).getParcels().get(j).getOtherHolders()) {
                            coholdersList += holder + ",";
                        }
                        out.println("<td>" + coholdersList.substring(0, coholdersList.length() - 1) + "</td>");
                        boolean missing = holdersListI.get(i).getParcels().get(j).hasMissingValue();
                        out.println("<td " + (missing ? "class='warning'" : "") + " >" + holdersListI.get(i).getParcels().get(j).hasMissingValueText() + "</td>");
                        out.print("</tr><tr>");
                    }
                    out.println("</tr>");
                }
            %>
        </tbody>
    </table>

    <table class="table table-striped table-bordered table-hover" >
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
                <th><%=CommonStorage.getText("incomplete")%>?</th>
            </tr>
        </thead>
        <tbody>
            <%
                for (int i = 0; i < holdersListO.size(); i++) {
                    out.println("<tr>");
                    int parcelCount = holdersListO.get(i).getParcels().size();
                    out.println("<td rowspan='" + parcelCount + "' >" + holdersListO.get(i).getId() + "</td>");
                    out.println("<td rowspan='" + parcelCount + "' >" + holdersListO.get(i).getName() + "</td>");
                    out.println("<td rowspan='" + parcelCount + "' >" + holdersListO.get(i).getSexText() + "</td>");

                    for (int j = 0; j < holdersListO.get(i).getParcels().size(); j++) {
                        out.println(String.format("<td> %05d</td>", holdersListO.get(i).getParcels().get(j).getParcelId()));
                        out.println("<td>" + holdersListO.get(i).getParcels().get(j).hasDisputeText() + "</td>");
                        out.println("<td>" + String.format("%.5f", holdersListO.get(i).getParcels().get(j).getArea()) + "</td>");
                        String guardiansList = "";
                        for (String guardian : holdersListO.get(i).getParcels().get(j).getGuardians()) {
                            guardiansList += guardian + ",";
                        }
                        out.println("<td>" + guardiansList.substring(0, guardiansList.length() - 1) + "</td>");
                        String coholdersList = "";
                        for (String holder : holdersListO.get(i).getParcels().get(j).getOtherHolders()) {
                            coholdersList += holder + ",";
                        }
                        out.println("<td>" + coholdersList.substring(0, coholdersList.length() - 1) + "</td>");
                        boolean missing = holdersListO.get(i).getParcels().get(j).hasMissingValue();
                        out.println("<td " + (missing ? "class='warning'" : "") + " >" + holdersListO.get(i).getParcels().get(j).hasMissingValueText() + "</td>");
                        out.print("</tr><tr>");
                    }
                    out.println("</tr>");
                }
            %>
        </tbody>
    </table>
    <center>
        <table class="table table-striped table-bordered table-hover" style="width: 100%" >
            <caption>
                <h3><%=CommonStorage.getText("parcels_with_no_textual_information")%></h3>
                <h5><%=CommonStorage.getText("kebele")%> - <%=request.getAttribute("kebeleName")%></h5>
            </caption>
            <tbody>
                <%
                    int step = 20;
                    int last = parcels.size() % step;
                    for (int i = 0; i < parcels.size() - last; i += step) {
                        out.println("<tr>");
                        for (int j = 0; j < step; j++) {
                            if (parcels.get(i + j) != null && !parcels.get(i + j).isEmpty()) {
                                out.println(String.format("<td style='text-align:center'> %05d</td>", Integer.parseInt(parcels.get(i + j))));
                            } else {
                                out.println("<td></td>");
                            }
                        }
                        out.print("</tr>");
                    }
                    out.println("<tr>");
                    for (int i = parcels.size() - last; i < parcels.size(); i++) {
                        if (!parcels.get(i).equalsIgnoreCase("null") && !parcels.get(i).isEmpty()) {
                            out.println(String.format("<td style='text-align:center'>" + parcels.get(i)) + "</td>");
                        } else {
                            out.println("<td></td>");
                        }
                    }
                    out.print("</tr>");
                %>
            </tbody>
        </table>
    </center>
</div>