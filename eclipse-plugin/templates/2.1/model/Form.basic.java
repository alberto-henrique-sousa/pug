/*******************************************************************************
 * Generator by Pug Framework %version%
 *******************************************************************************/
package %formPackage%;

import java.util.ArrayList;
import java.util.Date;

import %clientPackage%.Resources;
import %clientPackage%.Theme;
import %clientPackage%.dto.%classDTO%;
import %clientPackage%.dto.ResultSaveDTO;
import %clientPackage%.service.PathServices;
import %clientPackage%.service.%classDAO%Service;
import %clientPackage%.service.%classDAO%ServiceAsync;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
%imports%
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.LongBox;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.pugsource.gwt.library.client.ConfigFormPug;
import com.pugsource.gwt.library.client.Tools;
import com.pugsource.gwt.library.client.grids.ButtonImageCellPug;
import com.pugsource.gwt.library.client.grids.PaginationPug;
import com.pugsource.gwt.library.client.popup.DialogBoxPug;
import com.pugsource.gwt.library.client.popup.MessageBoxConfirmePug;
import com.pugsource.gwt.library.client.popup.MessageBoxErrorPug;
import com.pugsource.gwt.library.client.popup.MessageProgressPug;
import com.pugsource.gwt.library.client.ui.ButtonPug;
import com.pugsource.gwt.library.client.ui.Formatter;
import com.pugsource.gwt.library.client.ui.MaskDecimalTextBox;
import com.pugsource.gwt.library.client.ui.MaskIntegerTextBox;
import com.pugsource.gwt.library.client.ui.MaskTextBox;
import com.pugsource.gwt.library.client.ui.RichTextPug;

public class %crudName% extends Composite {

	private ConfigFormPug configFormPug = new ConfigFormPug();
	private final Resources Resources = GWT.create(Resources.class);
	private DataGrid.Resources dataGridResources = Theme.dataGridDefault();	
	private static final Binder binder = GWT.create(Binder.class);
	private %classDTO% %instanceDTO%Edit;
	private DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
	private ArrayList<%classDTO%> list%classDAO%;	
	private final SelectionModel<%classDTO%> selectionModel%classDTO% = new MultiSelectionModel<%classDTO%>();
	private PaginationPug pagination;
	private String title;
	private DialogBoxPug dialogBoxPug;
	private Integer rowUpdate;
	public static %classDTO% %instanceDTO%Selected;
	@UiField HorizontalPanel horizontalPanelAdd;
	@UiField VerticalPanel verticalPanelEdit;
	@UiField VerticalPanel verticalPanelGrid;
	@UiField VerticalPanel verticalPanelTitle;
	@UiField Label titleForm;
	@UiField(provided=true) DataGrid<%classDTO%> dataGrid = new DataGrid<%classDTO%>(1000, dataGridResources);
	@UiField Label lblOrderSearch;
	@UiField Label lblAdd;
	@UiField ButtonPug btnGravar;
	@UiField ButtonPug btnCancelar;
	@UiField ButtonPug bntPesquisar;
	@UiField Button btnRefresh;
	@UiField Button btnGridPrimeiro;
	@UiField Button btnGridAnterior;
	@UiField Button btnGridProximo;
	@UiField Button btnGridUltimo;
	@UiField ListBox comboBoxGridPaginas;
	@UiField VerticalPanel verticalPanelSearch;
	@UiField ListBox comboBoxOrder;
	@UiField CheckBox checkBoxOrder;
	@UiField HorizontalPanel horizontalPanelPagination;
%fieldsDeclare%	

	interface Binder extends UiBinder<Widget, %crudName%> {
	}

	@Override
	protected void initWidget(Widget widget) {
		super.initWidget(widget);
		
		if (this.configFormPug.isGrid()) {
			if (this.configFormPug.isDelete())
				dataGrid.setSelectionModel(selectionModel%classDTO%, DefaultSelectionEventManager
						.<%classDTO%> createCheckboxManager(0));							
			addColumns();
		}
	}

	public %crudName%() {
		init(null, null);
	}
	
	public %crudName%(ConfigFormPug configFormPug) {
		init(null, configFormPug);
	}

	public %crudName%(%classDTO% %instanceDTO%Edit) {
		init(%instanceDTO%Edit, null);
	}
	
	public %crudName%(%classDTO% %instanceDTO%Edit, ConfigFormPug configFormPug) {
		init(%instanceDTO%Edit, configFormPug);
	}		
	
