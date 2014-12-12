<%-- 
    Document   : addDispute
    Created on : Dec 7, 2014, 11:19:54 PM
    Author     : Yoseph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MASSREG - Individual Holders List</title>
    </head>
    <body>
        <section id="container">
            <div id="holdersList">
                <h3>Parcel: UPI -  3485/3458/735/4398545</h3>
                <table style="border-width: 1px; border-style: solid;" >
                    <tr>
                        <th>Name</th>
                        <th>Date of Birth</th>
                        <th>Sex</th>
                        <th>Family Role</th>
                    </tr>
                    <tr>
                        <td>Some Name</td>
                        <td>23-12-1990</td>
                        <td>Male</td>
                        <td>Husband</td>
                    </tr>
                    <tr>
                        <td>Some Name</td>
                        <td>23-12-1992</td>
                        <td>Female</td>
                        <td>Wife</td>
                    </tr>
                </table>

            </div>
            <div>
                <label for="familySize">Family Size:</label>
                <input type="text" name="familySize" id="familySize" size="3" readonly="true"/>
                <div id="crudButtons" style="display: inline;float: right">
                    <button id="editButton" disabled>Edit</button>
                    <button id="deleteButton" disabled>Delete</button>
                    <button id="addButton" >Add</button> 
                </div>
            </div>
            <hr style="width: 90%"/>
            <div id="navButtons" >
                <button id="backButton" name="backButton">Back</button>
                <button id="nextButton" name="nextButton">Next</button>
            </div>
        </section>
    </body>
</html>
