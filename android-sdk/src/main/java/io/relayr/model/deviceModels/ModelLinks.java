package io.relayr.model.deviceModels;

public class ModelLinks {

    private ModelLink self;
    private ModelLink next;
    private ModelLink last;
    private ModelLink first;

    public String getSelf() {
        return self.href;
    }

    public String getLast() {
        return last.href;
    }

    public String getFirst() {
        return first.href;
    }

    public ModelLink getNext() {
        return next;
    }

    class ModelLink {

        final String href;

        public ModelLink(String href) {
            this.href = href;
        }
    }
}
