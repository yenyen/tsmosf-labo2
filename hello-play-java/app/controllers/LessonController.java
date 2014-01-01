package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Lesson;
import models.StudentLesson;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

public class LessonController extends AbstractRecordController<Lesson> {


    protected LessonController() {
		super(Lesson.class);
	}
	private static Class<?> thisClass=LessonController.class;
    
//    private static List<Lesson> items=new ArrayList<Lesson>();
//    static {
//    	Lesson o=null;
//    	for(int i=0; i<10; i++){
//    		items.add(o=new Lesson(i+1l, "Item "+i));
//    		for(int j=0; j<10; j++){
//    			o.add();
//    		}
//    	}
//    }
    static LessonController _ME=null;
    public static LessonController me(){
    	if(_ME!=null)
    		return _ME;
    	_ME=new LessonController();
    	return _ME;
    }
    
    public static Result indexHtml() {
        return ok(views.html.index.render("Hello Play Framework from "+thisClass.getName()+".index"));
    }
    public static Result createHtml() {
    	return ok(views.html.index.render("Hello Play Framework from "+thisClass.getName()+".create"));
    }
    public static Result readHtml(String id) {
    	return ok(views.html.index.render("Hello Play Framework from "+thisClass.getName()+".read("+id+")"));
    }
    public static Result updateHtml(String id) {
    	return ok(views.html.index.render("Hello Play Framework from "+thisClass.getName()+".update("+id+")"));
    }
    public static Result deleteHtml(String id) {
    	return ok(views.html.index.render("Hello Play Framework from "+thisClass.getName()+".delete("+id+")"));
    }
    
    public static Result index() {
    	LessonController me = me();
    	return play.mvc.Results.ok(Json.toJson(me.list()));
    }
    @BodyParser.Of(BodyParser.Json.class)
    public static Result create() {
    	Lesson model = null;
    	if(model==null){
	    	JsonNode json=request().body().asJson();
	    	model = Json.fromJson(json, Lesson.class);
    	}
    	List<StudentLesson> l = model.getStudents();
    	LessonController me = me();
    	
    	model=me.createIt(model);
		if(l!=null && l.isEmpty()==false){
    		updateRelation(l, model);
    		model=me.updateIt(model);
		}
		model=me.refresh(model);
    	return play.mvc.Results.created(Json.toJson(model));
    }
    public static Result read(Long id) {

    	LessonController me = me();
    	Lesson dr = me.get(id);
    	if(dr==null)
    		play.mvc.Results.badRequest("Item not found :"+id);
    	
    	return play.mvc.Results.ok(Json.toJson(dr));
    }
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update(Long id) {
    	Lesson model = null;
    	if(model==null){
	    	JsonNode json=request().body().asJson();
	    	model = Json.fromJson(json, Lesson.class);
    	}
    	model.setId(id);
    	List<StudentLesson> l = model.getStudents();
		if(l!=null)
			updateRelation(l, model);
    	LessonController me = me();
    	model= me.updateIt(model);
    	model=me.refresh(model);
    	return play.mvc.Results.ok(Json.toJson(model));
    }
    public static Result delete(Long id) {
    	try{
    		me().deleteIt(id);
    		return play.mvc.Results.ok("Deleted "+id);
    	}catch(Exception ex){
    		return play.mvc.Results.internalServerError(Json.toJson(ex));
    	}
    }

}
