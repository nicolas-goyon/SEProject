package com.SEApp.app.components;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.Function;

public class ListDisplay extends VBox {


    private final List<ElementLogic> list;

    private final List<ButtonData> buttons;

    public ListDisplay(List<ElementLogic> list, List<ButtonData> buttons) {
        super();
        this.list = list;
        this.buttons = buttons;

        updateList();

    }

    public void updateList() {
        getChildren().clear();
        for (ElementLogic element : list) {
            ListElement listElement = new ListElement(element.getId(), buttons);
            listElement.setTitle(element.getTitle());
            listElement.setDescription(element.getDescription());

            getChildren().add(listElement);
        }
    }
}
