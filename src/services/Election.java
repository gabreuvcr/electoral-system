package services;

import errors.StopTrap;
import errors.Warning;
import interfaces.IFederalDeputyRepository;
import interfaces.IPresidentRepository;
import interfaces.ITSEProfessionalRepository;
import interfaces.IVoteRepository;
import interfaces.IVoterRepository;
import models.Candidate;
import models.FederalDeputy;
import models.President;
import models.TSEProfessional;
import models.Voter;

public class Election {
    private final String password;
    private boolean status;
    private int nullPresidentVotes;
    private int nullFederalDeputyVotes;
    private int presidentProtestVotes;
    private int federalDeputyProtestVotes;
    private final IVoteRepository voteRepository;
    private final IPresidentRepository presidentRepository;
    private final IFederalDeputyRepository federalDeputyRepository;
    private final ITSEProfessionalRepository tseProfessionalRepository;
    private final IVoterRepository voterRepository;
    private static Election instance;

    private Election(
        String password, 
        IPresidentRepository presidentRepository,
        IFederalDeputyRepository federalDeputyRepository,
        IVoteRepository voteRepository,
        ITSEProfessionalRepository tseProfessionalRepository,
        IVoterRepository voterRepository
    ) {
        this.password = password;
        this.presidentRepository = presidentRepository;
        this.federalDeputyRepository = federalDeputyRepository;
        this.voteRepository = voteRepository;
        this.tseProfessionalRepository = tseProfessionalRepository;
        this.voterRepository = voterRepository;
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
        IVoteRepository voteRepository,
        ITSEProfessionalRepository tseProfessionalRepository,
        IVoterRepository voterRepository
    ) {
        if (instance == null) {
            instance = new Election(
                password, 
                presidentRepository,
                federalDeputyRepository,
                voteRepository,
                tseProfessionalRepository,
                voterRepository
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

    public Voter getVoterByElectorCard(String electoralCard) {
        return this.voterRepository.getByElectoralCard(electoralCard);
    }

    public TSEProfessional getTSEProfessionalByUser(String user) {
        return this.tseProfessionalRepository.getByUser(user);
    }

    public String getResults(String password) {
        if (!isValid(password)) {
            throw new Warning("Senha inválida");
        }

        if (this.status) {
            throw new StopTrap("Eleição ainda não finalizou, não é possível gerar o resultado");
        }

        return ElectionResult.produceResult(
            this.presidentRepository.getCandidates(), 
            this.federalDeputyRepository.getCandidates(), 
            presidentProtestVotes, 
            nullPresidentVotes, 
            federalDeputyProtestVotes,
            nullFederalDeputyVotes
        );
    }
}
