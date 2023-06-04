package data.repositories;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import domain.Voter;
import domain.interfaces.IVoterRepository;

import static java.lang.System.exit;

public class HashVoterRepository implements IVoterRepository{
    private final Map<String, Voter> voters = new HashMap<>();
    
    public HashVoterRepository() {
        this.preLoad();
    }

    @Override
    public Voter getByElectoralCard(String electoralCard) {
        return this.voters.get(electoralCard);
    }

    @Override
    public void addVoter(String electoralCard, Voter voter) {
        this.voters.put(electoralCard, voter);
    }

    @Override
    public void preLoad() {
        try {
            File myObj = new File("src/data/voterLoad.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                var voterData = data.split(",");
                this.addVoter(
                    voterData[0],
                    new Voter.Builder()
                        .electoralCard(voterData[0])
                        .name(voterData[1])
                        .state(voterData[2])
                        .build()
                );
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("Erro na inicialização dos dados");
            exit(1);
        }
    }
}