	private void init(%classDTO% %instanceDTO%Edit, ConfigFormPug configFormPug) {
		if (configFormPug != null)
			this.configFormPug = configFormPug;
		initWidget(binder.createAndBindUi(this));
		
		History.newItem("%classDTO%", false);
		
		pagination = new PaginationPug(10);
	
		this.%instanceDTO%Edit = %instanceDTO%Edit;
		this.rowUpdate = null;
		
%fieldsOrder%						
		verticalPanelEdit.setVisible(false);
		verticalPanelSearch.setVisible(false);
		horizontalPanelPagination.setVisible(this.configFormPug.isPagination());
		
		verticalPanelTitle.setVisible(this.configFormPug.isTitle());
		title = "%title%";
		titleForm.setText(title);		
		
		if (!this.configFormPug.isForm())
			this.configFormPug.setAdd(false);		
		if (!this.configFormPug.isOrder() && ! this.configFormPug.isAdd())
			horizontalPanelAdd.setVisible(false);
		verticalPanelGrid.setVisible(this.configFormPug.isGrid());
		lblAdd.setVisible(this.configFormPug.isAdd());
		if (this.configFormPug.isGrid()) {		
			callList%classDTO%();
		} else {
			this.configFormPug.setOrder(false);
			this.configFormPug.setAdd(false);
			horizontalPanelAdd.setVisible(false);
			verticalPanelEdit.setVisible(this.configFormPug.isForm());
		}
		lblOrderSearch.setVisible(this.configFormPug.isOrder());
		lblAdd.setVisible(this.configFormPug.isAdd());
		if (!this.configFormPug.isOrder() && !this.configFormPug.isAdd())
			horizontalPanelAdd.setVisible(false);
	}

	@UiHandler("lblOrderSearch")
	void onLblOrderSearchClick(ClickEvent event) {
		verticalPanelSearch.setVisible(!verticalPanelSearch.isVisible());
		lblOrderSearch.setText(!verticalPanelSearch.isVisible() ? "Ordenar e Pesquisar" : "Ordenar e Pesquisar (Ocultar)");
	}
	
	@UiHandler("lblAdd")
	void onLblAddClick(ClickEvent event) {
		callAdd%classDTO%();
	}
	
	@UiHandler("btnGravar")
	void onBtnGravarClick(ClickEvent event) {
		callBtnSave();
	}
	
	@UiHandler("btnGravar")
	void onBtnGravarKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			callBtnSave();
		}
	}	
	
	@UiHandler("btnCancelar")
	void onBtnCancelarClick(ClickEvent event) {
		callBtnCancel();
	}
	
	@UiHandler("btnCancelar")
	void onBtnCancelarKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			callBtnCancel();
		}
	}	
	
	@UiHandler("bntPesquisar")
	void onBntPesquisarClick(ClickEvent event) {
		callBtnFind();
	}
	
	@UiHandler("bntPesquisar")
	void onBtnPesquisarKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			callBtnFind();
		}
	}		
	
	@UiHandler("comboBoxGridPaginas")
	void onComboBoxGridPaginasChange(ChangeEvent event) {
		if (pagination.goToPage(comboBoxGridPaginas.getSelectedIndex()+1))
			callList%classDTO%();				
	}
	
	@UiHandler("btnRefresh")
	void onBtnRefreshClick(ClickEvent event) {
		callList%classDTO%();
	}		
	
	@UiHandler("btnGridPrimeiro")
	void onBtnGridPrimeiroClick(ClickEvent event) {
		if (pagination.firstPage())
			callList%classDTO%();
	}
	
	@UiHandler("btnGridAnterior")
	void onBtnGridAnteriorClick(ClickEvent event) {
		if (pagination.priorPage())
			callList%classDTO%();
	}
	
	@UiHandler("btnGridProximo")
	void onBtnGridProximoClick(ClickEvent event) {
		if (pagination.nextPage())
			callList%classDTO%();
	}
	
	@UiHandler("btnGridUltimo")
	void onBtnGridUltimoClick(ClickEvent event) {
		if (pagination.lastPage())
			callList%classDTO%();
	}

