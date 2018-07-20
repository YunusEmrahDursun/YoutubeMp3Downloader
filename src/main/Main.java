/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Main {

   
     static WebDriver webDriver=null;
    static PrintStream originalStream,dummyStream;
     public static WebDriver phantomjs(){
         //this function work with selected phantomjs.exe from out of project 
       DesiredCapabilities caps = new DesiredCapabilities();
         //caps.setJavascriptEnabled(true);
       // caps.setCapability("takesScreenshot", true);
      caps.setCapability(
                        PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"./phantomjs.exe"
                                                                                  // example "C:\\Users\\x\\Desktop\\phantomjs-1.9.7-windows\\phantomjs.exe"
                    );
        String[]  a={"--ignore-ssl-errors=true","--ssl-protocol=TLSv1","--webdriver-loglevel=NONE"};
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, a);
       return new  PhantomJSDriver(caps);
       
    }
    public static WebDriver chrome(){
    //this function work with selected chrome driver from out of project
    System.setProperty("webdriver.chrome.driver", "path/chromedriver.exe"
                                                  // example C:\\Users\\x\\Desktop\\chromedriver.exe
            );
       return new ChromeDriver();     
    }
    public static WebDriver phantomjsX() throws Exception {
        //if you want to work with phantomjs at inside(under of src/main folder) of jar use this function. running steps on netbeans : right click build.xml "Run target->other targets->package-for-store"
         // this function need to work on
        DesiredCapabilities caps = new DesiredCapabilities();
        
          String[]  a={"--ignore-ssl-errors=true","--ssl-protocol=TLSv1"};
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, a);
        File f = new File ("./phantomjs.exe");
        if (!f.exists()) {   
             InputStream in = Main.class.getResourceAsStream("phantomjs.exe");
             OutputStream out = null;
                   out=  new FileOutputStream("./phantomjs.exe");
             IOUtils.copy(in, out);
        }
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"./phantomjs.exe"  );
         return new  PhantomJSDriver(caps);
    }
    
    public static void main(String[] args) throws MalformedURLException, IOException {
            try{
       webDriver=phantomjs(); 
           if(args.length==2)
           { if(args[0].equals("/p") && args[1]!=null)
                { playlistParser(args[1]);
                     System.out.println("PlaylistLink="+args[1]);
                }}
           else if(args[0]!=null)
                {
                    System.out.println("Link="+args[0]);
                 List<String> dizi=new ArrayList<>();
                 dizi.add(args[0]);
                playlistDownload(dizi);
                }
            }catch(Exception e){
                System.out.println(e);}
      
     
    
   
        
    }
    public static void playlistDownload(List<String> dizi) throws Exception {
    
      JavascriptExecutor executer=(JavascriptExecutor)webDriver;                 
        webDriver.get("https://www.onlinevideoconverter.com/tr/mp3-converter");
             
        WebDriverWait webDriverWait=new WebDriverWait(webDriver,999999);
       
        webDriverWait.until((i)->(executer.executeScript("return document.readyState").equals("complete")));
        System.out.println("Starting...");
         URLConnection conn;
         InputStream is;
         OutputStream outstream ;
        for (String string : dizi) {
            webDriver.findElement(By.id("texturl")).sendKeys(string);
             webDriver.findElement(By.id("convert1")).click();
             
             WebDriverWait webDriverWait2=new WebDriverWait(webDriver,999999);
             System.out.println("Converting 0%...");
              webDriverWait2.until(new Function(){
             @Override
               public Object apply(Object t) {
                   String str=(String)executer.executeScript("return (document.getElementsByClassName('loader-progress')!=null && document.getElementsByClassName('loader-progress')[0].innerText).toString()");
                   if(str!=null && !str.equals("false") && !str.equals("0%"))
                            System.out.println("Converting "+str+"...");
                   
               return executer.executeScript("return (document.getElementById('downloadq')!=null)+''").equals("true");
               }
             });
            // webDriverWait2.until((i)->(executer.executeScript("return (document.getElementById('downloadq')!=null)+''").equals("true")));
            
             conn = new URL(webDriver.findElement(By.id("downloadq")).getAttribute("href")).openConnection();
             is = conn.getInputStream();
             outstream = new FileOutputStream(new File("./"+webDriver.findElement(By.xpath("//div[@class='download-section-1-1-title-content']/p[1]/b/a")).getText()+".mp3"));
             System.out.println("Downloading("+webDriver.findElement(By.xpath("//div[@class='download-section-1-1-title-content']/p[1]/b/a")).getText()+")...");
               byte[] buffer = new byte[4096];
             int len;
             while ((len = is.read(buffer)) > 0) {
                outstream.write(buffer, 0, len);
            }              
              outstream.close();
              webDriver.navigate().to("https://www.onlinevideoconverter.com/tr/mp3-converter");
                WebDriverWait webDriverWait3=new WebDriverWait(webDriver,999999);
                webDriverWait3.until((i)->(executer.executeScript("return document.readyState").equals("complete")));
      }
        webDriver.close();
       
    }
    public static void playlistParser(String link) throws Exception{
        JavascriptExecutor executer=(JavascriptExecutor)webDriver;   
         webDriver.get(link);
         WebDriverWait webDriverWait=new WebDriverWait(webDriver,999999);
        webDriverWait.until((i)->(executer.executeScript("return document.readyState").equals("complete")));
         List<WebElement> getlinks=webDriver.findElements(By.xpath("//a[@class='pl-video-title-link yt-uix-tile-link yt-uix-sessionlink  spf-link ']"));
         List<String> dizi=new ArrayList<>();
        for (WebElement x : getlinks) {
            dizi.add("https://www.youtube.com"+x.getAttribute("href"));
        }
        if(!dizi.isEmpty())
              playlistDownload(dizi);  
    }
    
}
