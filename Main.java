package ie.gmit.mypackage;

import java.io.File;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application implements Serializable {

    private static final long serialVersionUID = 1L; // Used for serialization
    BoxerManager bm = new BoxerManager(); // Used for managing boxers

    @Override
    public void start(Stage primaryStage) {
        // Create TextArea node for bottom of scene 1
        TextArea taMyOutput = new TextArea();
        taMyOutput.setPrefHeight(100); // sets height of the TextArea to 400 pixels
        taMyOutput.setPrefWidth(100); // sets width of the TextArea to 300 pixels

        // Show total number of boxers
        Button btnShowTotal = new Button("Show Total Boxers");
        TextField tfTotalNumberOfBoxers = new TextField();

        tfTotalNumberOfBoxers.setEditable(false); // This box is not editable. Only displays result.
        tfTotalNumberOfBoxers.setPromptText("0");

        btnShowTotal.setOnAction(e -> {

            // Code to run when button is clicked
            tfTotalNumberOfBoxers.setText(Integer.toString(bm.findTotalBoxers()));

        });

        // Add Boxer
        TextField tfBoxerID = new TextField();
        TextField tfBoxerFirstName = new TextField();
        TextField tfBoxerSurname = new TextField();
        // TextField tfBoxerFights = new TextField();

        Button btnAddBoxer = new Button("Add Boxer");
        
        tfBoxerID.setPromptText("Boxer ID");
        tfBoxerFirstName.setPromptText("First Name");
        tfBoxerSurname.setPromptText("Surname");
        // tfBoxerFights.setPromptText("Surname");

        btnAddBoxer.setOnAction(e -> {
            if (tfBoxerID.getText().trim().equals("")) { // If text field is empty
                taMyOutput.setText("Invalid");
            } else {
               Boxer boxer = new Boxer(tfBoxerID.getText(), tfBoxerFirstName.getText(), tfBoxerSurname.getText());
                bm.addBoxer(boxer); // Add boxer to boxer list
                tfBoxerID.clear();
                tfBoxerFirstName.clear();
                tfBoxerSurname.clear();
                //tfBoxerFights.clear();
            }
        });

        // Delete Boxer
        TextField tfBoxerDel = new TextField();
        Button btnDelBoxer = new Button("Delete Boxer");

        tfBoxerDel.setPromptText("Enter Boxer ID");

        btnDelBoxer.setOnAction(e -> {

            bm.deleteBoxerById(tfBoxerDel.getText());

        });

        // Search for Boxer by ID
        TextField tfBoxerSearch = new TextField();
        Button btnBoxerSearch = new Button("Search By ID");

        tfBoxerSearch.setPromptText("EnterBoxer ID");

        btnBoxerSearch.setOnAction(e -> {

            Boxer boxerObj = bm.searchForBoxerById(tfBoxerSearch.getText());
            taMyOutput.setText(boxerObj.getFirstName() + " " + boxerObj.getSurname());

        });





        // Save to DB
        Button btnSaveDB = new Button("Save Boxers to DB");
        btnSaveDB.setOnAction(e -> {
            if (bm.findTotalBoxers() > 0) {
                try {
                    File boxerDB = new File("./resources/boxersDB.ser");
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(boxerDB));
                    out.writeObject(bm);
                    out.close();
                    taMyOutput.setText("Boxer Database Saved");
                } catch (Exception exception) {
                    System.out.print("[Error] Cannont save DB. Cause: ");
                    exception.printStackTrace();
                    taMyOutput.setText("ERROR: Failed to save Boxers DB!");
                }
            } else {
                taMyOutput.setText("No Boxers in List to save!");
            }
        });

        // Load from DB
        Button btnLoadDB = new Button("Load Boxers from DB");
        TextField tfLoadBoxers = new TextField();

        tfLoadBoxers.setPromptText("Please enter DB path");
        btnLoadDB.setOnAction(e -> {

            try{
                File boxerDB = new File(tfLoadBoxers.getText());
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(boxerDB));
                bm = (BoxerManager) in.readObject();
                in.close();
                taMyOutput.setText("Successfully loaded Boxers from Database");
            } catch (Exception exception) {
                    System.out.print("[Error] Cannont load DB. Cause: ");
                    exception.printStackTrace();
                    taMyOutput.setText("ERROR: Failed to load Boxers DB!");
            }

        });

        // Add Quit button
		Button btnQuit = new Button("Quit");	
        btnQuit.setOnAction(e -> 
            Platform.exit()
        );

        // Adding and arranging all the nodes in the grid - add(node, column, row)
        GridPane gridPane1 = new GridPane();
        gridPane1.add(tfBoxerID, 0, 0);
        gridPane1.add(tfBoxerFirstName, 1, 0);
        gridPane1.add(tfBoxerSurname, 2, 0);
       // gridPane1.add(tfBoxerFights, 3, 0);
        gridPane1.add(btnAddBoxer, 3, 0);
        
        
        gridPane1.add(btnShowTotal, 0, 1);
        gridPane1.add(tfTotalNumberOfBoxers, 1, 1);
        gridPane1.add(tfBoxerDel, 0, 2);
        gridPane1.add(btnDelBoxer, 1, 2);

        gridPane1.add(tfBoxerSearch, 0, 3);
        gridPane1.add(btnBoxerSearch, 1, 3);
        
        gridPane1.add(btnSaveDB, 0, 4);
        gridPane1.add(btnLoadDB, 0, 5);
        gridPane1.add(tfLoadBoxers, 1, 5);
        gridPane1.add(taMyOutput, 0, 6, 2, 1);
        gridPane1.add(btnQuit, 0, 7);

        // Preparing the Stage (i.e. the container of any JavaFX application)
        // Create a Scene by passing the root group object, height and width
        Scene scene1 = new Scene(gridPane1, 400, 450);
        // Setting the title to Stage.

        if (getParameters().getRaw().size() == 0) {
            primaryStage.setTitle("Boxer Manager Application");
        } else {
            primaryStage.setTitle(getParameters().getRaw().get(0));
        }

        // Setting the scene to Stage
        primaryStage.setScene(scene1);
        // Displaying the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
