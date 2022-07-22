package test;

/**
 * @author litao34
 * @ClassName JavaBind
 * @Description TODO
 * @CreateDate 2022/6/16-11:46 AM
 **/
public class JavaBind {
    public static void main(String[] args) {

// An example of static and dynamic binding in Java

        Insurance current = new CarInsurance();

// dynamic binding based upon object

        int premium = current.premium();

// static binding based upon class

        String category = current.category();

        System.out.println("premium : " + premium);

        System.out.println("category : " + category);

    }


//Output premium : 200

//category : Insurance
}

class Insurance{

    public static final int LOW = 100;

    public int premium(){

        return LOW;

    }

    public static String category(){

        return "Insurance";

    }

}

class CarInsurance extends Insurance{

    public static final int HIGH = 200;

    public int premium(){

        return HIGH;

    }

    public static String category(){

        return "Car Insurance";

    }

}
