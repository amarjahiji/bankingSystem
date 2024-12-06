package org.bankingSystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Transfer {
    UUID transferId;
    UUID senderAccountId;
    UUID receiverAccountId;
    Double transfer_amount;
    String transfer_date;

    public Transfer(ResultSet rs) throws SQLException {
        transferId = UUID.fromString(rs.getString("transfer_id"));
        senderAccountId = UUID.fromString(rs.getString("sender_account_id"));
        receiverAccountId = UUID.fromString(rs.getString("receiver_account_id"));
        transfer_amount = rs.getDouble("transfer_amount");
        transfer_date = rs.getString("transfer_date");
    }
    public UUID getTransferId() {
        return transferId;
    }
    public UUID getSenderAccountId() {
        return senderAccountId;
    }
    public UUID getReceiverAccountId() {
        return receiverAccountId;
    }
    public Double getTransferAmount() {
        return transfer_amount;
    }
    public String getTransferDate() {
        return transfer_date;
    }
    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }
    public void setSenderAccountId(UUID senderAccountId) {
        this.senderAccountId = senderAccountId;
    }
    public void setReceiverAccountId(UUID receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }
    public void setTransferAmount(Double transferAmount) {
        this.transfer_amount = transferAmount;
    }
    public void setTransferDate(String transferDate) {
        this.transfer_date = transferDate;
    }

}
