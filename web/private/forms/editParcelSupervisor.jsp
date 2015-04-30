<%@page import="org.lift.massreg.dto.*"%>
<%@page import="org.lift.massreg.entity.Parcel"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    ParcelDifference parcelDifference = parcelDifference = (ParcelDifference) request.getAttribute("currentParcelDifference");
    String nexturl, backurl;
    nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_PARCEL_SUPERVISOR;
    backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_WELCOME_PART;
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
                    <%=CommonStorage.getText("parcel")%>: <%=CommonStorage.getText("administrative_upi")%> - ${requestScope.upi} [ <%=currentParcel.getHolderCount()%> <%=CommonStorage.getText("holders")%>]
                </div>
                <div class="panel-body">
                    <form role="form" action="#" id="editParcelForm" >
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="row">
                                    <div class="form-group col-lg-5">
                                        <label><%=CommonStorage.getText("team")%></label>
                                        <%
                                            if (parcelDifference.isTeamNo()) {
                                                out.println("<select class='form-control discrepancy-field ' placeholder='Does Not Exist' id='teamNo' name='teamNo' value='" + currentParcel.getTeamNo() + "' >");
                                            } else {
                                                out.println("<select class='form-control ' id='teamNo' name='teamNo' value='" + currentParcel.getTeamNo() + "' disabled>");
                                            }
                                            int[] teamNumbers = CommonStorage.getTeamNumbers();
                                            for (int i = 0; i < teamNumbers.length; i++) {
                                                out.println("<option value='" + teamNumbers[i] + "'>Team " + teamNumbers[i] + "</option>");
                                            }
                                            out.println("</select>");
                                        %>
                                    </div>
                                    <div class="form-group col-lg-7">
                                        <label><%=CommonStorage.getText("certificate_number")%></label>
                                        <%
                                            if (parcelDifference.isCertificateNumber()) {
                                                out.println("<input class='form-control discrepancy-field ' placeholder='Does Not Exist' id='certificateNumber' name='certificateNumber' value='" + currentParcel.getCertificateNumber() + "' />");
                                            } else {
                                                out.println("<input class='form-control ' placeholder='Does Not Exist' id='certificateNumber' name='certificateNumber' value='" + currentParcel.getCertificateNumber() + "' disabled/>");
                                            }
                                        %>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("holding_number")%></label>
                                    <%
                                        if (parcelDifference.isHoldingNumber()) {
                                            out.println("<input class='form-control discrepancy-field ' placeholder='Does Not Exist' id='holdingNumber' name='holdingNumber' value='" + currentParcel.getHoldingNumber() + "' />");
                                        } else {
                                            out.println("<input class='form-control ' placeholder='Does Not Exist' id='holdingNumber' name='holdingNumber' value='" + currentParcel.getHoldingNumber() + "' disabled/>");
                                        }
                                    %>
                                </div>                                
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("other_evidence")%></label>
                                    <%
                                        if (parcelDifference.isOtherEvidence()) {
                                            out.println("<select class='form-control discrepancy-field ' id='otherEvidence' name='otherEvidence' value='" + currentParcel.getOtherEvidence() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' placeholder='Does Not Exist' id='otherEvidence' name='otherEvidence' value='" + currentParcel.getOtherEvidence() + "' disabled >");
                                        }
                                    %>
                                    <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>

                                    <%
                                        Option[] otherEvidenceTypes = MasterRepository.getInstance().getAllOtherEvidenceTypes();
                                        for (int i = 0; i < otherEvidenceTypes.length; i++) {
                                            out.println("<option value = '" + otherEvidenceTypes[i].getKey() + "'>" + otherEvidenceTypes[i].getValue() + "</option>");
                                        }
                                        out.println("</select>");
                                    %>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("means_of_acquisition")%> </label>
                                    <%
                                        if (parcelDifference.isMeansOfAcquisition()) {
                                            out.println("<select class='form-control discrepancy-field ' id='meansOfAcquisition' name='meansOfAcquisition' value='" + currentParcel.getMeansOfAcquisition() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' id='meansOfAcquisition' name='meansOfAcquisition' value='" + currentParcel.getMeansOfAcquisition() + "' disabled >");
                                        }
                                    %>
                                    <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>

                                    <%
                                        Option[] meansOfAcquisitionTypes = MasterRepository.getInstance().getAllMeansOfAcquisitionTypes();
                                        for (int i = 0; i < meansOfAcquisitionTypes.length; i++) {
                                            out.println("<option value = '" + meansOfAcquisitionTypes[i].getKey() + "'>" + meansOfAcquisitionTypes[i].getValue() + "</option>");
                                        }
                                        out.println("</select>");
                                    %>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("acquisition_year")%></label>
                                    <%
                                        if (parcelDifference.isAcquisitionYear()) {
                                            out.println("<select class='form-control discrepancy-field ' id='acquisitionYear' name='acquisitionYear' value='" + currentParcel.getAcquisitionYear() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' id='acquisitionYear' name='acquisitionYear' value='" + currentParcel.getAcquisitionYear() + "' disabled >");
                                        }
                                    %>
                                    <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                    <option value="1"><%=CommonStorage.getText("not_available")%></option>
                                    <%
                                        for (int i = 1900; i <= 2007; i++) {
                                            out.println("<option value = '" + i + "'>" + i + "</option>");
                                        }
                                        out.println("</select>");
                                    %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("orthograph_map_sheet_no")%></label>
                                    <%
                                        if (parcelDifference.isMapSheetNo()) {
                                            out.println("<input class='form-control discrepancy-field ' id='mapsheetno' name='mapsheetno' value='" + currentParcel.getMapSheetNo() + "' />");
                                        } else {
                                            out.println("<input class='form-control ' placeholder='Does Not Exist' id='mapsheetno' name='mapsheetno' value='" + currentParcel.getMapSheetNo() + "' disabled/>");
                                        }
                                    %>
                                </div>
                                <input type="submit" id = "backButton" class="btn btn-default col-lg-3" value="<%=CommonStorage.getText("back")%>" />
                            </div> <!-- /.col-lg-6 (nested) -->
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("current_land_use")%></label>
                                    <%
                                        if (parcelDifference.isCurrentLandUse()) {
                                            out.println("<select class='form-control discrepancy-field ' id='currentLandUse' name='currentLandUse' value='" + currentParcel.getCurrentLandUse() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' id='currentLandUse' name='currentLandUse' value='" + currentParcel.getCurrentLandUse() + "' disabled >");
                                        }
                                    %>
                                    <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>

                                    <%
                                        Option[] currentLandUseTypes = MasterRepository.getInstance().getAllCurrentLandUseTypes();
                                        for (int i = 0; i < currentLandUseTypes.length; i++) {
                                            out.println("<option value = '" + currentLandUseTypes[i].getKey() + "'>" + currentLandUseTypes[i].getValue() + "</option>");
                                        }
                                        out.println("</select>");
                                    %>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("soil_fertility")%> </label>
                                    <%
                                        if (parcelDifference.isSoilFertility()) {
                                            out.println("<select class='form-control discrepancy-field ' id='soilFertility' name='soilFertility' value='" + currentParcel.getSoilFertility() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' placeholder='Does Not Exist' id='soilFertility' name='soilFertility' value='" + currentParcel.getSoilFertility() + "' disabled >");
                                        }
                                    %>
                                    <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>

                                    <%
                                        Option[] soilFertilityTypes = MasterRepository.getInstance().getAllSoilFertilityTypes();
                                        for (int i = 0; i < soilFertilityTypes.length; i++) {
                                            out.println("<option value = '" + soilFertilityTypes[i].getKey() + "'>" + soilFertilityTypes[i].getValue() + "</option>");
                                        }
                                        out.println("</select>");
                                    %>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("holding_type")%></label>
                                    <%
                                        if (parcelDifference.isHolding()) {
                                            out.println("<select class='form-control discrepancy-field ' id='holdingType' name='holdingType' value='" + currentParcel.getHolding() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' id='holdingType' name='holdingType' value='" + currentParcel.getHolding() + "' disabled >");
                                        }
                                    %>
                                    <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                    <%
                                        Option[] holdingTypes = MasterRepository.getInstance().getAllHoldingTypes();
                                        for (int i = 0; i < holdingTypes.length; i++) {
                                            out.println("<option value = '" + holdingTypes[i].getKey() + "'>" + holdingTypes[i].getValue() + "</option>");
                                        }
                                        out.println("</select>");
                                    %>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("encumbrance")%> </label>
                                    <%
                                        if (parcelDifference.isEncumbrance()) {
                                            out.println("<select class='form-control discrepancy-field ' id='encumbrance' name='encumbrance' value='" + currentParcel.getEncumbrance() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' id='encumbrance' name='encumbrance' value='" + currentParcel.getEncumbrance() + "' disabled >");
                                        }
                                    %>
                                    <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                    <%
                                        Option[] encumbranceTypes = MasterRepository.getInstance().getAllEncumbranceTypes();
                                        for (int i = 0; i < encumbranceTypes.length; i++) {
                                            out.println("<option value = '" + encumbranceTypes[i].getKey() + "'>" + encumbranceTypes[i].getValue() + "</option>");
                                        }
                                        out.println("</select>");
                                    %>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("survey_date")%> </label>
                                    <%
                                        if (parcelDifference.isSurveyDate()) {
                                            out.println("<input class='form-control discrepancy-field ' type='text' id='surveyDate' name='surveyDate' required value='" + currentParcel.getSurveyDate() + "'  readonly style='background: #FFF !important' />");
                                        } else {
                                            out.println("<input class='form-control ' type='text' id='surveyDate' name='surveyDate' required value='" + currentParcel.getSurveyDate() + "' disabled />");
                                        }
                                    %>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("has_dispute")%> ?:</label>
                                    <%
                                        if (parcelDifference.isHasDispute()) {
                                            out.println("<select class='form-control discrepancy-field ' id='hasDispute' name='hasDispute' value='" + currentParcel.hasDispute() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' id='hasDispute' name='hasDispute' value='" + currentParcel.hasDispute() + "' disabled >");
                                        }
                                    %>
                                    <option value=""><%=CommonStorage.getText("please_select_a_value")%></option>
                                    <option value = 'false'><%=CommonStorage.getText("no")%></option>
                                    <option value = 'true'><%=CommonStorage.getText("yes")%></option>
                                    </select>
                                </div>
                                <div class="row">
                                    <input type="submit" id = "saveButton" name = "saveButton" class="btn btn-default col-lg-3" style="float:right; margin-right: 1em" value="<%=CommonStorage.getText("save")%>" />
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
        $("#encumbrance").toggleClass("error-field", false);
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
        return returnValue;
    }
    function update() {
        $.ajax({
            type: 'POST',
            url: "<%=nexturl%>",
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
    $("#saveButton").click(function () {
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