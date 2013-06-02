package edu.um.umflix.reviewmanager.stubs;

import edu.um.umflix.reviewmanager.impl.ReviewManagerImpl;
import edu.umflix.authenticationhandler.AuthenticationHandler;
import edu.umflix.persistence.LicenseDao;
import edu.umflix.persistence.MovieDao;

/**
 *Stub for testing ReviewManager
 */
public class ReviewManagerStub extends ReviewManagerImpl{

    public void setAuthentificationHandler(AuthenticationHandler handler){
        this.aHandler=handler;
    }
    public void setMovieDAO(MovieDao movieDAO){
        this.movieDAO=movieDAO;
    }
    public void setLicenseDAO(LicenseDao licenseDAO){
        this.licenseDAO=licenseDAO;
    }
}
