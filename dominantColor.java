//Reference for all classes: https://docs.oracle.com/javase/8/docs/
/*Bibliography====================================================
 * http://www.java2s.com/Code/Java/JavaFX/JavaFXImageZoomExample.htm
 * https://stackoverflow.com/questions/10530426/how-can-i-find-dominant-color-of-an-image
 * ================================================================
 */
import java.util.Collections;//This class consists of static methods with collections.
import java.util.Comparator;//A function that provides order in a collection of objects.
import java.util.LinkedList;//Implements Linked lists with the List and Deque interfaces.
import java.util.List;//Implements an ordered collection.
import java.util.Map;//Implements an object that maps keys to values.

/**
 *This is the class that provides all functions of the to find the dominant color of a painting
 */

public class dominantColor
{   
    //This method with the help of the method below finds the dominant color of a map which is a painting from tha art library
    public String DominantColor(Map painting)
    {
        List x = new LinkedList(painting.entrySet());
        Collections.sort(x, new Comparator() 
        {
                public int compare(Object object1, Object object2) 
                {
                    return ((Comparable) ((Map.Entry) (object1)).getValue())
                    .compareTo(((Map.Entry) (object2)).getValue());
                }
            });    
        Map.Entry me = (Map.Entry )x.get(x.size()-1);
        int[] rgb= getRGB((Integer)me.getKey());
        String r ="";
        String g ="";
        String b="";
        r=""+rgb[0];
        if(rgb[0]<100)
        {
            r="0"+rgb[0];
            if(rgb[0]<10)
            {
                r="00"+rgb[0];
            }
        }
        g=""+rgb[1];
        if(rgb[1]<100)
        {
            g="0"+rgb[1];
            if(rgb[1]<10)
            {
                r="00"+rgb[1];
            }
        } 
        b=""+rgb[2];
        if(rgb[2]<100)
        {
            b="0"+rgb[2];
            if(rgb[2]<10)
            {
                b="00"+rgb[2];
            }
        }    
        return r+""+g+""+b;        
    }    
    //This method compares the colors in order to find the one that is used the most
    public boolean comparecolor(int[] getRGBrr) 
    {
        int rgdifference = getRGBrr[0] - getRGBrr[1];
        int rbdifference = getRGBrr[0] - getRGBrr[2];
        int max = 10;
        if (rgdifference > max || rgdifference < -max) 
            if (rbdifference > max || rbdifference < -max) 
            { 
                return false;
            }                 
        return true;
    }
    //this method instantiates the color variables
    public int[] getRGB(int pixel) 
    {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red,green,blue};
    }
}
