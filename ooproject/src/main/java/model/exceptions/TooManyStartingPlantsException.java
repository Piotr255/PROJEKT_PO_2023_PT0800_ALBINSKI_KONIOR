package model.exceptions;

import presenter.SimulationPresenter;

public class TooManyStartingPlantsException {

    public static void throwException(SimulationPresenter simulationPresenter){
        ConfigurationsExceptionDisplayer.printException("Zbyt mala mapa jak na tyle roslin na poczatek", simulationPresenter);
    }

}
