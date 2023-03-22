//Reference for all classes: https://docs.oracle.com/javase/8/docs/
/*Bibliography====================================================
 * http://www.java2s.com/Code/Java/JavaFX/JavaFXImageZoomExample.htm
 * https://stackoverflow.com/questions/10530426/how-can-i-find-dominant-color-of-an-image
 * ================================================================*/
import java.awt.Color;//The Color class encapsulates colors in the default sRGB color spectrum.
import java.awt.image.BufferedImage;//The BufferedImage subclass converts an Image into an accessible buffer of image data.
import java.io.BufferedWriter;//Writes text as an output stream for easier writing of single characters, arrays, and strings.
import java.io.File;//A representation of file and directory pathnames.
import java.io.FilenameFilter;//Filters filenames.
import java.io.FileWriter;//A class that provides the ease of writing character files.
import java.io.IOException;//Signals that an exception has occurred.
import java.nio.file.Files;//Consists of static methods that operate on files.
import java.nio.file.Paths;//Used to locate a file in a file system.
import java.util.Collections;//This class consists of static methods with collections.
import java.util.Comparator;//A function that provides order in a collection of objects.
import java.util.HashMap;//Hash map implements a hash table interpretation of the map interface.
import java.util.Iterator;//Implements an iterator on a collection.
import java.util.LinkedList;//Implements Linked lists with the List and Deque interfaces.
import java.util.List;//Implements an ordered collection.
import java.util.Map;//Implements an object that maps keys to values.
import java.util.stream.*;//Supports operations on streams of elements.
import javafx.application.*;//Provides the application life-cycle classes.
import javafx.beans.InvalidationListener;//Notifies whenever an Observable becomes invalid.
import javafx.beans.Observable;//Implements an entity that wraps content and chacks it for invalidations.
import javafx.beans.property.*;//Defines read-only properties and writable properties
import javafx.collections.*;//Implements the basic components of JavaFX collections and collection utilities
import javafx.event.*;//Provides the basics of events, their delivery and handling.
import javafx.geometry.*;//Implements the set of 2D classes for operations on objects.
import javafx.scene.*;//Provides the basic compometnts of the JavaFX Scene Graph API.
import javafx.scene.control.*;//Provides the basics of JavaFX User Interface Controls.
import javafx.scene.control.Alert.AlertType;//The Alert class provides support for a number of pre built dialog types that prompt the user for a response.
import javafx.scene.control.cell.*;// Provides virtualized controls such as ListView, TreeView, and TableView.
import javafx.scene.image.Image;//Represents graphical images.
import javafx.scene.image.ImageView;//Is a Node used for painting images loaded with the above class.
import javafx.scene.input.ScrollEvent;//Indicates that user performed scrolling by mouse wheel or a similar device.
import javafx.scene.layout.*;//Supports the user interface layout.
import javafx.scene.Scene;//Contains all content in a scene graph.
import javafx.scene.text.*;//Contains fonts and renderable Text Nodes.
import javafx.stage.*;//Contains the top-level container classes for JavaFX content.
import javafx.util.converter.*;//This package provides standard string converters for JavaFX.
import javax.imageio.ImageIO;//A class that contains methods for locating ImageReaders and ImageWriters.
import javax.imageio.ImageReader;//A superclass for parsing and decoding images.
import javax.imageio.stream.ImageInputStream;//A class that implemens the ImageInputStream interface.
import javax.swing.ImageIcon;//Implements the Icon interface that creates Icons from Images.
import javax.swing.JFrame;//An extended version of java.awt.Frame that adds support for the Swing component architecture.
import javax.swing.JScrollPane;//Provides a scrollable view.
import javax.swing.JTable;//Displays and edits regular two-dimensional tables.
import javax.swing.JOptionPane;//JOptionPane makes easy pop ups and standard dialog boxes.
/**
 * 
 *This is the class that provides all functions of the program
 * 
 */

public class PaintingList extends Application 
{
    final ObservableList<Painting> Data = FXCollections.observableArrayList();
    //This is the main method of the program used to launch the javafx application.
    public static void main(String[] args)throws Exception
    {
        launch(args);
    }

