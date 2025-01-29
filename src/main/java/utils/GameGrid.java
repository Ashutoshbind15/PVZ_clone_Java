package utils;

public class GameGrid {
    private final int windowWidth;
    private final int windowHeight;
    private final int gridRows;    // Number of rows
    private final int gridCols;    // Number of columns
    private final int blockWidth;  // Width of each block
    private final int blockHeight; // Height of each block

    public GameGrid(int windowWidth, int windowHeight, int gridRows, int gridCols) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.gridRows = gridRows;
        this.gridCols = gridCols;
        this.blockWidth = windowWidth / gridCols;  // Width of each column
        this.blockHeight = windowHeight / gridRows; // Height of each row
    }

    public int[] getBlock(int x, int y) {
        // Validate input
        if (x < 0 || x >= gridRows || y < 0 || y >= gridCols) {
            throw new IllegalArgumentException("Block coordinates are out of bounds.");
        }

        // Calculate center coordinates
        int centerX = y * blockWidth + blockWidth / 2;  // y is column index (horizontal position)
        int centerY = x * blockHeight + blockHeight / 2; // x is row index (vertical position)

        return new int[]{centerX, centerY};
    }

    public int getBlockWidth() {
        return blockWidth;
    }

    public int getBlockHeight() {
        return blockHeight;
    }

    public int getGridRows() {
        return  gridRows;
    }
}