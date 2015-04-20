<%@page import="org.lift.massreg.dto.ParcelDifference"%>
<%@page import="org.lift.massreg.dto.OrganizationHolderDifference"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");

    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    OrganizationHolder holder = currentParcel.getOrganizationHolder();
    String updateurl, cancelurl, backurl;

    updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_ORGANIZATION_HOLDER_MCO;
    cancelurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_MCO;
    backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_MCO;

%>
<div class="col-lg-5 col-lg-offset-4">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2">
            <h2 class="page-header"><%=CommonStorage.getText("edit_holder_details")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <%=CommonStorage.getText("parcel")%>: <%=CommonStorage.getText("administrative_upi")%> - ${requestScope.upi}
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form role="form" action="#" id="editHolderFrom" name="editHolderFrom" method="action">
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("name")%></label>
                                    <input class='form-control' id='organizationName' name='organizationName' value='<%= holder.getName() %>' />
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("type")%></label>
                                    <select class='form-control' id='organizationType' name='organizationType' value='<%=holder.getOrganizationType() %>' >
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
                                        <button type="submit" id = "backButton" class="btn btn-default col-lg-6" style="float:left"><%=CommonStorage.getText("back")%></button>
                                    </div>
                                    <div class="col-lg-6">
                                        <button type='submit' id = 'cancelButton' name = 'cancelButton' class='btn btn-default col-lg-4 col-lg-offset-2' ><%=CommonStorage.getText("cancel")%></button>
                                        <button type='submit' id = 'updateButton' name = 'updateButton' class='btn btn-default col-lg-5' style='float:right'><%=CommonStorage.getText("save")%></button>
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
    function validate() {
        var returnValue = true;
        $("#organizationName").toggleClass("error-field", false);
        $("#organizationType").toggleClass("error-field", false);
        if ($("#organizationName").val().trim() === "") {
            returnValue = false;
            $("#organizationName").toggleClass("error-field", true);
        }
        if ($("#organizationType").val().trim() === "") {
            returnValue = false;
            $("#organizationType").toggleClass("error-field", true);
        }
        return returnValue;
    }
    function save() {
        $.ajax({
            type: 'POST',
            url: "<%=updateurl%>",
            data: {
                "upi": '<%=request.getParameter("upi")%>',
                "organizationName": $("#organizationName").val(),
                "organizationType": $("#organizationType").val()
            },
            error: showajaxerror,
            success: loadForward
        });
    }
    $("#editHolderFrom select").each(function () {
        $(this).val($(this).attr("value"));
    });
    $("#cancelButton").click(function () {
        $.ajax({
            type: 'POST',
            data: {"upi": '<%=request.getParameter("upi")%>'},
            url: "<%=cancelurl%>",
            error: showajaxerror,
            success: loadInPlace
        });
        return false;
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
    $("#updateButton").click(function () {
        if (!validate()) {// validate
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
        } else {
            save();// save
        }
        return false;
    });
</script>