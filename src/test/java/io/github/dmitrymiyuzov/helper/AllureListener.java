package io.github.dmitrymiyuzov.helper;

import io.qameta.allure.Allure;
import io.restassured.filter.Filter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AllureListener {
    public static List<Filter> getHttpListener() {
        List<Filter> resultList = new ArrayList<>(2);
        RequestLoggingFilter request;
        ResponseLoggingFilter response;
        try {
            request = new RequestLoggingFilter(LogDetail.ALL, new CustomFileAllurePrintStream("request"));
            response = new ResponseLoggingFilter(LogDetail.ALL, new CustomFileAllurePrintStream("response"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
        resultList.add(request);
        resultList.add(response);
        return resultList;
    }

    /**
     * перегрузка {@link PrintStream}, при вызове printLn прикрепляет строку в файл с именем logName
     */
    private static class CustomFileAllurePrintStream extends PrintStream {
        private final String logName;

        public CustomFileAllurePrintStream(String logName) throws UnsupportedEncodingException {
            super(new ByteArrayOutputStream(), false, "UTF-8");
            this.logName = logName;
        }

        @Override
        public void println(String x) {
            Allure.attachment(logName, x);
        }
    }
}
