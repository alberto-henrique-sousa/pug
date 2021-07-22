// CodeMirror, copyright (c) by Marijn Haverbeke and others
// Distributed under an MIT license: http://codemirror.net/LICENSE

// PrimeFaces added by Alberto Henrique Sousa (alberto.henrique.sousa@gmail.com)

(function(mod) {
  if (typeof exports == "object" && typeof module == "object") // CommonJS
    mod(require("../../lib/codemirror"), require("./xml-hint"));
  else if (typeof define == "function" && define.amd) // AMD
    define(["../../lib/codemirror", "./xml-hint"], mod);
  else // Plain browser env
    mod(CodeMirror);
})(function(CodeMirror) {
  "use strict";

  var langs = "ab aa af ak sq am ar an hy as av ae ay az bm ba eu be bn bh bi bs br bg my ca ch ce ny zh cv kw co cr hr cs da dv nl dz en eo et ee fo fj fi fr ff gl ka de el gn gu ht ha he hz hi ho hu ia id ie ga ig ik io is it iu ja jv kl kn kr ks kk km ki rw ky kv kg ko ku kj la lb lg li ln lo lt lu lv gv mk mg ms ml mt mi mr mh mn na nv nb nd ne ng nn no ii nr oc oj cu om or os pa pi fa pl ps pt qu rm rn ro ru sa sc sd se sm sg sr gd sn si sk sl so st es su sw ss sv ta te tg th ti bo tk tl tn to tr ts tt tw ty ug uk ur uz ve vi vo wa cy wo fy xh yi yo za zu".split(" ");
  var targets = ["_blank", "_self", "_top", "_parent"];
  var charsets = ["ascii", "utf-8", "utf-16", "latin1", "latin1"];
  var methods = ["get", "post", "put", "delete"];
  var encs = ["application/x-www-form-urlencoded", "multipart/form-data", "text/plain"];
  var media = ["all", "screen", "print", "embossed", "braille", "handheld", "print", "projection", "screen", "tty", "tv", "speech",
               "3d-glasses", "resolution [>][<][=] [X]", "device-aspect-ratio: X/Y", "orientation:portrait",
               "orientation:landscape", "device-height: [X]", "device-width: [X]"];
  var s = { attrs: {} }; // Simple tag, reused for a whole lot of tags

  var data = {
    a: {
      attrs: {
        href: null, ping: null, type: null,
        media: media,
        target: targets,
        hreflang: langs
      }
    },
    abbr: s,
    acronym: s,
    address: s,
    applet: s,
    area: {
      attrs: {
        alt: null, coords: null, href: null, target: null, ping: null,
        media: media, hreflang: langs, type: null,
        shape: ["default", "rect", "circle", "poly"]
      }
    },
    article: s,
    aside: s,
    audio: {
      attrs: {
        src: null, mediagroup: null,
        crossorigin: ["anonymous", "use-credentials"],
        preload: ["none", "metadata", "auto"],
        autoplay: ["", "autoplay"],
        loop: ["", "loop"],
        controls: ["", "controls"]
      }
    },
    b: s,
    base: { attrs: { href: null, target: targets } },
    basefont: s,
    bdi: s,
    bdo: s,
    big: s,
    blockquote: { attrs: { cite: null } },
    body: s,
    br: s,
    button: {
      attrs: {
        form: null, formaction: null, name: null, value: null,
        autofocus: ["", "autofocus"],
        disabled: ["", "autofocus"],
        formenctype: encs,
        formmethod: methods,
        formnovalidate: ["", "novalidate"],
        formtarget: targets,
        type: ["submit", "reset", "button"]
      }
    },
    canvas: { attrs: { width: null, height: null } },
    caption: s,
    center: s,
    cite: s,
    code: s,
    col: { attrs: { span: null } },
    colgroup: { attrs: { span: null } },
    command: {
      attrs: {
        type: ["command", "checkbox", "radio"],
        label: null, icon: null, radiogroup: null, command: null, title: null,
        disabled: ["", "disabled"],
        checked: ["", "checked"]
      }
    },
    data: { attrs: { value: null } },
    datagrid: { attrs: { disabled: ["", "disabled"], multiple: ["", "multiple"] } },
    datalist: { attrs: { data: null } },
    dd: s,
    del: { attrs: { cite: null, datetime: null } },
    details: { attrs: { open: ["", "open"] } },
    dfn: s,
    dir: s,
    div: s,
    dl: s,
    dt: s,
    em: s,
    embed: { attrs: { src: null, type: null, width: null, height: null } },
    eventsource: { attrs: { src: null } },
    fieldset: { attrs: { disabled: ["", "disabled"], form: null, name: null } },
    figcaption: s,
    figure: s,
    font: s,
    footer: s,
    form: {
      attrs: {
        action: null, name: null,
        "accept-charset": charsets,
        autocomplete: ["on", "off"],
        enctype: encs,
        method: methods,
        novalidate: ["", "novalidate"],
        target: targets
      }
    },
    frame: s,
    frameset: s,
    h1: s, h2: s, h3: s, h4: s, h5: s, h6: s,
    head: {
      attrs: {},
      children: ["title", "base", "link", "style", "meta", "script", "noscript", "command"]
    },
    header: s,
    hgroup: s,
    hr: s,
    html: {
      attrs: { manifest: null },
      children: ["head", "body"]
    },
    i: s,
    iframe: {
      attrs: {
        src: null, srcdoc: null, name: null, width: null, height: null,
        sandbox: ["allow-top-navigation", "allow-same-origin", "allow-forms", "allow-scripts"],
        seamless: ["", "seamless"]
      }
    },
    img: {
      attrs: {
        alt: null, src: null, ismap: null, usemap: null, width: null, height: null,
        crossorigin: ["anonymous", "use-credentials"]
      }
    },
    input: {
      attrs: {
        alt: null, dirname: null, form: null, formaction: null,
        height: null, list: null, max: null, maxlength: null, min: null,
        name: null, pattern: null, placeholder: null, size: null, src: null,
        step: null, value: null, width: null,
        accept: ["audio/*", "video/*", "image/*"],
        autocomplete: ["on", "off"],
        autofocus: ["", "autofocus"],
        checked: ["", "checked"],
        disabled: ["", "disabled"],
        formenctype: encs,
        formmethod: methods,
        formnovalidate: ["", "novalidate"],
        formtarget: targets,
        multiple: ["", "multiple"],
        readonly: ["", "readonly"],
        required: ["", "required"],
        type: ["hidden", "text", "search", "tel", "url", "email", "password", "datetime", "date", "month",
               "week", "time", "datetime-local", "number", "range", "color", "checkbox", "radio",
               "file", "submit", "image", "reset", "button"]
      }
    },
    ins: { attrs: { cite: null, datetime: null } },
    kbd: s,
    keygen: {
      attrs: {
        challenge: null, form: null, name: null,
        autofocus: ["", "autofocus"],
        disabled: ["", "disabled"],
        keytype: ["RSA"]
      }
    },
    label: { attrs: { "for": null, form: null } },
    legend: s,
    li: { attrs: { value: null } },
    link: {
      attrs: {
        href: null, type: null,
        hreflang: langs,
        media: media,
        sizes: ["all", "16x16", "16x16 32x32", "16x16 32x32 64x64"]
      }
    },
    map: { attrs: { name: null } },
    mark: s,
    menu: { attrs: { label: null, type: ["list", "context", "toolbar"] } },
    meta: {
      attrs: {
        content: null,
        charset: charsets,
        name: ["viewport", "application-name", "author", "description", "generator", "keywords"],
        "http-equiv": ["content-language", "content-type", "default-style", "refresh"]
      }
    },
    meter: { attrs: { value: null, min: null, low: null, high: null, max: null, optimum: null } },
    nav: s,
    noframes: s,
    noscript: s,
    object: {
      attrs: {
        data: null, type: null, name: null, usemap: null, form: null, width: null, height: null,
        typemustmatch: ["", "typemustmatch"]
      }
    },
    ol: { attrs: { reversed: ["", "reversed"], start: null, type: ["1", "a", "A", "i", "I"] } },
    optgroup: { attrs: { disabled: ["", "disabled"], label: null } },
    option: { attrs: { disabled: ["", "disabled"], label: null, selected: ["", "selected"], value: null } },
    output: { attrs: { "for": null, form: null, name: null } },
    p: s,
    param: { attrs: { name: null, value: null } },
    pre: s,
    progress: { attrs: { value: null, max: null } },
    q: { attrs: { cite: null } },
    rp: s,
    rt: s,
    ruby: s,
    s: s,
    samp: s,
    script: {
      attrs: {
        type: ["text/javascript"],
        src: null,
        async: ["", "async"],
        defer: ["", "defer"],
        charset: charsets
      }
    },
    section: s,
    select: {
      attrs: {
        form: null, name: null, size: null,
        autofocus: ["", "autofocus"],
        disabled: ["", "disabled"],
        multiple: ["", "multiple"]
      }
    },
    small: s,
    source: { attrs: { src: null, type: null, media: null } },
    span: s,
    strike: s,
    strong: s,
    style: {
      attrs: {
        type: ["text/css"],
        media: media,
        scoped: null
      }
    },
    sub: s,
    summary: s,
    sup: s,
    table: s,
    tbody: s,
    td: { attrs: { colspan: null, rowspan: null, headers: null } },
    textarea: {
      attrs: {
        dirname: null, form: null, maxlength: null, name: null, placeholder: null,
        rows: null, cols: null,
        autofocus: ["", "autofocus"],
        disabled: ["", "disabled"],
        readonly: ["", "readonly"],
        required: ["", "required"],
        wrap: ["soft", "hard"]
      }
    },
    tfoot: s,
    th: { attrs: { colspan: null, rowspan: null, headers: null, scope: ["row", "col", "rowgroup", "colgroup"] } },
    thead: s,
    time: { attrs: { datetime: null } },
    title: s,
    tr: s,
    track: {
      attrs: {
        src: null, label: null, "default": null,
        kind: ["subtitles", "captions", "descriptions", "chapters", "metadata"],
        srclang: langs
      }
    },
    tt: s,
    u: s,
    ul: s,
    "var": s,
    video: {
      attrs: {
        src: null, poster: null, width: null, height: null,
        crossorigin: ["anonymous", "use-credentials"],
        preload: ["auto", "metadata", "none"],
        autoplay: ["", "autoplay"],
        mediagroup: ["movie"],
        muted: ["", "muted"],
        controls: ["", "controls"]
      }
    },
    wbr: s,
    // primefaces
    "p:dataExporter": {
        attrs: {
          "target": null,
          "type": null,
          "fileName": null,
          "pageOnly": null,
          "preProcessor": null,
          "postProcessor": null,
          "encoding": null,
          "selectionOnly": null,
          "repeat": null
        }
    },
    "p:fileDownload": {
        attrs: {
          "value": null,
          "contentDisposition": null
        }
    },
    "p:collector": {
        attrs: {
          "value": null,
          "addTo": null,
          "removeFrom": null,
          "unique": null
        }
    },
    "p:ajax": {
        attrs: {
          "listener": null,
          "immediate": null,
          "async": null,
          "process": null,
          "update": null,
          "onstart": null,
          "oncomplete": null,
          "onerror": null,
          "onsuccess": null,
          "global": null,
          "delay": null,
          "disabled": null,
          "event": null,
          "partialSubmit": null,
          "resetValues": null,
          "ignoreAutoUpdate": null,
          "partialSubmitFilter": null,
          "form": null,
          "skipChildren": null
        }
    },
    "p:printer": {
        attrs: {
          "target": null
        }
    },
    "p:confirm": {
        attrs: {
          "header": null,
          "message": null,
          "icon": null,
          "disabled": null
        }
    },
    "p:resetInput": {
        attrs: {
          "target": null,
          "clearModel": null
        }
    },
    "p:clientValidator": {
        attrs: {
          "event": null,
          "disabled": null
        }
    },
    "p:repeat": {
        attrs: {
          "value": null,
          "var": null,
          "varStatus": null,
          "offset": null,
          "step": null,
          "size": null
        }
    },
    "p:importConstants": {
        attrs: {
          "type": null,
          "var": null
        }
    },
    "p:importEnum": {
        attrs: {
          "type": null,
          "var": null,
          "allSuffix": null
        }
    },
    "p:accordionPanel": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "activeIndex": null,
          "style": null,
          "styleClass": null,
          "onTabChange": null,
          "onTabShow": null,
          "onTabClose": null,
          "dynamic": null,
          "cache": null,
          "var": null,
          "value": null,
          "multiple": null,
          "dir": null,
          "prependId": null,
          "tabindex": null,
          "tabController": null
        }
    },
    "p:ajaxExceptionHandler": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "onexception": null,
          "update": null,
          "type": null
        }
    },
    "p:ajaxStatus": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "onstart": null,
          "oncomplete": null,
          "onsuccess": null,
          "onerror": null,
          "style": null,
          "styleClass": null
        }
    },
    "p:autoComplete": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "accesskey": null,
          "alt": null,
          "autocomplete": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "lang": null,
          "maxlength": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "size": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "placeholder": null,
          "widgetVar": null,
          "completeMethod": null,
          "var": null,
          "itemLabel": null,
          "itemValue": null,
          "maxResults": null,
          "minQueryLength": null,
          "queryDelay": null,
          "forceSelection": null,
          "scrollHeight": null,
          "effect": null,
          "effectDuration": null,
          "dropdown": null,
          "panelStyle": null,
          "panelStyleClass": null,
          "multiple": null,
          "itemtipMyPosition": null,
          "itemtipAtPosition": null,
          "cache": null,
          "cacheTimeout": null,
          "emptyMessage": null,
          "appendTo": null,
          "resultsMessage": null,
          "groupBy": null,
          "queryEvent": null,
          "dropdownMode": null,
          "autoHighlight": null,
          "selectLimit": null,
          "inputStyle": null,
          "inputStyleClass": null,
          "groupByTooltip": null,
          "my": null,
          "at": null,
          "active": null,
          "type": null,
          "moreText": null
        }
    },
    "p:barcode": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "type": null,
          "cache": null,
          "format": null,
          "orientation": null,
          "value": null,
          "alt": null,
          "width": null,
          "height": null,
          "title": null,
          "dir": null,
          "lang": null,
          "ismap": null,
          "usemap": null,
          "style": null,
          "styleClass": null,
          "onclick": null,
          "ondblclick": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null
        }
    },
    "p:blockUI": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "trigger": null,
          "block": null,
          "blocked": null,
          "animate": null,
          "styleClass": null
        }
    },
    "p:breadCrumb": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "model": null,
          "style": null,
          "styleClass": null,
          "homeDisplay": null
        }
    },
    "p:button": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "value": null,
          "outcome": null,
          "includeViewParams": null,
          "fragment": null,
          "disabled": null,
          "accesskey": null,
          "alt": null,
          "dir": null,
          "image": null,
          "lang": null,
          "tabindex": null,
          "title": null,
          "readonly": null,
          "style": null,
          "styleClass": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "icon": null,
          "iconPos": null,
          "href": null,
          "target": null,
          "escape": null,
          "inline": null,
          "disableClientWindow": null
        }
    },
    "p:cache": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "disabled": null,
          "region": null,
          "key": null,
          "processEvents": null
        }
    },
    "p:calendar": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "accesskey": null,
          "alt": null,
          "autocomplete": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "lang": null,
          "maxlength": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "size": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "placeholder": null,
          "widgetVar": null,
          "mindate": null,
          "maxdate": null,
          "pages": null,
          "mode": null,
          "pattern": null,
          "locale": null,
          "navigator": null,
          "timeZone": null,
          "readonlyInput": null,
          "showButtonPanel": null,
          "effect": null,
          "effectDuration": null,
          "showOn": null,
          "showWeek": null,
          "disabledWeekends": null,
          "showOtherMonths": null,
          "selectOtherMonths": null,
          "yearRange": null,
          "timeOnly": null,
          "stepHour": null,
          "stepMinute": null,
          "stepSecond": null,
          "minHour": null,
          "maxHour": null,
          "minMinute": null,
          "maxMinute": null,
          "minSecond": null,
          "maxSecond": null,
          "pagedate": null,
          "beforeShowDay": null,
          "mask": null,
          "timeControlType": null,
          "beforeShow": null,
          "maskSlotChar": null,
          "maskAutoClear": null,
          "timeControlObject": null,
          "timeInput": null,
          "showHour": null,
          "showMinute": null,
          "showSecond": null,
          "showMillisec": null,
          "showTodayButton": null
        }
    },
    "p:captcha": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "theme": null,
          "language": null,
          "tabindex": null,
          "label": null,
          "callback": null,
          "expired": null
        }
    },
    "p:carousel": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "value": null,
          "var": null,
          "firstVisible": null,
          "numVisible": null,
          "circular": null,
          "vertical": null,
          "autoPlayInterval": null,
          "pageLinks": null,
          "effect": null,
          "easing": null,
          "effectDuration": null,
          "dropdownTemplate": null,
          "style": null,
          "styleClass": null,
          "itemStyle": null,
          "itemStyleClass": null,
          "headerText": null,
          "footerText": null,
          "responsive": null,
          "breakpoint": null
        }
    },
    "p:cellEditor": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null
        }
    },
    "p:chart": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "type": null,
          "model": null,
          "style": null,
          "styleClass": null,
          "responsive": null
        }
    },
    "p:checkbox": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "disabled": null,
          "itemIndex": null,
          "onchange": null,
          "for": null,
          "style": null,
          "styleClass": null,
          "tabindex": null
        }
    },
    "p:clock": {
        attrs: {
          "pattern": null,
          "mode": null,
          "autoSync": null,
          "syncInterval": null
        }
    },
    "p:colorPicker": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "mode": null,
          "style": null,
          "styleClass": null
        }
    },
    "p:column": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "sortBy": null,
          "style": null,
          "styleClass": null,
          "sortFunction": null,
          "filterBy": null,
          "filterStyle": null,
          "filterStyleClass": null,
          "filterOptions": null,
          "filterMatchMode": null,
          "filterPosition": null,
          "rowspan": null,
          "colspan": null,
          "headerText": null,
          "footerText": null,
          "selectionMode": null,
          "filterMaxLength": null,
          "resizable": null,
          "exportable": null,
          "filterValue": null,
          "width": null,
          "toggleable": null,
          "filterFunction": null,
          "field": null,
          "priority": null,
          "sortable": null,
          "filterable": null,
          "visible": null,
          "selectRow": null,
          "ariaHeaderText": null,
          "exportFunction": null
        }
    },
    "p:columnGroup": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "type": null
        }
    },
    "p:columns": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "var": null,
          "sortBy": null,
          "style": null,
          "styleClass": null,
          "sortFunction": null,
          "filterBy": null,
          "filterStyle": null,
          "filterStyleClass": null,
          "filterOptions": null,
          "filterMatchMode": null,
          "filterPosition": null,
          "filterValue": null,
          "rowspan": null,
          "colspan": null,
          "headerText": null,
          "footerText": null,
          "filterMaxLength": null,
          "resizable": null,
          "exportable": null,
          "columnIndexVar": null,
          "width": null,
          "toggleable": null,
          "filterFunction": null,
          "field": null,
          "priority": null,
          "sortable": null,
          "filterable": null,
          "visible": null,
          "selectRow": null,
          "ariaHeaderText": null,
          "exportFunction": null
        }
    },
    "p:columnToggler": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "trigger": null,
          "datasource": null
        }
    },
    "p:commandButton": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "actionListener": null,
          "action": null,
          "immediate": null,
          "widgetVar": null,
          "ajax": null,
          "async": null,
          "process": null,
          "update": null,
          "onstart": null,
          "oncomplete": null,
          "onerror": null,
          "onsuccess": null,
          "global": null,
          "delay": null,
          "timeout": null,
          "partialSubmit": null,
          "resetValues": null,
          "ignoreAutoUpdate": null,
          "accesskey": null,
          "alt": null,
          "dir": null,
          "disabled": null,
          "image": null,
          "label": null,
          "lang": null,
          "tabindex": null,
          "title": null,
          "type": null,
          "style": null,
          "styleClass": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "icon": null,
          "iconPos": null,
          "inline": null,
          "escape": null,
          "validateClient": null,
          "partialSubmitFilter": null,
          "form": null
        }
    },
    "p:commandLink": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "actionListener": null,
          "action": null,
          "immediate": null,
          "ajax": null,
          "async": null,
          "process": null,
          "update": null,
          "onstart": null,
          "oncomplete": null,
          "onerror": null,
          "onsuccess": null,
          "global": null,
          "delay": null,
          "timeout": null,
          "partialSubmit": null,
          "resetValues": null,
          "ignoreAutoUpdate": null,
          "accesskey": null,
          "charset": null,
          "coords": null,
          "dir": null,
          "disabled": null,
          "hreflang": null,
          "rel": null,
          "rev": null,
          "shape": null,
          "tabindex": null,
          "target": null,
          "title": null,
          "type": null,
          "style": null,
          "styleClass": null,
          "onblur": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "validateClient": null,
          "partialSubmitFilter": null,
          "form": null
        }
    },
    "p:confirmDialog": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "message": null,
          "header": null,
          "severity": null,
          "width": null,
          "height": null,
          "style": null,
          "styleClass": null,
          "closable": null,
          "appendTo": null,
          "visible": null,
          "showEffect": null,
          "hideEffect": null,
          "closeOnEscape": null,
          "dir": null,
          "global": null,
          "responsive": null
        }
    },
    "p:contentFlow": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "value": null,
          "var": null,
          "style": null,
          "styleClass": null
        }
    },
    "p:contextMenu": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "for": null,
          "style": null,
          "styleClass": null,
          "model": null,
          "nodeType": null,
          "event": null,
          "beforeShow": null,
          "selectionMode": null,
          "targetFilter": null
        }
    },
    "p:dashboard": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "model": null,
          "disabled": null,
          "style": null,
          "styleClass": null
        }
    },
    "p:dataGrid": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "var": null,
          "rows": null,
          "first": null,
          "widgetVar": null,
          "columns": null,
          "paginator": null,
          "paginatorTemplate": null,
          "rowsPerPageTemplate": null,
          "rowsPerPageLabel": null,
          "currentPageReportTemplate": null,
          "pageLinks": null,
          "paginatorPosition": null,
          "paginatorAlwaysVisible": null,
          "style": null,
          "styleClass": null,
          "rowIndexVar": null,
          "emptyMessage": null,
          "lazy": null,
          "layout": null,
          "rowStatePreserved": null
        }
    },
    "p:dataList": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "var": null,
          "rows": null,
          "first": null,
          "widgetVar": null,
          "type": null,
          "itemType": null,
          "paginator": null,
          "paginatorTemplate": null,
          "rowsPerPageTemplate": null,
          "rowsPerPageLabel": null,
          "currentPageReportTemplate": null,
          "pageLinks": null,
          "paginatorPosition": null,
          "paginatorAlwaysVisible": null,
          "style": null,
          "styleClass": null,
          "rowIndexVar": null,
          "varStatus": null,
          "lazy": null,
          "emptyMessage": null,
          "itemStyleClass": null,
          "rowStatePreserved": null
        }
    },
    "p:dataScroller": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "var": null,
          "rows": null,
          "first": null,
          "widgetVar": null,
          "style": null,
          "styleClass": null,
          "chunkSize": null,
          "rowIndexVar": null,
          "mode": null,
          "scrollHeight": null,
          "lazy": null,
          "buffer": null
        }
    },
    "p:dataTable": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "var": null,
          "rows": null,
          "first": null,
          "widgetVar": null,
          "paginator": null,
          "paginatorTemplate": null,
          "rowsPerPageTemplate": null,
          "rowsPerPageLabel": null,
          "currentPageReportTemplate": null,
          "pageLinks": null,
          "paginatorPosition": null,
          "paginatorAlwaysVisible": null,
          "scrollable": null,
          "scrollHeight": null,
          "scrollWidth": null,
          "selectionMode": null,
          "selection": null,
          "rowIndexVar": null,
          "emptyMessage": null,
          "style": null,
          "styleClass": null,
          "liveScroll": null,
          "rowStyleClass": null,
          "onExpandStart": null,
          "resizableColumns": null,
          "sortBy": null,
          "sortOrder": null,
          "sortFunction": null,
          "scrollRows": null,
          "rowKey": null,
          "filterEvent": null,
          "filterDelay": null,
          "tableStyle": null,
          "tableStyleClass": null,
          "draggableColumns": null,
          "editable": null,
          "lazy": null,
          "filteredValue": null,
          "sortMode": null,
          "editMode": null,
          "editingRow": null,
          "cellSeparator": null,
          "summary": null,
          "frozenRows": null,
          "dir": null,
          "liveResize": null,
          "stickyHeader": null,
          "expandedRow": null,
          "disabledSelection": null,
          "rowSelectMode": null,
          "rowExpandMode": null,
          "dataLocale": null,
          "nativeElements": null,
          "frozenColumns": null,
          "draggableRows": null,
          "caseSensitiveSort": null,
          "skipChildren": null,
          "disabledTextSelection": null,
          "sortField": null,
          "initMode": null,
          "nullSortOrder": null,
          "tabindex": null,
          "reflow": null,
          "liveScrollBuffer": null,
          "rowHover": null,
          "rowStatePreserved": null,
          "resizeMode": null,
          "ariaRowLabel": null,
          "saveOnCellBlur": null
        }
    },
    "p:defaultCommand": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "target": null,
          "scope": null
        }
    },
    "p:diagram": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "widgetVar": null,
          "var": null,
          "style": null,
          "styleClass": null
        }
    },
    "p:dialog": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "header": null,
          "draggable": null,
          "resizable": null,
          "modal": null,
          "visible": null,
          "width": null,
          "height": null,
          "minWidth": null,
          "minHeight": null,
          "style": null,
          "styleClass": null,
          "showEffect": null,
          "hideEffect": null,
          "position": null,
          "closable": null,
          "onShow": null,
          "onHide": null,
          "appendTo": null,
          "showHeader": null,
          "footer": null,
          "dynamic": null,
          "minimizable": null,
          "maximizable": null,
          "closeOnEscape": null,
          "dir": null,
          "focus": null,
          "fitViewport": null,
          "positionType": null,
          "responsive": null
        }
    },
    "p:dock": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "model": null,
          "position": null,
          "itemWidth": null,
          "maxWidth": null,
          "proximity": null,
          "halign": null
        }
    },
    "p:draggable": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "proxy": null,
          "dragOnly": null,
          "for": null,
          "disabled": null,
          "axis": null,
          "containment": null,
          "helper": null,
          "revert": null,
          "snap": null,
          "snapMode": null,
          "snapTolerance": null,
          "zindex": null,
          "handle": null,
          "opacity": null,
          "stack": null,
          "grid": null,
          "scope": null,
          "cursor": null,
          "dashboard": null,
          "appendTo": null
        }
    },
    "p:droppable": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "for": null,
          "disabled": null,
          "hoverStyleClass": null,
          "activeStyleClass": null,
          "onDrop": null,
          "accept": null,
          "scope": null,
          "tolerance": null,
          "datasource": null
        }
    },
    "p:editor": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "controls": null,
          "height": null,
          "width": null,
          "disabled": null,
          "style": null,
          "styleClass": null,
          "onchange": null,
          "maxlength": null
        }
    },
    "p:effect": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "event": null,
          "type": null,
          "for": null,
          "speed": null,
          "delay": null,
          "queue": null
        }
    },
    "p:feedReader": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "var": null,
          "size": null
        }
    },
    "p:fieldset": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "legend": null,
          "style": null,
          "styleClass": null,
          "toggleable": null,
          "toggleSpeed": null,
          "collapsed": null,
          "tabindex": null
        }
    },
    "p:fileUpload": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "style": null,
          "styleClass": null,
          "update": null,
          "process": null,
          "fileUploadListener": null,
          "multiple": null,
          "auto": null,
          "label": null,
          "allowTypes": null,
          "fileLimit": null,
          "sizeLimit": null,
          "mode": null,
          "uploadLabel": null,
          "cancelLabel": null,
          "invalidSizeMessage": null,
          "invalidFileMessage": null,
          "fileLimitMessage": null,
          "dragDropSupport": null,
          "onstart": null,
          "oncomplete": null,
          "onerror": null,
          "disabled": null,
          "messageTemplate": null,
          "previewWidth": null,
          "skinSimple": null,
          "accept": null,
          "sequential": null
        }
    },
    "p:focus": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "for": null,
          "context": null,
          "minSeverity": null
        }
    },
    "p:fragment": {
        attrs: {
          "autoUpdate": null
        }
    },
    "p:galleria": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "value": null,
          "var": null,
          "style": null,
          "styleClass": null,
          "effect": null,
          "effectSpeed": null,
          "frameWidth": null,
          "frameHeight": null,
          "showFilmstrip": null,
          "autoPlay": null,
          "transitionInterval": null,
          "panelWidth": null,
          "panelHeight": null,
          "showCaption": null
        }
    },
    "p:gmap": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "model": null,
          "style": null,
          "styleClass": null,
          "type": null,
          "center": null,
          "zoom": null,
          "streetView": null,
          "disableDefaultUI": null,
          "navigationControl": null,
          "mapTypeControl": null,
          "draggable": null,
          "disableDoubleClickZoom": null,
          "onPointClick": null,
          "fitBounds": null,
          "scrollWheel": null
        }
    },
    "p:gmapInfoWindow": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "maxWidth": null
        }
    },
    "p:graphicImage": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "alt": null,
          "url": null,
          "width": null,
          "height": null,
          "title": null,
          "dir": null,
          "lang": null,
          "ismap": null,
          "usemap": null,
          "style": null,
          "styleClass": null,
          "onclick": null,
          "ondblclick": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "cache": null,
          "name": null,
          "library": null,
          "stream": null
        }
    },
    "p:growl": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "sticky": null,
          "showSummary": null,
          "showDetail": null,
          "globalOnly": null,
          "life": null,
          "autoUpdate": null,
          "redisplay": null,
          "for": null,
          "escape": null,
          "severity": null
        }
    },
    "p:hotkey": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "actionListener": null,
          "action": null,
          "immediate": null,
          "bind": null,
          "update": null,
          "process": null,
          "handler": null,
          "onstart": null,
          "oncomplete": null,
          "onerror": null,
          "onsuccess": null,
          "global": null,
          "delay": null,
          "timeout": null,
          "async": null,
          "partialSubmit": null,
          "resetValues": null,
          "ignoreAutoUpdate": null,
          "partialSubmitFilter": null,
          "form": null
        }
    },
    "p:idleMonitor": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "timeout": null,
          "onidle": null,
          "onactive": null
        }
    },
    "p:imageCompare": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "leftImage": null,
          "rightImage": null,
          "width": null,
          "height": null,
          "style": null,
          "styleClass": null
        }
    },
    "p:imageCropper": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "image": null,
          "alt": null,
          "aspectRatio": null,
          "minSize": null,
          "maxSize": null,
          "backgroundColor": null,
          "backgroundOpacity": null,
          "initialCoords": null,
          "boxWidth": null,
          "boxHeight": null
        }
    },
    "p:imageSwitch": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "style": null,
          "styleClass": null,
          "effect": null,
          "speed": null,
          "slideshowSpeed": null,
          "slideshowAuto": null,
          "activeIndex": null
        }
    },
    "p:inplace": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "label": null,
          "emptyLabel": null,
          "effect": null,
          "effectSpeed": null,
          "disabled": null,
          "style": null,
          "styleClass": null,
          "editor": null,
          "saveLabel": null,
          "cancelLabel": null,
          "event": null,
          "toggleable": null
        }
    },
    "p:inputMask": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "accesskey": null,
          "alt": null,
          "autocomplete": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "lang": null,
          "maxlength": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "size": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "placeholder": null,
          "widgetVar": null,
          "mask": null,
          "slotChar": null,
          "autoClear": null
        }
    },
    "p:inputNumber": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "accesskey": null,
          "alt": null,
          "autocomplete": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "lang": null,
          "maxlength": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "size": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "placeholder": null,
          "widgetVar": null,
          "type": null,
          "decimalSeparator": null,
          "thousandSeparator": null,
          "symbol": null,
          "symbolPosition": null,
          "minValue": null,
          "maxValue": null,
          "roundMethod": null,
          "decimalPlaces": null,
          "emptyValue": null,
          "inputStyle": null,
          "inputStyleClass": null
        }
    },
    "p:inputSwitch": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "onLabel": null,
          "offLabel": null,
          "label": null,
          "disabled": null,
          "onchange": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "showLabels": null,
          "onfocus": null,
          "onblur": null
        }
    },
    "p:inputText": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "accesskey": null,
          "alt": null,
          "autocomplete": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "lang": null,
          "maxlength": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "size": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "placeholder": null,
          "widgetVar": null,
          "type": null
        }
    },
    "p:inputTextarea": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "accesskey": null,
          "cols": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "lang": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "rows": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "placeholder": null,
          "widgetVar": null,
          "autoResize": null,
          "maxlength": null,
          "counter": null,
          "counterTemplate": null,
          "completeMethod": null,
          "minQueryLength": null,
          "queryDelay": null,
          "scrollHeight": null,
          "addLine": null
        }
    },
    "p:keyboard": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "accesskey": null,
          "alt": null,
          "autocomplete": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "lang": null,
          "maxlength": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "size": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "placeholder": null,
          "widgetVar": null,
          "password": null,
          "showMode": null,
          "buttonImage": null,
          "buttonImageOnly": null,
          "effect": null,
          "effectDuration": null,
          "layout": null,
          "layoutTemplate": null,
          "keypadOnly": null,
          "promptLabel": null,
          "closeLabel": null,
          "clearLabel": null,
          "backspaceLabel": null
        }
    },
    "p:keyFilter": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "for": null,
          "regEx": null,
          "inputRegEx": null,
          "mask": null,
          "testFunction": null,
          "preventPaste": null
        }
    },
    "p:knob": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "min": null,
          "max": null,
          "step": null,
          "thickness": null,
          "width": null,
          "height": null,
          "foregroundColor": null,
          "backgroundColor": null,
          "colorTheme": null,
          "disabled": null,
          "showLabel": null,
          "cursor": null,
          "labelTemplate": null,
          "onchange": null
        }
    },
    "p:layout": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "fullPage": null,
          "style": null,
          "styleClass": null,
          "onResize": null,
          "onClose": null,
          "onToggle": null,
          "resizeTitle": null,
          "collapseTitle": null,
          "expandTitle": null,
          "closeTitle": null,
          "stateful": null
        }
    },
    "p:layoutUnit": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "position": null,
          "size": null,
          "resizable": null,
          "closable": null,
          "collapsible": null,
          "header": null,
          "footer": null,
          "minSize": null,
          "maxSize": null,
          "gutter": null,
          "visible": null,
          "collapsed": null,
          "collapseSize": null,
          "style": null,
          "styleClass": null,
          "effect": null,
          "effectSpeed": null
        }
    },
    "p:lifecycle": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null
        }
    },
    "p:lightBox": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "style": null,
          "styleClass": null,
          "width": null,
          "height": null,
          "iframe": null,
          "iframeTitle": null,
          "visible": null,
          "onShow": null,
          "onHide": null
        }
    },
    "p:link": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "outcome": null,
          "includeViewParams": null,
          "fragment": null,
          "disabled": null,
          "disableClientWindow": null,
          "accesskey": null,
          "charset": null,
          "coords": null,
          "dir": null,
          "hreflang": null,
          "rel": null,
          "rev": null,
          "shape": null,
          "tabindex": null,
          "target": null,
          "title": null,
          "type": null,
          "style": null,
          "styleClass": null,
          "onblur": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "href": null,
          "escape": null
        }
    },
    "p:log": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null
        }
    },
    "p:media": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "player": null,
          "width": null,
          "height": null,
          "style": null,
          "styleClass": null,
          "cache": null
        }
    },
    "p:megaMenu": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "model": null,
          "style": null,
          "styleClass": null,
          "autoDisplay": null,
          "activeIndex": null,
          "orientation": null
        }
    },
    "p:menu": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "model": null,
          "trigger": null,
          "my": null,
          "at": null,
          "overlay": null,
          "style": null,
          "styleClass": null,
          "triggerEvent": null,
          "tabindex": null,
          "toggleable": null
        }
    },
    "p:menubar": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "model": null,
          "style": null,
          "styleClass": null,
          "autoDisplay": null,
          "tabindex": null,
          "toggleEvent": null
        }
    },
    "p:menuButton": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "model": null,
          "value": null,
          "style": null,
          "styleClass": null,
          "disabled": null,
          "iconPos": null,
          "appendTo": null,
          "menuStyleClass": null
        }
    },
    "p:menuitem": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "actionListener": null,
          "action": null,
          "immediate": null,
          "url": null,
          "target": null,
          "style": null,
          "styleClass": null,
          "onclick": null,
          "update": null,
          "process": null,
          "onstart": null,
          "disabled": null,
          "oncomplete": null,
          "onerror": null,
          "onsuccess": null,
          "global": null,
          "delay": null,
          "timeout": null,
          "async": null,
          "ajax": null,
          "icon": null,
          "iconPos": null,
          "partialSubmit": null,
          "resetValues": null,
          "ignoreAutoUpdate": null,
          "title": null,
          "outcome": null,
          "includeViewParams": null,
          "fragment": null,
          "disableClientWindow": null,
          "containerStyle": null,
          "containerStyleClass": null,
          "partialSubmitFilter": null,
          "form": null,
          "escape": null,
          "rel": null
        }
    },
    "p:message": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "showSummary": null,
          "showDetail": null,
          "for": null,
          "redisplay": null,
          "display": null,
          "escape": null,
          "severity": null,
          "style": null,
          "styleClass": null
        }
    },
    "p:messages": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "showSummary": null,
          "showDetail": null,
          "globalOnly": null,
          "redisplay": null,
          "for": null,
          "autoUpdate": null,
          "escape": null,
          "severity": null,
          "closable": null,
          "style": null,
          "styleClass": null,
          "showIcon": null
        }
    },
    "p:mindmap": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "value": null,
          "style": null,
          "styleClass": null,
          "effectSpeed": null
        }
    },
    "p:multiSelectListbox": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "style": null,
          "styleClass": null,
          "disabled": null,
          "effect": null,
          "showHeaders": null,
          "header": null
        }
    },
    "p:notificationBar": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "style": null,
          "styleClass": null,
          "position": null,
          "effect": null,
          "effectSpeed": null,
          "autoDisplay": null
        }
    },
    "p:orderList": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "var": null,
          "itemLabel": null,
          "itemValue": null,
          "style": null,
          "styleClass": null,
          "disabled": null,
          "effect": null,
          "moveUpLabel": null,
          "moveTopLabel": null,
          "moveDownLabel": null,
          "moveBottomLabel": null,
          "controlsLocation": null,
          "responsive": null
        }
    },
    "p:outputLabel": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "accesskey": null,
          "dir": null,
          "escape": null,
          "for": null,
          "lang": null,
          "tabindex": null,
          "title": null,
          "style": null,
          "styleClass": null,
          "onblur": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "indicateRequired": null
        }
    },
    "p:outputPanel": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "style": null,
          "styleClass": null,
          "autoUpdate": null,
          "deferred": null,
          "deferredMode": null,
          "global": null,
          "layout": null,
          "delay": null
        }
    },
    "p:overlayPanel": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "style": null,
          "styleClass": null,
          "for": null,
          "showEvent": null,
          "hideEvent": null,
          "showEffect": null,
          "hideEffect": null,
          "appendToBody": null,
          "onShow": null,
          "onHide": null,
          "my": null,
          "at": null,
          "dynamic": null,
          "dismissable": null,
          "showCloseIcon": null,
          "modal": null
        }
    },
    "p:panel": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "header": null,
          "footer": null,
          "toggleable": null,
          "toggleSpeed": null,
          "style": null,
          "styleClass": null,
          "collapsed": null,
          "closable": null,
          "closeSpeed": null,
          "visible": null,
          "closeTitle": null,
          "toggleTitle": null,
          "menuTitle": null,
          "toggleOrientation": null
        }
    },
    "p:panelGrid": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "columns": null,
          "style": null,
          "styleClass": null,
          "columnClasses": null,
          "layout": null,
          "role": null
        }
    },
    "p:panelMenu": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "model": null,
          "style": null,
          "styleClass": null,
          "stateful": null
        }
    },
    "p:password": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "accesskey": null,
          "alt": null,
          "autocomplete": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "lang": null,
          "maxlength": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "size": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "placeholder": null,
          "widgetVar": null,
          "feedback": null,
          "inline": null,
          "promptLabel": null,
          "weakLabel": null,
          "goodLabel": null,
          "strongLabel": null,
          "redisplay": null,
          "match": null
        }
    },
    "p:photoCam": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "style": null,
          "styleClass": null,
          "process": null,
          "update": null,
          "listener": null,
          "width": null,
          "height": null,
          "photoWidth": null,
          "photoHeight": null,
          "format": null,
          "jpegQuality": null,
          "forceFlash": null
        }
    },
    "p:pickList": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "var": null,
          "itemLabel": null,
          "itemValue": null,
          "style": null,
          "styleClass": null,
          "disabled": null,
          "effect": null,
          "effectSpeed": null,
          "addLabel": null,
          "addAllLabel": null,
          "removeLabel": null,
          "removeAllLabel": null,
          "moveUpLabel": null,
          "moveTopLabel": null,
          "moveDownLabel": null,
          "moveBottomLabel": null,
          "showSourceControls": null,
          "showTargetControls": null,
          "onTransfer": null,
          "label": null,
          "itemDisabled": null,
          "showSourceFilter": null,
          "showTargetFilter": null,
          "filterMatchMode": null,
          "filterFunction": null,
          "showCheckbox": null,
          "labelDisplay": null,
          "orientation": null,
          "responsive": null
        }
    },
    "p:poll": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "interval": null,
          "update": null,
          "listener": null,
          "immediate": null,
          "onstart": null,
          "oncomplete": null,
          "process": null,
          "onerror": null,
          "onsuccess": null,
          "global": null,
          "delay": null,
          "timeout": null,
          "async": null,
          "autoStart": null,
          "stop": null,
          "partialSubmit": null,
          "resetValues": null,
          "ignoreAutoUpdate": null,
          "partialSubmitFilter": null,
          "form": null
        }
    },
    "p:progressBar": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "value": null,
          "disabled": null,
          "ajax": null,
          "interval": null,
          "style": null,
          "styleClass": null,
          "labelTemplate": null,
          "displayOnly": null,
          "global": null
        }
    },
    "p:radioButton": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "disabled": null,
          "itemIndex": null,
          "onchange": null,
          "for": null,
          "style": null,
          "styleClass": null,
          "tabindex": null
        }
    },
    "p:rating": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "stars": null,
          "disabled": null,
          "readonly": null,
          "onRate": null,
          "style": null,
          "styleClass": null,
          "cancel": null
        }
    },
    "p:remoteCommand": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "actionListener": null,
          "action": null,
          "immediate": null,
          "name": null,
          "update": null,
          "process": null,
          "onstart": null,
          "oncomplete": null,
          "onerror": null,
          "onsuccess": null,
          "global": null,
          "delay": null,
          "timeout": null,
          "async": null,
          "autoRun": null,
          "partialSubmit": null,
          "resetValues": null,
          "ignoreAutoUpdate": null,
          "partialSubmitFilter": null,
          "form": null
        }
    },
    "p:resizable": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "for": null,
          "aspectRatio": null,
          "proxy": null,
          "handles": null,
          "ghost": null,
          "animate": null,
          "effect": null,
          "effectDuration": null,
          "maxWidth": null,
          "maxHeight": null,
          "minWidth": null,
          "minHeight": null,
          "containment": null,
          "grid": null,
          "onStart": null,
          "onResize": null,
          "onStop": null
        }
    },
    "p:ribbon": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "style": null,
          "styleClass": null,
          "activeIndex": null
        }
    },
    "p:ribbonGroup": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "label": null,
          "style": null,
          "styleClass": null
        }
    },
    "p:ring": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "var": null,
          "rows": null,
          "first": null,
          "widgetVar": null,
          "style": null,
          "styleClass": null,
          "easing": null,
          "autoplay": null,
          "autoplayDuration": null,
          "autoplayPauseOnHover": null,
          "autoplayInitialDelay": null
        }
    },
    "p:row": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "style": null,
          "styleClass": null
        }
    },
    "p:rowEditor": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "style": null,
          "styleClass": null,
          "editTitle": null,
          "cancelTitle": null,
          "saveTitle": null
        }
    },
    "p:rowExpansion": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "styleClass": null
        }
    },
    "p:rowToggler": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "expandLabel": null,
          "collapseLabel": null,
          "tabindex": null
        }
    },
    "p:schedule": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "value": null,
          "locale": null,
          "aspectRatio": null,
          "view": null,
          "initialDate": null,
          "showWeekends": null,
          "style": null,
          "styleClass": null,
          "draggable": null,
          "resizable": null,
          "showHeader": null,
          "leftHeaderTemplate": null,
          "centerHeaderTemplate": null,
          "rightHeaderTemplate": null,
          "allDaySlot": null,
          "slotDuration": null,
          "slotMinutes": null,
          "scrollTime": null,
          "firstHour": null,
          "minTime": null,
          "maxTime": null,
          "axisFormat": null,
          "timeFormat": null,
          "columnFormat": null,
          "timeZone": null,
          "clientTimeZone": null,
          "ignoreTimezone": null,
          "tooltip": null,
          "showWeekNumbers": null,
          "extender": null,
          "displayEventEnd": null,
          "weekNumberCalculation": null,
          "weekNumberCalculator": null
        }
    },
    "p:scrollPanel": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "style": null,
          "styleClass": null,
          "mode": null
        }
    },
    "p:selectBooleanButton": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "disabled": null,
          "label": null,
          "onchange": null,
          "style": null,
          "styleClass": null,
          "onLabel": null,
          "offLabel": null,
          "onIcon": null,
          "offIcon": null,
          "tabindex": null,
          "title": null,
          "onfocus": null,
          "onblur": null
        }
    },
    "p:selectBooleanCheckbox": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "disabled": null,
          "label": null,
          "onchange": null,
          "style": null,
          "styleClass": null,
          "itemLabel": null,
          "tabindex": null,
          "onfocus": null,
          "onblur": null,
          "title": null
        }
    },
    "p:selectCheckboxMenu": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "disabled": null,
          "label": null,
          "onchange": null,
          "style": null,
          "styleClass": null,
          "scrollHeight": null,
          "onShow": null,
          "onHide": null,
          "filter": null,
          "filterMatchMode": null,
          "filterFunction": null,
          "caseSensitive": null,
          "panelStyle": null,
          "panelStyleClass": null,
          "appendTo": null,
          "tabindex": null,
          "title": null,
          "showHeader": null,
          "updateLabel": null
        }
    },
    "p:selectManyButton": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "disabled": null,
          "label": null,
          "onchange": null,
          "style": null,
          "styleClass": null,
          "tabindex": null
        }
    },
    "p:selectManyCheckbox": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "disabled": null,
          "label": null,
          "layout": null,
          "columns": null,
          "onchange": null,
          "style": null,
          "styleClass": null,
          "tabindex": null
        }
    },
    "p:selectManyMenu": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "disabled": null,
          "label": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "var": null,
          "showCheckbox": null,
          "filter": null,
          "filterMatchMode": null,
          "filterFunction": null,
          "caseSensitive": null,
          "scrollHeight": null
        }
    },
    "p:selectOneButton": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "disabled": null,
          "label": null,
          "onchange": null,
          "style": null,
          "styleClass": null,
          "tabindex": null
        }
    },
    "p:selectOneListbox": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "disabled": null,
          "label": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "var": null,
          "filter": null,
          "filterMatchMode": null,
          "filterFunction": null,
          "caseSensitive": null,
          "scrollHeight": null
        }
    },
    "p:selectOneMenu": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "effect": null,
          "effectSpeed": null,
          "disabled": null,
          "label": null,
          "onchange": null,
          "style": null,
          "styleClass": null,
          "panelStyle": null,
          "panelStyleClass": null,
          "var": null,
          "height": null,
          "tabindex": null,
          "editable": null,
          "onkeydown": null,
          "onkeyup": null,
          "filter": null,
          "filterMatchMode": null,
          "filterFunction": null,
          "caseSensitive": null,
          "maxlength": null,
          "appendTo": null,
          "title": null,
          "syncTooltip": null,
          "labelTemplate": null,
          "onfocus": null,
          "onblur": null,
          "autoWidth": null
        }
    },
    "p:selectOneRadio": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "disabled": null,
          "label": null,
          "layout": null,
          "columns": null,
          "onchange": null,
          "onclick": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "plain": null
        }
    },
    "p:separator": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "title": null,
          "style": null,
          "styleClass": null
        }
    },
    "p:signature": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "backgroundColor": null,
          "color": null,
          "thickness": null,
          "style": null,
          "styleClass": null,
          "readonly": null,
          "guideline": null,
          "guidelineColor": null,
          "guidelineOffset": null,
          "guidelineIndent": null,
          "onchange": null,
          "base64Value": null
        }
    },
    "p:slideMenu": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "model": null,
          "style": null,
          "styleClass": null,
          "backLabel": null,
          "trigger": null,
          "my": null,
          "at": null,
          "overlay": null,
          "triggerEvent": null
        }
    },
    "p:slider": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "for": null,
          "display": null,
          "minValue": null,
          "maxValue": null,
          "style": null,
          "styleClass": null,
          "animate": null,
          "type": null,
          "step": null,
          "disabled": null,
          "onSlideStart": null,
          "onSlide": null,
          "onSlideEnd": null,
          "range": null,
          "displayTemplate": null
        }
    },
    "p:socket": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "channel": null,
          "transport": null,
          "fallbackTransport": null,
          "onMessage": null,
          "onError": null,
          "onClose": null,
          "onOpen": null,
          "onReconnect": null,
          "onMessagePublished": null,
          "onTransportFailure": null,
          "onLocalMessage": null,
          "autoConnect": null
        }
    },
    "p:spacer": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "width": null,
          "height": null,
          "title": null,
          "style": null,
          "styleClass": null
        }
    },
    "p:spinner": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "accesskey": null,
          "alt": null,
          "autocomplete": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "lang": null,
          "maxlength": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "size": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "placeholder": null,
          "widgetVar": null,
          "stepFactor": null,
          "min": null,
          "max": null,
          "prefix": null,
          "suffix": null
        }
    },
    "p:splitButton": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "actionListener": null,
          "action": null,
          "immediate": null,
          "widgetVar": null,
          "ajax": null,
          "async": null,
          "process": null,
          "update": null,
          "onstart": null,
          "oncomplete": null,
          "onerror": null,
          "onsuccess": null,
          "global": null,
          "delay": null,
          "timeout": null,
          "accesskey": null,
          "alt": null,
          "dir": null,
          "disabled": null,
          "image": null,
          "label": null,
          "lang": null,
          "tabindex": null,
          "title": null,
          "type": null,
          "readonly": null,
          "style": null,
          "styleClass": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "icon": null,
          "iconPos": null,
          "inline": null,
          "partialSubmit": null,
          "resetValues": null,
          "ignoreAutoUpdate": null,
          "appendTo": null,
          "partialSubmitFilter": null,
          "menuStyleClass": null,
          "form": null
        }
    },
    "p:spotlight": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "target": null,
          "active": null
        }
    },
    "p:stack": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "model": null,
          "icon": null,
          "openSpeed": null,
          "closeSpeed": null,
          "expanded": null
        }
    },
    "p:steps": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "model": null,
          "style": null,
          "styleClass": null,
          "activeIndex": null,
          "readonly": null
        }
    },
    "p:sticky": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "target": null,
          "margin": null
        }
    },
    "p:submenu": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "label": null,
          "icon": null,
          "style": null,
          "styleClass": null,
          "expanded": null
        }
    },
    "p:subTable": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "var": null,
          "rows": null,
          "first": null
        }
    },
    "p:summaryRow": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "listener": null
        }
    },
    "p:tab": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "title": null,
          "titleStyle": null,
          "titleStyleClass": null,
          "disabled": null,
          "closable": null,
          "titletip": null,
          "ariaLabel": null
        }
    },
    "p:tabMenu": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "model": null,
          "style": null,
          "styleClass": null,
          "activeIndex": null
        }
    },
    "p:tabView": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "activeIndex": null,
          "effect": null,
          "effectDuration": null,
          "dynamic": null,
          "cache": null,
          "onTabChange": null,
          "onTabShow": null,
          "style": null,
          "styleClass": null,
          "var": null,
          "value": null,
          "orientation": null,
          "onTabClose": null,
          "dir": null,
          "scrollable": null,
          "prependId": null,
          "tabindex": null
        }
    },
    "p:tagCloud": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "model": null,
          "style": null,
          "styleClass": null
        }
    },
    "p:terminal": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "style": null,
          "styleClass": null,
          "welcomeMessage": null,
          "prompt": null,
          "commandHandler": null
        }
    },
    "p:themeSwitcher": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "buttonPreText": null,
          "effect": null,
          "effectSpeed": null,
          "disabled": null,
          "label": null,
          "onchange": null,
          "style": null,
          "styleClass": null,
          "var": null,
          "height": null,
          "tabindex": null
        }
    },
    "p:tieredMenu": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "model": null,
          "style": null,
          "styleClass": null,
          "autoDisplay": null,
          "trigger": null,
          "my": null,
          "at": null,
          "overlay": null,
          "triggerEvent": null,
          "toggleEvent": null
        }
    },
    "p:timeline": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "style": null,
          "styleClass": null,
          "var": null,
          "value": null,
          "varGroup": null,
          "locale": null,
          "timeZone": null,
          "browserTimeZone": null,
          "height": null,
          "minHeight": null,
          "width": null,
          "responsive": null,
          "axisOnTop": null,
          "dragAreaWidth": null,
          "editable": null,
          "selectable": null,
          "unselectable": null,
          "zoomable": null,
          "moveable": null,
          "start": null,
          "end": null,
          "min": null,
          "max": null,
          "zoomMin": null,
          "zoomMax": null,
          "preloadFactor": null,
          "eventMargin": null,
          "eventMarginAxis": null,
          "eventStyle": null,
          "groupsChangeable": null,
          "groupsOnRight": null,
          "groupsOrder": null,
          "groupsWidth": null,
          "groupMinHeight": null,
          "snapEvents": null,
          "stackEvents": null,
          "showCurrentTime": null,
          "showMajorLabels": null,
          "showMinorLabels": null,
          "showButtonNew": null,
          "showNavigation": null,
          "timeChangeable": null,
          "dropHoverStyleClass": null,
          "dropActiveStyleClass": null,
          "dropAccept": null,
          "dropScope": null,
          "animate": null,
          "animateZoom": null
        }
    },
    "p:toolbar": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "style": null,
          "styleClass": null
        }
    },
    "p:toolbarGroup": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "align": null,
          "style": null,
          "styleClass": null
        }
    },
    "p:tooltip": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "widgetVar": null,
          "showEvent": null,
          "showEffect": null,
          "showDelay": null,
          "hideEvent": null,
          "hideEffect": null,
          "hideDelay": null,
          "for": null,
          "style": null,
          "styleClass": null,
          "globalSelector": null,
          "escape": null,
          "trackMouse": null,
          "beforeShow": null,
          "onHide": null,
          "onShow": null,
          "position": null
        }
    },
    "p:tree": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "value": null,
          "var": null,
          "dynamic": null,
          "cache": null,
          "onNodeClick": null,
          "selection": null,
          "style": null,
          "styleClass": null,
          "selectionMode": null,
          "highlight": null,
          "datakey": null,
          "animate": null,
          "orientation": null,
          "propagateSelectionUp": null,
          "propagateSelectionDown": null,
          "dir": null,
          "draggable": null,
          "droppable": null,
          "dragdropScope": null,
          "dragMode": null,
          "dropRestrict": null,
          "required": null,
          "requiredMessage": null,
          "skipChildren": null,
          "showUnselectableCheckbox": null,
          "tabindex": null,
          "nodeVar": null
        }
    },
    "p:treeNode": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "type": null,
          "styleClass": null,
          "icon": null,
          "expandedIcon": null,
          "collapsedIcon": null,
          "ariaLabel": null
        }
    },
    "p:treeTable": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "value": null,
          "var": null,
          "style": null,
          "styleClass": null,
          "selection": null,
          "selectionMode": null,
          "scrollable": null,
          "scrollHeight": null,
          "scrollWidth": null,
          "tableStyle": null,
          "tableStyleClass": null,
          "emptyMessage": null,
          "resizableColumns": null,
          "rowStyleClass": null,
          "liveResize": null,
          "required": null,
          "requiredMessage": null,
          "sortBy": null,
          "sortOrder": null,
          "sortFunction": null,
          "nativeElements": null,
          "dataLocale": null,
          "caseSensitiveSort": null,
          "skipChildren": null,
          "showUnselectableCheckbox": null,
          "nodeVar": null,
          "expandMode": null,
          "stickyHeader": null,
          "editable": null,
          "editMode": null,
          "editingRow": null,
          "cellSeparator": null
        }
    },
    "p:watermark": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "value": null,
          "for": null
        }
    },
    "p:wizard": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "step": null,
          "style": null,
          "styleClass": null,
          "flowListener": null,
          "showNavBar": null,
          "showStepStatus": null,
          "onback": null,
          "onnext": null,
          "nextLabel": null,
          "backLabel": null
        }
    },
    "h:commandButton": {
        attrs: {
          "action": null,
          "actionListener": null,
          "id": null,
          "immediate": null,
          "rendered": null,
          "value": null,
          "accesskey": null,
          "alt": null,
          "dir": null,
          "disabled": null,
          "image": null,
          "label": null,
          "lang": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "role": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "type": null,
          "binding": null
        }
    },
    "h:commandLink": {
        attrs: {
          "action": null,
          "actionListener": null,
          "id": null,
          "immediate": null,
          "rendered": null,
          "value": null,
          "accesskey": null,
          "charset": null,
          "coords": null,
          "dir": null,
          "disabled": null,
          "hreflang": null,
          "lang": null,
          "onblur": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "rel": null,
          "rev": null,
          "role": null,
          "shape": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "target": null,
          "title": null,
          "type": null,
          "binding": null
        }
    },
    "h:dataTable": {
        attrs: {
          "first": null,
          "id": null,
          "rendered": null,
          "rows": null,
          "value": null,
          "var": null,
          "bgcolor": null,
          "bodyrows": null,
          "border": null,
          "captionClass": null,
          "captionStyle": null,
          "cellpadding": null,
          "cellspacing": null,
          "columnClasses": null,
          "dir": null,
          "footerClass": null,
          "frame": null,
          "headerClass": null,
          "lang": null,
          "onclick": null,
          "ondblclick": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "role": null,
          "rowClasses": null,
          "rules": null,
          "style": null,
          "styleClass": null,
          "summary": null,
          "title": null,
          "width": null,
          "binding": null
        }
    },
    "h:form": {
        attrs: {
          "id": null,
          "prependId": null,
          "rendered": null,
          "accept": null,
          "acceptcharset": null,
          "dir": null,
          "enctype": null,
          "lang": null,
          "onclick": null,
          "ondblclick": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onreset": null,
          "onsubmit": null,
          "role": null,
          "style": null,
          "styleClass": null,
          "target": null,
          "title": null,
          "binding": null
        }
    },
    "h:graphicImage": {
        attrs: {
          "id": null,
          "rendered": null,
          "url": null,
          "value": null,
          "alt": null,
          "dir": null,
          "height": null,
          "ismap": null,
          "lang": null,
          "library": null,
          "longdesc": null,
          "name": null,
          "onclick": null,
          "ondblclick": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "role": null,
          "style": null,
          "styleClass": null,
          "title": null,
          "usemap": null,
          "width": null,
          "binding": null
        }
    },
    "h:inputFile": {
        attrs: {
          "converter": null,
          "converterMessage": null,
          "id": null,
          "immediate": null,
          "rendered": null,
          "required": null,
          "requiredMessage": null,
          "validator": null,
          "validatorMessage": null,
          "value": null,
          "valueChangeListener": null,
          "accesskey": null,
          "alt": null,
          "autocomplete": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "lang": null,
          "maxlength": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "role": null,
          "size": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "binding": null
        }
    },
    "h:inputHidden": {
        attrs: {
          "converter": null,
          "converterMessage": null,
          "id": null,
          "immediate": null,
          "rendered": null,
          "required": null,
          "requiredMessage": null,
          "validator": null,
          "validatorMessage": null,
          "value": null,
          "valueChangeListener": null,
          "binding": null
        }
    },
    "h:inputSecret": {
        attrs: {
          "converter": null,
          "converterMessage": null,
          "id": null,
          "immediate": null,
          "rendered": null,
          "required": null,
          "requiredMessage": null,
          "validator": null,
          "validatorMessage": null,
          "value": null,
          "valueChangeListener": null,
          "accesskey": null,
          "alt": null,
          "autocomplete": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "lang": null,
          "maxlength": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "redisplay": null,
          "role": null,
          "size": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "binding": null
        }
    },
    "h:inputText": {
        attrs: {
          "converter": null,
          "converterMessage": null,
          "id": null,
          "immediate": null,
          "rendered": null,
          "required": null,
          "requiredMessage": null,
          "validator": null,
          "validatorMessage": null,
          "value": null,
          "valueChangeListener": null,
          "accesskey": null,
          "alt": null,
          "autocomplete": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "lang": null,
          "maxlength": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "role": null,
          "size": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "binding": null
        }
    },
    "h:inputTextarea": {
        attrs: {
          "converter": null,
          "converterMessage": null,
          "id": null,
          "immediate": null,
          "rendered": null,
          "required": null,
          "requiredMessage": null,
          "validator": null,
          "validatorMessage": null,
          "value": null,
          "valueChangeListener": null,
          "accesskey": null,
          "cols": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "lang": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "role": null,
          "rows": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "binding": null
        }
    },
    "h:message": {
        attrs: {
          "for": null,
          "id": null,
          "rendered": null,
          "showDetail": null,
          "showSummary": null,
          "dir": null,
          "errorClass": null,
          "errorStyle": null,
          "fatalClass": null,
          "fatalStyle": null,
          "infoClass": null,
          "infoStyle": null,
          "lang": null,
          "role": null,
          "style": null,
          "styleClass": null,
          "title": null,
          "tooltip": null,
          "warnClass": null,
          "warnStyle": null,
          "binding": null
        }
    },
    "h:messages": {
        attrs: {
          "for": null,
          "globalOnly": null,
          "id": null,
          "rendered": null,
          "showDetail": null,
          "showSummary": null,
          "dir": null,
          "errorClass": null,
          "errorStyle": null,
          "fatalClass": null,
          "fatalStyle": null,
          "infoClass": null,
          "infoStyle": null,
          "lang": null,
          "layout": null,
          "role": null,
          "style": null,
          "styleClass": null,
          "title": null,
          "tooltip": null,
          "warnClass": null,
          "warnStyle": null,
          "binding": null
        }
    },
    "h:outputFormat": {
        attrs: {
          "converter": null,
          "id": null,
          "rendered": null,
          "value": null,
          "dir": null,
          "escape": null,
          "lang": null,
          "role": null,
          "style": null,
          "styleClass": null,
          "title": null,
          "binding": null
        }
    },
    "h:outputLabel": {
        attrs: {
          "converter": null,
          "id": null,
          "rendered": null,
          "value": null,
          "accesskey": null,
          "dir": null,
          "escape": null,
          "for": null,
          "lang": null,
          "onblur": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "role": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "binding": null
        }
    },
    "h:outputLink": {
        attrs: {
          "converter": null,
          "id": null,
          "rendered": null,
          "value": null,
          "accesskey": null,
          "charset": null,
          "coords": null,
          "dir": null,
          "disabled": null,
          "hreflang": null,
          "lang": null,
          "onblur": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "rel": null,
          "rev": null,
          "role": null,
          "shape": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "target": null,
          "title": null,
          "type": null,
          "binding": null
        }
    },
    "h:outputText": {
        attrs: {
          "converter": null,
          "id": null,
          "rendered": null,
          "value": null,
          "dir": null,
          "escape": null,
          "lang": null,
          "role": null,
          "style": null,
          "styleClass": null,
          "title": null,
          "binding": null
        }
    },
    "h:panelGrid": {
        attrs: {
          "id": null,
          "rendered": null,
          "bgcolor": null,
          "bodyrows": null,
          "border": null,
          "captionClass": null,
          "captionStyle": null,
          "cellpadding": null,
          "cellspacing": null,
          "columnClasses": null,
          "columns": null,
          "dir": null,
          "footerClass": null,
          "frame": null,
          "headerClass": null,
          "lang": null,
          "onclick": null,
          "ondblclick": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "role": null,
          "rowClasses": null,
          "rules": null,
          "style": null,
          "styleClass": null,
          "summary": null,
          "title": null,
          "width": null,
          "binding": null
        }
    },
    "h:panelGroup": {
        attrs: {
          "id": null,
          "rendered": null,
          "layout": null,
          "onclick": null,
          "ondblclick": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "style": null,
          "styleClass": null,
          "binding": null
        }
    },
    "h:selectBooleanCheckbox": {
        attrs: {
          "converter": null,
          "converterMessage": null,
          "id": null,
          "immediate": null,
          "rendered": null,
          "required": null,
          "requiredMessage": null,
          "validator": null,
          "validatorMessage": null,
          "value": null,
          "valueChangeListener": null,
          "accesskey": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "lang": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "role": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "binding": null
        }
    },
    "h:selectManyCheckbox": {
        attrs: {
          "converter": null,
          "converterMessage": null,
          "id": null,
          "immediate": null,
          "rendered": null,
          "required": null,
          "requiredMessage": null,
          "validator": null,
          "validatorMessage": null,
          "value": null,
          "valueChangeListener": null,
          "accesskey": null,
          "border": null,
          "collectionType": null,
          "dir": null,
          "disabled": null,
          "disabledClass": null,
          "enabledClass": null,
          "hideNoSelectionOption": null,
          "label": null,
          "lang": null,
          "layout": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "role": null,
          "selectedClass": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "unselectedClass": null,
          "binding": null
        }
    },
    "h:selectManyListbox": {
        attrs: {
          "converter": null,
          "converterMessage": null,
          "id": null,
          "immediate": null,
          "rendered": null,
          "required": null,
          "requiredMessage": null,
          "validator": null,
          "validatorMessage": null,
          "value": null,
          "valueChangeListener": null,
          "accesskey": null,
          "collectionType": null,
          "dir": null,
          "disabled": null,
          "disabledClass": null,
          "enabledClass": null,
          "hideNoSelectionOption": null,
          "label": null,
          "lang": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "role": null,
          "size": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "binding": null
        }
    },
    "h:selectManyMenu": {
        attrs: {
          "converter": null,
          "converterMessage": null,
          "id": null,
          "immediate": null,
          "rendered": null,
          "required": null,
          "requiredMessage": null,
          "validator": null,
          "validatorMessage": null,
          "value": null,
          "valueChangeListener": null,
          "accesskey": null,
          "collectionType": null,
          "dir": null,
          "disabled": null,
          "disabledClass": null,
          "enabledClass": null,
          "hideNoSelectionOption": null,
          "label": null,
          "lang": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "role": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "binding": null
        }
    },
    "h:selectOneListbox": {
        attrs: {
          "converter": null,
          "converterMessage": null,
          "id": null,
          "immediate": null,
          "rendered": null,
          "required": null,
          "requiredMessage": null,
          "validator": null,
          "validatorMessage": null,
          "value": null,
          "valueChangeListener": null,
          "accesskey": null,
          "dir": null,
          "disabled": null,
          "disabledClass": null,
          "enabledClass": null,
          "hideNoSelectionOption": null,
          "label": null,
          "lang": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "role": null,
          "size": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "binding": null
        }
    },
    "h:selectOneMenu": {
        attrs: {
          "converter": null,
          "converterMessage": null,
          "id": null,
          "immediate": null,
          "rendered": null,
          "required": null,
          "requiredMessage": null,
          "validator": null,
          "validatorMessage": null,
          "value": null,
          "valueChangeListener": null,
          "accesskey": null,
          "dir": null,
          "disabled": null,
          "disabledClass": null,
          "enabledClass": null,
          "hideNoSelectionOption": null,
          "label": null,
          "lang": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "role": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "binding": null
        }
    },
    "h:selectOneRadio": {
        attrs: {
          "converter": null,
          "converterMessage": null,
          "id": null,
          "immediate": null,
          "rendered": null,
          "required": null,
          "requiredMessage": null,
          "validator": null,
          "validatorMessage": null,
          "value": null,
          "valueChangeListener": null,
          "accesskey": null,
          "border": null,
          "dir": null,
          "disabled": null,
          "disabledClass": null,
          "enabledClass": null,
          "hideNoSelectionOption": null,
          "label": null,
          "lang": null,
          "layout": null,
          "onblur": null,
          "onchange": null,
          "onclick": null,
          "ondblclick": null,
          "onfocus": null,
          "onkeydown": null,
          "onkeypress": null,
          "onkeyup": null,
          "onmousedown": null,
          "onmousemove": null,
          "onmouseout": null,
          "onmouseover": null,
          "onmouseup": null,
          "onselect": null,
          "readonly": null,
          "role": null,
          "style": null,
          "styleClass": null,
          "tabindex": null,
          "title": null,
          "binding": null
        }
    },
    "h:column": {
        attrs: {
          "rendered": null,
          "binding": null,
          "id": null,
          "footerClass": null,
          "headerClass": null,
          "rowHeader": null
        }
    },
    "f:actionListener": {
        attrs: {
          "type": null,
          "binding": null
        }
    },
    "f:attribute": {
        attrs: {
          "name": null,
          "value": null
        }
    },
    "f:convertDateTime": {
        attrs: {
          "dateStyle": null,
          "locale": null,
          "pattern": null,
          "timeStyle": null,
          "timeZone": null,
          "type": null,
          "binding": null
        }
    },
    "f:convertNumber": {
        attrs: {
          "currencyCode": null,
          "currencySymbol": null,
          "groupingUsed": null,
          "integerOnly": null,
          "locale": null,
          "maxFractionDigits": null,
          "maxIntegerDigits": null,
          "minFractionDigits": null,
          "minIntegerDigits": null,
          "pattern": null,
          "type": null,
          "binding": null
        }
    },
    "f:converter": {
        attrs: {
          "converterId": null,
          "binding": null
        }
    },
    "f:facet": {
        attrs: {
          "name": null
        }
    },
    "f:loadBundle": {
        attrs: {
          "basename": null,
          "var": null
        }
    },
    "f:param": {
        attrs: {
          "binding": null,
          "id": null,
          "name": null,
          "value": null
        }
    },
    "f:viewParam": {
        attrs: {
          "converter": null,
          "converterMessage": null,
          "id": null,
          "required": null,
          "requiredMessage": null,
          "validator": null,
          "validatorMessage": null,
          "value": null,
          "valueChangeListener": null,
          "maxlength": null,
          "binding": null
        }
    },
    "f:phaseListener": {
        attrs: {
          "type": null,
          "binding": null
        }
    },
    "f:selectItem": {
        attrs: {
          "binding": null,
          "id": null,
          "itemDescription": null,
          "itemDisabled": null,
          "itemLabel": null,
          "escape": null,
          "itemValue": null,
          "value": null,
          "noSelectionOption": null
        }
    },
    "f:selectItems": {
        attrs: {
          "binding": null,
          "id": null,
          "value": null,
          "var": null,
          "itemValue": null,
          "itemLabel": null,
          "itemDescription": null,
          "itemDisabled": null,
          "itemLabelEscaped": null,
          "noSelectionOption": null
        }
    },
    "f:setPropertyActionListener": {
        attrs: {
          "value": null,
          "target": null
        }
    },
    "f:subview": {
        attrs: {
          "binding": null,
          "id": null,
          "rendered": null
        }
    },
    "f:validateDoubleRange": {
        attrs: {
          "maximum": null,
          "minimum": null,
          "binding": null
        }
    },
    "f:validateLength": {
        attrs: {
          "maximum": null,
          "minimum": null,
          "binding": null
        }
    },
    "f:validateLongRange": {
        attrs: {
          "maximum": null,
          "minimum": null,
          "binding": null
        }
    },
    "f:validateRegex": {
        attrs: {
          "pattern": null,
          "binding": null
        }
    },
    "f:validator": {
        attrs: {
          "validatorId": null,
          "binding": null
        }
    },
    "f:valueChangeListener": {
        attrs: {
          "type": null,
          "binding": null
        }
    },
    "f:verbatim": {
        attrs: {
          "escape": null,
          "rendered": null
        }
    },
    "f:view": {
        attrs: {
          "locale": null,
          "renderKitId": null,
          "beforePhase": null,
          "afterPhase": null
        }
    },
    "pe:convertJson": {
        attrs: {
          "type": null
        }
    },
    "pe:convertLocale": {
        attrs: {
          "separator": null
        }
    },
    "pe:javascript": {
        attrs: {
          "execute": null,
          "disabled": null,
          "event": null
        }
    },
    "pe:ckEditor": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "value": null,
          "height": null,
          "width": null,
          "theme": null,
          "skin": null,
          "toolbar": null,
          "readonly": null,
          "interfaceColor": null,
          "language": null,
          "defaultLanguage": null,
          "contentsCss": null,
          "customConfig": null,
          "tabindex": null,
          "label": null,
          "converter": null,
          "required": null,
          "escape": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "validator": null
        }
    },
    "pe:codeMirror": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "value": null,
          "completeMethod": null,
          "theme": null,
          "mode": null,
          "indentUnit": null,
          "smartIndent": null,
          "tabSize": null,
          "electricChars": null,
          "keyMap": null,
          "lineWrapping": null,
          "lineNumbers": null,
          "firstLineNumber": null,
          "gutter": null,
          "fixedGutter": null,
          "readonly": null,
          "matchBrackets": null,
          "workTime": null,
          "workDelay": null,
          "pollInterval": null,
          "undoDepth": null,
          "tabindex": null,
          "label": null,
          "extraKeys": null,
          "converter": null,
          "process": null,
          "onstart": null,
          "oncomplete": null,
          "onerror": null,
          "onsuccess": null,
          "global": null,
          "async": null,
          "escape": null,
          "escapeSuggestions": null,
          "required": null,
          "requiredMessage": null,
          "validator": null,
          "valueChangeListener": null
        }
    },
    "pe:dynaForm": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "value": null,
          "var": null,
          "varContainerId": null,
          "autoSubmit": null,
          "openExtended": null,
          "buttonBarPosition": null,
          "style": null,
          "styleClass": null,
          "columnClasses": null
        }
    },
    "pe:dynaFormControl": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "type": null,
          "for": null,
          "style": null,
          "styleClass": null
        }
    },
    "pe:fluidGrid": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "value": null,
          "var": null,
          "varContainerId": null,
          "style": null,
          "styleClass": null,
          "hGutter": null,
          "vGutter": null,
          "fitWidth": null,
          "originLeft": null,
          "originTop": null,
          "resizeBound": null,
          "stamp": null,
          "transitionDuration": null,
          "hasImages": null
        }
    },
    "pe:fluidGridItem": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "type": null,
          "styleClass": null
        }
    },
    "pe:exporter": {
        attrs: {
          "target": null,
          "type": null,
          "fileName": null,
          "tableTitle": null,
          "pageOnly": null,
          "preProcessor": null,
          "postProcessor": null,
          "encoding": null,
          "selectionOnly": null,
          "subTable": null,
          "facetBackground": null,
          "facetFontSize": null,
          "facetFontColor": null,
          "facetFontStyle": null,
          "fontName": null,
          "cellFontSize": null,
          "cellFontColor": null,
          "cellFontStyle": null,
          "datasetPadding": null,
          "orientation": null,
          "skipComponents": null
        }
    },
    "pe:head": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "title": null,
          "shortcutIcon": null
        }
    },
    "pe:imageAreaSelect": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "for": null,
          "aspectRatio": null,
          "autoHide": null,
          "fadeSpeed": null,
          "handles": null,
          "hide": null,
          "imageHeight": null,
          "imageWidth": null,
          "movable": null,
          "persistent": null,
          "resizable": null,
          "show": null,
          "zIndex": null,
          "maxHeight": null,
          "maxWidth": null,
          "minHeight": null,
          "minWidth": null,
          "parentSelector": null,
          "keyboardSupport": null
        }
    },
    "pe:imageRotateAndResize": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "for": null
        }
    },
    "pe:remoteCommand": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "actionListener": null,
          "action": null,
          "immediate": null,
          "name": null,
          "update": null,
          "process": null,
          "onstart": null,
          "oncomplete": null,
          "onerror": null,
          "onsuccess": null,
          "global": null,
          "async": null,
          "partialSubmit": null,
          "autoRun": null
        }
    },
    "pe:assignableParam": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "name": null,
          "assignTo": null,
          "converter": null
        }
    },
    "pe:methodParam": {
        attrs: {
          "id": null,
          "binding": null,
          "name": null,
          "converter": null
        }
    },
    "pe:methodSignature": {
        attrs: {
          "parameters": null
        }
    },
    "pe:tooltip": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "widgetVar": null,
          "global": null,
          "shared": null,
          "autoShow": null,
          "mouseTracking": null,
          "fixed": null,
          "adjustX": null,
          "adjustY": null,
          "atPosition": null,
          "myPosition": null,
          "showEvent": null,
          "showDelay": null,
          "showEffect": null,
          "showEffectLength": null,
          "hideEvent": null,
          "hideDelay": null,
          "hideEffect": null,
          "hideEffectLength": null,
          "for": null
        }
    },
    "pe:layout": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "fullPage": null,
          "options": null,
          "style": null,
          "styleClass": null,
          "state": null,
          "stateCookie": null,
          "resizerTip": null,
          "togglerTipOpen": null,
          "togglerTipClosed": null,
          "maskPanesEarly": null
        }
    },
    "pe:layoutPane": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "position": null,
          "styleHeader": null,
          "styleClassHeader": null,
          "styleContent": null,
          "styleClassContent": null,
          "resizable": null,
          "closable": null,
          "size": null,
          "minSize": null,
          "maxSize": null,
          "minWidth": null,
          "maxWidth": null,
          "minHeight": null,
          "maxHeight": null,
          "spacingOpen": null,
          "spacingClosed": null,
          "initClosed": null,
          "initHidden": null,
          "resizeWhileDragging": null,
          "maskContents": null,
          "maskObjects": null
        }
    },
    "pe:masterDetail": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "level": null,
          "contextValue": null,
          "selectLevelListener": null,
          "showBreadcrumb": null,
          "showAllBreadcrumbItems": null,
          "breadcrumbAboveHeader": null,
          "style": null,
          "styleClass": null
        }
    },
    "pe:masterDetailLevel": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "level": null,
          "contextVar": null,
          "levelLabel": null,
          "levelDisabled": null
        }
    },
    "pe:selectDetailLevel": {
        attrs: {
          "contextValue": null,
          "listener": null,
          "level": null,
          "step": null,
          "preserveInputs": null,
          "resetInputs": null,
          "event": null
        }
    },
    "pe:blockPanel": {
        attrs: {
        }
    },
    "pe:spotlight": {
        attrs: {
          "id": null,
          "rendered": null,
          "style": null,
          "styleClass": null,
          "widgetVar": null,
          "blocked": null
        }
    },
    "pe:blockUI": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "css": null,
          "cssOverlay": null,
          "source": null,
          "target": null,
          "content": null,
          "event": null,
          "autoShow": null,
          "timeout": null,
          "centerX": null,
          "centerY": null
        }
    },
    "pe:timePicker": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "widgetVar": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "accesskey": null,
          "alt": null,
          "autocomplete": null,
          "dir": null,
          "disabled": null,
          "label": null,
          "style": null,
          "styleClass": null,
          "timeSeparator": null,
          "showPeriod": null,
          "dialogPosition": null,
          "inputPosition": null,
          "mode": null,
          "startHours": null,
          "endHours": null,
          "startMinutes": null,
          "endMinutes": null,
          "intervalMinutes": null,
          "rows": null,
          "showHours": null,
          "showMinutes": null,
          "showCloseButton": null,
          "showDeselectButton": null,
          "showNowButton": null,
          "onHourShow": null,
          "onMinuteShow": null,
          "showOn": null,
          "locale": null,
          "minHour": null,
          "minMinute": null,
          "maxHour": null,
          "maxMinute": null
        }
    },
    "pe:triStateCheckbox": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "disabled": null,
          "label": null,
          "onchange": null,
          "style": null,
          "styleClass": null,
          "stateOneIcon": null,
          "stateTwoIcon": null,
          "stateThreeIcon": null,
          "itemLabel": null,
          "stateOneTitle": null,
          "stateTwoTitle": null,
          "stateThreeTitle": null,
          "tabindex": null
        }
    },
    "pe:triStateManyCheckbox": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null,
          "converter": null,
          "immediate": null,
          "required": null,
          "validator": null,
          "valueChangeListener": null,
          "requiredMessage": null,
          "converterMessage": null,
          "validatorMessage": null,
          "widgetVar": null,
          "disabled": null,
          "label": null,
          "layout": null,
          "onchange": null,
          "style": null,
          "styleClass": null,
          "stateOneIcon": null,
          "stateTwoIcon": null,
          "stateThreeIcon": null,
          "stateOneTitle": null,
          "stateTwoTitle": null,
          "stateThreeTitle": null,
          "tabindex": null
        }
    },
    "pe:waypoint": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "widgetVar": null,
          "for": null,
          "forContext": null,
          "enabled": null,
          "horizontal": null,
          "offset": null,
          "continuous": null,
          "triggerOnce": null
        }
    },
    "pe:switch": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "value": null
        }
    },
    "pe:case": {
        attrs: {
          "id": null,
          "binding": null,
          "value": null,
          "style": null,
          "styleClass": null
        }
    },
    "pe:defaultCase": {
        attrs: {
          "id": null,
          "binding": null,
          "style": null,
          "styleClass": null
        }
    },
    "pe:qrCode": {
        attrs: {
          "id": null,
          "binding": null,
          "renderMethod": null,
          "renderMode": null,
          "minVersion": null,
          "maxVersion": null,
          "ecLevel": null,
          "leftOffset": null,
          "topOffset": null,
          "size": null,
          "text": null,
          "fontName": null,
          "fontColor": null,
          "label": null,
          "fillColor": null,
          "background": null,
          "radius": null,
          "quiet": null,
          "mSize": null,
          "mPosX": null,
          "mPosY": null
        }
    },
    "pe:analogClock": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "colorTheme": null,
          "widgetVar": null,
          "mode": null,
          "startTime": null,
          "width": null
        }
    },
    "pe:gravatar": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "size": null,
          "style": null,
          "notFound": null,
          "value": null,
          "qrCode": null,
          "secure": null
        }
    },
    "pe:documentViewer": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "width": null,
          "height": null,
          "style": null,
          "url": null,
          "cache": null,
          "locale": null,
          "page": null,
          "zoom": null,
          "name": null,
          "library": null,
          "value": null
        }
    },
    "pe:gChart": {
        attrs: {
          "id": null,
          "rendered": null,
          "binding": null,
          "width": null,
          "height": null,
          "title": null,
          "value": null,
          "widgetVar": null
        }
    },
    "pe:timer": {
        attrs: {
          "id": null,
          "rendered": null,
          "visible": null,
          "binding": null,
          "widgetVar": null,
          "timeout": null,
          "update": null,
          "listener": null,
          "immediate": null,
          "onstart": null,
          "oncomplete": null,
          "process": null,
          "onerror": null,
          "onsuccess": null,
          "global": null,
          "delay": null,
          "format": null,
          "formatFunction": null,
          "async": null,
          "autoStart": null,
          "forward": null,
          "singleRun": null,
          "partialSubmit": null,
          "resetValues": null,
          "ignoreAutoUpdate": null,
          "ontimerstep": null,
          "ontimercomplete": null
        }
    },
    "ui:component": {
        attrs: {
          "id": null,
          "binding": null
        }
    },
    "ui:composition": {
        attrs: {
          "template": null
        }
    },
    "ui:debug": {
        attrs: {
          "hotkey": null,
          "rendered": null
        }
    },
    "ui:define": {
        attrs: {
          "name": null
        }
    },
    "ui:decorate": {
        attrs: {
          "template": null
        }
    },
    "ui:fragment": {
        attrs: {
          "id": null,
          "binding": null
        }
    },
    "ui:include": {
        attrs: {
          "src": null
        }
    },
    "ui:insert": {
        attrs: {
          "name": null
        }
    },
    "ui:param": {
        attrs: {
          "name": null,
          "value": null
        }
    },
    "ui:repeat": {
        attrs: {
          "offset": null,
          "size": null,
          "step": null,
          "value": null,
          "var": null,
          "varStatus": null
        }
    },
    "ui:remove": {
        attrs: {
        }
    }
    // primefaces
  };

  var globalAttrs = {
    accesskey: ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"],
    "class": null,
    contenteditable: ["true", "false"],
    contextmenu: null,
    dir: ["ltr", "rtl", "auto"],
    draggable: ["true", "false", "auto"],
    dropzone: ["copy", "move", "link", "string:", "file:"],
    hidden: ["hidden"],
    id: null,
    inert: ["inert"],
    itemid: null,
    itemprop: null,
    itemref: null,
    itemscope: ["itemscope"],
    itemtype: null,
    lang: ["en", "es"],
    spellcheck: ["true", "false"],
    style: null,
    tabindex: ["1", "2", "3", "4", "5", "6", "7", "8", "9"],
    title: null,
    translate: ["yes", "no"],
    onclick: null,
    rel: ["stylesheet", "alternate", "author", "bookmark", "help", "license", "next", "nofollow", "noreferrer", "prefetch", "prev", "search", "tag"]
  };
  function populate(obj) {
    for (var attr in globalAttrs) if (globalAttrs.hasOwnProperty(attr))
      obj.attrs[attr] = globalAttrs[attr];
  }

  populate(s);
  for (var tag in data) if (data.hasOwnProperty(tag) && data[tag] != s) {
    populate(data[tag]);
  }  

  CodeMirror.htmlSchema = data;
  function htmlHint(cm, options) {
    var local = {schemaInfo: data};
    if (options) for (var opt in options) local[opt] = options[opt];
    return CodeMirror.hint.xml(cm, local);
  }
  CodeMirror.registerHelper("hint", "html", htmlHint);
});
