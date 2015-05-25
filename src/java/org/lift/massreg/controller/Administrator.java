package org.lift.massreg.controller;

import java.io.*;
import java.sql.Date;
import java.time.Instant;
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
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
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
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
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
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
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
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
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
                    case "PDC":
                        user.setRole(Constants.ROLE.POSTPDCOORDINATOR);
                        break;
                    case "WC":
                        user.setRole(Constants.ROLE.WOREDA_COORDINATOR);
                        break;
                    case "MCO":
                        user.setRole(Constants.ROLE.MINOR_CORRECTION_OFFICER);
                        break;
                    case "CFEO":
                        user.setRole(Constants.ROLE.CORRECTION_FIRST_ENTRY_OPERATOR);
                        break;
                    case "CSEO":
                        user.setRole(Constants.ROLE.CORRECTION_SECOND_ENTRY_OPERATOR);
                        break;
                    case "CSUPER":
                        user.setRole(Constants.ROLE.CORRECTION_SUPERVISOR);
                        break;
                }

                if (user.validateForSave()) {
                    user.save(request);
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
                ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
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
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
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
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
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
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
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
                case "PDC":
                    newUser.setRole(Constants.ROLE.POSTPDCOORDINATOR);
                    break;
                case "WC":
                    newUser.setRole(Constants.ROLE.WOREDA_COORDINATOR);
                    break;
                case "MCO":
                    newUser.setRole(Constants.ROLE.MINOR_CORRECTION_OFFICER);
                    break;
                case "CFEO":
                    newUser.setRole(Constants.ROLE.CORRECTION_FIRST_ENTRY_OPERATOR);
                    break;
                case "CSEO":
                    newUser.setRole(Constants.ROLE.CORRECTION_SECOND_ENTRY_OPERATOR);
                    break;
                case "CSUPER":
                    newUser.setRole(Constants.ROLE.CORRECTION_SUPERVISOR);
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
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
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
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void updateSettings(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String language = request.getParameter("language");
        long woreda = Long.parseLong(request.getParameter("woreda"));
        CommonStorage.setLanguage(language);
        CommonStorage.setWoreda(woreda, MasterRepository.getInstance().getWoredaName(woreda), MasterRepository.getInstance().getWoredaIdForUPI(woreda));
        reload(request, response);
    }

    /**
     * Handlers request to update password by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void updatePassword(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String newPassword = request.getParameter("newPassword");
        long userId = Long.parseLong(request.getParameter("userId"));
        if (MasterRepository.getInstance().userExists(MasterRepository.getInstance().getUser(userId).getUsername())) {
            MasterRepository.getInstance().changePassword(userId, newPassword);
            welcomeForm(request, response);
        } else { // User Does not exist
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the user your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to delete a user by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (User.delete(request, Long.parseLong(request.getParameter("userId")))) {
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

    /**
     * Handlers request to generate report by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    public static void exportReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String file = request.getParameter("file");
        File f = new File(file);
        try {
            ReportUtil.sign(file);
            System.err.println("0");
            FileInputStream in = new FileInputStream(f);
            byte[] data = new byte[(int) f.length()];
            System.err.println("1");
            in.read(data);
            System.err.println("2");
            in.close();
            System.err.println("3");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + file);
            response.setHeader("Cache-Control", "no cache");
            System.err.println("4");
            OutputStream out = response.getOutputStream();
            System.err.println("5");
            out.write(data);
            System.err.println("6");
            out.flush();
            System.err.println("7");
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    protected static void timeBoundReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Date fromDate = Date.valueOf(request.getParameter("fromDate"));
        Date toDate = Date.valueOf(request.getParameter("toDate"));
        String fileName = CommonStorage.getCurrentWoredaName() + " Timebound report " + (new Date(Instant.now().toEpochMilli()).toString()) + ".xlsx";

        if (fromDate == null || toDate == null) {
            response.getOutputStream().println("Please specfiey valide report dates");
        } else {
            ReportUtil.generateTimeBoundReport(fromDate, toDate, fileName);
            request.setAttribute("reportURL", request.getContextPath() + "/Index?action=" + Constants.ACTION_EXPORT_REPORT_ADMINISTRATOR + "&file=" + fileName);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_DOWNLOAD_REPORT));
            rd.forward(request, response);
        }
    }

    public static void kebeleReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String kebele = request.getParameter("kebele");
        if (kebele.isEmpty()) {
            response.getOutputStream().println("Please specfiey kebele for the report ");
        } else {
            long kebeleId = Long.parseLong(request.getParameter("kebele"));
            String kebeleName = MasterRepository.getInstance().getKebeleName(kebeleId, "english");
            String fileName = "Kebele Report " + kebeleName + " " + (new Date(Instant.now().toEpochMilli()).toString()) + ".xlsx";
            ReportUtil.generateKebeleReport(kebeleId, kebeleName, fileName);
            request.setAttribute("reportURL", request.getContextPath() + "/Index?action=" + Constants.ACTION_EXPORT_REPORT_ADMINISTRATOR + "&file=" + fileName);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_DOWNLOAD_REPORT));
            rd.forward(request, response);
        }
    }

    public static void publicDisplay(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String kebele = request.getParameter("kebele");
            if (kebele.isEmpty()) {
                response.getOutputStream().println("Please specfiey kebele for the report ");
            } else {
                long kebeleId = Long.parseLong(request.getParameter("kebele"));
                MasterRepository.getInstance().updateParcelArea(kebeleId);
                request.setAttribute("holdersList", MasterRepository.getInstance().getPublicDisplayReport(kebeleId));
                request.setAttribute("parcelList", MasterRepository.getInstance().getParcelsWithoutHolder(kebeleId));
                request.setAttribute("missingParcels", MasterRepository.getInstance().getParcelsWithoutTextualData(kebeleId));

                request.setAttribute("regionName", MasterRepository.getInstance().getRegionName(CommonStorage.getCurrentWoredaId(), CommonStorage.getCurrentLanguage()));
                request.setAttribute("zoneName", MasterRepository.getInstance().getZoneName(CommonStorage.getCurrentWoredaId(), CommonStorage.getCurrentLanguage()));
                request.setAttribute("woredaName", MasterRepository.getInstance().getWoredaName(CommonStorage.getCurrentWoredaId(), CommonStorage.getCurrentLanguage()));
                request.setAttribute("kebeleName", MasterRepository.getInstance().getKebeleName(kebeleId, CommonStorage.getCurrentLanguage()));
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_PUBLIC_DISPLAY));
                rd.forward(request, response);
            }
        } catch (IOException | NumberFormatException | ServletException ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }

    }

    public static void dailyReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Date date = Date.valueOf(request.getParameter("reportDate"));
        if (date == null) {
            response.getOutputStream().println("Please specfiey a date for the report ");
            response.getOutputStream().close();
        } else {
            request.setAttribute("report", ReportUtil.generateDailyReport(date));
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_DAILY_REPORT_ADMINISTRATOR));
            rd.forward(request, response);
        }
    }

}
