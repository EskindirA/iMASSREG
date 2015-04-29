<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.Parcel"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Parcel> parcelsInCommitted = (ArrayList<Parcel>) request.getAttribute("parcels");

    String viewURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_WC;
    String filterURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_WELCOME_PART;
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-3 col-lg-offset-5 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CommonStorage.getText("welcome")%></h2>
        </div>
    </div>
    <div class="bs-example">
        <ul class="nav nav-tabs" style="margin-bottom: 15px;">
            <li class="active"><a href="#confirmedParcels" data-toggle="tab"><%=CommonStorage.getText("committed")%></a></li>
            <li ><a href="#certificatePrinting" data-toggle="tab"><%=CommonStorage.getText("certificate_printing")%></a></li>
        </ul>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade active in" id="confirmedParcels">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-lg-2"><%=CommonStorage.getText("committed_parcels")%></div>
                                    <div class="col-lg-2" style="vertical-align: middle"><label style="float: right"><%=CommonStorage.getText("status")%></label></div>
                                    <div class="col-lg-3" style="vertical-align: middle">
                                        <select class="form-control" id="status" name="status">
                                            <option value = 'confirmed'><%=CommonStorage.getText("confirmed")%></option>
                                            <option value = 'committed'><%=CommonStorage.getText("committed")%></option>
                                            <option value = 'committedbutnotconfirmed'><%=CommonStorage.getText("committed_but_not_confirmed")%></option>
                                            <option value = 'stillincorrection'><%=CommonStorage.getText("submitted_for_correction_but_not_corrected")%></option>
                                        </select>
                                    </div><div class="col-lg-2" style="vertical-align: middle"><label style="float: right"><%=CommonStorage.getText("kebele")%></label></div>
                                    <div class="col-lg-3" style="vertical-align: middle">
                                        <select class="form-control" id="displayKebele" name="displayKebele">
                                            <%
                                                Option[] kebeles = MasterRepository.getInstance().getAllKebeles();
                                                out.println("<option value = 'all'>" + CommonStorage.getText("select_a_kebele") + "</option>");
                                                for (int i = 0; i < kebeles.length; i++) {
                                                    out.println("<option value = '" + kebeles[i].getKey() + "'>" + kebeles[i].getValue() + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
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
                                                for (int i = 0; i < parcelsInCommitted.size(); i++) {
                                                    if (i % 2 == 0) {
                                                        out.println("<tr class='odd gradeA'>");
                                                    } else {
                                                        out.println("<tr class='even gradeA'>");
                                                    }
                                                    out.println("<td>" + parcelsInCommitted.get(i).getUpi() + "</td>");
                                                    out.println("<td>" + parcelsInCommitted.get(i).getCertificateNumber() + "</td>");
                                                    out.println("<td>" + parcelsInCommitted.get(i).hasDisputeText() + "</td>");
                                                    out.println("<td>" + parcelsInCommitted.get(i).getMeansOfAcquisition() + "</td>");
                                                    out.println("<td>" + parcelsInCommitted.get(i).getSurveyDate() + "</td>");

                                                    out.print("<td>");
                                                    out.print("<a href = '#' class='viewButton' "
                                                            + "data-upi='" + parcelsInCommitted.get(i).getUpi() + "'>" + CommonStorage.getText("view") + "</a>");
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
            <!-- Certificate Printing (+ Check List)-->
        
        </div>
    </div>
</div>
<div class="modal fade" id="view" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true" ></div>

<script>

    $("#displayKebele").val('<%=request.getSession().getAttribute("kebele").toString()%>');
    $("#status").val('<%=request.getSession().getAttribute("status").toString()%>');

    $("#displayKebele").change(function () {
        if ($("#displayKebele").val() === "") {
            showError("Please select a kebele");
        }else if ($("#status").val() === "") {
            showError("Please select a status");
        }
        else { // Call to the service to get the view parcel form
            $.ajax({
                success: loadInPlace,
                error: showajaxerror,
                type: 'POST',
                data: {
                    "kebele": $("#displayKebele").val(),
                    "status": $("#status").val()
                },
                url: '<%=filterURL%>'
            });
        }
        return false;
    });
    $("#status").change(function () {
        if ($("#displayKebele").val() === "") {
            showError("Please select a kebele");
        }else if ($("#status").val() === "") {
            showError("Please select a status");
        }
        else { // Call to the service to get the view parcel form
            $.ajax({
                success: loadInPlace,
                error: showajaxerror,
                type: 'POST',
                data: {
                    "kebele": $("#displayKebele").val(),
                    "status": $("#status").val()
                },
                url: '<%=filterURL%>'
            });
        }
        return false;
    });
    $('#dataTables').dataTable({
        "lengthMenu": [[30, 50, 100, -1], [30, 50, 100, "All"]]
    });
    $("#dataTables").on("click", ".viewButton", function () {
        var upi = $(this).attr("data-upi");
        viewParcel(upi);
        return false;
    });
    function loadViewParcel(result) {
        $("#view").html(result).modal();
    }
    function viewParcel(upi) {
        $.ajax({
            type: 'POST',
            url: "<%=viewURL%>",
            data: {"upi": upi},
            error: showajaxerror,
            success: loadViewParcel
        });
    }
    function closeModals() {
        $("#view").hide();
        $("#view").html("");
    }
</script>