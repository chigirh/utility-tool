package chigirh.app.utility.app.screen.index.taskmgr;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import chigirh.app.utility.app.domain.taskmgr.TaskGroupEntity;
import chigirh.app.utility.app.domain.taskmgr.TaskManagerService;
import chigirh.app.utility.common.prop.FxmlProperties;
import chigirh.app.utility.javafx.component.UtlLabel;
import chigirh.app.utility.javafx.presenter.PresenterBase;
import chigirh.app.utility.javafx.window.WindowFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class TaskManagerIndexPresenter extends PresenterBase {

	private static final double HEIGHT = 30.0;

	@FXML
	private ScrollPane taskScroll;

	@FXML
	private VBox taskBox;

	@FXML
	private TextField taskAddTf;

	@FXML
	private Button taskAddBt;

	final WindowFactory windowFactory;

	final FxmlProperties fxmlProperties;

	final TaskManagerService taskManagerService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		taskScroll.setHbarPolicy(ScrollBarPolicy.NEVER);

	}

	@Override
	public void onStart() {
		update();
	}

	private void update() {
		taskBox.getChildren().clear();
		taskBox.getChildren()
				.addAll(taskManagerService.taskGroupGet().stream().map(this::creteRow).collect(Collectors.toList()));

	}

	private HBox creteRow(TaskGroupEntity entity) {

		Insets mergin = new Insets(0.0, 10.0, 0.0, 0.0);

		HBox row = new HBox();
		VBox.setMargin(row, new Insets(2.0, 0, 2.0, 0));
		UtlLabel title = new UtlLabel(entity.getTaskGroupName());
		title.setPrefWidth(300.0);
		title.setPrefHeight(HEIGHT);
		HBox.setMargin(title, mergin);
		row.getChildren().add(title);

		Button addbutton = new Button("開く");
		addbutton.setPrefHeight(HEIGHT);
		HBox.setMargin(addbutton, mergin);
		addbutton.setOnAction(
				e -> windowFactory.createWindow(fxmlProperties.getTaskManager(), entity).resizable(false).show());

		Button remButton = new Button("削除");
		remButton.setPrefHeight(HEIGHT);
		HBox.setMargin(remButton, mergin);
		remButton.setOnAction(e -> {
			taskManagerService.taskGroupDelete(entity.getTaskGroupId());
			update();
		});

		row.getChildren().add(addbutton);
		row.getChildren().add(remButton);

		return row;
	}

	@FXML
	public void onTaskAdd(ActionEvent e) throws ParseException {
		String groupName = taskAddTf.getText();

		if (StringUtils.isEmpty(groupName)) {
			return;
		}

		TaskGroupEntity entity = taskManagerService.taskGroupAdd(groupName);
		if (entity == null) {
			return;
		}
		taskAddTf.setText("");
		taskBox.getChildren().add(creteRow(entity));
	}
}
