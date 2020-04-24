package cz.vse.java.utils.userTaskAssignment;


import cz.vse.java.messages.AskForTaskCommand;
import cz.vse.java.utils.persistance.entities.tasks.ETaskState;
import cz.vse.java.utils.persistance.entities.User;
import cz.vse.java.utils.persistance.entities.tasks.Task;
import cz.vse.java.utils.persistance.service.TaskService;
import cz.vse.java.utils.random.RandomNumberGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/*********************************************************************
 * <p>The class of {@code RandomAssign} is used to abstractly define
 * the type of the instances.</p>
 *
 *
 * <i>Written for project "Connections2".</i>
 * @author Vojtěch Pavlů
 * @version 10. 04. 2020
 *
 *
 * @see cz.vse.java.utils.userTaskAssignment
 */
public class RandomAssign implements IAssignScenario {


    /* *****************************************************************/
    /* Instance variables **********************************************/


    /* *****************************************************************/
    /* Static variables ************************************************/

    /**
     * <p>Private static instance of the {@link Logger}
     * - the logger of the {@link RandomAssign class</p>
     */
    private static final Logger LOG =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /* *****************************************************************/
    /* Constructors ****************************************************/


    public RandomAssign() {

    }

    /* *****************************************************************/
    /* Instance methods ************************************************/

    /**
     * <p>Assigns the task to the user.</p>
     *
     * @param task task to be assigned
     */
    @Override
    public void assign(TaskSolverContainer tsc, Task task) {

        int size = tsc.getContainer().size();
        TaskSolver ts = tsc.getContainer().get(
                RandomNumberGenerator
                        .getRandomNumberInRange(0, size-1));

        ts.add(task);
    }

    /* *****************************************************************/
    /* Static methods **************************************************/



    /* *****************************************************************/
    /* Getters *********************************************************/



    /* *****************************************************************/
    /* Setters *********************************************************/



}
