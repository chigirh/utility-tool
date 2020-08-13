package chigirh.app.utility.javafx.component.actualwork;


import chigirh.app.utility.javafx.component.PseudoClassConstans;
import chigirh.app.utility.javafx.component.TableRow;
import chigirh.app.utility.javafx.component.TextTableCell;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ActualWorkTableRow extends TableRow<ActualWorkTableRowObject> {

	private static final double ICON_WIDTH = 30.0;

	final Runnable callBack;

	public ActualWorkTableRow(Runnable callBack) {
		super();
		this.callBack = callBack;
	}

	@Override
	protected void changed(ObservableValue<? extends ActualWorkTableRowObject> observable,
			ActualWorkTableRowObject oldValue, ActualWorkTableRowObject newValue) {
		row.getChildren().clear();

		if (newValue == null || newValue.getRowType() == RowType.EMPTY) {
			HBox hBox = new HBox();
			TextTableCell emptyCell = new TextTableCell("");
			hBox.getChildren().add(emptyCell);
			emptyCell.setPrefWidth(Double.MAX_VALUE);
			row.getChildren().add(emptyIcon());
			row.getChildren().add(hBox);
			return;
		}

		row.getChildren().add(newValue.getRowType() == RowType.PARENT ? icon() : emptyIcon());
		row.getChildren().add(newValue.createRow());
		pseudoClassStateChanged(PseudoClassConstans.EXPANTED,newValue.isExpanted());
		if(newValue.getRowType() == RowType.PARENT) {
			Button addButton = new Button("追加");
			addButton.setOnAction(e -> newValue.getAddTask().run());
			HBox.setMargin(addButton, new Insets(0, 0, 0, 5.0));
			row.getChildren().add(addButton);
		}
	}

	private Label emptyIcon() {
		Label icon = new Label();
		icon.setPrefWidth(ICON_WIDTH);
		return icon;
	}

	private TextTableCell icon() {
		TextTableCell icon = new TextTableCell("▼");
		icon.getStyleClass().add("expanted-icon");
		icon.setWidth(ICON_WIDTH);
		icon.addEvenet(MouseEvent.MOUSE_CLICKED, e -> iconClickHandler(e, icon));
		return icon;
	}

	private void iconClickHandler(MouseEvent event, TextTableCell icon) {
		if (event.getButton() != MouseButton.PRIMARY) {
			return;
		}
		ActualWorkTableRowObject tableRowObject = objectProperty.get();

		tableRowObject.getChildren().forEach(e -> e.setExpanted(!tableRowObject.isExpanted()));
		tableRowObject.getChildren().forEach(e -> e.setVisible(!tableRowObject.isExpanted()));
		tableRowObject.setExpanted(!tableRowObject.isExpanted());

		pseudoClassStateChanged(PseudoClassConstans.EXPANTED,tableRowObject.isExpanted());

		callBack.run();
	}

}
