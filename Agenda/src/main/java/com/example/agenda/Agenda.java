package com.example.agenda;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Agenda extends Application{

    private ObservableList<Persona> personData = FXCollections.observableArrayList();

    // Constructor

    public Agenda() {
        // Añadir personas a una lista
        personData.add(new Persona("Hans", "Muster"));
        personData.add(new Persona("Ruth", "Mueller"));
        personData.add(new Persona("Heinz", "Kurz"));
        personData.add(new Persona("Cornelia", "Meier"));
        personData.add(new Persona("Werner", "Meyer"));
        personData.add(new Persona("Lydia", "Kunz"));
        personData.add(new Persona("Anna", "Best"));
        personData.add(new Persona("Stefan", "Meier"));
        personData.add(new Persona("Martin", "Mueller"));
    }

    //devuelve la lista de personas
    public ObservableList<Persona> getPersonData() {
        return personData;
    }

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Agenda");

        //añadir un icono a la aplicación
        this.primaryStage.getIcons().add(new Image("file:images/agenda.png"));

        initRootLayout();

        showPersonOverview();
    }

    private void initRootLayout() {
        try {
            //carga del rootLayout desde el fichero XML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Agenda.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            //Mostrar el scene en el rootLayout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPersonOverview() {
        try {
            // Cargar el fichero PersonOverview.fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PersonOverview.fxml"));

            //Cargar el contenido del fxml en el AnchorPane
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Posicionar el personOverview en el centro del rootLayout.
            rootLayout.setCenter(personOverview);

            // Dar acceso a la clase ejecutable al controlador
            PersonOverviewController controller = loader.getController();
            controller.setAgenda(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
      launch();
    }

    public boolean showPersonEditDialog(Persona persona) {
        try {
            // Carga el fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Agenda.class.getResource("PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Crea el Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar persona");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Le pasa la persona al controlador.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(persona);

            // Muestra la ventana hasta que el usuario decide cerrarla
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}