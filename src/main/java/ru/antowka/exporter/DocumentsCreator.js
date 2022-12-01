//TODO прикрутить локальный кэш
var ctx = Packages.org.springframework.web.context.ContextLoader.getCurrentWebApplicationContext();
var nodeService = ctx.getBean('nodeService', ctx.getClass().getClassLoader().loadClass('java.lang.reflect.Proxy'));

var date_regex = /^([\+-]?\d{4}(?!\d{2}\b))((-?)((0[1-9]|1[0-2])(\3([12]\d|0[1-9]|3[01]))?|W([0-4]\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\d|[12]\d{2}|3([0-5]\d|6[1-6])))([T\s]((([01]\d|2[0-3])((:?)[0-5]\d)?|24\:?00)([\.,]\d+(?!:))?)?(\17[0-5]\d([\.,]\d+)?)?([zZ]|([\+-])([01]\d|2[0-3]):?([0-5]\d)?)?)?)?$/;

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

    "lecm-outgoing:recipient-assoc": ["0", {"lecm-representative:representative-type": "lecm-representative:surname,lecm-representative:firstname,lecm-representative:middlename"}],
    "lecm-incoming:addressee-assoc": ["0", {"lecm-representative:representative-type": "lecm-representative:surname,lecm-representative:firstname,lecm-representative:middlename"}],
    "lecm-incoming:document-signer-assoc": ["0", {"lecm-representative:representative-type": "lecm-representative:surname,lecm-representative:firstname,lecm-representative:middlename"}],
    "lecm-resolutions:author-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    //"lecm-resolutions:base-assoc": ["0", {"lecm-document:base": "lecm-document:present-string"}],
    //"lecm-resolutions:base-document-assoc": ["0", {"lecm-document:base": "lecm-document:present-string"}],
    "lecm-resolutions:control-group-assoc": ["0", {"lecm-orgstr:workGroup": "lecm-orgstr:element-short-name"}],
    "lecm-errands:control-group-assoc": ["0", {"lecm-orgstr:workGroup": "lecm-orgstr:element-short-name"}],
    "lecm-errands-aspect:errands-executors-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-resolutions:assigned-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-eds-aspect:security-classification-assoc": ["Грифы секретности", "cm:title"],
    "lecm-outgoing:chief-initiator-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-errands:category-assoc": ["Категории поручений", "cm:name"],
    "lecm-errands:initiator-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-errands:controller-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    //"lecm-errands:base-document-assoc": ["0", {"lecm-document:base": "lecm-document:present-string"}],
    "lecm-errands:additional-document-assoc": ["0", {"lecm-document:base": "lecm-document:present-string"}],
    "lecm-errands:complex-executor-assoc": ["0", {
        "lecm-orgstr:employee": "lecm-orgstr:employee-short-name",
        "lecm-orgstr:organization-unit": "lecm-orgstr:element-short-name",
        "lecm-orgstr:workGroup": "lecm-orgstr:element-short-name"
    }],
    "lecm-errands:complex-coexecutors-assoc": ["0", {
        "lecm-orgstr:employee": "lecm-orgstr:employee-short-name",
        "lecm-orgstr:organization-unit": "lecm-orgstr:element-short-name",
        "lecm-orgstr:workGroup": "lecm-orgstr:element-short-name"
    }],
    "lecm-errands:complex-for-info-assoc": ["0", {
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
    "lecm-eds-document:recipients-assoc": ["0", {
        "lecm-orgstr:employee": "lecm-orgstr:employee-short-name",
        "lecm-orgstr:organization-unit": "lecm-orgstr:element-short-name"
    }],
    "lecm-internal:copies-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
    "lecm-incoming:recipient-assoc": ["0", {
        "lecm-orgstr:employee": "lecm-orgstr:employee-short-name",
        "lecm-orgstr:organization-unit": "lecm-orgstr:element-short-name",
        "lecm-orgstr:workGroup": "lecm-orgstr:element-short-name"
    }],
    "lecm-internal:chief-initiator-assoc": ["0", {"lecm-orgstr:employee": "lecm-orgstr:employee-short-name"}],
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
    "lecm-errands:character-assoc": ["0", {"lecm-errands-dic:errand-character": "cm:name"}],
    "lecm-eds-aspect:eds-document-org-unit-owner-assoc": ["0", {"lecm-orgstr:organization-unit": "lecm-orgstr:element-short-name"}],
    "lecm-errands:type-assoc": ["0", {"lecm-errands-dic:errand-type": "cm:name"}]
};

//Если true, то не создаем доки, только читаем файл и пробуем искать асоки
var dryRun = false;

//если true - то пытаемся создать вложения у существующих доков
var tryToCreateAttachments = true;

var version = "DocumentsCreator.js version 12";


try {
    logger.error("OG2. Begin " + version);
    //var document - вложение, передаваемое из js консоли
    var inputObject = JSON.parse(document.content.substring(document.content.indexOf("["), document.content.length()));
} catch (e) {
    logger.error(e);
}

run();



function run() {
    for (var i = 0; i < inputObject.length; i++) {

        var docObj = inputObject[i];
        try {
            var createdDoc = processDocument(docObj);
            if (!createdDoc || !createdDoc.nodeRef) {
                logger.error("OG2_IMPORT: FAIL. Doc was not created: " + docObj.nodeRef);
            }
        } catch (e) {
            logger.error("OG2_IMPORT: FAIL. Doc was not created: " + docObj.nodeRef);
            logger.error(e);
        }

    }
    logger.error("OG2. DONE " + version);
}

function processDocument(docObj) {
    var type = docObj.type;
    var nodeRef =docObj.nodeRef;
    var props = propsConverter(docObj.props);
    var assocs = propsConverter(docObj.assocs);
    var approvalData = docObj.approvalData;
    var attachmentsData = docObj.attachmentsData;

    var existDocument = search.findNode(nodeRef);
    if (existDocument) {
        logger.error("OG2. Document with ref " + nodeRef + " already exist");
        if (tryToCreateAttachments) {
            logger.error("OG2. Try to create attachments for document with ref " + nodeRef);
            createAttachments(existDocument, attachmentsData);
        }
        return existDocument;
    } else {
        var newDocument;
        rnUtils.doInTransaction(false, true, function () {
            rnUtils.runAs(props["cm:creator"], function () {
                try {
                    logger.error("OG2. Try to create " + nodeRef);
                    //получим sys:node-uuid
                    var sysNodeUuid = nodeRef.split("SpacesStore/");
                    props["sys:node-uuid"] = sysNodeUuid[1];

                    var root = documentScript.getDraftRoot(type);

                    if (!dryRun) {
                        if ("lecm-errands:document" != type) {
                            newDocument = root.createNode(props["cm:name"] + new Date().getTime(), type, props);
                        } else {
                            //для поручений надо сразу создавать док с асоками
                            //способ записи для асок assoc_lecm-errands_category-assoc
                            //способ записи для пропери prop_lecm-errands_is-external-control
                            var errand = {};

                            for (var propName in props) {
                                switch (propName) {
                                    case "cm:name":
                                        errand["prop_" + propName.replace(":", "_")] = props[propName] + new Date().getTime();
                                        break;
                                    case "lecm-errands:limitation-date":
                                        //в атрибуте lecm-errands:limitation-date может храниться как дата, так как и текст и массив
                                        if (date_regex.test(props[propName])) {
                                            errand["prop_" + propName.replace(":", "_")] = props[propName];
                                        }
                                        break;
                                    default:
                                        errand["prop_" + propName.replace(":", "_")] = props[propName];
                                        break;
                                }
                            }

                            errand["prop_lecm-eds-aspect_importer-code"] = "lb";

                            if (props["lecm-document:regnum"]) {
                                errand["prop_lecm-eds-aspect_regnum-after-script-create-doc"] = props["lecm-document:regnum"];
                            }

                            for (var assocName in assocs) {
                                //values - текстовые представления асок. Нужно превести их в объекты
                                var values = assocs[assocName].split(";");
                                if (values && values.length) {
                                    values.forEach(function (textValue) {
                                        //находим значение в соответствующем справочнике
                                        var value = getValue(assocName, textValue);
                                        if (value) {
                                            errand["assoc_" + assocName.replace(":", "_")] = value.nodeRef + "";
                                            //на момент 12.10.2022 неправильно мапится атрибут
                                            //нужно lecm-errands:base-assoc, а в json lecm-errands:base-document-assoc
                                            //чтобы не пределывать json пока сделаем подмену
                                            if (assocName == "lecm-errands:base-document-assoc") {
                                                errand["assoc_lecm-errands_base-assoc"] = value.nodeRef + "";
                                            }
                                        } else {
                                            logger.error("OG2. For assocName " + assocName + " and value " + textValue + " item not found. Document with ref " + nodeRef);
                                        }
                                    });
                                }
                            }

                            newDocument = errands.createErrandsSync(errand);
                        }

                        if ("lecm-errands:document" != type) {
                            //отдельно сохраняем регномер, чтобы выдать его после того как док будет зареган
                            if (props["lecm-document:regnum"]) {
                                newDocument.properties["lecm-eds-aspect:regnum-after-script-create-doc"] = props["lecm-document:regnum"];
                            }
                            newDocument.properties["lecm-eds-aspect:importer-code"] = "lb";
                            newDocument.save();
                        }
                    }

                    //асоки
                    if (Object.keys(assocs).length && "lecm-errands:document" != type) {
                        for (var assocName in assocs) {
                            //values - текстовые представления асок. Нужно превести их в объекты
                            var values = assocs[assocName].split(";");
                            if (values && values.length) {
                                values.forEach(function (textValue) {
                                    //находим значение в соответствующем справочнике
                                    var value = getValue(assocName, textValue);
                                    if (value) {
                                        if (!dryRun) {
                                            newDocument.createAssociation(value, assocName);
                                        }
                                    } else {
                                        logger.error("OG2. For assocName " + assocName + " and value " + textValue + " item not found. Document with ref " + nodeRef);
                                    }
                                });
                            }
                        }
                    }

                    //согласование
                    if (!dryRun && "lecm-errands:document" != type) {
                        createApprovalRoute(newDocument, approvalData);
                    }

                    logger.error("OG2. Document with ref " + nodeRef + " created");

                } catch (e) {
                    logger.error("OG2. Error while creating document with ref " + nodeRef + ". " + e);
                }
            });
        });

        if (newDocument && !dryRun && tryToCreateAttachments) {
            logger.error("OG2. Try to create attachments for document with ref " + nodeRef);
            createAttachments(newDocument, attachmentsData);
        }

        return newDocument;
    }
}

function createApprovalRoute(document, approvalData) {
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
                    logger.error("Not found stage " + approvalStageData);
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

function createAttachments(document, attachmentsData) {
    try {
        var folder = document.childByNamePath("Вложения");
        var categories = folder.getChildAssocsByType('lecm-document:attachmentsCategory');
        for (var i = 0; i < attachmentsData.length; i++) {
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
                }
            }
            if (initiatorRef != null) {
                var initiator = search.findNode(initiatorRef);
                if (initiator) {
                    initiatorLogin = orgstructure.getEmployeeLogin(initiator);
                }
            }
            var nameLength = name.length - 4;
            for (var j = 0; j < paths.length; j++) {

                name = name.substring(0, nameLength) + j + "." + paths[j].type;
                //Распарисить пассы;
                var storePathRelative = paths[j].path.substring((paths[j].path.indexOf("contentstore", 0) + 12));
                var url = "store:/" + storePathRelative;
                var fileSize = new Packages.java.io.File('/opt/infooborot/alf_data/contentstore' + storePathRelative).length();
                var contentData = new org.alfresco.service.cmr.repository.ContentData(url, "application/pdf", fileSize, "UTF-8", new Packages.java.util.Locale("RU_ru"));

                if (initiatorLogin && initiatorLogin) {
                    //rnUtils.runAs(initiatorLogin, function () {
                    rnUtils.runAs("admin", function () {
                        //attachment = category.createNode(name, 'cm:content', arg);
                        attachment = category.createNode(name, 'cm:content', {});
                    });
                } else {
                    attachment = category.createNode(name, 'cm:content', arg);
                }
                if (attachment == null) {
                    logger.error("OG2. Attachments not exist");
                } else {
                    logger.error("OG2. Attachments added to doc " + document.nodeRef);
                    nodeService.setProperty(attachment.nodeRef, Packages.org.alfresco.model.ContentModel.PROP_CONTENT, contentData);
                }
            }
        }
    } catch (e) {
        logger.error("OG2. Error whike attachment added for document " + document.nodeRef + ". " + e);
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
        logger.error("OG2. For assocName " + assocName + " and value " + value + " mapping item not found");
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
        query += 'TYPE:"' + typeName + '" AND';
        if (param.indexOf(",") > -1) {//для публиков param и value состовные выстраиваем их в соответствующие позициии
            var params = param.split(",");
            //разбиваем value по пробелам
            var values = value.split(" ");
            //сопостовляем
            if (params.length == values.length) {
                for (var i = 0; i < params.length; i++) {
                    query += ' (' + params[i] + ':"' + values[i] + '")';
                    if (i < params.length - 1) {
                        query += " OR ";
                    }

                }
                counter++;
                if (counter < count) {
                    query += " OR ";
                }
            } else {
                logger.error("OG2. Not found representative for " + value);
            }
        } else {
            query += ' ' + param + ':"' + value + '"';
            counter++;
            if (counter < count) {
                query += " OR ";
            }
        }
    }

    if (path) {
        query += ' AND PATH:"/app:company_home/cm:Business_x0020_platform/cm:LECM/cm:Сервис_x0020_Справочники/' + path + '//*"';
    }

    var firstTry = true;

    for (var i = 0; i < 10; i++) {
        try {
            if (!firstTry) {
                logger.error("OG2. Retry number " + i);
            }
            result = search.query({
                query: query,
                language: "fts-alfresco",
                onerror: "exception"
            });
            return result[0];
        } catch (e) {
            logger.error("OG2. Error in solar request " + query + ". " + e);
            firstTry = false;
            java.lang.Thread.sleep(1500);
        }
    }

    return "";
}

function propsConverter(props) {
    var result = {};
    props.forEach(function(prop) {
        result[prop.name.trim()] = prop.value.trim();
    });
    return result;
}