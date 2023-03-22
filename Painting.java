//Reference for all classes: https://docs.oracle.com/javase/8/docs/
/**
 * This is the class Painting that instantiates Painting objects
 */
public class Painting
//Instance variables
{   private String Name;
    private int CatNumb;
    private int Year;
    private String Dimensions;
    private String Drawnon;
    private String Drawnwith;
    private String Ownership;
//No argument constructor
    public Painting(){
        this.Name = "";
        this.CatNumb = 0;
        this.Year = 0;
        this.Dimensions = "";
        this.Drawnon = "";
        this.Drawnwith = "";
        this.Ownership = "";
    }
//Constructor with parameters
    public Painting(String Name, int CatNumb, int Year, String Dimensions, String Drawnon, String Drawnwith, String Ownership){
        this.Name = Name;
        this.CatNumb = CatNumb;
        this.Year = Year;
        this.Dimensions = Dimensions;
        this.Drawnon = Drawnon;
        this.Drawnwith = Drawnwith;
        this.Ownership = Ownership;

    }
//Accesor for Name
    public String getName(){
        return Name;
    }
//Mutator for Name
    public void setName(String Name){
        this.Name=Name;
    }
//Accesor for CatNumb
    public int getCatNumb(){
        return CatNumb;
    }
//Mutator for CatNumb
    public void setCatNumb(int CatNumb){
        this.CatNumb=CatNumb;
    }
//Accesor for Year
    public int getYear(){
        return Year;
    }
//Mutator for Year
    public void setYear(int Year){
        this.Year=Year;
    }
//Accesor for Dimensions
    public String getDimensions(){
        return Dimensions;
    }
//Mutator for Dimensions
    public void setDimensions(String Dimensions){
        this.Dimensions=Dimensions;
    }
//Accesor for Drawnon
    public String getDrawnon(){
        return Drawnon;
    }
//Mutator for Drawnon
    public void setDrawnon(String Drawnon){
        this.Drawnon=Drawnon;
    }
//Accesor for Drawnwith
    public String getDrawnwith(){
        return Drawnwith;
    }
//Mutator for Drawnwith
    public void setDrawnwith(String Drawnwith){
        this.Drawnwith=Drawnwith;
    }
//Accesor for Ownership
    public String getOwnership(){
        return Ownership;
    }
//Mutator for Ownership
    public void setOwnership(String Ownership){
        this.Ownership=Ownership;
    }
//Method to convert to string mainly used for validation
    public String toString(){
        return Name + ", " + CatNumb + ", " + Year + ", " + Dimensions + ", " + Drawnon + ", " + Drawnwith + ", " + Ownership;
    }
}
