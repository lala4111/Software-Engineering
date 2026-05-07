package com.university.ui;

import com.university.database.DBConnection;
import com.university.model.Course;
import com.university.model.Enrollment;
import com.university.service.CourseService;
import com.university.service.EnrollmentService;
import com.university.service.LogInService;
import com.university.service.RegisterService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;


public class MainApp extends Application {
    BorderPane aLlPages= new BorderPane();
    VBox homePage = new VBox(10);
    VBox aboutPage = new VBox(10);
    VBox adminPage = new VBox(10);
    VBox coursesPage = new VBox(10);
    VBox signUpPage = new VBox(10);
    VBox loginPage = new VBox(10);
    VBox coursesboxes = new VBox();
    HBox aboutFooter = new HBox(10);
    Boolean loggedIn = false;
    Boolean sucessfullEnrollment = true;
    Boolean isAdmin = false;
    int tempStudent_id = 2;// temp var and needed to be replaced when the user login logic is ready
    int tempEnrollment_id = 1;
    int numberOfEnrollments = 0;
    private final EnrollmentService enrollmentService = new EnrollmentService(
    );
    TableView<Course> courseList = new TableView<>();
    ObservableList<Course> data = FXCollections.observableArrayList();

    double screenSize = Screen.getPrimary().getBounds().getWidth();
    //double screenSize = 1100;
    double titleFontSize= (int) (screenSize*0.01);
    double textFontSize= (int) (screenSize *0.008);
    double primaryBtnFSize= (int) (screenSize *0.03);
    Button logOutButton = new Button("Log Out");
    List<Course> courses = new CourseService().getCourses();
    HBox filters = new HBox(10);
    Button applyFilters = new Button("Apply Filters");
    ComboBox<String> categoryFilter = new ComboBox<>();
    String[] categories= {"Select Category","Math", "Physics","IT","Art", "Linguistics","Literature","History"};
    ObservableList<String> categoryList = FXCollections.observableArrayList(categories);

    ComboBox<String> feeFilter = new ComboBox<>();
    String[] fees= {"Select Fee Range","less than 50", "50-100","100-200","bigger than 200"};
    ObservableList<String> feeList = FXCollections.observableArrayList(fees);

    ComboBox<String> levelFilter = new ComboBox<>();
    String[] levels= {"Select Level","Beginner","Intermediate", "Advanced"};
    ObservableList<String> levelList = FXCollections.observableArrayList(levels);
    Button clearFilter = new Button("Clear Filter");
    TextField searchCourse = new TextField("search by course title");
    Button searchCourseButton = new Button("Search Course");

    List<Enrollment> enrollmentList= new EnrollmentService().getALlEnrollments();
    Button enrollmentmangement= new Button("Enrollment Mangement");
    VBox enrollmenBox = new VBox(10);
    TableView<Enrollment> enrollmentTableView = new TableView<>();

    Button searchbyStdID = new Button("Search by Student ID");
    TextField stdID = new TextField();
    Label stdIDLabel = new Label("Student ID");



    String headerButtonStlye= "-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 10; -fx-border-radius: 10;" +
            "-fx-font-weight:bold; -fx-text-fill: #FFFFFF;"+"-fx-font-size:" +titleFontSize+ "px; -fx-text-alignment: center;";
    String primaryBtnStlye= "-fx-background-color: white; -fx-background-radius: 7; -fx-border-color: #d0baf5;  -fx-padding: 8 16;" +
            "-fx-text-fill: #12012e; -fx-font-size: 18px;-fx-border-radius: 8; -fx-font-weight:bold";
    String primaryBtnStlye2= "-fx-background-color: #D0BAF5FF;" +
            "-fx-text-fill: #12012EFF;  -fx-font-size:titleFontSize+px;-fx-border-radius: 8; -fx-font-weight:bold; -fx-text-alignment: center;";
    String primaryBtnStlye3= " -fx-text-fill: #12012e; -fx-font-size: 12px;-fx-border-radius: 8; -fx-font-weight:bold";

    String titleStyle= "fx-text-fill:#12012e;"+ "-fx-font-size:" +titleFontSize+ "px;"+" -fx-font-weight: bold; -fx-text-alignment: center;";
    //rara part
    Manage archive = new Manage();
    VBox coursel = new VBox();
    TilePane tp1 = new TilePane();


    public TabPane tb () {
        TabPane tabs = new TabPane();
        tabs.setStyle(primaryBtnStlye3);
        Tab t1 = new Tab();
        t1.setText("Add Course");
        t1.setClosable(false);
        t1.setContent(tab1());// inset the created tab into the Tab content

        tabs.getTabs().add(t1);

        Tab t2 = new Tab();
        t2.setText("Course Dashboard");
        t2.setClosable(false);
        t2.setContent(tab2());
        tabs.getTabs().add(t2);

        Tab t3 = new Tab();
        t3.setText("Manage Enrollment");
        t3.setClosable(false);
        t3.setContent(enrollmenBox);
        tabs.getTabs().add(t3);

        return tabs;
    }

