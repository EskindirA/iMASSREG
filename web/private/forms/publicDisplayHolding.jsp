<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.dto.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    ArrayList<HoldingPublicDisplayDTO> holdingListI = (ArrayList<HoldingPublicDisplayDTO>) request.getAttribute("holdersListI");
    //ArrayList<HolderPublicDisplayDTO> holdersListO = (ArrayList<HolderPublicDisplayDTO>) request.getAttribute("holdersListO");
    ArrayList<String> missingParcels = (ArrayList<String>) request.getAttribute("missingParcels");

%>
<style>
    @media print
    {
        table { page-break-after:auto }
        tr    { page-break-inside:avoid; page-break-after:auto;clear:both!important;}
        td    { page-break-inside:avoid; page-break-after:auto; }
        thead { display: table-header-group;}
        tfoot { display:table-footer-group; }
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
                <th><%=CommonStorage.getText("holding_no")%></th>
                <th><%=CommonStorage.getText("name")%></th>
                <th><%=CommonStorage.getText("sex")%></th>
                <th><%=CommonStorage.getText("parcel_number")%></th>
                <th><%=CommonStorage.getText("has_dispute")%></th>
                <th><%=CommonStorage.getText("area")%></th>
                <th><%=CommonStorage.getText("guardian")%></th>
                <th><%=CommonStorage.getText("incomplete")%>?</th>
            </tr>
        </thead>
        <tbody>
            <%
                //for (int i = 0; i < holdingListI.size(); i++) {
                int i = 0;
                out.println("<tr>");
                ArrayList<HoldingHolderDTO> holders = holdingListI.get(i).getHolders();
                ArrayList<HoldingParcelPublicDisplayDTO> parcels = holdingListI.get(i).getParcels();

                int parcelCount = parcels.size();
                int holderCount = holders.size();

                out.println("<td rowspan='" + Math.max(parcelCount, holderCount) + "' >" + holdingListI.get(i).getHoldingNumber() + "</td>");
                boolean h = holders.size() > parcels.size();
                for (int j = 0; j < holders.size(); j++) {
                    if (h && j == holders.size() - 1) {
                        out.println("<td rowspan='" + (holders.size() - parcels.size()) + "' >" + holders.get(i).getName() + "</td>");
                        out.println("<td rowspan='" + (holders.size() - parcels.size()) + "' >" + holders.get(i).getSex() + "</td>");
                    } else {
                        out.println("<td>" + holders.get(i).getName() + "</td>");
                        out.println("<td>" + holders.get(i).getSex() + "</td>");
                    }
                }
                for (int j = 0; j < parcels.size(); j++) {
                    if (!h && j == parcels.size() - 1) {
                        out.println(String.format("<td rowspan='" + (parcels.size() - holders.size()) + "' > %05d</td>", parcels.get(j).getParcelId()));
                        out.println("<td rowspan='" + (parcels.size() - holders.size()) + "' >" + parcels.get(j).hasDisputeText() + "</td>");
                        out.println("<td rowspan='" + (parcels.size() - holders.size()) + "' >" + String.format("%.5f", parcels.get(j).getArea()) + "</td>");
                        String guardiansList = "";
                        for (String guardian : parcels.get(j).getGuardians()) {
                            guardiansList += guardian + ",";
                        }
                        out.println("<td rowspan='" + (parcels.size() - holders.size()) + "' >" + guardiansList.substring(0, guardiansList.length() - 1) + "</td>");
                        boolean missing = parcels.get(j).hasMissingValue();
                        out.println("<td rowspan='" + (parcels.size() - holders.size()) + "' " + (missing ? "class='warning'" : "") + " >" + parcels.get(j).hasMissingValueText() + "</td>");
                    } else {
                        out.println(String.format("<td> %05d</td>", parcels.get(j).getParcelId()));
                        out.println("<td>" + parcels.get(j).hasDisputeText() + "</td>");
                        out.println("<td>" + String.format("%.5f", parcels.get(j).getArea()) + "</td>");
                        String guardiansList = "";
                        for (String guardian : parcels.get(j).getGuardians()) {
                            guardiansList += guardian + ",";
                        }
                        out.println("<td>" + guardiansList.substring(0, guardiansList.length() - 1) + "</td>");
                        boolean missing = parcels.get(j).hasMissingValue();
                        out.println("<td " + (missing ? "class='warning'" : "") + " >" + parcels.get(j).hasMissingValueText() + "</td>");
                    }

                }
                out.print("</tr>");
                // }
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
                    int last = missingParcels.size() % step;
                    for (i = 0;
                            i < missingParcels.size()
                            - last; i += step) {
                        out.println("<tr>");
                        for (int j = 0; j < step; j++) {
                            if (missingParcels.get(i + j) != null && !missingParcels.get(i + j).isEmpty()) {
                                out.println(String.format("<td style='text-align:center'> %05d</td>", Integer.parseInt(missingParcels.get(i + j))));
                            } else {
                                out.println("<td></td>");
                            }
                        }
                        out.print("</tr>");
                    }

                    out.println(
                            "<tr>");
                    for (i = missingParcels.size() - last;
                            i < missingParcels.size();
                            i++) {
                        if (!missingParcels.get(i).equalsIgnoreCase("null") && !missingParcels.get(i).isEmpty()) {
                            out.println(String.format("<td style='text-align:center'>" + missingParcels.get(i)) + "</td>");
                        } else {
                            out.println("<td></td>");
                        }
                    }

                    out.print(
                            "</tr>");
                %>
            </tbody>
        </table>
    </center>
</div>