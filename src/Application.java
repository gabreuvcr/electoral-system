import models.FederalDeputy;
import models.President;
import services.Election;
import services.Urna;

public class Application {
    public static void main(String[] args) {
        String electionPassword = "password";

        Election currentElection = new Election.Builder()
                .password(electionPassword)
                .build();

        loadCandidates(currentElection, electionPassword);

        Urna urna = new Urna(currentElection);
        urna.init();
    }
    
    public static void loadCandidates(Election currentElection, String electionPassword) {
        President presidentCandidate1 = new President.Builder()
                .name("João")
                .number(123)
                .party("PDS1")
                .build();
        President presidentCandidate2 = new President.Builder()
                .name("Maria")
                .number(124)
                .party("ED")
                .build();
        FederalDeputy federalDeputyCandidate1 = new FederalDeputy.Builder()
                .name("Carlos")
                .number(12345)
                .party("PDS1")
                .state("MG")
                .build();
        FederalDeputy federalDeputyCandidate2 = new FederalDeputy.Builder()
                .name("Cleber")
                .number(54321)
                .party("PDS2")
                .state("MG")
                .build();
        FederalDeputy federalDeputyCandidate3 = new FederalDeputy.Builder()
                .name("Sofia")
                .number(11211)
                .party("IHC")
                .state("MG")
                .build();

        currentElection.addPresidentCandidate(presidentCandidate1, electionPassword);
        currentElection.addPresidentCandidate(presidentCandidate2, electionPassword);
        currentElection.addFederalDeputyCandidate(federalDeputyCandidate1, electionPassword);
        currentElection.addFederalDeputyCandidate(federalDeputyCandidate2, electionPassword);
        currentElection.addFederalDeputyCandidate(federalDeputyCandidate3, electionPassword);
    }
}
