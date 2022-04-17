package sebastien.cantin.musicAPI.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class Page {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Page Attributes
    int number;
    Jukebox jukebox;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Page(int number, Jukebox jukebox){
        this.number = number;
        this.jukebox = jukebox;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public int getNumber() {
        return number;
    }

    public Jukebox getJukebox() {
        return jukebox;
    }

    @Override
    public String toString() {
        return "Page{" +
                "number=" + number +
                ", jukebox=" + jukebox +
                "}";
    }
}
