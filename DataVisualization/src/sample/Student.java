package sample;

import javafx.beans.property.*;
import javafx.scene.control.Toggle;

public class Student {
	private IntegerProperty stu_id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private DoubleProperty percentage = new SimpleDoubleProperty();
	private IntegerProperty semester = new SimpleIntegerProperty();
	private StringProperty course = new SimpleStringProperty();
	private StringProperty branch = new SimpleStringProperty();
	private IntegerProperty year = new SimpleIntegerProperty();
	private StringProperty gender = new SimpleStringProperty();
	private IntegerProperty admissionyear = new SimpleIntegerProperty();

	public Student(){

	}

	public Student(IntegerProperty stu_id, StringProperty name, DoubleProperty percentage, IntegerProperty semester, StringProperty course, StringProperty branch, IntegerProperty year, StringProperty gender, IntegerProperty admissionyear) {
		this.stu_id = stu_id;
		this.name = name;
		this.percentage = percentage;
		this.semester = semester;
		this.course = course;
		this.branch = branch;
		this.year = year;
		this.gender = gender;
		this.admissionyear = admissionyear;
	}

	public int getStu_id() {
		return stu_id.get();
	}

	public IntegerProperty stu_idProperty() {
		return stu_id;
	}

	public void setStu_id(int stu_id) {
		this.stu_id.set(stu_id);
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public double getPercentage() {
		return percentage.get();
	}

	public DoubleProperty percentageProperty() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage.set(percentage);
	}

	public int getSemester() {
		return semester.get();
	}

	public IntegerProperty semesterProperty() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester.set(semester);
	}

	public String getCourse() {
		return course.get();
	}

	public StringProperty courseProperty() {
		return course;
	}

	public void setCourse(String course) {
		this.course.set(course);
	}

	public String getBranch() {
		return branch.get();
	}

	public StringProperty branchProperty() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch.set(branch);
	}

	public int getYear() {
		return year.get();
	}

	public IntegerProperty yearProperty() {
		return year;
	}

	public void setYear(int year) {
		this.year.set(year);
	}

	public String getGender() {
		return gender.get();
	}

	public StringProperty genderProperty() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender.set(gender);
	}

	public int getAdmissionyear() {
		return admissionyear.get();
	}

	public IntegerProperty admissionyearProperty() {
		return admissionyear;
	}

	public void setAdmissionyear(int admissionyear) {
		this.admissionyear.set(admissionyear);
	}

	@Override
	public String toString() {
		return "Student{" +
				"stu_id=" + stu_id +
				", name=" + name +
				", percentage=" + percentage +
				", semester=" + semester +
				", course=" + course +
				", branch=" + branch +
				", year=" + year +
				", gender=" + gender +
				", admissionyear=" + admissionyear +
				'}';
	}
}