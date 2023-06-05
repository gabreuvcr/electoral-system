package domain;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class ElectionResult {
    public static String produce(
        Map<Integer, President> presidentCandidates,
        Map<String, Governor> governorCandidates,
        Map<String, FederalDeputy> federalDeputyCandidates,
        Map<String, StateDeputy> stateDeputyCandidates,
        int protestVotesPresident,
        int nullVotesPresident,
        int protestVotesGovernor,
        int nullVotesGovernor,
        int protestVotesFederalDeputy,
        int nullVotesFederalDeputy,
        int protestVotesStateDeputy,
        int nullVotesStateDeputy
    ) {
        var decimalFormater = new DecimalFormat("0.00");
        var presidentRank = new ArrayList<President>();
        var federalDeputyRank = new ArrayList<FederalDeputy>();
        var governorRank = new ArrayList<Governor>();
        var stateDeputyRank = new ArrayList<StateDeputy>();

        var builder = new StringBuilder();

        builder.append("Resultado da eleicao:\n");

        int totalVotesP = protestVotesPresident + nullVotesPresident;
        for (Map.Entry<Integer, President> candidateEntry : presidentCandidates.entrySet()) {
            President candidate = candidateEntry.getValue();
            totalVotesP += candidate.numVotes;
            presidentRank.add(candidate);
        }

        int totalVotesFD = protestVotesFederalDeputy + nullVotesFederalDeputy;
        for (Map.Entry<String, FederalDeputy> candidateEntry : federalDeputyCandidates.entrySet()) {
            FederalDeputy candidate = candidateEntry.getValue();
            totalVotesFD += candidate.numVotes;
            federalDeputyRank.add(candidate);
        }

        int totalVotesG = protestVotesGovernor + nullVotesGovernor;
        for (Map.Entry<String, Governor> candidateEntry : governorCandidates.entrySet()) {
            Governor candidate = candidateEntry.getValue();
            totalVotesG += candidate.numVotes;
            governorRank.add(candidate);
        }

        int totalVotesSD = protestVotesStateDeputy + nullVotesStateDeputy;
        for (Map.Entry<String, StateDeputy> stateCandidateEntry : stateDeputyCandidates.entrySet()) {
            StateDeputy stateCandidate = stateCandidateEntry.getValue();
            totalVotesSD += stateCandidate.numVotes;
            stateDeputyRank.add(stateCandidate);
        }

        var sortedFederalDeputyRank = federalDeputyRank.stream()
            .sorted((o1, o2) -> o1.numVotes == o2.numVotes ? 0 : o1.numVotes < o2.numVotes ? 1 : -1)
            .collect(Collectors.toList());

        var sortedPresidentRank = presidentRank.stream()
            .sorted((o1, o2) -> o1.numVotes == o2.numVotes ? 0 : o1.numVotes < o2.numVotes ? 1 : -1)
            .collect(Collectors.toList());

        var sortedGovernorRank = governorRank.stream()
            .sorted((o1, o2) -> o1.numVotes == o2.numVotes ? 0 : o1.numVotes < o2.numVotes ? 1 : -1)
            .collect(Collectors.toList());

        var sortedStateDeputyRank = stateDeputyRank.stream()
            .sorted((o1, o2) -> o1.numVotes == o2.numVotes ? 0 : o1.numVotes < o2.numVotes ? 1 : -1)
            .collect(Collectors.toList());

        builder.append("  Votos presidente:\n");
        builder.append("  Total: " + totalVotesP + "\n");
        builder.append("  Votos nulos: " + nullVotesPresident + " ("
            + decimalFormater.format((double) nullVotesPresident / (double) totalVotesFD * 100) + "%)\n");
        builder.append("  Votos brancos: " + protestVotesPresident + " ("
            + decimalFormater.format((double) protestVotesPresident / (double) totalVotesFD * 100) + "%)\n");
        builder.append("\tNumero - Partido - Nome  - Votos  - % dos votos totais\n");
        for (President candidate : sortedPresidentRank) {
            builder.append("\t" + candidate.number + " - " + candidate.party + " - " + candidate.name + " - "
                + candidate.numVotes + " - "
                + decimalFormater.format((double) candidate.numVotes / (double) totalVotesP * 100)
                + "%\n");
        }

        President electPresident = sortedPresidentRank.get(0);
        if (electPresident.numVotes == 0) {
            builder.append("\n\n  Presidente eleito:\n");
            builder.append("  Nenhum candidato foi eleito, pois não houve votos válidos\n");
        } else {
            builder.append("\n\n  Presidente eleito:\n");
            builder.append("  " + electPresident.name + " do " + electPresident.party + " com "
                + decimalFormater.format((double) electPresident.numVotes / (double) totalVotesP * 100)
                + "% dos votos\n");
                builder.append("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
        }

        builder.append("\n\n  Votos deputado federal:\n");
        builder.append("  Votos nulos: " + nullVotesFederalDeputy + " ("
            + decimalFormater.format((double) nullVotesFederalDeputy / (double) totalVotesFD * 100) + "%)\n");
        builder.append("  Votos brancos: " + protestVotesFederalDeputy + " ("
            + decimalFormater.format((double) protestVotesFederalDeputy / (double) totalVotesFD * 100)
            + "%)\n");
        builder.append("  Total: " + totalVotesFD + "\n");
        builder.append("\tNumero - Partido - Nome - Estado - Votos - % dos votos totais\n");
        for (FederalDeputy candidate : sortedFederalDeputyRank) {
            builder.append(
                "\t" + candidate.number + " - " + candidate.party + " - " + candidate.state + " - "
                + candidate.name
                + " - "
                + candidate.numVotes + " - "
                + decimalFormater.format((double) candidate.numVotes / (double) totalVotesFD * 100)
                + "%\n");
        }

        FederalDeputy firstDeputy = sortedFederalDeputyRank.get(0);
        FederalDeputy secondDeputy = sortedFederalDeputyRank.get(1);
        if (firstDeputy.numVotes == 0) {
            builder.append("\n\n  Deputados eleitos:\n");
            builder.append("  Nenhum candidato foi eleito, pois não houve votos válidos\n");
            return builder.toString();
        } else {
            builder.append("\n\n  Deputados eleitos:\n");
            builder.append("  1º " + firstDeputy.name + " do " + firstDeputy.party + " com "
                + decimalFormater.format((double) firstDeputy.numVotes / (double) totalVotesFD * 100)
                + "% dos votos\n");
            if (secondDeputy.numVotes != 0) {
                builder.append("  2º " + secondDeputy.name + " do " + secondDeputy.party + " com "
                    + decimalFormater.format((double) secondDeputy.numVotes / (double) totalVotesFD * 100)
                    + "% dos votos\n");
            }
        }

        builder.append("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");

        builder.append("  Votos governador:\n");
        builder.append("  Votos nulos: " + nullVotesGovernor + " ("
            + decimalFormater.format((double) nullVotesGovernor / (double) totalVotesG * 100) + "%)\n");
        builder.append("  Votos brancos: " + protestVotesGovernor + " ("
            + decimalFormater.format((double) protestVotesGovernor / (double) totalVotesG * 100) + "%)\n");
        builder.append("  Total: " + totalVotesG + "\n");
        builder.append("\tNumero - Partido - Nome - Estado - Votos - % dos votos totais\n");
        for (Governor candidate : sortedGovernorRank) {
            builder.append(
                "\t" + candidate.number + " - " + candidate.party + " - " + candidate.state + " - "
                + candidate.name
                + " - "
                + candidate.numVotes + " - "
                + decimalFormater.format((double) candidate.numVotes / (double) totalVotesG * 100)
                + "%\n");
        }

        builder.append("\n\n  Governador eleito:\n");
        Governor electGovernor = sortedGovernorRank.get(0);

        if (electGovernor.numVotes == 0) {
            builder.append("  Nenhum candidato foi eleito, pois não houve votos válidos\n");
        } else {
            builder.append("  " + electGovernor.name + " do " + electGovernor.party + " com "
                + decimalFormater.format((double) electGovernor.numVotes / (double) totalVotesG * 100)
                + "% dos votos\n");
        }

        builder.append("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");

        builder.append("  Votos deputado estadual:\n");
        builder.append("  Votos nulos: " + nullVotesStateDeputy + " ("
            + decimalFormater.format((double) nullVotesStateDeputy / (double) totalVotesSD * 100) + "%)\n");
        builder.append("  Votos brancos: " + protestVotesStateDeputy + " ("
            + decimalFormater.format((double) protestVotesStateDeputy / (double) totalVotesSD * 100)
            + "%)\n");
        builder.append("  Total: " + totalVotesSD + "\n");
        builder.append("\tNumero - Partido - Nome - Estado - Votos - % dos votos totais\n");
        for (StateDeputy candidate : sortedStateDeputyRank) {
            builder.append(
                "\t" + candidate.number + " - " + candidate.party + " - " + candidate.state + " - "
                + candidate.name
                + " - "
                + candidate.numVotes + " - "
                + decimalFormater.format((double) candidate.numVotes / (double) totalVotesSD * 100)
                + "%\n");
        }

        StateDeputy firstStateDeputy = sortedStateDeputyRank.get(0);
        StateDeputy secondStateDeputy = sortedStateDeputyRank.get(1);
        if (firstStateDeputy.numVotes == 0) {
            builder.append("\n\n  Deputados eleitos:\n");
            builder.append("  Nenhum candidato foi eleito, pois não houve votos válidos\n");
            return builder.toString();
        } else {
            builder.append("\n\n  Deputados eleitos:\n");
            builder.append("  1º " + firstStateDeputy.name + " do " + firstStateDeputy.party + " com "
                + decimalFormater.format((double) firstStateDeputy.numVotes / (double) totalVotesSD * 100)
                + "% dos votos\n");
            if (secondStateDeputy.numVotes != 0) {
                builder.append("  2º " + secondStateDeputy.name + " do " + secondStateDeputy.party + " com "
                    + decimalFormater.format((double) secondStateDeputy.numVotes / (double) totalVotesSD * 100)
                    + "% dos votos\n");
            }
        }

        builder.append("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");

        return builder.toString();
    }
}
