package repositories;

import java.util.HashMap;
import java.util.Map;

import errors.Warning;
import models.President;

public class HashPresidentRepository implements IPresidentRepository {
    private Map<Integer, President> presidentCandidates = new HashMap<Integer, President>();

    @Override
    public President getByNumber(int number) {
        return this.presidentCandidates.get(number);
    }

    @Override
    public Map<Integer, President> getCandidates() {
        return this.presidentCandidates;
    }

    @Override
    public void addCandidate(President candidate) {
        if (this.presidentCandidates.get(candidate.number) != null) {
            throw new Warning("Numero de candidato indisponível");
        }

        this.presidentCandidates.put(candidate.number, candidate);
    }

    @Override
    public void removeCandidate(President candidate) {
        this.presidentCandidates.remove(candidate.number);
    }

}
