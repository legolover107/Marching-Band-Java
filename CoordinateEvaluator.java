import java.util.Map;
import java.util.TreeMap;

public class CoordinateEvaluator {

    private Map<Integer, String> xIntToString = new TreeMap<>();
    private Map<String, Integer> xStringToInt = new TreeMap<>();
    private Map<Integer, String> yIntToString = new TreeMap<>();
    private Map<String, Integer> yStringToInt = new TreeMap<>();

    public CoordinateEvaluator() {
        // Setup X Coordinates
        for (int x = -80; x <= 80; x++) {
            String tempString = "; ";
            if (x < 0) {
                tempString = "Side 1: ";
            } else if (x > 0) {
                tempString = "Side 2: ";
            }
            
            if (Math.abs(x) % 8 == 0) {
                tempString += "On " + (50 - ((x / 8) * 5)) + " yd ln";
            } else if (Math.abs(x) % 8 <= 4) {
                String isPlural = "";
                if ((Math.abs(x) % 8) != 1) {
                    isPlural = "s";
                }
                tempString += (Math.abs(x) % 8) + " step" + isPlural + " outside " + (50 - ((Math.abs(x) / 8) * 5)) + " yd ln";
            } else {
                String isPlural = "";
                if ((8 - (Math.abs(x) % 8)) != 1) {
                    isPlural = "s";
                }
                tempString += (8 - (Math.abs(x) % 8)) + " step" + isPlural + " inside " + (50 - (((Math.abs(x) / 8) * 5) + 5)) + " yd ln";
            }
            xIntToString.put(x, tempString);
            xStringToInt.put(tempString, x);
        }

        // Setup Y Coordinate
        for (int y = -42; y <= 42; y++) {
            String tempString = "";
            if (y == -42) {
                tempString = "On back sideline";
            } else if (y <= -28) {
                String isPlural = "";
                if ((42 + y) != 1) {
                    isPlural = "s";
                }
                tempString = (42 + y) + " step" + isPlural + " in front of back sideline";
            } else if (y < -14) {
                String isPlural = "";
                if (((-1 * y) - 14) != 1) {
                    isPlural = "s";
                }
                tempString = ((-1 * y) - 14) + " step" + isPlural + " behind back hash";
            } else if (y == -14) {
                tempString = "On back hash";
            } else if (y <= 0) {
                String isPlural = "";
                if ((14 + y) != 1) {
                    isPlural = "s";
                }
                tempString = (14 + y) + " step" + isPlural + " in front of back hash";
            } else if (y > 0 && y < 14) {
                String isPlural = "";
                if (y != 1) {
                    isPlural = "s";
                }
                tempString = y + " step" + isPlural + " behind front hash";
            } else if (y == 14) {
                tempString = "On front hash";
            } else if (y > 14 && y <= 28) {
                String isPlural = "";
                if ((y - 14) != 1) {
                    isPlural = "s";
                }
                tempString = (y - 14) + " step" + isPlural + " in front of front hash";
            } else if (y > 28 && y < 42) {
                String isPlural = "";
                if ((42 - y) != 1) {
                    isPlural = "s";
                }
                tempString = (42 - y) + " step" + isPlural + " behind front sideline";
            } else if (y == 42) {
                tempString = "On front sideline";
            }
            yIntToString.put(y, tempString);
            yStringToInt.put(tempString, y);
        }

    }

    public int xCoord(String xCoord) {
        return xStringToInt.get(xCoord);
    }

    public String xCoord(int xCoord) {
        return xIntToString.get(xCoord);
    }

    public int yCoord(String yCoord) {
        return yStringToInt.get(yCoord);
    }

    public String yCoord(int yCoord) {
        return yIntToString.get(yCoord);
    }

    public boolean testPrint(boolean test) {
        if (test) {
            CoordinateEvaluator coordEval = new CoordinateEvaluator();
            System.out.println(coordEval.xIntToString + "\n\n" + coordEval.xStringToInt);
        }
        return test;
    }

}