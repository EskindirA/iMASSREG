<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    Timestamp registeredOn = Timestamp.valueOf(request.getParameter("registeredOn"));
    PersonWithInterest personWithInterest = currentParcel.getPersonWithInterest(currentParcel.getStage(), registeredOn);
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title"><%=CommonStorage.getText("view_person_with_interest_details")%></h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="viewPersonWithInterestFrom">
                <div class="panel-body">         
                    <div class="row">
                        <div class="form-group">
                            <label><%=CommonStorage.getText("first_name")%></label>
                            <input class="form-control " id="firstName" name="firstName" value="<%=personWithInterest.getFirstName()%>" disabled/>
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("fathers_name")%></label>
                            <input class="form-control " id="fathersName" name="fathersName" value="<%=personWithInterest.getFathersName()%>" disabled/>
                        </div> 
                        <div class="form-group">
                            <label><%=CommonStorage.getText("grandfathers_Name")%></label>
                            <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=personWithInterest.getGrandFathersName()%>" disabled/>
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("sex")%></label>
                            <select class="form-control" id="sex" name="sex" value="<%=personWithInterest.getSex()%>" disabled >
                                <option value = 'm'><%=CommonStorage.getText("male")%></option>
                                <option value = 'f'><%=CommonStorage.getText("female")%></option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("date_of_birth")%></label>
                            <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='text' value="<%=personWithInterest.getDateOfBirth()%>" disabled/>
                        </div>
                    </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>   
        <div class="modal-footer">
            <%
                if (editable) {
                    out.println("<input type='submit' id='editPersonWithInterestButton' name = 'editPersonWithInterestButton' class='btn btn-primary' value = '" + CommonStorage.getText("edit") + "' />");
                }
            %>
            <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("close")%></button>
        </div> 
    </div>
</div>
<script type="text/javascript">
    var calendar = $.calendars.instance("ethiopian", "am");
    $("#surveyDate").calendarsPicker({calendar: calendar});
    $("#viewPersonWithInterestFrom select").each(function () {
        $(this).val($(this).attr("value"));
    });
    $("#editPersonWithInterestButton").click(function () {
        editPersonWithInterest("<%=request.getParameter("registeredOn")%>");
    });
</script>