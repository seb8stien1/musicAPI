package sebastien.cantin.musicAPI.model;

import java.util.List;

public class Jukebox {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Jukebox Attributes
    private String id;
    private String model;
    private List components;


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Jukebox(String id, String model, List components)
    {
        this.id = id;
        this.model = model;
        this.components = components;
    }

    //------------------------
    // INTERFACE
    //------------------------


    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public List getComponents() {
        return components;
    }

    @Override
    public String toString() {
        return "Jukebox{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", components=" + components +
                '}';
    }
}
