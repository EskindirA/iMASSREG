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
        } else if (action.equalsIgnoreCase(Constants.ACTION_ERROR)) {
            ///TODO:Handle the error Case: 
            All.showError(request, response);
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
            }
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
        }else if (action.equalsIgnoreCase(Constants.ACTION_VIEW_PARCEL_SEO)) {
            getUPI(request);
            SecondEntry.viewParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_FIND_PARCEL_SEO)) {
            getUPI(request);
            SecondEntry.viewParcel(request, response);
        }  else if (action.equalsIgnoreCase(Constants.ACTION_EDIT_PARCEL_SEO)) {
            getUPI(request);
            SecondEntry.editParcel(request, response);
        }  else if (action.equalsIgnoreCase(Constants.ACTION_UPDATE_PARCEL_SEO)) {
            getUPI(request);
            SecondEntry.updateParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_DELETE_PARCEL_SEO)) {
            getUPI(request);
            SecondEntry.deleteParcel(request, response);
        }  else if (action.equalsIgnoreCase(Constants.ACTION_SAVE_HOLDER_SEO)) {
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
        } // for all unknown requests
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
