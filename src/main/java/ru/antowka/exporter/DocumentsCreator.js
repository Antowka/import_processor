var ctx = Packages.org.springframework.web.context.ContextLoader.getCurrentWebApplicationContext();
var behaviourFilter = ctx.getBean('policyBehaviourFilter', ctx.getClass().getClassLoader().loadClass('org.alfresco.repo.policy.BehaviourFilter'));


//TODO прикрутить локальный кэш
//TODO сделать работ со адресантами (проблема в том, что в SO - ФИО, а справчонике все разбито на атрибуты): подписанты ВхД, адресанты ИсхД, исполнителт поручений

//------------Тестовые данные--------------//

var approvalData = {
    "Согласование": "workspace://SpacesStore/1305f18e-9f49-4f45-847b-a06c0939f07d,workspace://SpacesStore/26428d89-4636-4a20-8612-3ddb36db3cb8",
    "NOT_SPECIFIED": "workspace://SpacesStore/0aedfce1-24db-48a7-8c37-3965b5227f47,workspace://SpacesStore/1acbfd84-0c43-4d18-8fe9-e1b265e21553"
};
// Данные для 88
var attachmentsData =
    [{
        "paths": [
            {
                "path": "c:\\alfresco-rn\\alf_data\\contentstore\\2022\\10\\6\\12\\39\\073d0ae8-9ea8-4972-b00c-84c6bf91a145.bin",
                "type": "pdf"
            },
            {
                "path": "c:\\alfresco-rn\\alf_data\\contentstore\\2022\\10\\6\\12\\39\\5132ef63-99b8-4114-8692-fcf2da7eef54.bin",
                "type": "pdf"
            },
            {
                "path": "c:\\alfresco-rn\\alf_data\\contentstore\\2022\\10\\6\\12\\39\\92c757f7-b88f-4717-9c73-6a49f3bf690f.bin",
                "type": "pdf"
            }],
        "nameAttachment": "(A6)Печатная форма резолюции-П-00050-ИС-22-06.10.2022.pdf",
        "category": "Зарегистрированный документ",
        "initiator": "workspace://SpacesStore/75deee15-b9e7-4b62-81b7-9f2723a3b9ba"
    },
        {
            "paths": [
                {
                    "path": "c:\\alfresco-rn\\alf_data\\contentstore\\2022\\10\\6\\11\\58\\7f0943bd-9143-49f4-8eeb-2f28deb2eef7.bin",
                    "type": "pdf"
                },
                {
                    "path": "c:\\alfresco-rn\\alf_data\\contentstore\\2022\\10\\6\\11\\58\\a5e34699-8412-4c23-91fc-50df65ec6f8b.bin",
                    "type": "pdf"
                }],
            "nameAttachment": "Печатная форма комплекта.pdf",
            "category": "Документы для рассмотрения",
            "initiator": null
        }
    ]
;


//******************Тестовые данные для карточки согласования********************************
/*var type = "rn-document-approval:document";
 var nodeRef = "workspace://SpacesStore/f6414768-decc-4d64-85b4-d341c52525a6";


 var props = {
 "cm:creator": "delopr1",
 "cm:created": "2022-10-07T11:40:09+00:00",
 "lecm-document:title": "Тест",
 "lecm-document-aspects:reg-project-data-number": "У-05148-22",
 "cm:name": "Акт, №У-05148-22 от 07.10.2022 f6414768-decc-4d64-85b4-d341c52525a6"
 };

 var assocs = {
 "lecm-eds-aspect:document-category-assoc": "Открытый",
 "lecm-eds-document:executor-assoc": "Administrator A.A.",
 "rn-document-approval:curators-assoc": "Усманов Р.У.",
 "lecm-orgstr-aspects:linked-organization-assoc": "ПАО «НК «Роснефть»",
 "rn-document-approval:document-kind-assoc": "Акт"
 };*/

//******************Тестовые данные для ИсхД********************************

