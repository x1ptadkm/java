/* Lớp chứa các hành vi giúp nhập data thông dụng trong các ứng dụng console 
   (ứng dụng sử dụng bàn phím và màn hình chuẩn). Các hành vi trong lớp 
   này đều là các hành vi static để không cần phải tạo object thuộc lớp.*/

/* Ứng dụng lớn bao gồm nhiều lớp. Một cách để quản lý code là phân chia các 
   file code vào các thư mục con, trong Java gọi là gói (package). Trình biên 
   dịch (TBD) sẽ biên dịch code theo từng dòng từ trên xuống dưới nên lệnh khai 
   báo package phải là dòng lệnh đầu tiên trong file.java. Khi biên dịch lệnh 
   package, nếu thư mục con này chưa có thì TBD sẽ tự tạo thư mục này
*/

// Lớp này được để trong gói tools- Thường dùng chữ thường cho tên gói
package tool; 
// Đưa vào (import) để sử dụng lớp Scanner trong gói java.util
import java.io.IOException;
import java.util.Scanner; 
import java.util.Date; // lớp mô tả cho ngày tháng
import java.text.DateFormat; // lớp mô tả dạng ngày tháng
import java.text.SimpleDateFormat;// định dạng ngày tháng đơn giản dd-MM-yyyy...
// lớp mô tả lỗi khi phân tích (parse) string sang số/Date...
import java.text.ParseException; 
import java.util.Calendar; //Lớp trừu tượng mô tả chung cho lịch  
import java.util.GregorianCalendar; //Lớp cụ thể cho dương lịch

