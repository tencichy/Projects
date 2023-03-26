package com.damiskot;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;


public class AboutController{

    static HostServices hostServices;

    @FXML
    private ImageView appImage;

    @FXML
    private Hyperlink iconMaker;

    @FXML
    private Hyperlink iconSite;

    @FXML
    private Hyperlink creativeCommons;



    public AboutController(){

    }

    @FXML
    private void initialize(){
        appImage.setImage(new Image(MainApp.class.getResourceAsStream("img/mainIcon-256.png")));
        appImage.setFitHeight(80);
        appImage.setFitWidth(80);

        iconMaker.setBorder(Border.EMPTY);
        iconSite.setBorder(Border.EMPTY);
        creativeCommons.setBorder(Border.EMPTY);

    }

    @FXML
    private void handleIconMaker(){

        hostServices.showDocument("https://www.flaticon.com/authors/smashicons");
        iconMaker.setVisited(false);
    }

    @FXML
    private void handleIconSite(){
        hostServices.showDocument("https://www.flaticon.com");
        iconSite.setVisited(false);
    }

    @FXML
    private void handleCreativeCommons(){
        hostServices.showDocument("http://creativecommons.org/licenses/by/3.0/");
        creativeCommons.setVisited(false);
    }
}
