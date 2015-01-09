<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    Timestamp registeredOn = Timestamp.valueOf(request.getParameter("registeredOn"));
    IndividualHolder holder = currentParcel.getIndividualHolder(request.getParameter("holderId"), currentParcel.getStage(), registeredOn);
    String updateurl;
    if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
        updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_INDIVIDUAL_HOLDER_FEO;
    } else {
        updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_INDIVIDUAL_HOLDER_SEO;
    }
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">Edit Holder Details</h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="editHolderFrom" name="editHolderFrom">
                <div class="panel-body">         
                    <div class="row">
                        <div class="form-group">
                            <label>Holder Id</label>
                            <input class="form-control " type="text" id="holderId" name="holderId" value="<%=holder.getId()%>" />
                        </div>
                        <div class="form-group">
                            <label>First Name</label>
                            <input class="form-control " id="firstName" name="firstName" value="<%=holder.getFirstName()%>" />
                        </div>
                        <div class="form-group">
                            <label>Father's Name</label>
                            <input class="form-control " id="fathersName" name="fathersName" value="<%=holder.getFathersName()%>" />
                        </div> 
                        <div class="form-group">
                            <label>Grandfather's Name</label>
                            <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=holder.getGrandFathersName()%>" />
                        </div>
                        <div class="form-group">
                            <label>Sex</label>
                            <select class="form-control" id="sex" name="sex" value="<%=holder.getSex()%>" >
                                <option value = 'm'>Male</option>
                                <option value = 'f'>Female</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Date of Birth</label>
                            <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='text' value="<%=holder.getDateOfBirth()%>" />
                        </div>
                        <div class="form-group">
                            <label>Family Role</label>
                            <select class="form-control" id="familyRole" name="familyRole" value="<%=holder.getFamilyRole()%>" >
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
                            <select class="form-control" id="physicalImpairment" name="physicalImpairment" value="<%=holder.hasPhysicalImpairment()%>" >
                                <option value = 'false'>No</option>
                                <option value = 'true'>Yes</option>
                            </select>
                        </div>
               
                    </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>   
        <div class="modal-footer">
            <input type="submit" id="updateHolderButton" name="updateHolderButton" class='btn btn-info' value = 'Save' />
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div> 
    </div>
</div>
<script type="text/javascript">
    var calendarview = $.calendars.instance("ethiopian","am"); 
    $("#editHolderFrom #dateOfBirth").calendarsPicker({calendar: calendarview});
    $("#updateHolderButton").click(function() {
        if (!validate("editHolderFrom")) {// validate
            showError("Please input appropriate values in the highlighted fields");
        } else {
            update();// save
        }
        closeModals();
        return false;
    });
    $("#editHolderFrom select").each(function() {
        $(this).val($(this).attr("value"));
    });
    $("#editHolderButton").click(function() {
        editDispute("<%=request.getParameter("holderId")%>", "<%=request.getParameter("registeredOn")%>");
    });
    function update() {
        $.ajax({
            type:'POST',
            url: "<%=updateurl%>",
            data: {
                "dateofbirth": $("#editHolderFrom #dateOfBirth").val(),
                "familyrole": $("#editHolderFrom #familyRole").val(),
                "firstname": $("#editHolderFrom #firstName").val(),
                "fathersname": $("#editHolderFrom #fathersName").val(),
                "grandfathersname": $("#editHolderFrom #grandFathersName").val(),
                "newHolderId": $("#editHolderFrom #holderId").val(),
                "sex": $("#editHolderFrom #sex").val(),
                "physicalImpairment": $("#editHolderFrom #physicalImpairment").val(),
                "registeredOn": "<%=registeredOn%>",
                "oldHolderId": "<%=holder.getId()%>"
            },
            error: showajaxerror,
            success: loadForward
        });
    }
</script>