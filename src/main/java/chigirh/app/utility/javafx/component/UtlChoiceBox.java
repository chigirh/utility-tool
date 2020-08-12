package chigirh.app.utility.javafx.component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

public class UtlChoiceBox<E> extends ChoiceBox<UtlLabelValueBean<E>> {

	private static final String STYLE_CLASS = "utl-choice-box";

	private ObjectProperty<UtlLabelValueBean<E>> selectedProperty = new SimpleObjectProperty<>(this,
			"selectedProperty");

	public UtlChoiceBox() {
		super();
		getStyleClass().add(STYLE_CLASS);
		setConverter(this.new UtlStringConvrter());
		selectionModelProperty().get().selectedItemProperty()
				.addListener((ob, ov, nv) -> selectedProperty.set(nv));

		selectedProperty.addListener((ob, ov, nv) -> getSelectionModel().select(nv));
	}

	public ObjectProperty<UtlLabelValueBean<E>> selectedProperty() {
		return selectedProperty;
	}

	private class UtlStringConvrter extends StringConverter<UtlLabelValueBean<E>> {

		@Override
		public String toString(UtlLabelValueBean<E> object) {
			return object.getLabel();
		}

		@Override
		public UtlLabelValueBean<E> fromString(String string) {
			return null;
		}

	}

}
