package at.fhv.ohe.imageprocess.viewer.view;

import at.fhv.ohe.imageprocess.viewer.viewmodel.MainViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Assignment_3_Picture
 * at.fhv.ohe.imageprocess.viewer.view
 * MainView.java.java
 * 12/11/2018 OliverHeil
 * <p>
 * Enter Description here
 */
public class MainView implements FxmlView<MainViewModel>, Initializable {

    @FXML
    private Slider thresholdSlider;
    @FXML
    private TextField inputPathBox;
    @FXML
    private ComboBox<String> strategySelector;
    @FXML
    private Button runBtn;

    @InjectViewModel
    private MainViewModel viewModel;
    @FXML
    private ListView<StepView> stepViewer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        thresholdSlider.valueProperty().bindBidirectional(viewModel.thresholdProperty());
        thresholdSlider.setMin(0);
        thresholdSlider.setMax(100);
        thresholdSlider.setMajorTickUnit(25);
        thresholdSlider.setMinorTickCount(5);
        thresholdSlider.setBlockIncrement(1);
        thresholdSlider.disableProperty().bind(viewModel.getRunCommand().executableProperty().not());

        inputPathBox.textProperty().bindBidirectional(viewModel.inputPathProperty());
        inputPathBox.disableProperty().bind(viewModel.getRunCommand().executableProperty().not());

        strategySelector.itemsProperty().bind(viewModel.strategiesProperty());
        strategySelector.disableProperty().bind(viewModel.getRunCommand().executableProperty().not());
        viewModel.selectedStrategyProperty().bind(strategySelector.selectionModelProperty().get().selectedItemProperty());

        runBtn.disableProperty().bind(viewModel.getRunCommand().executableProperty().not());

        stepViewer.itemsProperty().bind(viewModel.stepViewCollectionProperty());
        stepViewer.setCellFactory(param -> new ListCell<StepView>() {
            private ImageView imageView = new ImageView();

            @Override
            protected void updateItem(StepView item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null) {
                    setText("None");
                    setGraphic(null);
                    return;
                }
                imageView.setImage(item.getImage());
                setText(item.getStepMessage());
                setGraphic(imageView);
            }
        });
    }

    @FXML
    private void runBtnClick(ActionEvent actionEvent) {
        viewModel.getRunCommand().execute();
    }
}
