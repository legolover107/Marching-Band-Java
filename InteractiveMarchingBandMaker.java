import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * A text based, interactive Marching Band dot card maker
 */
public class InteractiveMarchingBandMaker {

    private Scanner scan = new Scanner(System.in);
    private String filePath = null;
    private MarchingBand playerMarchingBand = null;
    private ArrayList<String[]> currentEditorLevel = new ArrayList<>();
    private CoordinateEvaluator coordEval = new CoordinateEvaluator();

    public static void main(String[] args) {
        InteractiveMarchingBandMaker bandMaker = new InteractiveMarchingBandMaker("CHS 2025.band");
        bandMaker.evaluateInput("");
    }
    /**
     * Guides you through making a Marching Band
     */
    public InteractiveMarchingBandMaker() {
        System.out.print("Hello! Welcome to the Custom Marching Band Show Simulator. \n\n" + //
                         "Would you like to create a new band (n) or edit an exsisting one (e)?\t");
        if (scan.nextLine().equals("e")) {
            System.out.print("Please enter the filepath:\t");
            filePath = scan.nextLine();
            readFromFile(filePath);
        } else {
            System.out.print("Please enter your band name (example: \"CHS 2025\"):\t");
            playerMarchingBand = new MarchingBand(scan.nextLine());
            currentEditorLevel.add("band, 0".split(", "));
            filePath = playerMarchingBand.getName() + ".band";
            System.out.print("Let's create our first section. \nPlease enter your section name (example: \"Alto Mellos\"):\t");
            playerMarchingBand.addSection(new Section(scan.nextLine()));
            System.out.print("Now let's add your first marcher. Please enter their name:\t");
            playerMarchingBand.getSection(0).addMember(new Marcher(scan.nextLine()));
            currentEditorLevel.add("section, 0".split(", "));
            System.out.print("Would you like to add another marcher (m) or add some of " + playerMarchingBand.getSection(0).getMarcher(0).getName() + "'s coordinates (c)?\t");
            if (scan.nextLine().equals("m")) {
                System.out.println("Please enter their name: ");
                playerMarchingBand.getSection(0).addMember(new Marcher(scan.nextLine()));
            } else {
                System.out.println("Please enter each coordinate on a new line with the format \"x, y\". Press D (d) when you are done.");
                currentEditorLevel.add("marcher, 0".split(", "));
                for (String coordinate = scan.nextLine(); !coordinate.equals("d"); coordinate = scan.nextLine()) {
                    playerMarchingBand.getSection(0).getMarcher(0).addCoordinate(toIntArray(coordinate));
                }
            }
        
        }

        evaluateInput("");

    }
    /**
     * Saves the data from the specified file
     * @param fileName file to be evaluated
     */
    public InteractiveMarchingBandMaker(String filePath) {
        this.filePath = filePath;
        readFromFile(this.filePath);
    }

