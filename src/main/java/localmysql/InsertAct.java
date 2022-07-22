package localmysql;

import com.mysql.cj.jdbc.exceptions.MySQLQueryInterruptedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

/**
 * @author litao34
 * @ClassName InsertAct
 * @Description TODO
 * @CreateDate 2022/5/6-11:20 AM
 **/
public class InsertAct {
    private static int upRandon = 1;
    public void batchInsert() throws SQLException, ClassNotFoundException, InterruptedException {
        Connection connection =
                MysqlUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into t_table set value = ? ");
        for (int j = 1 ;j < 100 ; j ++){

            for (int i = 0; i < 100000 ; i ++){
                preparedStatement.setString(1, getRandonStr(8));
                int i1 = preparedStatement.executeUpdate();
                if (i % 100_00 == 0){
                    System.out.println(i);
                }
            }
            System.out.println("one batch finished : [100/" + j + "]");
        }

    }

    private static String getRandonStr(int size) {
        Random random = new Random(System.currentTimeMillis() * upRandon);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<size;i++){
            char temp = (char)('a' +  (upRandon = random.nextInt(25)));
            stringBuilder.append("" + temp);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
        new InsertAct().batchInsert();
//        System.out.println(getRandonStr(8));
    }

}
