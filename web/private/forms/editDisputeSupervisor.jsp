<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    Timestamp registeredOn = Timestamp.valueOf(request.getParameter("registeredOn"));
    Dispute dispute = currentParcel.getDispute(currentParcel.getStage(), registeredOn);
    String updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_DISPUTE_SUPERVISOR;
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title"><%=CommonStorage.getText("edit_dispute_details")%></h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="editDisputeFrom" name="editDisputeFrom" >
                <div class="panel-body">
                    <div class="row">
                        <div class="form-group">
                            <label><%=CommonStorage.getText("first_name")%></label>
                            <input class="form-control " id="firstName" name="firstName" value="<%=dispute.getFirstName()%>" />
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("fathers_name")%></label>
                            <input class="form-control " id="fathersName" name="fathersName" value="<%=dispute.getFathersName()%>" />
                        </div> 
                        <div class="form-group">
                            <label><%=CommonStorage.getText("grandfathers_name")%></label>
                            <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=dispute.getGrandFathersName()%>" />
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("sex")%></label>
                            <select class="form-control" id="sex" name="sex" value="<%=dispute.getSex()%>" >
                                <option value = 'm'><%=CommonStorage.getText("male")%></option>
                                <option value = 'f'><%=CommonStorage.getText("female")%></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("dispute_type")%></label>
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
                            <label><%=CommonStorage.getText("dispute_status")%></label>
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
            <input type='submit' id='updateDisputeButton' name='saveDisputeButton' class='btn btn-info' value = '<%=CommonStorage.getText("save")%>' />
            <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("close")%></button>
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
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
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