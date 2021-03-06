
<%@page import="org.lift.massreg.entity.Parcel"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");

    String nexturl, editurl, deleteurl, backurl;
    nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_MCO;
    editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_PARCEL_MCO;
    deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_PARCEL_MCO;
    backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_WELCOME_PART;
%>
<div class="col-lg-8 col-lg-offset-2">
    <div class="row">
        <div class="col-lg-6 col-lg-offset-3 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CommonStorage.getText("view_parcel_details")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <%=CommonStorage.getText("parcel")%>: <%=CommonStorage.getText("administrative_upi")+ "-" + request.getParameter("upi") + "[" +currentParcel.getHolderCount()%> <%=CommonStorage.getText("holders")%>]
                </div>
                <div class="panel-body">
                    <form role="form" action="#" id="viewParcelForm">
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="row">
                                    <div class="form-group col-lg-5">
                                        <label><%=CommonStorage.getText("team")%></label>
                                        <select class="form-control" name = "teamNo" id = "teamNo" value="${requestScope.currentParcel.teamNo}" disabled>
                                            <%
                                                int[] teamNumbers = CommonStorage.getTeamNumbers();
                                                for (int i = 0; i < teamNumbers.length; i++) {
                                                    out.println("<option value='" + teamNumbers[i] + "'>"+CommonStorage.getText("team") + " " + teamNumbers[i] + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="form-group col-lg-7">
                                        <label><%=CommonStorage.getText("certificate_number")%></label>
                                        <input class="form-control " id="certificateNumber" name="certificateNumber" value="${requestScope.currentParcel.certificateNumber}" disabled/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-lg-8">
                                        <label><%=CommonStorage.getText("holding_number")%></label>
                                        <input class="form-control " id="holdingNumber" name="holdingNumber" value="${requestScope.currentParcel.holdingNumber}" disabled/>
                                    </div>
                                    <div class="form-group col-lg-4">
                                        <label><%=CommonStorage.getText("holding_lot_number")%></label>
                                        <input class="form-control " id="holdingLotNumber" name="holdingLotNumber" value="${requestScope.currentParcel.holdingLotNumber}" disabled/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("other_evidence")%></label>
                                    <select class="form-control" id="otherEvidence" name="otherEvidence" value="${requestScope.currentParcel.otherEvidence}" disabled>
                                        <%
                                            Option[] otherEvidenceTypes = MasterRepository.getInstance().getAllOtherEvidenceTypes();
                                            for (int i = 0; i < otherEvidenceTypes.length-1; i++) {
                                                out.println("<option value = '" + otherEvidenceTypes[i].getKey() + "'>" + otherEvidenceTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("means_of_acquisition")%></label>
                                    <select class="form-control" id="meansOfAcquisition" name="meansOfAcquisition" value="${requestScope.currentParcel.meansOfAcquisition}" disabled>
                                        <%
                                            Option[] meansOfAcquisitionTypes = MasterRepository.getInstance().getAllMeansOfAcquisitionTypes();
                                            for (int i = 0; i < meansOfAcquisitionTypes.length-1; i++) {
                                                out.println("<option value = '" + meansOfAcquisitionTypes[i].getKey() + "'>" + meansOfAcquisitionTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("acquisition_year")%></label>
                                    <select class="form-control" name="acquisitionYear" id = "acquisitionYear" value="${requestScope.currentParcel.acquisitionYear}" disabled>
                                        <option value="1"><%=CommonStorage.getText("not_available")%></option>
                                        <%
                                            for (int i = 1900; i <= 2007; i++) {
                                                out.println("<option value = '" + i + "'>" + i + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("orthograph_map_sheet_no")%></label>
                                    <input class="form-control " id="mapsheetno" name="mapsheetno" required ="true" value="${requestScope.currentParcel.mapSheetNo}" disabled/>
                                </div>
                                <input type="submit" id = "backButton" class="btn btn-default col-lg-3" value="Back" />
                            </div> <!-- /.col-lg-6 (nested) -->
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("current_land_use")%></label>
                                    <select class="form-control" id="currentLandUse" name="currentLandUse" value="${requestScope.currentParcel.currentLandUse}" disabled>
                                        <%
                                            Option[] currentLandUseTypes = MasterRepository.getInstance().getAllCurrentLandUseTypes();
                                            for (int i = 0; i < currentLandUseTypes.length-1; i++) {
                                                out.println("<option value = '" + currentLandUseTypes[i].getKey() + "'>" + currentLandUseTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("soil_fertility")%></label>
                                    <select class="form-control" id="soilFertility" name="soilFertility" value="${requestScope.currentParcel.soilFertility}" disabled>
                                        <%
                                            Option[] soilFertilityTypes = MasterRepository.getInstance().getAllSoilFertilityTypes();
                                            for (int i = 0; i < soilFertilityTypes.length-1; i++) {
                                                out.println("<option value = '" + soilFertilityTypes[i].getKey() + "'>" + soilFertilityTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("holding_type")%></label>
                                    <select class="form-control" id="holdingType" name="holdingType" value="${requestScope.currentParcel.holding}" disabled>
                                        <%
                                            Option[] holdingTypes = MasterRepository.getInstance().getAllHoldingTypes();
                                            for (int i = 0; i < holdingTypes.length-1; i++) {
                                                out.println("<option value = '" + holdingTypes[i].getKey() + "'>" + holdingTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("encumbrance")%> </label>
                                    <select class="form-control" id="encumbrance" name="encumbrance" value="${requestScope.currentParcel.encumbrance}" disabled>
                                        <%
                                            Option[] encumbranceTypes = MasterRepository.getInstance().getAllEncumbranceTypes();
                                            for (int i = 0; i < encumbranceTypes.length-1; i++) {
                                                out.println("<option value = '" + encumbranceTypes[i].getKey() + "'>" + encumbranceTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("survey_date")%></label>
                                    <input class="form-control" type="text" id="surveyDate" name="surveyDate" required value="${requestScope.currentParcel.surveyDate}" disabled/>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("has_dispute")%> ?:</label>
                                    <select class="form-control" id="hasDispute" name="hasDispute" value="<%= currentParcel.hasDispute()%>" disabled>
                                        <option value = 'false'><%=CommonStorage.getText("no")%></option>
                                        <option value = 'true'><%=CommonStorage.getText("yes")%></option>
                                    </select>
                                </div>
                                <div class="row">
                                    <%
                                        if (currentParcel.canEdit(CommonStorage.getCurrentUser(request))) {
                                            out.println("<input type='submit' id = 'deleteButton' name = 'deleteButton' class='btn btn-danger col-lg-2 col-lg-offset-3' value='" + CommonStorage.getText("delete") + "' />");
                                            out.println("<input type='submit' id = 'editButton' name = 'editButton' class='btn btn-default col-lg-2' style='margin-left:1em' value='" + CommonStorage.getText("edit") + "' />");
                                        } else {
                                            out.println("<span class='col-lg-2 col-lg-offset-6'></span>");
                                        }
                                    %>

                                    <input type="submit" id = "nextButton" name = "nextButton" class="btn btn-default col-lg-3" style="margin-left: 1em" value="<%=CommonStorage.getText("next")%>" />
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
    $("#viewParcelForm select").each(function() {
        $(this).val($(this).attr("value"));
    });
    $("#editButton").click(function() {
        $.ajax({
            url: "<%=editurl%>",
            error: showajaxerror,
            data: {"upi":'<%=request.getParameter("upi")%>'},
            success: loadInPlace
        });
        return false;
    });
    $("#deleteButton").click(function() {
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_delete_this_parcel")%> ?", function(result) {
            if (result) {
                $.ajax({
                    url: "<%=deleteurl%>",
                    data: {"upi":'<%=request.getParameter("upi")%>'},
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
            data: {"upi":'<%=request.getParameter("upi")%>'},
            error: showajaxerror,
            success: loadForward
        });
        return false;
    });
    $("#backButton").click(function() {
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_go_back")%>?", function(result) {
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