package chigirh.app.utility.javafx.component;

import chigirh.app.utility.javafx.util.JavaFxBindingUtils;
import javafx.beans.property.BooleanProperty;

public class CheckTableColumn<R extends SimpleTableRow<?>> extends TableColumn<R, BooleanProperty, Boolean> {

	@Override
	protected UtlTableCell<?,?> getCell() {
		CheckTableCell cell = new CheckTableCell(false);
		cell.setWidth(getWidth());
		return cell;
	}

	@Override
	public void binding(UtlTableCell<?,?> cell, BooleanProperty prop,R row) {
		JavaFxBindingUtils.bindingCell((CheckTableCell)cell, prop);
	}

}
