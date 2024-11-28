//package com.example.librarymanagmentssystem;
//
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.stage.FileChooser;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Objects;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class ForCopying {
////    ---- اتربيوت في الكلاس
//
//    String imageName = null;
//    Image[] profileImage = {null};
//
//
////--- في داله الadminDashboard
//
//    public void adminDashboard() {
//    Label profilePictureLabel = new Label("UserImage :");
//    ImageView imageview = new ImageView();
//    imageview.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/main.png"))));
//    imageview.setFitWidth(70);
//    imageview.setFitHeight(70);
//
//        imageview.setOnMouseClicked(e ->
//
//        {
//            FileChooser fileChooser = new FileChooser();
//            File file = fileChooser.showOpenDialog(RegisterStage);
//            if (file != null) {
//                profileImage[0] = new Image(file.toURI().toString());
//                imageview.setImage(profileImage[0]);
//                this.imageName = "/images/" + file.getName();
//                try {
//                    saveimage(profileImage[0], file.getName());
//                } catch (IOException ex) {
//                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//        });
//}
//
//
//
//
//
//    //------------TableView
//        tableViewUser.getSelectionModel().
//
//    selectedItemProperty().
//
//    addListener(e ->
//
//    {
//        User user = tableViewUser.getSelectionModel().getSelectedItem();
//
//        if (user != null) {
//
//            fullNameTf.setText(user.getFullName());
//            usernameTf.setText(user.getUserName());
//            passwordTf.setText(user.getPassword());
//            phoneTf.setText(user.getPhone());
//            emailTf.setText(user.getEmail());
//            roleComboBox.setValue(user.getRole());
//            this.imageName = user.getProfileImagePath();
//
//            profileImage[0] = new Image(FxlLibraryMangmentsSysytem.class.getResourceAsStream(user.getProfileImagePath()));
//            imageview.setImage(profileImage[0]);
//
//        }
//
//    });
//
//
//    //        -----------addUser button
//        addButton.setOnAction(e ->
//
//    {
//
//        fullNameLabelError.setText("");
//        usernameLabelError.setText("");
//        passwordLabelError.setText("");
//        emailLabelError.setText("");
//        phoneLabelError.setText("");
//
//        boolean hasError = false;
//        if (fullNameTf.getText().isEmpty()) {
//            fullNameLabelError.setText("fullName is required.");
//            hasError = true;
//        }
//        if (usernameTf.getText().isEmpty()) {
//            usernameLabelError.setText("UserName is required.");
//            hasError = true;
//        }
//        if (passwordTf.getText().isEmpty()) {
//            passwordLabelError.setText("Password is required.");
//            hasError = true;
//        }
//        if (emailTf.getText().isEmpty()) {
//            emailLabelError.setText("Email is required.");
//            hasError = true;
//        }
//        if (phoneTf.getText().isEmpty()) {
//            phoneLabelError.setText("Phone is required.");
//            hasError = true;
//        }
//        if (this.imageName == null) {
//            profilePictureLabelError.setText("Image is required.");
//            hasError = true;
//        }
//
//        if (!hasError) {
//
//            boolean isFoundUsre = UserExiest(usernameTf.getText(), passwordTf.getText());
//            if (!isFoundUsre) {
//                User newUser = new User(fullNameTf.getText(), usernameTf.getText(), passwordTf.getText(), emailTf.getText(), phoneTf.getText(), roleComboBox.getValue(), this.imageName.toString());
//                Users.add(newUser);
////                     -------------alert
//                Alert alret = new Alert(Alert.AlertType.INFORMATION, "User has Been Registerd ..");
//                alret.showAndWait();
//
////                    -------clear input ---
//                fullNameTf.clear();
//                usernameTf.clear();
//                passwordTf.clear();
//                phoneTf.clear();
//                emailTf.clear();
//                this.imageName = null;
//            } else {
//                usernameLabelError.setText("User already exists with this username and password");
//            }
//        }
//
//    });
//
//
//
//-----------
//    helper function
//
//    public void saveimage(Image img, String name) throws IOException {
//        String projectPath = System.getProperty("user.dir");
//        String imagesFolderPath = projectPath + "/src/images";
//
//        File imagesFolder = new File(imagesFolderPath);
//        if (!imagesFolder.exists()) {
//            imagesFolder.mkdir();
//        }
//
//        String fullFilePath = imagesFolderPath + "/" + name;
//        File file = new File(fullFilePath);
//        BufferedImage BI = SwingFXUtils.fromFXImage(img, null);
//        ImageIO.write(BI, "png", file);
//    }
//
//
//
////--- تاكدي من اسم البكج تع الصور يكون images  والصور تكون موجودة قيه
//
//
//}
