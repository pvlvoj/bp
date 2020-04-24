package cz.vse.java;

import cz.vse.java.services.serverSide.Server;
import cz.vse.java.utils.database.DBConnection;
import cz.vse.java.utils.database.DatabaseConnectionContainer;
import cz.vse.java.utils.database.EDBUse;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {

        DatabaseConnectionContainer.getInstance().add(
                EDBUse.USER_AUTHENTICATION,
                new DBConnection("jdbc:h2:tcp://localhost/~/test", "sa", "")
        );

        DatabaseConnectionContainer.getInstance().add(
                EDBUse.FINGERPRINT_AUTHENTICATION,
                new DBConnection("jdbc:h2:tcp://localhost/~/test", "sa", "")
        );

        DatabaseConnectionContainer.getInstance().add(
                EDBUse.EMPLOYEE_MANAGEMENT,
                new DBConnection("jdbc:h2:tcp://localhost/~/test", "sa", "")
        );

        DatabaseConnectionContainer.getInstance().add(
                EDBUse.STORAGE_MANAGEMENT,
                new DBConnection("jdbc:h2:tcp://localhost/~/test", "sa", "")
        );

        DatabaseConnectionContainer.getInstance().add(
                EDBUse.TASK_MANAGEMENT,
                new DBConnection("jdbc:h2:tcp://localhost/~/test", "sa", "")
        );

        Server s = Server.getInstance();
        Thread t = new Thread(s);
        t.start();
        t.setPriority(3);
    }
}
