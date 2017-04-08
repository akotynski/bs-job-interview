package pl.aleksanderkotbury.bs.stooq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.aleksanderkotbury.bs.stooq.repository.StooqMongoDto;
import pl.aleksanderkotbury.bs.stooq.repository.StooqRepository;

import java.util.List;

@RestController
public class StooqController {

    private final StooqRepository stooqRepository;

    public StooqController(StooqRepository stooqRepository) {
        this.stooqRepository = stooqRepository;
    }

    @GetMapping("/stooqs")
    public List<StooqMongoDto> stooqMongoDto() {
        return stooqRepository.findAll();
    }
}
