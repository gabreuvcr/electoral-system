package repositories;

import java.util.Map;

import models.FederalDeputy;

public interface IFederalDeputyRepository {
    void addCandidate(FederalDeputy candidate);
    FederalDeputy getByNumber(String number);
    Map<String, FederalDeputy> getCandidates();
    void removeCandidate(FederalDeputy candidate);
    void preLoad();
}