public class ConsoleInputter {
    /* Tạo đối tượng bàn phím (sc) dạng static và dùng nó trong suốt 
     chương trình. Nghĩa là khi ứng dụng chạy thì chỉ dùng 1 đối tượng Scanner.
     Nếu mỗi lần cần nhập dữ liệu lại chạy lệnh new Scanner(System.in), 
     hiệu năng của chướng trình bị giảm do cơ chế cấp bộ nhớ cho object.*/
    public static Scanner sc = new Scanner(System.in); 
    /* Nhập data dạng boolean. Cách dùng:
       boolean cont = getBoolean ("Do you want to continue");
    */ 
    public static boolean getBoolean(String prompt){
        // xuất lời nhắc nhở cùng với giải thích cách nhập
        System.out.print(prompt + " (Y/N, T/F, 1/0)?: "); 
        String data = sc.nextLine().trim().toUpperCase(); // lấy vào 1 chuỗi 
        char c = data.charAt(0); // lấy ký tự đầu tiên do user trả lời
        // trả trị true cho 3 trường hợp sau / ngược lại trả trị false
        return c=='Y' || c=='T' || c=='1';
    }
    /* Nhập số nguyên trong 1 khoảng [min,max]
       Cách dùng: int age = getInt("Age", 18,60);
    */
    public static int getInt(String prompt, int min, int max){
        int result = 0;
        do{
             System.out.print(prompt + "[" + min + ", " + max + "]: ");
            result = Integer.parseInt(sc.nextLine().trim());
            if (result < min || result > max )
                System.out.println("Value range: " + "[" + min + ", " + max + "]" );          
        } while (result < min || result > max );
        return result;
    }
    /* Nhập số thực trong 1 khoảng [min,max]
       Cách dùng: double salary = getDouble("Salary", 1.0,4000.0);
    */
    public static double getDouble(String prompt, double min, double max){
        double result = 0;
        do{
            System.out.print(prompt + "[" + min + ", " + max + "]: ");
            result = Double.parseDouble(sc.nextLine().trim());
            if (result < min || result > max )
                System.out.println("Value range: " + "[" + min + ", " + max + "]" );            
        } while (result < min || result > max );
        return result;
    }
    // Nhập 1 chuỗi bất kỳ
    public static String getStr(String prompt){
        System.out.print(prompt + ": "); // xuất lời nhắc nhở
        return sc.nextLine().trim(); 
    }
    /* Nhập 1 chuỗi theo mẫu quy định (pattern). Vì pattern có nhiều dạng nên
       trong prompt nên có lời giải thích về mẫu nhập. Nếu user nhập sai, một
       thông báo errorMsg được xuất ra. Thí dụ một cách dùng:
       String phone = getStr("Phone no.", "[\\d]{9}|[\\d]{11}", "Phone:9/11 digits!"); 
    Tham khảo thêm về Regular Expression cho mô tả pattern: 
    https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html 
    https://www.w3schools.com/java/java_regex.asp
    */
    public static String getStr(String prompt, String pattern, String errorMsg){
        String data;
        boolean valid;
        do{
            System.out.print(prompt + ": ");
            data = sc.nextLine().trim();
            valid = data.matches(pattern);// kiểm tra data trùng với mẫu không
            if (!valid) System.out.println(errorMsg);// không trùng mẫu
        } while (!valid); // lặp lại nếu data không trùng mẫu
        return data; 
    }
    /* Nhập Date theo mẫu dd-MM-yyyy hoặc MM-dd-yyyy, .... Cách dủng
       Date d = getDate("Date of birth:", "dd-MM-yyyy");
       Ở đây hành vi parse(...) của lớp DateFormat được dùng. Hành vi này sẽ 
       tự động điều chỉnh ngày lên xuống để có ngày kết quả phù hợp. 
       Thí dụ 32-12-2024 sẽ chuyển thành 01/01/2025.
       Nếu không muốn dùng cơ chế tự động này, bạn cần tự viết lấy cơ chế 
       chuyển đổi, Cách làm được đề nghị: 
       - Nhập chuỗi
       - Cắt chuỗi thành 3 thành phần (date, month, year)
       - Kiểm tra tính hợp lệ của 3 thành phần này (lưu ý về năm nhuận)
       - Nếu dữ liệu nhập hợp lệ mới nhờ DataFormat chuyển String sang Date.
    */
    public static Date getDate(String prompt, String dateFormat){
        String dateStr;
        Date d;
        // Tạo DateFormat formatter với date format trong tham sồ
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        do{
            System.out.print(prompt + ": "); // xuất lời nhắc
            dateStr = sc.nextLine().trim(); // nhập data
            try{ // phân tích String -> Date. 
                // Hành vi parse sẽ tự động điều chỉnh phù hợp. 
                //Thí dụ 32-12-2024 sẽ chuyển thành 01/01/2025 
                 
                d = formatter.parse(dateStr); 
            }
            catch (ParseException e){ // nếu phân tích có lỗi xuất thông báo
                System.out.println("Date format should be " + dateFormat + ".");
                d = null;
            }
        } while (d==null);
        return d; 
    }
    /* Đổi Date sang chuỗi dd-MM-yyyy/ MM-dd-yyyy,... Cách dùng:
       System.out.println(dateStr(aDate, "dd-MM-yyyy"));
     */
    public static String dateStr(Date date, String dateFormat){
        if (date==null) return null;
        // Tạo DateFormat object làm việc với định đạng trong tham số thứ hai
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        // rồi trả kết quả sau khi đã chuyển dạng
        return formatter.format(date);
    }
    /* Lấy 1 thành phần trong Date. Lớp Calender giúp truy xuất các thành phần
       Cách dùng: int year = getPart(aDate, Calendar.YEAR);
       Chú ý: Thành phần tháng cho kết quả : Tháng 1 --> 0
    */
    public static int getPart(Date d, int calendarPart){
        GregorianCalendar cal = new GregorianCalendar();// tạo calandar
        cal.setTime(d); // cho calendar mang ngày d
        return cal.get(calendarPart);
    }
    /* Thay vì xây dựng lớp cho Menu, hàm menu được xây dựng. Cú pháp DataType... 
       (hàm có số đối sớ thay đổi)mô tả cho 1 nhóm với số phần tử tùy ý. 
       Trình biên dịch cư xử với tham số kiểu này giống như mảng 1 chiều. */
    
