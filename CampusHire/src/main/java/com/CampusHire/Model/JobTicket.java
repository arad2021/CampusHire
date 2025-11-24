package com.CampusHire.Model;

import jakarta.persistence.*;

@Embeddable
public class JobTicket extends Ticket {

    public JobTicket(String remote, int experience, String location, String jobType, String industry) {
        super(remote, experience, location, jobType, industry);
    }

    public JobTicket() {
    }

    public JobTicket(TicketJson ticketJson) {
        super(ticketJson);
    }

}
