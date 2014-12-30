<%@page import="org.lift.massreg.util.Constants"%>
<div class="col-lg-8 col-lg-offset-2">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Discrepancies</h2>
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
                            <p style="padding: 2em">Discrepancies exist between your version of data
                                and that of the FEO,please review your entry or
                                submit the parcel for correction by the supervisor</p>
                            <div class="row" style="margin-top: 3em">
                                <button id = "reviewButton" class="btn btn-default col-lg-2 col-lg-offset-6">Review</button>
                                <button id = "submitForCorrectionButton" class="btn btn-default col-lg-3" style="margin-left: 1em" >Submit For Correction</button>
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
            url: '<%=request.getContextPath() %>/Index?action=<%=Constants.ACTION_START_REVIEW_SEO%>'
        });
        return false;
    });
    $("#submitForCorrectionButton").click(function() {
        $.ajax({
            success: loadForward,
            error: showajaxerror,
            type: 'POST',
            url: '<%=request.getContextPath() %>/Index?action=<%=Constants.ACTION_SUBMIT_FOR_CORRECTION_SEO%>'
        });
        return false;
    });
</script>