    /*Menu trả về số int mà người dùng chọn. 
    * Cách dùng: int choice = intMenu("Add", "Search", "Remove");
    */
    public static int intMenu(Object... options) {
        int n = options.length;
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + " - " + options[i]);
        }

        int choice = -1;
        do {
            try {
                choice = getInt("Choose", 1, n);
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (choice < 1 || choice > n);

        return choice;
    }

    /*Menu trả về object mà người dùng chọn 
     Cách dùng: String objChoice = (String)objMenu("Add", "Search", "Remove");
    */
    public static Object objMenu (Object... options){
        int choice = intMenu(options);
        return options[choice-1];
    }   
    
    
    
    // sinh key dua tren ngay thang
    public static String DateKeyGen(){
        Date now = new Date();// lay ngay hien tai
        //chuyen thanh dang chuoi theo mau
        SimpleDateFormat f =  new SimpleDateFormat("yyyMMddhhmmss");
        return f.format(now);   
    }
 
    //Pause
    public static void pause() {
    System.out.println("Press Enter to continue...");
    sc.nextLine();
    }
    
    public static boolean isSameDay(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }
    // TESTS
    public static void main (String[] args){
        // Test nhập boolean
        boolean b = ConsoleInputter.getBoolean("Gender");
        System.out.println("Data input: " + b);
        
        // Test nhập số
        int age = ConsoleInputter.getInt("Age", 18, 60);
        System.out.println("Age inputted: " + age);
        /* Nhập số int lớn hơn 0. Các kiểu dữ liệu số đã định nghĩa sẵn 
           tầm trị bằng 2 hằng MIN_VLUE, MAX_VALUE
        */
        int nItem = ConsoleInputter.getInt("Number of Item", 1, Integer.MAX_VALUE);
        System.out.println("Number of items: " + nItem);
        // Nhập lương là số thực ít nhất là 200
        double salary = ConsoleInputter.getDouble("Sal", 200, Double.MAX_VALUE);
        System.out.println("Salaray inputted: " + salary);
       
        // Test nhập 1 String bất kỳ
        String str;
        str = ConsoleInputter.getStr("Input a string");
        System.out.println("Data input: " + str);

        // Test nhập số phone theo mẫu: 9 số hoặc 11 số có chỉ định tính từ 
        // đầu chuỗi nhập (chỉ định bằng ^) đến cuối chuỗi nhập ($)
        str = ConsoleInputter.getStr("Phone 1","^[\\d]{9}|[\\d]{11}$","9/11 digits!");
        System.out.println("Phone 1 input: " + str);
        str = ConsoleInputter.getStr("Phone 2","[\\d]{9}|[\\d]{11}","9/11 digits!");
        System.out.println("Phone 2 input: " + str);
        
        // Test xuất Date, truy xuất thành phần của Date
        Date d = new Date();  //Lấy ngày hiện hành trong máy tính 
        System.out.println(d);
        System.out.println("MM-dd-yyyy: " +dateStr(d, "MM-dd-yyyy"));
        System.out.println("dd-MM-yyyy: " + dateStr(d, "dd-MM-yyyy"));
        System.out.println("yyyy-MM: " + dateStr(d, "yyyy-MM"));
        System.out.println("Year: " + getPart(d, Calendar.YEAR));
        // Month bắt đầu từ 0 nên phải cộng thêm 1
        System.out.println("Month: " + getPart(d, Calendar.MONTH + 1));
        System.out.println("Date: " + getPart(d, Calendar.DATE));
        // Test nhập và xuất Date
        d= ConsoleInputter.getDate("Date of birth dd-MM-yyyy", "dd-MM-yyyy");
        System.out.println(d);
        System.out.println("dd-MM-yyyy: " +dateStr(d, "dd-MM-yyyy"));
        
        // Test các menu
        int choice = intMenu("Add", "Search", "Remove", "Update", "Print");
        System.out.println("User choice (int): " + choice);
        String objChoice = (String)objMenu("Add", "Search", "Remove", 
                                             "Update", "Print");
        System.out.println("User choice (obj): " + objChoice);       
        
        // test sinh ma tu dong
        String code = DateKeyGen();
        System.out.println("Code: " + code);
    } // main()   
}// class ConsoleInputter
    

