package com.SEApp.app.components;

import com.github.fxrouter.FXRouter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class GridDisplay extends VBox {

    private final GridPane gridPane = new GridPane();

    private final List<ElementLogic> list;

    private final Function<Integer, Void> callbackEdit;

    private final Function<Integer, Void> callbackDelete;

    private int columnCount = 3;

    private Map<String, String> options;

    // Constructor with a list, edit and delete callback functions
    public GridDisplay(List<ElementLogic> list, Function<Integer, Void> callbackEdit, Function<Integer, Void> callbackDelete ) {
        this(list, callbackEdit, callbackDelete, null);
    }

    public GridDisplay(List<ElementLogic> list, Function<Integer, Void> callbackEdit, Function<Integer, Void> callbackDelete, Map<String, String> options) {
        super();
        this.callbackDelete = callbackDelete;
        this.callbackEdit = callbackEdit;
        this.list = list;
        this.options = options;


        updateColumnCount(FXRouter.getWindowWidth());

        FXRouter.addWindowWidthListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
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
            GridElement gridElement = new GridElement(element.getId() , callbackEdit, callbackDelete, options);
            gridElement.setTitle(element.getTitle());
            gridElement.setDescription(element.getDescription());
            gridPane.add(gridElement, colIndex, rowIndex);

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
        if(options != null && options.containsKey("maxWidth")) {
            double maxWidth = Double.parseDouble(options.get("maxWidth"));
            if(width > maxWidth) {
                width = maxWidth;
            }
        }
        this.columnCount = calculateColumnCount(width);
        updateGrid();
    }

    private int calculateColumnCount(double width) {
        // Adjust this logic based on your requirements
        int elementWidth = 350; // Adjust this value based on the width of your elements
        int columnCount = (int) (width / elementWidth);
        if (columnCount == 0) {
            columnCount = 1;
        }
        return columnCount;
    }

}
