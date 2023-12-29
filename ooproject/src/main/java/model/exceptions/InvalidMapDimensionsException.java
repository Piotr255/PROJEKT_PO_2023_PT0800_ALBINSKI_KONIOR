package model.exceptions;

import presenter.ConfigurationsPresenter;

public class InvalidMapDimensionsException{

    public static void throwException(ConfigurationsPresenter configurationsPresenter){
        ConfigurationsExceptionDisplayer.printException("Niepoprawne wymiary mapy", configurationsPresenter);
    }

}
