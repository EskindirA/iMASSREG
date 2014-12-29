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
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?)";
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
            stmnt.setBoolean(18, parcel.hasDispute());
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

    public boolean save(IndividualHolder holder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO individualholder( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth, familyrole, holderId, physicalImpairment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
            stmnt.setDate(9, Date.valueOf(holder.getDateOfBirth()));
            stmnt.setByte(10, holder.getFamilyRole());
            stmnt.setString(11, holder.getId());
            stmnt.setBoolean(12, holder.hasPhysicalImpairment());
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

    public boolean save(PersonWithInterest personWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO PersonWithInterest( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth,physicalImpairment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
            stmnt.setDate(9, Date.valueOf(personWithInterest.getDateOfBirth()));
            stmnt.setBoolean(10, personWithInterest.hasPhysicalImpairment());
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
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
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
                returnValue.setSurveyDate(rs.getDate("surveydate").toString());
                returnValue.setUpi(rs.getString("upi"));
                returnValue.setRegisteredOn(rs.getTimestamp("registeredon"));
                returnValue.hasDispute(rs.getBoolean("hasdispute"));
                if (returnValue.hasDispute()) {
                    returnValue.setDisputes(getAllDisputes(upi, stage));
                }
                if (returnValue.getHolding() == 1) {
                    returnValue.setIndividualHolders(getAllIndividualHolders(upi, stage));
                } else {
                    returnValue.setOrganaizationHolder(getOrganaizationHolder(upi, stage));
                }
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
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
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
                }
            }
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
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
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
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
                Date d = rs.getDate("dateofbirth");
                if (d != null) {
                    ih.setDateOfBirth(d.toString());
                }
                ih.setFamilyRole(rs.getByte("familyrole"));
                ih.setFirstName(rs.getString("firstname"));
                ih.setFathersName(rs.getString("fathersname"));
                ih.setGrandFathersName(rs.getString("grandfathersname"));
                ih.setId(rs.getString("holderId"));
                ih.setRegisteredBy(rs.getLong("registeredby"));
                ih.setRegisteredOn(rs.getTimestamp("registeredon"));
                ih.setSex(rs.getString("sex"));
                ih.hasPhysicalImpairment(rs.getBoolean("physicalimpairment"));
                ih.setStage(rs.getByte("stage"));
                ih.setUpi(rs.getString("upi"));
                returnValue.add(ih);
            }
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
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
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
        }
        return returnValue;
    }

    public boolean deleteDispute(Timestamp registeredOn, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE dispute SET status='deleted' WHERE upi=? and stage=? and registeredOn=?");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.setTimestamp(3, registeredOn);
            stmnt.executeUpdate();
        } catch (Exception ex) {
            returnValue = false;
            CommonStorage.getLogger().logError(ex.toString());
        }
        return returnValue;
    }

    public boolean deleteIndividualHolder(String holderId, Timestamp registeredOn, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE IndividualHolder SET status='deleted' WHERE upi=? and stage=? and registeredOn=? and holderId=?");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.setTimestamp(3, registeredOn);
            stmnt.setString(4, holderId);
            stmnt.executeUpdate();
        } catch (Exception ex) {
            returnValue = false;
            CommonStorage.getLogger().logError(ex.toString());
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
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
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
                + "surveydate=?, mapsheetno=?, encumbrancetype=?, hasdispute=?"
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
            stmnt.setDate(9, Date.valueOf(newParcel.getSurveyDate()));
            stmnt.setString(10, newParcel.getMapSheetNo());
            stmnt.setByte(11, newParcel.getEncumbrance());
            stmnt.setBoolean(12, newParcel.hasDispute());
            stmnt.setString(13, oldParcel.getUpi());
            stmnt.setByte(14, oldParcel.getStage());
            stmnt.setTimestamp(15, oldParcel.getRegisteredOn());
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

    public boolean update(IndividualHolder oldIndividualHolder,
            IndividualHolder newIndividualHolder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE individualholder SET firstname=?, "
                + "fathersname=?, grandfathersname=?, sex=?, dateofbirth=?, "
                + "familyrole=?, holderId=?, physicalimpairment=? WHERE upi=? and stage = ? and registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newIndividualHolder.getFirstName());
            stmnt.setString(2, newIndividualHolder.getFathersName());
            stmnt.setString(3, newIndividualHolder.getGrandFathersName());
            stmnt.setString(4, newIndividualHolder.getSex());
            stmnt.setDate(5, Date.valueOf(newIndividualHolder.getDateOfBirth()));
            stmnt.setByte(6, newIndividualHolder.getFamilyRole());
            stmnt.setString(7, newIndividualHolder.getId());
            stmnt.setBoolean(8, newIndividualHolder.hasPhysicalImpairment());
            stmnt.setString(9, oldIndividualHolder.getUpi());
            stmnt.setByte(10, oldIndividualHolder.getStage());
            stmnt.setTimestamp(11, oldIndividualHolder.getRegisteredOn());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
        } catch (SQLException | NumberFormatException ex) {
            CommonStorage.getLogger().logError(ex.toString());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean update(PersonWithInterest oldPersonWithInterest,
            PersonWithInterest newPersonWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE personwithinterest SET firstname=?, "
                + "fathersname=?, grandfathersname=?, sex=?, dateofbirth=?, "
                + "physicalImpairment = ? WHERE upi=? and stage = ? and registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newPersonWithInterest.getFirstName());
            stmnt.setString(2, newPersonWithInterest.getFathersName());
            stmnt.setString(3, newPersonWithInterest.getGrandFathersName());
            stmnt.setString(4, newPersonWithInterest.getSex());
            stmnt.setDate(5, Date.valueOf(newPersonWithInterest.getDateOfBirth()));
            stmnt.setBoolean(6, newPersonWithInterest.hasPhysicalImpairment());
            stmnt.setString(7, oldPersonWithInterest.getUpi());
            stmnt.setByte(8, oldPersonWithInterest.getStage());
            stmnt.setTimestamp(9, oldPersonWithInterest.getRegisteredOn());
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
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean deleteOrganizationHolder(String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE OrganizationHolder SET status='deleted' WHERE upi=? and stage=? ");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.executeUpdate();
        } catch (Exception ex) {
            returnValue = false;
            CommonStorage.getLogger().logError(ex.toString());
        }
        return returnValue;
    }

    public boolean deleteParcel(Timestamp registeredOn, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE Parcel SET status='deleted' WHERE upi=? and stage=? and registeredOn=? ");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.setTimestamp(3, registeredOn);
            stmnt.executeUpdate();
        } catch (Exception ex) {
            returnValue = false;
            CommonStorage.getLogger().logError(ex.toString());
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
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
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
                Date d = rs.getDate("dateofbirth");
                if (d != null) {
                    personWithInterest.setDateOfBirth(d.toString());
                }
                personWithInterest.setFirstName(rs.getString("firstname"));
                personWithInterest.setFathersName(rs.getString("fathersname"));
                personWithInterest.setGrandFathersName(rs.getString("grandfathersname"));
                personWithInterest.setRegisteredBy(rs.getLong("registeredby"));
                personWithInterest.setRegisteredOn(rs.getTimestamp("registeredon"));
                personWithInterest.setSex(rs.getString("sex"));
                personWithInterest.hasPhysicalImpairment(rs.getBoolean("physicalImpairment"));
                personWithInterest.setStage(rs.getByte("stage"));
                personWithInterest.setUpi(rs.getString("upi"));
                returnValue.add(personWithInterest);
            }
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
        }

        return returnValue;
    }

    public boolean deletePersonWithInterest(Timestamp registeredOn, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE personwithinterest SET status='deleted' WHERE upi=? and stage=? and registeredOn=? ");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.setTimestamp(3, registeredOn);
            stmnt.executeUpdate();
        } catch (Exception ex) {
            returnValue = false;
            CommonStorage.getLogger().logError(ex.toString());
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
            stmnt.setInt(1, CommonStorage.getCurrentWoredaId());
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                returnValue.add(new Option(rs.getInt("code") + "", rs.getString(CommonStorage.getCurrentLanguage())));
            }
        } catch (Exception ex) {
            CommonStorage.getLogger().logError(ex.toString());
        }
        return returnValue.toArray(new Option[0]);
    }

}
