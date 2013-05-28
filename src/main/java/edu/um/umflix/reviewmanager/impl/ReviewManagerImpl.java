package edu.um.umflix.reviewmanager.impl;

import edu.um.umflix.reviewmanager.ReviewManager;
import edu.umflix.authenticationhandler.AuthenticationHandler;
import edu.umflix.model.Movie;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Implementation of the ReviewManager
 */
@Stateless
public class ReviewManagerImpl implements ReviewManager {

    @EJB(beanName = "AuthentificationHandlerImpl")
    AuthenticationHandler aHandler;

    @Override
    public List<Movie> getMovieToReview(String token) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void accept(String token, Long movieID, Long licenseID) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void reject(String token, Long movieID, Long licenseID) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
