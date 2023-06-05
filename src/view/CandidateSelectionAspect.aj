import java.util.List;

import domain.Voter;
import domain.President;
import domain.FederalDeputy;
import domain.Governor;
import domain.StateDeputy;
import view.UrnaCli;

public privileged aspect CandidateSelectionAspect {
	// President candidates
	public pointcut showAvailablePresidents(UrnaCli urna, Voter voter) :
		call(private boolean UrnaCli.votePresident(Voter)) && target(urna) && args(voter);

	before(UrnaCli urna, Voter voter) : showAvailablePresidents(urna, voter) {
		//#if ExibicaoCandidatos
		List<President> candidates = urna.currElection.getPresidents();
		System.out.println("Esses são os candidatos a Presidente:\n");
		System.out.println("Número | Nome | Partido\n");

		for (President candidate : candidates) {
            System.out.println(candidate.getNumber() + " - " + candidate.getName() + " do " + candidate.getParty());
        }

		System.out.println("\n");
		//#endif
	}

	// Federal Deputy candidates
	public pointcut showAvailableFederalDeputies(UrnaCli urna, Voter voter, int counter) :
		call(private boolean UrnaCli.voteFederalDeputy(Voter, int)) && target(urna) && args(voter, counter);

	before(UrnaCli urna, Voter voter, int counter) : showAvailableFederalDeputies(urna, voter, counter) {
		//#if ExibicaoCandidatos
		List<FederalDeputy> candidates = urna.currElection.getFederalDeputies();
        System.out.println("Esses são os candidatos a Deputado Federal:\n");
		System.out.println("Número | Nome | Partido\n");

		for (FederalDeputy candidate : candidates) {
            System.out.println(candidate.getNumber() + " - " + candidate.getName() + " do " + candidate.getParty());
        }
		//#endif

		System.out.println("\n");
	}

	// Governor candidates
	public pointcut showAvailableGovernors(UrnaCli urna, Voter voter) :
		call(private boolean UrnaCli.voteGovernor(Voter)) && target(urna) && args(voter);

	before(UrnaCli urna, Voter voter) : showAvailableGovernors(urna, voter) {
		List<Governor> candidates = urna.currElection.getGovernors();
		System.out.println("Esses são os candidatos a Governador:\n");
		System.out.println("Número | Nome | Partido\n");

		for (Governor candidate : candidates) {
			System.out.println(candidate.getNumber() + " - " + candidate.getName() + " do " + candidate.getParty());
		}

		System.out.println("\n");
	}

	// State Deputy candidates
	public pointcut showAvailableStateDeputies(UrnaCli urna, Voter voter, int counter) :
		call(private boolean UrnaCli.voteStateDeputy(Voter, int)) && target(urna) && args(voter, counter);

	before(UrnaCli urna, Voter voter, int counter) : showAvailableStateDeputies(urna, voter, counter) {
		List<FederalDeputy> candidates = urna.currElection.getStateDeputies();
		System.out.println("Esses são os candidatos a Deputado Estadual:\n");
		System.out.println("Número | Nome | Partido\n");

		for (FederalDeputy candidate : candidates) {
			System.out.println(candidate.getNumber() + " - " + candidate.getName() + " do " + candidate.getParty());
		}

		System.out.println("\n");
	}
}