package domain;

public class Voter {
    public final String electoralCard;
    public final String name;
    public final String state;

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
}
