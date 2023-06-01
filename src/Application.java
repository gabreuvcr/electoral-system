import repositories.HashFederalDeputyRepository;
import repositories.HashPresidentRepository;
import repositories.HashTSEProfessionalRepository;
import repositories.HashVoterRepository;
import repositories.HashVotesRepository;
import services.Election;
import services.Urna;

public class Application {
    public static void main(String[] args) {
        String electionPassword = "password";

        Election currentElection = new Election.Builder()
                .password(electionPassword)
                .presidentRepository(new HashPresidentRepository())
                .federalDeputyRepository(new HashFederalDeputyRepository())
                .votesRepository(new HashVotesRepository())
                .build();

        Urna urna = new Urna(
            currentElection,
            new HashTSEProfessionalRepository(),
            new HashVoterRepository()
        );
        urna.init();
    }
}
