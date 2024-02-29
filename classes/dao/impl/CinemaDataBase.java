package project.cinema.classes.dao.impl;


import project.cinema.classes.dao.DaoException;
import project.cinema.classes.entity.Film;
import project.cinema.classes.util.CreateNewDate;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CinemaDataBase {

    private final BlockingQueue<Film> films = new ArrayBlockingQueue<>(10000);

    private final File file = new File("cinemaFile.txt");

    public synchronized void writeToFile(Film film) throws DaoException {
        try (FileWriter fw = new FileWriter("cinemaFile.txt", true)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            films.add(film);
            if (film.getStartOfTheFilm() == null) {
                film.setStartOfTheFilm(CreateNewDate.addDate());
            }
            String str = "Id=" + film.getId() + "&Name=" + film.getFilmName() + "&Duration=" + film.getFilmDuration() + "&Price=" + film.getTicketPrice() + "&Genre=" + film.getFilmGenre() + "&Discription=" + film.getFilmDescription() + "&Start=" + formatter.format(film.getStartOfTheFilm());
            fw.write(str);
            fw.append("\n");
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    public synchronized BlockingQueue<Film> readFromFile() throws DaoException {
        List<Film> films_ = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("cinemaFile.txt"))) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String[] params;
            String line = reader.readLine();

            while (line != null) {

                params = line.split("&");
                Film newFilm = new Film();
                newFilm.setId(Integer.parseInt(params[0].split("=")[1]));
                newFilm.setFilmName(params[1].split("=")[1]);
                newFilm.setFilmDuration(Integer.parseInt(params[2].split("=")[1]));
                newFilm.setTicketPrice(Integer.parseInt(params[3].split("=")[1]));
                newFilm.setFilmGenre(params[4].split("=")[1]);
                newFilm.setFilmDescription(params[5].split("=")[1]);
                String dateInString = (params[6].split("=")[1]);
                Date date = formatter.parse(dateInString);
                newFilm.setStartOfTheFilm(date);
                films_.add(newFilm);

                line = reader.readLine();
                films.clear();
                films.addAll(films_);
            }

        } catch (IOException | ParseException e) {
            throw new DaoException(e);
        }
        return films;
    }

    public int countOfNotes() throws DaoException {
        readFromFile();
        return films.size();
    }

    private int lastId() throws DaoException {
        return countOfNotes();
    }

    public int getId() throws DaoException {
        return lastId() + 1;
    }

    public synchronized void clean() throws DaoException {
        films.clear();
        try (PrintWriter writer = new PrintWriter("cinemaFile.txt")) {
            writer.print("");
        } catch (FileNotFoundException e) {
            throw new DaoException(e);
        }
    }

    public synchronized void update(Film film) throws DaoException {
        List<Film> temp = new ArrayList<>(readFromFile());
        for (Film film1 : temp) {
            if (film1.getId() == film.getId()) {
                film1.setFilmName(film.getFilmName());
                film1.setFilmDuration(film.getFilmDuration());
                film1.setTicketPrice(film.getTicketPrice());
                film1.setFilmGenre(film.getFilmGenre());
                film1.setFilmDescription(film.getFilmDescription());
                film1.setStartOfTheFilm(film.getStartOfTheFilm());
                break;
            }
        }
        clean();
        for (Film f : temp) {
            writeToFile(f);
        }
        films.clear();
        films.addAll(temp);
    }

    public synchronized void deleteById(int id) throws DaoException {
        boolean flag = false;
        List<Film> temp = new ArrayList<>(readFromFile());
        List<Film> found = new ArrayList<>();
        clean();
        for (Film film : temp) {
            if (film.getId() != id) {
                if (!flag) {
                    found.add(film);
                } else {
                    film.setId(film.getId() - 1);
                    found.add(film);
                }
            } else {
                flag = true;
            }
        }

        for (Film film : found) {
            writeToFile(film);
        }
    }
}
