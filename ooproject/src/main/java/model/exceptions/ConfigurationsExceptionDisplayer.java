package model.exceptions;

import presenter.SimulationPresenter;

public class ConfigurationsExceptionDisplayer {
    static void printException(String message, SimulationPresenter simulationPresenter){
        simulationPresenter.printException(message);
    }

}