/*var type = "lecm-outgoing:document";
 var nodeRef = "workspace://SpacesStore/f6414768-decc-4d64-85b4-d341c52525a7";

 var props = {
 "cm:creator": "delopr1",
 "cm:created": "2022-10-07T11:40:09+00:00",
 "lecm-document:title": "Тест Исходящий",
 "lecm-document-aspects:reg-project-data-number": "У-05000-22",
 "lecm-signing-v2-aspects:signed-on-paper": "true",
 "lecm-eds-document:execution-date": "2022-11-09T11:40:09+00:00",
 "lecm-document:regnum": "123-00091-123"
 };

 var assocs = {
 "lecm-eds-aspect:document-category-assoc": "Открытый",
 "lecm-eds-document:document-type-assoc": "Запрос",
 "lecm-orgstr-aspects:linked-organization-assoc": "ПАО «НК «Роснефть»",
 "lecm-signing-v2-aspects:signerEmployeeAssoc": "Administrator A.A.;Анищенко И.П.",
 "lecm-outgoing:contractor-assoc": "Google;АО \"СНПЗ\";Герасимов О.",
 "lecm-outgoing:chief-initiator-assoc": "Анищенко И.П."
 };*/

//******************Тестовые данные для резолюции********************************
/*var type = "lecm-resolutions:document";
 var nodeRef = "workspace://SpacesStore/f6414768-decc-4d64-85b4-d341c52525a7";

 var props = {
 "cm:creator": "delopr1",
 "cm:created": "2022-10-07T11:40:09+00:00",
 "lecm-document:title": "Тест резолюции",
 "lecm-document-aspects:reg-project-data-number": "У-05000-22",
 "lecm-signing-v2-aspects:signed-on-paper": "false",
 "lecm-eds-document:execution-date": "2022-11-09T11:40:09+00:00",
 "lecm-document:regnum": "123-00091-123",
 "lecm-resolutions:title": "Тестовый заголовок резолюции"
 };

 var assocs = {
 "lecm-eds-aspect:document-category-assoc": "Открытый",
 "lecm-orgstr-aspects:linked-organization-assoc": "ПАО «НК «Роснефть»",
 "lecm-resolutions:author-assoc": "Сечин И.И.",
 "lecm-resolutions:base-assoc": "Служебная записка на Главного исполнительного директора № СЗ-AA-01401-22 от 06.10.2022",
 "lecm-resolutions:base-document-assoc": "Служебная записка на Главного исполнительного директора № СЗ-AA-01401-22 от 06.10.2022",
 "lecm-resolutions:control-group-assoc": "Группа контроля АК",
 "lecm-eds-aspect:security-classification-assoc": "Общее"
 };*/

/******************Тестовые данные для поручения************************/
/*
 var type = "lecm-errands:document";
 var nodeRef = "workspace://SpacesStore/f6414768-decc-4d64-85b4-d341c52525a7";

 var props = {
 "cm:creator": "delopr1",
 "cm:created": "2022-10-07T11:40:09+00:00",
 "lecm-document:title": "Тест Поручения",
 "lecm-document-aspects:reg-project-data-number": "У-05000-22",
 "lecm-signing-v2-aspects:signed-on-paper": "true",
 "lecm-eds-document:execution-date": "2022-11-09T11:40:09+00:00",
 "lecm-document:regnum": "123-00091-123"
 };

 var assocs = {
 "lecm-eds-aspect:document-category-assoc": "Открытый",
 "lecm-orgstr-aspects:linked-organization-assoc": "ПАО «НК «Роснефть»",
 "lecm-errands:initiator-assoc": "Administrator A.A.",
 "lecm-resolutions:author-assoc": "Сечин И.И.",
 "lecm-errands:controller-assoc": "Мигунова Л.В.",
 "lecm-errands:complex-executor-assoc": "Анищенко И.П.",
 "lecm-errands:complex-coexecutors": "Усманов Р.У.",
 "lecm-errands:for-information-assoc": "Соколова М.В."
 };*/


//*********************Служебки**********************//
/*var type = "lecm-internal:document";
 var nodeRef = "workspace://SpacesStore/f6414768-decc-4d64-85b4-d341c52525a6";

 var props = {
 "lecm-document:regnum": "123-00091-123",
 "cm:creator": "delopr1",
 "cm:created": "2022-10-07T11:40:09+00:00",
 "lecm-document:title": "Тест СЗ",
 "lecm-document-aspects:reg-project-data-number": "У-05148-22",
 "cm:name": "Акт, №У-05148-22 от 07.10.2022 f6414768-decc-4d64-85b4-d341c52525a6",
 "lecm-eds-document:summaryContent":"Содержание СЗ"
 };


 var assocs = {
 "lecm-orgstr-aspects:linked-organization-assoc": "ПАО «НК «Роснефть»",
 "lecm-eds-aspect:document-category-assoc": "Открытый",
 "lecm-eds-document:executor-assoc": "Соколова М.В.",
 "lecm-signing-v2-aspects:signerEmployeeAssoc": "Administrator A.A.;Анищенко И.П.",
 "lecm-eds-aspect:eds-document-owner-assoc": "Анищенко И.П.",
 "lecm-internal:recipients-assoc": "Анищенко И.П.;ПАО «НК «Роснефть»",
 "lecm-eds-document:document-type-assoc":"Служебная записка",
 "lecm-internal:copies-assoc":"Аверченко М.А."
 };*/


