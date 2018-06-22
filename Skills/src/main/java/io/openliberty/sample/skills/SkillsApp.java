package io.openliberty.sample.skills;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class SkillsApp extends Application {

    @PostConstruct
    public void setupInitialSkillsAndExperts() {
        SkillsResource sr = new SkillsResource();
        sr.newSkill("jaxrs");
        sr.newSkill("cdi");
        sr.newSkill("jsf");
        sr.newSkill("security");
        sr.newSkill("jpa");

        sr.newExpert("Al");
        sr.newExpert("Bob");
        sr.newExpert("Carl");

        Map<String,Integer> skillsMap = new HashMap<>();
        skillsMap.put("jaxrs", 5);
        skillsMap.put("cdi", 3);
        skillsMap.put("security", 2);
        Expert expert = new Expert("Al", skillsMap);
        sr.updateExpert("Al", expert);

        skillsMap = new HashMap<>();
        skillsMap.put("jaxrs", 3);
        skillsMap.put("jsf", 4);
        skillsMap.put("jpa", 1);
        expert = new Expert("Bob", skillsMap);
        sr.updateExpert("Bob", expert);

        skillsMap = new HashMap<>();
        skillsMap.put("jaxrs", 2);
        skillsMap.put("jpa", 2);
        skillsMap.put("security", 5);
        expert = new Expert("Carl", skillsMap);
        sr.updateExpert("Carl", expert);
    }
}
