package ru.antowka.importer.model;

import lombok.Data;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Представление даты в виде структуры папок
 * Формат: 30.09.2022
 */
@Data
public class DateFolderModel {

    private int year;

    private int month;

    private int day;


    public DateFolderModel(String date) {
        final String[] splitDate = date.split("\\.");
        if (splitDate.length != 3) {
            System.out.println("Date incorrect: " + date);
            throw new IllegalArgumentException("Date incorrect: " + date);
        }

        year = Integer.parseInt(splitDate[2]);
        month = Integer.parseInt(splitDate[1]);
        day = Integer.parseInt(splitDate[0]);
    }

    /**
     * Добавляем +1 день к текущей дате
     */
    public void addDay() {
        int lastDayOfMonth = 28;
        if (month != 2) { //если февраль то оставляем как есть 28 (29.02 - игнорируем :))
            if (month % 2 == 0) { //Чётный месяц
                lastDayOfMonth = 30;
            } else {  //Нечётный месяц
                lastDayOfMonth = 31;
            }
        }

        if (day < lastDayOfMonth) {
            day++;
        } else {
            month++;
            day = 1;
        }
    }

    /**
     * Формируем путь
     *
     * @return
     */
    public Path buildPath() {
        return Paths.get(String.valueOf(year), String.valueOf(month), String.valueOf(day));
    }

    /**
     * Сравнение дат, больше или равна дата объекта к переданному в аргумент
     *
     * @param dateForCompare дата для сравнения
     * @return
     */
    public boolean moreThan(DateFolderModel dateForCompare) {
        return this.year >= dateForCompare.getYear() && this.month >= dateForCompare.month && this.day >= dateForCompare.day;
    }

    @Override
    public boolean equals(Object o) {
        DateFolderModel dateForCompare = (DateFolderModel) o;
        return this.year == dateForCompare.getYear() && this.month == dateForCompare.month && this.day == dateForCompare.day;
    }
}