    public Pane tab1(){
        //Text id = new Text("Course ID");
        Text name = new Text("Name");
        Text description = new Text("Description");
        Text capacity = new Text("Capacity");
        Text fee = new Text("Fee");
        Text schedule = new Text("Schedule");
        Text level = new Text("Level");
        Text category = new Text("Category");
        Text credit = new Text("Credit");

        Text id_num = new Text(Integer.toString(courses.getLast().getId() + 1));
        TextField tx_name = new TextField();
        TextArea ta_description= new TextArea();
        ta_description.setPrefColumnCount(5);
        ta_description.setPrefRowCount(5);
        ta_description.setWrapText(true);
        TextField tx_capacity = new TextField();
        TextField tx_fee = new TextField();
        TextField tx_schedule = new TextField();
        ChoiceBox<String> cb_level = new ChoiceBox<>();
        cb_level.getItems().addAll("Select","beginner","intermediate", "advanced");
        cb_level.setValue("Select");
        ChoiceBox<String> cb_category = new ChoiceBox<>();
        cb_category.getItems().addAll("Select","Math", "Physics","IT","Art", "Linguistics","Literature","History");
        cb_category.setValue("Select");
        TextField tx_credit = new TextField();
        Button add = new Button("Add");
        Button clear = new Button("Clear");

        add.setOnAction(e->{
            try{
                CourseService courseService = new CourseService();
                courseService.addCourse(tx_name.getText(),ta_description.getText(),Integer.parseInt(tx_capacity.getText()),
                        Double.parseDouble(tx_fee.getText()),tx_schedule.getText(), Course.Level.valueOf(cb_level.getValue().toLowerCase()), cb_category.getValue(),
                        Integer.parseInt(tx_credit.getText()));
                showEnrollmentAlert(Alert.AlertType.CONFIRMATION, "sucessful creation", "Course created successfully");


                Course one = new Course(
                        Integer.parseInt(id_num.getText()),
                        tx_name.getText(),
                        ta_description.getText(),
                        Integer.parseInt(tx_capacity.getText()),
                        Double.parseDouble(tx_fee.getText()),
                        tx_schedule.getText(),
                        Course.Level.valueOf(cb_level.getValue()),
                        cb_category.getValue(),
                        Integer.parseInt(tx_credit.getText())


                );

                Course table = new Course(
                        Integer.parseInt(id_num.getText()),
                        tx_name.getText(),
                        cb_category.getValue(),
                        Double.parseDouble(tx_fee.getText()),
                        Integer.parseInt(tx_capacity.getText()),
                        Integer.parseInt(tx_credit.getText()),
                        tx_schedule.getText(),
                        Course.Level.valueOf(cb_level.getValue())
                );
                data.add(table);

                courseList.setItems(data);

                archive.setArchive(one);

                id_num.setText(Integer.toString(courses.getLast().getId() + 1));
                tx_name.clear();
                ta_description.clear();
                tx_capacity.clear();
                tx_fee.clear();
                tx_schedule.clear();
                cb_level.setValue("Select");
                cb_category.setValue("Select");
                tx_credit.clear();

                tp1.setVgap(10);
                tp1.setHgap(10);
                tp1.setPrefColumns(3);

                Rectangle r1 = new Rectangle(100,100);
                r1.setFill(Color.WHITE);
                tp1.getChildren().add(r1);
                r1.setOnMouseClicked(en-> getDetail(one));


            }
            catch(Exception exception){
                System.out.println(exception);
            }
        });

        clear.setOnAction(e->{

        });

        GridPane gp = new GridPane();
        //gp.add(id,0,0);
        //gp.add(id_num,1,0);
        gp.add(name,0,0);
        gp.add(tx_name,1,0);
        gp.add(capacity,2,0);
        gp.add(tx_capacity,3,0);
        gp.add(fee,0,1);
        gp.add(tx_fee,1,1);
        gp.add(schedule,2,1);
        gp.add(tx_schedule,3,1);
        gp.add(credit,0,2);
        gp.add(tx_credit,1,2);
        gp.add(level,2,2);
        gp.add(cb_level,3,2);
        gp.add(category,0,3);
        gp.add(cb_category,1,3);


        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(30));

        VBox vb1 = new VBox();
        vb1.getChildren().addAll(description,ta_description);
        vb1.setSpacing(5);
        vb1.setPadding(new Insets(30));
        vb1.setAlignment(Pos.CENTER);

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(add,clear);
        hb1.setAlignment(Pos.CENTER);
        hb1.setSpacing(200);


        VBox vb2 = new VBox();
        vb2.getChildren().addAll(gp,vb1,hb1);

        Pane p = new Pane();
        p.getChildren().add(vb2);

