package chigirh.app.utility.javafx.component.table;

import chigirh.app.utility.javafx.util.JavaFxBindingUtils;
import javafx.beans.property.StringProperty;
import lombok.Setter;

public class TextTableColumn<R extends SimpleTableRow<?>> extends TableColumn<R, StringProperty, String> {

	@Setter
	private String validator;

	@Override
	protected TableCell<?, ?> getCell() {
		TextTableCell cell = new TextTableCell(isEditable());
		cell.setWidth(getWidth());
		cell.setValidator(validator);
		return cell;
	}

	@Override
	public void binding(TableCell<?, ?> cell, StringProperty prop, R row) {
		JavaFxBindingUtils.bindingCell((TextTableCell) cell, prop);
	}
}
