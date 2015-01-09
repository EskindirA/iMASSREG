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
            <h4 class="modal-title">View Holder Details</h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="viewHolderFrom">
                <div class="panel-body">         
                        <div class="row">
                            <div class="form-group">
                                <label>Holder Id</label>
                                <input class="form-control " type="text" id="holderId" name="holderId" value="<%=holder.getId()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label>First Name</label>
                                <input class="form-control " id="firstName" name="firstName" value="<%=holder.getFirstName()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label>Father's Name</label>
                                <input class="form-control " id="fathersName" name="fathersName" value="<%=holder.getFathersName()%>" disabled/>
                            </div> 
                            <div class="form-group">
                                <label>Grandfather's Name</label>
                                <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=holder.getGrandFathersName()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label>Sex</label>
                                <select class="form-control" id="sex" name="sex" value="<%=holder.getSex()%>" disabled >
                                    <option value = 'm'>Male</option>
                                    <option value = 'f'>Female</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Date of Birth</label>
                                <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='text' value="<%=holder.getDateOfBirth()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label>Family Role</label>
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
                                <label>Has Physical Impairment</label>
                                <select class="form-control" id="physicalImpairment" name="physicalImpairment"  value="<%=holder.hasPhysicalImpairment()%>" disabled >
                                    <option value = 'false'>No</option>
                                    <option value = 'true'>Yes</option>
                                </select>
                            </div>
                        </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>   
        <div class="modal-footer">
            <%
                if (editable) {
                    out.println("<input type='submit' id='editHolderButton' name = 'editHolderButton' class='btn btn-primary' value = 'Edit' />");
                }
            %>
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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