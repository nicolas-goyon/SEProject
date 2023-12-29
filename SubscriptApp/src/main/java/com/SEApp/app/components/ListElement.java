package com.SEApp.app.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;

public class ListElement extends VBox {
    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private VBox editButtonContainer;

    @FXML
    private VBox deleteButtonContainer;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private SplitPane splitPane;

    public ListElement() {

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/SEApp/app/components/ListElement.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // Methods to set values for title and description
    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setDescription(String description) {
        descriptionLabel.setText(description);
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public void setNoEdit(String condition) {
        if (condition.equals("true")) {
            editButtonContainer.setVisible(false);
        }
        else {
            editButtonContainer.setVisible(true);
        }
        hideSplitPane();
    }

    public void setNoDelete(String condition) {
        if (condition.equals("true")) {
            deleteButtonContainer.setVisible(false);
        }
        else {
            deleteButtonContainer.setVisible(true);
        }
        hideSplitPane();
    }

    public String getNoEdit() {
        if (editButtonContainer.isVisible()) {
            return "false";
        }
        else {
            return "true";
        }
    }

    public String getNoDelete() {
        if (deleteButtonContainer.isVisible()) {
            return "false";
        }
        else {
            return "true";
        }
    }

    private void hideSplitPane() {
        boolean editVisible = editButtonContainer.isVisible();
        boolean deleteVisible = deleteButtonContainer.isVisible();

        if (editVisible && deleteVisible) {
            splitPane.setVisible(true);
            splitPane.setDividerPositions(0.5);
        } else if (editVisible || deleteVisible) {
            splitPane.setVisible(true);
            splitPane.getItems().clear();  // Remove all items from the SplitPane
            if (editVisible) {
                splitPane.getItems().add(editButtonContainer);
            } else {
                splitPane.getItems().add(deleteButtonContainer);
            }
        } else {
            splitPane.setVisible(false);
        }
    }







}
