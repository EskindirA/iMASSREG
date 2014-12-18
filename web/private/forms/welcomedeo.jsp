<%@page import="org.lift.massreg.util.CommonStorage"%>
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
                            <form role="form" action="/MASSREG/index.jsp" method="POST">
                                <input type="hidden" name="WoredaId" id="WoredaId" value="1/2/3/4"/>
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
                                    <input class="form-control " placeholder="Enter parcel # " name = "parcelNo" id = "parcelNo" type="type" size="5" maxlength="5" max="99999">
                                </div>
                            </form>
                        </div> <!-- /.col-lg-6 (nested) -->
                        <div class="col-lg-6">
                            <form role="form">
                                <div class="form-group">
                                    <label>Administrative UPI</label>
                                    <input class="form-control " placeholder="" name = "upi" id = "upi" disabled required/>
                                </div>
                                <div class="row" style="margin-top: 3em">
                                    <button id = "addButton" class="btn btn-default col-lg-2 col-lg-offset-7">Add</button>
                                    <button id = "findButton" class="btn btn-default col-lg-2" style="margin-left: 1em" >Find</button>
                                </div>
                            </form>
                        </div> <!-- /.col-lg-6 (nested) -->
                    </div> <!-- /.row (nested) -->
                </div> <!-- /.panel-body -->
            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div>
    <script type="text/javascript">
        <%            String url;
            if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
                url = request.getContextPath() + "/Index?action=" + Constants.ACTION_ADD_PARCEL_FEO;
            } else {
                url = request.getContextPath() + "/Index?action=" + Constants.ACTION_ADD_PARCEL_SEO;
            }%>
        // change this to loadAddParcel and then it should intern call load forward

        $(function() {
            $("#addButton").click(function() {
                if ($("#upi").val() === "") {
                    alert("Administrative UPI is required ");
                    return false;
                }
                //if(No validation error exists){
                // Call to the service to get the add parcel form
                $.ajax({
                    success: loadForward,
                    type: 'POST',
                    data: {"upi": $("#upi").val()},
                    url: '<%=url%>'
                });
                return false;
            });
            $("#findButton").click(function() {
                alert("Validate to find ");
                //if(No validation error exists){
                // Call to the service to get the view parcel form
                $.ajax({
                    // "<=request.getContextPath()>/Index",
                    url: "<%=request.getContextPath()%>/private/forms/trytable.jsp",
                    // change this to loadAddParcel and then it should intern call load forward
                    success: loadForward
                });
                // Else show error message
                // }
                // else  show validation error
                // Load Error or Next form
                return false;
            });
            $("#kebele").change(function() {
                updateUPI();
            });
            $("#parcelNo").keyup(function() {
                updateUPI();
            });
        });
        function updateUPI() {
            // Pad the upi with leading zeros
            var parcelNo = "" + $("#parcelNo").val();
            var pad = "00000";
            var value = pad.substring(0, pad.length - parcelNo.length) + parcelNo;
            $("#upi").val($("#WoredaId").val() + "/" + $("#kebele").val() + "/" + value);
        }
    </script>
</div>