<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.Parcel"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Parcel> parcelsInCorrection = (ArrayList<Parcel>) request.getAttribute("parcelsInCorrection");
    
    String editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_PARCEL_MCSUPERVISOR;
    String viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_MCSUPERVISOR;
    String deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_PARCEL_MCSUPERVISOR;
    String findurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_FIND_PARCEL_MCSUPERVISOR;
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-3 col-lg-offset-5 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CommonStorage.getText("welcome")%></h2>
        </div>
    </div>
    <div class="bs-example">
        <ul class="nav nav-tabs" style="margin-bottom: 15px;">
            <li class="active"><a href="#correctionParcels" data-toggle="tab"><%=CommonStorage.getText("corrections")%></a></li>
            <li><a href="#findParcel" data-toggle="tab"><%=CommonStorage.getText("find_a_parcel")%></a></li>
            <!--li class="disabled"><a>Disabled</a></li-->
        </ul>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade active in" id="correctionParcels">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading"><%=CommonStorage.getText("parcels_submitted_for_correction")%></div>
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
                                                for (int i = 0; i < parcelsInCorrection.size(); i++) {
                                                    if (i % 2 == 0) {
                                                        out.println("<tr class='odd gradeA'>");
                                                    } else {
                                                        out.println("<tr class='even gradeA'>");
                                                    }
                                                    out.println("<td>" + parcelsInCorrection.get(i).getUpi() + "</td>");
                                                    out.println("<td>" + parcelsInCorrection.get(i).getCertificateNumber() + "</td>");
                                                    out.println("<td>" + parcelsInCorrection.get(i).hasDisputeText() + "</td>");
                                                    out.println("<td>" + parcelsInCorrection.get(i).getMeansOfAcquisition() + "</td>");
                                                    out.println("<td>" + parcelsInCorrection.get(i).getSurveyDate() + "</td>");
                                                    
                                                    out.print("<td>");
                                                    out.print("<a href = '#' class='viewButton' "
                                                            + "data-upi='" + parcelsInCorrection.get(i).getUpi() + "'>" + CommonStorage.getText("view") + "</a>");
                                                    if (parcelsInCorrection.get(i).canEdit(CommonStorage.getCurrentUser(request))) {
                                                        out.print("|");
                                                        out.print("<a href = '#' class='editButton' "
                                                                + "data-upi='" + parcelsInCorrection.get(i).getUpi() + "'>" + CommonStorage.getText("edit") + "</a>");
                                                        out.print("|");
                                                        out.print("<a href = '#' class='deleteButton' "
                                                                + "data-upi='" + parcelsInCorrection.get(i).getUpi() + "'>" + CommonStorage.getText("delete") + "</a>");
                                                    }
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
            <div class="tab-pane fade" id="findParcel">
                <div class="row">
                    <div class="col-lg-4 col-lg-offset-4">
                        <div class="panel panel-default" >
                            <div class="panel-heading">
                                <%=CommonStorage.getText("find_a_parcel")%>
                            </div>
                            <div class="panel-body" id="panelBody" >
                                <form role="form" action="#" method="POST" id="findParcelForm" name="findParcelForm" style="padding-left: 1em;padding-right: 1em">
                                    <div class="row">
                                        <input type="hidden" name="WoredaId" id="WoredaId" value="<%= CommonStorage.getCurrentWoredaId()%>" />
                                        <div class="form-group">
                                            <label><%=CommonStorage.getText("kebele")%></label>
                                            <select class="form-control" id="kebele" name="kebele">
                                                <%
                                                    Option[] kebeles = MasterRepository.getInstance().getAllKebeles();
                                                    for (int i = 0; i < kebeles.length; i++) {
                                                        out.println("<option value = '" + kebeles[i].getKey() + "'>" + kebeles[i].getValue() + "</option>");
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <div class="form-group error" id="someId">
                                            <label><%=CommonStorage.getText("parcel_id")%></label>
                                            <input class="form-control" name = "parcelNo" id = "parcelNo" type="type" size="5" maxlength="5" required value="${sessionScope.parcelNo}" autocomplete="off" required />
                                        </div>
                                        <div class="form-group">
                                            <label><%=CommonStorage.getText("administrative_upi")%></label>
                                            <input class="form-control " name = "upi" id = "upi" disabled required />
                                        </div>
                                        <button type='submit' id = 'findParcelButton' name = 'nextButton' class='btn btn-default col-lg-3' style='float:right'><%=CommonStorage.getText("find")%></button>
                                    </div> <!-- /.row (nested) -->
                                </form>
                            </div> <!-- /.panel-body -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    function editParcel(upi) {
        $.ajax({
            type: 'POST',
            url: "<%=editurl%>",
            data: {"upi": upi},
            error: showajaxerror,
            success: loadForward
        });
    }
    function deleteParcel(upi) {
        $.ajax({
            type: 'POST',
            url: "<%=deleteurl%>",
            data: {"upi": upi},
            error: showajaxerror,
            success: loadForward
        });
    }
    function viewParcel(upi) {
        $.ajax({
            type: 'POST',
            url: "<%=viewurl%>",
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
    $("#dataTables").on("click", ".deleteButton", function () {
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_delete_this_parcel")%>?", function (result) {
            if (result) {
                deleteParcel($(this).attr("data-upi"));
            }
        });
        return false;
    });
    $("#findParcel #findParcelButton").click(function () {
        updateUPI();
        if ($("#kebele").val() === "") {
            showError("Please select a kebele");
        } else if ($("#findParcel #upi").val() === "" || $("#findParcel #parcelNo").val() === "") {
            showError("<%=CommonStorage.getText("parcel_number_and_administrative_upi_are_required_fields")%>");
        }
        else { // Call to the service to get the view parcel form
            $.ajax({
                success: loadForward,
                error: showajaxerror,
                type: 'POST',
                data: {
                    "upi": $("#findParcel #upi").val(),
                    "parcelNo": $("#findParcel #parcelNo").val(),
                    "kebele": $("#findParcel #kebele").val()},
                url: '<%=findurl%>'
            });
        }
        return false;
    });
    $("#findParcel #kebele").change(function () {
        updateUPI();
    });
    $("#findParcel #parcelNo").keypress(function () {
        updateUPI();
    });
    $("#findParcel #parcelNo").mouseup(function () {
        updateUPI();
    });
    $("#findParcel #parcelNo").keyup(function () {
        updateUPI();
    });
    function updateUPI() {
        // Pad the upi with leading zeros
        var parcelNo = "" + $("#findParcel #parcelNo").val();
        var pad = "00000";
        var value = pad.substring(0, pad.length - parcelNo.length) + parcelNo;
        $("#findParcel #upi").val($("#findParcel #kebele").val() + "/" + value);
    }
</script>