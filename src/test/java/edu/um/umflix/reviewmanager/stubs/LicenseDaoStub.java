package edu.um.umflix.reviewmanager.stubs;

import edu.umflix.exceptions.LicenseNotFoundException;
import edu.umflix.model.License;
import edu.umflix.persistence.LicenseDao;

import java.util.ArrayList;
import java.util.List;

/**
 * License DAO stub for testing. Simulates persistance.
 */
public class LicenseDaoStub implements LicenseDao{

    List<License> licenses;

    public  LicenseDaoStub(List<License> licenses){
        this.licenses=licenses;
    }

    @Override
    public void addLicense(License license) {

    }

    @Override
    public License getLicenseById(Long aLong) throws LicenseNotFoundException {
        License returnLicense = null;
        for(License license:licenses){
             if(license.getId().equals(aLong)){
                 returnLicense = license;
             }
        }
        if(returnLicense==null)throw new LicenseNotFoundException();
        return returnLicense;
    }

    @Override
    public void deleteLicense(Long aLong) throws LicenseNotFoundException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteLicenses(ArrayList<License> licenses) throws LicenseNotFoundException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateLicense(License license) throws LicenseNotFoundException {
       for(License lin: licenses ){
           if(lin.getId().equals(license.getId())){
               lin = license;
           }

       }
    }

    @Override
    public void updateLicenses(ArrayList<License> licenses) throws LicenseNotFoundException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
