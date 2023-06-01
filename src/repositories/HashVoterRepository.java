package repositories;

import java.util.HashMap;
import java.util.Map;

import models.Voter;

public class HashVoterRepository implements IVoterRepository{
    private final Map<String, Voter> voters = new HashMap<>();
    
    @Override
    public Voter getByElectoralCard(String electoralCard) {
        return this.voters.get(electoralCard);
    }

    @Override
    public void addVoter(String electoralCard, Voter voter) {
        this.voters.put(electoralCard, voter);
    }
    
}
