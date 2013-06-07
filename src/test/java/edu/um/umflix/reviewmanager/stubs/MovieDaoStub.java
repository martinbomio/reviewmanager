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
        License license1=new License(false,null,null,null,Long.valueOf(1),null,null,null,null);
        License license2=new License(false,null,null,null,Long.valueOf(2),null,null,null,null);
        licenses.add(license1);
        licenses.add(license2);
        movies = new ArrayList<Movie>();
        movies.add(new Movie("action",Long.valueOf(1),licenses,new Date(),"Armagedon",false,null,null,null,null));
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
    public void updateMovie(Movie movie) throws MovieNotFoundException, LicenseNotFoundException, ClipNotFoundException {
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
