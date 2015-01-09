<%@page import="org.lift.massreg.entity.Parcel"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%
    Parcel cParcel = (Parcel) request.getAttribute("currentParcel");

    String nexturl, editurl, deleteurl,backurl;
    if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
        nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_FEO;
        editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_PARCEL_FEO;
        deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_PARCEL_FEO;
        backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_WELCOME_PART;
    } else {
        nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_SEO;
        editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_PARCEL_SEO;
        deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_PARCEL_SEO;
        backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_WELCOME_PART;
    }
%>
<div class="col-lg-8 col-lg-offset-2">
    <div class="row">
        <div class="col-lg-6 col-lg-offset-3 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;View Parcel Details </h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Parcel: Administrative UPI - ${requestScope.upi} [ <%=cParcel.getHolderCount()%> holder(s)]
                </div>
                <div class="panel-body">
                    <form role="form" action="#" id="addParcelForm">
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label>Certificate Number</label>
                                    <input class="form-control " placeholder="Does Not Exist" id="certificateNumber" name="certificateNumber" value="${requestScope.currentParcel.certificateNumber}" disabled/>
                                </div>
                                <div class="form-group">
                                    <label>Holding Number</label>
                                    <input class="form-control " placeholder="Does Not Exist" id="holdingNumber" name="holdingNumber" value="${requestScope.currentParcel.holdingNumber}" disabled/>
                                </div>                                
                                <div class="form-group">
                                    <label>Other Evidence</label>
                                    <select class="form-control" id="otherEvidence" name="otherEvidence" value="${requestScope.currentParcel.otherEvidence}" disabled>
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
                                    <select class="form-control" id="meansOfAcquisition" name="meansOfAcquisition" value="${requestScope.currentParcel.meansOfAcquisition}" disabled>
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
                                    <select class="form-control" name="acquisitionYear" id = "acquisitionYear" value="${requestScope.currentParcel.acquisitionYear}" disabled>
                                        <%
                                            for (int i = 1963; i <= 2007; i++) {
                                                out.println("<option value = '" + i + "'>" + i + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Orthograph map sheet No.</label>
                                    <input class="form-control " placeholder="Enter Certificate # " id="mapsheetno" name="mapsheetno" required ="true" value="${requestScope.currentParcel.mapSheetNo}" disabled/>
                                </div>
                                <input type="submit" id = "backButton" class="btn btn-default col-lg-3" value="Back" />
                            </div> <!-- /.col-lg-6 (nested) -->
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label>Current Land Use</label>
                                    <select class="form-control" id="currentLandUse" name="currentLandUse" value="${requestScope.currentParcel.currentLandUse}" disabled>
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
                                    <select class="form-control" id="soilFertility" name="soilFertility" value="${requestScope.currentParcel.soilFertility}" disabled>
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
                                    <select class="form-control" id="holdingType" name="holdingType" value="${requestScope.currentParcel.holding}" disabled>
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
                                    <select class="form-control" id="encumbrance" name="encumbrance" value="${requestScope.currentParcel.encumbrance}" disabled>
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
                                    <input class="form-control " placeholder="Select survey date" type="text" id="surveyDate" name="surveyDate" required value="${requestScope.currentParcel.surveyDate}" disabled/>
                                </div>
                                <div class="form-group">
                                    <label>Has Dispute ?:</label>
                                    <select class="form-control" id="hasDispute" name="hasDispute" value="<%= cParcel.hasDispute()%>" disabled>
                                        <option value = 'false'>No</option>
                                        <option value = 'true'>Yes</option>
                                    </select>
                                </div>
                                <div class="row">
                                    <%
                                        if (cParcel.canEdit(CommonStorage.getCurrentUser(request))) {
                                            out.println("<input type='submit' id = 'deleteButton' name = 'deleteButton' class='btn btn-danger col-lg-2 col-lg-offset-3' value='Delete' />");
                                            out.println("<input type='submit' id = 'editButton' name = 'editButton' class='btn btn-default col-lg-2' style='margin-left:1em' value='Edit' />");
                                        } else {
                                            out.println("<span class='col-lg-2 col-lg-offset-6'></span>");
                                        }
                                    %>

                                    <input type="submit" id = "nextButton" name = "nextButton" class="btn btn-default col-lg-3" style="margin-left: 1em" value="Next" />
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
    var calendar = $.calendars.instance("ethiopian","am"); 
    $("#surveyDate").calendarsPicker({calendar: calendar});
    $("#addParcelForm select").each(function() {
        $(this).val($(this).attr("value"));
    });
    $("#editButton").click(function() {
        $.ajax({
            url: "<%=editurl%>",
            error: showajaxerror,
            success: loadInPlace
        });
        return false;
    });
    $("#deleteButton").click(function() {
        bootbox.confirm("Are you sure you want delete this parcel ?", function(result) {
            if (result) {
                $.ajax({
                    url: "<%=deleteurl%>",
                    error: showajaxerror,
                    success: loadBackward
                });
            }
        });
        return false;
    });
    $("#nextButton").click(function() {
        $.ajax({
            url: "<%=nexturl%>",
            error: showajaxerror,
            success: loadForward
        });
        return false;
    });
    $("#backButton").click(function() {
        bootbox.confirm("Are you sure you want to go back?", function(result) {
            if (result) {
                $.ajax({
                    url: "<%=backurl%>",
                    error: showajaxerror,
                    success: loadBackward
                });
            }
        });
        return false;
    });
</script>