package org.lift.massreg.util;

import java.io.*;
import java.security.*;
import java.security.cert.*;
import java.sql.Date;
import java.time.*;
import java.util.*;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.dsig.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.xssf.usermodel.*;
import org.lift.massreg.dao.*;
import org.lift.massreg.dto.*;

/**
 *
 * @author Yoseph
 */
public class ReportUtil {

    public static void sign(String fileName) {
        try (FileInputStream fis = new FileInputStream(new File(CommonStorage.getKeystoreFilePath()))) {
            KeyStore keystore = KeyStore.getInstance("jks");
            keystore.load(fis, CommonStorage.getKeyStorePassword());
            fis.close();
            // extracting private key and certificate
            String alias = "massreg.liftet.org"; // alias of the keystore entry
            Key keyPair = keystore.getKey(alias, CommonStorage.getKeyStorePassword());
            X509Certificate x509 = (X509Certificate) keystore.getCertificate(alias);

            KeyStore.PrivateKeyEntry keyEntry
                    = (KeyStore.PrivateKeyEntry) keystore.getEntry("massreg.liftet.org", new KeyStore.PasswordProtection(CommonStorage.getKeyStorePassword()));
            // filling the SignatureConfig entries (minimum fields, more options are available ...)
            SignatureConfig signatureConfig = new SignatureConfig();
            signatureConfig.setKey(keyEntry.getPrivateKey());
            signatureConfig.setSigningCertificateChain(Collections.singletonList(x509));
            OPCPackage pkg = OPCPackage.open(new File(fileName), PackageAccess.READ_WRITE);
            signatureConfig.setOpcPackage(pkg);

            // adding the signature document to the package
            SignatureInfo si = new SignatureInfo();
            si.setSignatureConfig(signatureConfig);
            si.confirmSignature();
            // optionally verify the generated signature
            boolean b = si.verifySignature();
            assert (b);
            // write the changes back to disc
            pkg.close();

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableEntryException | InvalidFormatException | XMLSignatureException | MarshalException ex) {
            System.err.println(ex);
        }

    }

    private static void refreshViews() {
        MasterRepository.getInstance().refreshView("malecoholdings");
        MasterRepository.getInstance().refreshView("husbandholders");
        MasterRepository.getInstance().refreshView("singlemaleholder");

        MasterRepository.getInstance().refreshView("femalecoholdings");
        MasterRepository.getInstance().refreshView("wifeholders");
        MasterRepository.getInstance().refreshView("singlefemaleholder");

        MasterRepository.getInstance().refreshView("committedparcels");

        MasterRepository.getInstance().refreshView("marriedfemale");
        MasterRepository.getInstance().refreshView("marriedcouple");
    }

