package chigirh.app.utility.javafx.component;

import java.util.function.Predicate;

import chigirh.app.utility.javafx.util.JavaFxBindingUtils;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;
import lombok.Setter;

public class TextTableColumn<E,R extends SimpleTableRow<?>> extends TableColumn<E,R,StringProperty,String> {

	@Setter
	private Predicate<TextField> validator;

	@Override
	protected TableCell<?,?> getCell() {
		TextTableCell cell =  new TextTableCell(isEditable());
		cell.setWidth(getWidth());
		cell.setValidator(validator);
		return cell;
	}

	@Override
	public void binding(TableCell<?,?> cell, StringProperty prop) {
		JavaFxBindingUtils.binding((TextTableCell)cell, prop);
	}


}
