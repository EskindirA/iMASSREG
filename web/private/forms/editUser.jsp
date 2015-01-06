<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    User currentUser = (User) request.getAttribute("currentUser");
    String updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_USER_ADMINISTRATOR;
%>
<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">Edit User Details</h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="editUserForm" name="editUserForm" >
                <div class="panel-body">
                    <div class="row">
                        <div class="form-group">
                            <label>First Name</label>
                            <input class="form-control " id="firstName" name="firstName" value="<%=currentUser.getFirstName()%>" />
                        </div>
                        <div class="form-group">
                            <label>Father's Name</label>
                            <input class="form-control " id="fathersName" name="fathersName" value="<%=currentUser.getFathersName()%>" />
                        </div> 
                        <div class="form-group">
                            <label>Grandfather's Name</label>
                            <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=currentUser.getGrandFathersName()%>" />
                        </div>
                        <div class="form-group">
                            <label>Phone Number</label>
                            <input class="form-control " id="phoneNo" name="phoneno" value="<%=currentUser.getPhoneNumber()%>" />
                        </div>
                        <div class="form-group">
                            <label>Role</label>
                            <select class="form-control" id="role" name="role" value="<%=currentUser.getRoleText().toLowerCase()%>" >
                                <option value = 'feo'>First Entry Operator</option>
                                <option value = 'seo'>Second Entry Operator</option>
                                <option value = 'supervisor'>Supervisor</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>User Name</label>
                            <input class="form-control " type="text" id="username" name="username" value="<%=currentUser.getUsername()%>" />
                        </div>
                    </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>
        <div class="modal-footer">
            <input type='submit' id='updateUserButton' name='updateUserButton' class='btn btn-info' value = 'Save' />
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div> 
    </div>
</div>
<script type="text/javascript">

    function validateUpdate() {
        var returnValue = true;
        $("#editUserFrom #username").toggleClass("error-field", false);
        $("#editUserForm #firstName").toggleClass("error-field", false);
        $("#editUserForm #fathersName").toggleClass("error-field", false);
        $("#editUserForm #grandFathersName").toggleClass("error-field", false);
        if ($("#editUserForm #username").val().trim() === "") {
            returnValue = false;
            $("#editUserForm #username").toggleClass("error-field", true);
        }
        if ($("#editUserForm #firstName").val().trim() === "") {
            returnValue = false;
            $("#editUserForm #firstName").toggleClass("error-field", true);
        }
        if ($("#editUserForm #fathersName").val().trim() === "") {
            returnValue = false;
            $("#editUserForm #fathersName").toggleClass("error-field", true);
        }
        if ($("#editUserForm #grandFathersName").val().trim() === "") {
            returnValue = false;
            $("#editUserForm #grandFathersName").toggleClass("error-field", true);
        }
        return returnValue;
    }

    $("#editUserFrom select").each(function() {
        $(this).val($(this).attr("value"));
    });
    function update() {
        $.ajax({
            url: "<%=updateurl%>",
            data: {
                "firstName": $("#editUserForm #firstName").val(),
                "fathersName": $("#editUserForm #fathersName").val(),
                "grandfathersName": $("#editUserForm #grandFathersName").val(),
                "username": $("#editUserForm #username").val(),
                "phoneno": $("#editUserForm #phoneNo").val(),
                "role": $("#editUserForm #role").val(),
                "userId": '<%=currentUser.getUserId()%>'
            },
            error: showajaxerror,
            success: loadForward
        });
    }
    $("#updateUserButton").click(function() {
        if (!validateUpdate()) {// validate
            showError("Please input appropriate values in the highlighted fields");
            return false;
        }else{
            update();
        }
        return false;
    });
    

</script>