package com.SEApp.app.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.util.function.Function;

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

    private final Function<Integer, Void> callbackEdit;

    private final Function<Integer, Void> callbackDelete;

    private final int id;

    public ListElement(int id, Function<Integer, Void> callbackEdit, Function<Integer, Void> callbackDelete) {
        this.callbackDelete = callbackDelete;
        this.callbackEdit = callbackEdit;
        this.id = id;

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

    public ListElement(int id, Function<Integer, Void> callbackEdit, Function<Integer, Void> callbackDelete, Map<String, String> options) {
        this(id, callbackEdit, callbackDelete);

        if (options == null) {
            return;
        }

        if (options.containsKey("noEdit")) {
            setNoEdit(options.get("noEdit"));
        }
        if (options.containsKey("noDelete")) {
            setNoDelete(options.get("noDelete"));
        }

        if (options.containsKey("editText")) {
            editButton.setText(options.get("editText"));
        }
        if (options.containsKey("deleteText")) {
            deleteButton.setText(options.get("deleteText"));
        }


    }

    public void initialize() {
        editButton.setOnAction(actionEvent -> {
            callbackEdit.apply(id);
        });

        deleteButton.setOnAction(actionEvent -> {
            callbackDelete.apply(id);
        });
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
        editButtonContainer.setVisible(!condition.equals("true"));
        hideSplitPane();
    }

    public void setNoDelete(String condition) {
        deleteButtonContainer.setVisible(!condition.equals("true"));
        hideSplitPane();
    }

    public String getNoEdit() {
        return (!editButtonContainer.isVisible()) ? "true" : "false";
    }

    public String getNoDelete() {
        return (!deleteButtonContainer.isVisible()) ? "true" : "false";
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
