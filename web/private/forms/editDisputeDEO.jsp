<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    Timestamp registeredOn = Timestamp.valueOf(request.getParameter("registeredOn"));
    Dispute dispute = currentParcel.getDispute(currentParcel.getStage(), registeredOn);
    String updateurl;
    if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
        updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_DISPUTE_FEO;
    } else {
        updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_DISPUTE_SEO;
    }
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">Edit Dispute Details</h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="editDisputeFrom" name="editDisputeFrom" >
                <div class="panel-body">
                    <div class="row">
                        <div class="form-group">
                            <label>First Name</label>
                            <input class="form-control " id="firstName" name="firstName" value="<%=dispute.getFirstName()%>" />
                        </div>
                        <div class="form-group">
                            <label>Father's Name</label>
                            <input class="form-control " id="fathersName" name="fathersName" value="<%=dispute.getFathersName()%>" />
                        </div> 
                        <div class="form-group">
                            <label>Grandfather's Name</label>
                            <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=dispute.getGrandFathersName()%>" />
                        </div>
                        <div class="form-group">
                            <label>Sex</label>
                            <select class="form-control" id="sex" name="sex" value="<%=dispute.getSex()%>" >
                                <option value = 'm'>Male</option>
                                <option value = 'f'>Female</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Dispute Type</label>
                            <select class="form-control" id="disputeType" name="disputeType" value="<%=dispute.getDisputeType()%>" >
                                <%
                                    Option[] disputeTypes = MasterRepository.getInstance().getAllDisputeTypes();
                                    for (int i = 0; i < disputeTypes.length; i++) {
                                        out.println("<option value = '" + disputeTypes[i].getKey() + "'>" + disputeTypes[i].getValue() + "</option>");
                                    }
                                %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Dispute is/being handled by</label>
                            <select class="form-control" id="disputeStatus" name="disputeStatus" value="<%=dispute.getDisputeStatus()%>" >
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
            <input type='submit' id='updateDisputeButton' name='saveDisputeButton' class='btn btn-info' value = 'Save' />
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div> 
    </div>
</div>
<script type="text/javascript">

    $("#editDisputeFrom select").each(function() {
        $(this).val($(this).attr("value"));
    });
    $("#editDisputeButton").click(function() {
        editDispute("<%=request.getParameter("registeredOn")%>");
    });
    $("#updateDisputeButton").click(function() {
        if (!validate("editDisputeFrom")) {// validate
            showError("Please input appropriate values in the highlighted fields");
            return false;
        } else {
            update();// save
            $("#addModal").hide();// close modale
        }
        closeModals();
        return false;
    });
    function update() {
        $.ajax({
            url: "<%=updateurl%>",
            data: { "firstname": $("#editDisputeFrom #firstName").val(),
                "fathersname": $("#editDisputeFrom #fathersName").val(),
                "grandfathersname": $("#editDisputeFrom #grandFathersName").val(),
                "sex": $("#editDisputeFrom #sex").val(),
                "disputeType": $("#editDisputeFrom #disputeType").val(),
                "disputeStatus" : $("#editDisputeFrom #disputeStatus").val(),
                "registeredOn": '<%=registeredOn%>'
            },
            error: showajaxerror,
            success: loadForward
        });
    }

</script>