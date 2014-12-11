package self;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoJava {
	public static void main(String[] args) throws IOException, Exception {
		Schema schema = new Schema(1, "com.example/entity");
		addNote(schema);
		new DaoGenerator().generateAll(schema, "../GreenDaoAndroid/src");
	}

	private static void addNote(Schema schema) {
		// TODO Auto-generated method stub
		Entity note = schema.addEntity("Note");
		note.addStringProperty("name");
		note.addStringProperty("location");
		note.addIntProperty("id");
	}
}
