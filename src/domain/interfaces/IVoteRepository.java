package domain.interfaces;

import domain.Candidate;
import domain.Voter;

public interface IVoteRepository {
    void addVoteForPresident(Voter voter);
    void addVoteForFederalDeputy(Voter voter);
    void addVoteForFederalDeputy(Voter voter, Candidate candidate);
    boolean alreadyVotedForPresident(Voter voter);
    boolean alreadyVotedForFederalDeputy(Voter voter);
    boolean isRepeatFederalDeputy(Voter voter, Candidate candidate);
}
