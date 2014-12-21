package org.lift.massreg.dao;

import java.sql.*;
import java.util.ArrayList;
import org.lift.massreg.dto.*;
import org.lift.massreg.entity.*;
import org.lift.massreg.util.*;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public class MasterRepository {

    private static final MasterRepository instance = new MasterRepository();

    private MasterRepository() {
    }

    public static MasterRepository getInstance() {
        return instance;
    }

    public boolean save(Parcel parcel) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Parcel (upi,stage,registeredBy,registeredOn,"
                + "parcelId,certificateNo,holdingNo, otherEvidence,landUseType,"
                + "soilFertilityType,holdingType,acquisitionType,acquisitionYear,"
                + "surveyDate,mapSheetNo,status,encumbranceType) VALUES (?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, parcel.getUpi());
            stmnt.setByte(2, parcel.getStage());
            stmnt.setLong(3, parcel.getRegisteredBy());
            stmnt.setTimestamp(4, parcel.getRegisteredOn());
            stmnt.setInt(5, parcel.getParcelId());
            stmnt.setString(6, parcel.getCertificateNumber());
            stmnt.setString(7, parcel.getHoldingNumber());
            stmnt.setByte(8, parcel.getOtherEvidence());
            stmnt.setByte(9, parcel.getCurrentLandUse());
            stmnt.setByte(10, parcel.getSoilFertility());
            stmnt.setByte(11, parcel.getHolding());
            stmnt.setByte(12, parcel.getMeansOfAcquisition());
            stmnt.setInt(13, parcel.getAcquisitionYear());
            stmnt.setDate(14, Date.valueOf(parcel.getSurveyDate()));
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
            returnValue = false;
        }
        return returnValue;
    }

    public Parcel getParcel(String upi, byte stage) {
        Parcel returnValue = null;

        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Parcel WHERE upi=? and stage=?");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = new Parcel();
                returnValue.setAcquisition(rs.getByte("acquisitiontype"));
                returnValue.setAcquisitionYear(rs.getInt("acquisitionyear"));
                returnValue.setCertificateNumber(rs.getString("certificateno"));
                returnValue.setCurrentLandUse(rs.getByte("landusetype"));
                returnValue.setEncumbrance(rs.getByte("encumbrancetype"));
                returnValue.setHolding(rs.getByte("holdingtype"));
                returnValue.setHoldingNumber(rs.getString("holdingno"));
                returnValue.setMapSheetNo(rs.getString("mapsheetno"));
                returnValue.setOtherEvidence(rs.getByte("otherevidence"));
                returnValue.setParcelId(rs.getInt("parcelid"));
                returnValue.setRegisteredBy(rs.getLong("registeredby"));
                returnValue.setSoilFertility(rs.getByte("soilfertilitytype"));
                returnValue.setStage(rs.getByte("stage"));
                returnValue.setStatus(rs.getString("status"));
                returnValue.setSurveyDate(rs.getDate("surveydate").toString());
                returnValue.setUpi(rs.getString("upi"));
                returnValue.setRegisteredOn(rs.getTimestamp("registeredon"));
                returnValue.setOrganaizationHolder(getOrganaizationHolder(upi,stage));
                returnValue.setIndividualHolders(getAllIndividualHolders(upi,stage));
                returnValue.setDisputes(getAllDisputes(upi,stage));
            }
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
        }
        
        return returnValue;
    }

    /**
     * Retrieves details of the user specified with the username
     *
     * @return an entity object representing a user
     * @param userName - username of the user to return
     */
    public CurrentUserDTO getCurrentUserDTO(String userName) {
        CurrentUserDTO returnValue = new CurrentUserDTO();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Users WHERE username= ?");
            stmnt.setString(1, userName);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue.setUserId(rs.getLong("userId"));
                returnValue.setUsername(rs.getString("username"));
                returnValue.setFirstName(rs.getString("firstname"));
                returnValue.setFathersName(rs.getString("fathersname"));
                returnValue.setGrandFathersName(rs.getString("grandfathersname"));
                returnValue.setPhoneNumber(rs.getString("phoneNo"));
                returnValue.setStatus(rs.getString("status"));
                String role = rs.getString("role");
                switch (role) {
                    case "FEO":
                        returnValue.setRole(Constants.ROLE.FIRST_ENTRY_OPERATOR);
                        break;
                    case "SEO":
                        returnValue.setRole(Constants.ROLE.SECOND_ENTRY_OPERATOR);
                        break;
                    case "SUPERVISOR":
                        returnValue.setRole(Constants.ROLE.SUPERVISOR);
                        break;
                }
            }
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
        }
        return returnValue;
    }

    public boolean parcelExists(String upi,byte stage ) {
        boolean returnValue = false;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Parcel WHERE upi=? and stage=?");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = true;
            }
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
        }
        return returnValue;
    }

    /**
     * @return an array of all the possible option from the table specified
     * @param table name of the table to query from
     */
    private Option[] getAllOptions(String table) {
        return getAllOptions(table, "code", "textvalue");
    }

    /**
     * @return an array of all the possible option from the table specified
     * @param table name of the table to query from
     * @param key column to be used as key in the option objects
     * @param value name of column to be used as value in the option objects
     */
    private Option[] getAllOptions(String table, String key, String value) {
        ArrayList<Option> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM " + table + " ORDER BY " + key);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                returnValue.add(new Option(rs.getInt(key) + "", rs.getString(value)));
            }
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
        }
        return returnValue.toArray(new Option[0]);
    }

    /**
     * @return an array of option objects representing the possible Other
     * Evidence Types
     */
    public Option[] getAllOtherEvidenceTypes() {
        return getAllOptions("otherevidencetypes");
    }

    /**
     * @return an array of option objects representing the possible Current Land
     * Use Types
     */
    public Option[] getAllCurrentLandUseTypes() {
        return getAllOptions("landusetypes");
    }

    /**
     * @return an array of option objects representing the possible Soil
     * Fertility Types
     */
    public Option[] getAllSoilFertilityTypes() {
        return getAllOptions("soilfertilitytypes");
    }

    /**
     * @return an array of option objects representing the possible holding
     * types
     */
    public Option[] getAllHoldingTypes() {
        return getAllOptions("holdingtypes");
    }

    /**
     * @return an array of option objects representing the possible means of
     * acquisition types
     */
    public Option[] getAllMeansOfAcquisitionTypes() {
        return getAllOptions("acquisitiontypes");
    }

    /**
     * @return an array of option objects representing the possible encumbrance
     * types
     */
    public Option[] getAllEncumbranceTypes() {
        return getAllOptions("encumbrancetypes");
    }

    /**
     * @return an array of option objects representing the possible encumbrance
     * types
     */
    public Option[] getAllStages() {
        return getAllOptions("stages");
    }

    /**
     * @return a hash table of all the kebele values
     */
    public Option[] getAllKebeles() {
        ArrayList<Option> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM kebele WHERE woredacode=? ORDER BY kebelecode");
            stmnt.setString(1, CommonStorage.getCurrentWoredaId());
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                returnValue.add(new Option(rs.getString("kebelecode") + "", rs.getString("name")));
            }
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
        }
        return returnValue.toArray(new Option[0]);
    }

    /**
     * Retrieves details of the user specified with the id
     *
     * @return an entity object representing a user
     * @param userId id of the user to return
     */
    public User getUser(long userId) {
        User returnValue = new User();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Users WHERE userid= ?");
            stmnt.setLong(1, userId);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue.setUserId(rs.getLong("userId"));
                returnValue.setUsername(rs.getString("username"));
                returnValue.setFirstName(rs.getString("firstname"));
                returnValue.setFathersName(rs.getString("fathersname"));
                returnValue.setGrandFathersName(rs.getString("grandfathersname"));
                returnValue.setPhoneNumber(rs.getString("phoneNo"));
                returnValue.setStatus(rs.getString("status"));
                String role = rs.getString("role");
                switch (role) {
                    case "FEO":
                        returnValue.setRole(Constants.ROLE.FIRST_ENTRY_OPERATOR);
                        break;
                    case "SEO":
                        returnValue.setRole(Constants.ROLE.SECOND_ENTRY_OPERATOR);
                        break;
                    case "SUPERVISOR":
                        returnValue.setRole(Constants.ROLE.SUPERVISOR);
                        break;
                }
            }
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
        }
        return returnValue;
    }

    private OrganizationHolder getOrganaizationHolder(String upi, byte stage) {
        return null;
    }
    private ArrayList<IndividualHolder> getAllIndividualHolders(String upi, byte stage) {
        return null;
    }
    private ArrayList<Dispute> getAllDisputes(String upi, byte stage) {
        return null;
    }

}
