<%-- 
    Document   : login
    Created on : Dec 17, 2014, 11:53:57 AM
    Author     : Yoseph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>MASSREG - Login</title>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/css/style.css" />
        <script src="<%=request.getContextPath()%>/assets/js/login.js"></script>
        <!--[if lte IE 7]><style>.main{display:none;} .support-note .note-ie{display:block;}</style><![endif]-->
    </head>
    <body>
        <div class="container">
            <header>
                <h1>Welcome to <strong>LIFT MASSREG</strong></h1>
                <h2>Please Login to gain access to the system</h2>
                <div class="support-note">
                    <span class="note-ie">Sorry, only modern browsers.</span>
                </div>
            </header>
            <section class="main">
                <form class="loginForm" action="j_security_check" method="POST">
                    <p class="field">
                        <input type="text" name="j_username" placeholder="Username">
                        <i class="icon-user icon-large"></i>
                    </p>
                    <p class="field">
                        <input type="password" name="j_password" placeholder="Password">
                        <i class="icon-lock icon-large"></i>
                    </p>
                    <p class="submit">
                        <button type="submit" name="submit" value="Submit">
                            <i class="icon-arrow-right icon-large"></i></button>
                    </p>
                </form>
            </section>
        </div>
    </body>
</html>
