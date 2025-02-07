package view;

import errors.StopTrap;
import errors.Warning;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import domain.Candidate;
import domain.CertifiedProfessional;
import domain.ElectionController;
import domain.FederalDeputy;
import domain.President;
import domain.Governor;
import domain.StateDeputy;
import domain.TSEEmployee;
import domain.TSEProfessional;
import domain.Voter;

public class UrnaCli {
    private final BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
    private boolean exit = false;
    private ElectionController currElection;

    public UrnaCli(ElectionController election) {
        this.currElection = election;
    }

    public void init() {      
        startMenu();
    }

    private void print(String output) {
        System.out.println(output);
    }

    private String readString() {
        try {
            return scanner.readLine();
        } catch (Exception e) {
            print("\nErro na leitura de entrada, digite novamente");
            return readString();
            // return "";
        }
    }

    private int readInt() {
        try {
            return Integer.parseInt(readString());
        } catch (Exception e) {
            print("\nErro na leitura de entrada, digite novamente");
            return readInt();
            // return -1;
        }
    }

    private void startMenu() {
        try {
            while (!exit) {
                print("Escolha uma opção:\n");
                print("(1) Entrar (Eleitor)");
                print("(2) Entrar (TSE)");
                print("(0) Fechar aplicação");
                int command = readInt();
                switch (command) {
                    case 1 -> voterMenu();
                    case 2 -> tseMenu();
                    case 0 -> exit = true;
                    default -> print("Comando inválido\n");
                }
                print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
            }
        } catch (Exception e) {
            print("Erro inesperado\n");
        }
    }

    private Voter getVoter() {
        print("Insira seu título de eleitor:");
        String electoralCard = readString();
        Voter voter = this.currElection.getVoterByElectorCard(electoralCard);
        if (voter == null) {
            print("Eleitor não encontrado, por favor confirme se a entrada está correta e tente novamente");
        } else {
            print("Olá, você é " + voter.name + " de " + voter.state + "?\n");
            print("(1) Sim\n(2) Não");
            int command = readInt();
            if (command == 1) {
                return voter;
            } else if (command == 2) {
                print("Ok, você será redirecionado para o menu inicial");
            } else {
                print("Entrada inválida, tente novamente");
                return getVoter();
            }
        }
        return null;
    }
    
