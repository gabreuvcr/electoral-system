package domain;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class ElectionResult {
    public static String produceResult(
        Map<Integer, President> presidentCandidates,
        Map<String, FederalDeputy> federalDeputyCandidates,
        int protestVotesPresident,
        int nullVotesPresident,
        int protestVotesFederalDeputy,
        int nullVotesFederalDeputy
    ) {
        var decimalFormater = new DecimalFormat("0.00");
        var presidentRank = new ArrayList<President>();
        var federalDeputyRank = new ArrayList<FederalDeputy>();

        var builder = new StringBuilder();

        builder.append("Resultado da eleicao:\n");

        int totalVotesP = protestVotesPresident + nullVotesPresident;
        for (Map.Entry<Integer, President> candidateEntry 
                : presidentCandidates.entrySet()) {
            President candidate = candidateEntry.getValue();
            totalVotesP += candidate.numVotes;
            presidentRank.add(candidate);
        }

        int totalVotesFD = protestVotesFederalDeputy + nullVotesFederalDeputy;
        for (Map.Entry<String, FederalDeputy> candidateEntry 
                : federalDeputyCandidates.entrySet()) {
            FederalDeputy candidate = candidateEntry.getValue();
            totalVotesFD += candidate.numVotes;
            federalDeputyRank.add(candidate);
        }

        var sortedFederalDeputyRank = federalDeputyRank.stream()
                .sorted((o1, o2) -> o1.numVotes == o2.numVotes ? 0 : o1.numVotes < o2.numVotes ? 1 : -1)
                .collect(Collectors.toList());

        var sortedPresidentRank = presidentRank.stream()
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
        builder.append("\n\n  Presidente eleito:\n");
        builder.append("  " + electPresident.name + " do " + electPresident.party + " com "
                + decimalFormater.format((double) electPresident.numVotes / (double) totalVotesP * 100)
                + "% dos votos\n");
        builder.append("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");

        builder.append("\n\n  Votos deputado federal:\n");
        builder.append("  Votos nulos: " + nullVotesFederalDeputy + " ("
                + decimalFormater.format((double) nullVotesFederalDeputy / (double) totalVotesFD * 100) + "%)\n");
        builder.append("  Votos brancos: " + protestVotesFederalDeputy + " ("
                + decimalFormater.format((double) protestVotesFederalDeputy / (double) totalVotesFD * 100) + "%)\n");
        builder.append("  Total: " + totalVotesFD + "\n");
        builder.append("\tNumero - Partido - Nome - Estado - Votos - % dos votos totais\n");
        for (FederalDeputy candidate : sortedFederalDeputyRank) {
            builder.append(
                    "\t" + candidate.number + " - " + candidate.party + " - " + candidate.state + " - " + candidate.name
                            + " - "
                            + candidate.numVotes + " - "
                            + decimalFormater.format((double) candidate.numVotes / (double) totalVotesFD * 100)
                            + "%\n");
        }

        FederalDeputy firstDeputy = sortedFederalDeputyRank.get(0);
        FederalDeputy secondDeputy = sortedFederalDeputyRank.get(1);
        builder.append("\n\n  Deputados eleitos:\n");
        builder.append("  1º " + firstDeputy.name + " do " + firstDeputy.party + " com "
                + decimalFormater.format((double) firstDeputy.numVotes / (double) totalVotesFD * 100)
                + "% dos votos\n");
        builder.append("  2º " + secondDeputy.name + " do " + secondDeputy.party + " com "
                + decimalFormater.format((double) secondDeputy.numVotes / (double) totalVotesFD * 100)
                + "% dos votos\n");

        return builder.toString();
    }
}
