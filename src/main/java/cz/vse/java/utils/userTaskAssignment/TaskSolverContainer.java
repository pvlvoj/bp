package cz.vse.java.utils.userTaskAssignment;


import cz.vse.java.connections.utils.IConnection;
import cz.vse.java.utils.persistance.entities.tasks.ETaskState;
import cz.vse.java.utils.persistance.entities.tasks.Task;
import cz.vse.java.utils.persistance.service.TaskService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/*********************************************************************
 * <p>The class of {@code TaskSolverContainer} is used to abstractly define
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
public class TaskSolverContainer {


    /* *****************************************************************/
    /* Instance variables **********************************************/

    private final CopyOnWriteArrayList<TaskSolver> container;

    private IAssignScenario assigner;

    /* *****************************************************************/
    /* Static variables ************************************************/

    /**
     * <p>Private static instance of the {@link Logger}
     * - the logger of the {@link TaskSolverContainer class</p>
     */
    private static final Logger LOG =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /* *****************************************************************/
    /* Constructors ****************************************************/

    public TaskSolverContainer(IAssignScenario assigner) {

        this.container = new CopyOnWriteArrayList<>();
        this.assigner = assigner;
    }

    /* *****************************************************************/
    /* Instance methods ************************************************/

    public void add(IConnection connection, String userName) {

        this.container.add(new TaskSolver(userName, connection, this));
    }


    public void assign(Task task) {

        this.assigner.assign(this, task);
    }


    public void resetTasks(TaskSolver ts) {

        List<Task> tasks = ts.getTasks(ts.getUserName());

        TaskService taskService = new TaskService();

        LOG.log(Level.SEVERE, "Reseting tasks of " + ts.getUserName());

        for (Task t : tasks) {

            t.setState(ETaskState.NOT_ASSIGNED);
            t.setUser(null);

            try {

                taskService.update(t);

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }

        LOG.log(Level.SEVERE, tasks.size() + " tasks reseted.");

        ts.clearTasks();
        this.getContainer().remove(ts);
    }

    /* *****************************************************************/
    /* Static methods **************************************************/



    /* *****************************************************************/
    /* Getters *********************************************************/

    public List<String> getNames() {

        List<String> names = new ArrayList<>();

        for (TaskSolver ts : this.container) {

            names.add(ts.getUserName());
        }

        return names;
    }


    public List<Task> getByName(String userName) {

        TaskSolver ts = this.getTaskSolver(userName);

        if(ts != null) {

            return ts.getTasks(userName);
        }

        return null;
    }

    public TaskSolver getTaskSolver(String userName) {

        for(TaskSolver ts : this.getContainer()) {

            if(ts.getUserName().equals(userName)) {

                return ts;
            }
        }

        return null;
    }


    /**
     * Getter for {@link CopyOnWriteArrayList<>} formed {@code container}
     * of the instance of {@link TaskSolverContainer}
     *
     * @return the value of {@code container}
     * @see CopyOnWriteArrayList<>
     * @see TaskSolverContainer
     */
    public CopyOnWriteArrayList<TaskSolver> getContainer() {

        return container;
    }

    /* *****************************************************************/
    /* Setters *********************************************************/


}
