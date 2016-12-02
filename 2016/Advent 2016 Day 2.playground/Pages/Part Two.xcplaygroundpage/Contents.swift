//: [Previous](@previous)

/*:
 
 # Advent of Code
 
 ## Day 2 : Bathroom Codes
 
 ### Part Two - Diamond Keypad
 
 
 */
import Cocoa

let resource = Bundle.main.url(forResource: "puzzleInput", withExtension: "txt")
let puzzleInput = try String(contentsOf:resource!, encoding:String.Encoding.utf8)


class DiamondKeypad : Keypad {
    let keys: [[Character]] = [
        [" ", " ", "1", " ", " "],
        [" ", "2", "3", "4", " "],
        ["5", "6", "7", "8", "9"],
        [" ", "A", "B", "C", " "],
        [" ", " ", "D", " ", " "],
    ]
    
    var currentPosition = (2,0)
    
    var currentX: Int {
        get {
            return currentPosition.0
        }
        set {
            currentPosition.0 = newValue
        }
    }
    
    var currentY: Int {
        get {
            return currentPosition.1
        }
        set {
            currentPosition.1 = newValue
        }
    }
    
    var key: Character {
        return keyAt(x:currentX, y:currentY)
    }
    
    private func keyAt(x:Int, y:Int) -> Character {
        return keys[y][x]
    }
    
    private func hasKey(x:Int, y:Int) -> Bool {
        return keyAt(x:x, y:y) != " "
    }
    
    func move( direction: Direction ) {
        switch( direction ) {
        case .Up:
            if( currentY > 0 && hasKey(x:currentX, y:currentY-1) ) {
                currentY -= 1;
            }
        case .Down:
            if( currentY < 4 && hasKey(x:currentX, y:currentY + 1) ) {
                currentY += 1;
            }
        case .Left:
            if( currentX > 0 && hasKey(x:currentX - 1, y:currentY) ) {
                currentX -= 1;
            }
        case .Right:
            if( currentX < 4 && hasKey(x:currentX + 1, y:currentY) ) {
                currentX += 1;
            }
        }
    }
}

let solver = try Solver(instructions: puzzleInput, keypad:DiamondKeypad())
print( "The combination is: \(solver.combination)" )
