package domain.interfaces;

import domain.Candidate;
import domain.FederalDeputy;
import domain.President;
import domain.StateDeputy;
import domain.Voter;
import domain.Governor;

public interface IVoteRepository {
    void addVoteForPresident(Voter voter, President president);
    void addProtestVoteForPresident(Voter voter);
    void addVoteForGovernor(Voter voter, Governor governor);
    void addProtestVoteForGovernor(Voter voter);
    void addNullVoteForPresident(Voter voter);
    void addNullVoteForGovernor(Voter voter);
    boolean alreadyVotedForPresident(Voter voter);
    boolean alreadyVotedForGovernor(Voter voter);
    int getNullVotesPresident();
    int getProtestVotesPresident();
    int getNullVotesGovernor();
    int getProtestVotesGovernor();


    void addVoteForFederalDeputy(Voter voter, FederalDeputy federalDeputy);
    void addProtestVoteForFederalDeputy(Voter voter);
    void addNullVoteForFederalDeputy(Voter voter);
    boolean alreadyVotedForFederalDeputy(Voter voter);
    boolean isRepeatFederalDeputy(Voter voter, Candidate candidate);
    int getNullVotesFederalDeputy();
    int getProtestVotesFederalDeputy();


    void addVoteForStateDeputy(Voter voter, Candidate candidate);
    void addProtestVoteForStateDeputy(Voter voter);
    void addNullVoteForStateDeputy(Voter voter);
    boolean alreadyVotedForStateDeputy(Voter voter);
    boolean isRepeatStateDeputy(Voter voter, Candidate candidate);
    int getNullVotesStateDeputy();
    int getProtestVotesStateDeputy();
}
