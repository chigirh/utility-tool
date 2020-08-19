package chigirh.app.utility.app.screen.shortcut;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import chigirh.app.utility.app.domain.shortcut.ShortcutEntity;
import chigirh.app.utility.app.domain.shortcut.ShortcutGroupEntity;
import chigirh.app.utility.app.domain.shortcut.ShortcutService;
import chigirh.app.utility.javafx.component.shortcut.ShortcutTableRow;
import chigirh.app.utility.javafx.component.shortcut.ShortcutTableRowObject;
import chigirh.app.utility.javafx.component.table.CheckTableColumn;
import chigirh.app.utility.javafx.component.table.TableCell;
import chigirh.app.utility.javafx.component.table.TableColumn;
import chigirh.app.utility.javafx.component.table.TextTableColumn;
import chigirh.app.utility.javafx.presenter.TablePresenterBase;
import javafx.scene.control.Tooltip;
import lombok.RequiredArgsConstructor;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class ShortcutTablePresenter
		extends TablePresenterBase<ShortcutEntity, ShortcutRow, ShortcutTableRow, ShortcutTableRowObject> {

	final ShortcutService shortcutService;

	private ShortcutGroupEntity windowParam = null;

	@Override
	public void setPatameter(Object windowParam) {
		if (windowParam instanceof ShortcutGroupEntity) {
			this.windowParam = (ShortcutGroupEntity) windowParam;
		}
	}

	@Override
	protected int getRowCount() {
		return 15;
	}

	@Override
	public void onStart() {
		super.onStart();
		update();
	}

	@Override
	public void delete() {
		tableRowObjects.stream().map(ShortcutTableRowObject::getVm).filter(ShortcutRow::getIsDeleteChecked)
				.forEach(e -> shortcutService.scDelete(e.getScId()));
	}

	@Override
	protected void clumnDefinition() {

		CheckTableColumn<ShortcutRow> col1 = new CheckTableColumn<>();
		col1.setOrder(1);
		col1.setColumnName("");
		col1.setEditable(true);
		col1.setWidth(25);
		col1.setPropertyFactory(ShortcutRow::isDeleteCheckedProperty);

		TextTableColumn<ShortcutRow> col2 = new TextTableColumn<>();
		col2.setOrder(2);
		col2.setColumnName("タイトル");
		col2.setEditable(true);
		col2.setWidth(250);
		col2.setPropertyFactory(ShortcutRow::scTitleProperty);
		col2.setSortCondition((c1, c2) -> c1.getVm().getScTitle().compareTo(c2.getVm().getScTitle()));

		TextTableColumn<ShortcutRow> col3 = new TextTableColumn<>();
		col3.setOrder(3);
		col3.setColumnName("パス");
		col3.setEditable(true);
		col3.setWidth(350);
		col3.setPropertyFactory(ShortcutRow::scPathProperty);
		col3.setSortCondition((c1, c2) -> c1.getVm().getScPath().compareTo(c2.getVm().getScPath()));

		columns = Arrays.asList(col1, col2, col3);

	}

	@Override
	protected ShortcutTableRow getRow() {
		return new ShortcutTableRow();
	}

	@Override
	protected List<ShortcutEntity> getEntity() {
		return shortcutService.scGet(windowParam.getScGroupId());
	}

	@Override
	protected ShortcutTableRowObject createRowObject(ShortcutEntity entity, ShortcutRow vm) {
		ShortcutTableRowObject rowObject = new ShortcutTableRowObject(vm);
		rowObject.setDisplay(false);
		rowObject.setVisible(true);
		return rowObject;
	}

	@Override
	protected ShortcutRow createVm(ShortcutEntity entity) {
		ShortcutRow vm = new ShortcutRow(entity.getScId());
		vm.setScTitle(entity.getScTitle());
		vm.setScPath(entity.getScPath());
		return vm;
	}

	@Override
	protected void rowUpdate(ShortcutRow vm) {
		ShortcutEntity updateEntity = shortcutService.scUpdate(vm.getKey(), windowParam.getScGroupId(),
				vm.getScTitle(), vm.getScPath());
	}

	@Override
	protected void cellSettings(TableCell<?, ?> cell, TableColumn<ShortcutRow, ?, ?> column, ShortcutEntity entity,
			ShortcutRow vm) {

		if (column.getOrder() == 3) {
			Tooltip tooltip = new Tooltip("aaaaaa");
			tooltip.textProperty().bindBidirectional(vm.scPathProperty());
			cell.setTooltip(tooltip);
		}

	}

}
