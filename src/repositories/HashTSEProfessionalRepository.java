package repositories;

import java.util.HashMap;
import java.util.Map;

import models.CertifiedProfessional;
import models.TSEEmployee;
import models.TSEProfessional;

public class HashTSEProfessionalRepository implements ITSEProfessionalRepository {
    private final Map<String, TSEProfessional> tseProfessionals = new HashMap<>();

    public HashTSEProfessionalRepository() {
        this.preLoad();
    }

    @Override
    public TSEProfessional getByUser(String user) {
        return this.tseProfessionals.get(user);
    }

    @Override
    public void addTSEProfessional(String user, TSEProfessional tseProfessional) {
        this.tseProfessionals.put(user, tseProfessional);
    }

    @Override
    public void preLoad() {
        this.addTSEProfessional(
            "cert",
            new CertifiedProfessional.Builder()
                .user("cert")
                .password("54321")
                .build()
        );
        this.addTSEProfessional(
            "emp",
            new TSEEmployee.Builder()
                .user("emp")
                .password("12345")
                .build()
        );
    }
}