    //#if PresidenteDeputadoFederal 
//@    private boolean votePresident(Voter voter) {
//@        print("(ext) Desistir");
//@        print("Digite o número do candidato escolhido por você para presidente:");
//@        String vote = readString();
//@        if (vote.equals("ext")) {
//@            throw new StopTrap("Saindo da votação");
//@        } else if (vote.equals("br")) { // Branco
//@            print("Você está votando branco\n");
//@            print("(1) Confirmar\n(2) Mudar voto");
//@            int confirm = readInt();
//@            if (confirm == 1) {
//@                currElection.voteProtestForPresident(voter);
//@                return true;
//@            } else {
//@                return votePresident(voter);
//@            }
//@        } else {
//@            try {
//@                int voteNumber = Integer.parseInt(vote);
//@                // Nulo
//@                if (voteNumber == 0) {
//@                    print("Você está votando nulo\n");
//@                    print("(1) Confirmar\n(2) Mudar voto");
//@                    int confirm = readInt();
//@                    if (confirm == 1) {
//@                        currElection.voteNullForPresident(voter);
//@                        return true;
//@                    } else {
//@                        votePresident(voter);
//@                    }
//@                }
//@
//@                // Normal
//@                President candidate = currElection.getPresidentByNumber(voteNumber);
//@                if (candidate == null) {
//@                    print("Nenhum candidato encontrado com este número, tente novamente");
//@                    print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
//@                    return votePresident(voter);
//@                }
//@                print(candidate.name + " do " + candidate.party + "\n");
//@                print("(1) Confirmar\n(2) Mudar voto");
//@                int confirm = readInt();
//@                if (confirm == 1) {
//@                    currElection.voteForPresident(voter, candidate);
//@                    return true;
//@                } else if (confirm == 2) {
//@                    return votePresident(voter);
//@                }
//@            } catch (Warning e) {
//@                print(e.getMessage());
//@                return votePresident(voter);
//@            } catch (Error e) {
//@                print(e.getMessage());
//@                throw e;
//@            } catch (Exception e) {
//@                print("Ocorreu um erro inesperado");
//@                return false;
//@            }
//@        }
//@        return true;
//@    }
//@
//@    private boolean voteFederalDeputy(Voter voter, int counter) {
//@        print("(ext) Desistir");
//@        print("Digite o número do " + counter + "º candidato escolhido por você para deputado federal:\n");
//@        String vote = readString();
//@        if (vote.equals("ext")) {
//@            throw new StopTrap("Saindo da votação");
//@        }
//@        // Branco
//@        if (vote.equals("br")) {
//@            print("Você está votando branco\n");
//@            print("(1) Confirmar\n(2) Mudar voto");
//@            int confirm = readInt();
//@            if (confirm == 1) {
//@                currElection.voteProtestForFederalDeputy(voter);
//@                return true;
//@            } else {
//@                return voteFederalDeputy(voter, counter);
//@            }
//@        } else {
//@            try {
//@                int voteNumber = Integer.parseInt(vote);
//@                // Nulo
//@                if (voteNumber == 0) {
//@                    print("Você está votando nulo\n");
//@                    print("(1) Confirmar\n(2) Mudar voto\n");
//@                    int confirm = readInt();
//@                    if (confirm == 1) {
//@                        currElection.voteNullForFederalDeputy(voter);
//@                        return true;
//@                    } else {
//@                        return voteFederalDeputy(voter, counter);
//@                    }
//@                }
//@
//@                // Normal
//@                FederalDeputy candidate = currElection.getFederalDeputyByNumber(voter.state, voteNumber);
//@                if (candidate == null) {
//@                    print("Nenhum candidato encontrado com este número, tente novamente");
//@                    print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
//@                    return voteFederalDeputy(voter, counter);
//@                }
//@                print(candidate.name + " do " + candidate.party + "(" + candidate.state + ")\n");
//@                print("(1) Confirmar\n(2) Mudar voto");
//@                int confirm = readInt();
//@                if (confirm == 1) {
//@                    currElection.voteForFederalDeputy(voter, candidate);
//@                    return true;
//@                } else if (confirm == 2) {
//@                    return voteFederalDeputy(voter, counter);
//@                }
//@            } catch (Warning e) {
//@                print(e.getMessage());
//@                return voteFederalDeputy(voter, counter);
//@            } catch (Error e) {
//@                print(e.getMessage());
//@                throw e;
//@            } catch (Exception e) {
//@                print("Ocorreu um erro inesperado");
//@                return false;
//@            }
//@        }
//@        return true;
//@
//@    }
    //#elif GovernadorDeputadoEstadual 
    private boolean voteGovernor(Voter voter) {
        print("(ext) Desistir");
        print("Digite o número do candidato escolhido por você para governador:");
        String vote = readString();
        if (vote.equals("ext")) {
            throw new StopTrap("Saindo da votação");
        } else if (vote.equals("br")) { // Branco
            print("Você está votando branco\n");
            print("(1) Confirmar\n(2) Mudar voto");
            int confirm = readInt();
            if (confirm == 1) {
                currElection.voteProtestForGovernor(voter);
                return true;
            } else {
                return voteGovernor(voter);
            }
        } else {
            try {
                int voteNumber = Integer.parseInt(vote);
                // Nulo
                if (voteNumber == 0) {
                    print("Você está votando nulo\n");
                    print("(1) Confirmar\n(2) Mudar voto");
                    int confirm = readInt();
                    if (confirm == 1) {
                        currElection.voteNullForGovernor(voter);
                        return true;
                    } else {
                        return voteGovernor(voter);
                    }
                }

                // Normal
                Governor candidate = currElection.getGovernorByNumber(voter.state, voteNumber);
                if (candidate == null) {
                    print("Nenhum candidato encontrado com este número, tente novamente");
                    print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
                    return voteGovernor(voter);
                }
                print(candidate.name + " do " + candidate.party + "(" + candidate.state + ")\n");
                print("(1) Confirmar\n(2) Mudar voto");
                int confirm = readInt();
                if (confirm == 1) {
                    currElection.voteForGovernor(voter, candidate);
                    return true;
                } else if (confirm == 2) {
                    return voteGovernor(voter);
                }
            } catch (Warning e) {
                print(e.getMessage());
                return voteGovernor(voter);
            } catch (Error e) {
                print(e.getMessage());
                throw e;
            } catch (Exception e) {
                print("Ocorreu um erro inesperado");
                return false;
            }
        }
        return true;
    }

