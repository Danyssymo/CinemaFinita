package project.cinema.classes.controller.impl;

import project.cinema.classes.controller.Command;
import project.cinema.classes.entity.Ticket;
import project.cinema.classes.logic.CinemaLogic;
import project.cinema.classes.logic.LogicException;
import project.cinema.classes.logic.LogicProvider;

public class SellTicketCommand implements Command {
    private final LogicProvider logicProvider = LogicProvider.getInstance();
    private final CinemaLogic logic = logicProvider.getCinemaLogic();

    @Override
    public String execute(String request) {
        String response = null;
        String[] params;

        params = request.split("\n");

        int filmId = Integer.parseInt(params[1].split("=")[1]);
        String hallSize = params[2].split("=")[1];
        int seatRow = Integer.parseInt(params[3].split("=")[1]);
        int seatNum = Integer.parseInt(params[4].split("=")[1]);

        try {
            Ticket ticket = (logic.createTicket(hallSize, filmId, seatRow, seatNum));
            StringBuffer sb = new StringBuffer();
            sb.append(ticket.getFilm()).append("\n");
            sb.append(ticket.getHall().getSize()).append(" ");
            sb.append(ticket.getSeat().getRow()).append(" ");
            sb.append(ticket.getSeat().getNumber()).append("\n");
            sb.append(ticket.getPrice());
            response = String.valueOf((sb));
        } catch (LogicException e) {
            // log
            response = "Ticket sale error";
        }

        return response;
    }
}
