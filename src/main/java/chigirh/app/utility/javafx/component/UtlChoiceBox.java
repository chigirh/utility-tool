package chigirh.app.utility.javafx.component;

import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

public class UtlChoiceBox<E> extends ChoiceBox<UtlLabelValueBean<E>>{

	private static final String STYLE_CLASS = "utl-choice-box";

	public UtlChoiceBox() {
		super();
		getStyleClass().add(STYLE_CLASS);
		setConverter(this.new UtlStringConvrter());
	}

	private class UtlStringConvrter extends StringConverter<UtlLabelValueBean<E>>{

		@Override
		public String toString(UtlLabelValueBean<E> object) {
			// TODO 自動生成されたメソッド・スタブ
			return object.getLabel();
		}

		@Override
		public UtlLabelValueBean<E> fromString(String string) {
			return null;
		}

	}


}
