package chigirh.app.utility.app.screen.taskmgr;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import chigirh.app.utility.app.domain.taskmgr.TaskEntity;
import chigirh.app.utility.app.domain.taskmgr.TaskGroupEntity;
import chigirh.app.utility.app.domain.taskmgr.TaskManagerService;
import chigirh.app.utility.app.domain.taskmgr.TaskStatusEntity;
import chigirh.app.utility.javafx.component.CheckTableColumn;
import chigirh.app.utility.javafx.component.ChoiceTableColumn;
import chigirh.app.utility.javafx.component.TableColumn;
import chigirh.app.utility.javafx.component.TableRow.RowType;
import chigirh.app.utility.javafx.component.TextTableColumn;
import chigirh.app.utility.javafx.component.UtlLabelValueBean;
import chigirh.app.utility.javafx.component.UtlTableCell;
import chigirh.app.utility.javafx.component.taskmgr.TaskManagerTableRow;
import chigirh.app.utility.javafx.component.taskmgr.TaskManagerTableRowObject;
import chigirh.app.utility.javafx.presenter.TablePresenterBase;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.RequiredArgsConstructor;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class TaskManagerTablePresenter
		extends TablePresenterBase<TaskEntity, TaskRow, TaskManagerTableRow, TaskManagerTableRowObject> {

	private static final int ROW_COUNT = 10;

	private static final String SDF_TIME_PAT = //
			"^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]):([0-1][0-9]|2[0-4]):([0-5][0-9]|60)$";

	final TaskManagerService taskManagerService;

	private TaskGroupEntity windowParam = null;

	@Override
	public void setPatameter(Object windowParam) {
		if (windowParam instanceof TaskGroupEntity) {
			this.windowParam = (TaskGroupEntity) windowParam;
		}
	}

	@Override
	public void onStart() {

		clumnDefinition();
		Label label = new Label("");
		label.setPrefWidth(20.0);
		header.getChildren().add(label);
		header.getChildren().addAll(columns.stream().map(this::createHeaderCell).collect(Collectors.toList()));
		table.setOnScroll(this::onScroll);

		for (int i = 0; i < ROW_COUNT; i++) {
			TaskManagerTableRow tableRow = new TaskManagerTableRow();
			tableRows.add(tableRow);
		}

		body.getChildren().addAll(tableRows);

		update();

	}

	@Override
	protected void clumnDefinition() {

		new ChoiceBox<TaskEntity>();

		CheckTableColumn<TaskRow> col1 = new CheckTableColumn<>();
		col1.setOrder(1);
		col1.setColumnName("");
		col1.setEditable(true);
		col1.setWidth(20);
		col1.setPropertyFactory(TaskRow::isDeleteCheckedProperty);

		ChoiceTableColumn<TaskRow, TaskStatusEntity> col2 = new ChoiceTableColumn<>();
		col2.setColumnName("ステータス");
		col2.setOrder(2);
		col2.setWidth(80);
		col2.setPropertyFactory(TaskRow::selectedStatusProperty);
		col2.setItemPropertyFactory(TaskRow::statusListPropery);

		TextTableColumn<TaskRow> col3 = new TextTableColumn<>();
		col3.setOrder(3);
		col3.setColumnName("タスク名");
		col3.setEditable(true);
		col3.setWidth(280);
		col3.setPropertyFactory(TaskRow::taskNameProperty);

		TextTableColumn<TaskRow> col4 = new TextTableColumn<>();
		col4.setOrder(4);
		col4.setColumnName("期限");
		col4.setEditable(true);
		col4.setWidth(115);
		col4.setPropertyFactory(TaskRow::limitDateProperty);
		col4.setValidator(SDF_TIME_PAT);

		TextTableColumn<TaskRow> col5 = new TextTableColumn<>();
		col5.setOrder(5);
		col5.setColumnName("登録日");
		col5.setEditable(false);
		col5.setWidth(115);
		col5.setPropertyFactory(TaskRow::startDateProperty);

		TextTableColumn<TaskRow> col6 = new TextTableColumn<>();
		col6.setOrder(6);
		col6.setColumnName("更新日");
		col6.setEditable(false);
		col6.setWidth(115);
		col6.setPropertyFactory(TaskRow::updateDateProperty);

		columns = Arrays.asList(col1, col2, col3, col4, col5, col6);

	}

	public Node createHeaderCell(TableColumn<TaskRow, ?, ?> def) {
		Label cell = new Label();
		cell.setText(def.getColumnName());
		cell.setPrefWidth(def.getWidth());
		return cell;
	}

	protected List<TaskEntity> getEntity() {
		return taskManagerService.taskGet(windowParam.getTaskGroupId());
	}

	@Override
	protected TaskManagerTableRowObject createRowObject(TaskEntity entity, TaskRow vm) {
		TaskManagerTableRowObject rowObject = new TaskManagerTableRowObject(RowType.NORMAL, vm);
		rowObject.setVisible(true);
		return rowObject;
	}

	@Override
	protected TaskRow createVm(TaskEntity entity) {
		TaskRow vm = new TaskRow(entity.getTaskId());
		vm.setStatusPropertyList(taskManagerService.getStatus().stream()
				.map(e -> new UtlLabelValueBean<>(e.getStatus(), e)).collect(Collectors.toList()));
		vm.setTaskName(entity.getTaskName());
		vm.setSelectedStatus(getStatusBean(entity));
		vm.setLimitDate(longToDateTime(entity.getLimitDate()));
		vm.setStartDate(longToDateTime(entity.getStartDate()));
		vm.setUpdateDate(longToDateTime(entity.getUpdateDate()));
		return vm;
	};

	@Override
	protected HBox createRow(TaskEntity entity, TaskRow vm) {
		HBox row = new HBox();
		row.getChildren().addAll(columns.stream().map(e -> createCell(e, entity, vm)).collect(Collectors.toList()));
		vm.setStatusPropertyList(taskManagerService.getStatus().stream()
				.map(e -> new UtlLabelValueBean<>(e.getStatus(), e)).collect(Collectors.toList()));
		return row;
	}

	@Override
	protected UtlTableCell<?, ?> createCell(TableColumn<TaskRow, ?, ?> column, TaskEntity entity,
			TaskRow vm) {
		UtlTableCell<?, ?> cell = column.cellCreate(vm);
		cell.change(() -> update(vm));
		return cell;
	}

	private void update(TaskRow vm) {
		TaskEntity updateEntity = taskManagerService.taskUpdate(vm.getKey(), vm.getTaskName(), vm.getLimitDate(),
				vm.getSelectedStatus().getValue().getStatusId());
		vm.setUpdateDate(longToDateTime(updateEntity.getUpdateDate()));
	}

	public UtlLabelValueBean<TaskStatusEntity> getStatusBean(TaskEntity entity) {
		TaskStatusEntity status = taskManagerService.getStatus(entity.getStatusId());
		return new UtlLabelValueBean<TaskStatusEntity>(status.getStatus(), status);
	}

}
