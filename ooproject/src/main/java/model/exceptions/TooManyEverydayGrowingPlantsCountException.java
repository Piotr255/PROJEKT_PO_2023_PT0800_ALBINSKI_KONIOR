package model.exceptions;

import presenter.SimulationPresenter;

public class TooManyEverydayGrowingPlantsCountException {

    public static void throwException(SimulationPresenter simulationPresenter){
        ConfigurationsExceptionDisplayer.printException("Podano wiecej roslin rosnacych codziennie niz sie zmiesci na mapie", simulationPresenter);
    }
}
