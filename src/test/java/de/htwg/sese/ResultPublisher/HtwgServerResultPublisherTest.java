package de.htwg.sese.ResultPublisher;

import org.junit.Ignore;
import org.junit.Test;

import de.htwg.sese.ResultPublisher.impl.HtwgServerResultPublisher;

@Ignore
public class HtwgServerResultPublisherTest {

	@Test
	public void publishResultTest() throws Exception {
		HtwgServerResultPublisher pub = new HtwgServerResultPublisher();

		pub.publishResult("04backgammon", "Player1", 1L);
	}
}
