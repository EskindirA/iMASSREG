<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    Timestamp registeredOn = Timestamp.valueOf(request.getParameter("registeredOn"));
    Guardian guardians = currentParcel.getGuardian(currentParcel.getStage(), registeredOn);
    
    String updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_GUARDIAN_MCO;
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title"><%=CommonStorage.getText("edit_guardian_details")%></h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="editGuardianFrom" name="editGuardianFrom">
                <div class="panel-body">         
                    <div class="row">
                        <div class="form-group">
                            <label><%=CommonStorage.getText("first_name")%></label>
                            <input class="form-control " id="firstName" name="firstName" value="<%=guardians.getFirstName()%>" />
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("fathers_name")%></label>
                            <input class="form-control " id="fathersName" name="fathersName" value="<%=guardians.getFathersName()%>" />
                        </div> 
                        <div class="form-group">
                            <label><%=CommonStorage.getText("grandfathers_name")%></label>
                            <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=guardians.getGrandFathersName()%>" />
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("sex")%></label>
                            <select class="form-control" id="sex" name="sex" value="<%=guardians.getSex()%>" >
                                <option value = 'm'><%=CommonStorage.getText("male")%></option>
                                <option value = 'f'><%=CommonStorage.getText("female")%></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("date_of_birth")%></label>
                            <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='text' value="<%=guardians.getDateOfBirth()%>" readonly style="background: #FFF !important"/>
                        </div>
                    </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>   
        <div class="modal-footer">
            <input type="submit" id="updateGuardianButton" name="updateGuardianButton" class='btn btn-info' value = '<%=CommonStorage.getText("save")%>' />
            <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("close")%></button>
        </div> 
    </div>
</div>
<script type="text/javascript">
    var calendarEdit = $.calendars.instance("ethiopian","am"); 
    $("#editGuardianFrom #dateOfBirth").calendarsPicker({calendar: calendarEdit});
    $("#updateGuardianButton").click(function() {
        if (!validate("editGuardianFrom")) {// validate
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
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
            type:'POST',
            url: "<%=updateurl%>",
            data: {
                "upi":'<%=request.getParameter("upi")%>',
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