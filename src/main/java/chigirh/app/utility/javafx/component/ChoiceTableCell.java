package chigirh.app.utility.javafx.component;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

public class ChoiceTableCell<T> extends UtlTableCell<UtlChoiceBox<T>, ObjectProperty<UtlLabelValueBean<T>>> {

	public ChoiceTableCell() {
		setCell(new UtlChoiceBox<T>());
	}

	public void change(Runnable task) {
		getProperty().addListener((ob, ov, nv) -> task.run());
	}

	@Override
	public ObjectProperty<UtlLabelValueBean<T>> getProperty() {
		return getCell().selectedProperty();
	}

	public ObjectProperty<ObservableList<UtlLabelValueBean<T>>> getItemProperty() {
		return getCell().itemsProperty();
	}

}
