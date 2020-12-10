
package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.WritableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.xml.validation.Validator;
import java.sql.*;

public class Controller {
	private int nos=42;
	private double m[]=new double[nos];
	private String n[]=new String[nos*nos];
	private ObservableList<PieChart.Data> data;
	private String l, SQL, selectedChoice1 = null, selectedChoice2 = null;
	private ObservableList<Student> slist ;
	@FXML
	Button pieChartButton;

	@FXML
	ChoiceBox<String> choiceBox1;

	@FXML
	Label paneLabel;

	@FXML
	ChoiceBox<String> choiceBox2;

	@FXML
	Pane pane;

	@FXML
	AnchorPane anchorPane2;

	@FXML
	Button lineGraph;

	@FXML
	Button barGraph;

	@FXML
	Button areaGraph;

	@FXML
	Button insertData;

	@FXML
	Button deleteData;

	@FXML
	Button updateData;

	@FXML
	Button showData;

	public void createChoiceBoxList() {

		ObservableList<String> cb1 = FXCollections.observableArrayList("Percentage", "Semester", "Year", "Admission_year", "Number of students");
		choiceBox1.setItems(cb1);
		Tooltip t1 = new Tooltip("Select from the list");
		t1.setStyle("-fx-font-size: 14");
		choiceBox1.setTooltip(t1);
		ObservableList<String> cb2 = FXCollections.observableArrayList("Name", "Branch", "Course", "Gender", "Number of students");
		choiceBox2.setItems(cb2);
		choiceBox2.setTooltip(t1);
	}

	public void choiceBoxChoices() {
		boolean isChoiceBox1Empty = choiceBox1.getSelectionModel().isEmpty();
		boolean isChoiceBox2Empty = choiceBox2.getSelectionModel().isEmpty();
		if (isChoiceBox1Empty && !isChoiceBox2Empty) {
			selectedChoice1 = "Number of students";
			selectedChoice2 = choiceBox2.getSelectionModel().getSelectedItem();
		} else if (isChoiceBox2Empty && !isChoiceBox1Empty) {
			selectedChoice1 = choiceBox1.getSelectionModel().getSelectedItem();
			selectedChoice2 = "Number of students";
		} else if (!isChoiceBox2Empty) {
			selectedChoice1 = choiceBox1.getSelectionModel().getSelectedItem();
			selectedChoice2 = choiceBox2.getSelectionModel().getSelectedItem();
		} else {

			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setHeaderText("Pie/Line/Area/Bar Chart or Graph cannot be formed.");
			alert.setContentText("Please specify the field for the formation of piechart.");
			alert.show();
		}
	}

