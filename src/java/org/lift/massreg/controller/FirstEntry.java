package org.lift.massreg.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.dto.CurrentUserDTO;
import org.lift.massreg.util.CommonStorage;
import org.lift.massreg.util.Constants;
import org.lift.massreg.util.IOC;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public class FirstEntry {

    /**
     * Handlers request for getting a single parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void welcomePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // setup request attributes
        request.setAttribute("page", IOC.getPage(Constants.INDEX_FIRST_ENTRY_WELCOME));
        request.setAttribute("title", IOC.getTitle(Constants.INDEX_FIRST_ENTRY_WELCOME));
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }
    
        /**
     * Handlers request for adding a parcel by the first entry operator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    protected static void addParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // setup request attributes
        // if the parcel exists redirect to viewParcel
        if(MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString())){
            request.setAttribute("title", IOC.getTitle(Constants.INDEX_VIEW_PARCEL_FEO));
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_PARCEL_FEO));
            rd.forward(request, response);
        }else{
            request.setAttribute("title", IOC.getTitle(Constants.INDEX_ADD_PARCEL_FEO));
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_ADD_PARCEL_FEO));
            rd.forward(request, response);
        }
        
    }


}
