import java.util.ArrayList;
import java.util.List;

public class Map {
    private int rows;
    private int columns;
    private List<Coordinate> path = new ArrayList<>();
    private final String mapGrid;
    private String[][] visualMap;

    public Map(String mapGrid) {
        this.mapGrid = mapGrid;
    }

    private void computeDimensions(){
        String[] lines = mapGrid.split("\\R");

        rows = lines.length;
        columns = lines[0].length();
    }

    public void initializeMap(){
        computeDimensions();
        visualMap = new String[rows][columns];

        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++) {

                int stringIndex = i * (columns + 1) + j;
                char symbol = mapGrid.charAt(stringIndex);

                switch (symbol) {
                    case 'S':
                        visualMap[i][j] = "S";
                        break;
                    case 'P':
                        visualMap[i][j] = "*";
                        break;
                    case 'E':
                        visualMap[i][j] = "E";
                        break;
                    default:
                        visualMap[i][j] = "-";
                        break;
                }
            }
        }
        generatePath();
    }

    public String[][] getSnapshot() {
        String[][] copy = new String[rows][columns];
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++) {
                copy[i][j] = visualMap[i][j];
            }
        }
        return copy;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void generatePath(){
        int[] start = findStart();
        int[] end = findEnd();
        if (start == null) {
            Renderer.renderErrorMessage("java.Map incomplete! missing start \"S\"");
            System.exit(1);
        }else if (end == null) {
            Renderer.renderErrorMessage("java.Map incomplete! missing end \"E\"");
            System.exit(1);
        }

        int currentRow = start[0];
        int currentCol = start[1];

        path.clear();
        path.add(new Coordinate(currentRow, currentCol));

        boolean[][] visited = new boolean[rows][columns];
        visited[currentRow][currentCol] = true;

        while (true) {
            if (isEnd(currentRow, currentCol)) {
                break;
            }

            int[] next = findNextStep(currentRow, currentCol, visited);
            if (next == null) {
            Renderer.renderErrorMessage("java.Map incomplete! Path is not complete");
                System.exit(1);
            }

            currentRow = next[0];
            currentCol = next[1];
            path.add(new Coordinate(currentRow, currentCol));
            visited[currentRow][currentCol] = true;
        }
    }

    private int[] findStart() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (visualMap[i][j].equals("S")) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private int[] findEnd() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (visualMap[i][j].equals("E")) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private int[] findNextStep(int row, int column, boolean[][] visited) {
        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] d : directions) {
            int newRow = row + d[0];
            int newColumn = column + d[1];
            if (inBounds(newRow, newColumn) && !visited[newRow][newColumn] && isPathOrEnd(newRow, newColumn)) {
                return new int[]{newRow, newColumn};
            }
        }
        return null;
    }

    private boolean isEnd(int row, int column) {
        return visualMap[row][column].equals("E");
    }

    private boolean inBounds(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    private boolean isPathOrEnd(int r, int c) {
        String tile = visualMap[r][c];
        return tile.equals("*") || tile.equals("E");
    }

    public List<Coordinate> getPath() {
        return path;
    }

    public String[][] getVisualMap() {
        return visualMap;
    }
}
