<%@page import="org.lift.massreg.dto.ParcelDifference"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    ArrayList<Guardian> guardians = currentParcel.getGuardians();
    ParcelDifference parcelDifference = new ParcelDifference();
    boolean reviewMode = false;
    if (request.getSession().getAttribute("reviewMode") != null) {
        reviewMode = Boolean.parseBoolean(request.getSession().getAttribute("reviewMode").toString());
    }
    if (reviewMode) {
        parcelDifference = (ParcelDifference) request.getAttribute("currentParcelDifference");
    }

    String saveurl, viewurl, editurl, deleteurl, backurl, nexturl, finshurl;
    if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.CORRECTION_FIRST_ENTRY_OPERATOR) {
        saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_GUARDIAN_MCFEO;
        viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_GUARDIAN_MCFEO;
        editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_GUARDIAN_MCFEO;
        deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_GUARDIAN_MCFEO;
        backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_MCFEO;
        nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PERSONS_WITH_INTEREST_LIST_MCFEO;
    } else {
        saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_GUARDIAN_MCSEO;
        viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_GUARDIAN_MCSEO;
        editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_GUARDIAN_MCSEO;
        deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_GUARDIAN_MCSEO;
        backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_MCSEO;
        nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PERSONS_WITH_INTEREST_LIST_MCSEO;
    }
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4 ">
            <h2 class="page-header">&nbsp;&nbsp;<%=CommonStorage.getText("guardians_list")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"> 
                    <%=CommonStorage.getText("parcel")%>: <%=CommonStorage.getText("administrative_upi")%> - ${sessionScope.upi}
                    <%
                        if (reviewMode && parcelDifference.isGuardiansDetails()) {
                            out.println("<span style='margin-left: 3em' class='discrepancy-field'>" + CommonStorage.getText("there_is_a_discrepancy_in_guardians_details") + "</span>");
                        }
                        
                    %>
                    <span style='float:right' class='<%= reviewMode
                                                && parcelDifference.isGuardiansCount()
                                                        ? "discrepancy-field" : ""%>'><%=CommonStorage.getText("guardians_count")%>:<%=currentParcel.getGuardiansCount()%></span>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                                <tr>
                                    <th><%=CommonStorage.getText("name")%></th>
                                    <th><%=CommonStorage.getText("sex")%></th>
                                    <th><%=CommonStorage.getText("date_of_birth")%></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (int i = 0; i < guardians.size(); i++) {
                                        if (i % 2 == 0) {
                                            out.println("<tr class='odd gradeA'>");
                                        } else {
                                            out.println("<tr class='even gradeA'>");
                                        }
                                        out.println("<td>" + guardians.get(i).getFullName() + "</td>");
                                        out.println("<td>" + guardians.get(i).getSexText() + "</td>");
                                        out.println("<td>" + guardians.get(i).getDateOfBirth() + "</td>");
                                        out.println("<td>");
                                        out.println("<a href = '#' class = 'viewButton' "
                                                + "data-registeredOn = '" + guardians.get(i).getRegisteredOn() + "' >" + CommonStorage.getText("view") + "</a>");

                                        if (editable) {
                                            out.println("|");
                                            out.println("<a href = '#' class='editButton' data-registeredOn='"
                                                    + guardians.get(i).getRegisteredOn() + "'>" + CommonStorage.getText("edit") + "</a>");
                                            out.println("|");
                                            out.println("<a href = '#' class='deleteButton' data-registeredOn='"
                                                    + guardians.get(i).getRegisteredOn() + "'>" + CommonStorage.getText("delete") + "</a>");

                                        }
                                        out.println("</td>");
                                        out.println("</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </div> <!-- /.table-responsive -->
                </div> <!-- /.panel-body -->
                <div class="row">
                    <div class="col-lg-6">
                        <button type="submit" id = "backButton" class="btn btn-default col-lg-2 col-lg-offset-1"><%=CommonStorage.getText("back")%></button>
                    </div>
                    <div class="col-lg-6">
                        <div class="row">
                            <%
                                if (editable) {
                                    out.println("<button type='submit' id = 'addGuardianButton' name = 'addGuardianButton' class='btn btn-default col-lg-2 col-lg-offset-6' data-toggle='modal' data-target='#addModal' >"+CommonStorage.getText("add")+"</button>");
                                } else {
                                    out.println("<span class='col-lg-2 col-lg-offset-6'></span>");
                                }
                                out.println("<button type='submit' id = 'nextButton' name = 'nextButton' class='btn btn-default col-lg-2' style='margin-left: 1em'>"+CommonStorage.getText("next")+"</button>");
                            %>
                        </div>                        
                    </div>
                </div>
                <br/>
            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div> <!-- /.row -->
</div>
<div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabe" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><%=CommonStorage.getText("register_a_guardian")%></h4>
            </div>            <!-- /modal-header -->
            <div class="modal-body">
                <form role="form" action="#" id="addGuardianForm">
                    <div class="panel-body">
                        <div class="row">
                            <div class="form-group">
                                <label><%=CommonStorage.getText("first_name")%></label>
                                <input class="form-control " id="firstName" name="firstName" />
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("fathers_name")%></label>
                                <input class="form-control " id="fathersName" name="fathersName" />
                            </div> 
                            <div class="form-group">
                                <label><%=CommonStorage.getText("grandfathers_name")%></label>
                                <input class="form-control " id="grandFathersName" name="grandFathersName" />
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("sex")%></label>
                                <select class="form-control" id="sex" name="sex">
                                    <option value = 'm'><%=CommonStorage.getText("male")%></option>
                                    <option value = 'f'><%=CommonStorage.getText("female")%></option>
                                    <option value = 'n'><%=CommonStorage.getText("not_available")%></option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("date_of_birth")%></label>
                                <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='text' readonly style="background: #FFF !important"/>
                            </div>
                        </div> <!-- /.row (nested) -->
                    </div> <!-- /.panel-body -->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("cancel")%></button>
                <input type="submit" id="saveGuardianButton" class="btn btn-primary" value = "<%=CommonStorage.getText("add")%>" />
            </div> 
        </div>
    </div>
</div>
<div class="modal fade" id="viewModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
<script type="text/javascript">
    var calendarAdd = $.calendars.instance("ethiopian", "am");
    $("#addGuardianForm #dateOfBirth").calendarsPicker({calendar: calendarAdd});
    function closeModals() {
        $("#editModal").html("");
        $("#editModal").hide();
        $("#viewModal").hide();
        $("#viewModal").html("");
    }
    function validate(formId){
        return true;
    }
    /*
    function validate(formId) {
        var returnValue = true;
        $("#" + formId + " #firstName").toggleClass("error-field", false);
        $("#" + formId + " #fathersName").toggleClass("error-field", false);
        $("#" + formId + " #grandFathersName").toggleClass("error-field", false);
        $("#" + formId + " #sex").toggleClass("error-field", false);
        if ($("#" + formId + " #firstName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #firstName").toggleClass("error-field", true);
        }
        if ($("#" + formId + " #fathersName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #fathersName").toggleClass("error-field", true);
        }
        if ($("#" + formId + " #grandFathersName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #grandFathersName").toggleClass("error-field", true);
        }
        if ($("#" + formId + " #sex").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #sex").toggleClass("error-field", true);
        }
        return returnValue;
    }
    */
    function loadViewGuardian(result) {
        $("#editModal").html("").hide();
        $("#viewModal").html(result).modal();
    }
    function loadEditGuardian(result) {
        $("#viewModal").html("").hide();
        $("#editModal").html(result);
    }
    function deleteGuardian(regOn) {
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_delete_this_guardian")%> ?", function(result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: "<%=deleteurl%>",
                    data: {"registeredOn": regOn},
                    error: showajaxerror,
                    success: loadInPlace
                });
            }
        });
    }
    function editGuardian(regOn) {
        $.ajax({
            type: 'POST',
            url: "<%=editurl%>",
            data: {"registeredOn": regOn},
            error: showajaxerror,
            success: loadViewHolder
        });
    }
    function viewGuardian(regOn) {
        $.ajax({
            type: 'POST',
            url: "<%=viewurl%>",
            data: {"registeredOn": regOn},
            error: showajaxerror,
            success: loadViewHolder
        });
    }
    function validateGuardiansList() {
        //showError("validateGuardianList");
        return true;
    }
    function save() {
        $.ajax({
            type: 'POST',
            url: "<%=saveurl%>",
            data: {
                "dateofbirth": $("#addGuardianForm #dateOfBirth").val(),
                "firstname": $("#addGuardianForm #firstName").val(),
                "fathersname": $("#addGuardianForm #fathersName").val(),
                "grandfathersname": $("#addGuardianForm #grandFathersName").val(),
                "sex": $("#addGuardianForm #sex").val()
            },
            error: showajaxerror,
            success: loadForward
        });
    }
    $('#dataTables-example').dataTable({"bPaginate": false});
    $('.editButton').click(function() {
        editGuardian($(this).attr("data-registeredOn"));
        return false;
    });
    $('.viewButton').click(function() {
        viewGuardian($(this).attr("data-registeredOn"));
        return false;
    });
    $('.deleteButton').click(function() {
        deleteGuardian($(this).attr("data-registeredOn"));
        return false;
    });
    $("#saveGuardianButton").click(function() {
        if (!validate("addGuardianForm")) {// validate
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
        }
        else {
            save();// save
            $("#addModal").hide();// close modale
        }
        return false;
    });
    $("#nextButton").click(function() {
        if (validateGuardiansList()) {
            $.ajax({
                type: 'POST',
                url: "<%=nexturl%>",
                error: showajaxerror,
                success: loadForward
            });
        }
        return false;
    });
    $("#backButton").click(function() {
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_go_back")%>?", function(result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: "<%=backurl%>",
                    error: showajaxerror,
                    success: loadBackward
                });
            }
        });
        return false;
    });
</script>