%methods%	
	public void addColumns() {
		if (this.configFormPug.isDelete()) {
			Header<Boolean> deleteHeader = new Header<Boolean>(new CheckboxCell()) {
				@Override
				public Boolean getValue() {
					return false;
				}
				@Override
				public void onBrowserEvent(Context context, Element elem, NativeEvent event) {
					InputElement input = elem.getFirstChild().cast();
					Boolean isChecked = input.isChecked();
					for (%classDTO% %classDTO% : list%classDAO%) {
						selectionModel%classDTO%.setSelected(%classDTO%, isChecked);
					}												
				}    
			};
	
			Header<String> deleteFooter = new Header<String>(new ButtonImageCellPug()) {
				@Override
				public String getValue() {
					return Resources.garbage().getSafeUri().asString();
				}
				@Override
				public void onBrowserEvent(Context context, Element elem, NativeEvent event) {
					MessageBoxConfirmePug.Util.showMessage("Excluir!", "Deseja excluir todos da seleção?", false, (new CloseHandler<PopupPanel>() {
						public void onClose(CloseEvent<PopupPanel> event) {
							if (MessageBoxConfirmePug.Util.getInstance().isOK()) {
								ArrayList<%classDTO%> listDelete = new ArrayList<%classDTO%>();
								for (%classDTO% %instanceDTO% : list%classDAO%) {
									if (selectionModel%classDTO%.isSelected(%instanceDTO%) ) {
										listDelete.add(%instanceDTO%);
									}
								}								
								if (listDelete.size() > 0) {
									deleteList(listDelete);
								}
							}
						}
	
					}));								
				}
			};		
			deleteFooter.setHeaderStyleNames("gwt-cell-image");
	
			Column<%classDTO%, Boolean> deleteColumn = new Column<%classDTO%, Boolean>(new CheckboxCell()) {
				@Override
				public Boolean getValue(%classDTO% %instanceDTO%) {
					return selectionModel%classDTO%.isSelected(%instanceDTO%);
				}
			};
			if (deleteHeader != null && deleteFooter != null)
				dataGrid.addColumn(deleteColumn, deleteHeader, deleteFooter);
			dataGrid.setColumnWidth(deleteColumn, 40, Unit.PX);
		}
%columnsGrid%	
		if (this.configFormPug.isPopupSearch()) {
			Column<%classDTO%, String> selectColumn = new Column<%classDTO%, String>(new ButtonCell()) {
				@Override
				public String getValue(%classDTO% %instanceDTO%) {
					return "Selecionar";
				}
			};		
			selectColumn.setFieldUpdater(new FieldUpdater<%classDTO%, String>() {
			      @Override
			      public void update(int index, %classDTO% %instanceDTO%, String value) {
			    	  %instanceDTO%Selected = %instanceDTO%;
			    	  DialogBoxPug.Util.getInstance().hide();
			      }
		    });		
			dataGrid.addColumn(selectColumn, "");
			dataGrid.setColumnWidth(selectColumn, 110, Unit.PX);
		}
		if (this.configFormPug.isForm() && this.configFormPug.isEdit()) {
			Column<%classDTO%, String> editColumn = new Column<%classDTO%, String>(new ButtonImageCellPug()) {
				@Override
				public String getValue(%classDTO% %instanceDTO%) {
					return Resources.pencil().getSafeUri().asString();
				}
			};
			editColumn.setFieldUpdater(new FieldUpdater<%classDTO%, String>() {
				public void update(int index, %classDTO% %instanceDTO%, String value) {
					titleForm.setText(title + " (Editar)");
					%instanceDTO%Edit = %instanceDTO%;
					clearEdit(true);
%fieldsEdit%
					%fieldFocus%
				}
			});
			editColumn.setCellStyleNames("gwt-cell-image");
			dataGrid.addColumn(editColumn);
			dataGrid.setColumnWidth(editColumn, 45, Unit.PX);
		}

	}
	
	public void callList%classDTO%() {
		String order = comboBoxOrder.getValue(comboBoxOrder.getSelectedIndex()) + (checkBoxOrder.getValue() ? " desc" : "");
		MessageProgressPug.Util.show("", true);
		%classDAO%ServiceAsync %instanceDAO%Service = %classDAO%Service.Util.getInstance();
		((ServiceDefTarget) %instanceDAO%Service).setServiceEntryPoint( PathServices.%pathServices%SERVICE );		
		%instanceDAO%Service.listAll(%fieldsSearchArg%order, pagination.getPageNum()-1, pagination.getPageSize(), %instanceDAO%ServiceCallback);
	}

	public void clearEdit(boolean visible) {
%fieldsValidClean%		
%fieldsClear%				
		if (this.configFormPug.isForm() && this.configFormPug.isGrid())
			verticalPanelEdit.setVisible(visible);
		if (this.configFormPug.isGrid())
			verticalPanelGrid.setVisible(!visible);		
		if ((this.configFormPug.isOrder() || this.configFormPug.isAdd()) && this.configFormPug.isGrid())
			horizontalPanelAdd.setVisible(!visible);
		if (dialogBoxPug != null)
			dialogBoxPug.hide();
	}
	
	private void callBtnCancel() {
		titleForm.setText(title);
		clearEdit(false);
		callList%classDTO%();		
	}
	
	private void callBtnSave() {
		rowUpdate = null;
		if (btnGravar.isEnabled())
			callSave();		
	}
	
	private void callBtnFind() {
		pagination.setPageNum(1);
		callList%classDTO%();						
	}

	private void callSave() {
		boolean valid = true;
		if (this.rowUpdate == null) {
%fieldsValid%
		}
		if (!valid)
			return;
		
		%classDTO% %instanceDTO% = new %classDTO%();
		if (%instanceDTO%Edit != null)
			%instanceDTO% = %instanceDTO%Edit;		
		if (this.rowUpdate == null) {
%fieldsAdd%
		}
		MessageProgressPug.Util.show("", true);
		%classDAO%ServiceAsync %instanceDAO%Service = %classDAO%Service.Util.getInstance();
		((ServiceDefTarget) %instanceDAO%Service).setServiceEntryPoint( PathServices.%pathServices%SERVICE );			
		%instanceDAO%Service.save(%instanceDTO%, %instanceDAO%SaveServiceCallback);
	}

	private void deleteList(ArrayList<%classDTO%> listDelete) {
		this.rowUpdate = null;
		MessageProgressPug.Util.show("", true);
		%classDAO%ServiceAsync %instanceDAO%Service = %classDAO%Service.Util.getInstance();
		((ServiceDefTarget) %instanceDAO%Service).setServiceEntryPoint( PathServices.%pathServices%SERVICE );			
		%instanceDAO%Service.delete(listDelete, %instanceDAO%SaveServiceCallback);		
	};

	private void callAdd%classDTO%() {
		titleForm.setText(title + " (Novo)");
		%instanceDTO%Edit = null;
		clearEdit(true);
		%fieldFocus%
	}	

	public AsyncCallback<ArrayList<%classDTO%>> %instanceDAO%ServiceCallback = new AsyncCallback<ArrayList<%classDTO%>>() {    
		public void onFailure(Throwable caught) {
			MessageProgressPug.Util.close();
			MessageBoxErrorPug.Util.showMessage("Erro!", "Falha na resposta do servidor, tente novamente.", caught.getMessage(), null);			
		}    
		public void onSuccess(ArrayList<%classDTO%> list%classDTO%) {
			try {
				MessageProgressPug.Util.close();
				list%classDAO% = list%classDTO%;
				if (list%classDTO% == null) {
					dataGrid.setRowCount(0, true);
				} else {
					if (list%classDTO%.size() > 0) {
						pagination.calcPageCount(list%classDTO%.get(0).getSizeData());
						if (comboBoxGridPaginas.getItemCount() != pagination.getPageCount()) {
							comboBoxGridPaginas.clear();
							for (int i = 0; i < pagination.getPageCount(); i++) {
								comboBoxGridPaginas.addItem(String.valueOf((i+1)));
							}
						}
						comboBoxGridPaginas.setSelectedIndex(pagination.getPageNum()-1);
					}					
					dataGrid.setRowCount(list%classDTO%.size(), true);
					dataGrid.setRowData(0, list%classDTO%);
					titleForm.setText(title);
				}
			} catch (Exception e) {
				MessageProgressPug.Util.close();
				MessageBoxErrorPug.Util.showMessage("Erro!", "Falha na leitura dos dados, tente novamente.", e.getMessage(), null);			
			}
		}
	};

	public AsyncCallback<ResultSaveDTO> %instanceDAO%SaveServiceCallback = new AsyncCallback<ResultSaveDTO>() {    
		public void onFailure(Throwable caught) {
			MessageProgressPug.Util.close();
			MessageBoxErrorPug.Util.showMessage("Erro!", "Falha na resposta do servidor, tente novamente.", caught.getMessage(), null);			
			if (rowUpdate != null) {
				dataGrid.redrawRow(rowUpdate);
				rowUpdate = null;
			}	
		}    
		public void onSuccess(ResultSaveDTO resultSaveDTO) {
			try {
				MessageProgressPug.Util.close();
				if (resultSaveDTO != null && resultSaveDTO.getError() != null) {
					MessageBoxErrorPug.Util.showMessage("Erro!", "Falha na leitura dos dados, tente novamente.", resultSaveDTO.getError(), null);
					if (rowUpdate != null) {
						dataGrid.redrawRow(rowUpdate);
					}	
				} else {
					if (rowUpdate == null) {
						clearEdit(false);					
						callList%classDTO%();
					}	
				}	
				rowUpdate = null;
			} catch (Exception e) {
				MessageProgressPug.Util.close();
				MessageBoxErrorPug.Util.showMessage("Erro!", "Falha na leitura dos dados, tente novamente.", e.getMessage(), null);			
				if (rowUpdate != null) {
					dataGrid.redrawRow(rowUpdate);
					rowUpdate = null;
				}	
			}
		}
	};

}
