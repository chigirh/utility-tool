package chigirh.app.utility.javafx.component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public abstract class TableRow<E extends TableRowObject<?>> extends AnchorPane {

	public static enum RowType {
		EMPTY, NORMAL, PARENT, CHILD;
	}

	private static final String STYLE_CLASS = "utl-table-row";

	protected HBox row = new HBox();

	protected final ObjectProperty<E> objectProperty = new SimpleObjectProperty<>(null);;

	public TableRow() {

		getStyleClass().add(STYLE_CLASS);

		getChildren().add(row);
		objectProperty.addListener(this::changed);
		clear();
	}

	public void clear() {
		objectProperty.set(null);
		changed(null, null, null);
	}

	public void set(E tableRowObject) {
		objectProperty.set(tableRowObject);
	}

	protected abstract void changed(ObservableValue<? extends E> observable, E oldValue,
			E newValue);

}
