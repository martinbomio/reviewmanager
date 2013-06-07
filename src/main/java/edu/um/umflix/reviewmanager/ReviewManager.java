package edu.um.umflix.reviewmanager;

import edu.um.umflix.reviewmanager.exceptions.NotReviewerException;
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
     * @param token identifier of the session
     * @return List of movies to review. Returns null if there are no movies to review
     * @throws InvalidTokenException
     * @throws NotReviewerException
     */
    public List<Movie> getMovieToReview(String token) throws InvalidTokenException,NotReviewerException;

    /**
     * Method that accept a license of a movie to review. If the movie isn't enabled for watching it, this method
     * enables it.
     * @param token identifier of the session.
     * @param movieID id of the movie the license belong.
     * @param licenseID id of the license to be accepted.
     * @throws InvalidTokenException if the token isn' a valid one.
     * @throws NotReviewerException if the user isn't a reviewer.
     */
    public void accept(String token, Long movieID, Long licenseID)throws InvalidTokenException,NotReviewerException;

    /**
     * Method that rejects a license of a movie to review. If the movie is enabled to watch and there are no license
     * accepted, the movies is disabled.
     * @param token identifier of the session.
     * @param movieID id of the movie the license belong.
     * @param licenseID id of the license to be rejected.
     * @throws InvalidTokenException f the token isn' a valid one.
     * @throws NotReviewerException  if the user isn't a reviewer.
     */
    public void reject(String token,Long movieID, Long licenseID)throws InvalidTokenException,NotReviewerException;
}
