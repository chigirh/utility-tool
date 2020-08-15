package chigirh.app.utility.javafx.component.actualwork;

import java.util.ArrayList;
import java.util.List;

import chigirh.app.utility.app.screen.actualwork.ActualWorkRow;
import chigirh.app.utility.javafx.component.table.TableRowObject;
import chigirh.app.utility.javafx.component.table.TableRow.RowType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualWorkTableRowObject extends TableRowObject<ActualWorkTableRowObject,ActualWorkRow>{

	public ActualWorkTableRowObject(RowType rowType) {
		super(rowType);
	}

	private boolean isExpanted = false;

	private Runnable addTask;

	List<ActualWorkTableRowObject> children = new ArrayList<>();


}
