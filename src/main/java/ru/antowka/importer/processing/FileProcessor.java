package ru.antowka.importer.processing;

import ru.antowka.importer.model.NodeModel;

import org.springframework.batch.item.ItemProcessor;
import java.io.File;

public class FileProcessor implements ItemProcessor<File, NodeModel> {

    @Override
    public NodeModel process(File o) throws Exception {
        return null;
    }
}