    private boolean voteStateDeputy(Voter voter, int counter) {
        print("(ext) Desistir");
        print("Digite o número do " + counter + "º candidato escolhido por você para deputado estadual:\n");
        String vote = readString();
        if (vote.equals("ext")) {
            throw new StopTrap("Saindo da votação");
        }
        // Branco
        if (vote.equals("br")) {
            print("Você está votando branco\n");
            print("(1) Confirmar\n(2) Mudar voto");
            int confirm = readInt();
            if (confirm == 1) {
                currElection.voteProtestForStateDeputy(voter);
                return true;
            } else {
                return voteStateDeputy(voter, counter);
            }
        } else {
            try {
                int voteNumber = Integer.parseInt(vote);
                // Nulo
                if (voteNumber == 0) {
                    print("Você está votando nulo\n");
                    print("(1) Confirmar\n(2) Mudar voto\n");
                    int confirm = readInt();
                    if (confirm == 1) {
                        currElection.voteNullForStateDeputy(voter);
                        return true;
                    } else {
                        return voteStateDeputy(voter, counter);
                    }
                }

                // Normal
                StateDeputy candidate = currElection.getStateDeputyByNumber(voter.state, voteNumber);
                if (candidate == null) {
                    print("Nenhum candidato encontrado com este número, tente novamente");
                    print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
                    return voteStateDeputy(voter, counter);
                }
                print(candidate.name + " do " + candidate.party + "(" + candidate.state + ")\n");
                print("(1) Confirmar\n(2) Mudar voto");
                int confirm = readInt();
                if (confirm == 1) {
                    currElection.voteForStateDeputy(voter, candidate);
                    return true;
                } else if (confirm == 2) {
                    return voteStateDeputy(voter, counter);
                }
            } catch (Warning e) {
                print(e.getMessage());
                return voteStateDeputy(voter, counter);
            } catch (Error e) {
                print(e.getMessage());
                throw e;
            } catch (Exception e) {
                print("Ocorreu um erro inesperado");
                return false;
            }
        }
        return true;
    }
    //#endif

    private void voterMenu() {
        try {
            print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
            if (!currElection.getStatus()) {
                print("A eleição ainda não foi inicializada, verifique com um funcionário do TSE");
                return;
            }

            Voter voter = getVoter();

            if (voter == null) return;

            print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");

            print("Vamos começar!\n");
            print(
                    "OBS:\n- A partir de agora caso você queira votar nulo você deve usar um numero composto de 0 (00 e 0000)\n- A partir de agora caso você queira votar branco você deve escrever br\n");
            print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
            
            //#if PresidenteDeputadoFederal 
//@            if (votePresident(voter)) {
//@                print("Voto para presidente registrado com sucesso");
//@            }
//@            print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
//@
//@            if (voteFederalDeputy(voter, 1)) {
//@                print("Primeiro voto para deputado federal registrado com sucesso");
//@            }
//@            print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
//@           
//@            if (voteFederalDeputy(voter, 2)) {
//@                print("Segundo voto para deputado federal registrado com sucesso");
//@            }
//@            print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
            //#elif GovernadorDeputadoEstadual 
            if (voteGovernor(voter)) {
                print("Voto para governador registrado com sucesso");
            }
            print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");

            if (voteStateDeputy(voter, 1)) {
                print("Primeiro voto para deputado estadual registrado com sucesso");
            }

            print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
    
            if (voteStateDeputy(voter, 2)) {
                print("Segundo voto para deputado estadual registrado com sucesso");
            }

            print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
            //#endif

        } catch (Warning e) {
            print(e.getMessage());
        } catch (StopTrap e) {
            print(e.getMessage());
        } catch (Exception e) {
            print("Erro inesperado");
        }
    }

