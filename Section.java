import java.util.ArrayList;

public class Section {

    private String sectionName = null;
    private ArrayList<Marcher> sectionMembers = new ArrayList<>();

    public Section() {}

    public Section(String name) {
        sectionName = name;
    }

    public Section(ArrayList<Marcher> members) {
        sectionMembers = members;
    }

    public Section(String name, ArrayList<Marcher> members) {
        sectionName = name;
        sectionMembers = members;
    }

    public void addMember(Marcher member) {
        sectionMembers.add(member);
    }

    public Marcher getMarcher(int marcherIndex) {
        return sectionMembers.get(marcherIndex);
    }

    public void setMarcher(int marcherIndex, Marcher newMarcher) {
        sectionMembers.set(marcherIndex, newMarcher);
    }

    public ArrayList<Marcher> getMarchers() {
        return sectionMembers;
    }

    public void changeName(String newName) {
        sectionName = newName;
    }

    public String getName() {
        return sectionName;
    }

    public int getNumMarchers() {
        return sectionMembers.size();
    }

    public void removeMarcher(int marcherIndex) {
        sectionMembers.remove(marcherIndex);
    }

}