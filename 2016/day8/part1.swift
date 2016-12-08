import Foundation

class Screen : CustomStringConvertible { 
	var rows:Int
	var columns:Int
	var pixels:[[Bool]]

	var description: String {
		return pixels.map(rowDescription).joined( separator: "\n" )
	}

	var count: Int {
		return pixels.reduce(0) { $0 + $1.filter { $0 }.count }
	}

	init(rows:Int, columns:Int) {
		self.rows = rows
		self.columns = columns
		pixels = Array<Array<Bool>>()
		for _ in 0 ..< rows {
			pixels.append( Array(repeating:false, count:columns) )
		}
	}

	public func rect(width:Int, height:Int) {
		for row in 0 ..< height {
			for col in 0 ..< width  {
				pixels[row][col] = true
			}
		}
	}

	public func rotateColumn(index: Int, count: Int ) {
		let rotations = count % rows
		guard rotations > 0 else { return }
		setColumn(getColumn(index).rotated(rotations), index:index);
	}

	public func rotateRow(index:Int, count:Int) {
		let rotations = count % columns
		guard rotations > 0 else { return }
		setRow(getRow(index).rotated(rotations), index:index);
	}

	private func rowDescription( row: [Bool] ) -> String {
		return row.map { pixel in return pixel ? "#" : "." }
			.joined( separator: "" )
	}

	private func getRow(_ index:Int) -> Array<Bool> {
		return pixels[index]
	}

	private func setRow(_ row:Array<Bool>, index:Int) {
		pixels[index] = row
	}

	private func getColumn(_ columnIndex:Int) -> Array<Bool> {
		var column = [Bool]()
		for rowIndex in 0 ..< rows {
			column.append( pixels[rowIndex][columnIndex] )
		}
		return column
	}

	private func setColumn(_ column:Array<Bool>, index columnIndex:Int) {
		for rowIndex in 0 ..< rows {
			pixels[rowIndex][columnIndex] = column[ rowIndex ]
		}
	}
}

extension Array {
	public func rotated(_ rotations:Int) -> Array<Element> {
		var ra = Array<Element>()
		for index in 0 ..< self.count {
			ra.append( self[ (index + count - rotations) % count ] )
		}
		return ra
	}
}

extension NSRange {
    func of(_ string: String) -> String? {
        guard location != NSNotFound else { return nil }

        guard let fromUTFIndex = string.utf16.index(string.utf16.startIndex, offsetBy: location, limitedBy: string.utf16.endIndex) else { return nil }
        guard let toUTFIndex = string.utf16.index(fromUTFIndex, offsetBy: length, limitedBy: string.utf16.endIndex) else { return nil }
        guard let fromIndex = String.Index(fromUTFIndex, within: string) else { return nil }
        guard let toIndex = String.Index(toUTFIndex, within: string) else { return nil }

        return string.substring(with: fromIndex ..< toIndex)
    }
}

class Solver {
	let instructions: [String]
	let screen: Screen

	var actions: [NSRegularExpression:(Int,Int)->Void]

	init(filename:String) {
		instructions = try! String(contentsOfFile:filename).components(separatedBy:"\n");
		screen = Screen(rows:6, columns:50)
		actions = Dictionary()

		actions = try! [
			NSRegularExpression(pattern:"rect (\\d+)x(\\d+)"): { self.screen.rect(width: $0, height: $1) },
			NSRegularExpression(pattern:"rotate row y=(\\d+) by (\\d+)"): { self.screen.rotateRow( index: $0, count: $1 ) },
			NSRegularExpression(pattern:"rotate column x=(\\d+) by (\\d+)"): { self.screen.rotateColumn( index: $0, count: $1 ) }
		]
	}

	public func solve() {
		for instruction in instructions {
			if( solve(instruction:instruction) ) {
				print( "After \(instruction):\n\(screen)" )
			} else {
				print( "No solution to: \(instruction)" )
				return
			}
		}
		print( "Screen has \(screen.count) pixels lit." );
	}

	private func solve(instruction:String) -> Bool {
		return actions.contains { pattern, action in
			parse(instruction: instruction, pattern: pattern, action: action )
		}
	}

	private func parse(instruction:String, pattern:NSRegularExpression, action:(Int,Int) -> Void) -> Bool {

		let range = NSRange(location: 0, length:instruction.characters.count)
		let matches = pattern.matches(in:instruction, range:range)
		guard matches.count == 1 else { return false }

		print( "Performing action: \(instruction)" )

		let match = matches[0]

		let first = match.rangeAt(1).of(instruction)
		let second = match.rangeAt(2).of(instruction)

		guard let firstString = first, let secondString = second else { return false }
		guard let firstInt = Int(firstString), let secondInt = Int(secondString) else { return false }

		action(firstInt,secondInt)
		return true
	}

}

Solver(filename:"puzzleInput.txt").solve()