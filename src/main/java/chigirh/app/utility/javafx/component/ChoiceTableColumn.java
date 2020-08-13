package chigirh.app.utility.javafx.component;

import java.util.function.Function;

import chigirh.app.utility.javafx.util.JavaFxBindingUtils;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import lombok.Setter;

public class ChoiceTableColumn<R extends SimpleTableRow<?>, C>
		extends TableColumn<R, ObjectProperty<UtlLabelValueBean<C>>, UtlLabelValueBean<C>> {

	@Setter
	private Function<R,ObjectProperty<ObservableList<UtlLabelValueBean<C>>>> itemPropertyFactory;

	@Override
	protected UtlTableCell<?, ?> getCell() {
		ChoiceTableCell<C> cell = new ChoiceTableCell<>();
		cell.setWidth(getWidth());
		return cell;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void binding(UtlTableCell<?, ?> cell, ObjectProperty<UtlLabelValueBean<C>> prop,R row) {
		JavaFxBindingUtils.bindingCell((ChoiceTableCell<C>) cell, prop, itemPropertyFactory.apply(row));

	}

}
