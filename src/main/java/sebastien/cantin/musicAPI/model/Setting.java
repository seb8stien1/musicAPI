package sebastien.cantin.musicAPI.model;

import java.util.List;

public class Setting {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Setting Attributes
    private String id;
    private List<String> requires;


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Setting(String id, List<String> requires)
    {
        this.id = id;
        this.requires = requires;
    }

    //------------------------
    // INTERFACE
    //------------------------


    public String getId() {
        return id;
    }

    public List<String> getRequires() {
        return requires;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "id='" + id + '\'' +
                ", requires=" + requires +
                '}';
    }

}
