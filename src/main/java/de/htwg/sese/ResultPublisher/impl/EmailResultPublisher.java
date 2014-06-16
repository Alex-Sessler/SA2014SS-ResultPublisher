package de.htwg.sese.ResultPublisher.impl;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.htwg.sese.ResultPublisher.IResultPublisher;

public class EmailResultPublisher implements IResultPublisher {

	String recipient;
	String sender;

	String mailserver;
	String port;
	String username;
	String password;
	String textCharset = "UTF-8";

	@Override
	public void configurePublisher(Map<String, String> conf) {
		this.recipient = conf.get("recipient");
		this.sender = conf.get("sender");

		this.mailserver = conf.get("mailserver");
		this.port = conf.get("port");
		this.username = conf.get("username");
		this.password = conf.get("password");
	}

	@Override
	public void publishResult(String game, String player, Long score)
			throws Exception {

		if (recipient == null || sender == null || mailserver == null
				|| port == null || username == null || password == null) {
			throw new Exception(
					"Invalid configuration! Required: recipient, sender, mailserver, username, password");
		}

		/*
		 * MultiPartEmail email = new MultiPartEmail(); if (username != null &&
		 * password != null) { email.setAuthenticator(new
		 * DefaultAuthenticator(username, password));
		 * email.setSSLOnConnect(true); } email.setHostName(mailserver);
		 * email.setFrom(sender); email.addTo(recipient);
		 * email.setCharset(textCharset); email.setSubject("SA2014SS Game " +
		 * game + " New Score!"); email.setMsg("Game: " + game + "\nPlayer: " +
		 * player + "\nNew Score: " + score.toString());
		 * 
		 * System.out.println(email.send());
		 */

		MailAuthenticator auth = new MailAuthenticator(username, password);

		Properties properties = new Properties();

		// Den Properties wird die ServerAdresse hinzugefügt
		properties.put("mail.smtp.host", mailserver);

		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");

		// !!Wichtig!! Falls der SMTP-Server eine Authentifizierung
		// verlangt
		// muss an dieser Stelle die Property auf "true" gesetzt
		// werden
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.user", username);
		properties.put("mail.password", password);

		// Hier wird mit den Properties und dem implements Contructor
		// erzeugten
		// MailAuthenticator eine Session erzeugt
		Session session = Session.getDefaultInstance(properties, auth);

		try {
			// Eine neue Message erzeugen
			Message msg = new MimeMessage(session);

			// Hier werden die Absender- und Empfängeradressen gesetzt
			msg.setFrom(new InternetAddress(sender));
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipient, false));

			// Der Betreff und Body der Message werden gesetzt
			msg.setSubject("SA2014SS Game " + game + " New Score!");
			msg.setText("Game: " + game + "\nPlayer: " + player
					+ "\nNew Score: " + score.toString());

			// Hier lassen sich HEADER-Informationen hinzufügen
			msg.setHeader("Test", "Test");
			msg.setSentDate(new Date());

			// Zum Schluss wird die Mail natürlich noch verschickt
			Transport.send(msg);
			// Transport tr = session.getTransport("smtps");
			//
			// tr.connect(properties.getProperty("mail.user"),
			// properties.getProperty("mail.password"));
			// msg.saveChanges();
			// tr.sendMessage(msg, msg.getAllRecipients());
			// tr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class MailAuthenticator extends Authenticator {

		/**
		 * Ein String, der den Usernamen nach der Erzeugung eines Objektes<br>
		 * dieser Klasse enthalten wird.
		 */
		private final String user;

		/**
		 * Ein String, der das Passwort nach der Erzeugung eines Objektes<br>
		 * dieser Klasse enthalten wird.
		 */
		private final String password;

		/**
		 * Der Konstruktor erzeugt ein MailAuthenticator Objekt<br>
		 * aus den beiden Parametern user und passwort.
		 * 
		 * @param user
		 *            String, der Username fuer den Mailaccount.
		 * @param password
		 *            String, das Passwort fuer den Mailaccount.
		 */
		public MailAuthenticator(String user, String password) {
			this.user = user;
			this.password = password;
		}

		/**
		 * Diese Methode gibt ein neues PasswortAuthentication Objekt zurueck.
		 * 
		 * @see javax.mail.Authenticator#getPasswordAuthentication()
		 */
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(this.user, this.password);
		}

	}

}
