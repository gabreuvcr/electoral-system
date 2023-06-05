package domain;

import java.util.ArrayList;
import java.util.List;

import domain.interfaces.IFederalDeputyRepository;
import domain.interfaces.IPresidentRepository;
import domain.interfaces.ITSEProfessionalRepository;
import domain.interfaces.IVoteRepository;
import domain.interfaces.IVoterRepository;
import domain.interfaces.IGovernorRepository;
import domain.interfaces.IStateDeputyRepository;

import errors.StopTrap;
import errors.Warning;

public class ElectionController {
    private final String password;
    private boolean status;
    private final IVoteRepository voteRepository;
    //#if PresidenteDeputadoFederal 
//@    private IPresidentRepository presidentRepository;
//@    private IFederalDeputyRepository federalDeputyRepository;
    //#elif GovernadorDeputadoEstadual 
    private IGovernorRepository governorRepository;
    private IStateDeputyRepository stateDeputyRepository;
    //#endif
    private final ITSEProfessionalRepository tseProfessionalRepository;
    private final IVoterRepository voterRepository;
    private static ElectionController instance;
    
    //#if PresidenteDeputadoFederal 
//@    private ElectionController(
//@        String password, 
//@        IPresidentRepository presidentRepository,
//@        IFederalDeputyRepository federalDeputyRepository,
//@        IVoteRepository voteRepository,
//@        ITSEProfessionalRepository tseProfessionalRepository,
//@        IVoterRepository voterRepository
//@    ) {
//@        this.password = password;
//@        this.presidentRepository = presidentRepository;
//@        this.federalDeputyRepository = federalDeputyRepository;
//@        this.voteRepository = voteRepository;
//@        this.tseProfessionalRepository = tseProfessionalRepository;
//@        this.voterRepository = voterRepository;
//@        this.status = false;
//@    }
//@
//@    public static ElectionController getInstance(
//@        String password, 
//@        IPresidentRepository presidentRepository,
//@        IFederalDeputyRepository federalDeputyRepository,
//@        IVoteRepository voteRepository,
//@        ITSEProfessionalRepository tseProfessionalRepository,
//@        IVoterRepository voterRepository
//@    ) {
//@        if (instance == null) {
//@            instance = new ElectionController(
//@                password,
//@                presidentRepository,
//@                federalDeputyRepository,
//@                voteRepository,
//@                tseProfessionalRepository,
//@                voterRepository
//@            );
//@        }
//@        return instance;
//@    }
    //#elif GovernadorDeputadoEstadual  
    private ElectionController(
        String password, 
        IGovernorRepository governorRepository,
        IStateDeputyRepository stateDeputyRepository,
        IVoteRepository voteRepository,
        ITSEProfessionalRepository tseProfessionalRepository,
        IVoterRepository voterRepository
    ) {
        this.password = password;
        this.governorRepository = governorRepository;
        this.stateDeputyRepository = stateDeputyRepository;
        this.voteRepository = voteRepository;
        this.tseProfessionalRepository = tseProfessionalRepository;
        this.voterRepository = voterRepository;
        this.status = false;
    }

    public static ElectionController getInstance(
        String password, 
        IGovernorRepository governorRepository,
        IStateDeputyRepository stateDeputyRepository,
        IVoteRepository voteRepository,
        ITSEProfessionalRepository tseProfessionalRepository,
        IVoterRepository voterRepository
    ) {
        if (instance == null) {
            instance = new ElectionController(
                password,
                governorRepository,
                stateDeputyRepository,
                voteRepository,
                tseProfessionalRepository,
                voterRepository
            );
        }
        return instance;
    }
    //#endif
    
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
    
    public void voteProtestForGovernor(Voter voter) {
        if (this.voteRepository.alreadyVotedForGovernor(voter)) {
            throw new StopTrap("Você não pode votar mais de uma vez para Governore");
        }

        this.voteRepository.addProtestVoteForGovernor(voter);
    }

    public void voteNullForGovernor(Voter voter) {
        if (this.voteRepository.alreadyVotedForGovernor(voter)) {
            throw new StopTrap("Você não pode votar mais de uma vez para Governore");
        }

        this.voteRepository.addNullVoteForGovernor(voter);
    }

