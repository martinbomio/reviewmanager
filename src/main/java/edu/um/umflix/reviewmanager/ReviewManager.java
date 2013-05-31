package edu.um.umflix.reviewmanager;

import edu.umflix.authenticationhandler.exceptions.InvalidTokenException;
import edu.umflix.model.Movie;

import javax.ejb.Remote;
import java.util.List;

/**
 * Interface that defines the actions of the reviewer of the movies before inserting them into UMFlix
 */
@Remote
public interface ReviewManager {
    /**
     * Method that returns all the movies that are pending to review
     * @param token identificator of the session
     * @return List of movies to review. Returns null if there are no movies to review
     */
    public List<Movie> getMovieToReview(String token) throws InvalidTokenException;

    /**
     *
     * @param token
     * @param movieID
     * @param licenseID
     * @throws InvalidTokenException
     */
    public void accept(String token, Long movieID, Long licenseID)throws InvalidTokenException;

    /**
     *
     * @param token
     * @param movieID
     * @param licenseID
     * @throws InvalidTokenException
     */
    public void reject(String token,Long movieID, Long licenseID)throws InvalidTokenException;
}
