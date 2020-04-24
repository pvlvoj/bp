package cz.vse.java.utils.userTaskAssignment;


import cz.vse.java.connections.utils.IConnection;
import cz.vse.java.utils.persistance.entities.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
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

    public TaskSolverContainer() {

        this.container = new CopyOnWriteArrayList<>();
    }

    /* *****************************************************************/
    /* Instance methods ************************************************/

    public void add(IConnection connection, String userName) {

        this.container.add(new TaskSolver(userName, connection));
    }


    public void assign(Task task) {

        this.assigner.assign(this, task);
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

        for (TaskSolver ts : container) {

            if(ts.getUserName().equals(userName)) {

                return ts.getTasks(userName);
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



    /* *****************************************************************/
    /* Main method *****************************************************/


    /**
     * The main method of the class of TaskSolverContainer.
     *
     */
  /*  public static void main(String[] args){
        
        System.err.println(">>> QuickTest: TaskSolverContainer class");
        System.err.println(">>> Creating TaskSolverContainer instance...");
        TaskSolverContainer instance = new TaskSolverContainer();
        
        System.out.println(instance.toString());
        
        
        //code
        
        
        System.err.println(">>> Creation successfull...");
    }
    
    */
}
