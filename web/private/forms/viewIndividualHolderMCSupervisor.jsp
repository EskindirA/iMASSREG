<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    Timestamp registeredOn = Timestamp.valueOf(request.getParameter("registeredOn"));
    IndividualHolder holder = currentParcel.getIndividualHolder(request.getParameter("holderId"), currentParcel.getStage(), registeredOn);
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title"><%=CommonStorage.getText("view_holder_details")%></h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="viewHolderFrom">
                <div class="panel-body">         
                        <div class="row">
                            <div class="form-group">
                                <label><%=CommonStorage.getText("holder_id")%></label>
                                <input class="form-control " type="text" id="holderId" name="holderId" value="<%=holder.getId()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("first_name")%></label>
                                <input class="form-control " id="firstName" name="firstName" value="<%=holder.getFirstName()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("fathers_name")%></label>
                                <input class="form-control " id="fathersName" name="fathersName" value="<%=holder.getFathersName()%>" disabled/>
                            </div> 
                            <div class="form-group">
                                <label><%=CommonStorage.getText("grandfathers_name")%></label>
                                <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=holder.getGrandFathersName()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("sex")%></label>
                                <select class="form-control" id="sex" name="sex" value="<%=holder.getSex()%>" disabled >
                                    <option value = 'm'><%=CommonStorage.getText("male")%></option>
                                    <option value = 'f'><%=CommonStorage.getText("female")%></option>
                                    <option value = 'n'><%=CommonStorage.getText("not_available")%></option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("date_of_birth")%></label>
                                <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='text' value="<%=holder.getDateOfBirth()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("family_role")%></label>
                                <select class="form-control" id="familyRole" name="familyRole" value="<%=holder.getFamilyRole()%>" disabled >
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
                                <select class="form-control" id="physicalImpairment" name="physicalImpairment"  value="<%=holder.hasPhysicalImpairment()%>" disabled >
                                    <option value = 'false'><%=CommonStorage.getText("no")%></option>
                                    <option value = 'true'><%=CommonStorage.getText("yes")%></option>
                                </select>
                            </div>
                                    <div class="form-group">
                            <label><%=CommonStorage.getText("is_orphan")%></label>
                                <select class="form-control" id="isOrphan" name="isOrphan" value="<%=holder.isOrphan()%>" disabled >
                                    <option value = 'false'><%=CommonStorage.getText("no")%></option>
                                    <option value = 'true'><%=CommonStorage.getText("yes")%></option>
                                </select>
                            </div>
                        </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>   
        <div class="modal-footer">
            <%
                if (editable) {
                    out.println("<input type='submit' id='editHolderButton' name = 'editHolderButton' class='btn btn-primary' value = '"+CommonStorage.getText("edit")+"' />");
                }
            %>
            <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("close")%></button>
        </div> 
    </div>
</div>
<script type="text/javascript">
    var calendar = $.calendars.instance("ethiopian","am"); 
    $("#dateOfBirth").calendarsPicker({calendar: calendar});
    $("#viewHolderFrom select").each(function() {
        $(this).val($(this).attr("value"));
    });
    $("#editHolderButton").click(function() {
        editHolder("<%=request.getParameter("holderId")%>","<%=request.getParameter("registeredOn")%>"); 
    });
</script>