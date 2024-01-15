package model.exceptions;

import presenter.ConfigurationsPresenter;

public class AlreadyExistingConfigurationNameException {

    public static void throwException(ConfigurationsPresenter configurationsPresenter){
        ConfigurationsExceptionDisplayer.printException("Istnieje juz konfiguracja o tej nazwie", configurationsPresenter);
    }
}
