<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%--Welcom Page: For the first entry operator--%>
<%
    String addurl, findurl;
    if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
        addurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_ADD_PARCEL_FEO;
        findurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_FIND_PARCEL_FEO;
    } else {
        addurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_ADD_PARCEL_SEO;
        findurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_FIND_PARCEL_SEO;
    }
%>
<div class="col-lg-8 col-lg-offset-2">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CommonStorage.getText("welcome")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <%=CommonStorage.getText("find_a_parcel")%>
                </div>
                <div class="panel-body" id="panelBody">
                    <form role="form" action="#" method="POST" id="welcomeForm" name="welcomeForm">
                        <div class="row">
                            <div class="col-lg-6">
                                <input type="hidden" name="WoredaId" id="WoredaId" value="<%= CommonStorage.getCurrentWoredaId()%>" />
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
                                <div class="form-group error" id="someId">
                                    <label><%=CommonStorage.getText("parcel_id")%></label>
                                    <input class="form-control" name = "parcelNo" id = "parcelNo" type="text" size="5" maxlength="5" required value="${sessionScope.parcelNo}" autocomplete="off" />
                                </div>
                            </div> <!-- /.col-lg-6 (nested) -->
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("administrative_upi")%></label>
                                    <input class="form-control" name = "upi" id = "upi" disabled required value="${sessionScope.upi}"/>
                                </div>
                                <div class="row" style="margin-top: 3em">
                                    <button id = "addButton" class="btn btn-default col-lg-2 col-lg-offset-7"><%=CommonStorage.getText("add")%></button>
                                    <button id = "findButton" class="btn btn-default col-lg-2" style="margin-left: 1em" ><%=CommonStorage.getText("find")%></button>
                                </div>
                            </div> <!-- /.col-lg-6 (nested) -->
                        </div> <!-- /.row (nested) -->
                    </form>
                </div> <!-- /.panel-body -->
            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div>
</div>
<script type="text/javascript">
    function loadAdd() {
        if(isNaN(parseFloat($("#parcelNo").val())) || !isFinite($("#parcelNo").val())){
            showError("Parcel number should be composed of digits only");
            return;
        }
        updateUPI();
        if ($("#kebele").val() === "" ) {
            showError("Please select a kebele");
        }else if ($("#upi").val() === "" || $("#parcelNo").val() === "" || $("#kebele").val() === "") {
            showError("<%=CommonStorage.getText("parcel_number_should_be_composed_of_digits_only")%>");
        } else {// Call to the service to get the add parcel form
            $.ajax({
                success: loadForward,
                type: 'POST',
                data: {
                    "upi": $("#upi").val(),
                    "parcelNo": $("#parcelNo").val(),
                    "kebele": $("#kebele").val()
                },
                url: '<%=addurl%>'
            });
        }
        return false;
    }
    $(function() {
        if ("${sessionScope.kebele}" !== "") {
            $("#kebele").val('${sessionScope.kebele}');
        }
    });
    $("#addButton").click(loadAdd);
    $("#welcomeForm").submit(loadAdd);
    $("#findButton").click(function() {
        if(isNaN(parseFloat($("#parcelNo").val())) || !isFinite($("#parcelNo").val())){
            showError("<%=CommonStorage.getText("parcel_number_should_be_composed_of_digits_only")%>");
            return;
        }
        updateUPI();
        if ($("#kebele").val() === "" ) {
            showError("Please select a kebele");
        }else if ($("#upi").val() === "" || $("#parcelNo").val() === "") {
            showError("<%=CommonStorage.getText("parcel_number_and_administrative_upi_are_required_fields")%>");
        }
        else { // Call to the service to get the view parcel form
            $.ajax({
                success: loadForward,
                error: showajaxerror,
                type: 'POST',
                data: {
                    "upi": $("#upi").val(),
                    "parcelNo": $("#parcelNo").val(),
                    "kebele": $("#kebele").val()},
                url: '<%=findurl%>'
            });
        }
        return false;
    });
    $("#kebele").change(function() {
        updateUPI();
    });
    $("#parcelNo").keypress(function() {
        updateUPI();
    });
    $("#parcelNo").mouseup(function() {
        updateUPI();
    });
    $("#parcelNo").keyup(function() {
        updateUPI();
    });
    function updateUPI() {
        // Pad the upi with leading zeros
        var parcelNo = "" + $("#parcelNo").val();
        var pad = "00000";
        var value = pad.substring(0, pad.length - parcelNo.length) + parcelNo;
        $("#upi").val($("#kebele").val() + "/" + value);
    }
</script>