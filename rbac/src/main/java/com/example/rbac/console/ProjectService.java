package com.example.rbac.console;

import java.io.IOException;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

public interface ProjectService {
    @Data
    @AllArgsConstructor
    public static class Project {
        private String id;
        private String name;
		private String path;
        private List<Project> subgroups;
    }

	public List<Project> list(String company);
	public void create(String company, String project) throws IOException;
	public void remove(String company, String project) throws IOException;
}