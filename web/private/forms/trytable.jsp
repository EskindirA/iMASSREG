<%--Tryout Page: Table--%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header centered">Tables</h1>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"> Holders' List </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                                <tr>
                                    <th>Rendering engine</th>
                                    <th>Browser</th>
                                    <th>Platform(s)</th>
                                    <th>Engine version</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr class="odd gradeA">
                                    <td>Trident</td>
                                    <td>Internet Explorer 5.5</td>
                                    <td>Win 95+</td>
                                    <td class="center">5.5</td>
                                </tr>
                                <tr class="even gradeA">
                                    <td>Trident</td>
                                    <td>Internet Explorer 6</td>
                                    <td>Win 98+</td>
                                    <td class="center">6</td>
                                </tr>
                                <tr class="odd gradeA">
                                    <td>Trident</td>
                                    <td>Internet Explorer 7</td>
                                    <td>Win XP SP2+</td>
                                    <td class="center">7</td>
                                </tr>
                                <tr class="even gradeA">
                                    <td>Trident</td>
                                    <td>AOL browser (AOL desktop)</td>
                                    <td>Win XP</td>
                                    <td class="center">6</td>
                                </tr>
                            </tbody>
                        </table>
                    </div> <!-- /.table-responsive -->
                </div> <!-- /.panel-body -->
                <div class="row">
                    <div class="col-lg-6">
                        <button type="submit" id = "backButton" class="btn btn-default col-lg-2 col-lg-offset-1">Back</button>
                    </div>
                    <div class="col-lg-6">
                        <button type="submit" id = "submitButton" class="btn btn-default col-lg-2 col-lg-offset-9">Save</button>
                    </div>
                </div>

            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div> <!-- /.row -->
</div>
<script>
    $(document).ready(function() {
        $('#dataTables-example').dataTable();
        $("#submitButton").click(function() {
            // Validate
            $.ajax({
                url: "<%=request.getContextPath()%>/private/forms/addDispute.jsp",
                success: loadForward
            });
            // Save and Get Response
            // Load Error or Next form
            return false;
        });

        $("#backButton").click(function() {
            $.ajax({
                url: "<%=request.getContextPath()%>/private/forms/addParcel.jsp",
                success: loadBackward
            });
            return false;
        });
    });

</script>
