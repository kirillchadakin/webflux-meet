package com.example.core;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProjectReactorDemo2 {
    public static void main(String[] args) throws InterruptedException {
        // Синхронный генератор
        Flux.generate(
                () -> 1,  // Инициализация состояния (счетчик)
                (state, sink) -> {
                    sink.next(state); // Отправляем текущее значение
                    if (state == 5) { // Завершаем поток после 5 итераций
                        sink.complete();
                    }
                    return state + 1; // Обновляем состояние (счетчик)
                })
                .subscribe(System.out::println);

        // Асинхронный генератор
        Flux.create(sink -> {
            sink.next("Первый");
            sink.next("Второй");
            sink.next("Третий");
            sink.complete(); // Завершаем поток
        }).subscribe(System.out::println);

        Flux.create(sink -> {
            long startTime = System.currentTimeMillis(); // Засекаем время начала
            while (System.currentTimeMillis() - startTime < 3000) { // Пока не прошло 3 секунды
                sink.next("Сообщение отправлено: " + System.currentTimeMillis());
                try {
                    Thread.sleep(500); // Ждем 500 мс перед следующей итерацией
                } catch (InterruptedException e) {
                    sink.error(e);
                }
            }
            sink.complete(); // Завершаем поток
        }).subscribe(System.out::println);
    }
}
