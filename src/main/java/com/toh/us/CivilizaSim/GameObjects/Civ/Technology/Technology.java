package com.toh.us.CivilizaSim.GameObjects.Civ.Technology;

import com.toh.us.CivilizaSim.GameObjects.Resources.Knowledge;

public abstract class Technology {

    private TechName name;

    private String description;

    private int level;

    private Knowledge knowledgeRequired = new Knowledge();

    public Technology(TechName name, int knowledgeRequired, String description) {
        this.name               = name;
        this.knowledgeRequired.setAmount(knowledgeRequired);
        this.description        = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Knowledge getKnowledgeRequired() {
        return knowledgeRequired;
    }

    public void setKnowledgeRequired(Knowledge knowledgeRequired) {
        this.knowledgeRequired = knowledgeRequired;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
