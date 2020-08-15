package chigirh.app.utility.javafx.component.table;

import chigirh.app.utility.javafx.component.UtlChoiceBox;
import chigirh.app.utility.javafx.component.UtlLabelValueBean;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

public class ChoiceTableCell<T> extends TableCell<UtlChoiceBox<T>, ObjectProperty<UtlLabelValueBean<T>>> {

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
