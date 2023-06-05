package domain.interfaces;

import java.util.Map;

import domain.Governor;

public interface IGovernorRepository {
    Governor getByNumber(String number);
    Map<String, Governor> getCandidates();
    void addCandidate(Governor candidate);
    void removeCandidate(Governor candidate);
    void preLoad();
}
