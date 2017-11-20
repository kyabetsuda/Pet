function handleDownload() {
	var data1 = [];
	var tr1 = $('table#nameTable tr');
	for( var i = 0, l = tr1.length; i < l; i++){
		var tr2 = tr1.eq(i).children();
		var data2 = [];
		for( var j = 0, m = tr2.length; j < m; j++){
			data2.push(tr2.eq(j).text());
		}
		data1.push(data2);
	}
	
    var bom = new Uint8Array([0xEF, 0xBB, 0xBF]);
    var content = data1.join('\n');
    var blob = new Blob([ bom, content ], { "type" : "text/csv" });

    if (window.navigator.msSaveBlob) { 
        window.navigator.msSaveBlob(blob, "today.csv"); 

        // msSaveOrOpenBlobの場合はファイルを保存せずに開ける
        window.navigator.msSaveOrOpenBlob(blob, "today.csv"); 
    } else {
        document.getElementById("download").href = window.URL.createObjectURL(blob);
    }
}