    private TableView<Painting> table;
    private TextField txtName, txtCatNumb, txtYear, txtDimensions, txtDrawnon, txtDrawnwith, txtOwnership;
    private ImageView IV = new ImageView();
    private ScrollPane SP = new ScrollPane();
    final DoubleProperty ZP = new SimpleDoubleProperty(200);
    //method to start the primary stage of the program.
    @Override public void start(Stage primaryStage){
        dominantColor d = new dominantColor();
        Label lblHeading = new Label("Painting List");
        lblHeading.setFont(new Font("Arial", 20));
        table = new TableView<Painting>();
        table.setEditable(true);
        table.setItems(loadData());
        TableColumn colName = new TableColumn("Name");
        colName.setMinWidth(300);
        colName.setCellValueFactory( new PropertyValueFactory<Painting, String>("Name"));
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setOnEditCommit( e -> colName_OnEditCommit(e));
        TableColumn colCatNumb = new TableColumn("Catalogue Number");
        colCatNumb.setMinWidth(150);
        colCatNumb.setCellValueFactory( new PropertyValueFactory<Painting, Integer>("CatNumb"));
        colCatNumb.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colCatNumb.setOnEditCommit(  e -> colCatNumb_OnEditCommit(e));
        TableColumn colYear = new TableColumn("Year");
        colYear.setMinWidth(100);
        colYear.setCellValueFactory( new PropertyValueFactory<Painting, Integer>("Year"));
        colYear.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colYear.setOnEditCommit( e -> colYear_OnEditCommit(e));
        TableColumn colDimensions = new TableColumn("Dimensions");
        colDimensions.setMinWidth(100);
        colDimensions.setCellValueFactory( new PropertyValueFactory<Painting, String>("Dimensions"));
        colDimensions.setCellFactory(TextFieldTableCell.forTableColumn());
        colDimensions.setOnEditCommit( e -> colDimensions_OnEditCommit(e));
        TableColumn colDrawnon = new TableColumn("Drawn on");
        colDrawnon.setMinWidth(100);
        colDrawnon.setCellValueFactory( new PropertyValueFactory<Painting, String>("Drawnon"));
        colDrawnon.setCellFactory(TextFieldTableCell.forTableColumn());
        colDrawnon.setOnEditCommit( e -> colDrawnon_OnEditCommit(e));
        TableColumn colDrawnwith = new TableColumn("Drawn with");
        colDrawnwith.setMinWidth(150);
        colDrawnwith.setCellValueFactory( new PropertyValueFactory<Painting, String>("Drawnwith"));
        colDrawnwith.setCellFactory(TextFieldTableCell.forTableColumn());
        colDrawnwith.setOnEditCommit( e -> colDrawnwith_OnEditCommit(e));
        TableColumn colOwnership = new TableColumn("Previous Ownership");
        colOwnership.setMinWidth(150);
        colOwnership.setCellValueFactory( new PropertyValueFactory<Painting, String>("Ownership"));
        colOwnership.setCellFactory(TextFieldTableCell.forTableColumn());
        colOwnership.setOnEditCommit( e -> colOwnership_OnEditCommit(e));
        table.getColumns().addAll(colName, colCatNumb, colYear, colDimensions, colDrawnon, colDrawnwith, colOwnership);
        txtName = new TextField();
        txtName.setPromptText("Name");
        txtName.setMinWidth(100);
        txtCatNumb = new TextField();
        txtCatNumb.setMaxWidth(100);
        txtCatNumb.setPromptText("Catalogue Number");
        txtYear = new TextField();
        txtYear.setMaxWidth(100);
        txtYear.setPromptText("Year");
        txtDimensions = new TextField();
        txtDimensions.setPromptText("Dimensions");
        txtDimensions.setMinWidth(100);
        txtDrawnon = new TextField();
        txtDrawnon.setPromptText("Drawn on");
        txtDrawnon.setMinWidth(100);
        txtDrawnwith = new TextField();
        txtDrawnwith.setPromptText("Drawn with");
        txtDrawnwith.setMinWidth(100);
        txtOwnership = new TextField();
        txtOwnership.setPromptText("Previous Ownership");
        txtOwnership.setMinWidth(100);
        Button btnSearchNameY = new Button("Search by Name and Year");
        btnSearchNameY.setMinWidth(60);
        btnSearchNameY.setOnAction(e -> btnSearchNameY_Clicked());
        Button btnSearchYear = new Button("Search by Year");
        btnSearchYear.setMinWidth(60);
        btnSearchYear.setOnAction(e -> btnSearchYear_Clicked());
        Button btnSearchName = new Button("Search by Name");
        btnSearchName.setMinWidth(60);
        btnSearchName.setOnAction(e -> btnSearchName_Clicked());
        Button btnSearchCatNumb = new Button("Search by Catalogue Number");
        btnSearchCatNumb.setMinWidth(60);
        btnSearchCatNumb.setOnAction(e -> btnSearchCatNumb_Clicked());
        Button btnShow = new Button("Show");
        btnShow.setMinWidth(60);
        Button btnColor = new Button("Dominant Color");
        btnColor.setMinWidth(60);
        Button btnAdd = new Button ("Add to collection");
        btnAdd.setMinWidth(60);
        btnAdd.setOnAction(e -> btnAdd_Clicked());
        txtName.focusedProperty().addListener((arg0, oldValue, newValue) ->
            {
                if(!newValue)
                {
                    if(!txtName.getText().matches("[a-zA-Z]+"))
                    {
                        Alert txtNameerror = new Alert(AlertType.ERROR);
                        txtNameerror.setTitle("Error");
                        txtNameerror.setHeaderText("Data type error");
                        txtNameerror.setContentText("You have enter an incorrect data type in the Name textfield");
                        txtNameerror.showAndWait();
                    }
                }
            }
        );
        txtCatNumb.focusedProperty().addListener((arg0, oldValue, newValue) ->
            {
                if(!newValue)
                {
                    if(!txtCatNumb.getText().matches("\\d+"))
                    {
                        Alert txtCatNumberror = new Alert(AlertType.ERROR);
                        txtCatNumberror.setTitle("Error");
                        txtCatNumberror.setHeaderText("Data type error");
                        txtCatNumberror.setContentText("You have enter an incorrect data type in the Catalogue Number textfield");
                        txtCatNumberror.showAndWait();
                    }
                }
            }
        );
        txtYear.focusedProperty().addListener((arg0, oldValue, newValue) ->
            {
                if(!newValue)
                {
                    if(!txtYear.getText().matches("\\d+"))
                    {
                        Alert txtYearerror = new Alert(AlertType.ERROR);
                        txtYearerror.setTitle("Error");
                        txtYearerror.setHeaderText("Data type error");
                        txtYearerror.setContentText("You have enter an incorrect data type in the Year textfield");
                        txtYearerror.showAndWait();
                    }
                }
            }
        );
        txtDrawnon.focusedProperty().addListener((arg0, oldValue, newValue) ->
            {
                if(!newValue)
                {
                    if(!txtDrawnon.getText().matches("[a-zA-Z]+"))
                    {
                        Alert txtDrawnonerror = new Alert(AlertType.ERROR);
                        txtDrawnonerror.setTitle("Error");
                        txtDrawnonerror.setHeaderText("Data type error");
                        txtDrawnonerror.setContentText("You have enter an incorrect data type in the Drawn on textfield");
                        txtDrawnonerror.showAndWait();
                    }
                }
            }
        );
        txtDrawnwith.focusedProperty().addListener((arg0, oldValue, newValue) ->
            {
                if(!newValue)
                {
                    if(!txtDrawnwith.getText().matches("[a-zA-Z]+"))
                    {
                        Alert txtDrawnwitherror = new Alert(AlertType.ERROR);
                        txtDrawnwitherror.setTitle("Error");
                        txtDrawnwitherror.setHeaderText("Data type error");
                        txtDrawnwitherror.setContentText("You have enter an incorrect data type in the Drawn with textfield");
                        txtDrawnwitherror.showAndWait();
                    }
                }
            }
        );
        txtOwnership.focusedProperty().addListener((arg0, oldValue, newValue) ->
            {
                if(!newValue)
                {
                    if(!txtOwnership.getText().matches("[a-zA-Z]+"))
                    {
                        Alert txtOwnershiperror = new Alert(AlertType.ERROR);
                        txtOwnershiperror.setTitle("Error");
                        txtOwnershiperror.setHeaderText("Data type error");
                        txtOwnershiperror.setContentText("You have enter an incorrect data type in the Previous Ownership textfield");
                        txtOwnershiperror.showAndWait();
                    }
                }
            }
        );
        Button btnRemove = new Button ("Remove from collection");
        btnRemove.setMinWidth(60);
        btnRemove.setOnAction(e -> btnRemove_Clicked());
        Button btnSave = new Button ("Save to collection");
        btnSave.setMinWidth(60);
        btnSave.setOnAction(e -> btnSave_Clicked());
        //This method provides the zoom property to the scroll event method.
        ZP.addListener(new InvalidationListener()
            {//
                @Override public void invalidated(Observable arg0) {
                    IV.setFitWidth(ZP.get() * 4);
                    IV.setFitHeight(ZP.get() * 3);
                }
            });
        //This method provides the scroll property to the stage used for the show button.
        SP.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>()
            {
                @Override public void handle(ScrollEvent event) {
                    if (event.getDeltaY() > 0) {
                        ZP.set(ZP.get() * 1.1);
                    } else if (event.getDeltaY() < 0) {
                        ZP.set(ZP.get() / 1.1);
                    }
                }
            });
        btnShow.setOnAction(new EventHandler<ActionEvent>()
            {//This is the method that dictates what the show button does.
                @Override public void handle(ActionEvent event) {
                    ObservableList<Painting> sel, items ;
                    items = table.getItems();
                    sel= table.getSelectionModel ().getSelectedItems();
                    int C =sel.get(0).getCatNumb();
                    final String s=Integer.toString(C);
                    File dir = new File("paintings");
                    JFrame frame = new JFrame("Error");
                    FilenameFilter filter = new FilenameFilter() 
                        {
                            public boolean accept (File dir, String name) { 
                                return name.equals( s+".jpg");
                            } 
                        }; 
                    String[] children = dir.list(filter);
                    if (children == null) {
                        JOptionPane.showMessageDialog(frame,"The folder does not exist"); 
                    } 
                    else
                    {
                        if(children.length==0)
                        {JOptionPane.showMessageDialog(frame,"The image does not exist");}
                        else{
                            IV.setImage(new Image("paintings/"+ s +".jpg"));
                            IV.preserveRatioProperty().set(true);
                            SP.setContent(IV);
                            Scene scene = new Scene(SP, 500, 600);
                            Stage a =new Stage();
                            a.setScene(scene);
                            a.setTitle("Painting ");
                            a.show();
                        }
                    }
                }
            });
        btnColor.setOnAction(new EventHandler<ActionEvent>()
            {//This is the method that dictates what the Dominant color button does.
                @Override public void handle(ActionEvent event)  {
                    ObservableList<Painting> sel, items ;
                    items = table.getItems();
                    sel= table.getSelectionModel ().getSelectedItems();
                    String s = "";
                    int C =sel.get(0).getCatNumb();
                    JFrame frame = new JFrame("Error");
                    s=Integer.toString(C);
                    try{
                        File Painting = new File("paintings/"+ s +".jpg");
                        ImageInputStream Input = ImageIO.createImageInputStream(Painting);
                        Iterator iterator = ImageIO.getImageReaders(Input);
                        ImageReader imageReader = (ImageReader)iterator.next();
                        imageReader.setInput(Input);
                        BufferedImage image = imageReader.read(0);
                        int H = image.getHeight();
                        int W = image.getWidth();
                        Map painting = new HashMap();
                        for(int wi=0; wi < W ; wi++)
                        {
                            for(int hi=0; hi < H ; hi++)
                            {
                                int rgb = image.getRGB(wi, hi);
                                int[] getRGBrr = d.getRGB(rgb);                
                                if (!d.comparecolor(getRGBrr)) {                
                                    Integer count = (Integer) painting.get(rgb);   
                                    if (count == null)
                                        count = 0;
                                    count++;                                
                                    painting.put(rgb, count);                
                                }                
                            }
                        }        
                        String color= d.DominantColor(painting);
                        int red = Integer.valueOf(color.substring(0,3));
                        int green = Integer.valueOf(color.substring(3,6));
                        int blue = Integer.valueOf(color.substring(6,9));
                        JFrame frame2 = new JFrame("Dominant color");
                        frame2.getContentPane().setBackground(new Color(red, green, blue));
                        frame2.setSize(300,300);
                        frame2.setLocationRelativeTo(null);
                        frame2.setVisible(true);
                    }
                    catch(IOException f){
                        JFrame error = new JFrame("Error");
                        JOptionPane.showMessageDialog(error,"Unable to load data from Painting file" + f.toString());
                    }
                }
            });
        HBox paneAdd = new HBox();
        paneAdd.getChildren().addAll(txtName, txtCatNumb, txtYear, txtDimensions, txtDrawnon, txtDrawnwith, txtOwnership, btnShow);
        paneAdd.setSpacing(8);
        HBox paneAdd2 = new HBox();
        paneAdd2.getChildren().addAll(btnColor, btnAdd, btnRemove, btnSave);
        paneAdd2.setSpacing(8);
        HBox paneAdd3 = new HBox();
        paneAdd3.getChildren().addAll(btnSearchNameY,btnSearchName,btnSearchYear,btnSearchCatNumb);
        paneAdd3.setSpacing(8);
        VBox paneMain = new VBox();
        paneMain.setSpacing(10);
        paneMain.setPadding(new Insets(10, 10, 10, 10));
        paneMain.getChildren().addAll(lblHeading, table, paneAdd, paneAdd2,paneAdd3);
        Scene scene = new Scene(paneMain);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Painting List");
        primaryStage.show();
    }
    //This is the method that loads the data from the paint.txt file.
    public ObservableList<Painting> loadData()
    {
        try{
            Stream<String> rows = Files.lines(Paths.get("Paint.txt"));
            rows.forEach((String text) ->
                {
                    String [] Dat = text.split(",");
                    Data.add(new Painting (Dat[0], Integer.parseInt(Dat[1]), Integer.parseInt(Dat[2]), Dat[3], Dat[4], Dat[5], Dat[6]));
                });
        }
        catch(IOException f){
            JFrame error = new JFrame("Error");
            JOptionPane.showMessageDialog(error,"Unable to load data from Painting file" + f.toString());
        }
        return Data;
    }
    //This method allows with the use of javafx to edit the column "Name".
    public void colName_OnEditCommit(Event e)
    {
        TableColumn.CellEditEvent<Painting, String> ce;
        ce = (TableColumn.CellEditEvent<Painting, String>) e;
        Painting p = ce.getRowValue();
        p.setName(ce.getNewValue());
    }
    //This method allows with the use of javafx to edit the column "Catalogue Number".
    public void colCatNumb_OnEditCommit(Event e)
    {
        TableColumn.CellEditEvent<Painting, Integer> ce;
        ce = (TableColumn.CellEditEvent<Painting, Integer>) e;
        Painting p = ce.getRowValue();
        p.setCatNumb(ce.getNewValue());
    }
    //This method allows with the use of javafx to edit the column "Year".
    public void colYear_OnEditCommit(Event e)
    {
        TableColumn.CellEditEvent<Painting, Integer> ce;
        ce = (TableColumn.CellEditEvent<Painting, Integer>) e;
        Painting p = ce.getRowValue();
        p.setYear(ce.getNewValue());
    }
    //This method allows with the use of javafx to edit the column "Dimensions".
    public void colDimensions_OnEditCommit(Event e)
    {
        TableColumn.CellEditEvent<Painting, String> ce;
        ce = (TableColumn.CellEditEvent<Painting, String>) e;
        Painting p = ce.getRowValue();
        p.setDimensions(ce.getNewValue());
    }
    //This method allows with the use of javafx to edit the column "Drawn on".
    public void colDrawnon_OnEditCommit(Event e)
    {
        TableColumn.CellEditEvent<Painting, String> ce;
        ce = (TableColumn.CellEditEvent<Painting, String>) e;
        Painting p = ce.getRowValue();
        p.setDrawnon(ce.getNewValue());
    }
    //This method allows with the use of javafx to edit the column "Drawn with".
    public void colDrawnwith_OnEditCommit(Event e)
    {
        TableColumn.CellEditEvent<Painting, String> ce;
        ce = (TableColumn.CellEditEvent<Painting, String>) e;
        Painting p = ce.getRowValue();
        p.setDrawnwith(ce.getNewValue());
    }
    //This method allows with the use of javafx to edit the column "Previous Ownership".
    public void colOwnership_OnEditCommit(Event e)
    {
        TableColumn.CellEditEvent<Painting, String> ce;
        ce = (TableColumn.CellEditEvent<Painting, String>) e;
        Painting p = ce.getRowValue();
        p.setOwnership(ce.getNewValue());
    }
    //This is the method that dictates what the Search by Name and Year button does.
    public void btnSearchNameY_Clicked()
    {
        Painting p = new Painting();
        p.setYear(Integer.parseInt(txtYear.getText()));
        p.setName(txtName.getText());
        ObservableList<Painting> sel, items;
        items = table.getItems();
        int c = 0;
        int i = 0;
        String [] array = new String [30];
        String [] counter = new String [30];
        for (Painting pp : items)
        { 
            if ((pp.getYear()==p.getYear())&&(pp.getName().contains(p.getName())))
            {
                array [i]=pp.toString();
                counter [i] = Integer.toString(i+1);
                i++;
            }c++;
        }
        JFrame Search = new JFrame("Search");
        Search.setSize(600,600);
        Search.setLocationRelativeTo(null);
        String[][] data = new String [i][2];
        for(int x=0; x<i; x++)
        {
            data [x][0]=counter[x];
            data [x][1]=array[x];
        }
        String [] header = {"id","Painting"};
        JTable table2 = new JTable(data,header);
        table2.setBounds(50,70,300,450);
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table2.getColumnModel().getColumn(0).setPreferredWidth(20);
        JScrollPane sp2 = new JScrollPane(table2);
        Search.add(sp2);
        Search.setVisible(true);
        Search.setVisible(true);
        txtName.clear();
        txtYear.clear();
    }
    //This is the method that dictates what the Search by Year button does.
    public void btnSearchYear_Clicked()
    {
        Painting p = new Painting();
        p.setYear(Integer.parseInt(txtYear.getText()));
        ObservableList<Painting> sel, items;
        items = table.getItems();
        int c = 0;
        int i = 0;
        String [] array = new String [30];
        String [] counter = new String [30];
        for (Painting pp : items)
        { 
            if (pp.getYear()==p.getYear())
            {
                array [i]=pp.toString();
                counter [i] = Integer.toString(i+1);
                i++;
            }c++;
        }
        JFrame Search = new JFrame("Search");
        Search.setSize(600,600);
        Search.setLocationRelativeTo(null);
        String[][] data = new String [i][2];
        for(int x=0; x<i; x++)
        {
            data [x][0]=counter[x];
            data [x][1]=array[x];
        }
        String [] header = {"id","Painting"};
        JTable table2 = new JTable(data,header);
        table2.setBounds(50,70,300,450);
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table2.getColumnModel().getColumn(0).setPreferredWidth(20);
        JScrollPane sp2 = new JScrollPane(table2);
        Search.add(sp2);
        Search.setVisible(true);
        Search.setVisible(true); 
        txtYear.clear();
    }
    //This is the method that dictates what the Search by Name button does.
    public void btnSearchName_Clicked()
    {
        Painting p = new Painting();
        p.setName(txtName.getText());
        ObservableList<Painting> sel, items;
        items = table.getItems();
        int c = 0;
        int i = 0;
        String [] array = new String [30];
        String [] counter = new String [30];
        for (Painting pp : items)
        {
            if (pp.getName().contains(p.getName()))
            {
                array [i]=pp.toString();
                counter [i] = Integer.toString(i+1);
                i++;
            }c++;
        }
        JFrame Search = new JFrame("Search");
        Search.setSize(600,600);
        Search.setLocationRelativeTo(null);
        String[][] data = new String [i][2];
        for(int x=0; x<i; x++)
        {
            data [x][0]=counter[x];
            data [x][1]=array[x];
        }
        String [] header = {"id","Painting"};
        JTable table2 = new JTable(data,header);
        table2.setBounds(50,70,300,450);
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table2.getColumnModel().getColumn(0).setPreferredWidth(20);
        JScrollPane sp2 = new JScrollPane(table2);
        Search.add(sp2);
        Search.setVisible(true);
        Search.setVisible(true);
        txtName.clear();
    }
    //This is the method that dictates what the Search by Catalogue Number button does.
    public void btnSearchCatNumb_Clicked()
    {
        Painting p = new Painting();
        p.setCatNumb(Integer.parseInt(txtCatNumb.getText()));
        ObservableList<Painting> sel, items;
        items = table.getItems();
        int c = 0;
        int i = 0;
        String [] array = new String [30];
        String [] counter = new String [30];
        for (Painting pp : items)
        { 
            if (pp.getCatNumb()==p.getCatNumb())
            {
                array [i]=pp.toString();
                counter [i] = Integer.toString(i+1);
                i++;
            }c++;
        }
        JFrame Search = new JFrame("Search");
        Search.setSize(600,600);
        Search.setLocationRelativeTo(null);
        String[][] data = new String [i][2];
        for(int x=0; x<i; x++)
        {
            data [x][0]=counter[x];
            data [x][1]=array[x];
        }
        String [] header = {"id","Painting"};
        JTable table2 = new JTable(data,header);
        table2.setBounds(50,70,300,450);
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table2.getColumnModel().getColumn(0).setPreferredWidth(20);
        JScrollPane sp2 = new JScrollPane(table2);
        Search.add(sp2);
        Search.setVisible(true);
        Search.setVisible(true);
        txtCatNumb.clear();
    }
    //This is the method that dictates what the Add button does.
    public void btnAdd_Clicked()
    {   Painting p= new Painting();
        p.setName(txtName.getText());
        p.setCatNumb(Integer.parseInt(txtCatNumb.getText()));
        p.setYear(Integer.parseInt(txtYear.getText()));
        p.setDimensions(txtDimensions.getText());
        p.setDrawnon(txtDrawnon.getText());
        p.setDrawnwith(txtDrawnwith.getText());
        p.setOwnership(txtOwnership.getText());
        table.getItems().add(p);
        txtName.clear();
        txtCatNumb.clear();
        txtYear.clear();
        txtDimensions.clear();
        txtDrawnon.clear();
        txtDrawnwith.clear();
        txtOwnership.clear();
    }
    //This is the method that dictates what the Remove button does.
    public void btnRemove_Clicked()
    {
        ObservableList<Painting> sel, items;
        items = table.getItems();
        sel = table.getSelectionModel().getSelectedItems();
        for (Painting p : sel)
            items.remove(p); 
    }
    //This is the method that dictates what the Save button does.
    public void btnSave_Clicked(){
        ObservableList<Painting>sel, items;
        items = table.getItems();
        try( BufferedWriter BW =new BufferedWriter(new FileWriter("Paint.txt")))
        {
            Painting p;
            int i = 0;
            for (Object line : items)
            {
                p= items.get(i);
                BW.write(p.getName()+","+p.getCatNumb()+","+p.getYear()+","+p.getDimensions()+","+p.getDrawnon()+","+p.getDrawnwith()+","+p.getOwnership());
                BW.newLine();
                i++;
            }
            BW.close();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("");
            alert.setContentText("The changes have been saved");
            alert.showAndWait();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

