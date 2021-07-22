/* JS design standard */

/* function executada no 'onload' do 'body', não remova! */
function init() {

}

/*
	Exemplo de leitura e atribuição de valor no inputText 
*/
function onClickBtnInput1() {
    console.log("Input Nome: " + PF('inputNome1').jq.val());
    PF('inputNome1').jq.val("Valor via JS");
}

/*
	Exemplo de como obter o index da TabView 
*/
function tabViewFocus() {
    console.log("Index da TabView: " + PF('tabViewSamples').getActiveIndex());
}

/*
	Exemplo de como obter mudar o index da TabView 
*/
function onClickGoTabInput() {
    PF('tabViewSamples').select(0, false);
}

/*
	Exemplo de como mudar o tema.
*/
function onClickChangeTheme() {
    PrimeFaces.changeTheme('aristo');
}

/*
	Função para codificar texto em html
*/
function encodeEntities(s){
	return $("<div/>").text(s).html();
}

/*
	Função para decodificar html em texto
*/
function decodeEntities(s){
	return $("<div/>").html(s).text();
}
