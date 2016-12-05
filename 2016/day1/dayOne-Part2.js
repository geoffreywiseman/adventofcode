var directions = [ [0,1,"N"], [1,0,"E"], [0,-1,"S"], [-1,0,"W"] ];
var positions = [ ];

function changeDirection( currentDirection, turn ) {
    if( turn == "R" )
        return (currentDirection + 1) % 4;
    else 
        return (currentDirection + 3) % 4;
}

function firstRevisit( input ) {
    var direction = 0;
    var position = [0,0];
    positions.push(position);
    
    var instructions = input.split( /,\s*/ );
    for( var instruction of instructions ) {
        var turn = instruction.charAt(0);
        direction = changeDirection( direction, turn );
        var blocks = Number.parseInt(instruction.slice(1));

        for( var index = 0; index < blocks; index ++ ) {
            var x = position[0] + directions[direction][0];
            var y = position[1] + directions[direction][1];
            //console.log( "at " + position + ", facing " + directions[direction][2] + ", going to " + x + "," + y )
            position = [x,y];
            if( revisiting(position) )
                return position;
            else
                positions.push(position);
        }
    }
    return false;
}

function revisiting( current ) {
    for( position of positions ) {
        if( current[0] == position[0] && current[1] == position[1] ) {
            return true;
        }
    }
    return false;
}
    
var input = "R1, R3, L2, L5, L2, L1, R3, L4, R2, L2, L4, R2, L1, R1, L2, R3, L1, L4, R2, L5, R3, R4, L1, R2, L1, R3, L4, R5, L4, L5, R5, L3, R2, L3, L3, R1, R3, L4, R2, R5, L4, R1, L1, L1, R5, L2, R1, L2, R188, L5, L3, R5, R1, L2, L4, R3, R5, L3, R3, R45, L4, R4, R72, R2, R3, L1, R1, L1, L1, R192, L1, L1, L1, L4, R1, L2, L5, L3, R5, L3, R3, L4, L3, R1, R4, L2, R2, R3, L5, R3, L1, R1, R4, L2, L3, R1, R3, L4, L3, L4, L2, L2, R1, R3, L5, L1, R4, R2, L4, L1, R3, R3, R1, L5, L2, R4, R4, R2, R1, R5, R5, L4, L1, R5, R3, R4, R5, R3, L1, L2, L4, R1, R4, R5, L2, L3, R4, L4, R2, L2, L4, L2, R5, R1, R4, R3, R5, L4, L4, L5, L5, R3, R4, L1, L3, R2, L2, R1, L3, L5, R5, R5, R3, L4, L2, R4, R5, R1, R4, L3";
var revisit = firstRevisit( input );
console.log( "First revisit " + revisit );