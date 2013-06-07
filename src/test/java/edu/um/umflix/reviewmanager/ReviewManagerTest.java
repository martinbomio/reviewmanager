package edu.um.umflix.reviewmanager;

import edu.um.umflix.reviewmanager.exceptions.LicenseNotFoundRuntimeException;
import edu.um.umflix.reviewmanager.exceptions.MovieNotFoundRuntimeException;
import edu.um.umflix.reviewmanager.exceptions.NotReviewerException;
import edu.um.umflix.reviewmanager.stubs.LicenseDaoStub;
import edu.um.umflix.reviewmanager.stubs.MovieDaoStub;
import edu.um.umflix.reviewmanager.stubs.ReviewManagerStub;
import edu.umflix.authenticationhandler.AuthenticationHandler;
import edu.umflix.authenticationhandler.exceptions.InvalidTokenException;
import edu.umflix.exceptions.LicenseNotFoundException;
import edu.umflix.exceptions.MovieNotFoundException;
import edu.umflix.model.License;
import edu.umflix.model.Movie;
import edu.umflix.model.Role;
import edu.umflix.model.User;
import edu.umflix.persistence.LicenseDao;
import edu.umflix.persistence.MovieDao;
import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

/**
 * Tests of ReviewManager using Mockito and Stubs
 */
public class ReviewManagerTest {

    AuthenticationHandler mockedAuth;


