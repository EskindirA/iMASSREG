package org.lift.massreg.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
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
        ArrayList<Parcel> parcelsInCorrection = MasterRepository.getInstance().getALLParcelsInCorrection();
        request.setAttribute("parcelsInCorrection", parcelsInCorrection);
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_WELCOME_SUPERVISOR));
        rd.forward(request, response);
    }

    /**
     * Handlers request for getting the find parcel form by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void findParceForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Parcel> parcelsInCorrection = MasterRepository.getInstance().getALLParcelsInCorrection();
        request.setAttribute("parcelsInCorrection", parcelsInCorrection);
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_WELCOME_SUPERVISOR));
        rd.forward(request, response);
    }

    /**
     * Handlers request for getting the fix parcel form by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void editParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel correctionParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", correctionParcel);
        // if the parcel does not exit in the database 
        if (!MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString(), CommonStorage.getCorrectionStage())) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The parcel you are trying to edit does not exist in the database.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        } else {
            Parcel feoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getFEStage());
            Parcel seoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getSEStage());
            request.setAttribute("currentParcelDifference", Parcel.difference(feoParcel, seoParcel));
            RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_PARCEL_SUPERVISOR));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to update parcel details by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void updateParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel oldParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        Parcel newParcel = new Parcel();
        try {
            newParcel.setStatus(Constants.STATUS[0]);
            newParcel.setStage(oldParcel.getStage());
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
            newParcel.setSurveyDate(request.getParameter("surveyDate"));
            newParcel.hasDispute(Boolean.parseBoolean(request.getParameter("hasDispute")));
            newParcel.setTeamNo(Byte.parseByte(request.getParameter("teamNo")));
            if (newParcel.validateForUpdate()) {
                //Parcel.getLogStatment(oldParcel,newParcel);
                MasterRepository.getInstance().update(oldParcel, newParcel);
                if (newParcel.getHolding() == 1 && oldParcel.getHolding() != 1) {
                    OrganizationHolder.delete(request,oldParcel.getUpi(), oldParcel.getStage());
                }
                if (newParcel.getHolding() != 1 && oldParcel.getHolding() == 1) {
                    ArrayList<IndividualHolder> holders = oldParcel.getIndividualHolders();
                    for (IndividualHolder holder : holders) {
                        holder.remove(request);
                    }
                }
                if (!newParcel.hasDispute() && oldParcel.hasDispute()) {
                    ArrayList<Dispute> disputes = oldParcel.getDisputes();
                    for (Dispute dispute : disputes) {
                        dispute.remove(request);
                    }
                }
                viewHolder(request, response);
            } else {
                // if the parcel fails to validate show error message
                request.getSession().setAttribute("title", "Validation Error");
                request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                request.getSession().setAttribute("returnTitle", "Go back to holder list");
                request.getSession().setAttribute("returnAction", Constants.ACTION_EDIT_PARCEL_SUPERVISOR);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Parcel");
            request.getSession().setAttribute("returnAction", Constants.ACTION_EDIT_PARCEL_SUPERVISOR);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for getting the fix parcel form by the supervisor
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
        } else if (MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString(), CommonStorage.getCorrectionStage())) {
            currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
            request.setAttribute("currentParcel", currentParcel);
        } else {
            currentParcel = null;
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The parcel you are trying to fix does not exist in the database.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
        Parcel correctionParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());

        // if the parcel does exit in the database 
        if (currentParcel != null) {
            Parcel feoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getFEStage());
            Parcel seoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getSEStage());
            request.setAttribute("currentParcelDifference", Parcel.difference(feoParcel, seoParcel));
            RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_PARCEL_SUPERVISOR));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to view parcel holder by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void viewHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
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
     * Handlers request to view list of individual holders by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void individualHolderList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", currentParcel);
        Parcel feoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getFEStage());
        Parcel seoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getSEStage());
        request.setAttribute("currentParcelDifference", Parcel.difference(feoParcel, seoParcel));
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_INDIVIDUAL_HOLDERS_LIST_SUPERVISOR));
        rd.forward(request, response);
    }

    /**
     * Handlers request for getting the edit individual holder form by
     * supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void editIndividualHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", currentParcel);
        // if the parcel does not exit in the database 
        if (!MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString(), CommonStorage.getCorrectionStage())) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The holder you are trying to edit does not exist in the database, use the add button to register it.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_INDIVIDUAL_HOLDER_SUPERVISOR));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to view organization holder by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void viewOrganizationHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_ORGANIZATION_HOLDER_SUPERVISOR));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for viewing an individual holder by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void viewIndividualHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_INDIVIDUAL_HOLDER_SUPERVISOR));
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
     * Handlers request to update individual holder information by the
     * supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void updateIndividualHolder(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(
                request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", currentParcel);

        IndividualHolder oldIndividualHolder = currentParcel.getIndividualHolder(
                request.getParameter("oldHolderId"), currentParcel.getStage(),
                Timestamp.valueOf(request.getParameter("registeredOn")));
        IndividualHolder newIndividualHolder = new IndividualHolder();
        try {
            newIndividualHolder.setDateOfBirth(request.getParameter("dateofbirth"));
            newIndividualHolder.setFamilyRole(Byte.parseByte(request.getParameter("familyrole")));
            newIndividualHolder.setFirstName(request.getParameter("firstname"));
            newIndividualHolder.setFathersName(request.getParameter("fathersname"));
            newIndividualHolder.setGrandFathersName(request.getParameter("grandfathersname"));
            newIndividualHolder.setId(request.getParameter("newHolderId"));

            newIndividualHolder.hasPhysicalImpairment(Boolean.parseBoolean(request.getParameter("physicalImpairment")));
            newIndividualHolder.isOrphan(Boolean.parseBoolean(request.getParameter("isOrphan")));
            newIndividualHolder.setSex(request.getParameter("sex"));
            newIndividualHolder.setUpi(request.getSession().getAttribute("upi").toString());
            newIndividualHolder.setStatus(Constants.STATUS[0]);

            if (newIndividualHolder.validateForUpdate()) {
                //IndividualHolder.getLogStatment(oldIndividualHolder,newIndividualHolder);
                MasterRepository.getInstance().update(oldIndividualHolder, newIndividualHolder);
                viewHolder(request, response);
            } else {
                // if the parcel fails to validate show error message
                request.getSession().setAttribute("title", "Validation Error");
                request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                request.getSession().setAttribute("returnTitle", "Go back to holder list");
                request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_SUPERVISOR);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }

        } catch (NumberFormatException | ServletException | IOException ex) {
            CommonStorage.getLogger().logError(ex.toString());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Parcel");
            request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_PARCEL_SUPERVISOR);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to save a holder by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void saveHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (parcel != null) {
            if (parcel.getHolding() == 1) {
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
     * Handlers request to save an individual holder by the supervisor
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
                ih.hasPhysicalImpairment(Boolean.parseBoolean(request.getParameter("physicalImpairment")));
                ih.isOrphan(Boolean.parseBoolean(request.getParameter("isOrphan")));
                ih.setStage(CommonStorage.getCorrectionStage());
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
                    request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_SUPERVISOR);
                    RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                    rd.forward(request, response);
                }

            } catch (NumberFormatException | ServletException | IOException ex) {
                CommonStorage.getLogger().logError(ex.toString());
                request.getSession().setAttribute("title", "Inrernal Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend");
                request.getSession().setAttribute("returnTitle", "Go back to Parcel");
                request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
        }

    }

    /**
     * Handlers request to save organization holder by the supervisor
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
                oh.setStage(CommonStorage.getCorrectionStage());
                oh.setUpi(request.getSession().getAttribute("upi").toString());
                oh.setStatus(Constants.STATUS[0]);

                if (oh.validateForSave()) {
                    oh.save();

                    if (parcel.hasDispute()) {
                        viewDisputeList(request, response);
                    } else {
                        commit(request, response);
                    }
                } else {
                    // if the parcel fails to validate show error message
                    request.getSession().setAttribute("title", "Validation Error");
                    request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                    request.getSession().setAttribute("returnTitle", "Go back to holder list");
                    request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_SUPERVISOR);
                    RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                    rd.forward(request, response);
                }

            } catch (NumberFormatException | ServletException | IOException ex) {
                CommonStorage.getLogger().logError(ex.toString());
                request.getSession().setAttribute("title", "Inrernal Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend");
                request.getSession().setAttribute("returnTitle", "Go back to Parcel");
                request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
        }
    }

    /**
     * Handlers request to view list of persons with interest on the parcel by
     * the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void personsWithInterestList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (parcel != null) {
            Parcel feoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getFEStage());
            Parcel seoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getSEStage());
            request.setAttribute("currentParcelDifference", Parcel.difference(feoParcel, seoParcel));
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_PERSONS_WITH_INTEREST_LIST_SUPERVISOR));
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
     * Handlers request to finish data entry by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void finishDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            parcel.complete();
            commit(request, response);
        } else {
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to finish data entry by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void finishPersonWithInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            parcel.complete();
            commit(request, response);
        } else {
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request try commit the current parcel to confirmed status
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void commit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().setAttribute("reviewMode", false);
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        currentParcel.commit();
        request.getSession().setAttribute("upi", null);
        request.getSession().setAttribute("parcelNo", null);
        welcomeFrom(request, response);
    }

    /**
     * Handlers request to save a person with interest by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void savePersonWithInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", parcel);
        if (parcel != null) {
            PersonWithInterest pwi = new PersonWithInterest();
            try {
                pwi.setRegisteredOn(Timestamp.from(Instant.now()));
                pwi.setDateOfBirth(request.getParameter("dateofbirth"));
                pwi.setFirstName(request.getParameter("firstname"));
                pwi.setFathersName(request.getParameter("fathersname"));
                pwi.setGrandFathersName(request.getParameter("grandfathersname"));
                pwi.setRegisteredBy(CommonStorage.getCurrentUser(request).getUserId());
                pwi.setSex(request.getParameter("sex"));
                pwi.setStage(CommonStorage.getCorrectionStage());
                pwi.setUpi(request.getSession().getAttribute("upi").toString());
                pwi.setStatus(Constants.STATUS[0]);

                if (pwi.validateForSave()) {
                    pwi.save();
                    personsWithInterestList(request, response);
                } else {
                    // if the parcel fails to validate show error message
                    request.getSession().setAttribute("title", "Validation Error");
                    request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                    request.getSession().setAttribute("returnTitle", "Go back to person with persons with interest list");
                    request.getSession().setAttribute("returnAction", Constants.ACTION_PERSONS_WITH_INTEREST_LIST_SUPERVISOR);
                    RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                    rd.forward(request, response);
                }

            } catch (NumberFormatException | ServletException | IOException ex) {
                CommonStorage.getLogger().logError(ex.toString());
                request.getSession().setAttribute("title", "Inrernal Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend");
                request.getSession().setAttribute("returnTitle", "Go Back To Welcome Page");
                request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
        }

    }

    /**
     * Handlers request for viewing a person with interest by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void viewPersonWithInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_PERSON_WITH_INTEREST_SUPERVISOR));
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
     * Handlers request for getting the edit person with form by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void editPersonWithInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", currentParcel);
        // if the parcel does not exit in the database 
        if (currentParcel == null) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The person with interest you are trying to edit does not exist in the database, use the add button to register it.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_PERSON_WITH_INTEREST_SUPERVISOR));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to update person with interest information by the
     * supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void updatePersonWithInterest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(
                request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", currentParcel);

        PersonWithInterest oldPersonWithInterest = currentParcel.getPersonWithInterest(
                currentParcel.getStage(), Timestamp.valueOf(request.getParameter("registeredOn")));
        PersonWithInterest newPersonWithInterest = new PersonWithInterest();
        try {
            newPersonWithInterest.setDateOfBirth(request.getParameter("dateofbirth"));
            newPersonWithInterest.setFirstName(request.getParameter("firstname"));
            newPersonWithInterest.setFathersName(request.getParameter("fathersname"));
            newPersonWithInterest.setGrandFathersName(request.getParameter("grandfathersname"));
            newPersonWithInterest.setSex(request.getParameter("sex"));
            newPersonWithInterest.setUpi(request.getSession().getAttribute("upi").toString());
            newPersonWithInterest.setStatus(Constants.STATUS[0]);
            if (newPersonWithInterest.validateForUpdate()) {
                //PersonWithInterest.getLogStatment(oldPersonWithInterest,newPersonWithInterest);
                MasterRepository.getInstance().update(oldPersonWithInterest, newPersonWithInterest);
                personsWithInterestList(request, response);
            } else {
                // if the parcel fails to validate show error message
                request.getSession().setAttribute("title", "Validation Error");
                request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                request.getSession().setAttribute("returnTitle", "Go back to persons with interest list");
                request.getSession().setAttribute("returnAction", Constants.ACTION_PERSONS_WITH_INTEREST_LIST_SUPERVISOR);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }

        } catch (NumberFormatException | ServletException | IOException ex) {
            CommonStorage.getLogger().logError(ex.toString());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go Back to Welcome Page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to delete a person with interest by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void deletePersonWithInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (PersonWithInterest.delete(request,Timestamp.valueOf(request.getParameter("registeredOn")), request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage())) {
            personsWithInterestList(request, response);
        } else {
            // if the parcel fails to validate show error message
            request.getSession().setAttribute("title", "Delete Error");
            request.getSession().setAttribute("message", "Sorry, there was a problem deleteing the person with interest");
            request.getSession().setAttribute("returnTitle", "Go back to persons with interest list");
            request.getSession().setAttribute("returnAction", Constants.ACTION_PERSONS_WITH_INTEREST_LIST_SUPERVISOR);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to delete an individual holder by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void deleteIndividualHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (IndividualHolder.delete(request,request.getParameter("holderId"), Timestamp.valueOf(request.getParameter("registeredOn")), request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage())) {
            Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
            request.setAttribute("currentParcel", parcel);
            individualHolderList(request, response);
        } else {
            // if the parcel fails to validate show error message
            request.getSession().setAttribute("title", "Delete Error");
            request.getSession().setAttribute("message", "Sorry, there was a problem deleteing the holder");
            request.getSession().setAttribute("returnTitle", "Go back to dispute list");
            request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_SUPERVISOR);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to view dispute list by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void viewDisputeList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        boolean reviewMode = false;
        Parcel feoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getFEStage());
        Parcel seoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getSEStage());
        request.setAttribute("currentParcelDifference", Parcel.difference(feoParcel, seoParcel));
        request.setAttribute("currentParcel", parcel);
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_DISPUTE_LIST_SUPERVISOR));
        rd.forward(request, response);
    }

    /**
     * Handlers request to save a dispute by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void saveDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (parcel != null) {
            Dispute dispute = new Dispute();
            try {
                dispute.setRegisteredOn(Timestamp.from(Instant.now()));
                dispute.setRegisteredBy(CommonStorage.getCurrentUser(request).getUserId());
                dispute.setStage(CommonStorage.getCorrectionStage());
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
                    request.getSession().setAttribute("returnTitle", "Go back to dispute list");
                    request.getSession().setAttribute("returnAction", Constants.ACTION_DISPUTE_LIST_SUPERVISOR);
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
     * Handlers request for viewing a dispute by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void viewDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_DISPUTE_SUPERVISOR));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the dispute your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for getting the edit dispute form by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void editDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", currentParcel);
        // if the parcel does not exit in the database 
        if (!MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString(), CommonStorage.getCorrectionStage())) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The dispute you are trying to edit does not exist in the database, use the add button to register it.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_DISPUTE_SUPERVISOR));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to update dispute information by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void updateDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", currentParcel);
        // if the parcel does not exit in the database 
        Dispute oldDispute = currentParcel.getDispute(currentParcel.getStage(), Timestamp.valueOf(request.getParameter("registeredOn")));
        Dispute newDispute = new Dispute();
        try {
            newDispute.setRegisteredOn(Timestamp.from(Instant.now()));
            newDispute.setRegisteredBy(CommonStorage.getCurrentUser(request).getUserId());
            newDispute.setStage(CommonStorage.getCorrectionStage());
            newDispute.setUpi(oldDispute.getUpi());
            newDispute.setFirstName(request.getParameter("firstname"));
            newDispute.setFathersName(request.getParameter("fathersname"));
            newDispute.setGrandFathersName(request.getParameter("grandfathersname"));
            newDispute.setDisputeStatus(Byte.parseByte(request.getParameter("disputeStatus")));
            newDispute.setSex(request.getParameter("sex"));
            newDispute.setDisputeType(Byte.parseByte(request.getParameter("disputeType")));
            if (newDispute.validateForUpdate()) {
                //Dispute.getLogStatment(oldDispute,newDispute);
                MasterRepository.getInstance().update(oldDispute, newDispute);
                viewDisputeList(request, response);
            } else {
                // if the parcel fails to validate show error message
                request.getSession().setAttribute("title", "Validation Error");
                request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                request.getSession().setAttribute("returnTitle", "Go back to holder list");
                request.getSession().setAttribute("returnAction", Constants.ACTION_DISPUTE_LIST_SUPERVISOR);
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
    }

    /**
     * Handlers request to delete a dispute by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void deleteDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (Dispute.delete(request,Timestamp.valueOf(request.getParameter("registeredOn")), request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage())) {
            Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
            request.setAttribute("currentParcel", parcel);
            viewDisputeList(request, response);
        } else {
            // if the parcel fails to validate show error message
            request.getSession().setAttribute("title", "Delete Error");
            request.getSession().setAttribute("message", "Sorry, there was a problem deleting the dispute");
            request.getSession().setAttribute("returnTitle", "Go back to dispute list");
            request.getSession().setAttribute("returnAction", Constants.ACTION_DISPUTE_LIST_SUPERVISOR);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to add organization holder by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void addOrganizationHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_ADD_ORGANIZATION_HOLDER_SUPERVISOR));
        rd.forward(request, response);
    }

    /**
     * Handlers request to finish data entry by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void finishOrganizationHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            parcel.complete();
            commit(request, response);
        } else {
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to edit organization holder form by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void editOrganizationHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", parcel);
        if (request.getAttribute("currentParcel") != null) {
            Parcel feoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getFEStage());

            Parcel seoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getSEStage());
            if (feoParcel.getHolding() > 1 && seoParcel.getHolding() > 1) {
                request.setAttribute("currentOrganizationHolderDifference", OrganizationHolder.difference(feoParcel.getOrganizationHolder(), seoParcel.getOrganizationHolder()));
                request.getSession().setAttribute("ignoreReviewMode",false);
            }else{
                request.getSession().setAttribute("ignoreReviewMode",true);
            }
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_ORGANIZATION_HOLDER_SUPERVISOR));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to update organization holder information by the
     * supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void updateOrganizationHolder(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(
                request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", currentParcel);

        try {
            OrganizationHolder oldOrganizationHolder = currentParcel.getOrganizationHolder();
            OrganizationHolder newOrganizationHolder = new OrganizationHolder();
            newOrganizationHolder.setName(request.getParameter("organizationName"));
            newOrganizationHolder.setOrganizationType(Byte.parseByte(request.getParameter("organizationType")));
            newOrganizationHolder.setStage(CommonStorage.getCorrectionStage());
            newOrganizationHolder.setStatus(Constants.STATUS[0]);

            if (newOrganizationHolder.validateForUpdate()) {
                //OrganizationHolder.getLogStatment(oldOrganizationHolder,newOrganizationHolder);
                MasterRepository.getInstance().update(oldOrganizationHolder, newOrganizationHolder);
                viewOrganizationHolder(request, response);
            } else {
                // if the parcel fails to validate show error message
                request.getSession().setAttribute("title", "Validation Error");
                request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                request.getSession().setAttribute("returnTitle", "Go back to holder list");
                request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_SUPERVISOR);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }

        } catch (NumberFormatException | ServletException | IOException ex) {
            CommonStorage.getLogger().logError(ex.toString());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to delete a parcel by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void deleteParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getCorrectionStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") == null) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the parcel your are trying to delete dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
            return;
        }
        ArrayList<PersonWithInterest> allPWI = parcel.getPersonsWithInterest();
        if (allPWI != null) {
            for (int i = 0; i < allPWI.size(); i++) {
                allPWI.get(i).remove(request);
            }
        }
        // Delete all disputes of the parcel, if any
        if (parcel.hasDispute()) {
            ArrayList<Dispute> allDisputes = parcel.getDisputes();
            for (int i = 0; i < allDisputes.size(); i++) {
                allDisputes.get(i).remove(request);
            }
        }
        // Delete all holders of the parcel, if any
        ArrayList<IndividualHolder> allHolders = parcel.getIndividualHolders();
        if (allHolders != null) {
            for (int i = 0; i < allHolders.size(); i++) {
                allHolders.get(i).remove(request);
            }
        }
        if (parcel.getOrganizationHolder() != null) {
            parcel.getOrganizationHolder().remove(request);
        }

        // Delete the parcel
        parcel.remove(request);

        // remove the session and request attributes
        // do not remove the kebele since the operator is probably going to 
        // work on a parcel within the same kebele
        request.getSession().setAttribute("upi", null);
        request.getSession().setAttribute("parcelNo", null);
        request.setAttribute("upi", null);
        request.setAttribute("parcelNo", null);
        request.setAttribute("currentParcel", null);

        // Forward to the welcome page
        welcomeFrom(request, response);
    }

}
