<%--Tryout Page: Table--%>
<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="org.lift.massreg.util.Option"%>
<%@page import="org.lift.massreg.util.Constants"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
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
                                <div class="form-group">
                                    <label>Certificate Number</label>
                                    <input class="form-control " placeholder="Leave empty if certificate number does not exist" id="certificateNumber" name="certificateNumber" value="${sessionScope.currentParcel.certificateNumber}"/>
                                </div>
                                <div class="form-group">
                                    <label>Holding Number</label>
                                    <input class="form-control " placeholder="Leave empty if holding number does not exist" id="holdingNumber" name="holdingNumber" value="${sessionScope.currentParcel.holdingNumber}"/>
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
                                <div class="form-group">
                                    <label>Orthograph map sheet No.</label>
                                    <input class="form-control " placeholder="Enter Certificate # " id="mapsheetno" name="mapsheetno" required ="true" value="${sessionScope.currentParcel.mapSheetNo}"/>
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
                                    <input class="form-control " placeholder="Select survey date" type="date" id="surveyDate" name="surveyDate" required value="${sessionScope.currentParcel.surveyDate}"/>
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
    <script type="text/javascript">
        <%
            String saveurl;
            if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
                saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_PARCEL_FEO;
            } else {
                saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_PARCEL_SEO;
            }
        %>

        function validate() {
            var returnValue = true;
            //$("#mapsheetno").toggle("error=off");
            //$("#surveyDate").toggle("error=off");
            if ($("#mapsheetno").val() === "") {
                returnValue = false;
                //$("#mapsheetno").toggle("error");
            }
            if ($("#surveyDate").val() === "") {
                returnValue = false;
                //$("#surveyDate").toggle("error");
            }
            showMessage("Validation");
            return returnValue;
        }
        function save() {
            $.ajax({
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
                    "hasDispute": $("#hasDispute").val()
                },
                error: showajaxerror,
                success: loadForward
            });
        }
        $(function() {
            $("#addParcelForm select").each(function() {
                $(this).val($(this).attr("value"));
            });
            $("#addParcelForm").submit(function() {
                if (validate()) {
                    if ($("#certificateNumber").val() === "" && $("#holdingNumber").val() === "") {
                        alert("Are you sure both Certificate Number and Holding Number are absent");
                    }
                    save();
                }
                return false;
            });
            $("#backButton").click(function() {
                bootbox.confirm("Are you sure you want to go back?", function(result) {
                    if (result) {
                        $.ajax({
                            url: "<%=request.getContextPath()%>/Index?action=<%=Constants.ACTION_WELCOME_PART%>",
                                                    error: showajaxerror,
                                                    success: loadBackward
                                                });
                                            }
                                        });
                                        return false;
                                    });
                                });
    </script>
</div>