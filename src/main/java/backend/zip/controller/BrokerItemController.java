package backend.zip.controller;

import backend.zip.service.BrokerItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "brokers")
public class BrokerItemController {
    private final BrokerItemService brokerItemService;

}

