package de.htwg.sese.ResultPublisher;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import backgammon04.ResultPublisher.IResultPublisher;
import backgammon04.ResultPublisher.impl.EmailResultPublisher;

@Ignore
public class EmailResultPublisherTest {

	@Test
	public void publishResultTest() throws Exception {
		IResultPublisher pub = new EmailResultPublisher();

		Map<String, String> conf = new HashMap<String, String>();
		conf.put("recipient", "");
		conf.put("sender", "");
		conf.put("mailserver", "smtp.gmail.com");
		conf.put("port", "465");
		conf.put("username", "");
		conf.put("password", "");

		pub.configurePublisher(conf);

		pub.publishResult("04backgammon", "Player1", 1L);

	}

}
