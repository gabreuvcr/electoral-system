package domain;

import java.util.ArrayList;
import java.util.List;

import domain.interfaces.IFederalDeputyRepository;
import domain.interfaces.IPresidentRepository;
import domain.interfaces.ITSEProfessionalRepository;
import domain.interfaces.IVoteRepository;
import domain.interfaces.IVoterRepository;
import errors.StopTrap;
import errors.Warning;

public class ElectionController {
    private final String password;
    private boolean status;
    private final IVoteRepository voteRepository;
    private final IPresidentRepository presidentRepository;
    private final IFederalDeputyRepository federalDeputyRepository;
    private final ITSEProfessionalRepository tseProfessionalRepository;
    private final IVoterRepository voterRepository;
    private static ElectionController instance;

    private ElectionController(
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
    }

    public static ElectionController getInstance(
        String password, 
        IPresidentRepository presidentRepository,
        IFederalDeputyRepository federalDeputyRepository,
        IVoteRepository voteRepository,
        ITSEProfessionalRepository tseProfessionalRepository,
        IVoterRepository voterRepository
    ) {
        if (instance == null) {
            instance = new ElectionController(
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

    public void voteProtestForPresident(Voter voter) {
        if (this.voteRepository.alreadyVotedForPresident(voter)) {
            throw new StopTrap("Você não pode votar mais de uma vez para presidente");
        }

        this.voteRepository.addProtestVoteForPresident(voter);
    }

    public void voteNullForPresident(Voter voter) {
        if (this.voteRepository.alreadyVotedForPresident(voter)) {
            throw new StopTrap("Você não pode votar mais de uma vez para presidente");
        }

        this.voteRepository.addNullVoteForPresident(voter);
    }

    public void voteForPresident(Voter voter, President president) {
        if (this.voteRepository.alreadyVotedForPresident(voter)) {
            throw new StopTrap("Você não pode votar mais de uma vez para presidente");
        }

        this.voteRepository.addVoteForPresident(voter, president);
    }

    public void voteProtestForFederalDeputy(Voter voter) {
        if (this.voteRepository.alreadyVotedForFederalDeputy(voter)) {
            throw new StopTrap("Você não pode votar mais de uma vez para deputado federal");
        }

        this.voteRepository.addProtestVoteForFederalDeputy(voter);
    }

    public void voteNullForFederalDeputy(Voter voter) {
        if (this.voteRepository.alreadyVotedForFederalDeputy(voter)) {
            throw new StopTrap("Você não pode votar mais de uma vez para deputado federal");
        }

        this.voteRepository.addNullVoteForFederalDeputy(voter);
    }

    public void voteForFederalDeputy(Voter voter, FederalDeputy federalDeputy) {
        if (this.voteRepository.alreadyVotedForFederalDeputy(voter)) {
            throw new StopTrap("Você não pode votar mais de uma vez para deputado federal");
        }

        if (this.voteRepository.isRepeatFederalDeputy(voter, federalDeputy)) {
            throw new Warning("Você não pode votar mais de uma vez em um mesmo candidato");
        }

        this.voteRepository.addVoteForFederalDeputy(voter, federalDeputy);
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

    public void addCandidate(Candidate candidate, String password) {
        if (!isValid(password)) {
            throw new Warning("Senha inválida");
        }

        if (candidate instanceof President) {
            this.presidentRepository.addCandidate((President) candidate);
        } else if (candidate instanceof FederalDeputy) {
            this.federalDeputyRepository.addCandidate((FederalDeputy) candidate);
        }
    }

    public void removeCandidate(Candidate candidate, String password) {
        if (!isValid(password)) {
            throw new Warning("Senha inválida");
        }

        if (candidate instanceof President) {
            this.presidentRepository.removeCandidate((President) candidate);
        } else if (candidate instanceof FederalDeputy) {
            this.federalDeputyRepository.removeCandidate((FederalDeputy) candidate);
        }
    }

    public FederalDeputy getFederalDeputyByNumber(String state, int number) {
        return this.federalDeputyRepository.getByNumber(state + number);
    }

    public Voter getVoterByElectorCard(String electoralCard) {
        return this.voterRepository.getByElectoralCard(electoralCard);
    }

    public TSEProfessional getTSEProfessionalByUser(String user) {
        return this.tseProfessionalRepository.getByUser(user);
    }
    
    public List<President> getPresidents() {
        return new ArrayList<President>(this.presidentRepository.getCandidates().values());
    }

    public List<FederalDeputy> getFederalDeputies() {
        return new ArrayList<FederalDeputy>(this.federalDeputyRepository.getCandidates().values());
    }

    public String getResults(String password) {
        if (!isValid(password)) {
            throw new Warning("Senha inválida");
        }

        if (this.status) {
            throw new StopTrap("Eleição ainda não finalizou, não é possível gerar o resultado");
        }

        return ElectionResult.produce(
            this.presidentRepository.getCandidates(), 
            this.federalDeputyRepository.getCandidates(), 
            this.voteRepository.getProtestVotesPresident(), 
            this.voteRepository.getNullVotesPresident(), 
            this.voteRepository.getProtestVotesFederalDeputy(),
            this.voteRepository.getNullVotesFederalDeputy()
        );
    }
}
