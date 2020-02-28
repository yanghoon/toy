package com.example.rbac.console;

import java.util.List;

import lombok.Builder;
import lombok.Data;

public interface UserService {
    @Data
    @Builder
    public static class User {
        private String id;
        private String name;
        private String email;
    }

	public List<User> list(String company, String project);
	// public void create(String company, String project, String username, String password) throws IOException;
    // public void remove(String company, String project, String username) throws IOException;

	public List<User> search(String company, String keyword);
	public void invite(String company, String project, String username);
	public void leave(String company, String project, String username);
}