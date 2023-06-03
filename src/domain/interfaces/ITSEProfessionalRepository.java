package domain.interfaces;

import domain.TSEProfessional;

public interface ITSEProfessionalRepository {
    TSEProfessional getByUser(String user);
    void addTSEProfessional(String user, TSEProfessional tseProfessional);
    void preLoad();
}
