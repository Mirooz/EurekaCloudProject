package be.mirooz.eurekaproject.secondclient.controller;


import be.mirooz.eurekaproject.secondclient.service.ClientOneController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class EurekaClient2Controller {
    Logger logger = Logger.getLogger("EurekaClient2Controller");
    @Autowired
    private ClientOneController clientOneController;

    @RequestMapping("/call-client-one")
    public String callClientOne() {
        logger.info("client 2 call");
        return clientOneController.greeting();
    }
}