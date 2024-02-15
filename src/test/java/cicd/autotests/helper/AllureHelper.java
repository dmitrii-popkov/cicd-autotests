package cicd.autotests.helper;

import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class AllureHelper {

    /**
     * Прикрепить файл лога как вложение к Allure отчету
     *
     * @param name    название вложения, которое будет отображаться в отчете
     * @param logFile путь к файлу лога
     */
    public static void addAttachmentLogFile(String name, String logFile) {
        log.info("Adding logFile [{}] with name [{}] to attachment", logFile, name);

        addAttachment(name, logFile, "plain/text", ".log");
    }

    public static void addAttachmentTar(String name, String path) {
        log.info("Adding file [{}] with name [{}] to attachment", path, name);

        addAttachment(name, path, "application/octet-stream", ".tgz");
    }

    public static void addAttachmentTarGz(String name, File file) {
        addAttachmentTarGz(name, file.getAbsolutePath());
    }

    public static void addAttachmentTarGz(String name, String path) {
        log.info("Adding file [{}] with name [{}] to attachment", path, name);

        addAttachment(name, path, "application/octet-stream", ".tar.gz");
    }

    /**
     * Прикрепить вложение к Allure отчету
     *
     * @param attachName название вложения, которое будет отображаться в отчете
     * @param path       путь к прикрепляемому файлу
     * @param type       MIME-type прикрепляемого файла
     * @param ext        тип расширения файла отображаемый в отчете
     */
    public static void addAttachment(String attachName, String path, String type, String ext) {
        log.info("Adding attachment [{}]", attachName);

        Path content = Paths.get(path);

        try (InputStream is = Files.newInputStream(content)) {
            Allure.addAttachment(attachName, type, is, ext);
        } catch (IOException e) {
            log.error("Exception while adding attachment [{}]: [{}]", attachName, e.getMessage());
        }
    }

    public static void addAttachmentText(String attachName, String text) {
        log.info("Adding attachment [{}]", attachName);

        Allure.addAttachment(attachName, "application/json", text);
    }

    public static AllureRestAssured getAllureRestAssured() {
        return new AllureRestAssured()
            .setRequestTemplate("request.ftl")
            .setResponseTemplate("response.ftl");
    }

}
