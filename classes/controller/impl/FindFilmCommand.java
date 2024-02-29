package project.cinema.classes.controller.impl;


import project.cinema.classes.controller.Command;
import project.cinema.classes.logic.CinemaLogic;
import project.cinema.classes.logic.LogicException;
import project.cinema.classes.logic.LogicProvider;

public class FindFilmCommand implements Command {
    private final LogicProvider logicProvider = LogicProvider.getInstance();
    private final CinemaLogic logic = logicProvider.getCinemaLogic();

    @Override
    public String execute(String request) {
        String response = null;
        String[] params;

        params = request.split("\n");
        String str = params[1];

        try {
            response = logic.find(str).toString();
        } catch (LogicException e) {
            // log
            response = "Error";
        }

        return response;
    }
}
