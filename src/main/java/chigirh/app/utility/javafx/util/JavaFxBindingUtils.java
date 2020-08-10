package chigirh.app.utility.javafx.util;

import chigirh.app.utility.javafx.component.CheckTableCell;
import chigirh.app.utility.javafx.component.TextTableCell;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public class JavaFxBindingUtils {

	public static void binding(TextTableCell cell,StringProperty property) {
		cell.getProperty().bindBidirectional(property);
	}

	public static void binding(CheckTableCell cell,BooleanProperty property) {
		cell.getProperty().bindBidirectional(property);
	}

}
