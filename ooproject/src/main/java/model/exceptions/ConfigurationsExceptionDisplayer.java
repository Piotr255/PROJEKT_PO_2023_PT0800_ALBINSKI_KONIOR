package model.exceptions;

import presenter.ConfigurationsPresenter;

public class ConfigurationsExceptionDisplayer {
    static void printException(String message, ConfigurationsPresenter configurationsPresenter){
        configurationsPresenter.printException(message);
    }

}
