var tabIndexCompile = 0;
var layoutFullFile = "";
var layoutFile = "";
var layoutFolder = "";
var closeWin = false;
var editors = {
		phtml: null,
		java: null,
		css: null,
		js: null,
		note1: null,
		note2: null
}

function init() {
	$('.ui-tabs.ui-tabs-top>.ui-tabs-nav li').css({"border-bottom": "0"});
	$('.ui-tabs.ui-tabs-top>.ui-tabs-nav li').css({"top": "2px"});
	if (visibleEditor()) {
    	PF('panelExplorer').collapse();
	}	
}

function visibleEditor() {
	return (layoutFullFile != null && layoutFullFile.length > 0);
}

function readLayout(folder, name) {
	layoutFullFile = folder + "/" + name;
	layoutFolder = folder;
	layoutFile = name;
	PF('statusDialog').show();	
	editors["phtml"] = updateCodeMirror(0, "editorPHTML", "text/html", "textPHTML1", "textPHTML2", "tabView:formCommandPHTML:btnSavePHTML", "tabView:formCommandPHTML:growlPHTML tabView:formCommandPHTML:phtmlDateUpdate tabView:formCommandPHTML:phtmlUserUpdate tabView:formCommandJava:javaDateUpdate tabView:formCommandJava:javaUserUpdate tabView:formCommandJava:msgConsole", true);
	editors["java"] = updateCodeMirror(1, "editorJava", "text/x-java", "textJava1", "textJava2", "tabView:formCommandJava:btnSaveJava", "tabView:formCommandJava:growlJava tabView:formCommandJava:javaDateUpdate tabView:formCommandJava:javaUserUpdate tabView:formCommandPHTML:phtmlDateUpdate tabView:formCommandPHTML:phtmlUserUpdate tabView:formCommandJava:msgConsole", true);
	editors["css"] = updateCodeMirror(2, "editorCSS", "text/x-scss", "textCSS", "", "tabView:formCommandCSS:btnSaveCSS", "tabView:formCommandCSS:growlCSS", true);
	editors["js"] = updateCodeMirror(3, "editorJS", "javascript", "textJS", "", "tabView:formCommandJS:btnSaveJS", "tabView:formCommandJS:growlJS", true);
	editors["note1"] = updateCodeMirror(4, "editorNote1", "text", "textNote1", "", "tabView:formCommandNote1:btnSaveNote1", "tabView:formCommandNote1:growlNote1 tabView:formCommandNote1:note1DateUpdate tabView:formCommandNote1:note1UserUpdate", true);
	editors["note2"] = updateCodeMirror(5, "editorNote2", "text", "textNote2", "", "", "", false);
	PF('tabView').select(0, false);
	PF('statusDialog').hide();
	editors["phtml"].focus();
}

function updateIFrame() {
    now = Date.now();    
   	$("#iframeView").attr('src', "projects/" + layoutFullFile + ".xhtml?_=" + now + '&folder=' + layoutFolder + '&file=' + layoutFile);
}

function clearErrors() {
	tabIndexCompile = PF('tabView').getActiveIndex();
	PF('tabView').select(1, false);
	editors["java"].clearGutter("breakpoints");
}

function goTabIndexCompile() {
	PF('tabView').select(tabIndexCompile, false);
}

function updateErrors(line, msg) {
	PF('tabView').select(1, false);
	var marker = document.createElement("img");
	marker.src = 'resources/images/error.png'; 
	marker.title = msg;
	editors["java"].setGutterMarker(line, "breakpoints", marker);
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
	    gutters: ["CodeMirror-linenumbers", "breakpoints"],
	    extraKeys: {
	    	"Ctrl-Space": "autocomplete",
	        "F11": function(cm) {
	          cm.setOption("fullScreen", !cm.getOption("fullScreen"));
	        },
	        "Esc": function(cm) {
	          if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
	        },
	        "Ctrl-S": function(cm) {
	        	if (callBtnSave.length > 0)
	        		PrimeFaces.ab({s:callBtnSave,u:callUpdateSave});
	        },
	        "Ctrl-Shift-S": function(cm) {
	        	if (callBtnSave.length > 0)
	        		saveAll();
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
	updateIFrame();
	return editor;
}

function foundSave(id) {
	return $("a[href='#tabView:"+id+"']").text().indexOf(" *") > -1;
}

function msgCloseWindow() {
	var active = foundSave("tabXHTML") || foundSave("tabJava") || foundSave("tabCSS") || foundSave("tabJS") || foundSave("tabNote1");
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
	var id = "";
	var id2 = "";
	var id3 = "";
	var id4 = "";
	switch(PF('tabView').getActiveIndex()) {
    case 0:
   		id = "btnSavePHTML";
   		id2 = "btnSaveJava";
   		id3 = "tabXHTML";
   		id4 = "tabJava";
        break;
    case 1:
   		id = "btnSavePHTML";
   		id2 = "btnSaveJava";
   		id3 = "tabXHTML";
   		id4 = "tabJava";
        break;
    case 2:
   		id = "btnSaveCSS";
   		id3 = "tabCSS";
        break;
    case 3:
   		id = "btnSaveJS";
   		id3 = "tabJS";
        break;
    case 4:
   		id = "btnSaveNote1";
   		id3 = "tabNote1";
        break;
    case 5:
        break;
	}
	changeBtnTitle(id, modified);
	changeBtnTitle(id2, modified);
	changeTabTitle(id3, modified);
	changeTabTitle(id4, modified);
	msgCloseWindow();
}

function refreshSource() {
	updateIFrame();
	changeBtnSave(false);
}

function convertId(id) {
	return id.replace(/:/g, "\\\\:");
}

function encodeEntities(s){
	return $("<div/>").text(s).html();
}

function decodeEntities(s){
	return $("<div/>").html(s).text();
}

function tabViewFocus(){
	var key = "";
	switch(PF('tabView').getActiveIndex()) {
    case 0:
    	key = "phtml";
        break;
    case 1:
    	key = "java";
        break;
    case 2:
    	key = "css";
        break;
    case 3:
    	key = "js";
        break;
    case 4:
    	key = "note1";
        break;
    case 5:
    	key = "note2";
        break;
	}	
	if (key.length > 0 && editors[key] != null)
		editors[key].focus();
}

function saveAll() {
	var currentTab = PF('tabView').getActiveIndex();
	PF('tabView').select(0, false);	
	PrimeFaces.ab({s:"tabView:formCommandPHTML:btnSavePHTML",u:"tabView:formCommandPHTML:growlPHTML tabView:formCommandPHTML:phtmlDateUpdate tabView:formCommandPHTML:phtmlUserUpdate tabView:formCommandJava:javaDateUpdate tabView:formCommandJava:javaUserUpdate tabView:formCommandJava:msgConsole"});
	refreshSource();
	PF('tabView').select(2, false);	
	PrimeFaces.ab({s:"tabView:formCommandCSS:btnSaveCSS",u:"tabView:formCommandCSS:growlCSS"});
	refreshSource();
	PF('tabView').select(3, false);	
	PrimeFaces.ab({s:"tabView:formCommandJS:btnSaveJS",u:"tabView:formCommandJS:growlJS"});
	refreshSource();
	PF('tabView').select(4, false);	
	PrimeFaces.ab({s:"tabView:formCommandNote1:btnSaveNote1",u:"tabView:formCommandNote1:growlNote1 tabView:formCommandNote1:note1DateUpdate tabView:formCommandNote1:note1UserUpdate"});
	refreshSource();
	PF('tabView').select(currentTab, false);
}