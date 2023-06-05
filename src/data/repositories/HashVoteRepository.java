package data.repositories;

import java.util.HashMap;
import java.util.Map;

import domain.Candidate;
import domain.FederalDeputy;
import domain.President;
import domain.Voter;
import domain.interfaces.IVoteRepository;
import domain.Governor;

public class HashVoteRepository implements IVoteRepository{
    private int nullVotesPresident;
    private int nullVotesFederalDeputy;
    private int nullVotesGovernor;
    private int nullVotesStateDeputy;
    private int protestVotesPresident;
    private int protestVotesGovernor;
    private int protestVotesFederalDeputy;
    private int protestVotesStateDeputy;
    private Map<Voter, Integer> votersPresident = new HashMap<Voter, Integer>();
    private Map<Voter, Integer> votersGovernor = new HashMap<Voter, Integer>();
    private Map<Voter, Integer> votersStateDeputy = new HashMap<Voter, Integer>();
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
    public boolean alreadyVotedForPresident(Voter voter) {
        if (votersPresident.get(voter) != null && votersPresident.get(voter) >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public void addVoteForGovernor(Voter voter, Governor Governor) {
        votersGovernor.put(voter, 1);
        Governor.numVotes++;
    }

    @Override 
    public void addProtestVoteForGovernor(Voter voter) {
        votersGovernor.put(voter, 1);
        protestVotesGovernor++;
    }

    @Override
    public void addNullVoteForGovernor(Voter voter) {
        votersGovernor.put(voter, 1);
        nullVotesGovernor++;
    }
    
    @Override
    public boolean alreadyVotedForGovernor(Voter voter) {
        if (votersGovernor.get(voter) != null && votersGovernor.get(voter) >= 1) {
            return true;
        }
        return false;
    }
    
    @Override
    public void addVoteForFederalDeputy(Voter voter, FederalDeputy federalDeputy) {
        tempFDVote.put(voter, federalDeputy);
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

    @Override
    public void addVoteForStateDeputy(Voter voter, Candidate candidate) {
        votersStateDeputy.put(voter, votersStateDeputy.getOrDefault(voter, 0) + 1);
        candidate.numVotes++;
    }

    @Override
    public void addProtestVoteForStateDeputy(Voter voter) {
        votersStateDeputy.put(voter, votersStateDeputy.getOrDefault(voter, 0) + 1);
        protestVotesStateDeputy++;
    }

    @Override
    public void addNullVoteForStateDeputy(Voter voter) {
        votersStateDeputy.put(voter, votersStateDeputy.getOrDefault(voter, 0) + 1);
        nullVotesStateDeputy++;
    }

    @Override
    public boolean alreadyVotedForStateDeputy(Voter voter) {
        if (votersStateDeputy.get(voter) != null && votersStateDeputy.get(voter) >= 2) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isRepeatStateDeputy(Voter voter, Candidate candidate) {
        if (votersStateDeputy.get(voter) != null && votersStateDeputy.get(voter).equals(candidate)) {
            return true;
        }
        return false;
    }

    public int getNullVotesPresident() {
        return nullVotesPresident;
    }
    
    public int getNullVotesGovernor() {
        return nullVotesGovernor;
    }

    public int getNullVotesFederalDeputy() {
        return nullVotesFederalDeputy;
    }

    public int getNullVotesStateDeputy() {
        return nullVotesStateDeputy;
    }

    public int getProtestVotesPresident() {
        return protestVotesPresident;
    }
    
    public int getProtestVotesGovernor() {
        return protestVotesGovernor;
    }

    public int getProtestVotesFederalDeputy() {
        return protestVotesFederalDeputy;
    }

    public int getProtestVotesStateDeputy() {
        return protestVotesStateDeputy;
    }
}
