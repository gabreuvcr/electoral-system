import data.repositories.HashFederalDeputyRepository;
import data.repositories.HashPresidentRepository;
import data.repositories.HashStateDeputyRepository;
import data.repositories.HashTSEProfessionalRepository;
import data.repositories.HashVoteRepository;
import data.repositories.HashGovernorRepository;
import data.repositories.HashVoterRepository;
import domain.ElectionController;
import view.UrnaCli;

public class Application {
    public static void main(String[] args) {
    	ElectionController currElection;
    	//#if PresidenteDeputadoFederal 
//@        currElection = ElectionController.getInstance(
//@            "password",
//@            new HashPresidentRepository(), 
//@            new HashFederalDeputyRepository(),                
//@            new HashVoteRepository(),
//@            new HashTSEProfessionalRepository(),
//@            new HashVoterRepository()
//@        );
        //#elif GovernadorDeputadoEstadual
        currElection = ElectionController.getInstance(
                "password",    
                new HashGovernorRepository(),
                new HashStateDeputyRepository(),
                new HashVoteRepository(),
                new HashTSEProfessionalRepository(),
                new HashVoterRepository()
            );
        //#endif

        UrnaCli urnaCli = new UrnaCli(currElection);
        urnaCli.init();
    }
}
