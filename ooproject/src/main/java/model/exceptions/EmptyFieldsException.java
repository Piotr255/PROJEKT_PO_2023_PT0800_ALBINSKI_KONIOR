package model.exceptions;

import presenter.ConfigurationsPresenter;

public class EmptyFieldsException {

    public static void throwException(ConfigurationsPresenter configurationsPresenter){
        ConfigurationsExceptionDisplayer.printException("Uzupelnij pola", configurationsPresenter);
    }

}
