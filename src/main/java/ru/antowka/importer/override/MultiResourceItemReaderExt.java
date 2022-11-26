package ru.antowka.importer.override;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.ResourceAware;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

import java.util.Comparator;

public class MultiResourceItemReaderExt<T> extends MultiResourceItemReader<T> {

    private boolean noInput;

    private int currentResource = -1;

    private Resource[] resources;

    private ResourceAwareItemReaderItemStream<? extends T> delegate;

    @Override
    public void setDelegate(ResourceAwareItemReaderItemStream<? extends T> delegate) {
        this.delegate = delegate;
        super.setDelegate(delegate);
    }

    @Override
    public void setSaveState(boolean saveState) {
        super.setSaveState(saveState);
    }

    @Override
    public void setComparator(Comparator<Resource> comparator) {
        super.setComparator(comparator);
    }

    @Override
    public void setResources(Resource[] resources) {
        this.resources = resources;
        super.setResources(resources);
    }

    @Nullable
    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException {

        if (noInput) {
            return null;
        }

        // If there is no resource, then this is the first item, set the current
        // resource to 0 and open the first delegate.
        if (currentResource == -1) {
            currentResource = 0;
            delegate.setResource(resources[currentResource]);
            delegate.open(new ExecutionContext());
        }

        return readNextItem();
    }


    private synchronized T readNextItem() throws Exception {

        T item = readFromDelegate();

        while (item == null) {

            currentResource++;
            if (currentResource >= resources.length) {
                return null;
            }

            delegate.close();
            delegate.setResource(resources[currentResource]);
            delegate.open(new ExecutionContext());

            item = readFromDelegate();
        }

        return item;
    }

    private T readFromDelegate() throws Exception {
        T item = delegate.read();
        if(item instanceof ResourceAware){
            ((ResourceAware) item).setResource(getCurrentResource());
        }
        return item;
    }
}
