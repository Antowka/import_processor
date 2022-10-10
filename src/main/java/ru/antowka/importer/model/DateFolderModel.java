package ru.antowka.importer.model;

import lombok.Data;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Представление даты в виде структуры папок
 * Формат: 30.09.2022
 */
@Data
public class DateFolderModel {

    private int year;

    private int month;

    private int day;

    private Date date;

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");


    public DateFolderModel(String date) {
        final String[] splitDate = date.split("\\.");
        if (splitDate.length != 3) {
            System.out.println("Date incorrect: " + date);
            throw new IllegalArgumentException("Date incorrect: " + date);
        }

        try {
            this.date = formatter.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date format is wrong (use dd.MM.yyyy): " + date);
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

        try {
            this.date = formatter.parse(day + "." + month + "." + year);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date format is wrong (use dd.MM.yyyy): " + date);
        }
    }

    /**
     * Формируем путь
     *
     * @return
     */
    public String buildPath() {
        return Paths.get(String.valueOf(year), String.valueOf(month), String.valueOf(day)).toString();
    }

    /**
     * Сравнение дат, больше или равна дата объекта к переданному в аргумент
     *
     * @param dateForCompare дата для сравнения
     * @return
     */
    public boolean moreThan(Date dateForCompare) {
        return this.date.after(dateForCompare);
    }

    @Override
    public boolean equals(Object o) {
        DateFolderModel dateForCompare = (DateFolderModel) o;
        return this.date.equals(dateForCompare.getDate());
    }

    public Date getDate() {
        return this.date;
    }

    public String getDateForOutputPath() {
        return formatter.format(this.date);
    }
}
