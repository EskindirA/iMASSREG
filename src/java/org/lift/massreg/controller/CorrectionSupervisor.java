package org.lift.massreg.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.lift.massreg.dao.*;
import org.lift.massreg.entity.*;
import org.lift.massreg.util.*;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class CorrectionSupervisor {

    /**
     * Handlers request for getting the welcome page
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void welcomePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Parcel> parcelsInCorrection = MasterRepository.getInstance().getALLParcelsInMajorCorrectionSupervisor();
        request.setAttribute("parcelsInCorrection", parcelsInCorrection);
        request.setAttribute("page", IOC.getPage(Constants.INDEX_WELCOME_MCSUPERVISOR));
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

    /**
     * Handlers request for getting the welcome form
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void welcomeForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Parcel> parcelsInCorrection = MasterRepository.getInstance().getALLParcelsInMajorCorrectionSupervisor();
        request.setAttribute("parcelsInCorrection", parcelsInCorrection);
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_WELCOME_MCSUPERVISOR));
        rd.forward(request, response);
    }

}
