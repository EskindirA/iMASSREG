package org.lift.massreg.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.entity.Parcel;
import org.lift.massreg.util.*;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class Correction {

    /**
     * Handlers request for getting the welcome page by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void welcomePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Parcel> parcelsInCorrection = MasterRepository.getInstance().getALLParcelsInCorrection();
        request.setAttribute("parcelsInCorrection", parcelsInCorrection);
        request.setAttribute("page", IOC.getPage(Constants.INDEX_WELCOME_SUPERVISOR));
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

    /**
     * Handlers request for getting the welcome form by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void welcomeFrom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_WELCOME_SUPERVISOR));
        rd.forward(request, response);
    }

    /**
     * Handlers request for getting the fix parcel form by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void fixParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel correctionParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), (byte) 3);
        request.setAttribute("currentParcel", correctionParcel);
        // if the parcel does not exit in the database 
        if (!MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString(), (byte) 1)) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The parcel you are trying to fix does not exist in the database.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        } else {
            Parcel feoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), (byte) 1);
            Parcel seoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), (byte) 2);
            request.setAttribute("currentParcelDifference", Parcel.difference(feoParcel, seoParcel));
            RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_INDEX_FIX_PARCEL_SUPERVISOR));
            rd.forward(request, response);
        }
    }
}
