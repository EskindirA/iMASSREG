package org.lift.massreg.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import javax.servlet.*;
import javax.servlet.http.*;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.entity.*;
import org.lift.massreg.util.*;

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
        // Remove the currentParcel from request
        request.setAttribute("currentParcel", null);
        if (MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString(), (byte) 1)) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The parcel you are trying to add already exists in the database, use find button to view the parcel details.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        } else {
            request.setAttribute("title", IOC.getTitle(Constants.INDEX_ADD_PARCEL_FEO));
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_ADD_PARCEL_FEO));
            rd.forward(request, response);
        }

    }

    /**
     * Handlers request for getting the edit parcel form by the first entry
     * operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void editParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), (byte) 1);
        request.setAttribute("currentParcel", currentParcel);
        // if the parcel does not exit in the database 
        if (!MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString(), (byte) 1)) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The parcel you are trying to edit does not exist in the database, use the add button to register it.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        } else {
            request.setAttribute("title", IOC.getTitle(Constants.INDEX_EDIT_PARCEL_FEO));
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_PARCEL_FEO));
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
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
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
     * Handlers request for viewing a dispute by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void viewDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), (byte) 1);
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_DISPUTE_FEO));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the disppute your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for viewing an individual holder by the first entry
     * operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void viewIndividualHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), (byte) 1);
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_INDIVIDUAL_HOLDER_FEO));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the holder your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for getting the edit individual holder form by the first
     * entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void editIndividualHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), (byte) 1);
        request.setAttribute("currentParcel", currentParcel);
        // if the parcel does not exit in the database 
        if (!MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString(), (byte) 1)) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The holder you are trying to edit does not exist in the database, use the add button to register it.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        } else {
            request.setAttribute("title", IOC.getTitle(Constants.INDEX_EDIT_INDIVIDUAL_HOLDER_FEO));
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_INDIVIDUAL_HOLDER_FEO));
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
            p.hasDispute(Boolean.parseBoolean(request.getParameter("hasDispute")));
            if (p.validateForSave()) {
                p.saveParcelOnly();
                request.setAttribute("currentParcel", p);
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

        } catch (NumberFormatException | ServletException | IOException ex) {
            CommonStorage.getLogger().logError(ex.toString());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Parcel");
            request.getSession().setAttribute("returnAction", Constants.ACTION_ADD_PARCEL_FEO);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to update parcel details by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void updateParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), (byte) 1);
        Parcel newParcel = new Parcel();
        try {
            newParcel.setStatus(Constants.STATUS[0]);

            newParcel.setParcelId(Integer.parseInt(request.getSession().getAttribute("parcelNo").toString()));
            newParcel.setUpi(request.getSession().getAttribute("upi").toString());
            newParcel.setRegisteredBy(CommonStorage.getCurrentUser(request).getUserId());

            newParcel.setCertificateNumber(request.getParameter("certificateNumber"));
            newParcel.setHoldingNumber(request.getParameter("holdingNumber"));

            newParcel.setMapSheetNo(request.getParameter("mapsheetNumber"));

            newParcel.setAcquisition(Byte.parseByte(request.getParameter("meansOfAcquisition")));
            newParcel.setAcquisitionYear(Integer.parseInt(request.getParameter("acquisitionYear")));
            newParcel.setCurrentLandUse(Byte.parseByte(request.getParameter("currentLandUse")));
            newParcel.setEncumbrance(Byte.parseByte(request.getParameter("encumbrance")));
            newParcel.setHolding(Byte.parseByte(request.getParameter("holdingType")));
            newParcel.setOtherEvidence(Byte.parseByte(request.getParameter("otherEvidence")));
            newParcel.setSoilFertility(Byte.parseByte(request.getParameter("soilFertility")));
            newParcel.setStage(CommonStorage.getFEStage());
            newParcel.setSurveyDate(request.getParameter("surveyDate"));
            newParcel.hasDispute(Boolean.parseBoolean(request.getParameter("hasDispute")));
            /*if (newParcel.validateForUpdate()) {
             newParcel.saveParcelOnly();
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
             */
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Parcel");
            request.getSession().setAttribute("returnAction", Constants.ACTION_ADD_PARCEL_FEO);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
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
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_INDIVIDUAL_HOLDERS_LIST_FEO));
        rd.forward(request, response);
    }

    /**
     * Handlers request to view organization holder by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void viewOrganizationHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), (byte) 1);
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_ORGANIZATION_HOLDERS_FEO));
            rd.forward(request, response);
        } else {
            addOrganizationHolder(request, response);
        }
    }

    /**
     * Handlers request to add organization holder by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void addOrganizationHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_ADD_ORGANIZATION_HOLDERS_FEO));
        rd.forward(request, response);
    }

    /**
     * Handlers request to save a holder by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void saveHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), (byte) 1);
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (parcel != null) {
            if (parcel.getHoldingText().equalsIgnoreCase("INDIVIDUAL")) {
                saveIndividualHolder(request, response);
            } else {
                saveOrganizationHolder(request, response);
            }
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
     * Handlers request to save a dispute by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void saveDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), (byte) 1);
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (parcel != null) {
            Dispute dispute = new Dispute();
            try {
                dispute.setRegisteredOn(Timestamp.from(Instant.now()));
                dispute.setRegisteredBy(CommonStorage.getCurrentUser(request).getUserId());
                dispute.setStage(CommonStorage.getFEStage());
                dispute.setUpi(request.getSession().getAttribute("upi").toString());

                dispute.setFirstName(request.getParameter("firstname"));
                dispute.setFathersName(request.getParameter("fathersname"));
                dispute.setGrandFathersName(request.getParameter("grandfathersname"));
                dispute.setSex(request.getParameter("sex"));
                dispute.setDisputeStatus(Byte.parseByte(request.getParameter("disputeStatus")));
                dispute.setDisputeType(Byte.parseByte(request.getParameter("disputeType")));
                if (dispute.validateForSave()) {
                    dispute.save();
                    viewDisputeList(request, response);
                } else {
                    // if the parcel fails to validate show error message
                    request.getSession().setAttribute("title", "Validation Error");
                    request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                    request.getSession().setAttribute("returnTitle", "Go back to holder list");
                    request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_FEO);
                    RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                    rd.forward(request, response);
                }

            } catch (NumberFormatException | ServletException | IOException ex) {
                CommonStorage.getLogger().logError(ex.toString());
                request.getSession().setAttribute("title", "Inrernal Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend");
                request.getSession().setAttribute("returnTitle", "Go back to wlecome page");
                request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
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
     * Handlers request to save an individual holder by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void saveIndividualHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = (Parcel) request.getAttribute("currentParcel");
        if (parcel != null) {
            IndividualHolder ih = new IndividualHolder();
            try {
                ih.setRegisteredOn(Timestamp.from(Instant.now()));

                ih.setDateOfBirth(request.getParameter("dateofbirth"));
                ih.setFamilyRole(Byte.parseByte(request.getParameter("familyrole")));
                ih.setFirstName(request.getParameter("firstname"));
                ih.setFathersName(request.getParameter("fathersname"));
                ih.setGrandFathersName(request.getParameter("grandfathersname"));
                ih.setId(request.getParameter("holderId"));
                ih.setRegisteredBy(CommonStorage.getCurrentUser(request).getUserId());
                ih.setSex(request.getParameter("sex"));
                ih.setStage(CommonStorage.getFEStage());
                ih.setUpi(request.getSession().getAttribute("upi").toString());
                ih.setStatus(Constants.STATUS[0]);

                if (ih.validateForSave()) {
                    ih.save();
                    viewHolder(request, response);
                } else {
                    // if the parcel fails to validate show error message
                    request.getSession().setAttribute("title", "Validation Error");
                    request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                    request.getSession().setAttribute("returnTitle", "Go back to holder list");
                    request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_FEO);
                    RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                    rd.forward(request, response);
                }

            } catch (NumberFormatException | ServletException | IOException ex) {
                CommonStorage.getLogger().logError(ex.toString());
                request.getSession().setAttribute("title", "Inrernal Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend");
                request.getSession().setAttribute("returnTitle", "Go back to Parcel");
                request.getSession().setAttribute("returnAction", Constants.ACTION_ADD_PARCEL_FEO);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
        }

    }

    /**
     * Handlers request to save organization holder by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void saveOrganizationHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = (Parcel) request.getAttribute("currentParcel");
        if (parcel != null) {

            OrganizationHolder oh = new OrganizationHolder();
            try {
                oh.setRegisteredon(Timestamp.from(Instant.now()));
                oh.setName(request.getParameter("organizationName"));
                oh.setOrganizationType(Byte.parseByte(request.getParameter("organizationType")));
                oh.setRegisteredby(CommonStorage.getCurrentUser(request).getUserId());
                oh.setStage(CommonStorage.getFEStage());
                oh.setUpi(request.getSession().getAttribute("upi").toString());
                oh.setStatus(Constants.STATUS[0]);

                if (oh.validateForSave()) {
                    oh.save();

                    if (parcel.hasDispute()) {
                        viewDisputeList(request, response);
                    } else {
                        welcomeFrom(request, response);
                    }
                } else {
                    // if the parcel fails to validate show error message
                    request.getSession().setAttribute("title", "Validation Error");
                    request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                    request.getSession().setAttribute("returnTitle", "Go back to holder list");
                    request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_FEO);
                    RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                    rd.forward(request, response);
                }

            } catch (NumberFormatException | ServletException | IOException ex) {
                CommonStorage.getLogger().logError(ex.toString());
                request.getSession().setAttribute("title", "Inrernal Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend");
                request.getSession().setAttribute("returnTitle", "Go back to Parcel");
                request.getSession().setAttribute("returnAction", Constants.ACTION_ADD_PARCEL_FEO);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
        }
    }

    /**
     * Handlers request to view parcel holder by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void viewHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), (byte) 1);
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        parcel = (Parcel) request.getAttribute("currentParcel");
        if (parcel != null) {
            if (parcel.getHolding() == 1) {
                individualHolderList(request, response);
            } else if (parcel.getHolderCount() < 1) {
                addOrganizationHolder(request, response);
            } else {
                viewOrganizationHolder(request, response);
            }
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
     * Handlers request to view parcel holder by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void viewDisputeList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), (byte) 1);
        request.setAttribute("currentParcel", parcel);
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_DISPUTE_LIST_FEO));
        rd.forward(request, response);
    }
}
