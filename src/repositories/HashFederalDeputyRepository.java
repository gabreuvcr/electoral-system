package repositories;

import java.util.HashMap;
import java.util.Map;

import errors.Warning;
import models.FederalDeputy;

public class HashFederalDeputyRepository implements IFederalDeputyRepository {
    private Map<String, FederalDeputy> federalDeputyCandidates = new HashMap<String, FederalDeputy>();

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
            candidate
        );
    }

    @Override
    public void removeCandidate(FederalDeputy candidate) {
        this.federalDeputyCandidates.remove(
            candidate.state + candidate.number
        );
    }
}
