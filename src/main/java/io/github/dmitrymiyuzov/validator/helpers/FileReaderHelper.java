package io.github.dmitrymiyuzov.validator.helpers;

import io.github.dmitrymiyuzov.validator.exceptions.ErrorManager;
import io.github.dmitrymiyuzov.validator.exceptions.response.body.bodyFile.ExpectedFileNotFoundError;
import io.github.dmitrymiyuzov.validator.exceptions.response.body.bodyFile.ReadFileError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class FileReaderHelper {
    public static String readFileAsString(String pathFile, ErrorManager errorManager) {
        var inputString = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathFile);

        if (inputString == null) {
            throw new ExpectedFileNotFoundError(errorManager.getErrors());
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputString))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new ReadFileError(errorManager.getErrors());
        }
    }
}
