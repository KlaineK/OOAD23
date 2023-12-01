module finalProject {
	requires java.sql;
	requires javafx.graphics;
	requires javafx.controls;
	opens database;
	opens main;
	opens view;
	opens controller;
	opens model;
}