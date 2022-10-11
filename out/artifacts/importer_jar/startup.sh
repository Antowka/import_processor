#!/bin/bash
java -jar importer.jar --date.processing.date=04.10.2022 \
                                      --path.folder.output=e:\\temp\\ \
                                      --path.to.contentstore=e:\\Alfresco\\Project\\alf_data\\contentstore\\ \
                                      --spring.datasource.notification.url=jdbc:postgresql://172.19.203.16:5432/notificationstore \
                                      --spring.datasource.notification.username=alfresco \
                                      --spring.datasource.notification.password=admin \
                                      --spring.datasource.bj.url=jdbc:postgresql://172.19.203.16:5432/bj \
                                      --spring.datasource.bj.username=alfresco \
                                      --spring.datasource.bj.password=admin \
                                      --html.file.read.limit.kb=20 \
                                      --dictionary.notificationtype.noderef=