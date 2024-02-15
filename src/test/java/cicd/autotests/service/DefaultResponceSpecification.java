package cicd.autotests.service;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultResponceSpecification {

    /**
     * Спецификация ответа по умолчанию с полным уровнем журналирования.
     *
     * @return спецификацию ответа
     */
    public static ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();
    }

    /**
     * Спецификация ответа по умолчанию с возможностью выбрать уровень журналирования и ожидаемый код ответа.
     *
     * @param expectedStatusCode ожидаемый код ответа
     * @param logDetail          уровень подробностей при журналировании
     * @return спецификацию ответа
     */
    public static ResponseSpecification getResponseSpecification(int expectedStatusCode, LogDetail logDetail) {
        return new ResponseSpecBuilder()
            .expectStatusCode(expectedStatusCode)
            .log(logDetail)
            .build();
    }

}
