package cz.vse.java.utils.persistance.service;

import cz.vse.java.utils.database.DBConnection;
import cz.vse.java.utils.database.DatabaseConnectionContainer;
import cz.vse.java.utils.database.EDBUse;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ProductServiceTest {


    DBConnection dbConnection;


    @Test
    public void getAll() {

        DatabaseConnectionContainer.getInstance().add(
                EDBUse.STORAGE_MANAGEMENT,
                new DBConnection("jdbc:h2:tcp://localhost/~/test", "sa", "")
        );

        ProductService ps = new ProductService();

        try {

            System.out.println("Products:");

            ps.getAll().forEach(n->{

                System.out.println("Entity: " + n.toString());
            });

        } catch (SQLException e) {

            e.printStackTrace();
            fail();
        }
    }
}