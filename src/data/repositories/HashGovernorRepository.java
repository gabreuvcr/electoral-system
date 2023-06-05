package data.repositories;

import java.util.HashMap;
import java.util.Map;

import domain.Governor;
import domain.interfaces.IGovernorRepository;
import errors.Warning;

public class HashGovernorRepository implements IGovernorRepository {
    private Map<String, Governor> governorCandidates = new HashMap<String, Governor>();

    public HashGovernorRepository() {
        this.preLoad();
    }

    @Override
    public Governor getByNumber(String number) {
        return this.governorCandidates.get(number);
    }

    @Override
    public Map<String, Governor> getCandidates() {
        return this.governorCandidates;
    }

    @Override
    public void addCandidate(Governor candidate) {
        if (this.getByNumber(candidate.state + candidate.number) != null) {
            throw new Warning("Numero de candidato indisponível");
        }

        this.governorCandidates.put(
            candidate.state + candidate.number,
            candidate
        );
    }

    @Override
    public void removeCandidate(Governor candidate) {
        this.governorCandidates.remove(
            candidate.state + candidate.number
        );
    }

    @Override
    public void preLoad() {
        Governor governorCandidate1 = new Governor.Builder()
            .name("Bento").number(123).party("PDS1").state("MG").build();
        Governor governorCandidate2 = new Governor.Builder()
            .name("Zuleide").number(124).party("ED").state("MG").build();

        this.addCandidate(governorCandidate1);
        this.addCandidate(governorCandidate2);
    }

}
