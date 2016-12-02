var directions = [ 
    { facing: "N", x: 0, y: 1 },
    { facing: "E", x: 1, y: 0 },
    { facing: "S", x: 0, y: -1 },
    { facing: "W", x: -1, y: 0 }
];
//    ["N",1,0], ["E",1,0], ["S",-1,0], ["W",0,-1] ];

function changeDirection( currentDirection, turn ) {
    if( turn == "R" )
        return (currentDirection + 1) % 4;
    else 
        return (currentDirection + 3) % 4;
}

function calculateDistance( input ) {
    var x = 0;
    var y = 0;
    var direction = 0;
    
    var instructions = input.split( /,\s*/ );
    for( var instruction of instructions ) {
        var turn = instruction.charAt(0);
        var blocks = Number.parseInt(instruction.slice(1));
        direction = changeDirection( direction, turn );
        //console.log( "Turn " + turn + " (now facing " + directions[direction].facing + "), go " + blocks + " blocks." );
        x += blocks * directions[direction].x;
        y += blocks * directions[direction].y;
    }
    console.log( "Final position: " + x + ", " + y );
    
    return Math.abs(x) + Math.abs(y);
}
    
var input = "R1, R3, L2, L5, L2, L1, R3, L4, R2, L2, L4, R2, L1, R1, L2, R3, L1, L4, R2, L5, R3, R4, L1, R2, L1, R3, L4, R5, L4, L5, R5, L3, R2, L3, L3, R1, R3, L4, R2, R5, L4, R1, L1, L1, R5, L2, R1, L2, R188, L5, L3, R5, R1, L2, L4, R3, R5, L3, R3, R45, L4, R4, R72, R2, R3, L1, R1, L1, L1, R192, L1, L1, L1, L4, R1, L2, L5, L3, R5, L3, R3, L4, L3, R1, R4, L2, R2, R3, L5, R3, L1, R1, R4, L2, L3, R1, R3, L4, L3, L4, L2, L2, R1, R3, L5, L1, R4, R2, L4, L1, R3, R3, R1, L5, L2, R4, R4, R2, R1, R5, R5, L4, L1, R5, R3, R4, R5, R3, L1, L2, L4, R1, R4, R5, L2, L3, R4, L4, R2, L2, L4, L2, R5, R1, R4, R3, R5, L4, L4, L5, L5, R3, R4, L1, L3, R2, L2, R1, L3, L5, R5, R5, R3, L4, L2, R4, R5, R1, R4, L3";
var distance = calculateDistance( input );
console.log( "Distance to EBHQ: " + distance );