    public static Workbook generateTimeBoundReport(Date fromDate, Date toDate, String fileName) {
        refreshViews();
        Workbook wb = new XSSFWorkbook();
        try {
            CreationHelper createHelper = wb.getCreationHelper();
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                Option[] kebeles = MasterRepository.getInstance().getAllKebelesTextValue();
                for (Option kebele : kebeles) {
                    long result = MasterRepository.getInstance().getCountOfAllCommittedParcels(fromDate, toDate, kebele.getKey());
                    if (result > 0) {
                        Sheet tempSheet = wb.createSheet(kebele.getValue());
                        Row row0 = tempSheet.createRow((short) 0);
                        row0.createCell(7).setCellValue(createHelper.createRichTextString("Timeframe of report"));

                        Row row1 = tempSheet.createRow((short) 1);
                        // Header
                        row1.createCell(0).setCellValue(createHelper.createRichTextString("Woreda Name"));
                        row1.createCell(1).setCellValue(createHelper.createRichTextString("Kebele Name"));
                        row1.createCell(2).setCellValue(createHelper.createRichTextString("Kebele Code"));
                        row1.createCell(3).setCellValue(createHelper.createRichTextString("Land Holder Type"));
                        row1.createCell(4).setCellValue(createHelper.createRichTextString("Number of non committed parcels in timeframe"));
                        row1.createCell(5).setCellValue(createHelper.createRichTextString("Number of committed parcels in timeframe"));
                        row1.createCell(6).setCellValue(createHelper.createRichTextString("Report generated on"));
                        row1.createCell(7).setCellValue(createHelper.createRichTextString("Start"));
                        row1.createCell(8).setCellValue(createHelper.createRichTextString("End"));

                        row1.createCell(9).setCellValue(createHelper.createRichTextString("Total number of non committed parcels"));
                        row1.createCell(10).setCellValue(createHelper.createRichTextString("Total number of committed parcels"));

                        Row row2 = tempSheet.createRow((short) 2);
                        row2.createCell(0).setCellValue(createHelper.createRichTextString(CommonStorage.getCurrentWoredaName()));
                        row2.createCell(1).setCellValue(createHelper.createRichTextString(kebele.getValue()));
                        row2.createCell(2).setCellValue(kebele.getKey());
                        row2.createCell(3).setCellValue(createHelper.createRichTextString("Married Couple"));
                        row2.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHolders(fromDate, toDate, kebele.getKey()));
                        row2.createCell(6).setCellValue(createHelper.createRichTextString((Date.from(Instant.now()).toString())));
                        row2.createCell(7).setCellValue(createHelper.createRichTextString(fromDate.toString()));
                        row2.createCell(8).setCellValue(createHelper.createRichTextString(toDate.toString()));

                        Row row3 = tempSheet.createRow((short) 3);
                        row3.createCell(3).setCellValue(createHelper.createRichTextString("Single Female"));
                        row3.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHolders(fromDate, toDate, kebele.getKey()));

                        Row row4 = tempSheet.createRow((short) 4);
                        row4.createCell(3).setCellValue(createHelper.createRichTextString("Single Male"));
                        row4.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHolders(fromDate, toDate, kebele.getKey()));

                        Row row5 = tempSheet.createRow((short) 5);
                        row5.createCell(3).setCellValue(createHelper.createRichTextString("Orphans"));
                        row5.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHolders(fromDate, toDate, kebele.getKey()));
                        Row row6 = tempSheet.createRow((short) 6);
                        row6.createCell(3).setCellValue(createHelper.createRichTextString("Non Natural"));
                        row6.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHolders(fromDate, toDate, kebele.getKey()));

                        Row row7 = tempSheet.createRow((short) 7);
                        row7.createCell(3).setCellValue(createHelper.createRichTextString("Other"));
                        row7.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypes(fromDate, toDate, kebele.getKey()));

                        Row row8 = tempSheet.createRow((short) 8);
                        row8.createCell(3).setCellValue(createHelper.createRichTextString("Dispute"));
                        row8.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDispute(fromDate, toDate, kebele.getKey()));
                       
                        Row row9 = tempSheet.createRow((short) 9);
                        row8.createCell(3).setCellValue(createHelper.createRichTextString("Deceased"));
                        row8.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDeceasedHolders(fromDate, toDate, kebele.getKey()));

                        Row row10 = tempSheet.createRow((short) 10);
                        row9.createCell(3).setCellValue(createHelper.createRichTextString("All"));
                        row9.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfAllNonCommittedParcels(fromDate, toDate, kebele.getKey()));
                        row9.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfAllCommittedParcels(fromDate, toDate, kebele.getKey()));

                        row9.createCell(9).setCellValue(MasterRepository.getInstance().getCountOfAllNonCommittedParcels(kebele.getKey()));
                        row9.createCell(10).setCellValue(MasterRepository.getInstance().getCountOfAllCommittedParcels(kebele.getKey()));
                        // Sheet Format
                        tempSheet.addMergedRegion(new CellRangeAddress(2, 9, 0, 0));
                        tempSheet.addMergedRegion(new CellRangeAddress(2, 9, 1, 1));
                        tempSheet.addMergedRegion(new CellRangeAddress(2, 9, 2, 2));
                        tempSheet.addMergedRegion(new CellRangeAddress(2, 9, 6, 6));
                        tempSheet.addMergedRegion(new CellRangeAddress(2, 9, 7, 7));
                        tempSheet.addMergedRegion(new CellRangeAddress(2, 9, 8, 8));
                        tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 8));

                        //tempSheet.addMergedRegion(new CellRangeAddress(2, 9, 9, 9));
                        //tempSheet.addMergedRegion(new CellRangeAddress(2, 9, 10, 10));
                        tempSheet.autoSizeColumn(0, true);
                        tempSheet.autoSizeColumn(1, true);
                        tempSheet.autoSizeColumn(2, true);
                        tempSheet.autoSizeColumn(3, true);
                        tempSheet.autoSizeColumn(4, true);
                        tempSheet.autoSizeColumn(5, true);
                        tempSheet.autoSizeColumn(6, true);
                        tempSheet.autoSizeColumn(7, true);
                        tempSheet.autoSizeColumn(8, true);
                        tempSheet.autoSizeColumn(9, true);
                        tempSheet.autoSizeColumn(10, true);

                        CellStyle cellStyle = wb.createCellStyle();
                        Font hSSFFont = wb.createFont();
                        hSSFFont.setFontName("Calibri");
                        hSSFFont.setFontHeightInPoints((short) 11);
                        cellStyle.setWrapText(true);
                        cellStyle.setFont(hSSFFont);

                        row0.setRowStyle(cellStyle);
                        row1.setRowStyle(cellStyle);
                        row2.setRowStyle(cellStyle);
                        row3.setRowStyle(cellStyle);
                        row4.setRowStyle(cellStyle);
                        row5.setRowStyle(cellStyle);
                        row6.setRowStyle(cellStyle);
                        row7.setRowStyle(cellStyle);
                        row8.setRowStyle(cellStyle);
                        row9.setRowStyle(cellStyle);

                        CellStyle boldCellStyle = wb.createCellStyle();
                        boldCellStyle.cloneStyleFrom(cellStyle);
                        hSSFFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
                        hSSFFont.setBold(true);
                        cellStyle.setFont(hSSFFont);
                        row1.setRowStyle(boldCellStyle);
                    }
                }
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return wb;
    }

    public static Workbook generateKebeleReport(long kebele, String kebeleName, String fileName) {
        MasterRepository.getInstance().updateParcelArea(kebele);
        refreshViews();
        Workbook wb = new XSSFWorkbook();
        try {
            CreationHelper createHelper = wb.getCreationHelper();
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {

                // Kebele report sheet
                Sheet kebeleSheet = wb.createSheet(kebeleName);
                // level 1 headers 
                Row row0 = kebeleSheet.createRow((short) 0);
                Row row1 = kebeleSheet.createRow((short) 1);
                Row row2 = kebeleSheet.createRow((short) 2); // Married Couple row
                Row row3 = kebeleSheet.createRow((short) 3); // Single female row
                Row row4 = kebeleSheet.createRow((short) 4); // Single male row
                Row row5 = kebeleSheet.createRow((short) 5); // Orphans row
                Row row6 = kebeleSheet.createRow((short) 6); // Non natural row
                Row row7 = kebeleSheet.createRow((short) 7); // Other row
                Row row8 = kebeleSheet.createRow((short) 8); // Dispute row
                Row row9 = kebeleSheet.createRow((short) 9); // No data row
                Row row10 = kebeleSheet.createRow((short) 10); // All row

                CellStyle alignmentStyleRight = wb.createCellStyle();
                alignmentStyleRight.setAlignment(CellStyle.ALIGN_RIGHT);
                alignmentStyleRight.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

                row0.createCell(11).setCellValue(createHelper.createRichTextString("Other ownership evidence"));
                row0.createCell(13).setCellValue(createHelper.createRichTextString("Land Use"));
                row0.createCell(24).setCellValue(createHelper.createRichTextString("Soil Fertility Status"));
                row0.createCell(27).setCellValue(createHelper.createRichTextString("Landholding Type"));
                row0.createCell(31).setCellValue(createHelper.createRichTextString("Public Organisation"));
                row0.createCell(36).setCellValue(createHelper.createRichTextString("Means of Acquisition"));
                row0.createCell(42).setCellValue(createHelper.createRichTextString("Encumbrances/ Servitude"));
                row0.createCell(44).setCellValue(createHelper.createRichTextString("Physical Impairment"));
                row0.createCell(46).setCellValue(createHelper.createRichTextString("Persons of interest"));
                row0.createCell(51).setCellValue(createHelper.createRichTextString("Guardians"));

                // level 2 headers  
                row1.createCell(0).setCellValue(createHelper.createRichTextString("Woreda Name"));
                row1.createCell(1).setCellValue(createHelper.createRichTextString("Kebele Name"));
                row1.createCell(2).setCellValue(createHelper.createRichTextString("Kebele Code"));
                row1.createCell(3).setCellValue(createHelper.createRichTextString("Land Holder Type"));
                row1.createCell(4).setCellValue(createHelper.createRichTextString("Unique Records"));
                row1.createCell(5).setCellValue(createHelper.createRichTextString("Total Parcels Demarcated"));
                row1.createCell(6).setCellValue(createHelper.createRichTextString("Average Number of Parcels"));
                row1.createCell(7).setCellValue(createHelper.createRichTextString("Total number of non committed parcels"));
                row1.createCell(8).setCellValue(createHelper.createRichTextString("Total number of committed parcels"));
                row1.createCell(9).setCellValue(createHelper.createRichTextString("Total area(Ha)"));
                row1.createCell(10).setCellValue(createHelper.createRichTextString("Average area(Ha)"));
                row1.createCell(11).setCellValue(createHelper.createRichTextString("Court Decision"));
                row1.createCell(12).setCellValue(createHelper.createRichTextString("Tax Receipt"));
                row1.createCell(13).setCellValue(createHelper.createRichTextString("Rain fed annual crops"));
                row1.createCell(14).setCellValue(createHelper.createRichTextString("Rain fed Perennial crops"));
                row1.createCell(15).setCellValue(createHelper.createRichTextString("Irrigated annual crops"));
                row1.createCell(16).setCellValue(createHelper.createRichTextString("Irrigated perennial crops"));
                row1.createCell(17).setCellValue(createHelper.createRichTextString("Grassland/Grazing Land"));
                row1.createCell(18).setCellValue(createHelper.createRichTextString("Shrub land/wood land"));
                row1.createCell(19).setCellValue(createHelper.createRichTextString("Natural Forest"));
                row1.createCell(20).setCellValue(createHelper.createRichTextString("Artificial Forest  (e.g. Eucalyptus plantation, etc)"));
                row1.createCell(21).setCellValue(createHelper.createRichTextString("Wetland"));
                row1.createCell(22).setCellValue(createHelper.createRichTextString("Built-Up  Areas ( Schools, churches, mosques, FTC, etc)"));
                row1.createCell(23).setCellValue(createHelper.createRichTextString("Bare land"));
                row1.createCell(24).setCellValue(createHelper.createRichTextString("High"));
                row1.createCell(25).setCellValue(createHelper.createRichTextString("Medium"));
                row1.createCell(26).setCellValue(createHelper.createRichTextString("Low to Poor"));
                row1.createCell(27).setCellValue(createHelper.createRichTextString("Individual Holding"));
                row1.createCell(28).setCellValue(createHelper.createRichTextString("Commons"));
                row1.createCell(29).setCellValue(createHelper.createRichTextString("Public Organizations"));
                row1.createCell(30).setCellValue(createHelper.createRichTextString("Federal/ Regional Government "));
                row1.createCell(31).setCellValue(createHelper.createRichTextString("Church"));
                row1.createCell(32).setCellValue(createHelper.createRichTextString("Mosque"));
                row1.createCell(33).setCellValue(createHelper.createRichTextString("School"));
                row1.createCell(34).setCellValue(createHelper.createRichTextString("Health Centre"));
                row1.createCell(35).setCellValue(createHelper.createRichTextString("Others"));
                row1.createCell(36).setCellValue(createHelper.createRichTextString("Redistribution"));
                row1.createCell(37).setCellValue(createHelper.createRichTextString("Inheritance"));
                row1.createCell(38).setCellValue(createHelper.createRichTextString("Gift"));
                row1.createCell(39).setCellValue(createHelper.createRichTextString("Divorce"));
                row1.createCell(40).setCellValue(createHelper.createRichTextString("Reallocation"));
                row1.createCell(41).setCellValue(createHelper.createRichTextString("OTHERS"));
                row1.createCell(42).setCellValue(createHelper.createRichTextString("Right of Way"));
                row1.createCell(43).setCellValue(createHelper.createRichTextString("Terms of Rights/Restrictions"));
                row1.createCell(44).setCellValue(createHelper.createRichTextString("Yes"));
                row1.createCell(45).setCellValue(createHelper.createRichTextString("No"));
                row1.createCell(46).setCellValue(createHelper.createRichTextString("Total number of parcels with P.I"));
                row1.createCell(47).setCellValue(createHelper.createRichTextString("Total Number of P.I"));
                row1.createCell(48).setCellValue(createHelper.createRichTextString("Average Number of P.I  parcels "));
                row1.createCell(49).setCellValue(createHelper.createRichTextString("Male"));
                row1.createCell(50).setCellValue(createHelper.createRichTextString("Female"));
                row1.createCell(51).setCellValue(createHelper.createRichTextString("Total number of parcels with Guardians"));
                row1.createCell(52).setCellValue(createHelper.createRichTextString("Total Number of Guardians"));
                row1.createCell(53).setCellValue(createHelper.createRichTextString("Average Number of Guardians to relevent parcels"));
                row1.createCell(54).setCellValue(createHelper.createRichTextString("Male"));
                row1.createCell(55).setCellValue(createHelper.createRichTextString("Female"));
                row1.createCell(56).setCellValue(createHelper.createRichTextString("Date Kebele commenced entry"));
                row1.createCell(57).setCellValue(createHelper.createRichTextString("Date Kebele completed entry"));
                row2.createCell(0).setCellValue(createHelper.createRichTextString(CommonStorage.getCurrentWoredaName()));
                row2.createCell(1).setCellValue(createHelper.createRichTextString(kebeleName));
                row2.createCell(2).setCellValue(kebele);

                //Land Holder Type
                row2.createCell(3).setCellValue(createHelper.createRichTextString("Married Couple"));
                row3.createCell(3).setCellValue(createHelper.createRichTextString("Single Female"));
                row4.createCell(3).setCellValue(createHelper.createRichTextString("Single Male"));
                row5.createCell(3).setCellValue(createHelper.createRichTextString("Orphans"));
                row6.createCell(3).setCellValue(createHelper.createRichTextString("Non Natural"));
                row7.createCell(3).setCellValue(createHelper.createRichTextString("Other"));
                row8.createCell(3).setCellValue(createHelper.createRichTextString("Dispute"));
                row9.createCell(3).setCellValue(createHelper.createRichTextString("No data"));
                row10.createCell(3).setCellValue(createHelper.createRichTextString("All"));
                //Unique Records
                row2.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfMarriedCoupleHolders(kebele, "stage = 4"));
                row3.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfSingleFemaleHolders(kebele, "stage = 4"));
                row4.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfSingleMaleHolders(kebele, "stage = 4"));
                row5.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfOrphanHolders(kebele, "stage = 4"));
                row6.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfNonNaturalHolders(kebele, "stage = 4"));
                row7.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfHoldersOfOtherHoldingTypes(kebele, "stage = 4"));
                row8.createCell(4).setCellValue(MasterRepository.getInstance().getNumberOfPeopleAllDisputes(kebele, "stage = 4"));
                row9.createCell(4).setCellValue(createHelper.createRichTextString("--")/*Data Not available*/);
                row10.createCell(4).setCellValue(createHelper.createRichTextString("--")/*Data Not available*/);
                // Total Parcels Demarcated
                row2.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row8.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfDemarcatedParcels(kebele));
                // Average Number of Parcels
                row2.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerMarriedCoupleHolder(kebele, "stage = 4"));
                row3.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerSingleFemaleHolder(kebele, "stage = 4"));
                row4.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerSingleMaleHolder(kebele, "stage = 4"));
                row5.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerOrphanHolder(kebele, "stage = 4"));
                row6.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerNonNaturalHolder(kebele, "stage = 4"));
                row7.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerOtherHoldingType(kebele, "stage = 4"));
                row8.createCell(6).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(6).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(6).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                //Total number of non committed parcels
                row2.createCell(7).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(7).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(7).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(7).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(7).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(7).setCellValue(createHelper.createRichTextString("---"/*Value not known*/));
                row8.createCell(7).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(7).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(7).setCellValue(MasterRepository.getInstance().getCountOfAllNonCommittedParcels(kebele));
                // Total number of committed parcels
                row2.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHolders(kebele, "stage = 4"));
                row3.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHolders(kebele, "stage = 4"));
                row4.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHolders(kebele, "stage = 4"));
                row5.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHolders(kebele, "stage = 4"));
                row6.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHolders(kebele, "stage = 4"));
                row7.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypes(kebele, "stage = 4"));
                row8.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDispute(kebele, "stage = 4"));
                row9.createCell(8).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfAllCommittedParcels(kebele, "stage = 4"));
                //Total Area
                row2.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderMarriedCoupleHolders(kebele, "stage = 4"));
                row3.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderSingleFemaleHolders(kebele, "stage = 4"));
                row4.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderSingleMaleHolders(kebele, "stage = 4"));
                row5.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderOrphanHolders(kebele, "stage = 4"));
                row6.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderNonNaturalHolders(kebele, "stage = 4"));
                row7.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderOtherHoldingTypes(kebele, "stage = 4"));
                row8.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderDispute(kebele, "stage = 4"));
                row9.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsWithNoData(kebele, "stage = 4"));
                row10.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfAllParcels(kebele));
                // Average area
                row2.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderMarriedCoupleHolders(kebele, "stage = 4"));
                row3.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderSingleFemaleHolders(kebele, "stage = 4"));
                row4.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderSingleMaleHolders(kebele, "stage = 4"));
                row5.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderOrphanHolders(kebele, "stage = 4"));
                row6.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderNonNaturalHolders(kebele, "stage = 4"));
                row7.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderOtherHoldingTypes(kebele, "stage = 4"));
                row8.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderDispute(kebele, "stage = 4"));
                row9.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsWithNoData(kebele, "stage = 4"));
                row10.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfAllParcels(kebele));
                //......
                //Court Decision
                row2.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage = 4"));
                row3.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage = 4"));
                row4.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage = 4"));
                row5.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage = 4"));
                row6.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage = 4"));
                row7.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage = 4"));
                row8.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage = 4"));
                row9.createCell(11).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage = 4"));
                //Tax Receipt
                row2.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage = 4"));
                row3.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage = 4"));
                row4.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage = 4"));
                row5.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage = 4"));
                row6.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage = 4"));
                row7.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage = 4"));
                row8.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage = 4"));
                row9.createCell(12).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage = 4"));
                //Rain fed annual crops
                row2.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage = 4"));
                row3.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage = 4"));
                row4.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage = 4"));
                row5.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage = 4"));
                row6.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage = 4"));
                row7.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage = 4"));
                row8.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage = 4"));
                row9.createCell(13).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage = 4"));
                row2.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage = 4"));
                row3.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage = 4"));
                row4.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage = 4"));
                row5.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage = 4"));
                row6.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage = 4"));
                row7.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage = 4"));
                row8.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage = 4"));
                row9.createCell(14).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage = 4"));
                row2.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage = 4"));
                row3.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage = 4"));
                row4.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage = 4"));
                row5.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage = 4"));
                row6.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage = 4"));
                row7.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage = 4"));
                row8.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage = 4"));
                row9.createCell(15).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage = 4"));
                row2.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage = 4"));
                row3.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage = 4"));
                row4.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage = 4"));
                row5.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage = 4"));
                row6.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage = 4"));
                row7.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage = 4"));
                row8.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage = 4"));
                row9.createCell(16).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage = 4"));

                row2.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage = 4"));
                row3.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage = 4"));
                row4.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage = 4"));
                row5.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage = 4"));
                row6.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage = 4"));
                row7.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage = 4"));
                row8.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage = 4"));
                row9.createCell(17).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage = 4"));

                row2.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage = 4"));
                row3.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage = 4"));
                row4.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage = 4"));
                row5.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage = 4"));
                row6.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage = 4"));
                row7.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage = 4"));
                row8.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage = 4"));
                row9.createCell(18).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage = 4"));

                row2.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage = 4"));
                row3.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage = 4"));
                row4.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage = 4"));
                row5.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage = 4"));
                row6.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage = 4"));
                row7.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage = 4"));
                row8.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage = 4"));
                row9.createCell(19).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage = 4"));

                row2.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage = 4"));
                row3.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage = 4"));
                row4.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage = 4"));
                row5.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage = 4"));
                row6.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage = 4"));
                row7.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage = 4"));
                row8.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage = 4"));
                row9.createCell(20).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage = 4"));

                row2.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage = 4"));
                row3.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage = 4"));
                row4.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage = 4"));
                row5.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage = 4"));
                row6.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage = 4"));
                row7.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage = 4"));
                row8.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage = 4"));
                row9.createCell(21).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage = 4"));

                row2.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage = 4"));
                row3.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage = 4"));
                row4.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage = 4"));
                row5.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage = 4"));
                row6.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage = 4"));
                row7.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage = 4"));
                row8.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage = 4"));
                row9.createCell(22).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage = 4"));

                row2.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage = 4"));
                row3.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage = 4"));
                row4.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage = 4"));
                row5.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage = 4"));
                row6.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage = 4"));
                row7.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage = 4"));
                row8.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage = 4"));
                row9.createCell(23).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage = 4"));

                row2.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage = 4"));
                row3.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage = 4"));
                row4.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage = 4"));
                row5.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage = 4"));
                row6.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage = 4"));
                row7.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage = 4"));
                row8.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage = 4"));
                row9.createCell(24).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage = 4"));

                row2.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage = 4"));
                row3.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage = 4"));
                row4.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage = 4"));
                row5.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage = 4"));
                row6.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage = 4"));
                row7.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage = 4"));
                row8.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage = 4"));
                row9.createCell(25).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage = 4"));

                row2.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage = 4"));
                row3.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage = 4"));
                row4.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage = 4"));
                row5.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage = 4"));
                row6.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage = 4"));
                row7.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage = 4"));
                row8.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage = 4"));
                row9.createCell(26).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage = 4"));

                row2.createCell(27).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(28).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(29).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(30).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(31).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(32).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(33).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(34).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(35).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage = 4"));
                row2.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage = 4"));
                row2.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage = 4"));
                row2.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage = 4"));
                row2.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage = 4"));
                row2.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage = 4"));
                row2.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage = 4"));
                row2.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage = 4"));
                row2.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfMarriedCoupleHoldersWithPhysicalImpairment(kebele, "stage = 4"));
                row2.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfMarriedCoupleHoldersWithoutPhysicalImpairment(kebele, "stage = 4"));

                row2.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderMarriedCoupleHolders(kebele, "stage = 4"));
                row2.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderMarriedCoupleHolders(kebele, "stage = 4"));
                row2.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderMarriedCoupleHolders(kebele, "stage = 4"));
                row2.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderMarriedCoupleHolders(kebele, "stage = 4"));
                row2.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderMarriedCoupleHolders(kebele, "stage = 4"));
                row2.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardianUnderMarriedCoupleHolders(kebele, "stage = 4"));
                row2.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderMarriedCoupleHolders(kebele, "stage = 4"));
                row2.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderMarriedCoupleHolders(kebele, "stage = 4"));
                row2.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderMarriedCoupleHolders(kebele, "stage = 4"));
                row2.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderMarriedCoupleHolders(kebele, "stage = 4"));
                row2.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForMarriedHolders(kebele, "stage = 4"));
                row2.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForMarriedHolders(kebele, "stage = 4"));

                row3.createCell(27).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(28).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(29).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(30).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(31).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(32).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(33).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(34).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(35).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage = 4"));
                row3.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage = 4"));
                row3.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage = 4"));
                row3.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage = 4"));
                row3.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage = 4"));
                row3.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage = 4"));
                row3.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage = 4"));
                row3.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage = 4"));
                row3.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfSingleFemaleHoldersWithPhysicalImpairment(kebele, "stage = 4"));
                row3.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfSingleFemaleHoldersWithoutPhysicalImpairment(kebele, "stage = 4"));
                row3.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderSingleFemaleHolders(kebele, "stage = 4"));
                row3.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderSingleFemaleHolders(kebele, "stage = 4"));
                row3.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderSingleFemaleHolders(kebele, "stage = 4"));
                row3.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderSingleFemaleHolders(kebele, "stage = 4"));
                row3.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderSingleFemaleHolders(kebele, "stage = 4"));
                row3.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderSingleFemaleHolders(kebele, "stage = 4"));
                row3.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderSingleFemaleHolders(kebele, "stage = 4"));
                row3.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderSingleFemaleHolders(kebele, "stage = 4"));
                row3.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderSingleFemaleHolders(kebele, "stage = 4"));
                row3.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderSingleFemaleHolders(kebele, "stage = 4"));
                row3.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForSingleFemaleHolders(kebele, "stage = 4"));
                row3.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForSingleFemaleHolders(kebele, "stage = 4"));

                row4.createCell(27).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(28).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(29).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(30).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(31).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(32).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(33).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(34).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(35).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage = 4"));
                row4.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage = 4"));
                row4.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage = 4"));
                row4.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage = 4"));
                row4.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage = 4"));
                row4.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage = 4"));
                row4.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage = 4"));
                row4.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage = 4"));
                row4.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfSingleMaleHoldersWithPhysicalImpairment(kebele, "stage = 4"));
                row4.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfSingleMaleHoldersWithoutPhysicalImpairment(kebele, "stage = 4"));
                row4.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderSingleMaleHolders(kebele, "stage = 4"));
                row4.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderSingleMaleHolders(kebele, "stage = 4"));
                row4.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderSingleMaleHolders(kebele, "stage = 4"));
                row4.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderSingleMaleHolders(kebele, "stage = 4"));
                row4.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderSingleMaleHolders(kebele, "stage = 4"));

                row4.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderSingleMaleHolders(kebele, "stage = 4"));
                row4.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderSingleMaleHolders(kebele, "stage = 4"));
                row4.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderSingleMaleHolders(kebele, "stage = 4"));
                row4.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderSingleMaleHolders(kebele, "stage = 4"));
                row4.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderSingleMaleHolders(kebele, "stage = 4"));
                row4.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForSingleMaleHolders(kebele, "stage = 4"));
                row4.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForSingleMaleHolders(kebele, "stage = 4"));

                row5.createCell(27).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(28).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(29).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(30).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(31).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(32).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(33).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(34).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(35).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage = 4"));
                row5.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage = 4"));
                row5.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage = 4"));
                row5.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage = 4"));
                row5.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage = 4"));
                row5.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage = 4"));
                row5.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage = 4"));
                row5.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage = 4"));
                row5.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfOrphanHoldersWithPhysicalImpairment(kebele, "stage = 4"));
                row5.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfOrphanHoldersWithoutPhysicalImpairment(kebele, "stage = 4"));
                row5.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderOrphanHolders(kebele, "stage = 4"));
                row5.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderOrphanHolders(kebele, "stage = 4"));
                row5.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderOrphanHolders(kebele, "stage = 4"));
                row5.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderOrphanHolders(kebele, "stage = 4"));
                row5.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderOrphanHolders(kebele, "stage = 4"));

                row5.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderOrphanHolders(kebele, "stage = 4"));
                row5.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderOrphanHolders(kebele, "stage = 4"));
                row5.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderSingleMaleHolders(kebele, "stage = 4"));
                row5.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderOrphanHolders(kebele, "stage = 4"));
                row5.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderOrphanHolders(kebele, "stage = 4"));
                row5.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForOrphanHolders(kebele, "stage = 4"));
                row5.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForOrphanHolders(kebele, "stage = 4"));

                row6.createCell(27).setCellValue(createHelper.createRichTextString("---")/*Does not make sense*/);
                row6.createCell(28).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByHoldingType(kebele, (byte) 2 /*Communal*/, "stage = 4"));
                row6.createCell(29).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByHoldingType(kebele, (byte) 3 /*Public/Instituional*/, "stage = 4"));
                row6.createCell(30).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByHoldingType(kebele, (byte) 4 /*State*/, "stage = 4"));
                row6.createCell(31).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 2 /*Church*/, "stage = 4"));
                row6.createCell(32).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 3 /*Mosque*/, "stage = 4"));
                row6.createCell(33).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 1 /*School*/, "stage = 4"));
                row6.createCell(34).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 4 /*Health Center*/, "stage = 4"));
                row6.createCell(35).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 5 /*Other*/, "stage = 4"));
                row6.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage = 4"));
                row6.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage = 4"));
                row6.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage = 4"));
                row6.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage = 4"));
                row6.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage = 4"));
                row6.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage = 4"));
                row6.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage = 4"));
                row6.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage = 4"));
                row6.createCell(44).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(45).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(46).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(47).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(48).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(49).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(50).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(51).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(52).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(53).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(54).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForNonNaturalHolders(kebele, "stage = 4"));
                row6.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForNonNaturalHolders(kebele, "stage = 4"));

                row7.createCell(27).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(28).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(29).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(30).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(31).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(32).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(33).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(34).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(35).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage = 4"));
                row7.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage = 4"));
                row7.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage = 4"));
                row7.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage = 4"));
                row7.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage = 4"));
                row7.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage = 4"));
                row7.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage = 4"));
                row7.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage = 4"));
                row7.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfOtherHoldingTypesWithPhysicalImpairment(kebele, "stage = 4"));
                row7.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfOtherHoldingTypesWithoutPhysicalImpairment(kebele, "stage = 4"));
                row7.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderOtherHoldingTypes(kebele, "stage = 4"));
                row7.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderOtherHoldingTypes(kebele, "stage = 4"));
                row7.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderOtherHoldingTypes(kebele, "stage = 4"));
                row7.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderOtherHoldingTypes(kebele, "stage = 4"));
                row7.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderOtherHoldingTypes(kebele, "stage = 4"));

                row7.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderOtherHoldingTypes(kebele, "stage = 4"));
                row7.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderOtherHoldingTypes(kebele, "stage = 4"));
                row7.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderOtherHoldingTypes(kebele, "stage = 4"));
                row7.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderOtherHoldingTypes(kebele, "stage = 4"));
                row7.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderOtherHoldingTypes(kebele, "stage = 4"));
                row7.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForOtherHoldingTypes(kebele, "stage = 4"));
                row7.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForOtherHoldingTypes(kebele, "stage = 4"));

                row8.createCell(27).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByHoldingType(kebele, (byte) 1 /*Individual*/, "stage = 4"));
                row8.createCell(28).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByHoldingType(kebele, (byte) 2 /*Communal*/, "stage = 4"));
                row8.createCell(29).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByHoldingType(kebele, (byte) 3 /*Public/Instituional*/, "stage = 4"));
                row8.createCell(30).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByHoldingType(kebele, (byte) 4 /*State*/, "stage = 4"));
                row8.createCell(31).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 2 /*Church*/, "stage = 4"));
                row8.createCell(32).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 3 /*Mosque*/, "stage = 4"));
                row8.createCell(33).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 1 /*School*/, "stage = 4"));
                row8.createCell(34).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 4 /*Health Center*/, "stage = 4"));
                row8.createCell(35).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 5 /*Other*/, "stage = 4"));
                row8.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage = 4"));
                row8.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage = 4"));
                row8.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage = 4"));
                row8.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage = 4"));
                row8.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage = 4"));
                row8.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage = 4"));
                row8.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage = 4"));
                row8.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage = 4"));
                row8.createCell(44).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row8.createCell(45).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row8.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderDispute(kebele, "stage = 4"));
                row8.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestForParcelsWithDispute(kebele, "stage = 4"));
                row8.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderDispute(kebele, "stage = 4"));
                row8.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestForParcelsWithDispute(kebele, "stage = 4"));
                row8.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestForParcelsWithDispute(kebele, "stage = 4"));

                row8.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderDispute(kebele, "stage = 4"));
                row8.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsWithDispute(kebele, "stage = 4"));
                row8.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderDispute(kebele, "stage = 4"));
                row8.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsWithDispute(kebele, "stage = 4"));
                row8.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsWithDispute(kebele, "stage = 4"));
                row8.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForParcelsWithDispute(kebele, "stage = 4"));
                row8.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForParcelsWithDispute(kebele, "stage = 4"));

                row9.createCell(27).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(28).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(29).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(30).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(31).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(32).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(33).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(34).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(35).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(36).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(37).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(38).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(39).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(40).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(41).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(42).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(43).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(44).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(45).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(46).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(47).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(48).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(49).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(50).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(51).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(52).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(53).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(54).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(55).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(56).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(57).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);

                row10.createCell(27).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByHoldingType(kebele, (byte) 1 /*Individual*/, "stage = 4"));
                row10.createCell(28).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByHoldingType(kebele, (byte) 2 /*Communal*/, "stage = 4"));
                row10.createCell(29).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByHoldingType(kebele, (byte) 3 /*Public/Instituional*/, "stage = 4"));
                row10.createCell(30).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByHoldingType(kebele, (byte) 4 /*State*/, "stage = 4"));
                row10.createCell(31).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 2 /*Church*/, "stage = 4"));
                row10.createCell(32).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 3 /*Mosque*/, "stage = 4"));
                row10.createCell(33).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 1 /*School*/, "stage = 4"));
                row10.createCell(34).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 4 /*Health Center*/, "stage = 4"));
                row10.createCell(35).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 5 /*Other*/, "stage = 4"));
                row10.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage = 4"));
                row10.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage = 4"));
                row10.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage = 4"));
                row10.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage = 4"));
                row10.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage = 4"));
                row10.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage = 4"));
                row10.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage = 4"));
                row10.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage = 4"));
                row10.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfAllHoldersWithPhysicalImpairment(kebele, "stage = 4"));
                row10.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfAllHoldersWithoutPhysicalImpairment(kebele, "stage = 4"));
                row10.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsWithPersonsOfInterest(kebele, "stage = 4"));
                row10.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestForAllParcels(kebele, "stage = 4"));
                row10.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelForAllParcels(kebele, "stage = 4"));
                row10.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestForAllParcels(kebele, "stage = 4"));
                row10.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestForAllParcels(kebele, "stage = 4"));

                row10.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsWithGuardians(kebele, "stage = 4"));
                row10.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForAllParcels(kebele, "stage = 4"));
                row10.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelForAllParcels(kebele, "stage = 4"));
                row10.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForAllParcels(kebele, "stage = 4"));
                row10.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForAllParcels(kebele, "stage = 4"));
                row10.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForAllParcels(kebele));
                row10.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForAllParcels(kebele));

                // Format the sheet
                CellStyle cellStyle = wb.createCellStyle();
                Font hSSFFont = wb.createFont();
                hSSFFont.setFontName("Calibri");
                hSSFFont.setFontHeightInPoints((short) 11);
                hSSFFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

                cellStyle.setWrapText(true);
                cellStyle.setFont(hSSFFont);

                for (int x = 0; x < 58/*Number of columns*/; x++) {
                    kebeleSheet.autoSizeColumn(x, true);
                }

                CellStyle boldCellStyle = wb.createCellStyle();
                boldCellStyle.cloneStyleFrom(cellStyle);
                hSSFFont.setBold(true);
                boldCellStyle.setFont(hSSFFont);
                row1.setRowStyle(cellStyle);

                // Merge region
                kebeleSheet.addMergedRegion(new CellRangeAddress(2, 10, 0, 0));
                kebeleSheet.addMergedRegion(new CellRangeAddress(2, 10, 1, 1));
                kebeleSheet.addMergedRegion(new CellRangeAddress(2, 10, 2, 2));

                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 23));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 24, 26));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 27, 30));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 31, 35));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 36, 41));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 42, 43));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 44, 45));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 46, 50));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 51, 55));

                cellStyle = wb.createCellStyle();
                cellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                row0.setRowStyle(cellStyle);

                /// Dispute report
                Sheet disputeSheet = wb.createSheet("Disputes");
                row0 = disputeSheet.createRow((short) 0);
                row0.createCell(11).setCellValue(createHelper.createRichTextString("Forum where the dispute is handled"));

                row1 = disputeSheet.createRow((short) 1);
                row1.createCell(1).setCellValue(createHelper.createRichTextString("Number of Disputes"));
                row1.createCell(2).setCellValue(createHelper.createRichTextString("Number of People"));
                row1.createCell(3).setCellValue(createHelper.createRichTextString("Number of Holders"));
                row1.createCell(4).setCellValue(createHelper.createRichTextString("Number of Disputants"));
                row1.createCell(5).setCellValue(createHelper.createRichTextString("Married Couple"));
                row1.createCell(6).setCellValue(createHelper.createRichTextString("Single Female"));
                row1.createCell(7).setCellValue(createHelper.createRichTextString("Single Male"));
                row1.createCell(8).setCellValue(createHelper.createRichTextString("Orphans"));
                row1.createCell(9).setCellValue(createHelper.createRichTextString("Non Natural"));
                row1.createCell(10).setCellValue(createHelper.createRichTextString("Other"));
                row1.createCell(11).setCellValue(createHelper.createRichTextString("Elder’s Committee"));
                row1.createCell(12).setCellValue(createHelper.createRichTextString("KLAC"));
                row1.createCell(13).setCellValue(createHelper.createRichTextString("Woreda Court"));
                row1.createCell(14).setCellValue(createHelper.createRichTextString("Others"));

                row2 = disputeSheet.createRow((short) 2);
                row2.createCell(0).setCellValue(createHelper.createRichTextString("Inheritance"));
                row2.createCell(1).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByType(kebele, (byte) 1 /*Inheritance*/, "stage = 4"));
                row2.createCell(2).setCellValue(MasterRepository.getInstance().getNumberOfPeopleInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage = 4"));
                row2.createCell(3).setCellValue(MasterRepository.getInstance().getNumberOfHoldersInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage = 4"));
                row2.createCell(4).setCellValue(MasterRepository.getInstance().getNumberOfDisputantsInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage = 4"));
                row2.createCell(5).setCellValue(MasterRepository.getInstance().getNumberOfMarriedCoupleHoldersInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage = 4"));
                row2.createCell(6).setCellValue(MasterRepository.getInstance().getNumberOfSingleFemaleHoldersInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage = 4"));
                row2.createCell(7).setCellValue(MasterRepository.getInstance().getNumberOfSingleMaleHoldersInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage = 4"));
                row2.createCell(8).setCellValue(MasterRepository.getInstance().getNumberOfOrphanHoldersInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage = 4"));
                row2.createCell(9).setCellValue(MasterRepository.getInstance().getNumberOfNonNaturalHoldersInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage = 4"));
                row2.createCell(10).setCellValue(MasterRepository.getInstance().getNumberOfOtherHoldingTypesInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage = 4"));
                row2.createCell(11).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 1 /*Inheritance*/, (byte) 1 /*Elders Committee*/, "stage = 4"));
                row2.createCell(12).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 1 /*Inheritance*/, (byte) 2 /*Kebele Land Administration Committee*/, "stage = 4"));
                row2.createCell(13).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 1 /*Inheritance*/, (byte) 3 /*Woreda Court*/, "stage = 4"));
                row2.createCell(14).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 1 /*Inheritance*/, (byte) 4 /*Others*/, "stage = 4"));

                row3 = disputeSheet.createRow((short) 3);
                row3.createCell(0).setCellValue(createHelper.createRichTextString("Ownership right"));
                row3.createCell(1).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage = 4"));
                row3.createCell(2).setCellValue(MasterRepository.getInstance().getNumberOfPeopleInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage = 4"));
                row3.createCell(3).setCellValue(MasterRepository.getInstance().getNumberOfHoldersInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage = 4"));
                row3.createCell(4).setCellValue(MasterRepository.getInstance().getNumberOfDisputantsInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage = 4"));
                row3.createCell(5).setCellValue(MasterRepository.getInstance().getNumberOfMarriedCoupleHoldersInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage = 4"));
                row3.createCell(6).setCellValue(MasterRepository.getInstance().getNumberOfSingleFemaleHoldersInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage = 4"));
                row3.createCell(7).setCellValue(MasterRepository.getInstance().getNumberOfSingleMaleHoldersInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage = 4"));
                row3.createCell(8).setCellValue(MasterRepository.getInstance().getNumberOfOrphanHoldersInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage = 4"));
                row3.createCell(9).setCellValue(MasterRepository.getInstance().getNumberOfNonNaturalHoldersInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage = 4"));
                row3.createCell(10).setCellValue(MasterRepository.getInstance().getNumberOfOtherHoldingTypesInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage = 4"));
                row3.createCell(11).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, (byte) 1 /*Elders Committee*/, "stage = 4"));
                row3.createCell(12).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, (byte) 2 /*Kebele Land Administration Committee*/, "stage = 4"));
                row3.createCell(13).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, (byte) 3 /*Woreda Court*/, "stage = 4"));
                row3.createCell(14).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, (byte) 4 /*Others*/, "stage = 4"));
                row4 = disputeSheet.createRow((short) 4);
                row4.createCell(0).setCellValue(createHelper.createRichTextString("Boundary"));
                row4.createCell(1).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByType(kebele, (byte) 3 /*"Boundary"*/, "stage = 4"));
                row4.createCell(2).setCellValue(MasterRepository.getInstance().getNumberOfPeopleInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage = 4"));
                row4.createCell(3).setCellValue(MasterRepository.getInstance().getNumberOfHoldersInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage = 4"));
                row4.createCell(4).setCellValue(MasterRepository.getInstance().getNumberOfDisputantsInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage = 4"));
                row4.createCell(5).setCellValue(MasterRepository.getInstance().getNumberOfMarriedCoupleHoldersInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage = 4"));
                row4.createCell(6).setCellValue(MasterRepository.getInstance().getNumberOfSingleFemaleHoldersInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage = 4"));
                row4.createCell(7).setCellValue(MasterRepository.getInstance().getNumberOfSingleMaleHoldersInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage = 4"));
                row4.createCell(8).setCellValue(MasterRepository.getInstance().getNumberOfOrphanHoldersInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage = 4"));
                row4.createCell(9).setCellValue(MasterRepository.getInstance().getNumberOfNonNaturalHoldersInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage = 4"));
                row4.createCell(10).setCellValue(MasterRepository.getInstance().getNumberOfOtherHoldingTypesInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage = 4"));
                row4.createCell(11).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 3 /*"Boundary"*/, (byte) 1 /*Elders Committee*/, "stage = 4"));
                row4.createCell(12).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 3 /*"Boundary"*/, (byte) 2 /*Kebele Land Administration Committee*/, "stage = 4"));
                row4.createCell(13).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 3 /*"Boundary"*/, (byte) 3 /*Woreda Court*/, "stage = 4"));
                row4.createCell(14).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 3 /*"Boundary"*/, (byte) 4 /*Others*/, "stage = 4"));
                row5 = disputeSheet.createRow((short) 5);
                row5.createCell(0).setCellValue(createHelper.createRichTextString("Other"));
                row5.createCell(1).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByType(kebele, (byte) 4 /*Other*/, "stage = 4"));
                row5.createCell(2).setCellValue(MasterRepository.getInstance().getNumberOfPeopleInDisputeByType(kebele, (byte) 4 /*Other*/, "stage = 4"));
                row5.createCell(3).setCellValue(MasterRepository.getInstance().getNumberOfHoldersInDisputeByType(kebele, (byte) 4 /*Other*/, "stage = 4"));
                row5.createCell(4).setCellValue(MasterRepository.getInstance().getNumberOfDisputantsInDisputeByType(kebele, (byte) 4 /*Other*/, "stage = 4"));
                row5.createCell(5).setCellValue(MasterRepository.getInstance().getNumberOfMarriedCoupleHoldersInDisputeByType(kebele, (byte) 4 /*Other*/, "stage = 4"));
                row5.createCell(6).setCellValue(MasterRepository.getInstance().getNumberOfSingleFemaleHoldersInDisputeByType(kebele, (byte) 4 /*Other*/, "stage = 4"));
                row5.createCell(7).setCellValue(MasterRepository.getInstance().getNumberOfSingleMaleHoldersInDisputeByType(kebele, (byte) 4 /*Other*/, "stage = 4"));
                row5.createCell(8).setCellValue(MasterRepository.getInstance().getNumberOfOrphanHoldersInDisputeByType(kebele, (byte) 4 /*Other*/, "stage = 4"));
                row5.createCell(9).setCellValue(MasterRepository.getInstance().getNumberOfNonNaturalHoldersInDisputeByType(kebele, (byte) 4 /*Other*/, "stage = 4"));
                row5.createCell(10).setCellValue(MasterRepository.getInstance().getNumberOfOtherHoldingTypesInDisputeByType(kebele, (byte) 4 /*Other*/, "stage = 4"));
                row5.createCell(11).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 4 /*Other*/, (byte) 1 /*Elders Committee*/, "stage = 4"));
                row5.createCell(12).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 4 /*Other*/, (byte) 2 /*Kebele Land Administration Committee*/, "stage = 4"));
                row5.createCell(13).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 4 /*Other*/, (byte) 3 /*Woreda Court*/, "stage = 4"));
                row5.createCell(14).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 4 /*Other*/, (byte) 4 /*Others*/, "stage = 4"));
                row6 = disputeSheet.createRow((short) 6);
                row6.createCell(0).setCellValue(createHelper.createRichTextString("All"));
                row6.createCell(1).setCellValue(MasterRepository.getInstance().getNumberOfAllDisputes(kebele, "stage = 4"));
                row6.createCell(2).setCellValue(MasterRepository.getInstance().getNumberOfPeopleAllDisputes(kebele, "stage = 4"));
                row6.createCell(3).setCellValue(MasterRepository.getInstance().getNumberOfHoldersInAllDisputes(kebele, "stage = 4"));
                row6.createCell(4).setCellValue(MasterRepository.getInstance().getNumberOfDisputantsInAllDisputes(kebele, "stage = 4"));
                row6.createCell(5).setCellValue(MasterRepository.getInstance().getNumberOfMarriedCoupleHoldersInAllDisputes(kebele, "stage = 4"));
                row6.createCell(6).setCellValue(MasterRepository.getInstance().getNumberOfSingleFemaleHoldersInAllDisputes(kebele, "stage = 4"));
                row6.createCell(7).setCellValue(MasterRepository.getInstance().getNumberOfSingleMaleHoldersInAllDisputes(kebele, "stage = 4"));
                row6.createCell(8).setCellValue(MasterRepository.getInstance().getNumberOfOrphanHoldersInAllDisputes(kebele, "stage = 4"));
                row6.createCell(9).setCellValue(MasterRepository.getInstance().getNumberOfNonNaturalHoldersInAllDisputes(kebele, "stage = 4"));
                row6.createCell(10).setCellValue(MasterRepository.getInstance().getNumberOfOtherHoldingTypesInAllDisputes(kebele, "stage = 4"));
                row6.createCell(11).setCellValue(MasterRepository.getInstance().getNumberOfAllDisputesByStatus(kebele, (byte) 1 /*Elders Committee*/, "stage = 4"));
                row6.createCell(12).setCellValue(MasterRepository.getInstance().getNumberOfAllDisputesByStatus(kebele, (byte) 2 /*Kebele Land Administration Committee*/, "stage = 4"));
                row6.createCell(13).setCellValue(MasterRepository.getInstance().getNumberOfAllDisputesByStatus(kebele, (byte) 3 /*Woreda Court*/, "stage = 4"));
                row6.createCell(14).setCellValue(MasterRepository.getInstance().getNumberOfAllDisputesByStatus(kebele, (byte) 4 /*Others*/, "stage = 4"));
                disputeSheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 14));
                //for (int x = 0; x < 14/*Number of columns*/; x++) {
                //    disputeSheet.autoSizeColumn(x, true);
                //}

                /// Missing Data report
                Sheet missingDataSheet = wb.createSheet("Missing Data");
                row0 = missingDataSheet.createRow((short) 0);
                row0.createCell(0).setCellValue(createHelper.createRichTextString("Missing Data"));
                row0.createCell(1).setCellValue(createHelper.createRichTextString("Number of Holders"));
                row0.createCell(2).setCellValue(createHelper.createRichTextString("Number of Parcels"));

                row1 = missingDataSheet.createRow((short) 1);
                row1.createCell(0).setCellValue(createHelper.createRichTextString("Holder's First Name"));
                row1.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfHoldersWithMissingFirstName(kebele));
                row1.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingFirstName(kebele));

                row2 = missingDataSheet.createRow((short) 2);
                row2.createCell(0).setCellValue(createHelper.createRichTextString("Holder's Father's Name"));
                row2.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfHoldersWithMissingFathersName(kebele));
                row2.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingFathersName(kebele));

                row3 = missingDataSheet.createRow((short) 3);
                row3.createCell(0).setCellValue(createHelper.createRichTextString("Holder's Grandfather's Name"));
                row3.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfHoldersWithMissingGrandfathersName(kebele));
                row3.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingGrandfathersName(kebele));

                row4 = missingDataSheet.createRow((short) 4);
                row4.createCell(0).setCellValue(createHelper.createRichTextString("Holder's Sex"));
                row4.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfHoldersWithMissingSex(kebele));
                row4.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingSex(kebele));

                row5 = missingDataSheet.createRow((short) 5);
                row5.createCell(0).setCellValue(createHelper.createRichTextString("Holder's Family Role"));
                row5.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfHoldersWithMissingFamilyRole(kebele));
                row5.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingFamilyRole(kebele));

                row6 = missingDataSheet.createRow((short) 6);
                row6.createCell(0).setCellValue(createHelper.createRichTextString("Holder's Date of Birth"));
                row6.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfHoldersWithMissingDateOfBirth(kebele));
                row6.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingDateOfBirth(kebele));

                row7 = missingDataSheet.createRow((short) 7);
                row7.createCell(0).setCellValue(createHelper.createRichTextString("Certificate No."));
                row7.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfHoldersInParcelsWithMissingCertificateNo(kebele));
                row7.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingCertificateNo(kebele));

                row8 = missingDataSheet.createRow((short) 8);
                row8.createCell(0).setCellValue(createHelper.createRichTextString("Holding No."));
                row8.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfHoldersInParcelsWithMissingHoldingNo(kebele));
                row8.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingHoldingNo(kebele));

                row9 = missingDataSheet.createRow((short) 9);
                row9.createCell(0).setCellValue(createHelper.createRichTextString("Other Evidence"));
                row9.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfHoldersInParcelsWithMissingOtherEvidence(kebele));
                row9.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingOtherEvidence(kebele));

                row10 = missingDataSheet.createRow((short) 10);
                row10.createCell(0).setCellValue(createHelper.createRichTextString("Land Use"));
                row10.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfHoldersInParcelsWithMissingLandUseType(kebele));
                row10.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingLandUseType(kebele));

                Row row11 = missingDataSheet.createRow((short) 11);
                row11.createCell(0).setCellValue(createHelper.createRichTextString("Soil Fertility"));
                row11.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfHoldersInParcelsWithMissingSoilFertility(kebele));
                row11.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingSoilFertility(kebele));

                Row row12 = missingDataSheet.createRow((short) 12);
                row12.createCell(0).setCellValue(createHelper.createRichTextString("Means of Acquisition"));
                row12.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfHoldersInParcelsWithMissingAcquisitionType(kebele));
                row12.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingAcquisitionType(kebele));

                Row row13 = missingDataSheet.createRow((short) 13);
                row13.createCell(0).setCellValue(createHelper.createRichTextString("Acquisition Year"));
                row13.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfHoldersInParcelsWithMissingAcquisitionYear(kebele));
                row13.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingAcquisitionYear(kebele));

                Row row14 = missingDataSheet.createRow((short) 14);
                row14.createCell(0).setCellValue(createHelper.createRichTextString("Encumbrance"));
                row14.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfHoldersInParcelsWithMissingEncumbranceType(kebele));
                row14.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingEncumbranceType(kebele));

                Row row15 = missingDataSheet.createRow((short) 15);
                row15.createCell(0).setCellValue(createHelper.createRichTextString("Disputant's First Name"));
                row15.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingDisputantsFirstName(kebele));

                Row row16 = missingDataSheet.createRow((short) 16);
                row16.createCell(0).setCellValue(createHelper.createRichTextString("Disputant's Father's Name"));
                row16.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingDisputantsFathersName(kebele));

                Row row17 = missingDataSheet.createRow((short) 17);
                row17.createCell(0).setCellValue(createHelper.createRichTextString("Disputant's Grandfather's Name"));
                row17.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingDisputantsGrandfathersName(kebele));

                Row row18 = missingDataSheet.createRow((short) 18);
                row18.createCell(0).setCellValue(createHelper.createRichTextString("Disputant's Sex"));
                row18.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingDisputantsSex(kebele));

                Row row19 = missingDataSheet.createRow((short) 19);
                row19.createCell(0).setCellValue(createHelper.createRichTextString("Dispute Type"));
                row19.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingDisputeType(kebele));

                Row row20 = missingDataSheet.createRow((short) 20);
                row20.createCell(0).setCellValue(createHelper.createRichTextString("Form Where the dispute is handled"));
                row20.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingDisputeStatus(kebele));

                Row row21 = missingDataSheet.createRow((short) 21);
                row21.createCell(0).setCellValue(createHelper.createRichTextString("Person With Interest's First Name"));
                row21.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingPersonWithInterestsFirstName(kebele));

                Row row22 = missingDataSheet.createRow((short) 22);
                row22.createCell(0).setCellValue(createHelper.createRichTextString("Person With Interest's Father's Name"));
                row22.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingPersonWithInterestsFathersName(kebele));

                Row row23 = missingDataSheet.createRow((short) 23);
                row23.createCell(0).setCellValue(createHelper.createRichTextString("Person With Interest's Grandfather's Name"));
                row23.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingPersonWithInterestsGrandfathersName(kebele));

                Row row24 = missingDataSheet.createRow((short) 24);
                row24.createCell(0).setCellValue(createHelper.createRichTextString("Person With Interest's Sex"));
                row24.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingPersonWithInterestsSex(kebele));

                Row row25 = missingDataSheet.createRow((short) 25);
                row25.createCell(0).setCellValue(createHelper.createRichTextString("Guardian's First Name"));
                row25.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingGuardiansFirstName(kebele));

                Row row26 = missingDataSheet.createRow((short) 26);
                row26.createCell(0).setCellValue(createHelper.createRichTextString("Guardian's Father's Name"));
                row26.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingGuardiansFathersName(kebele));

                Row row27 = missingDataSheet.createRow((short) 27);
                row27.createCell(0).setCellValue(createHelper.createRichTextString("Guardian's Grandfather's Name"));
                row27.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingGuardiansGrandfathersName(kebele));

                Row row28 = missingDataSheet.createRow((short) 28);
                row28.createCell(0).setCellValue(createHelper.createRichTextString("Guardian's Sex"));
                row28.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingGuardiansSex(kebele));

                Row row29 = missingDataSheet.createRow((short) 29);
                row29.createCell(0).setCellValue(createHelper.createRichTextString("Missing Any Data"));
                row29.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithMissingData(kebele));

                for (int x = 0; x < 2; x++) {
                    missingDataSheet.autoSizeColumn(x, true);
                }
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return wb;
    }
    
    public static Workbook generateCompletionReport(long kebele, String kebeleName, String fileName) {
        MasterRepository.getInstance().updateParcelArea(kebele);
        refreshViews();
        Workbook wb = new XSSFWorkbook();
        try {
            CreationHelper createHelper = wb.getCreationHelper();
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {

                // Kebele report sheet
                Sheet kebeleSheet = wb.createSheet(kebeleName);
                // level 1 headers 
                Row row0 = kebeleSheet.createRow((short) 0);
                Row row1 = kebeleSheet.createRow((short) 1);
                Row row2 = kebeleSheet.createRow((short) 2); // Married Couple row
                Row row3 = kebeleSheet.createRow((short) 3); // Single female row
                Row row4 = kebeleSheet.createRow((short) 4); // Single male row
                Row row5 = kebeleSheet.createRow((short) 5); // Orphans row
                Row row6 = kebeleSheet.createRow((short) 6); // Non natural row
                Row row7 = kebeleSheet.createRow((short) 7); // Other row
                Row row8 = kebeleSheet.createRow((short) 8); // Dispute row
                Row row9 = kebeleSheet.createRow((short) 9); // No data row
                Row row10 = kebeleSheet.createRow((short) 10); // All row

                CellStyle alignmentStyleRight = wb.createCellStyle();
                alignmentStyleRight.setAlignment(CellStyle.ALIGN_RIGHT);
                alignmentStyleRight.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

                row0.createCell(11).setCellValue(createHelper.createRichTextString("Other ownership evidence"));
                row0.createCell(13).setCellValue(createHelper.createRichTextString("Land Use"));
                row0.createCell(24).setCellValue(createHelper.createRichTextString("Soil Fertility Status"));
                row0.createCell(27).setCellValue(createHelper.createRichTextString("Landholding Type"));
                row0.createCell(31).setCellValue(createHelper.createRichTextString("Public Organisation"));
                row0.createCell(36).setCellValue(createHelper.createRichTextString("Means of Acquisition"));
                row0.createCell(42).setCellValue(createHelper.createRichTextString("Encumbrances/ Servitude"));
                row0.createCell(44).setCellValue(createHelper.createRichTextString("Physical Impairment"));
                row0.createCell(46).setCellValue(createHelper.createRichTextString("Persons of interest"));
                row0.createCell(51).setCellValue(createHelper.createRichTextString("Guardians"));

                // level 2 headers  
                row1.createCell(0).setCellValue(createHelper.createRichTextString("Woreda Name"));
                row1.createCell(1).setCellValue(createHelper.createRichTextString("Kebele Name"));
                row1.createCell(2).setCellValue(createHelper.createRichTextString("Kebele Code"));
                row1.createCell(3).setCellValue(createHelper.createRichTextString("Land Holder Type"));
                row1.createCell(4).setCellValue(createHelper.createRichTextString("Unique Records"));
                row1.createCell(5).setCellValue(createHelper.createRichTextString("Total Parcels Demarcated"));
                row1.createCell(6).setCellValue(createHelper.createRichTextString("Average Number of Parcels"));
                row1.createCell(7).setCellValue(createHelper.createRichTextString("Total number of non committed parcels"));
                row1.createCell(8).setCellValue(createHelper.createRichTextString("Total number of committed parcels"));
                row1.createCell(9).setCellValue(createHelper.createRichTextString("Total area(Ha)"));
                row1.createCell(10).setCellValue(createHelper.createRichTextString("Average area(Ha)"));
                row1.createCell(11).setCellValue(createHelper.createRichTextString("Court Decision"));
                row1.createCell(12).setCellValue(createHelper.createRichTextString("Tax Receipt"));
                row1.createCell(13).setCellValue(createHelper.createRichTextString("Rain fed annual crops"));
                row1.createCell(14).setCellValue(createHelper.createRichTextString("Rain fed Perennial crops"));
                row1.createCell(15).setCellValue(createHelper.createRichTextString("Irrigated annual crops"));
                row1.createCell(16).setCellValue(createHelper.createRichTextString("Irrigated perennial crops"));
                row1.createCell(17).setCellValue(createHelper.createRichTextString("Grassland/Grazing Land"));
                row1.createCell(18).setCellValue(createHelper.createRichTextString("Shrub land/wood land"));
                row1.createCell(19).setCellValue(createHelper.createRichTextString("Natural Forest"));
                row1.createCell(20).setCellValue(createHelper.createRichTextString("Artificial Forest  (e.g. Eucalyptus plantation, etc)"));
                row1.createCell(21).setCellValue(createHelper.createRichTextString("Wetland"));
                row1.createCell(22).setCellValue(createHelper.createRichTextString("Built-Up  Areas ( Schools, churches, mosques, FTC, etc)"));
                row1.createCell(23).setCellValue(createHelper.createRichTextString("Bare land"));
                row1.createCell(24).setCellValue(createHelper.createRichTextString("High"));
                row1.createCell(25).setCellValue(createHelper.createRichTextString("Medium"));
                row1.createCell(26).setCellValue(createHelper.createRichTextString("Low to Poor"));
                row1.createCell(27).setCellValue(createHelper.createRichTextString("Individual Holding"));
                row1.createCell(28).setCellValue(createHelper.createRichTextString("Commons"));
                row1.createCell(29).setCellValue(createHelper.createRichTextString("Public Organizations"));
                row1.createCell(30).setCellValue(createHelper.createRichTextString("Federal/ Regional Government "));
                row1.createCell(31).setCellValue(createHelper.createRichTextString("Church"));
                row1.createCell(32).setCellValue(createHelper.createRichTextString("Mosque"));
                row1.createCell(33).setCellValue(createHelper.createRichTextString("School"));
                row1.createCell(34).setCellValue(createHelper.createRichTextString("Health Centre"));
                row1.createCell(35).setCellValue(createHelper.createRichTextString("Others"));
                row1.createCell(36).setCellValue(createHelper.createRichTextString("Redistribution"));
                row1.createCell(37).setCellValue(createHelper.createRichTextString("Inheritance"));
                row1.createCell(38).setCellValue(createHelper.createRichTextString("Gift"));
                row1.createCell(39).setCellValue(createHelper.createRichTextString("Divorce"));
                row1.createCell(40).setCellValue(createHelper.createRichTextString("Reallocation"));
                row1.createCell(41).setCellValue(createHelper.createRichTextString("OTHERS"));
                row1.createCell(42).setCellValue(createHelper.createRichTextString("Right of Way"));
                row1.createCell(43).setCellValue(createHelper.createRichTextString("Terms of Rights/Restrictions"));
                row1.createCell(44).setCellValue(createHelper.createRichTextString("Yes"));
                row1.createCell(45).setCellValue(createHelper.createRichTextString("No"));
                row1.createCell(46).setCellValue(createHelper.createRichTextString("Total number of parcels with P.I"));
                row1.createCell(47).setCellValue(createHelper.createRichTextString("Total Number of P.I"));
                row1.createCell(48).setCellValue(createHelper.createRichTextString("Average Number of P.I  parcels "));
                row1.createCell(49).setCellValue(createHelper.createRichTextString("Male"));
                row1.createCell(50).setCellValue(createHelper.createRichTextString("Female"));
                row1.createCell(51).setCellValue(createHelper.createRichTextString("Total number of parcels with Guardians"));
                row1.createCell(52).setCellValue(createHelper.createRichTextString("Total Number of Guardians"));
                row1.createCell(53).setCellValue(createHelper.createRichTextString("Average Number of Guardians to relevent parcels"));
                row1.createCell(54).setCellValue(createHelper.createRichTextString("Male"));
                row1.createCell(55).setCellValue(createHelper.createRichTextString("Female"));
                row1.createCell(56).setCellValue(createHelper.createRichTextString("Date Kebele commenced entry"));
                row1.createCell(57).setCellValue(createHelper.createRichTextString("Date Kebele completed entry"));
                row2.createCell(0).setCellValue(createHelper.createRichTextString(CommonStorage.getCurrentWoredaName()));
                row2.createCell(1).setCellValue(createHelper.createRichTextString(kebeleName));
                row2.createCell(2).setCellValue(kebele);

                //Land Holder Type
                row2.createCell(3).setCellValue(createHelper.createRichTextString("Married Couple"));
                row3.createCell(3).setCellValue(createHelper.createRichTextString("Single Female"));
                row4.createCell(3).setCellValue(createHelper.createRichTextString("Single Male"));
                row5.createCell(3).setCellValue(createHelper.createRichTextString("Orphans"));
                row6.createCell(3).setCellValue(createHelper.createRichTextString("Non Natural"));
                row7.createCell(3).setCellValue(createHelper.createRichTextString("Other"));
                row8.createCell(3).setCellValue(createHelper.createRichTextString("Dispute"));
                row9.createCell(3).setCellValue(createHelper.createRichTextString("No data"));
                row10.createCell(3).setCellValue(createHelper.createRichTextString("All"));
                //Unique Records
                row2.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfMarriedCoupleHolders(kebele, "stage > 9"));
                row3.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfSingleFemaleHolders(kebele, "stage > 9"));
                row4.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfSingleMaleHolders(kebele, "stage > 9"));
                row5.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfOrphanHolders(kebele, "stage > 9"));
                row6.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfNonNaturalHolders(kebele, "stage > 9"));
                row7.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfHoldersOfOtherHoldingTypes(kebele, "stage > 9"));
                row8.createCell(4).setCellValue(MasterRepository.getInstance().getNumberOfPeopleAllDisputes(kebele, "stage > 9"));
                row9.createCell(4).setCellValue(createHelper.createRichTextString("--")/*Data Not available*/);
                row10.createCell(4).setCellValue(createHelper.createRichTextString("--")/*Data Not available*/);
                // Total Parcels Demarcated
                row2.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row8.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(5).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfDemarcatedParcels(kebele));
                // Average Number of Parcels
                row2.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerMarriedCoupleHolder(kebele, "stage > 9"));
                row3.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerSingleFemaleHolder(kebele, "stage > 9"));
                row4.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerSingleMaleHolder(kebele, "stage > 9"));
                row5.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerOrphanHolder(kebele, "stage > 9"));
                row6.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerNonNaturalHolder(kebele, "stage > 9"));
                row7.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerOtherHoldingType(kebele, "stage > 9"));
                row8.createCell(6).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(6).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(6).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                //Total number of non committed parcels
                row2.createCell(7).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(7).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(7).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(7).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(7).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(7).setCellValue(createHelper.createRichTextString("---"/*Value not known*/));
                row8.createCell(7).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(7).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(7).setCellValue(MasterRepository.getInstance().getCountOfAllNonCommittedParcels(kebele));
                // Total number of committed parcels
                row2.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHolders(kebele, "stage > 9"));
                row3.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHolders(kebele, "stage > 9"));
                row4.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHolders(kebele, "stage > 9"));
                row5.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHolders(kebele, "stage > 9"));
                row6.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHolders(kebele, "stage > 9"));
                row7.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypes(kebele, "stage > 9"));
                row8.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDispute(kebele, "stage > 9"));
                row9.createCell(8).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfAllCommittedParcels(kebele, "stage > 9"));
                //Total Area
                row2.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderMarriedCoupleHolders(kebele, "stage > 9"));
                row3.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderSingleFemaleHolders(kebele, "stage > 9"));
                row4.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderSingleMaleHolders(kebele, "stage > 9"));
                row5.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderOrphanHolders(kebele, "stage > 9"));
                row6.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderNonNaturalHolders(kebele, "stage > 9"));
                row7.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderOtherHoldingTypes(kebele, "stage > 9"));
                row8.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderDispute(kebele, "stage > 9"));
                row9.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsWithNoData(kebele, "stage > 9"));
                row10.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfAllParcels(kebele));
                // Average area
                row2.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderMarriedCoupleHolders(kebele, "stage > 9"));
                row3.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderSingleFemaleHolders(kebele, "stage > 9"));
                row4.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderSingleMaleHolders(kebele, "stage > 9"));
                row5.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderOrphanHolders(kebele, "stage > 9"));
                row6.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderNonNaturalHolders(kebele, "stage > 9"));
                row7.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderOtherHoldingTypes(kebele, "stage > 9"));
                row8.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderDispute(kebele, "stage > 9"));
                row9.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsWithNoData(kebele, "stage > 9"));
                row10.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfAllParcels(kebele));
                //......
                //Court Decision
                row2.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage > 9"));
                row3.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage > 9"));
                row4.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage > 9"));
                row5.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage > 9"));
                row6.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage > 9"));
                row7.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage > 9"));
                row8.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage > 9"));
                row9.createCell(11).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOwnershipEvidence(kebele, (byte) 1/*Court Decision*/, "stage > 9"));
                //Tax Receipt
                row2.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage > 9"));
                row3.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage > 9"));
                row4.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage > 9"));
                row5.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage > 9"));
                row6.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage > 9"));
                row7.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage > 9"));
                row8.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage > 9"));
                row9.createCell(12).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/, "stage > 9"));
                //Rain fed annual crops
                row2.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage > 9"));
                row3.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage > 9"));
                row4.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage > 9"));
                row5.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage > 9"));
                row6.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage > 9"));
                row7.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage > 9"));
                row8.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage > 9"));
                row9.createCell(13).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 1 /*Rain fed annual crops*/, "stage > 9"));
                row2.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage > 9"));
                row3.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage > 9"));
                row4.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage > 9"));
                row5.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage > 9"));
                row6.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage > 9"));
                row7.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage > 9"));
                row8.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage > 9"));
                row9.createCell(14).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 2 /*Rain fed Perennial crops*/, "stage > 9"));
                row2.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage > 9"));
                row3.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage > 9"));
                row4.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage > 9"));
                row5.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage > 9"));
                row6.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage > 9"));
                row7.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage > 9"));
                row8.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage > 9"));
                row9.createCell(15).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 3 /*Irrigated annual crops*/, "stage > 9"));
                row2.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage > 9"));
                row3.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage > 9"));
                row4.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage > 9"));
                row5.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage > 9"));
                row6.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage > 9"));
                row7.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage > 9"));
                row8.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage > 9"));
                row9.createCell(16).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 4 /*Irrigated perennial crops*/, "stage > 9"));

                row2.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage > 9"));
                row3.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage > 9"));
                row4.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage > 9"));
                row5.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage > 9"));
                row6.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage > 9"));
                row7.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage > 9"));
                row8.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage > 9"));
                row9.createCell(17).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 5 /*Grazing Land*/, "stage > 9"));

                row2.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage > 9"));
                row3.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage > 9"));
                row4.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage > 9"));
                row5.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage > 9"));
                row6.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage > 9"));
                row7.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage > 9"));
                row8.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage > 9"));
                row9.createCell(18).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 7 /*Shrubland/Woodland*/, "stage > 9"));

                row2.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage > 9"));
                row3.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage > 9"));
                row4.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage > 9"));
                row5.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage > 9"));
                row6.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage > 9"));
                row7.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage > 9"));
                row8.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage > 9"));
                row9.createCell(19).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 8 /*Natural Forest*/, "stage > 9"));

                row2.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage > 9"));
                row3.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage > 9"));
                row4.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage > 9"));
                row5.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage > 9"));
                row6.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage > 9"));
                row7.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage > 9"));
                row8.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage > 9"));
                row9.createCell(20).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 9 /*Artificial Forest*/, "stage > 9"));

                row2.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage > 9"));
                row3.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage > 9"));
                row4.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage > 9"));
                row5.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage > 9"));
                row6.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage > 9"));
                row7.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage > 9"));
                row8.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage > 9"));
                row9.createCell(21).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 10 /*Wetland*/, "stage > 9"));

                row2.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage > 9"));
                row3.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage > 9"));
                row4.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage > 9"));
                row5.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage > 9"));
                row6.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage > 9"));
                row7.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage > 9"));
                row8.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage > 9"));
                row9.createCell(22).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 11 /*Built-Up Areas*/, "stage > 9"));

                row2.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage > 9"));
                row3.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage > 9"));
                row4.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage > 9"));
                row5.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage > 9"));
                row6.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage > 9"));
                row7.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage > 9"));
                row8.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage > 9"));
                row9.createCell(23).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 12 /*Bareland*/, "stage > 9"));

                row2.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage > 9"));
                row3.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage > 9"));
                row4.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage > 9"));
                row5.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage > 9"));
                row6.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage > 9"));
                row7.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage > 9"));
                row8.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage > 9"));
                row9.createCell(24).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsBySoilFertility(kebele, (byte) 1 /*High/Good*/, "stage > 9"));

                row2.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage > 9"));
                row3.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage > 9"));
                row4.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage > 9"));
                row5.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage > 9"));
                row6.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage > 9"));
                row7.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage > 9"));
                row8.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage > 9"));
                row9.createCell(25).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/, "stage > 9"));

                row2.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage > 9"));
                row3.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage > 9"));
                row4.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage > 9"));
                row5.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage > 9"));
                row6.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage > 9"));
                row7.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage > 9"));
                row8.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage > 9"));
                row9.createCell(26).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row10.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsBySoilFertility(kebele, (byte) 3 /*Low/Poor*/, "stage > 9"));

                row2.createCell(27).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(28).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(29).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(30).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(31).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(32).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(33).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(34).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(35).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row2.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage > 9"));
                row2.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage > 9"));
                row2.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage > 9"));
                row2.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage > 9"));
                row2.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage > 9"));
                row2.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage > 9"));
                row2.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage > 9"));
                row2.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage > 9"));
                row2.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfMarriedCoupleHoldersWithPhysicalImpairment(kebele, "stage > 9"));
                row2.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfMarriedCoupleHoldersWithoutPhysicalImpairment(kebele, "stage > 9"));

                row2.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderMarriedCoupleHolders(kebele, "stage > 9"));
                row2.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderMarriedCoupleHolders(kebele, "stage > 9"));
                row2.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderMarriedCoupleHolders(kebele, "stage > 9"));
                row2.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderMarriedCoupleHolders(kebele, "stage > 9"));
                row2.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderMarriedCoupleHolders(kebele, "stage > 9"));
                row2.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardianUnderMarriedCoupleHolders(kebele, "stage > 9"));
                row2.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderMarriedCoupleHolders(kebele, "stage > 9"));
                row2.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderMarriedCoupleHolders(kebele, "stage > 9"));
                row2.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderMarriedCoupleHolders(kebele, "stage > 9"));
                row2.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderMarriedCoupleHolders(kebele, "stage > 9"));
                row2.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForMarriedHolders(kebele, "stage > 9"));
                row2.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForMarriedHolders(kebele, "stage > 9"));

                row3.createCell(27).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(28).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(29).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(30).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(31).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(32).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(33).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(34).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(35).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row3.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage > 9"));
                row3.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage > 9"));
                row3.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage > 9"));
                row3.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage > 9"));
                row3.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage > 9"));
                row3.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage > 9"));
                row3.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage > 9"));
                row3.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage > 9"));
                row3.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfSingleFemaleHoldersWithPhysicalImpairment(kebele, "stage > 9"));
                row3.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfSingleFemaleHoldersWithoutPhysicalImpairment(kebele, "stage > 9"));
                row3.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderSingleFemaleHolders(kebele, "stage > 9"));
                row3.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderSingleFemaleHolders(kebele, "stage > 9"));
                row3.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderSingleFemaleHolders(kebele, "stage > 9"));
                row3.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderSingleFemaleHolders(kebele, "stage > 9"));
                row3.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderSingleFemaleHolders(kebele, "stage > 9"));
                row3.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderSingleFemaleHolders(kebele, "stage > 9"));
                row3.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderSingleFemaleHolders(kebele, "stage > 9"));
                row3.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderSingleFemaleHolders(kebele, "stage > 9"));
                row3.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderSingleFemaleHolders(kebele, "stage > 9"));
                row3.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderSingleFemaleHolders(kebele, "stage > 9"));
                row3.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForSingleFemaleHolders(kebele, "stage > 9"));
                row3.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForSingleFemaleHolders(kebele, "stage > 9"));

                row4.createCell(27).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(28).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(29).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(30).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(31).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(32).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(33).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(34).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(35).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row4.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage > 9"));
                row4.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage > 9"));
                row4.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage > 9"));
                row4.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage > 9"));
                row4.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage > 9"));
                row4.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage > 9"));
                row4.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage > 9"));
                row4.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage > 9"));
                row4.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfSingleMaleHoldersWithPhysicalImpairment(kebele, "stage > 9"));
                row4.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfSingleMaleHoldersWithoutPhysicalImpairment(kebele, "stage > 9"));
                row4.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderSingleMaleHolders(kebele, "stage > 9"));
                row4.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderSingleMaleHolders(kebele, "stage > 9"));
                row4.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderSingleMaleHolders(kebele, "stage > 9"));
                row4.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderSingleMaleHolders(kebele, "stage > 9"));
                row4.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderSingleMaleHolders(kebele, "stage > 9"));

                row4.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderSingleMaleHolders(kebele, "stage > 9"));
                row4.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderSingleMaleHolders(kebele, "stage > 9"));
                row4.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderSingleMaleHolders(kebele, "stage > 9"));
                row4.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderSingleMaleHolders(kebele, "stage > 9"));
                row4.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderSingleMaleHolders(kebele, "stage > 9"));
                row4.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForSingleMaleHolders(kebele, "stage > 9"));
                row4.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForSingleMaleHolders(kebele, "stage > 9"));

                row5.createCell(27).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(28).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(29).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(30).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(31).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(32).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(33).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(34).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(35).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row5.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage > 9"));
                row5.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage > 9"));
                row5.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage > 9"));
                row5.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage > 9"));
                row5.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage > 9"));
                row5.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage > 9"));
                row5.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage > 9"));
                row5.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage > 9"));
                row5.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfOrphanHoldersWithPhysicalImpairment(kebele, "stage > 9"));
                row5.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfOrphanHoldersWithoutPhysicalImpairment(kebele, "stage > 9"));
                row5.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderOrphanHolders(kebele, "stage > 9"));
                row5.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderOrphanHolders(kebele, "stage > 9"));
                row5.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderOrphanHolders(kebele, "stage > 9"));
                row5.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderOrphanHolders(kebele, "stage > 9"));
                row5.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderOrphanHolders(kebele, "stage > 9"));

                row5.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderOrphanHolders(kebele, "stage > 9"));
                row5.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderOrphanHolders(kebele, "stage > 9"));
                row5.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderSingleMaleHolders(kebele, "stage > 9"));
                row5.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderOrphanHolders(kebele, "stage > 9"));
                row5.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderOrphanHolders(kebele, "stage > 9"));
                row5.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForOrphanHolders(kebele, "stage > 9"));
                row5.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForOrphanHolders(kebele, "stage > 9"));

                row6.createCell(27).setCellValue(createHelper.createRichTextString("---")/*Does not make sense*/);
                row6.createCell(28).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByHoldingType(kebele, (byte) 2 /*Communal*/, "stage > 9"));
                row6.createCell(29).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByHoldingType(kebele, (byte) 3 /*Public/Instituional*/, "stage > 9"));
                row6.createCell(30).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByHoldingType(kebele, (byte) 4 /*State*/, "stage > 9"));
                row6.createCell(31).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 2 /*Church*/, "stage > 9"));
                row6.createCell(32).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 3 /*Mosque*/, "stage > 9"));
                row6.createCell(33).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 1 /*School*/, "stage > 9"));
                row6.createCell(34).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 4 /*Health Center*/, "stage > 9"));
                row6.createCell(35).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 5 /*Other*/, "stage > 9"));
                row6.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage > 9"));
                row6.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage > 9"));
                row6.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage > 9"));
                row6.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage > 9"));
                row6.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage > 9"));
                row6.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage > 9"));
                row6.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage > 9"));
                row6.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage > 9"));
                row6.createCell(44).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(45).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(46).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(47).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(48).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(49).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(50).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(51).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(52).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(53).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(54).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row6.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForNonNaturalHolders(kebele, "stage > 9"));
                row6.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForNonNaturalHolders(kebele, "stage > 9"));

                row7.createCell(27).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(28).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(29).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(30).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(31).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(32).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(33).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(34).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(35).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row7.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage > 9"));
                row7.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage > 9"));
                row7.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage > 9"));
                row7.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage > 9"));
                row7.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage > 9"));
                row7.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage > 9"));
                row7.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage > 9"));
                row7.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage > 9"));
                row7.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfOtherHoldingTypesWithPhysicalImpairment(kebele, "stage > 9"));
                row7.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfOtherHoldingTypesWithoutPhysicalImpairment(kebele, "stage > 9"));
                row7.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderOtherHoldingTypes(kebele, "stage > 9"));
                row7.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderOtherHoldingTypes(kebele, "stage > 9"));
                row7.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderOtherHoldingTypes(kebele, "stage > 9"));
                row7.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderOtherHoldingTypes(kebele, "stage > 9"));
                row7.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderOtherHoldingTypes(kebele, "stage > 9"));

                row7.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderOtherHoldingTypes(kebele, "stage > 9"));
                row7.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderOtherHoldingTypes(kebele, "stage > 9"));
                row7.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderOtherHoldingTypes(kebele, "stage > 9"));
                row7.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderOtherHoldingTypes(kebele, "stage > 9"));
                row7.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderOtherHoldingTypes(kebele, "stage > 9"));
                row7.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForOtherHoldingTypes(kebele, "stage > 9"));
                row7.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForOtherHoldingTypes(kebele, "stage > 9"));

                row8.createCell(27).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByHoldingType(kebele, (byte) 1 /*Individual*/, "stage > 9"));
                row8.createCell(28).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByHoldingType(kebele, (byte) 2 /*Communal*/, "stage > 9"));
                row8.createCell(29).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByHoldingType(kebele, (byte) 3 /*Public/Instituional*/, "stage > 9"));
                row8.createCell(30).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByHoldingType(kebele, (byte) 4 /*State*/, "stage > 9"));
                row8.createCell(31).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 2 /*Church*/, "stage > 9"));
                row8.createCell(32).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 3 /*Mosque*/, "stage > 9"));
                row8.createCell(33).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 1 /*School*/, "stage > 9"));
                row8.createCell(34).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 4 /*Health Center*/, "stage > 9"));
                row8.createCell(35).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 5 /*Other*/, "stage > 9"));
                row8.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage > 9"));
                row8.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage > 9"));
                row8.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage > 9"));
                row8.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage > 9"));
                row8.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage > 9"));
                row8.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage > 9"));
                row8.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage > 9"));
                row8.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage > 9"));
                row8.createCell(44).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row8.createCell(45).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row8.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderDispute(kebele, "stage > 9"));
                row8.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestForParcelsWithDispute(kebele, "stage > 9"));
                row8.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderDispute(kebele, "stage > 9"));
                row8.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestForParcelsWithDispute(kebele, "stage > 9"));
                row8.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestForParcelsWithDispute(kebele, "stage > 9"));

                row8.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderDispute(kebele, "stage > 9"));
                row8.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsWithDispute(kebele, "stage > 9"));
                row8.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderDispute(kebele, "stage > 9"));
                row8.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsWithDispute(kebele, "stage > 9"));
                row8.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsWithDispute(kebele, "stage > 9"));
                row8.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForParcelsWithDispute(kebele, "stage > 9"));
                row8.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForParcelsWithDispute(kebele, "stage > 9"));

                row9.createCell(27).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(28).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(29).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(30).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(31).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(32).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(33).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(34).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(35).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(36).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(37).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(38).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(39).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(40).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(41).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(42).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(43).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(44).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(45).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(46).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(47).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(48).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(49).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(50).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(51).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(52).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(53).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(54).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(55).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(56).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);
                row9.createCell(57).setCellValue(createHelper.createRichTextString("---")/*Data Not available*/);

                row10.createCell(27).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByHoldingType(kebele, (byte) 1 /*Individual*/, "stage > 9"));
                row10.createCell(28).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByHoldingType(kebele, (byte) 2 /*Communal*/, "stage > 9"));
                row10.createCell(29).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByHoldingType(kebele, (byte) 3 /*Public/Instituional*/, "stage > 9"));
                row10.createCell(30).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByHoldingType(kebele, (byte) 4 /*State*/, "stage > 9"));
                row10.createCell(31).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 2 /*Church*/, "stage > 9"));
                row10.createCell(32).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 3 /*Mosque*/, "stage > 9"));
                row10.createCell(33).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 1 /*School*/, "stage > 9"));
                row10.createCell(34).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 4 /*Health Center*/, "stage > 9"));
                row10.createCell(35).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 5 /*Other*/, "stage > 9"));
                row10.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/, "stage > 9"));
                row10.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/, "stage > 9"));
                row10.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/, "stage > 9"));
                row10.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/, "stage > 9"));
                row10.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/, "stage > 9"));
                row10.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/, "stage > 9"));
                row10.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByEncumbrances(kebele, (byte) 1 /*Right of Way*/, "stage > 9"));
                row10.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/, "stage > 9"));
                row10.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfAllHoldersWithPhysicalImpairment(kebele, "stage > 9"));
                row10.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfAllHoldersWithoutPhysicalImpairment(kebele, "stage > 9"));
                row10.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsWithPersonsOfInterest(kebele, "stage > 9"));
                row10.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestForAllParcels(kebele, "stage > 9"));
                row10.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelForAllParcels(kebele, "stage > 9"));
                row10.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestForAllParcels(kebele, "stage > 9"));
                row10.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestForAllParcels(kebele, "stage > 9"));

                row10.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsWithGuardians(kebele, "stage > 9"));
                row10.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForAllParcels(kebele, "stage > 9"));
                row10.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelForAllParcels(kebele, "stage > 9"));
                row10.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForAllParcels(kebele, "stage > 9"));
                row10.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForAllParcels(kebele, "stage > 9"));
                row10.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForAllParcels(kebele));
                row10.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForAllParcels(kebele));

                // Format the sheet
                CellStyle cellStyle = wb.createCellStyle();
                Font hSSFFont = wb.createFont();
                hSSFFont.setFontName("Calibri");
                hSSFFont.setFontHeightInPoints((short) 11);
                hSSFFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

                cellStyle.setWrapText(true);
                cellStyle.setFont(hSSFFont);

                for (int x = 0; x < 58/*Number of columns*/; x++) {
                    kebeleSheet.autoSizeColumn(x, true);
                }

                CellStyle boldCellStyle = wb.createCellStyle();
                boldCellStyle.cloneStyleFrom(cellStyle);
                hSSFFont.setBold(true);
                boldCellStyle.setFont(hSSFFont);
                row1.setRowStyle(cellStyle);

                // Merge region
                kebeleSheet.addMergedRegion(new CellRangeAddress(2, 10, 0, 0));
                kebeleSheet.addMergedRegion(new CellRangeAddress(2, 10, 1, 1));
                kebeleSheet.addMergedRegion(new CellRangeAddress(2, 10, 2, 2));

                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 23));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 24, 26));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 27, 30));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 31, 35));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 36, 41));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 42, 43));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 44, 45));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 46, 50));
                kebeleSheet.addMergedRegion(new CellRangeAddress(0, 0, 51, 55));

                cellStyle = wb.createCellStyle();
                cellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                row0.setRowStyle(cellStyle);

                /// Dispute report
                Sheet disputeSheet = wb.createSheet("Disputes");
                row0 = disputeSheet.createRow((short) 0);
                row0.createCell(11).setCellValue(createHelper.createRichTextString("Forum where the dispute is handled"));

                row1 = disputeSheet.createRow((short) 1);
                row1.createCell(1).setCellValue(createHelper.createRichTextString("Number of Disputes"));
                row1.createCell(2).setCellValue(createHelper.createRichTextString("Number of People"));
                row1.createCell(3).setCellValue(createHelper.createRichTextString("Number of Holders"));
                row1.createCell(4).setCellValue(createHelper.createRichTextString("Number of Disputants"));
                row1.createCell(5).setCellValue(createHelper.createRichTextString("Married Couple"));
                row1.createCell(6).setCellValue(createHelper.createRichTextString("Single Female"));
                row1.createCell(7).setCellValue(createHelper.createRichTextString("Single Male"));
                row1.createCell(8).setCellValue(createHelper.createRichTextString("Orphans"));
                row1.createCell(9).setCellValue(createHelper.createRichTextString("Non Natural"));
                row1.createCell(10).setCellValue(createHelper.createRichTextString("Other"));
                row1.createCell(11).setCellValue(createHelper.createRichTextString("Elder’s Committee"));
                row1.createCell(12).setCellValue(createHelper.createRichTextString("KLAC"));
                row1.createCell(13).setCellValue(createHelper.createRichTextString("Woreda Court"));
                row1.createCell(14).setCellValue(createHelper.createRichTextString("Others"));

                row2 = disputeSheet.createRow((short) 2);
                row2.createCell(0).setCellValue(createHelper.createRichTextString("Inheritance"));
                row2.createCell(1).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByType(kebele, (byte) 1 /*Inheritance*/, "stage > 9"));
                row2.createCell(2).setCellValue(MasterRepository.getInstance().getNumberOfPeopleInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage > 9"));
                row2.createCell(3).setCellValue(MasterRepository.getInstance().getNumberOfHoldersInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage > 9"));
                row2.createCell(4).setCellValue(MasterRepository.getInstance().getNumberOfDisputantsInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage > 9"));
                row2.createCell(5).setCellValue(MasterRepository.getInstance().getNumberOfMarriedCoupleHoldersInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage > 9"));
                row2.createCell(6).setCellValue(MasterRepository.getInstance().getNumberOfSingleFemaleHoldersInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage > 9"));
                row2.createCell(7).setCellValue(MasterRepository.getInstance().getNumberOfSingleMaleHoldersInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage > 9"));
                row2.createCell(8).setCellValue(MasterRepository.getInstance().getNumberOfOrphanHoldersInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage > 9"));
                row2.createCell(9).setCellValue(MasterRepository.getInstance().getNumberOfNonNaturalHoldersInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage > 9"));
                row2.createCell(10).setCellValue(MasterRepository.getInstance().getNumberOfOtherHoldingTypesInDisputeByType(kebele, (byte) 1 /*Inheritance*/, "stage > 9"));
                row2.createCell(11).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 1 /*Inheritance*/, (byte) 1 /*Elders Committee*/, "stage > 9"));
                row2.createCell(12).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 1 /*Inheritance*/, (byte) 2 /*Kebele Land Administration Committee*/, "stage > 9"));
                row2.createCell(13).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 1 /*Inheritance*/, (byte) 3 /*Woreda Court*/, "stage > 9"));
                row2.createCell(14).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 1 /*Inheritance*/, (byte) 4 /*Others*/, "stage > 9"));

                row3 = disputeSheet.createRow((short) 3);
                row3.createCell(0).setCellValue(createHelper.createRichTextString("Ownership right"));
                row3.createCell(1).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage > 9"));
                row3.createCell(2).setCellValue(MasterRepository.getInstance().getNumberOfPeopleInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage > 9"));
                row3.createCell(3).setCellValue(MasterRepository.getInstance().getNumberOfHoldersInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage > 9"));
                row3.createCell(4).setCellValue(MasterRepository.getInstance().getNumberOfDisputantsInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage > 9"));
                row3.createCell(5).setCellValue(MasterRepository.getInstance().getNumberOfMarriedCoupleHoldersInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage > 9"));
                row3.createCell(6).setCellValue(MasterRepository.getInstance().getNumberOfSingleFemaleHoldersInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage > 9"));
                row3.createCell(7).setCellValue(MasterRepository.getInstance().getNumberOfSingleMaleHoldersInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage > 9"));
                row3.createCell(8).setCellValue(MasterRepository.getInstance().getNumberOfOrphanHoldersInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage > 9"));
                row3.createCell(9).setCellValue(MasterRepository.getInstance().getNumberOfNonNaturalHoldersInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage > 9"));
                row3.createCell(10).setCellValue(MasterRepository.getInstance().getNumberOfOtherHoldingTypesInDisputeByType(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, "stage > 9"));
                row3.createCell(11).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, (byte) 1 /*Elders Committee*/, "stage > 9"));
                row3.createCell(12).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, (byte) 2 /*Kebele Land Administration Committee*/, "stage > 9"));
                row3.createCell(13).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, (byte) 3 /*Woreda Court*/, "stage > 9"));
                row3.createCell(14).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 2 /*"Ownership" of Land Use Right*/, (byte) 4 /*Others*/, "stage > 9"));
                row4 = disputeSheet.createRow((short) 4);
                row4.createCell(0).setCellValue(createHelper.createRichTextString("Boundary"));
                row4.createCell(1).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByType(kebele, (byte) 3 /*"Boundary"*/, "stage > 9"));
                row4.createCell(2).setCellValue(MasterRepository.getInstance().getNumberOfPeopleInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage > 9"));
                row4.createCell(3).setCellValue(MasterRepository.getInstance().getNumberOfHoldersInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage > 9"));
                row4.createCell(4).setCellValue(MasterRepository.getInstance().getNumberOfDisputantsInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage > 9"));
                row4.createCell(5).setCellValue(MasterRepository.getInstance().getNumberOfMarriedCoupleHoldersInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage > 9"));
                row4.createCell(6).setCellValue(MasterRepository.getInstance().getNumberOfSingleFemaleHoldersInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage > 9"));
                row4.createCell(7).setCellValue(MasterRepository.getInstance().getNumberOfSingleMaleHoldersInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage > 9"));
                row4.createCell(8).setCellValue(MasterRepository.getInstance().getNumberOfOrphanHoldersInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage > 9"));
                row4.createCell(9).setCellValue(MasterRepository.getInstance().getNumberOfNonNaturalHoldersInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage > 9"));
                row4.createCell(10).setCellValue(MasterRepository.getInstance().getNumberOfOtherHoldingTypesInDisputeByType(kebele, (byte) 3 /*"Boundary"*/, "stage > 9"));
                row4.createCell(11).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 3 /*"Boundary"*/, (byte) 1 /*Elders Committee*/, "stage > 9"));
                row4.createCell(12).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 3 /*"Boundary"*/, (byte) 2 /*Kebele Land Administration Committee*/, "stage > 9"));
                row4.createCell(13).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 3 /*"Boundary"*/, (byte) 3 /*Woreda Court*/, "stage > 9"));
                row4.createCell(14).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 3 /*"Boundary"*/, (byte) 4 /*Others*/, "stage > 9"));
                row5 = disputeSheet.createRow((short) 5);
                row5.createCell(0).setCellValue(createHelper.createRichTextString("Other"));
                row5.createCell(1).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByType(kebele, (byte) 4 /*Other*/, "stage > 9"));
                row5.createCell(2).setCellValue(MasterRepository.getInstance().getNumberOfPeopleInDisputeByType(kebele, (byte) 4 /*Other*/, "stage > 9"));
                row5.createCell(3).setCellValue(MasterRepository.getInstance().getNumberOfHoldersInDisputeByType(kebele, (byte) 4 /*Other*/, "stage > 9"));
                row5.createCell(4).setCellValue(MasterRepository.getInstance().getNumberOfDisputantsInDisputeByType(kebele, (byte) 4 /*Other*/, "stage > 9"));
                row5.createCell(5).setCellValue(MasterRepository.getInstance().getNumberOfMarriedCoupleHoldersInDisputeByType(kebele, (byte) 4 /*Other*/, "stage > 9"));
                row5.createCell(6).setCellValue(MasterRepository.getInstance().getNumberOfSingleFemaleHoldersInDisputeByType(kebele, (byte) 4 /*Other*/, "stage > 9"));
                row5.createCell(7).setCellValue(MasterRepository.getInstance().getNumberOfSingleMaleHoldersInDisputeByType(kebele, (byte) 4 /*Other*/, "stage > 9"));
                row5.createCell(8).setCellValue(MasterRepository.getInstance().getNumberOfOrphanHoldersInDisputeByType(kebele, (byte) 4 /*Other*/, "stage > 9"));
                row5.createCell(9).setCellValue(MasterRepository.getInstance().getNumberOfNonNaturalHoldersInDisputeByType(kebele, (byte) 4 /*Other*/, "stage > 9"));
                row5.createCell(10).setCellValue(MasterRepository.getInstance().getNumberOfOtherHoldingTypesInDisputeByType(kebele, (byte) 4 /*Other*/, "stage > 9"));
                row5.createCell(11).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 4 /*Other*/, (byte) 1 /*Elders Committee*/, "stage > 9"));
                row5.createCell(12).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 4 /*Other*/, (byte) 2 /*Kebele Land Administration Committee*/, "stage > 9"));
                row5.createCell(13).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 4 /*Other*/, (byte) 3 /*Woreda Court*/, "stage > 9"));
                row5.createCell(14).setCellValue(MasterRepository.getInstance().getNumberOfDisputesByTypeAndStatus(kebele, (byte) 4 /*Other*/, (byte) 4 /*Others*/, "stage > 9"));
                row6 = disputeSheet.createRow((short) 6);
                row6.createCell(0).setCellValue(createHelper.createRichTextString("All"));
                row6.createCell(1).setCellValue(MasterRepository.getInstance().getNumberOfAllDisputes(kebele, "stage > 9"));
                row6.createCell(2).setCellValue(MasterRepository.getInstance().getNumberOfPeopleAllDisputes(kebele, "stage > 9"));
                row6.createCell(3).setCellValue(MasterRepository.getInstance().getNumberOfHoldersInAllDisputes(kebele, "stage > 9"));
                row6.createCell(4).setCellValue(MasterRepository.getInstance().getNumberOfDisputantsInAllDisputes(kebele, "stage > 9"));
                row6.createCell(5).setCellValue(MasterRepository.getInstance().getNumberOfMarriedCoupleHoldersInAllDisputes(kebele, "stage > 9"));
                row6.createCell(6).setCellValue(MasterRepository.getInstance().getNumberOfSingleFemaleHoldersInAllDisputes(kebele, "stage > 9"));
                row6.createCell(7).setCellValue(MasterRepository.getInstance().getNumberOfSingleMaleHoldersInAllDisputes(kebele, "stage > 9"));
                row6.createCell(8).setCellValue(MasterRepository.getInstance().getNumberOfOrphanHoldersInAllDisputes(kebele, "stage > 9"));
                row6.createCell(9).setCellValue(MasterRepository.getInstance().getNumberOfNonNaturalHoldersInAllDisputes(kebele, "stage > 9"));
                row6.createCell(10).setCellValue(MasterRepository.getInstance().getNumberOfOtherHoldingTypesInAllDisputes(kebele, "stage > 9"));
                row6.createCell(11).setCellValue(MasterRepository.getInstance().getNumberOfAllDisputesByStatus(kebele, (byte) 1 /*Elders Committee*/, "stage > 9"));
                row6.createCell(12).setCellValue(MasterRepository.getInstance().getNumberOfAllDisputesByStatus(kebele, (byte) 2 /*Kebele Land Administration Committee*/, "stage > 9"));
                row6.createCell(13).setCellValue(MasterRepository.getInstance().getNumberOfAllDisputesByStatus(kebele, (byte) 3 /*Woreda Court*/, "stage > 9"));
                row6.createCell(14).setCellValue(MasterRepository.getInstance().getNumberOfAllDisputesByStatus(kebele, (byte) 4 /*Others*/, "stage > 9"));
                disputeSheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 14));
                //for (int x = 0; x < 14/*Number of columns*/; x++) {
                //    disputeSheet.autoSizeColumn(x, true);
                //}
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return wb;
    }    
    
    public static Workbook generateApprovalReport(long kebele, String kebeleName, String fileName) {
        MasterRepository.getInstance().updateParcelArea(kebele);
        //refreshViews();
        Workbook wb = new XSSFWorkbook();
        try {
            CreationHelper createHelper = wb.getCreationHelper();
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {

                // Kebele report sheet
                Sheet kebeleSheet = wb.createSheet(kebeleName);
                // level 1 headers 
                Row titleRow = kebeleSheet.createRow((short) 0);
                Row dataRow = kebeleSheet.createRow((short) 1);

                CellStyle alignmentStyleRight = wb.createCellStyle();
                alignmentStyleRight.setAlignment(CellStyle.ALIGN_RIGHT);
                alignmentStyleRight.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

                titleRow.createCell(0).setCellValue(createHelper.createRichTextString("Kebele"));
                titleRow.createCell(1).setCellValue(createHelper.createRichTextString("Not Checked"));
                titleRow.createCell(2).setCellValue(createHelper.createRichTextString("Approved"));
                titleRow.createCell(3).setCellValue(createHelper.createRichTextString("Not Approved"));
                titleRow.createCell(4).setCellValue(createHelper.createRichTextString("Printed"));
                titleRow.createCell(5).setCellValue(createHelper.createRichTextString("Not Printed"));
                titleRow.createCell(6).setCellValue(createHelper.createRichTextString("Total"));

                // level 2 headers  
                dataRow.createCell(0).setCellValue(createHelper.createRichTextString(kebeleName));
                dataRow.createCell(1).setCellValue(MasterRepository.getInstance().getCountOfNotCheckedParcels(kebele));
                dataRow.createCell(2).setCellValue(MasterRepository.getInstance().getCountOfApprovedParcels(kebele));

                dataRow.createCell(3).setCellValue(MasterRepository.getInstance().getCountOfNotApprovedParcels(kebele));
                dataRow.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfPrintedParcels(kebele));

                dataRow.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfNotPrintedParcels(kebele));
                dataRow.createCell(6).setCellValue(MasterRepository.getInstance().getCountOfAllParcels(kebele));

                // Format the sheet
                CellStyle boldCellStyle = wb.createCellStyle();
                Font hSSFFont = wb.createFont();
                hSSFFont.setFontName("Calibri");
                hSSFFont.setFontHeightInPoints((short) 11);
                hSSFFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
                hSSFFont.setBold(true);
                boldCellStyle.setWrapText(true);
                boldCellStyle.setFont(hSSFFont);
                for (int x = 0; x < 3; x++) {
                    kebeleSheet.autoSizeColumn(x, true);
                }

                wb.write(fileOut);
            }
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return wb;
    }

    public static Workbook generateApprovalChecklist(long kebele, String kebeleName, String fileName) {
        MasterRepository.getInstance().updateParcelArea(kebele);
        
        Workbook wb = new XSSFWorkbook();
        try {
            CreationHelper createHelper = wb.getCreationHelper();
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                ArrayList<ParcelApprovalDetailDTO> parcels = MasterRepository.getInstance().getParcelsForApproval(kebele);
                Sheet parcelsSheet = wb.createSheet("Parcels in " + kebeleName);
                Row row0 = parcelsSheet.createRow((short) 0);
                row0.createCell(0).setCellValue(createHelper.createRichTextString(CommonStorage.getText("administrative_upi")));
                row0.createCell(1).setCellValue(createHelper.createRichTextString(CommonStorage.getText("holding_number")));
                row0.createCell(2).setCellValue(createHelper.createRichTextString(CommonStorage.getText("area")));
                row0.createCell(3).setCellValue(createHelper.createRichTextString(CommonStorage.getText("has_dispute")));
                row0.createCell(4).setCellValue(createHelper.createRichTextString(CommonStorage.getText("incomplete")));

                for (int i = 0; i < parcels.size(); i++) {
                    Row tempRow = parcelsSheet.createRow(i+1);
                    tempRow.createCell(0).setCellValue(parcels.get(i).getUpi());
                    tempRow.createCell(1).setCellValue(parcels.get(i).getHoldingNumber());
                    tempRow.createCell(2).setCellValue(parcels.get(i).getArea());
                    tempRow.createCell(3).setCellValue(parcels.get(i).hasDispute());
                    tempRow.createCell(4).setCellValue(parcels.get(i).hasMissingValue());

                }
                CellStyle cellStyle = wb.createCellStyle();
                Font hSSFFont = wb.createFont();
                hSSFFont.setFontName("Calibri");
                hSSFFont.setFontHeightInPoints((short) 11);
                cellStyle.setWrapText(true);
                cellStyle.setFont(hSSFFont);
                hSSFFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
                hSSFFont.setBold(true);
                row0.setRowStyle(cellStyle);

                for (int x = 0; x < 2; x++) {
                    parcelsSheet.autoSizeColumn(x, true);
                }
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return wb;
    }

    public static ArrayList<DailyPerformance> generateDailyReport(Date date) {
        return MasterRepository.getInstance().getDailyReport(date);
    }

    public static Workbook generateTimeBoundPerformanceReport(Date fromDate, Date toDate, String fileName) {
        Workbook wb = new XSSFWorkbook();
        try {
            CreationHelper createHelper = wb.getCreationHelper();
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                ArrayList<ParcelStatusDetailDTO> results = MasterRepository.getInstance().getParcelsStatus(fromDate, toDate);
                Sheet parcelsSheet = wb.createSheet("Parcels");
                Row row0 = parcelsSheet.createRow((short) 0);
                row0.createCell(0).setCellValue(createHelper.createRichTextString(CommonStorage.getText("administrative_upi")));
                row0.createCell(1).setCellValue(createHelper.createRichTextString(CommonStorage.getText("first_entry_operator")));
                row0.createCell(2).setCellValue(createHelper.createRichTextString(CommonStorage.getText("second_entry_operator")));
                row0.createCell(3).setCellValue(createHelper.createRichTextString(CommonStorage.getText("corrections")));
                row0.createCell(4).setCellValue(createHelper.createRichTextString(CommonStorage.getText("committed")));

                for (int i = 0; i < results.size(); i++) {
                    Row tempRow = parcelsSheet.createRow(i+1);
                    tempRow.createCell(0).setCellValue(results.get(i).getUpi());
                    tempRow.createCell(1).setCellValue(results.get(i).getFirstEntry());
                    tempRow.createCell(2).setCellValue(results.get(i).getSecondEntry());
                    tempRow.createCell(3).setCellValue(results.get(i).isCorrectionText());
                    tempRow.createCell(4).setCellValue(results.get(i).isCorrectionText());

                }
                CellStyle cellStyle = wb.createCellStyle();
                Font hSSFFont = wb.createFont();
                hSSFFont.setFontName("Calibri");
                hSSFFont.setFontHeightInPoints((short) 11);
                cellStyle.setWrapText(true);
                cellStyle.setFont(hSSFFont);
                hSSFFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
                hSSFFont.setBold(true);
                row0.setRowStyle(cellStyle);

                ArrayList<DEOPeformanceDetailDTO> deos = MasterRepository.getInstance().getDEOPerformance(fromDate, toDate);
                Sheet deosSheet = wb.createSheet("DEOs' performance ");
                row0 = deosSheet.createRow((short) 0);
                row0.createCell(0).setCellValue(createHelper.createRichTextString(CommonStorage.getText("name")));
                row0.createCell(1).setCellValue(createHelper.createRichTextString(CommonStorage.getText("first_entry_operator")));
                row0.createCell(2).setCellValue(createHelper.createRichTextString(CommonStorage.getText("second_entry_operator")));
                row0.createCell(3).setCellValue(createHelper.createRichTextString(CommonStorage.getText("corrections")));
                row0.createCell(4).setCellValue(createHelper.createRichTextString(CommonStorage.getText("total")));

                for (int i = 0; i < deos.size(); i++) {
                    Row tempRow = deosSheet.createRow(i + 1);
                    tempRow.createCell(0).setCellValue(deos.get(i).getName());
                    tempRow.createCell(1).setCellValue(deos.get(i).getFirstEntry());
                    tempRow.createCell(2).setCellValue(deos.get(i).getSecondEntry());
                    tempRow.createCell(3).setCellValue(deos.get(i).getCorrection());
                    tempRow.createCell(4).setCellValue(deos.get(i).getTotal());

                }
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return wb;
    }

    public static Workbook generateKebelePerformanceReport(long kebele, String kebeleName, String fileName) {
        Workbook wb = new XSSFWorkbook();
        try {
            CreationHelper createHelper = wb.getCreationHelper();
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                ArrayList<ParcelStatusDetailDTO> parcels = MasterRepository.getInstance().getParcelsStatus(kebele);
                Sheet parcelsSheet = wb.createSheet("Parcels in " + kebeleName);
                Row row0 = parcelsSheet.createRow((short) 0);
                row0.createCell(0).setCellValue(createHelper.createRichTextString(CommonStorage.getText("administrative_upi")));
                row0.createCell(1).setCellValue(createHelper.createRichTextString(CommonStorage.getText("first_entry_operator")));
                row0.createCell(2).setCellValue(createHelper.createRichTextString(CommonStorage.getText("second_entry_operator")));
                row0.createCell(3).setCellValue(createHelper.createRichTextString(CommonStorage.getText("corrections")));
                row0.createCell(4).setCellValue(createHelper.createRichTextString(CommonStorage.getText("committed")));

                for (int i = 0; i < parcels.size(); i++) {
                    Row tempRow = parcelsSheet.createRow(i + 1);
                    tempRow.createCell(0).setCellValue(parcels.get(i).getUpi());
                    tempRow.createCell(1).setCellValue(parcels.get(i).getFirstEntry());
                    tempRow.createCell(2).setCellValue(parcels.get(i).getSecondEntry());
                    tempRow.createCell(3).setCellValue(parcels.get(i).isCorrection());
                    tempRow.createCell(4).setCellValue(parcels.get(i).isCommitted());

                }
                CellStyle cellStyle = wb.createCellStyle();
                Font hSSFFont = wb.createFont();
                hSSFFont.setFontName("Calibri");
                hSSFFont.setFontHeightInPoints((short) 11);
                cellStyle.setWrapText(true);
                cellStyle.setFont(hSSFFont);
                hSSFFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
                hSSFFont.setBold(true);
                row0.setRowStyle(cellStyle);

                ArrayList<DEOPeformanceDetailDTO> deos = MasterRepository.getInstance().getDEOPerformance(kebele);
                Sheet deosSheet = wb.createSheet("DEOs' performance " + kebeleName);
                row0 = deosSheet.createRow((short) 0);
                row0.createCell(0).setCellValue(createHelper.createRichTextString(CommonStorage.getText("name")));
                row0.createCell(1).setCellValue(createHelper.createRichTextString(CommonStorage.getText("first_entry_operator")));
                row0.createCell(2).setCellValue(createHelper.createRichTextString(CommonStorage.getText("second_entry_operator")));
                row0.createCell(3).setCellValue(createHelper.createRichTextString(CommonStorage.getText("corrections")));
                row0.createCell(4).setCellValue(createHelper.createRichTextString(CommonStorage.getText("total")));

                for (int i = 0; i < deos.size(); i++) {
                    Row tempRow = deosSheet.createRow(i+1);
                    tempRow.createCell(0).setCellValue(deos.get(i).getName());
                    tempRow.createCell(1).setCellValue(deos.get(i).getFirstEntry());
                    tempRow.createCell(2).setCellValue(deos.get(i).getSecondEntry());
                    tempRow.createCell(3).setCellValue(deos.get(i).getCorrection());
                    tempRow.createCell(4).setCellValue(deos.get(i).getTotal());

                }
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return wb;
    }

    public static Workbook generateHolderList(long kebele, String kebeleName, String fileName) {
        Workbook wb = new XSSFWorkbook();
        try {
            CreationHelper createHelper = wb.getCreationHelper();
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                ArrayList<HolderListDTO> indvidualHolders = MasterRepository.getInstance().getIndividualHolderList(kebele);
                Sheet indvidualSheet = wb.createSheet("Indvidual Holders in " + kebeleName);
                Row row0 = indvidualSheet.createRow((short) 0);
                row0.createCell(0).setCellValue(createHelper.createRichTextString(CommonStorage.getText("holder_id")));
                row0.createCell(1).setCellValue(createHelper.createRichTextString(CommonStorage.getText("name")));
                row0.createCell(2).setCellValue(createHelper.createRichTextString(CommonStorage.getText("parcels")));

                for (int i = 0; i < indvidualHolders.size(); i++) {
                    Row tempRow = indvidualSheet.createRow(i+1);
                    tempRow.createCell(0).setCellValue(indvidualHolders.get(i).getPhotoId());
                    tempRow.createCell(1).setCellValue(indvidualHolders.get(i).getFullNume());
                    tempRow.createCell(2).setCellValue(indvidualHolders.get(i).getUpis());
                }

                CellStyle cellStyle = wb.createCellStyle();
                Font hSSFFont = wb.createFont();
                hSSFFont.setFontName("Calibri");
                hSSFFont.setFontHeightInPoints((short) 11);
                cellStyle.setWrapText(true);
                cellStyle.setFont(hSSFFont);
                hSSFFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
                hSSFFont.setBold(true);
                row0.setRowStyle(cellStyle);

                wb.write(fileOut);
            }
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return wb;
    }

    public static Workbook generatePrintedCertificateList(long kebele, String kebeleName, String fileName) {
        Workbook wb = new XSSFWorkbook();
        try {
            CreationHelper createHelper = wb.getCreationHelper();
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                ArrayList<PrintListDTO> indvidualHolders = MasterRepository.getInstance().getPrintListForIndividualHolder(kebele);
                
                Sheet indvidualSheet = wb.createSheet("Indvidual Holders in " + kebeleName);
                Row row0 = indvidualSheet.createRow((short) 0);
                row0.createCell(0).setCellValue(createHelper.createRichTextString(CommonStorage.getText("administrative_upi")));
                row0.createCell(1).setCellValue(createHelper.createRichTextString(CommonStorage.getText("name")));
                row0.createCell(2).setCellValue(createHelper.createRichTextString(CommonStorage.getText("holder_id")));
                
                row0.createCell(3).setCellValue(createHelper.createRichTextString(CommonStorage.getText("date")));
                row0.createCell(4).setCellValue(createHelper.createRichTextString(CommonStorage.getText("signature")));
                row0.createCell(5).setCellValue(createHelper.createRichTextString(CommonStorage.getText("date_sent_to_wc")));
                row0.createCell(6).setCellValue(createHelper.createRichTextString(CommonStorage.getText("date_corrected")));
                row0.createCell(7).setCellValue(createHelper.createRichTextString(CommonStorage.getText("not_collected_sent_to_ka")));

                for (int i = 0; i < indvidualHolders.size(); i++) {
                    Row tempRow = indvidualSheet.createRow(i+1);
                    tempRow.createCell(0).setCellValue(indvidualHolders.get(i).getUPI());
                    tempRow.createCell(1).setCellValue(indvidualHolders.get(i).getHolderName());
                    tempRow.createCell(2).setCellValue(indvidualHolders.get(i).getPhotoId());
                    
                }

                CellStyle cellStyle = wb.createCellStyle();
                Font hSSFFont = wb.createFont();
                hSSFFont.setFontName("Calibri");
                hSSFFont.setFontHeightInPoints((short) 11);
                cellStyle.setWrapText(true);
                cellStyle.setFont(hSSFFont);
                hSSFFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
                hSSFFont.setBold(true);
                row0.setRowStyle(cellStyle);
                
                
                ArrayList<PrintListDTO> organizationalolders = MasterRepository.getInstance().getPrintListForOrganizationalHolder(kebele);
                Sheet organizationalSheet = wb.createSheet("Organizational Holders in " + kebeleName);
                row0 = organizationalSheet.createRow((short) 0);
                row0.createCell(0).setCellValue(createHelper.createRichTextString(CommonStorage.getText("administrative_upi")));
                row0.createCell(1).setCellValue(createHelper.createRichTextString(CommonStorage.getText("name")));
                row0.createCell(2).setCellValue(createHelper.createRichTextString(CommonStorage.getText("holder_id")));
                
                row0.createCell(3).setCellValue(createHelper.createRichTextString(CommonStorage.getText("date")));
                row0.createCell(4).setCellValue(createHelper.createRichTextString(CommonStorage.getText("signature")));
                row0.createCell(5).setCellValue(createHelper.createRichTextString(CommonStorage.getText("date_sent_to_wc")));
                row0.createCell(6).setCellValue(createHelper.createRichTextString(CommonStorage.getText("date_corrected")));
                row0.createCell(7).setCellValue(createHelper.createRichTextString(CommonStorage.getText("not_collected_sent_to_ka")));

                for (int i = 0; i < organizationalolders.size(); i++) {
                    Row tempRow = organizationalSheet.createRow(i+1);
                    tempRow.createCell(0).setCellValue(organizationalolders.get(i).getUPI());
                    tempRow.createCell(1).setCellValue(organizationalolders.get(i).getHolderName());
                    
                }

                row0.setRowStyle(cellStyle);
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return wb;
    }

}
