package main;

import model.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private List<Simulation> simulations;
    private List<Thread> threads = new ArrayList<>();

    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runSync() {
        for (Simulation simulation : simulations) {
            simulation.run();
        }
    }
    public void runAsync() throws InterruptedException {
        for(Simulation simulation : simulations){
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }
        //awaitSimulationsEnd();
    }
    public void awaitSimulationsEnd() throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
    public void runAsyncInThreadPool(){
        for(Simulation simulation : simulations){
            executorService.submit(simulation);
        }
    }

}
