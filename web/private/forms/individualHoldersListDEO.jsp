<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    ArrayList<IndividualHolder> holders = currentParcel.getIndividualHolders();
    String saveurl, viewurl, editurl, deleteurl, backurl, nexturl;
    if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
        saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_HOLDER_FEO;
        viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_INDIVIDUAL_HOLDER_FEO;
        editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_INDIVIDUAL_HOLDER_FEO;
        deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_INDIVIDUAL_HOLDER_FEO;
        backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_FEO;
        nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PERSONS_WITH_INTEREST_LIST_FEO;
    } else {
        saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_HOLDER_SEO;
        viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_INDIVIDUAL_HOLDER_SEO;
        editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_INDIVIDUAL_HOLDER_SEO;
        deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_INDIVIDUAL_HOLDER_SEO;
        backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_SEO;
        nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PERSONS_WITH_INTEREST_LIST_SEO;
    }
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Parcel Holders' List</h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"> 
                    Parcel: Administrative UPI - ${sessionScope.upi}
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                                <tr>
                                    <th>Holder ID</th>
                                    <th>Name</th>
                                    <th>Sex</th>
                                    <th>Date of Birth</th>
                                    <th>Family Role</th>
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
                                        out.println("<td>");
                                        out.println("<a href = '#' class = 'viewButton' "
                                                + "data-registeredOn = '" + holders.get(i).getRegisteredOn() + "' "
                                                + "data-holderId = '" + holders.get(i).getId() + "'>View</a>");

                                        if (editable) {
                                            out.println("|");
                                            out.println("<a href = '#' class='editButton' data-holderId='"
                                                    + holders.get(i).getId() + "' data-registeredOn='"
                                                    + holders.get(i).getRegisteredOn() + "'>Edit</a>");
                                            out.println("|");
                                            out.println("<a href = '#' class='deleteButton' data-holderId='"
                                                    + holders.get(i).getId() + "' data-registeredOn='"
                                                    + holders.get(i).getRegisteredOn() + "'>Delete</a>");

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
                        <button type="submit" id = "backButton" class="btn btn-default col-lg-2 col-lg-offset-1">Back</button>
                    </div>
                    <div class="col-lg-6">
                        <div class="row">
                            <%
                                if (editable) {
                                    out.println("<button type='submit' id = 'addHolderButton' name = 'addHolderButton' class='btn btn-default col-lg-2 col-lg-offset-6' data-toggle='modal' data-target='#addModal' >Add</button>");
                                } else {
                                    out.println("<span class='col-lg-2 col-lg-offset-6'></span>");
                                }
                                if (currentParcel.getHolderCount() >= 1) {
                                    out.println("<button type='submit' id = 'nextButton' name = 'nextButton' class='btn btn-default col-lg-2' style='margin-left: 1em'>Next</button>");
                                } else {
                                    out.println("<button type='submit' id = 'nextButton' name = 'nextButton' class='btn btn-default col-lg-2' style='margin-left: 1em' disabled>Next</button>");
                                }
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
                <h4 class="modal-title">Register a holder</h4>
            </div>            <!-- /modal-header -->
            <div class="modal-body">
                <form role="form" action="#" id="addHolderForm">
                    <div class="panel-body">
                        <div class="row">
                            <div class="form-group">
                                <label>Holder Id</label>
                                <input class="form-control " type="text" id="holderId" name="holderId" />
                            </div>
                            <div class="form-group">
                                <label>First Name</label>
                                <input class="form-control " id="firstName" name="firstName" />
                            </div>
                            <div class="form-group">
                                <label>Father's Name</label>
                                <input class="form-control " id="fathersName" name="fathersName" />
                            </div> 
                            <div class="form-group">
                                <label>Grandfather's Name</label>
                                <input class="form-control " id="grandFathersName" name="grandFathersName" />
                            </div>
                            <div class="form-group">
                                <label>Sex</label>
                                <select class="form-control" id="sex" name="sex">
                                    <option value = 'm'>Male</option>
                                    <option value = 'f'>Female</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Date of Birth</label>
                                <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='date'/>
                            </div>
                            <div class="form-group">
                                <label>Family Role</label>
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
                                <label>Has Physical Impairment</label>
                                <select class="form-control" id="physicalImpairment" name="physicalImpairment"  >
                                    <option value = 'false'>No</option>
                                    <option value = 'true'>Yes</option>
                                </select>
                            </div>
                        </div> <!-- /.row (nested) -->
                    </div> <!-- /.panel-body -->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <input type="submit" id="saveHolderButton" class="btn btn-primary" value = "Add" />
            </div> 
        </div>
    </div>
</div>
<div class="modal fade" id="viewModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
<script type="text/javascript">
    function closeModals() {
        $("#editModal").hide();
        $("#editModal").html("");
        $("#viewModal").hide();
        $("#viewModal").html("");
    }
    function validate(formId) {
        var returnValue = true;
        $("#" + formId + " #holderId").toggleClass("error-field", false);
        $("#" + formId + " #firstName").toggleClass("error-field", false);
        $("#" + formId + " #fathersName").toggleClass("error-field", false);
        $("#" + formId + " #grandFathersName").toggleClass("error-field", false);
        if ($("#" + formId + " #holderId").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #holderId").toggleClass("error-field", true);
        }
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
        bootbox.confirm("Are you sure you want delete thise holder ?", function(result) {
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
        showError("validateHolderList");
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
                "sex": $("#addHolderForm #sex").val()
            },
            error: showajaxerror,
            success: loadForward
        });
    }
    $('#dataTables-example').dataTable({"bPaginate": false});
    $('.editButton').click(function() {
        editHolder($(this).attr("data-holderId"), $(this).attr("data-registeredOn"));
        return false;
    });
    $('.viewButton').click(function() {
        viewHolder($(this).attr("data-holderId"), $(this).attr("data-registeredOn"));
        return false;
    });
    $('.deleteButton').click(function() {
        deleteHolder($(this).attr("data-holderId"), $(this).attr("data-registeredOn"));
        return false;
    });
    $("#saveHolderButton").click(function() {
        if (!validate("addHolderForm")) {// validate
            showError("Please input appropriate values in the highlighted fields");
        } else {
            save();// save
            $("#addModal").hide();// close modale
        }
        return false;
    });
    $("#nextButton").click(function() {
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
    $("#backButton").click(function() {
        bootbox.confirm("Are you sure you want to go back?", function(result) {
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
