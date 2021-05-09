package service;

import model.Log;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IRestLogCollector {
    /**
     * Получение логов с сайта
     */
    List<Log> getLog();
}
