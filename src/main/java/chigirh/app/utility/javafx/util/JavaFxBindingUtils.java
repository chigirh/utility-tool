package chigirh.app.utility.javafx.util;

import chigirh.app.utility.javafx.component.UtlChoiceBox;
import chigirh.app.utility.javafx.component.UtlLabelValueBean;
import chigirh.app.utility.javafx.component.UtlTextField;
import chigirh.app.utility.javafx.component.table.CheckTableCell;
import chigirh.app.utility.javafx.component.table.ChoiceTableCell;
import chigirh.app.utility.javafx.component.table.TextTableCell;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

public class JavaFxBindingUtils {

	public static void bindingCell(TextTableCell cell, StringProperty property) {
		cell.getProperty().bindBidirectional(property);
	}

	public static void bindingNode(UtlTextField node, StringProperty property) {
		node.textProperty().bindBidirectional(property);
	}

	public static void bindingCell(CheckTableCell cell, BooleanProperty property) {
		cell.getProperty().bindBidirectional(property);
	}

	public static void bindingNode(CheckBox node, BooleanProperty property) {
		node.selectedProperty().bindBidirectional(property);
	}

	public static <E> void bindingCell(ChoiceTableCell<E> cell, ObjectProperty<UtlLabelValueBean<E>> selectedProperty,
			ObjectProperty<ObservableList<UtlLabelValueBean<E>>> listPropery) {
		cell.getItemProperty().bindBidirectional(listPropery);
		cell.getProperty().bindBidirectional(selectedProperty);
	}

	public static <E> void bindingNode(UtlChoiceBox<E> node, ObjectProperty<UtlLabelValueBean<E>> selectedProperty,
			ObjectProperty<ObservableList<UtlLabelValueBean<E>>> listPropery) {
		node.itemsProperty().bindBidirectional(listPropery);
		node.selectedProperty().bindBidirectional(selectedProperty);
	}

}
