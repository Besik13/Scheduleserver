package scheduleserver.bins.group;

public class Group {
    private int id;
    private String name;
    private int specialityId;

    public Group() {

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

    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialty) {
        this.specialityId = specialty;
    }
}
