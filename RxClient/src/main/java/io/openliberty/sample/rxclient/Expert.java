package io.openliberty.sample.rxclient;

import java.util.Map;

public class Expert {

    String name;

    Map<String,Integer> skills;

    public String getName() {
        return name;
    }

    public Map<String, Integer> getSkills() {
        return skills;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSkills(Map<String, Integer> skills) {
        this.skills = skills;
    }
    
    @Override
    public String toString() {
        return name + " " + skills;
    }
}
