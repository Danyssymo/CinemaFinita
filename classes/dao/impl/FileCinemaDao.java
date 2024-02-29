package project.cinema.classes.dao.impl;

import project.cinema.classes.dao.CinemaDao;
import project.cinema.classes.dao.DaoException;
import project.cinema.classes.entity.Film;
import project.cinema.classes.entity.Hall;
import project.cinema.classes.entity.Seat;
import project.cinema.classes.util.CreateNewDate;

import java.util.concurrent.BlockingQueue;

public class FileCinemaDao implements CinemaDao {
    private CinemaDataBase cinemaDb = new CinemaDataBase();
    private HallDataBase hallDb = new HallDataBase();

    @Override
    public void save(Film film) throws DaoException {
        int id = cinemaDb.getId();
        film.setId(id);

        if (film.getStartOfTheFilm() == null) {
            film.setStartOfTheFilm(CreateNewDate.addDate());
        }

        cinemaDb.writeToFile(film);
    }

    @Override
    public BlockingQueue<Film> allFilms() throws DaoException {
        return cinemaDb.readFromFile();
    }

    @Override
    public BlockingQueue<Hall> allHalls() throws DaoException {
        return hallDb.readAllHalls();
    }

    @Override
    public void deleteAll() throws DaoException {
        cinemaDb.clean();
    }

    @Override
    public void update(Film film) throws DaoException {
        cinemaDb.update(film);
    }

    @Override
    public void deleteById(int id) throws DaoException {
        cinemaDb.deleteById(id);
    }

    @Override
    public void addHall(Hall hall) throws DaoException {
        hallDb.addHallToTheCinema(hall);
    }

    @Override
    public void changeStatus(Seat s, Hall h) throws DaoException {
        hallDb.changeStatus(s, h);
    }

    @Override
    public void refundTicket(String size, int row, int num) throws DaoException {
        hallDb.refundTicket(size, row, num);
    }
}