//*************************Входящие*********************************
/*var type = "lecm-incoming:document";
 var nodeRef = "workspace://SpacesStore/f6414768-decc-4d64-85b4-d341c52525a6";

 var props = {
 "lecm-document:regnum": "123-00091-123",
 "cm:creator": "delopr1",
 "cm:created": "2022-10-07T11:40:09+00:00",
 "lecm-document:title": "Тест ВхД",
 "lecm-document-aspects:reg-project-data-number": "У-05148-22",
 "cm:name": "Акт, №У-05148-22 от 07.10.2022 f6414768-decc-4d64-85b4-d341c52525a6"
 };


 var assocs = {
 "lecm-orgstr-aspects:linked-organization-assoc": "ПАО «НК «Роснефть»",
 "lecm-eds-aspect:document-category-assoc": "Открытый",
 "lecm-eds-document:document-type-assoc": "Письмо",
 "lecm-eds-aspect:eds-document-owner-assoc": "Анищенко И.П.",
 "lecm-incoming:recipient-assoc": "Анищенко И.П.;ПАО «НК «Роснефть»;Сотрудники РН-Аэро",
 "lecm-incoming:sender-assoc": "ООО «РН-Туапсинский НПЗ»",
 "lecm-incoming:delivery-method-assoc": "E-mail"
 };*/


//*************************Протокол*********************************
var type = "lecm-protocol:document";
var nodeRef = "workspace://SpacesStore/f6414768-decc-4d64-85b4-d341c52525a6";

var props = {
    "lecm-document:regnum": "123-00091-123",
    "cm:creator": "delopr1",
    "cm:created": "2022-10-07T11:40:09+00:00",
    "lecm-document:title": "Тест Протокола",
    "cm:name": "Акт, №У-05148-22 от 07.10.2022 f6414768-decc-4d64-85b4-d341c52525a6"
};

var assocs = {
    "lecm-orgstr-aspects:linked-organization-assoc": "ПАО «НК «Роснефть»",
    "lecm-eds-aspect:document-category-assoc": "Открытый",
    "lecm-eds-aspect:eds-document-owner-assoc": "Анищенко И.П.",
    "lecm-signing-v2-aspects:signerEmployeeAssoc": "Анищенко И.П.",
    "lecm-eds-document:document-type-assoc": "Протокол. Подписание ЭП",
    "lecm-protocol:category-assoc": "Внеплановое",
    "lecm-protocol:control-group-assoc": "Группа контроля АК",
    "lecm-protocol:meeting-chairman-assoc": "Сечин И.И.",
    "lecm-protocol:secretary-assoc": "Усманов Р.У.",
    "lecm-protocol:attended-assoc": "Мигунова Л.В."
};


//*************************РД*********************************
/*var type = "lecm-ord:document";
 var nodeRef = "workspace://SpacesStore/f6414768-decc-4d64-85b4-d341c52525a6";

 var props = {
 "lecm-document:regnum": "123-00091-123",
 "cm:creator": "delopr1",
 "cm:created": "2022-10-07T11:40:09+00:00",
 "lecm-document:title": "Тест РД",
 "cm:name": "Акт, №У-05148-22 от 07.10.2022 f6414768-decc-4d64-85b4-d341c52525a6",
 "lecm-eds-document:summaryContent": "преамбула РД"
 };


 var assocs = {
 "lecm-orgstr-aspects:linked-organization-assoc": "ПАО «НК «Роснефть»",
 "lecm-eds-aspect:document-category-assoc": "Открытый",
 "lecm-eds-aspect:eds-document-owner-assoc": "Анищенко И.П.",
 "lecm-signing-v2-aspects:signerEmployeeAssoc": "Анищенко И.П.",
 "lecm-eds-document:document-type-assoc": "Приказ",
 "lecm-eds-document:executor-assoc": "Анищенко И.П.",
 "lecm-ord:curators-assoc": "Усманов Р.У.",
 "lecm-ord:controller-assoc":"Мигунова Л.В."
 };*/


