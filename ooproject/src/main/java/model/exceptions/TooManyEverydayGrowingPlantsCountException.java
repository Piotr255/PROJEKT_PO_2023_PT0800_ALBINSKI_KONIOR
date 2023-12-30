package model.exceptions;

import presenter.ConfigurationsPresenter;

public class TooManyEverydayGrowingPlantsCountException {

    public static void throwException(ConfigurationsPresenter configurationsPresenter){
        ConfigurationsExceptionDisplayer.printException("Podano wiecej roslin rosnacych codziennie niz sie zmiesci na mapie", configurationsPresenter);
    }
}
