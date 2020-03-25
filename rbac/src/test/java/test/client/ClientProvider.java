package test.client;

import test.model.Tool;

public interface ClientProvider<C> {
    public C create(Tool tool);
}