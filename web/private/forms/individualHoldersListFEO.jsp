<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    ArrayList<IndividualHolder> holders = currentParcel.getIndividualHolders();
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Parcel Holders' List</h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"> 
                    Parcel: Administrative UPI - ${sessionScope.upi}
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                                <tr>
                                    <th>Holder ID</th>
                                    <th>Name</th>
                                    <th>Sex</th>
                                    <th>Date of Birth</th>
                                    <th>Family Role</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (int i = 0; i < holders.size(); i++) {
                                        if (i % 2 == 0) {
                                            out.println("<tr class='odd gradeA'>");
                                        } else {
                                            out.println("<tr class='even gradeA'>");
                                        }
                                        out.println("<td>" + holders.get(i).getId() + "</td>");
                                        out.println("<td>" + holders.get(i).getFullName() + "</td>");
                                        out.println("<td>" + holders.get(i).getSexText() + "</td>");
                                        out.println("<td>" + holders.get(i).getDateOfBirth() + "</td>");
                                        out.println("<td>" + holders.get(i).getFamilyRoleText() + "</td>");
                                        out.println("<td>");
                                        out.println("<a href = '#' class = 'viewButton' "
                                                + "data-registeredOn = '" + holders.get(i).getRegisteredOn() + "' "
                                                + "data-holderId = '" + holders.get(i).getId()+ "'>View</a>");
                                        
                                        if (editable) {
                                            out.println("|");
                                            out.println("<a href = '#' class='editButton' data-holderId='"
                                                    + holders.get(i).getId() + "' data-registeredOn='"
                                                    + holders.get(i).getRegisteredOn() + "'>Edit</a>");
                                            out.println("|");
                                            out.println("<a href = '#' class='deleteButton' data-holderId='"
                                                    + holders.get(i).getId() + "' data-registeredOn='"
                                                    + holders.get(i).getRegisteredOn() + "'>Delete</a>");
 
                                        }
                                        out.println("</td>");
                                        out.println("</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </div> <!-- /.table-responsive -->
                </div> <!-- /.panel-body -->
                <div class="row">
                    <div class="col-lg-6">
                        <button type="submit" id = "backButton" class="btn btn-default col-lg-2 col-lg-offset-1">Back</button>
                    </div>
                    <div class="col-lg-6">
                        <div class="row">
                            <%
                                if (editable) {
                                    out.println("<button type='submit' id = 'addHolderButton' name = 'addHolderButton' class='btn btn-default col-lg-2 col-lg-offset-6' data-toggle='modal' data-target='#addModal' >Add</button>");
                                } else {
                                    out.println("<span class='col-lg-2 col-lg-offset-6'></span>");
                                }
                                if (currentParcel.hasDispute()) {
                                    out.println("<button type='submit' id = 'nextButton' name = 'nextButton' class='btn btn-default col-lg-2' style='margin-left: 1em'>Next</button>");
                                } else {
                                    out.println("<button type='submit' id = 'finishButton' name = 'finishButton' class='btn btn-default col-lg-2' style='margin-left: 1em'>Finish</button>");
                                }
                            %>
                        </div>                        
                    </div>
                </div>
                <br/>
            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div> <!-- /.row -->
</div>
<div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabe" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Register a holder</h4>
            </div>            <!-- /modal-header -->
            <div class="modal-body">
                <form>
                    <div class="panel-body">
                        <form role="form" action="#" id="addParcelForm">
                            <div class="row">
                                <div class="form-group">
                                    <label>Holder Id</label>
                                    <input class="form-control " type="number" id="holderId" name="holderId" />
                                </div>
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
                                    <label>Sex</label>
                                    <select class="form-control" id="sex" name="sex">
                                        <option value = 'm'>Male</option>
                                        <option value = 'f'>Female</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Date of Birth</label>
                                    <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='date'/>
                                </div>
                                <div class="form-group">
                                    <label>Family Role</label>
                                    <select class="form-control" id="familyRole" name="familyRole" >
                                        <%
                                            Option[] familyRoleTypes = MasterRepository.getInstance().getAllfamilyRoleTypes();
                                            for (int i = 0; i < familyRoleTypes.length; i++) {
                                                out.println("<option value = '" + familyRoleTypes[i].getKey() + "'>" + familyRoleTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div> <!-- /.row (nested) -->
                    </div> <!-- /.panel-body -->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <input type="submit" id="saveHolderButton" class="btn btn-primary" value = "Add" />
            </div> 
        </div>
    </div>
</div>
<div class="modal fade" id="viewModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
                                    
<script type="text/javascript">
    <%
        String saveurl,viewurl,editurl;
        if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
            saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_HOLDER_FEO;
            viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_INDIVIDUAL_HOLDER_FEO;
            editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_INDIVIDUAL_HOLDER_FEO;
        } else {
            saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_HOLDER_SEO;
            viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_INDIVIDUAL_HOLDER_SEO;
            editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_INDIVIDUAL_HOLDER_SEO;
        }
    %>
    function validate() {
        var returnValue = true;
//$("#holderId").toggle("error=off");
//$("#firstName").toggle("error=off");
//$("#fathersName").toggle("error=off");
//$("#grandFathersName").toggle("error=off");
        if ($("#holderId").val().trim() === "") {
            returnValue = false;
//$("#holderId").toggle("error");
        }
        if ($("#firstName").val().trim() === "") {
            returnValue = false;
//$("#firstName").toggle("error");
        }
        if ($("#fathersName").val().trim() === "") {
            returnValue = false;
//$("#fathersName").toggle("error");
        }
        if ($("#grandFathersName").val().trim() === "") {
            returnValue = false;
//$("#grandFathersName").toggle("error");
        }
        showMessage("Validation:");
        return returnValue;
    }
    function loadViewHolder(result) {
        $("#editModal").html("").hide();
        $("#viewModal").html(result).modal();
    }
    function loadEditHolder(result) {
        $("#viewModal").html("").hide();
        $("#editModal").html(result);
    }
    function deleteHolder(holderId,regOn) {
        bootbox.alert("Delete: " + regOn);
    }
    function editHolder(holderId,regOn) {
        $.ajax({
            url: "<%=editurl%>",
            data: {
                "registeredOn": regOn,
                "holderId": holderId
            },
            error: showajaxerror,
            success: loadViewHolder
        });
    }
    function viewHolder(holderId,regOn) {
        $.ajax({
            url: "<%=viewurl%>",
            data: {
                "registeredOn": regOn,
                "holderId": holderId
            },
            error: showajaxerror,
            success: loadViewHolder
        });
    }
    
    function save() {
        $.ajax({
            url: "<%=saveurl%>",
            data: {"dateofbirth": $("#dateOfBirth").val(),
                "familyrole": $("#familyRole").val(),
                "firstname": $("#firstName").val(),
                "fathersname": $("#fathersName").val(),
                "grandfathersname": $("#grandFathersName").val(),
                "holderId": $("#holderId").val(),
                "sex": $("#sex").val()
            },
            error: showajaxerror,
            success: loadForward
        });
    }
    $(document).ready(function() {
        $('#dataTables-example').dataTable({"bPaginate": false});
        $('.editButton').click(function() {
            editHolder($(this).attr("data-holderId"),$(this).attr("data-registeredOn"));
            return false;
        });
        $('.viewButton').click(function() {
            viewHolder($(this).attr("data-holderId"),$(this).attr("data-registeredOn"));
            return false;
        });
        $('.deleteButton').click(function() {
            deleteHolder($(this).attr("data-holderId"),$(this).attr("data-registeredOn"));
            return false;
        });
        $("#saveHolderButton").click(function() {
            if (!validate()) {// validate
                showError("Please input appropriate values in the highlighted fields");
                return false;
            } else {
                save();// save
                $("#addModal").hide();// close modale
            }
            return false;
        });
        $("#nextButton").click(function() {
            $.ajax({
                url: "<%=request.getContextPath()%>/Index?action=<%=Constants.ACTION_DISPUTE_LIST_FEO%>",
                                error: showajaxerror,
                                success: loadForward
                            });
                            return false;
                        });
        $("#finishButton").click(function() {
                            $.ajax({
                                url: "<%=request.getContextPath()%>/Index?action=<%=Constants.ACTION_WELCOME_PART%>",
                                                error: showajaxerror,
                                                success: loadForward
                                            });
                                            return false;
                                        });
        $("#backButton").click(function() {
                    bootbox.confirm("Are you sure you want to go back?", function(result) {
                                                if (result) {
                                                    $.ajax({
                                                        url: "<%=request.getContextPath()%>/Index?action=<%=Constants.ACTION_VIEW_PARCEL_FEO%>",
                                                                                error: showajaxerror,
                                                                                success: loadBackward
                                                                            });
                                                                        }
                                                                    });
                                                                    return false;
                                                                });
    });
</script>
