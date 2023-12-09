package com.SEApp.app.model.persist;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * 
 */
public interface Dao<T> {

        /**
        * @param id
        * @return
        */
        public T get(long id);

        /**
        * @param obj
        * @return
        */
        public T save(T obj) throws SQLException;

        /**
        * @param obj
        * @return
        */
        public T update(T obj) throws SQLException, IncorrectOperandException;

        /**
        * @param obj
        * @return
        */
        public boolean delete(T obj) throws SQLException, IncorrectOperandException;

        /**
        * @return List<Object> return a list of all the objects
        */
        public List<T> list() throws SQLException;
}