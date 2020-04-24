package cz.vse.java.services.serverSide;


import cz.vse.java.services.clientSide.Client;
import cz.vse.java.utils.observerDP.IObserver;
import cz.vse.java.utils.observerDP.ISubject;

import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;


/**************************************************************
 * <p>Class Server is Singleton by design pattern definition
 * with only one possible instance of this class.</p>
 *
 * <p>The class has <b>Thread-safe</b> getInstance() creation
 * mechanism implemented to be sure there is only one instance.</p>
 *
 * Written for project "Connections2".
 * @author Vojtěch Pavlů
 * @version 04. 04. 2020
 *
 *
 * @see cz.vse.java.services
 * @see <a href="https://en.wikipedia.org/wiki/Singleton_pattern">
 * Singleton at Wikipedia</a>
 */
public class Server implements Runnable, ISubject {

    /* *******************************************************/
    /* Instance variables ************************************/

    private CopyOnWriteArrayList<AService> services;
    private Router router;
    private final CopyOnWriteArrayList<IObserver> observers;

    /* *******************************************************/
    /* Static variables **************************************/

    /**
     * Volatile singleton container variable
     */
    private static volatile Server singletonInstance = null;


    /**
     * Private {@link Logger} instance - Logger of the {@link Server} class
     */
    private static final Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /* *******************************************************/
    /* Constructors ******************************************/

    /**
     * <p>Private Singleton design pattern constructor of
     * the only one instance of the Server class.</p>
     */
    private Server() {

        this.services = new CopyOnWriteArrayList<>();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("stores/keyStore.jks").getFile());

        this.router = new Router(
                50,
                50,
                9889,
                888,
                file.getAbsolutePath(),
                "changeit"
        );

        this.services.addIfAbsent(router);
        this.observers = new CopyOnWriteArrayList<>();
    }


    /* *******************************************************/
    /* Instance methods **************************************/

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        for (AService service : this.services) {

            Thread t = new Thread(service);
            t.start();
            t.setPriority(10);
        }
    }


    /**
     * <p>Adds the {@link IObserver} to the field.</p>
     *
     * @param observer the listener to any change.
     */
    @Override
    public void addObserver(IObserver observer) {

        synchronized (this.observers) {

            this.observers.addIfAbsent(observer);
        }
    }

    /**
     * <p>Removes the {@link IObserver} from the field.</p>
     *
     * @param observer the listener to any change.
     */
    @Override
    public void removeObserver(IObserver observer) {

        synchronized (this.observers) {

            this.observers.remove(observer);
        }
    }

    /**
     * <p>Notifies all the {@link IObserver}s about
     * the change of state</p>
     */
    @Override
    public void notifyObservers() {

        synchronized (this.observers) {

            for (IObserver observer : observers) {

                observer.update();
            }
        }
    }


    /* *******************************************************/
    /* Getters and setters ***********************************/

    /**
     * Getter for {@link CopyOnWriteArrayList<>}-formed {@code services} of
     * the instance of {@link Server} containing {@link AService} instances.
     *
     * @return the value of {@code services}
     * @see CopyOnWriteArrayList<>
     * @see Server
     */
    public CopyOnWriteArrayList<AService> getServices() {

        return services;
    }

    /* *******************************************************/
    /* Static methods ****************************************/

    /**
     * <p>Static Singleton method <strong>getInstance</strong>
     * for returning the only instance of the class of Server.</p>
     *
     * <p><b>Thread-safe</b> implementation of {@code getInstance()}
     * method to prevent more instance creation while using
     * multithreading algorithm.</p>
     *
     * @return the only instance of the Server class
     * @see <a href="https://en.wikipedia.org/wiki/Singleton_pattern">
     * Singleton at Wikipedia</a>
     * @see <a href="https://en.wikipedia.org/wiki/Thread_safety">
     * Thread safety at Wikipedia</a>
     * @see Thread
     */
    public static Server getInstance() {

        if (singletonInstance == null) {

            synchronized (Server.class) {

                singletonInstance = new Server();
            }
        }

        return singletonInstance;
    }


    /**
     * <p>Resets the static field of {@code singletonInstance}
     * to default state - it is set to {@code null} again.</p>
     *
     * <p><b>ALL SET DATA ARE GONNA BE REMOVED</b></p>
     */
    public static void reset() {

        singletonInstance = null;
    }
}
