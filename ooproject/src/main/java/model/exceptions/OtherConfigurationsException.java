package model.exceptions;

import presenter.ConfigurationsPresenter;

public class OtherConfigurationsException {

    public static void throwException(ConfigurationsPresenter configurationsPresenter){
        ConfigurationsExceptionDisplayer.printException("Niepoprawne dane wejsciowe", configurationsPresenter);
    }
}
