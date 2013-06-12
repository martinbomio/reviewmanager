package edu.um.umflix.reviewmanager.stubs;

import edu.umflix.exceptions.ClipNotFoundException;
import edu.umflix.exceptions.LicenseNotFoundException;
import edu.umflix.exceptions.MovieNotFoundException;
import edu.umflix.model.License;
import edu.umflix.model.Movie;
import edu.umflix.persistence.MovieDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MovieDao Stub for Testing. Simulates Persistance
 */
public class MovieDaoStub implements MovieDao{
    private List<Movie> movies;

    public MovieDaoStub(){
        ArrayList<License> licenses = new ArrayList<License>();
        License license1=new License(false,null,null,null,null,null,null,null);
        license1.setId(Long.valueOf(0));
        License license2=new License(false,null,null,null,null,null,null,null);
        license2.setId(Long.valueOf(1));
        licenses.add(license1);
        licenses.add(license2);
        movies = new ArrayList<Movie>();
        Movie movie = new Movie(null,null,null,null,false,"action",licenses,new Date(),"Avatar");
        movie.setId(Long.valueOf(0));
        movies.add(movie);
    }

    @Override
    public void addMovie(Movie movie) {

    }

    @Override
    public void deleteMovie(Long aLong) throws MovieNotFoundException, LicenseNotFoundException, ClipNotFoundException {

    }

    @Override
    public Movie getMovieById(Long aLong) throws MovieNotFoundException {
        Movie returnMovie = null;
        for(Movie movie : movies){
            if(movie.getId().equals(aLong)){
                returnMovie = movie;
            }
        }
        if(returnMovie==null) throw new MovieNotFoundException();
        return returnMovie;
    }

    @Override
    public List<Movie> getMovieList() {
        return movies;
    }

    @Override
    public List<Movie> getMovieListByKey(String s) {
        return null;
    }

    @Override
    public void updateMovie(Movie movie) throws MovieNotFoundException {
        for(Movie mov : movies){
          if(mov.getId().equals(movie.getId())){
              mov = movie;
          }
        }
    }

    @Override
    public List<Movie> getDisabledMovies() {
        return movies;
    }

}
