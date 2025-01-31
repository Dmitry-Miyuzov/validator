package io.github.dmitrymiyuzov.validator.proxy.markerInterface;

import io.github.dmitrymiyuzov.validator.proxy.AbstractCustomValidator;

/**
 * У модели которая реализует данный интерфейс - обязательно должен быть дефолтный конструктор.
 * Модель обязательно должна реализовывать метод который возвращает класс дефолтного валидатора.
 * Еще есть jsonPathCustomValidator -> по умолчанию, из ответа модель будет преобразовываться по пути $ (корень) -> но можно переопределить это поведение.
 *
 * @param <DefaultCustomValidator> - Когда модель реализует интерфейс - она говорит, что у нее есть дефолтный валидатор
 *                                Этот тип как раз отвечает за тот дефолтный валидатор, который есть у модели.
 */
public interface ResponseModelDefaultCustomValidator<DefaultCustomValidator extends AbstractCustomValidator<?>> extends ResponseModel {
    Class<DefaultCustomValidator> getDefaultCustomValidator();

    default String jsonPathCustomValidator() {
        return "$";
    }
}
