package org.lift.massreg.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.entity.Parcel;
import org.lift.massreg.util.CommonStorage;
import org.lift.massreg.util.Constants;
import org.lift.massreg.util.IOC;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class PostPDCoordinator {
 
     /**
     * Handlers request for getting the welcome page
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void welcomePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Parcel> parcelsInCorrection = MasterRepository.getInstance().getALLParcelsInCommitted(request.getAttribute("kebele").toString());
        request.setAttribute("parcelsInCommitted", parcelsInCorrection);
        request.setAttribute("page", IOC.getPage(Constants.INDEX_WELCOME_POSTPDCOORDINATOR));
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }
    
    /**
     * Handlers request for getting the welcome form
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void welcomeForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Parcel> parcelsInCorrection = MasterRepository.getInstance().getALLParcelsInCommitted(request.getAttribute("kebele").toString());
        request.setAttribute("parcelsInCommitted", parcelsInCorrection);
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_WELCOME_POSTPDCOORDINATOR));
        rd.forward(request, response);
    }
    
    /**
     * Handlers request for sending a parcel to minor correction
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void sendToMinorCorrection(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getParameter("upi"), CommonStorage.getCommitedStage());
        parcel.submitForMinorCorrection();
        welcomeForm(request, response);
    }

    /**
     * Handlers request for sending a parcel to major correction
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void sendToMajorCorrection(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getParameter("upi"), CommonStorage.getCommitedStage());
        parcel.submitForMajorCorrection();
        welcomeForm(request, response);
    }
    
    /**
     * Handlers request for sending a parcel to confirmed status
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void sendToConfirmed(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getParameter("upi"), CommonStorage.getCommitedStage());
        parcel.submitToConfirmed();
        welcomeForm(request, response);
    }

    
    /**
     * Handlers request for getting a parcel form by the Post-Public Display Coordinator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void viewParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel;
        if (MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString(), CommonStorage.getCommitedStage())) {
            currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCommitedStage());
            request.setAttribute("currentParcel", currentParcel);
            RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_PARCEL_PDC));
            rd.forward(request, response);
        } else {
            currentParcel = null;
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The parcel you are trying to find does not exist in the database.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    
}
