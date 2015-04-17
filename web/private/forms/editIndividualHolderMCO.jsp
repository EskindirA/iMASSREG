<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    Timestamp registeredOn = Timestamp.valueOf(request.getParameter("registeredOn"));
    IndividualHolder holder = currentParcel.getIndividualHolder(request.getParameter("holderId"), currentParcel.getStage(), registeredOn);
    String updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_INDIVIDUAL_HOLDER_MCO;
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title"><%=CommonStorage.getText("edit_holder_details")%></h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="editHolderFrom" name="editHolderFrom">
                <div class="panel-body">         
                    <div class="row">
                        <div class="form-group">
                            <label><%=CommonStorage.getText("holder_id")%></label>
                            <input class="form-control " type="text" id="holderId" name="holderId" value="<%=holder.getId()%>" />
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("first_name")%></label>
                            <input class="form-control " id="firstName" name="firstName" value="<%=holder.getFirstName()%>" />
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("fathers_name")%></label>
                            <input class="form-control " id="fathersName" name="fathersName" value="<%=holder.getFathersName()%>" />
                        </div> 
                        <div class="form-group">
                            <label><%=CommonStorage.getText("grandfathers_name")%></label>
                            <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=holder.getGrandFathersName()%>" />
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("sex")%></label>
                            <select class="form-control" id="sex" name="sex" value="<%=holder.getSex()%>" >
                                <option value = 'm'><%=CommonStorage.getText("male")%></option>
                                <option value = 'f'><%=CommonStorage.getText("female")%></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("date_of_birth")%></label>
                            <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='text' value="<%=holder.getDateOfBirth()%>"  readonly style="background: #FFF !important"/>
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("family_role")%></label>
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
                            <label><%=CommonStorage.getText("has_physical_impairment")%></label>
                            <select class="form-control" id="physicalImpairment" name="physicalImpairment" value="<%=holder.hasPhysicalImpairment()%>" >
                                <option value = 'false'><%=CommonStorage.getText("no")%></option>
                                <option value = 'true'><%=CommonStorage.getText("yes")%></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("is_orphan")%></label>
                            <select class="form-control" id="isOrphan" name="isOrphan" value="<%=holder.isOrphan()%>" >
                                <option value = 'false'><%=CommonStorage.getText("no")%></option>
                                <option value = 'true'><%=CommonStorage.getText("yes")%></option>
                            </select>
                        </div>
                    </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>   
        <div class="modal-footer">
            <input type="submit" id="updateHolderButton" name="updateHolderButton" class='btn btn-info' value = '<%=CommonStorage.getText("save")%>' />
            <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("close")%></button>
        </div> 
    </div>
</div>
<script type="text/javascript">
    var calendar = $.calendars.instance("ethiopian","am"); 
    $("#editHolderFrom #dateOfBirth").calendarsPicker({calendar: calendar});
    $("#updateHolderButton").click(function() {
        if (!validate("editHolderFrom")) {// validate
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
        } else {
            update();// save
            closeModals();
        }
        
        return false;
    });
    $("#editHolderFrom select").each(function() {
        $(this).val($(this).attr("value"));
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
                "isOrphan": $("#editHolderFrom #isOrphan").val(),
                "physicalImpairment": $("#editHolderFrom #physicalImpairment").val(),
                "registeredOn": "<%=registeredOn%>",
                "oldHolderId": "<%=holder.getId()%>"
            },
            error: showajaxerror,
            success: loadForward
        });
    }
</script>