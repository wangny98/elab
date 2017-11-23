import java.lang.reflect.Field;


public class GetSQL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class cs = Class.forName("UserBean");
			Field[] fs = cs.getFields();
			for(Field f : fs){/*
				if(f.getType() instancesOf )*/
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
