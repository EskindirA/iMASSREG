package org.lift.massreg.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.lift.massreg.util.CommonStorage;
import org.lift.massreg.util.Constants;

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

        if (action.equalsIgnoreCase(Constants.ACTION_LOGOUT)) {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath());
        }

        if (action.equalsIgnoreCase(Constants.ACTION_WELCOME)) {
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
        } else if (action.equalsIgnoreCase(Constants.ACTION_ERROR)) {
            ///TODO:Handle the error Case: 
            //All.goBackToHome(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADD_PARCEL_FEO)) {
            request.setAttribute("upi", request.getParameter("upi"));
            FirstEntry.addParcel(request, response);
        } else if (action.equalsIgnoreCase(Constants.ACTION_ADD_PARCEL_SEO)) {
            request.setAttribute("upi", request.getParameter("upi"));
            SecondEntry.addParcel(request, response);
        } else {
            All.goBackToHome(request, response);
        }
        /// TODO: Set attribute values
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
