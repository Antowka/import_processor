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
            put("Гриф конфиденциальности", new PropModel("lecm-eds-aspect:security-classification-assoc", PropModel.PropType.ASSOC));
            put("Комментарии ДДОиА", new PropModel("lecm-eds-ts:ddoia-comments-assoc", PropModel.PropType.ASSOC));
            put("Тип", new PropModel("lecm-document:doc-type", PropModel.PropType.STRING));
            put("Гриф конфиденциальности утвержден (СКХ). Служебное поле", new PropModel("lecm-eds-aspect:privacy-stamp-approved", PropModel.PropType.BOOLEAN));
            put("Строка представления для списка: Статус", new PropModel("lecm-document:list-present-string", PropModel.PropType.STRING));
            put("Краткое содержание", new PropModel("lecm-document:title", PropModel.PropType.STRING));
            put("Организация", new PropModel("lecm-orgstr-aspects:linked-organization-assoc", PropModel.PropType.ASSOC));
            put("Состояние подписания", new PropModel("lecm-signing-v2-aspects:signingState", PropModel.PropType.STRING));
            put("Автор", new PropModel("lecm-document:author-assoc", PropModel.PropType.STRING));
            put("Дата", new PropModel("lecm-document:doc-date", PropModel.PropType.DATE));
            put("Регистрационный номер", new PropModel("lecm-document:regnum", PropModel.PropType.STRING));
            put("Зарегистрирован", new PropModel("lecm-document-aspects:reg-data-is-registered", PropModel.PropType.BOOLEAN));
            put("История изменений", new PropModel("lecm-eds-ts:changes-history-assoc-ref", PropModel.PropType.ASSOC));
            put("Лишить текущего владельца прав на передаваемые документы", new PropModel("lecm-document:deprive-right", PropModel.PropType.BOOLEAN));
            put("Решение", new PropModel("lecm-routes-v2:decision", PropModel.PropType.STRING));
            put("Номер проекта", new PropModel("lecm-document-aspects:reg-project-data-number", PropModel.PropType.STRING));
            put("Подписанты", new PropModel("lecm-signing-v2-aspects:signerEmployeeAssoc", PropModel.PropType.ASSOC));
            put("Подразделение владелец документа", new PropModel("lecm-eds-aspect:eds-document-org-unit-owner-assoc", PropModel.PropType.ASSOC));
            put("Дата регистрации проекта", new PropModel("lecm-document-aspects:reg-project-data-date", PropModel.PropType.DATE));
            put("Подписан", new PropModel("lecm-signing-v2-aspects:isSigned", PropModel.PropType.BOOLEAN));
            put("Подписано вне КСЭД", new PropModel("lecm-signing-v2-aspects:signed-on-paper", PropModel.PropType.BOOLEAN));
            put("Расширенная строка представления", new PropModel("lecm-document:ext-present-string", PropModel.PropType.STRING));
            put("Дата регистрации", new PropModel("lecm-document-aspects:reg-data-date", PropModel.PropType.DATE));
            put("Статус изменён", new PropModel("lecm-document:status-changed-date", PropModel.PropType.DATE));
            put("Состояние исполнения", new PropModel("lecm-eds-aspect:execution-state", PropModel.PropType.STRING));
            put("Выполнение передачи прав при передаче документа", new PropModel("lecm-document:istransmit", PropModel.PropType.BOOLEAN));
            put("Категория документа", new PropModel("lecm-eds-aspect:document-category-assoc", PropModel.PropType.ASSOC));
            put("Количество листов", new PropModel("lecm-eds-aspect:sheets-number", PropModel.PropType.INT));
            put("Номер", new PropModel("lecm-document-aspects:reg-data-number", PropModel.PropType.STRING));
            put("Регистратор", new PropModel("rn-document-aspects:potential-registrars-assoc", PropModel.PropType.ASSOC));
            put("Срочно", new PropModel("lecm-eds-aspect:is-urgent", PropModel.PropType.BOOLEAN));
            put("Вид документа", new PropModel("lecm-eds-document:document-type-assoc", PropModel.PropType.ASSOC));
            put("Причины возврата", new PropModel("lecm-eds-ts:reasons-for-return-assoc", PropModel.PropType.ASSOC));
            put("Состояние ознакомления", new PropModel("lecm-review-ts:doc-review-state", PropModel.PropType.STRING));
            put("Разрешить редактирование владельца документа вручную", new PropModel("lecm-eds-aspect:change-eds-document-owner-manually", PropModel.PropType.BOOLEAN));
            put("Закрыть неисполненные подчиненные поручения", new PropModel("lecm-eds-aspect:completion-signal-close-child",PropModel.PropType.BOOLEAN));
            put("Инсайдерская информация", new PropModel("lecm-eds-aspect:inside-information",PropModel.PropType.BOOLEAN));
            put("Предыдущий статус", new PropModel("lecm-eds-aspect:previous-status",PropModel.PropType.STRING));
            put("Просрочено", new PropModel("lecm-eds-document:is-expired",PropModel.PropType.STRING));
            put("Содержание", new PropModel("lecm-eds-document:summaryContent",PropModel.PropType.STRING));
            put("Получатели", new PropModel("lecm-eds-document:recipients-assoc", PropModel.PropType.ASSOC));
            put("Примечание", new PropModel("lecm-eds-document:note", PropModel.PropType.STRING));
            put("Исполнитель", new PropModel("lecm-eds-document:executor-assoc", PropModel.PropType.ASSOC));
            put("Флаг необходимости перевода на регистрацию", new PropModel("lecm-eds-aspect:move-to-registration", PropModel.PropType.STRING));
            put("Исполнители поручений", new PropModel("lecm-errands-aspect:errands-executors-assoc", PropModel.PropType.ASSOC));
            //put("Состояние исполнения", new PropModel("prop_lecm-eds-aspect:execution-state", PropModel.PropType.STRING));
        }
    };

    private static final Map<DocType, Map<String, PropModel>> propsAssociationsWithNames = new HashMap<DocType, Map<String, PropModel>>() {{

        //Исходящий
        put(DocType.OUTGOING, new HashMap<String, PropModel>() {
            {
                put("Строка представления для списка", new PropModel("lecm-document:list-present-string", PropModel.PropType.STRING));
                put("Способ доставк (служебное)", new PropModel("external-mailing-list:items-assoc-delivery-method-assoc", PropModel.PropType.ASSOC));
                put("Флаг для отправки на подписание", new PropModel("lecm-outgoing:transit-to-signing", PropModel.PropType.BOOLEAN));
                put("Корреспондент", new PropModel("lecm-outgoing:contractor-assoc", PropModel.PropType.ASSOC));
                put("Подтверждать доставку", new PropModel("lecm-outgoing:confirm-delivery", PropModel.PropType.STRING));
                put("Подписан вне СЭД", new PropModel("lecm-outgoing:is-out-signing", PropModel.PropType.STRING));
                put("Руководитель-инициатор", new PropModel("lecm-outgoing:chief-initiator-assoc", PropModel.PropType.ASSOC));
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
                put("Автор (служебное)", new PropModel("lecm-resolutions:author-assoc", PropModel.PropType.ASSOC));
                put("Контролер (служебное)", new PropModel("lecm-resolutions:controller-assoc", PropModel.PropType.ASSOC));
                put("Основание (служебное)", new PropModel("lecm-resolutions:base-assoc", PropModel.PropType.STRING));
                put("Документ-основание (служебное)", new PropModel("lecm-resolutions:base-document-assoc", PropModel.PropType.ASSOC));
                put("Способ исполнения (служебное)", new PropModel("lecm-resolutions:completion-method-assoc", PropModel.PropType.ASSOC));
                put("Срок исполнения - тип", new PropModel("lecm-resolutions:limitation-date-type", PropModel.PropType.STRING));
                put("Срок исполнения", new PropModel("lecm-resolutions:limitation-date", PropModel.PropType.STRING));
                put("Список поручений (служебное)", new PropModel("lecm-resolutions:errands-json", PropModel.PropType.STRING));
                put("Имя атрибута документа основания для срока исполнения (служебное)", new PropModel("lecm-resolutions:base-doc-execution-date-attr-name", PropModel.PropType.STRING));
                put("Основания для подготовки СЗ", new PropModel("lecm-resolutions:grounds-for-preparation-of-internal", PropModel.PropType.STRING));
                put("Группа контроля (служебное)", new PropModel("lecm-resolutions:control-group-assoc", PropModel.PropType.ASSOC));
                put("Контролеры пунктов", new PropModel("lecm-resolutions:items-assoc-controller-assoc-ref", PropModel.PropType.ASSOC));
                put("Назначил (служебное)", new PropModel("lecm-resolutions:assigned-assoc", PropModel.PropType.ASSOC));
            }
        });

        //Поручение
        put(DocType.ERRANDS, new HashMap<String, PropModel>() {
            {
                put("Ответственный исполнитель", new PropModel("lecm-errands:complex-executor-assoc", PropModel.PropType.ASSOC));
                put("Срок исполнения", new PropModel("lecm-errands:limitation-date", PropModel.PropType.DATE));
                put("Отменить подчиненные поручения", new PropModel("lecm-errands:cancel-children", PropModel.PropType.BOOLEAN));
                put("Требуется отчет", new PropModel("lecm-errands-dic:errand-type-report-required", PropModel.PropType.BOOLEAN));
                put("Флаг для отключения действий на статусе", new PropModel("lecm-errands:hold-on-project", PropModel.PropType.BOOLEAN));
                put("Уведомлять Автора", new PropModel("lecm-errands:author-notification", PropModel.PropType.BOOLEAN));
                put("Документ-основание", new PropModel("lecm-errands:base-document-assoc", PropModel.PropType.ASSOC));
                put("Заголовок документа-основания", new PropModel("lecm-errands:base-document-content", PropModel.PropType.STRING));
                put("Групповое поручение", new PropModel("lecm-errands:is-group", PropModel.PropType.BOOLEAN));
                put("Тип поручения", new PropModel("lecm-errands:type-assoc", PropModel.PropType.ASSOC));
                put("Сигнал об отмене", new PropModel("lecm-errands:cancellation-signal", PropModel.PropType.BOOLEAN));
                put("На внешнем контроле", new PropModel("lecm-errands-aspect:on-external-control", PropModel.PropType.BOOLEAN));
                put("Тип срока исполнения", new PropModel("lecm-errands:limitation-date-type", PropModel.PropType.STRING));
                put("На докладе", new PropModel("lecm-errands:on-report", PropModel.PropType.BOOLEAN));
                put("Текст поручения", new PropModel("lecm-errands:content", PropModel.PropType.STRING));
                put("Флаг для перехода в статус \"Закрыто\"", new PropModel("lecm-errands:transit-to-closed", PropModel.PropType.BOOLEAN));
                put("Середина срока исполнения", new PropModel("lecm-errands:half-limit-date",PropModel.PropType.DATE));
                put("Срок исполнения в днях", new PropModel("lecm-errands:limitation-date-days",PropModel.PropType.INT));
                put("Атрибут индекса периодического поручения", new PropModel("lecm-errands:periodical-index-counter",PropModel.PropType.STRING));
                put("О принятии отчёта получателем", new PropModel("lecm-errands:report-is-accepted",PropModel.PropType.BOOLEAN));
                put("Исполнитель", new PropModel("lecm-errands:execution-report-employee-fake",PropModel.PropType.STRING));
                put("Тип документа-основания", new PropModel("lecm-eds-aspect:base-document-type",PropModel.PropType.STRING));
                put("Учитывать в КПЭ", new PropModel("lecm-errands:is-consider-KPI",PropModel.PropType.BOOLEAN));
                put("Число повторений", new PropModel("lecm-errands:reiteration-count",PropModel.PropType.INT));
                put("Периодичность доклада", new PropModel("lecm-errands:periodic-report-order-radio",PropModel.PropType.STRING));
                put("Номер поручения", new PropModel("lecm-errands:number",PropModel.PropType.STRING));
                put("Бессрочное действие грифа", new PropModel("lecm-eds-aspect:non-expiring-privacy-stamp",PropModel.PropType.STRING));
                put("Категория поручения", new PropModel("lecm-errands:category-assoc",PropModel.PropType.ASSOC));
                put("Контролеры", new PropModel("lecm-errands:controller-assoc",PropModel.PropType.ASSOC));
                put("Переход после исполнения (служебное)", new PropModel("lecm-errands:execute-result",PropModel.PropType.STRING));
                put("Периодический отчет", new PropModel("lecm-errands:is-periodic-report",PropModel.PropType.BOOLEAN));
                put("Контроль СВА", new PropModel("lecm-errands:is-control-SVA",PropModel.PropType.BOOLEAN));
                put("часы", new PropModel("lecm-errands:child-index-counter",PropModel.PropType.INT));
                put("Запросы", new PropModel("lecm-errands-ts:request-assoc",PropModel.PropType.ASSOC));
                put("Статус отчета", new PropModel("lecm-errands:execution-report-status",PropModel.PropType.STRING));
                put("Флаг для перехода на статус \"Исполнено\"", new PropModel("lecm-errands:transit-to-executed",PropModel.PropType.BOOLEAN));
                put("Без утверждения Инициатором", new PropModel("lecm-errands:without-initiator-approval",PropModel.PropType.BOOLEAN));
                put("Группа контроля", new PropModel("lecm-errands:control-group-assoc",PropModel.PropType.ASSOC));
                put("Необходима служебная записка", new PropModel("lecm-errands:is-internal-doc-required-for-intermediate-report",PropModel.PropType.BOOLEAN));
                put("В течение", new PropModel("lecm-errands:period-during",PropModel.PropType.INT));
                put("Краткосрочное", new PropModel("lecm-errands:is-limit-short",PropModel.PropType.BOOLEAN));
                put("Номер документа-основания", new PropModel("lecm-errands:additional-doc-number",PropModel.PropType.STRING));
                put("Исполнить точно в срок", new PropModel("lecm-errands:just-in-time",PropModel.PropType.BOOLEAN));
                put("Служебный атрибут для всплытия событий", new PropModel("lecm-errands:event-counter",PropModel.PropType.BOOLEAN));
                put("Флаг для перехода на статус \"На исполнении\"", new PropModel("lecm-errands:transit-to-on-execution",PropModel.PropType.BOOLEAN));
                put("Отчёты соисполнителей", new PropModel("lecm-errands-ts:coexecutor-reports-assoc",PropModel.PropType.ASSOC));
                put("График предоставления доклада", new PropModel("lecm-errands:periodic-report-date",PropModel.PropType.STRING));
                put("Доступно исполнителю, подписанту и всем адресатам СЗ", new PropModel("lecm-errands:available-for-esr-of-internal",PropModel.PropType.BOOLEAN));
                put("Направлять периодически", new PropModel("lecm-errands:periodically",PropModel.PropType.BOOLEAN));
                put("Характер поручения", new PropModel("lecm-errands:character-assoc",PropModel.PropType.ASSOC));
                put("Закрывает вышестоящее поручение", new PropModel("lecm-errands:auto-close",PropModel.PropType.BOOLEAN));
                put("Просрочено", new PropModel("lecm-errands:is-expired",PropModel.PropType.BOOLEAN));
                put("Короткое", new PropModel("lecm-errands:is-short",PropModel.PropType.BOOLEAN));
                put("Предоставить доступ участникам дерева поручений", new PropModel("lecm-errands-aspect:provide-access-to-errands-tree",PropModel.PropType.BOOLEAN));
                put("Контроль УКиП", new PropModel("lecm-errands:is-control-UKiP",PropModel.PropType.BOOLEAN));
            }
        });

        // Входящий
        put(DocType.INCOMING, new HashMap<String, PropModel>() {
            {
                put("Представитель корреспондента", new PropModel("lecm-incoming:addressee-assoc", PropModel.PropType.ASSOC));
                put("Изменён срок исполнения", new PropModel("lecm-incoming:execution-date-changed", PropModel.PropType.BOOLEAN));
                put("Исходящий номер", new PropModel("lecm-incoming:outgoing-number", PropModel.PropType.STRING));
                put("Акционер ПАО «НК Роснефть»", new PropModel("lecm-incoming:is-correspondent-rosneft-stockholder", PropModel.PropType.BOOLEAN));
                put("Переход в статус \"Зарегистрирован\"", new PropModel("lecm-incoming:auto-transition-to-registered", PropModel.PropType.BOOLEAN));
                put("Превышен срок предоставления ответа", new PropModel("lecm-incoming:expired-execution-date", PropModel.PropType.BOOLEAN));
                put("Адресат", new PropModel("lecm-incoming:recipient-assoc", PropModel.PropType.ASSOC));
                put("Поступившее по канала", new PropModel("llecm-incoming:is-by-channel", PropModel.PropType.BOOLEAN));
                put("UID организации отправителя", new PropModel("lecm-medo-aspects:incoming-organization-uid", PropModel.PropType.STRING));
                put("Переход в статус \"Экспертное рассмотрение\"", new PropModel("lecm-incoming:auto-transition-to-expert-review", PropModel.PropType.BOOLEAN));
                put("Вскрытие упаковки", new PropModel("lecm-incoming:is-open", PropModel.PropType.BOOLEAN));
                put("Показать признак \"Требуется ответ\"", new PropModel("lecm-incoming:show-response-required", PropModel.PropType.BOOLEAN));
                put("Создатели резолюций по входящему (Служебное)", new PropModel("lecm-incoming:resolution-creators", PropModel.PropType.STRING));
                put("Переход в статус \"На исполнении\"", new PropModel("lecm-incoming:auto-transition-to-execution", PropModel.PropType.BOOLEAN));
                put("Способ доставки", new PropModel("lecm-incoming:delivery-method-assoc", PropModel.PropType.ASSOC));
                put("Нарушение целостности", new PropModel("lecm-incoming:integrity-fault", PropModel.PropType.BOOLEAN));
                put("UID Временного документа", new PropModel("lecm-medo-aspects:incoming-temp-document-uid", PropModel.PropType.STRING));
                put("Нарушение целостности вложений", new PropModel("lecm-incoming:attachments-integrity-fault", PropModel.PropType.BOOLEAN));
                put("Нерегистрируемый", new PropModel("lecm-incoming:is-not-registered", PropModel.PropType.BOOLEAN));
                put("Номер накладной", new PropModel("lecm-incoming:invoice-number", PropModel.PropType.STRING));
                put("Дата исходящего", new PropModel("lecm-incoming:outgoing-date", PropModel.PropType.DATE));
                put("Правительственное поручение", new PropModel("lecm-incoming:is-government-errand", PropModel.PropType.BOOLEAN));
                put("Содержит персональные данные", new PropModel("lecm-eds-aspect:contains-personal-data", PropModel.PropType.BOOLEAN));
                put("Лично", new PropModel("lecm-incoming:in-person", PropModel.PropType.BOOLEAN));
                put("Рассматривающий", new PropModel("lecm-incoming:reviewer", PropModel.PropType.STRING));
                put("Обнаружено отсутствие маркировки", new PropModel("lecm-incoming:marking-not-found", PropModel.PropType.BOOLEAN));
                put("Регистрационный номер родительского документа", new PropModel("lecm-incoming:parent-regnum", PropModel.PropType.STRING));
                put("Повторная обработка", new PropModel("lecm-incoming:is-retreatment", PropModel.PropType.BOOLEAN));
                put("Документ был направлен адресатам", new PropModel("lecm-incoming:directed-to-addressees", PropModel.PropType.BOOLEAN));
                put("Категории получателей (служебное)", new PropModel("lecm-incoming:recipient-categories", PropModel.PropType.STRING));
                put("Документ был направлен экспертам", new PropModel("lecm-incoming:directed-to-experts", PropModel.PropType.BOOLEAN));
                put("Возврат отправителю", new PropModel("lecm-incoming:return-to-sender", PropModel.PropType.BOOLEAN));
                put("Доработка", new PropModel("lecm-incoming:finalization", PropModel.PropType.BOOLEAN));
                put("Корреспондент", new PropModel("lecm-incoming:sender-assoc",PropModel.PropType.ASSOC));
                put("Требуется ответ", new PropModel("lecm-incoming:response-required",PropModel.PropType.BOOLEAN));
                put("Переход в статус \"Исполнен\"", new PropModel("lecm-incoming:transition-to-execute",PropModel.PropType.STRING));
            }
        });


        //Карточка согласования
        put(DocType.APPROVAL_CARD, new HashMap<String, PropModel>() {
            {
                put("Код вида документа (служебное)", new PropModel("rn-document-approval:document-kind-code",PropModel.PropType.STRING));
                put("Вид документа", new PropModel("rn-document-approval:document-kind-assoc",PropModel.PropType.ASSOC));
                put("Комментарий", new PropModel("rn-document-approval:comment",PropModel.PropType.STRING));
                put("Приложен протокол разногласий", new PropModel("lecm-approval-rn-aspects:document-disagreement-protocol-added",PropModel.PropType.BOOLEAN));
                put("Антикоррупционная экспертиза", new PropModel("rn-document-approval:anti-corruption-expertise",PropModel.PropType.BOOLEAN));
            }
        });
        // СЗ
        put(DocType.INTERNAL, new HashMap<String, PropModel>() {
            {
                put("Прорабатывать в", new PropModel("lecm-internal:elaborated-in",PropModel.PropType.STRING));
                put("Получены все ответы", new PropModel("lecm-internal:all-answers-complete",PropModel.PropType.BOOLEAN));
                put("Основания для подготовки СЗ", new PropModel("lecm-internal-table-structure:base-assoc",PropModel.PropType.ASSOC));
                put("Основание для подготовки", new PropModel("lecm-internal:basis-from-create",PropModel.PropType.STRING));
                put("Ответы", new PropModel("lecm-internal-table-structure:answers-assoc",PropModel.PropType.ASSOC));
                put("Получатели", new PropModel("lecm-internal:recipients-assoc",PropModel.PropType.ASSOC));
                put("Обоснование", new PropModel("lecm-internal_urgency-reason",PropModel.PropType.STRING));
                put("Личный доклад", new PropModel("lecm-internal:personal-report", PropModel.PropType.BOOLEAN));
                put("Перевести на исполнение", new PropModel("lecm-internal:transit-to-execution", PropModel.PropType.BOOLEAN));
                put("Завершать работу по документу автоматически", new PropModel("lecm-internal:auto-complete", PropModel.PropType.BOOLEAN));
                put("Руководитель-инициатор", new PropModel("lecm-internal:chief-initiator-assoc", PropModel.PropType.ASSOC));
                put("Рассматривающий", new PropModel("lecm-internal:reviewer", PropModel.PropType.STRING));
                put("Адресаты", new PropModel("lecm-internal:recipients-assoc", PropModel.PropType.ASSOC));
                put("Данные о возврате с проработки", new PropModel("lecm-internal-table-structure:return-from-elaboration-assoc", PropModel.PropType.ASSOC));
                put("Доступность добавления оснований для подготовки СЗ", new PropModel("lecm-internal:add-grounds-documents-available", PropModel.PropType.BOOLEAN));
                put("Новый запрос", new PropModel("lecm-internal:new-query", PropModel.PropType.BOOLEAN));
                put("Оперативно", new PropModel("lecm-internal:is-operatively", PropModel.PropType.BOOLEAN));
            }
        });
        //Протокол
        put(DocType.PROTOCOL, new HashMap<String, PropModel>() {
            {
                put("Активные рассматривающие", new PropModel("lecm-review-ts:active-reviewers",PropModel.PropType.STRING));
                put("Председатель", new PropModel("lecm-protocol:meeting-chairman-assoc", PropModel.PropType.ASSOC));
                put("На докладе", new PropModel("lecm-protocol:on-report", PropModel.PropType.BOOLEAN));
                put("Заочное", new PropModel("lecm-protocol:extramural", PropModel.PropType.BOOLEAN));
                put("Согласование сторонними участниками", new PropModel("lecm-protocol:need-third-party-approve", PropModel.PropType.BOOLEAN));
                put("Участники", new PropModel("lecm-protocol:attended-assoc", PropModel.PropType.ASSOC));
                put("Сторонние участники", new PropModel("lecm-protocol:third-party-attendees", PropModel.PropType.STRING));
                put("Секретарь", new PropModel("lecm-protocol:secretary-assoc", PropModel.PropType.ASSOC));
                put("Дата совещания (заседания)", new PropModel("lecm-protocol:meeting-date", PropModel.PropType.DATE));
                put("Состояние исполнения", new PropModel("lecm-review-aspects:related-review-state", PropModel.PropType.STRING));
                put("Пункты", new PropModel("lecm-protocol-ts:points-assoc-text-content", PropModel.PropType.ASSOC));
            }
        });
        // РД
        put(DocType.ORD, new HashMap<String, PropModel>() {
            {
                put("Перечень внутренних документов (из Lotus)", new PropModel("lecm-ord:internal-documents-list",PropModel.PropType.STRING));
                put("Описание проблем и предмета регулирования", new PropModel("lecm-ord:description-of-problems", PropModel.PropType.STRING));
                put("Основание для подготовки", new PropModel("lecm-ord:basis-for-preparation-radio", PropModel.PropType.STRING));
                put("Содержит план мероприятий", new PropModel("lecm-ord:contains-events-plan", PropModel.PropType.BOOLEAN));
                put("Состояние", new PropModel("lecm-ord:state", PropModel.PropType.STRING));
                put("Краткий перечень конкретных мер/ключевые моменты документа", new PropModel("lecm-ord:short-list-of-specific-measures", PropModel.PropType.STRING));
                put("Ожидаемые результаты/положительные эффекты от утверждения", new PropModel("lecm-ord:expected-results", PropModel.PropType.STRING));
                put("Флаг для перехода на статус \"На исполнении\"", new PropModel("lecm-ord:transit-to-on-execution", PropModel.PropType.BOOLEAN));
                put("Флаг для перехода на статус \"Работа завершена\"", new PropModel("lecm-ord:transit-to-work-completed", PropModel.PropType.BOOLEAN));
                put("Контролёр", new PropModel("lecm-ord:controller-assoc", PropModel.PropType.STRING));
            }
        });
    }};

    public static PropModel getPropNameByPresentString(DocType docType, String presentString) {

        PropModel propModelByDocType = propsAssociationsWithNames.get(docType).get(presentString);

        if (Objects.isNull(propModelByDocType)) {
            propModelByDocType = generalProps.get(presentString);
        }

        if (Objects.isNull(propModelByDocType)) {
            return new PropModel(presentString, PropModel.PropType.FAIL);
        }

        return propModelByDocType;
    }
}
