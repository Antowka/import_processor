#!/bin/bash
java -jar importer.jar --date.processing.start=30.09.2021 \
                                      --date.processing.end=02.10.2021 \
                                      --path.file.output=e:\\temp\\result.json \
                                      --path.to.contentstore=e:\\Alfresco\\Project\\alf_data\\contentstore\\ \
                                      --spring.datasource.notification.url=jdbc:postgresql://172.19.203.16:5432/notificationstore \
                                      --spring.datasource.notification.username=alfresco \
                                      --spring.datasource.notification.password=admin \
                                      --spring.datasource.bj.url=jdbc:postgresql://172.19.203.16:5432/bj \
                                      --spring.datasource.bj.username=alfresco \
                                      --spring.datasource.bj.password=admin \
                                      --html.file.read.limit.kb=20 \
                                      --dictionary.notificationtype.noderef=