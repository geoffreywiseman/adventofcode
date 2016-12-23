// jshint esnext:true

// Part 1
const readline = require('readline');
const fs = require('fs');
const abba = /([a-z])(?!\1)([a-z])\2\1/;
const inner_abba = /\[[a-z]*([a-z])(?!\1)([a-z])\2\1/;

var count = 0;

readline.createInterface({
  input: fs.createReadStream('puzzleInput.txt')
}).on('line', checkTlsSupport).on('close', function() {
	console.log( `File complete. ${count} addresses support TLS.` );
});

function checkTlsSupport(line) {
	var result = supportsTls(line);
	if( result ) {
		var abba_match = line.match(abba)[0];
		var inner_abba_match = line.match(inner_abba);
		console.log( `Found abba (${abba_match}) but no inner abba in: ${line}` );
		count ++;
	}
}

function supportsTls(line) {
	return !inner_abba.test(line) && abba.test( line );
}