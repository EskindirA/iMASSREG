<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    Timestamp registeredOn = Timestamp.valueOf(request.getParameter("registeredOn"));
    PersonWithInterest personWithInterest = currentParcel.getPersonWithInterest(currentParcel.getStage(), registeredOn);
    
    String updateurl;
    if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
        updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_PERSON_WITH_INTEREST_FEO;
    } else {
        updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_PERSON_WITH_INTEREST_SEO;
    }
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title"><%=CommonStorage.getText("edit_person_with_interest_details")%></h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="editPersonWithInterestFrom" name="editPersonWithInterestFrom">
                <div class="panel-body">         
                    <div class="row">
                        <div class="form-group">
                            <label><%=CommonStorage.getText("first_name")%></label>
                            <input class="form-control " id="firstName" name="firstName" value="<%=personWithInterest.getFirstName()%>" />
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("fathers_name")%></label>
                            <input class="form-control " id="fathersName" name="fathersName" value="<%=personWithInterest.getFathersName()%>" />
                        </div> 
                        <div class="form-group">
                            <label><%=CommonStorage.getText("grandfathers_name")%></label>
                            <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=personWithInterest.getGrandFathersName()%>" />
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("sex")%></label>
                            <select class="form-control" id="sex" name="sex" value="<%=personWithInterest.getSex()%>" >
                                <option value = 'm'><%=CommonStorage.getText("male")%></option>
                                <option value = 'f'><%=CommonStorage.getText("female")%></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("date_of_birth")%></label>
                            <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='text' value="<%=personWithInterest.getDateOfBirth()%>" readonly style="background: #FFF !important"/>
                        </div>
                    </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>   
        <div class="modal-footer">
            <input type="submit" id="updatePersonWithInterestButton" name="updatePersonWithInterestButton" class='btn btn-info' value = '<%=CommonStorage.getText("save")%>' />
            <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("close")%></button>
        </div> 
    </div>
</div>
<script type="text/javascript">
    var calendarEdit = $.calendars.instance("ethiopian","am"); 
    $("#editPersonWithInterestFrom #dateOfBirth").calendarsPicker({calendar: calendarEdit});
    $("#updatePersonWithInterestButton").click(function() {
        if (!validate("editPersonWithInterestFrom")) {// validate
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
        } else {
            update();// save
            closeModals();
        }
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
                "registeredOn": "<%=registeredOn%>"
            },
            error: showajaxerror,
            success: loadForward
        });
    }
</script>