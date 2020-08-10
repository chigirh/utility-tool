package chigirh.app.utility.javafx.component;

import chigirh.app.utility.javafx.util.JavaFxBindingUtils;
import javafx.beans.property.BooleanProperty;

public class CheckTableColumn<E,R extends SimpleTableRow<?>> extends TableColumn<E,R, BooleanProperty,Boolean> {

	@Override
	protected TableCell<?,?> getCell() {
		CheckTableCell cell = new CheckTableCell(false);
		cell.setWidth(getWidth());
		return cell;
	}

	@Override
	public void binding(TableCell<?,?> cell, BooleanProperty prop) {
		JavaFxBindingUtils.binding((CheckTableCell)cell, prop);
	}

}