    public boolean evaluateInput(String inputString) {
        String input = inputString;
        String currentlyEditing = currentEditorLevel.get(currentEditorLevel.size() - 1)[0];
        int currentlyEditingIndex = Integer.parseInt(currentEditorLevel.get(currentEditorLevel.size() - 1)[1]);
        while (!input.equals("d")) {
            if (currentlyEditing.equals("marcher")) {
                Marcher tempMarcher = playerMarchingBand.getSection(Integer.parseInt(currentEditorLevel.get(1)[1])).getMarcher(currentlyEditingIndex);
                System.out.println(tempMarcher.getName() + ": ");
                for (int i = 0; i < tempMarcher.getNumCoords(); i++) {
                    System.out.print(tempMarcher.printableCoords(tempMarcher.getCoord(i)) + "    ");
                }
                System.out.println("\n");
                System.out.println("Go back (b)\t\t\tEdit the name (n)\t\t\tAdd a coordinate (a)\t\t\tEdit a coordinate (e)\t\t\tRemove a coordinate (r)");
                input = scan.nextLine();
                if (input.equals("b")) {
                    currentEditorLevel.remove(currentEditorLevel.size() - 1);
                    evaluateInput(input);
                } else if (input.equals("n")) {
                    System.out.print("Please enter the new name:\t");
                    tempMarcher.changeName(scan.nextLine());
                } else if (input.equals("a")) {
                    tempMarcher.addCoordinate(makeCoord());
                } else if (input.equals("e")) {
                    System.out.print("Please enter the index of the coordinate you would like to edit:\t");
                    int i = Integer.parseInt(scan.nextLine());
                    while (i >= tempMarcher.getNumCoords()) {
                        System.out.print("Out of bounds. Try again:\t");
                        i = Integer.parseInt(scan.nextLine());
                    }
                    tempMarcher.editCoordinate(i, makeCoord());
                } else if (input.equals("r")) {
                    System.out.print("Please enter the index of the coordinate you would like to remove:\t");
                    int i = Integer.parseInt(scan.nextLine());
                    while (i >= tempMarcher.getNumCoords()) {
                        System.out.print("Out of bounds. Try again:\t");
                        i = Integer.parseInt(scan.nextLine());
                    }
                    tempMarcher.removeCoordinate(i);
                } else if (input.equals("d")) {
                    input = "d";
                    playerMarchingBand.writeToDoc(filePath);
                    System.exit(0);
                }
                playerMarchingBand.getSection(Integer.parseInt(currentEditorLevel.get(1)[1])).setMarcher(currentlyEditingIndex, tempMarcher);

            } else if (currentlyEditing.equals("section")) {
                Section tempSection = playerMarchingBand.getSection(currentlyEditingIndex);
                System.out.println(tempSection.getName() + ": ");
                for (int i = 0; i < tempSection.getNumMarchers(); i++) {
                    System.out.print(tempSection.getMarcher(i).getName() + "    ");
                }
                System.out.println("\n");
                System.out.println("Go back (b)\t\t\tEdit section name (n)\t\t\tAdd a marcher (a)\t\t\tEdit a marcher (e)\t\t\tRemove a marcher (r)");
                input = scan.nextLine();
                if (input.equals("b")) {
                    currentEditorLevel.remove(currentEditorLevel.size() - 1);
                    evaluateInput(input);
                } else if (input.equals("n")) {
                    System.out.print("Please enter the new name:\t");
                    tempSection.changeName(scan.nextLine());
                } else if (input.equals("a")) {
                    System.out.println("Please enter each new marcher on a new line. Press D (d) when you are done. ");
                    for (String i = scan.nextLine(); !i.equals("d"); i = scan.nextLine()) {
                        tempSection.addMember(new Marcher(i));
                    }
                } else if (input.equals("e")) {
                    System.out.print("Please enter the index of the marcher you would like to edit:\t");
                    int i = Integer.parseInt(scan.nextLine());
                    while (i >= tempSection.getNumMarchers()) {
                        System.out.print("Out of bounds. Try again:\t");
                        i = Integer.parseInt(scan.nextLine());
                    }
                    String[] tempArray = {"marcher", Integer.toString(i)};
                    currentEditorLevel.add(tempArray);
                    evaluateInput(input);
                } else if (input.equals("r")) {
                    System.out.print("Please enter the index of the marcher you would like to remove:\t");
                    int i = Integer.parseInt(scan.nextLine());
                    while (i >= tempSection.getNumMarchers()) {
                        System.out.print("Out of bounds. Try again:\t");
                        i = Integer.parseInt(scan.nextLine());
                    }
                    tempSection.removeMarcher(i);
                } else if (input.equals("d")) {
                    input = "d";
                    playerMarchingBand.writeToDoc(filePath);
                    System.exit(0);
                }
                playerMarchingBand.setSection(currentlyEditingIndex, tempSection);

            } else if (currentlyEditing.equals("band")) {
                System.out.println(playerMarchingBand.getName() + ": ");
                for (int i = 0; i < playerMarchingBand.getNumSections(); i ++) {
                    System.out.print(playerMarchingBand.getSection(i).getName() + "    ");
                }
                System.out.println("\n");
                System.out.println("Edit file name (f)\t\tEdit band name (n)\t\t\tAdd a section (a)\t\t\tEdit a section (e)\t\t\tRemove a section (r)");
                input = scan.nextLine();
                if (input.equals("f")) {
                    System.out.print("Please enter the new file name:\t");
                    filePath = scan.nextLine();
                } else if (input.equals("n")) {
                    System.out.print("Please enter the new band name:\t");
                    playerMarchingBand.changeName(scan.nextLine());
                } else if (input.equals("a")) {
                    System.out.print("Please enter the new section's name:\t");
                    playerMarchingBand.addSection(new Section(scan.nextLine()));
                } else if (input.equals("e")) {
                    System.out.print("Please enter the index of the section you would like to edit:\t");
                    int i = Integer.parseInt(scan.nextLine());
                    while (i >= playerMarchingBand.getNumSections()) {
                        System.out.print("Out of bounds. Try again:\t");
                        i = Integer.parseInt(scan.nextLine());
                    }
                    String[] tempArray = {"section", Integer.toString(i)};
                    currentEditorLevel.add(tempArray);
                    evaluateInput(input);
                } else if (input.equals("r")) {
                    System.out.print("Please enter the index of the section you would like to remove:\t");
                    int i = Integer.parseInt(scan.nextLine());
                    while (i >= playerMarchingBand.getNumSections()) {
                        System.out.print("Out of bounds. Try again:\t");
                        i = Integer.parseInt(scan.nextLine());
                    }
                    playerMarchingBand.removeSection(i);
                } else if (input.equals("d")) {
                    input = "d";
                    playerMarchingBand.writeToDoc(filePath);
                    System.exit(0);
                }
            } else {
                System.out.println("Congratulations! You have made it to an impossiple spot.\n" + //
                                "I Have no idea how you got here.\n" + //
                                "Please get help. Why did you break my code?");
            }
            playerMarchingBand.writeToDoc(filePath);
            evaluateInput(input);
        }

        return false;

    }

