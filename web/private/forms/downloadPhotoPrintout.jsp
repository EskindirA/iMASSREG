<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.dto.*"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    ArrayList<String> holders = (ArrayList<String>) request.getAttribute("holders");

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Photo List</title>
        <style>
            *{
                box-sizing: border-box;

            }
            @media print
            {    body{
                     padding:2mm !important;
                     margin:2mm !important;
                 }
                 .upload
                 {
                     display: none !important;
                 }
            }
            body{
                width: 100% auto;
                margin:0;
                padding:0;
            }
            div{
                width: 60mm;
                padding:51mm 0mm 0mm 0mm;
                float:left; 
                display:inline; 
                border:solid 5px; 
                margin:0.2mm 0mm;
                text-align: center; 
                font-size: 17pt;
                letter-spacing: 20px;
                font-weight:bold;
                font-family: 'calibri';

            }
            div:after{
                content: attr(data-after);
                color: black; 
            }
        </style>
    </head>
    <body>  
        <%            for (int i = 0; i < holders.size(); i++) {
                out.println("<div>" + holders.get(i) + "</div>");
            }
        %>

    </body>
</html>
