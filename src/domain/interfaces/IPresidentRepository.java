package domain.interfaces;

import java.util.Map;

import domain.President;

public interface IPresidentRepository {
    President getByNumber(int number);
    Map<Integer, President> getCandidates();
    void addCandidate(President candidate);
    void removeCandidate(President candidate);
    void preLoad();
}
