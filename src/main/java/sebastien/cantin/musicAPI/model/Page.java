package sebastien.cantin.musicAPI.model;

public class Page {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Page Attributes
    int pageNumber;
    Jukebox[] jukeboxes;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Page(int number, Jukebox[] jukeboxes){
        this.pageNumber = number;
        this.jukeboxes = jukeboxes;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public int getPageNumber() {
        return pageNumber;
    }

    public Jukebox[] getJukeboxes() {
        return jukeboxes;
    }

}
