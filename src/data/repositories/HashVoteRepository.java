package data.repositories;

import java.util.HashMap;
import java.util.Map;

import domain.Candidate;
import domain.FederalDeputy;
import domain.President;
import domain.Voter;
import domain.interfaces.IVoteRepository;

public class HashVoteRepository implements IVoteRepository{
    private int nullVotesPresident;
    private int nullVotesFederalDeputy;
    private int protestVotesPresident;
    private int protestVotesFederalDeputy;
    private Map<Voter, Integer> votersPresident = new HashMap<Voter, Integer>();
    private Map<Voter, Integer> votersFederalDeputy = new HashMap<Voter, Integer>();
    private Map<Voter, FederalDeputy> tempFDVote = new HashMap<Voter, FederalDeputy>();
    
    @Override
    public void addVoteForPresident(Voter voter, President president) {
        votersPresident.put(voter, 1);
        president.numVotes++;
    }

    @Override 
    public void addProtestVoteForPresident(Voter voter) {
        votersPresident.put(voter, 1);
        protestVotesPresident++;
    }

    @Override
    public void addNullVoteForPresident(Voter voter) {
        votersPresident.put(voter, 1);
        nullVotesPresident++;
    }

    @Override
    public void addVoteForFederalDeputy(Voter voter, FederalDeputy federalDeputy) {
        if (tempFDVote.get(voter) != null) {
            tempFDVote.remove(voter);
        } else {
            tempFDVote.put(voter, federalDeputy);
        }
        votersFederalDeputy.put(voter, votersFederalDeputy.getOrDefault(voter, 0) + 1);
        federalDeputy.numVotes++;
    }

    @Override
    public void addProtestVoteForFederalDeputy(Voter voter) {
        votersFederalDeputy.put(voter, votersFederalDeputy.getOrDefault(voter, 0) + 1);
        protestVotesFederalDeputy++;
    }

    @Override
    public void addNullVoteForFederalDeputy(Voter voter) {
        votersFederalDeputy.put(voter, votersFederalDeputy.getOrDefault(voter, 0) + 1);
        nullVotesFederalDeputy++;
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

    public int getNullVotesPresident() {
        return nullVotesPresident;
    }

    public int getNullVotesFederalDeputy() {
        return nullVotesFederalDeputy;
    }

    public int getProtestVotesPresident() {
        return protestVotesPresident;
    }

    public int getProtestVotesFederalDeputy() {
        return protestVotesFederalDeputy;
    }
}
