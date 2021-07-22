package br.com.pug.showcase.bean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import br.com.pug.showcase.dao.User;
import br.com.pug.showcase.util.Utils;

@ManagedBean
@ViewScoped
public class ManagerBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7670562754545611184L;
	private List<User> listUsers;
	private User user;
	private File file;
	private boolean editPass;

	@PostConstruct
	public void init() {
		updateList();
	}
	
	private void updateList() {
		try {
			String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "WEB-INF/classes/users.json";
			file = new File(path);
			Gson gson = new Gson();
			listUsers = new ArrayList<User>();
			User[] list = gson.fromJson(FileUtils.readFileToString(file), User[].class);
			for (User user : list) {
				listUsers.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void createFolder(User user) {
		try {
			String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "projects/";
			File file = new File(path + user.getUsername());
			if (!file.exists())
				file.mkdir();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveNewUser() {
		try {
			if (user != null && !user.getUsername().trim().isEmpty() && !user.getPassword().trim().isEmpty()) {
				user.setPassword(Utils.md5(user.getPassword().toLowerCase()));
				this.listUsers.add(user);
				Gson gson = new Gson();
				FileUtils.writeStringToFile(file, gson.toJson(listUsers), "UTF-8");
				createFolder(user);
				updateList();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void saveEditUser(boolean updateList) {
		try {
			if (user != null && !user.getUsername().trim().isEmpty() && !user.getPassword().trim().isEmpty()) {
				if (updateList) {
					updateList();
					boolean search = true;
					for (int i = 0; i < listUsers.size() && search; i++) {
						if (listUsers.get(i).getUsername().equalsIgnoreCase(this.user.getUsername())) {
							listUsers.get(i).setPassword(Utils.md5(this.user.getPassword().toLowerCase()));
							search = false;
						}						
					}
				} else {
					if (this.editPass)
						user.setPassword(Utils.md5(user.getPassword().toLowerCase()));					
				}
				Gson gson = new Gson();
				FileUtils.writeStringToFile(file, gson.toJson(listUsers), "UTF-8");
				createFolder(user);
				updateList();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void newUser() {
		this.user = new User();
		this.user.setUsername("");
		this.user.setPassword("");
		this.user.setLevel("dev");
	}
	
	public void delete() {
		try {
			int pos = listUsers.indexOf(user);
			if (pos > -1) {
				listUsers.remove(pos);
				Gson gson = new Gson();
				FileUtils.writeStringToFile(file, gson.toJson(listUsers), "UTF-8");
				updateList();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<User> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<User> listUsers) {
		this.listUsers = listUsers;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isEditPass() {
		return editPass;
	}

	public void setEditPass(boolean editPass) {
		this.editPass = editPass;
	}
	
}
