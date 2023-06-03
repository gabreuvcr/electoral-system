package domain.interfaces;

import domain.Candidate;
import domain.FederalDeputy;
import domain.President;
import domain.Voter;

public interface IVoteRepository {
    void addVoteForPresident(Voter voter, President president);
    void addProtestVoteForPresident(Voter voter);
    void addNullVoteForPresident(Voter voter);
    boolean alreadyVotedForPresident(Voter voter);
    int getNullVotesPresident();
    int getProtestVotesPresident();


    void addVoteForFederalDeputy(Voter voter, FederalDeputy federalDeputy);
    void addProtestVoteForFederalDeputy(Voter voter);
    void addNullVoteForFederalDeputy(Voter voter);
    boolean alreadyVotedForFederalDeputy(Voter voter);
    boolean isRepeatFederalDeputy(Voter voter, Candidate candidate);
    int getNullVotesFederalDeputy();
    int getProtestVotesFederalDeputy();
}
