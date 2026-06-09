package la.bean;

import java.io.Serializable;

public class EmpBean implements Serializable {
	private int code;
	private String name;
	private int age;
	private String tel;

	public EmpBean() {

	}

	public EmpBean(int code, String name, int age, String tel) {
		setAge(code);
		setName(name);
		setAge(age);
		setTel(tel);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
