package newTech.demo.Service;

import newTech.demo.DTO.response;

public interface InfoService {
    response<Object> getStatus();

    response<Object> getNum();
}
