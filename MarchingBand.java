import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MarchingBand {

    private String bandName = null;
    private ArrayList<Section> bandSections = new ArrayList<>();

    public MarchingBand() {}

    public MarchingBand(String name) {
        bandName = name;
    }

    public MarchingBand(ArrayList<Section> sections) {
        bandSections = sections;
    }

    public MarchingBand(String name, ArrayList<Section> sections) {
        bandName = name;
        bandSections = sections;
    }

    public void addSection(Section section) {
        bandSections.add(section);
    }

    public Section getSection(int sectionIndex) {
        return bandSections.get(sectionIndex);
    }

    public void setSection(int sectionIndex, Section newSection) {
        bandSections.set(sectionIndex, newSection);
    }

    public String getName() {
        return bandName;
    }

    public void changeName(String newName) {
        bandName = newName;
    }

    public int getNumSections() {
        return bandSections.size();
    }

    public void removeSection(int sectionIndex) {
        bandSections.remove(sectionIndex);
    }

    public void writeToDoc(String filePath) {
        File file = new File(filePath);
        file.setWritable(true);
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(bandName + ":\n");
            for (Section tempSection:bandSections) {
                writer.write("\n\t" + tempSection.getName() + ":\n");
                for (Marcher tempMarcher:tempSection.getMarchers()) {
                    writer.write("\n\t\t" + tempMarcher.getName() + ":\n\t\t\t");
                    for (int[] coord:tempMarcher.getCoords()) {
                        writer.write(tempMarcher.printableCoords(coord) + "\t");
                    }
                }
            }

            writer.close();
        } catch (IOException e) {
            System.err.println("error");
        }

    }

}