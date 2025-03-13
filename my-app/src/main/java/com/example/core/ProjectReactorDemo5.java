package com.example.core;

import reactor.core.publisher.Flux;

public class ProjectReactorDemo5 {

    public static void main(String[] args) {
        // Создаем Flux, который генерирует данные с некоторым интервалом
        Flux<Double> flux = Flux.range(1, 100)  // Генерируем числа от 1 до 1000
                .doOnRequest(request -> System.out.println("Запросили " + request + " элементов")) // Логирование запроса
                .log()
                .doOnTerminate(() -> System.out.println("Поток завершился"))
                .map(i -> longTimeProcess(i)); // Применяем обработку данных

        // Подписка на Flux с ограничением скорости потока (backpressure)
        Flux<Double> doubleFlux = flux
//                .onBackpressureBuffer(10) // Применяем backpressure: буферизуем до 10 элементов
                .onBackpressureDrop() // Старые элементы будут сбрасываться, если не успеваем их обработать
//                .onBackpressureLatest() // Оставляем только последний элемент
//                .onBackpressureError()  // Ошибка при переполнении
//                .publishOn(Schedulers.parallel()) // Обработка на параллельных потоках
                .doOnNext(i -> {
                    try {
                        Thread.sleep(50);  // Эмуляция долгой обработки каждого элемента
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        doubleFlux.subscribe(
                        i -> {
                            System.out.println("Обработан в 1 подписчике");
                        }, // Пустой consumer, мы только логируем
                        Throwable::printStackTrace, // Логирование ошибок
                        () -> System.out.println("Все элементы обработаны в 1 подписчике"), // Логируем завершение
                        subscription -> subscription.request(10)
                );
        doubleFlux.subscribe(
                i -> {
                    System.out.println("Обработан в 2 подписчике");
                }, // Пустой consumer, мы только логируем
                Throwable::printStackTrace, // Логирование ошибок
                () -> System.out.println("Все элементы обработаны в 2 подписчике"), // Логируем завершение
                subscription -> subscription.request(10)
        );

    }


    // Симуляция долгой обработки
    private static Double longTimeProcess(Integer i) {
        double result = i * Math.random();
        for (int j = 0; j < 1_000_000; j++) {
            result = Math.sqrt(i * Math.random());
        }
        return result;
    }

}
