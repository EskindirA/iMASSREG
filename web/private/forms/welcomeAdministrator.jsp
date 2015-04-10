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
    String periodicalReporturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PERIODICAL_REPORT_ADMINISTRATOR;
    String kebeleReporturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_KEBELE_REPORT_ADMINISTRATOR;
    String publicDisplayurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PUBLIC_DISPLAY;
    
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-3 col-lg-offset-5 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CommonStorage.getText("welcome")%></h2>
        </div>
    </div>
    <div class="bs-example">
        <ul class="nav nav-tabs" style="margin-bottom: 15px;">
            <li class="active"><a href="#users" data-toggle="tab"><%=CommonStorage.getText("users")%></a></li>
            <li><a href="#settings" data-toggle="tab"><%=CommonStorage.getText("settings")%></a></li>
            <li><a href="#publicDisplay" data-toggle="tab"><%=CommonStorage.getText("public_display")%></a></li>
            <li><a href="#report" data-toggle="tab" id="reportLink"><%=CommonStorage.getText("report")%></a></li>
        </ul>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade active in" id="users">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading"><%=CommonStorage.getText("imassreg_users")%></div>
                            <div class="panel-body" >
                                <div>
                                    <table class="table table-striped table-bordered table-hover" id="dataTables" >
                                        <thead>
                                            <tr>
                                                <th><%=CommonStorage.getText("user_name")%></th>
                                                <th><%=CommonStorage.getText("name")%></th>
                                                <th><%=CommonStorage.getText("phone_number")%></th>
                                                <th><%=CommonStorage.getText("role")%></th>
                                                <th><%=CommonStorage.getText("fix")%></th>
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
                                                            + "data-userId='" + users.get(i).getUserId() + "'>" + CommonStorage.getText("view") + "</a>");
                                                    out.print("|<a href = '#' class='editButton' "
                                                            + "data-userId='" + users.get(i).getUserId() + "'>" + CommonStorage.getText("edit") + "</a>");
                                                    out.print("|<a href = '#' class='deleteButton' "
                                                            + "data-userId='" + users.get(i).getUserId() + "'>" + CommonStorage.getText("delete") + "</a>");
                                                    out.print("|<a href = '#' class='changePasswordButton' "
                                                            + "data-userId='" + users.get(i).getUserId() + "'>" + CommonStorage.getText("change_password") + "</a>");
                                                    out.println("</td>");
                                                }
                                            %>
                                        </tbody>
                                    </table>
                                </div> <!-- /.table-responsive -->
                                <div class="row">
                                    <button type='submit' id = 'addUserButton' name = 'addUserButton' class='btn btn-default col-lg-1 ' data-toggle='modal' data-target='#addModal' style="float:left;margin-left: 1em" ><%=CommonStorage.getText("add")%></button>
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
                                <%=CommonStorage.getText("settings")%>
                            </div>
                            <div class="panel-body" id="panelBody" >
                                <form role="form" action="#" method="POST" id="settingsForm" name="findParcelForm" style="padding-left: 1em;padding-right: 1em">
                                    <div class="row">
                                        <div class="form-group">
                                            <label><%=CommonStorage.getText("woreda")%></label>
                                            <select class="form-control" id="woreda" name="woreda" value="<%= CommonStorage.getCurrentWoredaId()%>">
                                                <%
                                                    Option[] regions = MasterRepository.getInstance().getAllRegions();
                                                    for (int i = 0; i < regions.length; i++) {
                                                        out.println("<optgroup label='" + regions[i].getValue() + "'>");
                                                        Option[] woredas = MasterRepository.getInstance().getAllWoredas(Integer.parseInt(regions[i].getKey()));
                                                        for (int j = 0; j < woredas.length; j++) {
                                                            out.println("<option value = '" + woredas[j].getKey() + "'>" + woredas[j].getValue() + "</option>");
                                                        }
                                                        out.println("</optgroup>");
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <div class="form-group error" id="someId">
                                            <label><%=CommonStorage.getText("language")%></label>
                                            <select class="form-control" id="language" name="language" value="<%= CommonStorage.getCurrentLanguage()%>">
                                                <option value = 'english' >English</option>
                                                <option value = 'amharic' >Amharic</option>
                                                <option value = 'tigrigna' >Tigrigna</option>
                                                <option value = 'oromiffa' >Oromiffa</option>
                                            </select>
                                        </div>
                                        <button type='submit' id = 'updateSettingsButton' name = 'updateSettingsButton' class='btn btn-default col-lg-3' style='float:right'><%=CommonStorage.getText("update")%></button>
                                    </div> <!-- /.row (nested) -->
                                </form>
                            </div> <!-- /.panel-body -->
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade" id="report">
                <div class="row">
                    <div class="col-lg-5 col-lg-offset-1">
                        <div class="panel panel-default" >
                            <div class="panel-heading">
                                <%=CommonStorage.getText("timebound_report")%>
                            </div>
                            <div class="panel-body" id="panelBody" >
                                <form role="form" action="#" method="POST" id="periodicalReportForm" name="periodicalReportForm" style="padding-left: 1em;padding-right: 1em">
                                    <div class="row">
                                        <div class="form-group col-lg-6">
                                            <label><%=CommonStorage.getText("from")%></label>
                                            <input type="date" class="form-control" id="fromDate" name="fromDate" value="<%=CommonStorage.getLastReportDate()%>"/>
                                        </div>
                                        <div class="form-group col-lg-6">
                                            <label><%=CommonStorage.getText("to")%></label>
                                            <input type="date" class="form-control" id="toDate" name="toDate" value="<%=CommonStorage.getCurrentDate()%>" />
                                        </div>
                                    </div>
                                    <div class="form-group ">
                                        <label>&nbsp;</label>
                                        <button id = 'periodicalReportButton' name = 'periodicalReportButton' class='btn btn-primary form-control' style="width:8em; float:right"><%=CommonStorage.getText("generate")%></button>
                                    </div>
                                </form>
                            </div> <!-- /.panel-body -->
                        </div>
                    </div>
                    <div class="col-lg-5 col-lg-offset-1">
                        <div class="panel panel-default" >
                            <div class="panel-heading">
                                <%=CommonStorage.getText("kebele_report")%>
                            </div>
                            <div class="panel-body" id="panelBody" >
                                <form role="form" action="#" method="POST" id="kebeleReportForm" name="kebeleReportForm" style="padding-left: 1em;padding-right: 1em">
                                    <div class="form-group">
                                        <label><%=CommonStorage.getText("kebele")%></label>
                                        <select class="form-control" id="kebele" name="kebele">
                                            <%
                                                Option[] kebeles = MasterRepository.getInstance().getAllKebeles();
                                                for (int i = 0; i < kebeles.length; i++) {
                                                    out.println("<option value = '" + kebeles[i].getKey() + "'>"
                                                            + kebeles[i].getValue()
                                                            + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group ">
                                        <label>&nbsp;</label>
                                        <button id = 'kebeleReportButton' name = 'kebeleReportButton' class='btn btn-primary form-control' style="width:8em; float:right"><%=CommonStorage.getText("generate")%></button>
                                    </div>
                                </form>
                            </div> <!-- /.panel-body -->
                        </div>
                    </div>
                </div>
                <div id="reportDetail"></div>
            </div>
            <div class="tab-pane fade" id="publicDisplay">
                <div class="row">
                    <div class="col-lg-4 col-lg-offset-4">
                        <div class="panel panel-default" >
                            <div class="panel-body" id="panelBody" >
                                <form role="form" action="#" method="POST" id="publicDisplayForm" name="publicDisplayForm" style="padding-left: 1em;padding-right: 1em">
                                    <div class="form-group">
                                        <label><%=CommonStorage.getText("kebele")%></label>
                                        <select class="form-control" id="publicDisplayKebele" name="publicDisplayKebele">
                                            <%
                                                for (int i = 0; i < kebeles.length; i++) {
                                                    out.println("<option value = '" + kebeles[i].getKey() + "'>"
                                                            + kebeles[i].getValue()
                                                            + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group ">
                                        <label>&nbsp;</label>
                                        <button id = 'publicDisplayButton' name = 'publicDisplayButton' class='btn btn-primary form-control' style="width:8em; float:right"><%=CommonStorage.getText("generate")%></button>
                                    </div>
                                </form>
                            </div> <!-- /.panel-body -->
                        </div>
                    </div>
                </div>
                <div id="publicDisplayDetail"></div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabe" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><%=CommonStorage.getText("register_a_user")%></h4>
            </div>            <!-- /modal-header -->
            <div class="modal-body">
                <form role="form" action="#" id="addUserForm">
                    <div class="panel-body">
                        <div class="row">
                            <div class="form-group">
                                <label><%=CommonStorage.getText("first_name")%></label>
                                <input class="form-control " id="firstName" name="firstName" />
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("fathers_name")%></label>
                                <input class="form-control " id="fathersName" name="fathersName" />
                            </div> 
                            <div class="form-group">
                                <label><%=CommonStorage.getText("grandfather_name")%></label>
                                <input class="form-control " id="grandFathersName" name="grandFathersName" />
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("phone_number")%></label>
                                <input class="form-control " id="phoneNo" name="phoneno" />
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("role")%></label>
                                <select class="form-control" id="role" name="role">
                                    <option value = 'feo'><%=CommonStorage.getText("first_entry_operator")%></option>
                                    <option value = 'seo'><%=CommonStorage.getText("second_entry_operator")%></option>
                                    <option value = 'supervisor'><%=CommonStorage.getText("supervisor")%></option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("user_name")%></label>
                                <input class="form-control " type="text" id="username" name="username" />
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("password")%></label>
                                <input class="form-control " type="password" id="password" name="password" />
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("confirm_password")%></label>
                                <input class="form-control " type="password" id="cpassword" name="cpassword" />
                            </div>
                        </div> <!-- /.row (nested) -->
                    </div> <!-- /.panel-body -->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("cancel")%></button>
                <input type="submit" id="saveUserButton" class="btn btn-primary" value = "<%=CommonStorage.getText("add")%>" />
            </div> 
        </div>
    </div>
</div>
<div class="modal fade" id="updatePasswordModal" tabindex="-1" aria-labelledby="updatePasswordModalLabe" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><%=CommonStorage.getText("update_password")%></h4>
            </div>            <!-- /modal-header -->
            <div class="modal-body">
                <form role="form" action="#" id="updatePasswordForm">
                    <input type="hidden" id="usrId" value=""/>
                    <div class="panel-body">
                        <div class="row">
                            <div class="form-group">
                                <label><%=CommonStorage.getText("new_password")%></label>
                                <input class="form-control " id="newPassword" name="newPassword" type="password"/>
                            </div> 
                            <div class="form-group">
                                <label><%=CommonStorage.getText("confirm_password")%></label>
                                <input class="form-control " id="cnewPassword" name="cnewPassword" type="password"/>
                            </div>
                        </div> <!-- /.row (nested) -->
                    </div> <!-- /.panel-body -->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("cancel")%></button>
                <input type="submit" id="updatePasswordButton" class="btn btn-primary" value = "<%=CommonStorage.getText("update")%>" />
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
    function validateUpdatePassword() {
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
    function validateGenerateReport() {
        var returnValue = true;
        $("#report #fromDate").toggleClass("error-field", false);
        $("#report #toDate").toggleClass("error-field", false);
        if ($("#report #fromDate").val().trim() === "") {
            returnValue = false;
            $("#report #fromDate").toggleClass("error-field", true);
        }
        if ($("#report #toDate").val().trim() === "") {
            returnValue = false;
            $("#report #toDate").toggleClass("error-field", true);
        }
        if (returnValue) {
            var fromDate = new Date($("#report #fromDate").val());
            var toDate = new Date($("#report #toDate").val());
            if (toDate < fromDate) {
                returnValue = false;
                $("#report #toDate").toggleClass("error-field", true);
                $("#report #fromDate").toggleClass("error-field", true);
            }
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
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_delete_this_user")%>?", function (result) {
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
    $("#settings select").each(function () {
        $(this).val($(this).attr("value"));
    });
    $("#updatePasswordButton").click(function () {
        if (!validateUpdatePassword()) {
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
        } else {
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
    $('#dataTables').dataTable( {
        "lengthMenu": [[30, 50, 100, -1], [30, 50, 100, "All"]]
    } );
    $('.editButton').click(function () {
        editUser($(this).attr("data-userId"));
        return false;
    });
    $('.viewButton').click(function () {
        viewUser($(this).attr("data-userId"));
        return false;
    });
    $('.changePasswordButton').click(function () {
        $("#updatePasswordForm #usrId").val($(this).attr("data-userId"));
        $("#updatePasswordModal").modal();
        return false;
    });
    $('.deleteButton').click(function () {
        deleteUser($(this).attr("data-userId"));
        return false;
    });
    $("#settings #updateSettingsButton").click(function () {
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_save_the_settings")%>?", function (result) {
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
    $("#saveUserButton").click(function () {
        if (!validate("addUserForm")) {// validate
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
        } else {
            save(); // save
            $("#addModal").hide(); // close modale
        }
        return false;
    });
    $("#periodicalReportButton").click(function () {
        if (!validateGenerateReport()) {
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
        } else {
            $.ajax({
                url: "<%=periodicalReporturl%>",
                data: {
                    fromDate: $("#report #fromDate").val(),
                    toDate: $("#report #toDate").val()
                },
                error: showajaxerror,
                success: function (data) {
                    $("#reportDetail").html(data);
                }
            });
        }
        return false;
    });

    $("#kebeleReportButton").click(function () {
        if ($("#kebeleReportForm #kebele").val().trim() === "") {
            showError("<%=CommonStorage.getText("please_select_a_kebele_first_to_generate_a_report")%>");
        } else {
            $.ajax({
                url: "<%=kebeleReporturl%>",
                data: {
                    kebele: $("#kebeleReportForm #kebele").val()
                },
                error: showajaxerror,
                success: function (data) {
                    $("#reportDetail").html(data);
                }
            });
        }
        return false;
    });
    $("#reportLink").click(function () {
        $("#reportDetail").html("");
    });
    
    $("#publicDisplayButton").click(function () {
        if ($("#publicDisplayForm #publicDisplayKebele").val().trim() === "") {
            showError("<%=CommonStorage.getText("please_select_a_kebele_first_to_generate_a_public_display_printout")%>");
        } else {
            $.ajax({
                url: "<%=publicDisplayurl%>",
                data: {
                    kebele: $("#publicDisplayForm #publicDisplayKebele").val()
                },
                error: showajaxerror,
                success: function (data) {
                    var mywindow = window.open('#','Public Display');
                    mywindow.document.write('<html><head><title>Public Display - Printout</title></head>');
                    mywindow.document.write('<body>');
                    mywindow.document.write(data);
                    mywindow.document.write('</body></html>');
                    mywindow.document.close();
                    mywindow.focus();
                    mywindow.print();
                    mywindow.close();
                    //$("#publicDisplayDetail").html(data);
                }
            });
        }
        return false;
    });
    
</script>