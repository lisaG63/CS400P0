import java.io.File;
import java.util.Scanner;

public class test {

  public static void main(String[] args) {
    File f = new File("input.txt");
    Scanner scan = null;
    try {
        scan = new Scanner(f);//从文件接收数据
    } catch (Exception e) {
    }
    StringBuffer sb = new StringBuffer();
    while (scan.hasNext()) {
        sb.append(scan.next()).append("\n");
    }
    System.out.println("文件内容是：" + sb);

  }

}
