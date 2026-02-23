package Enums;

import java.util.HashMap;
import java.util.Map;

public enum SkillType {
    LUCK("Luck", 10),
    KNOWLEDGE("Knowledge", 5);

    private final String displayName;
    private final int kcost;
    private static final Map<String, SkillType> lookup = new HashMap<>();

    static {
        for (SkillType skill : SkillType.values()) {
            lookup.put(skill.getName(), skill);
        }
    }

    private SkillType(String name, int cost){ 
        this.displayName = name;
        this.kcost = cost;
    }

    private SkillType(String name){
        this.displayName = name;
        this.kcost = 10;
    }

    public String getName(){
        return this.displayName;
    }

    public int getCost(){
        return this.kcost;
    }

    public static SkillType getByName(String name) {
        return lookup.get(name);
    }
}