    private TSEProfessional getTSEProfessional() {
        print("Insira seu usuário:");
        String user = readString();
        TSEProfessional tseProfessional = this.currElection.getTSEProfessionalByUser(user);
        if (tseProfessional == null) {
            print("Funcionário do TSE não encontrado, por favor confirme se a entrada está correta e tente novamente");
        } else {
            print("Insira sua senha:");
            String password = readString();

            // Deveria ser um hash na pratica
            if (tseProfessional.login(password)) return tseProfessional;

            print("Senha inválida, tente novamente");
            print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
        }
        return null;
    }

    private void addCandidate(TSEEmployee tseProfessional) {
        print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
        print("Qual a categoria de seu candidato?\n");
        print("(1) Presidente");
        print("(2) Deputado Federal");
        print("(3) Governador");
        print("(4) Deputado Estadual");
        int candidateType = readInt();

        if (candidateType > 4 || candidateType < 1) {
            print("Comando inválido");
            addCandidate(tseProfessional);
        }

        print("Qual o nome do candidato?");
        String name = readString();

        print("Qual o numero do candidato?");
        int number = readInt();

        print("Qual o partido do candidato?");
        String party = readString();

        Candidate candidate = null;
        if (candidateType == 2) {
            print("Qual o estado do candidato?");
            String state = readString();

            print("\nCadastrar o candidato deputado federal " + name + " Nº " + number + " do " + party + "(" + state
                    + ")?");
            candidate = new FederalDeputy.Builder()
                    .name(name)
                    .number(number)
                    .party(party)
                    .state(state)
                    .build();
        } else if (candidateType == 1) {
            print("\nCadastrar o candidato a presidente " + name + " Nº " + number + " do " + party + "?");
            candidate = new President.Builder()
                    .name(name)
                    .number(number)
                    .party(party)
                    .build();
        } else if (candidateType == 3) {
            print("Qual o estado do candidato?");
            String state = readString();

            print("\nCadastrar o candidato a governador " + name + " Nº " + number + " do " + party + "(" + state
                    + ")?");
            candidate = new Governor.Builder()
                    .name(name)
                    .number(number)
                    .party(party)
                    .state(state)
                    .build();
        }else if (candidateType == 4) {
            print("Qual o estado do candidato?");
            String state = readString();

            print("\nCadastrar o candidato a deputado estadual " + name + " Nº " + number + " do " + party + "(" + state
                    + ")?");
            candidate = new StateDeputy.Builder()
                    .name(name)
                    .number(number)
                    .party(party)
                    .state(state)
                    .build();
        }

        print("(1) Sim\n(2) Não");
        int save = readInt();
        if (save == 1 && candidate != null) {
            print("Insira a senha da urna");
            String pwd = readString();
            currElection.addCandidate(candidate, pwd);
            print("Candidato cadastrado com sucesso");
        }
    }

