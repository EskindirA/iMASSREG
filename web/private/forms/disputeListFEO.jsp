<%@page import="org.lift.massreg.entity.Dispute"%>
<%@page import="org.lift.massreg.util.Option"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="org.lift.massreg.entity.*"%>
<%@page import="org.lift.massreg.util.Constants"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    ArrayList<Dispute> disputes = currentParcel.getDisputes();
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
                                        out.println("<td>View/Edit/Delete</td>");
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
                            <button type='submit' id = 'finishButton' name = 'finishButton' class='btn btn-default col-lg-2' style='margin-left: 1em'>Finish</button>
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
                <form>
                    <div class="panel-body">
                        <form role="form" action="#" id="addDisputeForm">
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
<script type="text/javascript">
    <%
        String saveurl;
        if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
            saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_DISPUTE_FEO;
        } else {
            saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_DISPUTE_SEO;
        }
    %>
    function validate() {
        var returnValue = true;
//$("#firstName").toggle("error=off");
//$("#fathersName").toggle("error=off");
//$("#grandFathersName").toggle("error=off");
        if ($("#firstName").val().trim() === "") {
            returnValue = false;
//$("#firstName").toggle("error");
        }
        if ($("#fathersName").val().trim() === "") {
            returnValue = false;
//$("#fathersName").toggle("error");
        }
        if ($("#grandFathersName").val().trim() === "") {
            returnValue = false;
//$("#grandFathersName").toggle("error");
        }
        showMessage("Validation:");
        return returnValue;
    }
    function save() {
        $.ajax({
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
    $(document).ready(function() {
        $("#finishButton").click(function() {
            $.ajax({
                url: "<%=request.getContextPath()%>/Index?action=<%=Constants.ACTION_WELCOME_PART%>",
                                error: showajaxerror,
                                success: loadForward
                            });
                            return false;
                        });
                        $('#dataTables-example').dataTable();
                        $("#saveHolderButton").click(function() {
                            if (!validate()) {// validate
                                showError("Please input appropriate values in the highlighted fields");
                                return false;
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
                                        url: "<%=request.getContextPath()%>/Index?action=<%=Constants.ACTION_VIEW_PARCEL_FEO%>",
                                                                error: showajaxerror,
                                                                success: loadBackward
                                                            });
                                                        }
                                                    });
                                                    return false;
                                                });
                                            });
</script>
