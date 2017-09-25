<%@page import="org.lift.massreg.entity.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%
    User currentUser = CommonStorage.getCurrentUser(request);
    String updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_PROFILE;
    String viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PROFILE;
%>
<div class="col-lg-5 col-lg-offset-4">
    <div class="row">
        <div class="col-lg-7 col-lg-offset-3">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp; <%=CommonStorage.getText("profile_details")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <%= currentUser.getFirstName() + " " + currentUser.getFathersName()%>' <%=CommonStorage.getText("profile")%>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form role="form"  id="editProfileFrom" name="editProfileFrom" >
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("current_password")%></label>
                                    <input class="form-control " id="oldPassword" name="oldPassword" placeholder="Old password"  type="password"/>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("new_password")%>: </label>
                                    <input class="form-control " id="password" name="password" type="password" />
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("confirm_password")%>: </label>
                                    <input class="form-control " id="cPassword" name="cPassword" type="password" />
                                </div>
                                <div class="col-lg-12">
                                    <button type='submit' id = 'cancelEditButton' name = 'cancelEditButton' class='btn btn-default col-lg-2 col-lg-offset-7' ><%=CommonStorage.getText("cancel")%></button>
                                    <button type='submit' id = 'updatePasswordButton' name = 'updatePasswordButton' class='btn btn-default col-lg-2' style='margin-left: .2em' ><%=CommonStorage.getText("update")%></button>
                                </div>
                                <!-- /.row (nested) -->
                            </form>
                        </div>
                    </div>
                </div>    <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
</div>
<script type="text/javascript">
    function validatePassword() {
        var returnValue = true;
        $("#oldPassword").toggleClass("error-field", false);
        $("#password").toggleClass("error-field", false);
        $("#cPassword").toggleClass("error-field", false);
        if ($("#oldPassword").val().trim() === "") {
            returnValue = false;
            $("#oldPassword").toggleClass("error-field", true);
        }
        if ($("#password").val().trim() === "") {
            returnValue = false;
            $("#password").toggleClass("error-field", true);
        }
        if ($("#cPassword").val().trim() === "") {
            $("#cPassword").toggleClass("error-field", true);
            returnValue = false;
        }
        if ($("#cPassword").val().trim() !== $("#password").val().trim()) {
            returnValue = false;
            $("#cPassword").toggleClass("error-field", true);
        }
        
        return returnValue;
    }
    function updatePassword() {
        $.ajax({
            type: 'POST',
            url: "<%=updateurl%>",
            data: {
                oldPassword: $("#oldPassword").val(),
                password: $("#password").val()
            },
            error: showajaxerror,
            success: loadInPlace
        });
        return false;
    }
    $("#editProfileFrom select").each(function() {
        $(this).val($(this).attr("value"));
    });
    $("#cancelEditButton").click(function() {
        $.ajax({
            type: 'POST',
            url: "<%=viewurl%>",
            error: showajaxerror,
            success: loadInPlace
        });
        return false;
    });
    $("#updatePasswordButton").click(function() {
        if (!validatePassword()) {// validate
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
            return false;
        } else {
            updatePassword();// save
        }
        return false;
    });
</script>