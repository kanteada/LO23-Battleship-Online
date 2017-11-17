/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiTable.controller;

import guiTable.BoatDrawing;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import packageStructDonnées.BoatType;

/**
 *
 * @author caioz
 */
public class placementPhaseClassicController implements Initializable{
    
    @FXML
    private Rectangle porteAvionsRectangle;
    @FXML
    private Rectangle croiseurRectangle;
    @FXML
    private Rectangle contreTorpilleurRectangle;
    @FXML
    private Rectangle sousMarinRectangle;
    @FXML
    private Rectangle torpilleurRectangle; 
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane table;
    
    private static final int GRID_X = 100;
    private static final int GRID_Y = 100;
    private static final int SPACE = 3;
    private static final int GRID_ELEMENT_SIZE = 35;
    private static final int NB_CASES_GRID = 10;
    
    private boolean rotationIsValide;
    private BoatDrawing activeBoat;
    
    HashMap<Rectangle, BoatDrawing> boatMap;
    
    
    /**
     * The method initialize starts the window and assigns values BoatDrawing 
     * objects and methods to the window's objects.
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        boatMap = new HashMap<>();
        // init boat set
        boatMap.put(porteAvionsRectangle,new BoatDrawing(BoatType.PORTEAVIONS,porteAvionsRectangle));
        boatMap.put(croiseurRectangle, new BoatDrawing(BoatType.CROISEURFR,croiseurRectangle));
        boatMap.put(contreTorpilleurRectangle, new BoatDrawing(BoatType.CONTRETORPILLEUR,contreTorpilleurRectangle));
        boatMap.put(sousMarinRectangle, new BoatDrawing(BoatType.SOUSMARINFR,sousMarinRectangle));
        boatMap.put(torpilleurRectangle, new BoatDrawing(BoatType.TORPILLEUR,torpilleurRectangle));
        
        // acctive selection boat
        for (Rectangle rectangle : boatMap.keySet()) {
            rectangle.setOnMousePressed(activateBoat());
        }        
        activeBoat = null;

        for (int i = 0 ; i < NB_CASES_GRID ; i++) {
            for (int j = 0; j < NB_CASES_GRID; j++) {
                Pane pane = new Pane();
                pane.setOnMouseEntered(drawBoatsNewPosition());
                table.add(pane, i, j);
            }
        }
        
        table.setOnMousePressed(unactiveBoat());
        table.setOnMouseEntered(enableRotation());
        table.setOnMouseExited(disableRotation());
        
        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, rotateBoat());
        
        rotationIsValide = false;
    }
    
    /**
     * Method that enables the boat rotation, this only happens when the mouse is
     * over the grid.
     * QUESTION : utile ?
     * @return mouseLocationHandler
     */
    private EventHandler<MouseEvent> enableRotation() {
        EventHandler<MouseEvent> mouseLocationHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rotationIsValide = true;
                event.consume();
            }
        };
        return mouseLocationHandler;
    }
    
    /**
     * Method that disables the boat rotation, because the mouse is not over
     * the grid.
     * @return 
     */
    private EventHandler<MouseEvent> disableRotation() {
        EventHandler<MouseEvent> mouseLocationHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {   
                rotationIsValide = false;
                event.consume();
            }
        };
        return mouseLocationHandler;
    }
    
    /**
     * Method that draw the boats over the grid when the mouse enters one 
     * element of the grid.
     * @return mousePositionHandler
     */
    private EventHandler<MouseEvent> drawBoatsNewPosition() {
        EventHandler<MouseEvent> mousePositionHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(activeBoat!=null){
                    Node source = (Node)event.getSource() ;
                    Integer colIndex = GridPane.getColumnIndex(source);
                    Integer rowIndex = GridPane.getRowIndex(source);
                    draw(activeBoat, colIndex, rowIndex);                    
                }
                event.consume();
            }
        };
        return mousePositionHandler;
    }
    
    /**
     * draw boat in new position and update boat
     * @param boat
     * @param colIndex
     * @param rowIndex 
     */
    private void draw(BoatDrawing boat, Integer colIndex, Integer rowIndex){
        // if boat have rotation, we must deplace boat
        float offset = boat.isRotation()?boat.getOffset():0;
            
        float positionX = GRID_X + SPACE + GRID_ELEMENT_SIZE*colIndex - offset;
        float positionY = GRID_Y + SPACE + GRID_ELEMENT_SIZE*rowIndex + offset;
            
        boat.getBoatRectangle().setLayoutX(positionX);
        boat.getBoatRectangle().setLayoutY(positionY);
        boat.setGridCol(colIndex);
        boat.setGridRow(rowIndex);
    }
       
    /**
     * Method that rotates the boat active when the user press R.
     * @return keyEeventHandler
     */  
    public EventHandler<KeyEvent> rotateBoat() {
        EventHandler<KeyEvent> keyEventHandler;
        keyEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.R) {
                    if(rotationIsValide){
                        if(activeBoat!=null){
                            drawRotation(activeBoat);                            
                        }
                    }   
                }
                keyEvent.consume();
            }
        };
        return keyEventHandler;
    }
    
    /**
     * rotate boat with 90° and update view
     * @param boat 
     */
    private void drawRotation(BoatDrawing boat){

        boat.setRotation(!boat.isRotation());
        Rectangle rectangleBoat = boat.getBoatRectangle();
        rectangleBoat.setRotate(rectangleBoat.getRotate()+90);
        draw(boat,boat.getGridCol(),boat.getGridRow());
    }
    
    /**
     * Method that actives the boat when the user clicks  on it.
     * @return mousePressHandler
     */   
    private EventHandler<MouseEvent> activateBoat() {
        EventHandler<MouseEvent> mousePressHandler;
        mousePressHandler = new EventHandler<MouseEvent>() {
            
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    //If the user has clicked in the window
                    Rectangle myRectangle =(Rectangle) event.getSource();
                    BoatDrawing myboat  = boatMap.get(myRectangle);
                    activeBoat = myboat.setActiveBoat(boatMap);
                    
                }
            }
        };
        return mousePressHandler;
    }
    
    /**
     * Method that unactivates the boat when it is placed sur le grid.
     * @return mousePressGridHandler
     */    
    private EventHandler<MouseEvent> unactiveBoat() {
        EventHandler<MouseEvent> mousePressGridHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if(activeBoat!=null){
                        activeBoat.setActive(false);
                        activeBoat.getBoatRectangle().setMouseTransparent(false);
                        activeBoat.getBoatRectangle().setFill(Color.web("#ababab"));
                        activeBoat=null;
                    }                 
                }
                event.consume();
            }
        };
        return mousePressGridHandler;
    }
}
