var lastData = "";
var countUpdate = 0;
var maxUpdate = 10;
var begin = true;
var selectCmd = false;

function path()
{
	var args = arguments,
		result = []
		;
		
	for(var i = 0; i < args.length; i++)
		result.push(args[i].replace('@', 'resources/scripts/'));
		
	return result
};

function autoLoader() {
	SyntaxHighlighter.autoloader.apply(null, path(
			'xml xhtml xslt html	@shBrushXml.js'
		));
}

function updateCodeXHTML(codeFile) {
	PF('statusDialog').show();
	updateSyntax(codeFile);
}

function updateSyntax(codeFile) {
	$.ajax({
		  url: codeFile,
		  cache: false
	})
		  .done(function( data ) {
			  if (countUpdate < maxUpdate && lastData == data) {
				  countUpdate++;
				  setTimeout("updateSyntax('"+codeFile+"')", 1000);
			  } else {
				  var value = PF('command').input.val().trim();
				  if (value.length > 0) {					  
					  br = "";
					  if (begin && $("#areaHistoric").html().length > 0)
						  br = "<br/>";
					  $("#areaHistoric").append(br + encodeEntities(value) + "<br/>");
					  begin = false;
				  }	  
				  $("#areaHistoric").scrollTop(10000);
				  lastData = data;
				  countUpdate = 0;
				  applySyntax(data, "");
				  now = Date.now();			  
				  $("#iframeView").attr('src', "views/layout.xhtml?_=" + now);
				  focus();
				  PF('statusDialog').hide();
			  }
  });					
}

function applySyntax(data, highlight) {
  if (highlight.length > 0)
	  highlight = " highlight: ["+highlight+"]";
  $("#areaPreCode").html("<pre id=\"codeXHTML\" class=\"brush: xml;"+highlight+"\"></pre>");	
  autoLoader();
  $("#codeXHTML").text(data);
  SyntaxHighlighter.defaults['toolbar'] = false;
  SyntaxHighlighter.defaults['auto-links'] = false;
  SyntaxHighlighter.all();
}

function removeLayout() {
	$("#areaPreCode").html("<pre id=\"codeXHTML\" class=\"brush: xml;\"></pre>");
	$("#iframeView").attr('src', "");
	$("#areaHistoric").html("");
	focus();
}

function readLayout() {
	begin = true;
	$('.ui-tabs.ui-tabs-top>.ui-tabs-nav li').css({"border-bottom": "0"});
	$('.ui-tabs.ui-tabs-top>.ui-tabs-nav li').css({"top": "2px"});
	$.ajax({
		  url: "views/layout.cmd",
		  cache: false
	})
		  .done(function( data ) {
			  $("#areaHistoric").html(encodeEntities(data).replace(/\n/g, "<br/>"));
				updateCodeXHTML("views/layout.comp");
    });
}

function convertId(id) {
	return "#" + id.replace(/:/g, "\\\\:");
}

function selectBlockCmdCode() {
	var value = PF('command').input.val().trim();
	if (value.indexOf("pug:") > -1 && value.indexOf("line=") >-1) {
		var lines = value.split("=");
		if (lines.length > 1) {
			lines = lines[1];
			lines = lines.split("-");
			var nLine1 = lines[0];
			var nLine2 = lines[0];
			if (lines.length > 1)
				nLine2 = lines[1];
			nLine1 = parseInt(nLine1);
			nLine2 = parseInt(nLine2);
			var highlight = "";
			for (i = nLine1; i <= nLine2; i++) { 
				highlight += i + (i < nLine2 ? ", " : "");
			}
			applySyntax(lastData, highlight);
			selectCmd = true;
		}
	} else {
		if (selectCmd) {
			selectCmd = false;
			applySyntax(lastData, "");
		}
			
	}
}

function selectBlockInsCode() {
	var value1 = PF('opLine1').jq.val().trim();
	var value2 = PF('opLine2').jq.val().trim();
	var nLine1 = parseInt(value1);
	var nLine2 = parseInt(value2);
	var highlight = "";
	for (i = nLine1; i <= nLine2; i++) { 
		highlight += i + (i < nLine2 ? ", " : "");
	}
	if (highlight.length == 0 && nLine1 > 0)
		highlight = value1;
	applySyntax(lastData, highlight);
}

function editLine(text) {
	PF('command').input.val(text);
}

function encodeEntities(s){
	return $("<div/>").text(s).html();
}
function dencodeEntities(s){
	return $("<div/>").html(s).text();
}

function focus() {
    PF('command').input.val('');
	PF('opLine1').jq.val('0');
	PF('opLine2').jq.val('0');
	PrimeFaces.focus(PF('command').id);	
}

function pugCmd(cmd) {
	PF('opLine1').jq.val('0');
	PF('opLine2').jq.val('0');
	PF('command').input.val('pug:'+cmd);
	PrimeFaces.focus(PF('command').id);	
}