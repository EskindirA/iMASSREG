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

    public static Workbook generateTimeBoundReport(Date fromDate, Date toDate, String fileName) {
        Workbook wb = new XSSFWorkbook();
        try {
            CreationHelper createHelper = wb.getCreationHelper();
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                Option[] kebeles = MasterRepository.getInstance().getAllKebeles();
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
                        row1.createCell(4).setCellValue(createHelper.createRichTextString("Total number of non committed parcels"));
                        row1.createCell(5).setCellValue(createHelper.createRichTextString("Total number of committed parcels"));
                        row1.createCell(6).setCellValue(createHelper.createRichTextString("Report generated on"));
                        row1.createCell(7).setCellValue(createHelper.createRichTextString("Start"));
                        row1.createCell(8).setCellValue(createHelper.createRichTextString("End"));

                        Row row2 = tempSheet.createRow((short) 2);
                        row2.createCell(0).setCellValue(createHelper.createRichTextString(CommonStorage.getCurrentWoredaName()));
                        row2.createCell(1).setCellValue(createHelper.createRichTextString(kebele.getValue()));
                        row2.createCell(2).setCellValue(kebele.getKey());
                        row2.createCell(3).setCellValue(createHelper.createRichTextString("Married Couple"));
                        row2.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHolders(fromDate, toDate, fileName));
                        row2.createCell(6).setCellValue(createHelper.createRichTextString((Date.from(Instant.now()).toString())));
                        row2.createCell(7).setCellValue(createHelper.createRichTextString(fromDate.toString()));
                        row2.createCell(8).setCellValue(createHelper.createRichTextString(toDate.toString()));

                        Row row3 = tempSheet.createRow((short) 3);
                        row3.createCell(3).setCellValue(createHelper.createRichTextString("Single Female"));
                        row3.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHolders(fromDate, toDate, fileName));

                        Row row4 = tempSheet.createRow((short) 4);
                        row4.createCell(3).setCellValue(createHelper.createRichTextString("Single Male"));
                        row4.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHolders(fromDate, toDate, fileName));

                        Row row5 = tempSheet.createRow((short) 5);
                        row5.createCell(3).setCellValue(createHelper.createRichTextString("Orphans"));
                        row5.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHolders(fromDate, toDate, fileName));

                        Row row6 = tempSheet.createRow((short) 6);
                        row6.createCell(3).setCellValue(createHelper.createRichTextString("Non Natural"));
                        row6.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHolders(fromDate, toDate, fileName));

                        Row row7 = tempSheet.createRow((short) 7);
                        row7.createCell(3).setCellValue(createHelper.createRichTextString("Other"));
                        row7.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypes(fromDate, toDate, fileName));

                        Row row8 = tempSheet.createRow((short) 8);
                        row8.createCell(3).setCellValue(createHelper.createRichTextString("Dispute"));
                        row8.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDispute(fromDate, toDate, fileName));

                        Row row9 = tempSheet.createRow((short) 9);
                        row9.createCell(3).setCellValue(createHelper.createRichTextString("All"));
                        row9.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfAllNonCommittedParcels(fromDate, toDate, kebele + ""));
                        row9.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfAllCommittedParcels(fromDate, toDate, kebele + ""));

                        // Sheet Format
                        tempSheet.addMergedRegion(new CellRangeAddress(2, 9, 0, 0));
                        tempSheet.addMergedRegion(new CellRangeAddress(2, 9, 1, 1));
                        tempSheet.addMergedRegion(new CellRangeAddress(2, 9, 2, 2));
                        tempSheet.addMergedRegion(new CellRangeAddress(2, 9, 6, 6));
                        tempSheet.addMergedRegion(new CellRangeAddress(2, 9, 7, 7));
                        tempSheet.addMergedRegion(new CellRangeAddress(2, 9, 8, 8));
                        tempSheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 8));

                        tempSheet.autoSizeColumn(0, true);
                        tempSheet.autoSizeColumn(1, true);
                        tempSheet.autoSizeColumn(2, true);
                        tempSheet.autoSizeColumn(3, true);
                        tempSheet.autoSizeColumn(4, true);
                        tempSheet.autoSizeColumn(5, true);
                        tempSheet.autoSizeColumn(6, true);
                        tempSheet.autoSizeColumn(7, true);
                        tempSheet.autoSizeColumn(8, true);

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
        Workbook wb = new XSSFWorkbook();
        try {
            CreationHelper createHelper = wb.getCreationHelper();
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {

                // Kebele report sheet
                Sheet kebeleSheet = wb.createSheet(kebeleName);
                // level 1 headers 
                Row row0 = kebeleSheet.createRow((short) 0);
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
                Row row1 = kebeleSheet.createRow((short) 1);

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
                row1.createCell(9).setCellValue(createHelper.createRichTextString("Total Area"));
                row1.createCell(10).setCellValue(createHelper.createRichTextString("Average area"));
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
                
                // Married Couple row
                Row row2 = kebeleSheet.createRow((short) 2);
                row2.createCell(0).setCellValue(createHelper.createRichTextString(CommonStorage.getCurrentWoredaName()));
                row2.createCell(1).setCellValue(createHelper.createRichTextString(kebeleName));
                row2.createCell(2).setCellValue(kebele);
                row2.createCell(3).setCellValue(createHelper.createRichTextString("Married Couple"));
                row2.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfMarriedCoupleHolders(kebele));
                row2.createCell(5).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row2.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerMarriedCoupleHolder(kebele));
                row2.createCell(7).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row2.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHolders(kebele));
                row2.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderMarriedCoupleHolders(kebele));
                row2.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderMarriedCoupleHolders(kebele));
                row2.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/));
                row2.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByOwnershipEvidence(kebele, (byte) 3/*Court Decision*/));
                row2.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 11 /*Rain fed annual crops*/));
                row2.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 10 /*Rain fed Perennial crops*/));
                row2.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 9 /*Irrigated annual crops*/));
                row2.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 8 /*Irrigated perennial crops*/));
                row2.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 1 /*Grazing Land*/));
                row2.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 2 /*Shrubland/Woodland*/));
                row2.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 3 /*Natural Forest*/));
                row2.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 4 /*Artificial Forest*/));
                row2.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 5 /*Wetland*/));
                row2.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 7 /*Built-Up Areas*/));
                row2.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(kebele, (byte) 6 /*Bareland*/));
                row2.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/));
                row2.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/));
                row2.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/));
                //row2.createCell(27).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByHoldingType(kebele, (byte) 1 /*Individual*/));
                //row2.createCell(28).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByHoldingType(kebele, (byte) 2 /*Communal*/));
                //row2.createCell(29).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByHoldingType(kebele, (byte) 3 /*Public/Instituional*/));
                //row2.createCell(30).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByHoldingType(kebele, (byte) 4 /*State*/));
                row2.createCell(27).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row2.createCell(28).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row2.createCell(29).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row2.createCell(30).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row2.createCell(31).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row2.createCell(32).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row2.createCell(33).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row2.createCell(34).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row2.createCell(35).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row2.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/));
                row2.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/));
                row2.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/));
                row2.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/));
                row2.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/));
                row2.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/));
                row2.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/));
                row2.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderMarriedCoupleHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/));
                row2.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfMarriedCoupleHoldersWithPhysicalImpairment(kebele));
                row2.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfMarriedCoupleHoldersWithoutPhysicalImpairment(kebele));
                row2.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderMarriedCoupleHolders(kebele));
                row2.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderMarriedCoupleHolders(kebele));
                row2.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderMarriedCoupleHolders(kebele));
                row2.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderMarriedCoupleHolders(kebele));
                row2.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderMarriedCoupleHolders(kebele));
                row2.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardianUnderMarriedCoupleHolders(kebele));
                row2.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderMarriedCoupleHolders(kebele));
                row2.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderMarriedCoupleHolders(kebele));
                row2.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderMarriedCoupleHolders(kebele));
                row2.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderMarriedCoupleHolders(kebele));
                row2.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForMarriedHolders(kebele));
                row2.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForMarriedHolders(kebele));
                
                // Single female row
                Row row3 = kebeleSheet.createRow((short) 3);
                row3.createCell(3).setCellValue(createHelper.createRichTextString("Single Female"));
                row3.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfSingleFemaleHolders(kebele));
                row3.createCell(5).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row3.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerSingleFemaleHolder(kebele));
                row3.createCell(7).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row3.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHolders(kebele));
                row3.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderSingleFemaleHolders(kebele));
                row3.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderSingleFemaleHolders(kebele));
                row3.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/));
                row3.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByOwnershipEvidence(kebele, (byte) 3/*Court Decision*/));
                row3.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 11 /*Rain fed annual crops*/));
                row3.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 10 /*Rain fed Perennial crops*/));
                row3.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 9 /*Irrigated annual crops*/));
                row3.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 8 /*Irrigated perennial crops*/));
                row3.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 1 /*Grazing Land*/));
                row3.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 2 /*Shrubland/Woodland*/));
                row3.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 3 /*Natural Forest*/));
                row3.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 4 /*Artificial Forest*/));
                row3.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 5 /*Wetland*/));
                row3.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 7 /*Built-Up Areas*/));
                row3.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(kebele, (byte) 6 /*Bareland*/));
                row3.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/));
                row3.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/));
                row3.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/));
                
                //row3.createCell(27).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByHoldingType(kebele, (byte) 1 /*Individual*/));
                //row3.createCell(28).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByHoldingType(kebele, (byte) 2 /*Communal*/));
                //row3.createCell(29).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByHoldingType(kebele, (byte) 3 /*Public/Instituional*/));
                //row3.createCell(30).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByHoldingType(kebele, (byte) 4 /*State*/));
                row3.createCell(27).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row3.createCell(28).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row3.createCell(29).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row3.createCell(30).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row3.createCell(31).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row3.createCell(32).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row3.createCell(33).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row3.createCell(34).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row3.createCell(35).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row3.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/));
                row3.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/));
                row3.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/));
                row3.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/));
                row3.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/));
                row3.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/));
                row3.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/));
                row3.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleFemaleHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/));
                row3.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfSingleFemaleHoldersWithPhysicalImpairment(kebele));
                row3.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfSingleFemaleHoldersWithoutPhysicalImpairment(kebele));
                row3.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderSingleFemaleHolders(kebele));
                row3.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderSingleFemaleHolders(kebele));
                row3.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderSingleFemaleHolders(kebele));
                row3.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderSingleFemaleHolders(kebele));
                row3.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderSingleFemaleHolders(kebele));

                row3.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderSingleFemaleHolders(kebele));
                row3.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderSingleFemaleHolders(kebele));
                row3.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderSingleFemaleHolders(kebele));
                row3.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderSingleFemaleHolders(kebele));
                row3.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderSingleFemaleHolders(kebele));
                row3.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForSingleFemaleHolders(kebele));
                row3.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForSingleFemaleHolders(kebele));
                                
                // Single male row
                Row row4 = kebeleSheet.createRow((short) 4);
                row4.createCell(3).setCellValue(createHelper.createRichTextString("Single Male"));
                row4.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfSingleMaleHolders(kebele));
                row4.createCell(5).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row4.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerSingleMaleHolder(kebele));
                row4.createCell(7).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row4.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHolders(kebele));
                row4.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderSingleMaleHolders(kebele));
                row4.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderSingleMaleHolders(kebele));
                row4.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/));
                row4.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByOwnershipEvidence(kebele, (byte) 3/*Court Decision*/));
                row4.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 11 /*Rain fed annual crops*/));
                row4.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 10 /*Rain fed Perennial crops*/));
                row4.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 9 /*Irrigated annual crops*/));
                row4.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 8 /*Irrigated perennial crops*/));
                row4.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 1 /*Grazing Land*/));
                row4.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 2 /*Shrubland/Woodland*/));
                row4.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 3 /*Natural Forest*/));
                row4.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 4 /*Artificial Forest*/));
                row4.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 5 /*Wetland*/));
                row4.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 7 /*Built-Up Areas*/));
                row4.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByLandUseType(kebele, (byte) 6 /*Bareland*/));
                row4.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/));
                row4.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/));
                row4.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/));
                //row4.createCell(27).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByHoldingType(kebele, (byte) 1 /*Individual*/));
                //row4.createCell(28).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByHoldingType(kebele, (byte) 2 /*Communal*/));
                //row4.createCell(29).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByHoldingType(kebele, (byte) 3 /*Public/Instituional*/));
                //row4.createCell(30).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByHoldingType(kebele, (byte) 4 /*State*/));
                row4.createCell(27).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row4.createCell(28).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row4.createCell(29).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row4.createCell(30).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row4.createCell(31).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row4.createCell(32).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row4.createCell(33).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row4.createCell(34).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row4.createCell(35).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row4.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/));
                row4.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/));
                row4.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/));
                row4.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/));
                row4.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/));
                row4.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/));
                row4.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/));
                row4.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderSingleMaleHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/));
                row4.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfSingleMaleHoldersWithPhysicalImpairment(kebele));
                row4.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfSingleMaleHoldersWithoutPhysicalImpairment(kebele));
                row4.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderSingleMaleHolders(kebele));
                row4.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderSingleMaleHolders(kebele));
                row4.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderSingleMaleHolders(kebele));
                row4.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderSingleMaleHolders(kebele));
                row4.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderSingleMaleHolders(kebele));

                row4.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderSingleMaleHolders(kebele));
                row4.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderSingleMaleHolders(kebele));
                row4.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderSingleMaleHolders(kebele));
                row4.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderSingleMaleHolders(kebele));
                row4.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderSingleMaleHolders(kebele));
                row4.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForSingleMaleHolders(kebele));
                row4.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForSingleMaleHolders(kebele));
                
                // Orphans row
                Row row5 = kebeleSheet.createRow((short) 5);
                row5.createCell(3).setCellValue(createHelper.createRichTextString("Orphans"));
                row5.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfOrphanHolders(kebele));
                row5.createCell(5).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row5.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerOrphanHolder(kebele));
                row5.createCell(7).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row5.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHolders(kebele));
                row5.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderOrphanHolders(kebele));
                row5.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderOrphanHolders(kebele));
                row5.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/));
                row5.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByOwnershipEvidence(kebele, (byte) 3/*Court Decision*/));
                row5.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 11 /*Rain fed annual crops*/));
                row5.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 10 /*Rain fed Perennial crops*/));
                row5.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 9 /*Irrigated annual crops*/));
                row5.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 8 /*Irrigated perennial crops*/));
                row5.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 1 /*Grazing Land*/));
                row5.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 2 /*Shrubland/Woodland*/));
                row5.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 3 /*Natural Forest*/));
                row5.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 4 /*Artificial Forest*/));
                row5.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 5 /*Wetland*/));
                row5.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 7 /*Built-Up Areas*/));
                row5.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByLandUseType(kebele, (byte) 6 /*Bareland*/));
                row5.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/));
                row5.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/));
                row5.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/));
                //row5.createCell(27).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByHoldingType(kebele, (byte) 1 /*Individual*/));
                //row5.createCell(28).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByHoldingType(kebele, (byte) 2 /*Communal*/));
                //row5.createCell(29).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByHoldingType(kebele, (byte) 3 /*Public/Instituional*/));
                //row5.createCell(30).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByHoldingType(kebele, (byte) 4 /*State*/));
                row5.createCell(27).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row5.createCell(28).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row5.createCell(29).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row5.createCell(30).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row5.createCell(31).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row5.createCell(32).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row5.createCell(33).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row5.createCell(34).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row5.createCell(35).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row5.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/));
                row5.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/));
                row5.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/));
                row5.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/));
                row5.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/));
                row5.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/));
                row5.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/));
                row5.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOrphanHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/));
                row5.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfOrphanHoldersWithPhysicalImpairment(kebele));
                row5.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfOrphanHoldersWithoutPhysicalImpairment(kebele));
                row5.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderOrphanHolders(kebele));
                row5.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderOrphanHolders(kebele));
                row5.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderOrphanHolders(kebele));
                row5.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderOrphanHolders(kebele));
                row5.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderOrphanHolders(kebele));

                row5.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderOrphanHolders(kebele));
                row5.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderOrphanHolders(kebele));
                row5.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderSingleMaleHolders(kebele));
                row5.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderOrphanHolders(kebele));
                row5.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderOrphanHolders(kebele));
                row5.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForOrphanHolders(kebele));
                row5.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForOrphanHolders(kebele));
                
                // Non natural row
                Row row6 = kebeleSheet.createRow((short) 6);
                row6.createCell(3).setCellValue(createHelper.createRichTextString("Non Natural"));
                row6.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfNonNaturalHolders(kebele));
                row6.createCell(5).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row6.createCell(6).setCellValue(MasterRepository.getInstance().getAverageNumberOfParcelsPerNonNaturalHolder(kebele));
                row6.createCell(7).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row6.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHolders(kebele));
                row6.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderNonNaturalHolders(kebele));
                row6.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderNonNaturalHolders(kebele));
                row6.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/));
                row6.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOwnershipEvidence(kebele, (byte) 3/*Court Decision*/));
                row6.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 11 /*Rain fed annual crops*/));
                row6.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 10 /*Rain fed Perennial crops*/));
                row6.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 9 /*Irrigated annual crops*/));
                row6.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 8 /*Irrigated perennial crops*/));
                row6.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 1 /*Grazing Land*/));
                row6.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 2 /*Shrubland/Woodland*/));
                row6.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 3 /*Natural Forest*/));
                row6.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 4 /*Artificial Forest*/));
                row6.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 5 /*Wetland*/));
                row6.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 7 /*Built-Up Areas*/));
                row6.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByLandUseType(kebele, (byte) 6 /*Bareland*/));
                row6.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersBySoilFertility(kebele, (byte) 1 /*High/Good*/));
                row6.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/));
                row6.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersBySoilFertility(kebele, (byte) 3 /*Low/Poor*/));
                row6.createCell(27).setCellValue(createHelper.createRichTextString("")/*Does not make sense*/);
                row6.createCell(28).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByHoldingType(kebele, (byte) 2 /*Communal*/));
                row6.createCell(29).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByHoldingType(kebele, (byte) 3 /*Public/Instituional*/));
                row6.createCell(30).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByHoldingType(kebele, (byte) 4 /*State*/));
                row6.createCell(31).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 2 /*Church*/));
                row6.createCell(32).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 3 /*Mosque*/));
                row6.createCell(33).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 1 /*School*/));
                row6.createCell(34).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 4 /*Health Center*/));
                row6.createCell(35).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(kebele, (byte) 5 /*Other*/));
                row6.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/));
                row6.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/));
                row6.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/));
                row6.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/));
                row6.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/));
                row6.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/));
                row6.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByEncumbrances(kebele, (byte) 1 /*Right of Way*/));
                row6.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderNonNaturalHoldersByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/));
                row6.createCell(44).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row6.createCell(45).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row6.createCell(46).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row6.createCell(47).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row6.createCell(48).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row6.createCell(49).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row6.createCell(50).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row6.createCell(51).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row6.createCell(52).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row6.createCell(53).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row6.createCell(54).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row6.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForNonNaturalHolders(kebele));
                row6.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForNonNaturalHolders(kebele));

                // Other row
                Row row7 = kebeleSheet.createRow((short) 7);
                row7.createCell(3).setCellValue(createHelper.createRichTextString("Other"));
                row7.createCell(4).setCellValue(createHelper.createRichTextString(""/*Value not known*/));
                row7.createCell(5).setCellValue(createHelper.createRichTextString(""/*Value not known*/));
                row7.createCell(6).setCellValue(createHelper.createRichTextString(""/*Value not known*/));
                row7.createCell(7).setCellValue(createHelper.createRichTextString(""/*Value not known*/));
                row7.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypes(kebele));
                row7.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderOtherHoldingTypes(kebele));
                row7.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderOtherHoldingTypes(kebele));
                row7.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/));
                row7.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByOwnershipEvidence(kebele, (byte) 3/*Court Decision*/));
                row7.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 11 /*Rain fed annual crops*/));
                row7.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 10 /*Rain fed Perennial crops*/));
                row7.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 9 /*Irrigated annual crops*/));
                row7.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 8 /*Irrigated perennial crops*/));
                row7.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 1 /*Grazing Land*/));
                row7.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 2 /*Shrubland/Woodland*/));
                row7.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 3 /*Natural Forest*/));
                row7.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 4 /*Artificial Forest*/));
                row7.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 5 /*Wetland*/));
                row7.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 7 /*Built-Up Areas*/));
                row7.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByLandUseType(kebele, (byte) 6 /*Bareland*/));
                row7.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesBySoilFertility(kebele, (byte) 1 /*High/Good*/));
                row7.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/));
                row7.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesBySoilFertility(kebele, (byte) 3 /*Low/Poor*/));
                //row7.createCell(27).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByHoldingType(kebele, (byte) 1 /*Individual*/));
                //row7.createCell(28).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByHoldingType(kebele, (byte) 2 /*Communal*/));
                //row7.createCell(29).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByHoldingType(kebele, (byte) 3 /*Public/Instituional*/));
                //row7.createCell(30).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByHoldingType(kebele, (byte) 4 /*State*/));
                row7.createCell(27).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row7.createCell(28).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row7.createCell(29).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row7.createCell(30).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row7.createCell(31).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row7.createCell(32).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row7.createCell(33).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row7.createCell(34).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row7.createCell(35).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row7.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/));
                row7.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/));
                row7.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/));
                row7.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/));
                row7.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/));
                row7.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/));
                row7.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByEncumbrances(kebele, (byte) 1 /*Right of Way*/));
                row7.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderOtherHoldingTypesByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/));
                row7.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfOtherHoldingTypesWithPhysicalImpairment(kebele));
                row7.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfOtherHoldingTypesWithoutPhysicalImpairment(kebele));
                row7.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderOtherHoldingTypes(kebele));
                row7.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestUnderOtherHoldingTypes(kebele));
                row7.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderOtherHoldingTypes(kebele));
                row7.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestUnderOtherHoldingTypes(kebele));
                row7.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestUnderOtherHoldingTypes(kebele));

                row7.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderOtherHoldingTypes(kebele));
                row7.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsUnderOtherHoldingTypes(kebele));
                row7.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderOtherHoldingTypes(kebele));
                row7.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsUnderOtherHoldingTypes(kebele));
                row7.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsUnderOtherHoldingTypes(kebele));
                row7.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForOtherHoldingTypes(kebele));
                row7.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForOtherHoldingTypes(kebele));

                
                // Dispute row
                Row row8 = kebeleSheet.createRow((short) 8);
                row8.createCell(3).setCellValue(createHelper.createRichTextString("Dispute"));
                row8.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithDispute(kebele));
                row8.createCell(5).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row8.createCell(6).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row8.createCell(7).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row8.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDispute(kebele));
                row8.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsUnderDispute(kebele));
                row8.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsUnderDispute(kebele));
                row8.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/));
                row8.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOwnershipEvidence(kebele, (byte) 3/*Court Decision*/));
                row8.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 11 /*Rain fed annual crops*/));
                row8.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 10 /*Rain fed Perennial crops*/));
                row8.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 9 /*Irrigated annual crops*/));
                row8.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 8 /*Irrigated perennial crops*/));
                row8.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 1 /*Grazing Land*/));
                row8.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 2 /*Shrubland/Woodland*/));
                row8.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 3 /*Natural Forest*/));
                row8.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 4 /*Artificial Forest*/));
                row8.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 5 /*Wetland*/));
                row8.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 7 /*Built-Up Areas*/));
                row8.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByLandUseType(kebele, (byte) 6 /*Bareland*/));
                row8.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeBySoilFertility(kebele, (byte) 1 /*High/Good*/));
                row8.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/));
                row8.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeBySoilFertility(kebele, (byte) 3 /*Low/Poor*/));
                row8.createCell(27).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByHoldingType(kebele, (byte) 1 /*Individual*/));
                row8.createCell(28).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByHoldingType(kebele, (byte) 2 /*Communal*/));
                row8.createCell(29).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByHoldingType(kebele, (byte) 3 /*Public/Instituional*/));
                row8.createCell(30).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByHoldingType(kebele, (byte) 4 /*State*/));
                row8.createCell(31).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 2 /*Church*/));
                row8.createCell(32).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 3 /*Mosque*/));
                row8.createCell(33).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 1 /*School*/));
                row8.createCell(34).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 4 /*Health Center*/));
                row8.createCell(35).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByOrganisationType(kebele, (byte) 5 /*Other*/));
                row8.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/));
                row8.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/));
                row8.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/));
                row8.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/));
                row8.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/));
                row8.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/));
                row8.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByEncumbrances(kebele, (byte) 1 /*Right of Way*/));
                row8.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfParcelsUnderDisputeByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/));
                row8.createCell(44).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row8.createCell(45).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row8.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithPersonsOfInterestUnderDispute(kebele));
                row8.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestForParcelsWithDispute(kebele));
                row8.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelsUnderDispute(kebele));
                row8.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestForParcelsWithDispute(kebele));
                row8.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestForParcelsWithDispute(kebele));

                row8.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfParcelsWithGuardiansUnderDispute(kebele));
                row8.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForParcelsWithDispute(kebele));
                row8.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelsUnderDispute(kebele));
                row8.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForParcelsWithDispute(kebele));
                row8.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForParcelsWithDispute(kebele));
                row8.createCell(56).setCellValue(MasterRepository.getInstance().getFirstDataEntryDateInKebeleForParcelsWithDispute(kebele));
                row8.createCell(57).setCellValue(MasterRepository.getInstance().getLastDataEntryDateInKebeleForParcelsWithDispute(kebele));
                
                // No data row
                Row row9 = kebeleSheet.createRow((short) 9);
                row9.createCell(3).setCellValue(createHelper.createRichTextString("No data"));
                row9.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfParcelsNoData(kebele));
                row9.createCell(5).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(6).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(7).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(8).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfParcelsWithNoData(kebele));
                row9.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfParcelsWithNoData(kebele));
                row9.createCell(11).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(12).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(13).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(14).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(15).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(16).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(17).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(18).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(19).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(20).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(21).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(22).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(23).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(24).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(25).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(26).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(27).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(28).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(29).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(30).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(31).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(32).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(33).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(34).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(35).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(36).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(37).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(38).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(39).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(40).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(41).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(42).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(43).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(44).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(45).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(46).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(47).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(48).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(49).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(50).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(51).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(52).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(53).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(54).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(55).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(56).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row9.createCell(57).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);

                // All row
                Row row10 = kebeleSheet.createRow((short) 10);
                row10.createCell(3).setCellValue(createHelper.createRichTextString("All"));
                row10.createCell(4).setCellValue(MasterRepository.getInstance().getCountOfAllCommittedParcels(kebele));
                row10.createCell(5).setCellValue(MasterRepository.getInstance().getCountOfDemarcatedParcels(kebele));
                row10.createCell(6).setCellValue(createHelper.createRichTextString("")/*Data Not available*/);
                row10.createCell(7).setCellValue(MasterRepository.getInstance().getCountOfAllNonCommittedParcels(kebele));
                row10.createCell(8).setCellValue(MasterRepository.getInstance().getCountOfAllCommittedParcels(kebele));
                row10.createCell(9).setCellValue(MasterRepository.getInstance().getTotalAreaOfAllParcels(kebele));
                row10.createCell(10).setCellValue(MasterRepository.getInstance().getAverageAreaOfAllParcels(kebele));
                row10.createCell(11).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOwnershipEvidence(kebele, (byte) 2/*Tax Receipt*/));
                row10.createCell(12).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOwnershipEvidence(kebele, (byte) 3/*Court Decision*/));
                row10.createCell(13).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 11 /*Rain fed annual crops*/));
                row10.createCell(14).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 10 /*Rain fed Perennial crops*/));
                row10.createCell(15).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 9 /*Irrigated annual crops*/));
                row10.createCell(16).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 8 /*Irrigated perennial crops*/));
                row10.createCell(17).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 1 /*Grazing Land*/));
                row10.createCell(18).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 2 /*Shrubland/Woodland*/));
                row10.createCell(19).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 3 /*Natural Forest*/));
                row10.createCell(20).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 4 /*Artificial Forest*/));
                row10.createCell(21).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 5 /*Wetland*/));
                row10.createCell(22).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 7 /*Built-Up Areas*/));
                row10.createCell(23).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByLandUseType(kebele, (byte) 6 /*Bareland*/));
                row10.createCell(24).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsBySoilFertility(kebele, (byte) 1 /*High/Good*/));
                row10.createCell(25).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsBySoilFertility(kebele, (byte) 2 /*Medium/Fair*/));
                row10.createCell(26).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsBySoilFertility(kebele, (byte) 3 /*Low/Poor*/));
                row10.createCell(27).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByHoldingType(kebele, (byte) 1 /*Individual*/));
                row10.createCell(28).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByHoldingType(kebele, (byte) 2 /*Communal*/));
                row10.createCell(29).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByHoldingType(kebele, (byte) 3 /*Public/Instituional*/));
                row10.createCell(30).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByHoldingType(kebele, (byte) 4 /*State*/));
                row10.createCell(31).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 2 /*Church*/));
                row10.createCell(32).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 3 /*Mosque*/));
                row10.createCell(33).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 1 /*School*/));
                row10.createCell(34).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 4 /*Health Center*/));
                row10.createCell(35).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByOrganisationType(kebele, (byte) 5 /*Other*/));
                row10.createCell(36).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 1 /*Redistribution*/));
                row10.createCell(37).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 2 /*Inheritance*/));
                row10.createCell(38).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 3 /*Gift*/));
                row10.createCell(39).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 5 /*Divorce*/));
                row10.createCell(40).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 4 /*Reallocation*/));
                row10.createCell(41).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByMeansOfAcquisition(kebele, (byte) 7 /*OTHERS*/));
                row10.createCell(42).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByEncumbrances(kebele, (byte) 1 /*Right of Way*/));
                row10.createCell(43).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsByEncumbrances(kebele, (byte) 2 /*Rental arrangement/ leases*/));
                row10.createCell(44).setCellValue(MasterRepository.getInstance().getCountOfAllHoldersWithPhysicalImpairment(kebele));
                row10.createCell(45).setCellValue(MasterRepository.getInstance().getCountOfAllHoldersWithoutPhysicalImpairment(kebele));
                row10.createCell(46).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsWithPersonsOfInterest(kebele));
                row10.createCell(47).setCellValue(MasterRepository.getInstance().getCountOfPersonsWithInterestForAllParcels(kebele));
                row10.createCell(48).setCellValue(MasterRepository.getInstance().getAverageNumberOfPIPerParcelForAllParcels(kebele));
                row10.createCell(49).setCellValue(MasterRepository.getInstance().getCountOfMalePersonsWithInterestForAllParcels(kebele));
                row10.createCell(50).setCellValue(MasterRepository.getInstance().getCountOfFemalePersonsWithInterestForAllParcels(kebele));

                row10.createCell(51).setCellValue(MasterRepository.getInstance().getCountOfAllParcelsWithGuardians(kebele));
                row10.createCell(52).setCellValue(MasterRepository.getInstance().getCountOfGuardiansForAllParcels(kebele));
                row10.createCell(53).setCellValue(MasterRepository.getInstance().getAverageNumberOfGuardiansPerParcelForAllParcels(kebele));
                row10.createCell(54).setCellValue(MasterRepository.getInstance().getCountOfMaleGuardiansForAllParcels(kebele));
                row10.createCell(55).setCellValue(MasterRepository.getInstance().getCountOfFemaleGuardiansForAllParcels(kebele));
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
                cellStyle.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                row0.setRowStyle(cellStyle);

                /// Dispute report
                Sheet disputeSheet = wb.createSheet("Disputes");
                row0 = disputeSheet.createRow((short) 0);
                row0.createCell(13).setCellValue(createHelper.createRichTextString("Forum where the dispute is handled"));

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
                row1.createCell(11).setCellValue(createHelper.createRichTextString("Single Male"));
                row1.createCell(12).setCellValue(createHelper.createRichTextString("Elder’s Committee"));
                row1.createCell(13).setCellValue(createHelper.createRichTextString("KLAC"));
                row1.createCell(14).setCellValue(createHelper.createRichTextString("Woreda Court"));
                row1.createCell(15).setCellValue(createHelper.createRichTextString("Others"));

                row2 = disputeSheet.createRow((short) 2);
                row2.createCell(0).setCellValue(createHelper.createRichTextString("Inheritance"));
                row2.createCell(1).setCellValue(createHelper.createRichTextString("Inheritance - Number of Disputes"));

                row3 = disputeSheet.createRow((short) 3);
                row3.createCell(0).setCellValue(createHelper.createRichTextString("Ownership right"));
                row3.createCell(1).setCellValue(createHelper.createRichTextString("Ownership right - Number of Disputes"));

                row4 = disputeSheet.createRow((short) 4);
                row4.createCell(0).setCellValue(createHelper.createRichTextString("Boundary"));

                row5 = disputeSheet.createRow((short) 5);
                row5.createCell(0).setCellValue(createHelper.createRichTextString("All"));

                disputeSheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 15));

                ///TODO: the merged regions
                /*
                 disputeSheet.addMergedRegion(new CellRangeAddress(2, 9, 0, 0));
                 disputeSheet.addMergedRegion(new CellRangeAddress(2, 9, 1, 1));
                 disputeSheet.addMergedRegion(new CellRangeAddress(2, 9, 2, 2));
                 disputeSheet.addMergedRegion(new CellRangeAddress(2, 9, 6, 6));
                 disputeSheet.addMergedRegion(new CellRangeAddress(2, 9, 7, 7));
                 disputeSheet.addMergedRegion(new CellRangeAddress(2, 9, 8, 8));
                 disputeSheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 8));
                 */
                wb.write(fileOut);
            }
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return wb;
    }
}
