package org.lift.massreg.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
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
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public class FirstEntry {

    /**
     * Handlers request for getting the welcome page
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void welcomePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // setup request attributes
        request.setAttribute("page", IOC.getPage(Constants.INDEX_FIRST_ENTRY_WELCOME));
        request.setAttribute("title", IOC.getTitle(Constants.INDEX_FIRST_ENTRY_WELCOME));
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
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_FIRST_ENTRY_WELCOME));
        rd.forward(request, response);
    }

    /**
     * Handlers request for getting the add parcel form by the first entry
     * operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void addParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("currentParcel") != null) {
            request.setAttribute("currentParcel", request.getSession().getAttribute("currentParcel"));
        }
        if (MasterRepository.getInstance().parcelExists(request.getSession().getAttribute("upi").toString(), (byte) 1)) {
            request.setAttribute("title", IOC.getTitle(Constants.INDEX_VIEW_PARCEL_FEO));
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_PARCEL_FEO));
            rd.forward(request, response);
        } else {
            request.setAttribute("title", IOC.getTitle(Constants.INDEX_ADD_PARCEL_FEO));
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_ADD_PARCEL_FEO));
            rd.forward(request, response);
        }

    }

    /**
     * Handlers request for viewing a parcel by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void viewParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), (byte) 1);
        request.getSession().setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getSession().getAttribute("currentParcel") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_PARCEL_FEO));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the parcel your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }

    }

    /**
     * Handlers request to save parcel form by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void saveParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Parcel p = new Parcel();
        try {
            p.setRegisteredOn(Timestamp.from(Instant.now()));
            p.setStatus(Constants.STATUS[0]);

            p.setParcelId(Integer.parseInt(request.getSession().getAttribute("parcelNo").toString()));
            p.setUpi(request.getSession().getAttribute("upi").toString());
            p.setRegisteredBy(CommonStorage.getCurrentUser(request).getUserId());

            p.setCertificateNumber(request.getParameter("certificateNumber"));
            p.setHoldingNumber(request.getParameter("holdingNumber"));

            p.setMapSheetNo(request.getParameter("mapsheetNumber"));

            p.setAcquisition(Byte.parseByte(request.getParameter("meansOfAcquisition")));
            p.setAcquisitionYear(Integer.parseInt(request.getParameter("acquisitionYear")));
            p.setCurrentLandUse(Byte.parseByte(request.getParameter("currentLandUse")));
            p.setEncumbrance(Byte.parseByte(request.getParameter("encumbrance")));
            p.setHolding(Byte.parseByte(request.getParameter("holdingType")));
            p.setOtherEvidence(Byte.parseByte(request.getParameter("otherEvidence")));
            p.setSoilFertility(Byte.parseByte(request.getParameter("soilFertility")));
            p.setStage(CommonStorage.getFEStage());
            p.setSurveyDate(request.getParameter("surveyDate"));
            p.setHasDispute(Boolean.parseBoolean(request.getParameter("hasDispute")));
            if (p.validateForSave()) {
                p.saveParcelOnly();
                request.getSession().setAttribute("currentParcel", p);
                // Check the holding type of the current Parcel if it is 
                // indvidual forwared to indvidual holders list
                if (p.getHoldingText().equalsIgnoreCase("INDIVIDUAL")) {
                    individualHolderList(request, response);
                } else {
                    addOrganizationHolder(request, response);
                }
            } else {
                // if the parcel fails to validate show error message
                request.getSession().setAttribute("title", "Validation Error");
                request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                request.getSession().setAttribute("returnTitle", "Go back to Parcel");
                request.getSession().setAttribute("returnAction", Constants.ACTION_ADD_PARCEL_FEO);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }

        } catch (Exception ex) {
            ///TODO: Forward to error
            CommonStorage.getLogger().logError(ex.toString());
        }
    }

    /**
     * Handlers request to view list of individual holders by the first entry
     * operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void individualHolderList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().setAttribute("title", "Individual Holder List");
        request.getSession().setAttribute("message", "Sorry, You'r request could not be recognized");
        request.getSession().setAttribute("returnTitle", "Go back to view parcel page");
        request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_PARCEL_FEO);
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
        rd.forward(request, response);
    }

    /**
     * Handlers request to add organization holder by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void addOrganizationHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().setAttribute("title", "Add Organization Holder");
        request.getSession().setAttribute("message", "Sorry, You'r request could not be recognized");
        request.getSession().setAttribute("returnTitle", "Go back to view parcel page");
        request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_PARCEL_FEO);
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
        rd.forward(request, response);
    }
}
