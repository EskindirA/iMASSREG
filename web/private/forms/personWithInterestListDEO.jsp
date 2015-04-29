<%@page import="org.lift.massreg.dto.ParcelDifference"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    ArrayList<PersonWithInterest> personsWithInterest = currentParcel.getPersonsWithInterest();
    ParcelDifference parcelDifference = new ParcelDifference();
    boolean reviewMode = false;
    if (request.getSession().getAttribute("reviewMode") != null) {
        reviewMode = Boolean.parseBoolean(request.getSession().getAttribute("reviewMode").toString());
    }
    if (reviewMode) {
        parcelDifference = (ParcelDifference) request.getAttribute("currentParcelDifference");
    }

    String saveurl, viewurl, editurl, deleteurl,backurl,nexturl,finshurl;
    if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
        saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_PERSON_WITH_INTEREST_FEO;
        viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PERSON_WITH_INTEREST_FEO;
        editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_PERSON_WITH_INTEREST_FEO;
        deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_PERSON_WITH_INTEREST_FEO;
        if (currentParcel.hasOrphanHolder()) {
            backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_GUARDIANS_LIST_FEO;
        } else {
            backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_FEO;
        }
        nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DISPUTE_LIST_FEO;
        finshurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_FINISH_PERSON_WITH_INTEREST_FEO;
    } else {
        saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_PERSON_WITH_INTEREST_SEO;
        viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PERSON_WITH_INTEREST_SEO;
        editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_PERSON_WITH_INTEREST_SEO;
        deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_PERSON_WITH_INTEREST_SEO;
        nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DISPUTE_LIST_SEO;
        if (currentParcel.hasOrphanHolder()) {
            backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_GUARDIANS_LIST_SEO;
        } else {
            backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_SEO;
        }
        finshurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_FINISH_PERSON_WITH_INTEREST_SEO;
    }
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4 ">
            <h2 class="page-header"><%=CommonStorage.getText("persons_with_interest_list")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"> 
                    <%=CommonStorage.getText("parcel")%>: <%=CommonStorage.getText("administrative_upi")%> - ${sessionScope.upi}
                    <% 
                        if(reviewMode && parcelDifference.isPersonsWithInterestDetails()){
                        out.println("<span style='margin-left: 3em' class='discrepancy-field'>"+CommonStorage.getText("there_is_a_discrepancy_in_persons_with_interest_details")+"</span>");
                    }
                    %>
                    <span style='float:right' class='<%= reviewMode && 
                            parcelDifference.isPersonsWithInterestCount()
                            ? "discrepancy-field":""%>'><%=CommonStorage.getText("persons_with_interest_count")%>:<%=currentParcel.getPersonsWithInterestCount()%></span>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                                <tr>
                                    <th><%=CommonStorage.getText("name")%></th>
                                    <th><%=CommonStorage.getText("sex")%></th>
                                    <th><%=CommonStorage.getText("date_of_birth")%></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (int i = 0; i < personsWithInterest.size(); i++) {
                                        if (i % 2 == 0) {
                                            out.println("<tr class='odd gradeA'>");
                                        } else {
                                            out.println("<tr class='even gradeA'>");
                                        }
                                        out.println("<td>" + personsWithInterest.get(i).getFullName() + "</td>");
                                        out.println("<td>" + personsWithInterest.get(i).getSexText() + "</td>");
                                        out.println("<td>" + personsWithInterest.get(i).getDateOfBirth() + "</td>");
                                        out.println("<td>");
                                        out.println("<a href = '#' class = 'viewButton' "
                                                + "data-registeredOn = '" + personsWithInterest.get(i).getRegisteredOn() + "' >"+CommonStorage.getText("view")+"</a>");

                                        if (editable) {
                                            out.println("|");
                                            out.println("<a href = '#' class='editButton' data-registeredOn='"
                                                    + personsWithInterest.get(i).getRegisteredOn() + "'>"+CommonStorage.getText("edit")+"</a>");
                                            out.println("|");
                                            out.println("<a href = '#' class='deleteButton' data-registeredOn='"
                                                    + personsWithInterest.get(i).getRegisteredOn() + "'>"+CommonStorage.getText("delete")+"</a>");

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
                        <button type="submit" id = "backButton" class="btn btn-default col-lg-2 col-lg-offset-1"><%=CommonStorage.getText("back")%></button>
                    </div>
                    <div class="col-lg-6">
                        <div class="row">
                            <%
                                if (editable) {
                                    out.println("<button type='submit' id = 'addPersonWithInterestButton' name = 'addHolderButton' class='btn btn-default col-lg-2 col-lg-offset-6' data-toggle='modal' data-target='#addModal' >"+CommonStorage.getText("add")+"</button>");
                                } else {
                                    out.println("<span class='col-lg-2 col-lg-offset-6'></span>");
                                }
                                if (currentParcel.hasDispute()) {
                                    out.println("<button type='submit' id = 'nextButton' name = 'nextButton' class='btn btn-default col-lg-2' style='margin-left: 1em'>"+CommonStorage.getText("next")+"</button>");
                                }else{
                                    out.println("<button type='submit' id = 'finishButton' name = 'finishButton' class='btn btn-default col-lg-2' style='margin-left: 1em'>"+CommonStorage.getText("finish")+"</button>");
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
                <h4 class="modal-title"><%=CommonStorage.getText("register_a_person_with_interest")%></h4>
            </div>            <!-- /modal-header -->
            <div class="modal-body">
                <form role="form" action="#" id="addPersonWithInterestForm">
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
                                <label><%=CommonStorage.getText("grandfathers_name")%></label>
                                <input class="form-control " id="grandFathersName" name="grandFathersName" />
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("sex")%></label>
                                <select class="form-control" id="sex" name="sex">
                                    <option value = 'm'><%=CommonStorage.getText("male")%></option>
                                    <option value = 'f'><%=CommonStorage.getText("female")%></option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label><%=CommonStorage.getText("date_of_birth")%></label>
                                <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='text' readonly style="background: #FFF !important"/>
                            </div>
                        </div> <!-- /.row (nested) -->
                    </div> <!-- /.panel-body -->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("cancel")%></button>
                <input type="submit" id="savePersonWithInterestButton" class="btn btn-primary" value = "<%=CommonStorage.getText("add")%>" />
            </div> 
        </div>
    </div>
</div>
<div class="modal fade" id="viewModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
<script type="text/javascript">
    var calendarAdd = $.calendars.instance("ethiopian","am"); 
    $("#addPersonWithInterestForm #dateOfBirth").calendarsPicker({calendar: calendarAdd});
   function closeModals() {
        $("#editModal").html("");
        $("#editModal").hide();
        $("#viewModal").hide();
        $("#viewModal").html("");
    }
    function validate(formId){
        return true;
    }
    /*
    function validate(formId) {
        var returnValue = true;
        $("#" + formId + " #firstName").toggleClass("error-field",false);
        $("#" + formId + " #fathersName").toggleClass("error-field",false);
        $("#" + formId + " #grandFathersName").toggleClass("error-field",false);
        $("#" + formId + " #sex").toggleClass("error-field",false);
        if ($("#" + formId + " #firstName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #firstName").toggleClass("error-field",true);
        }
        if ($("#" + formId + " #fathersName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #fathersName").toggleClass("error-field",true);
        }
        if ($("#" + formId + " #grandFathersName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #grandFathersName").toggleClass("error-field",true);
        }
        if ($("#" + formId + " #sex").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #sex").toggleClass("error-field",true);
        }
        return returnValue;
    }
    */
    function loadViewPersonWithInterest(result) {
        $("#editModal").html("").hide();
        $("#viewModal").html(result).modal();
    }
    function loadEditPersonWithInterest(result) {
        $("#viewModal").html("").hide();
        $("#editModal").html(result);
    }
    function deletePersonWithInterest(regOn) {
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_delete_this_person_with_interest")%> ?", function(result) {
            if (result) {
                $.ajax({
                    type:'POST',
                    url: "<%=deleteurl%>",
                    data: {"registeredOn": regOn},
                    error: showajaxerror,
                    success: loadInPlace
                });
            }
        });
    }
    function editPersonWithInterest( regOn) {
        $.ajax({
            type:'POST',
            url: "<%=editurl%>",
            data: {"registeredOn": regOn },
            error: showajaxerror,
            success: loadViewHolder
        });
    }
    function viewPersonWithInterest(regOn) {
        $.ajax({
            type:'POST',
            url: "<%=viewurl%>",
            data: {"registeredOn": regOn},
            error: showajaxerror,
            success: loadViewHolder
        });
    }
    function validatePersonWithInterestList(){
        //showError("validatePersonWithInterestList");
        return true;
    }
    function save() {
        $.ajax({
            type:'POST',
            url: "<%=saveurl%>",
            data: {
                "dateofbirth": $("#addPersonWithInterestForm #dateOfBirth").val(),
                "firstname": $("#addPersonWithInterestForm #firstName").val(),
                "fathersname": $("#addPersonWithInterestForm #fathersName").val(),
                "grandfathersname": $("#addPersonWithInterestForm #grandFathersName").val(),
                "sex": $("#addPersonWithInterestForm #sex").val()
            },
            error: showajaxerror,
            success: loadForward
        });
    }
    $('#dataTables-example').dataTable({"bPaginate": false});
    $('.editButton').click(function() {
        editPersonWithInterest($(this).attr("data-registeredOn"));
        return false;
    });
    $('.viewButton').click(function() {
        viewPersonWithInterest($(this).attr("data-registeredOn"));
        return false;
    });
    $('.deleteButton').click(function() {
        deletePersonWithInterest($(this).attr("data-registeredOn"));
        return false;
    });
    $("#savePersonWithInterestButton").click(function() {
        if (!validate("addPersonWithInterestForm")) {// validate
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
        } 
        else {
            save();// save
            $("#addModal").hide();// close modale
        }
        return false;
    });
    $("#nextButton").click(function() {
        if(validatePersonWithInterestList()){
            $.ajax({
                type:'POST',
                url: "<%=nexturl%>",
                error: showajaxerror,
                success: loadForward
            });
        }
        return false;
    });
    $("#backButton").click(function() {
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_go_back")%>?", function(result) {
            if (result) {
                $.ajax({
                    type:'POST',
                    url: "<%=backurl%>",
                    error: showajaxerror,
                    success: loadBackward
                });
            }
        });
        return false;
    });
    $("#finishButton").click(function() {
        $.ajax({
            type:'POST',
            url: "<%=finshurl%>",
            error: showajaxerror,
            success: loadForward
                });
        return false;
    });
</script>
