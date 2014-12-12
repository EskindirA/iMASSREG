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
        <title>MASSREG - Add Individual Holder</title>
    </head>
    <body>
        <section id="container">
            <form id="addDispute" action="#" method="post">
                <h2>Add Dispute</h2>
                <fieldset>
                    <legend>Parcel: UPI -  3485/3458/735/4398545</legend>
                    <table>
                        <tr>
                            <td>
                                <label for="firstName">First Name :</label>
                            </td>
                            <td>
                                <input type="text" name="firstName" id="firstName" size="30" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="fathersName">Father's Name # :</label>
                            </td>
                            <td>
                                <input type="text" name="fathersName" id="fathersName" size="30" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="grandfathersName">Grandfather's Name # :</label>
                            </td>
                            <td>
                                <input type="text" name="grandfathersName" id="grandfathersName" size="30" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="sex">Sex:</label> 
                            </td>
                            <td> 
                                <input type="radio" name="sex" id="sexMale" size="30" value="male" /> Male
                                <input type="radio" name="sex" id="sexFemale" size="30" value="female" /> Female
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="disputeType">Dispute Type: </label>
                            </td>
                            <td>
                                <select id="disputeType" name="disputeType" >
                                    <option value="1">Boundary</option>
                                    <option value="2">Holding</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="disputeStatus">Dispute Status:</label>
                            </td>
                            <td>
                                <select id="disputeStatus" name="disputeStatus" >
                                    <option value="1">Kebele</option>
                                    <option value="2">Regional Court</option>
                                </select>
                            </td>
                        </tr>
                        <tr class="submit">
                            <td colspan="2">
                                <button type="submit">Cancel</button>
                                <button type="submit">Save</button>
                            </td>
                        </tr>
                    </table>
                </fieldset>	
            </form>	
        </section>
    </body>
</html>