//Мапинг асок, получаемых их Search Object на справочник и атриубут для поиска элемента справочника
var dictionaryMapping = {
    "lecm-eds-aspect:document-category-assoc": ["Категория документа", "document-category:name"],
    "lecm-eds-document:executor-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],//0 в первом элемента массива означает, нужно идти в солар, так как справочника нет
    "lecm-eds-aspect:eds-document-owner-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "rn-document-approval:curators-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-orgstr-aspects:linked-organization-assoc": ["Контрагенты", "lecm-contractor:shortname"],
    "rn-document-approval:document-kind-assoc": ["0", {"rn-document-approval-dic:documentType": "cm:name"}, "lecm-dic:Вид_x0020_документа"], //солар с path. Нужен для обработки иерархических справочников
    "lecm-eds-document:document-type-assoc": ["0", {"lecm-doc-dic-dt:documentType": "cm:name"}, "lecm-dic:Вид_x0020_документа"],

    "lecm-signing-v2-aspects:signerEmployeeAssoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-outgoing:contractor-assoc": [{
        "Физические лица": "lecm-contractor:shortname",
        "Контрагенты": "lecm-contractor:shortname"
    }],
    "lecm-resolutions:author-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-resolutions:base-assoc": ["0", {"lecm-document:base": "lecm-document:present-string"}],
    "lecm-resolutions:base-document-assoc": ["0", {"lecm-document:base": "lecm-document:present-string"}],
    "lecm-resolutions:control-group-assoc": ["0", {"lecm-orgstr:workGroup": "lecm-orgstr:element-short-name"}],
    "lecm-eds-aspect:security-classification-assoc": ["Грифы секретности", "cm:title"],
    "lecm-outgoing:chief-initiator-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-errands:initiator-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-errands:controller-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-errands:complex-executor-assoc": ["0", {
        "lecm-orgstr:employee": "lecm-orgstr:employee-short-name",
        "lecm-orgstr:organization-unit": "lecm-orgstr:element-short-name",
        "lecm-orgstr:workGroup": "lecm-orgstr:element-short-name"
    }],
    "lecm-errands:complex-coexecutors": ["0", {
        "lecm-orgstr:employee": "lecm-orgstr:employee-short-name",
        "lecm-orgstr:organization-unit": "lecm-orgstr:element-short-name",
        "lecm-orgstr:workGroup": "lecm-orgstr:element-short-name"
    }],
    "lecm-errands:for-information-assoc": ["0", {
        "lecm-orgstr:employee": "lecm-orgstr:employee-short-name",
        "lecm-orgstr:organization-unit": "lecm-orgstr:element-short-name",
        "lecm-orgstr:workGroup": "lecm-orgstr:element-short-name"
    }],
    "lecm-internal_recipients-assoc": ["0", {
        "lecm-orgstr:employee": "lecm-orgstr:employee-short-name",
        "lecm-orgstr:organization-unit": "lecm-orgstr:element-short-name"
    }],
    "lecm-internal:recipients-assoc": ["0", {
        "lecm-orgstr:employee": "lecm-orgstr:employee-short-name",
        "lecm-orgstr:organization-unit": "lecm-orgstr:element-short-name"
    }],
    "lecm-internal:copies-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-incoming:recipient-assoc": ["0", {
        "lecm-orgstr:employee": "lecm-orgstr:employee-short-name",
        "lecm-orgstr:organization-unit": "lecm-orgstr:element-short-name",
        "lecm-orgstr:workGroup": "lecm-orgstr:element-short-name"
    }],
    "lecm-incoming:sender-assoc": [{
        "Физические лица": "lecm-contractor:shortname",
        "Контрагенты": "lecm-contractor:shortname"
    }],
    "lecm-incoming:delivery-method-assoc": ["0", {"lecm-doc-dic-dm:deliveryMethod": "cm:name"}],
    "lecm-ord:curators-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-ord:controller-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-document:subject-assoc": ["0", {"lecm-doc-dic:subjects": "cm:name"}, "lecm-dic:Тематика"],

    "lecm-protocol:category-assoc": ["0", {"lecm-protocol-category:item": "cm:name"}, "lecm-dic:Категория_x0020_протокола"],
    "lecm-protocol:meeting-chairman-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-protocol:control-group-assoc": ["0", {"lecm-orgstr:workGroup": "lecm-orgstr:element-short-name"}],
    "lecm-protocol:secretary-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-protocol:attended-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "initiator": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "category": [["Категория документа", "document-category:name"]]
};

