Please provide review-comments for the code below:

```

General improvement comment: Should also probably use repository and entity classes instead of using java sql library directly

@Component
public class MyAction {
    public boolean debug = true; //variable should be private as it is not exposed to outside classes
    @Autowired
    public DataSource ds; //this variable should also be private as it is not and should not be exposed to outside classes

    /**
     General collection interface should not be used as return type for this method, instead the method return type should be
     the specific type, which in this case should be a List/ArrayList.
     */
    public Collection getCustomers(String firstName, String lastName, String address, String zipCode, String city) throws SQLException {
        //variable should be named properly for easier readability, in this case for example "connection" is better
        Connection conn = ds.getConnection();
        //unnecessary to initialize a new String object here, just value is fine. Also, maybe using a string builder might be better
        String query = new String("SELECT * FROM customers where 1=1");
        if (firstName != null) {
            query = query + " and first_name = '" + firstName + "'";
        }
        //wrong conditional statement, should be checking lastName and also use "lastName" variable in resulting query in the body
        if (firstName != null) {
            query = query + " and last_name = '" + firstName + "'";
        }
        //wrong conditional statement, should be checking address instead of firstName
        if (firstName != null) {
            query = query + " and address = '" + address + "'";
        }
        //wrong conditional statement, should be checking zipCode instead of firstName
        if (firstName != null) {
            query = query + " and zip_code = '" + zipCode + "'";
        }
        //wrong conditional statement, should be checking city instead of firstName
        if (firstName != null) {
            query = query + " and city = '" + city + "'";
        }
        //variables below should be named better for easier readability, in this case for example "statement" and "result" would be better
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List customers = new ArrayList(); //list should have a specific type for elements, in this case it is Customer object
        while (rs.next()) {
            //the column values are returned as String so the array can be the type of String instead of Object
            Object[] objects = new Object[] { rs.getString(1), rs.getString(2) };
            //redundant if check as debug is always set to true and never changed anywhere in the loop or program so should be removed
            //also proper way to debug is to use log through a library like Lombok instead of using print to console statements here
            if (debug) print(objects, 4);
            customers.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return customers;
    }

    public void print(Object[] s, int indent) {
        for (int i=0; i<=indent; i++) System.out.print(' ');
        printUpper(s);
    }

    public static void printUpper(Object [] words){
        int i = 0;
        try {
            //infinite loop unless exception is thrown, not the correct way to terminate loop. Should have better proper termination condition
            while (true){
                if (words[i].getClass() == String.class) {
                    //2 semi-colons below, one should be removed
                    String so = (String)words[i];;
                    so = so.toUpperCase();
                    System.out.println(so);
                }
                i++;
            }
          //incorrect way to terminate loop as described above, also no proper handling of exception in the catch block body
        } catch (IndexOutOfBoundsException e) {
            //iteration complete
        }
    }
}
```
