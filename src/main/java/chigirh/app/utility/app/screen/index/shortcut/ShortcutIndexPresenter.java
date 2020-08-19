package chigirh.app.utility.app.screen.index.shortcut;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import chigirh.app.utility.app.domain.shortcut.ShortcutGroupEntity;
import chigirh.app.utility.app.domain.shortcut.ShortcutService;
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
public class ShortcutIndexPresenter extends PresenterBase {

	private static final double HEIGHT = 30.0;

	@FXML
	private ScrollPane scScroll;

	@FXML
	private VBox scBox;

	@FXML
	private TextField scAddTf;

	@FXML
	private Button scAddBt;

	final WindowFactory windowFactory;

	final FxmlProperties fxmlProperties;

	final ShortcutService shortcutService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		scScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
	}

	@Override
	public void onStart() {
		update();
	}

	private void update() {
		scBox.getChildren().clear();
		scBox.getChildren()
				.addAll(shortcutService.scGroupGet().stream().map(this::creteRow).collect(Collectors.toList()));

	}

	private HBox creteRow(ShortcutGroupEntity entity) {

		Insets mergin = new Insets(0.0, 10.0, 0.0, 0.0);

		HBox row = new HBox();
		VBox.setMargin(row, new Insets(2.0, 0, 2.0, 0));
		UtlLabel title = new UtlLabel(entity.getScGroupName());
		title.setPrefWidth(300.0);
		title.setPrefHeight(HEIGHT);
		HBox.setMargin(title, mergin);
		row.getChildren().add(title);

		Button addbutton = new Button("開く");
		addbutton.setPrefHeight(HEIGHT);
		HBox.setMargin(addbutton, mergin);
		addbutton.setOnAction(
				e -> windowFactory.createWindow(fxmlProperties.getShortcut(), entity).resizable(false).show());

		Button remButton = new Button("削除");
		remButton.setPrefHeight(HEIGHT);
		HBox.setMargin(remButton, mergin);
		remButton.setOnAction(e -> {
			shortcutService.scGroupDelete(entity.getScGroupId());
			update();
		});

		row.getChildren().add(addbutton);
		row.getChildren().add(remButton);

		return row;
	}

	@FXML
	public void onscAdd(ActionEvent e) throws ParseException {
		String groupName = scAddTf.getText();

		if (StringUtils.isEmpty(groupName)) {
			return;
		}

		ShortcutGroupEntity entity = shortcutService.scGroupAdd(groupName);
		if (entity == null) {
			return;
		}
		scAddTf.setText("");
		scBox.getChildren().add(creteRow(entity));
	}
}
