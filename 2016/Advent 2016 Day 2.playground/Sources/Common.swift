import Foundation

public protocol Keypad {
    var key:Character { get }
    func move(direction:Direction)
}

public class Solver {
    public private(set) var combination: [Character]
    
    public init( instructions: String, keypad:Keypad ) throws {
        combination = Array()
        try solve( lines: instructions.components( separatedBy:"\n" ), keypad:keypad )
    }
    
    public func solve( lines: [String], keypad:Keypad ) throws {
        print( "Solving puzzle with \(lines.count) lines...")
        try lines
            .filter { line in line.characters.count > 0 }
            .forEach { line in
                try followInstructions(line,keypad:keypad)
                combination.append(keypad.key)
        }
    }
    
    private func followInstructions( _ instructions: String, keypad:Keypad ) throws {
        print( "Following instructions: \(instructions)")
        try instructions.characters.forEach { char in
            if let dir = Direction.fromCharacter( char: char ) {
                keypad.move(direction: dir)
            } else {
                throw ParseError.BadDirection(char:char)
            }
        }
    }
}


public enum ParseError: Error {
    case BadDirection(char:Character)
}

public enum Direction {
    case Up
    case Down
    case Left
    case Right
    
    public static func fromCharacter( char: Character ) -> Direction? {
        switch( char ) {
        case "U":
            return .Up
        case "D":
            return .Down
        case "L":
            return .Left
        case "R":
            return .Right
        default:
            return nil
        }
    }
}
