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
            <h4 class="modal-title"><%=CommonStorage.getText("edit_user_details")%></h4>
        </div>            <!-- /modal-header -->
        <div class="modal-body">
            <form id="editUserForm" name="editUserForm" >
                <div class="panel-body">
                    <div class="row">
                        <div class="form-group">
                            <label><%=CommonStorage.getText("first_name")%></label>
                            <input class="form-control " id="firstName" name="firstName" value="<%=currentUser.getFirstName()%>" />
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("fathers_name")%></label>
                            <input class="form-control " id="fathersName" name="fathersName" value="<%=currentUser.getFathersName()%>" />
                        </div> 
                        <div class="form-group">
                            <label><%=CommonStorage.getText("grandfathers_name")%></label>
                            <input class="form-control " id="grandFathersName" name="grandFathersName" value="<%=currentUser.getGrandFathersName()%>" />
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("phone_number")%></label>
                            <input class="form-control " id="phoneNo" name="phoneno" value="<%=currentUser.getPhoneNumber()%>" />
                        </div>
                        <div class="form-group">
                            <label><%=CommonStorage.getText("role")%></label>
                            <select class="form-control" id="role" name="role" value="<%=currentUser.getRoleText().toLowerCase()%>" >
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
                            <input class="form-control " type="text" id="username" name="username" value="<%=currentUser.getUsername()%>" />
                        </div>
                    </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </form>
        </div>
        <div class="modal-footer">
            <input type='submit' id='updateUserButton' name='updateUserButton' class='btn btn-info' value = '<%=CommonStorage.getText("save")%>' />
            <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("close")%></button>
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
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
            return false;
        }else{
            update();
        }
        return false;
    });
</script>