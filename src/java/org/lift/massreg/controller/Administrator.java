package org.lift.massreg.controller;

import java.io.*;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;
import java.util.Set;
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
        CommonStorage.setWoreda(woreda, MasterRepository.getInstance().getWoredaName(woreda), MasterRepository.getInstance().getWoredaIdForUPI(woreda));
        reload(request, response);
    }

    /**
     * Handlers request to update password by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
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
     */
    protected static void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

    /**
     * Handlers request to generate report by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    public static void exportReport(HttpServletRequest request, HttpServletResponse response) {
        String file = request.getParameter("file");
        File f = new File(file);
        try {
            FileInputStream in = new FileInputStream(f);
            byte[] data = new byte[(int) f.length()];
            in.read(data);
            in.close();
            response.setContentType("text/*");
            response.setHeader("Content-Disposition", "attachment; filename=" + file);
            response.setHeader("Cache-Control", "no cache");
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
        } catch (Exception e) {
        }
    }

    protected static void periodicalReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Date fromDate = Date.valueOf(request.getParameter("fromDate"));
        Date toDate = Date.valueOf(request.getParameter("toDate"));
        if (fromDate == null || toDate == null) {
            response.getOutputStream().println("Please specfiey valide report dates");
        } else {
            request.setAttribute("report", generatePeriodicalReport(request, fromDate, toDate));
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_PERIODICAL_REPORT));
            rd.forward(request, response);
        }
    }

    public static void kebeleReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String kebele = request.getParameter("kebele");
        if (kebele.isEmpty()) {
            response.getOutputStream().println("Please specfiey kebele for the report ");
        } else {
            request.setAttribute("report", generateKebeleReport(request));
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_KEBELE_REPORT));
            rd.forward(request, response);
        }
    }

    private static Properties generatePeriodicalReport(HttpServletRequest request, Date fromDate, Date toDate) {
        Properties report = new Properties();

        Option[] kebeles = MasterRepository.getInstance().getAllKebeles();

        for (Option kebele : kebeles) {
            int result = MasterRepository.getInstance().getCountOfCommittedParcels(fromDate, toDate, kebele.getKey());
            if (result > 0) {
                report.setProperty("Total number of parcels entered into iMASSREG:" + kebele.getValue(), result + "");
                report.setProperty("Total number of non committed parcels:" + kebele.getValue(), MasterRepository.getInstance().getCountOfNonCommittedParcels(fromDate, toDate, kebele.getKey()) + "");
                report.setProperty("Total number of parcels under dispute:" + kebele.getValue(), MasterRepository.getInstance().getCountOfParcelsWithDispute(fromDate, toDate, kebele.getKey()) + "");
                report.setProperty("Total number of unique individual holders:" + kebele.getValue(), MasterRepository.getInstance().getCountOfUniqueHolders(fromDate, toDate, kebele.getKey()) + "");
                report.setProperty("Total number of female holders:" + kebele.getValue(), MasterRepository.getInstance().getCountOfFemaleHolders(fromDate, toDate, kebele.getKey()) + "");
                report.setProperty("Total number of male holders:" + kebele.getValue(), MasterRepository.getInstance().getCountOfMaleHolders(fromDate, toDate, kebele.getKey()) + "");
                report.setProperty("Total number of of FHH:" + kebele.getValue(), MasterRepository.getInstance().getCountOfFHH(fromDate, toDate, kebele.getKey()) + "");
                report.setProperty("Total number of of MHH:" + kebele.getValue(), MasterRepository.getInstance().getCountOfMHH(fromDate, toDate, kebele.getKey()) + "");
                report.setProperty("Total number of single female holders:" + kebele.getValue(), MasterRepository.getInstance().getCountOfSingleFemaleHolders(fromDate, toDate, kebele.getKey()) + "");
                report.setProperty("Total number of single male holders:" + kebele.getValue(), MasterRepository.getInstance().getCountOfSingleMaleHolders(fromDate, toDate, kebele.getKey()) + "");
                report.setProperty("Total number of parcels with of shared ownership:" + kebele.getValue(), MasterRepository.getInstance().getCountOfSharedOwnership(fromDate, toDate, kebele.getKey()) + "");
                report.setProperty("Total number of female involved in a dispute:" + kebele.getValue(), MasterRepository.getInstance().getCountOfFemaleInDispute(fromDate, toDate, kebele.getKey()) + "");
                report.setProperty("Total number of male involved in a dispute:" + kebele.getValue(), MasterRepository.getInstance().getCountOfMaleInDispute(fromDate, toDate, kebele.getKey()) + "");
                report.setProperty("Total number of non-natural persons:" + kebele.getValue(), MasterRepository.getInstance().getCountOfNonNaturalPersons(fromDate, toDate, kebele.getKey()) + "");

            }
        }
        report.setProperty("Report generated on", Instant.now().toString().replace("T", " "));
        report.setProperty("Woreda id", CommonStorage.getCurrentWoredaIdForUPI());
        report.setProperty("Woreda name", CommonStorage.getCurrentWoredaName());
        try {
            report.store(new FileOutputStream("tempReport.properties"), null);
            String outputFile = Date.from(Instant.now()).getTime() + ".imrf";

            File iFile = new File("tempReport.properties");
            File oFile = new File(outputFile);
            FileInputStream fis = new FileInputStream(iFile);
            byte[] data = new byte[(int) iFile.length()];
            fis.read(data);
            fis.close();

            FileOutputStream fos = new FileOutputStream(oFile);
            fos.write(Base64.getEncoder().encode(data));
            fos.close();
            iFile.delete();
            request.setAttribute("reportURL", request.getContextPath() + "/Index?action=" + Constants.ACTION_EXPORT_REPORT_ADMINISTRATOR + "&file=" + outputFile);

        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
        }
        return report;
    }

    private static Properties generateKebeleReport(HttpServletRequest request) {
        long kebele = Long.parseLong(request.getParameter("kebele"));
        Date fromDate = Date.valueOf("1900-01-01");
        Date toDate = Date.valueOf("2200-01-01");
        MasterRepository.getInstance().updateParcelArea(kebele);
        Properties report = new Properties();
        long totalDemarcated = MasterRepository.getInstance().getCountOfDemarcatedParcels(kebele);
        long totalMASSREG = MasterRepository.getInstance().getCountOfCommittedParcels(kebele);

        //// aggregate data for the kebele
        report.setProperty("Woreda id", CommonStorage.getCurrentWoredaIdForUPI());
        report.setProperty("Woreda name", CommonStorage.getCurrentWoredaName());
        report.setProperty("Kebele", MasterRepository.getInstance().getKebeleName(kebele, "english"));
        report.setProperty("Report generated on", Instant.now().toString().replace("T", " "));
        report.setProperty("Total number of parcels entered into iMASSREG", MasterRepository.getInstance().getCountOfCommittedParcels(fromDate, toDate, kebele + "") + "");
        report.setProperty("Total number of non committed parcels", MasterRepository.getInstance().getCountOfNonCommittedParcels(fromDate, toDate, kebele + "") + "");
        report.setProperty("Total number of parcels under dispute", MasterRepository.getInstance().getCountOfParcelsWithDispute(fromDate, toDate, kebele + "") + "");
        report.setProperty("Total number of unique individual holders", MasterRepository.getInstance().getCountOfUniqueHolders(fromDate, toDate, kebele + "") + "");
        report.setProperty("Total number of female holders", MasterRepository.getInstance().getCountOfFemaleHolders(fromDate, toDate, kebele + "") + "");
        report.setProperty("Total number of male holders", MasterRepository.getInstance().getCountOfMaleHolders(fromDate, toDate, kebele + "") + "");
        report.setProperty("Total number of of FHH", MasterRepository.getInstance().getCountOfFHH(fromDate, toDate, kebele + "") + "");
        report.setProperty("Total number of of MHH", MasterRepository.getInstance().getCountOfMHH(fromDate, toDate, kebele + "") + "");
        report.setProperty("Total number of single female holders", MasterRepository.getInstance().getCountOfSingleFemaleHolders(fromDate, toDate, kebele + "") + "");
        report.setProperty("Total number of single male holders", MasterRepository.getInstance().getCountOfSingleMaleHolders(fromDate, toDate, kebele + "") + "");
        report.setProperty("Total number of parcels with of shared ownership", MasterRepository.getInstance().getCountOfSharedOwnership(fromDate, toDate, kebele + "") + "");
        report.setProperty("Total number of female involved in a dispute", MasterRepository.getInstance().getCountOfFemaleInDispute(fromDate, toDate, kebele + "") + "");
        report.setProperty("Total number of male involved in a dispute", MasterRepository.getInstance().getCountOfMaleInDispute(fromDate, toDate, kebele + "") + "");
        report.setProperty("Total number of non-natural persons", MasterRepository.getInstance().getCountOfNonNaturalPersons(fromDate, toDate, kebele + "") + "");

        //// Data from GIS
        report.setProperty("Total number of parcels demarcated", totalDemarcated + "");
        report.setProperty("Number of parcels with incomplete information", (totalDemarcated - totalMASSREG) + "");
        report.setProperty("Total Size of Kebele(in Ha)", MasterRepository.getInstance().getSizeOfKebele(kebele) + "");
        report.setProperty("Average parcel size(in Ha)", MasterRepository.getInstance().getAverageParcelSize(kebele) + "");
        report.setProperty("Average parcel size Per single male(in Ha)", MasterRepository.getInstance().getAverageParcelSizeForSingleMale(kebele) + "");
        report.setProperty("Average parcel size Per single female(in Ha)", MasterRepository.getInstance().getAverageParcelSizeForSingleFemale(kebele) + "");
        report.setProperty("Average parcel size per shared ownership(in Ha)", MasterRepository.getInstance().getAverageParcelSizeForSharedOwnership(kebele) + "");
        report.setProperty("Average parcel size for non natural persons(in Ha)", MasterRepository.getInstance().getAverageParcelSizeForNonNatrualPersons(kebele) + "");

        try {
            report.store(new FileOutputStream("tempReport.properties"), null);
            String outputFile = Date.from(Instant.now()).getTime() + ".imrf";

            File iFile = new File("tempReport.properties");
            File oFile = new File(outputFile);
            FileInputStream fis = new FileInputStream(iFile);
            byte[] data = new byte[(int) iFile.length()];
            fis.read(data);
            fis.close();

            FileOutputStream fos = new FileOutputStream(oFile);
            fos.write(Base64.getEncoder().encode(data));
            fos.close();
            iFile.delete();
            request.setAttribute("reportURL", request.getContextPath() + "/Index?action=" + Constants.ACTION_EXPORT_REPORT_ADMINISTRATOR + "&file=" + outputFile);

        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
        }
        return report;
    }
}
