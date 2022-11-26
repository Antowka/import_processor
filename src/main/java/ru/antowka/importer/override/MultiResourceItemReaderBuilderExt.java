package ru.antowka.importer.override;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Comparator;

public class MultiResourceItemReaderBuilderExt<T> extends MultiResourceItemReaderBuilder<T> {

    private ResourceAwareItemReaderItemStream<? extends T> delegate;

    private Resource[] resources;

    private boolean strict = false;

    private Comparator<Resource> comparator;

    private boolean saveState = true;

    private String name;


    public MultiResourceItemReaderBuilder<T> name(String name) {
        this.name = name;
        super.name(name);
        return this;
    }


    public MultiResourceItemReaderBuilder<T> delegate(ResourceAwareItemReaderItemStream<? extends T> delegate) {
        this.delegate = delegate;
        super.delegate(delegate);
        return this;
    }

    @NotNull
    public MultiResourceItemReaderBuilder<T> resources(Resource... resources) {
        this.resources = resources;
        super.resources(resources);
        return this;
    }

    @Override
    public MultiResourceItemReader<T> build() {

        Assert.notNull(this.resources, "resources array is required.");
        Assert.notNull(this.delegate, "delegate is required.");
        if (this.saveState) {
            Assert.state(StringUtils.hasText(this.name), "A name is required when saveState is set to true.");
        }

        MultiResourceItemReader<T> reader = new MultiResourceItemReaderExt<>();
        reader.setResources(this.resources);
        reader.setDelegate(this.delegate);
        reader.setSaveState(this.saveState);
        reader.setStrict(this.strict);

        if (comparator != null) {
            reader.setComparator(this.comparator);
        }
        if (StringUtils.hasText(this.name)) {
            reader.setName(this.name);
        }

        return reader;
    }
}
