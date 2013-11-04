package ru.mail.projects.base;

import java.util.Map;

public interface ResourceSystem {
	public void loadResources();
	public Resource getResource(String pathToResource);
    public Map<String, Resource> getResources ();
}
