import data.repositories.HashFederalDeputyRepository;
import data.repositories.HashPresidentRepository;
import data.repositories.HashTSEProfessionalRepository;
import data.repositories.HashVoteRepository;
import data.repositories.HashVoterRepository;
import domain.ElectionController;
import view.UrnaCli;

public class Application {
    public static void main(String[] args) {
        
        ElectionController currElection = ElectionController.getInstance(
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
