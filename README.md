# Документация по инструменту валидаций.
1. [Описание](#description)
2. [Сторонние зависимости](#dependencies)
3. [ValidatorFabric - основная часть инструмента](#validatorFabric)
   1. [Общее](#generalMoments)
      1. [Важные моменты в целом](#generalMoment)
      2. [Чем отличается Supplier<'Boolean'> condition от boolean condition?](#supplierMoment)
      3. [GroupSoft - проверки](#groupSoft)
   2. [AssertValidation](#assertValidation)
      1. [Доступные методы](#assertionAvailableMethod)
   3. [ResponseValidation](#responseValidation)
      1. [Доступные методы](#responseAvailableMethod)
4. [Дополнительно по ValidatorFabric](#additionalValidatorFabric)
   1. [AllureConfig](#allureConfig)
   2. [Настройка стек-трейса](#settingsStackTrace)


# 1. Описание: <a name="description"></a>
Инструмент состоит из 2-ух частей.<br>
Первая часть - это непосредственно инструмент валидаций -> ValidatorFabric.<br>
Вторая часть - это архитектурное решение, помогающее удобнее валидировать ответы (Response) -> от RestAssured, при этом есть возможность писать собственные валидаторы.
<br><br>

ValidatorFabric - из главных возможностей.<br>
1. Вызывать soft валидации в цепочке проверок. (при этом миксуя их с НЕ софт валидациями)<br>
2. Логически распределять проверки по группам.<br>
3. Заполнение Allure-отчета.<br>
4. Удобный стек-трейс после завершения цепочек валидаций. (при необходимости можно вызвать полный стек-трейс).<br>
5. Возможность включать/отключать Allure.
<br><br>

# 2. Сторонние зависимости: <a name="dependencies"></a>
В данном разделе не будет версий библиотек, т.к. я не тестировал различные версии<br>
Плюс не всегда будет указана зависимость, а будет просто указан пакет с классом -> при необходимости этой информации будет достаточно для понимания.<br>
1. io.rest-assured:rest-assured<br>
2. org.junit.jupiter.api.Assertions -> по факту можно заменить на какой-нибудь другой Assertions.<br>
3. io.qameta.allure<br>
4. com.github.erosb:everit-json-schema<br>
5. com.fasterxml.jackson
<br><br>

# 3. ValidatorFabric - основная часть инструмента. <a name="validatorFabric"></a>
Отправной точкой является обращение к классу ValidatorFabric<br>
![img.png](src/main/resources/_documentation/resources/png/img.png)<br>

Как видно из скриншота, есть 3 разные вариации вызова фабрики.
1. AssertValidation - предоставляет проверки утверждения.<br>
2. ResponseValidation - предоставляет проверки для валидации ответа от RestAssured.<br>
3. ComplexValidation - предоставляет проверки, как из AssertValidation, так и из ResponseValidation. Описан не будет, т.к. это лишь совокупность двух валидаторов (соотв. имеет все ровно тоже самое).
<br><br>

# 3.1 Общее. <a name="generalMoments"></a>

# 3.1.1 <u>**Сразу по важным моментам, которые далее объяснятся не будут.**</u> <a name="generalMoment"></a>
1. Все цепочки <u>**должны**</u> завершаться методом validate. Там происходят системные шаги -> по завершению Allure.<br>
   Но это не означает, что проверки - ленивые. Нет. Проверка вызывается ровно в тот момент - когда происходит ее вызов.<br>
2. У каждого обычного метода, <u>**чаще всего есть 2 варианта**</u>, без постфикса Soft и с постфиксом Soft.<br>
   <u>Soft</u> - указывает на то, что в случае ПАДЕНИЯ этой проверки, цепочка проверок не завершится, а пойдет дальше.<br>
   <u>НЕ Soft</u> - указывает на то, что в случае ПАДЕНИЯ этой проверки, цепочка проверок завершится, далее проверки НЕ пойдут.<br>
3. <u>String allureStepName</u> - это то, как будет отображаться шаг проверки в Allure-отчете. Если данного параметра у метода нет -> значит есть значение по умолчанию.<br>
4. <u>String errorMessage</u> - если данные параметр заполнен, в allure-отчете под упавший шаг проверки - будет добавлен еще один шаг, с описание которое вы указали под этот параметр.<br>
5. Чем отличается Supplier<'Boolean'> condition от boolean condition? Там где вы видите в ожидаемом результате принимает Supplier - действует это правило, которое я опишу ниже<br><br>
   Пример по данным аспектам, из кода:<br>
   ![img.png](src/main/resources/_documentation/resources/png/img4.png)<br>
   Пример консоли, исходя из этой цепочки проверок:<br>
   ![img.png](src/main/resources/_documentation/resources/png/img5.png)<br>
   Пример Allure-отчета:<br>
   ![img_1.png](src/main/resources/_documentation/resources/png/img6.png)<br>

Что мы видим:
1. Название главного шага<br>
2. Описание ошибки - там где указан errorMessage<br>
3. Название шагов (проверок) - так, как указано в allureStepName<br>
4. То что не было вызова четвертой валидации, т.к. перед ней был вызов НЕ Soft валидации, и она не прошла.<br>

# 3.1.2 Чем отличается Supplier<'Boolean'> condition от boolean condition? <a name="supplierMoment"></a>
Посмотрим пример из кода:<br>
![img.png](src/main/resources/_documentation/resources/png/img7.png)<br>

А теперь запустим тест, и посмотрим Allure-отчет:<br>
![img.png](src/main/resources/_documentation/resources/png/img8.png)<br>
Как видим из отчета, там где мы передавали метод с шагом - без функции, там этот шаг "улетел из контекста".<br>
Там где мы передавали метод с шагом - как функцию, там этот шаг находится в контексте.<br>

Вывод, если в ожидаемый результат - передается метод, или действие который/ое фиксируется в Allure-отчете, то
необходимо передовать этот метод/действие - в виде функции Supplier.<br><br>

# 3.1.3 GroupSoft - проверки <a name="groupSoft"></a>
Рассмотрим сразу на коде:<br>
![img.png](src/main/resources/_documentation/resources/png/img9.png)<br>
Что мы здесь видим?<br>
Мы вызываем на главным уровне две групповых проверки.<br>
1. Первая групповая проверка - содержит в себе 2 валидации, одна из них упадет. Соответственно групповая проверка считается проваленной.<br>
2. Вторая групповая проверка - содержит в себе 2 групповые проверки, первая групповая проверка - будет проваленной. Вторая групповая проверка будет успешной.<br>
   Т.к вторая групповая проверка - состоит из 2 подгрупповых проверок, и одна из них будет проваленной, соответственно главная групповая проверка - будет проваленной.<br>
   Посмотрим внимательно Allure-отчет:<br>
   ![img.png](src/main/resources/_documentation/resources/png/img10.png)<br>

Важные моменты для групповых проверок:<br>
1. Можно в групповых проверках вызывать групповые проверки (сколько угодно -> главное не запутайтесь)<br>
2. В групповых проверках - вам будут доступны только Soft валидации, нельзя будет вызвать обычную.<br>
3. В групповых проверках - не нужно создавать новый валидатор, пользуйтесь параметром которое дает lambda выражение ->
   nestedBuilder -> nestedBuilder.assertTrueSoft().<br>
   Если вы вызвали в групповой проверке - групповую проверку, вы не сможете назвать параметр опять nestedBuilder, т.к. будет конфликт имен.<br>
   Вам нужно будет назвать nestedValidator2 (например), и тогда используйте внутри этой групповой проверки именно эту переменную.<br>
   Пример:<br>
   ![img_1.png](src/main/resources/_documentation/resources/png/img11.png)<br>

# 3.2 AssertValidation. <a name="assertValidation"></a>
При вызове фабрики предоставляется 2 метода:<br>
![img.png](src/main/resources/_documentation/resources/png/img2.png)<br>

Дают доступ до данного типа проверок:<br>
<u>beginAssertValidation()</u> - В аллюр печатается шаг по умолчанию -> "Проверки (Assert)".<br>
<u>beginAssertValidation(String allureStepName)</u> - В аллюр печатается шаг - заданный пользователем.<br>
<br>
Этот шаг будет являться главным в Allure-отчете для проверок - которые будут далее вызываться по цепочке.<br>
<u>Пример</u>:
![img.png](src/main/resources/_documentation/resources/png/img3.png)<br>

# 3.2.1 Доcтупные методы <a name="assertionAvailableMethod"></a>
## AssertTrue
assertTrue(String allureStepName, boolean condition)<br>
assertTrue(String allureStepName, boolean condition, String errorMessage)<br>
assertTrue(String allureStepName, Supplier<'Boolean'> condition)<br>
assertTrue(String allureStepName, Supplier<'Boolean'> condition, String errorMessage)<br>

Имеются аналоги -> Soft

## AssertFalse
assertFalse(String allureStepName, boolean condition)<br>
assertFalse(String allureStepName, boolean condition, String errorMessage)<br>
assertFalse(String allureStepName, Supplier<'Boolean'> condition)<br>
assertFalse(String allureStepName, Supplier<'Boolean'> condition, String errorMessage)<br>

Имеются аналоги -> Soft

## AssertEquals
assertEquals(String allureStepName, T actual, T expected);<br>
assertEquals(String allureStepName, T actual, T expected, String errorMessage);<br>
assertEquals(String allureStepName, Supplier<'T'> actual, T expected);<br>
assertEquals(String allureStepName, Supplier<'T'> actual, T expected, String errorMessage);<br>
assertEquals(String allureStepName, Supplier<'T'> actual, Supplier<'T'> expected);
assertEquals(String allureStepName, Supplier<'T'> actual, Supplier<'T'> expected, String errorMessage);<br>
assertEquals(String allureStepName, T actual, Supplier<'T'> expected);<br>
assertEquals(String allureStepName, T actual, Supplier<'T'> expected, String errorMessage);<br>

Имеются аналоги -> Soft

## AssertNotEquals
assertNotEquals(String allureStepName, T actual, T expected);<br>
assertNotEquals(String allureStepName, T actual, T expected, String errorMessage);<br>
assertNotEquals(String allureStepName, Supplier<'T'> actual, T expected);<br>
assertNotEquals(String allureStepName, Supplier<'T'> actual, T expected, String errorMessage);<br>
assertNotEquals(String allureStepName, Supplier<'T'> actual, Supplier<'T'> expected);
assertNotEquals(String allureStepName, Supplier<'T'> actual, Supplier<'T'> expected, String errorMessage);<br>
assertNotEquals(String allureStepName, T actual, Supplier<'T'> expected);<br>
assertNotEquals(String allureStepName, T actual, Supplier<'T'> expected, String errorMessage);<br>

Имеются аналоги -> Soft

## AssertNull
assertNull(String allureStepName, Object object);<br>
assertNull(String allureStepName, Object object, String errorMessage);<br>
assertNull(String allureStepName, Supplier<'Object'> object);<br>
assertNull(String allureStepName, Supplier<'Object'> object, String errorMessage);<br>

Имеются аналоги -> Soft

## AssertNotNull
assertNotNull(String allureStepName, Object object);<br>
assertNotNull(String allureStepName, Object object, String errorMessage);<br>
assertNotNull(String allureStepName, Supplier<'Object'> object);<br>
assertNotNull(String allureStepName, Supplier<'Object'> object, String errorMessage);<br>

Имеются аналоги -> Soft

## AssertLocalDateTime
assertLocalDateTime(String nameActualDate, String nameExpectedDate, LocalDateTime actual, LocalDateTime expected, DateTimeFormatter format);<br>
Пример позитивный:<br>
![img.png](src/main/resources/_documentation/resources/png/img12.png)<br>
Отчет в аллюре:<br>
![img.png](src/main/resources/_documentation/resources/png/img13.png)<br>
Пример негативный:<br>
![img.png](src/main/resources/_documentation/resources/png/img14.png)<br>
Отчет в аллюре:<br>
![img.png](src/main/resources/_documentation/resources/png/img15.png)<br>

Имеются аналоги -> Soft

## AssertZonedDateTimeWithTolerance
Позволяют сравнить два ZonedDateTime с погрешностью.<br>
В чем измеряется погрешность, указано в названии метода.<br>
Допустимая величина указывается последним параметром в методе.<br>
Не важно в каком UTC - ZonedDateTime, они могут быть в разных UTC.<br>
Не важно в каком порядке передаются ZonedDateTime, в любом случае найдет ошибку несоответствия дат.<br>

assertZonedDateTimeWithToleranceHours(String allureStepName, ZonedDateTime z1, ZonedDateTime z2, long allowDifferenceHours);<br>
assertZonedDateTimeWithToleranceHours(String allureStepName, Supplier<'ZonedDateTime'> z1, ZonedDateTime z2, long allowDifferenceHours);<br>
assertZonedDateTimeWithToleranceHours(String allureStepName, ZonedDateTime z1, Supplier<'ZonedDateTime'> z2, long allowDifferenceHours);<br>
assertZonedDateTimeWithToleranceHours(String allureStepName, Supplier<'ZonedDateTime'> z1, Supplier<'ZonedDateTime'> z2, long allowDifferenceHours);<br>

Пример позитивный:<br>
![img.png](src/main/resources/_documentation/resources/png/img16.png)<br>
Отчет в аллюре:<br>
![img_1.png](src/main/resources/_documentation/resources/png/img17.png)<br>
Пример негативный:<br>
![img_2.png](src/main/resources/_documentation/resources/png/img18.png)<br>
Отчет в аллюре:<br>
![img_3.png](src/main/resources/_documentation/resources/png/img19.png)<br>


Имеются ровно такие же методы, только Minutes, Second.
Имеются аналоги -> Soft для каждого метода.

## AssertThatCodeDoesNotException
Позволяет передать функцией - код, подразумевается изначально какой-то ассерт из сторонней библиотеки.<br>

assertThatCodeDoesNotException(String allureStepName, ThrowingCallable call);<br>
assertThatCodeDoesNotException(String allureStepName, ThrowingCallable call, String errorMessageAllure);<br>

allureStepName - то как будет называться валидация в Allure.<br>
ThrowingCallable call - функция, описывающая код - который по итогу будет выполнятся и если выполнился без выброса исключения - считается успешной проверкой.<br>
String errorMessageAllure - дополнительное описание ошибки, которое будет отображаться в Allure в случае неуспешной проверки.<br>
При ошибке -> в консоли будет указано описание из исключения которое было вызвано.<br>

Примеры:<br>
![img.png](src/main/resources/_documentation/resources/png/img43.png)<br>
Отчет в аллюре:<br>
![img.png](src/main/resources/_documentation/resources/png/img44.png)<br>

Имеются аналоги -> Soft для каждого метода.

# 3.3 ResponseValidation. <a name="responseValidation"></a>
При вызове фабрики предоставляется несколько методов.<br>
![img.png](src/main/resources/_documentation/resources/png/img20.png)<br>
Response - объект от RestAssured
Responsible - интерфейс (кастомный), для случая, если вы оборачиваете Response в другой объект.<br>
Тогда "другой объект" должен реализовывать данный интерфейс.<br>

# 3.3.1 Доcтупные методы <a name="responseAvailableMethod"></a>
## Body
Универсальный метод, с помощью которого можно подставить свой matcher.<br>
body(String jsonPath, Matcher<'T'> matcher);<br>
body(String jsonPath, Matcher<'T'> matcher, String allureErrorMessage);<br>
body(String allureStepName, String jsonPath, Matcher<'T'> matcher);<br>
body(String allureStepName, String jsonPath, Matcher<'T'> matcher, String allureErrorMessage);<br>

Код:<br>
![img_1.png](src/main/resources/_documentation/resources/png/img21.png)<br>
Allure-отчет и валидируемый json:<br>
![img_2.png](src/main/resources/_documentation/resources/png/img22.png)<br>

Имеются аналоги -> Soft для каждого метода.

## BodyHasAttribute
bodyHasAttribute(String parentJsonPath, String attribute);<br>
bodyNotHasAttribute(String parentJsonPath, String attribute);<br>

Код:<br>
![img_3.png](src/main/resources/_documentation/resources/png/img23.png)<br>
Allure-отчет и валидируемый json:<br>
![img_4.png](src/main/resources/_documentation/resources/png/img24.png)<br>

Имеются аналоги -> Soft для каждого метода.

## BodyNotHasAttribute
bodyHasAttribute(String parentJsonPath, String attribute);<br>
bodyNotHasAttribute(String parentJsonPath, String attribute);<br>

Код:<br>
![img_5.png](src/main/resources/_documentation/resources/png/img25.png)<br>
Allure-отчет и валидируемый json:<br>
![img_6.png](src/main/resources/_documentation/resources/png/img26.png)<br>

Имеются аналоги -> Soft для каждого метода.

## BodyArray
bodyArrayEmpty(String jsonPath);
bodyArraySizeEquals(String jsonPath, int expectedSize);

Код:<br>
![img_7.png](src/main/resources/_documentation/resources/png/img27.png)<br>
Allure-отчет и валидируемый json:<br>
![img_8.png](src/main/resources/_documentation/resources/png/img28.png)<br>

Имеются аналоги -> Soft для каждого метода.

## BodyEquals && BodyContains && StatusCode
bodyEquals(String jsonPath, T expectedValue);<br>
bodyContains(String jsonPath, String containsValue);<br>
statusCode(int expectedStatus);<br>
statusCodeOrElse(int expectedStatus, Consumer<Response> orElse) - функция выполнится только в случае если проверка упадет<br>
statusCodeAndElse(int expectedStatus, Consumer<Response> andElse); - функция выполнится в любом случае (упадет или пройдент не важно)<br>

Код:<br>
![img_10.png](src/main/resources/_documentation/resources/png/img30.png)<br>
Allure-отчет и валидируемый json:<br>
![img_9.png](src/main/resources/_documentation/resources/png/img29.png)<br>

Имеются аналоги -> Soft для каждого метода.

## HeaderEquals && HeaderContains
headerEquals(String header, String expectedValue);<br>
headerContains(String header, String containsValue);<br>

Код:<br>
![img.png](src/main/resources/_documentation/resources/png/img31.png)
Allure-отчет и валидируемый json:<br>
![img_1.png](src/main/resources/_documentation/resources/png/img32.png)

Имеются аналоги -> Soft для каждого метода.

## JsonSchema
matchesJsonSchema(String schema);<br>

Данный метод -> использует библиотеку com.github.erosb:everit-json-schema.<br>
Вам необходимо написать схему, и передать ее в этот метод.<br>
Хранение схемы определяете сами (рекомендую в ресурсах)<br>

Пример отчета при падении теста по схеме:<br>
![img.png](src/main/resources/_documentation/resources/png/img33.png)<br>

Прикладывается сама схема, и ошибки схемы.<br>

Имеются аналоги -> Soft для каждого метода.

## BodyEqualsFile
bodyEqualsFile(String pathFile)<br>

Данный метод -> использует библиотеку com.fasterxml.jackson.<br>
Преобразует ответ и ваш файл к JsonNode и сравнивает их.<br>

Пример вызова:<br>
.bodyEqualsFileSoft("testFiles/dictionaries/cardSettings/create/feature-create.json")<br>
Расположение файла:<br>
![img.png](src/main/resources/_documentation/resources/png/img34.png)

Имеются аналоги -> Soft для каждого метода.

# 4. Дополнительно по ValidatorFabric: <a name="additionalValidatorFabric"></a>

# 4.1 AllureConfig: <a name="allureConfig"></a>
Предоставляется возможность отключать конфигурировать Allure.<br>
![img.png](src/main/resources/_documentation/resources/png/img35.png)<br>

Подробнее про каждую:<br>
disableSoftName<br>
Код:<br>
![img_1.png](src/main/resources/_documentation/resources/png/img36.png)<br>
Allure отчет:<br>
![img_2.png](src/main/resources/_documentation/resources/png/img37.png)<br>

disableValidationName<br>
Код:<br>
![img_4.png](src/main/resources/_documentation/resources/png/img39.png)<br>
Allure отчет:<br>
![img_3.png](src/main/resources/_documentation/resources/png/img38.png)<br>

disableMessageError - работает по аналогии. Отключает шаги описаний ошибок.<br><br><br>
Делаем вывод -> что каждый параметр отвечает за конкретную сущность.<br>
ВАЖНО. Эта конфигурация отвечает только за аллюр, логика инструмента не меняется никак.<br>
По умолчанию все шаги включены.<br>
Это не глобальные настройки, это настройки для конкретного Validator. То есть, когда вы вызываете ValidatorFabric.beginAssertValidation -> создается новый Validator и для него опять по умолчанию весь Allure будет включен.

# 4.2 Настройка стек-трейса: <a name="settingsStackTrace"></a>
После переноса валидатора себе, вам нужно изменить одну строчку кода, для того чтобы по умолчанию был красивый стек-трейс<br>
![img_5.png](src/main/resources/_documentation/resources/png/img40.png)<br>

Нужно указать groupId вашего проекта.<br>
Как его можно определить в проекте:<br>
![img_6.png](src/main/resources/_documentation/resources/png/img41.png)<br>
Как его можно определить в gradle:<br>
![img_7.png](src/main/resources/_documentation/resources/png/img42.png)<br>

Чтобы включить полный стек-трейс, у вас должна быть установлена Env переменная debug -> с значением true.


