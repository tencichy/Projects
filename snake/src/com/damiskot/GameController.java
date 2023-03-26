package com.damiskot;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.text.DecimalFormat;
import java.util.*;

public class GameController {

    private Random random = new Random();

    private javax.swing.Timer gameTimer = null;

    private ArrayList<Rectangle> snakeBody = new ArrayList<>();
    private ArrayList<Rectangle> snakeFood = new ArrayList<>();


    private final int NO_MOVE = 0;
    private final int LEFT = 1;
    private final int UP = 2;
    private final int RIGHT = 3;
    private final int DOWN = 4;

    private final int NORMAL = 0;
    private final int FASTER = 1;

    private double SCORE = 0;
    private int SPEED = 150;
    private double MULTIPLIER = 1;

    private int currentDirection = NO_MOVE;

    private boolean pause = false;
    private boolean gameOver = false;

    static Scene gameScene;
    static AnchorPane gamePane;

    @FXML
    private Rectangle head;

    @FXML
    private Label gamePausedLabel;

    @FXML
    private Label gameOverLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label sumLabel;

    @FXML
    private Label multiplierLabel;

    @FXML
    public void initialize(){
        HashMap<String,Integer> pos = randomPosition(10,580,10,380);
        head.setX(pos.get("X"));
        head.setY(pos.get("Y"));

        scoreLabel.setVisible(false);
        gamePausedLabel.setVisible(false);
        gameOverLabel.setVisible(false);
        sumLabel.setVisible(false);
        multiplierLabel.setVisible(false);
        snakeBody.add(head);

        Platform.runLater(()->gameOverLabel.toFront());
        Platform.runLater(()->scoreLabel.toFront());
        Platform.runLater(()->gamePausedLabel.toFront());
        Platform.runLater(()->sumLabel.toFront());
        Platform.runLater(()->multiplierLabel.toFront());

        Platform.runLater(()->addFood(0));

        gameTimer = new javax.swing.Timer(SPEED, e -> {
            if(!gameOver) {
                if (!pause) {
                    if (currentDirection != NO_MOVE) {
                        move();
                        eat();
                        gameOver = checkSnake() || isCollidingWithBorder();
                    }
                }
            }else {
                Platform.runLater(()->scoreLabel.setText("score " + new DecimalFormat("##.##").format(SCORE)));
                Platform.runLater(()->multiplierLabel.setText("multiplier " + new DecimalFormat("##.##").format(MULTIPLIER)));
                Platform.runLater(()->sumLabel.setText("sum " + new DecimalFormat("##.##").format(SCORE*MULTIPLIER)));
                gameOverLabel.setVisible(true);
                scoreLabel.setVisible(true);
                multiplierLabel.setVisible(true);
                sumLabel.setVisible(true);
                Platform.runLater(()->gameOverLabel.toFront());
                Platform.runLater(()->scoreLabel.toFront());
                Platform.runLater(()->sumLabel.toFront());
                Platform.runLater(()->multiplierLabel.toFront());
            }
        });

        gameTimer.start();

        Timer pauseTimer = new Timer();
        TimerTask pauseTask = new TimerTask() {
            @Override
            public void run() {
                if(pause){
                    gamePausedLabel.setVisible(!gamePausedLabel.isVisible());
                }
            }
        };

        pauseTimer.schedule(pauseTask,0,600);

        gameScene.setOnKeyPressed(event -> {
            if (!gameOver){
                if (!pause) {
                    if (event.getCode() == KeyCode.UP) {
                            currentDirection = UP;
                    } else if (event.getCode() == KeyCode.DOWN) {
                            currentDirection = DOWN;
                    } else if (event.getCode() == KeyCode.LEFT) {
                            currentDirection = LEFT;
                    } else if (event.getCode() == KeyCode.RIGHT) {
                            currentDirection = RIGHT;
                    } else if (event.getCode() == KeyCode.P) {
                        pause = true;
                    } else if (event.getCode() == KeyCode.A) {
                    //addRectangle();
                }else if(event.getCode() == KeyCode.F){
                    //addFood();
                }
                } else {
                    if (event.getCode() == KeyCode.P) {
                        pause = false;
                        gamePausedLabel.setVisible(false);
                    }
                }
        }else{
                if(event.getCode() == KeyCode.R){
                    currentDirection = NO_MOVE;
                    for (Rectangle shape: snakeBody) {
                        if(shape != head) {
                            Platform.runLater(() -> gamePane.getChildren().remove(shape));
                        }
                    }
                    for (Rectangle shape: snakeFood) {
                        Platform.runLater(()->gamePane.getChildren().remove(shape));
                    }
                    snakeBody.clear();
                    snakeFood.clear();
                    SCORE = 0;
                    MULTIPLIER = 0;
                    SPEED = 150;
                    gameOver = false;
                    scoreLabel.setVisible(false);
                    multiplierLabel.setVisible(false);
                    sumLabel.setVisible(false);
                    gamePausedLabel.setVisible(false);
                    gameOverLabel.setVisible(false);
                    HashMap<String,Integer> pos1 = randomPosition(10,580,10,380);
                    head.setX(pos1.get("X"));
                    head.setY(pos1.get("Y"));
                    Platform.runLater(()->addFood(0));
                    snakeBody.add(head);

                }
            }
        });
    }

