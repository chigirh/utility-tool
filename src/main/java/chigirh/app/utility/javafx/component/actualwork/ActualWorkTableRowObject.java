package chigirh.app.utility.javafx.component.actualwork;

import java.util.ArrayList;
import java.util.List;

import chigirh.app.utility.javafx.component.TableRow.RowType;
import chigirh.app.utility.javafx.component.TableRowObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualWorkTableRowObject extends TableRowObject<ActualWorkTableRowObject>{

	public ActualWorkTableRowObject(RowType rowType) {
		super(rowType);
	}

	private boolean isExpanted = false;

	private Runnable addTask;

	List<ActualWorkTableRowObject> children = new ArrayList<>();


}
