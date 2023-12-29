package model.exceptions;

import presenter.SimulationPresenter;

public class OtherConfigurationsException {

    public static void throwException(SimulationPresenter simulationPresenter){
        ConfigurationsExceptionDisplayer.printException("Niepoprawne dane wejsciowe", simulationPresenter);
    }
}
