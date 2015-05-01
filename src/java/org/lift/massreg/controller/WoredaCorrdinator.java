package org.lift.massreg.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.lift.massreg.dao.*;
import org.lift.massreg.entity.*;
import org.lift.massreg.util.*;
import org.lift.massreg.dto.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class WoredaCorrdinator {

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
        if(request.getSession().getAttribute("areaCalculated")==null){
            String kebele = request.getSession().getAttribute("kebele").toString();
            if(kebele==null || kebele.equalsIgnoreCase("ALL")){
                Option[] opt = MasterRepository.getInstance().getAllKebeles();
                for (int i = 0; i < opt.length; i++) {
                    MasterRepository.getInstance().updateParcelArea(Long.parseLong(opt[i].getKey()));
                }
            }
            MasterRepository.getInstance().updateParcelArea(Long.parseLong(kebele));
            request.getSession().setAttribute("areaCalculated",true);
        }
        ArrayList<Parcel> parcels = MasterRepository.getInstance().getALLParcels(request.getSession().getAttribute("kebele").toString(), request.getSession().getAttribute("status").toString());
        request.setAttribute("parcels", parcels);
        request.setAttribute("page", IOC.getPage(Constants.INDEX_WELCOME_WC));
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
        ArrayList<Parcel> parcels = MasterRepository.getInstance().getALLParcels(request.getSession().getAttribute("kebele").toString(), request.getSession().getAttribute("status").toString());
        request.setAttribute("parcels", parcels);
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_WELCOME_WC));
        rd.forward(request, response);
    }

    /**
     * Handlers request for getting the view pacel form
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void viewParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getParameter("upi"), CommonStorage.getConfirmedStage());
        request.setAttribute("parcel", parcel);
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_PARCEL_WC));
        rd.forward(request, response);
    }

    /**
     * Handlers request to approve a parcel
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void approveParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getParameter("upi"), CommonStorage.getConfirmedStage());
        parcel.approve();
        welcomeForm(request, response);
    }

    /**
     * Handlers request to get Certificate Printing Check List
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void printCheckList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<CertificatePrintingCheckList> list = MasterRepository.getInstance().getCertificatePrintingCheckList(request.getParameter("kebele"));
        request.setAttribute("list", list);
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_CERTIFICATE_PRINTING_CHECKLIST_WC));
        rd.forward(request, response);

    }

    /**
     * Handlers request to get Certificate
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void printCertificate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Parcel> list = MasterRepository.getInstance().getALLParcelsInApproved(request.getParameter("kebele"));
        request.setAttribute("list", list);
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_PRINT_CERTIFCATE_WC));
        rd.forward(request, response);

    }

    protected static void getImage(HttpServletRequest request, HttpServletResponse response) {
        try {
            File f = new File(request.getParameter("name"));

            FileInputStream in = new FileInputStream(f);
            byte[] data = new byte[(int) f.length()];
            in.read(data);
            in.close();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + request.getParameter("name"));
            response.setHeader("Cache-Control", "no cache");
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
        } catch (IOException ex) {
        }
    }

    protected static void updateSettings(HttpServletRequest request, HttpServletResponse response) {
        File file;
        int maxFileSize = 5000 * 1024;
        int maxMemSize = 5000 * 1024;
        String filePath = CommonStorage.getCurrentUser(request).getUserId() + ".png";
        // Verify the content type
        String contentType = request.getContentType();
        if ((contentType.contains("multipart/form-data"))) {
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // maximum size that will be stored in memory
            factory.setSizeThreshold(maxMemSize);
            // Location to save data that is larger than maxMemSize.
            factory.setRepository(new File(filePath));
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            // maximum file size to be uploaded.
            upload.setSizeMax(maxFileSize);
            try {
                // Parse the request to get file items.
                List fileItems = upload.parseRequest(request);
                // Process the uploaded file items
                Iterator i = fileItems.iterator();

                if (i.hasNext()) {
                    FileItem fi = (FileItem) i.next();
                    if (!fi.isFormField()) {
                        /* Get the uploaded file parameters*/
                        String fileName = fi.getName();

                        /* Write the file*/
                        file = new File(filePath);
                        fi.write(file);
                    }
                }
                response.sendRedirect(request.getContextPath());
            } catch (Exception ex) {
                System.out.println(ex);
            }

        } else {
            System.err.println("File not found");
        }
    }

    static String getExtention(String fileName) {
        return ".png";//fileName.substring(fileName.lastIndexOf("."));
    }
}
