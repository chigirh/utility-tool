package chigirh.app.utility.javafx.util;

import chigirh.app.utility.javafx.component.CheckTableCell;
import chigirh.app.utility.javafx.component.ChoiceTableCell;
import chigirh.app.utility.javafx.component.TextTableCell;
import chigirh.app.utility.javafx.component.UtlLabelValueBean;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class JavaFxBindingUtils {

	public static void binding(TextTableCell cell, StringProperty property) {
		cell.getProperty().bindBidirectional(property);
	}

	public static void binding(CheckTableCell cell, BooleanProperty property) {
		cell.getProperty().bindBidirectional(property);
	}

	public static <E> void binding(ChoiceTableCell<E> cell, ObjectProperty<UtlLabelValueBean<E>> selectedProperty,
			ObjectProperty<ObservableList<UtlLabelValueBean<E>>> listPropery) {
		cell.getItemProperty().bindBidirectional(listPropery);
		cell.getProperty().bindBidirectional(selectedProperty);
	}

}
