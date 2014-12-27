<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.entity.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%
    Parcel cParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = cParcel.canEdit(CommonStorage.getCurrentUser(request));
    OrganizationHolder holder = cParcel.getOrganaizationHolder();
    String finishurl, editurl, backurl, nexturl;
    if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
        finishurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_FINISH_ORGANIZATION_HOLDER_FEO;
        editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_ORGANIZATION_HOLDER_FEO;
        backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_FEO;
        nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DISPUTE_LIST_FEO;
    } else {
        finishurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_FINISH_ORGANIZATION_HOLDER_SEO;
        editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_ORGANIZATION_HOLDER_SEO;
        backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_SEO;
        nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DISPUTE_LIST_SEO;
    }
%>
<div class="col-lg-5 col-lg-offset-4">
    <div class="row">
        <div class="col-lg-7 col-lg-offset-3">
            <h2 class="page-header">View Holder Details</h2>
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
                            <form role="form" action="#" id="viewHolderFrom" name="viewHolderFrom" method="action">
                                <div class="form-group">
                                    <label>Name</label>
                                    <input class="form-control " id="organizationName" name="organizationName" placeholder="Name of the holding organization" value="<%=holder.getName()%>" disabled/>
                                </div>
                                <div class="form-group">
                                    <label>Type</label>
                                    <select class="form-control" id="organizationType" name="organizationType" value="<%=holder.getOrganizationType()%>" disabled>
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
                                        <button type="submit" id = "backButton" class="btn btn-default col-lg-6" style="float:left">Back</button>
                                    </div>
                                    <div class="col-lg-6">
                                        <%
                                            if (editable) {
                                                out.println("<button type='submit' id = 'editHolderButton' name = 'editHolderButton' class='btn btn-default col-lg-4 col-lg-offset-2' >Edit</button>");
                                            } else {
                                                out.println("<span class='col-lg-2 col-lg-offset-2'></span>");
                                            }
                                            if (cParcel.hasDispute()) {
                                                out.println("<button type='submit' id = 'nextButton' name = 'nextButton' class='btn btn-default col-lg-5' style='float:right'>Next</button>");
                                            } else {
                                                out.println("<button type='submit' id = 'finishButton' name = 'finishButton' class='btn btn-default col-lg-5' style='float:right'>Finish</button>");
                                            }
                                        %>
                                    </div>
                                    <!-- /.col-lg-6 (nested) -->
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
    $("#viewHolderFrom select").each(function() {
        $(this).val($(this).attr("value"));
    });
    $("#backButton").click(function() {
        bootbox.confirm("Are you sure you want to go back?", function(result) {
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
    $("#editHolderButton").click(function() {
        $.ajax({
            type:'POST',
            url: "<%=editurl%>",
            error: showajaxerror,
            success: loadInPlace
        });
        return false;
    });
    $("#nextButton").click(function() {
        $.ajax({
            type:'POST',
            url: "<%=nexturl%>",
            error: showajaxerror,
            success: loadForward
        });
        return false;
    });
    $("#finishButton").click(function() {
        $.ajax({
            type:'POST',
            url: "<%=finishurl%>",
            error: showajaxerror,
            success: loadForward
        });
        return false;
    });
</script>