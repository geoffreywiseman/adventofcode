// jshint esnext:true

// Part 2
const readline = require('readline');
const fs = require('fs');
const splitter = /\[|\]/;

var count = 0;

readline.createInterface({
  input: fs.createReadStream('puzzleInput.txt')
}).on('line', checkSslSupport).on('close', function() {
	console.log( `File complete. ${count} addresses support SSL.` );
});

function checkSslSupport(line) {
	var result = supportsSsl(line);
	if( result ) {
		count ++;
	}
}

function supportsSsl(line) {
	var sequences = line.split(splitter);
	var abas = sequences.filter(supernet).reduce(findAbas,[]);
	if( abas.length === 0 )
		return false;
	return sequences.filter(hypernet).some(function(sequence) {
		return abas.some( function(aba) {
			if( hasBab(sequence,aba) ) {
				return true;
			}
			return false;
		});
	});
}

// These filters rely on no malformed or nested braces
function supernet(sequence,index) {
	return index % 2 === 0;
}

function hypernet(sequence,index) {
	return index % 2 === 1;
}

function findAbas(abas,sequence) {
	var aba = /([a-z])(?!\1)([a-z])\1/g;
	while( ( match = aba.exec(sequence) ) !== null ) {
		abas.push( match[0] );
		aba.lastIndex = match.index + 1;
	}
	return abas;
}

function hasBab(sequence,aba) {
	return sequence.includes( aba[1] + aba[0] + aba[1] );
}