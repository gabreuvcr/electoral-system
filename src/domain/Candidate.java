package domain;

public class Candidate {
    public final String name;
    public final String party;
    public final int number;
    public int numVotes;

    public Candidate(String name, String party, int number) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name mustn't be null or empty");
        }

        if (party == null || party.isEmpty()) {
            throw new IllegalArgumentException("party mustn't be empty or empty");
        }

        if (number <= 0) {
            throw new IllegalArgumentException("number must be greater or equal to 1");
        }

        this.name = name;
        this.party = party;
        this.number = number;
        this.numVotes = 0;
    }

    public String getName() {
        return this.name;
    }

    public String getParty() {
        return this.party;
    }

    public int getNumber() {
        return this.number;
    }
}
