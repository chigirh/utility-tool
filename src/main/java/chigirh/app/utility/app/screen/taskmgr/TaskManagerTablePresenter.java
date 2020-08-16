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
import chigirh.app.utility.javafx.component.UtlLabelValueBean;
import chigirh.app.utility.javafx.component.table.CheckTableColumn;
import chigirh.app.utility.javafx.component.table.ChoiceTableColumn;
import chigirh.app.utility.javafx.component.table.TableCell;
import chigirh.app.utility.javafx.component.table.TableColumn;
import chigirh.app.utility.javafx.component.table.TableRow.RowType;
import chigirh.app.utility.javafx.component.table.TextTableColumn;
import chigirh.app.utility.javafx.component.taskmgr.TaskManagerTableRow;
import chigirh.app.utility.javafx.component.taskmgr.TaskManagerTableRowObject;
import chigirh.app.utility.javafx.presenter.TablePresenterBase;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.RequiredArgsConstructor;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class TaskManagerTablePresenter
		extends TablePresenterBase<TaskEntity, TaskRow, TaskManagerTableRow, TaskManagerTableRowObject> {

	private static final String SDF_TIME_PAT = //
			"^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]):([0-1][0-9]|2[0-4]):([0-5][0-9]|60)$";

	private TaskManagerViewModel screenVm;

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
		super.onStart();
		update();
	}

	@Override
	public void delete() {
		tableRowObjects.stream().map(TaskManagerTableRowObject::getVm).filter(TaskRow::getIsDeleteChecked)
				.forEach(e -> taskManagerService.taskDelete(e.getTaskId()));
	}

	@Override
	protected void defaultSort() {
		tableRowObjects.sort((c1, c2) -> c2.getVm().getUpdateDate().compareTo(c1.getVm().getUpdateDate()));
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
		col2.setSortCondition((c1, c2) -> c1.getVm().getSelectedStatus().getValue().getStatusId()
				.compareTo(c2.getVm().getSelectedStatus().getValue().getStatusId()));

		TextTableColumn<TaskRow> col3 = new TextTableColumn<>();
		col3.setOrder(3);
		col3.setColumnName("タスク名");
		col3.setEditable(true);
		col3.setWidth(280);
		col3.setPropertyFactory(TaskRow::taskNameProperty);
		col3.setSortCondition((c1, c2) -> c1.getVm().getTaskName().compareTo(c2.getVm().getTaskName()));

		TextTableColumn<TaskRow> col4 = new TextTableColumn<>();
		col4.setOrder(4);
		col4.setColumnName("期限");
		col4.setEditable(true);
		col4.setWidth(115);
		col4.setPropertyFactory(TaskRow::limitDateProperty);
		col4.setValidator(SDF_TIME_PAT);
		col4.setSortCondition((c1, c2) -> c1.getVm().getLimitDate().compareTo(c2.getVm().getLimitDate()));

		TextTableColumn<TaskRow> col5 = new TextTableColumn<>();
		col5.setOrder(5);
		col5.setColumnName("登録日");
		col5.setEditable(false);
		col5.setWidth(115);
		col5.setPropertyFactory(TaskRow::startDateProperty);
		col5.setSortCondition((c1, c2) -> c1.getVm().getStartDate().compareTo(c2.getVm().getStartDate()));

		TextTableColumn<TaskRow> col6 = new TextTableColumn<>();
		col6.setOrder(6);
		col6.setColumnName("更新日");
		col6.setEditable(false);
		col6.setWidth(115);
		col6.setPropertyFactory(TaskRow::updateDateProperty);
		col6.setSortCondition((c1, c2) -> c1.getVm().getUpdateDate().compareTo(c2.getVm().getUpdateDate()));

		columns = Arrays.asList(col1, col2, col3, col4, col5, col6);

	}

	@Override
	protected void createHeaderBefore() {
		Label label = new Label("");
		label.setPrefWidth(20.0);
		header.getChildren().add(label);
	}

	@Override
	protected int getRowCount() {
		return 10;
	}

	@Override
	protected TaskManagerTableRow getRow() {
		return new TaskManagerTableRow();
	}

	@Override
	protected List<TaskEntity> getEntity() {
		return taskManagerService.taskGet(windowParam.getTaskGroupId());
	}

	@Override
	protected TaskManagerTableRowObject createRowObject(TaskEntity entity, TaskRow vm) {
		TaskManagerTableRowObject rowObject = new TaskManagerTableRowObject(RowType.NORMAL, vm);
		excludeJudge(rowObject);
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
	protected TableCell<?, ?> createCell(TableColumn<TaskRow, ?, ?> column, TaskEntity entity,
			TaskRow vm) {
		TableCell<?, ?> cell = column.cellCreate(vm);
		cell.change(() -> update(vm));
		return cell;
	}

	private void update(TaskRow vm) {
		TaskEntity updateEntity = taskManagerService.taskUpdate(vm.getKey(), windowParam.getTaskGroupId(),
				vm.getTaskName(), vm.getLimitDate(),
				vm.getSelectedStatus().getValue().getStatusId());
		vm.setUpdateDate(longToDateTime(updateEntity.getUpdateDate()));
	}

	public UtlLabelValueBean<TaskStatusEntity> getStatusBean(TaskEntity entity) {
		TaskStatusEntity status = taskManagerService.getStatus(entity.getStatusId());
		return new UtlLabelValueBean<TaskStatusEntity>(status.getStatus(), status);
	}

	void setVm(TaskManagerViewModel vm) {
		this.screenVm = vm;
		vm.notYetCbProperty().addListener(this::excludeChange);
		vm.duringCbProperty().addListener(this::excludeChange);
		vm.rvCbProperty().addListener(this::excludeChange);
		vm.completeCbProperty().addListener(this::excludeChange);
		vm.holdCbProperty().addListener(this::excludeChange);
		vm.unCbProperty().addListener(this::excludeChange);

	}

	private void excludeChange(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		tableRowObjects.parallelStream().forEach(this::excludeJudge);
		redraw();
	}

	private void excludeJudge(TaskManagerTableRowObject rowObject) {
		if ("0".equals(rowObject.getVm().getSelectedStatus().getValue().getStatusId())) {
			rowObject.setVisible(screenVm.getNotYetCb());
		}
		if ("1".equals(rowObject.getVm().getSelectedStatus().getValue().getStatusId())) {
			rowObject.setVisible(screenVm.getDuringCb());
		}
		if ("2".equals(rowObject.getVm().getSelectedStatus().getValue().getStatusId())) {
			rowObject.setVisible(screenVm.getRvCb());
		}
		if ("3".equals(rowObject.getVm().getSelectedStatus().getValue().getStatusId())) {
			rowObject.setVisible(screenVm.getCompleteCb());
		}
		if ("4".equals(rowObject.getVm().getSelectedStatus().getValue().getStatusId())) {
			rowObject.setVisible(screenVm.getHoldCb());
		}
		if ("5".equals(rowObject.getVm().getSelectedStatus().getValue().getStatusId())) {
			rowObject.setVisible(screenVm.getUnCb());
		}
	}
}
