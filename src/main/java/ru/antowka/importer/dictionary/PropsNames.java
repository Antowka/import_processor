package ru.antowka.importer.dictionary;

import ru.antowka.importer.model.PropModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PropsNames {

    private final static Map<String, PropModel> generalProps = new HashMap<String, PropModel>() {
        {
            put("Дата изменения", new PropModel("cm:modified", PropModel.PropType.DATE));
            put("Владелец", new PropModel("cm:owner", PropModel.PropType.STRING));
            put("Имя", new PropModel("cm:name", PropModel.PropType.STRING));
            put("Дата создания", new PropModel("cm:created", PropModel.PropType.DATE));
            put("Создатель", new PropModel("cm:creator", PropModel.PropType.STRING));
            put("Редактор", new PropModel("cm:modifier", PropModel.PropType.STRING));
            put("Заголовок", new PropModel("cm:title", PropModel.PropType.STRING));

            //Проверить, что тут все только общие
            put("На контроле", new PropModel("lecm-document-aspects:is-on-control", PropModel.PropType.BOOLEAN));
            put("Статус", new PropModel("lecm-statemachine:status", PropModel.PropType.STRING));
            put("Гриф конфиденциальности", new PropModel("lecm-eds-aspect:security-classification-assoc", PropModel.PropType.STRING));
            put("Комментарии ДДОиА", new PropModel("lecm-eds-ts:ddoia-comments-assoc", PropModel.PropType.STRING));
            put("Тип", new PropModel("lecm-document:doc-type", PropModel.PropType.STRING));
            put("Гриф конфиденциальности утвержден (СКХ). Служебное поле", new PropModel("lecm-eds-aspect:privacy-stamp-approved", PropModel.PropType.BOOLEAN));
            put("real registrator", new PropModel("lecm-document-aspects:registrator-assoc", PropModel.PropType.STRING));
            put("Строка представления для списка: Статус", new PropModel("lecm-document:list-present-string", PropModel.PropType.STRING));
            put("Краткое содержание", new PropModel("lecm-document:title", PropModel.PropType.STRING));
            put("Организация", new PropModel("lecm-orgstr-aspects:linked-organization-assoc", PropModel.PropType.STRING));
            put("Состояние подписания", new PropModel("lecm-signing-v2-aspects:signingState", PropModel.PropType.STRING));
            put("Автор", new PropModel("lecm-document:author-assoc", PropModel.PropType.STRING));
            put("Дата", new PropModel("lecm-document:doc-date", PropModel.PropType.DATE));
            put("Регистрационный номер", new PropModel("lecm-document:regnum", PropModel.PropType.STRING));
            put("Зарегистрирован", new PropModel("lecm-document-aspects:reg-data-is-registered", PropModel.PropType.BOOLEAN));
            put("История изменений", new PropModel("lecm-eds-ts:changes-history-assoc-ref", PropModel.PropType.STRING));
            put("Лишить текущего владельца прав на передаваемые документы", new PropModel("lecm-document:deprive-right", PropModel.PropType.BOOLEAN));
            put("Решение", new PropModel("lecm-routes-v2:decision", PropModel.PropType.STRING));
            put("Номер проекта", new PropModel("lecm-document-aspects:reg-project-data-number", PropModel.PropType.STRING));
            put("Подписанты", new PropModel("lecm-signing-v2-aspects:signerEmployeeAssoc", PropModel.PropType.STRING));
            put("Подразделение владелец документа", new PropModel("lecm-eds-aspect:eds-document-org-unit-owner-assoc", PropModel.PropType.STRING));
            put("Дата регистрации проекта", new PropModel("lecm-document-aspects:reg-project-data-date", PropModel.PropType.DATE));
            put("Подписан", new PropModel("lecm-signing-v2-aspects:isSigned", PropModel.PropType.BOOLEAN));
            put("Подписано вне КСЭД", new PropModel("lecm-signing-v2-aspects:signed-on-paper", PropModel.PropType.BOOLEAN));
            put("Расширенная строка представления", new PropModel("lecm-document:ext-present-string", PropModel.PropType.STRING));
            put("Дата регистрации", new PropModel("lecm-document-aspects:reg-data-date", PropModel.PropType.DATE));
            put("Статус изменён", new PropModel("lecm-document:status-changed-date", PropModel.PropType.DATE));
            put("Состояние исполнения", new PropModel("lecm-eds-aspect:execution-state", PropModel.PropType.STRING));
            put("Выполнение передачи прав при передаче документа", new PropModel("lecm-document:istransmit", PropModel.PropType.BOOLEAN));
            put("Категория документа", new PropModel("lecm-eds-aspect:document-category-assoc", PropModel.PropType.STRING));
            put("Количество листов", new PropModel("lecm-eds-aspect:sheets-number", PropModel.PropType.INT));
            put("Номер", new PropModel("lecm-document-aspects:reg-data-number", PropModel.PropType.STRING));
            put("Регистратор", new PropModel("rn-document-aspects:potential-registrars-assoc", PropModel.PropType.STRING));
            put("Срочно", new PropModel("lecm-eds-aspect:is-urgent", PropModel.PropType.BOOLEAN));
            put("Вид документа", new PropModel("lecm-eds-document:document-type-assoc", PropModel.PropType.STRING));
            put("Причины возврата", new PropModel("lecm-eds-ts:reasons-for-return-assoc", PropModel.PropType.STRING));
            put("Состояние ознакомления", new PropModel("lecm-review-ts:doc-review-state", PropModel.PropType.STRING));
            put("Разрешить редактирование владельца документа вручную", new PropModel("lecm-eds-aspect:change-eds-document-owner-manually", PropModel.PropType.BOOLEAN));
        }
    };

    private static final Map<DocType, Map<String, PropModel>> propsAssociationsWithNames = new HashMap<DocType, Map<String, PropModel>>() {{

        //Исходящий
        put(DocType.OUTGOING, new HashMap<String, PropModel>() {
            {
                put("Строка представления для списка", new PropModel("lecm-document:list-present-string", PropModel.PropType.STRING));
                put("Способ доставк (служебное)", new PropModel("external-mailing-list:items-assoc-delivery-method-assoc", PropModel.PropType.STRING));
                put("Флаг для отправки на подписание", new PropModel("lecm-outgoing:transit-to-signing", PropModel.PropType.BOOLEAN));
                put("Корреспондент", new PropModel("lecm-outgoing:contractor-assoc", PropModel.PropType.STRING));
                put("Подтверждать доставку", new PropModel("lecm-outgoing:confirm-delivery", PropModel.PropType.STRING));
                put("Подписан вне СЭД", new PropModel("lecm-outgoing:is-out-signing", PropModel.PropType.STRING));
                put("Руководитель-инициатор", new PropModel("lecm-outgoing:chief-initiator-assoc", PropModel.PropType.STRING));
                put("Документ отложен", new PropModel("lecm-outgoing:is-deferred", PropModel.PropType.BOOLEAN));
                put("Требуется ответ", new PropModel("lecm-outgoing:response-required", PropModel.PropType.BOOLEAN));
                put("Предыдущий автор", new PropModel("lecm-outgoing:previous-author", PropModel.PropType.STRING));
                put("Основание подписания", new PropModel("lecm-outgoing:signing-basis", PropModel.PropType.STRING));
            }
        });

        //Резолюция
        put(DocType.RESOLUTION, new HashMap<String, PropModel>() {
            {
                put("Сводная", new PropModel("lecm-resolutions:summary", PropModel.PropType.BOOLEAN));
                put("Флаг для отключения действий на статусе", new PropModel("lecm-resolutions:hold-on-project", PropModel.PropType.BOOLEAN));
                put("Срок исполнения - дней", new PropModel("lecm-resolutions:limitation-date-days", PropModel.PropType.INT));
                put("Создание в черновик", new PropModel("lecm-resolutions:is-draft", PropModel.PropType.BOOLEAN));
                put("Номер резолюции", new PropModel("lecm-resolutions:number", PropModel.PropType.STRING));
                put("Сигнал об аннулировании", new PropModel("lecm-resolutions:annul-signal", PropModel.PropType.BOOLEAN));
                put("Автозавершение (служебное)", new PropModel("lecm-resolutions:auto-complete", PropModel.PropType.BOOLEAN));
                put("Утверждено вне системы", new PropModel("lecm-resolutions:approved-outside-system", PropModel.PropType.BOOLEAN));
                put("Требуется решение Завершающего", new PropModel("lecm-resolutions:require-closers-decision", PropModel.PropType.BOOLEAN));
                put("Контроль", new PropModel("lecm-resolutions:control", PropModel.PropType.BOOLEAN));
                put("Атрибут индекса резолюции в родительском документе (служебное)", new PropModel("lecm-resolutions:child-index-counter", PropModel.PropType.INT));
                put("Взамен разосланного", new PropModel("lecm-resolutions:instead-of-send", PropModel.PropType.BOOLEAN));
                put("Сигнал о переводе в статус \"На исполнение\"", new PropModel("lecm-resolutions:transit-to-on-execution", PropModel.PropType.BOOLEAN));
                put("Дата выдачи", new PropModel("lecm-resolutions:transition-to-execution-date", PropModel.PropType.DATE));
                put("Доступно исполнителю, подписанту и всем адресатам СЗ", new PropModel("lecm-resolutions:available-for-esr-of-internal", PropModel.PropType.BOOLEAN));
                put("Просрочено", new PropModel("lecm-resolutions:is-expired", PropModel.PropType.BOOLEAN));
                put("Сигнал об аннулировании - причина", new PropModel("lecm-resolutions:annul-signal-reason", PropModel.PropType.STRING));
                put("Сигнал оn комплекта", new PropModel("lecm-resolutions:suite-signal", PropModel.PropType.STRING));
                put("Автор (служебное)", new PropModel("lecm-resolutions:author-assoc", PropModel.PropType.STRING));
                put("Контролер (служебное)", new PropModel("lecm-resolutions:controller-assoc", PropModel.PropType.STRING));
                put("Основание (служебное)", new PropModel("lecm-resolutions:base-assoc", PropModel.PropType.STRING));
                put("Документ-основание (служебное)", new PropModel("lecm-resolutions:base-document-assoc", PropModel.PropType.STRING));
                put("Способ исполнения (служебное)", new PropModel("lecm-resolutions:completion-method-assoc", PropModel.PropType.STRING));
                put("Срок исполнения - тип", new PropModel("lecm-resolutions:limitation-date-type", PropModel.PropType.STRING));
                put("Срок исполнения", new PropModel("lecm-resolutions:limitation-date", PropModel.PropType.STRING));
                put("Список поручений (служебное)", new PropModel("lecm-resolutions:errands-json", PropModel.PropType.STRING));
                put("Имя атрибута документа основания для срока исполнения (служебное)", new PropModel("lecm-resolutions:base-doc-execution-date-attr-name", PropModel.PropType.STRING));
                put("Основания для подготовки СЗ", new PropModel("lecm-resolutions:grounds-for-preparation-of-internal", PropModel.PropType.STRING));
                put("Группа контроля (служебное)", new PropModel("lecm-resolutions:control-group-assoc", PropModel.PropType.STRING));
                put("Контролеры пунктов", new PropModel("lecm-resolutions:items-assoc-controller-assoc-ref", PropModel.PropType.STRING));
                put("Назначил (служебное)", new PropModel("lecm-resolutions:assigned-assoc", PropModel.PropType.STRING));
            }
        });

        //Поручение
        put(DocType.ERRANDS, new HashMap<String, PropModel>() {
            {
                put("Ответственный исполнитель", new PropModel("lecm-errands:complex-executor-assoc-text-content", PropModel.PropType.STRING));
                put("Срок исполнения", new PropModel("lecm-errands:limitation-date", PropModel.PropType.DATE));
                put("Отменить подчиненные поручения", new PropModel("lecm-errands:cancel-children", PropModel.PropType.BOOLEAN));
                put("Требуется отчет", new PropModel("lecm-errands-dic:errand-type-report-required", PropModel.PropType.BOOLEAN));
                put("Флаг для отключения действий на статусе", new PropModel("lecm-errands:hold-on-project", PropModel.PropType.BOOLEAN));
                put("Уведомлять Автора", new PropModel("lecm-errands:author-notification", PropModel.PropType.BOOLEAN));
                put("Документ-основание", new PropModel("lecm-errands:base-document-assoc", PropModel.PropType.STRING));
                put("Заголовок документа-основания", new PropModel("lecm-errands:base-document-content", PropModel.PropType.STRING));
                put("Групповое поручение", new PropModel("lecm-errands:is-group", PropModel.PropType.BOOLEAN));
                put("Тип поручения", new PropModel("lecm-errands:category-assoc", PropModel.PropType.STRING));
                put("Сигнал об отмене", new PropModel("lecm-errands:cancellation-signal", PropModel.PropType.BOOLEAN));
                put("На внешнем контроле", new PropModel("lecm-errands-aspect:on-external-control", PropModel.PropType.BOOLEAN));
                put("Тип срока исполнения", new PropModel("lecm-errands:limitation-date-type", PropModel.PropType.STRING));
                put("На докладе", new PropModel("lecm-errands:on-report", PropModel.PropType.BOOLEAN));
                put("Текст поручения", new PropModel("lecm-errands:content", PropModel.PropType.STRING));
                put("Флаг для перехода в статус \"Закрыто\"", new PropModel("lecm-errands:transit-to-closed", PropModel.PropType.BOOLEAN));
            }
        });

        //Карточка согласования
        put(DocType.APPROVAL_CARD, new HashMap<String, PropModel>() {
            {
                put("Код вида документа (служебное)", new PropModel("rn-document-approval:document-kind-code",PropModel.PropType.STRING));
                put("Вид документа", new PropModel("rn-document-approval:document-kind-assoc",PropModel.PropType.STRING));
                put("Комментарий", new PropModel("rn-document-approval:comment",PropModel.PropType.STRING));
                put("Приложен протокол разногласий", new PropModel("lecm-approval-rn-aspects:document-disagreement-protocol-added",PropModel.PropType.BOOLEAN));
                put("Разрешить редактирование владельца документа вручную", new PropModel("lecm-eds-aspect:change-eds-document-owner-manually",PropModel.PropType.BOOLEAN));
                put("Антикоррупционная экспертиза", new PropModel("rn-document-approval:anti-corruption-expertise",PropModel.PropType.BOOLEAN));
            }
        });
    }};

    public static PropModel getPropNameByPresentString(DocType docType, String presentString) {

        final PropModel propModel = generalProps.get(presentString);

        if (Objects.nonNull(propModel)) {
            return propModel;
        }

        return propsAssociationsWithNames
                .get(docType)
                .computeIfAbsent(presentString, key -> new PropModel(key.replace(" ", "_"), PropModel.PropType.FAIL));
    }
}
