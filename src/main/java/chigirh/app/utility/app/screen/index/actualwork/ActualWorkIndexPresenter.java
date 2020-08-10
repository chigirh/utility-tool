package chigirh.app.utility.app.screen.index.actualwork;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import chigirh.app.utility.app.domain.actualwork.ActualWorkGroupEntity;
import chigirh.app.utility.app.domain.actualwork.ActualWorkService;
import chigirh.app.utility.common.prop.FxmlProperties;
import chigirh.app.utility.javafx.presenter.PresenterBase;
import chigirh.app.utility.javafx.window.WindowFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class ActualWorkIndexPresenter extends PresenterBase {

	@FXML
	private ScrollPane operationScroll;

	@FXML
	private VBox operationBox;

	@FXML
	private TextField operationAddTf;

	@FXML
	private Button operationAddBt;

	final WindowFactory windowFactory;

	final FxmlProperties fxmlProperties;

	final ActualWorkService actualWorkService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		operationScroll.setHbarPolicy(ScrollBarPolicy.NEVER);

	}

	@Override
	public void onStart() {
		update();
	}

	private void update() {
		operationBox.getChildren().clear();
		operationBox.getChildren()
				.addAll(actualWorkService.awGroupGet().stream().map(this::creteRow).collect(Collectors.toList()));

	}

	private HBox creteRow(ActualWorkGroupEntity entity) {
		HBox row = new HBox();
		VBox.setMargin(row, new Insets(1.0, 0, 1.0, 0));
		AnchorPane titilePane = new AnchorPane();
		TextField title = new TextField(entity.getAwGroupName());
		title.setEditable(false);
		title.setPrefWidth(300.0);
		titilePane.getChildren().add(title);
		row.getChildren().add(titilePane);

		Button button = new Button("開く");
		button.setOnAction(e -> windowFactory.createWindow(fxmlProperties.getActualWork(), entity).resizable(false).show());

		row.getChildren().add(button);

		return row;
	}

	@FXML
	public void onOperationAdd(ActionEvent e) {
		String groupName = operationAddTf.getText();

		if (StringUtils.isEmpty(groupName)) {
			return;
		}

		ActualWorkGroupEntity entity = actualWorkService.awGroupAdd(groupName,null);
		if (entity == null) {
			return;
		}
		operationAddTf.setText("");
		operationBox.getChildren().add(creteRow(entity));
	}

}
