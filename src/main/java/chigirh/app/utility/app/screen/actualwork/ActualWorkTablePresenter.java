package chigirh.app.utility.app.screen.actualwork;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import chigirh.app.utility.app.domain.actualwork.ActualWorkEntity;
import chigirh.app.utility.app.domain.actualwork.ActualWorkGroupEntity;
import chigirh.app.utility.app.domain.actualwork.ActualWorkService;
import chigirh.app.utility.app.domain.actualwork.ActualWorkTaskEntity;
import chigirh.app.utility.javafx.component.CheckTableColumn;
import chigirh.app.utility.javafx.component.TableCell;
import chigirh.app.utility.javafx.component.TableColumn;
import chigirh.app.utility.javafx.component.TableRow.RowType;
import chigirh.app.utility.javafx.component.TextTableColumn;
import chigirh.app.utility.javafx.component.actualwork.ActualWorkTableRow;
import chigirh.app.utility.javafx.component.actualwork.ActualWorkTableRowObject;
import chigirh.app.utility.javafx.presenter.PresenterBase;
import chigirh.app.utility.javafx.util.JavaFxTextFieldUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class ActualWorkTablePresenter extends PresenterBase {

	private static final String STYLE_CLASS = ".table";

	private static final int ROW_COUNT = 10;

	private static final String AW_DATE_PAT = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

	private static final String AW_TIME_PAT = "^[0-9].[0-9]{1,2}$";

	final ActualWorkService actualWorkService;

	@FXML
	private VBox table;

	@FXML
	private HBox header;

	@FXML
	private VBox body;

	private ActualWorkGroupEntity windowParam = null;

	private List<TableColumn<ActualWorkEntity, ActualWorkRow, ?, ?>> awColumns;

	private List<TableColumn<ActualWorkTaskEntity, ActualWorkTaskRow, ?, ?>> awTaskColumns;

	private final List<ActualWorkTableRow> tableRows = new ArrayList<>();

	private final Map<String, ActualWorkRow> awRowMap = new HashMap<>();

	private final Map<String, ActualWorkTaskRow> awTaskRowMap = new HashMap<>();

	/** 現在見えているTableRowObjectの先頭 */
	private ActualWorkTableRowObject head = null;

	/** 現在見えているTableRowObjectの最後 */
	private ActualWorkTableRowObject tail = null;

	/** 全てのTableRowObjectの先頭 */
	private ActualWorkTableRowObject first = null;

	/** 全てのTableRowObjectの最後 */
	private ActualWorkTableRowObject last = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		table.getStyleClass().add(STYLE_CLASS);
	}

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

	private void clumnDefinition() {
		CheckTableColumn<ActualWorkEntity, ActualWorkRow> parentCol1 = new CheckTableColumn<>();
		parentCol1.setOrder(1);
		parentCol1.setColumnName("");
		parentCol1.setEditable(false);
		parentCol1.setWidth(30);
		parentCol1.setCellFactory(e -> false);
		parentCol1.setPropertyFactory(ActualWorkRow::isDeleteCheckedProperty);
		parentCol1.setValueSetter(ActualWorkRow::setIsDeleteChecked);

		TextTableColumn<ActualWorkEntity, ActualWorkRow> parentCol2 = new TextTableColumn<>();
		parentCol2.setOrder(2);
		parentCol2.setColumnName("");
		parentCol2.setEditable(true);
		parentCol2.setWidth(300);
		parentCol2.setCellFactory(e -> longToDate(e.getAwDate()));
		parentCol2.setPropertyFactory(ActualWorkRow::actualWorkDateProperty);
		parentCol2.setValueSetter(ActualWorkRow::setActualWorkDate);
		parentCol2.setValidator(e -> JavaFxTextFieldUtils.inputCheck(e, AW_DATE_PAT));

		TextTableColumn<ActualWorkEntity, ActualWorkRow> parentCol3 = new TextTableColumn<>();
		parentCol3.setOrder(3);
		parentCol3.setColumnName("");
		parentCol3.setEditable(false);
		parentCol3.setWidth(50);
		parentCol3.setCellFactory(e -> String
				.valueOf(actualWorkService.awTaskGet(e.getAwId()).stream()
						.mapToDouble(ActualWorkTaskEntity::getTaskTime).sum()));
		parentCol3.setPropertyFactory(ActualWorkRow::actualWorkTimeProperty);
		parentCol3.setValueSetter(ActualWorkRow::setActualWorkTime);

		awColumns = Arrays.asList(parentCol1, parentCol2, parentCol3);

		CheckTableColumn<ActualWorkTaskEntity, ActualWorkTaskRow> childCol1 = new CheckTableColumn<>();
		childCol1.setOrder(1);
		childCol1.setColumnName("");
		childCol1.setEditable(true);
		childCol1.setWidth(30);
		childCol1.setCellFactory(e -> false);
		childCol1.setPropertyFactory(ActualWorkTaskRow::isDeleteCheckedProperty);
		childCol1.setValueSetter(ActualWorkTaskRow::setIsDeleteChecked);

		TextTableColumn<ActualWorkTaskEntity, ActualWorkTaskRow> childCol2 = new TextTableColumn<>();
		childCol2.setOrder(2);
		childCol2.setColumnName("分類1");
		childCol2.setEditable(false);
		childCol2.setWidth(50);
		childCol2.setCellFactory(e -> "分類2");
		childCol2.setPropertyFactory(ActualWorkTaskRow::classification1Property);
		childCol2.setValueSetter(ActualWorkTaskRow::setClassification1);

		TextTableColumn<ActualWorkTaskEntity, ActualWorkTaskRow> childCol3 = new TextTableColumn<>();
		childCol3.setOrder(3);
		childCol3.setColumnName("分類2");
		childCol3.setEditable(false);
		childCol3.setWidth(50);
		childCol3.setCellFactory(e -> "分類2");
		childCol3.setPropertyFactory(ActualWorkTaskRow::classification2Property);
		childCol3.setValueSetter(ActualWorkTaskRow::setClassification2);

		TextTableColumn<ActualWorkTaskEntity, ActualWorkTaskRow> childCol4 = new TextTableColumn<>();
		childCol4.setOrder(4);
		childCol4.setColumnName("タスク名");
		childCol4.setEditable(true);
		childCol4.setWidth(300);
		childCol4.setCellFactory(ActualWorkTaskEntity::getTaskName);
		childCol4.setPropertyFactory(ActualWorkTaskRow::taskNameProperty);
		childCol4.setValueSetter(ActualWorkTaskRow::setTaskName);

		TextTableColumn<ActualWorkTaskEntity, ActualWorkTaskRow> childCol5 = new TextTableColumn<>();
		childCol5.setOrder(5);
		childCol5.setColumnName("時間");
		childCol5.setEditable(true);
		childCol5.setWidth(50);
		childCol5.setCellFactory(e -> String.valueOf(e.getTaskTime()));
		childCol5.setPropertyFactory(ActualWorkTaskRow::taskTimeProperty);
		childCol5.setValueSetter(ActualWorkTaskRow::setTaskTime);
		childCol5.setValidator(e -> JavaFxTextFieldUtils.inputCheck(e, AW_TIME_PAT));

		awTaskColumns = Arrays.asList(childCol1, /*childCol2, childCol3, */childCol4, childCol5);
	}

	private String longToDate(Long longDate) {
		if (longDate == null) {
			return StringUtils.EMPTY;
		}
		Date date = new Date(longDate);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public Node createHeaderCell(TableColumn<ActualWorkTaskEntity, ActualWorkTaskRow, ?, ?> def) {
		Label cell = new Label();
		cell.setText(def.getColumnName());
		cell.setPrefWidth(def.getWidth());
		return cell;
	}

	public void update() {
		List<ActualWorkEntity> awAll = actualWorkService.awGet(windowParam.getAwGroupId());
		first = null;
		last = null;
		awAll.stream().forEach(this::createParentRowObject);
		head = first;
		redraw();
	}

	public void delete() {

		awRowMap.values().stream()//
		.filter(this::judgeAndDelete)//
		.flatMap(e -> e.getAwTasks().stream())//
		.filter(ActualWorkTaskRow::getIsDeleteChecked)//
		.forEach(e -> actualWorkService.awTaslDelete(e.getActualWorkId(), Integer.parseInt(e.getSerial())));

		update();
	}

	private boolean judgeAndDelete(ActualWorkRow row) {
		if(row.getIsDeleteChecked()) {
			actualWorkService.awDelete(row.getActualWorkId());
			return false;
		}
		return true;
	}

	public void redraw() {
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
			if (sequence == null) {
				tableRow.clear();
			}

		}
	}

	private void createParentRowObject(ActualWorkEntity entity) {
		ActualWorkTableRowObject tableRowObject = new ActualWorkTableRowObject(RowType.PARENT);

		ActualWorkRow vm = new ActualWorkRow(entity.getAwId());
		awRowMap.put(vm.getKey(), vm);

		tableRowObject.setRowFactory(() -> createParentRow(entity, tableRowObject, vm));
		tableRowObject.setExpanted(false);
		tableRowObject.setVisible(true);
		tableRowObject.setDisplay(false);
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

	private HBox createParentRow(ActualWorkEntity entity, ActualWorkTableRowObject parent, ActualWorkRow vm) {
		HBox row = new HBox();
		row.getChildren()
				.addAll(awColumns.stream().map(e -> createTableCell(e, entity, vm)).collect(Collectors.toList()));

		parent.setAddTask(()-> addTask(parent,vm));

		return row;
	}

	private void addTask(ActualWorkTableRowObject parent, ActualWorkRow vm) {

		final String parenatAwId = vm.getActualWorkId();

		ActualWorkTaskEntity addEntity = actualWorkService.awTaskAdd(parenatAwId);
		createChildRowObject(addEntity, parent, vm);

		redraw();
	}

	private TableCell<?, ?> createTableCell(TableColumn<ActualWorkEntity, ActualWorkRow, ?, ?> columnDef,
			ActualWorkEntity entity, ActualWorkRow vm) {
		TableCell<?, ?> cell = columnDef.cellCreate(entity, vm);

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
		ActualWorkTaskRow cVm = new ActualWorkTaskRow(entity.getAwId(), String.valueOf(entity.getSerial()));
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

	public TableCell<?, ?> createChildCell(TableColumn<ActualWorkTaskEntity, ActualWorkTaskRow, ?, ?> columnDef,
			ActualWorkTaskEntity entity, ActualWorkTaskRow vm) {
		TableCell<?, ?> cell = columnDef.cellCreate(entity, vm);

		cell.change(() -> awTaskUpdate(entity, vm));

		return cell;
	}

	private void awTaskUpdate(ActualWorkTaskEntity entity, ActualWorkTaskRow vm) {
		actualWorkService.awTaskUpdate(entity, vm.getTaskName(), vm.getTaskTime());

		final String awId = vm.getActualWorkId();

		ActualWorkRow parentRow = awRowMap.get(awId);
		parentRow.setActualWorkTime(String.valueOf(actualWorkService.awTaskTimeSumGet(awId)));

	}



	/*
	 * スクロール処理ここから
	 */

	private void onScroll(ScrollEvent e) {

		double deltaY = e.getDeltaY();

		if (deltaY < 0) {
			scrollDown();
		}
		if (0 < deltaY) {
			scrollUp();
		}

		redraw();

	}

	private void scrollUp() {

		ActualWorkTableRowObject sequence = head.prev();

		while (sequence != null) {
			if (sequence.isVisible()) {
				head = sequence;
				break;
			}
			sequence = sequence.prev();
		}
	}

	private void scrollDown() {

		ActualWorkTableRowObject tailSequence = tail.next();

		while (tailSequence != null) {
			if (tailSequence.isVisible()) {
				ActualWorkTableRowObject headSequence = head.next();
				while (headSequence != null) {
					if (headSequence.isVisible()) {
						head = headSequence;
						break;
					}
					headSequence = headSequence.next();
				}
				break;
			}
			tailSequence = tailSequence.prev();
		}
	}

	/*
	 * スクロール処理ここまで
	 */

}
