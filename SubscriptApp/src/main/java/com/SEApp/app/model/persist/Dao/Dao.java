package com.SEApp.app.model.persist.Dao;

import com.SEApp.app.model.logic.exceptions.IncorrectOperandException;
import com.SEApp.app.model.persist.DBAccess.DBAccess;

import java.sql.SQLException;
import java.util.*;

/**
 * 
 */
public abstract class Dao<T> {

        protected DBAccess db;

        public Dao(DBAccess db){
            this.db = db;
        }

        /**
        * @param id
        * @return
        */
        public abstract T get(int id) throws SQLException;

        /**
        * @param obj
        * @return
        */
        public abstract T save(T obj) throws SQLException;

        /**
        * @param obj
        * @return
        */
        public abstract T update(T obj) throws SQLException, IncorrectOperandException;

        /**
        * @param obj
        * @return
        */
        public abstract boolean delete(T obj) throws SQLException, IncorrectOperandException;

        /**
        * @return List<Object> return a list of all the objects
        */
        public abstract List<T> list() throws SQLException;
}