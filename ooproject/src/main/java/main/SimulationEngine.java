package main;

import model.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SimulationEngine {
    private static ConcurrentMap<Simulation, ScheduledFuture<?>> simulationTasks = new ConcurrentHashMap<>();
    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);

    public static void submitSimulation(Simulation simulation){
        ScheduledFuture<?> future = executorService.scheduleAtFixedRate(simulation, 0, 75, TimeUnit.MILLISECONDS);
        simulationTasks.put(simulation, future);
    }
    public static void deleteSimulation(Simulation simulation){
        ScheduledFuture<?> future = simulationTasks.get(simulation);
        if (future != null) {
            future.cancel(true);
            simulationTasks.remove(simulation);
        }
    }


}
