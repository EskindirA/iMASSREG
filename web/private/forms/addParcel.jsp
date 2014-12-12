<%--Tryout Page: Table--%>
<div class="col-lg-8 col-lg-offset-2">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4 ">
            <h2 class="page-header">&nbsp;&nbsp;Add new parcel</h2>
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
                        <div class="col-lg-6">
                            <form role="form" action="/MASSREG/index.jsp" method="action">
                                <div class="form-group">
                                    <label>Certificate #</label>
                                    <input class="form-control " placeholder="Leave empty if certificate number does not exist">
                                </div>
                                <div class="form-group">
                                    <label>Has Holding #	</label>
                                    <input class="form-control " placeholder="Leave empty if holding number does not exist">
                                </div>                                
                                <div class="form-group">
                                    <label>Other Evidence</label>
                                    <select class="form-control">
                                        <option value = '0'>None</option>
                                        <option value = '1'>Tax</option>
                                        <option value = '2'>Other</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Acquisition Year*</label>
                                    <select class="form-control">
                                        <option value = '1900'>1900</option>
                                        <option value = '1901'>1901</option>
                                        <option value = '1902'>1902</option>
                                        <option value = '1903'>1903</option>
                                        <option value = '1904'>1904</option>
                                        <option value = '1905'>1905</option>
                                        <option value = '1906'>1906</option>
                                        <option value = '1907'>1907</option>
                                        <option value = '1908'>1908</option>
                                        <option value = '1909' selected>1909</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Survey Date *</label>
                                    <input class="form-control " placeholder="Select survey date" type="date">
                                </div>
                                <div class="form-group">
                                    <label>Orthograph sheet No.</label>
                                    <input class="form-control " placeholder="Enter Certificate # ">
                                </div>
                            </form>
                        </div> <!-- /.col-lg-6 (nested) -->
                        <div class="col-lg-6">
                            <form role="form">
                                <div class="form-group">
                                    <label>Current Land Use</label>
                                    <select class="form-control">
                                        <option>1</option>
                                        <option>2</option>
                                        <option>3</option>
                                        <option>4</option>
                                        <option>5</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Soil Fertility *</label>
                                    <select class="form-control">
                                        <option>1</option>
                                        <option>2</option>
                                        <option>3</option>
                                        <option>4</option>
                                        <option>5</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Holding Type</label>
                                    <select class="form-control">
                                        <option>1</option>
                                        <option>2</option>
                                        <option>3</option>
                                        <option>4</option>
                                        <option>5</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Acquisition </label>
                                    <select class="form-control">
                                        <option>1</option>
                                        <option>2</option>
                                        <option>3</option>
                                        <option>4</option>
                                        <option>5</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Encumbrance </label>
                                    <select class="form-control">
                                        <option>1</option>
                                        <option>2</option>
                                        <option>3</option>
                                        <option>4</option>
                                        <option>5</option>
                                    </select>
                                </div>
                                <br /><br />
                                <div class="form-group">
                                    <label>Has Dispute ?:</label>
                                    <label class="radio-inline">
                                        <input type="radio" name="hasDispute" id="hasDisputeYes" value="yes" >Yes
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="hasDispute" id="hasDisputeNo" value="no" checked>No
                                    </label>
                                </div>
                                <button type="submit" id = "submitButton" class="btn btn-default col-lg-3 col-lg-offset-9">Save</button>
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