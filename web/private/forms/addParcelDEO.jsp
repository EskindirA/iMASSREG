<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%
    String saveurl;
    if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
        saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_PARCEL_FEO;
    } else {
        saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_PARCEL_SEO;
    }
%>
<div class="col-lg-8 col-lg-offset-2">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4 ">
            <h2 class="page-header"><%=CommonStorage.getText("add_new_parcel")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <%=CommonStorage.getText("parcel")%>: <%=CommonStorage.getText("administrative_upi")%> - ${sessionScope.upi}
                </div>
                <div class="panel-body">
                    <form role="form" action="#" id="addParcelForm">
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="row">
                                    <div class="form-group col-lg-5">
                                        <label><%=CommonStorage.getText("team")%></label>
                                        <select class="form-control " name = "teamNo" id = "teamNo" value="${sessionScope.teamNo}">
                                            <%
                                                int[] teamNumbers = CommonStorage.getTeamNumbers();
                                                for (int i = 0; i < teamNumbers.length; i++) {
                                                    out.println("<option value='" + teamNumbers[i] + "'>" + CommonStorage.getText("team") + " " + teamNumbers[i] + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group col-lg-7">
                                        <label><%=CommonStorage.getText("orthograph_map_sheet_no")%></label>
                                        <input class="form-control " id="mapsheetno" name="mapsheetno" required ="true" value="${sessionScope.currentParcel.mapSheetNo}" autocomplete="off"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("certificate_number")%></label>
                                    <input class="form-control " id="certificateNumber" name="certificateNumber" value="${sessionScope.currentParcel.certificateNumber}" autocomplete="off"/>
                                </div>
                                <div class="row">
                                    <div class="form-group col-lg-8">
                                        <label><%=CommonStorage.getText("holding_number")%></label>
                                        <input class="form-control " id="holdingNumber" name="holdingNumber" value="${sessionScope.currentParcel.holdingNumber}" autocomplete="off"/>
                                    </div> 
                                    <div class="form-group col-lg-4">
                                        <label><%=CommonStorage.getText("holding_lot_number")%></label>
                                        <input class="form-control " id="holdingLotNumber" name="holdingLotNumber" value="${sessionScope.currentParcel.holdingLotNumber}" autocomplete="off"/>
                                    </div> 
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("other_evidence")%></label>
                                    <select class="form-control" id="otherEvidence" name="otherEvidence" data-value="${sessionScope.currentParcel.otherEvidence}">
                                        <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                        <%
                                            Option[] otherEvidenceTypes = MasterRepository.getInstance().getAllOtherEvidenceTypes();
                                            for (int i = 0; i < otherEvidenceTypes.length; i++) {
                                                out.println("<option value = '" + otherEvidenceTypes[i].getKey() + "'>" + otherEvidenceTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("means_of_acquisition")%> </label>
                                    <select class="form-control" id="meansOfAcquisition" name="meansOfAcquisition" data-value="${sessionScope.currentParcel.meansOfAcquisition}">
                                        <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                        <%
                                            Option[] meansOfAcquisitionTypes = MasterRepository.getInstance().getAllMeansOfAcquisitionTypes();
                                            for (int i = 0; i < meansOfAcquisitionTypes.length; i++) {
                                                out.println("<option value = '" + meansOfAcquisitionTypes[i].getKey() + "'>" + meansOfAcquisitionTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("acquisition_year")%></label>
                                    <select class="form-control" name="acquisitionYear" id = "acquisitionYear" value="${sessionScope.currentParcel.acquisitionYear}">
                                        <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                        <option value="1"><%=CommonStorage.getText("not_available")%></option>
                                        <%
                                            for (int i = 1900; i <= 2007; i++) {
                                                out.println("<option value = '" + i + "'>" + i + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <input type="submit" id = "backButton" class="btn btn-default col-lg-3 col-lg-offset-1" value="<%=CommonStorage.getText("back")%>" />
                            </div> <!-- /.col-lg-6 (nested) -->
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("current_land_use")%></label>
                                    <select class="form-control" id="currentLandUse" name="currentLandUse" value="${sessionScope.currentParcel.currentLandUse}">
                                        <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                        <%
                                            Option[] currentLandUseTypes = MasterRepository.getInstance().getAllCurrentLandUseTypes();
                                            for (int i = 0; i < currentLandUseTypes.length; i++) {
                                                out.println("<option value = '" + currentLandUseTypes[i].getKey() + "'>" + currentLandUseTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("soil_fertility")%> </label>
                                    <select class="form-control" id="soilFertility" name="soilFertility" value="${sessionScope.currentParcel.soilFertility}">
                                        <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                        <%
                                            Option[] soilFertilityTypes = MasterRepository.getInstance().getAllSoilFertilityTypes();
                                            for (int i = 0; i < soilFertilityTypes.length; i++) {
                                                out.println("<option value = '" + soilFertilityTypes[i].getKey() + "'>" + soilFertilityTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("holding_type")%></label>
                                    <select class="form-control" id="holdingType" name="holdingType" value="${sessionScope.currentParcel.holding}">
                                        <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                        <%
                                            Option[] holdingTypes = MasterRepository.getInstance().getAllHoldingTypes();
                                            for (int i = 0; i < holdingTypes.length; i++) {
                                                out.println("<option value = '" + holdingTypes[i].getKey() + "'>" + holdingTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("encumbrance")%> </label>
                                    <select class="form-control" id="encumbrance" name="encumbrance" value="${sessionScope.currentParcel.encumbrance}">
                                        <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                        <%
                                            Option[] encumbranceTypes = MasterRepository.getInstance().getAllEncumbranceTypes();
                                            for (int i = 0; i < encumbranceTypes.length; i++) {
                                                out.println("<option value = '" + encumbranceTypes[i].getKey() + "'>" + encumbranceTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("survey_date")%> </label>
                                    <input class="form-control " placeholder="Select survey date" type="text" id="surveyDate" name="surveyDate" required value="${sessionScope.currentParcel.surveyDate}" autocomplete="off" readonly style="background: #FFF !important"/>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("has_dispute")%> ?:</label>
                                    <select class="form-control" id="hasDispute" name="hasDispute" value="${sessionScope.currentParcel.hasDispute}">
                                        <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                        <option value = 'false'><%=CommonStorage.getText("no")%></option>
                                        <option value = 'true'><%=CommonStorage.getText("yes")%></option>
                                    </select>
                                </div>
                                <input type="submit" id = "submitButton" class="btn btn-default col-lg-3 col-lg-offset-9" value="<%=CommonStorage.getText("save")%>" />
                            </div> <!-- /.col-lg-6 (nested) -->
                        </div> <!-- /.row (nested) -->
                    </form>
                </div> <!-- /.panel-body -->
            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div>
</div>
<script type="text/javascript">
    var calendar = $.calendars.instance("ethiopian", "am");
    $("#surveyDate").calendarsPicker({calendar: calendar});
    function validate() {
        var returnValue = true;
        //var dateReg = "/^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/";
        $("#mapsheetno").toggleClass("error-field", false);
        $("#surveyDate").toggleClass("error-field", false);
        $("#otherEvidence").toggleClass("error-field", false);
        $("#meansOfAcquisition").toggleClass("error-field", false);
        $("#acquisitionYear").toggleClass("error-field", false);
        $("#currentLandUse").toggleClass("error-field", false);
        $("#soilFertility").toggleClass("error-field", false);
        $("#holdingType").toggleClass("error-field", false);
        $("#encumbrance").toggleClass("error-field", false);
        $("#holdingLotNumber").toggleClass("error-field", false);
        $("#hasDispute").toggleClass("error-field", false);
        if ($("#mapsheetno").val() === "") {
            returnValue = false;
            $("#mapsheetno").toggleClass("error-field", true);
        }
        if ($("#surveyDate").val() === "") {
            returnValue = false;
            $("#surveyDate").toggleClass("error-field", true);
        }
        if ($("#otherEvidence").val() === "") {
            returnValue = false;
            $("#otherEvidence").toggleClass("error-field", true);
        }
        if ($("#meansOfAcquisition").val() === "") {
            returnValue = false;
            $("#meansOfAcquisition").toggleClass("error-field", true);
        }
        if ($("#acquisitionYear").val() === "") {
            returnValue = false;
            $("#acquisitionYear").toggleClass("error-field", true);
        }
        if ($("#currentLandUse").val() === "") {
            returnValue = false;
            $("#currentLandUse").toggleClass("error-field", true);
        }
        if ($("#soilFertility").val() === "") {
            returnValue = false;
            $("#soilFertility").toggleClass("error-field", true);
        }
        if ($("#holdingType").val() === "") {
            returnValue = false;
            $("#holdingType").toggleClass("error-field", true);
        }
        if ($("#encumbrance").val() === "") {
            returnValue = false;
            $("#encumbrance").toggleClass("error-field", true);
        }
        if ($("#hasDispute").val() === "") {
            returnValue = false;
            $("#hasDispute").toggleClass("error-field", true);
        }
        if ($("#holdingLotNumber").val() !== "" && isNaN($("#holdingLotNumber").val())) {
            returnValue = false;
            $("#holdingLotNumber").toggleClass("error-field", true);
        }
        return returnValue;
    }
    function save() {
        $.ajax({
            type: 'POST',
            url: "<%=saveurl%>",
            data: {
                "certificateNumber": $("#certificateNumber").val(),
                "holdingNumber": $("#holdingNumber").val(),
                "holdingLotNumber": $("#holdingLotNumber").val(),
                "otherEvidence": $("#otherEvidence").val(),
                "meansOfAcquisition": $("#meansOfAcquisition").val(),
                "acquisitionYear": $("#acquisitionYear").val(),
                "mapsheetNumber": $("#mapsheetno").val(),
                "currentLandUse": $("#currentLandUse").val(),
                "soilFertility": $("#soilFertility").val(),
                "holdingType": $("#holdingType").val(),
                "encumbrance": $("#encumbrance").val(),
                "surveyDate": $("#surveyDate").val(),
                "teamNo": $("#teamNo").val(),
                "hasDispute": $("#hasDispute").val()
            },
            error: showajaxerror,
            success: loadForward
        });
    }
    $("#addParcelForm select").each(function () {
        $(this).val($(this).attr("value"));
    });
    $("#submitButton").click(function () {
        if (validate()) {
            if ($("#certificateNumber").val() === "" && $("#holdingNumber").val() === "") {
                bootbox.confirm("<%=CommonStorage.getText("are_you_sure_both_certificate_number_and_holding_number_are_absent")%>?", function (result) {
                    if (result) {
                        save();
                    }
                });
            } else {
                save();
                return false;
            }
        } else {
            showError("<%=CommonStorage.getText("please_input_appropriate_values_in_the_highlighted_fields")%>");

        }
        return false;
    });
    $("#backButton").click(function () {
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_go_back")%>?", function (result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: "<%=request.getContextPath()%>/Index?action=<%=Constants.ACTION_WELCOME_PART%>",
                                        error: showajaxerror,
                                        success: loadBackward
                                    });
                                }
                            });
                            return false;
                        });
</script>