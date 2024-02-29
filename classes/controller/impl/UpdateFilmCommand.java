package project.cinema.classes.controller.impl;


import project.cinema.classes.controller.Command;
import project.cinema.classes.entity.Film;
import project.cinema.classes.logic.CinemaLogic;
import project.cinema.classes.logic.LogicException;
import project.cinema.classes.logic.LogicProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateFilmCommand implements Command {
    private final LogicProvider logicProvider = LogicProvider.getInstance();
    private final CinemaLogic logic = logicProvider.getCinemaLogic();

    @Override
    public String execute(String request) {
        String response = null;
        String[] params;
        Film newFilm;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        params = request.split("\n");
        newFilm = new Film();

        newFilm.setId(Integer.parseInt(params[1].split("=")[1]));
        newFilm.setFilmName(params[2].split("=")[1]);
        newFilm.setFilmDuration(Integer.parseInt(params[3].split("=")[1]));
        newFilm.setTicketPrice(Integer.parseInt(params[4].split("=")[1]));
        newFilm.setFilmGenre(params[5].split("=")[1]);
        newFilm.setFilmDescription(params[6].split("=")[1]);
        try {
        if (params.length == 8) {
            String stringDate = (params[7].split("=")[1]);
            Date myDate = formatter.parse(stringDate);
            newFilm.setStartOfTheFilm(myDate);
            }
            logic.update(newFilm);
            response = "The record was updated successfully.";
        } catch (LogicException | ParseException e) {
            //log
            response = "Update Error";
        }

        return response;
    }
}
