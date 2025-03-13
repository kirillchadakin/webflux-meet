package com.example.core;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ProjectReactorDemo {
    public static void main(String[] args) throws InterruptedException {
//        Mono<String> callableExample = Mono.fromCallable(() -> {
//            System.out.println("Callable начался");
//            for (int i = 0; i < 5; i++) {
//                Thread.sleep(1000);  // Так делать плохо
//                System.out.println(i + " done");
//            }
//            System.out.println("Callable закончился");
//            return "Результат работы";
//        });
//        System.out.println("Mono from callable created");
//        callableExample
////                .publishOn(Schedulers.parallel()) // Обработка на параллельных потоках
//                .subscribe(System.out::println);


        Mono<String> monoExample = Mono.just("Привет, Mono!");
        System.out.println("Mono created");
        monoExample.subscribe(System.out::println);


        Flux<Integer> fluxExample = Flux.just(1, 2, 3, 4, 5);
        System.out.println("Flux created");
        fluxExample.subscribe(System.out::println);


        Thread.sleep(5000);
    }
}
