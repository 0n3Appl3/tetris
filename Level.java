public class Level {
    private int count;
    private int linesCleared;
    private int linesToLevelUp;
    private int score;

    public Level() {
        count = 0;
        linesCleared = 0;
        linesToLevelUp = 10;
        score = 0;
    }

    public void addLinesCleared() {
        linesCleared++;

        if (linesCleared % linesToLevelUp == 0) {
            count++;
            linesToLevelUp += 5;
            linesCleared = 0;
        }
    }

    public void addScore(int amount) {
        score += amount;
    }

    public int getLevel() {
        return count;
    }

    public int getLinesCleared() {
        return linesCleared;
    }

    public int getScore() {
        return score;
    }
}