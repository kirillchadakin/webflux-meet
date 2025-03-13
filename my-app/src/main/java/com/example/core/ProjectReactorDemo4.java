package com.example.core;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProjectReactorDemo4 {
    private static Flux<Object> createFlux() {
        return Flux.generate(() -> 1,  // Инициализация состояния (счетчик)
                (state, sink) -> {
                    sink.next(state); // Отправляем текущее значение
                    if (state == 2) {
                        throw new RuntimeException("Что-то пошло не так внутри генератора");
                    }
                    if (state == 5) { // Завершаем поток после 5 итераций
                        sink.complete();
                    }
                    return state + 1; // Обновляем состояние (счетчик)
                });
    }

    public static void main(String[] args) {

//        createFlux()
//                .onErrorResume(e -> {
//                    System.out.println("Ops, error");
//                    return Flux.just(100, 200);  // Подменяем ошибку новыми данными
//                })
//                .subscribe(
//                        System.out::println,
//                        error -> System.out.println("Произошла ошибка: " + error.getMessage()),
//                        () -> System.out.println("Завершено")
//                );

        createFlux()
                .retry(2)  // Повторяем поток 2 раза при ошибке
                .subscribe(
                        System.out::println,
                        error -> System.out.println("Произошла ошибка после ретрая: " + error.getMessage())
                );
    }

    private static Mono<String> receiveDataFromBD(String name) {
        return Mono.fromCallable(name::toUpperCase);
    }
}