/*var inputJSON = '';
 var inputObject = jsonUtils.toObject(inputJSON);
 Object.keys(inputObject).forEach(function (obj) {
 var props = obj.props;
 var assocs = obj.assocs;
 });*/

var q = java.lang.System.getProperty("user.dir");
logger.log(q);

var existDocument = search.findNode(nodeRef);
if (existDocument) {
    logger.log("Document with ref " + nodeRef + " already exist");
} else {
    rnUtils.runAs(props["cm:creator"], function () {
        //получим sys:node-uuid
        var sysNodeUuid = nodeRef.split("SpacesStore/");
        props["sys:node-uuid"] = sysNodeUuid[1];

        var root = documentScript.getDraftRoot(type);
        var document = root.createNode(props["cm:name"], type, props);

        //отдельно сохраняем регномер, чтобы выдать его после того как док будет зареган
        if (props["lecm-document:regnum"]) {
            document.properties["lecm-eds-aspect:regnum-after-script-create-doc"] = props["lecm-document:regnum"];
        }
        document.properties["lecm-eds-aspect:importer-code"] = "lb";
        document.save();

        //асоки
        if (Object.keys(assocs).length) {
            for (var assocName in assocs) {
                //values - текстовые представления асок. Нужно превести их в объекты
                var values = assocs[assocName].split(";");
                if (values && values.length) {
                    values.forEach(function (textValue) {
                        //находим значение в соответствующем справочнике
                        var value = getValue(assocName, textValue);
                        if (value) {
                            document.createAssociation(value, assocName);
                        }
                    });
                }
            }
        }

        //согласование
        createApprovalRoute(document);
    });
}


function createApprovalRoute(document) {
    var iteration = routesService.createEmptyIteration(document, routesService.findRouteType("Согласование"));
    if (iteration) {
        var routeOrganization = iteration.assocs["lecmWorkflowRoutes:routeOrganizationAssoc"];
        if (!routeOrganization || !routeOrganization.length) {
            var organization = orgstructure.getEmployeeOrganization(orgstructure.getCurrentEmployee());
            iteration.createAssociation(organization, "lecmWorkflowRoutes:routeOrganizationAssoc");
        }

        var props = {
            "lecmWorkflowRoutes:stageWorkflowType": "PARALLEL",
            "lecmWorkflowRoutesAspects:digitalSign": false,
            "lecmWorkflowRoutesAspects:routeStageInterruptAfterReject": true,
            "lecmWorkflowRoutes:stageWorkflowTerm": 1,
            "lecm-approval-rn-aspects:is-mandatory": false,
            "lecm-approval-rn-aspects:after-expire": "WAIT"
        };

        var itemProps = {
            'lecmWorkflowRoutes:stageWorkflowTerm': 1
        };

        approvers = [];
        for (var approvalStageData in approvalData) {
            var approversData = approvalData[approvalStageData];
            var approversRefs = approversData.split(",");

            for (var i = 0; i < approversRefs.length; i++) {
                var approver = search.findNode(approversRefs[i]);
                if (approver) {
                    approvers.push(approver);
                }
            }

            if ("NOT_SPECIFIED" != approvalStageData) {
                var stageTypes = dictionary.getRecordByParamValue('Виды этапов', 'rn-stages-types-dic:stage-name', approvalStageData);//вид этапа
                if (stageTypes) {
                    createApprovalStageItems(stageTypes[0], iteration, approvers, props, itemProps, approvalStageData);
                    document.addAspect('lecmWorkflowRoutesAspects:currentRouteInfo', {
                        'lecmWorkflowRoutesAspects:activeRoute': iteration.nodeRef
                    });
                } else {
                    logger.log("Not found stage " + approvalStageData);
                }
            }
        }
    }
}

