import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class PartTwo extends Solver {

	public static void main(String[] arguments) {
		new PartTwo( "puzzleInput.txt" ).solve();
	}

	public PartTwo( String inputFilename ) {
		super(inputFilename);
	}

	protected Character getLetter( Map<Character,Integer> frequency ) {
		Character letter = frequency.entrySet()
			.stream()
			.min( Map.Entry.comparingByValue(Integer::compareTo) )
			.get()
			.getKey();
		return letter;
	}

}