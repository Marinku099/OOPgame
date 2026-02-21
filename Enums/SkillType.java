package Enums;

public enum SkillType {
    LUCK("Luck"),
    KNOWLEDGE("Knowledge");

    private final String displayName;

    private SkillType(String name){ 
        this.displayName = name;
    }

    public String getName(){
        return this.displayName;
    }
}
