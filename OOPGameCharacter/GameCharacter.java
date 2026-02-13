package OOPGameCharacter;

public abstract class GameCharacter {
    protected String name;
    protected int knowledge;

    public GameCharacter(String name, int knowledge) {
        this.name = (name != null && !name.isBlank()) ? name : "Unknown Character";
        this.knowledge = knowledge;
    }

    public String getName() {
        return name;
    }

    public int getKnowledge() {
        return knowledge;
    }
}
