import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class PartTwo {
	private List<Map<Character,Integer>> columnCharacterFrequency;

	public static void main(String[] arguments) {
		new PartTwo( "puzzleInput.txt" ).solve();
	}

	public PartTwo( String filename ) {
		columnCharacterFrequency = new ArrayList<>();
		try {
			Path input = FileSystems.getDefault().getPath( "puzzleInput.txt" );
			Files.lines( input ).forEach( this::parseLine );
		} catch( IOException|RuntimeException exception ) {
			exception.printStackTrace();
		}
	}

	private void parseLine( String line ) {
		if( columnCharacterFrequency.size() == 0 ) {
			IntStream.range( 0, line.length() )
				.forEach( i -> columnCharacterFrequency.add( new HashMap<>() ) );
		} else if( columnCharacterFrequency.size() != line.length() ) {
			throw new RuntimeException( "columnCharacterFrequency length is " + columnCharacterFrequency.size() +
				" but string length is " + line.length() );
		}
		for( int index = 0; index < line.length(); index++ ) {
			char letter = line.charAt(index);
			Map<Character,Integer> map = columnCharacterFrequency.get(index);
			if( map.containsKey( letter ) )
				map.put( letter, map.get( letter ) + 1 );
			else
				map.put( letter, 1 );				
		}
	}

	Character getMostCommonLetter( Map<Character,Integer> frequency ) {
		Character letter = frequency.entrySet()
			.stream()
			.min( Map.Entry.comparingByValue(Integer::compareTo) )
			.get()
			.getKey();
		return letter;
	}

	public void solve() {
		System.out.println( "Solving...." );
		StringBuilder builder = new StringBuilder();
		columnCharacterFrequency.forEach( (m) -> builder.append( getMostCommonLetter(m) ) );
		System.out.println( "Most common string: " + builder.toString() );
	}
}