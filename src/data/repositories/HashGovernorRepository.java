package data.repositories;

import java.util.HashMap;
import java.util.Map;

import domain.Governor;
import domain.interfaces.IGovernorRepository;
import errors.Warning;

public class HashGovernorRepository implements IGovernorRepository {
    private Map<Integer, Governor> governorCandidates = new HashMap<Integer, Governor>();

    public HashGovernorRepository() {
        this.preLoad();
    }

    @Override
    public Governor getByNumber(int number) {
        return this.governorCandidates.get(number);
    }

    @Override
    public Map<Integer, Governor> getCandidates() {
        return this.governorCandidates;
    }

    @Override
    public void addCandidate(Governor candidate) {
        if (this.getByNumber(candidate.number) != null) {
            throw new Warning("Numero de candidato indisponível");
        }

        this.governorCandidates.put(candidate.number, candidate);
    }

    @Override
    public void removeCandidate(Governor candidate) {
        this.governorCandidates.remove(candidate.number);
    }

    @Override
    public void preLoad() {
        Governor governorCandidate1 = new Governor.Builder()
            .name("João").number(123).party("PDS1").build();
        Governor governorCandidate2 = new Governor.Builder()
            .name("Maria").number(124).party("ED").build();

        this.addCandidate(governorCandidate1);
        this.addCandidate(governorCandidate2);
    }

}
