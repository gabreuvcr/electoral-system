package repositories;

import java.util.HashMap;
import java.util.Map;

import errors.Warning;
import models.FederalDeputy;

public class HashFederalDeputyRepository implements IFederalDeputyRepository {
    private Map<String, FederalDeputy> federalDeputyCandidates = new HashMap<String, FederalDeputy>();

    public HashFederalDeputyRepository() {
        this.preLoad();
    }

    @Override
    public FederalDeputy getByNumber(String number) {
        return this.federalDeputyCandidates.get(number);
    }

    @Override
    public Map<String, FederalDeputy> getCandidates() {
        return this.federalDeputyCandidates;
    }

    @Override
    public void addCandidate(FederalDeputy candidate) {
        if (this.federalDeputyCandidates.get(candidate.state + candidate.number) != null) {
            throw new Warning("Numero de candidato indisponível");
        }

        this.federalDeputyCandidates.put(
                candidate.state + candidate.number,
                candidate);
    }

    @Override
    public void removeCandidate(FederalDeputy candidate) {
        this.federalDeputyCandidates.remove(
                candidate.state + candidate.number);
    }

    @Override
    public void preLoad() {
        FederalDeputy federalDeputyCandidate1 = new FederalDeputy.Builder()
            .name("Carlos").number(12345).party("PDS1").state("MG").build();
        FederalDeputy federalDeputyCandidate2 = new FederalDeputy.Builder()
            .name("Cleber").number(54321).party("PDS2").state("MG").build();
        FederalDeputy federalDeputyCandidate3 = new FederalDeputy.Builder()
            .name("Sofia").number(11211).party("IHC").state("MG").build();
        
        this.addCandidate(federalDeputyCandidate1);
        this.addCandidate(federalDeputyCandidate2);
        this.addCandidate(federalDeputyCandidate3);
    }
}
