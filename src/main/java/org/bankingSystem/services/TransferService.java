package org.bankingSystem.services;

import org.bankingSystem.DatabaseConnector;
import org.bankingSystem.model.Transfer;
import org.bankingSystem.queries.TransferSqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransferService extends CommonService {

    public List<Transfer> getTransfers(String query) throws SQLException {
        List<Transfer> transfers = new ArrayList<>();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConnector.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                Transfer transfer = new Transfer(rs);
                transfers.add(transfer);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve transfers" + e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closeStatements(st);
        }
        return transfers;
    }

    public List<Transfer> getAllTransfers() throws SQLException {
        return getTransfers(TransferSqlQueries.GET_TRANSFERS);
    }

    public List<Transfer> getCurrentMonthsTransfers() throws SQLException {
        return getTransfers(TransferSqlQueries.GET_CURRENT_MONTHS_TRANSFERS);
    }

    public Transfer getTransferById(UUID transferId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(TransferSqlQueries.GET_TRANSFER_BY_ID);
            ps.setString(1, transferId.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Transfer(rs);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve transfer" + e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closePreparedStatement(ps);
        }
        return null;
    }

    public List<Transfer> getTransfersFilteredByMonth(Integer month, Integer year) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Transfer> transfers = new ArrayList<>();
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(TransferSqlQueries.GET_TRANSFERS_FILTERED_BY_MONTH);
            ps.setInt(1, month);
            ps.setInt(2, year);
            rs = ps.executeQuery();
            while (rs.next()) {
                Transfer transfer = new Transfer(rs);
                transfers.add(transfer);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get transfers" +
                    e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closeStatements(ps);
        }
        return transfers;
    }

    public List<Transfer> getTransfersByOneParty(UUID id, String query) throws SQLException {
        List<Transfer> transfers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConnector.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, id.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                Transfer transfer = new Transfer(rs);
                transfers.add(transfer);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get transfers" + e.getMessage());
        } finally {
            closeConnection(connection);
            closeResultSet(rs);
            closePreparedStatement(ps);
        }
        return transfers;
    }

    public List<Transfer> getTransfersBySenderId(UUID senderId) throws SQLException {
        return getTransfersByOneParty(senderId, TransferSqlQueries.GET_TRANSFER_BY_SENDER_ID);
    }

    public List<Transfer> getTransfersByReceiverId(UUID receiverId) throws SQLException {
        return getTransfersByOneParty(receiverId, TransferSqlQueries.GET_TRANSFER_BY_RECEIVER_ID);
    }

    public Transfer createTransfer(Transfer transfer) throws SQLException {
        Connection connection = null;
        PreparedStatement psCheckBalance = null;
        PreparedStatement psInsert = null;
        PreparedStatement psUpdateSender = null;
        PreparedStatement psUpdateReceiver = null;
        ResultSet rs = null;
        try {
            connection = DatabaseConnector.getConnection();
            connection.setAutoCommit(false);

            psCheckBalance = connection.prepareStatement(TransferSqlQueries.CHECK_BALANCE);
            psCheckBalance.setString(1, transfer.getSenderAccountId().toString());
            rs = psCheckBalance.executeQuery();
            if (rs.next()) {
                double senderBalance = rs.getDouble("account_current_balance");
                if (senderBalance < transfer.getTransferAmount()) {
                    throw new SQLException("Insufficient funds for transfer");
                }
            } else {
                throw new SQLException("Sender account not found");
            }

            psInsert = connection.prepareStatement(TransferSqlQueries.CREATE_TRANSFER);
            UUID uuid = UUID.randomUUID();
            psInsert.setString(1, uuid.toString());
            psInsert.setString(2, transfer.getSenderAccountId().toString());
            psInsert.setString(3, transfer.getReceiverAccountId().toString());
            psInsert.setDouble(4, transfer.getTransferAmount());
            psInsert.setString(5, transfer.getTransferDate());
            psInsert.executeUpdate();

            psUpdateSender = connection.prepareStatement(TransferSqlQueries.UPDATE_SENDER_BALANCE);
            psUpdateSender.setDouble(1, transfer.getTransferAmount());
            psUpdateSender.setString(2, transfer.getSenderAccountId().toString());
            psUpdateSender.executeUpdate();

            psUpdateReceiver = connection.prepareStatement(TransferSqlQueries.UPDATE_RECEIVER_BALANCE);
            psUpdateReceiver.setDouble(1, transfer.getTransferAmount());
            psUpdateReceiver.setString(2, transfer.getReceiverAccountId().toString());
            psUpdateReceiver.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw new SQLException("Error while creating a transfer" + e.getMessage());
        } finally {
            closeConnection(connection);
            closePreparedStatement(psCheckBalance, psInsert, psUpdateSender, psUpdateReceiver);
        }
        return transfer;
    }
}
