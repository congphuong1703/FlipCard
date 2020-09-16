package npc.com.flipcard;

public class GamerModel {

    private int id;
    private String name;
    private int scoreHighest;
    private int scoreCurrent;
    private boolean isActive;

    public GamerModel() {
    }

    public GamerModel(int id, String name, int scoreHighest, int scoreCurrent, boolean isActive) {
        this.id = id;
        this.name = name;
        this.scoreHighest = scoreHighest;
        this.scoreCurrent = scoreCurrent;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScoreHighest() {
        return scoreHighest;
    }

    public void setScoreHighest(int scoreHighest) {
        this.scoreHighest = scoreHighest;
    }

    public int getScoreCurrent() {
        return scoreCurrent;
    }

    public void setScoreCurrent(int scoreCurrent) {
        this.scoreCurrent = scoreCurrent;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "GamerModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scoreHighest=" + scoreHighest +
                ", scoreCurrent=" + scoreCurrent +
                ", isActive=" + isActive +
                '}';
    }


}
