package data.repositories;

import java.util.HashMap;
import java.util.Map;

import domain.StateDeputy;
import domain.interfaces.IStateDeputyRepository;
import errors.Warning;

public class HashStateDeputyRepository implements IStateDeputyRepository {
    private Map<String, StateDeputy> stateDeputyCandidates = new HashMap<String, StateDeputy>();

    public HashStateDeputyRepository() {
        this.preLoad();
    }

    @Override
    public StateDeputy getByNumber(String number) {
        return this.stateDeputyCandidates.get(number);
    }

    @Override
    public Map<String, StateDeputy> getCandidates() {
        return this.stateDeputyCandidates;
    }

    @Override
    public void addCandidate(StateDeputy candidate) {
        if (this.getByNumber(candidate.state + candidate.number) != null) {
            throw new Warning("Numero de candidato indisponível");
        }

        this.stateDeputyCandidates.put(
            candidate.state + candidate.number,
            candidate
        );
    }

    @Override
    public void removeCandidate(StateDeputy candidate) {
        this.stateDeputyCandidates.remove(
            candidate.state + candidate.number
        );
    }

    @Override
    public void preLoad() {
        StateDeputy stateDeputyCandidate1 = new StateDeputy.Builder()
            .name("Vanessa").number(1234).party("PDS1").state("MG").build();
        StateDeputy stateDeputyCandidate2 = new StateDeputy.Builder()
            .name("Armando").number(5432).party("PDS2").state("MG").build();
        StateDeputy stateDeputyCandidate3 = new StateDeputy.Builder()
            .name("Juliana").number(1121).party("IHC").state("MG").build();
        
        this.addCandidate(stateDeputyCandidate1);
        this.addCandidate(stateDeputyCandidate2);
        this.addCandidate(stateDeputyCandidate3);
    }
}
