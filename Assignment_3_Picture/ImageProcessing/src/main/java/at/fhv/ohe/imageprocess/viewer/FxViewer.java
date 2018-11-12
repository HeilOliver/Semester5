package at.fhv.ohe.imageprocess.viewer;

import at.fhv.ohe.imageprocess.viewer.view.MainView;
import at.fhv.ohe.imageprocess.viewer.viewmodel.MainViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Assignment_3_Picture
 * at.fhv.ohe.imageprocess.viewer
 * null.java
 * 12/11/2018 OliverHeil
 * <p>
 * Enter Description here
 */
public class FxViewer extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Solder Join Checker");

        ViewTuple<MainView, MainViewModel> viewTuple =
                FluentViewLoader.fxmlView(MainView.class).load();

        Parent root = viewTuple.getView();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
