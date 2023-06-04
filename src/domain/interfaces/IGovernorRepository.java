package domain.interfaces;

import java.util.Map;

import domain.Governor;

public interface IGovernorRepository {
    Governor getByNumber(int number);
    Map<Integer, Governor> getCandidates();
    void addCandidate(Governor candidate);
    void removeCandidate(Governor candidate);
    void preLoad();
}
