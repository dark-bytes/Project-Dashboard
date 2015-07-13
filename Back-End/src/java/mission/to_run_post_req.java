/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mission;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.jasper.tagplugins.jstl.core.Url;

/**
 *
 * @author SUDHANSHU
 */
public class to_run_post_req {
    public static void main(String args[]) throws MalformedURLException, IOException{
        URL uri = new URL("http://localhost:8080/project_manage_dashboard/webresources/mission/edit");
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
	conn.setRequestProperty("Content-Type", "application/json");
        
        String input = "{\n" +
"    \"vision\":\"ajhsdahjv\",\n" +
"    \"deleteId\":[1,-2,3],\n" +
"    \"pointId\":[1,2,3],\n" +
"    \"Update\":[\n" +
"        {\n" +
"            \"what\":\"mission\",\n" +
"            \"id\":1,\n" +
"            \"value\":\"adsda\",\n" +
"            \"mid\":1,\n" +
"            \"add\":0\n" +
"        },\n" +
"        {\n" +
"            \"what\":\"mission\",\n" +
"            \"id\":-2,\n" +
"            \"value\":\"sadjajks\",\n" +
"            \"mid\":-2,\n" +
"            \"add\":1\n" +
"        },\n" +
"        {\n" +
"            \"what\":\"point\",\n" +
"            \"id\":3,\n" +
"            \"value\":\"asjdjh\",\n" +
"            \"mid\":1,\n" +
"            \"add\":0\n" +
"        },\n" +
"        {\n" +
"            \"what\":\"mission\",\n" +
"            \"id\":-5,\n" +
"            \"value\":\"sadjajks\",\n" +
"            \"mid\":-5,\n" +
"            \"add\":1\n" +
"        }\n" +
"    ]\n" +
"}";
        //BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\SUDHANSHU\\Documents\\NetBeansProjects\\project_manage_dashboard\\src\\java\\mission\\sample.json"));
        //String test;
       // while((test = br.readLine()) != null){
        //    System.out.println(test);
       //     input = input + test;
      //  }
        System.out.println(input);
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        try{
                BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
 
		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
 
		conn.disconnect();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
