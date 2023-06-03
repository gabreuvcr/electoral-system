package domain.interfaces;

import domain.Voter;

public interface IVoterRepository {
    Voter getByElectoralCard(String electoralCard);
    void addVoter(String electoralCard, Voter voter);
    void preLoad();
}