    private int[] toIntArray(String str) {

        String[] stringArray = str.split(", "); 


        int[] intArray = new int[stringArray.length];


        for (int i = 0; i < stringArray.length; i++) {
            try {
                intArray[i] = Integer.parseInt(stringArray[i]);
            } catch (NumberFormatException e) {
                System.err.println("Error converting '" + stringArray[i] + "' to an integer. Skipping.");
            }
        }

        return intArray;
    }

    private boolean readFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            playerMarchingBand = new MarchingBand(reader.readLine().replace(":", ""));
            Section tempSection = null;
            Marcher tempMarcher = null;
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.startsWith("\t\t\t")) {
                    try {
                        line = line.substring(3);
                        if (!line.equals("")) {
                            String[] coords = line.split("\t");
                            for (String coord:coords) {
                                tempMarcher.addCoordinate(tempMarcher.readableCoords(coord));
                            }
                        }
                    } catch (Exception e) {}
                    tempSection.addMember(tempMarcher);
                } else if (line.startsWith("\t\t")) {
                    if (tempMarcher != null) {
                        tempMarcher = null;
                    }
                    line = line.substring(2, line.length() - 1);
                    tempMarcher = new Marcher(line);
                } else if (line.startsWith("\t")) {
                    if (tempSection != null) {
                        tempMarcher = null;
                        playerMarchingBand.addSection(tempSection);
                    }
                    line = line.substring(1, line.length() - 1);
                    tempSection = new Section(line);
                }
            }
            
            if (tempSection != null) {
                playerMarchingBand.addSection(tempSection);
            }

            reader.close();
            currentEditorLevel.add("band, 0".split(", "));
            return true;

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }
    }

    private int[] makeCoord() {
        int[] coord = new int[2];
        coord[0] = 1;
        System.out.print("What side is it on? (1 or 2):\t");
        String input = scan.nextLine();
        if (input.equals("1")) {
            coord[0] = -1;
        }
        System.out.print("What yard line is it off of?\t");
        input = scan.nextLine();
        coord[0] *= ((50 - Integer.parseInt(input)) / 5) * 8;
        System.out.print("Is it off of the back sideline (bs), back hash (bh), front hash (fh), or front sideline (fs)?\t");
        input = scan.nextLine();
        if (input.equals("bs")) {
            coord[1] = -42;
        } else if (input.equals("bh")) {
            coord[1] = -14;
        } else if (input.equals("fh")) {
            coord[1] = 14;
        } else if (input.equals("fs")) {
            coord[1] = 42;
        }
        System.out.print("Is it in front of (f) or behind (b) the " + input + "?\t");
        input = scan.nextLine();
        if (input.equals("f")) {
            System.out.print("How many steps in front?\t");
            coord[1] += scan.nextInt();
        } else if (input.equals("b")) {
            System.out.print("How many steps behind?\t");
            coord[1] -= scan.nextInt();
        }
        return coord;
    }

}