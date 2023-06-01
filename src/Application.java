import repositories.HashFederalDeputyRepository;
import repositories.HashPresidentRepository;
import repositories.HashTSEProfessionalRepository;
import repositories.HashVoterRepository;
import repositories.HashVoteRepository;
import services.Election;
import services.Urna;

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

        Urna urna = new Urna(currElection);
        urna.init();
    }
}
