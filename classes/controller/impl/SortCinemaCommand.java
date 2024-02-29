package project.cinema.classes.controller.impl;

import project.cinema.classes.controller.Command;
import project.cinema.classes.logic.CinemaLogic;
import project.cinema.classes.logic.LogicException;
import project.cinema.classes.logic.LogicProvider;


public class SortCinemaCommand implements Command {

    private final LogicProvider logicProvider = LogicProvider.getInstance();
    private final CinemaLogic logic = logicProvider.getCinemaLogic();
    @Override
    public String execute(String request) {
        String response = null;
        String[] params;
        params = request.split("\n");
        String str = (params[1].toUpperCase());
        if (str.equals("NAME") || str.equals("DURATION") || str.equals("PRICE") || str.equals("GENRE") || str.equals("DESCRIPTION") || str.equals("DATA")){
            try {
                response = logic.sort(str).toString();;
            } catch (LogicException e) {
                response = "sorted is failed";
            }
        } else {
            response = "Enter the correct sorting";
        }
        return response;
    }
}
