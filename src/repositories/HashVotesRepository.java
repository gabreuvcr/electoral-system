package repositories;

import java.util.HashMap;
import java.util.Map;

import models.Candidate;
import models.FederalDeputy;
import models.Voter;

public class HashVotesRepository implements IVotesRepository{
    private Map<Voter, Integer> votersPresident = new HashMap<Voter, Integer>();
    private Map<Voter, Integer> votersFederalDeputy = new HashMap<Voter, Integer>();
    private Map<Voter, FederalDeputy> tempFDVote = new HashMap<Voter, FederalDeputy>();
    
    @Override
    public void addVoteForPresident(Voter voter) {
        votersPresident.put(voter, 1);
    }

    @Override
    public void addVoteForFederalDeputy(Voter voter) {
        if (votersFederalDeputy.get(voter) == null) {
            votersFederalDeputy.put(voter, 1);
        } else {
            votersFederalDeputy.put(voter, votersFederalDeputy.get(voter) + 1);
        }
    }

    @Override
    public void addVoteForFederalDeputy(Voter voter, Candidate candidate) {
        if (votersFederalDeputy.get(voter) == null) {
            votersFederalDeputy.put(voter, 1);
            tempFDVote.put(voter, (FederalDeputy)candidate);
        } else {
            votersFederalDeputy.put(voter, votersFederalDeputy.get(voter) + 1);
            tempFDVote.remove(voter);
        }
    }

    @Override
    public boolean alreadyVotedForPresident(Voter voter) {
        if (votersPresident.get(voter) != null && votersPresident.get(voter) >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean alreadyVotedForFederalDeputy(Voter voter) {
        if (votersFederalDeputy.get(voter) != null && votersFederalDeputy.get(voter) >= 2) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isRepeatFederalDeputy(Voter voter, Candidate candidate) {
        if (tempFDVote.get(voter) != null && tempFDVote.get(voter).equals(candidate)) {
            return true;
        }
        return false;
    }
}
