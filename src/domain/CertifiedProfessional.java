package domain;

public class CertifiedProfessional extends TSEProfessional {
    
    protected CertifiedProfessional(String user, String password) {
        super(user, password);
    }

    public static class Builder {
        protected String user;
        protected String password;

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public CertifiedProfessional build() {
            if (user == null || user.isEmpty()) {
                throw new IllegalArgumentException("user mustn't be null or empty");
            }

            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("password mustn't be null or empty");
            }

            return new CertifiedProfessional(this.user, this.password);
        }
    }

    public String getUser() {
        return this.user;
    }
}
