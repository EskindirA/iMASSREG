<%@page import="org.lift.massreg.util.*"%>
<div class="col-lg-8 col-lg-offset-2">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4 ">
            <h2 class="page-header"><%=CommonStorage.getText("discrepancies")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                </div>
                <div class="panel-body" id="panelBody">
                    <form role="form" action="#" method="POST" id="commitForm" name="commitForm">
                        <div class="row">
                            <p style="padding: 2em"><%=CommonStorage.getText("discrepancies_exist")%></p>
                            <div class="row" style="margin-top: 3em">
                                <button id = "reviewButton" class="btn btn-default col-lg-2 col-lg-offset-6"><%=CommonStorage.getText("review")%></button>
                                <button id = "submitForCorrectionButton" class="btn btn-default col-lg-3" style="margin-left: 1em" ><%=CommonStorage.getText("submit_for_correction")%></button>
                            </div>
                        </div> <!-- /.row (nested) -->
                    </form>
                </div> <!-- /.panel-body -->
            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div>
</div>
<script type="text/javascript">
    $("#reviewButton").click(function() {
        $.ajax({
            success: loadForward,
            error: showajaxerror,
            type: 'POST',
            url: '<%=request.getContextPath() %>/Index?action=<%=Constants.ACTION_START_REVIEW_MCSEO%>'
        });
        return false;
    });
    $("#submitForCorrectionButton").click(function() {
        $.ajax({
            success: loadForward,
            error: showajaxerror,
            type: 'POST',
            url: '<%=request.getContextPath() %>/Index?action=<%=Constants.ACTION_SUBMIT_FOR_CORRECTION_MCSEO%>'
        });
        return false;
    });
</script>