        return p;
    }

    public VBox tab2(){
        try(Connection connection= DBConnection.getConnection()) {
            String sql = "select id,title,seat,fee,schedule,level,category,credits from course";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery(sql);
            // always returns exactly one row, so an 'if' statement is sufficient instead of a 'while' loop
            while (rs.next()) {
                data.add(new Course(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getDouble("fee"),
                        rs.getInt("seat"),
                        rs.getInt("credits"),
                        rs.getString("schedule"),
                        Course.Level.valueOf(rs.getString("level"))

                ));
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        courseList.setItems(data);
        TableColumn<Course, Integer> coId = new TableColumn<>("ID");
        TableColumn<Course, String> coTitle = new TableColumn<>("Title");
        coTitle.setMinWidth(150);
        TableColumn<Course, String> coCategory = new TableColumn<>("Category");
        coCategory.setMinWidth(150);
        TableColumn<Course, Double> coFee = new TableColumn<>("Fee");
        TableColumn<Course, Integer> coSeat = new TableColumn<>("Capacity");
        TableColumn<Course, Integer> coCredit = new TableColumn<>("Credits");
        TableColumn<Course, String> coSchedule = new TableColumn<>("Schedule");
        coSchedule.setMinWidth(150);
        TableColumn<Course, Course.Level> coLevel = new TableColumn<>("Level");

        coId.setCellValueFactory(new PropertyValueFactory<Course, Integer>("id"));
        coTitle.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
        coCategory.setCellValueFactory(new PropertyValueFactory<Course, String>("category"));
        coFee.setCellValueFactory(new PropertyValueFactory<Course, Double>("fee"));
        coSeat.setCellValueFactory(new PropertyValueFactory<Course, Integer>("seatNum"));
        coCredit.setCellValueFactory(new PropertyValueFactory<Course, Integer>("credits"));
        coSchedule.setCellValueFactory(new PropertyValueFactory<Course, String>("schedule"));
        coLevel.setCellValueFactory(new PropertyValueFactory<Course, Course.Level>("level"));
        courseList.getColumns().addAll(coId,coTitle, coCategory,coFee,coSeat,coCredit,coSchedule,coLevel);
        coursel.getChildren().addAll(courseList);
        courseList.setOnMouseClicked(event->{
            Course selected = courseList.getSelectionModel().getSelectedItem();
            getDetail(selected);

        });
        return coursel;
    }

    public void getDetail(Course p){
        Stage stage = new Stage();
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20));

        Text t1 = new Text("Course ID: ");
        Text id_num = new Text(String.valueOf(p.getId()));
        Text t2 =new Text("Course Name: ");
        TextField tx2 = new TextField(p.getTitle());
        Text t3 =new Text("Capacity: ");
        TextField tx3 = new TextField(String.valueOf(p.getSeatNum()));
        Text t4 =new Text("Fee: ");
        TextField tx4 = new TextField(String.valueOf(p.getFee()));
        Text t5 =new Text("Level: ");
        TextField tx5 = new TextField(String.valueOf(p.getLevel()));
        Text t6 =new Text("Category: ");
        TextField tx6 = new TextField(p.getCategory());
        Text t7 =new Text("Credits: " );
        TextField tx7 = new TextField(String.valueOf(p.getCredits()));
        Text t8 =new Text("Schedule: " );
        TextField tx8 = new TextField(p.getSchedule());
        Text t9 =new Text("Description: ");
        TextField tx9 = new TextField(p.getDescription());

        Button modi = new Button("Modify");
        modi.setStyle("-fx-background-color: #d8b4fe;");
        Button delete = new Button("Delete");

        Text t10 = new Text("");
        t10.setFill(Color.RED);


        modi.setOnAction(e->{

            try{p.setTitle(tx2.getText());
            p.setSeat(Integer.parseInt(tx3.getText()));
            p.setFee((int) Double.parseDouble(tx4.getText()));
            p.setLevel(Course.Level.valueOf(tx5.getText()));
            p.setCategory(tx6.getText());
            p.setCredits(Integer.parseInt(tx7.getText()));
            p.setSchedule(tx8.getText());
            p.setDescription(tx9.getText());
            courseList.refresh();}

            catch(IllegalArgumentException exception){
                t10.setText("Please enter correct level");

            }

        });

        delete.setOnAction(de->{
            Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
            conf.setTitle("Confirm");
            conf.setContentText("Are you sure you want to delete this course?");
            Optional<ButtonType> result = conf.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                CourseService courseService = new CourseService();
                Course sele = courseList.getSelectionModel().getSelectedItem();
                courseService.deleteCourse(sele.getId());

            }else {

                System.out.println("Cancel deletion");
            }

        });
        //showEnrollmentAlert(Alert.AlertType.CONFIRMATION, "sucessful modification", "Course updated successfully");


        //gp.add(t1,0,0);
        //gp.add(id_num,1,0);
        gp.add(t2,0,1);
        gp.add(tx2,1,1);
        gp.add(t3,0,2);
        gp.add(tx3,1,2);
        gp.add(t4,0,3);
        gp.add(tx4,1,3);
        gp.add(t5,0,4);
        gp.add(tx5,1,4);
        gp.add(t6,0,5);
        gp.add(tx6,1,5);
        gp.add(t7,0,6);
        gp.add(tx7,1,6);
        gp.add(t8,0,7);
        gp.add(tx8,1,7);
        gp.add(t9,0,8);
        gp.add(tx9,1,8);
        gp.add(t10,0,9);
        gp.add(modi,0,10);
        gp.add(delete,1,10);

        gp.setVgap(5);
        gp.setHgap(5);
        gp.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gp, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Course Detail");
        stage.show();
    }

    private void showEnrollmentAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-size: " + textFontSize + "px; -fx-font-weight: bold;");

        alert.showAndWait();
    }

    public VBox getCourseBox(Course course){
        VBox courseBox = new VBox(10);


        Label courseName = new Label("Course Name: "+course.getTitle());
        courseName.setStyle("fx-text-fill:#12012e;"+ "-fx-font-size:" +titleFontSize+ "px;"+" -fx-font-weight: bold;");

        TextArea courseDescription = new TextArea("Description: "+course.getDescription());
        courseDescription.setWrapText(true);
        courseDescription.setMaxWidth(Double.MAX_VALUE);
        courseDescription.setEditable(false);
        courseDescription.setStyle("-fx-font-size:" +textFontSize+ "px;");
        Button enrollBtn = new Button("Enroll");
        Button loginToEnrollBtn = new Button("Login to Enrollment");

        Course currentCourse = course;
        int currentCourseId= course.getId();
        Label courseFee= new Label("Course Fee: "+ String.valueOf(currentCourse.getFee()));
        Label courseLevel= new Label("Course Level: "+ String.valueOf(currentCourse.getLevel()).toLowerCase());
        Label courseCategory= new Label("Course Category: "+ String.valueOf(currentCourse.getCategory()));


        enrollBtn.setOnAction(e -> {
            boolean success = enrollmentService.enrollStudent(
                    tempStudent_id,
                    currentCourseId,
                    Enrollment.PaymentStatus.unpaid,
                    Enrollment.EnrollmentStatus.pending
            );

            if (success) {
                // Enrollment successful
                showEnrollmentAlert(
                        Alert.AlertType.INFORMATION,
                        "Successfully Enrolled",
                        "You have registered successfully! Please visit the office to confirm your seat within 2 days."
                );
            } else {
                // Enrollment failed
                // provide specific feedback to the user
                int currentCount = enrollmentService.getCourseEnrollmentsCount(currentCourseId);

                if (currentCount >= currentCourse.getSeatNum()) {
                    showEnrollmentAlert(
                            Alert.AlertType.ERROR,
                            "Enrollment Failed",
                            "The course quota is full."
                    );
                } else {
                    showEnrollmentAlert(
                            Alert.AlertType.ERROR,
                            "Enrollment Failed",
                            "You have already enrolled for this course or a system error occurred."
                    );
                }
            }
        });

        enrollBtn.setStyle(primaryBtnStlye);

        loginToEnrollBtn.setStyle(primaryBtnStlye);

        Label quota= new Label("Remaining seats "+String.valueOf(course.getSeatNum()));
        quota.setStyle("-fx-font-size:" +textFontSize+ "px;");
        quota.setStyle("-fx-font-size:" +textFontSize+ "px;");
        courseCategory.setStyle("-fx-font-size:" +textFontSize+ "px;");
        courseLevel.setStyle("-fx-font-size:" +textFontSize+ "px;");
        courseFee.setStyle("-fx-font-size:" +textFontSize+ "px;");
        if(loggedIn & !isAdmin){
            courseBox.getChildren().addAll(courseName,courseDescription, quota, enrollBtn);
        }  else if (loggedIn& isAdmin) {
            courseBox.getChildren().addAll(courseName,courseDescription, quota);

        } else{
            courseBox.getChildren().addAll(courseName,courseDescription, quota, loginToEnrollBtn);
        }
        loginToEnrollBtn.setOnAction(e -> {aLlPages.setCenter(loginPage);});

        courseBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: #d0baf5;  -fx-padding: 15; -fx-border-radius: 10; ");


        return courseBox;

    }
    VBox getCoursesDiaplayList(){
        coursesboxes.getChildren().clear();
        filters.getChildren().clear();

        categoryFilter.getItems().setAll(categoryList);
        //categoryFilter.setValue(categoryFilter.getValue());
        feeFilter.getItems().setAll(feeList);
        categoryFilter.setValue("Select Category");
        feeFilter.setValue("Select Fee Range");
        feeFilter.setStyle(primaryBtnStlye2); categoryFilter.setStyle(primaryBtnStlye2); applyFilters.setStyle(primaryBtnStlye2);
        levelFilter.getItems().setAll(levelList); clearFilter.setStyle(primaryBtnStlye2); searchCourseButton.setStyle(primaryBtnStlye2);
        levelFilter.setStyle(primaryBtnStlye2); searchCourse.setStyle(primaryBtnStlye2);

        levelFilter.setValue("Select Level");
       
        filters.getChildren().addAll(categoryFilter,feeFilter, levelFilter);
        filters.getChildren().add(applyFilters);
        filters.getChildren().add(clearFilter);
        filters.getChildren().addAll(searchCourse, searchCourseButton);
        coursesboxes.getChildren().add(filters);
        filters.setPadding(new Insets(20));
        AtomicReference<Double> min = new AtomicReference<>((double) 0);
        AtomicReference<Double> max = new AtomicReference<>((double) 0);

        searchCourse.setOnMouseClicked(e -> {searchCourse.clear();});
        searchCourseButton.setOnAction(e -> {
            Boolean isfound=false;

            //new CourseService().searchCourse(Integer.parseInt(searchCourse.getText()));
            for(Course course: courses){
                if (searchCourse.getText().trim().equals( course.getTitle())) {isfound=true;}
            }
            new CourseService().searchCourse(searchCourseButton.getText());
            coursesboxes.getChildren().clear();
            //.getChildren().add(clearFilter);
            coursesboxes.getChildren().add(filters);
            //coursesboxes.getChildren().add(getCourseBox(new CourseService().searchCourse(Integer.parseInt(searchCourse.getText()))));
            System.out.println(getCourseBox(new CourseService().searchCourse(searchCourse.getText())));
            if(isfound){
                coursesboxes.getChildren().add(getCourseBox(new CourseService().searchCourse(searchCourse.getText())));
                showEnrollmentAlert(Alert.AlertType.CONFIRMATION,"found","corresponding result");
            }else{
                showEnrollmentAlert(Alert.AlertType.ERROR,"Not found","please try again, all captial and small letters must match");
                getCoursesDiaplayList();
            }


        });


        applyFilters.setOnAction(e->{
            String selectedLvlVal=levelFilter.getValue();
            String categoryVal=categoryFilter.getValue();
            String feeVal=feeFilter.getValue();

            if(!feeVal.equals("Select Fee Range")&& feeVal!=null){
                if(feeFilter.getValue().equals("less than 50")){
                    min.set(0.0);
                    max.set(50.0);
                } else if (feeFilter.getValue().equals("50-100")) {
                    min.set(50.0);
                    max.set(100.0);
                }else if (feeFilter.getValue().equals("100-200")) {
                    min.set(100.0);
                    max.set(200.0);
                }else if(feeFilter.getValue().equals("bigger than 200")){
                    min.set(200.01);
                    max.set(2000000.0);
                }
            }

            if(!categoryVal.equals("Select Category") && categoryVal!=null &&  !feeVal.equals("Select Fee Range")&& feeVal!=null
            && selectedLvlVal!= null&& !selectedLvlVal.equals("Select Level") ){

                selectedLvlVal=selectedLvlVal.toLowerCase();

                CourseService courseService = new CourseService();
                coursesboxes.getChildren().clear();
                courses= courseService.filterByCategoryFeeLevel(categoryFilter.getValue(), min.get(), max.get(), levelFilter.getValue().toLowerCase());
            }  else if (!categoryVal.equals("Select Category") && categoryVal !=null && selectedLvlVal!= null&& !selectedLvlVal.equals("Select Level")) {

                selectedLvlVal=selectedLvlVal.toLowerCase();


                CourseService courseService = new CourseService();
                coursesboxes.getChildren().clear();

                courses=courseService.filterByCategoryLevel(categoryVal,selectedLvlVal );
            }
            else if (!categoryVal.equals("Select Category") && categoryVal !=null && !feeVal.equals("Select Fee Range") && feeVal!=null) {
                CourseService courseService = new CourseService();
                coursesboxes.getChildren().clear();

                courses=courseService.filterByCategoryFee(categoryFilter.getValue(), min, max);
            }

            if(  !feeVal.equals("Select Fee Range")&& feeVal!=null
                    && selectedLvlVal!= null&& !selectedLvlVal.equals("Select Level") ){

                selectedLvlVal=selectedLvlVal.toLowerCase();

                CourseService courseService = new CourseService();
                coursesboxes.getChildren().clear();
                courses= courseService.filterByFeeLevel( min.get(), max.get(), levelFilter.getValue().toLowerCase());
            }



            else if(!categoryFilter.getValue().equals("Select Category")){
                CourseService courseService = new CourseService();
                coursesboxes.getChildren().clear();
                courses= courseService.filterByCategory(categoryFilter.getValue());
            }
            else if (selectedLvlVal!= null&& !selectedLvlVal.equals("Select Level")) {
                courses= new CourseService().filterByLevel(selectedLvlVal);

            }
            else if (!feeVal.equals("Select Fee Range") && feeVal!=null) {
                CourseService courseService = new CourseService();
                coursesboxes.getChildren().clear();
                courses= courseService.filterByPrice(min, max);

            }
            else{
                courses= new CourseService().getCourses();
            }
            coursesboxes.getChildren().clear();
            coursesboxes.getChildren().add(filters);


            for (int i=0;i<courses.size();i++){
                coursesboxes.getChildren().add(getCourseBox(courses.get(i)));
            }

            //filters.getChildren().add(clearFilter);



            //filters
            //getCoursesDiaplayList();

        });
        clearFilter.setOnAction(e->{
            coursesboxes.getChildren().clear();
            categoryFilter.setValue("Select Category");
            feeFilter.setValue("Select Fee Range");
            levelFilter.setValue("Select Level");
            courses= new CourseService().getCourses();
            coursesboxes.getChildren().add(filters);
            for (int i=0;i<courses.size();i++){
                coursesboxes.getChildren().add(getCourseBox(courses.get(i)));
            }

        });



        for(int i =0; i < courses.size(); i++){
            coursesboxes.getChildren().add(getCourseBox(courses.get(i)));
            coursesboxes.setSpacing(10);
            coursesboxes.setPadding(new Insets(10,10,10,10));
            //coursesboxes.set
        }
        return coursesboxes;

    }



    @Override
    public void start(Stage stage) {
        //rara part
        TabPane tabs = tb();
        tabs.setStyle("-fx-font-weight:bold; -fx-text-fill: #FFFFFF;"+"-fx-font-size:18px;");

        TableColumn<Enrollment, Integer> column1 =
                new TableColumn<>("Enrollment ID");

        column1.setCellValueFactory(
                new PropertyValueFactory<>("enrollmentId"));
        TableColumn<Enrollment, Integer> column2 =
                new TableColumn<>("Course ID");

        column2.setCellValueFactory(
                new PropertyValueFactory<>("id_Course"));

        TableColumn<Enrollment, Integer> column3 =
                new TableColumn<>("Student ID");

        column3.setCellValueFactory(
                new PropertyValueFactory<>("id_Student"));



        TableColumn<Enrollment, Enrollment.PaymentStatus> column4 =
                new TableColumn<>("Payment status");

        column4.setCellValueFactory(
                new PropertyValueFactory<>("payment_status"));
        column4.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Enrollment.PaymentStatus.values())));
        column4.setOnEditCommit(status -> {
            ((Enrollment) status.getTableView().getItems()
                    .get(status.getTablePosition().getRow()))
                    .setPayment_status(status.getNewValue());

            EnrollmentService enrollmentService = new EnrollmentService();
            Enrollment enrollment= status.getRowValue();
            enrollmentService.updateEnrollmentStatus(enrollment.getEnrollmentId(),
                    enrollment.getPayment_status(), enrollment.getEnrollment_status());
        });

        column4.setStyle("-fx-alignment: CENTER; -fx-cell-size:40px;");





        ComboBox<String> testList = new ComboBox<>();
        String[] testoptions= {"pending", "enrolled", "completed", "dropped"};
        ObservableList<Enrollment.EnrollmentStatus> testListober = FXCollections.observableArrayList(Enrollment.EnrollmentStatus.values());


        TableColumn<Enrollment, Enrollment.EnrollmentStatus> column5 =
                new TableColumn<>("Enrollment Status");
        column5.setCellValueFactory(
                new PropertyValueFactory<>("enrollment_status"));

        column5.setCellFactory(ComboBoxTableCell.forTableColumn(testListober));
        column5.setStyle("-fx-cell-size:80px;");
        column4.setStyle("-fx-cell-size:80px;");





        enrollmentTableView.setEditable(true);
        enrollmentTableView.setFixedCellSize(50);
        enrollmentTableView.setStyle("-fx-alignment: CENTER;");
        enrollmentTableView.setMinHeight(50);

        TableColumn<Enrollment, String> column6 =
                new TableColumn<>("Course Name");

        column6.setCellValueFactory(
                new PropertyValueFactory<>("courseName"));
        TableColumn<Enrollment, String> column7 =
                new TableColumn<>("Student firstName");

        column7.setCellValueFactory(
                new PropertyValueFactory<>("studentFirstName"));

        TableColumn<Enrollment, String> column8 =
                new TableColumn<>("Student lastName");

        column8.setCellValueFactory(
                new PropertyValueFactory<>("studentLastName"));



        column5.setEditable(true);
        column5.setOnEditStart(event -> {});


        enrollmentTableView.getColumns().addAll(column1, column2,column3, column4,column5, column6, column7, column8);
        enrollmentTableView.getItems().setAll(enrollmentList);
        //tblViewer.getItems().setAll(getAllstudentInfo());
        //enrollmentTableView.getItems().addAll(enrollmentObservableList);


        column5.setOnEditCommit(status -> {
            ((Enrollment) status.getTableView().getItems()
                    .get(status.getTablePosition().getRow()))
                    .setEnrollment_status(status.getNewValue());

            EnrollmentService enrollmentService = new EnrollmentService();
            Enrollment enrollment= status.getRowValue();
            column5.setEditable(true);
            enrollmentService.updateEnrollmentStatus(enrollment.getEnrollmentId(),
                    enrollment.getPayment_status(), enrollment.getEnrollment_status());
                });




        VBox tablebox = new VBox(enrollmentTableView);
        HBox searchEnrollment = new HBox(10);
        searchEnrollment.setAlignment(Pos.TOP_CENTER);
        searchEnrollment.setSpacing(10);
        searchEnrollment.setPadding(new Insets(10,10,10,10));
        stdIDLabel.setStyle(titleStyle);
        stdID.setStyle(titleStyle);

        searchEnrollment.getChildren().addAll(stdIDLabel, stdID, searchbyStdID);
        searchbyStdID.setStyle(titleStyle);
        enrollmenBox.getChildren().add(searchEnrollment);
        enrollmenBox.getChildren().add(tablebox);

        searchEnrollment.setStyle("-fx-alignment: CENTER;");

        searchbyStdID.setOnAction(event -> {
            enrollmentTableView.getItems().clear();
            enrollmentList= new EnrollmentService().searchEnrollment(Integer.parseInt(stdID.getText()));
            enrollmentTableView.getItems().setAll(enrollmentList);
            if(stdID.getText()==null || stdID.getText().isEmpty() || !new EnrollmentService().isStdIdExist(Integer.parseInt(stdID.getText()))){
                showEnrollmentAlert(Alert.AlertType.ERROR, "Invalid Student ID", "Student ID is invalid");
            }

        });



        //aboutPage.getChildren().add(enrollmentmangement);


        ScrollPane scrollPane = new ScrollPane();
        //scrollPane.setContent(coursesboxes);
        scrollPane.setContent(getCoursesDiaplayList());
        HBox header = new HBox();
        header.setSpacing(20);
        header.setPadding(new Insets(10,10,30,10));
        header.setSpacing(20);
        header.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 20%, purple 0%, indigo 100%)");

        Button homeBtn = new Button("Home");
        homeBtn.setStyle(headerButtonStlye);
        Button aboutBtn = new Button("About");
        aboutBtn.setStyle(headerButtonStlye );
        Button admindashboardBtn = new Button("Admindashboard");
        admindashboardBtn.setStyle(headerButtonStlye );
        logOutButton.setStyle(headerButtonStlye );
        Button courseBtn = new Button("Courses");
        courseBtn.setStyle(headerButtonStlye );
        Button loginBtn = new Button("Login");
        loginBtn.setStyle(headerButtonStlye );
        Button signupBtn = new Button("Signup");
        signupBtn.setStyle(headerButtonStlye );
        Label userId = new Label("User ID: "+ tempStudent_id);
        userId.setStyle(headerButtonStlye );

        // View Details & Enroll Button: separated??
        // view and enroll in the same course box

        Button footerHomeBtn = new Button("Home");
        footerHomeBtn.setStyle(headerButtonStlye);
        Button footerAboutBtn = new Button("About");
        footerAboutBtn.setStyle(headerButtonStlye );
        Button footerCourseBtn = new Button("Courses");
        footerCourseBtn.setStyle(headerButtonStlye );

        aboutFooter.setSpacing(20);
        aboutFooter.setPadding(new Insets(10,10,30,10));
        aboutFooter.setSpacing(20);

        aboutFooter.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 20%, purple 0%, indigo 100%)");

        aboutFooter.getChildren().addAll(footerHomeBtn,footerAboutBtn,footerCourseBtn);
        if(isAdmin){
            header.getChildren().addAll(homeBtn,aboutBtn,courseBtn,admindashboardBtn, logOutButton);
        }else{
            if(loggedIn){
                header.getChildren().addAll(homeBtn,aboutBtn, courseBtn, userId, logOutButton);
            }else{
                header.getChildren().addAll(homeBtn,aboutBtn, courseBtn, loginBtn,signupBtn);
            }
        }