    private void removeCandidate(TSEEmployee tseProfessional) {
        print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
        print("Qual a categoria de seu candidato?");
        //#if PresidenteDeputadoFederal
//@        print("(1) Presidente");
//@        print("(2) Deputado Federal");
        //#elif GovernadorDeputadoEstadual
        print("(1) Governador");
        print("(2) Deputado Estadual");
        //#endif
        int candidateType = readInt();

        if (candidateType > 2 || candidateType < 1) {
            print("Comando inválido");
            removeCandidate(tseProfessional);
        }

        Candidate candidate = null;
        print("Qual o numero do candidato?");
        int number = readInt();
        //#if PresidenteDeputadoFederal 
//@        if (candidateType == 2) {
//@            print("Qual o estado do candidato?");
//@            String state = readString();
//@
//@            candidate = currElection.getFederalDeputyByNumber(state, number);
//@            if (candidate == null) {
//@                print("Candidato não encontrado");
//@                return;
//@            }
//@            print("/Remover o candidato a deputado federal " + candidate.name + " Nº " + candidate.number + " do "
//@                    + candidate.party + "("
//@                    + ((FederalDeputy) candidate).state + ")?");
//@        } else if (candidateType == 1) {
//@            candidate = currElection.getPresidentByNumber(number);
//@            if (candidate == null) {
//@                print("Candidato não encontrado");
//@                return;
//@            }
//@            print("/Remover o candidato a presidente " + candidate.name + " Nº " + candidate.number + " do "
//@                    + candidate.party
//@                    + "?");
//@        }
        //#elif GovernadorDeputadoEstadual 
        if (candidateType == 1) {
            print("Qual o estado do candidato?");
            String state = readString();

            candidate = currElection.getGovernorByNumber(state, number);
            if (candidate == null) {
                print("Candidato não encontrado");
                return;
            }
            print("/Remover o candidato a governador " + candidate.name + " Nº " + candidate.number + " do "
                    + candidate.party + "("
                    + ((Governor) candidate).state + ")?");
        } else if (candidateType == 2) {
            print("Qual o estado do candidato?");
            String state = readString();

            candidate = currElection.getStateDeputyByNumber(state, number);
            if (candidate == null) {
                print("Candidato não encontrado");
                return;
            }
            print("/Remover o candidato a deputado estadual " + candidate.name + " Nº " + candidate.number + " do "
                    + candidate.party + "("
                    + ((StateDeputy) candidate).state + ")?");
        }
        //#endif

        print("(1) Sim\n(2) Não");
        int remove = readInt();
        if (remove == 1) {
            print("Insira a senha da urna:");
            String pwd = readString();
            currElection.removeCandidate(candidate, pwd);
            print("Candidato removido com sucesso");
        }
    }

    private void startSession(CertifiedProfessional tseProfessional) {
        try {
            print("Insira a senha da urna");
            String pwd = readString();
            currElection.start(pwd);
            print("Sessão inicializada");
            print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
        } catch (Warning e) {
            print(e.getMessage());
        }
    }

    private void endSession(CertifiedProfessional tseProfessional) {
        try {
            print("Insira a senha da urna:");
            String pwd = readString();
            currElection.finish(pwd);
            print("Sessão finalizada com sucesso");
            print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
        } catch (Warning e) {
            print(e.getMessage());
        }
    }

    private void showResults(CertifiedProfessional tseProfessional) {
        try {
            print("Insira a senha da urna");
            String pwd = readString();
            print(currElection.getResults(pwd));
            print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
        } catch (Warning e) {
            print(e.getMessage());
        }
    }

    private void tseMenu() {
        try {
            TSEProfessional tseProfessional = getTSEProfessional();

            if (tseProfessional == null) return;

            print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
            boolean back = false;
            while (!back) {
                print("Escolha uma opção:");
                if (tseProfessional instanceof TSEEmployee) {
                    print("(1) Cadastrar candidato");
                    print("(2) Remover candidato");
                    print("(0) Sair");
                    int command = readInt();
                    switch (command) {
                        case 1 -> addCandidate((TSEEmployee) tseProfessional);
                        case 2 -> removeCandidate((TSEEmployee) tseProfessional);
                        case 0 -> back = true;
                        default -> print("Comando inválido\n");
                    }
                } else if (tseProfessional instanceof CertifiedProfessional) {
                    print("(1) Iniciar sessão");
                    print("(2) Finalizar sessão");
                    print("(3) Mostrar resultados");
                    print("(0) Sair");
                    int command = readInt();
                    switch (command) {
                        case 1 -> startSession((CertifiedProfessional) tseProfessional);
                        case 2 -> endSession((CertifiedProfessional) tseProfessional);
                        case 3 -> showResults((CertifiedProfessional) tseProfessional);
                        case 0 -> back = true;
                        default -> print("Comando inválido\n");
                    }
                }
            }
        } catch (Warning e) {
            print(e.getMessage());
        } catch (Exception e) {
            print("Ocorreu um erro inesperado");
        }
    }
}
