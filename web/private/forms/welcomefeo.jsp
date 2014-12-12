<%@include file="../includes/checkDirectAccess.jsp" %>
<%--Welcom Page: For the first entry operator--%>
<div class="col-lg-8 col-lg-offset-2">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome</h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Find a parcel
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-6">
                            <form role="form" action="/MASSREG/index.jsp" method="action">
                                <div class="form-group">
                                    <label>Kebele</label>
                                    <select class = "form-control" id = "kebele" name = "kebele">
                                        <option value = '1'>1</option>
                                        <option value = '2'>2</option>
                                        <option value = '3'>3</option>
                                        <option value = '4'>4</option>
                                        <option value = '5'>5</option>
                                        <option value = '6'>6</option>
                                        <option value = '7'>7</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Parcel #</label>
                                    <input class="form-control " placeholder="Enter parcel # " name = "parcelNo" id = "parcelNo">
                                </div>
                            </form>
                        </div> <!-- /.col-lg-6 (nested) -->
                        <div class="col-lg-6">
                            <form role="form">
                                <div class="form-group">
                                    <label>UPI</label>
                                    <input class="form-control " placeholder="" name = "parcelNo" id = "parcelNo" disabled/>
                                </div>
                                <div class="row" style="margin-top: 3em">
                                    <button type="submit" id = "addButton" class="btn btn-default col-lg-2 col-lg-offset-7">Add</button>
                                    <button type="submit" style="margin-left: 1em" id = "findButton" class="btn btn-default col-lg-2">Find</button>
                                </div>
                            </form>
                        </div> <!-- /.col-lg-6 (nested) -->
                    </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div>
    <script type="text/javascript">
        $(function() {
            $("#submitButton").click(function() {
                // Validate
                $.ajax({
                    url: "<%=request.getContextPath()%>/private/forms/trytable.jsp",
                    success: loadForward
                });
                // Save and Get Response
                // Load Error or Next form
                alert("Validate, save and change form!");
                return false;
            });
        });
    </script>
</div>