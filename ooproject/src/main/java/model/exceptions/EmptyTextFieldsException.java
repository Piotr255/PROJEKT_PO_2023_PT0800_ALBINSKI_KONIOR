package model.exceptions;

import presenter.SimulationPresenter;

public class EmptyTextFieldsException {

    public static void throwException(SimulationPresenter simulationPresenter){
        ConfigurationsExceptionDisplayer.printException("Uzupelnij pola", simulationPresenter);
    }

}
