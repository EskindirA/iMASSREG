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
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Edit Parcel Details </h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Parcel: Administrative UPI - ${requestScope.upi} [ <%=currentParcel.getHolderCount()%> holder(s)]
                </div>
                <div class="panel-body">
                    <form role="form" action="#" id="editParcelForm" >
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="row">
                                    <div class="form-group col-lg-4">
                                        <label>Team</label>
                                        <%
                                            if (parcelDifference.isTeamNo()) {
                                                out.println("<select class='form-control discrepancy-field ' placeholder='Does Not Exist' id='teamNo' name='teamNo' value='" + currentParcel.getTeamNo() + "' />");
                                            } else {
                                                out.println("<input class='form-control ' placeholder='Does Not Exist' id='teamNo' name='teamNo' value='" + currentParcel.getTeamNo() + "' disabled/>");
                                            }
                                            int[] teamNumbers = CommonStorage.getTeamNumbers();
                                            for (int i = 0; i < teamNumbers.length; i++) {
                                                out.println("<option value='" + teamNumbers[i] + "'>Team " + teamNumbers[i] + "</option>");
                                            }
                                            out.println("</select>");
                                        %>
                                    </div>
                                    <div class="form-group col-lg-8">
                                        <label>Certificate Number</label>
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
                                    <label>Holding Number</label>
                                    <%
                                        if (parcelDifference.isHoldingNumber()) {
                                            out.println("<input class='form-control discrepancy-field ' placeholder='Does Not Exist' id='holdingNumber' name='holdingNumber' value='" + currentParcel.getHoldingNumber() + "' />");
                                        } else {
                                            out.println("<input class='form-control ' placeholder='Does Not Exist' id='holdingNumber' name='holdingNumber' value='" + currentParcel.getHoldingNumber() + "' disabled/>");
                                        }
                                    %>
                                </div>                                
                                <div class="form-group">
                                    <label>Other Evidence</label>
                                    <%
                                        if (parcelDifference.isOtherEvidence()) {
                                            out.println("<select class='form-control discrepancy-field ' id='otherEvidence' name='otherEvidence' value='" + currentParcel.getOtherEvidence() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' placeholder='Does Not Exist' id='otherEvidence' name='otherEvidence' value='" + currentParcel.getOtherEvidence() + "' disabled >");
                                        }
                                        Option[] otherEvidenceTypes = MasterRepository.getInstance().getAllOtherEvidenceTypes();
                                        for (int i = 0; i < otherEvidenceTypes.length; i++) {
                                            out.println("<option value = '" + otherEvidenceTypes[i].getKey() + "'>" + otherEvidenceTypes[i].getValue() + "</option>");
                                        }
                                        out.println("</select>");
                                    %>
                                </div>
                                <div class="form-group">
                                    <label>Means of Acquisition </label>
                                    <%
                                        if (parcelDifference.isMeansOfAcquisition()) {
                                            out.println("<select class='form-control discrepancy-field ' id='meansOfAcquisition' name='meansOfAcquisition' value='" + currentParcel.getMeansOfAcquisition() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' placeholder='Does Not Exist' id='meansOfAcquisition' name='meansOfAcquisition' value='" + currentParcel.getMeansOfAcquisition() + "' disabled >");
                                        }
                                        Option[] meansOfAcquisitionTypes = MasterRepository.getInstance().getAllMeansOfAcquisitionTypes();
                                        for (int i = 0; i < meansOfAcquisitionTypes.length; i++) {
                                            out.println("<option value = '" + meansOfAcquisitionTypes[i].getKey() + "'>" + meansOfAcquisitionTypes[i].getValue() + "</option>");
                                        }
                                        out.println("</select>");
                                    %>
                                </div>
                                <div class="form-group">
                                    <label>Acquisition Year</label>
                                    <%
                                        if (parcelDifference.isAcquisitionYear()) {
                                            out.println("<select class='form-control discrepancy-field ' id='acquisitionYear' name='acquisitionYear' value='" + currentParcel.getAcquisitionYear() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' placeholder='Does Not Exist' id='acquisitionYear' name='acquisitionYear' value='" + currentParcel.getAcquisitionYear() + "' disabled >");
                                        }
                                        for (int i = 1963; i <= 2007; i++) {
                                            out.println("<option value = '" + i + "'>" + i + "</option>");
                                        }
                                        out.println("</select>");
                                    %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Orthograph map sheet No.</label>
                                    <%
                                        if (parcelDifference.isMapSheetNo()) {
                                            out.println("<input class='form-control discrepancy-field ' placeholder='Does Not Exist' id='mapsheetno' name='mapsheetno' value='" + currentParcel.getMapSheetNo() + "' />");
                                        } else {
                                            out.println("<input class='form-control ' placeholder='Does Not Exist' id='mapsheetno' name='mapsheetno' value='" + currentParcel.getMapSheetNo() + "' disabled/>");
                                        }
                                    %>
                                </div>
                                <input type="submit" id = "backButton" class="btn btn-default col-lg-3" value="Back" />
                            </div> <!-- /.col-lg-6 (nested) -->
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label>Current Land Use</label>
                                    <%
                                        if (parcelDifference.isCurrentLandUse()) {
                                            out.println("<select class='form-control discrepancy-field ' id='currentLandUse' name='currentLandUse' value='" + currentParcel.getCurrentLandUse() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' placeholder='Does Not Exist' id='currentLandUse' name='currentLandUse' value='" + currentParcel.getCurrentLandUse() + "' disabled >");
                                        }
                                        Option[] currentLandUseTypes = MasterRepository.getInstance().getAllCurrentLandUseTypes();
                                        for (int i = 0; i < currentLandUseTypes.length; i++) {
                                            out.println("<option value = '" + currentLandUseTypes[i].getKey() + "'>" + currentLandUseTypes[i].getValue() + "</option>");
                                        }
                                        out.println("</select>");
                                    %>
                                </div>
                                <div class="form-group">
                                    <label>Soil Fertility </label>
                                    <%
                                        if (parcelDifference.isSoilFertility()) {
                                            out.println("<select class='form-control discrepancy-field ' id='soilFertility' name='soilFertility' value='" + currentParcel.getSoilFertility() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' placeholder='Does Not Exist' id='soilFertility' name='soilFertility' value='" + currentParcel.getSoilFertility() + "' disabled >");
                                        }
                                        Option[] soilFertilityTypes = MasterRepository.getInstance().getAllSoilFertilityTypes();
                                        for (int i = 0; i < soilFertilityTypes.length; i++) {
                                            out.println("<option value = '" + soilFertilityTypes[i].getKey() + "'>" + soilFertilityTypes[i].getValue() + "</option>");
                                        }
                                        out.println("</select>");
                                    %>
                                </div>
                                <div class="form-group">
                                    <label>Holding Type</label>
                                    <%
                                        if (parcelDifference.isHolding()) {
                                            out.println("<select class='form-control discrepancy-field ' id='holdingType' name='holdingType' value='" + currentParcel.getHolding() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' placeholder='Does Not Exist' id='holdingType' name='holdingType' value='" + currentParcel.getHolding() + "' disabled >");
                                        }
                                        Option[] holdingTypes = MasterRepository.getInstance().getAllHoldingTypes();
                                        for (int i = 0; i < holdingTypes.length; i++) {
                                            out.println("<option value = '" + holdingTypes[i].getKey() + "'>" + holdingTypes[i].getValue() + "</option>");
                                        }
                                        out.println("</select>");
                                    %>
                                </div>
                                <div class="form-group">
                                    <label>Encumbrance </label>
                                    <%
                                        if (parcelDifference.isEncumbrance()) {
                                            out.println("<select class='form-control discrepancy-field ' id='encumbrance' name='encumbrance' value='" + currentParcel.getEncumbrance() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' placeholder='Does Not Exist' id='encumbrance' name='encumbrance' value='" + currentParcel.getEncumbrance() + "' disabled >");
                                        }
                                        Option[] encumbranceTypes = MasterRepository.getInstance().getAllEncumbranceTypes();
                                        for (int i = 0; i < encumbranceTypes.length; i++) {
                                            out.println("<option value = '" + encumbranceTypes[i].getKey() + "'>" + encumbranceTypes[i].getValue() + "</option>");
                                        }
                                        out.println("</select>");
                                    %>
                                </div>
                                <div class="form-group">
                                    <label>Survey Date </label>
                                    <%
                                        if (parcelDifference.isSurveyDate()) {
                                            out.println("<input class='form-control discrepancy-field ' type='text' id='surveyDate' name='surveyDate' required value='" + currentParcel.getSurveyDate() + "'  readonly style='background: #FFF !important' />");
                                        } else {
                                            out.println("<input class='form-control ' type='text' id='surveyDate' name='surveyDate' required value='" + currentParcel.getSurveyDate() + "' disabled />");
                                        }
                                    %>
                                </div>
                                <div class="form-group">
                                    <label>Has Dispute ?:</label>
                                    <%
                                        if (parcelDifference.isHasDispute()) {
                                            out.println("<select class='form-control discrepancy-field ' id='hasDispute' name='hasDispute' value='" + currentParcel.hasDispute() + "' >");
                                        } else {
                                            out.println("<select class='form-control ' placeholder='Does Not Exist' id='hasDispute' name='hasDispute' value='" + currentParcel.hasDispute() + "' disabled >");
                                        }
                                    %>
                                    <option value = 'false'>No</option>
                                    <option value = 'true'>Yes</option>
                                    </select>
                                </div>
                                <div class="row">
                                    <input type="submit" id = "saveButton" name = "saveButton" class="btn btn-default col-lg-3" style="float:right; margin-right: 1em" value="Save" />
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
        if ($("#mapsheetno").val() === "") {
            returnValue = false;
            $("#mapsheetno").toggleClass("error-field", true);
        }
        if ($("#surveyDate").val() === "") {
            returnValue = false;
            $("#surveyDate").toggleClass("error-field", true);
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
    $("#editParcelForm select").each(function() {
        $(this).val($(this).attr("value"));
    });
    $("#saveButton").click(function() {
        if (validate()) {
            if ($("#certificateNumber").val() === "" && $("#holdingNumber").val() === "") {
                bootbox.confirm("Are you sure both Certificate Number and Holding Number are absent?", function(result) {
                    if (result) {
                        update();
                    }
                });
            } else {
                update();
            }
        } else {
            showError("Please fill in a correct value for the highlighted fields");
        }
        return false;
    });
    $("#backButton").click(function() {
        bootbox.confirm("Are you sure you want to go back?", function(result) {
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