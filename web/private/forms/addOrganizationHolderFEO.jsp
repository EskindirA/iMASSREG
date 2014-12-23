<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="org.lift.massreg.util.Constants"%>
<%@page import="org.lift.massreg.entity.Parcel"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.Option"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
%>
<div class="col-lg-5 col-lg-offset-4">
    <div class="row">
        <div class="col-lg-7 col-lg-offset-2 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;Register Holder</h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Parcel: Administrative UPI - ${sessionScope.upi}
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form role="form" action="#" method="action">
                                <div class="form-group">
                                    <label>Name</label>
                                    <input class="form-control " id="organizationName" name="organizationName" placeholder="Name of the holding organization" autocomplete="off"/>
                                </div>
                                <div class="form-group">
                                    <label>Type</label>
                                    <select class="form-control" id="organizationType" name="organizationType" >
                                        <%
                                            Option[] organizationTypes = MasterRepository.getInstance().getAllOrganizationTypes();
                                            for (int i = 0; i < organizationTypes.length; i++) {
                                                out.println("<option value = '" + organizationTypes[i].getKey() + "'>" + organizationTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="row">
                                    <div class="col-lg-6">
                                        <button type="submit" id = "backButton" class="btn btn-default col-lg-6" style="left">Back</button>
                                    </div>
                                    <div class="col-lg-6">
                                        <%
                                            if (currentParcel.hasDispute()) {
                                                out.println("<button type='submit' id = 'nextButton' name = 'nextButton' class='btn btn-default col-lg-5' style='float:right'>Next</button>");
                                            } else {
                                                out.println("<button type='submit' id = 'nextButton' name = 'nextButton' class='btn btn-default col-lg-5' style='float:right'>Finish</button>");
                                            }
                                        %>
                                    </div>
                                    <!-- /.col-lg-6 (nested) -->
                                </div>  
                            </form>
                        </div>
                        <!-- /.col-lg-6 (nested) -->
                    </div>
                    <!-- /.row (nested) -->
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
</div>
<script type="text/javascript">
    <%
        String saveurl;
        if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
            saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_HOLDER_FEO;
        } else {
            saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_HOLDER_SEO;
        }
    %>
    function validate() {
        var returnValue = true;
        //$("#organizationName").toggle("error=off");
        if ($("#organizationName").val().trim() === "") {
            returnValue = false;
            //$("#organizationName").toggle("error");
        }
        showMessage("Validation:");
        return returnValue;
    }
    function save() {
        $.ajax({
            url: "<%=saveurl%>",
            data: {
                "organizationName": $("#organizationName").val(),
                "organizationType": $("#organizationType").val()
            },
            error: showajaxerror,
            success: loadForward
        });
    }
    $(function() {
        $("#nextButton").click(function() {
            if (!validate()) {// validate
                showError("Please input appropriate values in the highlighted fields");
                return false;
            } else {
                save();// save
            }
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