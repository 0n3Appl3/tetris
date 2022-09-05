public abstract class Shape {
    public Block[] blocks = new Block[4];
    public int index = 0;

    public abstract Block[] draw();
    public abstract Block[] rotate();

    public void setNextRotation(int combinations) {
        index++;
        if (index % combinations == 0)
            index = 0;
    }
    
    public void saveState(Block[] b) {
        blocks = b;
    }
}
