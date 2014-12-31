<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.Parcel"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Parcel> parcelsInCorrection = (ArrayList<Parcel>)request.getAttribute("parcelsInCorrection");
    
    String fixurl;
    //if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.SUPERVISOR) {
        fixurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_FIX_PARCEL_SUPERVISOR;
    //}

%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-3 col-lg-offset-5 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"> Parcels Submitted For Correction </div>
                <div class="panel-body" >
                    <div >
                        <table class="table table-striped table-bordered table-hover" id="dataTables" >
                            <thead>
                                <tr>
                                    <th>Administrative UPI</th>
                                    <th>Certificate Number</th>
                                    <th>Has Dispute</th>
                                    <th>Means of Acquisition</th>
                                    <th>Survey Date</th>
                                    <th>Fix</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (int i = 0; i < parcelsInCorrection.size(); i++) {
                                        if (i % 2 == 0) {
                                            out.println("<tr class='odd gradeA'>");
                                        } else {
                                            out.println("<tr class='even gradeA'>");
                                        }
                                        out.println("<td>" + parcelsInCorrection.get(i).getUpi()+ "</td>");
                                        out.println("<td>" + parcelsInCorrection.get(i).getCertificateNumber()+ "</td>");
                                        out.println("<td>" + parcelsInCorrection.get(i).hasDispute()+ "</td>");
                                        out.println("<td>" + parcelsInCorrection.get(i).getMeansOfAcquisition()+ "</td>");
                                        out.println("<td>" + parcelsInCorrection.get(i).getSurveyDate()+ "</td>");
                                        out.println("<td><a href = '#' class='fixButton' data-upi='"
                                                    + parcelsInCorrection.get(i).getUpi()+ "'>Fix</a></td>");
                                        out.println("</tr>");
                                    }
                                %>

                            </tbody>
                        </table>
                    </div> <!-- /.table-responsive -->
                </div> <!-- /.panel-body -->
            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div> <!-- /.row -->
</div>
<script>
    function fixParcel(upi) {
        $.ajax({
            type:'POST',
            url: "<%=fixurl%>",
            data: {"upi": upi},
            error: showajaxerror,
            success: loadForward
        });
    }

    $('#dataTables').dataTable();
    $('.fixButton').click(function() {
        fixParcel($(this).attr("data-upi"));
        return false;
    });
</script>