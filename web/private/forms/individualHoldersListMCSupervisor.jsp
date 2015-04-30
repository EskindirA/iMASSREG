<%@page import="org.lift.massreg.dto.ParcelDifference"%>
<%@page import="org.lift.massreg.dto.IndividualHolderDifference"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    ArrayList<IndividualHolder> holders = currentParcel.getIndividualHolders();
    ParcelDifference parcelDifference = (ParcelDifference) request.getAttribute("currentParcelDifference");

    String saveurl, viewurl, editurl, deleteurl, backurl, nexturl;
    saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_HOLDER_MCSUPERVISOR;
    viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_INDIVIDUAL_HOLDER_MCSUPERVISOR;
    editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_INDIVIDUAL_HOLDER_MCSUPERVISOR;
    deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_INDIVIDUAL_HOLDER_MCSUPERVISOR;
    backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_MCSUPERVISOR;
    if (currentParcel.hasOrphanHolder()) {
        nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_GUARDIANS_LIST_MCSUPERVISOR;
    } else {
        nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PERSONS_WITH_INTEREST_LIST_MCSUPERVISOR;
    }
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CommonStorage.getText("parcel_holders_list")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"> 
                    <%=CommonStorage.getText("parcel")%>: <%=CommonStorage.getText("administrative_upi")%> - ${sessionScope.upi}
                    <%
                        if (currentParcel.canEdit(CommonStorage.getCurrentUser(request))) {
                            out.println(parcelDifference.isIndividualHolderDetails() ? "<span style='margin-left: 3em' class='discrepancy-field'>" + CommonStorage.getText("there_is_a_discrepancy_in_holder_details") + "</span>" : "");
                        }
                    %>

                          <span style='float:right' class='<%= parcelDifference.isHoldersCount()
                            ? "discrepancy-field" : ""%>'><%=CommonStorage.getText("holders_count")%>:<%=currentParcel.getHolderCount()%></span>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                                <tr>
                                    <th><%=CommonStorage.getText("holder_id")%></th>
                                    <th><%=CommonStorage.getText("name")%></th>
                                    <th><%=CommonStorage.getText("sex")%></th>
                                    <th><%=CommonStorage.getText("date_of_birth")%></th>
                                    <th><%=CommonStorage.getText("family_role")%></th>
                                    <th><%=CommonStorage.getText("has_physical_impairment")%></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (int i = 0; i < holders.size(); i++) {
                                        if (i % 2 == 0) {
                                            out.println("<tr class='odd gradeA'>");
                                        } else {
                                            out.println("<tr class='even gradeA'>");
                                        }
                                        out.println("<td>" + holders.get(i).getId() + "</td>");
                                        out.println("<td>" + holders.get(i).getFullName() + "</td>");
                                        out.println("<td>" + holders.get(i).getSexText() + "</td>");
                                        out.println("<td>" + holders.get(i).getDateOfBirth() + "</td>");
                                        out.println("<td>" + holders.get(i).getFamilyRoleText() + "</td>");
                                        out.println("<td>" + holders.get(i).hasPhysicalImpairmentText() + "</td>");
                                        out.println("<td>");
                                        out.println("<a href = '#' class = 'viewButton' "
                                                + "data-registeredOn = '" + holders.get(i).getRegisteredOn() + "' "
                                                + "data-holderId = '" + holders.get(i).getId() + "'>" + CommonStorage.getText("view") + "</a>");

                                        if (editable) {
                                            out.println("|");
                                            out.println("<a href = '#' class='editButton' data-holderId='"
                                                    + holders.get(i).getId() + "' data-registeredOn='"
                                                    + holders.get(i).getRegisteredOn() + "'>" + CommonStorage.getText("edit") + "</a>");
                                            out.println("|");
                                            out.println("<a href = '#' class='deleteButton' data-holderId='"
                                                    + holders.get(i).getId() + "' data-registeredOn='"
                                                    + holders.get(i).getRegisteredOn() + "'>" + CommonStorage.getText("delete") + "</a>");

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
                                    out.println("<button type='submit' id = 'addHolderButton' name = 'addHolderButton' class='btn btn-default col-lg-2 col-lg-offset-6' data-toggle='modal' data-target='#addModal' >"+CommonStorage.getText("add")+"</button>");
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
                <h4 class="modal-title"><%=CommonStorage.getText("register_a_holder")%></h4>
            </div>            <!-- /modal-header -->
            <div class="modal-body">
                <form role="form" action="#" id="addHolderForm">
                    <div class="panel-body">
                        <div class="row">
                            <div class="form-group">
                                <label><%=CommonStorage.getText("holder_id")%></label>
                                <input class="form-control " type="text" id="holderId" name="holderId" />
                            </div>
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
                            <div class="form-group">
                                <label><%=CommonStorage.getText("family_role")%></label>
                                <select class="form-control" id="familyRole" name="familyRole" >
                                    <%
                                        Option[] familyRoleTypes = MasterRepository.getInstance().getAllfamilyRoleTypes();
                                        for (int i = 0; i < familyRoleTypes.length; i++) {
                                            out.println("<option value = '" + familyRoleTypes[i].getKey() + "'>" + familyRoleTypes[i].getValue() + "</option>");
                                        }
                                    %>
                                </select>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("has_physical_impairment")%></label>
                                <select class="form-control" id="physicalImpairment" name="physicalImpairment"  >
                                    <option value = 'false'><%=CommonStorage.getText("no")%></option>
                                    <option value = 'true'><%=CommonStorage.getText("yes")%></option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("is_orphan")%></label>
                                <select class="form-control" id="isOrphan" name="isOrphan" >
                                    <option value = 'false'><%=CommonStorage.getText("no")%></option>
                                    <option value = 'true'><%=CommonStorage.getText("yes")%></option>
                                </select>
                            </div>
                        </div> <!-- /.row (nested) -->
                    </div> <!-- /.panel-body -->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("cancel")%></button>
                <input type="submit" id="saveHolderButton" class="btn btn-primary" value = "<%=CommonStorage.getText("add")%>" />
            </div> 
        </div>
    </div>
</div>
<div class="modal fade" id="viewModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
<script type="text/javascript">
    var calendarAdd = $.calendars.instance("ethiopian", "am");
    $("#addHolderForm #dateOfBirth").calendarsPicker({calendar: calendarAdd});

    function closeModals() {
        $("#editModal").hide();
        $("#editModal").html("");
        $("#viewModal").hide();
        $("#viewModal").html("");
    }
    function validate(formId) {
        var returnValue = true;
        $("#" + formId + " #holderId").toggleClass("error-field", false);
        /*
         $("#" + formId + " #firstName").toggleClass("error-field", false);
         $("#" + formId + " #fathersName").toggleClass("error-field", false);
         $("#" + formId + " #grandFathersName").toggleClass("error-field", false);
         $("#" + formId + " #sex").toggleClass("error-field", false);
         $("#" + formId + " #familyRole").toggleClass("error-field", false);
         $("#" + formId + " #physicalImpairment").toggleClass("error-field", false);
         $("#" + formId + " #isOrphan").toggleClass("error-field", false);
         */
        if ($("#" + formId + " #holderId").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #holderId").toggleClass("error-field", true);
        }
        /*
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
         if ($("#" + formId + " #familyRole").val().trim() === "") {
         returnValue = false;
         $("#" + formId + " #familyRole").toggleClass("error-field", true);
         }
         if ($("#" + formId + " #physicalImpairment").val().trim() === "") {
         returnValue = false;
         $("#" + formId + " #physicalImpairment").toggleClass("error-field", true);
         }
         if ($("#" + formId + " #isOrphan").val().trim() === "") {
         returnValue = false;
         $("#" + formId + " #isOrphan").toggleClass("error-field", true);
         }
         
         */
        return returnValue;
    }

    function loadViewHolder(result) {
        $("#editModal").html("").hide();
        $("#viewModal").html(result).modal();
    }
    function loadEditHolder(result) {
        $("#viewModal").html("").hide();
        $("#editModal").html(result);
    }
    function deleteHolder(holderId, regOn) {
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_delete_this_holder")%> ?", function (result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: "<%=deleteurl%>",
                    data: {"registeredOn": regOn, "holderId": holderId},
                    error: showajaxerror,
                    success: loadInPlace
                });
            }
        });
    }
    function editHolder(holderId, regOn) {
        $.ajax({
            type: 'POST',
            url: "<%=editurl%>",
            data: {
                "registeredOn": regOn,
                "holderId": holderId
            },
            error: showajaxerror,
            success: loadViewHolder
        });
    }
    function viewHolder(holderId, regOn) {
        $.ajax({
            type: 'POST',
            url: "<%=viewurl%>",
            data: {
                "registeredOn": regOn,
                "holderId": holderId
            },
            error: showajaxerror,
            success: loadViewHolder
        });
    }
    function validateHolderList() {
        //showError("validateHolderList");
        return true;
    }
    function save() {
        $.ajax({
            type: 'POST',
            url: "<%=saveurl%>",
            data: {
                "dateofbirth": $("#addHolderForm #dateOfBirth").val(),
                "familyrole": $("#addHolderForm #familyRole").val(),
                "firstname": $("#addHolderForm #firstName").val(),
                "fathersname": $("#addHolderForm #fathersName").val(),
                "grandfathersname": $("#addHolderForm #grandFathersName").val(),
                "holderId": $("#addHolderForm #holderId").val(),
                "physicalImpairment": $("#addHolderForm #physicalImpairment").val(),
                "isOrphan": $("#addHolderFrom #isOrphan").val(),
                "sex": $("#addHolderForm #sex").val()
            },
            error: showajaxerror,
            success: loadForward
        });
    }
    $('#dataTables-example').dataTable({"bPaginate": false});
    $('.editButton').click(function () {
        editHolder($(this).attr("data-holderId"), $(this).attr("data-registeredOn"));
        return false;
    });
    $('.viewButton').click(function () {
        viewHolder($(this).attr("data-holderId"), $(this).attr("data-registeredOn"));
        return false;
    });
    $('.deleteButton').click(function () {
        deleteHolder($(this).attr("data-holderId"), $(this).attr("data-registeredOn"));
        return false;
    });
    $("#saveHolderButton").click(function () {
        if (!validate("addHolderForm")) {// validate
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
        } else {
            save();// save
            $("#addModal").hide();// close modale
        }
        return false;
    });
    $("#nextButton").click(function () {
        if (validateHolderList()) {
            $.ajax({
                type: 'POST',
                url: "<%=nexturl%>",
                error: showajaxerror,
                success: loadForward
            });
        }
        return false;
    });
    $("#backButton").click(function () {
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_go_back")%>?", function (result) {
            if (result) {
                $.ajax({
                    url: "<%=backurl%>",
                    error: showajaxerror,
                    success: loadBackward
                });
            }
        });
        return false;
    });
</script>
