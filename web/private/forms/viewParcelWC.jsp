<%@page import="org.lift.massreg.entity.Dispute"%>
<%@page import="org.lift.massreg.entity.OrganizationHolder"%>
<%@page import="org.lift.massreg.entity.PersonWithInterest"%>
<%@page import="org.lift.massreg.entity.Guardian"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.entity.IndividualHolder"%>
<%@page import="org.lift.massreg.entity.Parcel"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("parcel");
%>
<style type="text/css">
    #basic-parcel-info td{
        padding-left:5px;
        padding-right:4px;
        border:1px solid #b9c9fe; 

    }
    #basic-parcel-info .pd-label{
        font-weight:bold;
        border-left:2px solid #59697e; 
    }
    .pd-label{
        font-weight: bold;
    }
    .warning{
        float: right;
        text-align: right;
        margin-right: 2em;
        font-weight: bolder;
        color: #C40000;
    }
</style>
<!--div class="modal-dialog "-->
<div class="modal-content col-lg-10 col-lg-offset-1">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title"><%=CommonStorage.getText("view_parcel_details")%> - Parcel: <%=currentParcel.getUpi()%>
            <% if (currentParcel.hasDispute()) {%>
            <span class="warning">Has Dispute</span>
            <%}%>
            <% if (currentParcel.hasOrphanHolder()) {%>
            <span class="warning">Has Orphan</span>
            <%}%>
        </h4>
    </div>            <!-- /modal-header -->
    <div class="modal-body">
        <form id="viewParcelForm" name="viewParcelForm" >
            <div class="panel-body">
                <div class="panel panel-green">
                    <div class="panel-heading">
                        <h3 class="panel-title">Basic Info</h3>
                    </div>
                    <div class="panel-body">
                        <table id="basic-parcel-info">
                            <tr>
                                <td class="pd-label">Means of Acquisition:</td>
                                <td><%=currentParcel.getMeansOfAcquisitionText()%></td>
                                <td class="pd-label">Year of Acquisition:</td>
                                <td><%=currentParcel.getAcquisitionYear()%></td>
                                <td class="pd-label">Other Evidence:</td>
                                <td><%=currentParcel.getOtherEvidenceText()%></td>
                                <td class="pd-label">Encumbrance:</td>
                                <td><%=currentParcel.getEncumbranceText()%></td>
                            </tr>
                            <tr>
                                <td class="pd-label">Holding No.:</td>
                                <td><%=currentParcel.getHoldingNumber()%></td>
                                <td class="pd-label">Certificate No:</td>
                                <td><%=currentParcel.getCertificateNumber()%></td>
                                <td class="pd-label">Mapsheet No.:</td>
                                <td><%=currentParcel.getMapSheetNo()%></td>
                                <td class="pd-label">Holding Type</td>
                                <td><%=currentParcel.getHoldingText()%></td>
                            </tr>
                            <tr>
                                <td class="pd-label">Parcel Id:</td>
                                <td><%=currentParcel.getParcelId()%></td>
                                <td class="pd-label">Landuse Type:</td>
                                <td><%=currentParcel.getCurrentLandUseText()%></td>
                                <td class="pd-label">Soil Fertility:</td>
                                <td><%=currentParcel.getSoilFertilityText()%></td>
                                <td class="pd-label">Area:</td>
                                <td><%=currentParcel.getArea()%></td>
                            </tr>
                        </table>
                    </div>
                </div>
                <%if (currentParcel.getHolding() == 1) {%> 
                <div class="panel panel-yellow">
                    <div class="panel-heading">
                        <h3 class="panel-title">Holders</h3>
                    </div>
                    <div class="panel-body"> 
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>Holder Id</th>
                                    <th>Name</th>
                                    <th>Sex</th>
                                    <th>Family Role</th>
                                    <th>Has P.I.</th>
                                    <th>Is orphan</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    ArrayList<IndividualHolder> holders = currentParcel.getIndividualHolders();
                                    for (int i = 0; i < holders.size(); i++) {
                                %>
                                <tr>
                                    <td><%=holders.get(i).getId()%></td>
                                    <td><%=holders.get(i).getFullName()%></td>
                                    <td><%=holders.get(i).getSexText()%></td>
                                    <td><%=holders.get(i).getFamilyRoleText()%></td>
                                    <td><%=holders.get(i).hasPhysicalImpairmentText()%></td>
                                    <td><%=holders.get(i).isOrphanText()%></td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>

                <%if (currentParcel.hasOrphanHolder()) {%>
                <div class="panel panel-yellow">
                    <div class="panel-heading">
                        <h3 class="panel-title">Guardians</h3>
                    </div>
                    <div class="panel-body">
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Sex</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    ArrayList<Guardian> guardians = currentParcel.getGuardians();
                                    for (int i = 0; i < guardians.size(); i++) {
                                %>
                                <tr>
                                    <td><%=guardians.get(i).getFullName()%></td>
                                    <td><%=guardians.get(i).getSexText()%></td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>                           
                <%}
                    ArrayList<PersonWithInterest> personsWithInterest = currentParcel.getPersonsWithInterest();
                    if (personsWithInterest.size() > 0) {
                %>
                <div class="panel panel-yellow">
                    <div class="panel-heading">
                        <h3 class="panel-title">Persons With Interest</h3>
                    </div>
                    <div class="panel-body"> 
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Sex</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (int i = 0; i < personsWithInterest.size(); i++) {
                                %>
                                <tr>
                                    <td><%=personsWithInterest.get(i).getFullName()%></td>
                                    <td><%=personsWithInterest.get(i).getSexText()%></td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>
                    </div>
                </div>
                <%}
                } else {%>
                <div class="panel panel-yellow">
                    <div class="panel-heading">
                        <h3 class="panel-title">Holder</h3>
                    </div>
                    <div class="panel-body"> 
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Type</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    OrganizationHolder holder = currentParcel.getOrganizationHolder();
                                %>
                                <tr>
                                    <td><%=holder.getName()%></td>
                                    <td><%=holder.getOrganizationTypeText()%></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <%}%>
                <%if (currentParcel.hasDispute()) {%>
                <div class="panel panel-red">
                    <div class="panel-heading">
                        <h3 class="panel-title">Dispute</h3>
                    </div>
                    <div class="panel-body"> 
                        <table class="table table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Sex</th>
                                    <th>Dispute Type</th>
                                    <th>Forum Where Dispute is Handled</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    ArrayList<Dispute> disputes = currentParcel.getDisputes();
                                    for (int i = 0; i < disputes.size(); i++) {
                                %>
                                <tr>
                                    <td><%=disputes.get(i).getFullName()%></td>
                                    <td><%=disputes.get(i).getSexText()%></td>
                                    <td><%=disputes.get(i).getDisputeTypeText()%></td>
                                    <td><%=disputes.get(i).getDisputeStatusText()%></td>
                                </tr>
                                <%}%>									</tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <%}%>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <%if (!currentParcel.hasDispute()) {%>
        <input type='submit' id='approveButton' class='btn btn-primary' value = '<%=CommonStorage.getText("approve")%>' />
        <%}%>
        <input type='submit' id='deleteButton' class='btn btn-danger' value = '<%=CommonStorage.getText("delete")%>' />
        <button type="button" class="btn btn-default" data-dismiss="modal"><%=CommonStorage.getText("close")%></button>
    </div> 
</div>
<!--/div-->
<script type="text/javascript">
    var calendar = $.calendars.instance("ethiopian", "am");
    $("#surveyDate").calendarsPicker({calendar: calendar});
    $("#viewParcelForm select").each(function () {
        $(this).val($(this).attr("value"));
    });
    $("#approveButton").click(function () {
        $.ajax({
            url: "<%=request.getContextPath() + "/Index?action=" + Constants.ACTION_APPROVE_WC%>",
            data:{"upi":'<%=currentParcel.getUpi()%>'},
            error: showajaxerror,
            success: loadInPlace
        });
        return false;
    });
    $("#deleteButton").click(function () {
        $.ajax({
            url: "<%=request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_WC%>",
            data:{"upi":'<%=currentParcel.getUpi()%>'},
            error: showajaxerror,
            success: loadInPlace
        });
        return false;
    });
</script>