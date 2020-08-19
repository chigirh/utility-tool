package chigirh.app.utility.javafx.component.shortcut;

import chigirh.app.utility.app.screen.shortcut.ShortcutRow;
import chigirh.app.utility.javafx.component.table.TableRow.RowType;
import chigirh.app.utility.javafx.component.table.TableRowObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortcutTableRowObject extends TableRowObject<ShortcutTableRowObject, ShortcutRow> {

	public ShortcutTableRowObject(ShortcutRow vm) {
		super(RowType.NORMAL);
		this.vm = vm;
	}
}