    public AuthenticationHandler setUpAuthMock(){
        AuthenticationHandler mockAuthenticationHandler = Mockito.mock(AuthenticationHandler.class);
        Mockito.when(mockAuthenticationHandler.validateToken("pruebatoken")).thenReturn(true);
        Mockito.when(mockAuthenticationHandler.validateToken("pruebatokenSinPermisos")).thenReturn(true);
        Mockito.when(mockAuthenticationHandler.validateToken("invalidToken")).thenReturn(false);

        User validUser =  new User("martin@gmail.com","martin","pass",
        new Role(Role.RoleType.REVIEWER.getRole()));
        User invalidUser =  new User("martin@gmail.com","martin","pass",
                new Role(Role.RoleType.USER.getRole()));
        try{
        Mockito.when(mockAuthenticationHandler.getUserOfToken("pruebatoken")).thenReturn(validUser);
        Mockito.when(mockAuthenticationHandler.getUserOfToken("pruebatokenSinPermisos")).thenReturn(invalidUser);

        Mockito.when(mockAuthenticationHandler.getUserOfToken("invalidToken")).thenThrow(new InvalidTokenException());

        Mockito.when(mockAuthenticationHandler.isUserInRole("pruebatoken",validUser.getRole())).thenReturn(true);
        Mockito.when(mockAuthenticationHandler.isUserInRole("pruebatokenSinPermisos",invalidUser.getRole())).thenReturn(false);
        }catch(InvalidTokenException e){
            //Never enters here
        }
       return mockAuthenticationHandler;
    }
    @Test
    public void testInvalidToken(){
     AuthenticationHandler aHandler = setUpAuthMock();
        MovieDao mDAO = new MovieDaoStub();
        LicenseDao lDAO = new LicenseDaoStub(mDAO.getMovieList().get(0).getLicenses());
        ReviewManagerStub reviewManager = new ReviewManagerStub();
        reviewManager.setAuthentificationHandler(aHandler);
        reviewManager.setLicenseDAO(lDAO);
        reviewManager.setMovieDAO(mDAO);



        List<Movie> movies = mDAO.getMovieList();
        List<Movie> moviesToReview;
        try{
            moviesToReview = reviewManager.getMovieToReview("invalidToken");
            Assert.fail();
        }catch(InvalidTokenException e){
            Assert.assertTrue(true);
        }catch (NotReviewerException e){
            Assert.fail();
        }

        try {
            reviewManager.accept("invalidToken",Long.valueOf(1),Long.valueOf(1));
        } catch (InvalidTokenException e) {
            Assert.assertTrue(true);
        } catch (NotReviewerException e) {
            Assert.fail();
        }

        try {
            reviewManager.reject("invalidToken",Long.valueOf(1),Long.valueOf(1));
        } catch (InvalidTokenException e) {
            Assert.assertTrue(true);
        } catch (NotReviewerException e) {
             Assert.fail();
        }

    }
    @Test
    public void testValidTokenWithPermission(){
        AuthenticationHandler aHandler = setUpAuthMock();
        MovieDao mDAO = new MovieDaoStub();
        LicenseDao lDAO = new LicenseDaoStub(mDAO.getMovieList().get(0).getLicenses());
        ReviewManagerStub reviewManager = new ReviewManagerStub();
        reviewManager.setAuthentificationHandler(aHandler);
        reviewManager.setLicenseDAO(lDAO);
        reviewManager.setMovieDAO(mDAO);

        List<Movie> movies = mDAO.getMovieList();
        List<Movie> moviesToReview = null;
        try{
            moviesToReview = reviewManager.getMovieToReview("pruebatoken");
            Assert.assertEquals(movies,moviesToReview);
        }catch (InvalidTokenException e){
            Assert.fail();
        }catch (NotReviewerException e){
            Assert.fail();
        }
        Movie movieToReview = moviesToReview.get(0);
        License license = movieToReview.getLicenses().get(0);
        License license1 = movieToReview.getLicenses().get(1);
        try{
            reviewManager.accept("pruebatoken",movieToReview.getId(),license.getId());
            Assert.assertEquals(lDAO.getLicenseById(license.getId()).isAccepted(),true);
            Assert.assertEquals(lDAO.getLicenseById(license1.getId()).isAccepted(),false);
            Assert.assertEquals(mDAO.getMovieById(movieToReview.getId()).isEnabled(),true);

            reviewManager.reject("pruebatoken",movieToReview.getId(),license.getId());
            Assert.assertEquals(lDAO.getLicenseById(license.getId()).isAccepted(),false);
            Assert.assertEquals(lDAO.getLicenseById(license1.getId()).isAccepted(),false);
            Assert.assertEquals(mDAO.getMovieById(movieToReview.getId()).isEnabled(),false);

            reviewManager.accept("pruebatoken",movieToReview.getId(),license.getId());
            reviewManager.accept("pruebatoken",movieToReview.getId(),license1.getId());

            Assert.assertEquals(lDAO.getLicenseById(license.getId()).isAccepted(),true);
            Assert.assertEquals(lDAO.getLicenseById(license1.getId()).isAccepted(),true);
            Assert.assertEquals(mDAO.getMovieById(movieToReview.getId()).isEnabled(),true);

            reviewManager.accept("pruebatoken",movieToReview.getId(),license.getId());
            Assert.assertEquals(lDAO.getLicenseById(license.getId()).isAccepted(),true);

            reviewManager.reject("pruebatoken",movieToReview.getId(),license.getId());
            Assert.assertEquals(lDAO.getLicenseById(license.getId()).isAccepted(),false);
            Assert.assertEquals(mDAO.getMovieById(movieToReview.getId()).isEnabled(),true);

            reviewManager.reject("pruebatoken",movieToReview.getId(),license1.getId());
            Assert.assertEquals(lDAO.getLicenseById(license1.getId()).isAccepted(),false);
            Assert.assertEquals(mDAO.getMovieById(movieToReview.getId()).isEnabled(),false);
        }catch (NotReviewerException e){
            Assert.fail();
        }catch (InvalidTokenException e){
            Assert.fail();
        }catch(LicenseNotFoundException e){
            Assert.fail();
        } catch (MovieNotFoundException e){
            Assert.fail();
        }


    }
    @Test
    public void testValidTokenWithoutPermission(){
        AuthenticationHandler aHandler = setUpAuthMock();
        MovieDao mDAO = new MovieDaoStub();
        LicenseDao lDAO = new LicenseDaoStub(mDAO.getMovieList().get(0).getLicenses());
        ReviewManagerStub reviewManager = new ReviewManagerStub();
        reviewManager.setAuthentificationHandler(aHandler);
        reviewManager.setLicenseDAO(lDAO);
        reviewManager.setMovieDAO(mDAO);

        List<Movie> movies = mDAO.getMovieList();
        try{
            List<Movie> moviesToReview = reviewManager.getMovieToReview("pruebatokenSinPermisos");
            Assert.fail();
        }catch (InvalidTokenException e){
            Assert.fail();
        }catch (NotReviewerException e){
            Assert.assertTrue(true);
        }

        try {
            reviewManager.accept("pruebatokenSinPermisos",Long.valueOf(1),Long.valueOf(1));
            Assert.fail();
        } catch (InvalidTokenException e) {
            Assert.fail();
        } catch (NotReviewerException e) {
            Assert.assertTrue(true);
        }

        try {
            reviewManager.reject("pruebatokenSinPermisos", Long.valueOf(1), Long.valueOf(1));
            Assert.fail();
        } catch (InvalidTokenException e) {
            Assert.fail();
        } catch (NotReviewerException e) {
            Assert.assertTrue(true);
        }
    }

