<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");

    String saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_USER_ADMINISTRATOR;
    String editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_USER_ADMINISTRATOR;
    String viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_USER_ADMINISTRATOR;
    String deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_USER_ADMINISTRATOR;
    String updateSettingsurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_SETTINGS_ADMINISTRATOR;
    String updatepasswordurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_PASSWORD_ADMINISTRATOR;
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-3 col-lg-offset-5 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome</h2>
        </div>
    </div>
    <div class="bs-example">
        <ul class="nav nav-tabs" style="margin-bottom: 15px;">
            <li class="active"><a href="#users" data-toggle="tab">Users</a></li>
            <li><a href="#settings" data-toggle="tab">Settings</a></li>
            <!--li class="disabled"><a>Disabled</a></li-->
        </ul>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade active in" id="users">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">MASSREG Users</div>
                            <div class="panel-body" >
                                <div>
                                    <table class="table table-striped table-bordered table-hover" id="dataTables" >
                                        <thead>
                                            <tr>
                                                <th>User Name</th>
                                                <th>Name</th>
                                                <th>Phone Number</th>
                                                <th>Role</th>
                                                <th>Fix</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                for (int i = 0; i < users.size(); i++) {
                                                    if (users.get(i).getUserId() == CommonStorage.getCurrentUser(request).getUserId()) {
                                                        continue;
                                                    }
                                                    if (i % 2 == 0) {
                                                        out.println("<tr class='odd gradeA'>");
                                                    } else {
                                                        out.println("<tr class='even gradeA'>");
                                                    }
                                                    out.println("<td>" + users.get(i).getUsername() + "</td>");
                                                    out.println("<td>" + users.get(i).getFullName() + "</td>");
                                                    out.println("<td>" + users.get(i).getPhoneNumber() + "</td>");
                                                    out.println("<td>" + users.get(i).getRole() + "</td>");
                                                    out.print("<td>");
                                                    out.print("<a href = '#' class='viewButton' "
                                                            + "data-userId='" + users.get(i).getUserId() + "'>View</a>");
                                                    out.print("|<a href = '#' class='editButton' "
                                                            + "data-userId='" + users.get(i).getUserId() + "'>Edit</a>");
                                                    out.print("|<a href = '#' class='deleteButton' "
                                                            + "data-userId='" + users.get(i).getUserId() + "'>Delete</a>");
                                                    out.print("|<a href = '#' class='changePasswordButton' "
                                                            + "data-userId='" + users.get(i).getUserId() + "'>Change Password</a>");
                                                    out.println("</td>");
                                                }
                                            %>
                                        </tbody>
                                    </table>
                                </div> <!-- /.table-responsive -->
                                <div class="row">
                                    <button type='submit' id = 'addUserButton' name = 'addUserButton' class='btn btn-default col-lg-1 ' data-toggle='modal' data-target='#addModal' style="float:left;margin-left: 1em" >Add</button>
                                </div>
                                <br/>
                            </div> <!-- /.panel-body -->
                        </div> <!-- /.panel -->
                    </div>  <!-- /.col-lg-12 -->
                </div>
            </div>
            <div class="tab-pane fade" id="settings">
                <div class="row">
                    <div class="col-lg-4 col-lg-offset-4">
                        <div class="panel panel-default" >
                            <div class="panel-heading">
                                Settings
                            </div>
                            <div class="panel-body" id="panelBody" >
                                <form role="form" action="#" method="POST" id="settingsForm" name="findParcelForm" style="padding-left: 1em;padding-right: 1em">
                                    <div class="row">
                                        <div class="form-group">
                                            <label>Woreda</label>
                                            <select class="form-control" id="woreda" name="woreda" value="<%= CommonStorage.getCurrentWoredaId()%>">
                                                <%
                                                    Option[] woredas = MasterRepository.getInstance().getAllWoredas();
                                                    for (int i = 0; i < woredas.length; i++) {
                                                        out.println("<option value = '" + woredas[i].getKey() + "'>" + woredas[i].getValue() + "</option>");
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <div class="form-group error" id="someId">
                                            <label>Language</label>
                                            <select class="form-control" id="language" name="language" value="<%= CommonStorage.getCurrentLanguage()%>">
                                                <option value = 'english' >English</option>
                                                <option value = 'amharic' >Amharic</option>
                                                <option value = 'tigrigna' >Tigrigna</option>
                                                <option value = 'oromiffa' >Oromiffa</option>
                                            </select>
                                        </div>
                                        <button type='submit' id = 'updateSettingsButton' name = 'updateSettingsButton' class='btn btn-default col-lg-3' style='float:right'>Update</button>
                                    </div> <!-- /.row (nested) -->
                                </form>
                            </div> <!-- /.panel-body -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabe" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Register a User</h4>
            </div>            <!-- /modal-header -->
            <div class="modal-body">
                <form role="form" action="#" id="addUserForm">
                    <div class="panel-body">
                        <div class="row">
                            <div class="form-group">
                                <label>First Name</label>
                                <input class="form-control " id="firstName" name="firstName" />
                            </div>
                            <div class="form-group">
                                <label>Father's Name</label>
                                <input class="form-control " id="fathersName" name="fathersName" />
                            </div> 
                            <div class="form-group">
                                <label>Grandfather's Name</label>
                                <input class="form-control " id="grandFathersName" name="grandFathersName" />
                            </div>
                            <div class="form-group">
                                <label>Phone Number</label>
                                <input class="form-control " id="phoneNo" name="phoneno" />
                            </div>
                            <div class="form-group">
                                <label>Role</label>
                                <select class="form-control" id="role" name="role">
                                    <option value = 'feo'>First Entry Operator</option>
                                    <option value = 'seo'>Second Entry Operator</option>
                                    <option value = 'supervisor'>Supervisor</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>User Name</label>
                                <input class="form-control " type="text" id="username" name="username" />
                            </div>
                            <div class="form-group">
                                <label>Password</label>
                                <input class="form-control " type="password" id="password" name="password" />
                            </div>
                            <div class="form-group">
                                <label>Confirm Password</label>
                                <input class="form-control " type="password" id="cpassword" name="cpassword" />
                            </div>
                        </div> <!-- /.row (nested) -->
                    </div> <!-- /.panel-body -->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <input type="submit" id="saveUserButton" class="btn btn-primary" value = "Add" />
            </div> 
        </div>
    </div>
</div>
<div class="modal fade" id="updatePasswordModal" tabindex="-1" aria-labelledby="updatePasswordModalLabe" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Update Password </h4>
            </div>            <!-- /modal-header -->
            <div class="modal-body">
                <form role="form" action="#" id="updatePasswordForm">
                    <input type="hidden" id="usrId" value=""/>
                    <div class="panel-body">
                        <div class="row">
                            <div class="form-group">
                                <label>New Password</label>
                                <input class="form-control " id="newPassword" name="newPassword" type="password"/>
                            </div> 
                            <div class="form-group">
                                <label>Confirm Password</label>
                                <input class="form-control " id="cnewPassword" name="cnewPassword" type="password"/>
                            </div>
                        </div> <!-- /.row (nested) -->
                    </div> <!-- /.panel-body -->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <input type="submit" id="updatePasswordButton" class="btn btn-primary" value = "Update" />
            </div> 
        </div>
    </div>
</div>

<div class="modal fade" id="viewModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>

<script type="text/javascript">
    function loadViewUser(result) {
        $("#editModal").html("").hide();
        $("#viewModal").html(result).modal();
    }
    function loadEditUser(result) {
        $("#viewModal").html("").hide();
        $("#editModal").html(result).modal();
    }
    function validate(formId) {
        var returnValue = true;
        $("#" + formId + " #username").toggleClass("error-field", false);
        $("#" + formId + " #firstName").toggleClass("error-field", false);
        $("#" + formId + " #fathersName").toggleClass("error-field", false);
        $("#" + formId + " #grandFathersName").toggleClass("error-field", false);
        $("#" + formId + " #password").toggleClass("error-field", false);
        $("#" + formId + " #cpassword").toggleClass("error-field", false);
        if ($("#" + formId + " #username").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #username").toggleClass("error-field", true);
        }
        if ($("#" + formId + " #firstName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #firstName").toggleClass("error-field", true);
        }
        if ($("#" + formId + " #fathersName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #fathersName").toggleClass("error-field", true);
        }
        if ($("#" + formId + " #grandFathersName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #grandFathersName").toggleClass("error-field", true);
        }
        if ($("#" + formId + " #password").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #password").toggleClass("error-field", true);
        }
        if ($("#" + formId + " #cpassword").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #cpassword").toggleClass("error-field", true);
        }
        if ($("#" + formId + " #cpassword").val().trim() !== $("#" + formId + " #password").val().trim()) {
            returnValue = false;
            $("#" + formId + " #cpassword").toggleClass("error-field", true);
        }
        return returnValue;
    }
    function validateUpdatePassword(){
        var returnValue = true;
        $("#updatePasswordForm #newPassword").toggleClass("error-field", false);
        $("#updatePasswordForm #cnewPassword").toggleClass("error-field", false);
        if ($("#updatePasswordForm #newPassword").val().trim() === "") {
            returnValue = false;
            $("#updatePasswordForm #newPassword").toggleClass("error-field", true);
        }
        if ($("#updatePasswordForm #cnewPassword").val().trim() === "") {
            returnValue = false;
            $("#updatePasswordForm #cnewPassword").toggleClass("error-field", true);
        }
        if ($("#updatePasswordForm #cnewPassword").val().trim() !== $("#updatePasswordForm #newPassword").val().trim()) {
            returnValue = false;
            $("#updatePasswordForm #cnewPassword").toggleClass("error-field", true);
        }
        return returnValue;
    }
    
    function closeModals() {
        $("#editModal").hide();
        $("#editModal").html("");
        $("#viewModal").hide();
        $("#viewModal").html("");
    }
    function editUser(userId) {
        $.ajax({
            type: 'POST',
            url: "<%=editurl%>",
            data: {"userId": userId},
            error: showajaxerror,
            success: loadEditUser
        });
    }
    function deleteUser(userId) {
        bootbox.confirm("Are you sure you want to delete this user ?", function(result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: "<%=deleteurl%>",
                    data: {"userId": userId},
                    error: showajaxerror,
                    success: loadForward
                });
            }
        });
    }
    function viewUser(userId) {
        $.ajax({
            type: 'POST',
            url: "<%=viewurl%>",
            data: {"userId": userId},
            error: showajaxerror,
            success: loadViewUser
        });
    }
    $("#settings select").each(function() {
        $(this).val($(this).attr("value"));
    });
    $("#updatePasswordButton").click(function() {
        if(!validateUpdatePassword()){
            showError("Please input appropriate values in the highlighted fields");
        }else {
            $.ajax({
            type: 'POST',
            url: "<%=updatepasswordurl%>",
            data: {
                "newPassword": $("#updatePasswordForm #newPassword").val(),
                "userId": $("#updatePasswordForm #usrId").val()
            },
            error: showajaxerror,
            success: loadForward
        });
        }
        return false;
    });
    $('#dataTables').dataTable();
    $('.editButton').click(function() {
        editUser($(this).attr("data-userId"));
        return false;
    });
    $('.viewButton').click(function() {
        viewUser($(this).attr("data-userId"));
        return false;
    });
    $('.changePasswordButton').click(function() {
        $("#updatePasswordForm #usrId").val($(this).attr("data-userId"));
        $("#updatePasswordModal").modal();
        return false;
    });
    $('.deleteButton').click(function() {
        deleteUser($(this).attr("data-userId"));
        return false;
    });
    $("#settings #updateSettingsButton").click(function() {
        bootbox.confirm("Are you sure you want to save the settings ?", function(result) {
            if (result) {
                $.ajax({
                    url: "<%=updateSettingsurl%>",
                    data: {
                        woreda: $("#settings #woreda").val(),
                        language: $("#settings #language").val()
                    },
                    error: showajaxerror,
                    success: loadInPlace
                });
            }
        });
        return false;
    });
    function save() {
        $.ajax({
            type: 'POST',
            url: "<%=saveurl%>",
            data: {
                "firstName": $("#addUserForm #firstName").val(),
                "fathersName": $("#addUserForm #fathersName").val(),
                "grandfathersName": $("#addUserForm #grandFathersName").val(),
                "password": $("#addUserForm #password").val(),
                "username": $("#addUserForm #username").val(),
                "phoneno": $("#addUserForm #phoneNo").val(),
                "role": $("#addUserForm #role").val()
            },
            error: showajaxerror,
            success: loadForward
        });
    }
    $("#saveUserButton").click(function() {
        if (!validate("addUserForm")) {// validate
            showError("Please input appropriate values in the highlighted fields");
        } else {
            save(); // save
            $("#addModal").hide(); // close modale
        }
        return false;
    });
</script>