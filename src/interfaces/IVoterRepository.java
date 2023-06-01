package interfaces;

import models.Voter;

public interface IVoterRepository {
    Voter getByElectoralCard(String electoralCard);
    void addVoter(String electoralCard, Voter voter);
    void preLoad();
}
