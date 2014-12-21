package org.lift.massreg.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.lift.massreg.util.*;

/**
 * Controller class to handle request common for all levels
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class All {

    /**
     * Handlers requests that were not recognized
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void goBackToHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().setAttribute("title", "Not Recognized");
        request.getSession().setAttribute("message", "Sorry, You'r request could not be recognized");
        request.getSession().setAttribute("returnTitle", "Go back to home page");
        request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
        rd.forward(request, response);
    }
    /**
     * Handlers requests that were not recognized
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void showError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_ERROR));
        rd.forward(request, response);
    }    
}