    public void voteForGovernor(Voter voter, Governor Governor) {
        if (this.voteRepository.alreadyVotedForGovernor(voter)) {
            throw new StopTrap("Você não pode votar mais de uma vez para Governore");
        }

        this.voteRepository.addVoteForGovernor(voter, Governor);
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

    public void voteProtestForStateDeputy(Voter voter) {
        if (this.voteRepository.alreadyVotedForStateDeputy(voter)) {
            throw new StopTrap("Você não pode votar mais de uma vez para deputado estadual");
        }

        this.voteRepository.addProtestVoteForStateDeputy(voter);
    }

    public void voteNullForStateDeputy(Voter voter) {
        if (this.voteRepository.alreadyVotedForStateDeputy(voter)) {
            throw new StopTrap("Você não pode votar mais de uma vez para deputado estadual");
        }

        this.voteRepository.addNullVoteForStateDeputy(voter);
    }

    public void voteForStateDeputy(Voter voter, StateDeputy stateDeputy) {
        if (this.voteRepository.alreadyVotedForStateDeputy(voter)) {
            throw new StopTrap("Você não pode votar mais de uma vez para deputado estadual");
        }

        if (this.voteRepository.isRepeatStateDeputy(voter, stateDeputy)) {
            throw new Warning("Você não pode votar mais de uma vez em um mesmo candidato");
        }

        this.voteRepository.addVoteForStateDeputy(voter, stateDeputy);
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
    
     public Voter getVoterByElectorCard(String electoralCard) {
         return this.voterRepository.getByElectoralCard(electoralCard);
    }

    public TSEProfessional getTSEProfessionalByUser(String user) {
        return this.tseProfessionalRepository.getByUser(user);
    }

    public void addCandidate(Candidate candidate, String password) {
        if (!isValid(password)) {
            throw new Warning("Senha inválida");
        }
        
        //#if PresidenteDeputadoFederal 
//@        if (candidate instanceof President) {
//@            this.presidentRepository.addCandidate((President) candidate);
//@        }
//@        if (candidate instanceof FederalDeputy) {
//@        	this.federalDeputyRepository.addCandidate((FederalDeputy) candidate);
//@        }
        //#elif GovernadorDeputadoEstadual 
        if (candidate instanceof Governor) {
            this.governorRepository.addCandidate((Governor) candidate);
        }
        if (candidate instanceof StateDeputy) {
        	this.stateDeputyRepository.addCandidate((StateDeputy) candidate);
        }
        //#endif
    }

    public void removeCandidate(Candidate candidate, String password) {
        if (!isValid(password)) {
            throw new Warning("Senha inválida");
        }
        
        //#if PresidenteDeputadoFederal 
//@        if (candidate instanceof President) {
//@            this.presidentRepository.removeCandidate((President) candidate);
//@        }
//@        if (candidate instanceof FederalDeputy) {
//@            this.federalDeputyRepository.removeCandidate((FederalDeputy) candidate);
//@        }
        //#elif GovernadorDeputadoEstadual 
        if (candidate instanceof Governor) {
        	this.governorRepository.removeCandidate((Governor) candidate);
        }
        if (candidate instanceof StateDeputy) {
        	this.stateDeputyRepository.removeCandidate((StateDeputy) candidate);
        }
        //#endif
    }
    
    //#if PresidenteDeputadoFederal 
//@    public President getPresidentByNumber(int number) {
//@        return this.presidentRepository.getByNumber(number);
//@    }
//@    
//@    public FederalDeputy getFederalDeputyByNumber(String state, int number) {
//@        return this.federalDeputyRepository.getByNumber(state + number);
//@    }
//@
//@    
//@    public List<President> getPresidents() {
//@        return new ArrayList<President>(this.presidentRepository.getCandidates().values());
//@    }
//@    
//@    public List<FederalDeputy> getFederalDeputies() {
//@        return new ArrayList<FederalDeputy>(this.federalDeputyRepository.getCandidates().values());
//@    }
//@    
    //#elif GovernadorDeputadoEstadual 
    public Governor getGovernorByNumber(String number) {
        return this.governorRepository.getByNumber(number);
    }
    
    public List<Governor> getGovernors() {
        return new ArrayList<Governor>(this.governorRepository.getCandidates().values());
    }

    public Governor getGovernorByNumber(String state, int number) {
        return this.governorRepository.getByNumber(state + number);
    }

    public StateDeputy getStateDeputyByNumber(String state, int number) {
        return this.stateDeputyRepository.getByNumber(state + number);
    }

    public List<StateDeputy> getStateDeputies() {
        return new ArrayList<StateDeputy>(this.stateDeputyRepository.getCandidates().values());
    }
    //#endif

    public String getResults(String password) {
        if (!isValid(password)) {
            throw new Warning("Senha inválida");
        }

        if (this.status) {
            throw new StopTrap("Eleição ainda não finalizou, não é possível gerar o resultado");
        }

        return ElectionResult.produce(
        	//#if PresidenteDeputadoFederal
//@            this.presidentRepository.getCandidates(),
//@            this.federalDeputyRepository.getCandidates(),
//@            this.voteRepository.getProtestVotesPresident(), 
//@            this.voteRepository.getNullVotesPresident(),
//@            this.voteRepository.getProtestVotesFederalDeputy(),
//@            this.voteRepository.getNullVotesFederalDeputy()
            //#elif GovernadorDeputadoEstadual 
            this.governorRepository.getCandidates(),
            this.stateDeputyRepository.getCandidates(),
            this.voteRepository.getProtestVotesGovernor(), 
            this.voteRepository.getNullVotesGovernor(),
            this.voteRepository.getProtestVotesStateDeputy(),
            this.voteRepository.getNullVotesStateDeputy()
            //#endif
        );
    }
}
