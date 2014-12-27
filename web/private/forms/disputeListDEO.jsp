<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    ArrayList<Dispute> disputes = currentParcel.getDisputes();
    String saveurl, viewurl, deleteurl, editurl, backurl,finishurl;
    if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
        saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_DISPUTE_FEO;
        viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_DISPUTE_FEO;
        deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_DISPUTE_FEO;
        editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_DISPUTE_FEO;
        finishurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_FINISH_DISPUTE_FEO;
        if (currentParcel.getHolding() == 1) {
            backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PERSONS_WITH_INTEREST_LIST_FEO;
        } else {
            backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_FEO;
        }
    } else {
        saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_DISPUTE_SEO;
        viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_DISPUTE_SEO;
        deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_DISPUTE_SEO;
        editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_DISPUTE_SEO;
        finishurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_FINISH_DISPUTE_SEO;
        if (currentParcel.getHolding() == 1) {
            backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PERSONS_WITH_INTEREST_LIST_SEO;
        } else {
            backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_SEO;
        }
    }
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-3 col-lg-offset-4 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Disputes List</h2>
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
                                    <th>Name</th>
                                    <th>Sex</th>
                                    <th>Type</th>
                                    <th>Status</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (int i = 0; i < disputes.size(); i++) {
                                        if (i % 2 == 0) {
                                            out.println("<tr class='odd gradeA'>");
                                        } else {
                                            out.println("<tr class='even gradeA'>");
                                        }
                                        out.println("<td>" + disputes.get(i).getFullName() + "</td>");
                                        out.println("<td>" + disputes.get(i).getSexText() + "</td>");
                                        out.println("<td>" + disputes.get(i).getDisputeTypeText() + "</td>");
                                        out.println("<td>" + disputes.get(i).getDisputeStatusText() + "</td>");
                                        out.println("<td>");
                                        out.println("<a href = '#' class='viewButton' "
                                                + "data-registeredOn='" + disputes.get(i).getRegisteredOn() + "'>View</a>");
                                        if (editable) {
                                            out.println("|");
                                            out.println("<a href = '#' class='editButton' "
                                                    + "data-registeredOn='" + disputes.get(i).getRegisteredOn() + "'>Edit</a>");
                                            out.println("|");
                                            out.println("<a href = '#' class='deleteButton' "
                                                    + "data-registeredOn='" + disputes.get(i).getRegisteredOn() + "'>Delete</a>");
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
                                    out.println("<button type='submit' id = 'addDisputeButton' name = 'addDisputeButton' class='btn btn-default col-lg-2 col-lg-offset-6' data-toggle='modal' data-target='#addModal' >Add</button>");
                                } else {
                                    out.println("<span class='col-lg-2 col-lg-offset-6'></span>");
                                }
                            %>
                            <%
                                if (currentParcel.getDisputeCount() >= 1) {
                                    out.println("<button type='submit' id = 'finishButton' name = 'finishButton' class='btn btn-default col-lg-2' style='margin-left: 1em'>Finish</button>");
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
                <h4 class="modal-title">Register a Dispute</h4>
            </div>            <!-- /modal-header -->
            <div class="modal-body">
                <form id="addDisputeForm">
                    <div class="panel-body">
                        <div class="row">
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
                                <label>Dispute Type</label>
                                <select class="form-control" id="disputeType" name="disputeType" >
                                    <%
                                        Option[] disputeTypes = MasterRepository.getInstance().getAllDisputeTypes();
                                        for (int i = 0; i < disputeTypes.length; i++) {
                                            out.println("<option value = '" + disputeTypes[i].getKey() + "'>" + disputeTypes[i].getValue() + "</option>");
                                        }
                                    %>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Dispute Status</label>
                                <select class="form-control" id="disputeStatus" name="disputeStatus" >
                                    <%
                                        Option[] disputeStatusTypes = MasterRepository.getInstance().getAllDisputeStatusTypes();
                                        for (int i = 0; i < disputeStatusTypes.length; i++) {
                                            out.println("<option value = '" + disputeStatusTypes[i].getKey() + "'>" + disputeStatusTypes[i].getValue() + "</option>");
                                        }
                                    %>
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
    function validate(formId) {
        var returnValue = true;
        $("#" + formId + " #firstName").toggleClass("error-field",false);
        $("#" + formId + " #fathersName").toggleClass("error-field",false);
        $("#" + formId + " #grandFathersName").toggleClass("error-field",false);
        if ($("#" + formId + " #firstName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #firstName").toggleClass("error-field",true);
        }
        if ($("#" + formId + " #fathersName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #fathersName").toggleClass("error-field",true);
        }
        if ($("#" + formId + " #grandFathersName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #grandFathersName").toggleClass("error-field",true);
        }
        return returnValue;
    }
    function loadViewDispute(result) {
        $("#editModal").html("").hide();
        $("#viewModal").html(result).modal();
    }
    function loadEditDispute(result) {
        $("#viewModal").html("").hide();
        $("#editModal").html(result);
    }
    function deleteDispute(regOn) {
        bootbox.confirm("Are you sure you want delete thise dispute ?", function(result) {
            if (result) {
                $.ajax({
                    type:'POST',
                    url: "<%=deleteurl%>",
                    data: {"registeredOn": regOn},
                    error: showajaxerror,
                    success: loadInPlace
                });
            }
        });
    }
    function editDispute(regOn) {
        $.ajax({
            type:'POST',
            url: "<%=editurl%>",
            data: {"registeredOn": regOn},
            error: showajaxerror,
            success: loadViewHolder
        });
    }
    function viewDispute(regOn) {
        $.ajax({
            type:'POST',
            url: "<%=viewurl%>",
            data: {
                "registeredOn": regOn
            },
            error: showajaxerror,
            success: loadViewDispute
        });
    }
    function validateDisputeList(){
        showMessage("validateDisputeList"); 
    }
    function save() {
        $.ajax({
            type:'POST',
            url: "<%=saveurl%>",
            data: {
                "firstname": $("#firstName").val(),
                "fathersname": $("#fathersName").val(),
                "grandfathersname": $("#grandFathersName").val(),
                "sex": $("#sex").val(),
                "disputeType": $("#disputeType").val(),
                "disputeStatus": $("#disputeStatus").val()
            },
            error: showajaxerror,
            success: loadForward
        });
    }
    $("#finishButton").click(function() {
        $.ajax({
            type:'POST',
            url: "<%=finishurl%>",
            error: showajaxerror,
            success: loadForward
        });
        return false;
    });
    $('#dataTables-example').dataTable({"bPaginate": false});
    $('.editButton').click(function() {
        editDispute($(this).attr("data-registeredOn"));
        return false;
    });
    $('.viewButton').click(function() {
        viewDispute($(this).attr("data-registeredOn"));
        return false;
    });
    $('.deleteButton').click(function() {
        deleteDispute($(this).attr("data-registeredOn"));
        return false;
    });
    $("#saveHolderButton").click(function() {
        if (!validate("addDisputeForm")) {// validate
            showError("Please input appropriate values in the highlighted fields");
        } else {
            save();// save
            $("#addModal").hide();// close modale
        }
        return false;
    });
    $("#backButton").click(function() {
        bootbox.confirm("Are you sure you want to go back?", function(result) {
            if (result) {
                $.ajax({
                    type:'POST',
                    url: "<%=backurl%>",
                    error: showajaxerror,
                    success: loadBackward
                });
            }
        });
        return false;
    });
</script>
