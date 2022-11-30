#!/bin/bash
java -jar importer.jar --date.processing.date=01.10.2021 \
                                      --path.folder.output=/tmp/test_JAR/out \
                                      --path.to.contentstore=/opt/alfresco-community/alf_data/contentstore/ \
                                      --spring.datasource.notification.url=jdbc:postgresql://127.0.0.1:5432/notificationstore \
                                      --spring.datasource.notification.username=alfresco \
                                      --spring.datasource.notification.password=admin \
                                      --spring.datasource.bj.url=jdbc:postgresql://127.0.0.1:5432/bj \
                                      --spring.datasource.bj.username=alfresco \
                                      --spring.datasource.bj.password=admin \
                                      --html.file.read.limit.kb=20 \
                                      --threads.limit=10 \
                                      --time.max.diff.dj.and.file=1000 \
                                      --dictionary.notificationtype.noderef=workspace://SpacesStore/00527524-7653-43c1-b04f-4cb0b1e8dd95