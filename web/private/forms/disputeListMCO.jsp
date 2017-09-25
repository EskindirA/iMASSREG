<%@page import="org.lift.massreg.dto.ParcelDifference"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    ArrayList<Dispute> disputes = currentParcel.getDisputes();
    String saveurl, viewurl, deleteurl, editurl, backurl, finishurl, welcomeurl;
    saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_DISPUTE_MCO;
    viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_DISPUTE_MCO;
    deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_DISPUTE_MCO;
    editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_DISPUTE_MCO;
    finishurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_FINISH_DISPUTE_MCO;
    welcomeurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_WELCOME_PART;
    if (currentParcel.getHolding() == 1) {
        backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PERSONS_WITH_INTEREST_LIST_MCO;
    } else {
        backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_MCO;
    }
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-3 col-lg-offset-4 ">
            <h2 class="page-header"><%=CommonStorage.getText("dispute_list")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"> 
                    <%=CommonStorage.getText("parcel")%>: <%=CommonStorage.getText("administrative_upi") + " - " + request.getParameter("upi")%> 
                    <span style='float:right' ><%=CommonStorage.getText("dispute_count")%>:<%=currentParcel.getDisputeCount()%></span>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                                <tr>
                                    <th><%=CommonStorage.getText("name")%></th>
                                    <th><%=CommonStorage.getText("sex")%></th>
                                    <th><%=CommonStorage.getText("type")%></th>
                                    <th><%=CommonStorage.getText("dispute_status")%></th>
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
                                                + "data-registeredOn='" + disputes.get(i).getRegisteredOn() + "'>" + CommonStorage.getText("view") + "</a>");
                                        out.println("|");
                                        out.println("<a href = '#' class='editButton' "
                                            + "data-registeredOn='" + disputes.get(i).getRegisteredOn() + "'>" + CommonStorage.getText("edit") + "</a>");
                                        out.println("|");
                                        out.println("<a href = '#' class='deleteButton' "
                                            + "data-registeredOn='" + disputes.get(i).getRegisteredOn() + "'>" + CommonStorage.getText("delete") + "</a>");
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
                            <button type='submit' id = 'addDisputeButton' name = 'addDisputeButton' class='btn btn-default col-lg-2 col-lg-offset-6' data-toggle='modal' data-target='#addModal' ><%= CommonStorage.getText("add") %></button>
                            <%
                                if (currentParcel.getDisputeCount() >= 1 && currentParcel.canEdit(CommonStorage.getCurrentUser(request))) {
                                    out.println("<button type='submit' id = 'finishButton' name = 'finishButton' class='btn btn-default col-lg-2' style='margin-left: 1em'>" + CommonStorage.getText("finish") + "</button>");
                                } else {
                                    out.println("<button type='submit' id = 'finishButton' name = 'finishButton' class='btn btn-default col-lg-2' style='margin-left: 1em' disabled>" + CommonStorage.getText("finish") + "</button>");
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
                <h4 class="modal-title"><%=CommonStorage.getText("register_a_dispute")%></h4>
            </div>            <!-- /modal-header -->
            <div class="modal-body">
                <form id="addDisputeForm">
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
                                    <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                    <option value = 'm'><%=CommonStorage.getText("male")%></option>
                                    <option value = 'f'><%=CommonStorage.getText("female")%></option>
                                    <option value = 'n'><%=CommonStorage.getText("not_available")%></option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("dispute_type")%></label>
                                <select class="form-control" id="disputeType" name="disputeType" >
                                    <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                    <%
                                        Option[] disputeTypes = MasterRepository.getInstance().getAllDisputeTypes();
                                        for (int i = 0; i < disputeTypes.length-1; i++) {
                                            out.println("<option value = '" + disputeTypes[i].getKey() + "'>" + disputeTypes[i].getValue() + "</option>");
                                        }
                                    %>
                                </select>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("dispute_status")%></label>
                                <select class="form-control" id="disputeStatus" name="disputeStatus" >
                                    <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                    <%
                                        Option[] disputeStatusTypes = MasterRepository.getInstance().getAllDisputeStatusTypes();
                                        for (int i = 0; i < disputeStatusTypes.length-1; i++) {
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
                <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("cancel")%></button>
                <input type="submit" id="saveDisputeButton" class="btn btn-primary" value = "<%=CommonStorage.getText("add")%>" />
            </div> 
        </div>
    </div>
</div>
<div class="modal fade" id="viewModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
<script type="text/javascript">
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
        $("#" + formId + " #disputeType").toggleClass("error-field", false);
        $("#" + formId + " #disputeStatus").toggleClass("error-field", false);
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
        if ($("#" + formId + " #disputeType").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #disputeType").toggleClass("error-field", true);
        }
        if ($("#" + formId + " #disputeStatus").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #disputeStatus").toggleClass("error-field", true);
        }
        return returnValue;
    }
    */
    function loadViewDispute(result) {
        $("#editModal").html("").hide();
        $("#viewModal").html(result).modal();
    }
    function loadEditDispute(result) {
        $("#viewModal").html("").hide();
        $("#editModal").html(result);
    }
    function deleteDispute(regOn) {
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_delete_this_dispute")%> ?", function (result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: "<%=deleteurl%>",
                    data: {"registeredOn": regOn,"upi":'<%=request.getParameter("upi")%>'},
                    error: showajaxerror,
                    success: loadInPlace
                });
            }
        });
    }
    function editDispute(regOn) {
        $.ajax({
            type: 'POST',
            url: "<%=editurl%>",
            data: {"registeredOn": regOn,"upi":'<%=request.getParameter("upi")%>'},
            error: showajaxerror,
            success: loadViewDispute
        });
    }
    function viewDispute(regOn) {
        $.ajax({
            type: 'POST',
            url: "<%=viewurl%>",
            data: {
                "registeredOn": regOn,
                "upi":'<%=request.getParameter("upi")%>'
            },
            error: showajaxerror,
            success: loadViewDispute
        });
    }
    function validateDisputeList() {
        showMessage("validateDisputeList");
    }
    function save() {
        $.ajax({
            type: 'POST',
            url: "<%=saveurl%>",
            data: {
                "upi":'<%=request.getParameter("upi")%>',
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
    $("#finishButton").click(function () {
        $.ajax({
            type: 'POST',
            url: "<%=finishurl%>",
            data:{"upi":'<%=request.getParameter("upi")%>'},
            error: showajaxerror,
            success: loadForward
        });
        return false;
    });
    $('#dataTables-example').dataTable({"bPaginate": false});
    $('.editButton').click(function () {
        editDispute($(this).attr("data-registeredOn"));
        return false;
    });
    $('.viewButton').click(function () {
        viewDispute($(this).attr("data-registeredOn"));
        return false;
    });
    $('.deleteButton').click(function () {
        deleteDispute($(this).attr("data-registeredOn"));
        return false;
    });
    $("#saveDisputeButton").click(function () {
        if (!validate("addDisputeForm")) {// validate
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
        } else {
            save();// save
            $("#addModal").hide();// close modale
        }
        return false;
    });
    $("#goToWelcomeButton").click(function () {
        $.ajax({
            type: 'POST',
            url: "<%=welcomeurl%>",
            data:{"upi":'<%=request.getParameter("upi")%>'},
            error: showajaxerror,
            success: loadForward
        });
    });
    $("#backButton").click(function () {
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_go_back")%>?", function (result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: "<%=backurl%>",
                    data:{"upi":'<%=request.getParameter("upi")%>'},
                    error: showajaxerror,
                    success: loadBackward
                });
            }
        });
        return false;
    });
</script>
