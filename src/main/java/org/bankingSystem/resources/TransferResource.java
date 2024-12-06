package org.bankingSystem.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bankingSystem.model.Transfer;
import org.bankingSystem.services.TransferService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Path("transfers")
public class TransferResource extends AbstractResource {

    private final TransferService TRANSFER_SERVICE = new TransferService();

    public Response getTransfers(List<Transfer> transfersAsArgument) {
        List<Transfer> transfers = transfersAsArgument;
        if (transfers != null) {
            return transfersToJson(transfers, 200);
        } else {
            return notFound();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTransfers() throws SQLException {
        return getTransfers(TRANSFER_SERVICE.getAllTransfers());
    }

    @Path("/current-month")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentMonthsTransfers() throws SQLException {
        return getTransfers(TRANSFER_SERVICE.getCurrentMonthsTransfers());
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransferById(@PathParam("id") UUID transferId) throws SQLException {
        Transfer transfer = TRANSFER_SERVICE.getTransferById(transferId);
        if (transfer != null) {
            return transferToJson(transfer, 200);
        } else {
            return notFound();
        }
    }

    @Path("/{month}/{year}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransfersFilteredByMonth(@PathParam("month") Integer month, @PathParam("year") Integer year) throws SQLException {
        List<Transfer> transfers = TRANSFER_SERVICE.getTransfersFilteredByMonth(month, year);
        if (!transfers.isEmpty()) {
            return transfersToJson(transfers, 200);
        } else {
            return notFound();
        }
    }

    @Path("/sender")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransfersBySender(@QueryParam("sender") UUID senderId) throws SQLException {
        List<Transfer> transfers = TRANSFER_SERVICE.getTransfersBySenderId(senderId);
        if (!transfers.isEmpty()) {
            return transfersToJson(transfers, 200);
        } else {
            return notFound();
        }
    }

    @Path("/receiver")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransfersByReceiver(@QueryParam("receiver") UUID receiverId) throws SQLException {
        List<Transfer> transfers = TRANSFER_SERVICE.getTransfersByReceiverId(receiverId);
        if (!transfers.isEmpty()) {
            return transfersToJson(transfers, 200);
        } else {
            return notFound();
        }
    }

    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTransfer(String payload) throws SQLException {
        Transfer transfer = transferFromJson(payload);
        Transfer createdTransfer = TRANSFER_SERVICE.createTransfer(transfer);
        if (createdTransfer != null) {
            return transferToJson(createdTransfer, 200);
        } else {
            return notFound();
        }
    }
}
