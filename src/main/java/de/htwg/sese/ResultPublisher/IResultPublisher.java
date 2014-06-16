package de.htwg.sese.ResultPublisher;

import java.util.Map;

public interface IResultPublisher {

	public void configurePublisher(Map<String, String> conf);

	public void publishResult(String game, String player, Long score)
			throws Exception;

}
