package com.example.socketproggramming;

import com.example.socketproggramming.HelloController.*;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import com.example.socketproggramming.User;

import org.apache.commons.lang3.StringUtils;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.io.Serializable;

import static com.example.socketproggramming.HelloController.*;

public class Room extends Thread implements Initializable,Serializable  {
    private static final long serialVersionUID = 4801633306273802062L;
    File newUserFile = new File("User.txt");

    @FXML
    public Label clientNameLabel;
    @FXML
    public TextField changePasswordTextField;

    @FXML
    public Button changePasswordButton;

    @FXML
    public Button chatBoxButton;

    @FXML
    public Pane chatPane;

    @FXML
    public TextField messageField;

    @FXML
    public TextArea messageRoomArea;

    @FXML
    public Label onlineLabel ;

    @FXML
    public Label fullNameLabel;


    @FXML
    public Label emaiLabel;

    @FXML
    public Label phoneNumberLabel;

    @FXML
    public Label genderLabel;

    @FXML
    public Button profileButton;

    @FXML
    public Label profileLabel;

    @FXML
    public TextField fileChoosePathField;

    @FXML
    public ImageView profileImageView;

    @FXML
    public Circle showProfilePictureCircle;

    private FileChooser fileChooser;
    private File filePathFile;
    public boolean toggleChat = false , toggleProfile= false;


    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

   /* public void connectSocket() {
        try {
            socket = new Socket("localhost",1234);
            System.out.println("Socket is connected with Server!");

            this.start();

        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                String messageString = reader.readLine();
                String[] tokensString=messageString.split(" ");
                String cmdString = tokensString[0];
                System.out.println(cmdString);
                StringBuilder fullMessageBuilder = new StringBuilder();
                for(int i=1 ; i<tokensString.length;i++) {
                    fullMessageBuilder.append(tokensString[i]);
                }
                System.out.println(fullMessageBuilder);
                if(cmdString.equalsIgnoreCase(HelloController.username+ ":")) {
                    continue;

                }else if (fullMessageBuilder.toString().equalsIgnoreCase("bye")) {
                    break;
                }
                messageRoomArea.appendText(messageString+ "\n");
            }
            reader.close();
            writer.close();
            socket.close();
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }*/



    public void handleProfileButton(ActionEvent event) {
        if(event.getSource().equals(profileButton)  && !toggleProfile) {
            new FadeIn(profileButton).play();
            profileButton.toFront();
            chatPane.toBack();
            toggleProfile=true;
            toggleChat=false;
            profileButton.setText("Back");
            setProfile();


        }else if (event.getSource().equals(profileButton) && toggleProfile) {
            new FadeIn(chatPane).play();
            chatPane.toFront();
            toggleProfile=false;
            toggleChat=false;
            profileButton.setText("Profile");

        }

    }


    public void setProfile() {
        for(User user: users) {
            if(HelloController.username.equalsIgnoreCase(user.nameString)) {
                fullNameLabel.setText(user.fullNameString);
                fullNameLabel.setOpacity(1);
                emaiLabel.setText(user.emailString);
                emaiLabel.setOpacity(1);
                phoneNumberLabel.setText(user.phoneNumberString);
                phoneNumberLabel.setOpacity(1);
                genderLabel.setText(user.genderString);
                genderLabel.setOpacity(1);
            }

        }
    }


    public void handeSendEvent(MouseEvent event) {
        send();
        for(User user:users) {
            System.out.println(user.nameString);
        }

    }
    public void send() {
        String writer= "";
        String messageString=messageField.getText();
        writer.intern();
        // writer.println(HelloController.username + ": " + messageString);
        messageRoomArea.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        messageRoomArea.appendText("Me: "+messageString+" \n");
        messageField.setText("");
        if(messageString.equalsIgnoreCase("BYE") || messageString.equalsIgnoreCase("logout")) {
            System.exit(0);
        }
    }

    //For Changing profile picture

    public boolean saveControl = false;

    public void chooseImageButton(ActionEvent event) {



        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePathFile = fileChooser.showOpenDialog(stage);
        fileChoosePathField.setText(filePathFile.getPath());
        saveControl = true;



    }


    public void sendMessageByKey(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            send();
        }
    }

    //For Save profile picture

    public void saveImage() {



        if (saveControl) {


            try {

                BufferedImage bufferedImage = ImageIO.read(filePathFile);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                profileImageView.setImage(image);
                showProfilePictureCircle.setFill(new ImagePattern(image));
                saveControl = false;
                fileChoosePathField.setText("");
            } catch (IOException exception) {
                System.err.println(exception.getMessage());
            }
            File newFile = new File("ChangeImage");
            if(newUserFile.exists()){
                try {
                    FileWriter fileWriter = new FileWriter("ChangeImage.txt");
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    PrintWriter printWriter = new PrintWriter(bufferedWriter);
                    printWriter.write(filePathFile.getPath());

                    printWriter.close();


                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }


        }
    }


    @Override

    public void initialize(URL location, ResourceBundle resourceBundle) {



        showProfilePictureCircle.setStroke(Color.valueOf("#3344"));
        Image image;
        if (HelloController.gender.equalsIgnoreCase("Male")) {
            image = new Image("C:\\Users\\betul\\Desktop\\JavaFx\\SocketProggramming\\src\\İmage\\man2.png", false);


        } else {
            image = new Image("C:\\Users\\betul\\Desktop\\JavaFx\\SocketProggramming\\src\\İmage\\female.png", false);
            profileImageView.setImage(image);
        }
        showProfilePictureCircle.setFill(new ImagePattern(image));


        clientNameLabel.setText(HelloController.username);
      //  connectSocket();


    }


}
