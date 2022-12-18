package ru.otus4.listener;

import ru.otus4.model.Message;

public interface Listener {

    void onUpdated(Message msg);

    //todo: 4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
    //Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
    //Для него уже есть тест, убедитесь, что тест проходит
}
