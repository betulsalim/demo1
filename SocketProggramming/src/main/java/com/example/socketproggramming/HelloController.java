package com.example.socketproggramming;


import Server.ClientHandler;
import Server.Server;
import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.BufferedReader.*;



import java.io.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static javafx.fxml.FXMLLoader.load;


public class HelloController extends User implements Serializable{
    private static final long serialVersionUID = 4801633306273802062L;

    User user1;
    @FXML
    public Pane SignInPane;


    @FXML
    public Pane signUpPane;

    @FXML
    public Button signUpButton;

    @FXML
    public Button getStartedButton;

    @FXML
    public ImageView backImageView;

    @FXML
    public TextField registerNameField;

    @FXML
    public TextField registerPasswordField;

    @FXML
    public TextField registerEmailField;

    @FXML
    public TextField registerFirstNameField;

    @FXML
    public TextField registerPhoneNumberField;

    @FXML
    public RadioButton maleButton;

    @FXML
    public RadioButton femaleButton;

    @FXML
    public Label controlRegisterLabel;

    @FXML
    public Label succesLabel;

    @FXML
    public Label goBack;

    @FXML
    public TextField passwordField;

    @FXML
    public TextField usernameField;

    @FXML
    public Label loginNotifierLabel;

    @FXML
    public Label nameExistsLabel;

    @FXML
    public Label checkEmaiLabel;

    public static String username;
    public static String password;
    public static String gender;
    public static ArrayList<User> loggedInUser = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<User>();

    File newuserFile = new File("User.txt");
    User user = new User();

    public void registration() throws IOException {


        if((!registerNameField.getText().equalsIgnoreCase(""))
                &&( !registerPasswordField.getText().equalsIgnoreCase(""))
                &&(!registerEmailField.getText().equalsIgnoreCase(""))
                && (!registerFirstNameField.getText().equalsIgnoreCase(""))
                &&(!registerPhoneNumberField.getText().equalsIgnoreCase(""))
                &&(maleButton.isSelected() || femaleButton.isSelected())) {
            if(checkUser(registerNameField.getText())) {
                if(checkEmailLabel(registerEmailField.getText())) {

                    user.nameString = registerNameField.getText();
                    user.passwordString=registerPasswordField.getText();
                    user.emailString=registerEmailField.getText();
                    user.fullNameString=registerFirstNameField.getText();
                    user.phoneNumberString=registerPhoneNumberField.getText();
                    if(maleButton.isSelected()) {
                        user.genderString="Male";
                    }else {
                        user.genderString="FEMALE";
                    }
                    users.add(user);
                    // file save
                    // close file
                    goBack.setOpacity(1);
                    succesLabel.setOpacity(1);
                    makeDefault();
                    if(controlRegisterLabel.getOpacity()==1) {
                        controlRegisterLabel.setOpacity(0);
                    }
                    if(nameExistsLabel.getOpacity()==1) {
                        nameExistsLabel.setOpacity(0);
                    }
                }
                else {
                    checkEmaiLabel.setOpacity(1);
                    setOpacity(nameExistsLabel, goBack, controlRegisterLabel, succesLabel);
                }

            }
            else {
                nameExistsLabel.setOpacity(1);
                setOpacity(succesLabel, goBack, controlRegisterLabel, checkEmaiLabel);
            }
        }
        else {
            controlRegisterLabel.setOpacity(1);
            setOpacity(succesLabel, goBack, nameExistsLabel, checkEmaiLabel);
        }
        if(newuserFile.exists()){
            try{
                FileWriter fileWriter = new FileWriter("User.txt",true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter);
                printWriter.write(user.nameString+"\t");
                printWriter.write(user.passwordString+"\n");
                printWriter.close();
                bufferedWriter.close();
            }catch (Exception exception){
                exception.printStackTrace();
            }



        }else{
            newuserFile.createNewFile();
        }

    }

    private void setOpacity(Label aLabel , Label bLabel, Label cLabel, Label dLabel) {
        if(aLabel.getOpacity()==1 || bLabel.getOpacity()==1 || cLabel.getOpacity()==1 || dLabel.getOpacity()==1) {
            aLabel.setOpacity(0);
            bLabel.setOpacity(0);
            cLabel.setOpacity(0);
            dLabel.setOpacity(0);
        }
    }

    private void setOpacity(Label controlRegisterLabel, Label checkEmaiLabel, Label nameExistLabel) {
        controlRegisterLabel.setOpacity(0);
        checkEmaiLabel.setOpacity(0);
        nameExistLabel.setOpacity(0);

    }
    private boolean checkUser(String username) {
        for(User user: users) {
            if(user.nameString.equalsIgnoreCase(username)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkEmailLabel(String emailString) {
        for (User user: users) {
            if(user.emailString.equalsIgnoreCase(emailString)) {
                return false;
            }
        }
        return true;

    }

    private void makeDefault() {
        registerNameField.setText("");
        registerPasswordField.setText("");
        registerEmailField.setText("");
        registerFirstNameField.setText("");
        registerPhoneNumberField.setText("");
        maleButton.setSelected(true);
        setOpacity(controlRegisterLabel,checkEmaiLabel,nameExistsLabel);
    }


    public void login() throws IOException {


        username = usernameField.getText();
        password = passwordField.getText();
        boolean login = false;
        if (newuserFile.exists()) {
            Scanner file = new Scanner(new BufferedReader(new FileReader("User.txt")));

            for (User xUser : users) {


                while (file.hasNext()) {
                    xUser.nameString = file.next();
                    xUser.passwordString = file.next();
                    System.out.println(xUser.nameString + xUser.passwordString);

                    if (xUser.nameString.equalsIgnoreCase(username) && xUser.passwordString.equalsIgnoreCase(password)) {

                        login = true;
                        loggedInUser.add(xUser);
                        System.out.println(xUser.nameString);
                        gender = xUser.genderString;
                        break;
                    }

                }
            }

            file.close();

            if (login) {
                changeWindow();
            } else {
                loginNotifierLabel.setOpacity(1);
            }




        }

    }

    public void changeWindow() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Parent rootParent = load(Objects.requireNonNull(this.getClass().getResource("Room.fxml")));


            stage.setScene(new Scene(rootParent,330,560));
            stage.setTitle(username+ "");
            stage.setOnCloseRequest(event ->{
                System.exit(0);
            });
            stage.setResizable(false);
            stage.show();
        }catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws RuntimeException, IOException {

        if(event.getSource().equals(signUpButton))  {

            new FadeIn(signUpPane).play();

            signUpPane.toFront();

        }else if (event.getSource().equals(getStartedButton)) {

            new FadeIn(SignInPane).play();
            SignInPane.toFront();

        }
        loginNotifierLabel.setOpacity(0);
        usernameField.setText("");
        passwordField.setText("");
    }


    @FXML
    private  void handleMouseEvent(MouseEvent eventt) throws RuntimeException  {

        if(eventt.getSource()==backImageView) {
            new FadeIn(SignInPane).play();
            SignInPane.toFront();
        }
        registerNameField.setText("");
        registerPasswordField.setText("");
        registerEmailField.setText("");
    }
}