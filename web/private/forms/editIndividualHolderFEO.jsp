<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.Option"%>
<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    Timestamp registeredOn = Timestamp.valueOf(request.getParameter("registeredOn"));
    IndividualHolder holder = currentParcel.getIndividualHolder(request.getParameter("holderId"), currentParcel.getStage(), registeredOn);
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">Edit Holder Details</h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="editHolderFrom">
                <div class="panel-body">         
                        <div class="row">
                            <div class="form-group">
                                <label>Holder Id</label>
                                <input class="form-control " type="number" id="holderId" name="holderId" value="<%=holder.getId()%>" />
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
                                <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='date' value="<%=holder.getDateOfBirth()%>" />
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
                        </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>   
        <div class="modal-footer">
            <input type='submit' id='saveButton' name = 'cancelButton' class='btn btn-primary' value = 'Save' />
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div> 
    </div>
</div>
<script type="text/javascript">
    $("#editHolderFrom select").each(function() {
        $(this).val($(this).attr("value"));
    });
</script>