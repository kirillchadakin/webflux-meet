package com.example.core;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProjectReactorDemo3 {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .map(n1 -> n1 * 2) // Умножаем на 2
                .filter(n1 -> n1 > 10)
                .subscribe(n -> System.out.println("Получено: " + n));

        Flux.just("Алиса", "Боб", "Чарли")
                .flatMap(name -> receiveDataFromBD(name))
                .subscribe(System.out::println);

        // Контроль потока

        Flux.range(1, 10)
                .limitRate(3) // Будет запрашивать по 3 элемента за раз
                .subscribe(System.out::println);

        Flux.range(1, 10)
                .buffer(3) // Группируем элементы по 3
                .subscribe(System.out::println);

        Flux.range(1, 10)
                .window(3) // Разбиваем поток на под-потоки по 3 элемента
                .flatMap(Flux::collectList)
                .subscribe(System.out::println);
    }

    private static Mono<String> receiveDataFromBD(String name) {
        return Mono.fromCallable(name::toUpperCase);
    }
}
