package cz.vse.java.utils.userTaskAssignment;


import cz.vse.java.utils.persistance.entities.tasks.Task;

/**************************************************************
 * <p>The interface of IAssignScenario is used to contain all methods
 * of the type to be overriden (implemented).</p>
 *
 *
 * Written for project "Connections2".
 * @author Vojtěch Pavlů
 * @version 10. 04. 2020
 *
 * @see cz.vse.java.utils.userTaskAssignment
 */
public interface IAssignScenario {


    /* *****************************************************************/
    /* Methods to be overriden *****************************************/


    /**
     * <p>Assigns the task to the user.</p>
     *
     * @param tsc   {@link TaskSolverContainer} to assign to
     * @param task  task to be assigned
     */
    void assign(TaskSolverContainer tsc, Task task);

    /* *****************************************************************/
    /* Default methods *************************************************/


}
