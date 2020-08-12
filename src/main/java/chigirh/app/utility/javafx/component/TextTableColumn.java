package chigirh.app.utility.javafx.component;

import chigirh.app.utility.javafx.util.JavaFxBindingUtils;
import javafx.beans.property.StringProperty;
import lombok.Setter;

public class TextTableColumn<R extends SimpleTableRow<?>> extends TableColumn<R, StringProperty, String> {

	@Setter
	private String validator;

	@Override
	protected UtlTableCell<?, ?> getCell() {
		TextTableCell cell = new TextTableCell(isEditable());
		cell.setWidth(getWidth());
		cell.setValidator(validator);
		return cell;
	}

	@Override
	public void binding(UtlTableCell<?, ?> cell, StringProperty prop,R row) {
		JavaFxBindingUtils.binding((TextTableCell) cell, prop);
	}

}
