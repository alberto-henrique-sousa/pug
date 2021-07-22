package br.com.pug.showcase.dao;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class Document implements Serializable, Comparable<Document> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2908401675452475183L;
	private String name;
	private boolean folder;
	private boolean root;
	private String link;
	private String file;
	private String folderName;
	private String publish;
	private String status;
	private String publishText;
	private String statusText;
	private String publishColor;
	private String statusColor;
	private boolean level1;
	private boolean btnCreateFolder;
	private boolean btnCreateLayout;
	private boolean btnRename;
	private boolean btnDelete;
	private boolean btnViewFolder;
	private boolean btnViewLayout;
	private boolean admin;
	
	public Document(String name, boolean folder, boolean root, String link, String file, String folderName, String publish, String status, boolean level1, boolean admin) {
		super();
		this.name = name;
		this.folder = folder;
		this.root = root;
		this.link = link;
		this.file = file;
		this.folderName = folderName;
		this.publish = publish;
		this.status = status;
		this.level1 = level1;
		this.admin = admin;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}	
	
	public boolean isFolder() {
		return folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}
	
	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPublishText() {
		if (this.publish != null && !this.publish.isEmpty() && StringUtils.isNumeric(this.publish)) { 
			switch (Integer.valueOf(this.publish)) {
			case 0:
				this.publishText = "Concepção/Oculto";
				break;
			case 1:
				this.publishText = "Desenvolvimento";
				break;
			case 2:
				this.publishText = "Homologação";
				break;
			}			
		} else
			this.publishText = "";
		return this.publishText;
	}

	public String getStatusText() {
		if (this.status != null && !this.status.isEmpty() && StringUtils.isNumeric(this.status)) {			
			switch (Integer.valueOf(this.status)) {
			case 0:
				this.statusText = "Aguardando";
				break;
			case 1:
				this.statusText = "Aprovado";
				break;
			case 2:
				this.statusText = "Revisão";
				break;
			case 3:
				this.statusText = "Reprovado";
				break;
			}
		}
		return this.statusText;
	}
	
	public String getPublishColor() {
		this.publishColor = this.publish.equals("0") ? "black" : "green";
		return this.publishColor;
	}

	public String getStatusColor() {
		if (this.status != null && !this.status.isEmpty() && StringUtils.isNumeric(this.status)) {			
			switch (Integer.valueOf(this.status)) {
			case 0:
				this.statusColor = "black";
				break;
			case 1:
				this.statusColor = "green";
				break;
			case 2:
				this.statusColor = "blue";
				break;
			case 3:
				this.statusColor = "red";
				break;
			}
		}
		return this.statusColor;
	}

	public boolean isLevel1() {
		return level1;
	}

	public void setLevel1(boolean level1) {
		this.level1 = level1;
	}

    public boolean isBtnCreateFolder() {   
    	this.btnCreateFolder = this.folder && (!this.root || this.admin); 
		return this.btnCreateFolder;
	}

	public boolean isBtnCreateLayout() {
		this.btnCreateLayout = this.folder && !this.root; 
		return this.btnCreateLayout;
	}

	public boolean isBtnRename() {
		this.btnRename = !this.root && (!level1 || this.admin); 
		return this.btnRename;
	}

	public boolean isBtnDelete() {
		this.btnDelete = !this.root && (!level1 || this.admin); 
		return this.btnDelete;
	}

	public boolean isBtnViewFolder() {
		this.btnViewFolder = this.folder; 
		return btnViewFolder;
	}

	public boolean isBtnViewLayout() {
		this.btnViewLayout = !this.folder; 
		return this.btnViewLayout;
	}

	public boolean isAdmin() {
		return admin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + (folder ? 1231 : 1237);
		result = prime * result + ((folderName == null) ? 0 : folderName.hashCode());
		result = prime * result + (level1 ? 1231 : 1237);
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((publish == null) ? 0 : publish.hashCode());
		result = prime * result + ((publishColor == null) ? 0 : publishColor.hashCode());
		result = prime * result + ((publishText == null) ? 0 : publishText.hashCode());
		result = prime * result + (root ? 1231 : 1237);
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((statusColor == null) ? 0 : statusColor.hashCode());
		result = prime * result + ((statusText == null) ? 0 : statusText.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (folder != other.folder)
			return false;
		if (folderName == null) {
			if (other.folderName != null)
				return false;
		} else if (!folderName.equals(other.folderName))
			return false;
		if (level1 != other.level1)
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (publish == null) {
			if (other.publish != null)
				return false;
		} else if (!publish.equals(other.publish))
			return false;
		if (publishColor == null) {
			if (other.publishColor != null)
				return false;
		} else if (!publishColor.equals(other.publishColor))
			return false;
		if (publishText == null) {
			if (other.publishText != null)
				return false;
		} else if (!publishText.equals(other.publishText))
			return false;
		if (root != other.root)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (statusColor == null) {
			if (other.statusColor != null)
				return false;
		} else if (!statusColor.equals(other.statusColor))
			return false;
		if (statusText == null) {
			if (other.statusText != null)
				return false;
		} else if (!statusText.equals(other.statusText))
			return false;
		return true;
	}

	public int compareTo(Document document) {
        return this.getName().compareTo(document.getName());
    }}
