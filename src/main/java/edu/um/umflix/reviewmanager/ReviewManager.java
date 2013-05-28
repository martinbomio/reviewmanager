package edu.um.umflix.reviewmanager;

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
     * @return
     * List of movies to review.
     */
    public List<Movie> getMovieToReview(String token);
    public void accept(String token, Long movieID, Long licenseID);
    public void reject(String token,Long movieID, Long licenseID);
}
