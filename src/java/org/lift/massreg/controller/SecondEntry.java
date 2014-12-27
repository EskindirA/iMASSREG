package org.lift.massreg.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.lift.massreg.controller.FirstEntry.viewHolder;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.entity.IndividualHolder;
import org.lift.massreg.entity.Parcel;
import org.lift.massreg.util.CommonStorage;
import org.lift.massreg.util.Constants;
import org.lift.massreg.util.IOC;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class SecondEntry {

    /**
     * Handlers request for getting the welcome page
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void welcomePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // setup request attributes
        request.setAttribute("page", IOC.getPage(Constants.INDEX_WELCOME_SEO));
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
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_WELCOME_SEO));
        rd.forward(request, response);
    }

    /**
     * Handlers request for adding a parcel by the second entry
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void addParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // setup request attributes
        request.setAttribute("page", IOC.getPage(Constants.INDEX_WELCOME_SEO));
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

    /**
     * Handlers request for getting a single parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected void getParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handlers request for getting a single or all dispute(s) for the current
     * parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected void getDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handlers request for getting a single or all holders(s) for the current
     * parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected void getHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handlers request to save a parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected void saveParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handlers request to save a dispute for the current parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected void saveDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handlers request to save a holder by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void saveHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().setAttribute("title", "Error");
        request.getSession().setAttribute("message", "Sorry, the parcel your are looking for dose not exist in the database");
        request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
        request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
        rd.forward(request, response);
    }

    /**
     * Handlers request to save an individual holder by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void saveIndividualHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().setAttribute("title", "Error");
        request.getSession().setAttribute("message", "Sorry, the parcel your are looking for dose not exist in the database");
        request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
        request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
        rd.forward(request, response);

    }

    /**
     * Handlers request to save organization holder by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void saveOrganizationHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the parcel your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
    }

    /**
     * Handlers request to remove a parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected void removeParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handlers request to remove a holder for the current parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected void removeHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handlers request to remove a dispute for the current parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected void removeDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handlers request to update a parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected void updateParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handlers request to update a holder for the current parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected void updateHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handlers request to update a dispute for the current parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected void updateDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handlers request try commit the current parcel to confirmed status
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected void commit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handlers request send the current parcel for correction by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected void sendForCorrection(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
