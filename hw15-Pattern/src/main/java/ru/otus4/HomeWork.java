package ru.otus4;

import ru.otus4.handler.ComplexProcessor;
import ru.otus4.listener.ListenerPrinterConsole;
import ru.otus4.listener.homework.HistoryListener;
import ru.otus4.model.Message;
import ru.otus4.model.ObjectForMessage;
import ru.otus4.processor.ProcessorChangeField;
import ru.otus4.processor.ProcessorException;

import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Секунда должна определяьться во время выполнения.
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
       4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
        var processor = List.of(new ProcessorChangeField(), new ProcessorException());
        var complexProcessor = new ComplexProcessor(processor, ex -> {
        });
        var listener = new HistoryListener();
        var listenerPrint = new ListenerPrinterConsole();
        complexProcessor.addListener(listener);
        complexProcessor.addListener(listenerPrint);

        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(new ArrayList<>());

        var message = new Message.Builder(30l)
                .field11("field11")
                .field12("field12")
                .field13(objectForMessage)
                .build();
        listener.onUpdated(message);

        Message result = complexProcessor.handle(message);
        complexProcessor.removeListener(listener);
        System.out.println("result ".concat(result.toString()));


    }
}
