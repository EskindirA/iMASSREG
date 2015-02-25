<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    Timestamp registeredOn = Timestamp.valueOf(request.getParameter("registeredOn"));
    Guardian guardian = currentParcel.getGuardian(currentParcel.getStage(), registeredOn);
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">View Guardian Details</h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="viewGuardianFrom">
                <div class="panel-body">         
                        <div class="row">
                            <div class="form-group">
                                <label>First Name</label>
                                <input class="form-control " id="firstName" name="firstName" value="<%=guardian.getFirstName()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label>Father's Name</label>
                                <input class="form-control " id="fathersName" name="fathersName" value="<%=guardian.getFathersName()%>" disabled/>
                            </div> 
                            <div class="form-group">
                                <label>Grandfather's Name</label>
                                <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=guardian.getGrandFathersName()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label>Sex</label>
                                <select class="form-control" id="sex" name="sex" value="<%=guardian.getSex()%>" disabled >
                                    <option value = 'm'>Male</option>
                                    <option value = 'f'>Female</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Date of Birth</label>
                                <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='text' value="<%=guardian.getDateOfBirth()%>" disabled/>
                            </div>
                        </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>   
        <div class="modal-footer">
            <%
                if (editable) {
                    out.println("<input type='submit' id='editGuardianButton' name = 'editGuardianButton' class='btn btn-primary' value = 'Edit' />");
                }
            %>
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div> 
    </div>
</div>
<script type="text/javascript">
    var calendar = $.calendars.instance("ethiopian","am"); 
    $("#surveyDate").calendarsPicker({calendar: calendar});
    $("#viewGuardianFrom select").each(function() {
        $(this).val($(this).attr("value"));
    });
    $("#editGuardianButton").click(function() {
        editGuardian("<%=request.getParameter("registeredOn")%>"); 
    });
</script>