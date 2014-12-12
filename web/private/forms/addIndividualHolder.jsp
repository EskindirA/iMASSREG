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
            <form id="addIndividualHolder" action="#" method="post">
                <h2>Add Individual Holder</h2>
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
                                <label for="dob">Date of Birth: </label>
                            </td>
                            <td>
                                <input type="date" id="dob" name="dob" required size="30" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="familyRole">Family Role :</label>
                            </td>
                            <td>
                                <select id="familyRole" name="familyRole" >
                                    <option value="1">Husband</option>
                                    <option value="2">Wife</option>
                                    <option value="3">Brother</option>
                                    <option value="4">Sister</option>
                                    <option value="5">Bachelor</option>
                                    <option value="6">Bachelorette</option>
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
