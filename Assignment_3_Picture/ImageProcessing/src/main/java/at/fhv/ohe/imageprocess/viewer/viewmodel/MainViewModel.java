package at.fhv.ohe.imageprocess.viewer.viewmodel;

import at.fhv.ohe.imageprocess.strategy.IStrategy;
import at.fhv.ohe.imageprocess.strategy.SolderingResult;
import at.fhv.ohe.imageprocess.viewer.StrategyFactory;
import at.fhv.ohe.imageprocess.viewer.view.StepView;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Assignment_3_Picture
 * at.fhv.ohe.imageprocess.viewer.viewmodel
 * null.java
 * 12/11/2018 OliverHeil
 * <p>
 * Enter Description here
 */
public class MainViewModel implements ViewModel, IStepCallBack {
    private static final String DEFAULT_INPUT_PATH = "ImageProcessing\\target\\classes\\at\\fhv\\ohe\\imageprocess\\images\\loetstellen.jpg";
    private static final int DEFAULT_THRESHOLD = 30;

    private StringProperty inputPath = new SimpleStringProperty(DEFAULT_INPUT_PATH);
    private IntegerProperty threshold = new SimpleIntegerProperty(DEFAULT_THRESHOLD);
    private StringProperty selectedStrategy = new SimpleStringProperty();
    private ListProperty<String> strategies = new SimpleListProperty<>();
    private ListProperty<StepView> stepViewCollection = new SimpleListProperty<>();
    private Command runCommand;

    public MainViewModel() {
        final StrategyFactory strategyFactory = StrategyFactory.getInstance();

        strategies.setValue(
                FXCollections.observableArrayList(
                        strategyFactory.getAllStrategies()));

        final IStepCallBack stepCallBack = this;

        runCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                if (selectedStrategy.get() == null) {
                    Platform.runLater(() -> showValidationError("No Strategy is selected"));
                    return;
                }

                if (!checkInputPath()) {
                    Platform.runLater(() -> showValidationError("Input Path is not Valid"));
                    return;
                }

                IStrategy strategyByName = strategyFactory.getStrategyByName(selectedStrategy.get());
                try {
                    stepViewCollection.setValue(FXCollections.observableArrayList());
                    Set<SolderingResult> results = strategyByName.run(inputPath.get(), threshold.get(), stepCallBack);
                    Platform.runLater(() -> showResult(results));
                } catch (Exception e) {
                    Platform.runLater(() -> showExecutionError(e));
                }
            }
        }, true);
    }

    public ListProperty<StepView> stepViewCollectionProperty() {
        return stepViewCollection;
    }

    private boolean checkInputPath() {
        File file = new File(inputPath.get());
        return file.exists();
    }

    private void showResult(Set<SolderingResult> solderingResults) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Result");
        alert.setHeaderText("Result");

        String content = solderingResults.stream()
                .sorted(Comparator.comparingInt(SolderingResult::getNumber))
                .map(result ->
                        result.getNumber() + "- Soldering " +
                                (result.isOk() ? "OK" : "NOT OK") +
                                ">> " + result.getToleranceX() + ";" + result.getToleranceX() + "\n")
                .collect(Collectors.joining());
        alert.setContentText(content);
        alert.show();
    }

    private void showExecutionError(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Executing Failed");
        alert.setHeaderText("Exception!");
        alert.setContentText("An Exception occured while executing the pipe!");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    private void showValidationError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText("Validation for the input fields failed");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public StringProperty inputPathProperty() {
        return inputPath;
    }

    public IntegerProperty thresholdProperty() {
        return threshold;
    }

    public StringProperty selectedStrategyProperty() {
        return selectedStrategy;
    }

    public ListProperty<String> strategiesProperty() {
        return strategies;
    }

    public Command getRunCommand() {
        return runCommand;
    }

    @Override
    public void addStep(StepView stepView) {
        stepViewCollection.get().add(stepView);
    }
}
