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
            <h4 class="modal-title">View User Details</h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="viewDisputeFrom" name="viewUserFrom" >
                <div class="panel-body">
                    <div class="row">
                            <div class="form-group">
                                <label>First Name</label>
                                <input class="form-control " id="firstName" name="firstName" value="<%=currentUser.getFirstName()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label>Father's Name</label>
                                <input class="form-control " id="fathersName" name="fathersName" value="<%=currentUser.getFathersName()%>" disabled/>
                            </div> 
                            <div class="form-group">
                                <label>Grandfather's Name</label>
                                <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=currentUser.getGrandFathersName()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label>Phone Number</label>
                                <input class="form-control " id="phoneNo" name="phoneno" value="<%=currentUser.getPhoneNumber()%>" disabled/>
                            </div>
                            <div class="form-group">
                                <label>Role</label>
                                <select class="form-control" id="role" name="role" value="<%=currentUser.getRoleText().toLowerCase()%>" disabled>
                                    <option value = 'feo'>First Entry Operator</option>
                                    <option value = 'seo'>Second Entry Operator</option>
                                    <option value = 'supervisor'>Supervisor</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>User Name</label>
                                <input class="form-control " type="text" id="username" name="username" value="<%=currentUser.getUsername()%>" disabled/>
                            </div>
                        </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>
        <div class="modal-footer">
            <input type='submit' id='editUserButton' class='btn btn-primary' value = 'Edit' />
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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