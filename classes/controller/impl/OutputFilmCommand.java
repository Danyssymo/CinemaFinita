package project.cinema.classes.controller.impl;

import project.cinema.classes.entity.Film;
import project.cinema.classes.logic.CinemaLogic;
import project.cinema.classes.logic.LogicException;
import project.cinema.classes.logic.LogicProvider;
import project.cinema.classes.controller.Command;

import java.util.concurrent.BlockingQueue;

public class OutputFilmCommand implements Command {

    private final LogicProvider logicProvider = LogicProvider.getInstance();
    private final CinemaLogic logic = logicProvider.getCinemaLogic();
    @Override
    public String execute(String request) {
        String response = null;

        BlockingQueue<Film> myFilms = null;
        try {
            myFilms = logic.allFilms();
            if (myFilms == null || myFilms.size() <= 0) {
                response = "There are no records to output.";
            } else {
                response = myFilms.toString();
            }
        } catch (LogicException e) {
            // log
            response = "Output Error";
        }
        return response;
    }
}
