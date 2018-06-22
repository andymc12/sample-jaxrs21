package io.openliberty.sample.skills;

import java.util.HashMap;
import java.util.Map;

public class Expert {

    String name;

    Map<String,Integer> skills;

    public Expert() {
        
    }

    public Expert(String name) {
        this.name = name;
        skills = new HashMap<>();
    }

    public Expert(String name, Map<String,Integer> skills) {
        this.name = name;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getSkills() {
        return skills;
    }
}
