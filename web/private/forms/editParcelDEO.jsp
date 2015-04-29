<%@page import="org.lift.massreg.dto.ParcelDifference"%>
<%@page import="org.lift.massreg.entity.Parcel"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%
    Parcel cParcel = (Parcel) request.getAttribute("currentParcel");
    ParcelDifference parcelDifference = new ParcelDifference();
    boolean reviewMode = false;
    if (request.getSession().getAttribute("reviewMode") != null) {
        reviewMode = Boolean.parseBoolean(request.getSession().getAttribute("reviewMode").toString());
    }
    if (reviewMode) {
        parcelDifference = (ParcelDifference) request.getAttribute("currentParcelDifference");
    }
    String updateurl, cancelurl, backurl;
    if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
        updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_PARCEL_FEO;
        cancelurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_FEO;
        backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_WELCOME_PART;
    } else {
        updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_PARCEL_SEO;
        cancelurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_PARCEL_SEO;
        backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_WELCOME_PART;
    }
%>
<div class="col-lg-8 col-lg-offset-2">
    <div class="row">
        <div class="col-lg-6 col-lg-offset-3 ">
            <h2 class="page-header"><%=CommonStorage.getText("edit_parcel_details")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <%=CommonStorage.getText("parcel")%>: <%=CommonStorage.getText("administrative_upi")%> - ${requestScope.upi} [ <%=cParcel.getHolderCount()%> <%=CommonStorage.getText("holders")%>]
                </div>
                <div class="panel-body">
                    <form role="form" action="#" id="editParcelForm" >
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="row">
                                    <div class="form-group col-lg-5">
                                        <label><%=CommonStorage.getText("team")%></label>
                                                <select class="form-control <%= reviewMode
                                                && parcelDifference.isTeamNo()
                                                        ? "discrepancy-field" : ""%>" name = "teamNo" id = "teamNo" value="${requestScope.currentParcel.teamNo}">
                                            <%
                                                int[] teamNumbers = CommonStorage.getTeamNumbers();
                                                for (int i = 0; i < teamNumbers.length; i++) {
                                                    out.println("<option value='" + teamNumbers[i] + "'>" + CommonStorage.getText("team") + " " + teamNumbers[i] + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group col-lg-7">
                                        <label><%=CommonStorage.getText("certificate_number")%></label>
                                        <input class='form-control <%= reviewMode
                                                && parcelDifference.isCertificateNumber()
                                                        ? "discrepancy-field" : ""%>'
                                                        id="certificateNumber" name="certificateNumber" value="${requestScope.currentParcel.certificateNumber}" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("holding_number")%></label>
                                           <input class="form-control  <%= reviewMode
                                                   && parcelDifference.isHoldingNumber()
                                                           ? "discrepancy-field" : ""%>" id="holdingNumber" name="holdingNumber" value="${requestScope.currentParcel.holdingNumber}" />
                                </div>                                
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("other_evidence")%></label>
                                            <select class="form-control  <%= reviewMode
                                                    && parcelDifference.isOtherEvidence()
                                                            ? "discrepancy-field" : ""%>" id="otherEvidence" name="otherEvidence" value="${requestScope.currentParcel.otherEvidence}" >
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
                                            <select class="form-control <%= reviewMode
                                                    && parcelDifference.isMeansOfAcquisition()
                                                            ? "discrepancy-field" : ""%>" id="meansOfAcquisition" name="meansOfAcquisition" value="${requestScope.currentParcel.meansOfAcquisition}" >
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
                                            <select class="form-control  <%= reviewMode
                                                    && parcelDifference.isAcquisitionYear()
                                                            ? "discrepancy-field" : ""%>" name="acquisitionYear" id = "acquisitionYear" value="${requestScope.currentParcel.acquisitionYear}" >
                                        <%
                                            out.println("<option value = '0001'>" + CommonStorage.getText("not_available") + "</option>");
                                            for (int i = 1963; i <= 2007; i++) {
                                                out.println("<option value = '" + i + "'>" + i + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("orthograph_map_sheet_no")%></label>
                                           <input class="form-control  <%= reviewMode
                                                   && parcelDifference.isMapSheetNo()
                                                           ? "discrepancy-field" : ""%>" id="mapsheetno" name="mapsheetno" required ="true" value="${requestScope.currentParcel.mapSheetNo}" />
                                </div>
                                <input type="submit" id = "backButton" class="btn btn-default col-lg-3" value="<%=CommonStorage.getText("back")%>" />
                            </div> <!-- /.col-lg-6 (nested) -->
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("current_land_use")%></label>
                                            <select class="form-control  <%= reviewMode
                                                    && parcelDifference.isCurrentLandUse()
                                                            ? "discrepancy-field" : ""%>" id="currentLandUse" name="currentLandUse" value="${requestScope.currentParcel.currentLandUse}" >
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
                                            <select class="form-control  <%= reviewMode
                                                    && parcelDifference.isSoilFertility()
                                                            ? "discrepancy-field" : ""%>" id="soilFertility" name="soilFertility" value="${requestScope.currentParcel.soilFertility}" >
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
                                            <select class="form-control  <%= reviewMode
                                                    && parcelDifference.isHolding()
                                                            ? "discrepancy-field" : ""%>" id="holdingType" name="holdingType" value="${requestScope.currentParcel.holding}" >
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
                                            <select class="form-control  <%= reviewMode
                                                    && parcelDifference.isEncumbrance()
                                                            ? "discrepancy-field" : ""%>" id="encumbrance" name="encumbrance" value="${requestScope.currentParcel.encumbrance}" >
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
                                           <input class="form-control  <%= reviewMode
                                                   && parcelDifference.isSurveyDate()
                                                           ? "discrepancy-field" : ""%>" placeholder="Select survey date" type="text" id="surveyDate" name="surveyDate" required value="${requestScope.currentParcel.surveyDate}"  readonly style="background: #FFF !important"/>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("has_dispute")%> ?:</label>
                                            <select class="form-control  <%= reviewMode
                                                    && parcelDifference.isHasDispute()
                                                            ? "discrepancy-field" : ""%>" id="hasDispute" name="hasDispute" value="<%= cParcel.hasDispute()%>" >
                                        <option value = 'false'><%=CommonStorage.getText("no")%></option>
                                        <option value = 'true'><%=CommonStorage.getText("yes")%></option>
                                    </select>
                                </div>
                                <div class="row">
                                    <input type='submit' id = 'cancelButton' name = 'editButton' class='btn btn-default col-lg-2 col-lg-offset-6' value='<%=CommonStorage.getText("cancel")%>' />
                                    <input type="submit" id = "updateButton" name = "nextButton" class="btn btn-default col-lg-3" style="margin-left: 1em" value="<%=CommonStorage.getText("save")%>" />
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
    var calendar = $.calendars.instance("ethiopian", "am");
    $("#surveyDate").calendarsPicker({calendar: calendar});
    function validate() {
        var returnValue = true;
        $("#mapsheetno").toggleClass("error-field", false);
        $("#surveyDate").toggleClass("error-field", false);
        $("#otherEvidence").toggleClass("error-field", false);
        $("#meansOfAcquisition").toggleClass("error-field", false);
        $("#acquisitionYear").toggleClass("error-field", false);
        $("#currentLandUse").toggleClass("error-field", false);
        $("#soilFertility").toggleClass("error-field", false);
        $("#holdingType").toggleClass("error-field", false);
        $("#Encumbrance").toggleClass("error-field", false);
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
        if ($("#Encumbrance").val() === "") {
            returnValue = false;
            $("#Encumbrance").toggleClass("error-field", true);
        }
        if ($("#hasDispute").val() === "") {
            returnValue = false;
            $("#hasDispute").toggleClass("error-field", true);
        }
        return returnValue;
    }
    function update() {
        $.ajax({
            type: 'POST',
            url: "<%=updateurl%>",
            data: {
                "certificateNumber": $("#certificateNumber").val(),
                "holdingNumber": $("#holdingNumber").val(),
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
            success: loadInPlace
        });
    }
    $("#editParcelForm select").each(function () {
        $(this).val($(this).attr("value"));
    });
    $("#cancelButton").click(function () {
        $.ajax({
            type: 'POST',
            url: "<%=cancelurl%>",
            error: showajaxerror,
            success: loadInPlace
        });
        return false;
    });
    $("#updateButton").click(function () {
        if (validate()) {
            if ($("#certificateNumber").val() === "" && $("#holdingNumber").val() === "") {
                bootbox.confirm("<%=CommonStorage.getText("are_you_sure_both_certificate_number_and_holding_number_are_absent")%>?", function (result) {
                    if (result) {
                        update();
                    }
                });
            } else {
                update();
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
                    url: "<%=backurl%>",
                    error: showajaxerror,
                    success: loadBackward
                });
            }
        });
        return false;
    });
</script>