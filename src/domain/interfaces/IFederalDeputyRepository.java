package domain.interfaces;

import java.util.Map;

import domain.FederalDeputy;

public interface IFederalDeputyRepository {
    void addCandidate(FederalDeputy candidate);
    FederalDeputy getByNumber(String number);
    Map<String, FederalDeputy> getCandidates();
    void removeCandidate(FederalDeputy candidate);
    void preLoad();
}
