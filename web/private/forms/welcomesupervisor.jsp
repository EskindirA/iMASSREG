<%@include file="../includes/checkDirectAccess.jsp" %>
<%--Welcom Page: For the first entry operator--%>
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
                                    <th>UPI</th>
                                    <th>First Entry By</th>
                                    <th>Second Entry By</th>
                                    <th>Discrepancies in Dispute</th>
                                    <th>Discrepancies in Holder</th>
                                    <th>Fix</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr class="odd gradeA">
                                    <td>Trident</td>
                                    <td>Internet Explorer 5.5</td>
                                    <td>Win 95+</td>
                                    <td class="center">Yes</td>
                                    <td class="center">No</td>
                                    <td><a data-upi=""><img src="<%=request.getContextPath()%>/assets/images/edit-btn.png" class="edit-btn"/></td>
                                </tr>
                                <tr class="even gradeA">
                                    <td>Trident</td>
                                    <td>Internet Explorer 6</td>
                                    <td>Win 98+</td>
                                    <td class="center">Yes</td>
                                    <td class="center">Yes</td>
                                    <td><a data-upi=""><img src="<%=request.getContextPath()%>/assets/images/edit-btn.png" class="edit-btn"/></td>
                                </tr>
                                <tr class="odd gradeA">
                                    <td>Trident</td>
                                    <td>Internet Explorer 7</td>
                                    <td>Internet Explorer 7</td>
                                    <td class="center">Yes</td>
                                    <td class="center">No</td>
                                    <td><a data-upi=""><img src="<%=request.getContextPath()%>/assets/images/edit-btn.png" class="edit-btn"/></td>
                                </tr>
                                <tr class="even gradeA">
                                    <td>Trident</td>
                                    <td>AOL browser (AOL desktop)</td>
                                    <td>Win XP</td>
                                    <td class="center">No</td>
                                    <td class="center">Yes</td>
                                    <td><a data-upi=""><img src="<%=request.getContextPath()%>/assets/images/edit-btn.png" class="edit-btn"/></td>
                                </tr>
                            </tbody>
                        </table>
                    </div> <!-- /.table-responsive -->
                </div> <!-- /.panel-body -->
            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div> <!-- /.row -->
</div>
<script>
    $(document).ready(function() {
        $('#dataTables').dataTable();
        $("#backButton").click(function() {
            $.ajax({
                url: "<%=request.getContextPath()%>/Index?action=welcome",
                success: loadBackward
            });
            return false;
        });
    });
</script>