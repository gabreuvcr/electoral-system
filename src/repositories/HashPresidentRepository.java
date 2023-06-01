package repositories;

import java.util.HashMap;
import java.util.Map;

import errors.Warning;
import models.President;

public class HashPresidentRepository implements IPresidentRepository {
    private Map<Integer, President> presidentCandidates = new HashMap<Integer, President>();

    public HashPresidentRepository() {
        this.preLoad();
    }

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

    @Override
    public void preLoad() {
        President presidentCandidate1 = new President.Builder()
            .name("João").number(123).party("PDS1").build();
        President presidentCandidate2 = new President.Builder()
            .name("Maria").number(124).party("ED").build();

        this.addCandidate(presidentCandidate1);
        this.addCandidate(presidentCandidate2);
    }

}
