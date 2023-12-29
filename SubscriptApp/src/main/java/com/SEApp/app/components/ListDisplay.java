package com.SEApp.app.components;

import com.github.fxrouter.FXRouter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.Function;


public class ListDisplay extends VBox {

    private final GridPane gridPane = new GridPane();

    private final List<ElementLogic> list;

    private int columnCount = 3;

    // Constructor with a list, edit and delete callback functions
    public ListDisplay(List<ElementLogic> list, Function<Integer, Void> callbackEdit, Function<Integer, Void> callbackDelete) {
        this.list = list;

        updateColumnCount(FXRouter.getWindowWidth());

        FXRouter.addWindowWidthListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                System.out.println("Width changed to " + newSceneWidth);
                updateColumnCount(newSceneWidth.doubleValue());
            }
        });

    }

    public void updateGrid() {
        initializeGrid();
        setSpacing(10);
        getChildren().clear();
        getChildren().add(gridPane);
    }

    private void initializeGrid() {
        gridPane.getChildren().clear();
        int columnCount = this.columnCount; // You can adjust the number of columns as needed
        int rowIndex = 0;
        int colIndex = 0;

        for (ElementLogic element : list) {
            ListElement listElement = new ListElement();
            listElement.setTitle(element.getTitle());
            listElement.setDescription(element.getDescription());
            gridPane.add(listElement, colIndex, rowIndex);

            colIndex++;
            if (colIndex == columnCount) {
                colIndex = 0;
                rowIndex++;
            }
        }

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
    }

    private void updateColumnCount(double width) {
        // Adjust the number of columns based on the width of the parent container
        this.columnCount = calculateColumnCount(width);
        updateGrid();
    }

    private int calculateColumnCount(double width) {
        // Adjust this logic based on your requirements
        int elementWidth = 350; // Adjust this value based on the width of your elements
        return (int) (width / elementWidth);
    }

}
