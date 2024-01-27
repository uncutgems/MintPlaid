package com.application.mintplaid.controller;

import com.application.mintplaid.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SimplePageController {
    private final AuthenticationService authenticationService;
    @GetMapping(value = "/verify", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String verifyAccount(@RequestParam(name = "code") String confirmationCode) {
        System.out.println(confirmationCode);
        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
                "<body>\n" + authenticationService.verifyEmail(confirmationCode) +
                "\n" + "</body>\n" + "</html>";
    }
}
