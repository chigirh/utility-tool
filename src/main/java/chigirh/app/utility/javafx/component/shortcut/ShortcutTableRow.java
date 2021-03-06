package chigirh.app.utility.javafx.component.shortcut;

import java.io.IOException;
import java.util.regex.Pattern;

import chigirh.app.utility.app.screen.shortcut.ShortcutRow;
import chigirh.app.utility.javafx.component.table.TableRow;
import chigirh.app.utility.javafx.component.table.TextTableCell;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ShortcutTableRow extends TableRow<ShortcutTableRowObject> {

	public ShortcutTableRow() {
		super();
	}

	@Override
	protected void changed(ObservableValue<? extends ShortcutTableRowObject> observable,
			ShortcutTableRowObject oldValue, ShortcutTableRowObject newValue) {
		row.getChildren().clear();
		if (newValue == null || newValue.getRowType() == RowType.EMPTY) {
			HBox hBox = new HBox();
			TextTableCell emptyCell = new TextTableCell("");
			hBox.getChildren().add(emptyCell);
			emptyCell.setPrefWidth(Double.MAX_VALUE);
			row.getChildren().add(hBox);
			return;
		}
		row.getChildren().add(newValue.createRow());

		Button openButton = new Button("開く");
		openButton.setOnAction(e -> open(e, newValue.getVm()));
		HBox.setMargin(openButton, new Insets(0, 10, 0, 10));
		row.getChildren().add(openButton);

	}

	private void open(ActionEvent event, ShortcutRow vm) {

		Pattern pattern = Pattern.compile("^http");

		Runtime runtime = Runtime.getRuntime();;
		String[] command;
		if (pattern.matcher(vm.getScPath()).find()) {
			String[] c = { "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe", vm.getScPath() };
			command = c;
		} else {
			String[] c = { "EXPLORER", vm.getScPath() };
			command = c;
		}

		try {
			runtime.exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
