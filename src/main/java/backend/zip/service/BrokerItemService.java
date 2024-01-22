package backend.zip.service;

import backend.zip.repository.BrokerItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrokerItemService {
    private final BrokerItemRepository brokerItemRepository;


}
