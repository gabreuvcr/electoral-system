import data.repositories.HashFederalDeputyRepository;
import data.repositories.HashPresidentRepository;
import data.repositories.HashTSEProfessionalRepository;
import data.repositories.HashVoteRepository;
import data.repositories.HashVoterRepository;
import domain.Election;
import view.UrnaCli;

public class Application {
    public static void main(String[] args) {
        
        Election currElection = Election.getInstance(
            "password",
            new HashPresidentRepository(),
            new HashFederalDeputyRepository(),
            new HashVoteRepository(),
            new HashTSEProfessionalRepository(),
            new HashVoterRepository()
        );

        UrnaCli urnaCli = new UrnaCli(currElection);
        urnaCli.init();
    }
}
