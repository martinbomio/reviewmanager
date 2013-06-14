package edu.um.umflix.reviewmanager.impl;

import edu.um.umflix.reviewmanager.ReviewManager;
import edu.um.umflix.reviewmanager.exceptions.LicenseNotFoundRuntimeException;
import edu.um.umflix.reviewmanager.exceptions.MovieNotFoundRuntimeException;
import edu.um.umflix.reviewmanager.exceptions.NotReviewerException;
import edu.umflix.authenticationhandler.AuthenticationHandler;
import edu.umflix.authenticationhandler.exceptions.InvalidTokenException;
import edu.umflix.exceptions.LicenseNotFoundException;
import edu.umflix.exceptions.MovieNotFoundException;
import edu.umflix.model.License;
import edu.umflix.model.Movie;
import edu.umflix.model.User;
import edu.umflix.persistence.LicenseDao;
import edu.umflix.persistence.MovieDao;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Implementation of the ReviewManager
 */
@Stateless(name="ReviewManager")
public class ReviewManagerImpl implements ReviewManager {
    Logger log = Logger.getLogger(ReviewManagerImpl.class);

    @EJB(beanName = "AuthenticationHandler")
    protected AuthenticationHandler aHandler;
    @EJB(beanName="MovieDao")
    protected MovieDao movieDAO;
    @EJB(beanName="LicenseDao")
    protected LicenseDao licenseDAO;

    @Override
    public List<Movie> getMovieToReview(String token) throws InvalidTokenException,NotReviewerException{
        List<Movie> pendingMovies = null;
        if(!aHandler.validateToken(token)){
            new InvalidTokenException();
        }

        User reviewer = aHandler.getUserOfToken(token);
        if(!aHandler.isUserInRole(token,reviewer.getRole())){
          throw new NotReviewerException();
        }
        pendingMovies = movieDAO.getDisabledMovies();

        return pendingMovies;
    }

    @Override
    public void accept(String token, Long movieID, Long licenseID) throws InvalidTokenException,NotReviewerException{
        if(!aHandler.validateToken(token))throw new InvalidTokenException();

        User reviewer = aHandler.getUserOfToken(token);
        if(!aHandler.isUserInRole(token,reviewer.getRole())){
            throw new NotReviewerException();
        }
           try{
              License license = licenseDAO.getLicenseById(licenseID);
              license.setAccepted(true);
              licenseDAO.updateLicense(license);

              Movie movie = movieDAO.getMovieById(movieID);
              movie.setEnabled(true);
              movieDAO.updateMovie(movie);

           }catch(MovieNotFoundException e){
               log.error(e);
               throw new MovieNotFoundRuntimeException();
           }catch (LicenseNotFoundException e){
               log.error(e);
               throw new LicenseNotFoundRuntimeException();
           }
        log.info("License and Movie accepted");
    }

    @Override
    public void reject(String token, Long movieID, Long licenseID) throws InvalidTokenException,NotReviewerException{
        if(!aHandler.validateToken(token))throw new InvalidTokenException();

        User reviewer = aHandler.getUserOfToken(token);
        if(!aHandler.isUserInRole(token,reviewer.getRole())){
         throw new NotReviewerException();
        }
            try{
                License license = licenseDAO.getLicenseById(licenseID);
                license.setAccepted(false);
                licenseDAO.updateLicense(license);

                boolean movieAccepted = false;
                Movie movie = movieDAO.getMovieById(movieID);
                for(License mLicense : movie.getLicenses()){
                    if(mLicense.isAccepted()){
                         movieAccepted=true;
                    }
                }
                movie.setEnabled(movieAccepted);
                movieDAO.updateMovie(movie);

            }catch(MovieNotFoundException e){
                log.error(e);
                throw new MovieNotFoundRuntimeException();
            }catch (LicenseNotFoundException e){
                log.error(e);
                throw new LicenseNotFoundRuntimeException();
            }
        log.info("Movie and/or License rejected");
    }

}
