package fall2018.csc2017.gamecentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class GameCentreTest {

	@Test
	public void testConcat() {
		Random random = new Random();
		int n = Math.abs(random.nextInt(9)) + 1;
		String[] strings = new String[n];
		for(int i = 0; i < n; i++) {
			strings[i] = "";
		}
		String concat = GameCentre.concatFilename(strings);
		assertEquals(concat.length(), (n - 1));
	}

}
