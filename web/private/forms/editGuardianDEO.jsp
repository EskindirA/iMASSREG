<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    Timestamp registeredOn = Timestamp.valueOf(request.getParameter("registeredOn"));
    Guardian guardian = currentParcel.getGuardian(currentParcel.getStage(), registeredOn);
    
    String updateurl;
    if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
        updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_GUARDIAN_FEO;
    } else {
        updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_GUARDIAN_SEO;
    }
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">Edit Guardian Details</h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="editGuardianFrom" name="editGuardianFrom">
                <div class="panel-body">         
                    <div class="row">
                        <div class="form-group">
                            <label>First Name</label>
                            <input class="form-control " id="firstName" name="firstName" value="<%=guardian.getFirstName()%>" />
                        </div>
                        <div class="form-group">
                            <label>Father's Name</label>
                            <input class="form-control " id="fathersName" name="fathersName" value="<%=guardian.getFathersName()%>" />
                        </div> 
                        <div class="form-group">
                            <label>Grandfather's Name</label>
                            <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=guardian.getGrandFathersName()%>" />
                        </div>
                        <div class="form-group">
                            <label>Sex</label>
                            <select class="form-control" id="sex" name="sex" value="<%=guardian.getSex()%>" >
                                <option value = 'm'>Male</option>
                                <option value = 'f'>Female</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Date of Birth</label>
                            <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='text' value="<%=guardian.getDateOfBirth()%>" readonly style="background: #FFF !important"/>
                        </div>
                    </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>   
        <div class="modal-footer">
            <input type="submit" id="updateGuardianButton" name="updateGuardianButton" class='btn btn-info' value = 'Save' />
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div> 
    </div>
</div>
<script type="text/javascript">
    var calendarEdit = $.calendars.instance("ethiopian","am"); 
    $("#editGuardianFrom #dateOfBirth").calendarsPicker({calendar: calendarEdit});
    $("#updateGuardianButton").click(function() {
        if (!validate("editGuardianFrom")) {// validate
            showError("Please input appropriate values in the highlighted fields");
        } else {
            update();// save
            closeModals();
        }
        return false;
    });
    $("#editGuardianFrom select").each(function() {
        $(this).val($(this).attr("value"));
    });
    function update() {
        $.ajax({
            post:'POST',
            url: "<%=updateurl%>",
            data: {
                "dateofbirth": $("#editGuardianFrom #dateOfBirth").val(),
                "firstname": $("#editGuardianFrom #firstName").val(),
                "fathersname": $("#editGuardianFrom #fathersName").val(),
                "grandfathersname": $("#editGuardianFrom #grandFathersName").val(),
                "sex": $("#editGuardianFrom #sex").val(),
                "registeredOn": "<%=registeredOn%>"
            },
            error: showajaxerror,
            success: loadForward
        });
    }
</script>