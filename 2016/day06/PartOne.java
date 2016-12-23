import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class PartOne extends Solver {

	public static void main(String[] arguments) {
		new PartOne( "puzzleInput.txt" ).solve();
	}

	public PartOne( String inputFilename ) {
		super(inputFilename);
	}

	protected Character getLetter( Map<Character,Integer> frequency ) {
		Character letter = frequency.entrySet()
			.stream()
			.max( Map.Entry.comparingByValue(Integer::compareTo) )
			.get()
			.getKey();
		return letter;
	}

}