	public void createPieChart() {
		pieChartButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				pane.getChildren().clear();
				choiceBoxChoices();
				PieChart pieChart = new PieChart();
				Connection c;
				data = FXCollections.observableArrayList();
				try {
					c = DBConnect.connect();

					if (!selectedChoice2.equals("Number of students") && !selectedChoice1.equals("Number of students")) {
						SQL = "SELECT " + selectedChoice1 + ",count(" + selectedChoice2 + ") from student GROUP BY " + selectedChoice1;
						pieChart.setTitle("College's Data visualizing Software\n\t\t(Total Students:"+nos+")");
						l = "a(b):\na-->bth " + selectedChoice1 + " of 'a' " + selectedChoice2 + "(s) is going on    b-->" + selectedChoice1;
					}
					else if (selectedChoice2.equals("Number of students") && !selectedChoice1.equals("Number of students")) {
						SQL = "SELECT count(" + selectedChoice1 + ")," + selectedChoice1 + " from student GROUP BY " + selectedChoice1;
						pieChart.setTitle("College's Data visualizing Software\n\t\t(Total Students:"+nos+")");
						l = "a(b):\na-->" + selectedChoice1 + "  b-->number of students in that " + selectedChoice1;
					}
					else if (!selectedChoice2.equals("Number of students")) {
						SQL = "SELECT count(" + selectedChoice2 + ")," + selectedChoice2 + " from student GROUP BY " + selectedChoice2;
						pieChart.setTitle("College's Data visualizing Software\n\t\t(Total Students:"+nos+")");
						l = "a(b):\na-->" + selectedChoice2 + "  b-->number of students in/of that " + selectedChoice2;
					}
					else {
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setHeaderText("Pie/Line/Bar/Area Chart or Graph cannot be formed.");
						alert.setContentText("Please specify the field for the formation of piechart.");
						alert.show();

					}
//
					ResultSet rs = c.createStatement().executeQuery(SQL);
					while (rs.next()) {
						//adding data on piechart data
						data.add(new PieChart.Data(rs.getString(2), rs.getDouble(1)));
					}
					c.close();
				} catch (Exception exception) {
					System.out.println("Error on DB connection");
					return;
				}

				pieChart.getData().addAll(data);

				pieChart.setCenterShape(true);
				pieChart.setLegendSide(Side.BOTTOM);
				pieChart.setLegendVisible(false);

				data.forEach(data ->
						data.nameProperty().bind(
								Bindings.concat(
										data.getName(), " (", data.pieValueProperty(), ")"
								)
						)
				);
				paneLabel.setText(l);
				pane.getChildren().addAll(pieChart, paneLabel);
			}
		});
	}

	public void createLineGraph() {
		lineGraph.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				pane.getChildren().clear();
				choiceBoxChoices();
				final NumberAxis yaxis = new NumberAxis();
				final CategoryAxis xaxis = new CategoryAxis();

				//Creating the instance of linechart with the specified axis
				LineChart linechart = new LineChart(xaxis, yaxis);

				//creating the series
				XYChart.Series series = new XYChart.Series();
				Connection c;
				try {
					c = DBConnect.connect();

					if (!selectedChoice2.equals("Number of students") && !selectedChoice1.equals("Number of students")) {
						SQL = "SELECT " + selectedChoice1 + "," + selectedChoice2 + " from student ";
						linechart.setTitle("College's Data visualizing Software\n\t\t(Total Students:"+nos+")");
						l = "x(y):\nx--> yth " + selectedChoice1 + " of 'x' " + selectedChoice2 + "(s) is going on/present    y-->" + selectedChoice1;

						//Defining Label for Axis
						xaxis.setLabel(selectedChoice2);
						yaxis.setLabel(selectedChoice1);

					} else if (selectedChoice2.equals("Number of students") && !selectedChoice1.equals("Number of students")) {
						SQL = "SELECT count(" + selectedChoice1 + ")," + selectedChoice1 + " from student GROUP BY " + selectedChoice1 + " ORDER BY " + selectedChoice1 + " ASC";
						linechart.setTitle("College's Data visualizing Software\n\t\t(Total Students:"+nos+")");
						l = " ";
						//Defining Label for Axis
						xaxis.setLabel(selectedChoice1);
						yaxis.setLabel("no of students in " + selectedChoice1);

					} else if (!selectedChoice2.equals("Number of students") && selectedChoice1.equals("Number of students")) {
						SQL = "SELECT count(" + selectedChoice2 + ")," + selectedChoice2 + " from student GROUP BY " + selectedChoice2+" ORDER BY " + selectedChoice2 + " ASC";
						linechart.setTitle("College's Data visualizing Software\n\t\t(Total Students:"+nos+")");
						l = " ";
						xaxis.setLabel(selectedChoice2);
						yaxis.setLabel("no of students in " + selectedChoice2);
					} else {

						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setHeaderText("Line Graph cannot be formed.");
						alert.setContentText("Please specify the field for the formation of Line Graph.");
						alert.show();
					}

					ResultSet rs=c.createStatement().executeQuery(SQL);
					while (rs.next()) {
						//adding data on linechart data
						XYChart.Data<String, Number> d = new XYChart.Data<>(rs.getString(2), rs.getDouble(1));
						series.getData().add(d);
					}
					c.close();
				} catch (Exception exception) {
					System.out.println("Error on DB connection");
					return;
				}


				linechart.getData().addAll(series);
				paneLabel.setText(l);
				pane.getChildren().addAll(linechart, paneLabel);

			}
		});

	}

	public void createBarGraph() {
		barGraph.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				pane.getChildren().clear();
				choiceBoxChoices();
				final NumberAxis yaxis = new NumberAxis();
				final CategoryAxis xaxis = new CategoryAxis();

				//Creating the instance of linechart with the specified axis
				BarChart barChart = new BarChart(xaxis, yaxis);

				//creating the series
				XYChart.Series series = new XYChart.Series();
				Connection c;
				try {
					c = DBConnect.connect();

					if (!selectedChoice2.equals("Number of students") && !selectedChoice1.equals("Number of students")) {
						SQL = "SELECT " + selectedChoice1 + ",count(" + selectedChoice2 + ") from student GROUP BY " + selectedChoice2+" order by count(" + selectedChoice2 + ") ASC";
						barChart.setTitle("College's Data visualizing Software\n\t\t(Total Students:"+nos+")");
						l = "x(y):\nx--> yth " + selectedChoice1 + " of 'x' " + selectedChoice2 + "(s) is going on/present    y-->" + selectedChoice1;
						//Defining Label for Axis
						xaxis.setLabel(selectedChoice2);
						yaxis.setLabel(selectedChoice1);

					} else if (selectedChoice2.equals("Number of students") && !selectedChoice1.equals("Number of students")) {
						SQL = "SELECT count(" + selectedChoice1 + ")," + selectedChoice1 + " from student GROUP BY " + selectedChoice1 + " order by " + selectedChoice1 + " ASC";
						barChart.setTitle("College's Data visualizing Software\n\t\t(Total Students:"+nos+")");
						l = " ";
						//Defining Label for Axis
						xaxis.setLabel(selectedChoice1);
						yaxis.setLabel("no of students in " + selectedChoice1);

					} else if (!selectedChoice2.equals("Number of students")) {
						SQL = "SELECT count(" + selectedChoice2 + ")," + selectedChoice2 + " from student GROUP BY " + selectedChoice2;
						barChart.setTitle("College's Data visualizing Software\n\t\t(Total Students:"+nos+")");
						l = " ";
						xaxis.setLabel(selectedChoice2);
						yaxis.setLabel("no of students in " + selectedChoice2);
					} else {

						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setHeaderText("Bar Graph cannot be formed.");
						alert.setContentText("Please specify the field for the formation of BarGraph.");
						alert.show();
					}

					ResultSet rs = c.createStatement().executeQuery(SQL);
					while (rs.next()) {
						XYChart.Data<String, Number> d = new XYChart.Data<>(rs.getString(2), rs.getDouble(1));
						series.getData().add(d);
					}
					c.close();
				} catch (Exception exception) {
					System.out.println("Error on DB connection");
					return;
				}
				barChart.getData().addAll(series);
				paneLabel.setText(l);
				pane.getChildren().addAll(barChart, paneLabel);

			}
		});
	}
	public void createAreaGraph() {
		areaGraph.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				pane.getChildren().clear();
				choiceBoxChoices();
				final NumberAxis yaxis = new NumberAxis();
				final CategoryAxis xaxis = new CategoryAxis();

				//Creating the instance of linechart with the specified axis
				AreaChart areaChart = new AreaChart(xaxis, yaxis);

				//creating the series
				XYChart.Series series = new XYChart.Series();
				Connection c;
				try {
					c = DBConnect.connect();

					if (!selectedChoice2.equals("Number of students") && !selectedChoice1.equals("Number of students")) {
						SQL = "SELECT " + selectedChoice1 + "," + selectedChoice2 + " from student ";
						areaChart.setTitle("College's Data visualizing Software\n\t\t(Total Students:"+nos+")");
						l = "x(y):\nx--> yth " + selectedChoice1 + " of 'x' " + selectedChoice2 + "(s) is going on/present    y-->" + selectedChoice1;
						//Defining Label for Axis
						xaxis.setLabel(selectedChoice2);
						yaxis.setLabel(selectedChoice1);

					} else if (selectedChoice2.equals("Number of students") && !selectedChoice1.equals("Number of students")) {
						SQL = "SELECT count(" + selectedChoice1 + ")," + selectedChoice1 + " from student GROUP BY " + selectedChoice1 + " order by " + selectedChoice1 + " ASC";
						areaChart.setTitle("College's Data visualizing Software\n\t\t(Total Students:"+nos+")");
						l = " ";
						//Defining Label for Axis
						xaxis.setLabel(selectedChoice1);
						yaxis.setLabel("no of students in " + selectedChoice1);

					} else if (!selectedChoice2.equals("Number of students")) {
						SQL = "SELECT count(" + selectedChoice2 + ")," + selectedChoice2 + " from student GROUP BY " + selectedChoice2;
						areaChart.setTitle("College's Data visualizing Software\n\t\t(Total Students:"+nos+")");
						l = " ";
						xaxis.setLabel(selectedChoice2);
						yaxis.setLabel("no of students in " + selectedChoice2);
					} else {

						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setHeaderText("Area Graph cannot be formed.");
						alert.setContentText("Please specify the field for the formation of AreaGraph.");
						alert.show();
					}

					ResultSet rs = c.createStatement().executeQuery(SQL);
					while (rs.next()) {
						//adding data on linechart data
						XYChart.Data<String, Number> d = new XYChart.Data<>(rs.getString(2), rs.getDouble(1));
						series.getData().add(d);
					}
					c.close();
				} catch (Exception exception) {
					System.out.println("Error on DB connection");
					return;
				}
				areaChart.getData().addAll(series);
				paneLabel.setText(l);
				pane.getChildren().addAll(areaChart, paneLabel);

			}
		});
	}
	public void insertData() {
		insertData.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				pane.getChildren().clear();

				Label insertData =new Label();
				insertData.setText("Insert Data");
				insertData.setFont(new Font("Arial", 28));
				insertData.setAlignment(Pos.CENTER);
				insertData.setPrefSize(250,30);
				insertData.relocate(180,10);

				TextField name = new TextField();
				name.setPromptText("Name");
				name.setPrefSize(250,20);
				name.relocate(180,50);
				Tooltip tooltip = new Tooltip("Should contain only the alphabets and blank spaces(where necessary)");
				tooltip.setStyle("-fx-font-size: 11");
				name.setTooltip(tooltip);

				TextField percentage = new TextField();
				percentage.setPromptText("Percentage");
				percentage.setPrefSize(250,20);
				percentage.relocate(180,90);
				Tooltip tooltip1 = new Tooltip("Do not include % ");
				tooltip1.setStyle("-fx-font-size: 11");
				percentage.setTooltip(tooltip1);

				ComboBox<Integer> semester = new ComboBox<>();
				semester.getItems().addAll(1,2,3,4,5,6,7,8);
				semester.setPromptText("Semester");
				semester.setPrefSize(250,20);
				semester.relocate(180,130);

				ComboBox<String> course = new ComboBox<>();
				course.getItems().addAll("BTECH","MBA","MCom","BE","BCA","BA","MA","BArch","MArch","MTech","BPharm","DPhram","Diploma","MCA");
				course.setPromptText("Course");
				course.setPrefSize(250,20);
				course.relocate(180,170);

				TextField branch = new TextField();
				branch.setPromptText("Branch");
				branch.setPrefSize(250,20);
				branch.relocate(180,210);
				Tooltip tooltip2 = new Tooltip("Should not contain numbers");
				tooltip2.setStyle("-fx-font-size: 11");
				branch.setTooltip(tooltip2);

				ComboBox<Integer> year = new ComboBox<>();
				year.getItems().addAll(1,2,3,4);
				year.setPromptText("Year");
				year.setPrefSize(250,20);
				year.relocate(180,250);


				RadioButton female = new RadioButton();
				female.setText("Female");
				female.relocate(180,290);

				RadioButton male = new RadioButton();
				male.setText("Male");
				male.relocate(270,290);

				RadioButton others = new RadioButton();
				others.setText("Others");
				others.relocate(360,290);
				ToggleGroup group = new ToggleGroup();

				female.setToggleGroup(group);
				male.setToggleGroup(group);
				others.setToggleGroup(group);


				TextField admissionYear = new TextField();
				admissionYear.setPromptText("Admission year");
				admissionYear.setPrefSize(250,20);
				admissionYear.relocate(180,330);
				Tooltip tooltip4 = new Tooltip("Should contain year and No alphabets");
				tooltip4.setStyle("-fx-font-size: 11");
				admissionYear.setTooltip(tooltip4);

				CheckBox agreeAndContinue = new CheckBox();
				agreeAndContinue.setText("Agree and Continue");
				agreeAndContinue.setAlignment(Pos.CENTER);
				agreeAndContinue.relocate(250,370);

				Button submitData = new Button();
				submitData.setText("Submit Data");
				submitData.relocate(250,410);

				Label displayLabel =new Label();
				displayLabel.setText(" ");
				displayLabel.setFont(new Font("Arial", 15));
				displayLabel.setAlignment(Pos.CENTER);
				displayLabel.setPrefSize(250,30);
				displayLabel.relocate(180,430);



				submitData.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
					Connection conn;
					if(agreeAndContinue.isSelected()) {
						if(!name.getText().isEmpty() && !percentage.getText().isEmpty()&&
						semester.getValue()!= 0 &&
						course.getValue()!=null&& !branch.getText().isEmpty()&&
						year.getValue()!=0&& group.getSelectedToggle().isSelected()&&
								!admissionYear.getText().isEmpty()) {
							RadioButton rb = (RadioButton)group.getSelectedToggle();
							String n=name.getText();
							double p = Double.parseDouble(percentage.getText());
							int b = semester.getValue();
							String c = course.getValue();
							String br=branch.getText();
							int y=year.getValue();
							String g=rb.getText();
							int ay=Integer.parseInt(admissionYear.getText());
							System.out.print(n+p+b+c+br+y+g+ay);
							Studentdao sd =new Studentdao();
							sd.insertRecord(n,p,b,c,br,y,g,ay);
							displayLabel.setText("Record inserted successfully");
							displayLabel.setTextFill(Color.GREEN);
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setHeaderText("Record successfully inserted!!!");
							alert.setContentText("To view your data, go to SHOW DATA");
							alert.show();
							Tooltip t1 =new Tooltip("Semester");
							Tooltip t2 =new Tooltip("Course");
							Tooltip t3 =new Tooltip("Year");
							t1.setStyle("-fx-font-size: 15");
							t2.setStyle("-fx-font-size: 15");
							t3.setStyle("-fx-font-size: 15");
							name.setText("");
							percentage.setText("");
							semester.getSelectionModel().clearSelection();
							semester.setTooltip(t1);
							course.getSelectionModel().clearSelection();
							course.setTooltip(t2);
							branch.setText("");
							year.getSelectionModel().clearSelection();
							year.setTooltip(t3);
							group.getSelectedToggle().setSelected(false);
							agreeAndContinue.setSelected(false);
							admissionYear.setText("");

						}
					else {
						displayLabel.setText("Please provide the necessary details");
							displayLabel.setTextFill(Color.RED);
					}}
					else {
						displayLabel.setText("Please select the the checkbox");
						displayLabel.setTextFill(Color.RED);
					}
					}
				});
				pane.getChildren().addAll(insertData,name,percentage,semester,course,branch,year,female,male,others,admissionYear,agreeAndContinue,submitData,displayLabel);
			}
		});
	}
	public void deleteData() {
		deleteData.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent actionEvent) {
				pane.getChildren().clear();
				Label deleteData =new Label();
				deleteData.setText("Delete Data");
				deleteData.setFont(new Font("Arial", 28));
				deleteData.setAlignment(Pos.CENTER);
				deleteData.setPrefSize(250,30);
				deleteData.relocate(180,10);

				TextField id = new TextField();
				id.setPromptText("Student Id");
				id.setPrefSize(250,20);
				id.relocate(180,50);
				Tooltip tooltip = new Tooltip("Should be a number");
				tooltip.setStyle("-fx-font-size: 11");
				id.setTooltip(tooltip);

				Button submitData = new Button();
				submitData.setText("Submit Data");
				submitData.relocate(270,100);

				Label displayLabel =new Label();
				displayLabel.setText(" ");
				displayLabel.setFont(new Font("Arial", 15));
				displayLabel.setAlignment(Pos.CENTER);
				displayLabel.setPrefSize(250,30);
				displayLabel.relocate(180,150);

				submitData.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						if(!id.getText().isEmpty()){

						int i=Integer.parseInt(id.getText().trim());
						Studentdao sd =new Studentdao();
						 if(sd.deleteData(i)) {
							displayLabel.setText("Record deleted successfully");
							displayLabel.setTextFill(Color.GREEN);
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setHeaderText("Data successfully deleted!!!");
							alert.setContentText("To view your data, go to SHOW DATA");
							alert.show();
							id.setText("");
						}
						 else {
							 displayLabel.setText("Invalid Student ID. Try again");
							 displayLabel.setTextFill(Color.RED);
							 id.setText("");
						 }}

						else {
							displayLabel.setText("Please provide the Student ID");
							displayLabel.setTextFill(Color.RED);
						}
					}
				});
				pane.getChildren().addAll(deleteData,id,submitData,displayLabel);
			}

		});
	}

	public void updateData() {
		updateData.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				pane.getChildren().clear();
				Label updateData =new Label();
				updateData.setText("Update Data");
				updateData.setFont(new Font("Arial", 28));
				updateData.setAlignment(Pos.CENTER);
				updateData.setPrefSize(250,30);
				updateData.relocate(180,10);

				TextField id = new TextField();
				id.setPromptText("Student Id");
				id.setPrefSize(250,20);
				id.relocate(180,50);
				Tooltip tooltip = new Tooltip("Should be a number");
				tooltip.setStyle("-fx-font-size: 11");
				id.setTooltip(tooltip);

				Button submitData = new Button();
				submitData.setText("Submit Id");
				submitData.relocate(270,100);

				Label displayLabel =new Label();
				displayLabel.setText(" ");
				displayLabel.setFont(new Font("Arial", 15));
				displayLabel.setAlignment(Pos.CENTER);
				displayLabel.setPrefSize(250,30);
				displayLabel.relocate(180,150);

				Studentdao s=new Studentdao();

				submitData.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						if(!id.getText().isEmpty()) {
							int i = Integer.parseInt(id.getText().trim());

							pane.getChildren().clear();
							Label insertData = new Label();
							insertData.setText("Update Data");
							insertData.setFont(new Font("Arial", 28));
							insertData.setAlignment(Pos.CENTER);
							insertData.setPrefSize(250, 30);
							insertData.relocate(180, 10);

								TextField name = new TextField();
								name.setPromptText("Name");
								name.setPrefSize(250, 20);
								name.relocate(180, 50);
								Tooltip tooltip = new Tooltip("Should contain only the alphabets and blank spaces(where necessary)");
								tooltip.setStyle("-fx-font-size: 11");
								name.setTooltip(tooltip);

								TextField percentage = new TextField();
								percentage.setPromptText("Percentage");
								percentage.setPrefSize(250, 20);
								percentage.relocate(180, 90);
								Tooltip tooltip1 = new Tooltip("Do not include % ");
								tooltip1.setStyle("-fx-font-size: 11");
								percentage.setTooltip(tooltip1);

								ComboBox<Integer> semester = new ComboBox<>();
								semester.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8);
								semester.setPromptText("Semester");
								semester.setPrefSize(250, 20);
								semester.relocate(180, 130);

								ComboBox<String> course = new ComboBox<>();
								course.getItems().addAll("BTECH", "MBA", "MCom", "BE", "BCA", "BA", "MA", "BArch", "MArch", "MTech", "BPharm", "DPhram", "Diploma", "MCA");
								course.setPromptText("Course");
								course.setPrefSize(250, 20);
								course.relocate(180, 170);

								TextField branch = new TextField();
								branch.setPromptText("Branch");
								branch.setPrefSize(250, 20);
								branch.relocate(180, 210);
								Tooltip tooltip2 = new Tooltip("Should not contain numbers");
								tooltip2.setStyle("-fx-font-size: 11");
								branch.setTooltip(tooltip2);

								ComboBox<Integer> year = new ComboBox<>();
								year.getItems().addAll(1, 2, 3, 4);
								year.setPromptText("Year");
								year.setPrefSize(250, 20);
								year.relocate(180, 250);


								RadioButton female = new RadioButton();
								female.setText("Female");
								female.relocate(180, 290);

								RadioButton male = new RadioButton();
								male.setText("Male");
								male.relocate(270, 290);

								RadioButton others = new RadioButton();
								others.setText("Others");
								others.relocate(360, 290);
								ToggleGroup group = new ToggleGroup();

								female.setToggleGroup(group);
								male.setToggleGroup(group);
								others.setToggleGroup(group);


								TextField admissionYear = new TextField();
								admissionYear.setPromptText("Admission year");
								admissionYear.setPrefSize(250, 20);
								admissionYear.relocate(180, 330);
								Tooltip tooltip4 = new Tooltip("Should contain year and No alphabets");
								tooltip4.setStyle("-fx-font-size: 11");
								admissionYear.setTooltip(tooltip4);

								CheckBox agreeAndContinue = new CheckBox();
								agreeAndContinue.setText("Agree and Continue");
								agreeAndContinue.setAlignment(Pos.CENTER);
								agreeAndContinue.relocate(250, 370);

								Button submitData1 = new Button();
								submitData1.setText("Update Data");
								submitData1.relocate(250, 410);

								Label displayLabel = new Label();
								displayLabel.setText(" ");
								displayLabel.setFont(new Font("Arial", 15));
								displayLabel.setAlignment(Pos.CENTER);
								displayLabel.setPrefSize(250, 30);
								displayLabel.relocate(190, 430);
								String SQL = "SELECT * from student WHERE stu_id=" + i;

								try {
									Connection conn = DBConnect.connect();
									Statement statement = conn.createStatement();
									ResultSet rs=statement.executeQuery(SQL);

									while (rs.next()) {
										name.setText(rs.getString("name"));
										percentage.setText(rs.getString("percentage"));
										semester.setValue(rs.getInt("semester"));
										course.setValue(rs.getString("course"));
										branch.setText(rs.getString("branch"));
										year.setValue(rs.getInt("year"));
										String gender = rs.getString("gender");
										if (gender.equals("Male")) {
											male.setSelected(true);
										} else if (gender.equals("Female")) {
											female.setSelected(true);
										} else {
											others.setSelected(true);
										}
										admissionYear.setText(rs.getString("admission_year"));
									} 

									conn.close();

								} catch (Exception e) {
									System.out.print("Error in DB connectivity");
								}


								submitData1.setOnAction(new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent actionEvent) {
										if (agreeAndContinue.isSelected()) {
											if (!name.getText().isEmpty() && !percentage.getText().isEmpty() &&
											semester.getValue() != 0 &&	course.getValue() != null && !branch.getText().isEmpty() &&
											year.getValue() != 0 && group.getSelectedToggle().isSelected() &&
											!admissionYear.getText().isEmpty()) {
												RadioButton rb = (RadioButton) group.getSelectedToggle();
												String n = name.getText();
												double p = Double.parseDouble(percentage.getText());
												int b = semester.getValue();
												String c = course.getValue();
												String br = branch.getText();
												int y = year.getValue();
												String g = rb.getText();
												int ay = Integer.parseInt(admissionYear.getText());
												System.out.print(n + p + b + c + br + y + g + ay);
												Studentdao sd = new Studentdao();
												if(sd.updateRecord(n, p, b, c, br, y, g, ay, i)) {
													displayLabel.setText("Record updated successfully");
													displayLabel.setTextFill(Color.GREEN);
													Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
													alert.setHeaderText("Record successfully updated!!!");
													alert.setContentText("To view your data, go to SHOW DATA");
													alert.show();
												}else {
													displayLabel.setText("Data doesn't exist for the student Id = "+i);
													displayLabel.setTextFill(Color.RED);
												}
											}
											else {
												displayLabel.setText("Please provide the necessary details");
												displayLabel.setTextFill(Color.RED);
											}
										} else {
											displayLabel.setText("Please select the the checkbox");
											displayLabel.setTextFill(Color.RED);
										}
									}
								});
								pane.getChildren().addAll(insertData, name, percentage, semester, course, branch, year, female, male, others, admissionYear, agreeAndContinue, submitData1, displayLabel);

						}

						else {
							displayLabel.setText("Please provide the Student Id");
							displayLabel.setTextFill(Color.RED);
						}
					}
				});
				pane.getChildren().addAll(updateData,id,submitData,displayLabel);
			}
		});
	}
	public void showData() {
		showData.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				pane.getChildren().clear();
				Label showData =new Label();
				showData.setText("Student Records");
				showData.setFont(new Font("Arial", 20));
				showData.setAlignment(Pos.CENTER);
				showData.setPrefSize(250,20);
				showData.relocate(180,5);

				TextField txtField = new TextField();
				txtField.setPromptText("Search");
				txtField.setPrefSize(250,20);
				txtField.relocate(180,30);

				TableView<Student> tableView = new TableView();
				tableView.relocate(0,60);
				tableView.setPrefWidth(600);
				tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

				TableColumn<Student,Integer> stu_id = new TableColumn<>("stu_id");
				TableColumn<Student,String> name = new TableColumn<>("Name");
				TableColumn<Student,Double> percentage = new TableColumn<>("Percentage");
				TableColumn<Student,Integer> semester = new TableColumn<>("Semester");
				TableColumn<Student,String> branch = new TableColumn<>("Branch");
				TableColumn<Student,String> course= new TableColumn<>("Course");
				TableColumn<Student,Integer> year = new TableColumn<>("Year");
				TableColumn<Student,String> gender = new TableColumn<>("Gender");
				TableColumn<Student,Integer> admissionyear = new TableColumn<>("Admission_Year");

				slist=FXCollections.observableArrayList();
				String SQL ="SELECT * from student";
				try {
					Connection con=DBConnect.connect();
					Statement statement = con.createStatement();
					ResultSet rs = statement.executeQuery(SQL);
					while(rs.next()) {
						Student s=new Student();
						s.setStu_id(rs.getInt("stu_id"));
						s.setName(rs.getString("name"));
						s.setPercentage(rs.getDouble("percentage"));
						s.setSemester(rs.getInt("semester"));
						s.setCourse(rs.getString("course"));
						s.setBranch(rs.getString("branch"));
						s.setYear(rs.getInt("year"));
						s.setGender(rs.getString("gender"));
						s.setAdmissionyear(rs.getInt("admission_year"));
						slist.add(s);

					}

					stu_id.setCellValueFactory(new PropertyValueFactory<>("stu_id"));
					name.setCellValueFactory(new PropertyValueFactory<>("name"));
					percentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));
					semester.setCellValueFactory(new PropertyValueFactory<>("semester"));
					branch.setCellValueFactory(new PropertyValueFactory<>("branch"));
					course.setCellValueFactory(new PropertyValueFactory<>("course"));
					year.setCellValueFactory(new PropertyValueFactory<>("year"));
					gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
					admissionyear.setCellValueFactory(new PropertyValueFactory<>("admissionyear"));
					tableView.setItems(slist);
					tableView.getColumns().addAll(stu_id,name,percentage,semester,branch,course,year,gender,admissionyear);

				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
				txtField.textProperty().addListener(new InvalidationListener() {


					@Override

					public void invalidated(Observable o) {

						if(txtField.textProperty().get().isEmpty()) {

							tableView.setItems(slist);

							return;

						}

						ObservableList<Student> tableItems = FXCollections.observableArrayList();

						ObservableList<TableColumn<Student, ?>> cols = tableView.getColumns();

						for(int i=0; i<slist.size(); i++) {



							for(int j=0; j<cols.size(); j++) {

								TableColumn col = cols.get(j);

								String cellValue = col.getCellData(slist.get(i)).toString();

								cellValue = cellValue.toLowerCase();

								if(cellValue.contains(txtField.textProperty().get().toLowerCase())) {

									tableItems.add(slist.get(i));

									break;

								}

							}


						}

						tableView.setItems(tableItems);

					}

				});
				pane.getChildren().addAll(showData,txtField,tableView);

			}
		});
	}
}