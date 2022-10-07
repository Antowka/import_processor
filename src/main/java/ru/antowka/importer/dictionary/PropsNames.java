package ru.antowka.importer.dictionary;

import org.springframework.stereotype.Component;
import ru.antowka.importer.model.PropModel;

import java.util.HashMap;
import java.util.Map;

public class PropsNames {
    
    private static Map<String, PropModel> propsAssociationsWithNames = new HashMap<String, PropModel>() {{
        put("Гриф конфиденциальности утвержден (СКХ). Служебное поле", new PropModel("lecm-eds-aspect:privacy-stamp-approved", PropModel.PropType.BOOLEAN));
        put("Владелец", new PropModel("cm:owner", PropModel.PropType.STRING));
        put("real registrator", new PropModel("lecm-document-aspects:registrator-assoc-text-content", PropModel.PropType.STRING));
        put("Сводная", new PropModel("lecm-resolutions:summary", PropModel.PropType.BOOLEAN));
        put("Флаг для отключения действий на статусе", new PropModel("lecm-resolutions:hold-on-project", PropModel.PropType.BOOLEAN));
        put("Строка представления для списка: Статус", new PropModel("lecm-document:list-present-string", PropModel.PropType.STRING));
        put("Краткое содержание", new PropModel("lecm-document:title", PropModel.PropType.STRING));
        put("Срок исполнения - дней", new PropModel("lecm-resolutions:limitation-date-days", PropModel.PropType.INT));
        put("Создание в черновик", new PropModel("lecm-resolutions:is-draft", PropModel.PropType.BOOLEAN));
        put("Организация", new PropModel("lecm-orgstr-aspects:linked-organization-assoc-text-content", PropModel.PropType.STRING));
        put("Состояние подписания", new PropModel("lecm-signing-v2-aspects:signingState", PropModel.PropType.STRING));
        put("Автор", new PropModel("lecm-document:author-assoc-text-content", PropModel.PropType.STRING));
        put("Статус", new PropModel("lecm-statemachine:status", PropModel.PropType.STRING));
        put("Гриф конфиденциальности", new PropModel("lecm-eds-aspect:security-classification-assoc-text-content", PropModel.PropType.STRING));
        put("Дата", new PropModel("lecm-document:doc-date", PropModel.PropType.DATE));
        put("Комментарии ДДОиА", new PropModel("lecm-eds-ts:ddoia-comments-assoc-text-content", PropModel.PropType.STRING));
        put("Регистрационный номер", new PropModel("lecm-document:regnum", PropModel.PropType.STRING));
        put("Имя", new PropModel("cm:name", PropModel.PropType.STRING));
        put("Зарегистрирован", new PropModel("lecm-document-aspects:reg-data-is-registered", PropModel.PropType.BOOLEAN));
        put("Дата создания", new PropModel("cm:created", PropModel.PropType.DATE));
        put("История изменений", new PropModel("lecm-eds-ts:changes-history-assoc-ref", PropModel.PropType.STRING));
        put("Лишить текущего владельца прав на передаваемые документы", new PropModel("lecm-document:deprive-right", PropModel.PropType.BOOLEAN));
        put("Решение", new PropModel("lecm-routes-v2:decision", PropModel.PropType.STRING));
        put("Номер проекта", new PropModel("lecm-document-aspects:reg-project-data-number", PropModel.PropType.STRING));
        put("Номер резолюции", new PropModel("lecm-resolutions:number", PropModel.PropType.STRING));
        put("Подписанты", new PropModel("lecm-signing-v2-aspects:signerEmployeeAssoc-text-content", PropModel.PropType.STRING));
        put("Сигнал об аннулировании", new PropModel("lecm-resolutions:annul-signal", PropModel.PropType.BOOLEAN));
        put("Подразделение владелец документа", new PropModel("lecm-eds-aspect:eds-document-org-unit-owner-assoc-text-content", PropModel.PropType.STRING));
        put("Дата регистрации проекта", new PropModel("lecm-document-aspects:reg-project-data-date", PropModel.PropType.DATE));
        put("Автозавершение (служебное)", new PropModel("lecm-resolutions:auto-complete", PropModel.PropType.BOOLEAN));
        put("Создатель", new PropModel("cm:creator",PropModel.PropType.STRING));
        put("Подписан", new PropModel("lecm-signing-v2-aspects:isSigned",PropModel.PropType.BOOLEAN));
        put("Тип", new PropModel("lecm-document:doc-type",PropModel.PropType.STRING));
        put("Подписано вне КСЭД", new PropModel("lecm-signing-v2-aspects:signed-on-paper",PropModel.PropType.BOOLEAN));
        put("Утверждено вне системы", new PropModel("lecm-resolutions:approved-outside-system",PropModel.PropType.BOOLEAN));
        put("Требуется решение Завершающего", new PropModel("lecm-resolutions:require-closers-decision",PropModel.PropType.BOOLEAN));
        put("Контроль", new PropModel("lecm-resolutions:control",PropModel.PropType.BOOLEAN));

        put("Атрибут индекса резолюции в родительском документе (служебное)", new PropModel("lecm-resolutions:child-index-counter",PropModel.PropType.INT));
        put("Взамен разосланного", new PropModel("lecm-resolutions:instead-of-send",PropModel.PropType.BOOLEAN));
        put("Расширенная строка представления", new PropModel("lecm-document:ext-present-string",PropModel.PropType.STRING));
        put("Дата регистрации", new PropModel("lecm-document-aspects:reg-data-date",PropModel.PropType.DATE));
        put("Сигнал о переводе в статус \"На исполнение\"", new PropModel("lecm-resolutions:transit-to-on-execution",PropModel.PropType.BOOLEAN));
        put("Дата выдачи", new PropModel("lecm-resolutions:transition-to-execution-date",PropModel.PropType.DATE));
        put("Статус изменён", new PropModel("lecm-document:status-changed-date",PropModel.PropType.DATE));
        put("Редактор", new PropModel("cm:modifier",PropModel.PropType.STRING));
        put("Заголовок", new PropModel("cm:title",PropModel.PropType.STRING));
        put("Состояние исполнения", new PropModel("lecm-eds-aspect:execution-state",PropModel.PropType.STRING));
        put("Доступно исполнителю, подписанту и всем адресатам СЗ", new PropModel("lecm-resolutions:available-for-esr-of-internal",PropModel.PropType.BOOLEAN));
        put("Выполнение передачи прав при передаче документа", new PropModel("lecm-document:istransmit",PropModel.PropType.BOOLEAN));
        put("Категория документа", new PropModel("lecm-eds-aspect:document-category-assoc-text-conte",PropModel.PropType.STRING));
        put("Просрочено", new PropModel("lecm-resolutions:is-expired",PropModel.PropType.BOOLEAN));
        put("Количество листов", new PropModel("lecm-eds-aspect:sheets-number",PropModel.PropType.INT));
        put("Номер", new PropModel("lecm-document-aspects:reg-data-number",PropModel.PropType.STRING));
        put("Регистратор", new PropModel("rn-document-aspects:potential-registrars-assoc-text-content",PropModel.PropType.STRING));
        put("Дата изменения", new PropModel("cm:modified",PropModel.PropType.DATE));
        put("Срочно", new PropModel("lecm-eds-aspect:is-urgent",PropModel.PropType.BOOLEAN));
    }};

    public static PropModel getPropNameByPresentString(String presentString) {
        return propsAssociationsWithNames
                .computeIfAbsent(presentString, key -> new PropModel(key.replace(" ", "_"), PropModel.PropType.FAIL));
    }
}
