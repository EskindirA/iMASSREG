<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    Timestamp registeredOn = Timestamp.valueOf(request.getParameter("registeredOn"));
    Dispute dispute = currentParcel.getDispute(currentParcel.getStage(), registeredOn);
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">View Dispute Details</h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="viewDisputeFrom" name="viewDisputeFrom" >
                <div class="panel-body">
                    <div class="row">
                        <div class="form-group">
                            <label>First Name</label>
                            <input class="form-control " id="firstName" name="firstName" value="<%=dispute.getFirstName()%>" disabled/>
                        </div>
                        <div class="form-group">
                            <label>Father's Name</label>
                            <input class="form-control " id="fathersName" name="fathersName" value="<%=dispute.getFathersName()%>" disabled/>
                        </div> 
                        <div class="form-group">
                            <label>Grandfather's Name</label>
                            <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=dispute.getGrandFathersName()%>" disabled/>
                        </div>
                        <div class="form-group">
                            <label>Sex</label>
                            <select class="form-control" id="sex" name="sex" value="<%=dispute.getSex()%>" disabled>
                                <option value = 'm'>Male</option>
                                <option value = 'f'>Female</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Dispute Type</label>
                            <select class="form-control" id="disputeType" name="disputeType" value="<%=dispute.getDisputeType()%>" disabled>
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
                            <select class="form-control" id="disputeStatus" name="disputeStatus" value="<%=dispute.getDisputeStatus()%>" disabled>
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
            <%
                if (editable) {
                    out.println("<input type='submit' id='editDisputeButton' class='btn btn-primary' value = 'Edit' />");
                }
            %>
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div> 
    </div>
</div>
<script type="text/javascript">
    $("#viewDisputeFrom select").each(function() {
        $(this).val($(this).attr("value"));
    });
    $("#editDisputeButton").click(function() {
        editDispute("<%=request.getParameter("registeredOn")%>"); 
    });
</script>