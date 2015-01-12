<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    Timestamp registeredOn = Timestamp.valueOf(request.getParameter("registeredOn"));
    PersonWithInterest personWithInterest = currentParcel.getPersonWithInterest(currentParcel.getStage(), registeredOn);
    
    String updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_PERSON_WITH_INTEREST_SUPERVISOR;
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">Edit Person With Interest Details</h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="editPersonWithInterestFrom" name="editPersonWithInterestFrom">
                <div class="panel-body">         
                    <div class="row">
                        <div class="form-group">
                            <label>First Name</label>
                            <input class="form-control " id="firstName" name="firstName" value="<%=personWithInterest.getFirstName()%>" />
                        </div>
                        <div class="form-group">
                            <label>Father's Name</label>
                            <input class="form-control " id="fathersName" name="fathersName" value="<%=personWithInterest.getFathersName()%>" />
                        </div> 
                        <div class="form-group">
                            <label>Grandfather's Name</label>
                            <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=personWithInterest.getGrandFathersName()%>" />
                        </div>
                        <div class="form-group">
                            <label>Sex</label>
                            <select class="form-control" id="sex" name="sex" value="<%=personWithInterest.getSex()%>" >
                                <option value = 'm'>Male</option>
                                <option value = 'f'>Female</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Date of Birth</label>
                            <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='text' value="<%=personWithInterest.getDateOfBirth()%>" />
                        </div>
                        <div class="form-group">
                            <label>Has Physical Impairment</label>
                            <select class="form-control" id="physicalImpairment" name="physicalImpairment" value="<%=personWithInterest.hasPhysicalImpairment()%>" >
                                <option value = 'false'>No</option>
                                <option value = 'true'>Yes</option>
                            </select>
                        </div>
                    </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>   
        <div class="modal-footer">
            <input type="submit" id="updatePersonWithInterestButton" name="updatePersonWithInterestButton" class='btn btn-info' value = 'Save' />
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div> 
    </div>
</div>
<script type="text/javascript">
    var calendarEdit = $.calendars.instance("ethiopian","am"); 
    $("#editPersonWithInterestFrom #dateOfBirth").calendarsPicker({calendar: calendarEdit});
    $("#updatePersonWithInterestButton").click(function() {
        if (!validate("editPersonWithInterestFrom")) {// validate
            showError("Please input appropriate values in the highlighted fields");
        } else {
            update();// save
        }
        closeModals();
        return false;
    });
    $("#editPersonWithInterestFrom select").each(function() {
        $(this).val($(this).attr("value"));
    });
    function update() {
        $.ajax({
            post:'POST',
            url: "<%=updateurl%>",
            data: {
                "dateofbirth": $("#editPersonWithInterestFrom #dateOfBirth").val(),
                "firstname": $("#editPersonWithInterestFrom #firstName").val(),
                "fathersname": $("#editPersonWithInterestFrom #fathersName").val(),
                "grandfathersname": $("#editPersonWithInterestFrom #grandFathersName").val(),
                "sex": $("#editPersonWithInterestFrom #sex").val(),
                "physicalImpairment": $("#editPersonWithInterestFrom #physicalImpairment").val(),
                "registeredOn": "<%=registeredOn%>"
            },
            error: showajaxerror,
            success: loadForward
        });
    }
</script>