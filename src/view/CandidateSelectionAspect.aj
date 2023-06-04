import java.util.List;

import domain.Voter;
import domain.President;
import domain.FederalDeputy;
import view.UrnaCli;

public privileged aspect CandidateSelectionAspect {
	
	public pointcut showAvailablePresidents(UrnaCli urna, Voter voter) :
		call(private boolean UrnaCli.votePresident(Voter)) && target(urna) && args(voter);
	
	before(UrnaCli urna, Voter voter) : showAvailablePresidents(urna, voter) {
		List<President> candidates = urna.currElection.getPresidents();
        
		for (President candidate : candidates) {
            System.out.println(candidate.getNumber() + " - " + candidate.getName() + " do " + candidate.getParty() + "\n");
        }
	}
	
	public pointcut showAvailableFederalDeputies(UrnaCli urna, Voter voter, int counter) :
		call(private boolean UrnaCli.voteFederalDeputy(Voter, int)) && target(urna) && args(voter, counter);
	
	before(UrnaCli urna, Voter voter, int counter) : showAvailableFederalDeputies(urna, voter, counter) {
		List<FederalDeputy> candidates = urna.currElection.getFederalDeputies();
        
		for (FederalDeputy candidate : candidates) {
            System.out.println(candidate.getNumber() + " - " + candidate.getName() + " do " + candidate.getParty() + "\n");
        }
	}
}