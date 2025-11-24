package com.CampusHire.Model;

import jakarta.persistence.*;

@Embeddable
public class StudentTicket extends Ticket {

    public StudentTicket() {
    }

    public StudentTicket(String remote, int experience, String location, String jobType, String industry) {
        super(remote, experience, location, jobType, industry);
    }

    public StudentTicket(TicketJson ticketJson) {
        super(ticketJson);
    }

}
