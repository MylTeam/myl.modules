package com.myl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

public class IssueMail {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IssueMail.class);

	private JavaMailSender mailSender;
	private JavaMailSender mailSender1;
	private JavaMailSender mailSender2;
	private JavaMailSender mailSender3;
	private JavaMailSender mailSender4;
	private JavaMailSender mailSender5;
	private JavaMailSender mailSenderErr;
	private JavaMailSender mailSenderCom;

	public void setMailSender(JavaMailSender mailSender) {
		LOGGER.info("Initializing e-mail 0");
		this.mailSender = mailSender;
	}

	public void setMailSender1(JavaMailSender mailSender1) {
		LOGGER.info("Initializing e-mail 1");
		this.mailSender1 = mailSender1;
	}

	public void setMailSender2(JavaMailSender mailSender2) {
		LOGGER.info("Initializing e-mail 2");
		this.mailSender2 = mailSender2;
	}

	public void setMailSender3(JavaMailSender mailSender3) {
		LOGGER.info("Initializing e-mail 3");
		this.mailSender3 = mailSender3;
	}

	public void setMailSender4(JavaMailSender mailSender4) {
		LOGGER.info("Initializing e-mail 4");
		this.mailSender4 = mailSender4;
	}

	public void setMailSender5(JavaMailSender mailSender5) {
		LOGGER.info("Initializing e-mail 5");
		this.mailSender5 = mailSender5;
	}

	public void setMailSenderErr(JavaMailSender mailSenderErr) {
		LOGGER.info("Initializing e-mail errors");
		this.mailSenderErr = mailSenderErr;
	}

	public void setMailSenderCom(JavaMailSender mailSenderCom) {
		LOGGER.info("Initializing e-mail comments");
		this.mailSenderCom = mailSenderCom;
	}

	public void sendMailTo(String to, String subject, String msg) {
		LOGGER.info("Sending e-mail");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(getProperty("mail.username"));
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
	}

	/************************/
	public void sendMailConfirm(String to, String subject, String msg) {
		Properties pc = getProperties("mailConfirm.properties");
		Integer c = Integer.valueOf(pc.getProperty("mail.current"));
		if (c == null) {
			System.out.println("es nulo");
			setProperty("mailConfirm.properties", "mail.current", "1");
		}
		Properties prop = getProperties("mail.properties");
		String from = prop.getProperty("mail.confirm" + c);

		LOGGER.info("Sending Confirm e-mail from " + from);
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
					false, "utf-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			mimeMessage.setContent(msg, "text/html");

			if (c.equals(1)) {
				mailSender1.send(mimeMessage);
			} else if (c.equals(2)) {
				mailSender2.send(mimeMessage);
			} else if (c.equals(3)) {
				mailSender3.send(mimeMessage);
			} else if (c.equals(4)) {
				mailSender4.send(mimeMessage);
			} else if (c.equals(5)) {
				mailSender5.send(mimeMessage);
			}

		} catch (Exception e) {
			LOGGER.error("Error al intentar enviar e-mail de confirmación desde "
					+ from);
			LOGGER.error("Error", e.getMessage());
			if (c.equals(5)) {
				c = 0;
			}
			setProperty("mailConfirm.properties", "mail.current",
					String.valueOf(c += 1));
			// sendMailConfirm(to, subject, msg);
		}
	}

	public Boolean sendMailConfirmTest(String to, String subject, String msg) {

		Properties prop = getProperties("mail.properties");
		for (Integer c = 1; c <= 5; c++) {
			String from = prop.getProperty("mail.confirm" + c);
			try {
				LOGGER.info("Trying to send Confirm e-mail from " + from);
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper helper;
				try {
					helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
					helper.setFrom(from);
					helper.setTo(to);
					helper.setSubject(subject);
					mimeMessage.setContent(msg, "text/html");
				} catch (MessagingException e) {
					LOGGER.error("Mail Error", e);
					return false;
				}

				if (c.equals(1)) {
					mailSender1.send(mimeMessage);
				} else if (c.equals(2)) {
					mailSender2.send(mimeMessage);
				} else if (c.equals(3)) {
					mailSender3.send(mimeMessage);
				} else if (c.equals(4)) {
					mailSender4.send(mimeMessage);
				} else if (c.equals(5)) {
					mailSender5.send(mimeMessage);
					
				}
				LOGGER.info("E-mail enviado a: "+to);
				return true;
			} catch (Exception e) {
				LOGGER.error("Error al intentar enviar e-mail de confirmación desde " + from);
				LOGGER.error("Error", e.getMessage());
				if (c.equals(5)){
					return false;
				}
			}
		}

		return true;
	}

	public void sendMailComment(String subject, String msg) {
		LOGGER.info("Sending Comment e-mail");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(getProperty("mail.comments"));
		message.setTo(getProperty("mail.username"));
		message.setSubject(subject);
		message.setText(msg);
		mailSenderCom.send(message);
	}

	public void sendMailError(String subject, String msg) {
		LOGGER.info("Sending Error e-mail");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(getProperty("mail.errors"));
		message.setTo(getProperty("mail.username"));
		message.setSubject(subject);
		message.setText(msg);
		mailSenderErr.send(message);
	}

	public String getProperty(String propiedad) {
		String property = "";
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("mail.properties"));
			property = prop.getProperty(propiedad);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return property;
	}

	public Properties getProperties(String propertiesFile) {
		Properties prop = new Properties();
		try {
			InputStream input = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(propertiesFile);
			prop.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public void setProperty(String file, String property, String value) {
		Properties prop = new Properties();
		try {
			InputStream input = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(file);
			prop.load(input);

			URL url = Thread.currentThread().getContextClassLoader()
					.getResource(file);
			FileOutputStream out = new FileOutputStream(new File(url.toURI()));
			prop.setProperty(property, value);
			prop.store(out, null);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

}