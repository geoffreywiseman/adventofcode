/*:
 
 # Advent of Code
 
 ## Day 2 : Bathroom Codes
 
 ### Part One - Determine Code
 
 
*/
import Cocoa

//var puzzleInput = "ULL\nRRDDD\nLURDL\nUUUUD" // 1985
let resource = Bundle.main.url(forResource: "puzzleInput", withExtension: "txt")
let puzzleInput = try String(contentsOf:resource!, encoding:String.Encoding.utf8)


class SquareKeypad : Keypad {
    let keys: [[Character]] = [ ["1","2","3"], ["4","5","6"], ["7","8","9"] ]
    var position = (1,1)
    
    var key: Character {
       return keys[position.1][position.0]
    }
    
    func move( direction: Direction ) {
        switch( direction ) {
        case .Up:
            if( position.1 > 0 ) {
                position.1 -= 1;
            }
        case .Down:
            if( position.1 < 2 ) {
                position.1 += 1;
            }
        case .Left:
            if( position.0 > 0 ) {
                position.0 -= 1;
            }
        case .Right:
            if( position.0 < 2 ) {
                position.0 += 1;
            }
        }
    }
}

let solver = try Solver(instructions: puzzleInput, keypad:SquareKeypad() )
print( "The combination is: \(solver.combination)" )

//: [Next](@next)
