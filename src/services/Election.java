package services;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import errors.StopTrap;
import errors.Warning;
import interfaces.IFederalDeputyRepository;
import interfaces.IPresidentRepository;
import interfaces.IVoteRepository;
import models.Candidate;
import models.FederalDeputy;
import models.President;
import models.Voter;

import java.text.DecimalFormat;

public class Election {
    private final String password;
    private boolean status;
    private int nullPresidentVotes;
    private int nullFederalDeputyVotes;
    private int presidentProtestVotes;
    private int federalDeputyProtestVotes;
    private IVoteRepository voteRepository;
    private IPresidentRepository presidentRepository;
    private IFederalDeputyRepository federalDeputyRepository;
    private static Election instance;

    private Election(
        String password, 
        IPresidentRepository presidentRepository,
        IFederalDeputyRepository federalDeputyRepository,
        IVoteRepository voteRepository
    ) {
        this.password = password;
        this.presidentRepository = presidentRepository;
        this.federalDeputyRepository = federalDeputyRepository;
        this.voteRepository = voteRepository;
        this.status = false;
        this.nullFederalDeputyVotes = 0;
        this.nullPresidentVotes = 0;
        this.presidentProtestVotes = 0;
        this.federalDeputyProtestVotes = 0;
    }

    public static Election getInstance(
        String password, 
        IPresidentRepository presidentRepository,
        IFederalDeputyRepository federalDeputyRepository,
        IVoteRepository voteRepository
    ) {
        if (instance == null) {
            instance = new Election(
                password, 
                presidentRepository,
                federalDeputyRepository,
                voteRepository
            );
        }
        return instance;
    }

    private Boolean isValid(String password) {
        return this.password.equals(password);
    }

    public void computeVote(Candidate candidate, Voter voter) {
        if (candidate instanceof President) {
            if (this.voteRepository.alreadyVotedForPresident(voter)) {
                throw new StopTrap("Você não pode votar mais de uma vez para presidente");
            }

            candidate.numVotes++;
            this.voteRepository.addVoteForPresident(voter);
        } else if (candidate instanceof FederalDeputy) {
            if (this.voteRepository.alreadyVotedForFederalDeputy(voter)) {
                throw new StopTrap("Você não pode votar mais de uma vez para deputado federal");
            }

            if (this.voteRepository.isRepeatFederalDeputy(voter, candidate)) {
                throw new Warning("Você não pode votar mais de uma vez em um mesmo candidato");
            }

            candidate.numVotes++;
            this.voteRepository.addVoteForFederalDeputy(voter, candidate);
        }
    };

    public void computeNullVote(String type, Voter voter) {
        if (type.equals("President")) {
            if (this.voteRepository.alreadyVotedForPresident(voter)) {
                throw new StopTrap("Você não pode votar mais de uma vez para presidente");
            }

            this.nullPresidentVotes++;
            this.voteRepository.addVoteForPresident(voter);
        } else if (type.equals("FederalDeputy")) {
            if (this.voteRepository.alreadyVotedForFederalDeputy(voter)) {
                throw new StopTrap("Você não pode votar mais de uma vez para deputado federal");
            }

            this.nullFederalDeputyVotes++;
            this.voteRepository.addVoteForFederalDeputy(voter);
        }
    }

    public void computeProtestVote(String type, Voter voter) {
        if (type.equals("President")) {
            if (this.voteRepository.alreadyVotedForPresident(voter)) {
                throw new StopTrap("Você não pode votar mais de uma vez para presidente");
            }

            this.presidentProtestVotes++;
            this.voteRepository.addVoteForPresident(voter);
        } else if (type.equals("FederalDeputy")) {
            if (this.voteRepository.alreadyVotedForFederalDeputy(voter)) {
                throw new StopTrap("Você não pode votar mais de uma vez para deputado federal");
            }

            this.federalDeputyProtestVotes++;
            this.voteRepository.addVoteForFederalDeputy(voter);
        }
    }

    public boolean getStatus() {
        return this.status;
    }

    public void start(String password) {
        if (!isValid(password)) {
            throw new Warning("Senha inválida");
        }

        this.status = true;
    }

    public void finish(String password) {
        if (!isValid(password)) {
            throw new Warning("Senha inválida");
        }

        this.status = false;
    }

    public President getPresidentByNumber(int number) {
        return this.presidentRepository.getByNumber(number);
    }

    public void addPresidentCandidate(President candidate, String password) {
        if (!isValid(password)) {
            throw new Warning("Senha inválida");
        }

        this.presidentRepository.addCandidate(candidate);
    }

    public void removePresidentCandidate(President candidate, String password) {
        if (!isValid(password)) {
            throw new Warning("Senha inválida");
        }

        this.presidentRepository.removeCandidate(candidate);
    }

    public FederalDeputy getFederalDeputyByNumber(String state, int number) {
        return this.federalDeputyRepository.getByNumber(state + number);
    }

    public void addFederalDeputyCandidate(FederalDeputy candidate, String password) {
        if (!isValid(password)) {
            throw new Warning("Senha inválida");
        }

        this.federalDeputyRepository.addCandidate(candidate);
    }

    public void removeFederalDeputyCandidate(FederalDeputy candidate, String password) {
        if (!isValid(password)) {
            throw new Warning("Senha inválida");
        }

        this.federalDeputyRepository.removeCandidate(candidate);
    }

    public String getResults(String password) {
        if (!isValid(password)) {
            throw new Warning("Senha inválida");
        }

        if (this.status) {
            throw new StopTrap("Eleição ainda não finalizou, não é possível gerar o resultado");
        }

        var decimalFormater = new DecimalFormat("0.00");
        var presidentRank = new ArrayList<President>();
        var federalDeputyRank = new ArrayList<FederalDeputy>();

        var builder = new StringBuilder();

        builder.append("Resultado da eleicao:\n");

        int totalVotesP = presidentProtestVotes + nullPresidentVotes;
        for (Map.Entry<Integer, President> candidateEntry 
                : this.presidentRepository.getCandidates().entrySet()) {
            President candidate = candidateEntry.getValue();
            totalVotesP += candidate.numVotes;
            presidentRank.add(candidate);
        }

        int totalVotesFD = federalDeputyProtestVotes + nullFederalDeputyVotes;
        for (Map.Entry<String, FederalDeputy> candidateEntry 
                : this.federalDeputyRepository.getCandidates().entrySet()) {
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
        builder.append("  Votos nulos: " + nullPresidentVotes + " ("
                + decimalFormater.format((double) nullPresidentVotes / (double) totalVotesFD * 100) + "%)\n");
        builder.append("  Votos brancos: " + presidentProtestVotes + " ("
                + decimalFormater.format((double) presidentProtestVotes / (double) totalVotesFD * 100) + "%)\n");
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
        builder.append("  Votos nulos: " + nullFederalDeputyVotes + " ("
                + decimalFormater.format((double) nullFederalDeputyVotes / (double) totalVotesFD * 100) + "%)\n");
        builder.append("  Votos brancos: " + federalDeputyProtestVotes + " ("
                + decimalFormater.format((double) federalDeputyProtestVotes / (double) totalVotesFD * 100) + "%)\n");
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
