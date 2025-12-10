import java.util.ArrayList;

public class Marcher {
        
    private String displayName = null;
    private ArrayList<int[]> coordinateList = new ArrayList<>();
    private CoordinateEvaluator coordEval = new CoordinateEvaluator();

    public Marcher() {}
    
    public Marcher(String name) {
        displayName = name;
    }

    public Marcher(ArrayList<int[]> coords) {
        coordinateList = coords;
    }

    public Marcher(String name, ArrayList<int[]> coords) {
        displayName = name;
        coordinateList = coords;
    }

    public void addCoordinate(int[] coordinate) {
        coordinateList.add(coordinate);
    }

    public void changeName(String newName) {
        displayName = newName;
    }

    public void editCoordinate(int coordIndex, int[] newCoord) {
        coordinateList.set(coordIndex, newCoord);
    }

    public String getName() {
        return displayName;
    }

    public int getNumCoords() {
        return coordinateList.size();
    }

    public int getX(int coordIndex) {
        return coordinateList.get(coordIndex)[0];
    }

    public int getY(int coordIndex) {
        return coordinateList.get(coordIndex)[1];
    }

    public int[] getCoord(int coordIndex) {
        return coordinateList.get(coordIndex);
    }

    public void removeCoordinate(int coordIndex) {
        coordinateList.remove(coordIndex);
    }

    public ArrayList<int[]> getCoords() {
        return coordinateList;
    }

    public String printableCoords(int[] coords) {
        return coordEval.xCoord(coords[0]) + "; " + coordEval.yCoord(coords[1]);
    }

    public int[] readableCoords(String printedCoords) {
        String[] tempCoords = printedCoords.split("; ");
        int[] coords = new int[2];
        coords[0] = coordEval.xCoord(tempCoords[0]);
        coords[1] = coordEval.yCoord(tempCoords[1]);
        return coords;
    }

}