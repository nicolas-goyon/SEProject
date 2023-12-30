package com.SEApp.app.components;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.Function;

public class ListDisplay extends VBox {


    private final List<ElementLogic> list;

    private final Function<Integer, Void> callbackSubscription;

    public ListDisplay(List<ElementLogic> list, Function<Integer, Void> callbackSubscription){
        super();
        this.list = list;
        this.callbackSubscription = callbackSubscription;

        updateList();

    }

    public void updateList() {
        getChildren().clear();
        for (ElementLogic element : list) {
            ListElement listElement = new ListElement(element.getId(), callbackSubscription);
            listElement.setTitle(element.getTitle());
            listElement.setDescription(element.getDescription());

            getChildren().add(listElement);
        }
    }
}
