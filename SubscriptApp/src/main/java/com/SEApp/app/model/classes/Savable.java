package com.SEApp.app.model.classes;

import com.SEApp.app.model.persist.utils.UpdateOperand;

public interface Savable {
    public UpdateOperand[] toUpdateOperands();
}

