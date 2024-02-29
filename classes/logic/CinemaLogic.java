package project.cinema.classes.logic;

import project.cinema.classes.entity.Film;
import project.cinema.classes.entity.Hall;
import project.cinema.classes.entity.Ticket;

import java.util.concurrent.BlockingQueue;

public interface CinemaLogic {
    void add(Film film) throws LogicException;

    BlockingQueue<Film> find(String text) throws LogicException;

    BlockingQueue<Film> allFilms() throws LogicException;

    BlockingQueue<Hall> allHalls() throws LogicException;

    void clean() throws LogicException;

    void update(Film film) throws LogicException;

    BlockingQueue<Film> sort(String text) throws LogicException;

    void deleteById(int id) throws LogicException;

    void build(Hall hall) throws LogicException;

    void refundTicket(String size,int row, int num) throws LogicException;

    Ticket createTicket(String hallSize, int filmId, int seat_row, int seat_num) throws LogicException;
}
