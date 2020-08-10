package chigirh.app.utility.javafx.component;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.CheckBox;

public class CheckTableCell extends TableCell<CheckBox, BooleanProperty> {


	CheckTableCell(boolean isSelected) {
		super();
		setCell(new CheckBox());
		getCell().setSelected(isSelected);
	}

	@Override
	public void change(Runnable task) {
		getCell().selectedProperty().addListener((ob, ov, nv) -> {
			task.run();
		});
	}

	@Override
	public BooleanProperty getProperty() {
		return getCell().selectedProperty();
	}

}
