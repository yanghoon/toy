package com.example.rbac.console;

import java.util.List;

public interface ProjectService {
    public static class Project {

        private String name;
		private String path;
        private List<Project> subgroups;

        public Project(String name, String path, List<Project> subgroups) {
            this.name = name;
            this.path = path;
            this.subgroups = subgroups;
        }

        public String getName() { return name; }
        public String getPath() { return path; }
        public List<Project> getSubgroups() { return subgroups; }
    }

	public List<Project> list(String company);
	public void create(String project, String company);
}