function createApprovalStageItems(stageType, iteration, approvers, props, itemProps, stageName) {
    var type, stage, stageItem;
    //тип может быть каждый раз разный, исходя из stageTypes
    type = stageType.properties["rn-stages-types-dic:stage-doc-type"];

    stage = iteration.createNode(stageName, 'lecmWorkflowRoutes:stage', props);
    stage.createAssociation(stageType, "lecm-approval-rn-aspects:rn-stage-type");

    for (k in approvers) {
        stageItem = stage.createNode(null, 'lecmWorkflowRoutes:stageItem', itemProps);
        stageItem.createAssociation(approvers[k], 'lecmWorkflowRoutes:stageItemEmployeeAssoc');
        stageItem.addAspect("lecm-base-aspects:orderable");
    }
}

function createAttachments(documentRef) {
    document = search.findNode(documentRef);
    var folder = document.childByNamePath("Вложения");
    var categories = folder.getChildAssocsByType('lecm-document:attachmentsCategory');
    for (var i = 0; i < attachmentsData.length; i++) {
        logger.log("______________");
        var name = attachmentsData[i].nameAttachment;
        var categoryName = attachmentsData[i].category;
        var initiatorRef = attachmentsData[i].initiator;
        var paths = attachmentsData[i].paths;
        var initiatorLogin = null;
        var category = null;
        var attachment = null;
        for (var j = 0; j < categories.length; j++) {
            if (categories[j].properties["cm:name"] == categoryName) {
                category = categories[j];
                logger.log(categories[j].properties["cm:name"]);
            }
        }
        if (initiatorRef != null) {
            logger.log(initiatorRef);
            var initiator = search.findNode(initiatorRef);
            if (initiator) {
                initiatorLogin = orgstructure.getEmployeeLogin(initiator);
            }
        }
        for (var j = 0; j < paths.length; j++) {
            //Распарисить пассы;
            paths[j].substring((paths[j].indexOf("contentstore", 0) + 12))
            var a = "contentUrl=store:" + paths[j].substring((paths[j].indexOf("contentstore", 0) + 12))
                + "|mimetype=application/pdf|size=" + new java.io.File(paths[j]).length().toString() + "|encoding=UTF-8|locale=ru_RU_";
            arg = {
                "cm:content": a
            }
            if (initiatorLogin && initiatorLogin) {
                rnUtils.runAs(initiatorLogin, function () {
                    attachment = category.createNode(name, 'cm:content', arg);
                });
            } else {
                attachment = category.createNode(name, 'cm:content', arg);
            }
            if (attachment == null) {
                logger.log("Вложений нет");
            }
        }
    }
}

function getValue(assocName, value) {
    //По assocName определяем откуда будем брать инфу
    var mappingItem = dictionaryMapping[assocName];
    if (mappingItem) {
        if (mappingItem[0] == 0) {
            return getValueFromSolar(mappingItem[1], value, mappingItem[2]);
        } else {
            if (mappingItem[0].length != undefined) {
                var dictionaryValue = dictionary.getRecordByParamValue(mappingItem[0], mappingItem[1], value);
                if (dictionaryValue && dictionaryValue.length) {
                    return dictionaryValue[0];
                }
            } else {//обходим все вложенные обекты
                for (var typeName in mappingItem[0]) {
                    var dictionaryValue = dictionary.getRecordByParamValue(typeName, mappingItem[0][typeName], value);
                    if (dictionaryValue && dictionaryValue.length) {
                        return dictionaryValue[0];
                    }
                }
            }
        }
    } else {
        logger.error("OG2. For assocName " + assocName + " and value " + value + " item not found");
    }
}

/**
 *
 * @param types - обхект вида {"тип для поиска 1":"атрибут для поиска 1", "тип для поиска 2":"атрибут для поиска 2"}
 * @param value - значение для поиска
 * @param path - путь для поиска
 * @returns {*}
 */
function getValueFromSolar(types, value, path) {
    var query = "";
    var count = Object.keys(types).length;
    var counter = 0;

    for (var typeName in types) {
        var param = types[typeName];
        param = param.replace(/-/g, "\\-").replace(":", "\\:");
        query += 'TYPE:"' + typeName + '" AND @' + param + ':"' + value + '"';
        counter++;
        if (counter < count) {
            query += " OR ";
        }
    }

    if (path) {
        query += ' AND PATH:"/app:company_home/cm:Business_x0020_platform/cm:LECM/cm:Сервис_x0020_Справочники/' + path + '//*"';
    }

    result = search.query({
        query: query,
        language: "fts-alfresco",
        onerror: "exception"
    });

    if (result && result.length) {
        return result[0];
    }

    return "";
}