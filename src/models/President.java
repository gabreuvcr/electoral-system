package models;

public class President extends Candidate {

    protected President(String name, String party, int number) {
        super(name, party, number);
    }

    public static class Builder {
        protected String name;
        protected String party;
        protected int number;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder party(String party) {
            this.party = party;
            return this;
        }

        public Builder number(int number) {
            this.number = number;
            return this;
        }

        public President build() {
            if (number <= 0) {
                throw new IllegalArgumentException("number mustn't be less than or equal to 0");
            }

            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("name mustn't be null or empty");
            }

            if (party == null || party.isEmpty()) {
                throw new IllegalArgumentException("party mustn't be null or empty");
            }

            return new President(this.name, this.party, this.number);
        }
    }
}