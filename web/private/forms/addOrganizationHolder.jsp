<%--Tryout Page: Table--%>
<div class="col-lg-5 col-lg-offset-4">
    <div class="row">
        <div class="col-lg-7 col-lg-offset-2 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;Register Holder</h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Parcel: Administrative UPI - 3485/3458/735/4398545
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form role="form" action="/MASSREG/index.jsp" method="action">
                                <div class="form-group">
                                    <label>Name</label>
                                    <input class="form-control " placeholder="Name of the holding organization">
                                </div>
                                <div class="form-group">
                                    <label>Type</label>
                                    <select class="form-control">
                                        <option value = '0'>None</option>
                                        <option value = '1'>Tax</option>
                                        <option value = '2'>Other</option>
                                    </select>
                                </div>
                                <button type="submit" id = "submitButton" class="btn btn-default col-lg-3 col-lg-offset-9">Save</button>
                            </form>
                        </div>
                        <!-- /.col-lg-6 (nested) -->
                    </div>
                    <!-- /.row (nested) -->
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
</div>
<script type="text/javascript">
    $(function() {
        $("#submitButton").click(function() {
            alert("Validate, save and change form!");
            return false;
        });
    });
</script>