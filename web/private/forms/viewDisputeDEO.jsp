<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.Option"%>
<%@page import="org.lift.massreg.util.CommonStorage"%>
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
            <h4 class="modal-title"><%=CommonStorage.getText("view_dispute_details")%></h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="viewDisputeFrom" name="viewDisputeFrom" >
                <div class="panel-body">
                    <div class="row">
                        <div class="form-group">
                            <label><%=CommonStorage.getText("firstName")%></label>
                            <input class="form-control " id="firstName" name="firstName" value="<%=dispute.getFirstName()%>" disabled/>
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("fathers_name")%></label>
                            <input class="form-control " id="fathersName" name="fathersName" value="<%=dispute.getFathersName()%>" disabled/>
                        </div> 
                        <div class="form-group">
                            <label><%=CommonStorage.getText("grandfathers_name")%></label>
                            <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=dispute.getGrandFathersName()%>" disabled/>
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("sex")%></label>
                            <select class="form-control" id="sex" name="sex" value="<%=dispute.getSex()%>" disabled>
                                <option value = 'm'><%=CommonStorage.getText("male")%></option>
                                <option value = 'f'><%=CommonStorage.getText("female")%></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("dispute_type")%></label>
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
                            <label><%=CommonStorage.getText("dispute_status")%></label>
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
                    out.println("<input type='submit' id='editDisputeButton' class='btn btn-primary' value = '"+CommonStorage.getText("edit")+"' />");
                }
            %>
            <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("close")%></button>
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