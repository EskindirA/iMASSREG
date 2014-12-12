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
        <title>MASSREG - View Parcel</title>
    </head>
    <body>
        <section id="container">
            <form id="viewParcel" action="#" method="post">	
                <fieldset>
                    <legend>Parcel: UPI -  3485/3458/735/4398545</legend>
                    <table>
                        <tr>
                            <td>
                                <label for="stage">Stage :</label>
                            </td>
                            <td>
                                <input type="text" name="stage" id="stage" size="30" disabled/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="certificateNo">Certificate # :</label>
                            </td>
                            <td>
                                <input type="text" name="certificateNo" id="certificateNo" size="30" disabled/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="holdingNo">Holding # :</label>
                            </td>
                            <td>
                                <input type="text" name="holdingNo" id="holdingNo" size="30" disabled/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="otherEvidence">Other Evidence :</label>
                            </td>
                            <td>
                                <select id="otherEvidence" name="otherEvidence" disabled>
                                    <option value="1">None</option>
                                    <option value="2">Tax</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="acquisitionYear">Acquisition Year :</label>
                            </td>
                            <td>
                                <select id="acquisitionYear" name="acquisitionYear" disabled>
                                    <option value="1900">1900</option>
                                    <option value="1901">1901</option>
                                    <option value="1902">1902</option>
                                    <option value="1903">1903</option>
                                    <option value="1904">1904</option>
                                    <option value="1905">1905</option>
                                    <option value="1906">1906</option>
                                    <option value="1907">1907</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="surveyDate">Survey Date: </label>
                            </td>
                            <td>
                                <input type="date" id="surveyDate" name="surveyDate" disabled/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="mapsheetNumber">Orthograph sheet #: </label>
                            </td>
                            <td>
                                <input type="text" id="mapsheetNumber" name="mapsheetNumber" disabled />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="landUse">Current Land Use :</label>
                            </td>
                            <td>
                                <select id="landUse" name="landUse" disabled >
                                    <option value="1">None</option>
                                    <option value="2">Forest</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="soilFertility">Soil Fertility :</label>
                            </td>
                            <td>
                                <select id="soilFertility" name="soilFertility" disabled >
                                    <option value="1">High</option>
                                    <option value="2">Medium</option>
                                    <option value="3">Low</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="holdingType">Holding Type :</label>
                            </td>
                            <td>
                                <select id="holdingType" name="holdingType" disabled >
                                    <option value="1">Individual</option>
                                    <option value="2">Communal</option>
                                    <option value="3">Public</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="acquisition">Acquisition :</label>
                            </td>
                            <td>
                                <select id="acquisition" name="acquisition" disabled >
                                    <option value="1">Redistribution</option>
                                    <option value="2">Inheritance</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="encumbrance">Encumbrance :</label>
                            </td>
                            <td>
                                <select id="encumbrance" name="encumbrance" disabled >
                                    <option value="1">None</option>
                                    <option value="2">Right of Use</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="disputeCount">Dispute Count:</label> 
                            </td>
                            <td> 
                                <input type="text" id="disputeCount" name="disputeCount" disabled />
                                <button name="viewDisputesButton" id="viewDisputesButton" >view</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="holderCount">Holder Count:</label> 
                            </td>
                            <td> 
                                <input type="text" id="holderCount" name="holderCount" disabled />
                                <button name="viewHoldersButton" id="viewHoldersButton" >view</button>
                            </td>
                        </tr>
                        <tr class="submit">
                            <td colspan="2">
                                <button type="submit" name="editButton" id="editButton" disabled>Edit</button>
                            </td>
                        </tr>
                    </table>
                </fieldset>	
            </form>	
        </section>
    </body>
</html>
