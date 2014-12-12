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
        <title>MASSREG - Dispute List</title>
    </head>
    <body>
        <section id="container">
            <div id="disputeList">
                <h3>Parcel: UPI -  3485/3458/735/4398545</h3>
                <table style="border-width: 1px; border-style: solid;" >
                    <tr>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Sex</th>
                        <th>Status</th>
                    </tr>
                    <tr>
                        <td>Some Name</td>
                        <td>Boundary</td>
                        <td>Male</td>
                        <td>Court</td>
                    </tr>
                    <tr>
                        <td>Some Name</td>
                        <td>Boundary</td>
                        <td>Female</td>
                        <td>Kebele</td>
                    </tr>
                </table>

            </div>
            <div id="crudButtons">
                <button id="editButton" disabled>Edit</button>
                <button id="deleteButton" disabled>Delete</button>
                <button id="addButton" >Add</button> 
            </div>
            <hr style="width: 90%"/>
            <div id="navButtons">
                <button id="backButton" name="backButton">Back</button>
                <button id="finishButton" name="nextButton">Finish</button>
            </div>
        </section>
    </body>
</html>
