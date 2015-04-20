<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.entity.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    OrganizationHolder holder = currentParcel.getOrganizationHolder();
    String finishurl, editurl, backurl, nexturl;
    finishurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_WELCOME_PART;
    editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_ORGANIZATION_HOLDER_MCO;
    backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_MCO;
    nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DISPUTE_LIST_MCO;
%>
<div class="col-lg-5 col-lg-offset-4">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2">
            <h2 class="page-header"><%=CommonStorage.getText("view_holder_details")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <%=CommonStorage.getText("parcel")%>: <%=CommonStorage.getText("administrative_upi") + " - " + request.getParameter("upi") %>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form role="form" action="#" id="viewHolderFrom" name="viewHolderFrom" method="action">
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("name")%></label>
                                    <input class="form-control " id="organizationName" name="organizationName" value="<%=holder.getName()%>" disabled/>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("type")%></label>
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
                                                out.println("<button type='submit' id = 'editHolderButton' name = 'editHolderButton' class='btn btn-default col-lg-4 col-lg-offset-2' >" + CommonStorage.getText("edit") + "</button>");
                                            } else {
                                                out.println("<span class='col-lg-2 col-lg-offset-2'></span>");
                                            }
                                            if (currentParcel.hasDispute()) {
                                                out.println("<button type='submit' id = 'nextButton' name = 'nextButton' class='btn btn-default col-lg-5' style='float:right'>" + CommonStorage.getText("next") + "</button>");
                                            } else {
                                                out.println("<button type='submit' id = 'finishButton' name = 'finishButton' class='btn btn-default col-lg-5' style='float:right'>"+CommonStorage.getText("finish")+"</button>");
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
    $("#viewHolderFrom select").each(function () {
        $(this).val($(this).attr("value"));
    });
    $("#backButton").click(function () {
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_go_back")%>?", function (result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    data: {"upi": '<%=request.getParameter("upi")%>'},
                    url: "<%=backurl%>",
                    error: showajaxerror,
                    success: loadBackward
                });
            }
        });
        return false;
    });
    $("#editHolderButton").click(function () {
        $.ajax({
            type: 'POST',
            data: {"upi": '<%=request.getParameter("upi")%>'},
            url: "<%=editurl%>",
            error: showajaxerror,
            success: loadInPlace
        });
        return false;
    });
    $("#nextButton").click(function () {
        $.ajax({
            type: 'POST',
            data: {"upi": '<%=request.getParameter("upi")%>'},
            url: "<%=nexturl%>",
            error: showajaxerror,
            success: loadForward
        });
        return false;
    });
    $("#finishButton").click(function () {
        $.ajax({
            type: 'POST',
            data: {"upi": '<%=request.getParameter("upi")%>'},
            url: "<%=finishurl%>",
            error: showajaxerror,
            success: loadForward
        });
        return false;
    });
</script>