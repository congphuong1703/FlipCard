package npc.com.flipcard.Model;

public class GamerModel {

    private int id;
    private String name;
    private int scoreHighest;
    private int scoreCurrent;
    private int shuffle;
    private boolean isActive;

    public GamerModel() {
    }

    public GamerModel( String name) {
        this.name = name;
        this.scoreCurrent = 0;
        this.setScoreHighest();
        this.shuffle = 10;
        this.isActive = false;
    }

    public GamerModel(int id, String name, int scoreHighest, int scoreCurrent,int shuffle, boolean isActive) {
        this.id = id;
        this.name = name;
        this.scoreHighest = scoreHighest;
        this.scoreCurrent = scoreCurrent;
        this.shuffle = shuffle;
        this.isActive = isActive;
    }

    public int getShuffle() {
        return shuffle;
    }

    public void setShuffle(int shuffle) {
        this.shuffle = shuffle;
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

    public void setScoreHighest() {
        this.scoreHighest = this.scoreHighest > this.scoreCurrent ? this.scoreHighest : this.scoreCurrent;
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
