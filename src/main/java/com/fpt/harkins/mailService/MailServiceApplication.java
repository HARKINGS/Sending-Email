package com.fpt.harkins.mailService;

import com.fpt.harkins.mailService.dto.request.MailRequest;
import com.fpt.harkins.mailService.dto.response.MailResponse;
import com.fpt.harkins.mailService.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class MailServiceApplication {

	@Autowired
	private EmailSenderService emailSenderService;

	@PostMapping("/sendEmail")
	public MailResponse sendEmail(@RequestBody MailRequest mailRequest) {
		Map<String, Object> model = new HashMap<>();
		model.put("Name", mailRequest);
		model.put("location", "Hanoi, Vietnam");

		return emailSenderService.sendEmail(mailRequest, model);
	}

	public static void main(String[] args) {
		SpringApplication.run(MailServiceApplication.class, args);
	}
}
