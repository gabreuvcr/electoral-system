import java.util.List;

public aspect CandidateSelectionAspect {
    pointcut chooseCandidatePresident(int candidateNumber) : execution(* votePresident(Voter)) && args(candidateNumber);
    pointcut chooseCandidateFederalDeputy(int candidateId) : execution(* voteFederalDeputy(Voter, int)) && args(candidateId);

    before(int candidateNumber) : chooseCandidatePresident(candidateNumber) {
        showAvailableCandidates("president", candidateNumber);
    }

    before(int candidateId) : chooseCandidateFederalDeputy(candidateId) {
        showAvailableCandidates("federalDeputy", candidateId);
    }

    private void showAvailableCandidates(String position, int candidateId) {
        if (position.equals("president")) {
            List<President> candidates = currElection.getPresidents();
            System.out.println("Candidatos a presidente:\n");
            for (President candidate : candidates) {
                if (candidate.getNumber() == candidateId) {
                    System.out.println("* " + candidate.getNumber() + " - " + candidate.getName() + " do " + candidate.getParty() + "\n");
                } else {
                    System.out.println(candidate.getNumber() + " - " + candidate.getName() + " do " + candidate.getParty() + "\n");
                }
            }
        } else if (position.equals("federalDeputy")) {
            List<FederalDeputy> candidates = currElection.getFederalDeputies();
            System.out.println("Candidatos a deputado federal:\n");
            for (FederalDeputy candidate : candidates) {
                if (candidate.getId() == candidateId) {
                    System.out.println("* " + candidate.getNumber() + " - " + candidate.getName() + " do " + candidate.getParty() + "\n");
                } else {
                    System.out.println(candidate.getNumber() + " - " + candidate.getName() + " do " + candidate.getParty() + "\n");
                }
            }
        }
    }
}