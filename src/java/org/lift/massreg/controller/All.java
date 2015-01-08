package org.lift.massreg.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.dto.CurrentUserDTO;
import org.lift.massreg.entity.User;
import org.lift.massreg.util.*;

/**
 * Controller class to handle request common for all levels
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class All {

    /**
     * Handlers requests that were not recognized
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void goBackToHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().setAttribute("title", "Not Recognized");
        request.getSession().setAttribute("message", "Sorry, You'r request could not be recognized");
        request.getSession().setAttribute("returnTitle", "Go back to home page");
        request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
        rd.forward(request, response);
    }

    protected static void viewProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_PROFILE));
        rd.forward(request, response);
    }

    protected static void editProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_PROFILE));
        rd.forward(request, response);
    }

    protected static void updateProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Only change password
        String oldPassword = request.getParameter("oldPassword");
        String password = request.getParameter("password");
        CurrentUserDTO user = CommonStorage.getCurrentUser(request);
        if(!MasterRepository.getInstance().checkPassword(user, oldPassword)){
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The password you specified is not valid");
            request.getSession().setAttribute("returnTitle", "Go back");
            request.getSession().setAttribute("returnAction", Constants.ACTION_EDIT_PROFILE);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);            
        }else if (MasterRepository.getInstance().changePassword(user.getUserId(), password)) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_PROFILE));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, Your request to change the password could not be completed");
            request.getSession().setAttribute("returnTitle", "Go back to home page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }
}
