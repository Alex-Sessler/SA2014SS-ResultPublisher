SA2014SS-ResultPublisher
========================

Use HtwgServerResultPublisher:

	  HtwgServerResultPublisher pub = new HtwgServerResultPublisher();
		publisher.publishResult("04backgammon", "Player1", 1L);


Use EmailResultPublisher:

Map<String, String> conf = new HashMap<String, String>();
		conf.put("recipient", "recipient@test.com");
		conf.put("sender", "sender@gmail.com");
		conf.put("mailserver", "smtp.gmail.com");
		conf.put("port", "465");
		conf.put("username", "user");
		conf.put("password", "passwird");

		pub.configurePublisher(conf);

		pub.publishResult("game1", "player1", 12345L);
		
ONLY SUPPORTS SMTP/SSH (gmail etc.)
