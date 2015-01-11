<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.Parcel"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Parcel> parcelsInCorrection = (ArrayList<Parcel>) request.getAttribute("parcelsInCorrection");

    String editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_PARCEL_SUPERVISOR;
    String viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_SUPERVISOR;
    String deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_PARCEL_SUPERVISOR;
    String findurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_FIND_PARCEL_SUPERVISOR;
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-3 col-lg-offset-5 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome</h2>
        </div>
    </div>
    <div class="bs-example">
        <ul class="nav nav-tabs" style="margin-bottom: 15px;">
            <li class="active"><a href="#correctionParcels" data-toggle="tab">Corrections</a></li>
            <li><a href="#findParcel" data-toggle="tab">Find Parcels</a></li>
            <!--li class="disabled"><a>Disabled</a></li-->
        </ul>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade active in" id="correctionParcels">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">Parcels Submitted For Correction</div>
                            <div class="panel-body" >
                                <div>
                                    <table class="table table-striped table-bordered table-hover" id="dataTables" >
                                        <thead>
                                            <tr>
                                                <th>Administrative UPI</th>
                                                <th>Certificate Number</th>
                                                <th>Has Dispute</th>
                                                <th>Means of Acquisition</th>
                                                <th>Survey Date</th>
                                                <th>Fix</th>
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
                                                            + "data-upi='" + parcelsInCorrection.get(i).getUpi() + "'>View</a>");
                                                    if (parcelsInCorrection.get(i).canEdit(CommonStorage.getCurrentUser(request))) {
                                                        out.print("|");
                                                        out.print("<a href = '#' class='editButton' "
                                                                + "data-upi='" + parcelsInCorrection.get(i).getUpi() + "'>Edit</a>");
                                                        out.print("|");
                                                        out.print("<a href = '#' class='deleteButton' "
                                                                + "data-upi='" + parcelsInCorrection.get(i).getUpi() + "'>Delete</a>");
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
                                Find Parcel
                            </div>
                            <div class="panel-body" id="panelBody" >
                                <form role="form" action="#" method="POST" id="findParcelForm" name="findParcelForm" style="padding-left: 1em;padding-right: 1em">
                                    <div class="row">
                                        <input type="hidden" name="WoredaId" id="WoredaId" value="<%= CommonStorage.getCurrentWoredaId()%>" />
                                        <div class="form-group">
                                            <label>Kebele</label>
                                            <select class="form-control" id="kebele" name="kebele">
                                                <%
                                                    Option[] kebeles = MasterRepository.getInstance().getAllKebeles();
                                                    for (int i = 0; i < kebeles.length; i++) {
                                                        out.println("<option value = '" + CommonStorage.getCurrentWoredaIdForUPI() + "/" + kebeles[i].getKey() + "'>" + kebeles[i].getValue() + "</option>");
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <div class="form-group error" id="someId">
                                            <label>Parcel #</label>
                                            <input class="form-control" placeholder="Enter parcel # " name = "parcelNo" id = "parcelNo" type="type" size="5" maxlength="5" required value="${sessionScope.parcelNo}" autocomplete="off" required />
                                        </div>
                                        <div class="form-group">
                                            <label>Administrative UPI</label>
                                            <input class="form-control " placeholder="" name = "upi" id = "upi" disabled required />
                                        </div>
                                        <button type='submit' id = 'findParcelButton' name = 'nextButton' class='btn btn-default col-lg-3' style='float:right'>Find</button>
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
    $('#dataTables').dataTable();
    $('.editButton').click(function() {
        editParcel($(this).attr("data-upi"));
        return false;
    });
    $('.viewButton').click(function() {
        viewParcel($(this).attr("data-upi"));
        return false;
    });
    $('.deleteButton').click(function() {
        bootbox.confirm("Are you sure you want to delete this parcel back?", function(result) {
            if (result) {
                deleteParcel($(this).attr("data-upi"));
            }
        });
        return false;
    });
    $("#findParcel #findParcelButton").click(function() {
        updateUPI();
        if ($("#findParcel #upi").val() === "" || $("#findParcel #parcelNo").val() === "") {
            showError("Parcel Number and Administrative UPI are required fields");
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
    $("#findParcel #kebele").change(function() {
        updateUPI();
    });
    $("#findParcel #parcelNo").keypress(function() {
        updateUPI();
    });
    $("#findParcel #parcelNo").mouseup(function() {
        updateUPI();
    });
    $("#findParcel #parcelNo").keyup(function() {
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