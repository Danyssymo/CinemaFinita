package project.cinema.classes.controller.impl;

import project.cinema.classes.controller.Command;
import project.cinema.classes.entity.Hall;
import project.cinema.classes.logic.CinemaLogic;
import project.cinema.classes.logic.LogicException;
import project.cinema.classes.logic.LogicProvider;


import java.util.concurrent.BlockingQueue;

public class ShowAllHallsCommand implements Command{
    private final LogicProvider logicProvider = LogicProvider.getInstance();
    private final CinemaLogic logic = logicProvider.getCinemaLogic();

    @Override
    public String execute(String request) {
        String response;
        BlockingQueue<Hall> myHalls;
        try {
            myHalls = logic.allHalls();
            if (myHalls == null || myHalls.size() <=0){
                response = "There are no records to output.";
            } else {
                response = myHalls.toString();
            }
        } catch (LogicException e) {
            // log
            response = "Output Error";
        }

        return response;
    }
}
