package model.exceptions;

import presenter.ConfigurationsPresenter;

public class TooManyStartingPlantsException {

    public static void throwException(ConfigurationsPresenter configurationsPresenter){
        ConfigurationsExceptionDisplayer.printException("Zbyt mala mapa jak na tyle roslin na poczatek", configurationsPresenter);
    }

}
