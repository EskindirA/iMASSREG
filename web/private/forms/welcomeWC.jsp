<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.Parcel"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Parcel> parcelsInCommitted = (ArrayList<Parcel>) request.getAttribute("parcels");

    String viewURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_WC;
    String filterURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_WELCOME_PART;
    String printCertifcateURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_PRINT_CERTIFCATE_WC;
    String printCheckListURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_PRINT_CHEACKLIST_WC;
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-3 col-lg-offset-5 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CommonStorage.getText("welcome")%></h2>
        </div>
    </div>
    <div class="bs-example">
        <ul class="nav nav-tabs" style="margin-bottom: 15px;">
            <li class="active"><a href="#confirmedParcels" data-toggle="tab"><%=CommonStorage.getText("confirmed")%></a></li>
            <li ><a href="#checkList" data-toggle="tab"><%=CommonStorage.getText("certificate")%></a></li>
            <li ><a href="#settings" data-toggle="tab"><%=CommonStorage.getText("settings")%></a></li>
        </ul>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade active in" id="confirmedParcels">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-lg-2"><%=CommonStorage.getText("confirmed_parcels")%></div>
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
            <div class="tab-pane fade" id="checkList">
                <div class="row">
                    <div class="col-lg-4 col-lg-offset-4">
                        <div class="panel panel-default" >
                            <div class="panel-heading">
                                <%=CommonStorage.getText("certificate")%>
                            </div>
                            <div class="panel-body" id="panelBody" >
                                <form role="form" action="#" method="POST" id="printCheckListForm" name="printCheckListForm" style="padding-left: 1em;padding-right: 1em">
                                    <div class="row">
                                        <input type="hidden" name="WoredaId" id="WoredaId" value="<%= CommonStorage.getCurrentWoredaId()%>" />
                                        <div class="form-group">
                                            <label><%=CommonStorage.getText("kebele")%></label>
                                            <select class="form-control" id="checkListKebele" name="checkListKebele">
                                                <%
                                                    for (int i = 0; i < kebeles.length; i++) {
                                                        out.println("<option value = '" + kebeles[i].getKey() + "'>" + kebeles[i].getValue() + "</option>");
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <button type='submit' id = 'printCheckListButton' name = 'printCheckListButton' class='btn btn-default col-lg-4' style='float:right; margin-left: 1em'><%=CommonStorage.getText("print_checklist")%></button>
                                        <button type='submit' id = 'printCertifcateButton' name = 'printCertifcateButton' class='btn btn-default col-lg-4' style='float:right'><%=CommonStorage.getText("print_certificate")%></button>
                                    </div> <!-- /.row (nested) -->
                                </form>
                            </div> <!-- /.panel-body -->
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade" id="settings">
                <div class="row">
                    <div class="col-lg-4 col-lg-offset-4">
                        <div class="panel panel-default" >
                            <div class="panel-heading">
                                <%=CommonStorage.getText("settings")%>
                            </div>
                            <div class="panel-body" id="panelBody" >
                                <center><img src="<%=request.getContextPath() + "/Index?action=" + Constants.ACTION_GET_IMAGE_WC + "&name=" + CommonStorage.getCurrentUser(request).getUserId() + ".png"%>" style="width:150px;height: 80px"/></center>
                                <form role="form" action="<%=request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_SETTINGS_WC%>" method="POST" id="settingsForm" name="settingsForm" style="padding-left: 1em;padding-right: 1em" enctype="multipart/form-data">
                                    <div class="row">
                                        <div class="form-group">
                                            <label><%=CommonStorage.getText("signature")%></label>
                                            <input type="file" class="form-control" id="signature" name="signature" />
                                            <input type="hidden" id="hi" name="hi" value="Hello" />
                                        </div>
                                        <button type='submit' id = 'updateSettingsButton' name = 'updateSettingsButton' class='btn btn-default col-lg-3' style='float:right'><%=CommonStorage.getText("update")%></button>
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
<div class="modal fade" id="view" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true" ></div>

<script>

    $("#displayKebele").val('<%=request.getSession().getAttribute("kebele").toString()%>');
    $("#status").val('<%=request.getSession().getAttribute("status").toString()%>');

    $("#displayKebele").change(function () {
        if ($("#displayKebele").val() === "") {
            showError("Please select a kebele");
        } else if ($("#status").val() === "") {
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
        } else if ($("#status").val() === "") {
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

    $("#checkList #printCheckListButton").click(function () {
        if ($("#kebele").val() === "") {
            showError("Please select a kebele");
        }
        else { // Call to the service to get print form
            $.ajax({
                success: function (data) {
                    var mywindow = window.open('#', 'Certifcate Printing - Check List');
                    mywindow.document.write('<html><head>');
                    mywindow.document.write('<title>Certificate Printing - Check List</title>');
                    mywindow.document.write('</head>');
                    mywindow.document.write('<body>');
                    mywindow.document.write(data);
                    mywindow.document.write('</body></html>');
                    mywindow.document.close();
                    mywindow.focus();
                    mywindow.print();
                    mywindow.close();
                    //$("#publicDisplayDetail").html(data);
                },
                error: showajaxerror,
                type: 'POST',
                url: '<%=printCheckListURL%>',
                data: {
                    "kebele": $("#checkList #checkListKebele").val()
                }
            });
        }
        return false;
    });

    $("#checkList #printCertifcateButton").click(function () {
        updateUPI();
        if ($("#kebele").val() === "") {
            showError("Please select a kebele");
        }
        else { // Call to the service to get print form
            $.ajax({
                success: function (data) {
                    var mywindow = window.open('#', 'Certifcate Printing - Check List');
                    mywindow.document.write('<html><head>');
                    mywindow.document.write('<title>Certificate Printing - Check List</title>');
                    mywindow.document.write('</head>');
                    mywindow.document.write('<body>');
                    mywindow.document.write(data);
                    mywindow.document.write('</body></html>');
                    mywindow.document.close();
                    mywindow.focus();
                    mywindow.print();
                    mywindow.close();
                    //$("#publicDisplayDetail").html(data);
                },
                error: showajaxerror,
                type: 'POST',
                url: '<%=printCertifcateURL%>',
                data: {
                    "kebele": $("#checkList #checkListKebele").val()
                }

            });
        }
        return false;
    });

    function validateSettings() {
        var returnValue = true;
        $("#settings #signature").toggleClass("error-field", false);
        if ($("#settings #signature").val() === "") {
            returnValue = false;
            $("#settings #signature").toggleClass("error-field", true);
        }
        return returnValue;
    }
    $("#settings #updateSettingsButton").click(function () {
        if (validateSettings()) {
            bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_save_the_settings")%>?", function (result) {
                if (result) {
                    $("#settings #settingsForm").submit();
                }
            });
        } else {
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
        }
        return false;
    });

</script>