    private boolean isCollidingWithBorder(){
        if(head.getY() < 0){
            return true;
        }else if(head.getX() < 0){
            return true;
        }else if(head.getX() > 590){
            return true;
        }else if(head.getY() > 390){
            return true;
        }
        return false;
    }

    private HashMap<String,Integer> randomPosition(int xMin,int xMax,int yMin,int yMax){
        HashMap<String,Integer> toReturn = new HashMap<>();
        Random random = new Random();
        int randomX = random.nextInt((xMax - xMin) + 1) + xMin;
        int randomY = random.nextInt((yMax - yMin) + 1) + yMin;
        char[] charsX = String.valueOf(randomX).toCharArray();
        char[] charsY = String.valueOf(randomY).toCharArray();
        randomX = randomX - Character.getNumericValue(charsX[charsX.length - 1]);
        randomY = randomY - Character.getNumericValue(charsY[charsY.length - 1]);
        toReturn.put("X",randomX);
        toReturn.put("Y",randomY);
        return toReturn;
    }

    private void move(){
        ArrayList<Double> x = new ArrayList<>();
        ArrayList<Double> y = new ArrayList<>();
        for (Rectangle aSnakeBody : snakeBody) {
            x.add(aSnakeBody.getX());
            y.add(aSnakeBody.getY());
        }
        moveHead();
        for (int i = 1; i < snakeBody.size(); i++) {
            snakeBody.get(i).setX(x.get(i-1));
            snakeBody.get(i).setY(y.get(i-1));
        }
    }

    private void moveHead(){
        if(currentDirection == UP){
            head.setY(head.getY() - 10);
        }else if(currentDirection == DOWN){
            head.setY(head.getY() + 10);
        }else if(currentDirection == LEFT){
            head.setX(head.getX() - 10);
        }else if(currentDirection == RIGHT){
            head.setX(head.getX() + 10);
        }
    }

    private void addFood(int type){
        if(type == NORMAL) {
            HashMap<String, Integer> pos = randomPosition(0, 590, 0, 390);
            Rectangle rectangleToAdd = new Rectangle(pos.get("X"), pos.get("Y"), 10, 10);
            snakeFood.add(rectangleToAdd);
            rectangleToAdd.setArcWidth(0);
            rectangleToAdd.setArcHeight(0);
            rectangleToAdd.setFill(Paint.valueOf("#a50000"));
            rectangleToAdd.setStrokeType(StrokeType.valueOf("INSIDE"));
            rectangleToAdd.setStrokeWidth(1);
            rectangleToAdd.setStroke(Paint.valueOf("black"));
            gamePane.getChildren().add(rectangleToAdd);
        }else if(type == FASTER){
            HashMap<String, Integer> pos = randomPosition(0, 590, 0, 390);
            Rectangle rectangleToAdd = new Rectangle(pos.get("X"), pos.get("Y"), 10, 10);
            snakeFood.add(rectangleToAdd);
            rectangleToAdd.setArcWidth(0);
            rectangleToAdd.setArcHeight(0);
            rectangleToAdd.setFill(Paint.valueOf("#ffa800"));
            rectangleToAdd.setStrokeType(StrokeType.valueOf("INSIDE"));
            rectangleToAdd.setStrokeWidth(1);
            rectangleToAdd.setStroke(Paint.valueOf("black"));
            gamePane.getChildren().add(rectangleToAdd);
        }
    }

    private void addRectangle(){
        Rectangle rectangleToAdd = new Rectangle(snakeBody.get(snakeBody.size()-1).getX(), snakeBody.get(snakeBody.size()-1).getY()+0.001,10,10);
        snakeBody.add(rectangleToAdd);
        rectangleToAdd.setArcWidth(0);
        rectangleToAdd.setArcHeight(0);
        rectangleToAdd.setFill(Paint.valueOf("#00a806"));
        rectangleToAdd.setStrokeType(StrokeType.valueOf("INSIDE"));
        rectangleToAdd.setStrokeWidth(1);
        rectangleToAdd.setStroke(Paint.valueOf("black"));
        gamePane.getChildren().add(rectangleToAdd);
    }

    private void eat(){
        Bounds bound = checkBounds(head, snakeFood);
        if(bound.isCollision()){
            snakeFood.remove(bound.getShape());
            Platform.runLater(()->gamePane.getChildren().remove(bound.getShape()));
            Platform.runLater(this::addRectangle);
            Platform.runLater(()->addFood(random.nextInt(1)));
            SCORE++;
            MULTIPLIER += 0.1;
            setSpeed();
        }
    }

    private boolean checkSnake(){
        return checkBounds(head,snakeBody).isCollision();
    }

    private void setSpeed(){
        if(SPEED > 10){
            SPEED --;
            gameTimer.setDelay(SPEED);
        }
    }

    private Bounds checkBounds(Rectangle block, ArrayList<Rectangle> shapes) {
        boolean collisionDetected = false;
        Rectangle collisionShape = null;
        for (Rectangle static_bloc : shapes) {
            if (static_bloc != block) {
                if(static_bloc.getY() == block.getY() && static_bloc.getX() == block.getX()){
                    collisionShape = static_bloc;
                    collisionDetected = true;
                }
//                if (block.getBoundsInParent().intersects(static_bloc.getBoundsInParent())) {
//                    collisionShape = static_bloc;
//                    collisionDetected = true;
//                }
            }
        }
        return new Bounds(collisionShape,collisionDetected);
    }

}
