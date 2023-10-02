package ru.sadykov.service;

/**
 * Утильный класс для получения имени файла и расширения из заданной строки.
 * Есть класс Paths, но он работает с Path. И там доступен только метод получения имени файла
 */
public final class Photos {

    private Photos() {
    }

    static String getPhotoName(String photoPath) {
        int dotIndex = photoPath.lastIndexOf("/");
        if (dotIndex >= 0 && dotIndex < photoPath.length() - 1) {
            return photoPath.substring(dotIndex + 1);
        }
        throw new RuntimeException("Некорректный путь!");
    }

    static String getPhotoExtension(String photoPath) {
        int dotIndex = photoPath.lastIndexOf(".");
        if (dotIndex >= 0 && dotIndex < photoPath.length() - 1) {
            return photoPath.substring(dotIndex + 1);
        }
        throw new RuntimeException("Некорректный путь!");
    }
}
