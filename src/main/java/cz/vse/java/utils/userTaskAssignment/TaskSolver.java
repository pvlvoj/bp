package cz.vse.java.utils.userTaskAssignment;


import cz.vse.java.connections.serviceSide.S2CConnection;
import cz.vse.java.connections.utils.IConnection;
import cz.vse.java.messages.AddTaskMessage;
import cz.vse.java.utils.observerDP.IObserver;
import cz.vse.java.utils.observerDP.ISubject;
import cz.vse.java.utils.persistance.entities.tasks.ETaskState;
import cz.vse.java.utils.persistance.entities.tasks.Task;
import cz.vse.java.utils.persistance.entities.tasks.TaskContainer;
import cz.vse.java.utils.persistance.service.TaskService;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/*********************************************************************
 * <p>The class of {@code TaskSolver} is used to abstractly define
 * the type of the instances.</p>
 *
 *
 * <i>Written for project "Connections2".</i>
 * @author Vojtěch Pavlů
 * @version 18. 04. 2020
 *
 *
 * @see cz.vse.java.utils.userTaskAssignment
 */
public class TaskSolver implements IObserver {


    /* *****************************************************************/
    /* Instance variables **********************************************/


    private String userName;
    private TaskContainer container;
    private IConnection connection;

    private boolean listening = false;

    private TaskSolverContainer tsc;


    /* *****************************************************************/
    /* Static variables ************************************************/

    /**
     * <p>Private static instance of the {@link Logger}
     * - the logger of the {@link TaskSolver class</p>
     */
    private static final Logger LOG =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /* *****************************************************************/
    /* Constructors ****************************************************/

    public TaskSolver(String userName, IConnection connection, TaskSolverContainer tsc) {

        this.userName = userName;
        this.connection = connection;
        this.container = new TaskContainer();

        this.tsc = tsc;
    }

    /* *****************************************************************/
    /* Instance methods ************************************************/

    public List<Task> getTasks(String userName) {

        if(userName.equals(this.userName)) {

            return this.container.getTasks();
        }

        return null;
    }


    public int size() {

        return this.getTasks(this.userName).size();
    }


    public void add(Task task) {

        if(listening) {

            this.container.add(task);
            this.connection.send(new AddTaskMessage(task));
        }
    }


    public void clearTasks() {

        this.container.clear();
    }

    /**
     * <p>Gets updated by notification of the {@link ISubject}
     * implementing instance.</p>
     */
    @Override
    public void update() {

        if(connection instanceof S2CConnection) {

            S2CConnection conn = (S2CConnection) connection;

            if(!conn.isRunning() && conn.isAuthenticated()) {

                listening = false;
                this.tsc.resetTasks(this);
            }

        } else {

            LOG.log(Level.SEVERE, "UNSUPPORTED CONNECTION TYPE!");
        }
    }


    public void updateTaskState(ETaskState state, Long taskID) {

        for (Task t : this.getTasks(this.getUserName())) {

            if(t.getId().equals(taskID)) {

                t.setState(state);
                TaskService ts = new TaskService();
                try {

                    ts.update(t);

                } catch (SQLException e) {

                    LOG.log(Level.SEVERE, "Cannot update the task! " + e.getMessage());
                }
                break;
            }
        }
    }


    /* *****************************************************************/
    /* Static methods **************************************************/



    /* *****************************************************************/
    /* Getters *********************************************************/

    /**
     * Getter for {@link String} formed {@code userName}
     * of the instance of {@link TaskSolver}
     *
     * @return the value of {@code userName}
     * @see String
     * @see TaskSolver
     */
    public String getUserName() {

        return userName;
    }


    /**
     * Getter for {@link IConnection} formed {@code connection}
     * of the instance of {@link TaskSolver}
     *
     * @return the value of {@code connection}
     * @see IConnection
     * @see TaskSolver
     */
    public IConnection getConnection() {

        return connection;
    }

    /**
     * Getter for {@link boolean} formed {@code listening}
     * of the instance of {@link TaskSolver}
     *
     * @return the value of {@code listening}
     * @see boolean
     * @see TaskSolver
     */
    public boolean isListening() {

        return listening;
    }

    /* *****************************************************************/
    /* Setters *********************************************************/

    /**
     * <p>Setter for the {@code $field.typeName} formed
     * {@code listening} variable.</p>
     *
     * @param listening given $field.typeName value to
     *                  be set to the variable
     * @see boolean
     * @see TaskSolver
     */
    public void setListening(boolean listening) {

        this.listening = listening;
    }
}
