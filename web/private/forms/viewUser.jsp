<%@page import="java.sql.Timestamp"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    User currentUser = (User) request.getAttribute("currentUser");
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title"><%=CommonStorage.getText("view_user_details")%></h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="viewDisputeFrom" name="viewUserFrom" >
                <div class="panel-body">
                    <div class="row">
                            <div class="form-group">
                                <label><%=CommonStorage.getText("first_name")%></label>
                                <input class="form-control " id="firstName" name="firstName" value="<%=currentUser.getFirstName()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("fathers_name")%></label>
                                <input class="form-control " id="fathersName" name="fathersName" value="<%=currentUser.getFathersName()%>" disabled/>
                            </div> 
                            <div class="form-group">
                                <label><%=CommonStorage.getText("grandfathers_name")%></label>
                                <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=currentUser.getGrandFathersName()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("phone_number")%></label>
                                <input class="form-control " id="phoneNo" name="phoneno" value="<%=currentUser.getPhoneNumber()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("role")%></label>
                                <select class="form-control" id="role" name="role" value="<%=currentUser.getRoleText().toLowerCase()%>" disabled>
                                    <option value = 'feo'><%=CommonStorage.getText("first_entry_operator")%></option>
                                    <option value = 'seo'><%=CommonStorage.getText("second_entry_operator")%></option>
                                    <option value = 'supervisor'><%=CommonStorage.getText("supervisor")%></option>
                                    <option value = 'PDC'><%=CommonStorage.getText("post_public_display_coordinator")%></option>
                                    <option value = 'MCO'><%=CommonStorage.getText("minor_corrections_officer")%></option>
                                    <option value = 'CFEO'><%=CommonStorage.getText("correction_first_entry_operator")%></option>
                                    <option value = 'CSEO'><%=CommonStorage.getText("correction_second_entry_operator")%></option>
                                    <option value = 'CSUPER'><%=CommonStorage.getText("correction_supervisor")%></option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("user_name")%></label>
                                <input class="form-control " type="text" id="username" name="username" value="<%=currentUser.getUsername()%>" disabled/>
                            </div>
                        </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>
        <div class="modal-footer">
            <input type='submit' id='editUserButton' class='btn btn-primary' value = '<%=CommonStorage.getText("edit")%>' />
            <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("close")%></button>
        </div> 
    </div>
</div>
<script type="text/javascript">
    $("#viewUserFrom select").each(function() {
        $(this).val($(this).attr("value"));
    });
    $("#editUserButton").click(function() {
        editUser("<%=currentUser.getUserId()%>"); 
    });
</script>