    @Test(expected = MovieNotFoundRuntimeException.class)
    public void testAcceptMovieNotFoundRuntimeException(){
        AuthenticationHandler aHandler = setUpAuthMock();
        MovieDao mDAO = new MovieDaoStub();
        LicenseDao lDAO = new LicenseDaoStub(mDAO.getMovieList().get(0).getLicenses());
        ReviewManagerStub reviewManager = new ReviewManagerStub();
        reviewManager.setAuthentificationHandler(aHandler);
        reviewManager.setLicenseDAO(lDAO);
        reviewManager.setMovieDAO(mDAO);

        try {
            reviewManager.accept("pruebatoken",Long.valueOf(3),Long.valueOf(1));
        } catch (InvalidTokenException e) {
            Assert.fail();
        } catch (NotReviewerException e) {
           Assert.fail();
        }
    }
    @Test(expected = LicenseNotFoundRuntimeException.class)
    public void testAcceptLicenseNotFoundRuntimeException(){
        AuthenticationHandler aHandler = setUpAuthMock();
        MovieDao mDAO = new MovieDaoStub();
        LicenseDao lDAO = new LicenseDaoStub(mDAO.getMovieList().get(0).getLicenses());
        ReviewManagerStub reviewManager = new ReviewManagerStub();
        reviewManager.setAuthentificationHandler(aHandler);
        reviewManager.setLicenseDAO(lDAO);
        reviewManager.setMovieDAO(mDAO);

        try {
            reviewManager.accept("pruebatoken",Long.valueOf(1),Long.valueOf(3));
        } catch (InvalidTokenException e) {
            Assert.fail();
        } catch (NotReviewerException e) {
            Assert.fail();
        }
   }
    @Test(expected = MovieNotFoundRuntimeException.class)
    public void testRejectMovieNotFoundRuntimeException(){
        AuthenticationHandler aHandler = setUpAuthMock();
        MovieDao mDAO = new MovieDaoStub();
        LicenseDao lDAO = new LicenseDaoStub(mDAO.getMovieList().get(0).getLicenses());
        ReviewManagerStub reviewManager = new ReviewManagerStub();
        reviewManager.setAuthentificationHandler(aHandler);
        reviewManager.setLicenseDAO(lDAO);
        reviewManager.setMovieDAO(mDAO);

        try {
            reviewManager.reject("pruebatoken",Long.valueOf(3),Long.valueOf(1));
        } catch (InvalidTokenException e) {
            Assert.fail();
        } catch (NotReviewerException e) {
            Assert.fail();
        }
    }
    @Test(expected = LicenseNotFoundRuntimeException.class)
    public void testRejectLicenseNotFoundRuntimeException(){
        AuthenticationHandler aHandler = setUpAuthMock();
        MovieDao mDAO = new MovieDaoStub();
        LicenseDao lDAO = new LicenseDaoStub(mDAO.getMovieList().get(0).getLicenses());
        ReviewManagerStub reviewManager = new ReviewManagerStub();
        reviewManager.setAuthentificationHandler(aHandler);
        reviewManager.setLicenseDAO(lDAO);
        reviewManager.setMovieDAO(mDAO);

        try {
            reviewManager.reject("pruebatoken",Long.valueOf(1),Long.valueOf(3));
        } catch (InvalidTokenException e) {
            Assert.fail();
        } catch (NotReviewerException e) {
            Assert.fail();
        }
    }
}
