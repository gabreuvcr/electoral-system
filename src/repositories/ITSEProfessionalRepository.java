package repositories;

import models.TSEProfessional;

public interface ITSEProfessionalRepository {
    TSEProfessional getByUser(String user);
    void addTSEProfessional(String user, TSEProfessional tseProfessional);
}
