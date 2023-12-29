package model.exceptions;

import presenter.SimulationPresenter;

public class InvalidMapDimensionsException{

    public static void throwException(SimulationPresenter simulationPresenter){
        ConfigurationsExceptionDisplayer.printException("Niepoprawne wymiary mapy", simulationPresenter);
    }

}
