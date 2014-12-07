<%-- 
    Document   : findParcel
    Created on : Dec 7, 2014, 10:33:08 PM
    Author     : Yoseph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>MASSREG - Welcome</title>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <link rel="stylesheet" type="text/css" href="../../assets/css/style.css" />
        <script src="../../assets/js/login.js"></script>
        <!--[if lte IE 7]><style>.main{display:none;} .support-note .note-ie{display:block;}</style><![endif]-->
    </head>
    <body>
        <div class="container" >
            <section class="main">
                <form method = "POST" action="#" class="form-2" >
                    <div id="WoredaInfo">
                        <h1>SNNP Region, Gurage Zone, Meskan Woreda</h1>
                        <input type="hidden" region="woredaId" value="7842/3578/234"/>
                        <label>Kebele:</label>
                        <select id="kebeleID">
                            <option>Keble I</option>
                            <option>Keble II</option>
                            <option>Keble III</option>
                        </select>
                        <label>Parcel #:</label>
                        <input type="number" required="true" id="parcelNumber" name="parceNumber" min="1"/>
                        <label>UPI:</label>
                        <input type="text" readonly="true" required="true" id="parcelNumber" name="parceNumber" min="1"/>
                        <input type="submit" title="Add New Parcel" value="Add" id="addParcel" name="addParcel"/>
                        <input type="submit" title="Find Parcel" value="Find" id="findParcel" name="findParcel"/>                        
                        <br/>
                    </div>
                </form>
            </section>
        </div>
    </body>
</html>
