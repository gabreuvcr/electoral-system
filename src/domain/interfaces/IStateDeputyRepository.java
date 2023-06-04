package domain.interfaces;

import java.util.Map;

import domain.StateDeputy;

public interface IStateDeputyRepository {
    void addCandidate(StateDeputy candidate);
    StateDeputy getByNumber(String number);
    Map<String, StateDeputy> getCandidates();
    void removeCandidate(StateDeputy candidate);
    void preLoad();
}
