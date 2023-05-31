public class Voter {
    protected final String electoralCard;
    protected final String name;
    protected final String state;

    protected Voter(String electoralCard, String name, String state) {
        this.electoralCard = electoralCard;
        this.name = name;
        this.state = state;
    }

    public static class Builder {
        private String electoralCard;
        private String name;
        private String state;

        public Builder electoralCard(String electoralCard) {
            this.electoralCard = electoralCard;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Voter build() {
            if (electoralCard == null || electoralCard.isEmpty()) {
                throw new IllegalArgumentException("electoralCard mustn't be null or empty");
            }

            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("name mustn't be null or empty");
            }

            if (state == null || state.isEmpty()) {
                throw new IllegalArgumentException("state mustn't be null or empty");
            }

            return new Voter(electoralCard, name, state);
        }
    }

    public void vote(int number, Election election, String type, Boolean isProtestVote) {
        if (type.equals("President")) {
            if (isProtestVote) {
                election.computeProtestVote("President", this);
            } else if (number == 0) {
                election.computeNullVote("President", this);
            } else {
                President candidate = election.getPresidentByNumber(number);
                if (candidate == null) {
                    throw new Warning("Número de candidato inválido");
                }
                election.computeVote(candidate, this);
            }
        } else if (type.equals("FederalDeputy")) {
            if (number == 0) {
                election.computeNullVote("FederalDeputy", this);
            } else if (isProtestVote) {
                election.computeProtestVote("FederalDeputy", this);
            } else {
                FederalDeputy candidate = election.getFederalDeputyByNumber(this.state, number);
                if (candidate == null) {
                    throw new Warning("Número de candidato inválido");
                }
                election.computeVote(candidate, this);
            }
        }
    }
}
