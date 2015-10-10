package org.lift.massreg.dao;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
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
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute, teamNo,holding_lot) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?, ?,?)";
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
            stmnt.setString(14, parcel.getSurveyDate());
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            stmnt.setBoolean(18, parcel.hasDispute());
            stmnt.setByte(19, parcel.getTeamNo());
            stmnt.setInt(20, parcel.getHoldingLotNumber());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, parcel.getRegisteredBy());
            stmnt.setString(3, "Parcel");
            stmnt.setString(4, "NEW");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean save(IndividualHolder holder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO individualholder( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth, familyrole, holderId, physicalImpairment, isOrphan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, holder.getUpi());
            stmnt.setByte(2, holder.getStage());
            stmnt.setLong(3, holder.getRegisteredBy());
            stmnt.setTimestamp(4, holder.getRegisteredOn());
            stmnt.setString(5, holder.getFirstName());
            stmnt.setString(6, holder.getFathersName());
            stmnt.setString(7, holder.getGrandFathersName());
            stmnt.setString(8, holder.getSex());
            stmnt.setString(9, holder.getDateOfBirth());
            stmnt.setByte(10, holder.getFamilyRole());
            stmnt.setString(11, holder.getId());
            stmnt.setBoolean(12, holder.hasPhysicalImpairment());
            stmnt.setBoolean(13, holder.isOrphan());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, holder.getRegisteredBy());
            stmnt.setString(3, "IndividualHolder");
            stmnt.setString(4, "NEW");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean save(PersonWithInterest personWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO PersonWithInterest( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, personWithInterest.getUpi());
            stmnt.setByte(2, personWithInterest.getStage());
            stmnt.setLong(3, personWithInterest.getRegisteredBy());
            stmnt.setTimestamp(4, personWithInterest.getRegisteredOn());
            stmnt.setString(5, personWithInterest.getFirstName());
            stmnt.setString(6, personWithInterest.getFathersName());
            stmnt.setString(7, personWithInterest.getGrandFathersName());
            stmnt.setString(8, personWithInterest.getSex());
            stmnt.setString(9, personWithInterest.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, personWithInterest.getRegisteredBy());
            stmnt.setString(3, "PersonWithInterest");
            stmnt.setString(4, "NEW");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean save(Guardian guardian) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Guardian( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, guardian.getUpi());
            stmnt.setByte(2, guardian.getStage());
            stmnt.setLong(3, guardian.getRegisteredBy());
            stmnt.setTimestamp(4, guardian.getRegisteredOn());
            stmnt.setString(5, guardian.getFirstName());
            stmnt.setString(6, guardian.getFathersName());
            stmnt.setString(7, guardian.getGrandFathersName());
            stmnt.setString(8, guardian.getSex());
            stmnt.setString(9, guardian.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, guardian.getRegisteredBy());
            stmnt.setString(3, "Guardian");
            stmnt.setString(4, "NEW");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean save(Dispute dispute) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO dispute(upi, stage, registeredby, registeredon,"
                + "firstname, fathersname, grandfathersname, sex, disputetype, "
                + "disputestatus,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, dispute.getUpi());
            stmnt.setByte(2, dispute.getStage());
            stmnt.setLong(3, dispute.getRegisteredBy());
            stmnt.setTimestamp(4, dispute.getRegisteredOn());
            stmnt.setString(5, dispute.getFirstName());
            stmnt.setString(6, dispute.getFathersName());
            stmnt.setString(7, dispute.getGrandFathersName());
            stmnt.setString(8, dispute.getSex());
            stmnt.setByte(9, dispute.getDisputeType());
            stmnt.setByte(10, dispute.getDisputeStatus());
            stmnt.setString(11, "active");
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, dispute.getRegisteredBy());
            stmnt.setString(3, "Dispute");
            stmnt.setString(4, "NEW");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean save(HttpServletRequest request, User user) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO users(username, password, firstname,"
                + " fathersname, grandfathersname, phoneno, role) VALUES "
                + "( ?, (SELECT MD5(?)), ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, user.getUsername());
            stmnt.setString(2, user.getPassword());
            stmnt.setString(3, user.getFirstName());
            stmnt.setString(4, user.getFathersName());
            stmnt.setString(5, user.getGrandFathersName());
            stmnt.setString(6, user.getPhoneNumber());
            stmnt.setString(7, user.getRoleText());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            query = "INSERT INTO groups(username, groupid) VALUES (?, 'user')";
            stmnt = connection.prepareStatement(query);
            stmnt.setString(1, user.getUsername());
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "User");
            stmnt.setString(4, "NEW");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean save(OrganizationHolder holder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO organizationholder(upi, stage, organizationname, "
                + "registeredby, registeredon, organizationtype) VALUES "
                + "( ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, holder.getUpi());
            stmnt.setByte(2, holder.getStage());
            stmnt.setString(3, holder.getName());
            stmnt.setLong(4, holder.getRegisteredby());
            stmnt.setTimestamp(5, holder.getRegisteredon());
            stmnt.setByte(6, holder.getOrganizationType());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, holder.getRegisteredby());
            stmnt.setString(3, "OrganizationHolder");
            stmnt.setString(4, "NEW");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public Parcel getParcel(String upi, byte stage) {
        Parcel returnValue = null;

        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Parcel WHERE upi=? and stage=? and status='active'");
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
                returnValue.setSurveyDate(rs.getString("surveydate"));
                returnValue.setUpi(rs.getString("upi"));
                returnValue.setRegisteredOn(rs.getTimestamp("registeredon"));
                returnValue.hasDispute(rs.getBoolean("hasdispute"));
                returnValue.setTeamNo(rs.getByte("teamNo"));
                returnValue.setHoldingLotNumber(rs.getInt("holding_lot"));
                if (returnValue.hasDispute()) {
                    returnValue.setDisputes(getAllDisputes(upi, stage));
                }
                if (returnValue.getHolding() == 1) {
                    returnValue.setIndividualHolders(getAllIndividualHolders(upi, stage));
                    returnValue.setGuardians(getAllGuardians(upi, stage));
                    returnValue.setPersonsWithInterest(getAllPersonsWithInterest(upi, stage));
                } else {
                    returnValue.setOrganaizationHolder(getOrganaizationHolder(upi, stage));
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
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
                String role = rs.getString("role").toUpperCase();
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
                    case "ADMINISTRATOR":
                        returnValue.setRole(Constants.ROLE.ADMINISTRATOR);
                        break;
                    case "PDC":
                        returnValue.setRole(Constants.ROLE.POSTPDCOORDINATOR);
                        break;
                    case "WC":
                        returnValue.setRole(Constants.ROLE.WOREDA_COORDINATOR);
                        break;
                    case "MCO":
                        returnValue.setRole(Constants.ROLE.MINOR_CORRECTION_OFFICER);
                        break;
                    case "CFEO":
                        returnValue.setRole(Constants.ROLE.CORRECTION_FIRST_ENTRY_OPERATOR);
                        break;
                    case "CSEO":
                        returnValue.setRole(Constants.ROLE.CORRECTION_SECOND_ENTRY_OPERATOR);
                        break;
                    case "CSUPER":
                        returnValue.setRole(Constants.ROLE.CORRECTION_SUPERVISOR);
                        break;
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean parcelExists(String upi, byte stage) {
        boolean returnValue = false;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Parcel WHERE upi=? and stage=? and status='active'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = true;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean isParcelComplete(String upi, byte stage) {
        boolean returnValue = false;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Parcel WHERE upi=? and stage=? and status='active' and completed='true'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = true;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
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
                    case "ADMINISTRATOR":
                        returnValue.setRole(Constants.ROLE.ADMINISTRATOR);
                        break;
                    case "PDC":
                        returnValue.setRole(Constants.ROLE.POSTPDCOORDINATOR);
                        break;
                    case "WC":
                        returnValue.setRole(Constants.ROLE.WOREDA_COORDINATOR);
                        break;
                    case "MCO":
                        returnValue.setRole(Constants.ROLE.MINOR_CORRECTION_OFFICER);
                        break;
                    case "CFEO":
                        returnValue.setRole(Constants.ROLE.CORRECTION_FIRST_ENTRY_OPERATOR);
                        break;
                    case "CSEO":
                        returnValue.setRole(Constants.ROLE.CORRECTION_SECOND_ENTRY_OPERATOR);
                        break;
                    case "CSUPER":
                        returnValue.setRole(Constants.ROLE.CORRECTION_SUPERVISOR);
                        break;
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    private OrganizationHolder getOrganaizationHolder(String upi, byte stage) {
        OrganizationHolder returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM organizationholder WHERE upi=? and stage=? and status='active'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = new OrganizationHolder();
                returnValue.setName(rs.getString("organizationname"));
                returnValue.setUpi(rs.getString("upi"));
                returnValue.setStage(rs.getByte("stage"));
                returnValue.setRegisteredon(rs.getTimestamp("registeredon"));
                returnValue.setRegisteredby(rs.getLong("registeredby"));
                returnValue.setOrganizationType(rs.getByte("organizationtype"));
                returnValue.setStatus(rs.getString("status"));

            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<IndividualHolder> getAllIndividualHolders(String upi, byte stage) {
        ArrayList<IndividualHolder> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM individualholder WHERE upi=? and stage=? and status='active'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                IndividualHolder ih = new IndividualHolder();
                ih.setDateOfBirth(rs.getString("dateofbirth"));
                ih.setFamilyRole(rs.getByte("familyrole"));
                ih.setFirstName(rs.getString("firstname"));
                ih.setFathersName(rs.getString("fathersname"));
                ih.setGrandFathersName(rs.getString("grandfathersname"));
                ih.setId(rs.getString("holderId"));
                ih.setRegisteredBy(rs.getLong("registeredby"));
                ih.setRegisteredOn(rs.getTimestamp("registeredon"));
                ih.setSex(rs.getString("sex"));
                ih.hasPhysicalImpairment(rs.getBoolean("physicalimpairment"));
                ih.isOrphan(rs.getBoolean("isOrphan"));
                ih.setStage(rs.getByte("stage"));
                ih.setUpi(rs.getString("upi"));
                returnValue.add(ih);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }

        return returnValue;
    }

    public ArrayList<Dispute> getAllDisputes(String upi, byte stage) {
        ArrayList<Dispute> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM dispute WHERE upi=? and stage=? and status='active'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                Dispute dispute = new Dispute();
                dispute.setFirstName(rs.getString("firstname"));
                dispute.setFathersName(rs.getString("fathersname"));
                dispute.setGrandFathersName(rs.getString("grandfathersname"));
                dispute.setRegisteredBy(rs.getLong("registeredby"));
                dispute.setRegisteredOn(rs.getTimestamp("registeredon"));
                dispute.setSex(rs.getString("sex"));
                dispute.setStage(rs.getByte("stage"));
                dispute.setUpi(rs.getString("upi"));
                dispute.setDisputeStatus(rs.getByte("disputestatus"));
                dispute.setDisputeType(rs.getByte("disputetype"));
                returnValue.add(dispute);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean deleteDispute(HttpServletRequest request, Timestamp registeredOn, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE dispute SET status='deleted' WHERE upi=? and stage=? and registeredOn=?");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.setTimestamp(3, registeredOn);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "Dispute");
            stmnt.setString(4, "DELETE");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            returnValue = false;
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean deleteIndividualHolder(HttpServletRequest request, String holderId, Timestamp registeredOn, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE IndividualHolder SET status='deleted' WHERE upi=? and stage=? and registeredOn=? and holderId=?");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.setTimestamp(3, registeredOn);
            stmnt.setString(4, holderId);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "IndividualHolder");
            stmnt.setString(4, "DELETE");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            returnValue = false;
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean update(Dispute oldDispute, Dispute newDispute) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE dispute SET firstname = ?,  fathersname = ?,"
                + "grandfathersname = ?, sex=?, disputetype=?, disputestatus=?,"
                + "status=? WHERE upi=? and stage = ? and registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newDispute.getFirstName());
            stmnt.setString(2, newDispute.getFathersName());
            stmnt.setString(3, newDispute.getGrandFathersName());
            stmnt.setString(4, newDispute.getSex());
            stmnt.setByte(5, newDispute.getDisputeType());
            stmnt.setByte(6, newDispute.getDisputeStatus());
            stmnt.setString(7, "active");
            stmnt.setString(8, oldDispute.getUpi());
            stmnt.setByte(9, oldDispute.getStage());
            stmnt.setTimestamp(10, oldDispute.getRegisteredOn());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            ArrayList<Change> changes = oldDispute.getDifferenceForChangeLog(newDispute);
            for (Change change : changes) {
                query = "INSERT INTO changelog(upi, activitydate, userid, "
                        + "tablename, fieldname, fieldoldvalue, fieldnewvalue, "
                        + "actiontype) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                stmnt = connection.prepareStatement(query);
                stmnt.setString(1, oldDispute.getUpi());
                stmnt.setTimestamp(2, Timestamp.from(Instant.now()));
                stmnt.setLong(3, newDispute.getRegisteredBy());
                stmnt.setString(4, "Dispute");
                stmnt.setString(5, change.getField());
                stmnt.setString(6, change.getOldValue());
                stmnt.setString(7, change.getNewValue());
                stmnt.setString(8, "UPDATE");
                result = stmnt.executeUpdate();
                if (result < 1) {
                    returnValue = false;
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean update(Parcel oldParcel, Parcel newParcel) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE parcel SET certificateno=?, holdingno=?,"
                + "otherevidence=?, landusetype=?, soilfertilitytype=?, "
                + "holdingtype=?, acquisitiontype=?, acquisitionyear=?, "
                + "surveydate=?, mapsheetno=?, encumbrancetype=?, hasdispute=?, teamNo=?"
                + "WHERE upi=? and stage = ? and registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newParcel.getCertificateNumber());
            stmnt.setString(2, newParcel.getHoldingNumber());
            stmnt.setByte(3, newParcel.getOtherEvidence());
            stmnt.setByte(4, newParcel.getCurrentLandUse());
            stmnt.setByte(5, newParcel.getSoilFertility());
            stmnt.setByte(6, newParcel.getHolding());
            stmnt.setByte(7, newParcel.getMeansOfAcquisition());
            stmnt.setInt(8, newParcel.getAcquisitionYear());
            stmnt.setString(9, newParcel.getSurveyDate());
            stmnt.setString(10, newParcel.getMapSheetNo());
            stmnt.setByte(11, newParcel.getEncumbrance());
            stmnt.setBoolean(12, newParcel.hasDispute());
            stmnt.setByte(13, newParcel.getTeamNo());
            stmnt.setString(14, oldParcel.getUpi());
            stmnt.setByte(15, oldParcel.getStage());
            stmnt.setTimestamp(16, oldParcel.getRegisteredOn());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            ArrayList<Change> changes = oldParcel.getDifferenceForChangeLog(newParcel);
            for (Change change : changes) {
                query = "INSERT INTO changelog(upi, activitydate, userid, "
                        + "tablename, fieldname, fieldoldvalue, fieldnewvalue, "
                        + "actiontype) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                stmnt = connection.prepareStatement(query);
                stmnt.setString(1, oldParcel.getUpi());
                stmnt.setTimestamp(2, Timestamp.from(Instant.now()));
                stmnt.setLong(3, newParcel.getRegisteredBy());
                stmnt.setString(4, "Parcel");
                stmnt.setString(5, change.getField());
                stmnt.setString(6, change.getOldValue());
                stmnt.setString(7, change.getNewValue());
                stmnt.setString(8, "UPDATE");
                result = stmnt.executeUpdate();
                if (result < 1) {
                    returnValue = false;
                }
            }

            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean update(IndividualHolder oldIndividualHolder,
            IndividualHolder newIndividualHolder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE individualholder SET firstname=?, "
                + "fathersname=?, grandfathersname=?, sex=?, dateofbirth=?, "
                + "familyrole=?, holderId=?, physicalimpairment=?, isOrphan=? WHERE upi=? and stage = ? and registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newIndividualHolder.getFirstName());
            stmnt.setString(2, newIndividualHolder.getFathersName());
            stmnt.setString(3, newIndividualHolder.getGrandFathersName());
            stmnt.setString(4, newIndividualHolder.getSex());
            stmnt.setString(5, newIndividualHolder.getDateOfBirth());
            stmnt.setByte(6, newIndividualHolder.getFamilyRole());
            stmnt.setString(7, newIndividualHolder.getId());
            stmnt.setBoolean(8, newIndividualHolder.hasPhysicalImpairment());
            stmnt.setBoolean(9, newIndividualHolder.isOrphan());
            stmnt.setString(10, oldIndividualHolder.getUpi());
            stmnt.setByte(11, oldIndividualHolder.getStage());
            stmnt.setTimestamp(12, oldIndividualHolder.getRegisteredOn());

            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            ArrayList<Change> changes = oldIndividualHolder.getDifferenceForChangeLog(newIndividualHolder);
            for (Change change : changes) {
                query = "INSERT INTO changelog(upi, activitydate, userid, "
                        + "tablename, fieldname, fieldoldvalue, fieldnewvalue, "
                        + "actiontype) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                stmnt = connection.prepareStatement(query);
                stmnt.setString(1, oldIndividualHolder.getUpi());
                stmnt.setTimestamp(2, Timestamp.from(Instant.now()));
                stmnt.setLong(3, newIndividualHolder.getRegisteredBy());
                stmnt.setString(4, "IndividualHolder");
                stmnt.setString(5, change.getField());
                stmnt.setString(6, change.getOldValue());
                stmnt.setString(7, change.getNewValue());
                stmnt.setString(8, "UPDATE");
                result = stmnt.executeUpdate();
                if (result < 1) {
                    returnValue = false;
                }
            }
            connection.close();
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean updatePhotoID(IndividualHolder oldIndividualHolder,
            String newId) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE individualholder SET holderId=? WHERE holderId=? AND upi=? LIMIT 1";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newId);
            stmnt.setString(2, oldIndividualHolder.getId());
            stmnt.setString(3, oldIndividualHolder.getUpi());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean update(PersonWithInterest oldPersonWithInterest,
            PersonWithInterest newPersonWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE personwithinterest SET firstname=?, "
                + "fathersname=?, grandfathersname=?, sex=?, dateofbirth=? "
                + "WHERE upi=? and stage = ? and registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newPersonWithInterest.getFirstName());
            stmnt.setString(2, newPersonWithInterest.getFathersName());
            stmnt.setString(3, newPersonWithInterest.getGrandFathersName());
            stmnt.setString(4, newPersonWithInterest.getSex());
            stmnt.setString(5, newPersonWithInterest.getDateOfBirth());
            stmnt.setString(6, oldPersonWithInterest.getUpi());
            stmnt.setByte(7, oldPersonWithInterest.getStage());
            stmnt.setTimestamp(8, oldPersonWithInterest.getRegisteredOn());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            ArrayList<Change> changes = oldPersonWithInterest.getDifferenceForChangeLog(newPersonWithInterest);
            for (Change change : changes) {
                query = "INSERT INTO changelog(upi, activitydate, userid, "
                        + "tablename, fieldname, fieldoldvalue, fieldnewvalue, "
                        + "actiontype) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                stmnt = connection.prepareStatement(query);
                stmnt.setString(1, oldPersonWithInterest.getUpi());
                stmnt.setTimestamp(2, Timestamp.from(Instant.now()));
                stmnt.setLong(3, newPersonWithInterest.getRegisteredBy());
                stmnt.setString(4, "PersonWithInterest");
                stmnt.setString(5, change.getField());
                stmnt.setString(6, change.getOldValue());
                stmnt.setString(7, change.getNewValue());
                stmnt.setString(8, "UPDATE");
                result = stmnt.executeUpdate();
                if (result < 1) {
                    returnValue = false;
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean update(Guardian oldGuardian, Guardian newGuardian) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE Guardian SET firstname=?, "
                + "fathersname=?, grandfathersname=?, sex=?, dateofbirth=? "
                + "WHERE upi=? and stage = ? and registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newGuardian.getFirstName());
            stmnt.setString(2, newGuardian.getFathersName());
            stmnt.setString(3, newGuardian.getGrandFathersName());
            stmnt.setString(4, newGuardian.getSex());
            stmnt.setString(5, newGuardian.getDateOfBirth());
            stmnt.setString(6, oldGuardian.getUpi());
            stmnt.setByte(7, oldGuardian.getStage());
            stmnt.setTimestamp(8, oldGuardian.getRegisteredOn());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            ArrayList<Change> changes = oldGuardian.getDifferenceForChangeLog(newGuardian);
            for (Change change : changes) {
                query = "INSERT INTO changelog(upi, activitydate, userid, "
                        + "tablename, fieldname, fieldoldvalue, fieldnewvalue, "
                        + "actiontype) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                stmnt = connection.prepareStatement(query);
                stmnt.setString(1, oldGuardian.getUpi());
                stmnt.setTimestamp(2, Timestamp.from(Instant.now()));
                stmnt.setLong(3, newGuardian.getRegisteredBy());
                stmnt.setString(4, "Guardian");
                stmnt.setString(5, change.getField());
                stmnt.setString(6, change.getOldValue());
                stmnt.setString(7, change.getNewValue());
                stmnt.setString(8, "UPDATE");
                result = stmnt.executeUpdate();
                if (result < 1) {
                    returnValue = false;
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean update(OrganizationHolder oldOrganizationHolder,
            OrganizationHolder newOrganizationHolder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE organizationholder SET organizationname=?, "
                + "organizationtype=? WHERE upi=? and stage = ? and "
                + "registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newOrganizationHolder.getName());
            stmnt.setByte(2, newOrganizationHolder.getOrganizationType());
            stmnt.setString(3, oldOrganizationHolder.getUpi());
            stmnt.setByte(4, oldOrganizationHolder.getStage());
            stmnt.setTimestamp(5, oldOrganizationHolder.getRegisteredon());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            ArrayList<Change> changes = oldOrganizationHolder.getDifferenceForChangeLog(newOrganizationHolder);
            for (Change change : changes) {
                query = "INSERT INTO changelog(upi, activitydate, userid, "
                        + "tablename, fieldname, fieldoldvalue, fieldnewvalue, "
                        + "actiontype) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                stmnt = connection.prepareStatement(query);
                stmnt.setString(1, oldOrganizationHolder.getUpi());
                stmnt.setTimestamp(2, Timestamp.from(Instant.now()));
                stmnt.setLong(3, newOrganizationHolder.getRegisteredby());
                stmnt.setString(4, "OrganizationHolder");
                stmnt.setString(5, change.getField());
                stmnt.setString(6, change.getOldValue());
                stmnt.setString(7, change.getNewValue());
                stmnt.setString(8, "UPDATE");
                result = stmnt.executeUpdate();
                if (result < 1) {
                    returnValue = false;
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean update(User oldUser, User newUser) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE users SET username =?, firstName=?, "
                + "fathersName=?, grandfathersName=?, phoneno=?, role=? "
                + "WHERE userid=? ";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newUser.getUsername());
            stmnt.setString(2, newUser.getFirstName());
            stmnt.setString(3, newUser.getFathersName());
            stmnt.setString(4, newUser.getGrandFathersName());
            stmnt.setString(5, newUser.getPhoneNumber());
            stmnt.setString(6, newUser.getRoleText());
            stmnt.setLong(7, oldUser.getUserId());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean updatePassword(User oldUser, String newPassword) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE users SET password=MD5(?) WHERE userid=? ";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newPassword);
            stmnt.setLong(2, oldUser.getUserId());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean deleteOrganizationHolder(HttpServletRequest request, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE OrganizationHolder SET status='deleted' WHERE upi=? and stage=? ");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.executeUpdate();
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "OrganizationHolder");
            stmnt.setString(4, "DELETE");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }

            connection.close();
        } catch (Exception ex) {
            returnValue = false;
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean deleteParcel(HttpServletRequest request, Timestamp registeredOn, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE Parcel SET status='deleted' WHERE upi=? and stage=? and registeredOn=? ");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.setTimestamp(3, registeredOn);
            stmnt.executeUpdate();
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "Parcel");
            stmnt.setString(4, "DELETE");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            returnValue = false;
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public void completeParcel(String upi, byte stage, Timestamp registeredOn) {
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE parcel SET completed=? "
                + "WHERE upi=? and stage = ? and registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setBoolean(1, true);
            stmnt.setString(2, upi);
            stmnt.setByte(3, stage);
            stmnt.setTimestamp(4, registeredOn);
            int result = stmnt.executeUpdate();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
    }

    public ArrayList<PersonWithInterest> getAllPersonsWithInterest(String upi, byte stage) {
        ArrayList<PersonWithInterest> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM PersonWithInterest WHERE upi=? and stage=? and status='active'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                PersonWithInterest personWithInterest = new PersonWithInterest();
                personWithInterest.setDateOfBirth(rs.getString("dateofbirth"));
                personWithInterest.setFirstName(rs.getString("firstname"));
                personWithInterest.setFathersName(rs.getString("fathersname"));
                personWithInterest.setGrandFathersName(rs.getString("grandfathersname"));
                personWithInterest.setRegisteredBy(rs.getLong("registeredby"));
                personWithInterest.setRegisteredOn(rs.getTimestamp("registeredon"));
                personWithInterest.setSex(rs.getString("sex"));
                personWithInterest.setStage(rs.getByte("stage"));
                personWithInterest.setUpi(rs.getString("upi"));
                returnValue.add(personWithInterest);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<Guardian> getAllGuardians(String upi, byte stage) {
        ArrayList<Guardian> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Guardian WHERE upi=? and stage=? and status='active'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                Guardian guardian = new Guardian();
                guardian.setDateOfBirth(rs.getString("dateofbirth"));
                guardian.setFirstName(rs.getString("firstname"));
                guardian.setFathersName(rs.getString("fathersname"));
                guardian.setGrandFathersName(rs.getString("grandfathersname"));
                guardian.setRegisteredBy(rs.getLong("registeredby"));
                guardian.setRegisteredOn(rs.getTimestamp("registeredon"));
                guardian.setSex(rs.getString("sex"));
                guardian.setStage(rs.getByte("stage"));
                guardian.setUpi(rs.getString("upi"));
                returnValue.add(guardian);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean deletePersonWithInterest(HttpServletRequest request, Timestamp registeredOn, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE personwithinterest SET status='deleted' WHERE upi=? and stage=? and registeredOn=? ");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.setTimestamp(3, registeredOn);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "PersonWithInterest");
            stmnt.setString(4, "DELETE");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }

            connection.close();
        } catch (Exception ex) {
            returnValue = false;
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean deleteGuardian(HttpServletRequest request, Timestamp registeredOn, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE Guardian SET status='deleted' WHERE upi=? and stage=? and registeredOn=? ");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.setTimestamp(3, registeredOn);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "Guardian");
            stmnt.setString(4, "DELETE");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }

            connection.close();
        } catch (Exception ex) {
            returnValue = false;
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean deleteUser(HttpServletRequest request, long userId) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE users SET username=? , status='deleted' WHERE userId=? ");
            stmnt.setString(1, Instant.now().getEpochSecond() + getUser(userId).getUsername());
            stmnt.setLong(2, userId);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "User");
            stmnt.setString(4, "DELETE");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            returnValue = false;
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /**
     * @return an array of all the possible option from the table specified
     * @param table name of the table to query from
     */
    private Option[] getAllOptions(String table) {
        return getAllOptions(table, "code", CommonStorage.getCurrentLanguage());
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
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
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
     * @return an array of option objects representing the possible family role
     * types
     */
    public Option[] getAllFamilyRoles() {
        return getAllOptions("familyroletypes");
    }

    /**
     * @return an array of option objects representing the possible organization
     * types
     */
    public Option[] getAllOrganizationTypes() {
        return getAllOptions("organizationtypes");
    }

    /**
     * @return an array of option objects representing the possible encumbrance
     * types
     */
    public Option[] getAllStages() {
        return getAllOptions("stages");
    }

    /**
     * @return an array of option objects representing the possible dispute
     * status types
     */
    public Option[] getAllDisputeStatusTypes() {
        return getAllOptions("disputestatustypes");
    }

    /**
     * @return an array of option objects representing the possible dispute
     * types
     */
    public Option[] getAllDisputeTypes() {
        return getAllOptions("disputetypes");
    }

    /**
     * @return an array of option objects representing all possible family role
     * types types
     */
    public Option[] getAllfamilyRoleTypes() {
        return getAllOptions("familyroletypes");
    }

    /**
     * @return a hash table of all the kebele values
     */
    public Option[] getAllKebeles() {
        ArrayList<Option> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM kebele WHERE woredacode=? ORDER BY code");
            stmnt.setLong(1, CommonStorage.getCurrentWoredaId());
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                returnValue.add(new Option(rs.getInt("code") + "", rs.getString(CommonStorage.getCurrentLanguage())));
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue.toArray(new Option[0]);
    }

    /**
     * @return a hash table of all the kebele values
     */
    public Option[] getAllKebelesTextValue() {
        ArrayList<Option> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM kebele WHERE woredacode=? ORDER BY code");
            stmnt.setLong(1, CommonStorage.getCurrentWoredaId());
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                returnValue.add(new Option(rs.getInt("code") + "", rs.getString("textvalue")));
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue.toArray(new Option[0]);
    }

    /**
     * @return a hash table of all the woredas
     */
    public Option[] getAllWoredas() {
        ArrayList<Option> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Woreda ORDER BY code");
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                returnValue.add(new Option(rs.getInt("code") + "", rs.getString(CommonStorage.getCurrentLanguage())));
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue.toArray(new Option[0]);
    }

    /**
     * @return a hash table of all the regions
     */
    public Option[] getAllRegions() {
        ArrayList<Option> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Region ORDER BY code");
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                returnValue.add(new Option(rs.getInt("code") + "", rs.getString(CommonStorage.getCurrentLanguage())));
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue.toArray(new Option[0]);
    }

    /**
     * @param region id of the region
     * @return a hash table of all the woredas in a region
     */
    public Option[] getAllWoredas(int region) {
        ArrayList<Option> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT Woreda.* FROM Woreda, Zone WHERE Zone.regioncode = ? and Woreda.zonecode = Zone.code ORDER BY Woreda.english");
            stmnt.setInt(1, region);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                returnValue.add(new Option(rs.getInt("code") + "", rs.getString(CommonStorage.getCurrentLanguage())));
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue.toArray(new Option[0]);
    }

    public boolean commit(Parcel parcel) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Parcel (upi,stage,registeredBy,registeredOn,"
                + "parcelId,certificateNo,holdingNo, otherEvidence,landUseType,"
                + "soilFertilityType,holdingType,acquisitionType,acquisitionYear,"
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute, teamNo) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, parcel.getUpi());
            stmnt.setByte(2, CommonStorage.getCommitedStage());
            stmnt.setLong(3, parcel.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setInt(5, parcel.getParcelId());
            stmnt.setString(6, parcel.getCertificateNumber());
            stmnt.setString(7, parcel.getHoldingNumber());
            stmnt.setByte(8, parcel.getOtherEvidence());
            stmnt.setByte(9, parcel.getCurrentLandUse());
            stmnt.setByte(10, parcel.getSoilFertility());
            stmnt.setByte(11, parcel.getHolding());
            stmnt.setByte(12, parcel.getMeansOfAcquisition());
            stmnt.setInt(13, parcel.getAcquisitionYear());
            stmnt.setString(14, parcel.getSurveyDate());
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            stmnt.setBoolean(18, parcel.hasDispute());
            stmnt.setByte(19, parcel.getTeamNo());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean commit(OrganizationHolder holder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO organizationholder(upi, stage, organizationname, "
                + "registeredby, registeredon, organizationtype) VALUES "
                + "( ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, holder.getUpi());
            stmnt.setByte(2, CommonStorage.getCommitedStage());
            stmnt.setString(3, holder.getName());
            stmnt.setLong(4, holder.getRegisteredby());
            stmnt.setTimestamp(5, Timestamp.from(Instant.now()));
            stmnt.setByte(6, holder.getOrganizationType());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean commit(IndividualHolder individualHolder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO individualholder( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth, familyrole, holderId, physicalImpairment, isOrphan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, individualHolder.getUpi());
            stmnt.setByte(2, CommonStorage.getCommitedStage());
            stmnt.setLong(3, individualHolder.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, individualHolder.getFirstName());
            stmnt.setString(6, individualHolder.getFathersName());
            stmnt.setString(7, individualHolder.getGrandFathersName());
            stmnt.setString(8, individualHolder.getSex());
            stmnt.setString(9, individualHolder.getDateOfBirth());
            stmnt.setByte(10, individualHolder.getFamilyRole());
            stmnt.setString(11, individualHolder.getId());
            stmnt.setBoolean(12, individualHolder.hasPhysicalImpairment());
            stmnt.setBoolean(13, individualHolder.isOrphan());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean commit(PersonWithInterest personWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO PersonWithInterest( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, personWithInterest.getUpi());
            stmnt.setByte(2, CommonStorage.getCommitedStage());
            stmnt.setLong(3, personWithInterest.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, personWithInterest.getFirstName());
            stmnt.setString(6, personWithInterest.getFathersName());
            stmnt.setString(7, personWithInterest.getGrandFathersName());
            stmnt.setString(8, personWithInterest.getSex());
            stmnt.setString(9, personWithInterest.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean commit(Guardian guardian) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Guardian( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, guardian.getUpi());
            stmnt.setByte(2, CommonStorage.getCommitedStage());
            stmnt.setLong(3, guardian.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, guardian.getFirstName());
            stmnt.setString(6, guardian.getFathersName());
            stmnt.setString(7, guardian.getGrandFathersName());
            stmnt.setString(8, guardian.getSex());
            stmnt.setString(9, guardian.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean commit(Dispute dispute) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO dispute(upi, stage, registeredby, registeredon,"
                + "firstname, fathersname, grandfathersname, sex, disputetype, "
                + "disputestatus,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, dispute.getUpi());
            stmnt.setByte(2, CommonStorage.getCommitedStage());
            stmnt.setLong(3, dispute.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, dispute.getFirstName());
            stmnt.setString(6, dispute.getFathersName());
            stmnt.setString(7, dispute.getGrandFathersName());
            stmnt.setString(8, dispute.getSex());
            stmnt.setByte(9, dispute.getDisputeType());
            stmnt.setByte(10, dispute.getDisputeStatus());
            stmnt.setString(11, "active");
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForCorrection(Parcel parcel) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Parcel (upi,stage,registeredBy,registeredOn,"
                + "parcelId,certificateNo,holdingNo, otherEvidence,landUseType,"
                + "soilFertilityType,holdingType,acquisitionType,acquisitionYear,"
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute,teamNo) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, parcel.getUpi());
            stmnt.setByte(2, CommonStorage.getCorrectionStage());
            stmnt.setLong(3, parcel.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setInt(5, parcel.getParcelId());
            stmnt.setString(6, parcel.getCertificateNumber());
            stmnt.setString(7, parcel.getHoldingNumber());
            stmnt.setByte(8, parcel.getOtherEvidence());
            stmnt.setByte(9, parcel.getCurrentLandUse());
            stmnt.setByte(10, parcel.getSoilFertility());
            stmnt.setByte(11, parcel.getHolding());
            stmnt.setByte(12, parcel.getMeansOfAcquisition());
            stmnt.setInt(13, parcel.getAcquisitionYear());
            stmnt.setString(14, parcel.getSurveyDate());
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            stmnt.setBoolean(18, parcel.hasDispute());
            stmnt.setByte(19, parcel.getTeamNo());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForCorrection(OrganizationHolder holder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO organizationholder(upi, stage, organizationname, "
                + "registeredby, registeredon, organizationtype) VALUES "
                + "( ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, holder.getUpi());
            stmnt.setByte(2, CommonStorage.getCorrectionStage());
            stmnt.setString(3, holder.getName());
            stmnt.setLong(4, holder.getRegisteredby());
            stmnt.setTimestamp(5, Timestamp.from(Instant.now()));
            stmnt.setByte(6, holder.getOrganizationType());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForCorrection(IndividualHolder individualHolder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO individualholder( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth, familyrole, holderId, physicalImpairment, isOrphan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, individualHolder.getUpi());
            stmnt.setByte(2, CommonStorage.getCorrectionStage());
            stmnt.setLong(3, individualHolder.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, individualHolder.getFirstName());
            stmnt.setString(6, individualHolder.getFathersName());
            stmnt.setString(7, individualHolder.getGrandFathersName());
            stmnt.setString(8, individualHolder.getSex());
            stmnt.setString(9, individualHolder.getDateOfBirth());
            stmnt.setByte(10, individualHolder.getFamilyRole());
            stmnt.setString(11, individualHolder.getId());
            stmnt.setBoolean(12, individualHolder.hasPhysicalImpairment());
            stmnt.setBoolean(13, individualHolder.isOrphan());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForCorrection(PersonWithInterest personWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO PersonWithInterest( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, personWithInterest.getUpi());
            stmnt.setByte(2, CommonStorage.getCorrectionStage());
            stmnt.setLong(3, personWithInterest.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, personWithInterest.getFirstName());
            stmnt.setString(6, personWithInterest.getFathersName());
            stmnt.setString(7, personWithInterest.getGrandFathersName());
            stmnt.setString(8, personWithInterest.getSex());
            stmnt.setString(9, personWithInterest.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForCorrection(Guardian guardian) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Guardian( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, guardian.getUpi());
            stmnt.setByte(2, CommonStorage.getCorrectionStage());
            stmnt.setLong(3, guardian.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, guardian.getFirstName());
            stmnt.setString(6, guardian.getFathersName());
            stmnt.setString(7, guardian.getGrandFathersName());
            stmnt.setString(8, guardian.getSex());
            stmnt.setString(9, guardian.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForCorrection(Dispute dispute) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO dispute(upi, stage, registeredby, registeredon,"
                + "firstname, fathersname, grandfathersname, sex, disputetype, "
                + "disputestatus,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, dispute.getUpi());
            stmnt.setByte(2, CommonStorage.getCorrectionStage());
            stmnt.setLong(3, dispute.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, dispute.getFirstName());
            stmnt.setString(6, dispute.getFathersName());
            stmnt.setString(7, dispute.getGrandFathersName());
            stmnt.setString(8, dispute.getSex());
            stmnt.setByte(9, dispute.getDisputeType());
            stmnt.setByte(10, dispute.getDisputeStatus());
            stmnt.setString(11, "active");
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForPPDCorrection(Parcel parcel) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Parcel (upi,stage,registeredBy,registeredOn,"
                + "parcelId,certificateNo,holdingNo, otherEvidence,landUseType,"
                + "soilFertilityType,holdingType,acquisitionType,acquisitionYear,"
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute,teamNo) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, parcel.getUpi());
            stmnt.setByte(2, CommonStorage.getMajorCorrectionSupervisorStage());
            stmnt.setLong(3, parcel.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setInt(5, parcel.getParcelId());
            stmnt.setString(6, parcel.getCertificateNumber());
            stmnt.setString(7, parcel.getHoldingNumber());
            stmnt.setByte(8, parcel.getOtherEvidence());
            stmnt.setByte(9, parcel.getCurrentLandUse());
            stmnt.setByte(10, parcel.getSoilFertility());
            stmnt.setByte(11, parcel.getHolding());
            stmnt.setByte(12, parcel.getMeansOfAcquisition());
            stmnt.setInt(13, parcel.getAcquisitionYear());
            stmnt.setString(14, parcel.getSurveyDate());
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            stmnt.setBoolean(18, parcel.hasDispute());
            stmnt.setByte(19, parcel.getTeamNo());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForPPDCorrection(OrganizationHolder holder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO organizationholder(upi, stage, organizationname, "
                + "registeredby, registeredon, organizationtype) VALUES "
                + "( ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, holder.getUpi());
            stmnt.setByte(2, CommonStorage.getMajorCorrectionSupervisorStage());
            stmnt.setString(3, holder.getName());
            stmnt.setLong(4, holder.getRegisteredby());
            stmnt.setTimestamp(5, Timestamp.from(Instant.now()));
            stmnt.setByte(6, holder.getOrganizationType());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForPPDCorrection(IndividualHolder individualHolder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO individualholder( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth, familyrole, holderId, physicalImpairment, isOrphan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, individualHolder.getUpi());
            stmnt.setByte(2, CommonStorage.getMajorCorrectionSupervisorStage());
            stmnt.setLong(3, individualHolder.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, individualHolder.getFirstName());
            stmnt.setString(6, individualHolder.getFathersName());
            stmnt.setString(7, individualHolder.getGrandFathersName());
            stmnt.setString(8, individualHolder.getSex());
            stmnt.setString(9, individualHolder.getDateOfBirth());
            stmnt.setByte(10, individualHolder.getFamilyRole());
            stmnt.setString(11, individualHolder.getId());
            stmnt.setBoolean(12, individualHolder.hasPhysicalImpairment());
            stmnt.setBoolean(13, individualHolder.isOrphan());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForPPDCorrection(PersonWithInterest personWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO PersonWithInterest( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, personWithInterest.getUpi());
            stmnt.setByte(2, CommonStorage.getMajorCorrectionSupervisorStage());
            stmnt.setLong(3, personWithInterest.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, personWithInterest.getFirstName());
            stmnt.setString(6, personWithInterest.getFathersName());
            stmnt.setString(7, personWithInterest.getGrandFathersName());
            stmnt.setString(8, personWithInterest.getSex());
            stmnt.setString(9, personWithInterest.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForPPDCorrection(Guardian guardian) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Guardian( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, guardian.getUpi());
            stmnt.setByte(2, CommonStorage.getMajorCorrectionSupervisorStage());
            stmnt.setLong(3, guardian.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, guardian.getFirstName());
            stmnt.setString(6, guardian.getFathersName());
            stmnt.setString(7, guardian.getGrandFathersName());
            stmnt.setString(8, guardian.getSex());
            stmnt.setString(9, guardian.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForPPDCorrection(Dispute dispute) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO dispute(upi, stage, registeredby, registeredon,"
                + "firstname, fathersname, grandfathersname, sex, disputetype, "
                + "disputestatus,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, dispute.getUpi());
            stmnt.setByte(2, CommonStorage.getMajorCorrectionSupervisorStage());
            stmnt.setLong(3, dispute.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, dispute.getFirstName());
            stmnt.setString(6, dispute.getFathersName());
            stmnt.setString(7, dispute.getGrandFathersName());
            stmnt.setString(8, dispute.getSex());
            stmnt.setByte(9, dispute.getDisputeType());
            stmnt.setByte(10, dispute.getDisputeStatus());
            stmnt.setString(11, "active");
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public ArrayList<Parcel> getALLParcelsInCorrection() {
        ArrayList<Parcel> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT *, (SELECT max(stage) FROM Parcel P2 WHERE"
                    + " P2.UPI=P.UPI AND P2.status='active') as maxstage  FROM "
                    + " Parcel P WHERE P.stage = 3 AND P.status='active' AND "
                    + " P.UPI not in (SELECT upi FROM Parcel WHERE stage = 4 and status='active') LIMIT 100";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                Parcel parcel = new Parcel();
                parcel.setAcquisition(rs.getByte("acquisitiontype"));
                parcel.setAcquisitionYear(rs.getInt("acquisitionyear"));
                parcel.setCertificateNumber(rs.getString("certificateno"));
                parcel.setCurrentLandUse(rs.getByte("landusetype"));
                parcel.setEncumbrance(rs.getByte("encumbrancetype"));
                parcel.setHolding(rs.getByte("holdingtype"));
                parcel.setHoldingNumber(rs.getString("holdingno"));
                parcel.setMapSheetNo(rs.getString("mapsheetno"));
                parcel.setOtherEvidence(rs.getByte("otherevidence"));
                parcel.setParcelId(rs.getInt("parcelid"));
                parcel.setRegisteredBy(rs.getLong("registeredby"));
                parcel.setSoilFertility(rs.getByte("soilfertilitytype"));
                parcel.setStage(rs.getByte("stage"));
                parcel.setStatus(rs.getString("status"));
                parcel.setSurveyDate(rs.getString("surveydate"));
                parcel.setUpi(rs.getString("upi"));
                parcel.setRegisteredOn(rs.getTimestamp("registeredon"));
                parcel.hasDispute(rs.getBoolean("hasdispute"));
                parcel.setTeamNo(rs.getByte("teamNo"));
                parcel.setMaxStage(rs.getByte("maxStage"));

                if (parcel.hasDispute()) {
                    parcel.setDisputes(getAllDisputes(parcel.getUpi(), parcel.getStage()));
                }
                if (parcel.getHolding() == 1) {
                    parcel.setIndividualHolders(getAllIndividualHolders(parcel.getUpi(), parcel.getStage()));
                    parcel.setPersonsWithInterest(getAllPersonsWithInterest(parcel.getUpi(), parcel.getStage()));
                } else {
                    parcel.setOrganaizationHolder(getOrganaizationHolder(parcel.getUpi(), parcel.getStage()));
                }
                returnValue.add(parcel);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Users WHERE status='active'  and username in (SELECT username FROM Groups)");
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setFathersName(rs.getString("fathersName"));
                user.setFirstName(rs.getString("firstName"));
                user.setGrandFathersName(rs.getString("grandFathersName"));
                user.setPhoneNumber(rs.getString("phoneno"));
                user.setUserId(rs.getLong("userId"));
                user.setUsername(rs.getString("username"));
                String role = rs.getString("role");
                switch (role) {
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
                returnValue.add(user);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean changePassword(long userId, String password) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE users SET password=(SELECT MD5(?)) WHERE userId=?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, password);
            stmnt.setLong(2, userId);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean checkPassword(CurrentUserDTO user, String password) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "SELECT * FROM users WHERE password = (SELECT MD5(?)) and userId=?  and username in (SELECT username FROM Groups)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, password);
            stmnt.setLong(2, user.getUserId());
            ResultSet result = stmnt.executeQuery();
            if (!result.next()) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean userExists(String username) {
        boolean returnValue = false;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Users WHERE username=? and status='active' and username in (SELECT username FROM Groups)");
            stmnt.setString(1, username);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = true;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public String getWoredaName(long woredaId) {
        String returnValue = "";
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT " + CommonStorage.getCurrentLanguage() + " as woredaName FROM WOREDA WHERE woreda.code = ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setLong(1, woredaId);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getString("woredaName");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public String getKebeleName(long kebeleId, String language) {
        String returnValue = "";
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT " + language + " as kebeleName FROM kebele WHERE kebele.code = ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setLong(1, kebeleId);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getString("kebeleName");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public String getWoredaName(long woredaId, String language) {
        String returnValue = "";
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT woreda." + language + " as woredaName FROM woreda WHERE woreda.code = ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setLong(1, woredaId);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getString("woredaName");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public String getZoneName(long woredaId, String language) {
        String returnValue = "";
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT zone." + language + " as zoneName FROM zone,woreda WHERE woreda.zonecode=Zone.code and woreda.code = ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setLong(1, woredaId);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getString("zoneName");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public String getRegionName(long woredaId, String language) {
        String returnValue = "";
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT region." + language + " as regionName FROM region,zone,woreda WHERE zone.regioncode=Region.code and woreda.zonecode=Zone.code and woreda.code = ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setLong(1, woredaId);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getString("regionName");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public String getWoredaIdForUPI(long woredaId) {
        String returnValue = "";
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT woreda.code as woredacode, woreda.textValue FROM WOREDA WHERE woreda.code = ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setLong(1, woredaId);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getString("woredacode");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    private String getKebeleTable(long kebele) {
        return getKebeleName(kebele, "english").toLowerCase().replace(" ", "_")
                + "_" + String.format("%09d", kebele);
    }

    private String getDBLinkString() {
        return "'dbname=" + CommonStorage.getGISDBName() + " user="
                + CommonStorage.getGISDBUserName() + " password="
                + CommonStorage.getGISDBPassword() + "'";
    }

    public void updateParcelArea(long kebele) {
        try {
            String query = "UPDATE Parcel SET area = P2.area FROM dblink("
                    + getDBLinkString() + " ,'SELECT parcel_id, "
                    + "round ((ST_Area(the_geom)/10000)::numeric,5) as area FROM " + getKebeleTable(kebele)
                    + "') as P2(parcel_id integer, area double precision) WHERE Parcel.parcelid = P2.parcel_id";
            Connection connection = CommonStorage.getConnection();
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.executeUpdate();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
    }

    /////////////////////
    // New Reports
    // Timebound Report
    /**
     * @return Total number of parcels with married couple holders for a kebele
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderMarriedCoupleHolders(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and registeredon::DATE between ? and ? and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM MarriedCouple)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under single female holders for a kebele
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderSingleFemaleHolders(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and registeredon::DATE between ? and ? and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM singlefemaleholder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under single male holders for a kebele
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderSingleMaleHolders(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and registeredon::DATE between ? and ? and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM singlemaleholder)";

            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under orphans holders for a kebele
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderOrphanHolders(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and registeredon::DATE between ? and ? and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under holding of non-natural persons for
     * a kebele
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderNonNaturalHolders(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and holdingtype!=1 and registeredon::DATE between ? and ? and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under holding of not married persons for
     * a kebele
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderOtherHoldingTypes(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and registeredon::DATE between ? and ? and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under dispute for a kebele
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderDispute(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' and stage=4 and status='active' and registeredon::DATE between ? and ? and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /**
     * @return Total number of parcels entered into iMASSREG for a kebele
     * @param kebele : kebele to report on
     */
    public long getCountOfAllCommittedParcels(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE stage=4 and status='active' and registeredon::DATE between ? and ? and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /**
     * @return Total number of parcels entered into iMASSREG for a kebele but
     * not committed
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfAllNonCommittedParcels(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM parcel WHERE status = 'active' and  registeredon::DATE between ? and ? and UPI LIKE '" + kebele + "%' and UPI not in ( SELECT upi FROM committedparcels)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /// Kebele Report
    public long getCountOfMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM MarriedCouple WHERE UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM SingleFemaleHolder WHERE UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM SingleMaleHolder WHERE UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder, Parcel WHERE IndividualHolder.UPI LIKE '" + kebele + "/%' AND Parcel.status='active' AND Parcel.hasDispute=false AND IndividualHolder.stage=Parcel.stage AND IndividualHolder.UPI=Parcel.UPI AND IndividualHolder.stage=4 AND IndividualHolder.status='active' AND IndividualHolder.isOrphan=true";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfNonNaturalHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct organizationname) as c FROM OrganizationHolder,Parcel WHERE  OrganizationHolder.upi=parcel.upi AND OrganizationHolder.UPI LIKE '" + kebele + "/%'  AND Parcel.stage=4 AND Parcel.status='active' AND Parcel.hasDispute=false AND OrganizationHolder.stage=4 AND OrganizationHolder.status='active' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND Parcel.status='active' AND Parcel.hasDispute=true AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /**
     * Gets the total number of demarcated parcels from the GIS database
     *
     * @return the total number of demarcated parcels
     * @param kebele : kebele to report on
     */
    public long getCountOfDemarcatedParcels(long kebele) {
        long returnValue = 0;
        try {
            String query = "SELECT P2.c as c FROM dblink(" + getDBLinkString()
                    + ",'SELECT count (parcel_id) as c FROM " + getKebeleTable(kebele)
                    + "') as P2(c bigint)";
            Connection connection = CommonStorage.getConnection();
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfAllCommittedParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsNoData(long kebele) {
        long returnValue = 0;
        returnValue = getCountOfDemarcatedParcels(kebele) - getCountOfAllCommittedParcels(kebele);
        return returnValue;
    }

    public double getAverageCountOfParcelsUnderMarriedCoupleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageNumberOfParcelsPerMarriedCoupleHolder(long kebele) {
        return (double) getCountOfParcelsUnderMarriedCoupleHolders(kebele) / (double) getCountOfMarriedCoupleHolders(kebele);
    }

    public double getAverageNumberOfParcelsPerSingleFemaleHolder(long kebele) {
        return (double) getCountOfParcelsUnderSingleFemaleHolders(kebele) / (double) getCountOfSingleFemaleHolders(kebele);
    }

    public double getAverageNumberOfParcelsPerSingleMaleHolder(long kebele) {
        return (double) getCountOfParcelsUnderSingleMaleHolders(kebele) / (double) getCountOfSingleMaleHolders(kebele);
    }

    public double getAverageNumberOfParcelsPerOrphanHolder(long kebele) {
        return (double) getCountOfParcelsUnderOrphanHolders(kebele) / (double) getCountOfOrphanHolders(kebele);
    }

    public double getAverageNumberOfParcelsPerNonNaturalHolder(long kebele) {
        return (double) getCountOfParcelsUnderNonNaturalHolders(kebele) / (double) getCountOfNonNaturalHolders(kebele);
    }

    public double getAverageNumberOfParcelsPerOtherHoldingType(long kebele) {
        return (double) getCountOfParcelsUnderOtherHoldingTypes(kebele) / (double) getCountOfHoldersOfOtherHoldingTypes(kebele);
    }

    /**
     * @return Total number of parcels entered into iMASSREG for a kebele but
     * not committed
     * @param kebele : kebele to report on
     */
    public long getCountOfAllNonCommittedParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM parcel WHERE status = 'active' and  UPI LIKE '" + kebele + "%' and UPI not in ( SELECT upi FROM committedparcels)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderNonNaturalHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and holdingtype!=1 and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM MarriedCouple)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under single female holders for a kebele
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM singlefemaleholder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under single male holders for a kebele
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM singlemaleholder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsUnderMarriedCoupleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) a FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%'  and  Parcel.hasDispute='false' and Parcel.status='active' and Parcel.stage = 4 and UPI in (SELECT UPI FROM MarriedCouple)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsUnderSingleFemaleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) a FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%'  and  Parcel.hasDispute='false' and Parcel.status='active' and Parcel.stage = 4 and UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsUnderSingleMaleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) a FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%'  and  Parcel.hasDispute='false' and Parcel.status='active' and Parcel.stage = 4 and UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsUnderOrphanHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and hasDispute='false' and stage=4 and status='active' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsUnderNonNaturalHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and hasDispute='false' and stage=4 and status='active' and holdingtype!=1";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsUnderOtherHoldingTypes(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and hasDispute='false' and stage=4 and status='active' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsUnderDispute(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) as a FROM Parcel WHERE hasDispute='true' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsWithNoData(long kebele) {
        double returnValue = 0;
        Connection connection;
        try {
            String query = "SELECT sum(area) as a FROM dblink(" + getDBLinkString()
                    + ",'SELECT parcel_id, ST_Area (the_geom) / 10000 as area FROM "
                    + getKebeleTable(kebele) + "') as P2(parcel_id integer, area double precision) WHERE parcel_id NOT IN (SELECT parcelId FROM Parcel WHERE status='active' and stage=4)";
            connection = CommonStorage.getConnection();
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getTotalAreaOfAllParcels(long kebele) {
        double returnValue = 0;
        Connection connection;
        try {
            String query = "SELECT sum(area) as a FROM dblink(" + getDBLinkString()
                    + ",'SELECT ST_Area (the_geom) / 10000  as area FROM "
                    + getKebeleTable(kebele) + "') as P2(area double precision)";
            connection = CommonStorage.getConnection();
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsUnderMarriedCoupleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and stage=4 and status='active' and hasDispute='false' and UPI in (SELECT UPI FROM MarriedCouple )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsUnderSingleFemaleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and stage=4 and status='active' and hasDispute='false' and UPI in (SELECT UPI FROM SingleFemaleHolder )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsUnderSingleMaleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and stage=4 and hasDispute='false' and status='active' and UPI in (SELECT UPI FROM SingleMaleHolder )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsUnderOrphanHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and hasDispute='false' and stage=4 and status='active' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsUnderNonNaturalHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and hasDispute='false' and stage=4 and status='active' and holdingtype!=1";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsUnderOtherHoldingTypes(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and hasDispute='false' and stage=4 and status='active' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsUnderDispute(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) as a FROM Parcel WHERE hasDispute='true' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsWithNoData(long kebele) {
        double returnValue = 0;
        Connection connection;
        try {
            String query = "SELECT avg(area) as a FROM dblink(" + getDBLinkString()
                    + ",'SELECT parcel_id, ST_Area (the_geom)  / 10000  as area FROM "
                    + getKebeleTable(kebele) + "') as P2(parcel_id integer, area double precision) WHERE parcel_id NOT IN (SELECT parcelId FROM Parcel WHERE status='active' and stage=4)";
            connection = CommonStorage.getConnection();
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageAreaOfAllParcels(long kebele) {
        double returnValue = 0;
        Connection connection;
        try {
            String query = "SELECT avg(area) as a FROM dblink(" + getDBLinkString()
                    + ",'SELECT ST_Area (the_geom) / 10000  as area FROM "
                    + getKebeleTable(kebele) + "') as P2(area double precision)";
            connection = CommonStorage.getConnection();
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    // By Other ownership evidence
    public long getCountOfParcelsUnderMarriedCoupleHoldersByOwnershipEvidence(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND otherevidence=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM MarriedCouple )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleFemaleHoldersByOwnershipEvidence(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND otherevidence=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleMaleHoldersByOwnershipEvidence(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND otherevidence=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOrphanHoldersByOwnershipEvidence(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND otherevidence=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderNonNaturalHoldersByOwnershipEvidence(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND otherevidence=? AND stage=4 and status='active' and holdingtype!=1 and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOtherHoldingTypesByOwnershipEvidence(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' AND otherevidence=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDisputeByOwnershipEvidence(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' AND otherevidence=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfAllParcelsByOwnershipEvidence(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND otherevidence=? AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    // By Land Use type
    public long getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND landusetype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM MarriedCouple )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND landusetype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleMaleHoldersByLandUseType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND landusetype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOrphanHoldersByLandUseType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND landusetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderNonNaturalHoldersByLandUseType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND landusetype=? AND stage=4 and status='active' and holdingtype!=1 and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOtherHoldingTypesByLandUseType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' AND landusetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDisputeByLandUseType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' AND landusetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfAllParcelsByLandUseType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND landusetype=? AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    // By Soil Fertility Status
    public long getCountOfParcelsUnderMarriedCoupleHoldersBySoilFertility(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND soilfertilitytype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM MarriedCouple )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleFemaleHoldersBySoilFertility(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND soilfertilitytype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleMaleHoldersBySoilFertility(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND soilfertilitytype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOrphanHoldersBySoilFertility(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND soilfertilitytype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderNonNaturalHoldersBySoilFertility(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND soilfertilitytype=? AND stage=4 and status='active' and holdingtype!=1 and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOtherHoldingTypesBySoilFertility(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' AND soilfertilitytype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDisputeBySoilFertility(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' AND soilfertilitytype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfAllParcelsBySoilFertility(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND soilfertilitytype=? AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    // By Soil Fertility Status
    /// TODO: Remove
    public long getCountOfParcelsUnderMarriedCoupleHoldersByHoldingType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND holdingtype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM MarriedCouple )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /// TODO: Remove
    public long getCountOfParcelsUnderSingleFemaleHoldersByHoldingType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND holdingtype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /// TODO: Remove
    public long getCountOfParcelsUnderSingleMaleHoldersByHoldingType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND holdingtype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /// TODO: Remove
    public long getCountOfParcelsUnderOrphanHoldersByHoldingType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND holdingtype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderNonNaturalHoldersByHoldingType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND holdingtype=? AND stage=4 and status='active' and holdingtype!=1 and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOtherHoldingTypesByHoldingType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' AND holdingtype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDisputeByHoldingType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' AND holdingtype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfAllParcelsByHoldingType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND holdingtype=? AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    // By Organisation Type
    public long getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct Parcel.UPI) as c FROM Parcel, OrganizationHolder WHERE Parcel.UPI LIKE '" + kebele + "/%' AND Parcel.hasDispute='false' AND Parcel.status='active' AND Parcel.stage=4 AND OrganizationHolder.UPI = Parcel.UPI AND OrganizationHolder.stage=Parcel.stage AND OrganizationHolder.status='active' AND OrganizationHolder.organizationtype= ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDisputeByOrganisationType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct Parcel.UPI) as c FROM Parcel, OrganizationHolder WHERE Parcel.UPI LIKE '" + kebele + "/%' AND Parcel.hasDispute='true' AND Parcel.status='active' AND Parcel.stage=4 AND OrganizationHolder.UPI = Parcel.UPI AND OrganizationHolder.stage=Parcel.stage AND OrganizationHolder.status='active' AND OrganizationHolder.organizationtype= ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfAllParcelsByOrganisationType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct Parcel.UPI) as c FROM Parcel, OrganizationHolder WHERE Parcel.UPI LIKE '" + kebele + "/%' AND Parcel.status='active' AND Parcel.stage=4 AND OrganizationHolder.UPI = Parcel.UPI AND OrganizationHolder.stage=Parcel.stage AND OrganizationHolder.status='active' AND OrganizationHolder.organizationtype= ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    // By means of acquisition
    public long getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND acquisitiontype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM MarriedCouple )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND acquisitiontype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND acquisitiontype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND acquisitiontype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND acquisitiontype=? AND stage=4 and status='active' and holdingtype!=1 and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' AND acquisitiontype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDisputeByMeansOfAcquisition(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' AND acquisitiontype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfAllParcelsByMeansOfAcquisition(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND acquisitiontype=? AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    // By encumbrance
    public long getCountOfParcelsUnderMarriedCoupleHoldersByEncumbrances(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND encumbrancetype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM MarriedCouple )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleFemaleHoldersByEncumbrances(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND encumbrancetype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleMaleHoldersByEncumbrances(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND encumbrancetype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOrphanHoldersByEncumbrances(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND encumbrancetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderNonNaturalHoldersByEncumbrances(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND encumbrancetype=? AND stage=4 and status='active' and holdingtype!=1 and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOtherHoldingTypesByEncumbrances(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' AND encumbrancetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDisputeByEncumbrances(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' AND encumbrancetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfAllParcelsByEncumbrances(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND encumbrancetype=? AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    // Physical Impairment
    public long getCountOfMarriedCoupleHoldersWithPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND (familyrole=1 OR familyrole=2 ) AND physicalimpairment='true' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMarriedCoupleHoldersWithoutPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND (familyrole=1 OR familyrole=2 ) AND physicalimpairment='false' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfSingleFemaleHoldersWithPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM SingleFemaleHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND physicalimpairment='true' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfSingleFemaleHoldersWithoutPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM SingleFemaleHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND physicalimpairment='false' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfSingleMaleHoldersWithPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM SingleMaleHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND physicalimpairment='true' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfSingleMaleHoldersWithoutPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM SingleMaleHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND physicalimpairment='false' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfOrphanHoldersWithPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND isOrphan='true' AND physicalimpairment='true' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfOrphanHoldersWithoutPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND isOrphan='true' AND physicalimpairment='false' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfOtherHoldingTypesWithPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE physicalimpairment='true' AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfOtherHoldingTypesWithoutPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE physicalimpairment='false' AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfAllHoldersWithPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND physicalimpairment='true' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfAllHoldersWithoutPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND physicalimpairment='false' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithPersonsOfInterestUnderMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM PersonWithInterest WHERE stage=4 AND status='active' ) AND UPI in (SELECT UPI FROM MarriedCouple)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithPersonsOfInterestUnderSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM PersonWithInterest WHERE stage=4 AND status='active' ) AND UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithPersonsOfInterestUnderSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM PersonWithInterest  WHERE stage=4 AND status='active' ) AND UPI in (SELECT UPI FROM singlemaleholder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithPersonsOfInterestUnderOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM PersonWithInterest WHERE stage=4 AND status='active' ) AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithPersonsOfInterestUnderOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1) AND UPI IN (SELECT UPI FROM PersonWithInterest WHERE stage=4 AND status='active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithPersonsOfInterestUnderDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='true' AND UPI IN (SELECT UPI FROM PersonWithInterest WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfAllParcelsWithPersonsOfInterest(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM PersonWithInterest WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestUnderMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestUnderSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingleFemaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestUnderSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM singlemaleholder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestUnderOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestUnderOphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestUnderOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI NOT IN (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI IN (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestForParcelsWithDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' and hasDispute='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestForAllParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestUnderMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestUnderSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingleFemaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestUnderSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE  sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM singlemaleholder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestUnderOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE  sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestUnderOphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestUnderOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI NOT IN (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI IN (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestForParcelsWithDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' and hasDispute='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestForAllParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemalePersonsWithInterestUnderMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemalePersonsWithInterestUnderSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingleFemaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemalePersonsWithInterestUnderSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM singlemaleholder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemalePersonsWithInterestUnderOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemalePersonsWithInterestUnderOphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemalePersonsWithInterestUnderOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI NOT IN (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI IN (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemalePersonsWithInterestForParcelsWithDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' and hasDispute='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemalePersonsWithInterestForAllParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageNumberOfPIPerParcelsUnderMarriedCoupleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(PersonWithInterest.*) c FROM PersonWithInterest, Parcel WHERE Parcel.stage = 4 and PersonWithInterest.UPI = Parcel.UPI and PersonWithInterest.stage=Parcel.stage and  PersonWithInterest.stage=4 and PersonWithInterest.status='active' and PersonWithInterest.UPI LIKE '" + kebele + "/%' and Parcel.UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 and status='active' ) group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageNumberOfPIPerParcelsUnderSingleFemaleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(PersonWithInterest.*) c FROM PersonWithInterest, Parcel WHERE Parcel.stage = 4 and PersonWithInterest.UPI = Parcel.UPI and PersonWithInterest.stage=Parcel.stage and  PersonWithInterest.stage=4 and PersonWithInterest.status='active' and PersonWithInterest.UPI LIKE '" + kebele + "/%' and Parcel.UPI in (SELECT UPI FROM SingleFemaleHolder WHERE stage=4 and status='active' ) group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageNumberOfPIPerParcelsUnderSingleMaleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(PersonWithInterest.*) c FROM PersonWithInterest, Parcel WHERE Parcel.stage = 4 and PersonWithInterest.UPI = Parcel.UPI and PersonWithInterest.stage=Parcel.stage and  PersonWithInterest.stage=4 and PersonWithInterest.status='active' and PersonWithInterest.UPI LIKE '" + kebele + "/%' and Parcel.UPI in (SELECT UPI FROM SingleMaleHolder WHERE stage=4 and status='active' ) group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageNumberOfPIPerParcelsUnderOrphanHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(PersonWithInterest.*) c FROM PersonWithInterest, Parcel WHERE Parcel.stage = 4 and PersonWithInterest.UPI = Parcel.UPI and PersonWithInterest.stage=Parcel.stage and  PersonWithInterest.stage=4 and PersonWithInterest.status='active' and PersonWithInterest.UPI LIKE '" + kebele + "/%' and Parcel.UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status='active' and isOrphan='true') group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageNumberOfPIPerParcelsUnderOtherHoldingTypes(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(PersonWithInterest.*) c FROM PersonWithInterest, Parcel WHERE Parcel.stage = 4 and PersonWithInterest.UPI = Parcel.UPI and PersonWithInterest.stage=Parcel.stage and  PersonWithInterest.stage=4 and PersonWithInterest.status='active' and PersonWithInterest.UPI LIKE '" + kebele + "/%' and  Parcel.UPI NOT IN (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and Parcel.UPI IN (SELECT UPI FROM IndividualHolder WHERE sex='m' and stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1) group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithGuardianUnderMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM guardian WHERE stage=4 AND status='active' ) AND UPI in (SELECT UPI FROM MarriedCouple)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfGuardiansForParcelsUnderMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageNumberOfGuardiansPerParcelsUnderMarriedCoupleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(Guardian.*) c FROM Guardian, Parcel WHERE Parcel.stage = 4 and Guardian.UPI = Parcel.UPI and Guardian.stage=Parcel.stage and  Guardian.stage=4 and Guardian.status='active' and Guardian.UPI LIKE '" + kebele + "/%' and Parcel.UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 and status='active' ) group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMaleGuardiansForParcelsUnderMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemaleGuardiansForParcelsUnderMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public String getFirstDataEntryDateInKebeleForMarriedHolders(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT min(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and  UPI in (SELECT UPI FROM MarriedCouple)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public String getLastDataEntryDateInKebeleForMarriedHolders(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT max(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and  UPI in (SELECT UPI FROM MarriedCouple)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public double getAverageNumberOfPIPerParcelForAllParcels(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(PersonWithInterest.*) c FROM PersonWithInterest, Parcel WHERE Parcel.stage = 4 and PersonWithInterest.UPI = Parcel.UPI and PersonWithInterest.stage=Parcel.stage and  PersonWithInterest.stage=4 and PersonWithInterest.status='active' and PersonWithInterest.UPI LIKE '" + kebele + "/%' GROUP BY Parcel.UPI) as t ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    ////////////////////
    public int getCountOfParcelsWithPersonsOfInterestForMarriedHolders(long kebele) {
        int returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel  WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM PersonWithInterest WHERE stage=4 and status='active') and UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 and status='active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getInt("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public int getCountOfPersonWithInterestForParcelsUnderMarriedHolders(long kebele) {
        int returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 and status='active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getInt("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public int getCountOfMalePersonWithInterestForParcelsUnderMarriedHolders(long kebele) {
        int returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex = 'm' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 and status='active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getInt("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public int getCountOfFemalePersonWithInterestForParcelsUnderMarriedHolders(long kebele) {
        int returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex = 'f' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 and status='active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getInt("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithGuardiansUnderSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM Guardian WHERE stage=4 AND status='active' ) AND UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfGuardiansForParcelsUnderSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingleFemaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageNumberOfGuardiansPerParcelsUnderSingleFemaleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(Guardian.*) c FROM Guardian, Parcel WHERE Parcel.stage = 4 and Guardian.UPI = Parcel.UPI and Guardian.stage=Parcel.stage and  Guardian.stage=4 and Guardian.status='active' and Guardian.UPI LIKE '" + kebele + "/%' and Parcel.UPI in (SELECT UPI FROM SingleFemaleHolder WHERE stage=4 and status='active' ) group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMaleGuardiansForParcelsUnderSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingleFemaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemaleGuardiansForParcelsUnderSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingleFemaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public String getFirstDataEntryDateInKebeleForSingleFemaleHolders(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT min(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and  UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public String getLastDataEntryDateInKebeleForSingleFemaleHolders(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT max(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and  UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public long getCountOfParcelsWithGuardiansUnderSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM Guardian WHERE stage=4 AND status='active' ) AND UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfGuardiansForParcelsUnderSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingleMaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageNumberOfGuardiansPerParcelsUnderSingleMaleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(Guardian.*) c FROM Guardian, Parcel WHERE Parcel.stage = 4 and Guardian.UPI = Parcel.UPI and Guardian.stage=Parcel.stage and  Guardian.stage=4 and Guardian.status='active' and Guardian.UPI LIKE '" + kebele + "/%' and Parcel.UPI in (SELECT UPI FROM SingleMaleHolder WHERE stage=4 and status='active' ) group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMaleGuardiansForParcelsUnderSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingleMaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemaleGuardiansForParcelsUnderSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingleMaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public String getFirstDataEntryDateInKebeleForSingleMaleHolders(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT min(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and  UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public String getLastDataEntryDateInKebeleForSingleMaleHolders(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT max(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and  UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public long getCountOfParcelsWithGuardiansUnderOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM Guardian WHERE stage=4 AND status='active' ) AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfGuardiansForParcelsUnderOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageNumberOfGuardiansPerParcelsUnderOrphanHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(Guardian.*) c FROM Guardian, Parcel WHERE Parcel.stage = 4 and Guardian.UPI = Parcel.UPI and Guardian.stage=Parcel.stage and  Guardian.stage=4 and Guardian.status='active' and Guardian.UPI LIKE '" + kebele + "/%' and Parcel.UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status='active' and isOrphan='true') group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMaleGuardiansForParcelsUnderOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE  sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemaleGuardiansForParcelsUnderOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE  sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public String getFirstDataEntryDateInKebeleForOrphanHolders(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT min(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and  UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public String getLastDataEntryDateInKebeleForOrphanHolders(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT max(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and  UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public String getFirstDataEntryDateInKebeleForNonNaturalHolders(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT min(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and holdingtype!=1 ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public String getLastDataEntryDateInKebeleForNonNaturalHolders(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT max(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and holdingtype!=1 ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public long getCountOfParcelsWithGuardiansUnderOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1) AND UPI IN (SELECT UPI FROM Guardian WHERE stage=4 AND status='active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfGuardiansForParcelsUnderOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI NOT IN (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI IN (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    //TODO: Check
    public double getAverageNumberOfGuardiansPerParcelsUnderOtherHoldingTypes(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(Guardian.*) c FROM Guardian, Parcel WHERE Parcel.stage = 4 and Guardian.UPI = Parcel.UPI and Guardian.stage=Parcel.stage and  Guardian.stage=4 and Guardian.status='active' and Guardian.UPI LIKE '" + kebele + "/%' and  Parcel.UPI NOT IN (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and Parcel.UPI IN (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1) group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMaleGuardiansForParcelsUnderOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI NOT IN (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI IN (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemaleGuardiansForParcelsUnderOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI NOT IN (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI IN (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public String getFirstDataEntryDateInKebeleForOtherHoldingTypes(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT min(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and  UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public String getLastDataEntryDateInKebeleForOtherHoldingTypes(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT max(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and  UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public double getAverageNumberOfPIPerParcelsUnderDispute(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(PersonWithInterest.*) c FROM PersonWithInterest, Parcel WHERE Parcel.stage = 4 and PersonWithInterest.UPI = Parcel.UPI and PersonWithInterest.stage=Parcel.stage and  PersonWithInterest.stage=4 and PersonWithInterest.status='active' and PersonWithInterest.UPI LIKE '" + kebele + "/%' and Parcel.hasDispute='true' group by Parcel.UPI) as q";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithGuardiansUnderDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='true' AND UPI IN (SELECT UPI FROM Guardian WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfGuardiansForParcelsWithDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' and hasDispute='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageNumberOfGuardiansPerParcelsUnderDispute(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(Guardian.*) c FROM Guardian, Parcel WHERE Parcel.stage = 4 and Guardian.UPI = Parcel.UPI and Guardian.stage=Parcel.stage and  Guardian.stage=4 and Guardian.status='active' and Guardian.UPI LIKE '" + kebele + "/%' and  Parcel.hasDispute='true' GROUP BY Parcel.UPI) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMaleGuardiansForParcelsWithDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' and hasDispute='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemaleGuardiansForParcelsWithDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' and hasDispute='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public String getFirstDataEntryDateInKebeleForParcelsWithDispute(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT min(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and  hasDispute='true' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public String getLastDataEntryDateInKebeleForParcelsWithDispute(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT max(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and  hasDispute='true' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public long getCountOfAllParcelsWithGuardians(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Guardian WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfGuardiansForAllParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public double getAverageNumberOfGuardiansPerParcelForAllParcels(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(Guardian.*) c FROM Guardian, Parcel WHERE Parcel.stage = 4 and Guardian.UPI = Parcel.UPI and Guardian.stage=Parcel.stage and  Guardian.stage=4 and Guardian.status='active' and Guardian.UPI LIKE '" + kebele + "/%' GROUP BY Parcel.UPI) as t ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfMaleGuardiansForAllParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfFemaleGuardiansForAllParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Guardian WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public String getFirstDataEntryDateInKebeleForAllParcels(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT min(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    public String getLastDataEntryDateInKebeleForAllParcels(long kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT max(registeredon) as d  FROM Parcel WHERE UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue == null ? "" : returnValue.toString();
    }

    /// Disputes Report
    public long getNumberOfDisputesByType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Dispute WHERE disputetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfAllDisputes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Dispute WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfPeopleInDisputeByType(long kebele, byte type) {
        return getNumberOfHoldersInDisputeByType(kebele, type) + getNumberOfDisputantsInDisputeByType(kebele, type);
    }

    public long getNumberOfPeopleAllDisputes(long kebele) {
        return getNumberOfHoldersInAllDisputes(kebele) + getNumberOfDisputantsInAllDisputes(kebele);
    }

    public long getNumberOfDisputantsInDisputeByType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Dispute WHERE disputetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfDisputantsInAllDisputes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM Dispute WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfHoldersInDisputeByType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Dispute WHERE disputetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%') ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfHoldersInAllDisputes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Dispute WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%') ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfMarriedCoupleHoldersInDisputeByType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM marriedcouple WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Dispute WHERE disputetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%') ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfMarriedCoupleHoldersInAllDisputes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM marriedcouple WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Dispute WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%') ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfSingleFemaleHoldersInDisputeByType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM singlefemaleholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Dispute WHERE disputetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%') ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfSingleFemaleHoldersInAllDisputes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM singlefemaleholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Dispute WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%') ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfSingleMaleHoldersInDisputeByType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM singlemaleholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Dispute WHERE disputetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%') ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfSingleMaleHoldersInAllDisputes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM singlemaleholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Dispute WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%') ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfOrphanHoldersInDisputeByType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and isorphan = 'true' and UPI in (SELECT UPI FROM Dispute WHERE disputetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%') ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfOrphanHoldersInAllDisputes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and isorphan = 'true' and UPI in (SELECT UPI FROM Dispute WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%') ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfNonNaturalHoldersInDisputeByType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM organizationholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Dispute WHERE disputetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%') ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfNonNaturalHoldersInAllDisputes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM organizationholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Dispute WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%') ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfOtherHoldingTypesInDisputeByType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and familyrole!=1 AND familyrole!=2  and UPI in (SELECT UPI FROM Dispute WHERE disputetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1) ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfOtherHoldingTypesInAllDisputes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and familyrole!=1 AND familyrole!=2  and UPI in (SELECT UPI FROM Dispute WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1) ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfDisputesByTypeAndStatus(long kebele, byte type, byte status) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Dispute WHERE disputetype=? AND disputestatus=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            stmnt.setByte(2, status);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getNumberOfAllDisputesByStatus(long kebele, byte status) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Dispute WHERE disputestatus=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, status);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfHoldersOfOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder,Parcel WHERE Parcel.hasDispute='false' AND Parcel.stage=4 and Parcel.status='active' AND Parcel.UPI =IndividualHolder.UPI AND IndividualHolder.stage=4 and IndividualHolder.status='active' AND IndividualHolder.UPI LIKE '" + kebele + "/%' and IndividualHolder.UPI not in ( SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and IndividualHolder.UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<String> getHoldingNumbers(long kebele, byte type) {
        ArrayList<String> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT distinct holdingno FROM Parcel WHERE stage=4 and status='active' AND UPI LIKE '" + kebele + "/%' AND holdingtype=? ORDER BY holdingno";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet holdings = stmnt.executeQuery();
            while (holdings.next()) {
                String holding = holdings.getString("holdingno");
                if (!holding.trim().isEmpty()) {
                    returnValue.add(holding);
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<String> getHoldingNumbers(long kebele) {
        ArrayList<String> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT distinct holdingno FROM Parcel WHERE stage=4 and status='active' AND UPI LIKE '" + kebele + "/%' AND holdingtype<>1 ORDER BY holdingno";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet holdings = stmnt.executeQuery();
            while (holdings.next()) {
                String holding = holdings.getString("holdingno");
                if (!holding.trim().isEmpty()) {
                    returnValue.add(holding);
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<HolderPublicDisplayDTO> getPublicDisplayReportI(long kebele) {
        ArrayList<HolderPublicDisplayDTO> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT distinct holderId FROM individualholder WHERE stage=4 and status='active' AND UPI LIKE '" + kebele + "/%' ORDER BY holderid";
            PreparedStatement stmnt0 = connection.prepareStatement(query);
            ResultSet rs = stmnt0.executeQuery();
            ArrayList<String> holderIds = new ArrayList<>();
            while (rs.next()) {
                holderIds.add(rs.getString("holderid"));
            }

            for (String h : holderIds) {
                query = "SELECT distinct holderid, upi, stage, firstname,  fathersname, grandfathersname, sex, dateofbirth, familyrole, physicalimpairment, status, isorphan FROM individualholder WHERE stage=4 and status='active' AND UPI LIKE '" + kebele + "/%' AND holderid=?";
                PreparedStatement stmnt1 = connection.prepareStatement(query);
                stmnt1.setString(1, h);
                ResultSet holderrs = stmnt1.executeQuery();
                HolderPublicDisplayDTO holder = null;
                while (holderrs.next()) {
                    // if the holder is not registered already add it
                    if (holder == null) {
                        holder = new HolderPublicDisplayDTO();
                        holder.setId(holderrs.getString("holderid"));
                        holder.setName(holderrs.getString("firstname") + " " + holderrs.getString("fathersname") + " " + holderrs.getString("grandfathersname"));
                        holder.setSex("f".equalsIgnoreCase(holderrs.getString("sex")) ? CommonStorage.getText("female") : CommonStorage.getText("male"));
                    }
                    query = "SELECT distinct upi, stage, teamno, area, parcelid, certificateno, holdingno, hasdispute, otherevidence, landusetype, soilfertilitytype, holdingtype, acquisitiontype, acquisitionyear, surveydate, mapsheetno, encumbrancetype, status FROM parcel WHERE stage=4 and status='active' AND UPI = ?";
                    PreparedStatement stmnt2 = connection.prepareStatement(query);
                    stmnt2.setString(1, holderrs.getString("upi"));
                    ResultSet parcels = stmnt2.executeQuery();
                    if (parcels.next()) {
                        boolean hasMissingValue = false;
                        ParcelPublicDisplayDTO parcel = new ParcelPublicDisplayDTO();
                        parcel.setHolding(parcels.getByte("holdingtype"));
                        parcel.setMapSheetNo(parcels.getString("mapsheetno"));
                        parcel.setParcelId(parcels.getInt("parcelid"));
                        parcel.setUpi(parcels.getString("upi"));
                        parcel.setArea(parcels.getDouble("area"));
                        parcel.hasDispute(parcels.getBoolean("hasdispute"));

                        if (parcels.getLong("otherevidence") == 5) {
                            hasMissingValue = true;
                        }
                        if (parcels.getLong("landusetype") == 13) {
                            hasMissingValue = true;
                        }
                        if (parcels.getLong("soilfertilitytype") == 5) {
                            hasMissingValue = true;
                        }
                        if (parcels.getLong("acquisitiontype") == 9) {
                            hasMissingValue = true;
                        }
                        if (parcels.getLong("acquisitionyear") < 1000) {
                            hasMissingValue = true;
                        }
                        if (parcels.getLong("encumbrancetype") == 7) {
                            hasMissingValue = true;
                        }

                        // Other holders
                        query = "SELECT * FROM individualholder WHERE stage=4 and status='active' AND UPI = ?";
                        PreparedStatement stmnt3 = connection.prepareStatement(query);
                        stmnt3.setString(1, holderrs.getString("upi"));
                        ResultSet coholders = stmnt3.executeQuery();
                        while (coholders.next()) {
                            if (coholders.getString("holderid").equalsIgnoreCase(holderrs.getString("holderid"))) {
                                continue;
                            }
                            parcel.addHolder(coholders.getString("firstName") + " " + coholders.getString("fathersname") + " " + coholders.getString("grandfathersname"));
                        }

                        query = "SELECT * FROM guardian WHERE stage=4 and status='active' AND UPI = ?";
                        PreparedStatement stmnt4 = connection.prepareStatement(query);
                        stmnt4.setString(1, holderrs.getString("upi"));
                        ResultSet guardians = stmnt4.executeQuery();

                        while (guardians.next()) {
                            parcel.addGuardian(guardians.getString("firstname") + " " + guardians.getString("fathersname") + " " + guardians.getString("grandfathersname"));
                            if (guardians.getString("firstname") == null || guardians.getString("firstname").isEmpty()) {
                                hasMissingValue = true;
                            }
                            if (guardians.getString("fathersname") == null || guardians.getString("fathersname").isEmpty()) {
                                hasMissingValue = true;
                            }
                            if (guardians.getString("grandfathersname") == null || guardians.getString("grandfathersname").isEmpty()) {
                                hasMissingValue = true;
                            }
                            if ("n".equalsIgnoreCase(guardians.getString("sex"))) {
                                hasMissingValue = true;
                            }
                        }

                        query = "SELECT * FROM personwithinterest WHERE stage=4 and status='active' AND UPI = ?";
                        PreparedStatement stmnt5 = connection.prepareStatement(query);
                        stmnt5.setString(1, holderrs.getString("upi"));
                        ResultSet personswithinterest = stmnt5.executeQuery();

                        while (personswithinterest.next()) {
                            if (personswithinterest.getString("firstname") == null || personswithinterest.getString("firstname").isEmpty()) {
                                hasMissingValue = true;
                            }
                            if (personswithinterest.getString("fathersname") == null || personswithinterest.getString("fathersname").isEmpty()) {
                                hasMissingValue = true;
                            }
                            if (personswithinterest.getString("grandfathersname") == null || personswithinterest.getString("grandfathersname").isEmpty()) {
                                hasMissingValue = true;
                            }
                            if ("n".equalsIgnoreCase(personswithinterest.getString("sex"))) {
                                hasMissingValue = true;
                            }

                        }
                        query = "SELECT * FROM dispute WHERE stage=4 and status='active' AND UPI = ?";
                        PreparedStatement stmnt6 = connection.prepareStatement(query);
                        stmnt6.setString(1, holderrs.getString("upi"));
                        ResultSet disputes = stmnt6.executeQuery();

                        while (disputes.next()) {
                            if (disputes.getString("firstname") == null || disputes.getString("firstname").isEmpty()) {
                                hasMissingValue = true;
                            }
                            if (disputes.getString("fathersname") == null || disputes.getString("fathersname").isEmpty()) {
                                hasMissingValue = true;
                            }
                            if (disputes.getString("grandfathersname") == null || disputes.getString("grandfathersname").isEmpty()) {
                                hasMissingValue = true;
                            }
                            if ("n".equalsIgnoreCase(disputes.getString("sex"))) {
                                hasMissingValue = true;
                            }
                            if (disputes.getLong("disputetype") == 5) {
                                hasMissingValue = true;
                            }
                            if (disputes.getLong("disputestatus") == 8) {
                                hasMissingValue = true;
                            }

                        }
                        parcel.hasMissingValue(hasMissingValue);
                        holder.addParcel(parcel);
                    }
                }
                returnValue.add(holder);

            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<HolderPublicDisplayDTO> getPublicDisplayReportO(long kebele) {
        ArrayList<HolderPublicDisplayDTO> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            /// Organizational Holder
            String query = "SELECT * FROM organizationholder WHERE stage=4 and status='active' AND UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt4 = connection.prepareStatement(query);
            ResultSet organizationholder = stmnt4.executeQuery();
            while (organizationholder.next()) {
                HolderPublicDisplayDTO holder = new HolderPublicDisplayDTO();
                holder.setName(organizationholder.getString("organizationname"));

                query = "SELECT * FROM parcel WHERE stage=4 and status='active' AND UPI = ?";
                PreparedStatement stmnt5 = connection.prepareStatement(query);
                stmnt5.setString(1, organizationholder.getString("upi"));
                ResultSet parcels = stmnt5.executeQuery();
                while (parcels.next()) {
                    boolean hasMissingValue = false;
                    ParcelPublicDisplayDTO parcel = new ParcelPublicDisplayDTO();
                    parcel.setHolding(parcels.getByte("holdingtype"));
                    parcel.setMapSheetNo(parcels.getString("mapsheetno"));
                    parcel.setParcelId(parcels.getInt("parcelid"));
                    parcel.setArea(parcels.getDouble("area"));
                    parcel.setUpi(parcels.getString("upi"));
                    parcel.hasDispute(parcels.getBoolean("hasdispute"));
                    if (parcels.getLong("otherevidence") == 5) {
                        hasMissingValue = true;
                    }
                    if (parcels.getLong("landusetype") == 13) {
                        hasMissingValue = true;
                    }
                    if (parcels.getLong("soilfertilitytype") == 5) {
                        hasMissingValue = true;
                    }
                    if (parcels.getLong("acquisitiontype") == 9) {
                        hasMissingValue = true;
                    }
                    if (parcels.getLong("acquisitionyear") == 0) {
                        hasMissingValue = true;
                    }
                    if (parcels.getLong("encumbrancetype") == 7) {
                        hasMissingValue = true;
                    }
                    parcel.hasMissingValue(hasMissingValue);
                    holder.addParcel(parcel);
                }

                returnValue.add(holder);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<HoldingPublicDisplayDTO> getPublicDisplayReportI2(long kebele) {
        ArrayList<HoldingPublicDisplayDTO> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT distinct holdingno FROM Parcel WHERE stage=4 and status='active' AND UPI LIKE '" + kebele + "/%' ORDER BY holdingno";
            PreparedStatement holdingsStmnt = connection.prepareStatement(query);
            ResultSet holdings = holdingsStmnt.executeQuery();
            ArrayList<String> holdingNos = new ArrayList<>();
            while (holdings.next()) {
                String holding = holdings.getString("holdingno");
                if (!holding.trim().isEmpty()) {
                    holdingNos.add(holding);
                }
            }
            for (String h : holdingNos) {
                HoldingPublicDisplayDTO hpd = new HoldingPublicDisplayDTO();
                hpd.setHoldingNumber(h);

                // Get UPIs under this holding number
                query = "SELECT distinct upi,* FROM parcel WHERE stage=4 and status='active' AND holdingno = ?";
                PreparedStatement parcelsStmnt = connection.prepareStatement(query);
                parcelsStmnt.setString(1, h);
                ResultSet parcelsRS = parcelsStmnt.executeQuery();
                while (parcelsRS.next()) {
                    boolean hasMissingValue = false;
                    HoldingParcelPublicDisplayDTO parcel = new HoldingParcelPublicDisplayDTO();
                    parcel.setHolding(parcelsRS.getByte("holdingtype"));
                    parcel.setMapSheetNo(parcelsRS.getString("mapsheetno"));
                    parcel.setParcelId(parcelsRS.getInt("parcelid"));
                    parcel.setUpi(parcelsRS.getString("upi"));
                    parcel.setArea(parcelsRS.getDouble("area"));
                    parcel.hasDispute(parcelsRS.getBoolean("hasdispute"));
                    if (parcelsRS.getLong("otherevidence") == 5) {
                        hasMissingValue = true;
                    }
                    if (parcelsRS.getLong("landusetype") == 13) {
                        hasMissingValue = true;
                    }
                    if (parcelsRS.getLong("soilfertilitytype") == 5) {
                        hasMissingValue = true;
                    }
                    if (parcelsRS.getLong("acquisitiontype") == 9) {
                        hasMissingValue = true;
                    }
                    if (parcelsRS.getLong("acquisitionyear") < 1000) {
                        hasMissingValue = true;
                    }
                    if (parcelsRS.getLong("encumbrancetype") == 7) {
                        hasMissingValue = true;
                    }
                    String upi = parcelsRS.getString("upi");
                    query = "SELECT * FROM guardian WHERE stage=4 and status='active' AND UPI = ?";
                    connection = CommonStorage.getConnection();
                    PreparedStatement guardianStmnt = connection.prepareStatement(query);
                    guardianStmnt.setString(1, parcel.getUpi());
                    ResultSet guardianRS = guardianStmnt.executeQuery();
                    while (guardianRS.next()) {
                        parcel.addGuardian(guardianRS.getString("firstname") + " " + guardianRS.getString("fathersname") + " " + guardianRS.getString("grandfathersname"));
                        if (guardianRS.getString("firstname") == null || guardianRS.getString("firstname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if (guardianRS.getString("fathersname") == null || guardianRS.getString("fathersname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if (guardianRS.getString("grandfathersname") == null || guardianRS.getString("grandfathersname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if ("n".equalsIgnoreCase(guardianRS.getString("sex"))) {
                            hasMissingValue = true;
                        }
                    }
                    query = "SELECT * FROM personwithinterest WHERE stage=4 and status='active' AND UPI = ?";
                    connection = CommonStorage.getConnection();
                    PreparedStatement pwiStmnt = connection.prepareStatement(query);
                    pwiStmnt.setString(1, parcel.getUpi());
                    ResultSet pwiRS = pwiStmnt.executeQuery();

                    while (pwiRS.next()) {
                        if (pwiRS.getString("firstname") == null || pwiRS.getString("firstname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if (pwiRS.getString("fathersname") == null || pwiRS.getString("fathersname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if (pwiRS.getString("grandfathersname") == null || pwiRS.getString("grandfathersname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if ("n".equalsIgnoreCase(pwiRS.getString("sex"))) {
                            hasMissingValue = true;
                        }
                    }
                    query = "SELECT * FROM dispute WHERE stage=4 and status='active' AND UPI = ?";
                    connection = CommonStorage.getConnection();
                    PreparedStatement disputeStmnt = connection.prepareStatement(query);
                    disputeStmnt.setString(1, parcel.getUpi());
                    ResultSet disputeRS = disputeStmnt.executeQuery();

                    while (disputeRS.next()) {
                        if (disputeRS.getString("firstname") == null || disputeRS.getString("firstname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if (disputeRS.getString("fathersname") == null || disputeRS.getString("fathersname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if (disputeRS.getString("grandfathersname") == null || disputeRS.getString("grandfathersname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if ("n".equalsIgnoreCase(disputeRS.getString("sex"))) {
                            hasMissingValue = true;
                        }
                        if (disputeRS.getLong("disputetype") == 5) {
                            hasMissingValue = true;
                        }
                        if (disputeRS.getLong("disputestatus") == 8) {
                            hasMissingValue = true;
                        }

                    }
                    parcel.hasMissingValue(hasMissingValue);
                    hpd.addParcel(parcel);
                    // Get holders list
                    query = "SELECT distinct holderid, firstname, fathersname, grandfathersname,"
                            + " sex FROM individualholder WHERE stage=4 AND status='active' "
                            + "AND upi= ?";
                    connection = CommonStorage.getConnection();
                    PreparedStatement holdersStmnt = connection.prepareStatement(query);
                    holdersStmnt.setString(1, upi);
                    ResultSet holdersRS = holdersStmnt.executeQuery();
                    while (holdersRS.next()) {
                        HoldingHolderDTO holder = new HoldingHolderDTO();
                        holder.setName(holdersRS.getString("firstname") + " " + holdersRS.getString("fathersname") + " " + holdersRS.getString("grandfathersname"));
                        holder.setSex(holdersRS.getString("sex"));
                        hpd.addHolder(holder);
                    }
                }

                returnValue.add(hpd);
            }
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<HoldingPublicDisplayDTO> getPublicDisplayReportO2(long kebele) {
        ArrayList<HoldingPublicDisplayDTO> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT distinct holdingno FROM Parcel WHERE stage=4 and status='active' AND UPI LIKE '" + kebele + "/%' ORDER BY holdingno";
            PreparedStatement holdingsStmnt = connection.prepareStatement(query);
            ResultSet holdings = holdingsStmnt.executeQuery();
            ArrayList<String> holdingNos = new ArrayList<>();
            while (holdings.next()) {
                String holding = holdings.getString("holdingno");
                if (!holding.trim().isEmpty()) {
                    holdingNos.add(holding);
                }
            }
            for (String h : holdingNos) {
                HoldingPublicDisplayDTO hpd = new HoldingPublicDisplayDTO();
                hpd.setHoldingNumber(h);

                // Get UPIs under this holding number
                query = "SELECT distinct upi,* FROM parcel WHERE stage=4 and status='active' AND holdingno = ?";
                PreparedStatement parcelsStmnt = connection.prepareStatement(query);
                parcelsStmnt.setString(1, h);
                ResultSet parcelsRS = parcelsStmnt.executeQuery();
                while (parcelsRS.next()) {
                    boolean hasMissingValue = false;
                    HoldingParcelPublicDisplayDTO parcel = new HoldingParcelPublicDisplayDTO();
                    parcel.setHolding(parcelsRS.getByte("holdingtype"));
                    parcel.setMapSheetNo(parcelsRS.getString("mapsheetno"));
                    parcel.setParcelId(parcelsRS.getInt("parcelid"));
                    parcel.setUpi(parcelsRS.getString("upi"));
                    parcel.setArea(parcelsRS.getDouble("area"));
                    parcel.hasDispute(parcelsRS.getBoolean("hasdispute"));
                    if (parcelsRS.getLong("otherevidence") == 5) {
                        hasMissingValue = true;
                    }
                    if (parcelsRS.getLong("landusetype") == 13) {
                        hasMissingValue = true;
                    }
                    if (parcelsRS.getLong("soilfertilitytype") == 5) {
                        hasMissingValue = true;
                    }
                    if (parcelsRS.getLong("acquisitiontype") == 9) {
                        hasMissingValue = true;
                    }
                    if (parcelsRS.getLong("acquisitionyear") < 1000) {
                        hasMissingValue = true;
                    }
                    if (parcelsRS.getLong("encumbrancetype") == 7) {
                        hasMissingValue = true;
                    }
                    String upi = parcelsRS.getString("upi");

                    query = "SELECT * FROM dispute WHERE stage=4 and status='active' AND UPI = ?";
                    connection = CommonStorage.getConnection();
                    PreparedStatement disputeStmnt = connection.prepareStatement(query);
                    disputeStmnt.setString(1, parcel.getUpi());
                    ResultSet disputeRS = disputeStmnt.executeQuery();

                    while (disputeRS.next()) {
                        if (disputeRS.getString("firstname") == null || disputeRS.getString("firstname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if (disputeRS.getString("fathersname") == null || disputeRS.getString("fathersname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if (disputeRS.getString("grandfathersname") == null || disputeRS.getString("grandfathersname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if ("n".equalsIgnoreCase(disputeRS.getString("sex"))) {
                            hasMissingValue = true;
                        }
                        if (disputeRS.getLong("disputetype") == 5) {
                            hasMissingValue = true;
                        }
                        if (disputeRS.getLong("disputestatus") == 8) {
                            hasMissingValue = true;
                        }

                    }
                    parcel.hasMissingValue(hasMissingValue);
                    hpd.addParcel(parcel);
                    // Get holders list
                    query = "SELECT distinct organizationname"
                            + "  FROM organizationholder WHERE stage=4 AND status='active' "
                            + "AND upi= ?";
                    PreparedStatement holdersStmnt = connection.prepareStatement(query);
                    holdersStmnt.setString(1, upi);
                    ResultSet holdersRS = holdersStmnt.executeQuery();
                    while (holdersRS.next()) {
                        HoldingHolderDTO holder = new HoldingHolderDTO();
                        holder.setName(holdersRS.getString("organizationname"));
                        hpd.addHolder(holder);
                    }
                }

                returnValue.add(hpd);
            }
            //}
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;

    }

    public HoldingPublicDisplayDTO getPublicDisplayInfoByHoldingNumberIH(long kebele, String holdingNo) {
        HoldingPublicDisplayDTO returnValue = new HoldingPublicDisplayDTO();
        returnValue.setHoldingNumber(holdingNo);
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT distinct upi,area,parcelid,holdingtype,mapsheetno,hasdispute, "
                    + "otherevidence,landusetype,soilfertilitytype,acquisitiontype,acquisitionyear,encumbrancetype FROM parcel WHERE stage=4 AND status='active' AND holdingno=? AND UPI LIKE '" + kebele + "/%' ORDER BY upi";
            PreparedStatement parcelsStmnt = connection.prepareStatement(query);
            parcelsStmnt.setString(1, holdingNo);
            ResultSet parcelsRS = parcelsStmnt.executeQuery();
            ArrayList<HoldingParcelPublicDisplayDTO> parcels = new ArrayList<>();
            ArrayList<String> upis = new ArrayList<>();
            // Get Parcel Detail
            while (parcelsRS.next()) {
                boolean hasMissingValue = false;
                HoldingParcelPublicDisplayDTO parcel = new HoldingParcelPublicDisplayDTO();
                parcel.setUpi(parcelsRS.getString("upi"));
                upis.add(parcelsRS.getString("upi"));
                parcel.setArea(parcelsRS.getDouble("area"));
                parcel.setHolding(parcelsRS.getByte("holdingtype"));
                parcel.setMapSheetNo(parcelsRS.getString("mapsheetno"));
                parcel.setParcelId(parcelsRS.getInt("parcelid"));
                parcel.hasDispute(parcelsRS.getBoolean("hasdispute"));

                if (parcelsRS.getLong("otherevidence") == 5) {
                    hasMissingValue = true;
                }
                if (parcelsRS.getLong("landusetype") == 13) {
                    hasMissingValue = true;
                }// 
                if (parcelsRS.getLong("soilfertilitytype") == 5) {
                    hasMissingValue = true;
                }
                if (parcelsRS.getLong("acquisitiontype") == 9) {
                    hasMissingValue = true;
                }
                if (parcelsRS.getLong("acquisitionyear") < 1000) {
                    hasMissingValue = true;
                }
                if (parcelsRS.getLong("encumbrancetype") == 7) {
                    hasMissingValue = true;
                }
                query = "SELECT 'dispute' as tablename, firstname, fathersname, "
                        + "grandfathersname, disputetype, disputestatus, 'n' as "
                        + "sex FROM dispute WHERE upi=? AND status='active' AND "
                        + "stage=4 UNION SELECT 'personwithinterest' as tablename, "
                        + "firstname, fathersname, grandfathersname, -1 as disputetype, "
                        + "-1 as disputestatus,sex FROM personwithinterest WHERE "
                        + "upi=? AND status='active' AND stage=4 UNION SELECT "
                        + "'guardian' as tablename, firstname, fathersname, "
                        + "grandfathersname, -1 as disputetype, -1 as disputestatus, "
                        + "sex FROM guardian WHERE upi=? AND status='active' AND stage=4 ";
                PreparedStatement stmnt = connection.prepareStatement(query);
                stmnt.setString(1, parcel.getUpi());
                stmnt.setString(2, parcel.getUpi());
                stmnt.setString(3, parcel.getUpi());
                ResultSet rs = stmnt.executeQuery();

                while (rs.next()) {
                    String tbl = rs.getString("tablename");
                    if (tbl.equalsIgnoreCase("dispute")) {
                        if (rs.getString("firstname") == null || rs.getString("firstname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if (rs.getString("fathersname") == null || rs.getString("fathersname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if (rs.getString("grandfathersname") == null || rs.getString("grandfathersname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if ("n".equalsIgnoreCase(rs.getString("sex"))) {
                            hasMissingValue = true;
                        }
                        if (rs.getLong("disputetype") == 5) {
                            hasMissingValue = true;
                        }
                        if (rs.getLong("disputestatus") == 8) {
                            hasMissingValue = true;
                        }
                    } else if (tbl.equalsIgnoreCase("personwithinterest")) {
                        if (rs.getString("firstname") == null || rs.getString("firstname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if (rs.getString("fathersname") == null || rs.getString("fathersname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if (rs.getString("grandfathersname") == null || rs.getString("grandfathersname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if ("n".equalsIgnoreCase(rs.getString("sex"))) {
                            hasMissingValue = true;
                        }

                    } else if (tbl.equalsIgnoreCase("guardian")) {
                        parcel.addGuardian(rs.getString("firstname") + " " + rs.getString("fathersname") + " " + rs.getString("grandfathersname"));
                        if (rs.getString("firstname") == null || rs.getString("firstname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if (rs.getString("fathersname") == null || rs.getString("fathersname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if (rs.getString("grandfathersname") == null || rs.getString("grandfathersname").isEmpty()) {
                            hasMissingValue = true;
                        }
                        if ("n".equalsIgnoreCase(rs.getString("sex"))) {
                            hasMissingValue = true;
                        }
                    }
                }

                parcel.hasMissingValue(hasMissingValue);
                parcels.add(parcel);
            }
            ArrayList<HoldingHolderDTO> holders;
            for (String upi : upis) {
                holders = new ArrayList<>();
                query = "SELECT distinct firstname ||' ' || fathersname ||' ' || grandfathersname as name, sex FROM individualholder WHERE status='active' AND stage=4 AND upi=? ORDER BY name";
                PreparedStatement stmnt = connection.prepareStatement(query);
                stmnt.setString(1, upi);
                ResultSet rs = stmnt.executeQuery();
                while (rs.next()) {

                    HoldingHolderDTO holder = new HoldingHolderDTO();
                    holder.setName(rs.getString("name"));
                    holder.setSex(rs.getString("sex"));
                    holders.add(holder);
                }
                returnValue.setHolders(holders);
                returnValue.setParcels(parcels);
            }

            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public HoldingPublicDisplayDTO getPublicDisplayInfoByHoldingNumberOH(String holdingNo, long kebele) {
        HoldingPublicDisplayDTO returnValue = new HoldingPublicDisplayDTO();
        returnValue.setHoldingNumber(holdingNo);
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT distinct upi,area,parcelid,holdingtype,mapsheetno,hasdispute, "
                    + "otherevidence,landusetype,soilfertilitytype,acquisitiontype,acquisitionyear,encumbrancetype FROM parcel WHERE stage=4 AND status='active' AND holdingno=? AND UPI LIKE '" + kebele + "/%' ORDER BY upi";
            PreparedStatement parcelsStmnt = connection.prepareStatement(query);
            parcelsStmnt.setString(1, holdingNo);
            ResultSet parcelsRS = parcelsStmnt.executeQuery();
            ArrayList<HoldingParcelPublicDisplayDTO> parcels = new ArrayList<>();
            ArrayList<String> upis = new ArrayList<>();
            // Get Parcel Detail
            while (parcelsRS.next()) {
                boolean hasMissingValue = false;
                HoldingParcelPublicDisplayDTO parcel = new HoldingParcelPublicDisplayDTO();
                parcel.setUpi(parcelsRS.getString("upi"));
                upis.add(parcelsRS.getString("upi"));
                parcel.setArea(parcelsRS.getDouble("area"));
                parcel.setHolding(parcelsRS.getByte("holdingtype"));
                parcel.setMapSheetNo(parcelsRS.getString("mapsheetno"));
                parcel.setParcelId(parcelsRS.getInt("parcelid"));
                parcel.hasDispute(parcelsRS.getBoolean("hasdispute"));

                if (parcelsRS.getLong("otherevidence") == 5) {
                    hasMissingValue = true;
                }
                if (parcelsRS.getLong("landusetype") == 13) {
                    hasMissingValue = true;
                }// 
                if (parcelsRS.getLong("soilfertilitytype") == 5) {
                    hasMissingValue = true;
                }
                if (parcelsRS.getLong("acquisitiontype") == 9) {
                    hasMissingValue = true;
                }
                if (parcelsRS.getLong("acquisitionyear") < 1000) {
                    hasMissingValue = true;
                }
                if (parcelsRS.getLong("encumbrancetype") == 7) {
                    hasMissingValue = true;
                }
                query = "SELECT firstname, fathersname, grandfathersname, disputetype,"
                        + "disputestatus, 'n' as sex FROM dispute WHERE upi=? AND "
                        + "status='active' AND stage=4 ";
                PreparedStatement stmnt = connection.prepareStatement(query);
                stmnt.setString(1, parcel.getUpi());
                ResultSet rs = stmnt.executeQuery();

                while (rs.next()) {
                    if (rs.getString("firstname") == null || rs.getString("firstname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if (rs.getString("fathersname") == null || rs.getString("fathersname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if (rs.getString("grandfathersname") == null || rs.getString("grandfathersname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if ("n".equalsIgnoreCase(rs.getString("sex"))) {
                        hasMissingValue = true;
                    }
                    if (rs.getLong("disputetype") == 5) {
                        hasMissingValue = true;
                    }
                    if (rs.getLong("disputestatus") == 8) {
                        hasMissingValue = true;
                    }
                }
                parcel.hasMissingValue(hasMissingValue);
                parcels.add(parcel);
            }
            ArrayList<HoldingHolderDTO> holders;
            // for all
            holders = new ArrayList<>();
            query = "SELECT distinct organizationname FROM organizationholder WHERE status='active' AND stage=4 AND upi=? ORDER BY organizationname";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, upis.get(0));
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                HoldingHolderDTO holder = new HoldingHolderDTO();
                holder.setName(rs.getString("organizationname"));
                holders.add(holder);
            }
            returnValue.setHolders(holders);
            returnValue.setParcels(parcels);

            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public HolderPublicDisplayDTO getPublicDisplayInfoByPhotoIDIH(String photoId) {
        HolderPublicDisplayDTO returnValue = null;
        /// TODO:
        return returnValue;
    }

    public HolderPublicDisplayDTO getPublicDisplayInfoByNameOH(String name) {
        HolderPublicDisplayDTO returnValue = null;
        /// TODO:
        return returnValue;
    }

    public ArrayList<String> getParcelsWithoutHolder(long kebele) {
        ArrayList<String> returnValue = new ArrayList<>();
        Connection connection;
        try {
            String query = "SELECT * FROM dblink("
                    + getDBLinkString() + " ,'SELECT parcel_id FROM " + getKebeleTable(kebele)
                    + "') as P2(parcelId integer) EXCEPT SELECT parcelId FROM "
                    + "Parcel WHERE status='active' AND stage=4 AND UPI LIKE '" + kebele + "/%'";

            connection = CommonStorage.getConnection();
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                returnValue.add(rs.getString("parcelId"));
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<Parcel> getALLParcelsInCommitted(String kebele) {
        ArrayList<Parcel> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT * FROM Parcel WHERE stage = 4 and status='active' and upi not in (SELECT upi FROM Parcel WHERE stage >= 5 and status='active')";
            if (!kebele.equalsIgnoreCase("all")) {
                query += " and UPI LIKE '" + kebele + "/%'";
            }
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                Parcel parcel = new Parcel();
                parcel.setUpi(rs.getString("upi"));
                parcel.setCertificateNumber(rs.getString("certificateno"));
                parcel.hasDispute(rs.getBoolean("hasdispute"));
                parcel.setAcquisition(rs.getByte("acquisitiontype"));
                parcel.setSurveyDate(rs.getString("surveydate"));

                /*parcel.setAcquisitionYear(rs.getInt("acquisitionyear"));
                 parcel.setCurrentLandUse(rs.getByte("landusetype"));
                 parcel.setEncumbrance(rs.getByte("encumbrancetype"));
                 parcel.setHolding(rs.getByte("holdingtype"));
                 parcel.setHoldingNumber(rs.getString("holdingno"));
                 parcel.setMapSheetNo(rs.getString("mapsheetno"));
                 parcel.setOtherEvidence(rs.getByte("otherevidence"));
                 parcel.setParcelId(rs.getInt("parcelid"));
                 parcel.setRegisteredBy(rs.getLong("registeredby"));
                 parcel.setSoilFertility(rs.getByte("soilfertilitytype"));
                 parcel.setStage(rs.getByte("stage"));
                 parcel.setStatus(rs.getString("status"));
                
                 parcel.setRegisteredOn(rs.getTimestamp("registeredon"));
                
                 parcel.setTeamNo(rs.getByte("teamNo"));
                 if (parcel.hasDispute()) {
                 parcel.setDisputes(getAllDisputes(parcel.getUpi(), parcel.getStage()));
                 }
                 if (parcel.getHolding() == 1) {
                 parcel.setIndividualHolders(getAllIndividualHolders(parcel.getUpi(), parcel.getStage()));
                 parcel.setPersonsWithInterest(getAllPersonsWithInterest(parcel.getUpi(), parcel.getStage()));
                 } else {
                 parcel.setOrganaizationHolder(getOrganaizationHolder(parcel.getUpi(), parcel.getStage()));
                 }
                 */
                returnValue.add(parcel);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    // Missing Data
    public long getCountOfHoldersWithMissingFirstName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and (firstName=null OR firstName='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingFirstName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM individualholder WHERE stage=4 and status='active' and (firstName=null OR firstName='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfHoldersWithMissingFathersName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and (fathersname=null OR fathersname='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingFathersName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM individualholder WHERE stage=4 and status='active' and (fathersname=null OR fathersname='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfHoldersWithMissingGrandfathersName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and (grandfathersname=null OR grandfathersname='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingGrandfathersName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM individualholder WHERE stage=4 and status='active' and (grandfathersname=null OR grandfathersname='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfHoldersWithMissingSex(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and sex='n' and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingSex(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM individualholder WHERE stage=4 and status='active' and sex='n' and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfHoldersWithMissingFamilyRole(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and familyrole=13 and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingFamilyRole(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM individualholder WHERE stage=4 and status='active' and familyrole=13 and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfHoldersWithMissingDateOfBirth(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and (dateofbirth=null OR dateofbirth='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingDateOfBirth(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM individualholder WHERE stage=4 and status='active' and (dateofbirth=null OR dateofbirth='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingCertificateNo(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE stage=4 and status='active' and (certificateno=null OR certificateno='') and  UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfHoldersInParcelsWithMissingCertificateNo(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and (certificateno=null OR certificateno='') and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingHoldingNo(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE stage=4 and status='active' and (holdingno=null OR holdingno='') and  UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfHoldersInParcelsWithMissingHoldingNo(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and (holdingno=null OR holdingno='') and UPI LIKE '" + kebele + "/%')";

            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingOtherEvidence(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE stage=4 and status='active' and otherevidence=5 and  UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfHoldersInParcelsWithMissingOtherEvidence(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and otherevidence=5 and UPI LIKE '" + kebele + "/%')";

            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingLandUseType(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE stage=4 and status='active' and landusetype = 14 and  UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfHoldersInParcelsWithMissingLandUseType(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and landusetype = 14 and UPI LIKE '" + kebele + "/%')";

            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingSoilFertility(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE stage=4 and status='active' and soilfertilitytype = 5 and  UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfHoldersInParcelsWithMissingSoilFertility(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and soilfertilitytype = 5 and UPI LIKE '" + kebele + "/%')";

            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfHoldersInParcelsWithMissingAcquisitionType(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE stage=4 and status='active' and acquisitiontype = 9 and  UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingAcquisitionType(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and acquisitiontype = 9 and UPI LIKE '" + kebele + "/%')";

            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfHoldersInParcelsWithMissingAcquisitionYear(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE stage=4 and status='active' and acquisitionyear < 1000 and  UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingAcquisitionYear(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and acquisitionyear < 1000 and UPI LIKE '" + kebele + "/%')";

            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfHoldersInParcelsWithMissingEncumbranceType(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE stage=4 and status='active' and encumbrancetype = 7 and  UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingEncumbranceType(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM individualholder WHERE stage=4 and status='active' and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and encumbrancetype = 7 and UPI LIKE '" + kebele + "/%')";

            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingDisputantsFirstName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM dispute WHERE stage=4 and status='active' and (firstName=null OR firstName='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingDisputantsFathersName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM dispute WHERE stage=4 and status='active' and (fathersname=null OR fathersname='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingDisputantsGrandfathersName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM dispute WHERE stage=4 and status='active' and (grandfathersname=null OR grandfathersname='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingDisputantsSex(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM dispute WHERE stage=4 and status='active' and (sex=null OR sex='n') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingDisputeType(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM dispute WHERE stage=4 and status='active' and disputetype=5 AND UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingDisputeStatus(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM dispute WHERE stage=4 and status='active' and disputestatus=8 and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingData(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM ( (SELECT upi FROM individualholder WHERE stage=4 and status='active' and (firstName=null OR firstName='' OR fathersname=null OR fathersname=''OR grandfathersname=null OR grandfathersname='' OR sex=null OR sex='n' OR familyrole=13 OR dateofbirth=null OR dateofbirth='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')) UNION (SELECT upi FROM Parcel WHERE stage=4 and status='active' and (certificateno=null OR certificateno='' OR holdingno=null OR holdingno='' OR otherevidence=5 OR landusetype = 14 OR soilfertilitytype = 5 OR acquisitiontype = 9 OR acquisitionyear < 1000 OR encumbrancetype = 7 ) and UPI LIKE '" + kebele + "/%') UNION (SELECT upi  FROM dispute WHERE stage=4 and status='active' AND (firstName=null OR firstName='' OR fathersname=null OR fathersname=''OR grandfathersname=null OR grandfathersname='' OR sex=null OR sex='n' OR  disputetype=5 OR disputestatus=8) AND UPI in (SELECT UPI FROM Parcel WHERE stage=4 AND status='active' AND UPI LIKE '" + kebele + "/%')) UNION (SELECT upi  FROM guardian WHERE stage=4 and status='active' and (firstName=null OR firstName='' OR fathersname=null OR fathersname=''OR grandfathersname=null OR grandfathersname='' OR sex=null OR sex='n') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%') ) UNION (SELECT upi  FROM personwithinterest WHERE stage=4 and status='active' and (firstName=null OR firstName='' OR fathersname=null OR fathersname=''OR grandfathersname=null OR grandfathersname='' OR sex=null OR sex='n') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%'))) T;";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingPersonWithInterestsFirstName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM personwithinterest WHERE stage=4 and status='active' and (firstName=null OR firstName='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingPersonWithInterestsFathersName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM personwithinterest WHERE stage=4 and status='active' and (fathersname=null OR fathersname='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingPersonWithInterestsGrandfathersName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM personwithinterest WHERE stage=4 and status='active' and (grandfathersname=null OR grandfathersname='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingPersonWithInterestsSex(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM personwithinterest WHERE stage=4 and status='active' and (sex=null OR sex='n') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingGuardiansFirstName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Guardian WHERE stage=4 and status='active' and (firstName=null OR firstName='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingGuardiansFathersName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Guardian WHERE stage=4 and status='active' and (fathersname=null OR fathersname='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingGuardiansGrandfathersName(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Guardian WHERE stage=4 and status='active' and (grandfathersname=null OR grandfathersname='') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfParcelsWithMissingGuardiansSex(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Guardian WHERE stage=4 and status='active' and (sex=null OR sex='n') and UPI in (SELECT UPI FROM Parcel WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    // Post PD & O Correction
    public boolean submitForMinorCorrection(Parcel parcel) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Parcel (upi,stage,registeredBy,registeredOn,"
                + "parcelId,certificateNo,holdingNo, otherEvidence,landUseType,"
                + "soilFertilityType,holdingType,acquisitionType,acquisitionYear,"
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute,teamNo) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, parcel.getUpi());
            stmnt.setByte(2, CommonStorage.getMinorCorrectionStage());
            stmnt.setLong(3, parcel.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setInt(5, parcel.getParcelId());
            stmnt.setString(6, parcel.getCertificateNumber());
            stmnt.setString(7, parcel.getHoldingNumber());
            stmnt.setByte(8, parcel.getOtherEvidence());
            stmnt.setByte(9, parcel.getCurrentLandUse());
            stmnt.setByte(10, parcel.getSoilFertility());
            stmnt.setByte(11, parcel.getHolding());
            stmnt.setByte(12, parcel.getMeansOfAcquisition());
            stmnt.setInt(13, parcel.getAcquisitionYear());
            stmnt.setString(14, parcel.getSurveyDate());
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            stmnt.setBoolean(18, parcel.hasDispute());
            stmnt.setByte(19, parcel.getTeamNo());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForMinorCorrection(Parcel parcel, int holdingLot) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Parcel (upi,stage,registeredBy,registeredOn,"
                + "parcelId,certificateNo,holdingNo, otherEvidence,landUseType,"
                + "soilFertilityType,holdingType,acquisitionType,acquisitionYear,"
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute,teamNo, holding_lot) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?,?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, parcel.getUpi());
            stmnt.setByte(2, CommonStorage.getMinorCorrectionStage());
            stmnt.setLong(3, parcel.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setInt(5, parcel.getParcelId());
            stmnt.setString(6, parcel.getCertificateNumber());
            stmnt.setString(7, parcel.getHoldingNumber());
            stmnt.setByte(8, parcel.getOtherEvidence());
            stmnt.setByte(9, parcel.getCurrentLandUse());
            stmnt.setByte(10, parcel.getSoilFertility());
            stmnt.setByte(11, parcel.getHolding());
            stmnt.setByte(12, parcel.getMeansOfAcquisition());
            stmnt.setInt(13, parcel.getAcquisitionYear());
            stmnt.setString(14, parcel.getSurveyDate());
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            stmnt.setBoolean(18, parcel.hasDispute());
            stmnt.setByte(19, parcel.getTeamNo());
            stmnt.setByte(20, (byte) holdingLot);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForMinorCorrection(OrganizationHolder holder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO organizationholder(upi, stage, organizationname, "
                + "registeredby, registeredon, organizationtype) VALUES "
                + "( ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, holder.getUpi());
            stmnt.setByte(2, CommonStorage.getMinorCorrectionStage());
            stmnt.setString(3, holder.getName());
            stmnt.setLong(4, holder.getRegisteredby());
            stmnt.setTimestamp(5, Timestamp.from(Instant.now()));
            stmnt.setByte(6, holder.getOrganizationType());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForMinorCorrection(IndividualHolder individualHolder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO individualholder( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth, familyrole, holderId, physicalImpairment, isOrphan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, individualHolder.getUpi());
            stmnt.setByte(2, CommonStorage.getMinorCorrectionStage());
            stmnt.setLong(3, individualHolder.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, individualHolder.getFirstName());
            stmnt.setString(6, individualHolder.getFathersName());
            stmnt.setString(7, individualHolder.getGrandFathersName());
            stmnt.setString(8, individualHolder.getSex());
            stmnt.setString(9, individualHolder.getDateOfBirth());
            stmnt.setByte(10, individualHolder.getFamilyRole());
            stmnt.setString(11, individualHolder.getId());
            stmnt.setBoolean(12, individualHolder.hasPhysicalImpairment());
            stmnt.setBoolean(13, individualHolder.isOrphan());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForMinorCorrection(PersonWithInterest personWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO PersonWithInterest( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, personWithInterest.getUpi());
            stmnt.setByte(2, CommonStorage.getMinorCorrectionStage());
            stmnt.setLong(3, personWithInterest.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, personWithInterest.getFirstName());
            stmnt.setString(6, personWithInterest.getFathersName());
            stmnt.setString(7, personWithInterest.getGrandFathersName());
            stmnt.setString(8, personWithInterest.getSex());
            stmnt.setString(9, personWithInterest.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForMinorCorrection(Guardian guardian) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Guardian( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, guardian.getUpi());
            stmnt.setByte(2, CommonStorage.getMinorCorrectionStage());
            stmnt.setLong(3, guardian.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, guardian.getFirstName());
            stmnt.setString(6, guardian.getFathersName());
            stmnt.setString(7, guardian.getGrandFathersName());
            stmnt.setString(8, guardian.getSex());
            stmnt.setString(9, guardian.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForMinorCorrection(Dispute dispute) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO dispute(upi, stage, registeredby, registeredon,"
                + "firstname, fathersname, grandfathersname, sex, disputetype, "
                + "disputestatus,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, dispute.getUpi());
            stmnt.setByte(2, CommonStorage.getMinorCorrectionStage());
            stmnt.setLong(3, dispute.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, dispute.getFirstName());
            stmnt.setString(6, dispute.getFathersName());
            stmnt.setString(7, dispute.getGrandFathersName());
            stmnt.setString(8, dispute.getSex());
            stmnt.setByte(9, dispute.getDisputeType());
            stmnt.setByte(10, dispute.getDisputeStatus());
            stmnt.setString(11, "active");
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForMajorCorrection(Parcel parcel) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Parcel (upi,stage,registeredBy,registeredOn,"
                + "parcelId,certificateNo,holdingNo, otherEvidence,landUseType,"
                + "soilFertilityType,holdingType,acquisitionType,acquisitionYear,"
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute,teamNo) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, parcel.getUpi());
            stmnt.setByte(2, CommonStorage.getMajorCorrectionStage());
            stmnt.setLong(3, parcel.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setInt(5, parcel.getParcelId());
            stmnt.setString(6, parcel.getCertificateNumber());
            stmnt.setString(7, parcel.getHoldingNumber());
            stmnt.setByte(8, parcel.getOtherEvidence());
            stmnt.setByte(9, parcel.getCurrentLandUse());
            stmnt.setByte(10, parcel.getSoilFertility());
            stmnt.setByte(11, parcel.getHolding());
            stmnt.setByte(12, parcel.getMeansOfAcquisition());
            stmnt.setInt(13, parcel.getAcquisitionYear());
            stmnt.setString(14, parcel.getSurveyDate());
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            stmnt.setBoolean(18, parcel.hasDispute());
            stmnt.setByte(19, parcel.getTeamNo());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForMajorCorrection(Parcel parcel, int holdingLot) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Parcel (upi,stage,registeredBy,registeredOn,"
                + "parcelId,certificateNo,holdingNo, otherEvidence,landUseType,"
                + "soilFertilityType,holdingType,acquisitionType,acquisitionYear,"
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute,teamNo, holding_lot) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?,?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, parcel.getUpi());
            stmnt.setByte(2, CommonStorage.getMajorCorrectionStage());
            stmnt.setLong(3, parcel.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setInt(5, parcel.getParcelId());
            stmnt.setString(6, parcel.getCertificateNumber());
            stmnt.setString(7, parcel.getHoldingNumber());
            stmnt.setByte(8, parcel.getOtherEvidence());
            stmnt.setByte(9, parcel.getCurrentLandUse());
            stmnt.setByte(10, parcel.getSoilFertility());
            stmnt.setByte(11, parcel.getHolding());
            stmnt.setByte(12, parcel.getMeansOfAcquisition());
            stmnt.setInt(13, parcel.getAcquisitionYear());
            stmnt.setString(14, parcel.getSurveyDate());
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            stmnt.setBoolean(18, parcel.hasDispute());
            stmnt.setByte(19, parcel.getTeamNo());
            stmnt.setByte(20, (byte) holdingLot);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForMajorCorrection(OrganizationHolder holder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO organizationholder(upi, stage, organizationname, "
                + "registeredby, registeredon, organizationtype) VALUES "
                + "( ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, holder.getUpi());
            stmnt.setByte(2, CommonStorage.getMajorCorrectionStage());
            stmnt.setString(3, holder.getName());
            stmnt.setLong(4, holder.getRegisteredby());
            stmnt.setTimestamp(5, Timestamp.from(Instant.now()));
            stmnt.setByte(6, holder.getOrganizationType());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForMajorCorrection(IndividualHolder individualHolder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO individualholder( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth, familyrole, holderId, physicalImpairment, isOrphan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, individualHolder.getUpi());
            stmnt.setByte(2, CommonStorage.getMajorCorrectionStage());
            stmnt.setLong(3, individualHolder.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, individualHolder.getFirstName());
            stmnt.setString(6, individualHolder.getFathersName());
            stmnt.setString(7, individualHolder.getGrandFathersName());
            stmnt.setString(8, individualHolder.getSex());
            stmnt.setString(9, individualHolder.getDateOfBirth());
            stmnt.setByte(10, individualHolder.getFamilyRole());
            stmnt.setString(11, individualHolder.getId());
            stmnt.setBoolean(12, individualHolder.hasPhysicalImpairment());
            stmnt.setBoolean(13, individualHolder.isOrphan());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForMajorCorrection(PersonWithInterest personWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO PersonWithInterest( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, personWithInterest.getUpi());
            stmnt.setByte(2, CommonStorage.getMajorCorrectionStage());
            stmnt.setLong(3, personWithInterest.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, personWithInterest.getFirstName());
            stmnt.setString(6, personWithInterest.getFathersName());
            stmnt.setString(7, personWithInterest.getGrandFathersName());
            stmnt.setString(8, personWithInterest.getSex());
            stmnt.setString(9, personWithInterest.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForMajorCorrection(Guardian guardian) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Guardian( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, guardian.getUpi());
            stmnt.setByte(2, CommonStorage.getMajorCorrectionStage());
            stmnt.setLong(3, guardian.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, guardian.getFirstName());
            stmnt.setString(6, guardian.getFathersName());
            stmnt.setString(7, guardian.getGrandFathersName());
            stmnt.setString(8, guardian.getSex());
            stmnt.setString(9, guardian.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForMajorCorrection(Dispute dispute) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO dispute(upi, stage, registeredby, registeredon,"
                + "firstname, fathersname, grandfathersname, sex, disputetype, "
                + "disputestatus,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, dispute.getUpi());
            stmnt.setByte(2, CommonStorage.getMajorCorrectionStage());
            stmnt.setLong(3, dispute.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, dispute.getFirstName());
            stmnt.setString(6, dispute.getFathersName());
            stmnt.setString(7, dispute.getGrandFathersName());
            stmnt.setString(8, dispute.getSex());
            stmnt.setByte(9, dispute.getDisputeType());
            stmnt.setByte(10, dispute.getDisputeStatus());
            stmnt.setString(11, "active");
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitToConfirmed(Parcel parcel) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Parcel (upi,stage,registeredBy,registeredOn,"
                + "parcelId,certificateNo,holdingNo, otherEvidence,landUseType,"
                + "soilFertilityType,holdingType,acquisitionType,acquisitionYear,"
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute,teamNo) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, parcel.getUpi());
            stmnt.setByte(2, CommonStorage.getConfirmedStage());
            stmnt.setLong(3, parcel.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setInt(5, parcel.getParcelId());
            stmnt.setString(6, parcel.getCertificateNumber());
            stmnt.setString(7, parcel.getHoldingNumber());
            stmnt.setByte(8, parcel.getOtherEvidence());
            stmnt.setByte(9, parcel.getCurrentLandUse());
            stmnt.setByte(10, parcel.getSoilFertility());
            stmnt.setByte(11, parcel.getHolding());
            stmnt.setByte(12, parcel.getMeansOfAcquisition());
            stmnt.setInt(13, parcel.getAcquisitionYear());
            stmnt.setString(14, parcel.getSurveyDate());
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            stmnt.setBoolean(18, parcel.hasDispute());
            stmnt.setByte(19, parcel.getTeamNo());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitToConfirmed(Parcel parcel, int holdingLot) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Parcel (upi,stage,registeredBy,registeredOn,"
                + "parcelId,certificateNo,holdingNo, otherEvidence,landUseType,"
                + "soilFertilityType,holdingType,acquisitionType,acquisitionYear,"
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute,teamNo, holding_lot) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?,?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, parcel.getUpi());
            stmnt.setByte(2, CommonStorage.getConfirmedStage());
            stmnt.setLong(3, parcel.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setInt(5, parcel.getParcelId());
            stmnt.setString(6, parcel.getCertificateNumber());
            stmnt.setString(7, parcel.getHoldingNumber());
            stmnt.setByte(8, parcel.getOtherEvidence());
            stmnt.setByte(9, parcel.getCurrentLandUse());
            stmnt.setByte(10, parcel.getSoilFertility());
            stmnt.setByte(11, parcel.getHolding());
            stmnt.setByte(12, parcel.getMeansOfAcquisition());
            stmnt.setInt(13, parcel.getAcquisitionYear());
            stmnt.setString(14, parcel.getSurveyDate());
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            stmnt.setBoolean(18, parcel.hasDispute());
            stmnt.setByte(19, parcel.getTeamNo());
            stmnt.setByte(20, (byte) holdingLot);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitToConfirmed(OrganizationHolder holder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO organizationholder(upi, stage, organizationname, "
                + "registeredby, registeredon, organizationtype) VALUES "
                + "( ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, holder.getUpi());
            stmnt.setByte(2, CommonStorage.getConfirmedStage());
            stmnt.setString(3, holder.getName());
            stmnt.setLong(4, holder.getRegisteredby());
            stmnt.setTimestamp(5, Timestamp.from(Instant.now()));
            stmnt.setByte(6, holder.getOrganizationType());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitToConfirmed(IndividualHolder individualHolder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO individualholder( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth, familyrole, holderId, physicalImpairment, isOrphan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, individualHolder.getUpi());
            stmnt.setByte(2, CommonStorage.getConfirmedStage());
            stmnt.setLong(3, individualHolder.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, individualHolder.getFirstName());
            stmnt.setString(6, individualHolder.getFathersName());
            stmnt.setString(7, individualHolder.getGrandFathersName());
            stmnt.setString(8, individualHolder.getSex());
            stmnt.setString(9, individualHolder.getDateOfBirth());
            stmnt.setByte(10, individualHolder.getFamilyRole());
            stmnt.setString(11, individualHolder.getId());
            stmnt.setBoolean(12, individualHolder.hasPhysicalImpairment());
            stmnt.setBoolean(13, individualHolder.isOrphan());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitToConfirmed(PersonWithInterest personWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO PersonWithInterest( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, personWithInterest.getUpi());
            stmnt.setByte(2, CommonStorage.getConfirmedStage());
            stmnt.setLong(3, personWithInterest.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, personWithInterest.getFirstName());
            stmnt.setString(6, personWithInterest.getFathersName());
            stmnt.setString(7, personWithInterest.getGrandFathersName());
            stmnt.setString(8, personWithInterest.getSex());
            stmnt.setString(9, personWithInterest.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitToConfirmed(Guardian guardian) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Guardian( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, guardian.getUpi());
            stmnt.setByte(2, CommonStorage.getConfirmedStage());
            stmnt.setLong(3, guardian.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, guardian.getFirstName());
            stmnt.setString(6, guardian.getFathersName());
            stmnt.setString(7, guardian.getGrandFathersName());
            stmnt.setString(8, guardian.getSex());
            stmnt.setString(9, guardian.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitToConfirmed(Dispute dispute) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO dispute(upi, stage, registeredby, registeredon,"
                + "firstname, fathersname, grandfathersname, sex, disputetype, "
                + "disputestatus,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, dispute.getUpi());
            stmnt.setByte(2, CommonStorage.getConfirmedStage());
            stmnt.setLong(3, dispute.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, dispute.getFirstName());
            stmnt.setString(6, dispute.getFathersName());
            stmnt.setString(7, dispute.getGrandFathersName());
            stmnt.setString(8, dispute.getSex());
            stmnt.setByte(9, dispute.getDisputeType());
            stmnt.setByte(10, dispute.getDisputeStatus());
            stmnt.setString(11, "active");
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public ArrayList<Parcel> getALLParcelsInMinorCorrection() {
        ArrayList<Parcel> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT * FROM Parcel WHERE stage = " + CommonStorage.getMinorCorrectionStage()
                    + " and status='active' and upi not in (SELECT upi FROM Parcel "
                    + "WHERE stage > " + CommonStorage.getMinorCorrectionStage() + " and status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                Parcel parcel = new Parcel();
                parcel.setAcquisition(rs.getByte("acquisitiontype"));
                parcel.setAcquisitionYear(rs.getInt("acquisitionyear"));
                parcel.setCertificateNumber(rs.getString("certificateno"));
                parcel.setCurrentLandUse(rs.getByte("landusetype"));
                parcel.setEncumbrance(rs.getByte("encumbrancetype"));
                parcel.setHolding(rs.getByte("holdingtype"));
                parcel.setHoldingNumber(rs.getString("holdingno"));
                parcel.setMapSheetNo(rs.getString("mapsheetno"));
                parcel.setOtherEvidence(rs.getByte("otherevidence"));
                parcel.setParcelId(rs.getInt("parcelid"));
                parcel.setRegisteredBy(rs.getLong("registeredby"));
                parcel.setSoilFertility(rs.getByte("soilfertilitytype"));
                parcel.setStage(rs.getByte("stage"));
                parcel.setStatus(rs.getString("status"));
                parcel.setSurveyDate(rs.getString("surveydate"));
                parcel.setUpi(rs.getString("upi"));
                parcel.setRegisteredOn(rs.getTimestamp("registeredon"));
                parcel.hasDispute(rs.getBoolean("hasdispute"));
                parcel.setTeamNo(rs.getByte("teamNo"));
                if (parcel.hasDispute()) {
                    parcel.setDisputes(getAllDisputes(parcel.getUpi(), parcel.getStage()));
                }
                if (parcel.getHolding() == 1) {
                    parcel.setIndividualHolders(getAllIndividualHolders(parcel.getUpi(), parcel.getStage()));
                    parcel.setPersonsWithInterest(getAllPersonsWithInterest(parcel.getUpi(), parcel.getStage()));
                } else {
                    parcel.setOrganaizationHolder(getOrganaizationHolder(parcel.getUpi(), parcel.getStage()));
                }
                returnValue.add(parcel);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<Parcel> getALLParcelsInMajorCorrectionSupervisor() {
        ArrayList<Parcel> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Parcel WHERE stage = " + CommonStorage.getMajorCorrectionSupervisorStage() + " and status='active' and upi not in (SELECT upi FROM Parcel WHERE stage = " + CommonStorage.getConfirmedStage() + " and status='active')");
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                Parcel parcel = new Parcel();
                parcel.setAcquisition(rs.getByte("acquisitiontype"));
                parcel.setAcquisitionYear(rs.getInt("acquisitionyear"));
                parcel.setCertificateNumber(rs.getString("certificateno"));
                parcel.setCurrentLandUse(rs.getByte("landusetype"));
                parcel.setEncumbrance(rs.getByte("encumbrancetype"));
                parcel.setHolding(rs.getByte("holdingtype"));
                parcel.setHoldingNumber(rs.getString("holdingno"));
                parcel.setMapSheetNo(rs.getString("mapsheetno"));
                parcel.setOtherEvidence(rs.getByte("otherevidence"));
                parcel.setParcelId(rs.getInt("parcelid"));
                parcel.setRegisteredBy(rs.getLong("registeredby"));
                parcel.setSoilFertility(rs.getByte("soilfertilitytype"));
                parcel.setStage(rs.getByte("stage"));
                parcel.setStatus(rs.getString("status"));
                parcel.setSurveyDate(rs.getString("surveydate"));
                parcel.setUpi(rs.getString("upi"));
                parcel.setRegisteredOn(rs.getTimestamp("registeredon"));
                parcel.hasDispute(rs.getBoolean("hasdispute"));
                parcel.setTeamNo(rs.getByte("teamNo"));
                if (parcel.hasDispute()) {
                    parcel.setDisputes(getAllDisputes(parcel.getUpi(), parcel.getStage()));
                }
                if (parcel.getHolding() == 1) {
                    parcel.setIndividualHolders(getAllIndividualHolders(parcel.getUpi(), parcel.getStage()));
                    parcel.setPersonsWithInterest(getAllPersonsWithInterest(parcel.getUpi(), parcel.getStage()));
                } else {
                    parcel.setOrganaizationHolder(getOrganaizationHolder(parcel.getUpi(), parcel.getStage()));
                }
                returnValue.add(parcel);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<Parcel> getALLParcels(String kebele, String status) {
        ArrayList<Parcel> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "";
            if (status.equalsIgnoreCase("committed")) {
                query = "SELECT * FROM Parcel WHERE stage = " + CommonStorage.getCommitedStage() + " and status='active' and upi not in (SELECT upi FROM Parcel WHERE stage > " + CommonStorage.getCommitedStage() + "  and status='active')";
            } else if (status.equalsIgnoreCase("committedbutnotconfirmed")) {
                query = "SELECT * FROM Parcel WHERE stage = " + CommonStorage.getCommitedStage() + " and status='active' and upi not in (SELECT upi FROM Parcel WHERE stage = " + CommonStorage.getConfirmedStage() + "  and status='active')";
            } else if (status.equalsIgnoreCase("confirmed")) {
                query = "SELECT * FROM Parcel WHERE stage = " + CommonStorage.getConfirmedStage() + "  and status='active'";
            } else if (status.equalsIgnoreCase("stillincorrection")) {
                query = "SELECT * FROM Parcel WHERE status = 'active' and stage > " + CommonStorage.getCommitedStage() + " and stage < " + CommonStorage.getConfirmedStage() + " and UPI not in (SELECT UPI FROM Parcel WHERE stage = " + CommonStorage.getConfirmedStage() + " and status='active')";
            }
            query += " and UPI NOT IN (SELECT upi FROM PARCEL WHERE stage=" + CommonStorage.getApprovedStage() + " and status='active')";
            if (!kebele.equalsIgnoreCase("all")) {
                query += " and UPI LIKE '" + kebele + "/%'";
            }
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                Parcel parcel = new Parcel();
                parcel.setAcquisition(rs.getByte("acquisitiontype"));
                parcel.setAcquisitionYear(rs.getInt("acquisitionyear"));
                parcel.setCertificateNumber(rs.getString("certificateno"));
                parcel.setCurrentLandUse(rs.getByte("landusetype"));
                parcel.setEncumbrance(rs.getByte("encumbrancetype"));
                parcel.setHolding(rs.getByte("holdingtype"));
                parcel.setHoldingNumber(rs.getString("holdingno"));
                parcel.setMapSheetNo(rs.getString("mapsheetno"));
                parcel.setOtherEvidence(rs.getByte("otherevidence"));
                parcel.setParcelId(rs.getInt("parcelid"));
                parcel.setRegisteredBy(rs.getLong("registeredby"));
                parcel.setSoilFertility(rs.getByte("soilfertilitytype"));
                parcel.setStage(rs.getByte("stage"));
                parcel.setStatus(rs.getString("status"));
                parcel.setSurveyDate(rs.getString("surveydate"));
                parcel.setUpi(rs.getString("upi"));
                parcel.setRegisteredOn(rs.getTimestamp("registeredon"));
                parcel.hasDispute(rs.getBoolean("hasdispute"));
                parcel.setTeamNo(rs.getByte("teamNo"));
                if (parcel.hasDispute()) {
                    parcel.setDisputes(getAllDisputes(parcel.getUpi(), parcel.getStage()));
                }
                if (parcel.getHolding() == 1) {
                    parcel.setIndividualHolders(getAllIndividualHolders(parcel.getUpi(), parcel.getStage()));
                    parcel.setPersonsWithInterest(getAllPersonsWithInterest(parcel.getUpi(), parcel.getStage()));
                } else {
                    parcel.setOrganaizationHolder(getOrganaizationHolder(parcel.getUpi(), parcel.getStage()));
                }
                returnValue.add(parcel);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<PublicDisplayCheckList> getPublicDisplayCheckList(String kebele) {
        ArrayList<PublicDisplayCheckList> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT a.UPI, stages." + CommonStorage.getCurrentLanguage() + " as status FROM (SELECT upi, max(stage) as stage FROM Parcel group by upi) a, stages Where stages.code = a.stage and UPI LIKE '" + kebele + "/%'");
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                PublicDisplayCheckList displayCheckList = new PublicDisplayCheckList();
                displayCheckList.setStage(rs.getString("status"));
                displayCheckList.setUPI(rs.getString("upi"));
                returnValue.add(displayCheckList);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<CertificatePrintingCheckList> getCertificatePrintingCheckList(String kebele) {
        ArrayList<CertificatePrintingCheckList> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT parcel.UPI, stages." + CommonStorage.getCurrentLanguage() + " as status FROM Parcel, stages Where parcel.status='active' AND stage=" + CommonStorage.getApprovedStage() + " and stages.code = Parcel.stage and UPI LIKE '" + kebele + "/%'");
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                CertificatePrintingCheckList checkList = new CertificatePrintingCheckList();
                checkList.setStage(rs.getString("status"));
                checkList.setUPI(rs.getString("upi"));
                returnValue.add(checkList);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean approve(Parcel parcel) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Parcel (upi,stage,registeredBy,registeredOn,"
                + "parcelId,certificateNo,holdingNo, otherEvidence,landUseType,"
                + "soilFertilityType,holdingType,acquisitionType,acquisitionYear,"
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute,teamNo) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, parcel.getUpi());
            stmnt.setByte(2, CommonStorage.getApprovedStage());
            stmnt.setLong(3, parcel.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setInt(5, parcel.getParcelId());
            stmnt.setString(6, parcel.getCertificateNumber());
            stmnt.setString(7, parcel.getHoldingNumber());
            stmnt.setByte(8, parcel.getOtherEvidence());
            stmnt.setByte(9, parcel.getCurrentLandUse());
            stmnt.setByte(10, parcel.getSoilFertility());
            stmnt.setByte(11, parcel.getHolding());
            stmnt.setByte(12, parcel.getMeansOfAcquisition());
            stmnt.setInt(13, parcel.getAcquisitionYear());
            stmnt.setString(14, parcel.getSurveyDate());
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            stmnt.setBoolean(18, parcel.hasDispute());
            stmnt.setByte(19, parcel.getTeamNo());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean approve(OrganizationHolder holder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO organizationholder(upi, stage, organizationname, "
                + "registeredby, registeredon, organizationtype) VALUES "
                + "( ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, holder.getUpi());
            stmnt.setByte(2, CommonStorage.getApprovedStage());
            stmnt.setString(3, holder.getName());
            stmnt.setLong(4, holder.getRegisteredby());
            stmnt.setTimestamp(5, Timestamp.from(Instant.now()));
            stmnt.setByte(6, holder.getOrganizationType());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean approve(IndividualHolder individualHolder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO individualholder( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth, familyrole, holderId, physicalImpairment, isOrphan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, individualHolder.getUpi());
            stmnt.setByte(2, CommonStorage.getApprovedStage());
            stmnt.setLong(3, individualHolder.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, individualHolder.getFirstName());
            stmnt.setString(6, individualHolder.getFathersName());
            stmnt.setString(7, individualHolder.getGrandFathersName());
            stmnt.setString(8, individualHolder.getSex());
            stmnt.setString(9, individualHolder.getDateOfBirth());
            stmnt.setByte(10, individualHolder.getFamilyRole());
            stmnt.setString(11, individualHolder.getId());
            stmnt.setBoolean(12, individualHolder.hasPhysicalImpairment());
            stmnt.setBoolean(13, individualHolder.isOrphan());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean approve(PersonWithInterest personWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO PersonWithInterest( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, personWithInterest.getUpi());
            stmnt.setByte(2, CommonStorage.getApprovedStage());
            stmnt.setLong(3, personWithInterest.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, personWithInterest.getFirstName());
            stmnt.setString(6, personWithInterest.getFathersName());
            stmnt.setString(7, personWithInterest.getGrandFathersName());
            stmnt.setString(8, personWithInterest.getSex());
            stmnt.setString(9, personWithInterest.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean approve(Guardian guardian) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Guardian( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, guardian.getUpi());
            stmnt.setByte(2, CommonStorage.getApprovedStage());
            stmnt.setLong(3, guardian.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, guardian.getFirstName());
            stmnt.setString(6, guardian.getFathersName());
            stmnt.setString(7, guardian.getGrandFathersName());
            stmnt.setString(8, guardian.getSex());
            stmnt.setString(9, guardian.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean approve(Dispute dispute) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO dispute(upi, stage, registeredby, registeredon,"
                + "firstname, fathersname, grandfathersname, sex, disputetype, "
                + "disputestatus,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, dispute.getUpi());
            stmnt.setByte(2, CommonStorage.getApprovedStage());
            stmnt.setLong(3, dispute.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, dispute.getFirstName());
            stmnt.setString(6, dispute.getFathersName());
            stmnt.setString(7, dispute.getGrandFathersName());
            stmnt.setString(8, dispute.getSex());
            stmnt.setByte(9, dispute.getDisputeType());
            stmnt.setByte(10, dispute.getDisputeStatus());
            stmnt.setString(11, "active");
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public ArrayList<Parcel> getALLParcelsInApproved(String kebele) {
        ArrayList<Parcel> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Parcel WHERE stage = " + CommonStorage.getApprovedStage() + " and status='active' and hasdispute='false' and UPI LIKE '" + kebele + "/%'");
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                Parcel parcel = new Parcel();
                parcel.setAcquisition(rs.getByte("acquisitiontype"));
                parcel.setAcquisitionYear(rs.getInt("acquisitionyear"));
                parcel.setCertificateNumber(rs.getString("certificateno"));
                parcel.setCurrentLandUse(rs.getByte("landusetype"));
                parcel.setEncumbrance(rs.getByte("encumbrancetype"));
                parcel.setHolding(rs.getByte("holdingtype"));
                parcel.setHoldingNumber(rs.getString("holdingno"));
                parcel.setMapSheetNo(rs.getString("mapsheetno"));
                parcel.setOtherEvidence(rs.getByte("otherevidence"));
                parcel.setParcelId(rs.getInt("parcelid"));
                parcel.setRegisteredBy(rs.getLong("registeredby"));
                parcel.setSoilFertility(rs.getByte("soilfertilitytype"));
                parcel.setStage(rs.getByte("stage"));
                parcel.setStatus(rs.getString("status"));
                parcel.setSurveyDate(rs.getString("surveydate"));
                parcel.setUpi(rs.getString("upi"));
                parcel.setRegisteredOn(rs.getTimestamp("registeredon"));
                parcel.hasDispute(rs.getBoolean("hasdispute"));
                parcel.setTeamNo(rs.getByte("teamNo"));
                if (parcel.hasDispute()) {
                    parcel.setDisputes(getAllDisputes(parcel.getUpi(), parcel.getStage()));
                }
                if (parcel.getHolding() == 1) {
                    parcel.setIndividualHolders(getAllIndividualHolders(parcel.getUpi(), parcel.getStage()));
                    parcel.setPersonsWithInterest(getAllPersonsWithInterest(parcel.getUpi(), parcel.getStage()));
                } else {
                    parcel.setOrganaizationHolder(getOrganaizationHolder(parcel.getUpi(), parcel.getStage()));
                }
                returnValue.add(parcel);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<DailyPerformance> getDailyReport(Date date) {
        ArrayList<DailyPerformance> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT *, (SELECT count(*) FROM Parcel WHERE Parcel.status='active' AND Parcel.registeredBy=u.userId AND Parcel.stage=1 AND Parcel.registeredOn::DATE = ? ) as firstEntry, (SELECT count(*) FROM Parcel WHERE Parcel.status='active' AND Parcel.registeredBy=u.userId AND Parcel.stage=2 AND Parcel.registeredOn::DATE = ? ) as secondEntry, (SELECT count(*) FROM Parcel WHERE Parcel.status='active' AND Parcel.registeredBy=u.userId AND Parcel.stage=3 AND Parcel.registeredOn::DATE = ? ) as supervisor FROM Users u WHERE u.status='active' AND (u.role = 'FEO' OR u.role = 'SEO' OR u.role = 'SUPERVISOR' )");
            stmnt.setDate(1, date);
            stmnt.setDate(2, date);
            stmnt.setDate(3, date);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                DailyPerformance performance = new DailyPerformance();
                performance.setUser(rs.getString("firstName") + " " + rs.getString("fathersName") + " " + rs.getString("grandfathersName"));
                performance.setDate(date);
                performance.setFirstEntry(rs.getInt("firstEntry"));
                performance.setFirstEntry(rs.getInt("firstEntry"));
                performance.setSupervisor(rs.getInt("supervisor"));
                returnValue.add(performance);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public void refreshView(String viewName) {
        try (Connection connection = CommonStorage.getConnection()) {
            String query = "REFRESH MATERIALIZED VIEW " + viewName;
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
    }

    public ArrayList<ParcelStatusDetailDTO> getParcelsStatus(Date from, Date to) {
        ArrayList<ParcelStatusDetailDTO> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT first_stage.upi, first_stage.registeredby as firstEntery, second_stage.registeredby as secondEntery, correction_stage.correction as inCorrection, committed_stage.committed as committed FROM ( ( (SELECT upi, firstname || ' ' || fathersname as registeredby FROM parcel , users WHERE parcel.status = 'active' AND parcel.stage = 1 AND  registeredby=userid AND registeredon::DATE between ? AND ? ORDER BY upi ) first_stage LEFT JOIN (SELECT upi, firstname || ' ' || fathersname as registeredby FROM parcel , users WHERE parcel.status = 'active' AND parcel.stage = 2 AND  registeredby=userid AND registeredon::DATE between ? AND ? ) second_stage ON (first_stage.upi=second_stage.upi) ) LEFT JOIN (SELECT upi, true as correction FROM parcel WHERE status = 'active' AND parcel.stage = 3 AND registeredon::DATE between ? AND ? ) correction_stage ON (first_stage.upi=correction_stage.upi) ) LEFT JOIN (SELECT upi, true as committed FROM parcel WHERE status = 'active' AND parcel.stage = 4 AND registeredon::DATE between ? AND ? ) committed_stage ON (first_stage.upi=committed_stage.upi) ORDER BY upi";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            stmnt.setDate(3, from);
            stmnt.setDate(4, to);
            stmnt.setDate(5, from);
            stmnt.setDate(6, to);
            stmnt.setDate(7, from);
            stmnt.setDate(8, to);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                ParcelStatusDetailDTO parcel = new ParcelStatusDetailDTO();
                parcel.setUpi(rs.getString("upi"));
                parcel.setFirstEntry(rs.getString("firstEntery"));
                parcel.setSecondEntry(rs.getString("secondEntery"));
                parcel.isCorrection(rs.getBoolean("inCorrection"));
                parcel.isCommitted(rs.getBoolean("committed"));
                returnValue.add(parcel);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<ParcelStatusDetailDTO> getParcelsStatus(long kebele) {
        ArrayList<ParcelStatusDetailDTO> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT first_stage.upi, first_stage.registeredby as firstEntery, second_stage.registeredby as secondEntery, correction_stage.correction as inCorrection, committed_stage.committed as committed FROM ( ( (SELECT upi, firstname || ' ' || fathersname as registeredby FROM parcel , users WHERE parcel.status = 'active' AND parcel.stage = 1 AND  registeredby=userid AND UPI LIKE '" + kebele + "/%' ORDER BY upi ) first_stage LEFT JOIN (SELECT upi, firstname || ' ' || fathersname as registeredby FROM parcel , users WHERE parcel.status = 'active' AND parcel.stage = 2 AND  registeredby=userid AND UPI LIKE '" + kebele + "/%' ) second_stage ON (first_stage.upi=second_stage.upi) ) LEFT JOIN (SELECT upi, true as correction FROM parcel WHERE status = 'active' AND parcel.stage = 3 AND UPI LIKE '" + kebele + "/%' ) correction_stage ON (first_stage.upi=correction_stage.upi) ) LEFT JOIN (SELECT upi, true as committed FROM parcel WHERE status = 'active' AND parcel.stage = 4 AND UPI LIKE '" + kebele + "/%' ) committed_stage ON (first_stage.upi=committed_stage.upi) ORDER BY upi";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                ParcelStatusDetailDTO parcel = new ParcelStatusDetailDTO();
                parcel.setUpi(rs.getString("upi"));
                parcel.setFirstEntry(rs.getString("firstEntery"));
                parcel.setSecondEntry(rs.getString("secondEntery"));
                parcel.isCorrection(rs.getBoolean("inCorrection"));
                parcel.isCommitted(rs.getBoolean("committed"));
                returnValue.add(parcel);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<DEOPeformanceDetailDTO> getDEOPerformance(Date from, Date to) {
        ArrayList<DEOPeformanceDetailDTO> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT firstname || ' ' || fathersname || ' ' || grandfathersname as name, (SELECT count(*) FROM Parcel WHERE status='active' AND registeredBy=u.userId AND stage=1  AND registeredon::DATE between ? AND ? ) as firstEntry, (SELECT count(*) FROM Parcel WHERE status='active' AND registeredBy=u.userId AND stage=2  AND registeredon::DATE between ? AND ? ) as secondEntry, (SELECT count(*) FROM Parcel WHERE status='active' AND registeredBy=u.userId AND stage=3  AND registeredon::DATE between ? AND ? ) as supervisor FROM Users u WHERE u.status='active' AND (u.role = 'FEO' OR u.role = 'SEO' OR u.role = 'SUPERVISOR' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            stmnt.setDate(3, from);
            stmnt.setDate(4, to);
            stmnt.setDate(5, from);
            stmnt.setDate(6, to);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                DEOPeformanceDetailDTO deo = new DEOPeformanceDetailDTO();
                deo.setName(rs.getString("name"));
                deo.setFirstEntry(rs.getLong("firstEntry"));
                deo.setSecondEntry(rs.getLong("secondEntry"));
                deo.setCorrection(rs.getLong("supervisor"));
                returnValue.add(deo);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<DEOPeformanceDetailDTO> getDEOPerformance(long kebele) {
        ArrayList<DEOPeformanceDetailDTO> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT firstname || ' ' || fathersname || ' ' || grandfathersname as name, (SELECT count(*) FROM Parcel WHERE status='active' AND registeredBy=u.userId AND stage=1  AND UPI LIKE '" + kebele + "/%' ) as firstEntry, (SELECT count(*) FROM Parcel WHERE status='active' AND registeredBy=u.userId AND stage=2  AND UPI LIKE '" + kebele + "/%' ) as secondEntry, (SELECT count(*) FROM Parcel WHERE status='active' AND registeredBy=u.userId AND stage=3  AND UPI LIKE '" + kebele + "/%' ) as supervisor FROM Users u WHERE u.status='active' AND (u.role = 'FEO' OR u.role = 'SEO' OR u.role = 'SUPERVISOR' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                DEOPeformanceDetailDTO deo = new DEOPeformanceDetailDTO();
                deo.setName(rs.getString("name"));
                deo.setFirstEntry(rs.getLong("firstEntry"));
                deo.setSecondEntry(rs.getLong("secondEntry"));
                deo.setCorrection(rs.getLong("supervisor"));
                returnValue.add(deo);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfCheckedParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND status='actvie' AND stage = " + CommonStorage.getCheckedStage();
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfNotCheckedParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE status='active' AND UPI LIKE '" + kebele + "/%' AND stage = " + CommonStorage.getConfirmedStage() + " UPI NOT IN (SELECT upi FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage = " + CommonStorage.getCheckedStage() + ")";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfApprovedParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND status='actvie' AND stage = " + CommonStorage.getApprovedStage();
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfNotApprovedParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE status='active' AND UPI LIKE '" + kebele + "/%' AND stage = " + CommonStorage.getCheckedStage() + " UPI NOT IN (SELECT upi FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage = " + CommonStorage.getApprovedStage() + ")";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfPrintedParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND status='actvie' AND stage = " + CommonStorage.getPrintedStage();
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfNotPrintedParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE status='active' AND UPI LIKE '" + kebele + "/%' AND stage = " + CommonStorage.getApprovedStage() + " UPI NOT IN (SELECT upi FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage = " + CommonStorage.getPrintedStage() + ")";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public long getCountOfAllParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE status='active' AND UPI LIKE '" + kebele + "/%' AND stage > " + CommonStorage.getCommitedStage();
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<ParcelApprovalDetailDTO> getParcelsForApproval(long kebele) {
        ArrayList<ParcelApprovalDetailDTO> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT distinct upi, stage, teamno, area, parcelid, certificateno, holdingno, hasdispute, otherevidence, landusetype, soilfertilitytype, holdingtype, acquisitiontype, acquisitionyear, surveydate, mapsheetno, encumbrancetype, status FROM parcel WHERE stage=" + CommonStorage.getConfirmedStage() + " and status='active' AND UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt2 = connection.prepareStatement(query);
            ResultSet parcels = stmnt2.executeQuery();
            while (parcels.next()) {
                boolean hasMissingValue = false;
                ParcelApprovalDetailDTO parcel = new ParcelApprovalDetailDTO();
                parcel.setUpi(parcels.getString("upi"));
                parcel.setArea(parcels.getDouble("area"));
                parcel.hasDispute(parcels.getBoolean("hasdispute"));

                if (parcels.getLong("otherevidence") == 5) {
                    hasMissingValue = true;
                }
                if (parcels.getLong("landusetype") == 13) {
                    hasMissingValue = true;
                }
                if (parcels.getLong("soilfertilitytype") == 5) {
                    hasMissingValue = true;
                }
                if (parcels.getLong("acquisitiontype") == 9) {
                    hasMissingValue = true;
                }
                if (parcels.getLong("acquisitionyear") < 1000) {
                    hasMissingValue = true;
                }
                if (parcels.getLong("encumbrancetype") == 7) {
                    hasMissingValue = true;
                }

                query = "SELECT * FROM individualholder WHERE stage=" + CommonStorage.getConfirmedStage() + " and status='active' AND UPI = ?";
                PreparedStatement stmnt3 = connection.prepareStatement(query);
                stmnt3.setString(1, parcel.getUpi());
                ResultSet holders = stmnt3.executeQuery();

                while (holders.next()) {
                    if (holders.getString("firstname") == null || holders.getString("firstname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if (holders.getString("fathersname") == null || holders.getString("fathersname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if (holders.getString("grandfathersname") == null || holders.getString("grandfathersname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if ("n".equalsIgnoreCase(holders.getString("sex"))) {
                        hasMissingValue = true;
                    }
                }

                query = "SELECT * FROM organizationholder WHERE stage=" + CommonStorage.getConfirmedStage() + " and status='active' AND UPI = ?";
                stmnt3 = connection.prepareStatement(query);
                stmnt3.setString(1, parcel.getUpi());
                holders = stmnt3.executeQuery();

                while (holders.next()) {
                    if (holders.getString("organizationname") == null || holders.getString("organizationname").isEmpty()) {
                        hasMissingValue = true;
                    }
                }

                query = "SELECT * FROM guardian WHERE stage=" + CommonStorage.getConfirmedStage() + " and status='active' AND UPI = ?";
                PreparedStatement stmnt4 = connection.prepareStatement(query);
                stmnt4.setString(1, parcel.getUpi());
                ResultSet guardians = stmnt4.executeQuery();

                while (guardians.next()) {
                    if (guardians.getString("firstname") == null || guardians.getString("firstname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if (guardians.getString("fathersname") == null || guardians.getString("fathersname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if (guardians.getString("grandfathersname") == null || guardians.getString("grandfathersname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if ("n".equalsIgnoreCase(guardians.getString("sex"))) {
                        hasMissingValue = true;
                    }
                }

                query = "SELECT * FROM personwithinterest WHERE stage=" + CommonStorage.getConfirmedStage() + " and status='active' AND UPI = ?";
                PreparedStatement stmnt5 = connection.prepareStatement(query);
                stmnt5.setString(1, parcel.getUpi());
                ResultSet personswithinterest = stmnt5.executeQuery();

                while (personswithinterest.next()) {
                    if (personswithinterest.getString("firstname") == null || personswithinterest.getString("firstname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if (personswithinterest.getString("fathersname") == null || personswithinterest.getString("fathersname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if (personswithinterest.getString("grandfathersname") == null || personswithinterest.getString("grandfathersname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if ("n".equalsIgnoreCase(personswithinterest.getString("sex"))) {
                        hasMissingValue = true;
                    }
                }
                query = "SELECT * FROM dispute WHERE stage=" + CommonStorage.getConfirmedStage() + " and status='active' AND UPI = ?";
                PreparedStatement stmnt6 = connection.prepareStatement(query);
                stmnt6.setString(1, parcel.getUpi());
                ResultSet disputes = stmnt6.executeQuery();

                while (disputes.next()) {
                    if (disputes.getString("firstname") == null || disputes.getString("firstname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if (disputes.getString("fathersname") == null || disputes.getString("fathersname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if (disputes.getString("grandfathersname") == null || disputes.getString("grandfathersname").isEmpty()) {
                        hasMissingValue = true;
                    }
                    if ("n".equalsIgnoreCase(disputes.getString("sex"))) {
                        hasMissingValue = true;
                    }
                    if (disputes.getLong("disputetype") == 5) {
                        hasMissingValue = true;
                    }
                    if (disputes.getLong("disputestatus") == 8) {
                        hasMissingValue = true;
                    }

                }
                parcel.hasMissingValue(hasMissingValue);
                returnValue.add(parcel);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<HolderListDTO> getIndividualHolderList(long kebele) {
        ArrayList<HolderListDTO> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT distinct holderid, firstname, fathersname, grandfathersname FROM individualholder WHERE stage=" + CommonStorage.getCommitedStage() + " and status='active'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet holders = stmnt.executeQuery();
            while (holders.next()) {
                HolderListDTO holder = new HolderListDTO();
                holder.setFathersName(holders.getString("fathersname"));
                holder.setFirstName(holders.getString("firstname"));
                holder.setGrandFathersName(holders.getString("grandfathersname"));
                holder.setPhotoId(holders.getString("holderid"));

                returnValue.add(holder);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

}
