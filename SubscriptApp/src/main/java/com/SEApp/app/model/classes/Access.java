package com.SEApp.app.model.classes;

import com.SEApp.app.model.persist.utils.UpdateOperand;

public class Access implements Savable {
    private int id;
    private String name;
    private String description;

    public Access(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Access(String name, String description) {
        this(-1, name, description);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = description;
    }

    @Override
    public String toString() {
        return "Access{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


    /**
     * @return
     */
    @Override
    public UpdateOperand[] toUpdateOperands() {
        return new UpdateOperand[] {
                new UpdateOperand("name", name),
                new UpdateOperand("description", description)
        };
    }
}