// ADDED BY ARNAS ON 24.04.2026 ============================================================================

        // loginpage and signuppage...
        TextField usernameFd = new TextField();
        usernameFd.setPromptText("Username");
        TextField passwordFd = new TextField(); // Can be set to PasswordField
        //PasswordField passwordFd= new PasswordField();
        passwordFd.setPromptText("Password");
        Button logbtn = new Button("Log in");
        logbtn.setStyle(primaryBtnStlye2);

        logbtn.setOnAction(e->{ // log in button
            loggedIn = LogInService.CheckPassword(usernameFd.getText(), passwordFd.getText()); // TODO: make it into a function, check values
            if (loggedIn){
                isAdmin = LogInService.GetPrivilege(usernameFd.getText());
                tempStudent_id = LogInService.GetID(usernameFd.getText());
                userId.setText("User ID: "+ tempStudent_id);
                System.out.println(tempStudent_id + " ID UPDATED");
                userId.setText("User ID: " + tempStudent_id);
                header.getChildren().clear();
                loggedIn= true;
                scrollPane.setContent(coursesboxes);

                if(isAdmin){ // Admin view if true
                    header.getChildren().addAll(homeBtn,aboutBtn,courseBtn,admindashboardBtn, userId, logOutButton);
                }
                else{ // Normie view
                    header.getChildren().addAll(homeBtn,aboutBtn, courseBtn, userId, logOutButton);
                }
                VBox coursesDiaplayList= getCoursesDiaplayList();
                scrollPane.setContent(coursesDiaplayList);
                aLlPages.setCenter(scrollPane);

                usernameFd.clear(); passwordFd.clear();
            }
            else {
                showEnrollmentAlert(
                        Alert.AlertType.ERROR,
                        "Unsuccessful Login",
                        "Incorrect Username or Password"
                );

            }
        });

        loginPage.getChildren().addAll(usernameFd, passwordFd, logbtn); // creating the login page
        logOutButton.setOnAction(e->{ // logging out and setting you back to the logged out view
            loggedIn = false;
            tempStudent_id = -1;
            header.getChildren().clear();
            header.getChildren().addAll(homeBtn,aboutBtn, courseBtn,loginBtn, signupBtn);
            VBox coursesDiaplayList= getCoursesDiaplayList();
            scrollPane.setContent(coursesDiaplayList);
            aLlPages.setCenter(scrollPane);
        }
        );

        TextField newUsername = new TextField(); //Registration view
        newUsername.setPromptText("Username");
        TextField newPassword = new TextField();
        //PasswordField newPassword = new PasswordField();
        newPassword.setPromptText("Password");
        TextField againPassword = new TextField();
        againPassword.setPromptText("Confirm password");
        TextField firstName = new TextField();
        firstName.setPromptText("First name");
        TextField surName = new TextField();
        surName.setPromptText("Surname");
        TextField phone = new TextField();
        phone.setPromptText("Phone");
        TextField email = new TextField();
        email.setPromptText("Email");

        Button regBtn = new Button("Register"); // button for registering

        regBtn.setOnAction(e->{ //TODO: check phone value?
            if((!newUsername.getText().isBlank() || !newPassword.getText().isBlank() || !firstName.getText().isBlank() ||
                    !surName.getText().isBlank() || !phone.getText().isBlank() || !email.getText().isBlank()) &&
                    newPassword.getText().equals(againPassword.getText())) { // the first if checks if the spaces are filled and if the passwords match
                if (RegisterService.AddAccount(newUsername.getText(), newPassword.getText(), firstName.getText(), surName.getText(), phone.getText(), email.getText())) {
                    loggedIn = LogInService.CheckPassword(newUsername.getText(), newPassword.getText()); // checks inside if email is fine, adds to the database if it is
                    if (loggedIn) { // same as log in button, might move to a separate method?
                        isAdmin = LogInService.GetPrivilege(newUsername.getText());
                        tempStudent_id = LogInService.GetID(newUsername.getText());
                        userId.setText("User ID: " + tempStudent_id);
                        header.getChildren().clear();
                        header.getChildren().addAll(homeBtn, aboutBtn, courseBtn, userId, logOutButton);
                        VBox coursesDiaplayList = getCoursesDiaplayList();
                        scrollPane.setContent(coursesDiaplayList);
                        aLlPages.setCenter(scrollPane);
                    }
                }else showEnrollmentAlert(
                        Alert.AlertType.ERROR,
                        "Unsuccessful Registration",
                        "Email might be in use."
                );
            }
            else showEnrollmentAlert(
                    Alert.AlertType.ERROR,
                    "Unsuccessful Registration",
                    "Make sure you filled out the fields right."
            );

        });


        signUpPage.getChildren().addAll(newUsername,newPassword,againPassword,firstName,surName,phone,email,regBtn);



         adminPage.getChildren().add(tabs);
        coursesPage.getChildren().add(new Label("courses page, user view courses"));
        aboutPage.getChildren().add(new Label("information abouty the orgnaization"));

        homeBtn.setOnAction(e -> {
            aLlPages.setCenter(scrollPane);});
        aboutBtn.setOnAction(e -> {aLlPages.setCenter(aboutPage);});
        loginBtn.setOnAction(e -> {aLlPages.setCenter(loginPage);});
        signupBtn.setOnAction(e -> {aLlPages.setCenter(signUpPage);});
        admindashboardBtn.setOnAction(e -> {aLlPages.setCenter(adminPage);});
        courseBtn.setOnAction(e -> {aLlPages.setCenter(scrollPane);});
        footerHomeBtn.setOnAction(e -> {
            aLlPages.setCenter(scrollPane);});
        footerAboutBtn.setOnAction(e -> {aLlPages.setCenter(aboutPage);});
        footerCourseBtn.setOnAction(e -> {aLlPages.setCenter(coursesPage);});


        scrollPane.setStyle("-fx-border-color: black;");
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(20));
        scrollPane.setPrefHeight(400);
        aLlPages.setCenter(scrollPane);
        aLlPages.setTop(header);
        aLlPages.setBottom(aboutFooter);
        Scene scene = new Scene(aLlPages, 1000, 900);
        stage.setScene(scene);
        stage.setTitle("Course System");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}










