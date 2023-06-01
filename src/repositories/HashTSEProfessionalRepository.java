package repositories;

import java.util.HashMap;
import java.util.Map;

import models.TSEProfessional;

public class HashTSEProfessionalRepository implements ITSEProfessionalRepository {
    private final Map<String, TSEProfessional> TSEProfessionals = new HashMap<>();

    @Override
    public TSEProfessional getByUser(String user) {
        return this.TSEProfessionals.get(user);
    }

    @Override
    public void addTSEProfessional(String user, TSEProfessional tseProfessional) {
        this.TSEProfessionals.put(user, tseProfessional);
    }
    
}
