<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.Parcel"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    String saveurl, backurl;
    saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_HOLDER_SUPERVISOR;
    backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_SUPERVISOR;
%>
<div class="col-lg-5 col-lg-offset-4">
    <div class="row">
        <div class="col-lg-7 col-lg-offset-2 ">
            <h2 class="page-header"><%=CommonStorage.getText("register_holder")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <%=CommonStorage.getText("parcel")%>: <%=CommonStorage.getText("administrative_upi")%> - ${sessionScope.upi}
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form role="form" action="#" method="action">
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-lg-8">
                                            <label><%=CommonStorage.getText("name")%></label>
                                        </div>
                                        <div class="col-lg-4">
                                            <input type="checkbox" id="notavailablechkbox" name="notavailablechkbox"/>
                                            <label><%=CommonStorage.getText("not_available")%></label>
                                        </div>
                                    </div>
                                    <input class="form-control " id="organizationName" name="organizationName" placeholder="Name of the holding organization" autocomplete="off"/>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("type")%></label>
                                    <select class="form-control" id="organizationType" name="organizationType" >
                                        <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
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
                                        <button type="submit" id = "backButton" class="btn btn-default col-lg-6" style="left"><%=CommonStorage.getText("back")%></button>
                                    </div>
                                    <div class="col-lg-6">
                                        <%
                                            if (currentParcel.hasDispute()) {
                                                out.println("<button type='submit' id = 'nextButton' name = 'nextButton' class='btn btn-default col-lg-5' style='float:right'>"+CommonStorage.getText("next")+"</button>");
                                            } else {
                                                out.println("<button type='submit' id = 'nextButton' name = 'nextButton' class='btn btn-default col-lg-5' style='float:right'>"+CommonStorage.getText("finish")+"</button>");
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
    function validate() {
        var returnValue = true;
        $("#organizationName").toggleClass("error-field",false);
        $("#organizationType").toggleClass("error-field",false);
        if ($("#organizationName").val().trim() === "") {
            returnValue = false;
            $("#organizationName").toggleClass("error-field",true);
        }
        if ($("#organizationType").val().trim() === "") {
            returnValue = false;
            $("#organizationType").toggleClass("error-field",true);
        }
        return returnValue;
    }
    function save() {
        $.ajax({
            type:'POST',
            url: "<%=saveurl%>",
            data: {
                "organizationName": $("#organizationName").val(),
                "organizationType": $("#organizationType").val()
            },
            error: showajaxerror,
            success: loadForward
        });
    }
    $("#nextButton").click(function() {
        if (!validate()) {// validate
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");
        } else {
            save();// save
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
    $("#notavailablechkbox").click(function() {
        if ($(this).is(':checked')){
            $("#organizationName").val("<%=CommonStorage.getText("not_available")%>");
            $("#organizationName").attr("disabled", "disabled");
        } else {
            $("#organizationName").val("");
            $("#organizationName").removeAttr("disabled");
        }
    });
</script>