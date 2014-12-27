package org.lift.massreg.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.lift.massreg.util.*;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class Correction {

    /**
     * Handlers request for getting a single parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void welcomePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // setup request attributes
        request.setAttribute("page", IOC.getPage(Constants.INDEX_WELCOME_SUPERVISOR));
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }
    /**
     * Handlers request for getting the welcome from
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void welcomeFrom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_WELCOME_SUPERVISOR));
        rd.forward(request, response);
    }
}
