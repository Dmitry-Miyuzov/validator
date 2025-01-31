package io.github.dmitrymiyuzov.validator.proxy.markerInterface;

import io.github.dmitrymiyuzov.validator.proxy.AbstractCustomValidatorList;

/**
 * У модели которая реализует данный интерфейс - обязательно должен быть дефолтный конструктор.
 * Модель обязательно должна реализовывать метод который возвращает класс дефолтного валидатора.
 * Еще есть jsonPathCustomValidatorList -> по умолчанию, из ответа модель будет преобразовываться по пути $ (корень) -> но можно переопределить это поведение.
 *
 * @param <DefaultCustomValidatorList> - Когда модель реализует интерфейс - она говорит, что у нее есть дефолтный валидатор
 *                                Этот тип как раз отвечает за тот дефолтный валидатор, который есть у модели.
 */
public interface ResponseModelDefaultCustomValidatorList <DefaultCustomValidatorList extends AbstractCustomValidatorList<?>> extends ResponseModel{
    Class<DefaultCustomValidatorList> getDefaultCustomValidatorList();

    default String jsonPathCustomValidatorList() {
        return "$";
    }
}
