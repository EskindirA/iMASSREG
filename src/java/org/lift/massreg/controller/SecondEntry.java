package org.lift.massreg.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class SecondEntry {

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
     * Handlers request to save a holder for the current parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected void saveHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
