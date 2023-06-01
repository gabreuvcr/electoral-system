package repositories;

import models.Candidate;
import models.Voter;

public interface IVotesRepository {
    void addVoteForPresident(Voter voter);
    void addVoteForFederalDeputy(Voter voter);
    void addVoteForFederalDeputy(Voter voter, Candidate candidate);
    boolean alreadyVotedForPresident(Voter voter);
    boolean alreadyVotedForFederalDeputy(Voter voter);
    boolean isRepeatFederalDeputy(Voter voter, Candidate candidate);
}
