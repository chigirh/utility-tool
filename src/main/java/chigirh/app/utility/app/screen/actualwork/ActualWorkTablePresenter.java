package chigirh.app.utility.app.screen.actualwork;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import chigirh.app.utility.app.domain.actualwork.ActualWorkClassifcation1Entity;
import chigirh.app.utility.app.domain.actualwork.ActualWorkClassifcation2Entity;
import chigirh.app.utility.app.domain.actualwork.ActualWorkClassifcationService;
import chigirh.app.utility.app.domain.actualwork.ActualWorkEntity;
import chigirh.app.utility.app.domain.actualwork.ActualWorkGroupEntity;
import chigirh.app.utility.app.domain.actualwork.ActualWorkService;
import chigirh.app.utility.app.domain.actualwork.ActualWorkTaskEntity;
import chigirh.app.utility.javafx.component.CheckTableColumn;
import chigirh.app.utility.javafx.component.ChoiceTableColumn;
import chigirh.app.utility.javafx.component.TableColumn;
import chigirh.app.utility.javafx.component.TableRow.RowType;
import chigirh.app.utility.javafx.component.TextTableColumn;
import chigirh.app.utility.javafx.component.UtlLabelValueBean;
import chigirh.app.utility.javafx.component.UtlTableCell;
import chigirh.app.utility.javafx.component.actualwork.ActualWorkTableRow;
import chigirh.app.utility.javafx.component.actualwork.ActualWorkTableRowObject;
import chigirh.app.utility.javafx.presenter.TablePresenterBase;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.RequiredArgsConstructor;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class ActualWorkTablePresenter
		extends TablePresenterBase<ActualWorkEntity, ActualWorkRow, ActualWorkTableRow, ActualWorkTableRowObject> {

	private static final int ROW_COUNT = 10;

	private static final String AW_DATE_PAT = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

	private static final String AW_TIME_PAT = "^[0-9].[0-9]{1,2}$";

	final ActualWorkService actualWorkService;

	final ActualWorkClassifcationService classifcationService;

	private ActualWorkGroupEntity windowParam = null;

	private List<TableColumn<ActualWorkTaskRow, ?, ?>> awTaskColumns;

	private final Map<String, ActualWorkRow> awRowMap = new HashMap<>();

	private final Map<String, ActualWorkTaskRow> awTaskRowMap = new HashMap<>();

	@Override
	public void setPatameter(Object windowParam) {
		if (windowParam instanceof ActualWorkGroupEntity) {
			this.windowParam = (ActualWorkGroupEntity) windowParam;
		}
	}

	@Override
	public void onStart() {

		clumnDefinition();

		table.setOnScroll(this::onScroll);

		Label headerLeftLabel = new Label("");
		headerLeftLabel.setPrefWidth(30.0);
		header.getChildren().add(headerLeftLabel);
		header.getChildren().addAll(awTaskColumns.stream().map(this::createHeaderCell).collect(Collectors.toList()));

		for (int i = 0; i < ROW_COUNT; i++) {
			ActualWorkTableRow tableRow = new ActualWorkTableRow(this::redraw);
			tableRows.add(tableRow);
		}

		body.getChildren().addAll(tableRows);
	}

	@Override
	protected void clumnDefinition() {
		CheckTableColumn<ActualWorkRow> parentCol1 = new CheckTableColumn<>();
		parentCol1.setOrder(1);
		parentCol1.setColumnName("");
		parentCol1.setEditable(false);
		parentCol1.setWidth(20);
		parentCol1.setPropertyFactory(ActualWorkRow::isDeleteCheckedProperty);

		TextTableColumn<ActualWorkRow> parentCol2 = new TextTableColumn<>();
		parentCol2.setOrder(2);
		parentCol2.setColumnName("");
		parentCol2.setEditable(true);
		parentCol2.setWidth(120);
		parentCol2.setPropertyFactory(ActualWorkRow::actualWorkDateProperty);
		parentCol2.setValidator(AW_DATE_PAT);

		TextTableColumn<ActualWorkRow> parentCol3 = new TextTableColumn<>();
		parentCol3.setOrder(3);
		parentCol3.setColumnName("");
		parentCol3.setEditable(false);
		parentCol3.setWidth(50);
		parentCol3.setPropertyFactory(ActualWorkRow::actualWorkTimeProperty);

		columns = Arrays.asList(parentCol1, parentCol2, parentCol3);

		CheckTableColumn<ActualWorkTaskRow> childCol1 = new CheckTableColumn<>();
		childCol1.setOrder(1);
		childCol1.setColumnName("");
		childCol1.setEditable(true);
		childCol1.setWidth(20);
		childCol1.setPropertyFactory(ActualWorkTaskRow::isDeleteCheckedProperty);

		ChoiceTableColumn<ActualWorkTaskRow, ActualWorkClassifcation1Entity> childCol2 = new ChoiceTableColumn<>();
		childCol2.setOrder(2);
		childCol2.setColumnName("分類1");
		childCol2.setWidth(100);
		childCol2.setPropertyFactory(ActualWorkTaskRow::selectedClassification1Property);
		childCol2.setItemPropertyFactory(ActualWorkTaskRow::classification1ListPropery);

		ChoiceTableColumn<ActualWorkTaskRow, ActualWorkClassifcation2Entity> childCol3 = new ChoiceTableColumn<>();
		childCol3.setOrder(3);
		childCol3.setColumnName("分類2");
		childCol3.setWidth(100);
		childCol3.setPropertyFactory(ActualWorkTaskRow::selectedClassification2Property);
		childCol3.setItemPropertyFactory(ActualWorkTaskRow::classification2ListPropery);

		TextTableColumn<ActualWorkTaskRow> childCol4 = new TextTableColumn<>();
		childCol4.setOrder(4);
		childCol4.setColumnName("タスク名");
		childCol4.setEditable(true);
		childCol4.setWidth(300);
		childCol4.setPropertyFactory(ActualWorkTaskRow::taskNameProperty);

		TextTableColumn<ActualWorkTaskRow> childCol5 = new TextTableColumn<>();
		childCol5.setOrder(5);
		childCol5.setColumnName("時間");
		childCol5.setEditable(true);
		childCol5.setWidth(50);
		childCol5.setPropertyFactory(ActualWorkTaskRow::taskTimeProperty);
		childCol5.setValidator(AW_TIME_PAT);

		awTaskColumns = Arrays.asList(childCol1, childCol2, childCol3, childCol4, childCol5);
	}

	public Node createHeaderCell(TableColumn<ActualWorkTaskRow, ?, ?> def) {
		Label cell = new Label();
		cell.setText(def.getColumnName());
		cell.setPrefWidth(def.getWidth());
		return cell;
	}

	public void delete() {

		awRowMap.values().stream()//
				.filter(this::judgeAndDelete)//
				.flatMap(e -> e.getAwTasks().stream())//
				.filter(ActualWorkTaskRow::getIsDeleteChecked)//
				.forEach(e -> actualWorkService.awTaskDelete(e.getActualWorkId(), Integer.parseInt(e.getSerial())));

		update();
	}

	private boolean judgeAndDelete(ActualWorkRow row) {
		if (row.getIsDeleteChecked()) {
			actualWorkService.awDelete(row.getActualWorkId());
			return false;
		}
		return true;
	}

	@Override
	public void redraw() {
		tableRows.forEach(ActualWorkTableRow::clear);
		if (head == null) {
			return;
		}
		ActualWorkTableRowObject sequence = head;

		for (ActualWorkTableRow tableRow : tableRows) {

			while (sequence != null) {
				if (sequence.isVisible()) {
					tableRow.set(sequence);
					sequence.setDisplay(true);
					tail = sequence;
					sequence = sequence.next();
					break;
				}
				sequence.setDisplay(false);
				sequence = sequence.next();

			}

		}
	}

	@Override
	protected void createRow(ActualWorkEntity entity) {

		ActualWorkRow vm = createVm(entity);
		ActualWorkTableRowObject tableRowObject = createRowObject(entity, vm);

		if (last == null) {
			first = tableRowObject;
			last = tableRowObject;
		} else {
			tableRowObject.setPrev(last);
			last.setNext(tableRowObject);
			last = tableRowObject;
		}

		tableRowObject.getChildren()
				.addAll(actualWorkService.awTaskGet(entity.getAwId()).stream()
						.map(e -> createChildRowObject(e, tableRowObject, vm))
						.collect(Collectors.toList()));
	}

	protected HBox createRow(ActualWorkEntity entity, ActualWorkTaskRow vm) {
		//使用しないので空実装で潰す
		return null;
	}

	private HBox createRow(ActualWorkEntity entity, ActualWorkTableRowObject parent, ActualWorkRow vm) {
		HBox row = new HBox();
		row.getChildren()
				.addAll(columns.stream().map(e -> createCell(e, entity, vm)).collect(Collectors.toList()));

		parent.setAddTask(() -> addTask(parent, vm));

		return row;
	}

	private void addTask(ActualWorkTableRowObject parent, ActualWorkRow vm) {

		final String parenatAwId = vm.getActualWorkId();

		ActualWorkTaskEntity addEntity = actualWorkService.awTaskAdd(parenatAwId);
		createChildRowObject(addEntity, parent, vm);

		redraw();
	}

	@Override
	protected UtlTableCell<?, ?> createCell(TableColumn<ActualWorkRow, ?, ?> columnDef,
			ActualWorkEntity entity, ActualWorkRow vm) {
		UtlTableCell<?, ?> cell = columnDef.cellCreate(vm);

		cell.change(() -> awUpdate(entity, vm));

		return cell;
	}

	private void awUpdate(ActualWorkEntity entity, ActualWorkRow vm) {
		try {
			actualWorkService.awUpdate(entity, vm.getActualWorkDate());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public ActualWorkTableRowObject createChildRowObject(ActualWorkTaskEntity entity, ActualWorkTableRowObject paret,
			ActualWorkRow pVm) {
		ActualWorkTableRowObject tableRowObject = new ActualWorkTableRowObject(RowType.CHILD);
		ActualWorkTaskRow cVm = createVm(entity);
		awTaskRowMap.put(cVm.getKey(), cVm);

		paret.getChildren().add(tableRowObject);
		pVm.getAwTasks().add(cVm);

		tableRowObject.setRowFactory(() -> createChildRow(entity, cVm));
		tableRowObject.setExpanted(paret.isExpanted());
		tableRowObject.setVisible(paret.isExpanted());
		tableRowObject.setDisplay(false);
		if (paret.isLast()) {
			tableRowObject.setPrev(last);
			last.setNext(tableRowObject);
			last = tableRowObject;
		} else {
			tableRowObject.setPrev(paret);
			tableRowObject.setNext(paret.next());
			paret.setNext(tableRowObject);
			tableRowObject.next().setPrev(tableRowObject);
		}

		return tableRowObject;
	}

	public HBox createChildRow(ActualWorkTaskEntity entity, ActualWorkTaskRow vm) {
		HBox row = new HBox();
		row.getChildren()
				.addAll(awTaskColumns.stream().map(e -> createChildCell(e, entity, vm)).collect(Collectors.toList()));
		return row;
	}

	public UtlTableCell<?, ?> createChildCell(TableColumn<ActualWorkTaskRow, ?, ?> columnDef,
			ActualWorkTaskEntity entity, ActualWorkTaskRow vm) {
		UtlTableCell<?, ?> cell = columnDef.cellCreate(vm);

		cell.change(() -> awTaskUpdate(entity, vm));

		return cell;
	}

	private void awTaskUpdate(ActualWorkTaskEntity entity, ActualWorkTaskRow vm) {

		actualWorkService.awTaskUpdate(entity, //
				vm.getSelectedClassification1() == null ? null : vm.getSelectedClassification1().getValue().getId(), //
				vm.getSelectedClassification2() == null ? null : vm.getSelectedClassification2().getValue().getId(), //
				vm.getTaskName(), vm.getTaskTime());

		final String awId = vm.getActualWorkId();

		ActualWorkRow pVm = awRowMap.get(awId);
		pVm.setActualWorkTime(String.valueOf(actualWorkService.awTaskTimeSumGet(awId)));

	}

	@Override
	protected List<ActualWorkEntity> getEntity() {
		return actualWorkService.awGet(windowParam.getAwGroupId()).stream()
				.sorted((c1, c2) -> c1.getAwDate().compareTo(c2.getAwDate())).collect(Collectors.toList());
	}

	@Override
	protected ActualWorkTableRowObject createRowObject(ActualWorkEntity entity, ActualWorkRow vm) {
		ActualWorkTableRowObject tableRowObject = new ActualWorkTableRowObject(RowType.PARENT);
		tableRowObject.setRowFactory(() -> createRow(entity, tableRowObject, vm));
		tableRowObject.setExpanted(false);
		tableRowObject.setVisible(true);
		tableRowObject.setDisplay(false);
		return tableRowObject;
	}

	@Override
	protected ActualWorkRow createVm(ActualWorkEntity entity) {
		ActualWorkRow vm = new ActualWorkRow(entity.getAwId());
		vm.setActualWorkDate(longToDate(entity.getAwDate()));
		vm.setActualWorkTime(String
				.valueOf(actualWorkService.awTaskGet(entity.getAwId()).stream()
						.mapToDouble(ActualWorkTaskEntity::getTaskTime).sum()));
		awRowMap.put(vm.getKey(), vm);
		return vm;
	}

	protected ActualWorkTaskRow createVm(ActualWorkTaskEntity entity) {
		ActualWorkTaskRow vm = new ActualWorkTaskRow(entity.getAwId(), String.valueOf(entity.getSerial()));

		vm.setTaskName(entity.getTaskName());

		vm.setClassification1PropertyList(classifcationService.classifcation1Get().stream()
				.map(this::classifcation1BeanMappr).collect(Collectors.toList()));
		vm.setSelectedClassification1(
				classifcation1BeanMappr(classifcationService.classifcation1Get(entity.getClassification1())));

		setClassification2ListUpdate(vm);
		vm.setSelectedClassification2(
				classifcation2BeanMappr(classifcationService.classifcation2Get(entity.getClassification2())));

		vm.setTaskTime(String.valueOf(entity.getTaskTime()));

		vm.selectedClassification1Property().addListener((ob, ov, nv) -> setClassification2ListUpdate(vm));

		return vm;
	}

	private void setClassification2ListUpdate(ActualWorkTaskRow vm) {
		vm.setSelectedClassification2(null);
		if (vm.getSelectedClassification1() == null) {
			vm.setClassification2PropertyList(new ArrayList<>());
			return;
		}

		vm.setClassification2PropertyList(
				classifcationService.findByclassifcation1(vm.getSelectedClassification1().getValue().getId())
						.stream().map(this::classifcation2BeanMappr).collect(Collectors.toList()));
	}

	private UtlLabelValueBean<ActualWorkClassifcation1Entity> classifcation1BeanMappr(
			ActualWorkClassifcation1Entity entity) {
		return entity == null ? null : new UtlLabelValueBean<>(entity.getName(), entity);
	}

	private UtlLabelValueBean<ActualWorkClassifcation2Entity> classifcation2BeanMappr(
			ActualWorkClassifcation2Entity entity) {
		return entity == null ? null : new UtlLabelValueBean<>(entity.getName(), entity);
	}
}
