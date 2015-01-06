package org.lift.massreg.controller;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.entity.User;
import org.lift.massreg.util.*;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class Administrator {

    /**
     * Handlers request for getting the welcome page
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void welcomePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // setup request attributes
        ArrayList<User> users = MasterRepository.getInstance().getAllUsers();
        request.setAttribute("users", users);
        request.setAttribute("page", IOC.getPage(Constants.INDEX_WELCOME_ADMINISTRATOR));
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
        ArrayList<User> users = MasterRepository.getInstance().getAllUsers();
        request.setAttribute("users", users);
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_WELCOME_ADMINISTRATOR));
        rd.forward(request, response);
    }

    
    /**
     * Sends a reload order for the browser
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void reload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_RELOAD));
        rd.forward(request, response);
    }

    
    /**
     * Handlers request to save a user by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void saveUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!MasterRepository.getInstance().userExists(request.getParameter("username"))) {
            User user = new User();
            try {
                user.setFathersName(request.getParameter("fathersName"));
                user.setFirstName(request.getParameter("firstName"));
                user.setGrandFathersName(request.getParameter("grandfathersName"));
                user.setPhoneNumber(request.getParameter("phoneno"));
                user.setUsername(request.getParameter("username"));
                user.setPassword(request.getParameter("password"));
                String role = request.getParameter("role");
                switch (role.toUpperCase()) {
                    case "FEO":
                        user.setRole(Constants.ROLE.FIRST_ENTRY_OPERATOR);
                        break;
                    case "SEO":
                        user.setRole(Constants.ROLE.SECOND_ENTRY_OPERATOR);
                        break;
                    case "SUPERVISOR":
                        user.setRole(Constants.ROLE.SUPERVISOR);
                        break;
                    case "ADMINISTRATOR":
                        user.setRole(Constants.ROLE.ADMINISTRATOR);
                        break;
                }

                if (user.validateForSave()) {
                    user.save();
                    welcomeForm(request, response);
                } else {
                    request.getSession().setAttribute("title", "Validation Error");
                    request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                    request.getSession().setAttribute("returnTitle", "Go back to holder list");
                    request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_FEO);
                    RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                    rd.forward(request, response);
                }

            } catch (ServletException | IOException ex) {
                CommonStorage.getLogger().logError(ex.toString());
                request.getSession().setAttribute("title", "Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend");
                request.getSession().setAttribute("returnTitle", "Go back to user list");
                request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The username already existes");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for viewing a user by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void viewUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = MasterRepository.getInstance().getUser(Long.parseLong(request.getParameter("userId")));
        request.setAttribute("currentUser", user);
        // if the parcel does exist in database
        if (request.getAttribute("currentUser") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_USER_ADMINISTRATOR));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the user your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for getting the edit user form by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void editUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = MasterRepository.getInstance().getUser(Long.parseLong(request.getParameter("userId")));
        request.setAttribute("currentUser", user);
        if (request.getAttribute("currentUser") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_USER_ADMINISTRATOR));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the user your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to update user information by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void updateUser(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        User oldUser = MasterRepository.getInstance().getUser(Long.parseLong(request.getParameter("userId")));
        try {
            User newUser = new User();
            newUser.setFathersName(request.getParameter("fathersName"));
            newUser.setFirstName(request.getParameter("firstName"));
            newUser.setGrandFathersName(request.getParameter("grandfathersName"));
            newUser.setUsername(request.getParameter("username"));
            newUser.setPhoneNumber(request.getParameter("phoneno"));
            newUser.setFathersName(request.getParameter("fathersName"));
            String role = request.getParameter("role");
            switch (role.toUpperCase()) {
                case "FEO":
                    newUser.setRole(Constants.ROLE.FIRST_ENTRY_OPERATOR);
                    break;
                case "SEO":
                    newUser.setRole(Constants.ROLE.SECOND_ENTRY_OPERATOR);
                    break;
                case "SUPERVISOR":
                    newUser.setRole(Constants.ROLE.SUPERVISOR);
                    break;
                case "ADMINISTRATOR":
                    newUser.setRole(Constants.ROLE.ADMINISTRATOR);
                    break;
            }
            if (MasterRepository.getInstance().update(oldUser, newUser)) {
                welcomeForm(request, response);
            } else {
                request.getSession().setAttribute("title", "Inrernal Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend/");
                request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
                request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
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
     * Handlers request to update settings by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void updateSettings(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String language = request.getParameter("language");
        long woreda = Long.parseLong(request.getParameter("woreda"));
        CommonStorage.setLanguage(language);
        CommonStorage.setWoreda(woreda, MasterRepository.getInstance().getWoredaName(woreda),MasterRepository.getInstance().getWoredaIdForUPI(woreda));
        reload(request, response);
    }

    /**
     * Handlers request to delete a user by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.err.println(request.getParameter("userId"));
        if (User.delete(Long.parseLong(request.getParameter("userId")))) {
            welcomeForm(request, response);
        } else {
            // if the parcel fails to validate show error message
            request.getSession().setAttribute("title", "Delete Error");
            request.getSession().setAttribute("message", "Sorry, there was a problem deleting the user");
            request.getSession().setAttribute("returnTitle", "Go back to user list");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

}
