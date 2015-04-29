package org.lift.massreg.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.entity.*;
import org.lift.massreg.util.*;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class WoredaCorrdinator {

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
        ArrayList<Parcel> parcels = MasterRepository.getInstance().getALLParcels(request.getSession().getAttribute("kebele").toString(), request.getSession().getAttribute("status").toString());
        request.setAttribute("parcels", parcels);
        request.setAttribute("page", IOC.getPage(Constants.INDEX_WELCOME_WC));
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
        ArrayList<Parcel> parcels = MasterRepository.getInstance().getALLParcels(request.getSession().getAttribute("kebele").toString(), request.getSession().getAttribute("status").toString());
        request.setAttribute("parcels", parcels);
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_WELCOME_WC));
        rd.forward(request, response);
    }
    
    /**
     * Handlers request for getting the view pacel form
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void viewParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel  parcel = MasterRepository.getInstance().getParcel(request.getParameter("upi"),CommonStorage.getConfirmedStage());
        request.setAttribute("parcel", parcel);
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_PARCEL_WC));
        rd.forward(request, response);
    }

    /**
     * Handlers request to approve a parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void approveParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel  parcel = MasterRepository.getInstance().getParcel(request.getParameter("upi"),CommonStorage.getConfirmedStage());
        parcel.approve();
        welcomeForm(request, response);
    }

}
