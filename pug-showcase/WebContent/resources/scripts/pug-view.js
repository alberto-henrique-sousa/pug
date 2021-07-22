var closeWin = false;
var projectFile = "";
var note2 = null;

function readLayout(folder, name) {
	$('.ui-tabs.ui-tabs-top>.ui-tabs-nav li').css({"border-bottom": "0"});
	$('.ui-tabs.ui-tabs-top>.ui-tabs-nav li').css({"top": "2px"});
	
	if (PF("textNote2") != null) {
		note2 = updateCodeMirror(1, "editorNote2", "text", "textNote2", "", "tabView:formCommandNote2:btnSaveNote2", "tabView:formCommandNote2:growlNote2 tabView:formCommandNote2:note2DateUpdate tabView:formCommandNote2:note2UserUpdate", true);
	}	
	PF('tabView').select(0, false);
	
    now = Date.now();
   	$("#iframeView").attr('src', "projects/" + folder + "/" + name + ".xhtml?_=" + now + '&folder=' + folder + '&file=' + name);
}

function updateCodeMirror(tabIndex, idEditor, modeL, idTextArea1, idTextArea2, callBtnSave, callUpdateSave, editable) {
	var data = PF(idTextArea1).jq.val();
	PF('tabView').select(tabIndex, false);
	$("#" + idEditor).text('');
	var editor = CodeMirror(document.getElementById(idEditor), {
	    value: data, 	
	    lineNumbers: true,
        mode: modeL,
        autoCloseTags: true,
	    keyMap: "sublime",
	    autoCloseBrackets: true,
	    matchBrackets: true,
	    showCursorWhenSelecting: true,
	    tabSize: 4,
	    indentUnit: 4,
	    autofocus: true,
	    readOnly: !editable,
	    extraKeys: {
	        "F11": function(cm) {
	          cm.setOption("fullScreen", !cm.getOption("fullScreen"));
	        },
	        "Esc": function(cm) {
	          if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
	        },
	        "Ctrl-S": function(cm) {
	        	if (callBtnSave.length > 0)
	        		PrimeFaces.ab({s:callBtnSave,u:callUpdateSave});
	        }
	      }	    
	});	
	editor.on("change", function() {
		changeBtnSave(true);
		PF(idTextArea1).jq.val(editor.getValue());
		if (idTextArea2.length > 0) {
			PF(idTextArea2).jq.val(editor.getValue());
		}	
	});
	return editor;
}

function changeBtnTitle(id, modified) {
	if (id.length > 0) {
		PF(id).jq.html('<span class="ui-button-icon-left ui-icon ui-c  ui-icon-disk"></span><span class="ui-button-text ui-c">Salvar'+(modified ? ' *' : '')+'</span>');		
	}	
}

function changeTabTitle(id, modified) {
	if (id.length > 0) {
		var id = "a[href='#tabView:"+id+"']";
		var title = $(id).text();
		title = title.replace(" *", "");
		if (modified)
			title += " *";
		$(id).text(title);
	}	
}

function changeBtnSave(modified) {
	changeBtnTitle("btnSaveNote2", modified);	
	changeTabTitle("tabNote2", modified);
	msgCloseWindow();
}

function foundSave(id) {
	return $("a[href='#tabView:"+id+"']").text().indexOf(" *") > -1;
}

function msgCloseWindow() {	
	var active = foundSave("tabNote2");
	if (active) {
		if (!closeWin) {
			$(window).bind('beforeunload', function(){
				return "Alterações não salvas!";
			});		
			closeWin = true;
		}	
	} else {	
		closeWin = false;
		$(window).unbind('beforeunload');
	}	
} 

function refreshSource() {
	changeBtnSave(false);
}

function tabViewFocus(){
	if (note2 != null) {
		if (PF('tabView').getActiveIndex() == 1)
			note2.focus();
	}	
}
