<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.Parcel"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Parcel> parcelsInMinorCorrection = (ArrayList<Parcel>) request.getAttribute("parcelsInMinorCorrection");

    String viewURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_MCO;
    String editURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_PARCEL_MCO;
    String deleteURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_PARCEL_MCO;
    String confirmURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_CONFIRM_PARCEL_MCO;
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-3 col-lg-offset-5 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CommonStorage.getText("welcome")%></h2>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div><%=CommonStorage.getText("parcels_in_minor_correction")%></div>
                </div>
                <div class="panel-body" >
                    <div>
                        <table class="table table-striped table-bordered table-hover" id="dataTables" >
                            <thead>
                                <tr>
                                    <th><%=CommonStorage.getText("administrative_upi")%></th>
                                    <th><%=CommonStorage.getText("certificate_number")%></th>
                                    <th><%=CommonStorage.getText("has_dispute")%></th>
                                    <th><%=CommonStorage.getText("means_of_acquisition")%></th>
                                    <th><%=CommonStorage.getText("survey_date")%></th>
                                    <th><%=CommonStorage.getText("fix")%></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (int i = 0; i < parcelsInMinorCorrection.size(); i++) {
                                        if (i % 2 == 0) {
                                            out.println("<tr class='odd gradeA'>");
                                        } else {
                                            out.println("<tr class='even gradeA'>");
                                        }
                                        out.println("<td>" + parcelsInMinorCorrection.get(i).getUpi() + "</td>");
                                        out.println("<td>" + parcelsInMinorCorrection.get(i).getCertificateNumber() + "</td>");
                                        out.println("<td>" + parcelsInMinorCorrection.get(i).hasDisputeText() + "</td>");
                                        out.println("<td>" + parcelsInMinorCorrection.get(i).getMeansOfAcquisition() + "</td>");
                                        out.println("<td>" + parcelsInMinorCorrection.get(i).getSurveyDate() + "</td>");

                                        out.print("<td>");
                                        out.print("<a href = '#' class='viewButton' "
                                            + "data-upi='" + parcelsInMinorCorrection.get(i).getUpi() + "'>" + CommonStorage.getText("view") + "</a> | ");
                                        out.print("<a href = '#' class='editButton' "
                                            + "data-upi='" + parcelsInMinorCorrection.get(i).getUpi() + "'>" + CommonStorage.getText("edit") + "</a> | ");
                                        out.print("<a href = '#' class='deleteButton' "
                                            + "data-upi='" + parcelsInMinorCorrection.get(i).getUpi() + "'>" + CommonStorage.getText("delete") + "</a> |");
                                        out.print("<a href = '#' class='confirmButton' "
                                            + "data-upi='" + parcelsInMinorCorrection.get(i).getUpi() + "'>" + CommonStorage.getText("confirm") + "</a>");
                                        out.println("</td>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </div> <!-- /.table-responsive -->
                </div> <!-- /.panel-body -->
            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div>
</div>

<script>
    function editParcel(upi) {
        $.ajax({
            type: 'POST',
            url: "<%=editURL%>",
            data: {"upi": upi},
            error: showajaxerror,
            success: loadForward
        });
    }
    function deleteParcel(upi) {
        $.ajax({
            type: 'POST',
            url: "<%=deleteURL%>",
            data: {"upi": upi},
            error: showajaxerror,
            success: loadForward
        });
    }
    function confirmParcel(upi) {
        $.ajax({
            type: 'POST',
            url: "<%=confirmURL%>",
            data: {"upi": upi},
            error: showajaxerror,
            success: loadForward
        });
    }
    function viewParcel(upi) {
        $.ajax({
            type: 'POST',
            url: "<%=viewURL%>",
            data: {"upi": upi},
            error: showajaxerror,
            success: loadForward
        });
    }
    $('#dataTables').dataTable( {
        "lengthMenu": [[30, 50, 100, -1], [30, 50, 100, "All"]]
    } );
    $("#dataTables").on("click", ".editButton", function () {
        editParcel($(this).attr("data-upi"));
        return false;
    });
    $("#dataTables").on("click", ".viewButton", function () {
        viewParcel($(this).attr("data-upi"));
        return false;
    });
    $("#dataTables").on("click", ".confirmButton", function () {
        var upi = $(this).attr("data-upi");
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_confirm_this_parcel")%>?", function (result) {
            if (result) {
                confirmParcel(upi);
            }
        });
        return false;
    });
    $("#dataTables").on("click", ".deleteButton", function () {
        var upi = $(this).attr("data-upi");
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_delete_this_parcel")%>?", function (result) {
            if (result) {
                deleteParcel(upi);
            }
        });
        return false;
    });
</script>