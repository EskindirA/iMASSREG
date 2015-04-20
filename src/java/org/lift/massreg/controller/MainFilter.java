package org.lift.massreg.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.lift.massreg.util.*;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
@WebServlet(name = "MainFilter", urlPatterns = {"/Index"})
public class MainFilter extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        String action;
        // Get Request Parameter 
        action = (String) request.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = Constants.ACTION_WELCOME;
        }
        // For all users
        if (action.equalsIgnoreCase(Constants.ACTION_LOGOUT)) {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath());
        } else if (action.equalsIgnoreCase(Constants.ACTION_WELCOME)) {
            switch (CommonStorage.getCurrentUser(request).getRole()) {
                case FIRST_ENTRY_OPERATOR:
                    FirstEntry.welcomePage(request, response);
                    break;
                case SECOND_ENTRY_OPERATOR:
                    SecondEntry.welcomePage(request, response);
                    break;
                case SUPERVISOR:
                    Correction.welcomePage(request, response);
                    break;
                case ADMINISTRATOR:
                    Administrator.welcomePage(request, response);
                    break;
                case POSTPDCOORDINATOR:
                    if (request.getParameter("kebele") != null) {
                        request.getSession().setAttribute("kebele", request.getParameter("kebele").trim());
                    } else {
                        request.getSession().setAttribute("kebele", "all");
                    }
                    PostPDCoordinator.welcomePage(request, response);
                    break;
                case MINOR_CORRECTION_OFFICER:
                    MinorCorrections.welcomePage(request, response);
                    break;
                case CORRECTION_FIRST_ENTRY_OPERATOR:
                    CorrectionFirstEntry.welcomePage(request, response);
                    break;
                case CORRECTION_SECOND_ENTRY_OPERATOR:
                    CorrectionSecondEntry.welcomePage(request, response);
                    break;
                case CORRECTION_SUPERVISOR:
                    CorrectionSupervisor.welcomePage(request, response);
                    break;

            }
        } else if (action.equalsIgnoreCase(Constants.ACTION_WELCOME_PART)) {
            switch (CommonStorage.getCurrentUser(request).getRole()) {
                case FIRST_ENTRY_OPERATOR:
                    FirstEntry.welcomeFrom(request, response);
                    break;
                case SECOND_ENTRY_OPERATOR:
                    SecondEntry.welcomeFrom(request, response);
                    break;
                case SUPERVISOR:
                    Correction.welcomeFrom(request, response);
                    break;
                case ADMINISTRATOR:
                    Administrator.welcomeForm(request, response);
                    break;
                case POSTPDCOORDINATOR:
                    if (request.getParameter("kebele") != null) {
                        request.getSession().setAttribute("kebele", request.getParameter("kebele").trim());
                    } else {
                        request.getSession().setAttribute("kebele", "all");
                    }
                    PostPDCoordinator.welcomeForm(request, response);
                    break;
                case MINOR_CORRECTION_OFFICER:
                    MinorCorrections.welcomeForm(request, response);
                    break;
                case CORRECTION_FIRST_ENTRY_OPERATOR:
                    CorrectionFirstEntry.welcomeForm(request, response);
                    break;
                case CORRECTION_SECOND_ENTRY_OPERATOR:
                    CorrectionSecondEntry.welcomeForm(request, response);
                    break;
                case CORRECTION_SUPERVISOR:
                    CorrectionSupervisor.welcomeForm(request, response);
                    break;
            }
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PROFILE)) {
            getUPI(request);
            All.viewProfile(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_PROFILE)) {
            getUPI(request);
            All.editProfile(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PROFILE)) {
            getUPI(request);
            All.updateProfile(request, response);
        } // For FEO 
        else if (action.equalsIgnoreCase(Constants.ACTION_ADD_PARCEL_FEO)) {
            getUPI(request);
            FirstEntry.addParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FIND_PARCEL_FEO)) {
            getUPI(request);
            FirstEntry.viewParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PARCEL_FEO)) {
            getUPI(request);
            FirstEntry.viewParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_PARCEL_FEO)) {
            getUPI(request);
            FirstEntry.editParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_PARCEL_FEO)) {
            getUPI(request);
            FirstEntry.saveParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PARCEL_FEO)) {
            getUPI(request);
            FirstEntry.updateParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_HOLDER_FEO)) {
            getUPI(request);
            FirstEntry.viewHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_PERSONS_WITH_INTEREST_LIST_FEO)) {
            getUPI(request);
            FirstEntry.personsWithInterestList(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_ORGANIZATION_HOLDER_FEO)) {
            getUPI(request);
            FirstEntry.editOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_ORGANIZATION_HOLDER_FEO)) {
            getUPI(request);
            FirstEntry.finishOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_DISPUTE_FEO)) {
            getUPI(request);
            FirstEntry.finishDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_HOLDER_FEO)) {
            getUPI(request);
            FirstEntry.saveHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DISPUTE_LIST_FEO)) {
            getUPI(request);
            FirstEntry.viewDisputeList(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_DISPUTE_FEO)) {
            getUPI(request);
            FirstEntry.saveDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_DISPUTE_FEO)) {
            getUPI(request);
            FirstEntry.viewDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_INDIVIDUAL_HOLDER_FEO)) {
            getUPI(request);
            FirstEntry.viewIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_INDIVIDUAL_HOLDER_FEO)) {
            getUPI(request);
            FirstEntry.editIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_DISPUTE_FEO)) {
            getUPI(request);
            FirstEntry.deleteDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_INDIVIDUAL_HOLDER_FEO)) {
            getUPI(request);
            FirstEntry.deleteIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_PARCEL_FEO)) {
            getUPI(request);
            FirstEntry.deleteParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_DISPUTE_FEO)) {
            getUPI(request);
            FirstEntry.editDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_DISPUTE_FEO)) {
            getUPI(request);
            FirstEntry.updateDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_INDIVIDUAL_HOLDER_FEO)) {
            getUPI(request);
            FirstEntry.updateIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_ORGANIZATION_HOLDER_FEO)) {
            getUPI(request);
            FirstEntry.updateOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_PERSON_WITH_INTEREST_FEO)) {
            getUPI(request);
            FirstEntry.savePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_PERSON_WITH_INTEREST_FEO)) {
            getUPI(request);
            FirstEntry.deletePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PERSON_WITH_INTEREST_FEO)) {
            getUPI(request);
            FirstEntry.viewPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_PERSON_WITH_INTEREST_FEO)) {
            getUPI(request);
            FirstEntry.editPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PERSON_WITH_INTEREST_FEO)) {
            getUPI(request);
            FirstEntry.updatePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_PERSON_WITH_INTEREST_FEO)) {
            getUPI(request);
            FirstEntry.finishPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_GUARDIAN_FEO)) {
            getUPI(request);
            FirstEntry.saveGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_GUARDIAN_FEO)) {
            getUPI(request);
            FirstEntry.deleteGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_GUARDIAN_FEO)) {
            getUPI(request);
            FirstEntry.viewGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_GUARDIAN_FEO)) {
            getUPI(request);
            FirstEntry.editGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_GUARDIAN_FEO)) {
            getUPI(request);
            FirstEntry.updateGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_GUARDIANS_LIST_FEO)) {
            getUPI(request);
            FirstEntry.guardiansList(request, response);
        }// for SEO
        else if (action.equalsIgnoreCase(Constants.ACTION_ADD_PARCEL_SEO)) {
            getUPI(request);
            SecondEntry.addParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_HOLDER_SEO)) {
            getUPI(request);
            SecondEntry.saveHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_PARCEL_SEO)) {
            getUPI(request);
            SecondEntry.saveParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PARCEL_SEO)) {
            getUPI(request);
            SecondEntry.viewParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FIND_PARCEL_SEO)) {
            getUPI(request);
            SecondEntry.viewParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_PARCEL_SEO)) {
            getUPI(request);
            SecondEntry.editParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PARCEL_SEO)) {
            getUPI(request);
            SecondEntry.updateParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_PARCEL_SEO)) {
            getUPI(request);
            SecondEntry.deleteParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_HOLDER_SEO)) {
            getUPI(request);
            SecondEntry.saveHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_HOLDER_SEO)) {
            getUPI(request);
            SecondEntry.viewHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_ORGANIZATION_HOLDER_SEO)) {
            getUPI(request);
            SecondEntry.editOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_ORGANIZATION_HOLDER_SEO)) {
            getUPI(request);
            SecondEntry.updateOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_INDIVIDUAL_HOLDER_SEO)) {
            getUPI(request);
            SecondEntry.viewIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_INDIVIDUAL_HOLDER_SEO)) {
            getUPI(request);
            SecondEntry.editIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_INDIVIDUAL_HOLDER_SEO)) {
            getUPI(request);
            SecondEntry.updateIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_INDIVIDUAL_HOLDER_SEO)) {
            getUPI(request);
            SecondEntry.deleteIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_PERSONS_WITH_INTEREST_LIST_SEO)) {
            getUPI(request);
            SecondEntry.personsWithInterestList(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_PERSON_WITH_INTEREST_SEO)) {
            getUPI(request);
            SecondEntry.savePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PERSON_WITH_INTEREST_SEO)) {
            getUPI(request);
            SecondEntry.viewPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_PERSON_WITH_INTEREST_SEO)) {
            getUPI(request);
            SecondEntry.editPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PERSON_WITH_INTEREST_SEO)) {
            getUPI(request);
            SecondEntry.updatePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_PERSON_WITH_INTEREST_SEO)) {
            getUPI(request);
            SecondEntry.deletePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DISPUTE_LIST_SEO)) {
            getUPI(request);
            SecondEntry.viewDisputeList(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_DISPUTE_SEO)) {
            getUPI(request);
            SecondEntry.saveDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_DISPUTE_SEO)) {
            getUPI(request);
            SecondEntry.viewDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_DISPUTE_SEO)) {
            getUPI(request);
            SecondEntry.editDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_DISPUTE_SEO)) {
            getUPI(request);
            SecondEntry.updateDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_DISPUTE_SEO)) {
            getUPI(request);
            SecondEntry.deleteDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_ORGANIZATION_HOLDER_SEO)) {
            getUPI(request);
            SecondEntry.finishOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_DISPUTE_SEO)) {
            getUPI(request);
            SecondEntry.finishDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_PERSON_WITH_INTEREST_SEO)) {
            getUPI(request);
            SecondEntry.finishPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUBMIT_FOR_CORRECTION_SEO)) {
            getUPI(request);
            SecondEntry.submitForCorrection(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_START_REVIEW_SEO)) {
            getUPI(request);
            SecondEntry.startReview(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_GUARDIAN_SEO)) {
            getUPI(request);
            SecondEntry.saveGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_GUARDIAN_SEO)) {
            getUPI(request);
            SecondEntry.deleteGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_GUARDIAN_SEO)) {
            getUPI(request);
            SecondEntry.viewGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_GUARDIAN_SEO)) {
            getUPI(request);
            SecondEntry.editGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_GUARDIAN_SEO)) {
            getUPI(request);
            SecondEntry.updateGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_GUARDIANS_LIST_SEO)) {
            getUPI(request);
            SecondEntry.guardiansList(request, response);
        } // for supervisor
        else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_PARCEL_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.editParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PARCEL_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.updateParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PARCEL_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.viewParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PARCEL_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.updateParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PARCEL_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.viewParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_HOLDER_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.viewHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_INDIVIDUAL_HOLDER_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.viewIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_INDIVIDUAL_HOLDER_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.deleteIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_INDIVIDUAL_HOLDER_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.editIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_INDIVIDUAL_HOLDER_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.updateIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_HOLDER_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.saveHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_PERSONS_WITH_INTEREST_LIST_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.personsWithInterestList(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_PERSON_WITH_INTEREST_SUPERVISOR)) {
            getUPI(request);
            Correction.savePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PERSON_WITH_INTEREST_SUPERVISOR)) {
            getUPI(request);
            Correction.viewPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_PERSON_WITH_INTEREST_SUPERVISOR)) {
            getUPI(request);
            Correction.editPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PERSON_WITH_INTEREST_SUPERVISOR)) {
            getUPI(request);
            Correction.updatePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_PERSON_WITH_INTEREST_SUPERVISOR)) {
            getUPI(request);
            Correction.deletePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DISPUTE_LIST_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.viewDisputeList(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_DISPUTE_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.saveDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_DISPUTE_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.viewDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_DISPUTE_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.editDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_DISPUTE_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.updateDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_DISPUTE_SUPERVISOR)) {
            getUPI(request);
            Correction.deleteDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_ORGANIZATION_HOLDER_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.finishOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_ORGANIZATION_HOLDER_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.editOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_ORGANIZATION_HOLDER_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.updateOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_PARCEL_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.deleteParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FIND_PARCEL_SUPERVISOR)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            Correction.viewParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_DISPUTE_SUPERVISOR)) {
            getUPI(request);
            Correction.finishDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_GUARDIAN_SUPERVISOR)) {
            getUPI(request);
            Correction.saveGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_GUARDIAN_SUPERVISOR)) {
            getUPI(request);
            Correction.deleteGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_GUARDIAN_SUPERVISOR)) {
            getUPI(request);
            Correction.viewGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_GUARDIAN_SUPERVISOR)) {
            getUPI(request);
            Correction.editGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_GUARDIAN_SUPERVISOR)) {
            getUPI(request);
            Correction.updateGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_GUARDIANS_LIST_SUPERVISOR)) {
            getUPI(request);
            Correction.guardiansList(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_PERSON_WITH_INTEREST_SUPERVISOR)) {
            getUPI(request);
            Correction.finishPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_USER_ADMINISTRATOR)) {
            Administrator.saveUser(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_USER_ADMINISTRATOR)) {
            Administrator.viewUser(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_USER_ADMINISTRATOR)) {
            Administrator.editUser(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_USER_ADMINISTRATOR)) {
            Administrator.updateUser(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_USER_ADMINISTRATOR)) {
            Administrator.deleteUser(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_SETTINGS_ADMINISTRATOR)) {
            Administrator.updateSettings(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PASSWORD_ADMINISTRATOR)) {
            Administrator.updatePassword(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_PERIODICAL_REPORT_ADMINISTRATOR)) {
            Administrator.timeBoundReport(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_KEBELE_REPORT_ADMINISTRATOR)) {
            Administrator.kebeleReport(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_PUBLIC_DISPLAY)) {
            Administrator.publicDisplay(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EXPORT_REPORT_ADMINISTRATOR)) {
            Administrator.exportReport(request, response);
        } // For PDC
        else if (action.equalsIgnoreCase(Constants.ACTION_MINOR_CORRECTION_PDC)) {
            PostPDCoordinator.sendToMinorCorrection(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_MAJOR_CORRECTION_PDC)) {
            PostPDCoordinator.sendToMajorCorrection(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_CONFIRMED_PARCEL_PDC)) {
            PostPDCoordinator.sendToConfirmed(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FIND_PARCEL_PDC)) {
            if (request.getParameter("upi") != null) {
                request.setAttribute("upi", request.getParameter("upi").trim());
                request.getSession().setAttribute("upi", request.getParameter("upi").trim());
            } else {
                request.setAttribute("upi", request.getSession().getAttribute("upi"));
            }
            PostPDCoordinator.viewParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_PARCEL_LIST_PDC)) {
            if (request.getParameter("kebele") != null) {
                request.getSession().setAttribute("kebele", request.getParameter("kebele").trim());
            } else {
                request.getSession().setAttribute("kebele", "all");
            }
            PostPDCoordinator.welcomeForm(request, response);
        } // Minor corrections officer 
        else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PARCEL_MCO)) {
            MinorCorrections.viewParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_PARCEL_MCO)) {
            MinorCorrections.editParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_PARCEL_MCO)) {
            MinorCorrections.deleteParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_CONFIRM_PARCEL_MCO)) {
            MinorCorrections.confirmParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PARCEL_MCO)) {
            MinorCorrections.updateParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_HOLDER_MCO)) {
            MinorCorrections.viewHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_INDIVIDUAL_HOLDER_MCO)) {
            MinorCorrections.viewIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_INDIVIDUAL_HOLDER_MCO)) {
            MinorCorrections.updateIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_INDIVIDUAL_HOLDER_MCO)) {
            MinorCorrections.editIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_INDIVIDUAL_HOLDER_MCO)) {
            MinorCorrections.deleteIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_ORGANIZATION_HOLDER_MCO)) {
            MinorCorrections.editOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_ORGANIZATION_HOLDER_MCO)) {
            MinorCorrections.updateOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_HOLDER_MCO)) {
            MinorCorrections.saveHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_GUARDIANS_LIST_MCO)) {
            MinorCorrections.guardiansList(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_GUARDIAN_MCO)) {
            MinorCorrections.viewGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_GUARDIAN_MCO)) {
            MinorCorrections.editGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_GUARDIAN_MCO)) {
            MinorCorrections.deleteGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_GUARDIAN_MCO)) {
            MinorCorrections.updateGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_GUARDIAN_MCO)) {
            MinorCorrections.saveGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_PERSONS_WITH_INTEREST_LIST_MCO)) {
            MinorCorrections.personsWithInterestList(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PERSON_WITH_INTEREST_MCO)) {
            MinorCorrections.viewPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PERSON_WITH_INTEREST_MCO)) {
            MinorCorrections.updatePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_PERSON_WITH_INTEREST_MCO)) {
            MinorCorrections.editPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_PERSON_WITH_INTEREST_MCO)) {
            MinorCorrections.deletePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_PERSON_WITH_INTEREST_MCO)) {
            MinorCorrections.savePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DISPUTE_LIST_MCO)) {
            MinorCorrections.viewDisputeList(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_DISPUTE_MCO)) {
            MinorCorrections.viewDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_DISPUTE_MCO)) {
            MinorCorrections.editDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_DISPUTE_MCO)) {
            MinorCorrections.updateDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_DISPUTE_MCO)) {
            MinorCorrections.deleteDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_DISPUTE_MCO)) {
            MinorCorrections.saveDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_PERSON_WITH_INTEREST_MCO)) {
            MinorCorrections.welcomeForm(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_DISPUTE_MCO)) {
            MinorCorrections.welcomeForm(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_ORGANIZATION_HOLDER_MCO)) {
            MinorCorrections.welcomeForm(request, response);
        }// Coorection First Entry
        else if (action.equalsIgnoreCase(Constants.ACTION_ADD_PARCEL_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.addParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FIND_PARCEL_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.viewParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_PARCEL_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.saveParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PARCEL_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.viewParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_PARCEL_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.editParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PARCEL_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.updateParcel(request, response);
        }else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_HOLDER_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.viewHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_PERSONS_WITH_INTEREST_LIST_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.personsWithInterestList(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_ORGANIZATION_HOLDER_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.editOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_ORGANIZATION_HOLDER_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.finishOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_DISPUTE_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.finishDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_HOLDER_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.saveHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DISPUTE_LIST_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.viewDisputeList(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_DISPUTE_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.saveDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_DISPUTE_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.viewDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_INDIVIDUAL_HOLDER_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.viewIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_INDIVIDUAL_HOLDER_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.editIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_DISPUTE_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.deleteDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_INDIVIDUAL_HOLDER_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.deleteIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_PARCEL_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.deleteParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_DISPUTE_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.editDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_DISPUTE_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.updateDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_INDIVIDUAL_HOLDER_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.updateIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_ORGANIZATION_HOLDER_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.updateOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_PERSON_WITH_INTEREST_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.savePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_PERSON_WITH_INTEREST_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.deletePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PERSON_WITH_INTEREST_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.viewPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_PERSON_WITH_INTEREST_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.editPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PERSON_WITH_INTEREST_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.updatePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_PERSON_WITH_INTEREST_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.finishPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_GUARDIAN_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.saveGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_GUARDIAN_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.deleteGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_GUARDIAN_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.viewGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_GUARDIAN_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.editGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_GUARDIAN_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.updateGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_GUARDIANS_LIST_MCFEO)) {
            getUPI(request);
            CorrectionFirstEntry.guardiansList(request, response);
        } // Correction Second Entry Operator
        else if (action.equalsIgnoreCase(Constants.ACTION_ADD_PARCEL_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.addParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_HOLDER_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.saveHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_PARCEL_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.saveParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PARCEL_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.viewParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FIND_PARCEL_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.viewParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_PARCEL_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.editParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PARCEL_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.updateParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_PARCEL_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.deleteParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_HOLDER_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.saveHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_HOLDER_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.viewHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_ORGANIZATION_HOLDER_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.editOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_ORGANIZATION_HOLDER_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.updateOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_INDIVIDUAL_HOLDER_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.viewIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_INDIVIDUAL_HOLDER_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.editIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_INDIVIDUAL_HOLDER_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.updateIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_INDIVIDUAL_HOLDER_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.deleteIndividualHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_PERSONS_WITH_INTEREST_LIST_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.personsWithInterestList(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_PERSON_WITH_INTEREST_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.savePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PERSON_WITH_INTEREST_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.viewPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_PERSON_WITH_INTEREST_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.editPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PERSON_WITH_INTEREST_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.updatePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_PERSON_WITH_INTEREST_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.deletePersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DISPUTE_LIST_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.viewDisputeList(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_DISPUTE_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.saveDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_DISPUTE_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.viewDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_DISPUTE_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.editDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_DISPUTE_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.updateDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_DISPUTE_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.deleteDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_ORGANIZATION_HOLDER_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.finishOrganizationHolder(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_DISPUTE_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.finishDispute(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FINISH_PERSON_WITH_INTEREST_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.finishPersonWithInterest(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SUBMIT_FOR_CORRECTION_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.submitForCorrection(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_START_REVIEW_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.startReview(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_GUARDIAN_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.saveGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_GUARDIAN_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.deleteGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_GUARDIAN_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.viewGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_GUARDIAN_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.editGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_GUARDIAN_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.updateGuardian(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_GUARDIANS_LIST_MCSEO)) {
            getUPI(request);
            CorrectionSecondEntry.guardiansList(request, response);
        }// for all unknown requests
        else {
            All.goBackToHome(request, response);
        }
    }

    private void getUPI(HttpServletRequest request) {
        if (request.getParameter("upi") != null) {
            request.setAttribute("upi", request.getParameter("upi").trim());
            request.getSession().setAttribute("upi", request.getParameter("upi").trim());
        } else {
            request.setAttribute("upi", request.getSession().getAttribute("upi"));
        }
        if (request.getParameter("parcelNo") != null) {
            request.setAttribute("parcelNo", request.getParameter("parcelNo").trim());
            request.getSession().setAttribute("parcelNo", request.getParameter("parcelNo").trim());
        } else {
            request.setAttribute("parcelNo", request.getSession().getAttribute("parcelNo"));
        }
        if (request.getParameter("kebele") != null) {
            request.setAttribute("kebele", request.getParameter("kebele").trim());
            request.getSession().setAttribute("kebele", request.getParameter("kebele").trim());
        } else {
            request.setAttribute("kebele", request.getSession().getAttribute("kebele"));
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
