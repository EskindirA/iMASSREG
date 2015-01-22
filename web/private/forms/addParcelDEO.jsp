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
            <h2 class="page-header">&nbsp;&nbsp;Add new parcel</h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Parcel: Administrative UPI - ${sessionScope.upi}
                </div>
                <div class="panel-body">
                    <form role="form" action="#" id="addParcelForm">
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="row">
                                <div class="form-group col-lg-4">
                                    <label>Team</label>
                                    <select class="form-control " name = "teamNo" id = "teamNo" value="${sessionScope.teamNo}">
                                        <%
                                            int[] teamNumbers = CommonStorage.getTeamNumbers();
                                            for(int i=0; i < teamNumbers.length;i++){
                                                out.println("<option value='"+teamNumbers[i]+"'>Team "+teamNumbers[i]+"</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group col-lg-8">
                                    <label>Orthograph map sheet No.</label>
                                    <input class="form-control " placeholder="Enter orthograph map sheet number " id="mapsheetno" name="mapsheetno" required ="true" value="${sessionScope.currentParcel.mapSheetNo}" autocomplete="off"/>
                                </div>
                                </div>
                                <div class="form-group">
                                    <label>Certificate Number</label>
                                    <input class="form-control " placeholder="Leave empty if certificate number does not exist" id="certificateNumber" name="certificateNumber" value="${sessionScope.currentParcel.certificateNumber}" autocomplete="off"/>
                                </div>
                                <div class="form-group">
                                    <label>Holding Number</label>
                                    <input class="form-control " placeholder="Leave empty if holding number does not exist" id="holdingNumber" name="holdingNumber" value="${sessionScope.currentParcel.holdingNumber}" autocomplete="off"/>
                                </div>                                
                                <div class="form-group">
                                    <label>Other Evidence</label>
                                    <select class="form-control" id="otherEvidence" name="otherEvidence" data-value="${sessionScope.currentParcel.otherEvidence}">
                                        <%
                                            Option[] otherEvidenceTypes = MasterRepository.getInstance().getAllOtherEvidenceTypes();
                                            for (int i = 0; i < otherEvidenceTypes.length; i++) {
                                                out.println("<option value = '" + otherEvidenceTypes[i].getKey() + "'>" + otherEvidenceTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Means of Acquisition </label>
                                    <select class="form-control" id="meansOfAcquisition" name="meansOfAcquisition" data-value="${sessionScope.currentParcel.meansOfAcquisition}">
                                        <%
                                            Option[] meansOfAcquisitionTypes = MasterRepository.getInstance().getAllMeansOfAcquisitionTypes();
                                            for (int i = 0; i < meansOfAcquisitionTypes.length; i++) {
                                                out.println("<option value = '" + meansOfAcquisitionTypes[i].getKey() + "'>" + meansOfAcquisitionTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Acquisition Year</label>
                                    <select class="form-control" name="acquisitionYear" id = "acquisitionYear" value="${sessionScope.currentParcel.acquisitionYear}">
                                        <%
                                            for (int i = 1963; i <= 2007; i++) {
                                                out.println("<option value = '" + i + "'>" + i + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <input type="submit" id = "backButton" class="btn btn-default col-lg-3 col-lg-offset-1" value="Back" />
                            </div> <!-- /.col-lg-6 (nested) -->
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label>Current Land Use</label>
                                    <select class="form-control" id="currentLandUse" name="currentLandUse" value="${sessionScope.currentParcel.currentLandUse}">
                                        <%
                                            Option[] currentLandUseTypes = MasterRepository.getInstance().getAllCurrentLandUseTypes();
                                            for (int i = 0; i < currentLandUseTypes.length; i++) {
                                                out.println("<option value = '" + currentLandUseTypes[i].getKey() + "'>" + currentLandUseTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Soil Fertility </label>
                                    <select class="form-control" id="soilFertility" name="soilFertility" value="${sessionScope.currentParcel.soilFertility}">
                                        <%
                                            Option[] soilFertilityTypes = MasterRepository.getInstance().getAllSoilFertilityTypes();
                                            for (int i = 0; i < soilFertilityTypes.length; i++) {
                                                out.println("<option value = '" + soilFertilityTypes[i].getKey() + "'>" + soilFertilityTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Holding Type</label>
                                    <select class="form-control" id="holdingType" name="holdingType" value="${sessionScope.currentParcel.holding}">
                                        <%
                                            Option[] holdingTypes = MasterRepository.getInstance().getAllHoldingTypes();
                                            for (int i = 0; i < holdingTypes.length; i++) {
                                                out.println("<option value = '" + holdingTypes[i].getKey() + "'>" + holdingTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Encumbrance </label>
                                    <select class="form-control" id="encumbrance" name="encumbrance" value="${sessionScope.currentParcel.encumbrance}">
                                        <%
                                            Option[] encumbranceTypes = MasterRepository.getInstance().getAllEncumbranceTypes();
                                            for (int i = 0; i < encumbranceTypes.length; i++) {
                                                out.println("<option value = '" + encumbranceTypes[i].getKey() + "'>" + encumbranceTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Survey Date </label>
                                    <input class="form-control " placeholder="Select survey date" type="text" id="surveyDate" name="surveyDate" required value="${sessionScope.currentParcel.surveyDate}" autocomplete="off" readonly style="background: #FFF !important"/>
                                </div>
                                <div class="form-group">
                                    <label>Has Dispute ?:</label>
                                    <select class="form-control" id="hasDispute" name="hasDispute" value="${sessionScope.currentParcel.hasDispute}">
                                        <option value = 'false'>No</option>
                                        <option value = 'true'>Yes</option>
                                    </select>
                                </div>
                                <input type="submit" id = "submitButton" class="btn btn-default col-lg-3 col-lg-offset-9" value="Save" />
                            </div> <!-- /.col-lg-6 (nested) -->
                        </div> <!-- /.row (nested) -->
                    </form>
                </div> <!-- /.panel-body -->
            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div>
</div>
<script type="text/javascript">
    var calendar = $.calendars.instance("ethiopian","am"); 
    $("#surveyDate").calendarsPicker({calendar: calendar});
    function validate() {
        var returnValue = true;
        //var dateReg = "/^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/";
        $("#mapsheetno").toggleClass("error-field",false);
        $("#surveyDate").toggleClass("error-field",false);
        $("#otherEvidence").toggleClass("error-field",false);
        $("#meansOfAcquisition").toggleClass("error-field",false);
        $("#acquisitionYear").toggleClass("error-field",false);
        $("#currentLandUse").toggleClass("error-field",false);
        $("#soilFertility").toggleClass("error-field",false);
        $("#holdingType").toggleClass("error-field",false);
        $("#Encumbrance").toggleClass("error-field",false);
        $("#hasDispute").toggleClass("error-field",false);
        if ($("#mapsheetno").val() === "") {
            returnValue = false;
            $("#mapsheetno").toggleClass("error-field",true);
        }
        if ($("#surveyDate").val() === "") {
            returnValue = false;
            $("#surveyDate").toggleClass("error-field",true);
        }
        if ($("#otherEvidence").val() === "") {
            returnValue = false;
            $("#otherEvidence").toggleClass("error-field",true);
        }
        if ($("#meansOfAcquisition").val() === "") {
            returnValue = false;
            $("#meansOfAcquisition").toggleClass("error-field",true);
        }
        if ($("#acquisitionYear").val() === "") {
            returnValue = false;
            $("#acquisitionYear").toggleClass("error-field",true);
        }
        if ($("#currentLandUse").val() === "") {
            returnValue = false;
            $("#currentLandUse").toggleClass("error-field",true);
        }
        if ($("#soilFertility").val() === "") {
            returnValue = false;
            $("#soilFertility").toggleClass("error-field",true);
        }
        if ($("#holdingType").val() === "") {
            returnValue = false;
            $("#holdingType").toggleClass("error-field",true);
        }
        if ($("#Encumbrance").val() === "") {
            returnValue = false;
            $("#Encumbrance").toggleClass("error-field",true);
        }
        if ($("#hasDispute").val() === "") {
            returnValue = false;
            $("#hasDispute").toggleClass("error-field",true);
        }
        //if (!$("#surveyDate").val().match(dateReg)) {
        //    returnValue = false;
        //    $("#surveyDate").toggleClass("error-field",true);
        //}
        
        return returnValue;
    }
    function save() {
        $.ajax({
            type: 'POST',
            url: "<%=saveurl%>",
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
            success: loadForward
        });
    }
    $("#addParcelForm select").each(function() {
        $(this).val($(this).attr("value"));
    });
    $("#submitButton").click(function() {
        if (validate()) {
            if ($("#certificateNumber").val() === "" && $("#holdingNumber").val() === "") {
                bootbox.confirm("Are you sure both Certificate Number and Holding Number are absent?",function(result){
                    if(result){
                        save();
                    }        
                });
            } else{
                save();
                return false;
            }
        }else{
            showError("Please fill in a correct value for the highlighted fields");

        }
        return false;
    });
    $("#backButton").click(function() {
        bootbox.confirm("Are you sure you want to go back?", function(result) {
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